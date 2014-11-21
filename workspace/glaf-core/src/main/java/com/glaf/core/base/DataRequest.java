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
package com.glaf.core.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.glaf.core.util.DateUtils;

public class DataRequest implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int page;

	private int pageSize;

	private int take;

	private int skip;

	private FilterDescriptor filter;

	private HashMap<String, Object> data;

	private List<SortDescriptor> sort;

	private List<GroupDescriptor> group;

	private List<AggregateDescriptor> aggregate;

	public DataRequest() {
		this.filter = new FilterDescriptor();
		this.data = new HashMap<String, Object>();
	}

	public static class AggregateDescriptor implements java.io.Serializable {

		private static final long serialVersionUID = 1L;

		private String field;
		private String aggregate;

		public String getAggregate() {
			return this.aggregate;
		}

		public String getField() {
			return this.field;
		}

		public void setAggregate(String aggregate) {
			this.aggregate = aggregate;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}
	}

	public static class FilterDescriptor implements java.io.Serializable {

		private static final long serialVersionUID = 1L;

		private String logic;
		private String logicValue;
		private String field;
		private String column;
		private String columnAlias = "E";
		private Object value;
		private String operator;
		private String javaType;
		private String stringValue;
		private Date dateValue;
		private Integer intValue;
		private Long longValue;
		private Double doubleValue;
		private int level = 1;
		private boolean ignoreCase = true;
		private FilterDescriptor parent;
		private List<FilterDescriptor> filters;

		public FilterDescriptor() {
			this.filters = new ArrayList<FilterDescriptor>();
		}

		public JSONObject toJSONObject() {
			JSONObject json = new JSONObject();
			if (logic != null) {
				json.put("logic", logic);
			}
			if (field != null) {
				json.put("field", field);
			}
			if (value != null) {
				json.put("value", value);
			}
			if (operator != null) {
				json.put("operator", operator);
			}

			json.put("ignoreCase", ignoreCase);

			JSONArray array = new JSONArray();

			if (filters != null && !filters.isEmpty()) {
				for (FilterDescriptor f : filters) {
					array.add(f.toJSONObject());
				}
				json.put("filters", array);
			}

			return json;
		}

		public String getColumn() {
			return column;
		}

		public void setColumn(String column) {
			this.column = column;
		}

		public String getColumnAlias() {
			if (columnAlias == null) {
				columnAlias = "E";
			}
			return columnAlias;
		}

		public void setColumnAlias(String columnAlias) {
			this.columnAlias = columnAlias;
		}

		public String getJavaType() {
			return javaType;
		}

		public void setJavaType(String javaType) {
			this.javaType = javaType;
		}

		public int getLevel() {
			if (parent != null) {
				level = parent.getLevel() + 1;
			}
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public FilterDescriptor getParent() {
			return parent;
		}

		public void setParent(FilterDescriptor parent) {
			this.parent = parent;
		}

		public String getLogicValue() {
			logicValue = "and";
			if (StringUtils.equalsIgnoreCase(logic, "or")) {
				logicValue = "or";
			}
			return logicValue;
		}

		public String getStringValue() {
			if (value != null) {
				if (value instanceof String) {
					stringValue = (String) value;
				} else {
					stringValue = value.toString();
				}
			} else {
				stringValue = null;
			}
			if (stringValue != null && stringValue.trim().length() > 0) {
				if (StringUtils.equalsIgnoreCase(operator, "startswith")) {
					stringValue = stringValue + "%";
				} else if (StringUtils.equalsIgnoreCase(operator, "endswith")) {
					stringValue = "%" + stringValue;
				} else if (StringUtils.equalsIgnoreCase(operator,
						"doesnotcontain")) {
					stringValue = "%" + stringValue + "%";
				} else if (StringUtils.equalsIgnoreCase(operator, "contains")) {
					stringValue = "%" + stringValue + "%";
				}
			}
			return stringValue;
		}

		public Date getDateValue() {
			if (value != null) {
				if (value instanceof Date) {
					dateValue = (Date) value;
				} else if (value instanceof String) {
					String dateString = (String) value;
					dateValue = DateUtils.toDate(dateString);
				} else {
					String dateString = value.toString();
					dateValue = DateUtils.toDate(dateString);
				}
			} else {
				dateValue = null;
			}
			return dateValue;
		}

		public Integer getIntValue() {
			if (value != null) {
				if (value instanceof Integer) {
					intValue = (Integer) value;
				} else if (value instanceof String) {
					String str = (String) value;
					intValue = Integer.parseInt(str);
				} else {
					String str = value.toString();
					intValue = Integer.parseInt(str);
				}
			} else {
				intValue = null;
			}
			return intValue;
		}

		public Long getLongValue() {
			if (value != null) {
				if (value instanceof Long) {
					longValue = (Long) value;
				} else if (value instanceof String) {
					String str = (String) value;
					longValue = Long.parseLong(str);
				} else {
					String str = value.toString();
					longValue = Long.parseLong(str);
				}
			} else {
				longValue = null;
			}
			return longValue;
		}

		public Double getDoubleValue() {
			if (value != null) {
				if (value instanceof Double) {
					doubleValue = (Double) value;
				} else if (value instanceof String) {
					String str = (String) value;
					doubleValue = Double.parseDouble(str);
				} else {
					String str = value.toString();
					doubleValue = Double.parseDouble(str);
				}
			} else {
				doubleValue = null;
			}
			return doubleValue;
		}

		public String getField() {
			return this.field;
		}

		public List<FilterDescriptor> getFilters() {
			if (filters != null && !filters.isEmpty()) {
				for (FilterDescriptor f : filters) {
					f.setParent(this);
				}
			}
			return this.filters;
		}

		public String getLogic() {
			return this.logic;
		}

		public String getOperator() {
			return this.operator;
		}

		public Object getValue() {
			return this.value;
		}

		public boolean isIgnoreCase() {
			return this.ignoreCase;
		}

		public void setField(String field) {
			this.field = field;
		}

		public void setIgnoreCase(boolean ignoreCase) {
			this.ignoreCase = ignoreCase;
		}

		public void setLogic(String logic) {
			this.logic = logic;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}
	}

	public static class GroupDescriptor extends DataRequest.SortDescriptor {

		private static final long serialVersionUID = 1L;
		
		private List<DataRequest.AggregateDescriptor> aggregates;

		public GroupDescriptor() {
			this.aggregates = new ArrayList<DataRequest.AggregateDescriptor>();
		}

		public List<DataRequest.AggregateDescriptor> getAggregates() {
			return this.aggregates;
		}

		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}
	}

	public static class SortDescriptor implements java.io.Serializable {

		private static final long serialVersionUID = 1L;

		private String field;
		private String dir;

		public String getDir() {
			return this.dir;
		}

		public String getField() {
			return this.field;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}
	}

	public List<AggregateDescriptor> getAggregate() {
		return this.aggregate;
	}

	public HashMap<String, Object> getData() {
		return this.data;
	}

	public FilterDescriptor getFilter() {
		return this.filter;
	}

	public List<GroupDescriptor> getGroup() {
		return this.group;
	}

	public int getPage() {
		return this.page;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getSkip() {
		return this.skip;
	}

	public List<SortDescriptor> getSort() {
		return this.sort;
	}

	public int getTake() {
		return this.take;
	}

	public void setAggregate(List<AggregateDescriptor> aggregate) {
		this.aggregate = aggregate;
	}

	public void setFilter(FilterDescriptor filter) {
		this.filter = filter;
	}

	public void setGroup(List<GroupDescriptor> group) {
		this.group = group;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public void setSort(List<SortDescriptor> sort) {
		this.sort = sort;
	}

	public void setTake(int take) {
		this.take = take;
	}

	public List<SortDescriptor> sortDescriptors() {
		List<SortDescriptor> sort = new ArrayList<SortDescriptor>();

		List<GroupDescriptor> groups = getGroup();
		List<SortDescriptor> sorts = getSort();
		if (groups != null) {
			sort.addAll(groups);
		}
		if (sorts != null) {
			sort.addAll(sorts);
		}
		return sort;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}