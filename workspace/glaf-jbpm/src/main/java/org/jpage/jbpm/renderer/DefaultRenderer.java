/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.renderer;

import java.util.Map;

import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.mail.JavaMailDigger;
import org.jpage.jbpm.model.MessageTemplate;

public class DefaultRenderer implements Renderer {

 

	/**
	 * ‰÷»æ
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
