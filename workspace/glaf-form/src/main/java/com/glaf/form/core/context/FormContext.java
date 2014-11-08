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
package com.glaf.form.core.context;

import java.util.Collection;
import java.util.Map;

import com.glaf.core.base.DataFile;
import com.glaf.core.base.DataItem;
import com.glaf.core.base.DataModel;
import com.glaf.core.security.LoginContext;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;

public class FormContext {
	protected byte[] content = null;
	protected String contextPath = null;
	protected Collection<DataFile> dataFiles = null;
	protected Map<String, DataItem> dataItemMap = new java.util.HashMap<String, DataItem>();
	protected Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
	protected String encoding = "UTF-8";
	protected FormApplication formApplication = null;
	protected FormDefinition formDefinition = null;
	protected LoginContext loginContext;
	protected DataModel dataModel = null;
	protected String resourceId = null;

	public FormContext() {

	}

	public boolean containsKey(Object key) {
		if (key != null && dataMap != null) {
			return dataMap.containsKey(key);
		}
		return false;
	}

	public Object get(String key) {
		if (key != null && dataMap != null) {
			return dataMap.get(key);
		}
		return null;
	}

	public String getActorId() {
		if (loginContext != null) {
			return loginContext.getActorId();
		}
		return null;
	}

	public byte[] getContent() {
		return content;
	}

	public String getContextPath() {
		return contextPath;
	}

	public Collection<DataFile> getDataFiles() {
		return dataFiles;
	}

	public DataItem getDataItemByCode(String code) {
		if (dataItemMap != null) {
			return dataItemMap.get(code);
		}
		return null;
	}

	public Map<String, DataItem> getDataItemMap() {
		if (dataItemMap == null) {
			dataItemMap = new java.util.HashMap<String, DataItem>();
		}
		return dataItemMap;
	}

	public String getDataItemName(String code) {
		DataItem dataItem = this.getDataItemByCode(code);
		if (dataItem != null) {
			return dataItem.getName();
		}
		return null;
	}

	public Map<String, Object> getDataMap() {
		if (dataMap == null) {
			dataMap = new java.util.HashMap<String, Object>();
		}
		return dataMap;
	}

	public DataModel getDataModel() {
		return dataModel;
	}

	public String getEncoding() {
		return encoding;
	}

	public FormApplication getFormApplication() {
		return formApplication;
	}

	public FormDefinition getFormDefinition() {
		return formDefinition;
	}

	public LoginContext getLoginContext() {
		return loginContext;
	}

	public String getResourceId() {
		return resourceId;
	}

	public String getString(String key) {
		if (key != null && dataMap != null) {
			return (String) dataMap.get(key);
		}
		return null;
	}

	public void put(String key, Object value) {
		if (dataMap == null) {
			dataMap = new java.util.HashMap<String, Object>();
		}
		if (key != null && value != null) {
			dataMap.put(key, value);
		}
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void setDataFiles(Collection<DataFile> dataFiles) {
		this.dataFiles = dataFiles;
	}

	public void setDataItemMap(Map<String, DataItem> dataItemMap) {
		this.dataItemMap = dataItemMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setFormApplication(FormApplication formApplication) {
		this.formApplication = formApplication;
	}

	public void setFormDefinition(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}

	public void setLoginContext(LoginContext loginContext) {
		this.loginContext = loginContext;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}