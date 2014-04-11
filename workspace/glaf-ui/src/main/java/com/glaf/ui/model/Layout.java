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

package com.glaf.ui.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

@Entity
@Table(name = "UI_LAYOUT")
public class Layout implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id = null;

	@Column(name = "NAME_", nullable = false, length = 50)
	protected String name = null;

	@Column(name = "TITLE_")
	protected String title = null;

	@Column(name = "DATAINDEX_")
	protected int dataIndex = 0;

	@Column(name = "COLUMNS_")
	protected int columns = 0;

	@Column(name = "TEMPLATEID_", length = 50)
	protected String templateId = null;

	@Column(name = "SPACESTYLE_", length = 50)
	protected String spaceStyle = null;

	@Column(name = "COLUMNSTYLE_")
	protected String columnStyle = null;

	@Column(name = "PANELS_")
	protected String panels = null;

	@Column(name = "ACTORID_")
	protected String actorId = null;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate = null;

	public Layout() {

	}

	public String getActorId() {
		return actorId;
	}

	public int getColumns() {
		return columns;
	}

	public String getColumnStyle() {
		return columnStyle;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public int getDataIndex() {
		return dataIndex;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPanels() {
		return panels;
	}

	public String getSpaceStyle() {
		return spaceStyle;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getTitle() {
		return title;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setColumnStyle(String columnStyle) {
		this.columnStyle = columnStyle;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataIndex(int dataIndex) {
		this.dataIndex = dataIndex;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPanels(String panels) {
		this.panels = panels;
	}

	public void setSpaceStyle(String spaceStyle) {
		this.spaceStyle = spaceStyle;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public JSONObject toJsonObject() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", name);
		jsonObject.put("title", title);
		jsonObject.put("createDate", createDate);
		if (actorId != null) {
			jsonObject.put("actorId", actorId);
		}
		return jsonObject;
	}

}