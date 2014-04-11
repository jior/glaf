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

package com.glaf.template.engine;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.template.Template;
import com.glaf.template.engine.freemarker.*;

public class FreemarkerTemplateEngine implements TemplateEngine {

	protected static Log logger = LogFactory
			.getLog(FreemarkerTemplateEngine.class);

	protected static Configuration configuration = null;

	static {
		try {
			configuration = (Configuration) ContextFactory
					.getBean("freemarkerConfiguration");
			configuration.setSharedVariable("block", new BlockDirective());
			configuration
					.setSharedVariable("override", new OverrideDirective());
			configuration.setSharedVariable("extends", new ExtendsDirective());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static freemarker.template.Template getTemplate(Template template,
			String encoding) {
		freemarker.template.Template t = null;
		try {
			if (StringUtils.isNotEmpty(template.getContent())) {
				logger.debug("-----------------------------------");
				Reader reader = new BufferedReader(new StringReader(
						template.getContent()));
				t = new freemarker.template.Template(template.getDataFile(),
						reader, configuration);
				t.setEncoding(encoding);
				logger.debug("build template engine " + template.getDataFile());
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return t;
	}

	public FreemarkerTemplateEngine() {

	}

	public void evaluate(String name, String content,
			Map<String, Object> context, Writer writer) {
		try {
			long startTime = System.currentTimeMillis();
			StringWriter out = new StringWriter();

			freemarker.template.Template t = freemarker.template.Template
					.getPlainTextTemplate(name, content, configuration);
			t.process(context, out);
			out.flush();
			out.close();
			String text = out.toString();
			writer.write(text);
			long endTime = System.currentTimeMillis();
			long renderTime = (endTime - startTime);

			logger.debug("Rendered [" + name + "] in " + renderTime
					+ " milliseconds");
			logger.debug(text);
		} catch (Exception ex) {
			logger.debug("error template content:" + content);
			throw new RuntimeException(ex);
		}
	}

	public void evaluate(Template template, Map<String, Object> context,
			Writer writer) {
		try {
			context.put("currentDate", new java.util.Date());
			StringWriter out = new StringWriter();
			Reader reader = new BufferedReader(new StringReader(
					template.getContent()));
			freemarker.template.Template t = new freemarker.template.Template(
					template.getDataFile(), reader, configuration);
			long startTime = System.currentTimeMillis();
			t.process(context, out);
			long endTime = System.currentTimeMillis();
			long renderTime = (endTime - startTime);

			logger.debug("Rendered [" + template.getTemplateId() + "] in "
					+ renderTime + " milliseconds");
			out.flush();
			out.close();
			String text = out.toString();
			writer.write(text);
		} catch (Exception ex) {
			logger.debug("error template:" + template.getDataFile());
			throw new RuntimeException(ex);
		}
	}

}