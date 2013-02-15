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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jpage.persistence.SQLParameter;
import org.jpage.util.FieldType;

public class SqlMapReader {

	public SQLMapActionMapping getActionMapping(InputStream inputStream) {
		SQLMapActionMapping mapping = new SQLMapActionMapping();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			String namespace = root.attributeValue("namespace");
			mapping.setNamespace(namespace);
			List actionList = root.elements();
			if (actionList != null && actionList.size() > 0) {
				Iterator iterator = actionList.iterator();
				while (iterator.hasNext()) {
					Element elem = (Element) iterator.next();
					if (StringUtils.isNotBlank(elem.attributeValue("name"))) {
						SQLMapAction action = new SQLMapAction();
						action.setName(elem.attributeValue("name"));
						mapping.getActions().put(action.getName(), action);
						List executorList = elem.elements();
						if (executorList != null && executorList.size() > 0) {
							Iterator iter = executorList.iterator();
							while (iter.hasNext()) {
								Element el = (Element) iter.next();
								SQLMap sqlmap = new SQLMap();
								sqlmap.setName(el.attributeValue("name"));
								sqlmap
										.setRefName(el
												.attributeValue("ref-name"));
								sqlmap.setOperation(el
										.attributeValue("operation"));
								sqlmap.setClassName(el.attributeValue("class"));
								action.getSqlmaps().add(sqlmap);
								List fields = el.elements();
								if (fields != null && fields.size() > 0) {
									Iterator it = fields.iterator();
									while (it.hasNext()) {
										Element e = (Element) it.next();
										SQLParameter param = new SQLParameter();
										List props = e.elements("field");
										for (int k = 0; k < props.size(); k++) {
											Element element = (Element) props
													.get(k);
											String propertyName = element
													.attributeValue("name");
											String propertyValue = null;
											if (element.attribute("value") != null) {
												propertyValue = element
														.attributeValue("value");
											} else {
												propertyValue = element
														.getTextTrim();
											}
											if (StringUtils
													.isBlank(propertyValue)) {
												continue;
											}
											if ("typeName"
													.equalsIgnoreCase(propertyName)) {
												param
														.setType(FieldType
																.getFieldType(propertyValue));
												param
														.setTypeName(propertyValue);
											} else {
												try {
													BeanUtils.setProperty(
															param,
															propertyName,
															propertyValue);
												} catch (Exception ex) {
												}
											}
										}
										sqlmap.getFields().add(param);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return mapping;
	}

	public static void main(String[] args) throws Exception {
		SqlMapReader reader = new SqlMapReader();
		InputStream inputStream = new FileInputStream(args[0]);
		System.out.println(reader.getActionMapping(inputStream));
	}
}
