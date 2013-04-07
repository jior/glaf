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

@Entity
@Table(name = "UI_SKIN")
public class Skin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id = null;

	@Column(name = "NAME_", nullable = false, length = 50)
	protected String name = null;

	@Column(name = "TITLE_")
	protected String title = null;

	@Column(name = "DESCRIPTION_", length = 500)
	protected String description = null;

	@Column(name = "IMAGE_")
	protected String image = null;

	@Column(name = "STYLECLASS_")
	protected String styleClass = null;

	@Column(name = "LOCKED_", nullable = false)
	protected int locked = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate = null;

	public Skin() {

	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public int getLocked() {
		return locked;
	}

	public String getName() {
		return name;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public String getTitle() {
		return title;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}