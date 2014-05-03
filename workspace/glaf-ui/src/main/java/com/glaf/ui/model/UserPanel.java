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
@Table(name = "UI_USERPANEL")
public class UserPanel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id = null;

	@Column(name = "ACTORID_", length = 50, nullable = true)
	protected String actorId = null;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_", nullable = true)
	protected Date createDate = null;

	@Column(name = "REFRESHSECONDS_")
	protected int refreshSeconds = 0;

	@Column(name = "LAYOUTNAME_", length = 20)
	protected String layoutName = null;

	@javax.persistence.Transient
	protected String layoutId = null;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "LAYOUT_")
	protected Layout layout = null;

	@Transient
	protected Map<String, Panel> panels = new java.util.HashMap<String, Panel>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userPanel")
	protected Set<PanelInstance> panelInstances = new HashSet<PanelInstance>();

	public UserPanel() {

	}

	public void addPanel(Panel panel) {
		if (panels == null) {
			panels = new java.util.HashMap<String, Panel>();
		}
		panels.put(panel.getName(), panel);
	}

	public void addPanelInstance(PanelInstance panelInstance) {
		if (panelInstances == null) {
			panelInstances = new HashSet<PanelInstance>();
		}
		panelInstances.add(panelInstance);
	}

	public String getActorId() {
		return actorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getId() {
		return id;
	}

	public Layout getLayout() {
		return layout;
	}

	public String getLayoutId() {
		return layoutId;
	}

	public String getLayoutName() {
		return layoutName;
	}

	public Set<PanelInstance> getPanelInstances() {
		return panelInstances;
	}

	@Transient
	public Map<String, Panel> getPanels() {
		return panels;
	}

	public int getRefreshSeconds() {
		return refreshSeconds;
	}

	@Transient
	public void removePanel(Panel panel) {
		if (panels != null) {
			panels.remove(panel.getName());
		}
	}

	@Transient
	public void removePanel(String name) {
		if (panels != null) {
			panels.remove(name);
		}
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	public void setPanelInstances(Set<PanelInstance> panelInstances) {
		this.panelInstances = panelInstances;
	}

	public void setPanels(Map<String, Panel> panels) {
		this.panels = panels;
	}

	public void setRefreshSeconds(int refreshSeconds) {
		this.refreshSeconds = refreshSeconds;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}