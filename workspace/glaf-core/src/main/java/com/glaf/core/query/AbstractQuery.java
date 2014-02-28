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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.glaf.core.util.SearchFilter;

public abstract class AbstractQuery<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected Map<String, String> columns = new java.util.concurrent.ConcurrentHashMap<String, String>();

	protected List<QueryCondition> conditions = new java.util.concurrent.CopyOnWriteArrayList<QueryCondition>();

	protected Map<String, Object> parameters = new java.util.concurrent.ConcurrentHashMap<String, Object>();

	public AbstractQuery() {

	}

	public void addColumn(String property, String columnName) {
		if (columns == null) {
			columns = new java.util.concurrent.ConcurrentHashMap<String, String>();
		}
	}

	public void addCondition(QueryCondition condition) {
		if (condition.getName() == null) {
			throw new RuntimeException("name is null");
		}
		this.addCondition(condition.getName(), condition.getAlias(),
				condition.getFilter(), condition.getValue());
	}

	public void addCondition(String name, String alias, String filter,
			Object value) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		if (value == null) {
			throw new RuntimeException("value is null");
		}

		String column = this.getColumn(name);
		if (column == null) {
			column = name;
		}

		conditions.add(new QueryCondition(name, alias, column, filter, value));
	}

	@SuppressWarnings("unchecked")
	public T equals(String name, String alias, Object value) {
		addCondition(name, alias, SearchFilter.EQUALS, value);
		return (T) this;
	}

	public String getColumn(String property) {
		if (columns != null) {
			return columns.get(property);
		}
		return null;
	}

	public Map<String, String> getColumns() {
		if (columns == null) {
			columns = new java.util.concurrent.ConcurrentHashMap<String, String>();
		}
		return columns;
	}

	public List<QueryCondition> getConditions() {
		if (conditions == null) {
			conditions = new java.util.concurrent.CopyOnWriteArrayList<QueryCondition>();
		}
		return conditions;
	}

	public Map<String, Object> getParameters() {
		if (parameters == null) {
			parameters = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		}
		return parameters;
	}

	@SuppressWarnings("unchecked")
	public T greaterThan(String name, String alias, Object value) {
		addCondition(name, alias, SearchFilter.GREATER_THAN, value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T greaterThanOrEqual(String name, String alias, Object value) {
		addCondition(name, alias, SearchFilter.GREATER_THAN_OR_EQUAL, value);
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
						String filter = null;
						if (SearchFilter.EQUALS.equals(value.toString())) {
							filter = SearchFilter.EQUALS;
						} else if (SearchFilter.NOT_EQUALS.equals(value
								.toString())) {
							filter = SearchFilter.NOT_EQUALS;
						} else if (SearchFilter.LIKE.equals(value.toString())) {
							filter = SearchFilter.LIKE;
						} else if (SearchFilter.NOT_LIKE.equals(value
								.toString())) {
							filter = SearchFilter.NOT_LIKE;
						} else if (SearchFilter.GREATER_THAN_OR_EQUAL
								.equals(value.toString())) {
							filter = SearchFilter.GREATER_THAN_OR_EQUAL;
						} else if (SearchFilter.GREATER_THAN.equals(value
								.toString())) {
							filter = SearchFilter.GREATER_THAN;
						} else if (SearchFilter.LESS_THAN_OR_EQUAL.equals(value
								.toString())) {
							filter = SearchFilter.LESS_THAN_OR_EQUAL;
						} else if (SearchFilter.LESS_THAN.equals(value
								.toString())) {
							filter = SearchFilter.LESS_THAN;
						}
						if (filter != null) {
							this.addCondition(k, "E", filter, v);
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T lessThan(String name, String alias, Object value) {
		addCondition(name, alias, SearchFilter.LESS_THAN, value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T lessThanOrEqual(String name, String alias, Object value) {
		addCondition(name, alias, SearchFilter.LESS_THAN_OR_EQUAL, value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T like(String name, String alias, String value) {
		addCondition(name, alias, SearchFilter.LIKE, value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T notEquals(String name, String alias, Object value) {
		addCondition(name, alias, SearchFilter.NOT_EQUALS, value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T notLike(String name, String alias, String value) {
		addCondition(name, alias, SearchFilter.NOT_LIKE, value);
		return (T) this;
	}

}