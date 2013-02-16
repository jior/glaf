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

package org.jpage.core.mail.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MailUser implements Serializable, Comparable {
	private static final long serialVersionUID = 1L;

	private String accountId;

	private String accountName;

	private String accountType;

	private String userId;

	private String username;

	private String password;

	private String pop3Server;

	private int receivePort = 110;

	private String smtpServer;

	private int sendPort = 25;

	private int autoReceive;

	private int rememberPassword;

	private int enabled;

	private int status;

	private Date createDate;

	private Date lastLoginDate;

	private String mailAddress;

	private String showName;

	private String mobile;

	private String inbox;

	private String authFlag;

	public MailUser() {

	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getAutoReceive() {
		return autoReceive;
	}

	public void setAutoReceive(int autoReceive) {
		this.autoReceive = autoReceive;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getInbox() {
		return inbox;
	}

	public void setInbox(String inbox) {
		this.inbox = inbox;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPop3Server() {
		return pop3Server;
	}

	public void setPop3Server(String pop3Server) {
		this.pop3Server = pop3Server;
	}

	public int getReceivePort() {
		return receivePort;
	}

	public void setReceivePort(int receivePort) {
		this.receivePort = receivePort;
	}

	public int getRememberPassword() {
		return rememberPassword;
	}

	public void setRememberPassword(int rememberPassword) {
		this.rememberPassword = rememberPassword;
	}

	public int getSendPort() {
		return sendPort;
	}

	public void setSendPort(int sendPort) {
		this.sendPort = sendPort;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public int hashCode() {
		return 2004071202;
	}

	public boolean equals(Object other) {
		if (other instanceof MailUser) {
			MailUser otherKey = (MailUser) other;
			return ((accountId.equals(otherKey.getAccountId())));
		}
		return false;
	}

	public int compareTo(Object o) {
		if (o instanceof MailUser) {
			return this.accountName.compareTo(((MailUser) o).getAccountName());
		} else {
			return -1;
		}
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}