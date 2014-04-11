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

package com.glaf.report.runtime;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.report.data.DataManager;
import com.glaf.report.data.MyBatisDataServiceImpl;
import com.glaf.report.def.ReportDataSet;
import com.glaf.report.def.ReportDefinition;
import com.glaf.report.def.ReportRowSet;

public class ReportBean {

	protected final static Log logger = LogFactory.getLog(ReportBean.class);

	public ReportBean() {

	}

	public synchronized Map<String, Object> populate(String reportId,
			String actorId, Map<String, Object> context) {
		Map<String, Object> dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		ReportDefinition reportDefinition = ReportContainer.getContainer()
				.getReportDefinition(reportId);
		logger.debug(reportDefinition.getReportId() + " -> "
				+ reportDefinition.getTemplateFile());
		List<ReportDataSet> dataSetList = reportDefinition.getDataSetList();
		if (dataSetList != null && !dataSetList.isEmpty()) {
			logger.debug("dataSetList size:" + dataSetList.size());
			for (ReportDataSet rds : dataSetList) {
				Connection connection = null;
				try {
					connection = DBConnectionFactory.getConnection();
					logger.debug("get default connection.");

					List<ReportRowSet> rowSetList = rds.getRowSetList();
					logger.debug("rowSetList:" + rowSetList);
					if (rowSetList != null && !rowSetList.isEmpty()) {
						for (ReportRowSet rs : rowSetList) {
							String dataMgr = rs.getDataMgr();
							String query = rs.getQuery();
							String mapping = rs.getMapping();
							String rptMgrMapping = rs.getRptMgrMapping();
							DataManager dataManager = null;
							List<?> rows = null;
							if ("mybatis3x".equals(dataMgr)) {
								dataManager = new MyBatisDataServiceImpl();
							} else {
								String dataMgrClassName = rs
										.getDataMgrClassName();
								if (StringUtils.isNotEmpty(dataMgrClassName)) {
									dataManager = (DataManager) ClassUtils
											.instantiateObject(dataMgrClassName);

								}
								String dataMgrBeanId = rs.getDataMgrBeanId();
								if (StringUtils.isNotEmpty(dataMgrBeanId)) {
									Object bean = ContextFactory
											.getBean(dataMgrBeanId);
									if (bean instanceof DataManager) {
										dataManager = (DataManager) bean;
									}
								}
							}
							if (dataManager != null) {
								if (rptMgrMapping != null) {
									dataMap.put(rptMgrMapping, dataManager);
								} else {
									String clazz = dataManager.getClass()
											.getSimpleName();
									clazz = clazz.substring(0, 1).toLowerCase()
											+ clazz.substring(1);
									dataMap.put(clazz, dataManager);
								}
								rows = dataManager.getResultList(connection,
										query, context);
								logger.debug(rows);
								boolean singleResult = rs.isSingleResult();
								if (rows != null && rows.size() > 0) {
									if (singleResult) {
										Object object = rows.get(0);
										dataMap.put(mapping, object);
										dataMap.put("dataModel_" + mapping,
												object);
									} else {
										dataMap.put(mapping, rows);
										dataMap.put("dataModel_" + mapping,
												rows);
									}
								}
							}
						}
					}
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				} finally {
					JdbcUtils.close(connection);
				}
			}
		}

		return dataMap;
	}
}