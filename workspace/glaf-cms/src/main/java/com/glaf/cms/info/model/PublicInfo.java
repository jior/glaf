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
package com.glaf.cms.info.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.cms.info.util.*;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "CMS_PUBLICINFO")
public class PublicInfo implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	@Column(name = "AUTHOR_", length = 200)
	protected String author;

	/**
	 * ����Ŀ
	 */
	@Column(name = "BURY_")
	protected Integer bury;

	/**
	 * ��������
	 */
	@Column(name = "COMMENTCOUNT_")
	protected Integer commentCount;

	/**
	 * ���۱��
	 */
	@Column(name = "COMMENTFLAG_")
	protected Integer commentFlag;

	/**
	 * ����
	 */
	@Lob
	@Column(name = "CONTENT_")
	protected String content;

	/**
	 * ������
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * ��������
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * ɾ�����
	 */
	@Column(name = "DELETEFLAG_")
	protected Integer deleteFlag;

	/**
	 * ����Ŀ
	 */
	@Column(name = "DIGG_")
	protected Integer digg;

	/**
	 * ��������
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE_")
	protected Date endDate;

	/**
	 * �˻ر��
	 */
	@Column(name = "FALLBACKFLAG_", length = 10)
	protected String fallbackFlag;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * �ؼ���
	 */
	@Column(name = "KEYWORDS_", length = 200)
	protected String keywords;

	/**
	 * ����
	 */
	@Column(name = "LINK_", length = 250)
	protected String link;

	/**
	 * ����
	 */
	@Column(name = "NAME_", length = 200)
	protected String name;

	/**
	 * �ڵ���
	 */
	@Column(name = "NODEID_")
	protected Long nodeId;

	/**
	 * �Ƿ�ԭ��
	 */
	@Column(name = "ORIGINALFLAG_")
	protected Integer originalFlag;

	/**
	 * ����ʵ�����
	 */
	@Column(name = "PROCESSINSTANCEID_")
	protected Long processInstanceId;

	/**
	 * ��������
	 */
	@Column(name = "PROCESSNAME_", length = 50)
	protected String processName;

	/**
	 * �������
	 */
	@Column(name = "PUBLISHFLAG_")
	protected Integer publishFlag;

	/**
	 * ���õ�ַ
	 */
	@Column(name = "REFERERURL_", length = 200)
	protected String refererUrl;

	/**
	 * �����ʶ
	 */
	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	/**
	 * ���
	 */
	@Column(name = "SORTNO_")
	protected Integer sortNo;

	/**
	 * ��ʼ����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE_")
	protected Date startDate;

	/**
	 * ״̬
	 */
	@Column(name = "STATUS_")
	protected Integer status;

	/**
	 * ����
	 */
	@Column(name = "SUBJECT_", length = 200)
	protected String subject;

	/**
	 * ժҪ
	 */
	@Column(name = "SUMMARY_", length = 500)
	protected String summary;

	/**
	 * Tag
	 */
	@Column(name = "TAG_", length = 200)
	protected String tag;

	/**
	 * ������λ
	 */
	@Column(name = "UNITNAME_", length = 200)
	protected String unitName;

	/**
	 * ������
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * �޸�����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	/**
	 * �鿴����
	 */
	@Column(name = "VIEWCOUNT_")
	protected Integer viewCount;

	/**
	 * ������״̬
	 */
	@Column(name = "WFSTATUS_")
	protected Integer wfStatus;

	public PublicInfo() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicInfo other = (PublicInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAuthor() {
		return this.author;
	}

	public Integer getBury() {
		return this.bury;
	}

	public Integer getCommentCount() {
		return this.commentCount;
	}

	public Integer getCommentFlag() {
		return this.commentFlag;
	}

	public String getContent() {
		return this.content;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public Integer getDigg() {
		return this.digg;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public String getFallbackFlag() {
		return this.fallbackFlag;
	}

	public String getId() {
		return this.id;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public String getLink() {
		return this.link;
	}

	public String getName() {
		return this.name;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public Integer getOriginalFlag() {
		return this.originalFlag;
	}

	public Long getProcessInstanceId() {
		return this.processInstanceId;
	}

	public String getProcessName() {
		return this.processName;
	}

	public Integer getPublishFlag() {
		return this.publishFlag;
	}

	public String getRefererUrl() {
		return this.refererUrl;
	}

	public String getServiceKey() {
		return this.serviceKey;
	}

	public Integer getSortNo() {
		return this.sortNo;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getSummary() {
		return summary;
	}

	public String getTag() {
		return this.tag;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public Integer getViewCount() {
		return this.viewCount;
	}

	public Integer getWfStatus() {
		return this.wfStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public PublicInfo jsonToObject(JSONObject jsonObject) {
		return PublicInfoJsonFactory.jsonToObject(jsonObject);
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setBury(Integer bury) {
		this.bury = bury;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public void setCommentFlag(Integer commentFlag) {
		this.commentFlag = commentFlag;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDigg(Integer digg) {
		this.digg = digg;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setFallbackFlag(String fallbackFlag) {
		this.fallbackFlag = fallbackFlag;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setOriginalFlag(Integer originalFlag) {
		this.originalFlag = originalFlag;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setPublishFlag(Integer publishFlag) {
		this.publishFlag = publishFlag;
	}

	public void setRefererUrl(String refererUrl) {
		this.refererUrl = refererUrl;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public void setWfStatus(Integer wfStatus) {
		this.wfStatus = wfStatus;
	}

	public JSONObject toJsonObject() {
		return PublicInfoJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return PublicInfoJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
