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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeType", propOrder = { "event", "property" })
public class NodeType {

	protected List<EventType> event;

	protected List<PropertyType> property;

	@XmlAttribute(name = "nodeType")
	protected String nodeType;

	@XmlAttribute(name = "rowIndex")
	protected Integer rowIndex;

	@XmlAttribute(name = "colIndex")
	protected Integer colIndex;

	@XmlAttribute(name = "rowSpan")
	protected Integer rowSpan;

	@XmlAttribute(name = "colSpan")
	protected Integer colSpan;

	@XmlAttribute(name = "x")
	protected Integer x;

	@XmlAttribute(name = "y")
	protected Integer y;

	@XmlAttribute(name = "width")
	protected Integer width;

	@XmlAttribute(name = "height")
	protected Integer height;

	@XmlAttribute(name = "fontName")
	protected String fontName;

	@XmlAttribute(name = "fontStyle")
	protected String fontStyle;

	@XmlAttribute(name = "fontSize")
	protected Integer fontSize;

	@XmlAttribute(name = "bold")
	protected Boolean bold;

	@XmlAttribute(name = "italic")
	protected Boolean italic;

	@XmlAttribute(name = "required")
	protected Boolean required;

	@XmlAttribute(name = "textAlignment")
	protected String textAlignment;

	@XmlAttribute(name = "verticalAlignment")
	protected String verticalAlignment;

	@XmlAttribute(name = "name", required = true)
	protected String name;

	@XmlAttribute(name = "column")
	protected String column;

	@XmlAttribute(name = "title")
	protected String title;

	@XmlAttribute(name = "display")
	protected String display;

	@XmlAttribute(name = "displayType")
	protected Integer displayType;

	@XmlAttribute(name = "description")
	protected String description;

	@XmlAttribute(name = "dataCode")
	protected String dataCode;

	@XmlAttribute(name = "initialValue")
	protected String initialValue;

	@XmlAttribute(name = "englishTitle")
	protected String englishTitle;

	@XmlAttribute(name = "accessLevel")
	protected Integer accessLevel;

	@XmlAttribute(name = "maxLength")
	protected Integer maxLength;

	@XmlAttribute(name = "minLength")
	protected Integer minLength;

	@XmlAttribute(name = "dataField")
	protected String dataField;

	@XmlAttribute(name = "formula")
	protected String formula;

	@XmlAttribute(name = "regex")
	protected String regex;

	@XmlAttribute(name = "pattern")
	protected String pattern;

	@XmlAttribute(name = "toolTip")
	protected String toolTip;

	@XmlAttribute(name = "expression")
	protected String expression;

	@XmlAttribute(name = "foreground")
	protected String foreground;

	@XmlAttribute(name = "background")
	protected String background;

	@XmlAttribute(name = "decoration")
	protected String decoration;

	@XmlAttribute(name = "renderer")
	protected String renderer;

	@XmlAttribute(name = "renderType")
	protected String renderType;

	@XmlAttribute(name = "queryId")
	protected String queryId;

	@XmlAttribute(name = "objectId")
	protected String objectId;

	@XmlAttribute(name = "objectValue")
	protected String objectValue;

	@XmlTransient
	protected FormDefinitionType formDefinitionType;

	public Integer getAccessLevel() {
		return accessLevel;
	}

	public String getBackground() {
		return background;
	}

	public Boolean getBold() {
		return bold;
	}

	public Integer getColIndex() {
		return colIndex;
	}

	public Integer getColSpan() {
		return colSpan;
	}

	public String getDataField() {
		return dataField;
	}

	public String getDecoration() {
		return decoration;
	}

	public String getDescription() {
		return description;
	}

	public String getDisplay() {
		return display;
	}

	public Integer getDisplayType() {
		return displayType;
	}

	public List<EventType> getEvent() {
		if (event == null) {
			event = new java.util.concurrent.CopyOnWriteArrayList<EventType>();
		}
		return this.event;
	}

	public String getExpression() {
		return expression;
	}

	public String getFontName() {
		return fontName;
	}

	public Integer getFontSize() {
		if (fontSize == null) {
			return 12;
		} else {
			return fontSize;
		}
	}

	public String getFontStyle() {
		return fontStyle;
	}

	public String getForeground() {
		return foreground;
	}

	public FormDefinitionType getFormDefinitionType() {
		return formDefinitionType;
	}

	public String getFormula() {
		return formula;
	}

	public Integer getHeight() {
		return height;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public Boolean getItalic() {
		return italic;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public String getName() {
		return name;
	}

	public String getNodeType() {
		return nodeType;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getPattern() {
		return pattern;
	}

	public List<PropertyType> getProperty() {
		if (property == null) {
			property = new java.util.concurrent.CopyOnWriteArrayList<PropertyType>();
		}
		return this.property;
	}

	public String getQueryId() {
		return queryId;
	}

	public String getRegex() {
		return regex;
	}

	public String getRenderer() {
		return renderer;
	}

	public Boolean getRequired() {
		return required;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public Integer getRowSpan() {
		return rowSpan;
	}

	public String getTextAlignment() {
		return textAlignment;
	}

	public String getTitle() {
		return title;
	}

	public String getToolTip() {
		return toolTip;
	}

	public String getVerticalAlignment() {
		return verticalAlignment;
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

	public Boolean isBold() {
		return bold;
	}

	public Boolean isItalic() {
		return italic;
	}

	public Boolean isRequired() {
		if (required == null) {
			return false;
		}
		return required;
	}

	public void setAccessLevel(Integer value) {
		this.accessLevel = value;
	}

	public void setBackground(String value) {
		this.background = value;
	}

	public void setBold(Boolean bold) {
		this.bold = bold;
	}

	public void setColIndex(Integer value) {
		this.colIndex = value;
	}

	public void setColSpan(Integer value) {
		this.colSpan = value;
	}

	public void setDataField(String value) {
		this.dataField = value;
	}

	public void setDecoration(String value) {
		this.decoration = value;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public void setDisplay(String value) {
		this.display = value;
	}

	public void setDisplayType(Integer value) {
		this.displayType = value;
	}

	public void setExpression(String value) {
		this.expression = value;
	}

	public void setFontName(String value) {
		this.fontName = value;
	}

	public void setFontSize(Integer value) {
		this.fontSize = value;
	}

	public void setFontStyle(String value) {
		this.fontStyle = value;
	}

	public void setForeground(String value) {
		this.foreground = value;
	}

	public void setFormDefinitionType(FormDefinitionType formDefinitionType) {
		this.formDefinitionType = formDefinitionType;
	}

	public void setFormula(String value) {
		this.formula = value;
	}

	public void setHeight(Integer value) {
		this.height = value;
	}

	public void setInitialValue(String value) {
		this.initialValue = value;
	}

	public void setItalic(Boolean italic) {
		this.italic = italic;
	}

	public void setMaxLength(Integer value) {
		this.maxLength = value;
	}

	public void setMinLength(Integer value) {
		this.minLength = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setNodeType(String value) {
		this.nodeType = value;
	}

	public void setObjectId(String value) {
		this.objectId = value;
	}

	public void setObjectValue(String value) {
		this.objectValue = value;
	}

	public void setPattern(String value) {
		this.pattern = value;
	}

	public void setQueryId(String value) {
		this.queryId = value;
	}

	public void setRegex(String value) {
		this.regex = value;
	}

	public void setRenderer(String value) {
		this.renderer = value;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setRowIndex(Integer value) {
		this.rowIndex = value;
	}

	public void setRowSpan(Integer value) {
		this.rowSpan = value;
	}

	public void setTextAlignment(String value) {
		this.textAlignment = value;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public void setToolTip(String value) {
		this.toolTip = value;
	}

	public void setVerticalAlignment(String value) {
		this.verticalAlignment = value;
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