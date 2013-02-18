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

import java.util.Map;

import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.mail.JavaMailDigger;
import org.jpage.jbpm.model.MessageTemplate;

public class DefaultRenderer implements Renderer {

 

	/**
	 * ��Ⱦ
	 * 
	 * @param ctx
	 * @return
	 */
	public String render(RendererContext ctx) {
		String content = null;
		Map variables = ctx.getVariables();
		MessageTemplate template = ctx.getMessageTemplate();
		if (variables != null) {
				variables.putAll(template.getVariables());
		} else {
			variables = template.getVariables();
		}
		if (template.getTemplateType() != null && template.getBytes() != null) {
			if ("eml".equalsIgnoreCase(template.getTemplateType())) {
				String cacheKey = "cache_template_" + template.getTemplateId();
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

			content = (String) DefaultExpressionEvaluator.evaluate(content,
					variables);
		}

		return content;
	}

}