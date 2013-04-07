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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UI_PANELINSTANCE")
public class PanelInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id = null;

	@Column(name = "NAME_", nullable = true, length = 50)
	protected String name = null;

	@javax.persistence.Transient
	protected String panelId = null;

	@javax.persistence.Transient
	protected String userPanelId = null;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PANEL_")
	protected Panel panel = null;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USERPANEL_")
	protected UserPanel userPanel = null;

	public PanelInstance() {

	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Panel getPanel() {
		return panel;
	}

	public String getPanelId() {
		return panelId;
	}

	public UserPanel getUserPanel() {
		return userPanel;
	}

	public String getUserPanelId() {
		return userPanelId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	public void setPanelId(String panelId) {
		this.panelId = panelId;
	}

	public void setUserPanel(UserPanel userPanel) {
		this.userPanel = userPanel;
	}

	public void setUserPanelId(String userPanelId) {
		this.userPanelId = userPanelId;
	}

}