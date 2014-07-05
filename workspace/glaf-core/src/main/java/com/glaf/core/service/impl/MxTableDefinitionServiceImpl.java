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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.ColumnDefinitionMapper;
import com.glaf.core.mapper.QueryDefinitionMapper;
import com.glaf.core.mapper.TableDefinitionMapper;
import com.glaf.core.query.QueryDefinitionQuery;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.StringTools;

@Service("tableDefinitionService")
@Transactional(readOnly = true)
public class MxTableDefinitionServiceImpl implements ITableDefinitionService {
	protected final static Log logger = LogFactory
			.getLog(MxTableDefinitionServiceImpl.class);

	protected ColumnDefinitionMapper columnDefinitionMapper;

	protected IdGenerator idGenerator;

	protected QueryDefinitionMapper queryDefinitionMapper;

	protected SqlSession sqlSession;

	protected TableDefinitionMapper tableDefinitionMapper;

	public MxTableDefinitionServiceImpl() {

	}

	public int count(TableDefinitionQuery query) {
		query.ensureInitialized();
		if (StringUtils.isEmpty(query.getType())) {
			throw new RuntimeException(" type is null ");
		}
		return tableDefinitionMapper.getTableDefinitionCount(query);
	}

	@Transactional
	public void deleteColumn(String columnId) {
		columnDefinitionMapper.deleteColumnDefinitionById(columnId);
	}

	/**
	 * 删除表定义
	 * 
	 * @param tableName
	 */
	@Transactional
	public void deleteTable(String tableName) {
		columnDefinitionMapper.deleteColumnDefinitionByTableName(tableName);
		tableDefinitionMapper.deleteTableDefinitionById(tableName);
	}

	public ColumnDefinition getColumnDefinition(String columnId) {
		return columnDefinitionMapper.getColumnDefinitionById(columnId);
	}

	public List<ColumnDefinition> getColumnDefinitionsByTableName(
			String tableName) {
		TableDefinition tableDefinition = this.getTableDefinition(tableName);
		if (tableDefinition != null) {
			return tableDefinition.getColumns();
		}
		return null;
	}

	public List<ColumnDefinition> getColumnDefinitionsByTargetId(String targetId) {
		return columnDefinitionMapper.getColumnDefinitionsByTargetId(targetId);
	}

	public List<TableDefinition> getTableColumnsCount(TableDefinitionQuery query) {
		List<TableDefinition> list = tableDefinitionMapper
				.getTableColumnsCount(query);
		return list;
	}

	public TableDefinition getTableDefinition(String tableName) {
		if (tableName == null) {
			return null;
		}
		TableDefinition tableDefinition = tableDefinitionMapper
				.getTableDefinitionById(tableName);
		if (tableDefinition != null) {
			if (StringUtils.isNotEmpty(tableDefinition.getQueryIds())) {
				QueryDefinitionQuery query = new QueryDefinitionQuery();
				List<String> queryIds = StringTools.split(tableDefinition
						.getQueryIds());
				query.queryIds(queryIds);
				List<QueryDefinition> list = queryDefinitionMapper
						.getQueryDefinitions(query);
				if (list != null && !list.isEmpty()) {
					for (QueryDefinition q : list) {
						tableDefinition.addQuery(q);
					}
				}
			}

			List<ColumnDefinition> columns = columnDefinitionMapper
					.getColumnDefinitionsByTableName(tableName);
			if (columns != null && !columns.isEmpty()) {
				tableDefinition.setColumns(columns);
				for (ColumnDefinition column : columns) {
					if (column.isPrimaryKey()) {
						logger.debug("##PrimaryKey:"
								+ column.toJsonObject().toJSONString());
						tableDefinition.setIdColumn(column);
						columns.remove(column);
						break;
					}
				}
			}
		}

		return tableDefinition;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTableDefinitionCountByQueryCriteria(TableDefinitionQuery query) {
		if (StringUtils.isEmpty(query.getType())) {
			throw new RuntimeException(" type is null ");
		}
		return tableDefinitionMapper.getTableDefinitionCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TableDefinition> getTableDefinitionsByQueryCriteria(int start,
			int pageSize, TableDefinitionQuery query) {
		if (StringUtils.isEmpty(query.getType())) {
			throw new RuntimeException(" type is null ");
		}
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TableDefinition> rows = sqlSession.selectList(
				"getTableDefinitions", query, rowBounds);
		return rows;
	}

	public List<TableDefinition> list(TableDefinitionQuery query) {
		query.ensureInitialized();
		if (StringUtils.isEmpty(query.getType())) {
			throw new RuntimeException(" type is null ");
		}
		List<TableDefinition> list = tableDefinitionMapper
				.getTableDefinitions(query);
		return list;
	}

	@Transactional
	public void save(TableDefinition tableDefinition) {
		String tableName = tableDefinition.getTableName();
		tableName = tableName.toUpperCase();
		tableDefinition.setTableName(tableName);
		TableDefinition table = this.getTableDefinition(tableDefinition
				.getTableName());
		if (table == null) {
			tableDefinition.setCreateTime(new Date());
			tableDefinition.setRevision(1);
			if (tableDefinition.getColumns() != null
					&& !tableDefinition.getColumns().isEmpty()) {
				tableDefinition.setColumnQty(tableDefinition.getColumns()
						.size());
			}
			tableDefinitionMapper.insertTableDefinition(tableDefinition);
		} else {
			if (tableDefinition.getColumns() != null
					&& !tableDefinition.getColumns().isEmpty()) {
				tableDefinition.setColumnQty(tableDefinition.getColumns()
						.size());
			}
			tableDefinition.setRevision(tableDefinition.getRevision() + 1);
			tableDefinitionMapper.updateTableDefinition(tableDefinition);
		}

		if (tableDefinition.getColumns() != null
				&& !tableDefinition.getColumns().isEmpty()) {
			for (ColumnDefinition column : tableDefinition.getColumns()) {
				String id = (tableName + "_" + column.getColumnName())
						.toUpperCase();
				if (columnDefinitionMapper.getColumnDefinitionById(id) == null) {
					column.setId(id);
					column.setTableName(tableName);
					if ("SYS".equals(tableDefinition.getType())) {
						column.setSystemFlag("1");
					}
					columnDefinitionMapper.insertColumnDefinition(column);
				} else {
					columnDefinitionMapper.updateColumnDefinition(column);
				}
			}
		}

		if ("SYS".equals(tableDefinition.getType())) {
			String id = (tableDefinition.getTableName() + "_ID").toUpperCase();
			if (columnDefinitionMapper.getColumnDefinitionById(id) == null) {
				ColumnDefinition column = new ColumnDefinition();
				column.setColumnName("ID");
				column.setId(id);
				column.setName("id");
				column.setTitle("ID");
				column.setPrimaryKey(true);
				column.setJavaType("String");
				column.setLength(50);
				column.setSystemFlag("1");
				column.setTableName(tableName);
				columnDefinitionMapper.insertColumnDefinition(column);
			}
		}
	}

	@Transactional
	public void saveColumn(String tableName, ColumnDefinition columnDefinition) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = this.getTableDefinition(tableName);
		if (tableDefinition != null) {
			Set<String> cols = new HashSet<String>();
			List<ColumnDefinition> columns = tableDefinition.getColumns();
			boolean exists = false;
			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition column : columns) {
					if (StringUtils.equalsIgnoreCase(column.getColumnName(),
							columnDefinition.getColumnName())) {
						column.setValueExpression(columnDefinition
								.getValueExpression());
						column.setFormula(columnDefinition.getFormula());
						column.setName(columnDefinition.getName());
						column.setTitle(columnDefinition.getTitle());
						column.setEnglishTitle(columnDefinition
								.getEnglishTitle());
						column.setHeight(columnDefinition.getHeight());
						column.setLength(columnDefinition.getLength());
						column.setTranslator(columnDefinition.getTranslator());
						column.setPrecision(columnDefinition.getPrecision());
						column.setScale(columnDefinition.getScale());
						column.setAlign(columnDefinition.getAlign());
						column.setCollection(columnDefinition.isCollection());
						column.setFrozen(columnDefinition.isFrozen());
						column.setNullable(columnDefinition.isNullable());
						column.setSortable(columnDefinition.isSortable());
						column.setDisplayType(columnDefinition.getDisplayType());
						column.setDiscriminator(columnDefinition
								.getDiscriminator());
						column.setFormatter(columnDefinition.getFormatter());
						column.setLink(columnDefinition.getLink());
						column.setOrdinal(columnDefinition.getOrdinal());
						column.setRegex(columnDefinition.getRegex());
						column.setSortType(columnDefinition.getSortType());
						column.setSummaryExpr(columnDefinition.getSummaryExpr());
						column.setSummaryType(columnDefinition.getSummaryType());

						columnDefinitionMapper.updateColumnDefinition(column);
						exists = true;
						break;
					}
				}
			}
			if (!exists) {
				String id = (tableName + "_" + columnDefinition.getColumnName())
						.toUpperCase();
				if (!cols.contains(id)) {
					columnDefinition.setId(id);
					columnDefinition.setTableName(tableName);
					columnDefinitionMapper
							.insertColumnDefinition(columnDefinition);
					cols.add(id);
				}
			}
		}
	}

	@Transactional
	public void saveColumns(String targetId,
			List<ColumnDefinition> columnDefinitions) {
		columnDefinitionMapper.deleteColumnDefinitionByTargetId(targetId);
		if (columnDefinitions != null && !columnDefinitions.isEmpty()) {
			for (ColumnDefinition col : columnDefinitions) {
				if (StringUtils.isEmpty(col.getId())) {
					col.setId(col.getTargetId() + "_" + col.getColumnName());
				}
				col.setTargetId(targetId);
				columnDefinitionMapper.insertColumnDefinition(col);
			}
		}
	}

	@Transactional
	public void saveSystemTable(String tableName, List<ColumnDefinition> rows) {
		tableName = tableName.toUpperCase();
		TableDefinition table = this.getTableDefinition(tableName);
		if (table == null) {
			table = new TableDefinition();
			table.setType("SYS");
			table.setSystemFlag("1");
			table.setTableName(tableName.toUpperCase());
			table.setColumnQty(rows.size());
			table.setCreateBy("system");
			table.setCreateTime(new Date());
			table.setRevision(1);
			table.setDeleteFlag(0);
			table.setLocked(0);
			table.setEnglishTitle(tableName);
			tableDefinitionMapper.insertTableDefinition(table);
		} else {
			table.setColumnQty(rows.size());
			tableDefinitionMapper.updateTableDefinition(table);
		}
		columnDefinitionMapper.deleteColumnDefinitionByTableName(tableName);
		Set<String> cols = new HashSet<String>();
		for (ColumnDefinition column : rows) {
			String id = (tableName + "_" + column.getColumnName())
					.toUpperCase();
			if (!cols.contains(id)) {
				column.setId(id);
				column.setSystemFlag("1");
				column.setTableName(tableName);
				columnDefinitionMapper.insertColumnDefinition(column);
				cols.add(id);
			}
		}
	}

	@javax.annotation.Resource
	public void setColumnDefinitionMapper(
			ColumnDefinitionMapper columnDefinitionMapper) {
		this.columnDefinitionMapper = columnDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setQueryDefinitionMapper(
			QueryDefinitionMapper queryDefinitionMapper) {
		this.queryDefinitionMapper = queryDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setTableDefinitionMapper(
			TableDefinitionMapper tableDefinitionMapper) {
		this.tableDefinitionMapper = tableDefinitionMapper;
	}

}