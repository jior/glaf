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


package org.jpage.jbpm.renderer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.context.ApplicationContext;
import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.mail.JavaMailDigger;
import org.jpage.jbpm.model.MessageTemplate;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MruCacheStorage;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerRenderer {
	private static final Log logger = LogFactory
			.getLog(FreeMarkerRenderer.class);
 

	protected static final Configuration cfg = new Configuration();

	protected static boolean isStartup = false;

	public void startup() {
		if (isStartup) {
			return;
		}
		try {
			String path = ApplicationContext.getAppPath()
					+ "/resources/templates";
			path = org.jpage.util.FileTools.getJavaFileSystemPath(path);
			System.out.println("FreeMarker load path:" + path);
			FileTemplateLoader ftl = new FileTemplateLoader(new File(path));
			ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "");
			TemplateLoader[] loaders = new TemplateLoader[] { ftl, ctl };
			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
			cfg.setCacheStorage(new MruCacheStorage(20, 250));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setTemplateLoader(mtl);
			isStartup = true;
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	/**
	 * ‰÷»æ
	 * 
	 * @param ctx
	 * @return
	 */
	public String render(RendererContext ctx) {
		if (!isStartup) {
			startup();
		}
		String content = null;
		Map variables = ctx.getVariables();
		MessageTemplate template = ctx.getMessageTemplate();
		if (variables != null) {
			variables.putAll(template.getVariables());
		} else {
			variables = template.getVariables();
		}

		String encoding = template.getEncoding();

		if (StringUtils.isBlank(encoding)) {
			encoding = "GBK";
		}

		String dataFile = template.getDataFile();
		String templateType = template.getTemplateType();
		StringWriter writer = new StringWriter();

		try {
			if (templateType != null && template.getBytes() != null) {
				if ("eml".equalsIgnoreCase(templateType)) {
					String cacheKey = "cache_template_"
							+ template.getTemplateId();
					if (CacheFactory.get(cacheKey) != null) {
						content = (String) CacheFactory.get(cacheKey);
					} else {
						JavaMailDigger digger = new JavaMailDigger();
						content = digger.getContent(template.getBytes());
						CacheFactory.put(cacheKey, content);
					}
				} else if ("html".equalsIgnoreCase(template.getTemplateType())) {
					content = new String(template.getBytes());
				}
				if (content != null) {
					Reader reader = new BufferedReader(
							new StringReader(content));
					Template t = new Template(template.getTemplateId(), reader,
							cfg, encoding);
					t.process(variables, writer);
					writer.flush();
					content = writer.toString();
				}
			} else if (dataFile != null) {
				Template t = cfg.getTemplate(dataFile, encoding);
				t.process(variables, writer);
				writer.flush();
				content = writer.toString();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} catch (TemplateException ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		}
		content = (String) DefaultExpressionEvaluator.evaluate(content,
				variables);

		return content;
	}

}
