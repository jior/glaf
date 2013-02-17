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

package com.glaf.base.query;

import java.util.*;
import java.util.Map.Entry;

public abstract class AbstractQuery<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected List<QueryCondition> conditions = new ArrayList<QueryCondition>();

	protected Map<String, Object> parameters = new HashMap<String, Object>();

	protected Map<String, String> columns = new HashMap<String, String>();

	public AbstractQuery() {

	}

	public void addColumn(String property, String columnName) {
		if (columns == null) {
			columns = new HashMap<String, String>();
		}
	}

	public String getColumn(String property) {
		if (columns != null) {
			return columns.get(property);
		}
		return null;
	}

	public void addCondition(String name, Object value, QueryOperator operator) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		if (value == null || isBoolean(value)) {
			// Null-values and booleans can only be used in EQUALS and
			// NOT_EQUALS
			switch (operator) {
			case GREATER_THAN:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'greater than' condition");
			case LESS_THAN:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'less than' condition");
			case GREATER_THAN_OR_EQUAL:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'greater than or equal' condition");
			case LESS_THAN_OR_EQUAL:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'less than or equal' condition");
			case LIKE:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'like' condition");
			case NOT_LIKE:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'not like' condition");
			default:
				break;
			}
		}
		String column = this.getColumn(name);
		if (column == null) {
			throw new RuntimeException(" column name'" + name + "' is null");
		}
		conditions.add(new QueryCondition(name, column, value, operator));
	}

	@SuppressWarnings("unchecked")
	public T equals(String name, Object value) {
		addCondition(name, value, QueryOperator.EQUALS);
		return (T) this;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public List<QueryCondition> getConditions() {
		return conditions;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	@SuppressWarnings("unchecked")
	public T greaterThan(String name, Object value) {
		addCondition(name, value, QueryOperator.GREATER_THAN);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T greaterThanOrEqual(String name, Object value) {
		addCondition(name, value, QueryOperator.GREATER_THAN_OR_EQUAL);
		return (T) this;
	}

	public void initQueryColumns() {

	}

	public void initQueryParameters() {
		if (parameters != null && !parameters.isEmpty()) {
			Set<Entry<String, Object>> entrySet = parameters.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (name.endsWith("_filter") && value != null) {
					String k = name.substring(0, name.lastIndexOf("_"));
					Object v = parameters.get(k);
					if (k != null && v != null) {
						QueryOperator operator = null;
						if ("=".equals(value.toString())) {
							operator = QueryOperator.EQUALS;
						} else if ("!=".equals(value.toString())) {
							operator = QueryOperator.NOT_EQUALS;
						} else if ("LIKE".equals(value.toString())) {
							operator = QueryOperator.LIKE;
						} else if ("NOT LIKE".equals(value.toString())) {
							operator = QueryOperator.NOT_LIKE;
						} else if (">=".equals(value.toString())) {
							operator = QueryOperator.GREATER_THAN_OR_EQUAL;
						} else if (">".equals(value.toString())) {
							operator = QueryOperator.GREATER_THAN;
						} else if ("<=".equals(value.toString())) {
							operator = QueryOperator.LESS_THAN_OR_EQUAL;
						} else if ("<".equals(value.toString())) {
							operator = QueryOperator.LESS_THAN;
						}
						if (operator != null) {
							this.addCondition(k, v, operator);
						}
					}
				}
			}
		}
	}

	private boolean isBoolean(Object value) {
		if (value == null) {
			return false;
		}
		return Boolean.class.isAssignableFrom(value.getClass())
				|| boolean.class.isAssignableFrom(value.getClass());
	}

	@SuppressWarnings("unchecked")
	public T lessThan(String name, Object value) {
		addCondition(name, value, QueryOperator.LESS_THAN);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T lessThanOrEqual(String name, Object value) {
		addCondition(name, value, QueryOperator.LESS_THAN_OR_EQUAL);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T like(String name, String value) {
		addCondition(name, value, QueryOperator.LIKE);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T notEquals(String name, Object value) {
		addCondition(name, value, QueryOperator.NOT_EQUALS);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T notLike(String name, String value) {
		addCondition(name, value, QueryOperator.NOT_LIKE);
		return (T) this;
	}

}
