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
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.form.core.domain.FormDefinition;

public abstract class GraphElement implements Serializable {
	protected final transient Log logger = LogFactory
			.getLog(GraphElement.class);
	private static final long serialVersionUID = 1L;
	protected Map<String, Object> dataMap = null;
	protected Map<String, FormEvent> events = null;
	protected FormDefinition formDefinition = null;
	protected Map<String, FormProperty> properties = null;

	public GraphElement() {

	}

	public FormEvent addEvent(FormEvent event) {
		if (event == null) {
			throw new IllegalArgumentException(
					"can't add a null event to a graph element");
		}
		if (event.getType() == null) {
			throw new IllegalArgumentException(
					"can't add an event without an eventType to a graph element");
		}
		if (events == null) {
			events = new java.util.concurrent.ConcurrentHashMap<String, FormEvent>();
		}
		events.put(event.getType(), event);
		event.graphElement = this;
		return event;
	}

	public void addProperty(FormProperty formProperty) {
		if (formProperty != null
				&& StringUtils.isNotEmpty(formProperty.getName())
				&& StringUtils.isNotEmpty(formProperty.getValue())) {
			formProperty.setGraphElement(this);
			if (properties == null) {
				properties = new java.util.concurrent.ConcurrentHashMap<String, FormProperty>();
			}
			FormProperty p = properties.get(formProperty.getName());
			if (p == null) {
				p = new FormProperty();
			}
			p.setName(formProperty.getName());
			p.setValue(formProperty.getValue());
			p.setGraphElement(this);
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
			p.setGraphElement(this);
			properties.put(name, p);
		}
	}

	public Map<String, Object> getDataMap() {
		if (dataMap == null) {
			dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		}
		return dataMap;
	}

	public FormEvent getEvent(String eventType) {
		FormEvent event = null;
		if (events != null) {
			event = events.get(eventType);
		}
		return event;
	}

	public Map<String, FormEvent> getEvents() {
		if (events == null) {
			events = new java.util.concurrent.ConcurrentHashMap<String, FormEvent>();
		}
		return events;
	}

	public FormDefinition getFormDefinition() {
		return formDefinition;
	}

	public Map<String, FormProperty> getProperties() {
		if (properties == null) {
			properties = new java.util.concurrent.ConcurrentHashMap<String, FormProperty>();
		}
		return properties;
	}

	public String getProperty(String name) {
		if (properties != null) {
			FormProperty formProperty = properties.get(name);
			if (formProperty != null) {
				return formProperty.getValue();
			}
		}
		return null;
	}

	public String getProperty(String name, String defaultValue) {
		if (properties != null) {
			FormProperty formProperty = properties.get(name);
			if (formProperty != null
					&& StringUtils.isNotEmpty(formProperty.getValue())) {
				return formProperty.getValue();
			}
		}
		return defaultValue;
	}

	public abstract String[] getSupportedEventTypes();

	public Object getValue(String name) {
		return this.getDataMap().get(name);
	}

	public boolean hasEvent(String eventType) {
		boolean hasEvent = false;
		if (events != null) {
			hasEvent = events.containsKey(eventType);
		}
		return hasEvent;
	}

	public boolean hasEvents() {
		return ((events != null) && (events.size() > 0));
	}

	public FormEvent removeEvent(FormEvent event) {
		FormEvent removedEvent = null;
		if (event == null) {
			throw new IllegalArgumentException(
					"can't remove a null event from a graph element");
		}
		if (event.getType() == null) {
			throw new IllegalArgumentException(
					"can't remove an event without an eventType from a graph element");
		}
		if (events != null) {
			removedEvent = events.remove(event.getType());
			if (removedEvent != null) {
				event.graphElement = null;
			}
		}
		return removedEvent;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setFormDefinition(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}

	public void setProperties(Map<String, FormProperty> properties) {
		this.properties = properties;
	}

}