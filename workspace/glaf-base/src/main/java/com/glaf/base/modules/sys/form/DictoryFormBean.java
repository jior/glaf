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

package com.glaf.base.modules.sys.form;

public class DictoryFormBean {
	private int blocked;
	private String code;
	private String desc;
	private String ext1;
	private String ext2;
	private long id;
	private String name;
	private int sort;
	private long typeId;
	private String value;

	public DictoryFormBean() {

	}

	public int getBlocked() {
		return blocked;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String getExt1() {
		return ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getSort() {
		return sort;
	}

	public long getTypeId() {
		return typeId;
	}

	public String getValue() {
		return value;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public void setValue(String value) {
		this.value = value;
	}

}