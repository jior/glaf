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

package com.glaf.base.modules.sys.springmvc;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.SystemParam;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ISystemParamService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.service.SysDataService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailSender;

@Controller("/sys/sendMail")
public class SendBaseDataController {

	protected SysDataService sysDataService;

	protected ITableDataService tableDataService;

	protected ITableDefinitionService tableDefinitionService;

	protected ITablePageService tablePageService;

	protected ISystemParamService systemParamService;

	@javax.annotation.Resource
	public void setSysDataService(SysDataService sysDataService) {
		this.sysDataService = sysDataService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@javax.annotation.Resource
	public void setSystemParamService(ISystemParamService systemParamService) {
		this.systemParamService = systemParamService;
	}

	@RequestMapping
	public void sendMail(HttpServletRequest request) {
		SysUser login = RequestUtil.getLoginUser(request);
		if (login.isSystemAdmin()) {
			StringBuffer sb = new StringBuffer();
			JSONArray result = null;
			SystemParam param = systemParamService.getSystemParam("sys_table");
			if (param != null && StringUtils.isNotEmpty(param.getTextVal())) {
				result = JSON.parseArray(param.getTextVal());
			}
			String dbType = request.getParameter("dbType");
			if (StringUtils.isNotEmpty(dbType) && result != null) {
				for (int index = 0, len = result.size(); index < len; index++) {
					JSONObject json = result.getJSONObject(index);
					String tablename = json.getString("tablename");
					if (StringUtils.isNotEmpty(tablename)) {
						List<ColumnDefinition> columns = DBUtils
								.getColumnDefinitions(tablename);
						TablePageQuery query = new TablePageQuery();
						query.tableName(tablename);
						query.firstResult(0);
						query.maxResults(5000);
						int count = tablePageService.getTableCount(query);
						if (count <= 5000) {
							List<Map<String, Object>> rows = tablePageService
									.getTableData(query);
							if (rows != null && !rows.isEmpty()) {
								for (Map<String, Object> dataMap : rows) {
									Map<String, Object> lowerMap = QueryUtils
											.lowerKeyMap(dataMap);
									sb.append(" insert into ")
											.append(tablename).append(" (");
									for (int i = 0, len2 = columns.size(); i < len2; i++) {
										ColumnDefinition column = columns
												.get(i);
										sb.append(column.getColumnName()
												.toLowerCase());
										if (i < columns.size() - 1) {
											sb.append(", ");
										}
									}
									sb.append(" ) values (");
									for (int i = 0, len2 = columns.size(); i < len2; i++) {
										ColumnDefinition column = columns
												.get(i);
										Object value = lowerMap.get(column
												.getColumnName().toLowerCase());
										if (value != null) {
											if (value instanceof Short) {
												sb.append(value);
											} else if (value instanceof Integer) {
												sb.append(value);
											} else if (value instanceof Long) {
												sb.append(value);
											} else if (value instanceof Double) {
												sb.append(value);
											} else if (value instanceof String) {
												String str = (String) value;
												str = StringTools.replace(str,
														"'", "''");
												sb.append("'").append(str)
														.append("'");
											} else if (value instanceof Date) {
												Date date = (Date) value;
												if (StringUtils
														.equalsIgnoreCase(
																dbType,
																"oracle")) {
													sb.append(" to_date('")
															.append(DateUtils
																	.getDateTime(date))
															.append("', 'yyyy-mm-dd hh24:mi:ss')");
												} else if (StringUtils
														.equalsIgnoreCase(
																dbType, "db2")) {
													sb.append(" TO_DATE('")
															.append(DateUtils
																	.getDateTime(date))
															.append("', ''YYY-MM-DD HH24:MI:SS')");
												} else {
													sb.append("'")
															.append(DateUtils
																	.getDateTime(date))
															.append("'");
												}
											} else {
												String str = value.toString();
												str = StringTools.replace(str,
														"'", "''");
												sb.append("'").append(str)
														.append("'");
											}
										} else {
											sb.append("null");
										}
										if (i < columns.size() - 1) {
											sb.append(", ");
										}
									}
									sb.append(");");
									sb.append(FileUtils.newline);
								}
							}
						}
						sb.append(FileUtils.newline);
						sb.append(FileUtils.newline);
					}
				}
			}
			if (sb.length() > 1000) {
				String mailTo = request.getParameter("mailTo");
				if (StringUtils.isNotEmpty(mailTo)) {
					MailMessage mailMessage = new MailMessage();
					mailMessage.setTo("mailTo");
					mailMessage.setSubject("sys_data");
					mailMessage.setContent(sb.toString());
					mailMessage.setSupportExpression(false);
					mailMessage.setSaveMessage(false);
					MailSender mailSender = ContextFactory
							.getBean("mailSender");
					try {
						mailSender.send(mailMessage);
					} catch (Exception ex) {
					}
				}
			}
		}
	}

}
