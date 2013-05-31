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

package com.glaf.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.List;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.template.Template;
import com.glaf.core.xml.TemplateReader;

public class TemplateProperties {
	private static ConcurrentMap<String, Template> cache = new ConcurrentHashMap<String, Template>();

	static {
		try {
			reload();
		} catch (Exception ex) {
		}
	}

	public static Template getTemplate(String key) {
		Template value = (Template) cache.get(key);
		if (value == null) {
			value = (Template) cache.get(key.toLowerCase());
		}
		return value;
	}

	public synchronized static void reload() {
		try {
			String config = SystemProperties.getConfigRootPath()
					+ "/conf/templates";
			File directory = new File(config);
			if (directory.exists() && directory.isDirectory()) {
				TemplateReader reader = new TemplateReader();
				String[] filelist = directory.list();
				for (int i = 0; i < filelist.length; i++) {
					String filename = config + filelist[i];
					File file = new File(filename);
					if (file.isFile() && file.getName().endsWith(".xml")) {
						InputStream inputStream = new FileInputStream(file);
						List<Template> templates = reader
								.readTemplates(inputStream);
						if (templates != null) {
							for (Template tpl : templates) {
								if (tpl != null && tpl.getName() != null) {
									cache.put(tpl.getName(), tpl);
									cache.put(tpl.getName().toLowerCase(), tpl);
								}
							}
						}
						inputStream.close();
						inputStream = null;
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private TemplateProperties() {

	}

}