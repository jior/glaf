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

package com.glaf.jbpm.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.todo.Todo;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.Tools;

public class JpdlXmlReader {
	private final static String sp = System.getProperty("line.separator");

	private static final Configuration conf = BaseConfiguration.create();

	private static int index = 1001;

	public List<String> getTaskNames(InputStream inputStream) {
		List<String> taskNames = new ArrayList<String>();
		SAXReader xmlReader = new SAXReader();
		Document doc = null;
		try {
			doc = xmlReader.read(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		Element root = doc.getRootElement();
		List<?> rows = root.elements("task-node");
		Iterator<?> iterator = rows.iterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			List<?> tasks = element.elements("task");
			Iterator<?> iter = tasks.iterator();
			while (iter.hasNext()) {
				Element elem = (Element) iter.next();
				String taskName = elem.attributeValue("name");
				if (taskNames.contains(taskName)) {
					throw new RuntimeException("task name '" + taskName
							+ "' is exists.");
				}
				taskNames.add(taskName);
			}
		}

		return taskNames;
	}

	public void checkTaskNames(Document doc) {
		List<String> taskNames = new ArrayList<String>();
		Element root = doc.getRootElement();
		List<?> rows = root.elements("task-node");
		Iterator<?> iterator = rows.iterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			List<?> tasks = element.elements("task");
			Iterator<?> iter = tasks.iterator();
			while (iter.hasNext()) {
				Element elem = (Element) iter.next();
				String taskName = elem.attributeValue("name");
				if (taskNames.contains(taskName)) {
					throw new RuntimeException("task name '" + taskName
							+ "' is exists.");
				}
				taskNames.add(taskName);
			}
		}
	}

	public List<Todo> read(InputStream inputStream) {
		List<Todo> todoList = new java.util.ArrayList<Todo>();
		SAXReader xmlReader = new SAXReader();
		int sortNo = 1;
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			String processName = root.attributeValue("name");
			String moduleName = root.elementText("description");
			List<?> rows = root.elements("task-node");
			Iterator<?> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				List<?> tasks = element.elements("task");
				Iterator<?> iter = tasks.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					String taskName = elem.attributeValue("name");
					if (taskName.startsWith("task55")) {
						continue;
					}
					Todo model = new Todo();
					model.setSortNo(sortNo++);
					model.setProcessName(processName);
					model.setModuleName(moduleName);
					model.setTitle(moduleName + " "
							+ elem.attributeValue("description") + "尚未完成！");
					model.setContent(moduleName + " "
							+ elem.attributeValue("description") + "尚未完成！");
					model.setCode(processName + "_" + taskName);
					model.setTaskName(taskName);
					model.setProvider("jbpm");
					model.setLinkType(processName + "_" + taskName);
					String detail_url = conf.get("jbpm_task_detail_url");
					String list_url = conf.get("jbpm_task_url");
					model.setLink(detail_url
							+ "&rowId=#{rowId}&x_method=view&app_name="
							+ processName);
					model.setListLink(list_url + "&taskType=running&app_name="
							+ processName);
					model.setLimitDay(2);
					model.setXa(6);
					model.setXb(6);
					todoList.add(model);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return todoList;
	}

	public void convert(File dir) {
		if (!(dir.exists() || dir.isDirectory())) {
			return;
		}
		String[] filelist = dir.list();
		for (int i = 0; i < filelist.length; i++) {
			String filename = dir.getAbsolutePath() + "/" + filelist[i];
			java.io.File file = new java.io.File(filename);
			if (file.isDirectory()) {
				this.convert(file);
			} else if (file.isFile()
					&& file.getName().equals("processdefinition.xml")) {
				List<Todo> todoList = null;
				try {
					todoList = this.read(new FileInputStream(file));
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
				if (todoList != null && todoList.size() > 0) {
					index = index + 100;
					Document doc = DocumentHelper.createDocument();
					doc.setXMLEncoding("GBK");
					Element root = doc.addElement("rows");
					Iterator<Todo> iter = todoList.iterator();
					while (iter.hasNext()) {
						Todo todo = (Todo) iter.next();
						Map<String, Object> dataMap = Tools.getDataMap(todo);
						dataMap.remove("id");
						dataMap.remove("locked");
						dataMap.remove("configFlag");
						dataMap.remove("versionNo");
						Element row = root.addElement("row");
						row.addAttribute("id", String.valueOf(index++));
						Set<Entry<String, Object>> entrySet = dataMap
								.entrySet();
						for (Entry<String, Object> entry : entrySet) {
							String key = entry.getKey();
							Object value = entry.getValue();
							if (value != null && !(value instanceof Map<?, ?>)
									&& !(value instanceof Set<?>)
									&& !(value instanceof Collection<?>)) {
								Element elem = row.addElement("property");
								elem.addAttribute("name", key);
								if (key.equals("link")
										|| key.equals("listLink")) {
									elem.addCDATA(sp + "        "
											+ value.toString());
								} else {
									elem.addAttribute("value", value.toString());
								}
							}
						}
					}
					filename = dir.getAbsolutePath() + "/" + "todo.xml";
					Dom4jUtils.savePrettyDoument(doc, filename, "GBK");
					doc = null;
					root = null;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		JpdlXmlReader reader = new JpdlXmlReader();
		reader.convert(new File(args[0]));
	}

}