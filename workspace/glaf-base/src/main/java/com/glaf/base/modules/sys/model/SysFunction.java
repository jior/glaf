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
import com.glaf.base.modules.sys.util.SysFunctionJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "sys_function")
public class SysFunction implements Serializable, JSONable {
	private static final long serialVersionUID = -4669036487930746301L;

	@javax.persistence.Transient
	private SysApplication app;

	/**
	 * 应用编号
	 */
	@Column(name = "APPID")
	protected long appId;

	/**
	 * 权限代码
	 */
	@Column(name = "CODE", length = 50)
	protected String code;

	/**
	 * 描述
	 */
	@Column(name = "FUNCDESC", length = 500)
	protected String funcDesc;

	/**
	 * 方法
	 */
	@Column(name = "FUNCMETHOD", length = 250)
	protected String funcMethod;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 100)
	protected String name;

	/**
	 * 序号
	 */
	@Column(name = "SORT")
	protected int sort;

	public SysFunction() {

	}

	public SysApplication getApp() {
		return app;
	}

	public long getAppId() {
		return appId;
	}

	public String getCode() {
		return code;
	}

	public String getFuncDesc() {
		return funcDesc;
	}

	public String getFuncMethod() {
		return funcMethod;
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

	public SysFunction jsonToObject(JSONObject jsonObject) {
		return SysFunctionJsonFactory.jsonToObject(jsonObject);
	}

	public void setApp(SysApplication app) {
		this.app = app;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}

	public void setFuncMethod(String funcMethod) {
		this.funcMethod = funcMethod;
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

	public JSONObject toJsonObject() {
		return SysFunctionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysFunctionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}