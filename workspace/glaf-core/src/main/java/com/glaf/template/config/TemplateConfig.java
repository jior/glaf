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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.IOUtils;
import com.glaf.template.Template;
import com.glaf.template.TemplateReader;

public class TemplateConfig {
	private static Log logger = LogFactory.getLog(TemplateConfig.class);

	private static ConcurrentMap<String, Template> concurrentMap = new ConcurrentHashMap<String, Template>();

	private static AtomicBoolean loading = new AtomicBoolean(false);

	static {
		try {
			reload();
		} catch (Exception ex) {
		}
	}

	public static Template getTemplate(String key) {
		if (key == null) {
			throw new RuntimeException(" template key is null ");
		}
		Template template = concurrentMap.get(key.toLowerCase());
		if (template == null) {
			if (concurrentMap.isEmpty()) {
				reload();
			}
			template = concurrentMap.get(key.toLowerCase());
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
									}
									if (template.getTemplateId() != null) {
										concurrentMap.put(template
												.getTemplateId().toLowerCase(),
												template);
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