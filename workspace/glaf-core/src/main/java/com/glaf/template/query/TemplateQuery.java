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

package com.glaf.template.query;

import java.util.List;

import com.glaf.core.query.DataQuery;

public class TemplateQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String templateId;
	protected List<String> templateIds;
	protected String templateType;
	protected List<String> templateTypes;
	protected String descriptionLike;
	protected String name;
	protected String nameLike;
	protected String encoding;
	protected String titleLike;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected String contentLike;
	protected String dataFile;
	protected Integer fileType;
	protected String moduleId;
	protected List<String> moduleIds;
	protected String moduleNameLike;
	protected List<String> objectValues;
	protected List<String> objectIds;
	protected String language;
	protected String callbackUrlLike;
	protected List<String> createBys;

	public TemplateQuery() {

	}

	public TemplateQuery callbackUrlLike(String callbackUrlLike) {
		if (callbackUrlLike == null) {
			throw new RuntimeException("callbackUrl is null");
		}
		this.callbackUrlLike = callbackUrlLike;
		return this;
	}

	public TemplateQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public TemplateQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public TemplateQuery dataFile(String dataFile) {
		if (dataFile == null) {
			throw new RuntimeException("dataFile is null");
		}
		this.dataFile = dataFile;
		return this;
	}

	public TemplateQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public TemplateQuery encoding(String encoding) {
		if (encoding == null) {
			throw new RuntimeException("encoding is null");
		}
		this.encoding = encoding;
		return this;
	}

	public TemplateQuery fileType(Integer fileType) {
		if (fileType == null) {
			throw new RuntimeException("fileType is null");
		}
		this.fileType = fileType;
		return this;
	}

	public String getCallbackUrlLike() {
		return callbackUrlLike;
	}

	public String getContentLike() {
		return contentLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public String getDataFile() {
		return dataFile;
	}

	public String getDescriptionLike() {
		return descriptionLike;
	}

	public String getEncoding() {
		return encoding;
	}

	public Integer getFileType() {
		return fileType;
	}

	public String getLanguage() {
		return language;
	}

	public String getModuleId() {
		return moduleId;
	}

	public List<String> getModuleIds() {
		return moduleIds;
	}

	public String getModuleNameLike() {
		return moduleNameLike;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
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

	public String getTemplateId() {
		return templateId;
	}

	public List<String> getTemplateIds() {
		return templateIds;
	}

	public String getTemplateType() {
		return templateType;
	}

	public List<String> getTemplateTypes() {
		return templateTypes;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public TemplateQuery language(String language) {
		if (language == null) {
			throw new RuntimeException("language is null");
		}
		this.language = language;
		return this;
	}

	public TemplateQuery moduleId(String moduleId) {
		if (moduleId == null) {
			throw new RuntimeException("moduleId is null");
		}
		this.moduleId = moduleId;
		return this;
	}

	public TemplateQuery moduleIds(List<String> moduleIds) {
		if (moduleIds == null) {
			throw new RuntimeException("moduleIds is empty ");
		}
		this.moduleIds = moduleIds;
		return this;
	}

	public TemplateQuery moduleNameLike(String moduleNameLike) {
		if (moduleNameLike == null) {
			throw new RuntimeException("moduleName is null");
		}
		this.moduleNameLike = moduleNameLike;
		return this;
	}

	public TemplateQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public TemplateQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public TemplateQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public TemplateQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public TemplateQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public TemplateQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public void setCallbackUrlLike(String callbackUrlLike) {
		this.callbackUrlLike = callbackUrlLike;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setModuleIds(List<String> moduleIds) {
		this.moduleIds = moduleIds;
	}

	public void setModuleNameLike(String moduleNameLike) {
		this.moduleNameLike = moduleNameLike;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
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

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTemplateIds(List<String> templateIds) {
		this.templateIds = templateIds;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public void setTemplateTypes(List<String> templateTypes) {
		this.templateTypes = templateTypes;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public TemplateQuery templateId(String templateId) {
		if (templateId == null) {
			throw new RuntimeException("templateId is null");
		}
		this.templateId = templateId;
		return this;
	}

	public TemplateQuery templateIds(List<String> templateIds) {
		if (templateIds == null) {
			throw new RuntimeException("templateIds is empty ");
		}
		this.templateIds = templateIds;
		return this;
	}

	public TemplateQuery templateType(String templateType) {
		if (templateType == null) {
			throw new RuntimeException("templateType is null");
		}
		this.templateType = templateType;
		return this;
	}

	public TemplateQuery templateTypes(List<String> templateTypes) {
		if (templateTypes == null) {
			throw new RuntimeException("templateTypes is empty ");
		}
		this.templateTypes = templateTypes;
		return this;
	}

	public TemplateQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}