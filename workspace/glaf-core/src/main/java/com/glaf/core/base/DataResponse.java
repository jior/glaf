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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataResponse implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long total;

	private List<?> data;

	private Map<String, Object> aggregates;

	public DataResponse() {

	}

	public Map<String, Object> getAggregates() {
		return this.aggregates;
	}

	public List<?> getData() {
		return this.data;
	}

	public long getTotal() {
		return this.total;
	}

	public void setAggregates(Map<String, Object> aggregates) {
		this.aggregates = aggregates;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
