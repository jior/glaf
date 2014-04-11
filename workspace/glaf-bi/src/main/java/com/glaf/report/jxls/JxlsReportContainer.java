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

package com.glaf.report.jxls;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.sf.jxls.report.ReportManager;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;

import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ReflectUtils;
import com.glaf.report.def.ReportDataSet;
import com.glaf.report.def.ReportDefinition;
import com.glaf.report.def.ReportRowSet;
import com.glaf.report.runtime.ReportBean;
import com.glaf.report.runtime.ReportContainer;

public class JxlsReportContainer {
	protected final static Log logger = LogFactory
			.getLog(JxlsReportContainer.class);

	private static JxlsReportContainer container = new JxlsReportContainer();

	public final static JxlsReportContainer getContainer() {
		return container;
	}

	private JxlsReportContainer() {

	}

	public Workbook execute(String reportId, String actorId,
			Map<String, Object> context) {
		if (context == null) {
			context = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		}

		Workbook workbook = null;
		Connection connection = null;
		InputStream inputStream = null;

		context.put("actorId", actorId);
		LoginContext loginContext = null;
		try {
			loginContext = IdentityFactory.getLoginContext(actorId);
		} catch (Exception ex) {
			logger.error(ex);
		}
		if (loginContext != null) {
			context.put("loginContext", loginContext);
			context.put("currUser", loginContext.getUser());
			context.put("roleIds", loginContext.getRoleIds());
			context.put("perms", loginContext.getPermissions());
		}
		context.put("currDate", new java.util.Date());
		context.put("calendar", java.util.Calendar.getInstance());
		context.put("time", java.util.Calendar.getInstance().getTime());

		ReportDefinition reportDefinition = ReportContainer.getContainer()
				.getReportDefinition(reportId);
		if (reportDefinition != null && reportDefinition.getData() != null) {
			ReportBean reportBean = new ReportBean();
			logger.debug("reportId:" + reportId);
			Map<String, Object> dataMap = reportBean.populate(reportId,
					actorId, context);
			if (dataMap != null) {
				context.putAll(dataMap);
				logger.debug("---------------dataMap------------");
				logger.debug("dataMap:" + dataMap);
			}
			try {
				connection = DBConnectionFactory.getConnection();
				List<ReportDataSet> dataSetList = reportDefinition
						.getDataSetList();
				if (dataSetList != null && !dataSetList.isEmpty()) {
					for (ReportDataSet rds : dataSetList) {
						List<ReportRowSet> rowSetList = rds.getRowSetList();
						if (rowSetList != null && !rowSetList.isEmpty()) {
							for (ReportRowSet rs : rowSetList) {
								String rptMgr = rs.getRptMgr();
								String rptMgrMapping = rs.getRptMgrMapping();
								ReportManager rm = null;
								if ("sql".equals(rptMgr)) {
									rm = new ReportManagerImpl(connection,
											context, rs.getProperties());
								} else {
									String rptMgrClassName = rs
											.getRptMgrClassName();
									if (StringUtils.isNotEmpty(rptMgrClassName)) {
										rm = (ReportManager) ClassUtils
												.instantiateObject(rptMgrClassName);
										try {
											ReflectUtils.setFieldValue(rm,
													"connection", connection);
										} catch (Exception ex) {
										}
										try {
											ReflectUtils.setFieldValue(rm,
													"properties",
													rs.getProperties());
										} catch (Exception ex) {
										}
									}
								}
								if (rm != null) {
									context.put(rptMgrMapping, rm);
								}
							}
						}
					}
				}

				logger.debug("context:" + context);
				XLSTransformer transformer = new XLSTransformer();
				inputStream = new BufferedInputStream(new ByteArrayInputStream(
						reportDefinition.getData()));
				workbook = transformer.transformXLS(inputStream, context);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				try {
					JdbcUtils.close(connection);
				} catch (Exception e) {
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
					}
				}
			}

		}

		return workbook;
	}

	public byte[] toXls(String reportId, String actorId,
			Map<String, Object> context) {
		Workbook wb = JxlsReportContainer.getContainer().execute(reportId,
				actorId, context);
		if (wb != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			try {
				wb.write(bos);
				bos.flush();
				return baos.toByteArray();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				wb = null;
				try {
					baos.close();
				} catch (IOException e) {
				}
				try {
					bos.close();
				} catch (IOException e) {
				}
				baos = null;
				bos = null;
			}
		}
		return null;
	}

}