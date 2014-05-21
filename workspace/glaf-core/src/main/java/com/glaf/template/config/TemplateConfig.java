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

package com.glaf.template.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.ConfigFactory;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.IOUtils;
import com.glaf.template.Template;
import com.glaf.template.TemplateReader;
import com.glaf.template.service.ITemplateService;
import com.glaf.template.util.TemplateJsonFactory;

public class TemplateConfig {
	private static Log logger = LogFactory.getLog(TemplateConfig.class);

	private static ConcurrentMap<String, Template> concurrentMap = new ConcurrentHashMap<String, Template>();

	private static AtomicBoolean loading = new AtomicBoolean(false);

	private volatile static ITemplateService templateService;

	static {
		try {
			reload();
		} catch (Exception ex) {
		}
	}

	public static ITemplateService getTemplateService() {
		if (templateService == null) {
			templateService = (ITemplateService) ContextFactory
					.getBean("templateService");
		}
		return templateService;
	}

	public static Template getTemplate(String key) {
		if (key == null) {
			throw new RuntimeException(" template key is null ");
		}
		Template template = null;
		String text = ConfigFactory.getString(
				TemplateConfig.class.getSimpleName(), key.toLowerCase());
		if (StringUtils.isNotEmpty(text)) {
			logger.debug("json:" + text);
			JSONObject jsonObject = JSON.parseObject(text);
			template = TemplateJsonFactory.jsonToObject(jsonObject);
		}

		if (template == null) {
			template = concurrentMap.get(key.toLowerCase());
		}

		if (template == null) {
			if (concurrentMap.isEmpty()) {
				reload();
			}
			template = concurrentMap.get(key.toLowerCase());
		}

		if (template == null) {
			template = getTemplateService().getTemplate(key);
			if (template != null) {
				concurrentMap.put(template.getName().toLowerCase(), template);
				concurrentMap.put(template.getTemplateId().toLowerCase(),
						template);
				ConfigFactory.put(TemplateConfig.class.getSimpleName(),
						template.getName().toLowerCase(), TemplateJsonFactory
								.toJsonObject(template).toJSONString());
				ConfigFactory.put(TemplateConfig.class.getSimpleName(),
						template.getTemplateId().toLowerCase(),
						TemplateJsonFactory.toJsonObject(template)
								.toJSONString());
			}
		}

		return template;
	}

	public static void reload() {
		if (!loading.get()) {
			InputStream inputStream = null;
			try {
				loading.set(true);
				String config = SystemProperties.getConfigRootPath()
						+ "/conf/templates/";
				logger.debug("load config dir:" + config);
				File directory = new File(config);
				if (directory.exists() && directory.isDirectory()) {
					TemplateReader reader = new TemplateReader();
					String[] filelist = directory.list();
					for (int i = 0; i < filelist.length; i++) {
						String filename = config + filelist[i];
						// logger.debug("read config:" + filename);
						File file = new File(filename);
						if (file.isFile() && file.getName().endsWith(".xml")) {
							logger.debug("read config:" + filename);
							inputStream = new FileInputStream(file);
							List<Template> templates = reader
									.readTemplates(inputStream);
							if (templates != null && !templates.isEmpty()) {
								for (Template template : templates) {
									if (template.getName() != null) {
										concurrentMap.put(template.getName()
												.toLowerCase(), template);
										ConfigFactory.put(TemplateConfig.class
												.getSimpleName(), template
												.getName().toLowerCase(),
												TemplateJsonFactory
														.toJsonObject(template)
														.toJSONString());
									}
									if (template.getTemplateId() != null) {
										concurrentMap.put(template
												.getTemplateId().toLowerCase(),
												template);
										ConfigFactory.put(TemplateConfig.class
												.getSimpleName(), template
												.getTemplateId().toLowerCase(),
												TemplateJsonFactory
														.toJsonObject(template)
														.toJSONString());
									}
								}
							}
							IOUtils.closeStream(inputStream);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			} finally {
				loading.set(false);
				IOUtils.closeStream(inputStream);
			}
		}
	}

	private TemplateConfig() {

	}

}