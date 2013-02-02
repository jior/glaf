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
import java.util.Date;

public class SysLog implements Serializable {
	private static final long serialVersionUID = 3489584842305336744L;
	private long id;
	private String account;
	private String ip;
	private Date createTime;
	private String operate;
	private int flag;

	public String getAccount() {
		return account;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public int getFlag() {
		return flag;
	}

	public long getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getOperate() {
		return operate;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

}