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
package com.glaf.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.EntityDefinitionJsonFactory;

@Entity
@Table(name = "SYS_ENTITY")
public class EntityDefinition implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 聚合主键
	 */
	@Column(name = "AGGREGATIONKEYS_", length = 200)
	protected String aggregationKeys;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@javax.persistence.Transient
	protected byte[] data;

	/**
	 * 文件内容
	 */
	@Lob
	@Column(name = "FILECONTENT_")
	protected String fileContent;

	@Column(name = "FILENAME_", length = 200)
	protected String filename;

	/**
	 * 文件前缀
	 */
	@Column(name = "FILEPREFIX_", length = 200)
	protected String filePrefix;

	@Id
	@Column(name = "ID_", length = 100, nullable = false)
	protected String id;

	/**
	 * 是否只做Insert
	 */
	@Column(name = "INSERTONLY_", length = 20)
	protected String insertOnly;

	/**
	 * 类型
	 */
	@Column(name = "JAVATYPE_", length = 200)
	protected String javaType;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 100)
	protected String name;

	/**
	 * 节点编号
	 */
	@Column(name = "NODEID_")
	protected Long nodeId;

	/**
	 * 解析类型
	 */
	@Column(name = "PARSETYPE_", length = 20)
	protected String parseType;

	/**
	 * 主键
	 */
	@Column(name = "primaryKey", length = 50)
	protected String primaryKey;

	/**
	 * 开始行
	 */
	@Column(name = "STARTROW_")
	protected Integer startRow;

	/**
	 * 结束词条
	 */
	@Column(name = "STOPWORD_")
	protected String stopWord;

	/**
	 * 数据表
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tablename;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * 修改日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	public EntityDefinition() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityDefinition other = (EntityDefinition) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAggregationKeys() {
		return this.aggregationKeys;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public byte[] getData() {
		return data;
	}

	public String getFileContent() {
		return this.fileContent;
	}

	public String getFilename() {
		return filename;
	}

	public String getFilePrefix() {
		return this.filePrefix;
	}

	public String getId() {
		return this.id;
	}

	public String getInsertOnly() {
		return this.insertOnly;
	}

	public String getJavaType() {
		return this.javaType;
	}

	public String getName() {
		return this.name;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public String getParseType() {
		return this.parseType;
	}

	public String getPrimaryKey() {
		return this.primaryKey;
	}

	public Integer getStartRow() {
		return this.startRow;
	}

	public String getStopWord() {
		return this.stopWord;
	}

	public String getTablename() {
		return this.tablename;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public EntityDefinition jsonToObject(JSONObject jsonObject) {
		return EntityDefinitionJsonFactory.jsonToObject(jsonObject);
	}

	public void setAggregationKeys(String aggregationKeys) {
		this.aggregationKeys = aggregationKeys;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInsertOnly(String insertOnly) {
		this.insertOnly = insertOnly;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setParseType(String parseType) {
		this.parseType = parseType;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public void setStopWord(String stopWord) {
		this.stopWord = stopWord;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public JSONObject toJsonObject() {
		return EntityDefinitionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EntityDefinitionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
