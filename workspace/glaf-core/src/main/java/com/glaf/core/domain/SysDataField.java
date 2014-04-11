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

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.domain.util.*;

@Entity
@Table(name = "SYS_DATA_FIELD")
public class SysDataField implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 80, nullable = false)
	protected String id;

	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	@Column(name = "TABLENAME_", length = 50)
	protected String tablename;

	@Column(name = "COLUMNNAME_", length = 50)
	protected String columnName;

	@Column(name = "NAME_", length = 50)
	protected String name;

	@Column(name = "TITLE_", length = 100)
	protected String title;

	@Column(name = "FRMTYPE_", length = 50)
	protected String frmType;

	@Column(name = "DATATYPE_", length = 20)
	protected String dataType;

	@Column(name = "LENGTH_")
	protected Integer length;

	@Column(name = "LISTWEIGTH_")
	protected Integer listWeigth;

	@Column(name = "PRIMARYKEY_", length = 5)
	protected String primaryKey;

	@Column(name = "SYSTEMFLAG_", length = 5)
	protected String systemFlag;

	@Column(name = "INPUTTYPE_", length = 50)
	protected String inputType;

	@Column(name = "DISPLAYTYPE_")
	protected Integer displayType;

	@Column(name = "IMPORTTYPE_")
	protected Integer importType;

	@Column(name = "FORMATTER_", length = 50)
	protected String formatter;

	@Column(name = "SEARCHABLE_", length = 5)
	protected String searchable;

	@Column(name = "EDITABLE_", length = 5)
	protected String editable;

	@Column(name = "UPDATEABLE_", length = 5)
	protected String updatable;

	@Column(name = "FORMULA_", length = 100)
	protected String formula;

	@Column(name = "MASK_", length = 100)
	protected String mask;

	@Column(name = "QUERYID_", length = 50)
	protected String queryId;

	@Column(name = "VALUEFIELD_", length = 50)
	protected String valueField;

	@Column(name = "TEXTFIELD_", length = 50)
	protected String textField;

	@Column(name = "VALIDTYPE_", length = 50)
	protected String validType;

	@Column(name = "REQUIRED_", length = 5)
	protected String required;

	@Column(name = "INITVALUE_", length = 200)
	protected String initValue;

	@Column(name = "DEFAULTVALUE_", length = 100)
	protected String defaultValue;

	@Column(name = "VALUEEXPRESSION_", length = 100)
	protected String valueExpression;

	@Column(name = "SORTABLE_", length = 5)
	protected String sortable;

	@Column(name = "ORDINAL_")
	protected Integer ordinal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	public SysDataField() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysDataField other = (SysDataField) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getDataType() {
		return this.dataType;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public Integer getDisplayType() {
		return this.displayType;
	}

	public String getEditable() {
		return this.editable;
	}

	public String getFormatter() {
		return this.formatter;
	}

	public String getFormula() {
		return this.formula;
	}

	public String getFrmType() {
		return this.frmType;
	}

	public String getId() {
		return this.id;
	}

	public Integer getImportType() {
		return this.importType;
	}

	public String getInitValue() {
		return this.initValue;
	}

	public String getInputType() {
		return this.inputType;
	}

	public Integer getLength() {
		return this.length;
	}

	public Integer getListWeigth() {
		return this.listWeigth;
	}

	public String getMask() {
		return this.mask;
	}

	public String getName() {
		return this.name;
	}

	public Integer getOrdinal() {
		return this.ordinal;
	}

	public String getPrimaryKey() {
		return this.primaryKey;
	}

	public String getQueryId() {
		return this.queryId;
	}

	public String getRequired() {
		return this.required;
	}

	public String getSearchable() {
		return this.searchable;
	}

	public String getServiceKey() {
		return this.serviceKey;
	}

	public String getSortable() {
		return this.sortable;
	}

	public String getSystemFlag() {
		return this.systemFlag;
	}

	public String getTablename() {
		return this.tablename;
	}

	public String getTextField() {
		return this.textField;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUpdatable() {
		return this.updatable;
	}

	public String getValidType() {
		return this.validType;
	}

	public String getValueExpression() {
		return this.valueExpression;
	}

	public String getValueField() {
		return this.valueField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SysDataField jsonToObject(JSONObject jsonObject) {
		return SysDataFieldJsonFactory.jsonToObject(jsonObject);
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setFrmType(String frmType) {
		this.frmType = frmType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImportType(Integer importType) {
		this.importType = importType;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public void setListWeigth(Integer listWeigth) {
		this.listWeigth = listWeigth;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setSearchable(String searchable) {
		this.searchable = searchable;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpdatable(String updatable) {
		this.updatable = updatable;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public JSONObject toJsonObject() {
		return SysDataFieldJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysDataFieldJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
