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

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;

import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.mapper.*;
import com.glaf.core.service.IQueryDefinitionService;

@Service("queryDefinitionService")
@Transactional(readOnly = true)
public class MxQueryDefinitionServiceImpl implements IQueryDefinitionService {
	protected final static Log logger = LogFactory
			.getLog(MxQueryDefinitionServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected QueryDefinitionMapper queryDefinitionMapper;

	protected ColumnDefinitionMapper columnDefinitionMapper;

	public MxQueryDefinitionServiceImpl() {

	}

	public int count(QueryDefinitionQuery query) {
		query.ensureInitialized();
		return queryDefinitionMapper.getQueryDefinitionCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			if (this.hasChildren(id)) {
				throw new RuntimeException("children is not null");
			}
			queryDefinitionMapper.deleteQueryDefinitionById(id);
		}
	}

	public QueryDefinition getQueryDefinition(String queryId) {
		if (queryId == null) {
			return null;
		}
		QueryDefinition queryDefinition = queryDefinitionMapper
				.getQueryDefinitionById(queryId);
		if (queryDefinition != null) {

		}
		return queryDefinition;
	}

	public QueryDefinition getQueryDefinitionByName(String serviceKey) {
		if (serviceKey == null) {
			return null;
		}
		QueryDefinition queryDefinition = null;
		QueryDefinitionQuery query = new QueryDefinitionQuery();
		query.serviceKey(serviceKey);

		List<QueryDefinition> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			queryDefinition = list.get(0);
		}

		if (queryDefinition != null) {

		}
		return queryDefinition;
	}

	/**
	 * ��ȡĳ��Ŀ����ȫ����ѯ
	 * 
	 * @param targetTableName
	 * @return
	 */
	public List<QueryDefinition> getQueryDefinitionByTableName(
			String targetTableName) {
		return queryDefinitionMapper
				.getQueryDefinitionByTableName(targetTableName);
	}

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	public int getQueryDefinitionCountByQueryCriteria(QueryDefinitionQuery query) {
		return queryDefinitionMapper.getQueryDefinitionCount(query);
	}

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	public List<QueryDefinition> getQueryDefinitionsByQueryCriteria(int start,
			int pageSize, QueryDefinitionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<QueryDefinition> rows = sqlSession.selectList(
				"getQueryDefinitions", query, rowBounds);
		return rows;
	}

	/**
	 * ��ȡĳ����ѯ��ȫ�����Ȳ�ѯ,��ѯ�ӽڵ�����ջ����ѯ���ڵ����ջ
	 * 
	 * @param queryId
	 * @return
	 */
	public Stack<QueryDefinition> getQueryDefinitionStack(String queryId) {
		Stack<QueryDefinition> stack = new Stack<QueryDefinition>();
		QueryDefinition queryDefinition = this.getQueryDefinition(queryId);
		if (queryDefinition != null) {
			stack.push(queryDefinition);
			this.loadQueryDefinitionStack(stack, queryDefinition);
		}
		return stack;
	}

	public QueryDefinition getQueryDefinitionWithAncestors(String queryId) {
		QueryDefinition queryDefinition = this.getQueryDefinition(queryId);
		if (queryDefinition != null) {
			loadQueryDefinitionWithParent(queryDefinition);
		}
		return queryDefinition;
	}

	public QueryDefinition getQueryDefinitionWithColumns(String queryId) {
		if (queryId == null) {
			return null;
		}
		QueryDefinition queryDefinition = queryDefinitionMapper
				.getQueryDefinitionById(queryId);
		if (queryDefinition != null) {
			ColumnDefinitionQuery q = new ColumnDefinitionQuery();
			q.queryId(queryId);
			q.discriminator("C");
			List<ColumnDefinition> columns = columnDefinitionMapper
					.getColumnDefinitions(q);
			queryDefinition.setColumns(columns);
		}
		return queryDefinition;
	}

	public QueryDefinition getQueryDefinitionWithParameters(String queryId) {
		if (queryId == null) {
			return null;
		}
		QueryDefinition queryDefinition = queryDefinitionMapper
				.getQueryDefinitionById(queryId);
		if (queryDefinition != null) {
			ColumnDefinitionQuery q = new ColumnDefinitionQuery();
			q.queryId(queryId);
			q.discriminator("P");
			List<ColumnDefinition> columns = columnDefinitionMapper
					.getColumnDefinitions(q);
			queryDefinition.setColumns(columns);
		}
		return queryDefinition;
	}

	public boolean hasChildren(String queryId) {
		boolean result = false;
		QueryDefinitionQuery query = new QueryDefinitionQuery();
		query.parentId(queryId);
		List<QueryDefinition> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			result = true;
		}
		return result;
	}

	public List<QueryDefinition> list(QueryDefinitionQuery query) {
		List<QueryDefinition> list = queryDefinitionMapper
				.getQueryDefinitions(query);
		return list;
	}

	private void loadQueryDefinitionStack(Stack<QueryDefinition> stack,
			QueryDefinition query) {
		if (query != null && query.getParentId() != null) {
			QueryDefinition parent = this.getQueryDefinition(query
					.getParentId());
			if (parent != null) {
				query.setParent(parent);
				parent.setChild(query);
				stack.push(parent);
				this.loadQueryDefinitionStack(stack, parent);
			}
		}
	}

	private void loadQueryDefinitionWithParent(QueryDefinition query) {
		if (query != null && query.getParentId() != null) {
			QueryDefinition parent = this.getQueryDefinition(query
					.getParentId());
			if (parent != null) {
				query.setParent(parent);
				parent.setChild(query);
				this.loadQueryDefinitionWithParent(parent);
			}
		}
	}

	@Transactional
	public void save(QueryDefinition queryDefinition) {
		QueryDefinition query = this
				.getQueryDefinition(queryDefinition.getId());
		if (query == null) {
			if (StringUtils.isEmpty(queryDefinition.getId())) {
				queryDefinition.setId(idGenerator.getNextId());
			}
			queryDefinition.setRevision(1);
			queryDefinition.setCreateTime(new Date());
			queryDefinitionMapper.insertQueryDefinition(queryDefinition);
		} else {
			queryDefinition.setRevision(queryDefinition.getRevision() + 1);
			queryDefinitionMapper.updateQueryDefinition(queryDefinition);
		}

		if (queryDefinition.getColumns() != null
				&& !queryDefinition.getColumns().isEmpty()) {

			List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
			List<ColumnDefinition> parameters = new ArrayList<ColumnDefinition>();

			for (ColumnDefinition column : queryDefinition.getColumns()) {
				if ("C".equals(column.getDiscriminator())) {
					columns.add(column);
				} else if ("P".equals(column.getDiscriminator())) {
					parameters.add(column);
				}
			}

			if (query != null) {
				if (!columns.isEmpty()) {
					columnDefinitionMapper
							.deleteColumnDefinitionByTargetId(query.getId()
									+ "_C");
				}
				if (!parameters.isEmpty()) {
					columnDefinitionMapper
							.deleteColumnDefinitionByTargetId(query.getId()
									+ "_P");
				}
			}
			int index = 0;
			for (ColumnDefinition column : columns) {
				index++;
				column.setId(queryDefinition.getId() + "_C_" + index);
				column.setQueryId(queryDefinition.getId());
				column.setTargetId(queryDefinition.getId() + "_C");
				columnDefinitionMapper.insertColumnDefinition(column);
			}

			index = 0;
			for (ColumnDefinition column : parameters) {
				index++;
				column.setId(queryDefinition.getId() + "_P_" + index);
				column.setQueryId(queryDefinition.getId());
				column.setTargetId(queryDefinition.getId() + "_P");
				columnDefinitionMapper.insertColumnDefinition(column);
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

}