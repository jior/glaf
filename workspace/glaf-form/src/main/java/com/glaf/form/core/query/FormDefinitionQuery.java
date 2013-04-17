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

public class FormDefinitionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String title;
	protected String titleLike;
	protected String descriptionLike;
	protected String formDefinitionId;
	protected String formDefinitionIdLike;
	protected List<String> formDefinitionIds;
	protected String templateName;
	protected String templateNameLike;
	protected String templateType;
	protected String templateTypeLike;
	protected List<String> templateTypes;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected List<String> objectIds;
	protected List<String> objectValues;

	public FormDefinitionQuery() {

	}

	public FormDefinitionQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public FormDefinitionQuery formDefinitionId(String formDefinitionId) {
		if (formDefinitionId == null) {
			throw new RuntimeException("formDefinitionId is null");
		}
		this.formDefinitionId = formDefinitionId;
		return this;
	}

	public FormDefinitionQuery formDefinitionIdLike(String formDefinitionIdLike) {
		if (formDefinitionIdLike == null) {
			throw new RuntimeException("formDefinitionId is null");
		}
		this.formDefinitionIdLike = formDefinitionIdLike;
		return this;
	}

	public FormDefinitionQuery formDefinitionIds(List<String> formDefinitionIds) {
		if (formDefinitionIds == null) {
			throw new RuntimeException("formDefinitionIds is empty ");
		}
		this.formDefinitionIds = formDefinitionIds;
		return this;
	}

	public String getDescriptionLike() {
		return descriptionLike;
	}

	public String getFormDefinitionId() {
		return formDefinitionId;
	}

	public String getFormDefinitionIdLike() {
		return formDefinitionIdLike;
	}

	public List<String> getFormDefinitionIds() {
		return formDefinitionIds;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
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

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateNameLike() {
		return templateNameLike;
	}

	public String getTemplateType() {
		return templateType;
	}

	public String getTemplateTypeLike() {
		return templateTypeLike;
	}

	public List<String> getTemplateTypes() {
		return templateTypes;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public FormDefinitionQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormDefinitionQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FormDefinitionQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public FormDefinitionQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public FormDefinitionQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public FormDefinitionQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public FormDefinitionQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setFormDefinitionId(String formDefinitionId) {
		this.formDefinitionId = formDefinitionId;
	}

	public void setFormDefinitionIdLike(String formDefinitionIdLike) {
		this.formDefinitionIdLike = formDefinitionIdLike;
	}

	public void setFormDefinitionIds(List<String> formDefinitionIds) {
		this.formDefinitionIds = formDefinitionIds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
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

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setTemplateNameLike(String templateNameLike) {
		this.templateNameLike = templateNameLike;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public void setTemplateTypeLike(String templateTypeLike) {
		this.templateTypeLike = templateTypeLike;
	}

	public void setTemplateTypes(List<String> templateTypes) {
		this.templateTypes = templateTypes;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public FormDefinitionQuery templateName(String templateName) {
		if (templateName == null) {
			throw new RuntimeException("templateName is null");
		}
		this.templateName = templateName;
		return this;
	}

	public FormDefinitionQuery templateNameLike(String templateNameLike) {
		if (templateNameLike == null) {
			throw new RuntimeException("templateName is null");
		}
		this.templateNameLike = templateNameLike;
		return this;
	}

	public FormDefinitionQuery templateType(String templateType) {
		if (templateType == null) {
			throw new RuntimeException("templateType is null");
		}
		this.templateType = templateType;
		return this;
	}

	public FormDefinitionQuery templateTypeLike(String templateTypeLike) {
		if (templateTypeLike == null) {
			throw new RuntimeException("templateType is null");
		}
		this.templateTypeLike = templateTypeLike;
		return this;
	}

	public FormDefinitionQuery templateTypes(List<String> templateTypes) {
		if (templateTypes == null) {
			throw new RuntimeException("templateTypes is empty ");
		}
		this.templateTypes = templateTypes;
		return this;
	}

	public FormDefinitionQuery title(String title) {
		if (title == null) {
			throw new RuntimeException("title is null");
		}
		this.title = title;
		return this;
	}

	public FormDefinitionQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}