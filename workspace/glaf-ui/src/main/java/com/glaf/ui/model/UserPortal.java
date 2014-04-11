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

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "UI_USERPORTAL")
public class UserPortal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id = null;

	@Column(name = "ACTORID_", length = 50)
	protected String actorId = null;

	@Column(name = "PANELID_", length = 50)
	protected String panelId = null;

	@Column(name = "COLUMNINDEX_")
	protected int columnIndex;

	@Column(name = "POSITION_")
	protected int position;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_", nullable = true)
	protected Date createDate = null;

	@Transient
	protected Panel panel = null;

	public UserPortal() {

	}

	public String getActorId() {
		return actorId;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getId() {
		return id;
	}

	public Panel getPanel() {
		return panel;
	}

	public String getPanelId() {
		return panelId;
	}

	public int getPosition() {
		return position;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	public void setPanelId(String panelId) {
		this.panelId = panelId;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}