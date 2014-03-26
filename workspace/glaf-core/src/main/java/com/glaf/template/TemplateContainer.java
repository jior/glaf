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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.template.service.ITemplateService;

public class TemplateContainer {
	protected static final Log logger = LogFactory
			.getLog(TemplateContainer.class);

	private static TemplateContainer container = new TemplateContainer();

	public static TemplateContainer getContainer() {
		return container;
	}

	private volatile ITemplateService templateService;

	private TemplateContainer() {
		templateService = (ITemplateService) ContextFactory
				.getBean("templateService");
	}

	public Template getTemplate(String templateId) {
		if (templateId == null) {
			throw new RuntimeException(" templateId is null ");
		}
		Template template = templateService.getTemplate(templateId);
		return template;
	}

	public void reload() {
		templateService.installAllTemplates();
	}

}