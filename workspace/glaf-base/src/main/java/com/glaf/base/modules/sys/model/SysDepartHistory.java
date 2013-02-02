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

public class SysDepartHistory implements Serializable {

	private static final long serialVersionUID = 5222802125836801964L;
	private long id;
	private Date createDate;
	private Date updateDate;
	private long actorId;
	private String remark;

	private SysDepartment newDepart;
	private SysDepartment oldDepart;

	public long getActorId() {
		return actorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public long getId() {
		return id;
	}

	public SysDepartment getNewDepart() {
		return newDepart;
	}

	public SysDepartment getOldDepart() {
		return oldDepart;
	}

	public String getRemark() {
		return remark;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNewDepart(SysDepartment newDepart) {
		this.newDepart = newDepart;
	}

	public void setOldDepart(SysDepartment oldDepart) {
		this.oldDepart = oldDepart;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}