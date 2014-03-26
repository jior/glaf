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

package com.glaf.template.engine.freemarker;

import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class BlockDirective implements TemplateDirectiveModel {

	@SuppressWarnings("rawtypes")
	public void execute(final Environment env,  final Map params,
			final TemplateModel[] loopVars, final TemplateDirectiveBody body)
			throws TemplateException, IOException {
		final String name = DirectiveUtils.getRequiredParam(params, "name");
		final TemplateDirectiveBodyModel overrideBody = getOverrideBody(env,
				name);
		final TemplateDirectiveBody outputBody = overrideBody == null ? body
				: overrideBody.body;
		if (outputBody != null) {
			outputBody.render(env.getOut());
		}
	}

	public String getName() {
		return "block";
	}

	private TemplateDirectiveBodyModel getOverrideBody(final Environment env,
			final String name) throws TemplateModelException {
		final TemplateDirectiveBodyModel value = (TemplateDirectiveBodyModel) env
				.getVariable(DirectiveUtils.getOverrideVariableName(name));
		return value;
	}

}