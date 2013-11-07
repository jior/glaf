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

package com.glaf.core.id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_DBID")
public class Dbid implements java.io.Serializable {
 
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NAME_", length = 50, nullable = false)
	private String name;
	
	@Column(name = "TITLE_", length = 200)
	private String title;

	@Column(name = "VALUE_", length = 500, nullable = false)
	private String value;

	@Column(name = "VERSION_", nullable = false)
	private int version;

	
	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getValue() {
		return value;
	}

	public int getVersion() {
		return version;
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

	public void setVersion(int version) {
		this.version = version;
	}

}