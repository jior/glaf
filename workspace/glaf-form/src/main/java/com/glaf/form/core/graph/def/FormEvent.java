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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class FormEvent implements Serializable {
	public static final String EVENTTYPE_AFTER_RENDER = "after-render";
	public static final String EVENTTYPE_BEFORE_RENDER = "before-render";
	public static final String EVENTTYPE_RENDER = "render";
	private static final long serialVersionUID = 1L;

	protected GraphElement graphElement = null;
	protected Map<String, FormProperty> properties = null;
	protected List<FormScript> scripts = null;
	protected String type = null;

	public FormEvent() {

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

	public FormScript addScript(FormScript script) {
		if (script == null) {
			throw new IllegalArgumentException(
					"can't add a null script to an event");
		}
		if (scripts == null) {
			scripts = new java.util.concurrent.CopyOnWriteArrayList<FormScript>();
		}
		scripts.add(script);
		script.setFormEvent(this);
		return script;
	}

	public GraphElement getGraphElement() {
		return graphElement;
	}

	public Map<String, FormProperty> getProperties() {
		if (properties == null) {
			properties = new java.util.concurrent.ConcurrentHashMap<String, FormProperty>();
		}
		return properties;
	}

	public List<FormScript> getScripts() {
		return scripts;
	}

	public String getType() {
		return type;
	}

	public boolean hasScripts() {
		return ((scripts != null) && (scripts.size() > 0));
	}

	public void removeScript(FormScript script) {
		if (script == null) {
			throw new IllegalArgumentException(
					"can't remove a null script from an event");
		}
		if (scripts != null) {
			if (scripts.remove(script)) {
				script.setFormEvent(null);
			}
		}
	}

	public void setGraphElement(GraphElement graphElement) {
		this.graphElement = graphElement;
	}

	public void setProperties(Map<String, FormProperty> properties) {
		this.properties = properties;
	}

	public void setScripts(List<FormScript> scripts) {
		this.scripts = scripts;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}

}