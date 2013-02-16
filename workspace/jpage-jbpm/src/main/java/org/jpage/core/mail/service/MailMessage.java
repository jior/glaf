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


package org.jpage.core.mail.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MailMessage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String messageId;

	private String from;

	private String[] to;

	private String[] cc;

	private String[] bcc;

	private String subject;

	private String text;

	private String encoding;

	private String templateId;

	private boolean saveMessage;

	private boolean supportExpression = true;

	private Map dataMap = new HashMap();

	private Collection files = new HashSet();

	private String[] replyTo;

	public String[] getReplyTo() {
		return this.replyTo;
	}

	public void setReplyTo(String[] replyTo) {
		this.replyTo = replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = new String[] { replyTo };
	}

	public MailMessage() {
	}

	public MailMessage(MailMessage original) {
		this.from = original.getFrom();
		if (original.getTo() != null) {
			this.to = new String[original.getTo().length];
			System.arraycopy(original.getTo(), 0, this.to, 0,
					original.getTo().length);
		}
		if (original.getReplyTo() != null) {
			this.replyTo = new String[original.getReplyTo().length];
			System.arraycopy(original.getReplyTo(), 0, this.replyTo, 0,
					original.getReplyTo().length);
		}
		if (original.getCc() != null) {
			this.cc = new String[original.getCc().length];
			System.arraycopy(original.getCc(), 0, this.cc, 0,
					original.getCc().length);
		}
		if (original.getBcc() != null) {
			this.bcc = new String[original.getBcc().length];
			System.arraycopy(original.getBcc(), 0, this.bcc, 0,
					original.getBcc().length);
		}
		this.subject = original.getSubject();
		this.text = original.getText();
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFrom() {
		return this.from;
	}

	public void setTo(String to) {
		this.to = new String[] { to };
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getTo() {
		return this.to;
	}

	public void setCc(String cc) {
		this.cc = new String[] { cc };
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getCc() {
		return cc;
	}

	public void setBcc(String bcc) {
		this.bcc = new String[] { bcc };
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String[] getBcc() {
		return bcc;
	}

	public Map getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Collection getFiles() {
		return files;
	}

	public void setFiles(Collection files) {
		this.files = files;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public boolean isSaveMessage() {
		return saveMessage;
	}

	public void setSaveMessage(boolean saveMessage) {
		this.saveMessage = saveMessage;
	}

	public boolean isSupportExpression() {
		return supportExpression;
	}

	public void setSupportExpression(boolean supportExpression) {
		this.supportExpression = supportExpression;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}