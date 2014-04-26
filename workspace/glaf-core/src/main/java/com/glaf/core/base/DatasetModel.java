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

import java.util.*;

public class DatasetModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;

	protected String title;

	protected String sql;

	protected String queryId;

	protected boolean single;

	protected boolean foreachPerRow;

	protected Map<String, String> attributes = new HashMap<String, String>();

	protected List<String> splitList = new ArrayList<String>();

	protected List<FieldController> controllers = new ArrayList<FieldController>();

	public DatasetModel() {

	}

	public void addAttribute(String key, String value) {
		if (attributes == null) {
			attributes = new HashMap<String, String>();
		}
		attributes.put(key, value);
	}

	public void addController(FieldController c) {
		if (controllers == null) {
			controllers = new ArrayList<FieldController>();
		}
		controllers.add(c);
	}

	public void addSplit(String split) {
		if (splitList == null) {
			splitList = new ArrayList<String>();
		}
		splitList.add(split);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatasetModel other = (DatasetModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public List<FieldController> getControllers() {
		return controllers;
	}

	public String getId() {
		return id;
	}

	public String getQueryId() {
		return queryId;
	}

	public List<String> getSplitList() {
		return splitList;
	}

	public String getSql() {
		return sql;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean isForeachPerRow() {
		return foreachPerRow;
	}

	public boolean isSingle() {
		return single;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void setControllers(List<FieldController> controllers) {
		this.controllers = controllers;
	}

	public void setForeachPerRow(boolean foreachPerRow) {
		this.foreachPerRow = foreachPerRow;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public void setSplitList(List<String> splitList) {
		this.splitList = splitList;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}