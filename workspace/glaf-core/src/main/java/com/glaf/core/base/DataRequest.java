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
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataRequest implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int page;

	private int pageSize;

	private int take;

	private int skip;

	private List<SortDescriptor> sort;

	private List<GroupDescriptor> group;

	private List<AggregateDescriptor> aggregate;

	private HashMap<String, Object> data;

	private FilterDescriptor filter;

	public DataRequest() {
		this.filter = new FilterDescriptor();
		this.data = new HashMap<String, Object>();
	}

	public static class AggregateDescriptor {
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
			return ToStringBuilder.reflectionToString(this);
		}
	}

	public static class FilterDescriptor {
		private String logic;
		private List<FilterDescriptor> filters;
		private String field;
		private Object value;
		private String operator;
		private boolean ignoreCase = true;

		public FilterDescriptor() {
			this.filters = new ArrayList<FilterDescriptor>();
		}

		public String getField() {
			return this.field;
		}

		public List<FilterDescriptor> getFilters() {
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
			return ToStringBuilder.reflectionToString(this);
		}
	}

	public static class GroupDescriptor extends DataRequest.SortDescriptor {
		private List<DataRequest.AggregateDescriptor> aggregates;

		public GroupDescriptor() {
			this.aggregates = new ArrayList<DataRequest.AggregateDescriptor>();
		}

		public List<DataRequest.AggregateDescriptor> getAggregates() {
			return this.aggregates;
		}

		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}

	public static class SortDescriptor {
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
			return ToStringBuilder.reflectionToString(this);
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
		return ToStringBuilder.reflectionToString(this);
	}

}