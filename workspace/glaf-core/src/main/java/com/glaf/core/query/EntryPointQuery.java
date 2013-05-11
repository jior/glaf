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

public class EntryPointQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String moduleId;
	protected String entityEntryId;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected String entityId;
	protected List<String> entityIds;
	protected String entryKey;
	protected List<String> entryKeys;
	protected String name;
	protected List<String> names;
	protected String value;
	protected List<String> values;

	public EntryPointQuery() {

	}

	public EntryPointQuery moduleId(String moduleId) {
		if (moduleId == null) {
			throw new RuntimeException("moduleId is null");
		}
		this.moduleId = moduleId;
		return this;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public EntryPointQuery entityEntryId(String entityEntryId) {
		if (entityEntryId == null) {
			throw new RuntimeException("entityEntryId is null");
		}
		this.entityEntryId = entityEntryId;
		return this;
	}

	public EntryPointQuery entityId(String entityId) {
		if (entityId == null) {
			throw new RuntimeException("entityId is null");
		}
		this.entityId = entityId;
		return this;
	}

	public EntryPointQuery entityIds(List<String> entityIds) {
		if (entityIds == null) {
			throw new RuntimeException("entityIds is empty ");
		}
		this.entityIds = entityIds;
		return this;
	}

	public EntryPointQuery entryKey(String entryKey) {
		if (entryKey == null) {
			throw new RuntimeException("entryKey is null");
		}
		this.entryKey = entryKey;
		return this;
	}

	public EntryPointQuery entryKeys(List<String> entryKeys) {
		if (entryKeys == null) {
			throw new RuntimeException("entryKeys is empty ");
		}
		this.entryKeys = entryKeys;
		return this;
	}

	public String getEntityEntryId() {
		return entityEntryId;
	}

	public String getEntityId() {
		return entityId;
	}

	public List<String> getEntityIds() {
		return entityIds;
	}

	public String getEntryKey() {
		return entryKey;
	}

	public List<String> getEntryKeys() {
		return entryKeys;
	}

	public String getName() {
		return name;
	}

	public List<String> getNames() {
		return names;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public List<Long> getNodeIds() {
		return nodeIds;
	}

	public String getValue() {
		return value;
	}

	public List<String> getValues() {
		return values;
	}

	public EntryPointQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public EntryPointQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public EntryPointQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public EntryPointQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public void setEntityEntryId(String entityEntryId) {
		this.entityEntryId = entityEntryId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setEntityIds(List<String> entityIds) {
		this.entityIds = entityIds;
	}

	public void setEntryKey(String entryKey) {
		this.entryKey = entryKey;
	}

	public void setEntryKeys(List<String> entryKeys) {
		this.entryKeys = entryKeys;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<Long> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public EntryPointQuery value(String value) {
		if (value == null) {
			throw new RuntimeException("value is null");
		}
		this.value = value;
		return this;
	}

	public EntryPointQuery values(List<String> values) {
		if (values == null) {
			throw new RuntimeException("values is empty ");
		}
		this.values = values;
		return this;
	}

}