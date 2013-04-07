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

package com.glaf.activiti.extension.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "EX_ACT_EXTENSION_FIELD")
public class ExtensionFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "EXTENDID_", nullable = false)
	protected String extendId = null;

	@Column(name = "NAME_")
	protected String name = null;

	@Column(name = "VALUE_", length = 1000)
	protected String value = null;

	@Transient
	protected ExtensionEntity extension = null;

	public ExtensionFieldEntity() {

	}

	public String getExtendId() {
		return extendId;
	}

	public ExtensionEntity getExtension() {
		return extension;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public void setExtension(ExtensionEntity extension) {
		this.extension = extension;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return name + "=" + value;
	}

}