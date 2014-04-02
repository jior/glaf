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

package com.glaf.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysDataFieldQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String tablename;
	protected List<String> tablenames;
	protected String titleLike;
	protected String frmType;
	protected String systemFlag;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public SysDataFieldQuery() {

	}

	public SysDataFieldQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public SysDataFieldQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public SysDataFieldQuery frmType(String frmType) {
		if (frmType == null) {
			throw new RuntimeException("frmType is null");
		}
		this.frmType = frmType;
		return this;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getFrmType() {
		return frmType;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("serviceKey".equals(sortColumn)) {
				orderBy = "E.SERVICEKEY_" + a_x;
			}

			if ("tablename".equals(sortColumn)) {
				orderBy = "E.TABLENAME_" + a_x;
			}

			if ("columnName".equals(sortColumn)) {
				orderBy = "E.COLUMNNAME_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("frmType".equals(sortColumn)) {
				orderBy = "E.FRMTYPE_" + a_x;
			}

			if ("dataType".equals(sortColumn)) {
				orderBy = "E.DATATYPE_" + a_x;
			}

			if ("length".equals(sortColumn)) {
				orderBy = "E.LENGTH_" + a_x;
			}

			if ("listWeigth".equals(sortColumn)) {
				orderBy = "E.LISTWEIGTH_" + a_x;
			}

			if ("primaryKey".equals(sortColumn)) {
				orderBy = "E.PRIMARYKEY_" + a_x;
			}

			if ("systemFlag".equals(sortColumn)) {
				orderBy = "E.SYSTEMFLAG_" + a_x;
			}

			if ("inputType".equals(sortColumn)) {
				orderBy = "E.INPUTTYPE_" + a_x;
			}

			if ("displayType".equals(sortColumn)) {
				orderBy = "E.DISPLAYTYPE_" + a_x;
			}

			if ("importType".equals(sortColumn)) {
				orderBy = "E.IMPORTTYPE_" + a_x;
			}

			if ("formatter".equals(sortColumn)) {
				orderBy = "E.FORMATTER_" + a_x;
			}

			if ("searchable".equals(sortColumn)) {
				orderBy = "E.SEARCHABLE_" + a_x;
			}

			if ("editable".equals(sortColumn)) {
				orderBy = "E.EDITABLE_" + a_x;
			}

			if ("updatable".equals(sortColumn)) {
				orderBy = "E.UPDATEABLE_" + a_x;
			}

			if ("formula".equals(sortColumn)) {
				orderBy = "E.FORMULA_" + a_x;
			}

			if ("mask".equals(sortColumn)) {
				orderBy = "E.MASK_" + a_x;
			}

			if ("queryId".equals(sortColumn)) {
				orderBy = "E.QUERYID_" + a_x;
			}

			if ("valueField".equals(sortColumn)) {
				orderBy = "E.VALUEFIELD_" + a_x;
			}

			if ("textField".equals(sortColumn)) {
				orderBy = "E.TEXTFIELD_" + a_x;
			}

			if ("validType".equals(sortColumn)) {
				orderBy = "E.VALIDTYPE_" + a_x;
			}

			if ("required".equals(sortColumn)) {
				orderBy = "E.REQUIRED_" + a_x;
			}

			if ("initValue".equals(sortColumn)) {
				orderBy = "E.INITVALUE_" + a_x;
			}

			if ("defaultValue".equals(sortColumn)) {
				orderBy = "E.DEFAULTVALUE_" + a_x;
			}

			if ("valueExpression".equals(sortColumn)) {
				orderBy = "E.VALUEEXPRESSION_" + a_x;
			}

			if ("sortable".equals(sortColumn)) {
				orderBy = "E.SORTABLE_" + a_x;
			}

			if ("ordinal".equals(sortColumn)) {
				orderBy = "E.ORDINAL_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

		}
		return orderBy;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public String getTablename() {
		return tablename;
	}

	public List<String> getTablenames() {
		return tablenames;
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("serviceKey", "SERVICEKEY_");
		addColumn("tablename", "TABLENAME_");
		addColumn("columnName", "COLUMNNAME_");
		addColumn("name", "NAME_");
		addColumn("title", "TITLE_");
		addColumn("frmType", "FRMTYPE_");
		addColumn("dataType", "DATATYPE_");
		addColumn("length", "LENGTH_");
		addColumn("listWeigth", "LISTWEIGTH_");
		addColumn("primaryKey", "PRIMARYKEY_");
		addColumn("systemFlag", "SYSTEMFLAG_");
		addColumn("inputType", "INPUTTYPE_");
		addColumn("displayType", "DISPLAYTYPE_");
		addColumn("importType", "IMPORTTYPE_");
		addColumn("formatter", "FORMATTER_");
		addColumn("searchable", "SEARCHABLE_");
		addColumn("editable", "EDITABLE_");
		addColumn("updatable", "UPDATEABLE_");
		addColumn("formula", "FORMULA_");
		addColumn("mask", "MASK_");
		addColumn("queryId", "QUERYID_");
		addColumn("valueField", "VALUEFIELD_");
		addColumn("textField", "TEXTFIELD_");
		addColumn("validType", "VALIDTYPE_");
		addColumn("required", "REQUIRED_");
		addColumn("initValue", "INITVALUE_");
		addColumn("defaultValue", "DEFAULTVALUE_");
		addColumn("valueExpression", "VALUEEXPRESSION_");
		addColumn("sortable", "SORTABLE_");
		addColumn("ordinal", "ORDINAL_");
		addColumn("createTime", "CREATETIME_");
		addColumn("createBy", "CREATEBY_");
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setFrmType(String frmType) {
		this.frmType = frmType;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTablenames(List<String> tablenames) {
		this.tablenames = tablenames;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public SysDataFieldQuery systemFlag(String systemFlag) {
		if (systemFlag == null) {
			throw new RuntimeException("systemFlag is null");
		}
		this.systemFlag = systemFlag;
		return this;
	}

	public SysDataFieldQuery tablename(String tablename) {
		if (tablename == null) {
			throw new RuntimeException("tablename is null");
		}
		this.tablename = tablename;
		return this;
	}

	public SysDataFieldQuery tablenames(List<String> tablenames) {
		if (tablenames == null) {
			throw new RuntimeException("tablenames is empty ");
		}
		this.tablenames = tablenames;
		return this;
	}

	public SysDataFieldQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}