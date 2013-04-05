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
package com.glaf.form.core.export.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.Paging;
import com.glaf.form.core.graph.def.FormApplication;
import com.glaf.form.core.graph.def.FormDefinition;
import com.glaf.form.core.query.FormApplicationQuery;
import com.glaf.form.core.query.FormDefinitionQuery;
import com.glaf.form.core.service.FormDataService;

public class MxFormDataExporter {

	protected final static Log logger = LogFactory
			.getLog(MxFormDataExporter.class);

	public void addElement(Document doc, Map<String, Object> context) {
		Element root = doc.getRootElement();
		FormDataService formDataService = (FormDataService) ContextFactory
				.getBean("formDataService");
		FormApplicationQuery query = new FormApplicationQuery();
		Paging jpage = formDataService.getPageApplication(query);
		List<Object> rows = jpage.getRows();
		if (rows != null && rows.size() > 0) {
			Element element = root.addElement("applications");
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				FormApplication app = (FormApplication) iterator.next();
				Element elem = element.addElement("application");
				elem.addAttribute("name", app.getName());
				elem.addAttribute("title", app.getTitle());
				elem.addAttribute("nodeId", String.valueOf(app.getNodeId()));
				elem.addAttribute("formName", app.getFormName());
				elem.addAttribute("processName", app.getProcessName());
			}
		}

		FormDefinitionQuery query2 = new FormDefinitionQuery();
		Paging jpage2 = formDataService.getPageFormDefinition(query2);
		List<Object> rows2 = jpage2.getRows();
		if (rows2 != null && rows2.size() > 0) {
			Element element = root.addElement("forms");
			Iterator<Object> iterator = rows2.iterator();
			while (iterator.hasNext()) {
				FormDefinition form = (FormDefinition) iterator.next();
				Element elem = element.addElement("form");
				elem.addAttribute("name", form.getName());
				elem.addAttribute("title", form.getTitle());
				elem.addAttribute("nodeId", String.valueOf(form.getNodeId()));
			}
		}

	}
}