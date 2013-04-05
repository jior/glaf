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

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;

import com.glaf.form.core.graph.def.*;
import com.glaf.core.util.DateUtils;

public class FormModelXmlWriter {
	private static final Log log = LogFactory.getLog(FormModelXmlWriter.class);

	protected FormDefinition formDefinition = null;

	protected Document document = null;

	public FormModelXmlWriter(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}

	public FormDefinition getFormDefinition() {
		return formDefinition;
	}

	public Document writeFormDefinition() {
		try {
			document = DocumentHelper.createDocument();
			Element root = document.addElement("form-definition");
			if (root != null) {
				writeFormDefinitionAttributes(root, formDefinition);
				writeNodes(root, formDefinition);
			}
		} catch (Exception ex) {
			log.error("couldn't write form definition", ex);
		}
		return document;
	}

	protected void writeFormDefinitionAttributes(Element root,
			FormDefinition formDefinition) {
		root.addAttribute("name", formDefinition.getName());
		root.addAttribute("title", formDefinition.getTitle());
		root.addAttribute("description", formDefinition.getDescription());

	}

	public void writeNodes(Element element, FormDefinition formDefinition) {
		List<FormNode> nodes = formDefinition.getNodes();
		if (nodes == null || nodes.size() == 0) {
			return;
		}
		Collection<String> names = new HashSet<String>();
		Iterator<FormNode> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			FormNode node = iterator.next();
			if (!names.contains(node.getName())) {
				names.add(node.getName());
				this.writeNode(element, node, formDefinition);
			}
		}
	}

	public void writeNode(Element element, FormNode node,
			FormDefinition formDefinition) {
		Element elem = null;
		String nodeType = node.getNodeType();
		if ("numberfield".equals(nodeType)) {
			elem = element.addElement(node.getName());
		} else if ("datefield".equals(nodeType)) {
			elem = element.addElement(node.getName());
		} else if ("textfield".equals(nodeType)) {
			elem = element.addElement(node.getName());
		} else if ("textarea".equals(nodeType)) {
			elem = element.addElement(node.getName());
		} else if ("password".equals(nodeType)) {
			elem = element.addElement(node.getName());
		} else if ("select".equals(nodeType)) {
			elem = element.addElement(node.getName());
		} else if ("radio".equals(nodeType)) {
			elem = element.addElement(node.getName());
		} else if ("checkbox".equals(nodeType)) {
			elem = element.addElement(node.getName());
		} else if ("hidden".equals(nodeType)) {
			elem = element.addElement(node.getName());
		} else {
			return;
		}

		elem.addAttribute("title", node.getTitle());
		elem.addAttribute("display", node.getDisplay());
		elem.addAttribute("description", node.getDescription());

		if (node.getValue() != null) {
			if (node.getValue() instanceof Date) {
				String value = DateUtils.getDateTime((Date) node.getValue());
				elem.addAttribute("value", value);
			} else {
				String value = node.getValue().toString();
				Element e = elem.addElement("value");
				e.addCDATA(value);
			}
		}

	}

}