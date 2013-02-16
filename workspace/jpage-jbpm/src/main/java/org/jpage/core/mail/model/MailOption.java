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

public class MailOption implements Serializable {
	private static final long serialVersionUID = 1L;

	private String optionId;

	private String userId;

	private int forwardMessageOption;

	private int forwardAttachOption;

	private int replyOption;

	private int initCharOption;

	private int sentOption;

	private int signOption;

	private int confirmOption;

	private int javascriptOption;

	private int icalwinOption;

	private int msgCountOption;

	private int sortTypeOption;

	private int dateSortOption;

	private int textAttachOption;

	private int menuButtonOption;

	private int unreadMessageOption;

	private int previewOption;

	private int previewSizeOption;

	private int deleteOption;

	private int chkdelconfirmOption;

	public MailOption() {

	}

	public int getChkdelconfirmOption() {
		return chkdelconfirmOption;
	}

	public void setChkdelconfirmOption(int chkdelconfirmOption) {
		this.chkdelconfirmOption = chkdelconfirmOption;
	}

	public int getConfirmOption() {
		return confirmOption;
	}

	public void setConfirmOption(int confirmOption) {
		this.confirmOption = confirmOption;
	}

	public int getDateSortOption() {
		return dateSortOption;
	}

	public void setDateSortOption(int dateSortOption) {
		this.dateSortOption = dateSortOption;
	}

	public int getDeleteOption() {
		return deleteOption;
	}

	public void setDeleteOption(int deleteOption) {
		this.deleteOption = deleteOption;
	}

	public int getForwardAttachOption() {
		return forwardAttachOption;
	}

	public void setForwardAttachOption(int forwardAttachOption) {
		this.forwardAttachOption = forwardAttachOption;
	}

	public int getForwardMessageOption() {
		return forwardMessageOption;
	}

	public void setForwardMessageOption(int forwardMessageOption) {
		this.forwardMessageOption = forwardMessageOption;
	}

	public int getIcalwinOption() {
		return icalwinOption;
	}

	public void setIcalwinOption(int icalwinOption) {
		this.icalwinOption = icalwinOption;
	}

	public int getInitCharOption() {
		return initCharOption;
	}

	public void setInitCharOption(int initCharOption) {
		this.initCharOption = initCharOption;
	}

	public int getJavascriptOption() {
		return javascriptOption;
	}

	public void setJavascriptOption(int javascriptOption) {
		this.javascriptOption = javascriptOption;
	}

	public int getMenuButtonOption() {
		return menuButtonOption;
	}

	public void setMenuButtonOption(int menuButtonOption) {
		this.menuButtonOption = menuButtonOption;
	}

	public int getMsgCountOption() {
		return msgCountOption;
	}

	public void setMsgCountOption(int msgCountOption) {
		this.msgCountOption = msgCountOption;
	}

	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public int getPreviewOption() {
		return previewOption;
	}

	public void setPreviewOption(int previewOption) {
		this.previewOption = previewOption;
	}

	public int getPreviewSizeOption() {
		return previewSizeOption;
	}

	public void setPreviewSizeOption(int previewSizeOption) {
		this.previewSizeOption = previewSizeOption;
	}

	public int getReplyOption() {
		return replyOption;
	}

	public void setReplyOption(int replyOption) {
		this.replyOption = replyOption;
	}

	public int getSentOption() {
		return sentOption;
	}

	public void setSentOption(int sentOption) {
		this.sentOption = sentOption;
	}

	public int getSignOption() {
		return signOption;
	}

	public void setSignOption(int signOption) {
		this.signOption = signOption;
	}

	public int getSortTypeOption() {
		return sortTypeOption;
	}

	public void setSortTypeOption(int sortTypeOption) {
		this.sortTypeOption = sortTypeOption;
	}

	public int getTextAttachOption() {
		return textAttachOption;
	}

	public void setTextAttachOption(int textAttachOption) {
		this.textAttachOption = textAttachOption;
	}

	public int getUnreadMessageOption() {
		return unreadMessageOption;
	}

	public void setUnreadMessageOption(int unreadMessageOption) {
		this.unreadMessageOption = unreadMessageOption;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}