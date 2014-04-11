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

package com.glaf.core.query;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class QueryCondition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String alias;
	private String column;
	private String filter;
	private String name;
	private String stringValue;
	private String type;
	private Object value;

	public QueryCondition() {

	}

	public QueryCondition(String name, String alias, String column,
			String filter, Object value) {
		this.name = name;
		this.alias = alias;
		this.column = column;
		this.filter = filter;
		this.value = value;
	}

	public QueryCondition(String name, String alias, String column,
			String type, String filter, String stringValue, Object value) {
		this.name = name;
		this.alias = alias;
		this.column = column;
		this.type = type;
		this.filter = filter;
		this.stringValue = stringValue;
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryCondition other = (QueryCondition) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String getAlias() {
		return alias;
	}

	public String getColumn() {
		return column;
	}

	public String getFilter() {
		return filter;
	}

	public String getName() {
		return name;
	}

	public String getStringValue() {
		return stringValue;
	}

	public String getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}