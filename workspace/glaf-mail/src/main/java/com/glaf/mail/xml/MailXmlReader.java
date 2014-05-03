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

package com.glaf.mail.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.mail.def.MailDataSet;
import com.glaf.mail.def.MailRowSet;
import com.glaf.mail.def.MailTemplate;

public class MailXmlReader {

	public List<MailTemplate> read(java.io.InputStream inputStream) {
		List<MailTemplate> mailTemplates = new java.util.ArrayList<MailTemplate>();
		SAXReader xmlReader = new SAXReader();
		Document doc = null;
		try {
			doc = xmlReader.read(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		Element root = doc.getRootElement();
		List<?> rows = root.elements();
		Iterator<?> iterator = rows.iterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			String mailId = element.attributeValue("id");
			String title = element.attributeValue("title");
			String templateId = element.elementText("templateId");
			String templatePath = element.elementText("templatePath");

			MailTemplate mt = new MailTemplate();
			mt.setTitle(title);
			mt.setMailDefId(mailId);
			mt.setTemplateId(templateId);
			mt.setTemplatePath(templatePath);
			mt.setProperties(this.readProperties(element));
			mt.setContent(element.elementText("content"));
			mt.setDescription(element.elementText("description"));

			List<?> datasets = element.elements("dataset");
			Iterator<?> iter = datasets.iterator();
			while (iter.hasNext()) {
				Element elem = (Element) iter.next();
				MailDataSet mailDS = new MailDataSet();
				mailDS.setProperties(this.readProperties(elem));

				List<?> rowsets = elem.elements("rowset");
				Iterator<?> it = rowsets.iterator();
				while (it.hasNext()) {
					Element em = (Element) it.next();
					MailRowSet rs = new MailRowSet();
					rs.setQuery(em.elementTextTrim("query"));
					rs.setMapping(em.attributeValue("mapping"));
					rs.setMailMgr(em.attributeValue("mailMgr"));
					rs.setMailMgrClassName(em
							.attributeValue("mailMgrClassName"));
					rs.setMailMgrMapping(em.attributeValue("mailMgrMapping"));
					rs.setDataMgr(em.attributeValue("dataMgr"));
					rs.setDataMgrBeanId(em.attributeValue("dataMgrBeanId"));
					rs.setDataMgrClassName(em
							.attributeValue("dataMgrClassName"));
					rs.setProperties(this.readProperties(em));
					String singleResult = em.attributeValue("singleResult");
					if ("true".equals(singleResult)) {
						rs.setSingleResult(true);
					}
					mailDS.addRowSet(rs);
				}

				mt.addDataSet(mailDS);
			}
			mailTemplates.add(mt);
		}

		return mailTemplates;
	}

	protected Map<String, Object> readProperties(Element element) {
		Map<String, Object> properties = new java.util.HashMap<String, Object>();
		Element propsElement = element.element("properties");
		if (propsElement != null) {
			List<?> rows = propsElement.elements("property");
			Iterator<?> iter = rows.iterator();
			while (iter.hasNext()) {
				Element elem = (Element) iter.next();
				String name = elem.attributeValue("name");
				String value = elem.attributeValue("value");
				if (StringUtils.isEmpty(value)) {
					value = elem.getStringValue();
				}
				properties.put(name, value);
			}
		}
		return properties;
	}

}