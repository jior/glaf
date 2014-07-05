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

package com.glaf.core.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.TablePage;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.TablePageMapper;
import com.glaf.core.query.QueryCondition;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.Paging;
import com.glaf.core.util.QueryUtils;

@Service("tablePageService")
@Transactional(readOnly = true)
public class MxTablePageServiceImpl implements ITablePageService {
	protected final static Log logger = LogFactory
			.getLog(MxTablePageServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected TablePageMapper tablePageMapper;

	public List<Map<String, Object>> getListData(String sql,
			Map<String, Object> params) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (params != null && !params.isEmpty()) {
			queryMap.putAll(params);
		}
		queryMap.put("queryString", sql);
		return tablePageMapper.getSqlQueryList(queryMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getQueryCount(String querySql, List<QueryCondition> conditions) {
		SqlExecutor sqlExecutor = QueryUtils
				.getMyBatisAndConditionSql(conditions);
		StringBuilder buffer = new StringBuilder();
		buffer.append(querySql);
		if (querySql.toUpperCase().indexOf(" where ") == -1) {
			buffer.append(" where 1=1 ");
		}
		String sql = buffer.toString() + sqlExecutor.getSql();

		Map<String, Object> params = new java.util.HashMap<String, Object>();
		if (sqlExecutor.getParameter() instanceof Map) {
			params.putAll((Map) sqlExecutor.getParameter());
		}

		logger.debug("countSql:\n" + sql);
		logger.debug("params:" + params);

		params.put("queryString", sql);

		int total = tablePageMapper.getSqlQueryCount(params);
		return total;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> getQueryList(String querySql, int begin, int pageSize,
			List<QueryCondition> conditions) {
		SqlExecutor sqlExecutor = QueryUtils
				.getMyBatisAndConditionSql(conditions);

		StringBuilder buffer = new StringBuilder();
		buffer.append(querySql);
		if (querySql.toUpperCase().indexOf(" WHERE ") == -1) {
			buffer.append(" WHERE 1=1 ");
		}
		String sql = buffer.toString() + sqlExecutor.getSql();

		Map<String, Object> params = new java.util.HashMap<String, Object>();
		if (sqlExecutor.getParameter() instanceof Map) {
			params.putAll((Map) sqlExecutor.getParameter());
		}

		logger.debug("sql:\n" + sql);
		logger.debug("params:" + params);

		params.put("queryString", sql);

		if (begin < 0) {
			begin = 0;
		}
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}
		RowBounds rowBounds = new RowBounds(begin, pageSize);
		List<Object> rows = sqlSession.selectList("getSqlQueryList", params,
				rowBounds);
		return rows;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getTableCount(String tableName, String idColumn,
			List<QueryCondition> conditions) {
		SqlExecutor sqlExecutor = QueryUtils
				.getMyBatisAndConditionSql(conditions);
		StringBuilder buffer = new StringBuilder();
		buffer.append(" select count(*) ").append(" from ").append(tableName);
		buffer.append(" where 1=1 ");
		String sql = buffer.toString() + sqlExecutor.getSql();

		Map<String, Object> params = new java.util.HashMap<String, Object>();
		if (sqlExecutor.getParameter() instanceof Map) {
			params.putAll((Map) sqlExecutor.getParameter());
		}

		logger.debug("countSql:\n" + sql);
		logger.debug("params:" + params);

		params.put("queryString", sql);

		int total = tablePageMapper.getSqlQueryCount(params);
		return total;
	}

	public int getTableCount(TablePageQuery query) {
		return tablePageMapper.getTableCount(query);
	}

	public List<Map<String, Object>> getTableData(String tableName,
			int firstResult, int maxResults) {
		TablePageQuery query = new TablePageQuery();
		query.tableName(tableName);
		int begin = query.getFirstResult();
		int pageSize = query.getMaxResults();
		if (begin < 0) {
			begin = 0;
		}
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}
		RowBounds rowBounds = new RowBounds(begin, pageSize);
		List<Map<String, Object>> rows = sqlSession.selectList("getTableData",
				query, rowBounds);
		return rows;
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getTableData(TablePageQuery query) {
		int begin = query.getFirstResult();
		int pageSize = query.getMaxResults();
		if (begin < 0) {
			begin = 0;
		}
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}
		RowBounds rowBounds = new RowBounds(begin, pageSize);
		List<Map<String, Object>> list = sqlSession.selectList("getTableData",
				query, rowBounds);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> getTableList(String tableName, String idColumn,
			int begin, int pageSize, List<QueryCondition> conditions) {
		SqlExecutor sqlExecutor = QueryUtils
				.getMyBatisAndConditionSql(conditions);

		StringBuilder buffer = new StringBuilder();
		buffer.append(" select * ").append(" from ").append(tableName);
		buffer.append(" where 1=1 ");
		String sql = buffer.toString() + sqlExecutor.getSql();

		Map<String, Object> params = new java.util.HashMap<String, Object>();
		if (sqlExecutor.getParameter() instanceof Map) {
			params.putAll((Map) sqlExecutor.getParameter());
		}

		logger.debug("sql:\n" + sql);
		logger.debug("params:" + params);

		params.put("queryString", sql);

		if (begin < 0) {
			begin = 0;
		}
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}
		RowBounds rowBounds = new RowBounds(begin, pageSize);
		List<Object> rows = sqlSession.selectList("getSqlQueryList", params,
				rowBounds);
		return rows;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> getTableList(String tableName, String idColumn,
			Map<String, String> selectColumns, int begin, int pageSize,
			List<QueryCondition> conditions) {
		SqlExecutor sqlExecutor = QueryUtils
				.getMyBatisAndConditionSql(conditions);
		StringBuilder buffer = new StringBuilder();
		buffer.append(" select ");
		if (selectColumns != null && !selectColumns.isEmpty()) {
			Set<Entry<String, String>> entrySet = selectColumns.entrySet();
			for (Entry<String, String> entry : entrySet) {
				String columnName = entry.getKey();
				String columnLabel = entry.getValue();
				if (columnName != null && columnLabel != null) {
					buffer.append(columnName).append(" as ")
							.append(columnLabel);
					buffer.append(" , ");
				}
			}
			buffer.delete(buffer.length() - 2, buffer.length());
			buffer.append(" from ").append(tableName);
		} else {
			buffer.append(" * from ").append(tableName);
		}

		buffer.append(" where 1=1 ");

		String sql = buffer.toString() + sqlExecutor.getSql();
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		if (sqlExecutor.getParameter() instanceof Map) {
			params.putAll((Map) sqlExecutor.getParameter());
		}

		logger.debug("sql:\n" + sql);
		logger.debug("params:" + params);

		params.put("queryString", sql);

		if (begin < 0) {
			begin = 0;
		}
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}
		RowBounds rowBounds = new RowBounds(begin, pageSize);
		List<Object> rows = sqlSession.selectList("getSqlQueryList", params,
				rowBounds);
		return rows;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> getTableList(String tableName, String idColumn,
			Map<String, String> selectColumns, int begin, int pageSize,
			List<QueryCondition> conditions,
			LinkedHashMap<String, Boolean> orderByColumns) {
		SqlExecutor sqlExecutor = QueryUtils
				.getMyBatisAndConditionSql(conditions);
		StringBuilder buffer = new StringBuilder();
		buffer.append(" select ");
		if (selectColumns != null && !selectColumns.isEmpty()) {
			Set<Entry<String, String>> entrySet = selectColumns.entrySet();
			for (Entry<String, String> entry : entrySet) {
				String columnName = entry.getKey();
				String columnLabel = entry.getValue();
				if (columnName != null && columnLabel != null) {
					buffer.append(columnName).append(" as ")
							.append(columnLabel);
					buffer.append(" , ");
				}
			}
			buffer.delete(buffer.length() - 2, buffer.length());
			buffer.append(" from ").append(tableName);
		} else {
			buffer.append(" * from ").append(tableName);
		}

		buffer.append(" where 1=1 ");

		String sql = buffer.toString() + sqlExecutor.getSql();

		if (orderByColumns != null && !orderByColumns.isEmpty()) {
			buffer.delete(0, buffer.length());
			buffer.append(" order by ");
			Set<Entry<String, Boolean>> entrySet = orderByColumns.entrySet();
			for (Entry<String, Boolean> entry : entrySet) {
				String columnName = entry.getKey();
				Boolean ordinal = entry.getValue();
				if (columnName != null && ordinal != null) {
					buffer.append(columnName);
					if (ordinal.booleanValue()) {
						buffer.append(" asc");
					} else {
						buffer.append(" desc");
					}
					buffer.append(" ,");
				}
			}
			if (buffer.toString().endsWith(",")) {
				buffer.delete(buffer.length() - 2, buffer.length());
			}
		}

		sql = sql + buffer.toString();

		Map<String, Object> params = new java.util.HashMap<String, Object>();
		if (sqlExecutor.getParameter() instanceof Map) {
			params.putAll((Map) sqlExecutor.getParameter());
		}

		logger.debug("sql:\n" + sql);
		logger.debug("params:" + params);

		params.put("queryString", sql);

		if (begin < 0) {
			begin = 0;
		}
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}
		RowBounds rowBounds = new RowBounds(begin, pageSize);
		List<Object> rows = sqlSession.selectList("getSqlQueryList", params,
				rowBounds);
		return rows;
	}

	public TablePage getTablePage(TablePageQuery query, int firstResult,
			int maxResults) {
		TablePage tablePage = new TablePage();
		String tableName = query.getTableName();
		tablePage.setTableName(tableName);
		int begin = query.getFirstResult();
		int pageSize = query.getMaxResults();
		if (begin < 0) {
			begin = 0;
		}
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}

		Integer count = (Integer) sqlSession.selectOne("getTableCount",
				Collections.singletonMap("tableName", tableName));
		if (count > 0) {
			tablePage.setTotal(count);
			RowBounds rowBounds = new RowBounds(begin, pageSize);
			List<Map<String, Object>> tableData = sqlSession.selectList(
					"getTableData", query, rowBounds);
			tablePage.setRows(tableData);
			tablePage.setFirstResult(firstResult);
		}

		return tablePage;
	}

	public Paging getTablePaging(TablePageQuery query, int firstResult,
			int maxResults) {
		Paging paging = new Paging();
		String tableName = query.getTableName();
		int begin = query.getFirstResult();
		int pageSize = query.getMaxResults();
		if (begin < 0) {
			begin = 0;
		}
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}

		Integer count = (Integer) sqlSession.selectOne("getTableCount",
				Collections.singletonMap("tableName", tableName));
		if (count > 0) {
			paging.setTotal(count);
			RowBounds rowBounds = new RowBounds(begin, pageSize);
			List<Object> tableData = (List<Object>) sqlSession.selectList(
					"getTableData", query, rowBounds);
			paging.setRows(tableData);
			int currentPage = begin / pageSize + 1;
			paging.setCurrentPage(currentPage);
		}

		return paging;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setTablePageMapper(TablePageMapper tablePageMapper) {
		this.tablePageMapper = tablePageMapper;
	}

}