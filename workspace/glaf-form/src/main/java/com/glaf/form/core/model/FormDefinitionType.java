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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormDefinitionType", propOrder = { "event", "script", "node",
		"property" })
public class FormDefinitionType {
	protected List<EventType> event;

	protected List<ScriptType> script;

	protected List<NodeType> node;

	protected List<PropertyType> property;

	@XmlAttribute(name = "packageName")
	protected String packageName;

	@XmlAttribute(name = "name", required = true)
	protected String name;

	@XmlAttribute(name = "table")
	protected String table;

	@XmlAttribute(name = "title")
	protected String title;

	@XmlAttribute(name = "englishTitle")
	protected String englishTitle;

	@XmlAttribute(name = "width")
	protected Integer width;

	@XmlAttribute(name = "height")
	protected Integer height;

	@XmlAttribute(name = "x")
	protected Integer x;

	@XmlAttribute(name = "y")
	protected Integer y;

	@XmlAttribute(name = "columns")
	protected Integer columns;

	@XmlAttribute(name = "rows")
	protected Integer rows;

	public Integer getColumns() {
		return columns;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public List<EventType> getEvent() {
		if (event == null) {
			event = new java.util.concurrent.CopyOnWriteArrayList<EventType>();
		}
		return this.event;
	}

	public Integer getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public List<NodeType> getNode() {
		if (node == null) {
			node = new java.util.concurrent.CopyOnWriteArrayList<NodeType>();
		}
		return this.node;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<PropertyType> getProperty() {
		if (property == null) {
			property = new java.util.concurrent.CopyOnWriteArrayList<PropertyType>();
		}
		return this.property;
	}

	public Integer getRows() {
		return rows;
	}

	public List<ScriptType> getScript() {
		if (script == null) {
			script = new java.util.concurrent.CopyOnWriteArrayList<ScriptType>();
		}
		return this.script;
	}

	public String getTable() {
		return table;
	}

	public String getTitle() {
		return title;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public void setColumns(Integer value) {
		this.columns = value;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public void setHeight(Integer value) {
		this.height = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setRows(Integer value) {
		this.rows = value;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public void setWidth(Integer value) {
		this.width = value;
	}

	public void setX(Integer value) {
		this.x = value;
	}

	public void setY(Integer value) {
		this.y = value;
	}

}