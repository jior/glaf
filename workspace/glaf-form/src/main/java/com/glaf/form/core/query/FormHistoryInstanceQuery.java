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
package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.*;

public class FormHistoryInstanceQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected List<String> names;
	protected String nodeId;
	protected List<String> nodeIds;
	protected List<String> objectIds;
	protected List<String> objectValues;
	protected String contentLike;
	protected Long versionNoGreaterThanOrEqual;
	protected Long versionNoLessThanOrEqual;

	public FormHistoryInstanceQuery() {

	}

	public FormHistoryInstanceQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public String getContentLike() {
		return contentLike;
	}

	public String getName() {
		return name;
	}

	public List<String> getNames() {
		return names;
	}

	public String getNodeId() {
		return nodeId;
	}

	public List<String> getNodeIds() {
		return nodeIds;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public Long getVersionNoGreaterThanOrEqual() {
		return versionNoGreaterThanOrEqual;
	}

	public Long getVersionNoLessThanOrEqual() {
		return versionNoLessThanOrEqual;
	}

	public FormHistoryInstanceQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormHistoryInstanceQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public FormHistoryInstanceQuery nodeId(String nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public FormHistoryInstanceQuery nodeIds(List<String> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public FormHistoryInstanceQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public FormHistoryInstanceQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<String> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setVersionNoGreaterThanOrEqual(Long versionNoGreaterThanOrEqual) {
		this.versionNoGreaterThanOrEqual = versionNoGreaterThanOrEqual;
	}

	public void setVersionNoLessThanOrEqual(Long versionNoLessThanOrEqual) {
		this.versionNoLessThanOrEqual = versionNoLessThanOrEqual;
	}

	public FormHistoryInstanceQuery versionNoGreaterThanOrEqual(
			Long versionNoGreaterThanOrEqual) {
		if (versionNoGreaterThanOrEqual == null) {
			throw new RuntimeException("versionNo is null");
		}
		this.versionNoGreaterThanOrEqual = versionNoGreaterThanOrEqual;
		return this;
	}

	public FormHistoryInstanceQuery versionNoLessThanOrEqual(
			Long versionNoLessThanOrEqual) {
		if (versionNoLessThanOrEqual == null) {
			throw new RuntimeException("versionNo is null");
		}
		this.versionNoLessThanOrEqual = versionNoLessThanOrEqual;
		return this;
	}

}