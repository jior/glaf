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
import com.glaf.core.query.DataQuery;

public class MailDataFileQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String email;
	protected String emailLike;
	protected List<String> emails;
	protected String msgId;
	protected String msgIdLike;
	protected List<String> msgIds;
	protected String inReplyTo;
	protected String inReplyToLike;

	protected String from;
	protected String fromLike;

	protected String to;
	protected String toLike;

	protected String cc;
	protected String ccLike;

	protected Date dateGreaterThanOrEqual;
	protected Date dateLessThanOrEqual;

	protected String subjectLike;

	protected String replyTo;
	protected String replyToLike;

	protected Integer intflag;
	protected Integer intflagGreaterThanOrEqual;
	protected Integer intflagLessThanOrEqual;
	protected List<Integer> intflags;
	protected String guidFrom;
	protected String guidFromLike;
	protected List<String> guidFroms;
	

	public MailDataFileQuery() {

	}

	public String getEmail() {
		return email;
	}

	public String getEmailLike() {
		if (emailLike != null && emailLike.trim().length() > 0) {
			if (!emailLike.startsWith("%")) {
				emailLike = "%" + emailLike;
			}
			if (!emailLike.endsWith("%")) {
				emailLike = emailLike + "%";
			}
		}
		return emailLike;
	}

	public List<String> getEmails() {
		return emails;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getMsgIdLike() {
		if (msgIdLike != null && msgIdLike.trim().length() > 0) {
			if (!msgIdLike.startsWith("%")) {
				msgIdLike = "%" + msgIdLike;
			}
			if (!msgIdLike.endsWith("%")) {
				msgIdLike = msgIdLike + "%";
			}
		}
		return msgIdLike;
	}

	public List<String> getMsgIds() {
		return msgIds;
	}

	public String getInReplyTo() {
		return inReplyTo;
	}

	public String getInReplyToLike() {
		if (inReplyToLike != null && inReplyToLike.trim().length() > 0) {
			if (!inReplyToLike.startsWith("%")) {
				inReplyToLike = "%" + inReplyToLike;
			}
			if (!inReplyToLike.endsWith("%")) {
				inReplyToLike = inReplyToLike + "%";
			}
		}
		return inReplyToLike;
	}

	public String getFrom() {
		return from;
	}

	public String getFromLike() {
		if (fromLike != null && fromLike.trim().length() > 0) {
			if (!fromLike.startsWith("%")) {
				fromLike = "%" + fromLike;
			}
			if (!fromLike.endsWith("%")) {
				fromLike = fromLike + "%";
			}
		}
		return fromLike;
	}

	public String getTo() {
		return to;
	}

	public String getToLike() {
		if (toLike != null && toLike.trim().length() > 0) {
			if (!toLike.startsWith("%")) {
				toLike = "%" + toLike;
			}
			if (!toLike.endsWith("%")) {
				toLike = toLike + "%";
			}
		}
		return toLike;
	}

	public String getCc() {
		return cc;
	}

	public String getCcLike() {
		if (ccLike != null && ccLike.trim().length() > 0) {
			if (!ccLike.startsWith("%")) {
				ccLike = "%" + ccLike;
			}
			if (!ccLike.endsWith("%")) {
				ccLike = ccLike + "%";
			}
		}
		return ccLike;
	}

	public Date getDateGreaterThanOrEqual() {
		return dateGreaterThanOrEqual;
	}

	public Date getDateLessThanOrEqual() {
		return dateLessThanOrEqual;
	}

	public String getSubjectLike() {
		if (subjectLike != null && subjectLike.trim().length() > 0) {
			if (!subjectLike.startsWith("%")) {
				subjectLike = "%" + subjectLike;
			}
			if (!subjectLike.endsWith("%")) {
				subjectLike = subjectLike + "%";
			}
		}
		return subjectLike;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public String getReplyToLike() {
		if (replyToLike != null && replyToLike.trim().length() > 0) {
			if (!replyToLike.startsWith("%")) {
				replyToLike = "%" + replyToLike;
			}
			if (!replyToLike.endsWith("%")) {
				replyToLike = replyToLike + "%";
			}
		}
		return replyToLike;
	}

	public Integer getIntflag() {
		return intflag;
	}

	public Integer getIntflagGreaterThanOrEqual() {
		return intflagGreaterThanOrEqual;
	}

	public Integer getIntflagLessThanOrEqual() {
		return intflagLessThanOrEqual;
	}

	public List<Integer> getIntflags() {
		return intflags;
	}

	public String getGuidFrom() {
		return guidFrom;
	}

	public String getGuidFromLike() {
		if (guidFromLike != null && guidFromLike.trim().length() > 0) {
			if (!guidFromLike.startsWith("%")) {
				guidFromLike = "%" + guidFromLike;
			}
			if (!guidFromLike.endsWith("%")) {
				guidFromLike = guidFromLike + "%";
			}
		}
		return guidFromLike;
	}

	public List<String> getGuidFroms() {
		return guidFroms;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailLike(String emailLike) {
		this.emailLike = emailLike;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void setMsgIdLike(String msgIdLike) {
		this.msgIdLike = msgIdLike;
	}

	public void setMsgIds(List<String> msgIds) {
		this.msgIds = msgIds;
	}

	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public void setInReplyToLike(String inReplyToLike) {
		this.inReplyToLike = inReplyToLike;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setFromLike(String fromLike) {
		this.fromLike = fromLike;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setToLike(String toLike) {
		this.toLike = toLike;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public void setCcLike(String ccLike) {
		this.ccLike = ccLike;
	}

	public void setDateGreaterThanOrEqual(Date dateGreaterThanOrEqual) {
		this.dateGreaterThanOrEqual = dateGreaterThanOrEqual;
	}

	public void setDateLessThanOrEqual(Date dateLessThanOrEqual) {
		this.dateLessThanOrEqual = dateLessThanOrEqual;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public void setReplyToLike(String replyToLike) {
		this.replyToLike = replyToLike;
	}

	public void setIntflag(Integer intflag) {
		this.intflag = intflag;
	}

	public void setIntflagGreaterThanOrEqual(Integer intflagGreaterThanOrEqual) {
		this.intflagGreaterThanOrEqual = intflagGreaterThanOrEqual;
	}

	public void setIntflagLessThanOrEqual(Integer intflagLessThanOrEqual) {
		this.intflagLessThanOrEqual = intflagLessThanOrEqual;
	}

	public void setIntflags(List<Integer> intflags) {
		this.intflags = intflags;
	}

	public void setGuidFrom(String guidFrom) {
		this.guidFrom = guidFrom;
	}

	public void setGuidFromLike(String guidFromLike) {
		this.guidFromLike = guidFromLike;
	}

	public void setGuidFroms(List<String> guidFroms) {
		this.guidFroms = guidFroms;
	}

	public MailDataFileQuery email(String email) {
		if (email == null) {
			throw new RuntimeException("email is null");
		}
		this.email = email;
		return this;
	}

	public MailDataFileQuery emailLike(String emailLike) {
		if (emailLike == null) {
			throw new RuntimeException("email is null");
		}
		this.emailLike = emailLike;
		return this;
	}

	public MailDataFileQuery emails(List<String> emails) {
		if (emails == null) {
			throw new RuntimeException("emails is empty ");
		}
		this.emails = emails;
		return this;
	}

	public MailDataFileQuery msgId(String msgId) {
		if (msgId == null) {
			throw new RuntimeException("msgId is null");
		}
		this.msgId = msgId;
		return this;
	}

	public MailDataFileQuery msgIdLike(String msgIdLike) {
		if (msgIdLike == null) {
			throw new RuntimeException("msgId is null");
		}
		this.msgIdLike = msgIdLike;
		return this;
	}

	public MailDataFileQuery msgIds(List<String> msgIds) {
		if (msgIds == null) {
			throw new RuntimeException("msgIds is empty ");
		}
		this.msgIds = msgIds;
		return this;
	}

	public MailDataFileQuery inReplyTo(String inReplyTo) {
		if (inReplyTo == null) {
			throw new RuntimeException("inReplyTo is null");
		}
		this.inReplyTo = inReplyTo;
		return this;
	}

	public MailDataFileQuery inReplyToLike(String inReplyToLike) {
		if (inReplyToLike == null) {
			throw new RuntimeException("inReplyTo is null");
		}
		this.inReplyToLike = inReplyToLike;
		return this;
	}

	public MailDataFileQuery from(String from) {
		if (from == null) {
			throw new RuntimeException("from is null");
		}
		this.from = from;
		return this;
	}

	public MailDataFileQuery fromLike(String fromLike) {
		if (fromLike == null) {
			throw new RuntimeException("from is null");
		}
		this.fromLike = fromLike;
		return this;
	}

	public MailDataFileQuery to(String to) {
		if (to == null) {
			throw new RuntimeException("to is null");
		}
		this.to = to;
		return this;
	}

	public MailDataFileQuery toLike(String toLike) {
		if (toLike == null) {
			throw new RuntimeException("to is null");
		}
		this.toLike = toLike;
		return this;
	}

	public MailDataFileQuery cc(String cc) {
		if (cc == null) {
			throw new RuntimeException("cc is null");
		}
		this.cc = cc;
		return this;
	}

	public MailDataFileQuery ccLike(String ccLike) {
		if (ccLike == null) {
			throw new RuntimeException("cc is null");
		}
		this.ccLike = ccLike;
		return this;
	}

	public MailDataFileQuery dateGreaterThanOrEqual(Date dateGreaterThanOrEqual) {
		if (dateGreaterThanOrEqual == null) {
			throw new RuntimeException("date is null");
		}
		this.dateGreaterThanOrEqual = dateGreaterThanOrEqual;
		return this;
	}

	public MailDataFileQuery dateLessThanOrEqual(Date dateLessThanOrEqual) {
		if (dateLessThanOrEqual == null) {
			throw new RuntimeException("date is null");
		}
		this.dateLessThanOrEqual = dateLessThanOrEqual;
		return this;
	}

	public MailDataFileQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public MailDataFileQuery replyTo(String replyTo) {
		if (replyTo == null) {
			throw new RuntimeException("replyTo is null");
		}
		this.replyTo = replyTo;
		return this;
	}

	public MailDataFileQuery replyToLike(String replyToLike) {
		if (replyToLike == null) {
			throw new RuntimeException("replyTo is null");
		}
		this.replyToLike = replyToLike;
		return this;
	}

	public MailDataFileQuery intflag(Integer intflag) {
		if (intflag == null) {
			throw new RuntimeException("intflag is null");
		}
		this.intflag = intflag;
		return this;
	}

	public MailDataFileQuery intflagGreaterThanOrEqual(
			Integer intflagGreaterThanOrEqual) {
		if (intflagGreaterThanOrEqual == null) {
			throw new RuntimeException("intflag is null");
		}
		this.intflagGreaterThanOrEqual = intflagGreaterThanOrEqual;
		return this;
	}

	public MailDataFileQuery intflagLessThanOrEqual(Integer intflagLessThanOrEqual) {
		if (intflagLessThanOrEqual == null) {
			throw new RuntimeException("intflag is null");
		}
		this.intflagLessThanOrEqual = intflagLessThanOrEqual;
		return this;
	}

	public MailDataFileQuery intflags(List<Integer> intflags) {
		if (intflags == null) {
			throw new RuntimeException("intflags is empty ");
		}
		this.intflags = intflags;
		return this;
	}

	public MailDataFileQuery guidFrom(String guidFrom) {
		if (guidFrom == null) {
			throw new RuntimeException("guidFrom is null");
		}
		this.guidFrom = guidFrom;
		return this;
	}

	public MailDataFileQuery guidFromLike(String guidFromLike) {
		if (guidFromLike == null) {
			throw new RuntimeException("guidFrom is null");
		}
		this.guidFromLike = guidFromLike;
		return this;
	}

	public MailDataFileQuery guidFroms(List<String> guidFroms) {
		if (guidFroms == null) {
			throw new RuntimeException("guidFroms is empty ");
		}
		this.guidFroms = guidFroms;
		return this;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder()!= null) {
				a_x = " desc ";
			}

			if (columns.get(sortField) != null) {
				orderBy = " E." + columns.get(sortField) + a_x;
			}
		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "id");
		addColumn("email", "email");
		addColumn("msgId", "msgId");
		addColumn("inReplyTo", "Inreplyto");
		addColumn("from", "From");
		addColumn("to", "To");
		addColumn("cc", "CC");
		addColumn("date", "Date");
		addColumn("subject", "Subject");
		addColumn("replyTo", "replyto");
		addColumn("intflag", "intflag");
		addColumn("guidFrom", "guid_from");
	}

}