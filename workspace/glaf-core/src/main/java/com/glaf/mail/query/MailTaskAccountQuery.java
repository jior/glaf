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

import java.util.*;
import com.glaf.core.query.BaseQuery;

public class MailTaskAccountQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String taskId;
	protected List<String> taskIds;
	protected String accountId;
	protected List<String> accountIds;

	public MailTaskAccountQuery() {

	}

	public MailTaskAccountQuery accountId(String accountId) {
		if (accountId == null) {
			throw new RuntimeException("accountId is null");
		}
		this.accountId = accountId;
		return this;
	}

	public MailTaskAccountQuery accountIds(List<String> accountIds) {
		if (accountIds == null) {
			throw new RuntimeException("accountIds is empty ");
		}
		this.accountIds = accountIds;
		return this;
	}

	public String getAccountId() {
		return accountId;
	}

	public List<String> getAccountIds() {
		return accountIds;
	}

	public String getTaskId() {
		return taskId;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("taskId", "TASKID_");
		addColumn("accountId", "ACCOUNTID_");
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setAccountIds(List<String> accountIds) {
		this.accountIds = accountIds;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public MailTaskAccountQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public MailTaskAccountQuery taskIds(List<String> taskIds) {
		if (taskIds == null) {
			throw new RuntimeException("taskIds is empty ");
		}
		this.taskIds = taskIds;
		return this;
	}

}