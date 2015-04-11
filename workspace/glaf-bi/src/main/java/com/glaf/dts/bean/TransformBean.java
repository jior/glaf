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

package com.glaf.dts.bean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.db.TransformTable;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.query.QueryDefinitionQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.dts.transform.MxTransformManager;

public class TransformBean {
	protected static final Log logger = LogFactory.getLog(TransformBean.class);

	protected IDatabaseService databaseService;

	protected ITableDefinitionService tableDefinitionService;

	protected IQueryDefinitionService queryDefinitionService;

	public TransformBean() {

	}

	public IDatabaseService getDatabaseService() {
		if (databaseService == null) {
			databaseService = ContextFactory.getBean("databaseService");
		}
		return databaseService;
	}

	public IQueryDefinitionService getQueryDefinitionService() {
		if (queryDefinitionService == null) {
			queryDefinitionService = ContextFactory
					.getBean("queryDefinitionService");
		}
		return queryDefinitionService;
	}

	public ITableDefinitionService getTableDefinitionService() {
		if (tableDefinitionService == null) {
			tableDefinitionService = ContextFactory
					.getBean("tableDefinitionService");
		}
		return tableDefinitionService;
	}

	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void setQueryDefinitionService(
			IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	public boolean transformAllQueryToTable() {
		boolean result = true;
		QueryDefinitionQuery q = new QueryDefinitionQuery();
		q.locked(0);
		List<QueryDefinition> queries = getQueryDefinitionService().list(q);
		if (queries != null && !queries.isEmpty()) {
			for (QueryDefinition queryDefinition : queries) {
				String tableName = queryDefinition.getTargetTableName();
				if (StringUtils.isNotEmpty(tableName)) {
					tableName = tableName.toLowerCase();
					if (StringUtils.startsWith(tableName, "mx_")
							|| StringUtils.startsWith(tableName, "sys_")
							|| StringUtils.startsWith(tableName, "act_")
							|| StringUtils.startsWith(tableName, "jbpm_")) {
						continue;
					}
					MxTransformManager manager = new MxTransformManager();
					TableDefinition tableDefinition = null;
					if (!StringUtils.equalsIgnoreCase(
							queryDefinition.getRotatingFlag(), "R2C")) {
						try {
							tableDefinition = manager
									.toTableDefinition(queryDefinition);
							tableDefinition.setTableName(tableName);
							tableDefinition.setType("DTS");
							tableDefinition.setNodeId(queryDefinition
									.getNodeId());
							TransformTable tbl = new TransformTable();
							tbl.createOrAlterTable(tableDefinition);
						} catch (Exception ex) {
							ex.printStackTrace();
							logger.error(ex);
						}
					}

					Long databaseId = queryDefinition.getDatabaseId();
					TransformTable transformTable = new TransformTable();
					try {
						Database db = getDatabaseService().getDatabaseById(
								databaseId);
						if (db != null) {
							transformTable.transformQueryToTable(tableName,
									queryDefinition.getId(), db.getName());
						} else {
							transformTable.transformQueryToTable(tableName,
									queryDefinition.getId(),
									Environment.DEFAULT_SYSTEM_NAME);
						}
					} catch (Exception ex) {
						result = false;
						ex.printStackTrace();
						logger.error(ex);
					}
				}
			}
		}
		return result;
	}

	public boolean transformQueryToTable(String queryId) {
		logger.debug("----------------------transformQueryToTable---------");
		boolean result = true;
		QueryDefinition queryDefinition = getQueryDefinitionService()
				.getQueryDefinition(queryId);
		String tableName = queryDefinition.getTargetTableName();
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = tableName.toLowerCase();
			if (StringUtils.startsWith(tableName, "mx_")
					|| StringUtils.startsWith(tableName, "sys_")
					|| StringUtils.startsWith(tableName, "act_")
					|| StringUtils.startsWith(tableName, "jbpm_")) {
				return false;
			}
			MxTransformManager manager = new MxTransformManager();
			TableDefinition tableDefinition = null;
			if (!StringUtils.equalsIgnoreCase(
					queryDefinition.getRotatingFlag(), "R2C")) {
				try {
					tableDefinition = manager
							.toTableDefinition(queryDefinition);
					tableDefinition.setTableName(tableName);
					tableDefinition.setType("DTS");
					tableDefinition.setNodeId(queryDefinition.getNodeId());
					TransformTable tbl = new TransformTable();
					tbl.createOrAlterTable(tableDefinition);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			}

			Long databaseId = queryDefinition.getDatabaseId();
			TransformTable transformTable = new TransformTable();
			try {
				Database db = getDatabaseService().getDatabaseById(databaseId);
				if (db != null) {
					transformTable.transformQueryToTable(tableName,
							queryDefinition.getId(), db.getName());
				} else {
					transformTable.transformQueryToTable(tableName,
							queryDefinition.getId(),
							Environment.DEFAULT_SYSTEM_NAME);
				}
			} catch (Exception ex) {
				result = false;
				ex.printStackTrace();
				logger.error(ex);
			}
		}

		return result;
	}

}
