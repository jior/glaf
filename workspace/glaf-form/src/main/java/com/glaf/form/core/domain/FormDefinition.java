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

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.graph.def.FormEvent;
import com.glaf.form.core.graph.def.FormNode;
import com.glaf.form.core.graph.def.FormScript;
import com.glaf.form.core.graph.def.GraphElement;
import com.glaf.form.core.util.FormDefinitionJsonFactory;

@Entity
@Table(name = "FORM_DEFINITION")
public class FormDefinition extends GraphElement implements JSONable {
	private static final long serialVersionUID = 1L;
	public static final String[] supportedEventTypes = new String[] {
			FormEvent.EVENTTYPE_RENDER, FormEvent.EVENTTYPE_BEFORE_RENDER,
			FormEvent.EVENTTYPE_AFTER_RENDER };
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "COLUMNS_")
	protected Integer columns = -1;

	@Column(name = "CREATEBY_", updatable = false)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_", updatable = false)
	protected Date createDate;

	@Column(name = "DESCRIPTION_")
	protected String description;

	@Transient
	protected transient FormContext formContext = null;

	@Column(name = "FORMDEFINITIONID_", updatable = false)
	protected String formDefinitionId;

	@Column(name = "LOCKED_")
	protected Integer locked = 0;

	@Column(name = "NAME_", updatable = false, length = 100)
	protected String name;

	@Column(name = "NODEID_")
	protected Long nodeId;

	@Transient
	protected List<FormNode> nodes = null;

	@Transient
	protected Map<String, FormNode> nodesMap = null;

	@Column(name = "OBJECTID_")
	protected String objectId;

	@Column(name = "OBJECTVALUE_")
	protected String objectValue;

	@Column(name = "ROWS_")
	protected Integer rows = -1;

	@Transient
	protected List<FormScript> scripts = null;

	@Transient
	protected byte[] templateData;

	@Column(name = "TEMPLATENAME_")
	protected String templateName;

	@Column(name = "TEMPLATETYPE_")
	protected String templateType;

	@Column(name = "TITLE_")
	protected String title;

	@Column(name = "VERSION_")
	protected Integer version = 1;

	public FormDefinition() {

	}

	@Transient
	public FormNode addNode(FormNode node) {
		if (node == null) {
			throw new IllegalArgumentException(
					"can't add a null node to a form definition");
		}
		if (nodes == null) {
			nodes = new java.util.ArrayList<FormNode>();
		}
		node.setFormDefinition(this);
		nodes.add(node);
		getNodesMap().put(node.getName(), node);
		return node;
	}

	@Transient
	public FormScript addScript(FormScript script) {
		if (script == null) {
			throw new IllegalArgumentException(
					"can't add a null script to a form definition");
		}
		if (scripts == null) {
			scripts = new java.util.ArrayList<FormScript>();
		}
		scripts.add(script);
		return script;
	}

	public Integer getColumns() {
		return columns;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getDescription() {
		return description;
	}

	public FormContext getFormContext() {
		return formContext;
	}

	public String getFormDefinitionId() {
		return formDefinitionId;
	}

	public String getId() {
		return id;
	}

	public Integer getLocked() {
		return locked;
	}

	public String getName() {
		return name;
	}

	@Transient
	public FormNode getNode(String name) {
		if (nodes == null) {
			return null;
		}
		return getNodesMap().get(name);
	}

	public Long getNodeId() {
		return nodeId;
	}

	@Transient
	public List<FormNode> getNodes() {
		if (nodes == null) {
			nodes = new java.util.ArrayList<FormNode>();
		}
		return nodes;
	}

	@Transient
	public Map<String, FormNode> getNodesMap() {
		if (nodesMap == null) {
			nodesMap = new LinkedHashMap<String, FormNode>();
		}
		if (nodes != null) {
			Iterator<FormNode> iter = nodes.iterator();
			while (iter.hasNext()) {
				FormNode node = iter.next();
				nodesMap.put(node.getName(), node);
			}
		}
		return nodesMap;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public Integer getRows() {
		return rows;
	}

	@Transient
	public List<FormScript> getScripts() {
		if (scripts == null) {
			scripts = new java.util.ArrayList<FormScript>();
		}
		return scripts;
	}

	@Transient
	public String[] getSupportedEventTypes() {
		return supportedEventTypes;
	}

	@Transient
	public byte[] getTemplateData() {
		return templateData;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateType() {
		return templateType;
	}

	public String getTitle() {
		return title;
	}

	public Integer getVersion() {
		return version;
	}

	@Transient
	public boolean hasNode(String name) {
		if (nodes == null) {
			return false;
		}
		return getNodesMap().containsKey(name);
	}

	@Transient
	public void removeNode(FormNode node) {
		FormNode removedNode = null;
		if (node == null)
			throw new IllegalArgumentException(
					"can't remove a null node from a form definition");
		if (nodes != null) {
			if (nodes.remove(node)) {
				removedNode = node;
				removedNode.setFormDefinition(null);
				getNodesMap().remove(node.getName());
			}
		}
	}

	@Transient
	public void removeScript(FormScript script) {
		FormScript removedScript = null;
		if (script == null)
			throw new IllegalArgumentException(
					"can't remove a null script from a form definition");
		if (scripts != null) {
			if (scripts.remove(script)) {
				removedScript = script;
				removedScript.setGraphElement(null);
			}
		}
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFormContext(FormContext formContext) {
		this.formContext = formContext;
	}

	public void setFormDefinitionId(String formDefinitionId) {
		this.formDefinitionId = formDefinitionId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodes(List<FormNode> nodes) {
		this.nodes = nodes;
	}

	public void setNodesMap(Map<String, FormNode> nodesMap) {
		this.nodesMap = nodesMap;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public void setScripts(List<FormScript> scripts) {
		this.scripts = scripts;
	}

	public void setTemplateData(byte[] templateData) {
		this.templateData = templateData;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public FormDefinition jsonToObject(JSONObject jsonObject) {
		return FormDefinitionJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormDefinitionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormDefinitionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}