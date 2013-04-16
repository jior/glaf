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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.glaf.form.core.context.FormContext;
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
import com.glaf.core.base.DataModel;

import com.glaf.core.util.DateUtils;
import com.glaf.core.util.ReflectUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

public class FormAccessUtil {

	public static Map<String, Object> process(FormContext formContext,
			Object entity, boolean isCreate) {
		FormDefinition formDefinition = formContext.getFormDefinition();
		Map<String, Object> persistMap = new HashMap<String, Object>();
		Map<String, Object> dataMap = formContext.getDataMap();
		DataModel dataModel = formContext.getDataModel();
		if (isCreate) {
			persistMap.put("id", dataModel.getId());
			persistMap.put("businessKey", dataModel.getBusinessKey());
			persistMap.put("parentId", dataModel.getParentId());
			persistMap.put("actorId", formContext.getActorId());
			persistMap.put("createBy", dataModel.getCreateBy());
			persistMap.put("createDate", new Date());
			persistMap.put("formName", formDefinition.getName());
			persistMap.put("formDefinitionId",
					formDefinition.getFormDefinitionId());
			persistMap.put("deleteFlag", 0);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
					Locale.getDefault());
			String ret = formatter.format(new Date());

			Long sid = System.currentTimeMillis();
			String serialNumber = ret + StringTools.getDigit4Id(sid.intValue());
			persistMap.put("serialNumber", serialNumber);
		} else {
			persistMap.put("updateBy", dataModel.getUpdateBy());
			persistMap.put("updateDate", new Date());
		}

		if (entity != null) {
			Tools.populate(entity, persistMap);
		}

		List<FormNode> nodes = formDefinition.getNodes();

		if (nodes != null && nodes.size() > 0) {
			Iterator<FormNode> iterator = nodes.iterator();
			while (iterator.hasNext()) {
				FormNode node = iterator.next();
				if (!(node instanceof PersistenceNode)) {
					continue;
				}
				String name = node.getName();
				if (StringUtils.isEmpty(name)) {
					continue;
				}

				int accessLevel = node.getAccessLevel();
				if (accessLevel == AccessLevelType.HIDE_TYPE
						|| accessLevel == AccessLevelType.VIEW_TYPE) {
					continue;
				}

				Object value = node.getValue();

				if (value == null) {
					String x_name = StringTools.lower(formDefinition.getName())
							+ "." + node.getName();
					value = dataMap.get(x_name);
				}

				if (value == null) {
					value = dataMap.get(name);
				}

				if (value != null) {
					if (node instanceof CheckboxNode) {
						if (value != null) {
							persistMap.put(name, value);
							ReflectUtils.setFieldValue(entity, name, value);
						}
					} else if (node instanceof RadioNode) {
						if (value != null) {
							persistMap.put(name, value);
							ReflectUtils.setFieldValue(entity, name, value);
						}
					} else if (node instanceof SelectNode) {
						if (value != null) {
							persistMap.put(name, value);
							ReflectUtils.setFieldValue(entity, name, value);
						}
					} else if (node instanceof HiddenNode) {
						if (value != null) {
							persistMap.put(name, value);
							ReflectUtils.setFieldValue(entity, name, value);
						}
					} else if (node instanceof PasswordNode) {
						if (value != null) {
							persistMap.put(name, value);
							ReflectUtils.setFieldValue(entity, name, value);
						}
					} else if (node instanceof TimestampFieldNode) {
						if (value != null) {
							if (value instanceof java.util.Date) {
								persistMap.put(name, value);
								ReflectUtils.setFieldValue(entity, name, value);
							} else if (value instanceof String) {
								Date date = DateUtils.toDate(value.toString());
								persistMap.put(name, date);
								ReflectUtils.setFieldValue(entity, name, date);
							}
						}
					} else if (node instanceof DateFieldNode) {
						if (value != null) {
							if (value instanceof java.util.Date) {
								persistMap.put(name, value);
								ReflectUtils.setFieldValue(entity, name, value);
							} else if (value instanceof String) {
								Date date = DateUtils.toDate(value.toString());
								persistMap.put(name, date);
								ReflectUtils.setFieldValue(entity, name, date);
							}
						}
					} else if (node instanceof NumberFieldNode) {
						NumberFieldNode x = (NumberFieldNode) node;
						if (x.validate(value) && value != null) {
							persistMap.put(name, value);
							ReflectUtils.setFieldValue(entity, name, value);
						}
					} else if (node instanceof TextAreaNode) {
						if (value != null) {
							persistMap.put(name, value);
							ReflectUtils.setFieldValue(entity, name, value);
						}
					} else if (node instanceof TextFieldNode) {
						if (value != null) {
							persistMap.put(name, value);
							ReflectUtils.setFieldValue(entity, name, value);
						}
					}
				}
			}
		}

		return persistMap;
	}

}