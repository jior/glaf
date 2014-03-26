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

package com.glaf.jbpm.export;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.db.GraphSession;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;

 
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.AntUtils;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.StringTools;

import com.glaf.jbpm.config.JbpmExtensionWriter;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.model.Extension;
import com.glaf.jbpm.manager.JbpmExtensionManager;

public class MxJbpmProcessExporter   {
	protected final Log logger = LogFactory.getLog(MxJbpmProcessExporter.class);

	public void addElement(Document doc, Map<String, Object> context) {
		Element root = doc.getRootElement();
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			graphSession = jbpmContext.getGraphSession();
			List<ProcessDefinition> processDefinitions = graphSession
					.findLatestProcessDefinitions();
			if (processDefinitions != null && processDefinitions.size() > 0) {
				Element element = root.addElement("processes");
				Iterator<ProcessDefinition> iterator = processDefinitions
						.iterator();
				while (iterator.hasNext()) {
					ProcessDefinition pd = iterator.next();
					Element elem = element.addElement("process");
					elem.addAttribute("name", pd.getName());
					elem.addAttribute("description", pd.getDescription());
				}
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				ex.printStackTrace();
			}
		} finally {
			Context.close(jbpmContext);
		}

		Element jbpmCfgTemplate = root.addElement("jbpm-cfg-template");

		String configPath = SystemProperties.getConfigRootPath();
		String path = configPath + "/conf/jbpm/template";
		java.io.File directory = new java.io.File(path);
		String[] filelist = directory.list();
		InputStream inputStream = null;
		SAXReader xmlReader = new SAXReader();
		for (int i = 0; i < filelist.length; i++) {
			String filename = directory.getAbsolutePath() + "/" + filelist[i];
			File file = new File(filename);
			if (file.isFile() && file.getName().endsWith(".xml")) {
				try {
					inputStream = new FileInputStream(file);
					Document doc2x = xmlReader.read(inputStream);
					Element root2x = doc2x.getRootElement();
					List<?> elements = root2x.elements("action-definition");
					Iterator<?> iterator = elements.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						elem.setParent(jbpmCfgTemplate);
						elem.setDocument(doc);
						jbpmCfgTemplate.add(elem);
					}
					inputStream.close();
					inputStream = null;
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public byte[] zipJpdl(long processDefinitionId, String encoding) {
		byte[] bytes = null;
		Document doc = null;
		String process_name = null;
		JbpmContext jbpmContext = null;
		Map<String, InputStream> zipMap = new java.util.concurrent.ConcurrentHashMap<String, InputStream>();
		try {

			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			ProcessDefinition processDefinition = jbpmContext.getGraphSession()
					.getProcessDefinition(processDefinitionId);
			if (processDefinition != null
					&& processDefinition.getFileDefinition() != null) {
				process_name = processDefinition.getName();
				FileDefinition fileDefinition = processDefinition
						.getFileDefinition();
				if (fileDefinition.getInputStreamMap() != null) {
					zipMap.putAll(fileDefinition.getInputStreamMap());
					byte[] gpdBytes = fileDefinition.getBytes("gpd.xml");
					if (gpdBytes != null) {
						try {
							Document doc2 = Dom4jUtils
									.toDocument(fileDefinition
											.getInputStream("gpd.xml"));
							if (doc2 != null) {
							}
						} catch (Exception ex) {
							try {
								String xml = new String(gpdBytes);
								xml = StringTools.replaceIgnoreCase(xml,
										"UTF-8", "GBK");
								InputStream inputStream = new ByteArrayInputStream(
										xml.getBytes("GBK"));
								Document doc2x = Dom4jUtils
										.toDocument(inputStream);
								inputStream.close();
								inputStream = new ByteArrayInputStream(
										Dom4jUtils.getBytesFromPrettyDocument(
												doc2x, "GBK"));
								zipMap.put("gpd.xml", inputStream);
							} catch (Exception ex3) {
								throw new RuntimeException(ex3);
							}
						}
					}
				}
			}
			JbpmExtensionManager jbpmExtensionManager = ProcessContainer
					.getContainer().getJbpmExtensionManager();
			List<Extension> extensions = jbpmExtensionManager.getExtensions(
					jbpmContext, process_name);
			if (extensions != null && extensions.size() > 0) {
				JbpmExtensionWriter writer = new JbpmExtensionWriter();
				doc = writer.write(extensions);
				byte[] cfgBytes = Dom4jUtils.getBytesFromPrettyDocument(doc,
						encoding);
				InputStream cfgStream = new BufferedInputStream(
						new ByteArrayInputStream(cfgBytes));
				zipMap.put("process.cfg.xml", cfgStream);
			}
		} catch (JbpmException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			Context.close(jbpmContext);
		}

		logger.debug(zipMap.keySet());

		bytes = AntUtils.getZipStream(zipMap);
		zipMap.clear();
		zipMap = null;

		return bytes;
	}

	public byte[] zipJpdl(String process_name, String encoding) {
		if (StringUtils.isEmpty(process_name)) {
			return null;
		}
		byte[] bytes = null;
		long processDefinitionId = 0;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			ProcessDefinition processDefinition = jbpmContext.getGraphSession()
					.findLatestProcessDefinition(process_name);
			if (processDefinition != null
					&& processDefinition.getFileDefinition() != null) {
				processDefinitionId = processDefinition.getId();
			}
		} catch (JbpmException ex) {
			throw ex;
		} finally {
			Context.close(jbpmContext);
		}
		if (processDefinitionId > 0) {
			bytes = this.zipJpdl(processDefinitionId, encoding);
		}
		return bytes;
	}

}