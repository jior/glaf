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

public class SysDataQuery extends DataQuery {
	private static final long serialVersionUID = 1L;

	protected String titleLike;

	protected String descriptionLike;

	protected String pathLike;

	protected String permsLike;

	protected String addressPermsLike;

	protected String type;

	protected Date createDateGreaterThanOrEqual;

	protected Date createDateLessThanOrEqual;

	protected String updateBy;

	protected Date updateDateGreaterThanOrEqual;

	protected Date updateDateLessThanOrEqual;

	public SysDataQuery() {

	}

	public SysDataQuery addressPermsLike(String addressPermsLike) {
		if (addressPermsLike == null) {
			throw new RuntimeException("addressPerms is null");
		}
		this.addressPermsLike = addressPermsLike;
		return this;
	}

	public SysDataQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public SysDataQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public SysDataQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public String getAddressPermsLike() {
		if (addressPermsLike != null && addressPermsLike.trim().length() > 0) {
			if (!addressPermsLike.startsWith("%")) {
				addressPermsLike = "%" + addressPermsLike;
			}
			if (!addressPermsLike.endsWith("%")) {
				addressPermsLike = addressPermsLike + "%";
			}
		}
		return addressPermsLike;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public String getDescriptionLike() {
		if (descriptionLike != null && descriptionLike.trim().length() > 0) {
			if (!descriptionLike.startsWith("%")) {
				descriptionLike = "%" + descriptionLike;
			}
			if (!descriptionLike.endsWith("%")) {
				descriptionLike = descriptionLike + "%";
			}
		}
		return descriptionLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("description".equals(sortColumn)) {
				orderBy = "E.DESCRIPTION_" + a_x;
			}

			if ("path".equals(sortColumn)) {
				orderBy = "E.PATH_" + a_x;
			}

			if ("perms".equals(sortColumn)) {
				orderBy = "E.PERMS_" + a_x;
			}

			if ("addressPerms".equals(sortColumn)) {
				orderBy = "E.ADDRESSPERMS_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.UPDATEDATE_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

		}
		return orderBy;
	}

	public String getPathLike() {
		if (pathLike != null && pathLike.trim().length() > 0) {
			if (!pathLike.startsWith("%")) {
				pathLike = "%" + pathLike;
			}
			if (!pathLike.endsWith("%")) {
				pathLike = pathLike + "%";
			}
		}
		return pathLike;
	}

	public String getPermsLike() {
		if (permsLike != null && permsLike.trim().length() > 0) {
			if (!permsLike.startsWith("%")) {
				permsLike = "%" + permsLike;
			}
			if (!permsLike.endsWith("%")) {
				permsLike = permsLike + "%";
			}
		}
		return permsLike;
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

	public String getType() {
		return type;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("title", "TITLE_");
		addColumn("description", "DESCRIPTION_");
		addColumn("path", "PATH_");
		addColumn("perms", "PERMS_");
		addColumn("addressPerms", "ADDRESSPERMS_");
		addColumn("type", "TYPE_");
		addColumn("locked", "LOCKED_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
		addColumn("updateDate", "UPDATEDATE_");
		addColumn("updateBy", "UPDATEBY_");
	}

	public SysDataQuery locked(Integer locked) {
		if (locked == null) {
			throw new RuntimeException("locked is null");
		}
		this.locked = locked;
		return this;
	}

	public SysDataQuery pathLike(String pathLike) {
		if (pathLike == null) {
			throw new RuntimeException("path is null");
		}
		this.pathLike = pathLike;
		return this;
	}

	public SysDataQuery permsLike(String permsLike) {
		if (permsLike == null) {
			throw new RuntimeException("perms is null");
		}
		this.permsLike = permsLike;
		return this;
	}

	public void setAddressPermsLike(String addressPermsLike) {
		this.addressPermsLike = addressPermsLike;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setPathLike(String pathLike) {
		this.pathLike = pathLike;
	}

	public void setPermsLike(String permsLike) {
		this.permsLike = permsLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public SysDataQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public SysDataQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public SysDataQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public SysDataQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public SysDataQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

}