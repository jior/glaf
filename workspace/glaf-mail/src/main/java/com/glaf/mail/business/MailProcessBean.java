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

package com.glaf.mail.business;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.service.ISystemPropertyService;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.template.util.TemplateUtils;
import com.glaf.mail.IMailDataManager;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailSender;
import com.glaf.mail.MyBatisMailDataManager;
import com.glaf.mail.def.MailDataSet;
import com.glaf.mail.def.MailRowSet;
import com.glaf.mail.def.MailTemplate;

public class MailProcessBean {

	protected final static Log logger = LogFactory
			.getLog(MailProcessBean.class);

	public MailProcessBean() {

	}

	public void sendMail(String mailDefId, String actorId,
			Map<String, Object> context, List<String> receiveList) {
		Map<String, Object> newContext = populate(mailDefId, actorId, context);
		MailTemplate mailDefinition = MailContainer.getContainer()
				.getMailDefinition(mailDefId);
		User user = IdentityFactory.getUser(actorId);
		newContext.put("sender", user);

		ISystemPropertyService systemPropertyService = ContextFactory
				.getBean("systemPropertyService");
		SystemProperty property = systemPropertyService.getSystemProperty(
				"SYS", "serviceUrl");

		if (property != null) {
			newContext.put("property", property);
			newContext.put("serviceUrl", property.getValue());
		}

		List<SystemProperty> props = systemPropertyService
				.getSystemProperties("SYS");
		if (props != null && !props.isEmpty()) {
			for (SystemProperty p : props) {
				if (!newContext.containsKey(p.getName())) {
					newContext.put(p.getName(), p.getValue());
				}
			}
		}

		newContext.put("now", DateUtils.getDate(new Date()));
		newContext.put("current_date", DateUtils.getDate(new Date()));
		String subject = mailDefinition.getTitle();
		String content = mailDefinition.getContent();
		String title = TemplateUtils.process(context, subject);
		String text = TemplateUtils.process(context, content);

		MailMessage mailMessage = new MailMessage();

		mailMessage.setTo(user.getMail());
		mailMessage.setSubject(title);
		mailMessage.setDataMap(context);
		mailMessage.setContent(text);
		mailMessage.setSupportExpression(false);

		mailMessage.setSaveMessage(false);
		MailSender mailSender = ContextFactory.getBean("mailSender");
		try {
			mailSender.send(mailMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

	}

	public Map<String, Object> populate(String mailDefId, String actorId,
			Map<String, Object> context) {
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		dataMap.putAll(context);
		MailTemplate mail = MailContainer.getContainer().getMailDefinition(
				mailDefId);
		logger.debug(mail.getMailDefId() + " -> " + mail.getTemplatePath());
		List<MailDataSet> dataSetList = mail.getDataSetList();
		if (dataSetList != null && !dataSetList.isEmpty()) {
			logger.debug("dataSetList size:" + dataSetList.size());
			for (MailDataSet mailDS : dataSetList) {
				try {
					List<MailRowSet> rowSetList = mailDS.getRowSetList();
					logger.debug("rowSetList:" + rowSetList);
					if (rowSetList != null && !rowSetList.isEmpty()) {
						for (MailRowSet rs : rowSetList) {
							String dataMgr = rs.getDataMgr();
							String query = rs.getQuery();
							String mapping = rs.getMapping();
							String mailMgrMapping = rs.getMailMgrMapping();
							IMailDataManager dataManager = null;
							List<?> rows = null;
							if ("mybatis".equals(dataMgr)) {
								dataManager = new MyBatisMailDataManager();
							} else {
								String dataMgrClassName = rs
										.getDataMgrClassName();
								if (StringUtils.isNotEmpty(dataMgrClassName)) {
									dataManager = (IMailDataManager) ClassUtils
											.instantiateObject(dataMgrClassName);
								} else {
									String dataMgrBeanId = rs
											.getDataMgrBeanId();
									if (StringUtils.isNotEmpty(dataMgrBeanId)) {
										Object bean = ContextFactory
												.getBean(dataMgrBeanId);
										if (bean instanceof IMailDataManager) {
											dataManager = (IMailDataManager) bean;
										}
									}
								}
							}
							if (dataManager != null) {
								if (mailMgrMapping != null) {
									dataMap.put(mailMgrMapping, dataManager);
								} else {
									String clazz = dataManager.getClass()
											.getSimpleName();
									clazz = clazz.substring(0, 1).toLowerCase()
											+ clazz.substring(1);
									dataMap.put(clazz, dataManager);
								}
								rows = dataManager
										.getResultList(query, context);
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
					logger.error(ex);
					throw new RuntimeException(ex);
				}
			}
		}

		return dataMap;
	}
}