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

public class FormLinkQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String appId;
	protected List<String> appIds;
	protected String childAppId;
	protected List<String> childAppIds;
	protected List<String> objectIds;
	protected List<String> objectValues;

	public FormLinkQuery() {

	}

	public FormLinkQuery appId(String appId) {
		if (appId == null) {
			throw new RuntimeException("appId is null");
		}
		this.appId = appId;
		return this;
	}

	public FormLinkQuery appIds(List<String> appIds) {
		if (appIds == null) {
			throw new RuntimeException("appIds is empty ");
		}
		this.appIds = appIds;
		return this;
	}

	public FormLinkQuery childAppId(String childAppId) {
		if (childAppId == null) {
			throw new RuntimeException("childAppId is null");
		}
		this.childAppId = childAppId;
		return this;
	}

	public FormLinkQuery childAppIds(List<String> childAppIds) {
		if (childAppIds == null) {
			throw new RuntimeException("childAppIds is empty ");
		}
		this.childAppIds = childAppIds;
		return this;
	}

	public String getAppId() {
		return appId;
	}

	public List<String> getAppIds() {
		return appIds;
	}

	public String getChildAppId() {
		return childAppId;
	}

	public List<String> getChildAppIds() {
		return childAppIds;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public FormLinkQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public FormLinkQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppIds(List<String> appIds) {
		this.appIds = appIds;
	}

	public void setChildAppId(String childAppId) {
		this.childAppId = childAppId;
	}

	public void setChildAppIds(List<String> childAppIds) {
		this.childAppIds = childAppIds;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

}