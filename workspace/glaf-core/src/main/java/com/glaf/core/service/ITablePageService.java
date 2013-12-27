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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.TablePage;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.query.QueryCondition;
import com.glaf.core.util.Paging;

@Transactional(readOnly = true)
public interface ITablePageService {

	List<Map<String, Object>> getListData(String sql, Map<String, Object> params);

	int getQueryCount(String querySql, List<QueryCondition> conditions);

	List<Object> getQueryList(String querySql, int begin, int pageSize,
			List<QueryCondition> conditions);

	int getTableCount(String tableName, String idColumn,
			List<QueryCondition> conditions);

	int getTableCount(TablePageQuery query);

	List<Map<String, Object>> getTableData(String tableName, int firstResult,
			int maxResults);

	List<Map<String, Object>> getTableData(TablePageQuery query);

	List<Object> getTableList(String tableName, String idColumn,
			int firstResult, int maxResults, List<QueryCondition> conditions);

	List<Object> getTableList(String tableName, String idColumn,
			Map<String, String> selectColumns, int firstResult, int maxResults,
			List<QueryCondition> conditions);

	List<Object> getTableList(String tableName, String idColumn,
			Map<String, String> selectColumns, int firstResult, int maxResults,
			List<QueryCondition> conditions,
			LinkedHashMap<String, Boolean> orderByColumns);

	TablePage getTablePage(TablePageQuery tablePageQuery, int firstResult,
			int maxResults);

	Paging getTablePaging(TablePageQuery tablePageQuery, int firstResult,
			int maxResults);

}