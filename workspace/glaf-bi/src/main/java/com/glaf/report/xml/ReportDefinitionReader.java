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

package com.glaf.report.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.report.def.ReportDataSet;
import com.glaf.report.def.ReportDefinition;
import com.glaf.report.def.ReportRowSet;

public class ReportDefinitionReader {

	public List<ReportDefinition> read(java.io.InputStream inputStream) {
		List<ReportDefinition> reports = new java.util.concurrent.CopyOnWriteArrayList<ReportDefinition>();
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
			String reportId = element.attributeValue("id");
			String templateId = element.elementText("templateId");
			String templateFile = element.elementText("templateFile");

			ReportDefinition rdf = new ReportDefinition();
			rdf.setReportId(reportId);
			rdf.setTemplateId(templateId);
			rdf.setTemplateFile(templateFile);
			rdf.setProperties(this.readProperties(element));

			List<?> datasets = element.elements("dataset");
			Iterator<?> iter = datasets.iterator();
			while (iter.hasNext()) {
				Element elem = (Element) iter.next();
				ReportDataSet rds = new ReportDataSet();
				rds.setDatasourceName(elem.attributeValue("datasourceName"));
				rds.setProperties(this.readProperties(elem));

				List<?> rowsets = elem.elements("rowset");
				Iterator<?> it = rowsets.iterator();
				while (it.hasNext()) {
					Element em = (Element) it.next();
					ReportRowSet rs = new ReportRowSet();
					rs.setQuery(em.elementTextTrim("query"));
					rs.setMapping(em.attributeValue("mapping"));
					rs.setRptMgr(em.attributeValue("rptMgr"));
					rs.setRptMgrClassName(em.attributeValue("rptMgrClassName"));
					rs.setRptMgrMapping(em.attributeValue("rptMgrMapping"));
					rs.setDataMgr(em.attributeValue("dataMgr"));
					rs.setDataMgrBeanId(em.attributeValue("dataMgrBeanId"));
					rs.setDataMgrClassName(em
							.attributeValue("dataMgrClassName"));
					rs.setProperties(this.readProperties(em));
					String singleResult = em.attributeValue("singleResult");
					if ("true".equals(singleResult)) {
						rs.setSingleResult(true);
					}
					rds.addRowSet(rs);
				}

				rdf.addDataSet(rds);
			}
			reports.add(rdf);
		}

		return reports;
	}

	protected Map<String, Object> readProperties(Element element) {
		Map<String, Object> properties = new java.util.concurrent.ConcurrentHashMap<String, Object>();
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