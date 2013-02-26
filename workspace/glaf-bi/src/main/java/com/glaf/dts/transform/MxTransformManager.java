/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.dts.transform;

import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.glaf.core.business.TransformTable;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDefinitionService;

import com.glaf.core.util.FieldType;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.dts.domain.TransformTask;
import com.glaf.dts.service.ITransformExcecutionService;
import com.glaf.dts.service.ITransformTaskService;

public class MxTransformManager {
	protected static final Log logger = LogFactory
			.getLog(MxTransformManager.class);

	protected IQueryDefinitionService queryDefinitionService;

	protected ITableDefinitionService tableDefinitionService;

	protected ITransformExcecutionService transformExcecutionService;

	protected ITransformTaskService transformTaskService;

	public MxTransformManager() {
		queryDefinitionService = ContextFactory
				.getBean("queryDefinitionService");
		tableDefinitionService = ContextFactory
				.getBean("tableDefinitionService");
		transformExcecutionService = ContextFactory
				.getBean("transformExcecutionService");
		transformTaskService = ContextFactory.getBean("transformTaskService");
	}

	protected QueryDefinition fill(String queryId, String currentSql) {
		Stack<QueryDefinition> stack = queryDefinitionService
				.getQueryDefinitionStack(queryId);
		QueryDefinition query = queryDefinitionService
				.getQueryDefinition(queryId);
		List<Map<String, Object>> resultList = null;
		if (stack != null && stack.size() > 0) {
			while (stack.size() > 0) {
				query = stack.pop();
				if (StringUtils.isNotEmpty(currentSql)
						&& StringUtils.equals(queryId, query.getId())) {
					currentSql = QueryUtils.replaceSQLVars(currentSql);
					query.setSql(currentSql);
					logger.debug("##currentSql:" + currentSql);
				}
				logger.debug("------------------------------------------------");
				logger.debug("####title:" + query.getTitle());
				logger.debug("####parentId:" + query.getParentId());
				if (query.getParentId() != null) {
					/***
					 * 只有当父查询有返回结果才进行当前查询
					 */
					if (resultList != null && !resultList.isEmpty()) {
						/**
						 * 如果有父节点，将父节点的结果作为当前节点的参数执行查询
						 */
						logger.debug("###########get result list###########");

						if (query.getChild() != null) {
							resultList = this.prepare(query, resultList);
						} else {
							resultList = this.prepare(query, resultList.get(0));
						}
					}
				} else {
					/**
					 * 如果没有父节点，说明已经是根节点，可以直接调用结果作为返回值
					 */
					logger.debug("--------get result list---------");
					resultList = this.prepare(query);
				}
			}
		}
		return query;
	}

	protected List<Map<String, Object>> prepare(QueryDefinition query) {
		logger.debug("-------------------------1 start------------------------");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			conn = DBConnectionFactory.getConnection();
			logger.debug("-------------------------1 connection------------------------");

			String sql = QueryUtils.replaceSQLVars(query.getSql());
			logger.debug(">sql=" + query.getSql());
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();
			rsmd = rs.getMetaData();
			logger.debug("-------------------------1 executeQuery------------------------");
			int count = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> rowMap = new HashMap<String, Object>();
				for (int i = 1; i <= count; i++) {
					String columnName = rsmd.getColumnName(i);
					try {
						rowMap.put(columnName, rs.getObject(i));
					} catch (SQLException ex) {
						rowMap.put(columnName, rs.getString(i));
					}
				}
				resultList.add(rowMap);
			}

			psmt.close();
			rs.close();

			query.setResultList(resultList);

			// logger.debug(">resultList=" + resultList);
			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
			logger.debug("-------------------------1 start------------------------");
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> prepare(QueryDefinition query,
			List<Map<String, Object>> paramList) {
		logger.debug("-------------------------3 start------------------------");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> tmpResultList = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			conn = DBConnectionFactory.getConnection();
			logger.debug("-------------------------3 connection------------------------");
			for (Map<String, Object> paramMap : paramList) {
				logger.debug("sql:" + query.getSql());
				SqlExecutor sqlExecutor = JdbcUtils.rebuildSQL(query.getSql(),
						paramMap);
				logger.debug("sql:" + sqlExecutor.getSql());
				psmt = conn.prepareStatement(sqlExecutor.getSql());
				if (sqlExecutor.getParameter() != null) {
					List<Object> values = (List<Object>) sqlExecutor
							.getParameter();
					JdbcUtils.fillStatement(psmt, values);
					logger.debug("values:" + values);
				}

				logger.debug("-------------------------3 executeQuery------------------------");
				rs = psmt.executeQuery();
				rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				while (rs.next()) {
					Map<String, Object> rowMap = new HashMap<String, Object>();
					for (int i = 1; i <= count; i++) {
						String columnName = rsmd.getColumnName(i);
						try {
							rowMap.put(columnName, rs.getObject(i));
						} catch (SQLException ex) {
							rowMap.put(columnName, rs.getString(i));
						}
					}
					resultList.add(rowMap);
					tmpResultList.add(rowMap);

				}

				psmt.close();
				rs.close();

				// logger.debug("resultList :" + tmpResultList);
				tmpResultList.clear();
			}

			query.setResultList(resultList);

			// logger.debug("resultList size:" + resultList.size());

			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
			logger.debug("-------------------------3 end------------------------");
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> prepare(QueryDefinition query,
			Map<String, Object> paramMap) {
		logger.debug("-------------------------2 start------------------------");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			conn = DBConnectionFactory.getConnection();
			logger.debug("-------------------------2 connection------------------------");
			SqlExecutor sqlExecutor = JdbcUtils.rebuildSQL(query.getSql(),
					paramMap);
			logger.debug("--2 sql:" + sqlExecutor.getSql());
			psmt = conn.prepareStatement(sqlExecutor.getSql());
			if (sqlExecutor.getParameter() != null) {
				List<Object> values = (List<Object>) sqlExecutor.getParameter();
				JdbcUtils.fillStatement(psmt, values);
			}

			rs = psmt.executeQuery();
			logger.debug("-------------------------2 executeQuery------------------------");
			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> rowMap = new HashMap<String, Object>();
				for (int i = 1; i <= count; i++) {
					String columnName = rsmd.getColumnName(i);
					try {
						rowMap.put(columnName, rs.getObject(i));
					} catch (SQLException ex) {
						rowMap.put(columnName, rs.getString(i));
					}
				}
				resultList.add(rowMap);
			}

			psmt.close();
			rs.close();

			query.setResultList(resultList);

			// logger.debug("resultList:" + resultList);

			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
			logger.debug("-------------------------2 end------------------------");
		}
	}

	public TableDefinition toTableDefinition(QueryDefinition query) {
		String currentSql = query.getSql();
		return this.toTableDefinition(query, currentSql);
	}

	@SuppressWarnings("unchecked")
	public TableDefinition toTableDefinition(QueryDefinition query,
			String currentSql) {

		if (query.getId() != null) {
			query = this.fill(query.getId(), currentSql);
		}

		if (query.getParentId() != null) {
			QueryDefinition parent = this.fill(query.getParentId(), null);
			if (parent != null) {
				logger.debug("parent:" + parent.getTitle());
				logger.debug("resultList:" + parent.getResultList());
				query.setParent(parent);
			}
		}

		String sql = currentSql;
		List<Object> values = null;
		logger.debug("currentSql:" + currentSql);
		if (query.getParentId() != null) {
			if (query.getParent() != null
					&& query.getParent().getResultList() != null
					&& !query.getParent().getResultList().isEmpty()) {
				for (Map<String, Object> paramMap : query.getParent()
						.getResultList()) {
					SqlExecutor sqlExecutor = JdbcUtils.rebuildSQL(sql,
							paramMap);
					sql = sqlExecutor.getSql();
					sql = QueryUtils.replaceSQLVars(sql);
					values = (List<Object>) sqlExecutor.getParameter();
					break;
				}
			}
		} else {
			if (sql != null && sql.indexOf("${") != -1) {
				sql = QueryUtils.replaceSQLVars(sql);
				SqlExecutor sqlExecutor = JdbcUtils.rebuildSQL(sql,
						new HashMap<String, Object>());
				if (sqlExecutor != null) {
					sql = sqlExecutor.getSql();
					sql = QueryUtils.replaceSQLVars(sql);
					values = (List<Object>) sqlExecutor.getParameter();
				}
			}
		}

		logger.debug("sql:" + sql);
		logger.debug("values:" + values);

		TableDefinition table = new TableDefinition();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			conn = DBConnectionFactory.getConnection();

			sql = QueryUtils.replaceSQLVars(sql);
			psmt = conn.prepareStatement(sql);

			if (values != null && !values.isEmpty()) {
				JdbcUtils.fillStatement(psmt, values);
			}

			rs = psmt.executeQuery();
			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				int sqlType = rsmd.getColumnType(i);
				ColumnDefinition column = new ColumnDefinition();
				column.setColumnName(rsmd.getColumnName(i));

				column.setJavaType(FieldType.getJavaType(sqlType));
				column.setPrecision(rsmd.getPrecision(i));
				column.setScale(rsmd.getScale(i));
				table.addColumn(column);

				logger.debug("----------------------------------------");
				logger.debug("sqlType:" + sqlType);
				logger.debug("javaType:" + FieldType.getJavaType(sqlType));
				logger.debug("columnName:" + rsmd.getColumnName(i));
				logger.debug("columnTypeName:" + rsmd.getColumnTypeName(i));
				logger.debug("columnClassName:" + rsmd.getColumnClassName(i));
				logger.debug("columnLabel:" + rsmd.getColumnLabel(i));
				logger.debug("columnDisplaySize:"
						+ rsmd.getColumnDisplaySize(i));
				logger.debug("precision:" + rsmd.getPrecision(i));
				logger.debug("scale:" + rsmd.getScale(i));
			}
			psmt.close();
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
		return table;
	}

	/**
	 * 传递数据
	 * 
	 * @param query
	 *            查询
	 * @param target
	 *            目标表(数据仓库中的事实表)
	 */
	public void transform(QueryDefinition query, TableDefinition target) {
		this.transformInner(query, target);
	}

	/**
	 * 传递数据
	 * 
	 * @param queryId
	 *            查询编号
	 * @param targetTable
	 *            目标表(数据仓库中的事实表)
	 */
	public void transform(String queryId, String targetTable) {
		TableDefinition target = tableDefinitionService
				.getTableDefinition(targetTable);
		QueryDefinition query = queryDefinitionService
				.getQueryDefinition(queryId);
		this.transform(query, target);
	}

	/**
	 * 传递数据
	 * 
	 * @param query
	 *            查询
	 * @param target
	 *            目标表(数据仓库中的事实表)
	 */
	protected void transformInner(QueryDefinition query, TableDefinition target) {
		query = this.fill(query.getId(), null);

		if (query.getParentId() != null) {
			if (query.getParent() != null
					&& query.getParent().getResultList() != null
					&& !query.getParent().getResultList().isEmpty()) {
				this.transformMany(query, target);
			}
		} else {
			this.transformSingle(query, target, new HashMap<String, Object>());
		}
	}

	protected void transformMany(QueryDefinition query, TableDefinition target) {
		logger.debug("------------------------------transformMany--------------");
		if (!StringUtils.equals(query.getTargetTableName(),
				target.getTableName())) {
			return;
		}
		if (query.getParent() != null
				&& query.getParent().getResultList() != null
				&& !query.getParent().getResultList().isEmpty()) {
			int execution = 0;
			logger.debug("#####################################################");
			logger.debug("execution count:"
					+ query.getParent().getResultList().size());

			List<TransformTask> tasks = new ArrayList<TransformTask>();
			List<String> taskIds = new ArrayList<String>();
			for (Map<String, Object> paramMap : query.getParent()
					.getResultList()) {
				execution++;
				TransformTask task = new TransformTask();
				task.setId(query.getId() + "_exec_" + execution);
				task.setStatus(0);
				task.setQueryId(query.getId());
				task.setTableName(query.getTargetTableName());
				task.setTitle(query.getTitle() + " 任务 " + execution);
				task.setSortNo(execution);
				JsonFactory f = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(f);
				Writer w = new StringWriter();
				try {
					mapper.writeValue(w, paramMap);
					task.setParameter(w.toString());
				} catch (Exception e) {
					task.setParameter(JsonUtils.encode(paramMap));
				}
				tasks.add(task);
				taskIds.add(task.getId());
			}

			transformTaskService.insertAll(query.getId(), tasks);

			if (taskIds.size() > 0) {

			}
		}
	}

	/**
	 * 传递数据
	 * 
	 * @param query
	 *            查询
	 * @param target
	 *            目标表(数据仓库中的事实表)
	 */
	protected void transformSingle(QueryDefinition query,
			TableDefinition target, Map<String, Object> paramMap) {
		logger.debug("------------------------------transformSingle--------------");
		if (!StringUtils.equals(query.getTargetTableName(),
				target.getTableName())) {
			return;
		}
		String taskId = query.getId() + "_exec_0";
		TransformTask task = new TransformTask();
		task.setId(taskId);
		task.setStatus(0);
		task.setQueryId(query.getId());
		task.setTitle(query.getTitle() + " 任务 ");
		task.setSortNo(0);
		JsonFactory f = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(f);
		Writer w = new StringWriter();
		try {
			mapper.writeValue(w, paramMap);
			task.setParameter(w.toString());
		} catch (Exception e) {
			task.setParameter(JsonUtils.encode(paramMap));
		}
		transformTaskService.save(task);

		MxTransformThread thread = new MxTransformThread(taskId);
		ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) ContextFactory
				.getBean("threadPoolTaskExecutor");
		if (executor != null) {
			executor.execute(thread);
		}
	}

	/**
	 * 将某个表的全部查询抽取数据到该表
	 * 
	 * @param tableName
	 */
	public void transformTable(String tableName) {
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null) {
			List<QueryDefinition> queries = tableDefinition.getQueries();

			if ("1".equals(tableDefinition.getTemporaryFlag())) {
				// DBUtils.deleteTemporaryTable(tableDefinition.getTableName());
			} else {
				// DBTools.deleteCurrentDayEtlMiddleTable(tableDefinition
				// .getTableName());
			}

			/**
			 * 如果表定义中指定了抽取某些查询，就抽取关联查询的数据到目标表
			 */
			if (tableDefinition != null
					&& StringUtils.isNotEmpty(tableDefinition
							.getAggregationKeys())
					&& !tableDefinition.getQueries().isEmpty()) {
				TransformTable tt = new TransformTable();
				tt.transform(tableName);
			} else {
				for (QueryDefinition query : queries) {
					this.transform(query, tableDefinition);
				}
			}
		}

	}

}