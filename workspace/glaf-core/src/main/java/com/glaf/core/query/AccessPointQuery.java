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

public class AccessPointQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected List<String> names;
	protected Integer accessLevel;
	protected Integer accessLevelGreaterThanOrEqual;
	protected Integer accessLevelLessThanOrEqual;
	protected List<Integer> accessLevels;
	protected String accessEntryId;
	protected List<String> accessEntryIds;

	public AccessPointQuery() {

	}

	public AccessPointQuery accessEntryId(String accessEntryId) {
		if (accessEntryId == null) {
			throw new RuntimeException("accessEntryId is null");
		}
		this.accessEntryId = accessEntryId;
		return this;
	}

	public AccessPointQuery accessEntryIds(List<String> accessEntryIds) {
		if (accessEntryIds == null) {
			throw new RuntimeException("accessEntryIds is empty ");
		}
		this.accessEntryIds = accessEntryIds;
		return this;
	}

	public AccessPointQuery accessLevel(Integer accessLevel) {
		if (accessLevel == null) {
			throw new RuntimeException("accessLevel is null");
		}
		this.accessLevel = accessLevel;
		return this;
	}

	public AccessPointQuery accessLevelGreaterThanOrEqual(
			Integer accessLevelGreaterThanOrEqual) {
		if (accessLevelGreaterThanOrEqual == null) {
			throw new RuntimeException("accessLevel is null");
		}
		this.accessLevelGreaterThanOrEqual = accessLevelGreaterThanOrEqual;
		return this;
	}

	public AccessPointQuery accessLevelLessThanOrEqual(
			Integer accessLevelLessThanOrEqual) {
		if (accessLevelLessThanOrEqual == null) {
			throw new RuntimeException("accessLevel is null");
		}
		this.accessLevelLessThanOrEqual = accessLevelLessThanOrEqual;
		return this;
	}

	public AccessPointQuery accessLevels(List<Integer> accessLevels) {
		if (accessLevels == null) {
			throw new RuntimeException("accessLevels is empty ");
		}
		this.accessLevels = accessLevels;
		return this;
	}

	public String getAccessEntryId() {
		return accessEntryId;
	}

	public List<String> getAccessEntryIds() {
		return accessEntryIds;
	}

	public Integer getAccessLevel() {
		return accessLevel;
	}

	public Integer getAccessLevelGreaterThanOrEqual() {
		return accessLevelGreaterThanOrEqual;
	}

	public Integer getAccessLevelLessThanOrEqual() {
		return accessLevelLessThanOrEqual;
	}

	public List<Integer> getAccessLevels() {
		return accessLevels;
	}

	public String getName() {
		return name;
	}

	public List<String> getNames() {
		return names;
	}

	public AccessPointQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public AccessPointQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setAccessEntryId(String accessEntryId) {
		this.accessEntryId = accessEntryId;
	}

	public void setAccessEntryIds(List<String> accessEntryIds) {
		this.accessEntryIds = accessEntryIds;
	}

	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}

	public void setAccessLevelGreaterThanOrEqual(
			Integer accessLevelGreaterThanOrEqual) {
		this.accessLevelGreaterThanOrEqual = accessLevelGreaterThanOrEqual;
	}

	public void setAccessLevelLessThanOrEqual(Integer accessLevelLessThanOrEqual) {
		this.accessLevelLessThanOrEqual = accessLevelLessThanOrEqual;
	}

	public void setAccessLevels(List<Integer> accessLevels) {
		this.accessLevels = accessLevels;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}