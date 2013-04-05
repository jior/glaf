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
package com.glaf.form.core.graph.def;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "FORM_APPLICATION_PROP")
public class FormApplicationProperty implements Serializable {
	private static final long serialVersionUID = 1L;
	@javax.persistence.Transient
	protected String appId = null;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FORMAPPLICATION_", nullable = false, updatable = false)
	protected FormApplication formApplication = null;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id = null;

	@Basic
	@Column(name = "NAME_", updatable = false)
	protected String name = null;

	@Basic
	@Column(name = "TITLE_")
	protected String title = null;

	@Basic
	@Column(name = "VALUE_", length = 2000)
	protected String value = null;

	public FormApplicationProperty() {

	}

	public String getAppId() {
		return appId;
	}

	public FormApplication getFormApplication() {
		return formApplication;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getValue() {
		return value;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setFormApplication(FormApplication formApplication) {
		this.formApplication = formApplication;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setValue(String value) {
		this.value = value;
	}

}