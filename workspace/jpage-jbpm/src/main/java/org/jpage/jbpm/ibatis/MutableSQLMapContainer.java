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


package org.jpage.jbpm.ibatis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.persistence.SQLParameter;
import org.jpage.util.DateTools;
import org.jpage.util.FieldType;
import org.jpage.util.Tools;

public class MutableSQLMapContainer {
	private static final Log logger = LogFactory
			.getLog(MutableSQLMapContainer.class);

	private static MutableSQLMapContainer container;

	private MutableSQLMapContainer() {
		logger.debug("load sqlmap config file......");
	}

	public final static MutableSQLMapContainer getContainer() {
		if (container == null) {
			container = new MutableSQLMapContainer();
		}
		return container;
	}

	public Object queryForObject(JbpmContext jbpmContext, String statementId,
			Object parameterObject) {
		JbpmSqlMapDAO dao = (JbpmSqlMapDAO) JbpmContextFactory
				.getBean("sqlmapClientDAO");
		dao.setConnection(jbpmContext.getConnection());
		logger.debug("execute sqlmap:" + statementId);
		logger.debug("parameterObject:" + parameterObject);
		return dao.queryForObject(statementId, parameterObject);
	}

	public java.util.List query(JbpmContext jbpmContext, String statementId,
			Object parameterObject) {
		JbpmSqlMapDAO dao = (JbpmSqlMapDAO) JbpmContextFactory
				.getBean("sqlmapClientDAO");
		dao.setConnection(jbpmContext.getConnection());
		logger.debug("execute sqlmap:" + statementId);
		logger.debug("parameterObject:" + parameterObject);
		return dao.query(statementId, parameterObject);
	}

	public java.util.List getList(JbpmContext jbpmContext, String statementId,
			Object parameterObject) {
		JbpmSqlMapDAO dao = (JbpmSqlMapDAO) JbpmContextFactory
				.getBean("sqlmapClientDAO");
		dao.setConnection(jbpmContext.getConnection());
		logger.debug("execute sqlmap:" + statementId);
		logger.debug("parameterObject:" + parameterObject);
		return dao.query(statementId, parameterObject);
	}

	public void execute(JbpmContext jbpmContext, String statementId,
			String operation, Map params) throws Exception {
		JbpmSqlMapDAO dao = (JbpmSqlMapDAO) JbpmContextFactory
				.getBean("sqlmapClientDAO");
		dao.setConnection(jbpmContext.getConnection());
		logger.debug("execute sqlmap:" + statementId);
		logger.debug("params:" + params);
		if (StringUtils.equalsIgnoreCase("insert", operation)) {
			dao.insertObject(statementId, params);
		} else if (StringUtils.equalsIgnoreCase("update", operation)) {
			dao.updateObject(statementId, params);
		} else if (StringUtils.equalsIgnoreCase("delete", operation)) {
			dao.deleteObject(statementId, params);
		}
	}

	public Map populate(List params, Map valueMap) {
		Map dataMap = new HashMap();
		if (params != null && valueMap != null) {
			Iterator iterator = params.iterator();
			while (iterator.hasNext()) {
				SQLParameter param = (SQLParameter) iterator.next();
				String name = param.getName();
				int type = param.getType();
				Object obj = valueMap.get(name);
				if (obj == null) {
					obj = valueMap.get(name.toLowerCase());
				}

				/**
				 * ���ȼ���Ƿ���Ĭ��ֵ�������Ĭ��ֵ��������Ĭ��ֵ�����ȡ����ֵ
				 */
				if (obj == null) {
					String defaultValue = param.getDefaultValue();
					if (StringUtils.isNotBlank(defaultValue)) {
						defaultValue = defaultValue.trim();
						if (defaultValue.startsWith("${")
								&& defaultValue.endsWith("}")) {
							String paramName = Tools.replaceIgnoreCase(
									defaultValue, "${", "");
							paramName = paramName.replaceAll("}", "");
							if (valueMap.get(paramName) != null) {
								obj = valueMap.get(paramName);
							}
						} else {
							obj = defaultValue;
						}
					}
				}

				/**
				 * ������ʽ������б���ʽ����ʹ�ñ���ʽ��������Ա���ʽ���м���
				 */
				if (obj == null) {
					String expression = param.getExpression();
					if (StringUtils.isNotBlank(expression)) {
						if (expression.startsWith("${")
								&& expression.endsWith("}")) {
							obj = DefaultExpressionEvaluator.evaluate(
									expression, valueMap);
						} else if (expression.startsWith("#{")
								&& expression.endsWith("}")) {
							obj = DefaultExpressionEvaluator.evaluate(
									expression, valueMap);
						}
					}
				}

				if (obj == null) {
					/**
					 * ����ò����Ǳ���ģ�������ֵΪ�գ����ܼ������´������׳��쳣��
					 */
					if (param.getRequired() > 0) {
						throw new RuntimeException("parameter \"" + name
								+ "\" value is null");
					}
					continue;
				}

				String value = null;
				java.util.Date date = null;

				if (obj instanceof String) {
					value = (String) obj;
				}

				switch (type) {
				case FieldType.DATE_TYPE:
					if (!(obj instanceof java.util.Date)) {
						if (StringUtils.isNotBlank(value)) {
							date = DateTools.toDate(value);
							obj = date;
						}
					}
					break;
				case FieldType.TIMESTAMP_TYPE:
					if (!(obj instanceof java.sql.Timestamp)) {
						if (obj instanceof java.util.Date) {
							date = (java.util.Date) obj;
						} else {
							if (StringUtils.isNotBlank(value)) {
								date = DateTools.toDate(value);
							}
						}
						obj = DateTools.toTimestamp(date);
					}
					break;
				case FieldType.DOUBLE_TYPE:
					if (!(obj instanceof Double)) {
						if (StringUtils.isNotBlank(value)) {
							Double d = new Double(value);
							obj = d;
						}
					}
					break;
				case FieldType.BOOLEAN_TYPE:
					if (!(obj instanceof Boolean)) {
						if (StringUtils.isNotBlank(value)) {
							Boolean b = new Boolean(value);
							obj = b;
						}
					}
					break;
				case FieldType.SHORT_TYPE:
					if (!(obj instanceof Short)) {
						if (StringUtils.isNotBlank(value)) {
							Short s = new Short(value);
							obj = s;
						}
					}
					break;
				case FieldType.INTEGER_TYPE:
					if (!(obj instanceof Integer)) {
						if (StringUtils.isNotBlank(value)) {
							Integer integer = new Integer(value);
							obj = integer;
						}
					}
					break;
				case FieldType.LONG_TYPE:
					if (!(obj instanceof Long)) {
						if (StringUtils.isNotBlank(value)) {
							Long l = new Long(value);
							obj = l;
						}
					}
					break;
				default:
					break;
				}
				dataMap.put(name, obj);
			}
		}
		return dataMap;
	}

	public int executeBatch(JbpmContext jbpmContext, java.util.List rows) {
		JbpmSqlMapDAO dao = (JbpmSqlMapDAO) JbpmContextFactory
				.getBean("sqlmapClientDAO");
		dao.setConnection(jbpmContext.getConnection());
		return dao.executeBatch(rows);
	}

}