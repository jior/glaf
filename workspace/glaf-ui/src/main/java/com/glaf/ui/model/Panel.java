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
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "UI_PANEL")
public class Panel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ACTORID_")
	protected String actorId = null;

	@Column(name = "CLOSE_")
	protected int close;

	@Column(name = "COLLAPSIBLE_")
	protected int collapsible;

	@Column(name = "COLOR_", length = 50)
	protected String color = null;

	@Column(name = "COLUMNINDEX_")
	protected int columnIndex;

	@Column(name = "CONTENT_", length = 2000)
	protected String content = null;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate = null;

	@Column(name = "HEIGHT_")
	protected int height;

	@Column(name = "ICON_")
	protected String icon = null;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id = null;

	@Column(name = "LINK_", length = 200)
	protected String link = null;

	@Column(name = "LOCKED_")
	protected int locked;

	@Column(name = "MODULEID_")
	protected String moduleId = null;

	@Column(name = "MODULENAME_")
	protected String moduleName = null;

	@Column(name = "MORELINK_", length = 200)
	protected String moreLink = null;

	@Column(name = "NAME_", nullable = false, length = 50)
	protected String name = null;

	@Column(name = "QUERYID_", length = 200)
	protected String queryId = null;

	@Column(name = "RESIZE_")
	protected int resize;

	@Transient
	protected String script = null;

	@Column(name = "STYLE_", length = 200)
	protected String style = null;

	@Column(name = "TITLE_")
	protected String title = null;

	@Column(name = "TYPE_", length = 20)
	protected String type = null;

	@Column(name = "WIDTH_")
	protected int width;

	public Panel() {

	}

	public String getActorId() {
		return actorId;
	}

	public int getClose() {
		return close;
	}

	public int getCollapsible() {
		return collapsible;
	}

	public String getColor() {
		return color;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public int getHeight() {
		return height;
	}

	public String getIcon() {
		return icon;
	}

	public String getId() {
		return id;
	}

	public String getLink() {
		return link;
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

	public String getMoreLink() {
		return moreLink;
	}

	public String getName() {
		return name;
	}

	public String getQueryId() {
		return queryId;
	}

	public int getResize() {
		return resize;
	}

	public String getScript() {
		return script;
	}

	public String getStyle() {
		return style;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public int getWidth() {
		return width;
	}

	public boolean canClosable() {
		return close == 1 ? true : false;
	}

	public boolean canCollapsible() {
		return collapsible == 1 ? true : false;
	}

	public boolean canResizeable() {
		return resize == 1 ? true : false;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setClose(int close) {
		this.close = close;
	}

	public void setCollapsible(int collapsible) {
		this.collapsible = collapsible;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLink(String link) {
		this.link = link;
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

	public void setMoreLink(String moreLink) {
		this.moreLink = moreLink;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setResize(int resize) {
		this.resize = resize;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}