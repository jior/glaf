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

package com.glaf.template.engine.pipeline;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;

@SuppressWarnings("unchecked")
public class FreemarkerPipeline implements Pipeline {

	private Configuration conf;

	private int bufferSize = DEFAULT_PIPELINE_BUFFER_SIZE;

	public FreemarkerPipeline() {
	}

	public FreemarkerPipeline(Configuration conf) {
		setConfiguration(conf);
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public Configuration getConfiguration() {
		return conf;
	}

	public Writer pipeline(String pipeTemplates, Map<String, Object> model, Writer writer)
			throws PipeException {
		return pipeline(pipeTemplates, (Object) model, writer);
	}

	public Writer pipeline(String pipeTemplates[], Object rootMap, Writer writer)
			throws PipeException {
		try {
			Map<String, String> globalContext = new java.util.HashMap<String, String>();
			for (int i = 0; i < pipeTemplates.length; i++) {
				String templateName = pipeTemplates[i];
				Template template = conf.getTemplate(templateName);
				if (i == pipeTemplates.length - 1) {
					Environment env = template.createProcessingEnvironment(
							rootMap, writer);
					env.getCurrentNamespace().putAll(globalContext);
					env.process();
				} else {
					Writer tempOutput = new StringWriter(bufferSize);
					Environment env = template.createProcessingEnvironment(
							rootMap, tempOutput);
					env.getCurrentNamespace().putAll(globalContext);
					env.process();
					globalContext.putAll(env.getCurrentNamespace().toMap());
					globalContext.put(Pipeline.PIPELINE_CONTENT_VAR_NAME,
							tempOutput.toString());
				}
			}
			return writer;
		} catch (Exception e) {
			throw new PipeException(
					"process FreeMarker template occer exception,pipeTemplates:"
							+ StringUtils.join(pipeTemplates, " | "), e);
		}
	}

	public Writer pipeline(String pipeTemplates, Object rootMap, Writer writer) {
		return pipeline(com.glaf.core.util.StringTools.splitToArray(
				pipeTemplates, Pipeline.PIPELINE_TEMPLATE_SEPERATORS), rootMap,
				writer);
	}

	public Writer pipeline(String[] pipeTemplates, Map<String, Object> model, Writer writer)
			throws PipeException {
		return pipeline(pipeTemplates, (Object) model, writer);
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public void setConfiguration(Configuration conf) {
		this.conf = conf;
	}

}