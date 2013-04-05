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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScriptType", propOrder = { "expression", "property" })
public class ScriptType {

	protected String expression;

	protected List<PropertyType> property;

	@XmlAttribute(name = "lang")
	protected String lang;

	@XmlAttribute(name = "type")
	protected String type;

	@XmlAttribute(name = "runat")
	protected String runat;

	public String getExpression() {
		return expression;
	}

	public String getLang() {
		return lang;
	}

	public List<PropertyType> getProperty() {
		if (property == null) {
			property = new ArrayList<PropertyType>();
		}
		return this.property;
	}

	public String getRunat() {
		return runat;
	}

	public String getType() {
		return type;
	}

	public void setExpression(String value) {
		this.expression = value;
	}

	public void setLang(String value) {
		this.lang = value;
	}

	public void setRunat(String value) {
		this.runat = value;
	}

	public void setType(String value) {
		this.type = value;
	}

}