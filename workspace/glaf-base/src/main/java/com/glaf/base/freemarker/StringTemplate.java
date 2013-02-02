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

package com.glaf.base.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StringTemplate {

	private static final Configuration cfg = new Configuration();

	public static String convert(Map root, String source) {
		if (source == null) {
			return null;
		}

		String rst = "";
		try {

			cfg.setTemplateLoader(new StringTemplateLoader(source));
			cfg.setDefaultEncoding("UTF-8");

			Template template = cfg.getTemplate("");

			StringWriter writer = new StringWriter();
			template.process(root, writer);

			writer.flush();
			rst = writer.toString();

		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
}