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
package com.glaf.form.core.util;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataModel;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.StringTools;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.graph.def.FormNode;
import com.glaf.form.core.graph.node.CheckboxNode;
import com.glaf.form.core.graph.node.DateFieldNode;
import com.glaf.form.core.graph.node.HiddenNode;
import com.glaf.form.core.graph.node.NumberFieldNode;
import com.glaf.form.core.graph.node.PasswordNode;
import com.glaf.form.core.graph.node.PersistenceNode;
import com.glaf.form.core.graph.node.RadioNode;
import com.glaf.form.core.graph.node.SelectNode;
import com.glaf.form.core.graph.node.TextAreaNode;
import com.glaf.form.core.graph.node.TextFieldNode;
import com.glaf.form.core.graph.node.TimestampFieldNode;

public final class FormTools {
	protected final static Log logger = LogFactory.getLog(FormTools.class);

	public static Map<String, Object> distill(FormDefinition formDefinition,
			DataModel formModel) {
		Map<String, Object> dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		List<FormNode> nodes = formDefinition.getNodes();
		if (nodes != null && nodes.size() > 0) {
			Iterator<FormNode> iterator = nodes.iterator();
			while (iterator.hasNext()) {
				FormNode formNode = iterator.next();
				if (!(formNode instanceof PersistenceNode)) {
					continue;
				}
				PersistenceNode node = (PersistenceNode) formNode;
				if (node.getAccessLevel() == AccessLevelType.WRITE_TYPE
						|| node.getAccessLevel() == AccessLevelType.REQUIRED_TYPE) {
					String name = node.getName();
					String value = node.getStringValue();
					if (value != null) {
						value = value.trim();
					}
					String nodeType = node.getNodeType();
					if (StringUtils.isEmpty(nodeType)) {
						continue;
					}

					if (node instanceof RadioNode
							|| StringUtils
									.equals(nodeType, RadioNode.NODE_TYPE)) {
						dataMap.put(name, value);
						node.setValue(value);
						ColumnModel col = new ColumnModel(node.getColumnName(),
								node.getName(), node.getValue());
						formModel.getFields().put(name, col);
					} else if (node instanceof CheckboxNode
							|| StringUtils.equals(nodeType,
									CheckboxNode.NODE_TYPE)) {
						dataMap.put(name, value);
						node.setValue(value);
						ColumnModel col = new ColumnModel(node.getColumnName(),
								node.getName(), node.getValue());
						formModel.getFields().put(name, col);
					} else if (node instanceof SelectNode
							|| StringUtils.equals(nodeType,
									SelectNode.NODE_TYPE)) {
						dataMap.put(name, value);
						node.setValue(value);
						ColumnModel col = new ColumnModel(node.getColumnName(),
								node.getName(), node.getValue());
						formModel.getFields().put(name, col);
					} else if (node instanceof HiddenNode
							|| StringUtils.equals(nodeType,
									HiddenNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							dataMap.put(name, value);
							node.setValue(value);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof PasswordNode
							|| StringUtils.equals(nodeType,
									PasswordNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							dataMap.put(name, value);
							node.setValue(value);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof TimestampFieldNode
							|| StringUtils.equals(nodeType,
									TimestampFieldNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							Date date = DateUtils.toDate(value);
							dataMap.put(name, date);
							node.setValue(date);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof DateFieldNode
							|| StringUtils.equals(nodeType,
									DateFieldNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							Date date = DateUtils.toDate(value);
							dataMap.put(name, date);
							node.setValue(date);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof NumberFieldNode
							|| StringUtils.equals(nodeType,
									NumberFieldNode.NODE_TYPE)) {
						String tmp = node.getStringValue();
						Object object = null;
						if (StringUtils.isNotEmpty(tmp)) {
							switch (node.getDataType()) {
							case FieldType.INTEGER_TYPE:
								object = Integer.valueOf(tmp);
								break;
							case FieldType.LONG_TYPE:
								object = Long.valueOf(tmp);
								break;
							case FieldType.DOUBLE_TYPE:
								object = Double.valueOf(tmp);
								break;
							default:
								object = Double.valueOf(tmp);
								break;
							}
							dataMap.put(name, object);
							node.setValue(object);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof TextAreaNode
							|| StringUtils.equals(nodeType,
									TextAreaNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							dataMap.put(name, value);
							node.setValue(value);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof TextFieldNode
							|| StringUtils.equals(nodeType,
									TextFieldNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							dataMap.put(name, value);
							node.setValue(value);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					}
				}
			}
		}
		return dataMap;
	}

	public static Map<String, Object> distill(Map<String, String> params,
			FormDefinition formDefinition, DataModel formModel) {
		Map<String, Object> dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		List<FormNode> nodes = formDefinition.getNodes();
		if (nodes != null && nodes.size() > 0) {
			Iterator<FormNode> iterator = nodes.iterator();
			while (iterator.hasNext()) {
				FormNode formNode = iterator.next();

				if (!(formNode instanceof PersistenceNode)) {
					continue;
				}
				PersistenceNode node = (PersistenceNode) formNode;
				if (node.getAccessLevel() == AccessLevelType.WRITE_TYPE
						|| node.getAccessLevel() == AccessLevelType.REQUIRED_TYPE) {
					String name = node.getName();
					String value = params.get(name);
					if (value != null) {
						value = value.trim();
					}
					String nodeType = node.getNodeType();
					if (StringUtils.isEmpty(nodeType)) {
						continue;
					}

					if (node instanceof RadioNode
							|| StringUtils
									.equals(nodeType, RadioNode.NODE_TYPE)) {
						dataMap.put(name, value);
						node.setValue(value);
						ColumnModel col = new ColumnModel(node.getColumnName(),
								node.getName(), node.getValue());
						formModel.getFields().put(name, col);
					} else if (node instanceof CheckboxNode
							|| StringUtils.equals(nodeType,
									CheckboxNode.NODE_TYPE)) {
						dataMap.put(name, value);
						node.setValue(value);
						ColumnModel col = new ColumnModel(node.getColumnName(),
								node.getName(), node.getValue());
						formModel.getFields().put(name, col);
					} else if (node instanceof SelectNode
							|| StringUtils.equals(nodeType,
									SelectNode.NODE_TYPE)) {
						dataMap.put(name, value);
						node.setValue(value);
						ColumnModel col = new ColumnModel(node.getColumnName(),
								node.getName(), node.getValue());
						formModel.getFields().put(name, col);
					} else if (node instanceof HiddenNode
							|| StringUtils.equals(nodeType,
									HiddenNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							dataMap.put(name, value);
							node.setValue(value);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof PasswordNode
							|| StringUtils.equals(nodeType,
									PasswordNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							dataMap.put(name, value);
							node.setValue(value);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof TimestampFieldNode
							|| StringUtils.equals(nodeType,
									TimestampFieldNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							Date date = DateUtils.toDate(value);
							dataMap.put(name, date);
							node.setValue(date);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof DateFieldNode
							|| StringUtils.equals(nodeType,
									DateFieldNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							Date date = DateUtils.toDate(value);
							dataMap.put(name, date);
							node.setValue(date);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof NumberFieldNode
							|| StringUtils.equals(nodeType,
									NumberFieldNode.NODE_TYPE)) {
						String tmp = params.get(name);
						Object object = null;
						if (StringUtils.isNotEmpty(tmp)) {
							switch (node.getDataType()) {
							case FieldType.INTEGER_TYPE:
								object = Integer.valueOf(tmp);
								break;
							case FieldType.LONG_TYPE:
								object = Long.valueOf(tmp);
								break;
							case FieldType.DOUBLE_TYPE:
								object = Double.valueOf(tmp);
								break;
							default:
								object = Double.valueOf(tmp);
								break;
							}
							dataMap.put(name, object);
							node.setValue(object);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof TextAreaNode
							|| StringUtils.equals(nodeType,
									TextAreaNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							dataMap.put(name, value);
							node.setValue(value);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					} else if (node instanceof TextFieldNode
							|| StringUtils.equals(nodeType,
									TextFieldNode.NODE_TYPE)) {
						if (StringUtils.isNotEmpty(value)) {
							dataMap.put(name, value);
							node.setValue(value);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						}
					}
				}
			}
		}
		return dataMap;
	}

	public static Map<String, Object> distill(HttpServletRequest request,
			FormDefinition formDefinition, DataModel formModel) {
		Map<String, Object> dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		List<FormNode> nodes = formDefinition.getNodes();
		if (nodes != null && nodes.size() > 0) {
			Iterator<FormNode> iterator = nodes.iterator();
			while (iterator.hasNext()) {
				FormNode formNode = iterator.next();

				logger.debug(formNode.getName() + "--"
						+ formNode.getAccessLevel());
				PersistenceNode node = (PersistenceNode) formNode;
				if (node.getAccessLevel() == AccessLevelType.WRITE_TYPE
						|| node.getAccessLevel() == AccessLevelType.REQUIRED_TYPE) {
					String name = node.getName();
					if (name != null) {
						String x_name = StringTools.lower(formDefinition
								.getName()) + "." + name;
						String value = request.getParameter(x_name);
						if (StringUtils.isEmpty(value)) {
							value = request.getParameter(name);
						}
						if (value != null) {
							value = value.trim();
						}
						String nodeType = node.getNodeType();
						if (StringUtils.isEmpty(nodeType)) {
							continue;
						}

						if (node instanceof RadioNode
								|| StringUtils.equals(nodeType,
										RadioNode.NODE_TYPE)) {
							dataMap.put(name, value);
							node.setValue(value);
							ColumnModel col = new ColumnModel(
									node.getColumnName(), node.getName(),
									node.getValue());
							formModel.getFields().put(name, col);
						} else if (node instanceof CheckboxNode
								|| StringUtils.equals(nodeType,
										CheckboxNode.NODE_TYPE)) {
							String[] values = request
									.getParameterValues(x_name);
							if (values == null || values.length == 0) {
								values = request.getParameterValues(name);
							}
							if (values != null && values.length > 0) {
								StringBuffer buffer = new StringBuffer();
								for (int i = 0; i < values.length; i++) {
									buffer.append(values[i].trim());
									buffer.append(',');
								}
								if (buffer.length() > 0) {
									buffer.delete(buffer.length() - 1,
											buffer.length());
								}
								dataMap.put(name, buffer.toString());
								node.setValue(buffer.toString());
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							} else {
								dataMap.put(name, value);
								node.setValue(value);
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							}
						} else if (node instanceof SelectNode
								|| StringUtils.equals(nodeType,
										SelectNode.NODE_TYPE)) {
							String[] values = request
									.getParameterValues(x_name);
							if (values == null || values.length == 0) {
								values = request.getParameterValues(name);
							}
							if (values != null && values.length > 0) {
								StringBuffer buffer = new StringBuffer();
								for (int i = 0; i < values.length; i++) {
									buffer.append(values[i].trim());
									buffer.append(',');
								}
								if (buffer.length() > 0) {
									buffer.delete(buffer.length() - 1,
											buffer.length());
								}
								dataMap.put(name, buffer.toString());
								node.setValue(buffer.toString());
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							} else {
								dataMap.put(name, value);
								node.setValue(value);
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							}
						} else if (node instanceof HiddenNode
								|| StringUtils.equals(nodeType,
										HiddenNode.NODE_TYPE)) {
							if (StringUtils.isNotEmpty(value)) {
								dataMap.put(name, value);
								node.setValue(value);
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							}
						} else if (node instanceof PasswordNode
								|| StringUtils.equals(nodeType,
										PasswordNode.NODE_TYPE)) {
							if (StringUtils.isNotEmpty(value)) {
								dataMap.put(name, value);
								node.setValue(value);
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							}
						} else if (node instanceof TimestampFieldNode
								|| StringUtils.equals(nodeType,
										TimestampFieldNode.NODE_TYPE)) {
							if (StringUtils.isNotEmpty(value)) {
								Date date = DateUtils.toDate(value);
								dataMap.put(name, date);
								node.setValue(date);
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							}
						} else if (node instanceof DateFieldNode
								|| StringUtils.equals(nodeType,
										DateFieldNode.NODE_TYPE)) {
							if (StringUtils.isNotEmpty(value)) {
								Date date = DateUtils.toDate(value);
								dataMap.put(name, date);
								node.setValue(date);
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							}
						} else if (node instanceof NumberFieldNode
								|| StringUtils.equals(nodeType,
										NumberFieldNode.NODE_TYPE)) {
							Object object = null;
							if (StringUtils.isNotEmpty(value)) {
								switch (node.getDataType()) {
								case FieldType.INTEGER_TYPE:
									object = Integer.valueOf(value);
									break;
								case FieldType.LONG_TYPE:
									object = Long.valueOf(value);
									break;
								case FieldType.DOUBLE_TYPE:
									object = Double.valueOf(value);
									break;
								default:
									object = Double.valueOf(value);
									break;
								}
								dataMap.put(name, object);
								node.setValue(object);
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							}
						} else if (node instanceof TextAreaNode
								|| StringUtils.equals(nodeType,
										TextAreaNode.NODE_TYPE)) {
							if (StringUtils.isNotEmpty(value)) {
								dataMap.put(name, value);
								node.setValue(value);
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							}
						} else if (node instanceof TextFieldNode
								|| StringUtils.equals(nodeType,
										TextFieldNode.NODE_TYPE)) {
							if (StringUtils.isNotEmpty(value)) {
								dataMap.put(name, value);
								node.setValue(value);
								ColumnModel col = new ColumnModel(
										node.getColumnName(), node.getName(),
										node.getValue());
								formModel.getFields().put(name, col);
							}
						}
					}
				}
			}
		}
		return dataMap;
	}

	private FormTools() {

	}
}