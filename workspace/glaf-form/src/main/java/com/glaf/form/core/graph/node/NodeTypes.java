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
package com.glaf.form.core.graph.node;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.PropertiesUtils;
import com.glaf.core.util.XmlUtils;
import com.glaf.form.core.graph.def.FormNode;

public class NodeTypes {

	private static final Log log = LogFactory.getLog(NodeTypes.class);

	public static final int BOX_NODE_TYPE = 1;

	public static final int BUTTON_NODE_TYPE = 2;

	public static final int CHECKBOX_NODE_TYPE = 3;

	public static final int DATEFIELD_NODE_TYPE = 4;

	public static final int FIELDSET_NODE_TYPE = 5;

	public static final int HIDDEN_NODE_TYPE = 7;

	public static final int IMAGE_NODE_TYPE = 8;

	public static final int LABEL_NODE_TYPE = 9;

	public static final int LINE_NODE_TYPE = 10;

	public static final int NUMBERFIELD_NODE_TYPE = 11;

	public static final int PASSWORD_NODE_TYPE = 12;

	public static final int RADIO_NODE_TYPE = 13;

	public static final int RECTANGLE_NODE_TYPE = 14;

	public static final int SELECT_NODE_TYPE = 15;

	public static final int TEXTAREA_NODE_TYPE = 16;

	public static final int TEXTFIELD_NODE_TYPE = 18;

	public static final int INTEGERFIELD_NODE_TYPE = 19;

	public static final int LONGFIELD_NODE_TYPE = 20;

	public static final int TIMESTAMPFIELD_NODE_TYPE = 21;

	static Map<String, Class<?>> nodeTypes = initialiseNodeTypes();

	static Map<String, Integer> nodeTypeMap = new java.util.concurrent.ConcurrentHashMap<String, Integer>();

	static {
		nodeTypeMap.put(BoxNode.NODE_TYPE, Integer.valueOf(BOX_NODE_TYPE));
		nodeTypeMap
				.put(ButtonNode.NODE_TYPE, Integer.valueOf(BUTTON_NODE_TYPE));
		nodeTypeMap.put(CheckboxNode.NODE_TYPE,
				Integer.valueOf(CHECKBOX_NODE_TYPE));
		nodeTypeMap.put(DateFieldNode.NODE_TYPE,
				Integer.valueOf(DATEFIELD_NODE_TYPE));
		nodeTypeMap.put(FieldsetNode.NODE_TYPE,
				Integer.valueOf(FIELDSET_NODE_TYPE));

		nodeTypeMap.put(TimestampFieldNode.NODE_TYPE,
				Integer.valueOf(TIMESTAMPFIELD_NODE_TYPE));

		nodeTypeMap
				.put(HiddenNode.NODE_TYPE, Integer.valueOf(HIDDEN_NODE_TYPE));
		nodeTypeMap.put(ImageNode.NODE_TYPE, Integer.valueOf(IMAGE_NODE_TYPE));
		nodeTypeMap.put(LabelNode.NODE_TYPE, Integer.valueOf(LABEL_NODE_TYPE));
		nodeTypeMap.put(LineNode.NODE_TYPE, Integer.valueOf(LINE_NODE_TYPE));
		nodeTypeMap.put(NumberFieldNode.NODE_TYPE,
				Integer.valueOf(NUMBERFIELD_NODE_TYPE));

		nodeTypeMap.put(TimestampFieldNode.NODE_TYPE,
				Integer.valueOf(TIMESTAMPFIELD_NODE_TYPE));
		nodeTypeMap.put(PasswordNode.NODE_TYPE,
				Integer.valueOf(PASSWORD_NODE_TYPE));
		nodeTypeMap.put(RadioNode.NODE_TYPE, Integer.valueOf(RADIO_NODE_TYPE));
		nodeTypeMap.put(RectangleNode.NODE_TYPE,
				Integer.valueOf(RECTANGLE_NODE_TYPE));
		nodeTypeMap
				.put(SelectNode.NODE_TYPE, Integer.valueOf(SELECT_NODE_TYPE));
		nodeTypeMap.put(TextAreaNode.NODE_TYPE,
				Integer.valueOf(TEXTAREA_NODE_TYPE));
		nodeTypeMap.put(TextFieldNode.NODE_TYPE,
				Integer.valueOf(TEXTFIELD_NODE_TYPE));
	}

	public static int getNodeType(FormNode node) {
		if (node != null && node.getNodeType() != null
				&& nodeTypeMap.get(node.getNodeType()) != null) {
			Integer x = nodeTypeMap.get(node.getNodeType());
			if (x != null) {
				return x.intValue();
			}
		}
		return 0;
	}

	public static Class<?> getNodeType(String name) {
		return nodeTypes.get(name);
	}

	public static Set<String> getNodeTypes() {
		return nodeTypes.keySet();
	}

	public static void reload() {
		nodeTypes = initialiseNodeTypes();
	}

	static Map<String, Class<?>> initialiseNodeTypes() {
		Map<String, Class<?>> types = new java.util.concurrent.ConcurrentHashMap<String, Class<?>>();
		String resource = SystemProperties
				.getString("resource.form.node.types");
		if (StringUtils.isNotEmpty(resource)) {
			InputStream actionTypesStream = PropertiesUtils
					.getInputStream(resource);
			Element nodeTypesElement = XmlUtils.parseXmlInputStream(
					actionTypesStream).getDocumentElement();
			Iterator<?> nodeTypeIterator = XmlUtils.elementIterator(
					nodeTypesElement, "node-type");
			while (nodeTypeIterator.hasNext()) {
				Element nodeTypeElement = (Element) nodeTypeIterator.next();
				String elementTag = nodeTypeElement.getAttribute("element");
				String className = nodeTypeElement.getAttribute("class");
				try {
					Class<?> nodeClass = com.glaf.core.util.ClassUtils
							.loadClass(className);
					types.put(elementTag, nodeClass);
				} catch (Exception e) {
					log.error("node '" + elementTag
							+ "' will not be available. class '" + className
							+ "' couldn't be loaded");
				}
			}
		}

		String user_resource = CustomProperties
				.getString("resource.form.node.types");
		if (StringUtils.isNotEmpty(user_resource)) {
			InputStream actionTypesStream = PropertiesUtils
					.getInputStream(resource);
			Element nodeTypesElement = XmlUtils.parseXmlInputStream(
					actionTypesStream).getDocumentElement();
			Iterator<?> nodeTypeIterator = XmlUtils.elementIterator(
					nodeTypesElement, "node-type");
			while (nodeTypeIterator.hasNext()) {
				Element nodeTypeElement = (Element) nodeTypeIterator.next();
				String elementTag = nodeTypeElement.getAttribute("element");
				String className = nodeTypeElement.getAttribute("class");
				try {
					Class<?> nodeClass = com.glaf.core.util.ClassUtils
							.loadClass(className);
					types.put(elementTag, nodeClass);
				} catch (Exception e) {
					log.error("node '" + elementTag
							+ "' will not be available. class '" + className
							+ "' couldn't be loaded");
				}
			}
		}

		return types;
	}
}