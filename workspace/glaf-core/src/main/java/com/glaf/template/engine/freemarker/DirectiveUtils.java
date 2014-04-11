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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

@SuppressWarnings("rawtypes")
public class DirectiveUtils {

	public static String BLOCK = "__ftl_override__";

	public static void exposeRapidMacros(Configuration conf) {
		conf.setSharedVariable(new BlockDirective().getName(),
				new BlockDirective());
		conf.setSharedVariable(new ExtendsDirective().getName(),
				new ExtendsDirective());
		conf.setSharedVariable(new OverrideDirective().getName(),
				new OverrideDirective());
	}

	public static String getOverrideVariableName(String name) {
		return BLOCK + name;
	}

	static String getParam(Map params, String key, String defaultValue)
			throws TemplateException {
		Object value = params.get(key);
		return value == null ? defaultValue : value.toString();
	}

	static String getRequiredParam(Map params, String key)
			throws TemplateException {
		Object value = params.get(key);
		if (value == null || StringUtils.isEmpty(value.toString())) {
			throw new TemplateModelException("not found required parameter:"
					+ key + " for directive");
		}
		return value.toString();
	}
}