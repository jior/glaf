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

package com.glaf.activiti.extension.xml;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.activiti.extension.model.ExtensionEntity;
import com.glaf.activiti.extension.model.ExtensionFieldEntity;
import com.glaf.activiti.extension.model.ExtensionParamEntity;

public class ExtensionReader {

	public List<ExtensionEntity> readTasks(java.io.InputStream inputStream) {
		List<ExtensionEntity> extensions = new java.util.ArrayList<ExtensionEntity>();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			String x_type = root.attributeValue("type");
			List<?> rows = root.elements("taskmgr");
			Iterator<?> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				ExtensionEntity extension = new ExtensionEntity();
				extension.setProcessName(element.attributeValue("processName"));
				extension.setTaskName(element.attributeValue("taskName"));
				extension.setType(x_type);
				if (element.elementTextTrim("taskMgmtType") != null) {
					ExtensionFieldEntity extensionField = new ExtensionFieldEntity();
					extensionField.setName("taskMgmtType");
					extensionField.setValue(element
							.elementTextTrim("taskMgmtType"));
					extension.addField(extensionField);
				}
				Element propertiesE = element.element("properties");
				if (propertiesE != null) {
					List<?> properties = propertiesE.elements("property");
					Iterator<?> iter = properties.iterator();
					while (iter.hasNext()) {
						Element elem = (Element) iter.next();
						String propertyName = elem.attributeValue("key");
						String propertyValue = null;
						if (elem.attribute("value") != null) {
							propertyValue = elem.attributeValue("value");
						} else {
							propertyValue = elem.getTextTrim();
						}
						if (StringUtils.isNotEmpty(propertyName)
								&& StringUtils.isNotEmpty(propertyValue)) {
							ExtensionFieldEntity extensionField = new ExtensionFieldEntity();
							extensionField.setName(propertyName.trim());
							extensionField.setValue(propertyValue.trim());
							extension.addField(extensionField);
						}
					}
				}
				if (element.elementText("handlers") != null) {
					ExtensionFieldEntity extensionField = new ExtensionFieldEntity();
					extensionField.setName("handlers");
					extensionField
							.setValue(element.elementTextTrim("handlers"));
					extension.addField(extensionField);
				}
				extensions.add(extension);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return extensions;
	}

	public List<ExtensionEntity> readActions(java.io.InputStream inputStream) {
		List<ExtensionEntity> extensions = new java.util.ArrayList<ExtensionEntity>();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			String x_type = root.attributeValue("type");
			List<?> actions = root.elements("action");
			Iterator<?> iter = actions.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				ExtensionEntity extension = new ExtensionEntity();
				extension.setProcessName(element.attributeValue("processName"));
				extension.setTaskName(element.attributeValue("taskName"));
				extension.setName(element.attributeValue("name"));
				extension.setType(x_type);
				extension
						.setDescription(element.elementTextTrim("description"));
				Iterator<?> it99 = element.elementIterator();
				while (it99.hasNext()) {
					Element elem = (Element) it99.next();
					String propertyName = elem.getName();
					String propertyValue = elem.getTextTrim();
					if (StringUtils.isNotEmpty(propertyValue)) {
						ExtensionFieldEntity extensionField = new ExtensionFieldEntity();
						extensionField.setName(propertyName.trim());
						extensionField.setValue(propertyValue.trim());
						extension.addField(extensionField);
					}
				}
				if (element.elementText("sql") != null) {
					ExtensionFieldEntity extensionField = new ExtensionFieldEntity();
					extensionField.setName("sql");
					extensionField.setValue(element.elementTextTrim("sql"));
					extension.addField(extensionField);

				}
				if (element.elementText("handlers") != null) {
					ExtensionFieldEntity extensionField = new ExtensionFieldEntity();
					extensionField.setName("handlers");
					extensionField
							.setValue(element.elementTextTrim("handlers"));
					extension.addField(extensionField);
				}

				Element parametersE = element.element("parameters");
				if (parametersE != null) {
					List<?> parameters = parametersE.elements("parameter");
					Iterator<?> it = parameters.iterator();
					while (it.hasNext()) {
						Element elem = (Element) it.next();
						String propertyName = elem.attributeValue("name");
						String type = elem.attributeValue("type");
						String propertyValue = null;
						if (elem.attribute("value") != null) {
							propertyValue = elem.attributeValue("value");
						} else {
							propertyValue = elem.getTextTrim();
						}
						if (StringUtils.isNotEmpty(propertyName)
								&& StringUtils.isNotEmpty(propertyValue)) {
							ExtensionParamEntity extensionParam = new ExtensionParamEntity();
							extensionParam.setName(propertyName.trim());
							extensionParam.setValue(propertyValue.trim());
							extensionParam.setType(type);
							extension.addParam(extensionParam);
						}
					}
				}

				extensions.add(extension);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return extensions;
	}
}