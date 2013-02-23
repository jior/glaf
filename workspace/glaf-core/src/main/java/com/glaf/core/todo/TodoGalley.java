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

package com.glaf.core.todo;

import java.util.Date;

public class TodoGalley implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Todo todo;

	private String actorId;

	private String rowId;

	private String processInstanceId;

	private Date createDate;
	
	public TodoGalley(){
		
	}

	public String getActorId() {
		return actorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getRowId() {
		return rowId;
	}

	public Todo getTodo() {
		return todo;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}
	
	

}