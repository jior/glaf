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


package org.jpage.jbpm.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DeployInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	private String objectId;

	private String objectName;

	private String processName;

	private String processDefinitionId;

	private String title;

	private Date deployDate;

	private long versionNo;

	private long lastModifiedTimeMillis;

	public DeployInstance() {

	}

	public Date getDeployDate() {
		return deployDate;
	}

	public void setDeployDate(Date deployDate) {
		this.deployDate = deployDate;
	}

	public long getLastModifiedTimeMillis() {
		return lastModifiedTimeMillis;
	}

	public void setLastModifiedTimeMillis(long lastModifiedTimeMillis) {
		this.lastModifiedTimeMillis = lastModifiedTimeMillis;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public int hashCode() {
		if (objectId != null) {
			return objectId.hashCode();
		}
		return 0;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof DeployInstance)) {
			return false;
		}

		final DeployInstance model = (DeployInstance) o;

		if (objectId != null ? !objectId.equals(model.getObjectId()) : model
				.getObjectId() != null) {
			return false;
		}

		return true;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
