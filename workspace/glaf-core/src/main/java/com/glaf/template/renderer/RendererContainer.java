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

package com.glaf.template.renderer;

import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.MessageProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.template.Template;

import com.glaf.template.TemplateContainer;
import com.glaf.template.engine.*;
import com.glaf.core.util.ClassUtils;

public class RendererContainer {

	private static Log logger = LogFactory.getLog(RendererContainer.class);

	private static Set<RendererFactory> rendererFactories = new HashSet<RendererFactory>();

	static {
		String systemFactories = SystemProperties
				.getString("systemRendererFactories");
		String userFactories = CustomProperties
				.getString("userRendererFactories");

		if (systemFactories != null && systemFactories.trim().length() > 0) {
			RendererFactory rendererFactory = null;
			String[] rFactories = systemFactories.split(",");
			for (int i = 0; i < rFactories.length; i++) {
				try {
					Class<?> factoryClass = ClassUtils.loadClass(rFactories[i]);
					rendererFactory = (RendererFactory) factoryClass
							.newInstance();
					rendererFactories.add(rendererFactory);
				} catch (ClassCastException cce) {
					logger.error(
							"It appears that your factory does not implement "
									+ "the RendererFactory interface", cce);
				} catch (Exception e) {
					logger.error("Unable to instantiate renderer factory ["
							+ rFactories[i] + "]", e);
				}
			}
		}

		if (userFactories != null && userFactories.trim().length() > 0) {
			RendererFactory rendererFactory = null;
			String[] uFactories = userFactories.split(",");
			for (int i = 0; i < uFactories.length; i++) {
				try {
					Class<?> factoryClass = ClassUtils.loadClass(uFactories[i]);
					rendererFactory = (RendererFactory) factoryClass
							.newInstance();
					rendererFactories.add(rendererFactory);
				} catch (ClassCastException cce) {
					logger.error(
							"It appears that your factory does not implement "
									+ "the RendererFactory interface", cce);
				} catch (Exception e) {
					logger.error("Unable to instantiate renderer factory ["
							+ uFactories[i] + "]", e);
				}
			}
		}

		if (rendererFactories.size() < 1) {
			logger.warn("Failed to load any renderer factories.  "
					+ "Rendering probably won't function as you expect.");
		}

		logger.info("Renderer Container Initialized.");
	}

	public static Renderer getRenderer(Template template) {
		Renderer renderer = null;
		Iterator<RendererFactory> factories = rendererFactories.iterator();
		while (factories.hasNext()) {
			renderer = ((RendererFactory) factories.next())
					.getRenderer(template);
			if (renderer != null) {
				return renderer;
			}
		}
		throw new RuntimeException("No renderer found for template "
				+ template.getTemplateId() + "!");
	}

	public static void render(Template template, Map<String, Object> model,
			Writer writer) {
		if (StringUtils.isEmpty(template.getLanguage())) {
			template.setLanguage("freemarker");
		}
		model.put("template", template);
		Renderer renderer = getRenderer(template);
		if (renderer != null) {
			Properties properties = MessageProperties.getProperties();
			if (properties != null) {
				Iterator<?> iterator = properties.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String value = properties.getProperty(key);
					if (!model.containsKey(key) && value != null) {
						model.put(key, value);
					}
				}
			}
			renderer.render(model, writer);
		}
	}

	public static void evaluate(String templateId, Map<String, Object> context,
			Writer writer) {
		Template template = TemplateContainer.getContainer().getTemplate(
				templateId);
		evaluate(template, context, writer);
	}

	public static void evaluate(String name, String content, String language,
			Map<String, Object> context, Writer writer) {
		if (language == null) {
			language = "freemarker";
		}
		TemplateEngine engine = null;
		try {
			engine = (TemplateEngine) TemplateEngineFactory.getBean(language);
			if (engine != null) {
				logger.debug("template engine:" + engine.getClass().getName());
				engine.evaluate(name, content, context, writer);
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new RuntimeException(ex);
		}
	}

	public static void evaluate(Template template, Map<String, Object> context,
			Writer writer) {
		if (template != null && StringUtils.isNotEmpty(template.getContent())) {
			String language = template.getLanguage();
			if (language == null) {
				language = "freemarker";
			}
			context.put("template", template);

			Properties properties2 = MessageProperties.getProperties();
			if (properties2 != null) {
				Iterator<?> iterator = properties2.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String value = properties2.getProperty(key);
					if (!context.containsKey(key)) {
						context.put(key, value);
					}
				}
			}

			TemplateEngine engine = null;
			try {
				engine = (TemplateEngine) TemplateEngineFactory
						.getBean(language);
				if (engine != null) {
					engine.evaluate(template, context, writer);
				}
			} catch (Exception ex) {
				logger.debug(ex);
				throw new RuntimeException(ex);
			}
		}
	}

	private RendererContainer() {

	}

}