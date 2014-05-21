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

package com.glaf.template.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.glaf.core.util.JsonUtils;

import com.glaf.template.TemplateContainer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtils {

	private static final Configuration cfg = new Configuration();

	public static void evaluate(com.glaf.template.Template template,
			Map<String, Object> context, Writer writer) {
		if (template != null && template.getContent() != null) {
			if (template.getJson() != null) {
				Map<String, Object> jsonMap = JsonUtils.decode(template
						.getJson());
				Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key != null && value != null) {
						context.put(key, value);
					}
				}
			}
			TemplateUtils.process(context, template.getContent());
		}
	}

	public static void evaluate(String templateId, Map<String, Object> context,
			Writer writer) {
		com.glaf.template.Template template = TemplateContainer.getContainer()
				.getTemplate(templateId);
		if (template != null && template.getContent() != null) {
			if (template.getJson() != null) {
				Map<String, Object> jsonMap = JsonUtils.decode(template
						.getJson());
				Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key != null && value != null) {
						context.put(key, value);
					}
				}
			}
			TemplateUtils.process(context, template.getContent());
		}
	}

	public static String process(Map<String, Object> context, String content) {
		if (content == null) {
			return null;
		}
		String result = "";
		try {
			cfg.setTemplateLoader(new StringTemplateLoader(content));
			cfg.setDefaultEncoding("UTF-8");
			Template template = cfg.getTemplate("");
			StringWriter writer = new StringWriter();
			template.process(context, writer);
			writer.flush();
			result = writer.toString();
		} catch (TemplateException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

}