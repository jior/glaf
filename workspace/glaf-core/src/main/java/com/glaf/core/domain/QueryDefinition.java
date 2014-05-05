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

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.QueryDefinitionJsonFactory;

@Entity
@Table(name = "SYS_QUERY")
public class QueryDefinition implements java.io.Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	@Transient
	protected QueryDefinition child = null;

	@Transient
	protected List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();

	/**
	 * 查询SQL语句
	 */
	@Lob
	@Column(name = "COUNTSQL_")
	protected String countSql;

	/**
	 * MyBatis查询语句的select编号
	 */
	@Column(name = "COUNTSTATEMENTID_", length = 100)
	protected String countStatementId;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_")
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	/**
	 * detailUrl
	 */
	@Column(name = "DETAILURL_", length = 250)
	protected String detailUrl;

	/**
	 * 数据源名称
	 */
	@Column(name = "DSNAME_")
	protected String dsName;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "IDFIELD_", length = 50)
	protected String idField;

	/**
	 * ListUrl
	 */
	@Column(name = "LISTURL_", length = 250)
	protected String listUrl;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 别名
	 */
	@Column(name = "MAPPING_", length = 50)
	protected String mapping;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	@Transient
	protected List<ColumnDefinition> parameters = new java.util.ArrayList<ColumnDefinition>();

	/**
	 * 参数类型
	 */
	@Column(name = "PARAMETERTYPE_", length = 200)
	protected String parameterType;

	@Transient
	protected List<Object> paramList = new java.util.ArrayList<Object>();

	@Transient
	protected QueryDefinition parent = null;

	/**
	 * 父查询编号
	 */
	@Column(name = "PARENTID_", length = 50)
	protected String parentId;

	@Transient
	protected List<Map<String, Object>> resultList = new java.util.ArrayList<Map<String, Object>>();

	/**
	 * 返回值类型
	 */
	@Column(name = "RESULTTYPE_", length = 200)
	protected String resultType;

	/**
	 * 修订版本
	 */
	@Column(name = "REVISION_")
	protected int revision;

	/**
	 * 服务标识
	 */
	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	/**
	 * 查询SQL语句
	 */
	@Lob
	@Column(name = "SQL_")
	protected String sql;

	/**
	 * MyBatis查询语句的select编号
	 */
	@Column(name = "STATEMENTID_", length = 100)
	protected String statementId;

	/**
	 * 目标表名
	 */
	@Column(name = "TARGETTABLENAME_", length = 50)
	protected String targetTableName;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_")
	protected String title;

	/**
	 * 类别
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "NODEID_")
	protected Long nodeId;

	public QueryDefinition() {

	}

	public void addColumn(ColumnDefinition column) {
		if (columns == null) {
			columns = new java.util.ArrayList<ColumnDefinition>();
		}
		column.setDiscriminator("C");
		columns.add(column);
	}

	public void addParameter(ColumnDefinition parameter) {
		if (parameters == null) {
			parameters = new java.util.ArrayList<ColumnDefinition>();
		}
		parameter.setDiscriminator("P");
		parameters.add(parameter);
	}

	public void addResult(Map<String, Object> row) {
		if (resultList == null) {
			resultList = new java.util.ArrayList<Map<String, Object>>();
		}
		resultList.add(row);
	}

	public void addResult(Object parameter) {
		if (paramList == null) {
			paramList = new java.util.ArrayList<Object>();
		}
		paramList.add(parameter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryDefinition other = (QueryDefinition) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public QueryDefinition getChild() {
		return child;
	}

	public List<ColumnDefinition> getColumns() {
		return columns;
	}

	public String getCountSql() {
		return countSql;
	}

	public String getCountStatementId() {
		return countStatementId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public String getDescription() {
		return description;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public String getDsName() {
		return dsName;
	}

	public String getId() {
		return id;
	}

	public String getIdField() {
		return idField;
	}

	public String getListUrl() {
		return listUrl;
	}

	public int getLocked() {
		return locked;
	}

	public String getMapping() {
		return mapping;
	}

	public String getName() {
		return name;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public List<ColumnDefinition> getParameters() {
		return parameters;
	}

	public String getParameterType() {
		return parameterType;
	}

	public List<Object> getParamList() {
		return paramList;
	}

	public QueryDefinition getParent() {
		return parent;
	}

	public String getParentId() {
		return parentId;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public String getResultType() {
		return resultType;
	}

	public int getRevision() {
		return revision;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getSql() {
		return sql;
	}

	public String getStatementId() {
		return statementId;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public QueryDefinition jsonToObject(JSONObject jsonObject) {
		return QueryDefinitionJsonFactory.jsonToObject(jsonObject);
	}

	public void setChild(QueryDefinition child) {
		this.child = child;
	}

	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public void setCountStatementId(String countStatementId) {
		this.countStatementId = countStatementId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setParameters(List<ColumnDefinition> parameters) {
		this.parameters = parameters;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public void setParamList(List<Object> paramList) {
		this.paramList = paramList;
	}

	public void setParent(QueryDefinition parent) {
		this.parent = parent;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject toJsonObject() {
		return QueryDefinitionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return QueryDefinitionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}