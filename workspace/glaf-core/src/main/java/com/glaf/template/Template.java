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

package com.glaf.template;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.JSONable;
import com.glaf.template.util.TemplateJsonFactory;

@Entity
@Table(name = "SYS_TEMPLATE")
public class Template implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TEMPLATEID_", length = 50, nullable = false)
	protected String templateId;

	@Column(name = "TEMPLATETYPE_", length = 50)
	protected String templateType;

	@Column(name = "NAME_", length = 50)
	protected String name;

	@Column(name = "TITLE_", length = 200)
	protected String title;

	@Lob
	@Column(name = "CONTENT_")
	protected String content;

	@Lob
	@Column(name = "JSON_")
	protected String json;

	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	@Column(name = "CALLBACKURL_", length = 200)
	protected String callbackUrl;

	@Column(name = "OBJECTID_", length = 50)
	protected String objectId;

	@Column(name = "OBJECTVALUE_", length = 200)
	protected String objectValue;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "LANGUAGE_", length = 50)
	protected String language;

	@Column(name = "MODULEID_", length = 50)
	protected String moduleId;

	@Column(name = "MODULENAME_", length = 50)
	protected String moduleName;

	@Column(name = "DATAFILE_", length = 200)
	protected String dataFile;

	@Column(name = "ENCODING_", length = 20)
	protected String encoding;

	@Column(name = "LOCKED_")
	protected int locked;

	@Column(name = "NODEID_")
	protected Long nodeId;

	@Column(name = "LASTMODIFIED_")
	protected long lastModified;

	@Column(name = "FILESIZE_")
	protected long fileSize;

	@Column(name = "FILETYPE_", length = 50)
	protected int fileType;

	@Column(name = "SYSTYPE_", length = 50)
	protected String sysType;

	@Transient
	protected byte[] data;

	public Template() {

	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public String getContent() {
		return content;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public byte[] getData() {
		return data;
	}

	public String getDataFile() {
		return dataFile;
	}

	public String getDescription() {
		return description;
	}

	public String getEncoding() {
		return encoding;
	}

	public long getFileSize() {
		return fileSize;
	}

	public int getFileType() {
		return fileType;
	}

	public String getJson() {
		return json;
	}

	public String getLanguage() {
		return language;
	}

	public long getLastModified() {
		return lastModified;
	}

	public int getLocked() {
		return locked;
	}

	public String getModuleId() {
		return moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getName() {
		return name;
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

	public String getSysType() {
		return sysType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getTemplateType() {
		return templateType;
	}

	public String getTitle() {
		return title;
	}

	public Template jsonToObject(JSONObject jsonObject) {
		Template model = TemplateJsonFactory.jsonToObject(jsonObject);
		return model;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = TemplateJsonFactory.toJsonObject(this);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = TemplateJsonFactory.toObjectNode(this);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}