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
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class StringTemplateLoader implements TemplateLoader {

	private String template;

	public StringTemplateLoader(String template) {
		this.template = template;
		if (template == null) {
			this.template = "";
		}
	}

	public void closeTemplateSource(Object templateSource) throws IOException {
		((StringReader) templateSource).close();
	}

	public Object findTemplateSource(String name) throws IOException {
		return new StringReader(template);
	}

	public long getLastModified(Object templateSource) {
		return 0;
	}

	public Reader getReader(Object templateSource, String encoding)
			throws IOException {
		return (Reader) templateSource;
	}

	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(new StringTemplateLoader("欢迎：${user}"));
		cfg.setDefaultEncoding("UTF-8");

		Template template = cfg.getTemplate("");

		Map<String, Object> root = new java.util.HashMap<String, Object>();
		root.put("user", "jior huang");

		StringWriter writer = new StringWriter();
		template.process(root, writer);
		System.out.println(writer.toString());
	}
}