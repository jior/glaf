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

public class FormApplicationQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected String name;
	protected List<String> names;
	protected String titleLike;
	protected String descriptionLike;
	protected List<String> formNames;
	protected String processDefinitionId;
	protected List<String> processDefinitionIds;
	protected String releaseFlag;
	protected Date releaseDateGreaterThanOrEqual;
	protected Date releaseDateLessThanOrEqual;
	protected String uploadFlag;
	protected String docRequiredFlag;
	protected String auditUploadFlag;
	protected String autoArchivesFlag;
	protected String manualRouteFlag;
	protected List<String> objectIds;
	protected List<String> objectValues;

	public FormApplicationQuery() {

	}

	public FormApplicationQuery auditUploadFlag(String auditUploadFlag) {
		if (auditUploadFlag == null) {
			throw new RuntimeException("auditUploadFlag is null");
		}
		this.auditUploadFlag = auditUploadFlag;
		return this;
	}

	public FormApplicationQuery autoArchivesFlag(String autoArchivesFlag) {
		if (autoArchivesFlag == null) {
			throw new RuntimeException("autoArchivesFlag is null");
		}
		this.autoArchivesFlag = autoArchivesFlag;
		return this;
	}

	public FormApplicationQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public FormApplicationQuery docRequiredFlag(String docRequiredFlag) {
		if (docRequiredFlag == null) {
			throw new RuntimeException("docRequiredFlag is null");
		}
		this.docRequiredFlag = docRequiredFlag;
		return this;
	}

	public FormApplicationQuery formNames(List<String> formNames) {
		if (formNames == null) {
			throw new RuntimeException("formNames is empty ");
		}
		this.formNames = formNames;
		return this;
	}

	public String getAuditUploadFlag() {
		return auditUploadFlag;
	}

	public String getAutoArchivesFlag() {
		return autoArchivesFlag;
	}

	public String getDescriptionLike() {
		return descriptionLike;
	}

	public String getDocRequiredFlag() {
		return docRequiredFlag;
	}

	public List<String> getFormNames() {
		return formNames;
	}

	public String getManualRouteFlag() {
		return manualRouteFlag;
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

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public List<String> getProcessDefinitionIds() {
		return processDefinitionIds;
	}

	public Date getReleaseDateGreaterThanOrEqual() {
		return releaseDateGreaterThanOrEqual;
	}

	public Date getReleaseDateLessThanOrEqual() {
		return releaseDateLessThanOrEqual;
	}

	public String getReleaseFlag() {
		return releaseFlag;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public String getUploadFlag() {
		return uploadFlag;
	}

	public FormApplicationQuery manualRouteFlag(String manualRouteFlag) {
		if (manualRouteFlag == null) {
			throw new RuntimeException("manualRouteFlag is null");
		}
		this.manualRouteFlag = manualRouteFlag;
		return this;
	}

	public FormApplicationQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormApplicationQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public FormApplicationQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public FormApplicationQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public FormApplicationQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public FormApplicationQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public FormApplicationQuery processDefinitionId(String processDefinitionId) {
		if (processDefinitionId == null) {
			throw new RuntimeException("processDefinitionId is null");
		}
		this.processDefinitionId = processDefinitionId;
		return this;
	}

	public FormApplicationQuery processDefinitionIds(
			List<String> processDefinitionIds) {
		if (processDefinitionIds == null) {
			throw new RuntimeException("processDefinitionIds is empty ");
		}
		this.processDefinitionIds = processDefinitionIds;
		return this;
	}

	public FormApplicationQuery releaseDateGreaterThanOrEqual(
			Date releaseDateGreaterThanOrEqual) {
		if (releaseDateGreaterThanOrEqual == null) {
			throw new RuntimeException("releaseDate is null");
		}
		this.releaseDateGreaterThanOrEqual = releaseDateGreaterThanOrEqual;
		return this;
	}

	public FormApplicationQuery releaseDateLessThanOrEqual(
			Date releaseDateLessThanOrEqual) {
		if (releaseDateLessThanOrEqual == null) {
			throw new RuntimeException("releaseDate is null");
		}
		this.releaseDateLessThanOrEqual = releaseDateLessThanOrEqual;
		return this;
	}

	public FormApplicationQuery releaseFlag(String releaseFlag) {
		if (releaseFlag == null) {
			throw new RuntimeException("releaseFlag is null");
		}
		this.releaseFlag = releaseFlag;
		return this;
	}

	public void setAuditUploadFlag(String auditUploadFlag) {
		this.auditUploadFlag = auditUploadFlag;
	}

	public void setAutoArchivesFlag(String autoArchivesFlag) {
		this.autoArchivesFlag = autoArchivesFlag;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setDocRequiredFlag(String docRequiredFlag) {
		this.docRequiredFlag = docRequiredFlag;
	}

	public void setFormNames(List<String> formNames) {
		this.formNames = formNames;
	}

	public void setManualRouteFlag(String manualRouteFlag) {
		this.manualRouteFlag = manualRouteFlag;
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

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public void setProcessDefinitionIds(List<String> processDefinitionIds) {
		this.processDefinitionIds = processDefinitionIds;
	}

	public void setReleaseDateGreaterThanOrEqual(
			Date releaseDateGreaterThanOrEqual) {
		this.releaseDateGreaterThanOrEqual = releaseDateGreaterThanOrEqual;
	}

	public void setReleaseDateLessThanOrEqual(Date releaseDateLessThanOrEqual) {
		this.releaseDateLessThanOrEqual = releaseDateLessThanOrEqual;
	}

	public void setReleaseFlag(String releaseFlag) {
		this.releaseFlag = releaseFlag;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setUploadFlag(String uploadFlag) {
		this.uploadFlag = uploadFlag;
	}

	public FormApplicationQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public FormApplicationQuery uploadFlag(String uploadFlag) {
		if (uploadFlag == null) {
			throw new RuntimeException("uploadFlag is null");
		}
		this.uploadFlag = uploadFlag;
		return this;
	}

}