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

package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.SubjectCodeJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "SubjectCode")
public class SubjectCode implements Serializable, JSONable {
	private static final long serialVersionUID = -1L;
	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * ¸¸½Úµã±àºÅ
	 */
	@Column(name = "PARENT")
	protected long parent;

	/**
	 * ±àÂë
	 */
	@Column(name = "SUBJECTCODE", length = 50)
	protected String subjectCode;

	/**
	 * Ãû³Æ
	 */
	@Column(name = "SUBJECTNAME", length = 250)
	protected String subjectName;

	/**
	 * Ë³Ðò
	 */
	@Column(name = "SORT")
	protected int sort;

	public long getId() {
		return id;
	}

	public long getParent() {
		return parent;
	}

	public int getSort() {
		return sort;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectCode other = (SubjectCode) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public SubjectCode jsonToObject(JSONObject jsonObject) {
		return SubjectCodeJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SubjectCodeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SubjectCodeJsonFactory.toObjectNode(this);
	}

}