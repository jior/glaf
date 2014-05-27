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

package com.glaf.activiti.tasklistener.factory;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.PropertiesUtils;
import com.glaf.core.util.XmlUtils;

public class TaskListenerTypes {

	private static final Log logger = LogFactory
			.getLog(TaskListenerTypes.class);

	static Map<String, Class<?>> taskListeners = initializeTaskListenerTypes();

	private final static String DEFAULT_CONFIG = "com/glaf/activiti/tasklistener/factory/activiti.tasklistener.xml";

	public static void addTaskListener(Class<?> clazz) {
		String name = clazz.getName();
		taskListeners.put(name, clazz);
	}

	public static void addTaskListener(String className) {
		try {
			Class<?> clazz = com.glaf.core.util.ClassUtils.loadClass(className);
			taskListeners.put(className, clazz);
		} catch (Exception ex) {
			logger.error("class '" + className + "' couldn't be loaded");
			throw new RuntimeException(ex);
		}
	}

	public static void addTaskListener(String name, Class<?> clazz) {
		taskListeners.put(name, clazz);
	}

	public static Class<?> getTaskListenerType(String name) {
		return taskListeners.get(name);
	}

	public static Set<String> getTaskListenerTypes() {
		return taskListeners.keySet();
	}

	static Map<String, Class<?>> initializeTaskListenerTypes() {
		Map<String, Class<?>> types = new java.util.HashMap<String, Class<?>>();
		String resource = SystemProperties.getString("activiti.taskListeners");
		if (StringUtils.isEmpty(resource)) {
			resource = DEFAULT_CONFIG;
		}
		if (StringUtils.isNotEmpty(resource)) {
			InputStream taskTypesStream = PropertiesUtils
					.getInputStream(resource);
			Element listenersElement = XmlUtils.parseXmlInputStream(
					taskTypesStream).getDocumentElement();
			Iterator<?> nodeTypeIterator = XmlUtils.elementIterator(
					listenersElement, "taskListeners");
			while (nodeTypeIterator.hasNext()) {
				Element nodeTypeElement = (Element) nodeTypeIterator.next();
				String elementTag = nodeTypeElement.getAttribute("element");
				String className = nodeTypeElement.getAttribute("class");
				try {
					Class<?> clazz = com.glaf.core.util.ClassUtils
							.loadClass(className);
					types.put(elementTag, clazz);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("node '" + elementTag
							+ "' will not be available. class '" + className
							+ "' couldn't be loaded");
				}
			}
		}

		String ext_resource = CustomProperties
				.getString("activiti.taskListeners");
		if (StringUtils.isNotEmpty(ext_resource)) {
			InputStream typesStream = PropertiesUtils.getInputStream(resource);
			Element listenersElement = XmlUtils
					.parseXmlInputStream(typesStream).getDocumentElement();
			Iterator<?> nodeTypeIterator = XmlUtils.elementIterator(
					listenersElement, "taskListener");
			while (nodeTypeIterator.hasNext()) {
				Element nodeTypeElement = (Element) nodeTypeIterator.next();
				String elementTag = nodeTypeElement.getAttribute("element");
				String className = nodeTypeElement.getAttribute("class");
				try {
					Class<?> clazz = com.glaf.core.util.ClassUtils
							.loadClass(className);
					types.put(elementTag, clazz);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("node '" + elementTag
							+ "' will not be available. class '" + className
							+ "' couldn't be loaded");
				}
			}
		}

		return types;
	}
}