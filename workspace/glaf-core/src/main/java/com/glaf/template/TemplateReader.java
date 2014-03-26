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

package com.glaf.template;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.template.Template;
import com.glaf.core.util.Tools;

public class TemplateReader {

	public Element getRootElement(InputStream inputStream) {
		SAXReader xmlReader = new SAXReader();
		Document doc = null;
		try {
			doc = xmlReader.read(inputStream);
		} catch (DocumentException ex) {
			throw new RuntimeException(ex);
		}
		Element root = doc.getRootElement();
		return root;
	}

	@SuppressWarnings("unchecked")
	public List<Template> readTemplates(InputStream inputStream) {
		List<Template> templates = new java.util.concurrent.CopyOnWriteArrayList<Template>();
		Element root = this.getRootElement(inputStream);
		List<Element> elements = root.elements("template");
		if (elements != null && !elements.isEmpty()) {
			for (Element element : elements) {
				Template tpl = new Template();
				tpl.setName(element.attributeValue("name"));
				tpl.setDescription(element.attributeValue("description"));
				List<Element> elems = element.elements();
				if (elems != null && !elems.isEmpty()) {
					Map<String, Object> dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
					for (Element em : elems) {
						dataMap.put(em.getName(), em.getTextTrim());
					}
					Tools.populate(tpl, dataMap);
				}
				templates.add(tpl);
			}
		}

		return templates;
	}

}
