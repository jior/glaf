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

package com.glaf.activiti.xml;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.activiti.model.ActivityCoordinates;
import com.glaf.activiti.model.ActivityInfo;
import com.glaf.activiti.model.UserTask;

public class BpmnXmlReader {

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

	public List<ActivityInfo> read(Element root, String processDefinitionKey) {
		List<ActivityInfo> activities = new java.util.ArrayList<ActivityInfo>();
		Element element = root.element("BPMNDiagram");
		if (element != null) {
			List<?> elements = element.elements("BPMNPlane");
			if (elements != null && !elements.isEmpty()) {
				Iterator<?> iterator = elements.iterator();
				while (iterator.hasNext()) {
					Element elem = (Element) iterator.next();
					String attr = elem.attributeValue("bpmnElement");
					if (StringUtils.equals(attr, processDefinitionKey)) {
						List<?> elems = elem.elements("BPMNShape");
						if (elems != null && !elems.isEmpty()) {
							Iterator<?> iter = elems.iterator();
							while (iter.hasNext()) {
								Element em = (Element) iter.next();
								Element e = em.element("Bounds");
								if (e != null) {
									ActivityInfo info = new ActivityInfo();
									info.setActivityId(em
											.attributeValue("bpmnElement"));
									ActivityCoordinates coord = new ActivityCoordinates();
									coord.setHeight(Double.valueOf(e
											.attributeValue("height")));
									coord.setWidth(Double.valueOf(e
											.attributeValue("width")));
									coord.setX(Double.valueOf(e
											.attributeValue("x")));
									coord.setY(Double.valueOf(e
											.attributeValue("y")));
									info.setCoordinates(coord);
									activities.add(info);
								}
							}
						}
					}
				}
			}
		}

		return activities;
	}

	public List<UserTask> readUserTasks(Element root,
			String processDefinitionKey) {
		List<UserTask> tasks = new java.util.ArrayList<UserTask>();
		List<?> elements = root.elements("process");
		if (elements != null && !elements.isEmpty()) {
			Iterator<?> iterator = elements.iterator();
			while (iterator.hasNext()) {
				Element elem = (Element) iterator.next();
				String pid = elem.attributeValue("id");
				if (!StringUtils.equals(processDefinitionKey, pid)) {
					continue;
				}
				List<?> elems = elem.elements("userTask");
				if (elems != null && !elems.isEmpty()) {
					Iterator<?> iter = elems.iterator();
					while (iter.hasNext()) {
						Element em = (Element) iter.next();
						UserTask task = new UserTask();
						task.setId(em.attributeValue("id"));
						task.setName(em.attributeValue("name"));
						tasks.add(task);
					}
				}
			}
		}

		return tasks;
	}

}