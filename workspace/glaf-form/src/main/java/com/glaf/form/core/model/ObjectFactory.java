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
package com.glaf.form.core.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private final static QName _Node_QNAME = new QName("", "node");
	private final static QName _FormDefinition_QNAME = new QName("",
			"form-definition");
	private final static QName _Event_QNAME = new QName("", "event");
	private final static QName _Property_QNAME = new QName("", "property");
	private final static QName _Script_QNAME = new QName("", "script");

	public ObjectFactory() {
	}

	@XmlElementDecl(namespace = "", name = "event")
	public JAXBElement<EventType> createEvent(EventType value) {
		return new JAXBElement<EventType>(_Event_QNAME, EventType.class, null,
				value);
	}

	public EventType createEventType() {
		return new EventType();
	}

	@XmlElementDecl(namespace = "", name = "form-definition")
	public JAXBElement<FormDefinitionType> createFormDefinition(
			FormDefinitionType value) {
		return new JAXBElement<FormDefinitionType>(_FormDefinition_QNAME,
				FormDefinitionType.class, null, value);
	}

	public FormDefinitionType createFormDefinitionType() {
		return new FormDefinitionType();
	}

	@XmlElementDecl(namespace = "", name = "node")
	public JAXBElement<NodeType> createNode(NodeType value) {
		return new JAXBElement<NodeType>(_Node_QNAME, NodeType.class, null,
				value);
	}

	public NodeType createNodeType() {
		return new NodeType();
	}

	@XmlElementDecl(namespace = "", name = "property")
	public JAXBElement<PropertyType> createProperty(PropertyType value) {
		return new JAXBElement<PropertyType>(_Property_QNAME,
				PropertyType.class, null, value);
	}

	public PropertyType createPropertyType() {
		return new PropertyType();
	}

	@XmlElementDecl(namespace = "", name = "script")
	public JAXBElement<ScriptType> createScript(ScriptType value) {
		return new JAXBElement<ScriptType>(_Script_QNAME, ScriptType.class,
				null, value);
	}

	public ScriptType createScriptType() {
		return new ScriptType();
	}

}