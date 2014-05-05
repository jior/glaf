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

package com.glaf.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.domain.*;
import com.glaf.core.query.*;

@Transactional(readOnly = true)
public interface IQueryDefinitionService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	QueryDefinition getQueryDefinition(String queryId);

	/**
	 * 根据名称获取查询定义
	 * 
	 * @param serviceKey
	 * @return
	 */
	QueryDefinition getQueryDefinitionByName(String serviceKey);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	QueryDefinition getQueryDefinitionWithColumns(String queryId);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	QueryDefinition getQueryDefinitionWithParameters(String queryId);

	/**
	 * 获取某个目标表的全部查询
	 * 
	 * @param targetTableName
	 * @return
	 */
	List<QueryDefinition> getQueryDefinitionByTableName(String targetTableName);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getQueryDefinitionCountByQueryCriteria(QueryDefinitionQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<QueryDefinition> getQueryDefinitionsByQueryCriteria(int start,
			int pageSize, QueryDefinitionQuery query);

	/**
	 * 获取某个查询的全部祖先查询,查询子节点先入栈，查询父节点后入栈
	 * 
	 * @param queryId
	 * @return
	 */
	Stack<QueryDefinition> getQueryDefinitionStack(String queryId);

	/**
	 * 获取某个查询的全部祖先查询
	 * 
	 * @param queryId
	 * @return
	 */
	QueryDefinition getQueryDefinitionWithAncestors(String queryId);

	/**
	 * 是否有子节点
	 * 
	 * @param queryId
	 * @return
	 */
	boolean hasChildren(String queryId);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<QueryDefinition> list(QueryDefinitionQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(QueryDefinition queryDefinition);

}