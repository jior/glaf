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
package com.glaf.form.core.xml;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.form.core.domain.FormApplication;
import com.glaf.core.util.*;

public class FormApplicationReader {

	public List<FormApplication> read(InputStream inputStream) {
		List<FormApplication> formApplications = new ArrayList<FormApplication>();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List<?> rows = root.elements("application");
			if (rows != null && rows.size() > 0) {
				Iterator<?> iterator = rows.iterator();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					FormApplication formApplication = this.parse(element);
					formApplications.add(formApplication);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return formApplications;
	}

	public FormApplication parse(Element element) {
		FormApplication formApplication = new FormApplication();
		String name = element.attributeValue("name");
		String title = element.attributeValue("title");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("name", name);
		dataMap.put("title", title);
		List<?> list = element.elements();
		if (list != null && list.size() > 0) {
			Iterator<?> iter = list.iterator();
			while (iter.hasNext()) {
				Element elem = (Element) iter.next();
				String propertyName = elem.getName();
				if (StringUtils.isNotEmpty(elem.attributeValue("name"))) {
					propertyName = elem.attributeValue("name");
				}
				String propertyValue = null;
				if (StringUtils.isNotEmpty(elem.attributeValue("value"))) {
					propertyValue = elem.attributeValue("value");
				} else {
					propertyValue = elem.getTextTrim();
				}
				dataMap.put(propertyName, propertyValue);
			}
		}

		dataMap.remove("id");

		Tools.populate(formApplication, dataMap);

		return formApplication;
	}

}