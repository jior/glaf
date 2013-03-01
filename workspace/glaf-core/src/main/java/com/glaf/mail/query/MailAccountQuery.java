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

public class MailAccountQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String accountType;
	protected String mailAddressLike;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	public MailAccountQuery() {

	}

	public String getAccountType() {
		return accountType;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public String getMailAddressLike() {
		return mailAddressLike;
	}

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if ("receivePort".equals(sortField)) {
				orderBy = "E.RECEIVEPORT_" + a_x;
			}

			if ("sendPort".equals(sortField)) {
				orderBy = "E.SENDPORT_" + a_x;
			}

			if ("autoReceive".equals(sortField)) {
				orderBy = "E.AUTORECEIVE_" + a_x;
			}

			if ("rememberPassword".equals(sortField)) {
				orderBy = "E.REMEMBERPASSWORD_" + a_x;
			}

			if ("locked".equals(sortField)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("createDate".equals(sortField)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("accountName", "ACCOUNTNAME_");
		addColumn("accountType", "ACCOUNTTYPE_");
		addColumn("actorId", "ACTORID_");
		addColumn("mailAddress", "MAILADDRESS_");
		addColumn("showName", "SHOWNAME_");
		addColumn("username", "USERNAME_");
		addColumn("password", "PASSWORD_");
		addColumn("pop3Server", "POP3SERVER_");
		addColumn("receivePort", "RECEIVEPORT_");
		addColumn("smtpServer", "SMTPSERVER_");
		addColumn("sendPort", "SENDPORT_");
		addColumn("autoReceive", "AUTORECEIVE_");
		addColumn("rememberPassword", "REMEMBERPASSWORD_");
		addColumn("locked", "LOCKED_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("authFlag", "AUTHFLAG_");
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setMailAddressLike(String mailAddressLike) {
		this.mailAddressLike = mailAddressLike;
	}

}