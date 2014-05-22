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

package com.glaf.template;

import com.glaf.core.context.ContextFactory;
import com.glaf.template.config.TemplateConfig;
import com.glaf.template.service.ITemplateService;

public class TemplateContainer {
	private static class TemplateContainerHolder {
		public static TemplateContainer instance = new TemplateContainer();
	}

	private volatile static ITemplateService templateService;

	public static TemplateContainer getContainer() {
		return TemplateContainerHolder.instance;
	}

	public static ITemplateService getTemplateService() {
		if (templateService == null) {
			templateService = (ITemplateService) ContextFactory
					.getBean("templateService");
		}
		return templateService;
	}

	private TemplateContainer() {

	}

	public Template getTemplate(String templateId) {
		if (templateId == null) {
			throw new RuntimeException(" templateId is null ");
		}
		Template template = getTemplateService().getTemplate(templateId);
		if (template == null) {
			template = TemplateConfig.getTemplate(templateId);
		}
		return template;
	}

}