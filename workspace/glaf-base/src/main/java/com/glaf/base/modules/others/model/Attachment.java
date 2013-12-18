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

package com.glaf.base.modules.others.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.others.util.AttachmentJsonFactory;
import com.glaf.core.base.JSONable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "ATTACHMENT")
public class Attachment implements Serializable, JSONable {
	private static final long serialVersionUID = 3825200508464771531L;
	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 引用ID
	 */
	@Column(name = "REFERID")
	protected long referId;

	/**
	 * 引用类型
	 */
	@Column(name = "REFERTYPE")
	protected int referType;

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 250)
	protected String name;

	/**
	 * URL
	 */
	@Column(name = "URL", length = 500)
	protected String url;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	protected Date createDate;

	/**
	 * 创建人编号
	 */
	@Column(name = "CREATEID")
	protected long createId;

	public Attachment() {

	}

	public long getCreateId() {
		return createId;
	}

	public void setCreateId(long createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getReferId() {
		return referId;
	}

	public void setReferId(long referId) {
		this.referId = referId;
	}

	public int getReferType() {
		return referType;
	}

	public void setReferType(int referType) {
		this.referType = referType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Attachment jsonToObject(JSONObject jsonObject) {
		return AttachmentJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return AttachmentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return AttachmentJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}