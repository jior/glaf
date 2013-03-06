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

package com.glaf.jbpm.config;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.glaf.core.util.Dom4jUtils;
import com.glaf.jbpm.model.Extension;
import com.glaf.jbpm.model.ExtensionField;
import com.glaf.jbpm.model.ExtensionParam;

public class JbpmExtensionWriter {

	public Document write(List<Extension> extensions) {
		Document doc = DocumentHelper.createDocument();
		if (extensions != null && extensions.size() > 0) {
			Element root = doc.addElement("bpm-cfg");
			Iterator<Extension> iterator = extensions.iterator();
			while (iterator.hasNext()) {
				Extension extension = iterator.next();
				if (root.attribute("type") == null
						&& extension.getType() != null) {
					root.addAttribute("type", extension.getType());
				}
				if (StringUtils.isNotEmpty(extension.getTaskName())) {
					Element element = root.addElement("taskmgr");
					element.addAttribute("processName",
							extension.getProcessName());
					element.addAttribute("taskName", extension.getTaskName());
					if (extension.getFieldValue("taskMgmtType") != null) {
						Element elem = element.addElement("taskMgmtType");
						elem.setText(extension.getFieldValue("taskMgmtType"));
					}
					if (extension.getFieldValue("handlers") != null) {
						Element elem = element.addElement("handlers");
						elem.setText(extension.getFieldValue("handlers"));
					}
					if (extension.getFields() != null
							&& extension.getFields().size() > 0) {
						Element elem = element.addElement("properties");
						Iterator<ExtensionField> iter = extension.getFields()
								.values().iterator();
						while (iter.hasNext()) {
							ExtensionField field = iter.next();
							Element e = elem.addElement("property");
							e.addAttribute("key", field.getName());
							e.addCDATA(field.getValue());
						}
					}
				} else if (StringUtils.isNotEmpty(extension.getName())) {
					Element element = root.addElement("action");
					element.addAttribute("processName",
							extension.getProcessName());
					element.addAttribute("name", extension.getName());
					if (extension.getFieldValue("sql") != null) {
						Element elem = element.addElement("sql");
						elem.addCDATA(extension.getFieldValue("sql"));
					}
					if (extension.getFieldValue("handlers") != null) {
						Element elem = element.addElement("handlers");
						elem.setText(extension.getFieldValue("handlers"));
					}
					if (extension.getParams() != null
							&& extension.getParams().size() > 0) {
						Element em = element.addElement("parameters");
						Iterator<ExtensionParam> iter = extension.getParams()
								.iterator();
						while (iter.hasNext()) {
							ExtensionParam param = iter.next();
							Element e = em.addElement("parameter");
							e.addAttribute("name", param.getName());
							e.addAttribute("type", param.getType());
							e.addCDATA(param.getValue());
						}
					}
				}
			}
		}
		return doc;
	}

	public static void main(String[] args) throws Exception {
		java.io.InputStream inputStream = new java.io.FileInputStream(args[0]);
		JbpmExtensionReader reader = new JbpmExtensionReader();
		List<Extension> extensions = reader.readTasks(inputStream);
		inputStream.close();
		inputStream = null;
		JbpmExtensionWriter writer = new JbpmExtensionWriter();
		Document doc = writer.write(extensions);
		byte[] bytes = Dom4jUtils.getBytesFromPrettyDocument(doc, "GBK");
		System.out.println(new String(bytes));
	}
}