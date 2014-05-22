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

package com.glaf.template;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.template.Template;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.Tools;

public class TemplateReader {

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

	@SuppressWarnings("unchecked")
	public Map<String, Template> getTemplates(InputStream inputStream) {
		Map<String, Template> dataMap = new java.util.HashMap<String, Template>();
		Element root = this.getRootElement(inputStream);
		List<?> templates = root.elements("template");
		if (templates != null && templates.size() > 0) {
			Iterator<?> iterator = templates.iterator();
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				Template template = new Template();

				List<Element> elems = element.elements();
				if (elems != null && !elems.isEmpty()) {
					Map<String, Object> rowMap = new java.util.HashMap<String, Object>();
					for (Element em : elems) {
						rowMap.put(em.getName(), em.getStringValue());
					}
					Tools.populate(template, rowMap);
				}

				String text = element.elementText("text");
				String name = element.elementText("name");
				String title = element.elementText("title");
				String templateId = element.attributeValue("id");
				String dataFile = element.elementText("dataFile");
				String moduleId = element.elementText("moduleId");
				String moduleName = element.elementText("moduleName");
				String callbackUrl = element.elementText("callbackUrl");
				String description = element.elementText("description");
				String language = element.elementText("language");
				String objectId = element.elementText("objectId");
				String objectValue = element.elementText("objectValue");
				String _fileType = element.elementText("fileType");

				template.setLanguage(language);

				int fileType = 0;
				if (StringUtils.isNumeric(_fileType)) {
					fileType = Integer.parseInt(_fileType);
				}
				if (dataFile.endsWith(".java")) {
					fileType = 50;
				} else if (dataFile.endsWith(".jsp")) {
					fileType = 51;
				} else if (dataFile.endsWith(".ftl")) {
					fileType = 52;
					template.setLanguage("freemarker");
				} else if (dataFile.endsWith(".vm")) {
					fileType = 54;
					template.setLanguage("velocity");
				} else if (dataFile.endsWith(".xml")) {
					fileType = 60;
				} else if (dataFile.endsWith(".htm")
						|| dataFile.endsWith(".html")) {
					fileType = 80;
				}

				template.setTemplateType(FileUtils.getFileExt(dataFile));

				if (StringUtils.isEmpty(text)) {
					String filename = SystemProperties.getConfigRootPath()
							+ dataFile;
					File file = new File(filename);
					template.setLastModified(file.lastModified());
					template.setFileSize(file.length());
					byte[] data = FileUtils.getBytes(file);
					template.setData(data);
				}
				if (template.getData() == null || template.getFileSize() == 0) {
					throw new RuntimeException(" template content is null ");
				}

				template.setContent(text);
				template.setDataFile(dataFile);
				template.setTitle(title);
				template.setName(name);
				if (StringUtils.isNotEmpty(name)) {
					template.setName(name);
				} else {
					template.setName(title);
				}
				template.setFileType(fileType);
				template.setCallbackUrl(callbackUrl);
				template.setDescription(description);
				template.setTemplateId(templateId);

				template.setModuleId(moduleId);
				template.setModuleName(moduleName);
				template.setObjectId(objectId);
				template.setObjectValue(objectValue);

				dataMap.put(templateId, template);
			}
		}

		return dataMap;
	}

	@SuppressWarnings("unchecked")
	public List<Template> readTemplates(InputStream inputStream) {
		List<Template> templates = new java.util.ArrayList<Template>();
		Element root = this.getRootElement(inputStream);
		List<Element> elements = root.elements("template");
		if (elements != null && !elements.isEmpty()) {
			for (Element element : elements) {
				Template tpl = new Template();
				tpl.setTemplateId(element.attributeValue("id"));
				tpl.setName(element.attributeValue("name"));
				tpl.setDescription(element.attributeValue("description"));
				List<Element> elems = element.elements();
				if (elems != null && !elems.isEmpty()) {
					Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
					for (Element em : elems) {
						dataMap.put(em.getName(), em.getStringValue());
					}
					Tools.populate(tpl, dataMap);
				}
				if (StringUtils.isEmpty(tpl.getContent())) {
					if (StringUtils.isNotEmpty(tpl.getDataFile())) {
						String filename = SystemProperties.getConfigRootPath()
								+ "/" + tpl.getDataFile();
						try {
							tpl.setContent(FileUtils.readFile(filename));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				templates.add(tpl);
			}
		}

		return templates;
	}

}
