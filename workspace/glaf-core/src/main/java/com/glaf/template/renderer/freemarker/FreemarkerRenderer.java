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

package com.glaf.template.renderer.freemarker;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.template.Template;
import com.glaf.template.engine.FreemarkerTemplateEngine;
import com.glaf.template.renderer.Renderer;

public class FreemarkerRenderer implements Renderer {

	private final static Log log = LogFactory.getLog(FreemarkerRenderer.class);

	private Template renderTemplate = null;

	private freemarker.template.Template t = null;

	private Exception parseException = null;

	public FreemarkerRenderer(Template template) throws Exception {
		this.renderTemplate = template;
		try {
			t = FreemarkerTemplateEngine.getTemplate(template, "UTF-8");
		} catch (Exception ex) {
			log.error(
					"Unknown exception creatting renderer for "
							+ template.getTemplateId(), ex);
			throw ex;
		}
	}

	public void render(Map<String, Object> model, Writer out) {
		try {
			if (parseException != null) {
				Map<String, Object> context = new java.util.HashMap<String, Object>();
				context.put("exception", parseException);
				context.put("exceptionSource", renderTemplate.getTemplateId());
				t.process(context, out);
				return;
			}
			model.put("currentDate", new java.util.Date());
			log.debug(model);
			StringWriter writer = new StringWriter();
			long startTime = System.currentTimeMillis();
			t.process(model, writer);
			writer.flush();
			writer.close();
			out.write(writer.toString());
			long endTime = System.currentTimeMillis();
			long renderTime = (endTime - startTime);
			log.debug("Rendered [" + renderTemplate.getTemplateId() + "] in "
					+ renderTime + " milliseconds");
			// log.debug(out.toString());
		} catch (Exception ex) {
			throw new RuntimeException("Error during rendering", ex);
		}
	}

}