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

import com.glaf.base.modules.sys.model.SysUser;

public class Message implements Serializable {

	private static final long serialVersionUID = -3111749338365950889L;

	private long id;

	private int type;

	// sysType 0:系统警告 1:系统消息
	private int sysType;

	private SysUser sender;

	private Long senderId;

	private SysUser recver;

	private Long recverId;

	private String recverList;

	private String title;

	private String content;

	private Date createDate;

	private int readed;

	private int category;

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

	public Long getRecverId() {
		return recverId;
	}

	public String getRecverList() {
		return recverList;
	}

	public SysUser getSender() {
		return sender;
	}

	public Long getSenderId() {
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

	public void setRecverId(Long recverId) {
		this.recverId = recverId;
	}

	public void setRecverList(String recverList) {
		this.recverList = recverList;
	}

	public void setSender(SysUser sender) {
		this.sender = sender;
	}

	public void setSenderId(Long senderId) {
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

}