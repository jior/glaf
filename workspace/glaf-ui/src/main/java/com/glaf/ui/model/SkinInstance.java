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
@Table(name = "UI_SKININSTANCE")
public class SkinInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id = null;

	@Column(name = "ACTORID_")
	protected String actorId;

	@javax.persistence.Transient
	protected String skinId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SKIN_")
	protected Skin skin;

	public SkinInstance() {

	}

	public String getSkinId() {
		return skinId;
	}

	public void setSkinId(String skinId) {
		this.skinId = skinId;
	}

	public String getActorId() {
		return actorId;
	}

	public String getId() {
		return id;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

}