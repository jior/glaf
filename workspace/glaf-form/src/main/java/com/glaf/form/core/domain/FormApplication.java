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
package com.glaf.form.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.form.core.util.FormApplicationJsonFactory;

@Entity
@Table(name = "FORM_APPLICATION")
public class FormApplication implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Basic
	@Column(name = "ARCHIVESNODE_")
	protected String archivesNode;

	@Basic
	@Column(name = "AUDITUPLOADFLAG_")
	protected String auditUploadFlag;

	@Basic
	@Column(name = "AUTOARCHIVESFLAG_")
	protected String autoArchivesFlag;

	@Basic
	@Column(name = "CREATEBY_", updatable = false)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_", updatable = false)
	protected Date createDate;

	@Basic
	@Column(name = "DESCRIPTION_")
	protected String description;

	@Basic
	@Column(name = "DOCREQUIREDFLAG_")
	protected String docRequiredFlag;

	@Basic
	@Column(name = "FORMNAME_")
	protected String formName;

	@Basic
	@Column(name = "FORMRENDERERTYPE_")
	protected String formRendererType;

	@Basic
	@Column(name = "LINKCONTROLLERNAME_")
	protected String linkControllerName;

	@Basic
	@Column(name = "LINKTEMPLATEID_")
	protected String linkTemplateId;

	@Basic
	@Column(name = "LISTCONTROLLERNAME_")
	protected String listControllerName;

	@Basic
	@Column(name = "LISTTEMPLATEID_")
	protected String listTemplateId;

	@Basic
	@Column(name = "MANUALROUTEFLAG_")
	protected String manualRouteFlag;

	@Basic
	@Column(name = "NODEID_")
	protected Long nodeId;

	@Basic
	@Column(name = "OBJECTID_")
	protected String objectId;

	@Basic
	@Column(name = "OBJECTVALUE_")
	protected String objectValue;

	@Basic
	@Column(name = "PROCESSNAME_")
	protected String processName;

	@Basic
	@Column(name = "PROVIDER_")
	protected String provider;

	@Basic
	@Column(name = "URL_")
	protected String url;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RELEASEDATE_")
	protected Date releaseDate;

	@Basic
	@Column(name = "RELEASEFLAG_")
	protected String releaseFlag;

	@Basic
	@Column(name = "TABLENAME_")
	protected String tableName;

	@Basic
	@Column(name = "TITLE_")
	protected String title;

	@Basic
	@Column(name = "UPLOADFLAG_")
	protected String uploadFlag;

	@Basic
	@Column(name = "DATAFIELD_")
	protected String dataField;

	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	public FormApplication() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormApplication other = (FormApplication) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getArchivesNode() {
		return archivesNode;
	}

	public String getAuditUploadFlag() {
		return auditUploadFlag;
	}

	public String getAutoArchivesFlag() {
		return autoArchivesFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getDataField() {
		return dataField;
	}

	public String getDescription() {
		return description;
	}

	public String getDocRequiredFlag() {
		return docRequiredFlag;
	}

	public String getFormName() {
		return formName;
	}

	public String getFormRendererType() {
		return formRendererType;
	}

	public String getId() {
		return id;
	}

	public String getLinkControllerName() {
		return linkControllerName;
	}

	public String getLinkTemplateId() {
		return linkTemplateId;
	}

	public String getListControllerName() {
		return listControllerName;
	}

	public String getListTemplateId() {
		return listTemplateId;
	}

	public String getManualRouteFlag() {
		return manualRouteFlag;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getProcessName() {
		return processName;
	}

	public String getProvider() {
		return provider;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public String getReleaseFlag() {
		return releaseFlag;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTitle() {
		return title;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public String getUploadFlag() {
		return uploadFlag;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FormApplication jsonToObject(JSONObject jsonObject) {
		return FormApplicationJsonFactory.jsonToObject(jsonObject);
	}

	public void setArchivesNode(String archivesNode) {
		this.archivesNode = archivesNode;
	}

	public void setAuditUploadFlag(String auditUploadFlag) {
		this.auditUploadFlag = auditUploadFlag;
	}

	public void setAutoArchivesFlag(String autoArchivesFlag) {
		this.autoArchivesFlag = autoArchivesFlag;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDocRequiredFlag(String docRequiredFlag) {
		this.docRequiredFlag = docRequiredFlag;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setFormRendererType(String formRendererType) {
		this.formRendererType = formRendererType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLinkControllerName(String linkControllerName) {
		this.linkControllerName = linkControllerName;
	}

	public void setLinkTemplateId(String linkTemplateId) {
		this.linkTemplateId = linkTemplateId;
	}

	public void setListControllerName(String listControllerName) {
		this.listControllerName = listControllerName;
	}

	public void setListTemplateId(String listTemplateId) {
		this.listTemplateId = listTemplateId;
	}

	public void setManualRouteFlag(String manualRouteFlag) {
		this.manualRouteFlag = manualRouteFlag;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setReleaseFlag(String releaseFlag) {
		this.releaseFlag = releaseFlag;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUploadFlag(String uploadFlag) {
		this.uploadFlag = uploadFlag;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject toJsonObject() {
		return FormApplicationJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormApplicationJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}