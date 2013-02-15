/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.core.mail.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Mail implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �ʼ����
	 */
	private String mailId;

	/**
	 * �ʼ���Ϣ���
	 */
	private String messageId;

	/**
	 * ������
	 */
	private String taskId;

	/**
	 * �ʼ�ģ��
	 */
	private String templateId;

	/**
	 * �û����
	 */
	private String userId;

	/**
	 * �ʼ��ʺ�
	 */
	private String accountId;

	/**
	 * ������
	 */
	private String mailFrom;

	/**
	 * �ռ���
	 */
	private String mailTo;

	/**
	 * ������
	 */
	private String mailCC;

	/**
	 * ������
	 */
	private String mailBCC;

	/**
	 * �ظ���ַ
	 */
	private String mailReplyTo;

	/**
	 * �ʼ�����
	 */
	private String mailSubject;

	/**
	 * �ʼ�����
	 */
	private String mailText;

	/**
	 * �ʼ�HTML����
	 */
	private String mailHtml;

	/**
	 * �ʼ���С
	 */
	private int mailSize;

	/**
	 * ���ȼ�
	 */
	private int priority;

	/**
	 * ��������
	 */
	private Date sendDate;

	/**
	 * ����״̬
	 */
	private int sendStatus;

	/**
	 * ���Դ���
	 */
	private int retryTimes;

	/**
	 * ��������
	 */
	private Date receiveDate;

	/**
	 * ��������
	 */
	private Date createDate;

	/**
	 * �޸�����
	 */
	private Date modifyDate;

	/**
	 * �ʼ�����
	 */
	private String mailType;

	/**
	 * �ʼ�״̬
	 */
	private String mailStatus;

	/**
	 * �ʼ�����״̬
	 */
	private int receiveStatus;

	/**
	 * �ʼ�Ŀ¼
	 */
	private String mailFolder;

	/**
	 * �ʼ�·��
	 */
	private String mailPath;

	/**
	 * �汾��
	 */
	private long versionNo;

	/**
	 * �������
	 */
	private int viewCount;

	/**
	 * ���һ�η�������
	 */
	private Date lastViewDate;

	/**
	 * ���һ�η���IP��ַ
	 */
	private String lastViewIP;

	/**
	 * ��չ����
	 */
	private String attribute;

	/**
	 * ͷ��Ϣ
	 */
	private String headerBuffer;

	/**
	 * ������Ϣ
	 */
	private String attachBuffer;

	/**
	 * 
	 */
	private Collection headers = new ArrayList();

	/**
	 * 
	 */
	private Collection attachments = new ArrayList();

	public Mail() {

	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Collection getAttachments() {
		return attachments != null ? attachments : new ArrayList();
	}

	public void setAttachments(Collection attachments) {
		this.attachments = attachments;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Collection getHeaders() {
		return headers != null ? headers : new ArrayList();
	}

	public void setHeaders(Collection headers) {
		this.headers = headers;
	}

	public String getMailBCC() {
		return mailBCC;
	}

	public void setMailBCC(String mailBCC) {
		this.mailBCC = mailBCC;
	}

	public String getMailCC() {
		return mailCC;
	}

	public void setMailCC(String mailCC) {
		this.mailCC = mailCC;
	}

	public String getMailFolder() {
		return mailFolder;
	}

	public void setMailFolder(String mailFolder) {
		this.mailFolder = mailFolder;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailHtml() {
		return mailHtml;
	}

	public void setMailHtml(String mailHtml) {
		this.mailHtml = mailHtml;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getMailPath() {
		return mailPath;
	}

	public void setMailPath(String mailPath) {
		this.mailPath = mailPath;
	}

	public String getMailReplyTo() {
		return mailReplyTo;
	}

	public void setMailReplyTo(String mailReplyTo) {
		this.mailReplyTo = mailReplyTo;
	}

	public int getMailSize() {
		return mailSize;
	}

	public void setMailSize(int mailSize) {
		this.mailSize = mailSize;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailText() {
		return mailText;
	}

	public void setMailText(String mailText) {
		this.mailText = mailText;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public int getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(int receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHeaderBuffer() {
		if (headerBuffer == null) {
			headerBuffer = "";
		}
		return headerBuffer;
	}

	public void setHeaderBuffer(String headerBuffer) {
		this.headerBuffer = headerBuffer;
	}

	public String getAttachBuffer() {
		if (attachBuffer == null) {
			attachBuffer = "";
		}
		return attachBuffer;
	}

	public void setAttachBuffer(String attachBuffer) {
		this.attachBuffer = attachBuffer;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public Date getLastViewDate() {
		return lastViewDate;
	}

	public void setLastViewDate(Date lastViewDate) {
		this.lastViewDate = lastViewDate;
	}

	public String getLastViewIP() {
		return lastViewIP;
	}

	public void setLastViewIP(String lastViewIP) {
		this.lastViewIP = lastViewIP;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


}