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

package com.glaf.mail;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.glaf.core.message.BaseMessage;

public class MailMessage extends BaseMessage {
	private static final long serialVersionUID = 1L;
	private String mailId;

	private String from;

	private String[] to;

	private String[] cc;

	private String[] bcc;

	private String replyTo;

	private String encoding;

	private boolean saveMessage;

	private boolean supportExpression = true;

	private Map<String, Object> dataMap = new java.util.HashMap<String, Object>();

	private Collection<Object> files = new java.util.ArrayList<Object>();

	public MailMessage() {

	}

	public void addFile(Object file) {
		if (files == null) {
			files = new java.util.ArrayList<Object>();
		}
		files.add(file);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof MailMessage)) {
			return false;
		}
		MailMessage other = (MailMessage) obj;
		if (mailId == null) {
			if (other.mailId != null) {
				return false;
			}
		} else if (!mailId.equals(other.mailId)) {
			return false;
		}
		return true;
	}

	public String[] getBcc() {
		return bcc;
	}

	public String[] getCc() {
		return cc;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public String getEncoding() {
		return encoding;
	}

	public Collection<Object> getFiles() {
		return files;
	}

	public String getFrom() {
		return this.from;
	}

	public String getMailId() {
		return mailId;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public String[] getTo() {
		return this.to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mailId == null) ? 0 : mailId.hashCode());
		return result;
	}

	public boolean isSaveMessage() {
		return saveMessage;
	}

	public boolean isSupportExpression() {
		return supportExpression;
	}

	public void setBcc(String bcc) {
		this.bcc = new String[] { bcc };
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public void setCc(String cc) {
		this.cc = new String[] { cc };
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setFiles(Collection<Object> files) {
		this.files = files;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public void setSaveMessage(boolean saveMessage) {
		this.saveMessage = saveMessage;
	}

	public void setSupportExpression(boolean supportExpression) {
		this.supportExpression = supportExpression;
	}

	public void setTo(String to) {
		this.to = new String[] { to };
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}