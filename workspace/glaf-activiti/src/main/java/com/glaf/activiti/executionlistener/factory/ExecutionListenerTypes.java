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

package com.glaf.activiti.executionlistener.factory;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.XmlUtils;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.util.PropertiesUtils;
 
 

public class ExecutionListenerTypes {

	private static final Log logger = LogFactory
			.getLog(ExecutionListenerTypes.class);

	static Map<String, Class<?>> executionListeners = initializeExecutionListenerTypes();

	private final static String DEFAULT_CONFIG = "com/glaf/activiti/executionlistener/factory/activiti.executionlistener.xml";

	public static void addExecutionListener(Class<?> clazz) {
		String name = clazz.getName();
		executionListeners.put(name, clazz);
	}

	public static void addExecutionListener(String className) {
		try {
			Class<?> clazz = com.glaf.core.util.ClassUtils
					.loadClass(className);
			executionListeners.put(className, clazz);
		} catch (Exception ex) {
			logger.error("class '" + className + "' couldn't be loaded");
			throw new RuntimeException(ex);
		}
	}

	public static void addExecutionListener(String name, Class<?> clazz) {
		executionListeners.put(name, clazz);
	}

	public static Class<?> getExecutionListenerType(String name) {
		return executionListeners.get(name);
	}

	public static Set<String> getExecutionListenerTypes() {
		return executionListeners.keySet();
	}

	static Map<String, Class<?>> initializeExecutionListenerTypes() {
		Map<String, Class<?>> types = new java.util.HashMap<String, Class<?>>();
		String resource = SystemProperties.getString("activiti.executionListeners");
		if (StringUtils.isEmpty(resource)) {
			resource = DEFAULT_CONFIG;
		}
		if (StringUtils.isNotEmpty(resource)) {
			InputStream actionTypesStream = PropertiesUtils
					.getInputStream(resource);
			Element executionListenersElement = XmlUtils.parseXmlInputStream(
					actionTypesStream).getDocumentElement();
			Iterator<?> nodeTypeIterator = XmlUtils.elementIterator(
					executionListenersElement, "executionListeners");
			while (nodeTypeIterator.hasNext()) {
				Element nodeTypeElement = (Element) nodeTypeIterator.next();
				String elementTag = nodeTypeElement.getAttribute("element");
				String className = nodeTypeElement.getAttribute("class");
				try {
					Class<?> clazz = com.glaf.core.util.ClassUtils
							.loadClass(className);
					types.put(elementTag, clazz);
				} catch (Exception ex) {
					if (LogUtils.isDebug()) {
						ex.printStackTrace();
					}
					logger.error("node '" + elementTag
							+ "' will not be available. class '" + className
							+ "' couldn't be loaded");
				}
			}
		}

		String ext_resource = CustomProperties
				.getString("activiti.executionListeners");
		if (StringUtils.isNotEmpty(ext_resource)) {
			InputStream actionTypesStream = PropertiesUtils
					.getInputStream(resource);
			Element executionListenersElement = XmlUtils.parseXmlInputStream(
					actionTypesStream).getDocumentElement();
			Iterator<?> nodeTypeIterator = XmlUtils.elementIterator(
					executionListenersElement, "executionListener");
			while (nodeTypeIterator.hasNext()) {
				Element nodeTypeElement = (Element) nodeTypeIterator.next();
				String elementTag = nodeTypeElement.getAttribute("element");
				String className = nodeTypeElement.getAttribute("class");
				try {
					Class<?> clazz = com.glaf.core.util.ClassUtils
							.loadClass(className);
					types.put(elementTag, clazz);
				} catch (Exception ex) {
					if (LogUtils.isDebug()) {
						ex.printStackTrace();
					}
					logger.error("node '" + elementTag
							+ "' will not be available. class '" + className
							+ "' couldn't be loaded");
				}
			}
		}

		return types;
	}
}