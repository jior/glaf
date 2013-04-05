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
	protected String applicationName;
	protected List<String> applicationNames;
	protected String childName;
	protected List<String> childNames;
	protected List<String> objectIds;
	protected List<String> objectValues;

	public FormLinkQuery() {

	}

	public FormLinkQuery applicationName(String applicationName) {
		if (applicationName == null) {
			throw new RuntimeException("applicationName is null");
		}
		this.applicationName = applicationName;
		return this;
	}

	public FormLinkQuery applicationNames(List<String> applicationNames) {
		if (applicationNames == null) {
			throw new RuntimeException("applicationNames is empty ");
		}
		this.applicationNames = applicationNames;
		return this;
	}

	public FormLinkQuery childName(String childName) {
		if (childName == null) {
			throw new RuntimeException("childName is null");
		}
		this.childName = childName;
		return this;
	}

	public FormLinkQuery childNames(List<String> childNames) {
		if (childNames == null) {
			throw new RuntimeException("childNames is empty ");
		}
		this.childNames = childNames;
		return this;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public List<String> getApplicationNames() {
		return applicationNames;
	}

	public String getChildName() {
		return childName;
	}

	public List<String> getChildNames() {
		return childNames;
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

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setApplicationNames(List<String> applicationNames) {
		this.applicationNames = applicationNames;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public void setChildNames(List<String> childNames) {
		this.childNames = childNames;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

}