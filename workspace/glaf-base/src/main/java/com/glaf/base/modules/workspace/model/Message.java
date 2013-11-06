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
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.workspace.util.MessageJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "Message")
public class Message implements Serializable, JSONable {

	private static final long serialVersionUID = -3111749338365950889L;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 消息类型
	 */
	@Column(name = "TYPE")
	protected int type;
 
	
	@Column(name = "SYSTYPE")
	protected int sysType;

	/**
	 * 接收人列表
	 */
	@Column(name = "RECVERLIST", length = 500)
	protected String recverList;

	/**
	 * 标题
	 */
	@Column(name = "TITLE", length = 250)
	protected String title;

	/**
	 * 内容
	 */
	@Lob
	@Column(name = "CONTENT", length = 2000)
	protected String content;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	protected Date createDate;

	/**
	 * 已读标记
	 */
	@Column(name = "READED")
	protected int readed;

	/**
	 * 分类
	 */
	@Column(name = "CATEGORY")
	protected int category;

	/**
	 * 发送者
	 */
	@Column(name = "SENDER")
	protected long senderId;

	/**
	 * 接收者
	 */
	@Column(name = "RECVER")
	protected long recverId;

	@javax.persistence.Transient
	protected SysUser recver;

	@javax.persistence.Transient
	protected SysUser sender;

	public int getCategory() {
		return category;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public long getId() {
		return id;
	}

	public int getReaded() {
		return readed;
	}

	public SysUser getRecver() {
		return recver;
	}

	public long getRecverId() {
		return recverId;
	}

	public String getRecverList() {
		return recverList;
	}

	public SysUser getSender() {
		return sender;
	}

	public long getSenderId() {
		return senderId;
	}

	public int getSysType() {
		return sysType;
	}

	public String getTitle() {
		return title;
	}

	public int getType() {
		return type;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setReaded(int readed) {
		this.readed = readed;
	}

	public void setRecver(SysUser recver) {
		this.recver = recver;
	}

	public void setRecverId(long recverId) {
		this.recverId = recverId;
	}

	public void setRecverList(String recverList) {
		this.recverList = recverList;
	}

	public void setSender(SysUser sender) {
		this.sender = sender;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Message jsonToObject(JSONObject jsonObject) {
		return MessageJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return MessageJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return MessageJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}