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

package com.glaf.core.todo.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.todo.Todo;
import com.glaf.core.util.Tools;

public class TodoXmlReader {

	public List<Todo> read(java.io.InputStream inputStream) {
		List<Todo> todos = new java.util.ArrayList<Todo>();
		SAXReader xmlReader = new SAXReader();
		int sortNo = 1;
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List<?> rows = root.elements();
			Iterator<?> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				String id = element.attributeValue("id");
				Map<String, Object> rowMap = new java.util.HashMap<String, Object>();
				rowMap.put("id", id);
				List<?> properties = element.elements("property");
				Iterator<?> iter = properties.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					String propertyName = elem.attributeValue("name");
					String propertyValue = null;
					if (elem.attribute("value") != null) {
						propertyValue = elem.attributeValue("value");
					} else {
						propertyValue = elem.getTextTrim();
					}
					if (StringUtils.isNotEmpty(propertyName)
							&& StringUtils.isNotEmpty(propertyValue)) {
						rowMap.put(propertyName, propertyValue);
					}
				}

				Todo model = new Todo();
				model.setSortNo(sortNo++);
				Tools.populate(model, rowMap);

				todos.add(model);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return todos;
	}
}