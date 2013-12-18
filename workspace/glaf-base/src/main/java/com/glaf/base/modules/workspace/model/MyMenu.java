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

package com.glaf.base.modules.workspace.model;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.workspace.util.MyMenuJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "MYMENU")
public class MyMenu implements Serializable, JSONable {

	private static final long serialVersionUID = 3488265849082732239L;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 用户ID
	 */
	@Column(name = "USERID")
	protected long userId;

	/**
	 * 标题
	 */
	@Column(name = "TITLE", length = 250)
	protected String title;

	/**
	 * 链接
	 */
	@Column(name = "URL", length = 500)
	protected String url;

	/**
	 * 顺序
	 */
	@Column(name = "SORT")
	protected int sort;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public MyMenu jsonToObject(JSONObject jsonObject) {
		return MyMenuJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return MyMenuJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return MyMenuJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}