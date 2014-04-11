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
package com.glaf.form.core.graph.def;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.form.core.context.FormContext;

public class FormScript implements Serializable {
	private static final Log log = LogFactory.getLog(FormScript.class);
	private static final long serialVersionUID = 1L;
	protected String expression = null;
	protected FormEvent formEvent = null;
	protected GraphElement graphElement = null;
	protected int index = 0;
	protected String lang = null;
	protected Map<String, FormProperty> properties = null;
	protected String runat = null;
	protected String type = null;

	public FormScript() {

	}

	public void addProperty(FormProperty formProperty) {
		if (formProperty != null
				&& StringUtils.isNotEmpty(formProperty.getName())
				&& StringUtils.isNotEmpty(formProperty.getValue())) {
			formProperty.setGraphElement(graphElement);
			if (properties == null) {
				properties = new java.util.concurrent.ConcurrentHashMap<String, FormProperty>();
			}
			FormProperty p = properties.get(formProperty.getName());
			if (p == null) {
				p = new FormProperty();
			}
			p.setName(formProperty.getName());
			p.setValue(formProperty.getValue());
			p.setGraphElement(graphElement);
			properties.put(p.getName(), p);
		}
	}

	public void addProperty(String name, String value) {
		if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
			if (properties == null) {
				properties = new java.util.concurrent.ConcurrentHashMap<String, FormProperty>();
			}
			FormProperty p = properties.get(name);
			if (p == null) {
				p = new FormProperty();
			}
			p.setName(name);
			p.setValue(value);
			p.setGraphElement(graphElement);
			properties.put(name, p);
		}
	}

	public Map<String, Object> eval(FormContext formContext) throws Exception {
		Map<String, Object> inputMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		Set<Object> outputNames = new HashSet<Object>();

		if (formContext.getDataMap() != null) {
			inputMap.putAll(formContext.getDataMap());
			outputNames.addAll(formContext.getDataMap().keySet());
		}

		Map<String, Object> outputMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		try {
			log.debug("script input: " + inputMap);

			Set<Entry<String, Object>> entrySet = outputMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String outputName = entry.getKey();
				Object outputValue = entry.getValue();
				outputMap.put(outputName, outputValue);
			}
			log.debug("script output: " + outputMap);
		} catch (Exception e) {
			log.warn("exception during evaluation of script expression", e);
			if (e.getCause() instanceof Exception) {
				throw (Exception) e.getCause();
			} else if (e.getCause() instanceof Error) {
				throw (Error) e.getCause();
			} else {
				throw e;
			}
		}
		return outputMap;
	}

	public String getExpression() {
		return expression;
	}

	public FormEvent getFormEvent() {
		return formEvent;
	}

	public GraphElement getGraphElement() {
		return graphElement;
	}

	public int getIndex() {
		return index;
	}

	public String getLang() {
		return lang;
	}

	public Map<String, FormProperty> getProperties() {
		if (properties == null) {
			properties = new java.util.concurrent.ConcurrentHashMap<String, FormProperty>();
		}
		return properties;
	}

	public String getRunat() {
		return runat;
	}

	public String getType() {
		return type;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setFormEvent(FormEvent formEvent) {
		this.formEvent = formEvent;
	}

	public void setGraphElement(GraphElement graphElement) {
		this.graphElement = graphElement;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setProperties(Map<String, FormProperty> properties) {
		this.properties = properties;
	}

	public void setRunat(String runat) {
		this.runat = runat;
	}

	public void setType(String type) {
		this.type = type;
	}

}