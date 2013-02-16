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

package org.jpage.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Executor implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static int JDBC_TYPE = 0;

	public final static int HIBERNATE_TYPE = 1;

	public final static int SQLMAP_TYPE = 2;

	private String query;

	private List fields;

	private Object[] values;

	private Map params;

	private boolean cacheable;

	private String gridName;

	private int queryType;

	private int persistenceType;

	private int typesHashCode;

	private int valuesHashCode;

	public Executor() {
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public List getFields() {
		return fields;
	}

	public void setFields(List fields) {
		this.fields = fields;
	}

	public int getPersistenceType() {
		return persistenceType;
	}

	public void setPersistenceType(int persistenceType) {
		this.persistenceType = persistenceType;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public int getTypesHashCode() {
		return typesHashCode;
	}

	public void setTypesHashCode(int typesHashCode) {
		this.typesHashCode = typesHashCode;
	}

	public int getValuesHashCode() {
		return valuesHashCode;
	}

	public void setValuesHashCode(int valuesHashCode) {
		this.valuesHashCode = valuesHashCode;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public List getListValues() {
		if (values != null && values.length > 0) {
			return Arrays.asList(values);
		}
		List list = new ArrayList();
		return list;
	}

	public void setListValues(List list) {
		if (list != null && list.size() > 0) {
			values = list.toArray();
		}
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}