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

import java.util.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FieldType;
import com.glaf.form.core.domain.FormDefinition;

public class FormNode extends GraphElement implements Comparable<FormNode> {

	public static final int LIST_AND_QUERY_DISPLAY_TYPE = 4;

	public static final int LIST_DISPLAY_TYPE = 1;

	protected static final Log logger = LogFactory.getLog(FormNode.class);

	public static final int QUERY_DISPLAY_TYPE = 2;

	private static final long serialVersionUID = 1L;

	public static final String[] supportedEventTypes = new String[] {
			FormEvent.EVENTTYPE_RENDER, FormEvent.EVENTTYPE_BEFORE_RENDER,
			FormEvent.EVENTTYPE_AFTER_RENDER };

	protected Integer accessLevel = 0;
	protected String background = null;
	protected Set<FormNode> children = new HashSet<FormNode>();
	protected Integer colIndex = -1;
	protected Integer colSpan = -1;
	protected String columnName = null;
	protected String dataCode = null;
	protected String dataField = null;
	protected String description = null;
	protected String display = null;
	protected Integer displayType = 0;
	protected String expression = null;
	protected String fontName = null;
	protected Integer fontSize;
	protected String fontStyle = null;
	protected String foreground = null;
	protected String formula = null;
	protected Integer height;
	protected Integer index = 0;
	protected String initialValue = null;
	protected Boolean isBold = false;
	protected Boolean isItalic = false;
	protected Integer maxLength = 0;
	protected Integer minLength = 0;
	protected String name = null;
	protected String nodeType = null;
	protected String objectId = null;
	protected String objectValue = null;
	protected String pattern = null;
	protected String queryId = null;
	protected String regex = null;
	protected Boolean rendered = true;
	protected String renderer = null;
	protected Boolean required = false;
	protected Integer rowIndex = -1;
	protected Integer rowSpan = -1;
	protected String searchFilter = null;
	protected Integer size = 0;
	protected String styleClass = null;
	protected String textAlignment = null;
	protected String title = null;
	protected String toolTip = null;
	protected Object value = null;
	protected String verticalAlignment = null;
	protected Integer width;
	protected Integer x = -1;
	protected Integer y = -1;

	public FormNode() {

	}

	public int compareTo(FormNode o) {
		if (o == null) {
			return -1;
		}

		FormNode object = o;

		int x = this.rowIndex - object.getRowIndex();
		int y = this.colIndex - object.getColIndex();

		int ret = 0;

		if (x > 0) {
			ret = 1;
		} else if (x < 0) {
			ret = -1;
		}

		if (ret == 0) {
			if (y > 0) {
				ret = 1;
			} else if (y < 0) {
				ret = -1;
			}
		}

		return ret;
	}

	public boolean equals(Object other) {
		if (other instanceof FormNode) {
			FormNode otherKey = (FormNode) other;
			return ((name != null && name.equals(otherKey.getName())));
		}
		return false;
	}

	public Integer getAccessLevel() {
		return accessLevel;
	}

	public String getBackground() {
		return background;
	}

	public Set<FormNode> getChildren() {
		return children;
	}

	public Integer getColIndex() {
		return colIndex;
	}

	public Integer getColSpan() {
		return colSpan;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getDataCode() {
		return dataCode;
	}

	public String getDataField() {
		return dataField;
	}

	public int getDataType() {
		return FieldType.STRING_TYPE;
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

	public String getExpression() {
		return expression;
	}

	public String getFontName() {
		return fontName;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public String getFontStyle() {
		return fontStyle;
	}

	public String getForeground() {
		return foreground;
	}

	public FormDefinition getFormDefinition() {
		return formDefinition;
	}

	public String getFormula() {
		return formula;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getIndex() {
		return index;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public Boolean getIsBold() {
		return isBold;
	}

	public Boolean getIsItalic() {
		return isItalic;
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

	public String getQueryId() {
		return queryId;
	}

	public String getRegex() {
		return regex;
	}

	public Boolean getRendered() {
		return rendered;
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

	public String getSearchFilter() {
		return searchFilter;
	}

	public Integer getSize() {
		return size;
	}

	public String getStringValue() {
		if (value != null) {
			if (value instanceof Date) {
				return DateUtils.getDateTime((Date) value);
			} else {
				return value.toString();
			}
		}
		return null;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public String[] getSupportedEventTypes() {
		return supportedEventTypes;
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

	public Object getValue() {
		return value;
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

	public int hashCode() {
		return 2008061202;
	}

	public boolean isBold() {
		return isBold != null ? isBold.booleanValue() : false;
	}

	Boolean isDifferent(String name1, String name2) {
		if ((name1 != null) && (name1.equals(name2))) {
			return false;
		} else if ((name1 == null) && (name2 == null)) {
			return false;
		}
		return true;
	}

	public boolean isItalic() {
		return isItalic != null ? isItalic.booleanValue() : false;
	}

	public boolean isRendered() {
		return rendered != null ? rendered.booleanValue() : false;
	}

	public boolean isRequired() {
		return required != null ? required.booleanValue() : false;
	}

	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public void setBold(Boolean isBold) {
		this.isBold = isBold;
	}

	public void setChildren(Set<FormNode> children) {
		this.children = children;
	}

	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	public void setColSpan(Integer colSpan) {
		this.colSpan = colSpan;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public void setForeground(String foreground) {
		this.foreground = foreground;
	}

	public void setFormDefinition(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}

	public void setIsBold(Boolean isBold) {
		this.isBold = isBold;
	}

	public void setIsItalic(Boolean isItalic) {
		this.isItalic = isItalic;
	}

	public void setItalic(Boolean isItalic) {
		this.isItalic = isItalic;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public void setRendered(Boolean rendered) {
		this.rendered = rendered;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setRowSpan(Integer rowSpan) {
		this.rowSpan = rowSpan;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setTextAlignment(String textAlignment) {
		this.textAlignment = textAlignment;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}