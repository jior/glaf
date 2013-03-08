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
package com.glaf.base.modules.workspace.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MessageQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> rowIds;
	protected Integer type;
	protected Integer typeGreaterThanOrEqual;
	protected Integer typeLessThanOrEqual;
	protected List<Integer> types;
	protected Integer sysType;
	protected Integer sysTypeGreaterThanOrEqual;
	protected Integer sysTypeLessThanOrEqual;
	protected List<Integer> sysTypes;

	protected String recverListLike;

	protected String titleLike;

	protected String contentLike;

	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	protected Integer readed;
	protected Integer readedGreaterThanOrEqual;
	protected Integer readedLessThanOrEqual;

	protected Integer category;
	protected Integer categoryGreaterThanOrEqual;
	protected Integer categoryLessThanOrEqual;
	protected List<Integer> categorys;
	protected Long senderId;
	protected Long senderIdGreaterThanOrEqual;
	protected Long senderIdLessThanOrEqual;
	protected List<Long> senderIds;
	protected Long recverId;
	protected Long recverIdGreaterThanOrEqual;
	protected Long recverIdLessThanOrEqual;
	protected List<Long> recverIds;

	public MessageQuery() {

	}

	public Integer getType() {
		return type;
	}

	public Integer getTypeGreaterThanOrEqual() {
		return typeGreaterThanOrEqual;
	}

	public Integer getTypeLessThanOrEqual() {
		return typeLessThanOrEqual;
	}

	public List<Integer> getTypes() {
		return types;
	}

	public Integer getSysType() {
		return sysType;
	}

	public Integer getSysTypeGreaterThanOrEqual() {
		return sysTypeGreaterThanOrEqual;
	}

	public Integer getSysTypeLessThanOrEqual() {
		return sysTypeLessThanOrEqual;
	}

	public List<Integer> getSysTypes() {
		return sysTypes;
	}

	public String getRecverListLike() {
		if (recverListLike != null && recverListLike.trim().length() > 0) {
			if (!recverListLike.startsWith("%")) {
				recverListLike = "%" + recverListLike;
			}
			if (!recverListLike.endsWith("%")) {
				recverListLike = recverListLike + "%";
			}
		}
		return recverListLike;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	public String getContentLike() {
		if (contentLike != null && contentLike.trim().length() > 0) {
			if (!contentLike.startsWith("%")) {
				contentLike = "%" + contentLike;
			}
			if (!contentLike.endsWith("%")) {
				contentLike = contentLike + "%";
			}
		}
		return contentLike;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public Integer getReaded() {
		return readed;
	}

	public Integer getReadedGreaterThanOrEqual() {
		return readedGreaterThanOrEqual;
	}

	public Integer getReadedLessThanOrEqual() {
		return readedLessThanOrEqual;
	}

	public Integer getCategory() {
		return category;
	}

	public Integer getCategoryGreaterThanOrEqual() {
		return categoryGreaterThanOrEqual;
	}

	public Integer getCategoryLessThanOrEqual() {
		return categoryLessThanOrEqual;
	}

	public List<Integer> getCategorys() {
		return categorys;
	}

	public Long getSenderId() {
		return senderId;
	}

	public Long getSenderIdGreaterThanOrEqual() {
		return senderIdGreaterThanOrEqual;
	}

	public Long getSenderIdLessThanOrEqual() {
		return senderIdLessThanOrEqual;
	}

	public List<Long> getSenderIds() {
		return senderIds;
	}

	public Long getRecverId() {
		return recverId;
	}

	public Long getRecverIdGreaterThanOrEqual() {
		return recverIdGreaterThanOrEqual;
	}

	public Long getRecverIdLessThanOrEqual() {
		return recverIdLessThanOrEqual;
	}

	public List<Long> getRecverIds() {
		return recverIds;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
	}

	public void setTypeLessThanOrEqual(Integer typeLessThanOrEqual) {
		this.typeLessThanOrEqual = typeLessThanOrEqual;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}

	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}

	public void setSysTypeGreaterThanOrEqual(Integer sysTypeGreaterThanOrEqual) {
		this.sysTypeGreaterThanOrEqual = sysTypeGreaterThanOrEqual;
	}

	public void setSysTypeLessThanOrEqual(Integer sysTypeLessThanOrEqual) {
		this.sysTypeLessThanOrEqual = sysTypeLessThanOrEqual;
	}

	public void setRecverListLike(String recverListLike) {
		this.recverListLike = recverListLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setReaded(Integer readed) {
		this.readed = readed;
	}

	public void setReadedGreaterThanOrEqual(Integer readedGreaterThanOrEqual) {
		this.readedGreaterThanOrEqual = readedGreaterThanOrEqual;
	}

	public void setReadedLessThanOrEqual(Integer readedLessThanOrEqual) {
		this.readedLessThanOrEqual = readedLessThanOrEqual;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public void setCategoryGreaterThanOrEqual(Integer categoryGreaterThanOrEqual) {
		this.categoryGreaterThanOrEqual = categoryGreaterThanOrEqual;
	}

	public void setCategoryLessThanOrEqual(Integer categoryLessThanOrEqual) {
		this.categoryLessThanOrEqual = categoryLessThanOrEqual;
	}

	public void setCategorys(List<Integer> categorys) {
		this.categorys = categorys;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public void setSenderIdGreaterThanOrEqual(Long senderIdGreaterThanOrEqual) {
		this.senderIdGreaterThanOrEqual = senderIdGreaterThanOrEqual;
	}

	public void setSenderIdLessThanOrEqual(Long senderIdLessThanOrEqual) {
		this.senderIdLessThanOrEqual = senderIdLessThanOrEqual;
	}

	public void setSenderIds(List<Long> senderIds) {
		this.senderIds = senderIds;
	}

	public void setRecverId(Long recverId) {
		this.recverId = recverId;
	}

	public void setRecverIdGreaterThanOrEqual(Long recverIdGreaterThanOrEqual) {
		this.recverIdGreaterThanOrEqual = recverIdGreaterThanOrEqual;
	}

	public void setRecverIdLessThanOrEqual(Long recverIdLessThanOrEqual) {
		this.recverIdLessThanOrEqual = recverIdLessThanOrEqual;
	}

	public void setRecverIds(List<Long> recverIds) {
		this.recverIds = recverIds;
	}

	public MessageQuery type(Integer type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public MessageQuery typeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		if (typeGreaterThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
		return this;
	}

	public MessageQuery typeLessThanOrEqual(Integer typeLessThanOrEqual) {
		if (typeLessThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLessThanOrEqual = typeLessThanOrEqual;
		return this;
	}

	public MessageQuery types(List<Integer> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public MessageQuery sysType(Integer sysType) {
		if (sysType == null) {
			throw new RuntimeException("sysType is null");
		}
		this.sysType = sysType;
		return this;
	}

	public MessageQuery sysTypeGreaterThanOrEqual(
			Integer sysTypeGreaterThanOrEqual) {
		if (sysTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("sysType is null");
		}
		this.sysTypeGreaterThanOrEqual = sysTypeGreaterThanOrEqual;
		return this;
	}

	public MessageQuery sysTypeLessThanOrEqual(Integer sysTypeLessThanOrEqual) {
		if (sysTypeLessThanOrEqual == null) {
			throw new RuntimeException("sysType is null");
		}
		this.sysTypeLessThanOrEqual = sysTypeLessThanOrEqual;
		return this;
	}

	public MessageQuery sysTypes(List<Integer> sysTypes) {
		if (sysTypes == null) {
			throw new RuntimeException("sysTypes is empty ");
		}
		this.sysTypes = sysTypes;
		return this;
	}

	public MessageQuery recverListLike(String recverListLike) {
		if (recverListLike == null) {
			throw new RuntimeException("recverList is null");
		}
		this.recverListLike = recverListLike;
		return this;
	}

	public MessageQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public MessageQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public MessageQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public MessageQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public MessageQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public MessageQuery readed(Integer readed) {
		if (readed == null) {
			throw new RuntimeException("readed is null");
		}
		this.readed = readed;
		return this;
	}

	public MessageQuery readedGreaterThanOrEqual(
			Integer readedGreaterThanOrEqual) {
		if (readedGreaterThanOrEqual == null) {
			throw new RuntimeException("readed is null");
		}
		this.readedGreaterThanOrEqual = readedGreaterThanOrEqual;
		return this;
	}

	public MessageQuery readedLessThanOrEqual(Integer readedLessThanOrEqual) {
		if (readedLessThanOrEqual == null) {
			throw new RuntimeException("readed is null");
		}
		this.readedLessThanOrEqual = readedLessThanOrEqual;
		return this;
	}

	public MessageQuery category(Integer category) {
		if (category == null) {
			throw new RuntimeException("category is null");
		}
		this.category = category;
		return this;
	}

	public MessageQuery categoryGreaterThanOrEqual(
			Integer categoryGreaterThanOrEqual) {
		if (categoryGreaterThanOrEqual == null) {
			throw new RuntimeException("category is null");
		}
		this.categoryGreaterThanOrEqual = categoryGreaterThanOrEqual;
		return this;
	}

	public MessageQuery categoryLessThanOrEqual(Integer categoryLessThanOrEqual) {
		if (categoryLessThanOrEqual == null) {
			throw new RuntimeException("category is null");
		}
		this.categoryLessThanOrEqual = categoryLessThanOrEqual;
		return this;
	}

	public MessageQuery categorys(List<Integer> categorys) {
		if (categorys == null) {
			throw new RuntimeException("categorys is empty ");
		}
		this.categorys = categorys;
		return this;
	}

	public MessageQuery senderId(Long senderId) {
		if (senderId == null) {
			throw new RuntimeException("senderId is null");
		}
		this.senderId = senderId;
		return this;
	}

	public MessageQuery senderIdGreaterThanOrEqual(
			Long senderIdGreaterThanOrEqual) {
		if (senderIdGreaterThanOrEqual == null) {
			throw new RuntimeException("senderId is null");
		}
		this.senderIdGreaterThanOrEqual = senderIdGreaterThanOrEqual;
		return this;
	}

	public MessageQuery senderIdLessThanOrEqual(Long senderIdLessThanOrEqual) {
		if (senderIdLessThanOrEqual == null) {
			throw new RuntimeException("senderId is null");
		}
		this.senderIdLessThanOrEqual = senderIdLessThanOrEqual;
		return this;
	}

	public MessageQuery senderIds(List<Long> senderIds) {
		if (senderIds == null) {
			throw new RuntimeException("senderIds is empty ");
		}
		this.senderIds = senderIds;
		return this;
	}

	public MessageQuery recverId(Long recverId) {
		if (recverId == null) {
			throw new RuntimeException("recverId is null");
		}
		this.recverId = recverId;
		return this;
	}

	public MessageQuery recverIdGreaterThanOrEqual(
			Long recverIdGreaterThanOrEqual) {
		if (recverIdGreaterThanOrEqual == null) {
			throw new RuntimeException("recverId is null");
		}
		this.recverIdGreaterThanOrEqual = recverIdGreaterThanOrEqual;
		return this;
	}

	public MessageQuery recverIdLessThanOrEqual(Long recverIdLessThanOrEqual) {
		if (recverIdLessThanOrEqual == null) {
			throw new RuntimeException("recverId is null");
		}
		this.recverIdLessThanOrEqual = recverIdLessThanOrEqual;
		return this;
	}

	public MessageQuery recverIds(List<Long> recverIds) {
		if (recverIds == null) {
			throw new RuntimeException("recverIds is empty ");
		}
		this.recverIds = recverIds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE" + a_x;
			}

			if ("sysType".equals(sortColumn)) {
				orderBy = "E.SYSTYPE" + a_x;
			}

			if ("recverList".equals(sortColumn)) {
				orderBy = "E.RECVERLIST" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE" + a_x;
			}

			if ("readed".equals(sortColumn)) {
				orderBy = "E.READED" + a_x;
			}

			if ("category".equals(sortColumn)) {
				orderBy = "E.CATEGORY" + a_x;
			}

			if ("senderId".equals(sortColumn)) {
				orderBy = "E.SENDER" + a_x;
			}

			if ("recverId".equals(sortColumn)) {
				orderBy = "E.RECVER" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("type", "TYPE");
		addColumn("sysType", "SYSTYPE");
		addColumn("recverList", "RECVERLIST");
		addColumn("title", "TITLE");
		addColumn("content", "CONTENT");
		addColumn("createDate", "CREATEDATE");
		addColumn("readed", "READED");
		addColumn("category", "CATEGORY");
		addColumn("senderId", "SENDER");
		addColumn("recverId", "RECVER");
	}

}