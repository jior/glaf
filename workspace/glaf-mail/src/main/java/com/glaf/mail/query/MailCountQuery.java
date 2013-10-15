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

package com.glaf.mail.query;

import com.glaf.core.query.BaseQuery;

public class MailCountQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String taskId;
	protected String type;

	public MailCountQuery() {

	}

	public String getTaskId() {
		return taskId;
	}

	public String getType() {
		return type;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("taskId", "TASKID_");
		addColumn("accountId", "ACCOUNTID_");
		addColumn("type", "TYPE_");
		addColumn("sendStatus", "SENDSTATUS_");
		addColumn("receiveStatus", "RECEIVESTATUS_");
		addColumn("qty", "QTY_");
		addColumn("lastModified", "LASTMODIFIED_");
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MailCountQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public MailCountQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}