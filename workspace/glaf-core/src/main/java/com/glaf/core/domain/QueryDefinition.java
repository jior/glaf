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

import java.util.ArrayList;
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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "SYS_QUERY")
public class QueryDefinition implements java.io.Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 父查询编号
	 */
	@Column(name = "PARENTID_", length = 50)
	protected String parentId;

	/**
	 * 目标表名
	 */
	@Column(name = "TARGETTABLENAME_", length = 50)
	protected String targetTableName;

	/**
	 * 服务标识
	 */
	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_")
	protected String title;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 别名
	 */
	@Column(name = "MAPPING_", length = 50)
	protected String mapping;

	/**
	 * 类别
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	/**
	 * 数据源名称
	 */
	@Column(name = "DSNAME_")
	protected String dsName;

	/**
	 * 查询SQL语句
	 */
	@Lob
	@Column(name = "SQL_")
	protected String sql;

	/**
	 * 查询SQL语句
	 */
	@Lob
	@Column(name = "COUNTSQL_")
	protected String countSql;

	@Column(name = "IDFIELD_", length = 50)
	protected String idField;

	/**
	 * MyBatis查询语句的select编号
	 */
	@Column(name = "STATEMENTID_", length = 100)
	protected String statementId;

	/**
	 * MyBatis查询语句的select编号
	 */
	@Column(name = "COUNTSTATEMENTID_", length = 100)
	protected String countStatementId;

	/**
	 * 参数类型
	 */
	@Column(name = "PARAMETERTYPE_", length = 200)
	protected String parameterType;

	/**
	 * 返回值类型
	 */
	@Column(name = "RESULTTYPE_", length = 200)
	protected String resultType;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_")
	protected String createBy;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	/**
	 * 修订版本
	 */
	@Column(name = "REVISION_")
	protected int revision;

	/**
	 * ListUrl
	 */
	@Column(name = "LISTURL_", length = 250)
	protected String listUrl;

	/**
	 * detailUrl
	 */
	@Column(name = "DETAILURL_", length = 250)
	protected String detailUrl;

	@Transient
	protected QueryDefinition parent = null;

	@Transient
	protected QueryDefinition child = null;

	@Transient
	protected List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

	@Transient
	protected List<ColumnDefinition> parameters = new ArrayList<ColumnDefinition>();

	@Transient
	protected List<Object> paramList = new ArrayList<Object>();

	@Transient
	protected List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

	public QueryDefinition() {

	}

	public void addColumn(ColumnDefinition column) {
		if (columns == null) {
			columns = new ArrayList<ColumnDefinition>();
		}
		column.setDiscriminator("C");
		columns.add(column);
	}

	public void addParameter(ColumnDefinition parameter) {
		if (parameters == null) {
			parameters = new ArrayList<ColumnDefinition>();
		}
		parameter.setDiscriminator("P");
		parameters.add(parameter);
	}

	public void addResult(Map<String, Object> row) {
		if (resultList == null) {
			resultList = new ArrayList<Map<String, Object>>();
		}
		resultList.add(row);
	}

	public void addResult(Object parameter) {
		if (paramList == null) {
			paramList = new ArrayList<Object>();
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

	public QueryDefinition jsonToObject(JSONObject jsonObject) {
		QueryDefinition model = new QueryDefinition();
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getString("parentId"));
		}
		if (jsonObject.containsKey("targetTableName")) {
			model.setTargetTableName(jsonObject.getString("targetTableName"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("mapping")) {
			model.setMapping(jsonObject.getString("mapping"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("dsName")) {
			model.setDsName(jsonObject.getString("dsName"));
		}
		if (jsonObject.containsKey("sql")) {
			model.setSql(jsonObject.getString("sql"));
		}
		if (jsonObject.containsKey("countSql")) {
			model.setCountSql(jsonObject.getString("countSql"));
		}
		if (jsonObject.containsKey("idField")) {
			model.setIdField(jsonObject.getString("idField"));
		}
		if (jsonObject.containsKey("statementId")) {
			model.setStatementId(jsonObject.getString("statementId"));
		}
		if (jsonObject.containsKey("countStatementId")) {
			model.setCountStatementId(jsonObject.getString("countStatementId"));
		}
		if (jsonObject.containsKey("parameterType")) {
			model.setParameterType(jsonObject.getString("parameterType"));
		}
		if (jsonObject.containsKey("resultType")) {
			model.setResultType(jsonObject.getString("resultType"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("revision")) {
			model.setRevision(jsonObject.getInteger("revision"));
		}
		if (jsonObject.containsKey("listUrl")) {
			model.setListUrl(jsonObject.getString("listUrl"));
		}
		if (jsonObject.containsKey("detailUrl")) {
			model.setDetailUrl(jsonObject.getString("detailUrl"));
		}
		return model;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (parentId != null) {
			jsonObject.put("parentId", parentId);
		}
		if (targetTableName != null) {
			jsonObject.put("targetTableName", targetTableName);
		}
		if (serviceKey != null) {
			jsonObject.put("serviceKey", serviceKey);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (mapping != null) {
			jsonObject.put("mapping", mapping);
		}
		if (type != null) {
			jsonObject.put("type", type);
		}
		if (description != null) {
			jsonObject.put("description", description);
		}
		if (dsName != null) {
			jsonObject.put("dsName", dsName);
		}
		if (sql != null) {
			jsonObject.put("sql", sql);
		}
		if (countSql != null) {
			jsonObject.put("countSql", countSql);
		}
		if (idField != null) {
			jsonObject.put("idField", idField);
		}
		if (statementId != null) {
			jsonObject.put("statementId", statementId);
		}
		if (countStatementId != null) {
			jsonObject.put("countStatementId", countStatementId);
		}
		if (parameterType != null) {
			jsonObject.put("parameterType", parameterType);
		}
		if (resultType != null) {
			jsonObject.put("resultType", resultType);
		}
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		jsonObject.put("locked", locked);
		jsonObject.put("deleteFlag", deleteFlag);
		jsonObject.put("revision", revision);
		if (listUrl != null) {
			jsonObject.put("listUrl", listUrl);
		}
		if (detailUrl != null) {
			jsonObject.put("detailUrl", detailUrl);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (parentId != null) {
			jsonObject.put("parentId", parentId);
		}
		if (targetTableName != null) {
			jsonObject.put("targetTableName", targetTableName);
		}
		if (serviceKey != null) {
			jsonObject.put("serviceKey", serviceKey);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (mapping != null) {
			jsonObject.put("mapping", mapping);
		}
		if (type != null) {
			jsonObject.put("type", type);
		}
		if (description != null) {
			jsonObject.put("description", description);
		}
		if (dsName != null) {
			jsonObject.put("dsName", dsName);
		}
		if (sql != null) {
			jsonObject.put("sql", sql);
		}
		if (countSql != null) {
			jsonObject.put("countSql", countSql);
		}
		if (idField != null) {
			jsonObject.put("idField", idField);
		}
		if (statementId != null) {
			jsonObject.put("statementId", statementId);
		}
		if (countStatementId != null) {
			jsonObject.put("countStatementId", countStatementId);
		}
		if (parameterType != null) {
			jsonObject.put("parameterType", parameterType);
		}
		if (resultType != null) {
			jsonObject.put("resultType", resultType);
		}
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		jsonObject.put("locked", locked);
		jsonObject.put("deleteFlag", deleteFlag);
		jsonObject.put("revision", revision);
		if (listUrl != null) {
			jsonObject.put("listUrl", listUrl);
		}
		if (detailUrl != null) {
			jsonObject.put("detailUrl", detailUrl);
		}
		return jsonObject;
	}

}