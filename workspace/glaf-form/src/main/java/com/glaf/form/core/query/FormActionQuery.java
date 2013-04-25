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
package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormActionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String activity;
	protected String activityLike;
	protected String appId;
	protected List<String> appIds;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String descriptionLike;
	protected String name;
	protected String nameLike;
	protected String provider;
	protected String titleLike;
	protected String urlLike;

	public FormActionQuery() {

	}

	public FormActionQuery activity(String activity) {
		if (activity == null) {
			throw new RuntimeException("activity is null");
		}
		this.activity = activity;
		return this;
	}

	public FormActionQuery activityLike(String activityLike) {
		if (activityLike == null) {
			throw new RuntimeException("activity is null");
		}
		this.activityLike = activityLike;
		return this;
	}

	public FormActionQuery appId(String appId) {
		if (appId == null) {
			throw new RuntimeException("appId is null");
		}
		this.appId = appId;
		return this;
	}

	public FormActionQuery appIds(List<String> appIds) {
		if (appIds == null) {
			throw new RuntimeException("appIds is empty ");
		}
		this.appIds = appIds;
		return this;
	}

	public FormActionQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public FormActionQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FormActionQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FormActionQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public String getActivity() {
		return activity;
	}

	public String getActivityLike() {
		if (activityLike != null && activityLike.trim().length() > 0) {
			if (!activityLike.startsWith("%")) {
				activityLike = "%" + activityLike;
			}
			if (!activityLike.endsWith("%")) {
				activityLike = activityLike + "%";
			}
		}
		return activityLike;
	}

	public String getAppId() {
		return appId;
	}

	public List<String> getAppIds() {
		return appIds;
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

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("url".equals(sortColumn)) {
				orderBy = "E.URL_" + a_x;
			}

			if ("provider".equals(sortColumn)) {
				orderBy = "E.PROVIDER_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("description".equals(sortColumn)) {
				orderBy = "E.DESCRIPTION_" + a_x;
			}

			if ("activity".equals(sortColumn)) {
				orderBy = "E.ACTIVITY_" + a_x;
			}

			if ("formName".equals(sortColumn)) {
				orderBy = "E.FORMNAME_" + a_x;
			}

			if ("appId".equals(sortColumn)) {
				orderBy = "E.APPID_" + a_x;
			}

		}
		return orderBy;
	}

	public String getProvider() {
		return provider;
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

	public String getUrlLike() {
		if (urlLike != null && urlLike.trim().length() > 0) {
			if (!urlLike.startsWith("%")) {
				urlLike = "%" + urlLike;
			}
			if (!urlLike.endsWith("%")) {
				urlLike = urlLike + "%";
			}
		}
		return urlLike;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("name", "NAME_");
		addColumn("title", "TITLE_");
		addColumn("url", "URL_");
		addColumn("provider", "PROVIDER_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("description", "DESCRIPTION_");
		addColumn("activity", "ACTIVITY_");
		addColumn("formName", "FORMNAME_");
		addColumn("appId", "APPID_");
	}

	public FormActionQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormActionQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FormActionQuery provider(String provider) {
		if (provider == null) {
			throw new RuntimeException("provider is null");
		}
		this.provider = provider;
		return this;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public void setActivityLike(String activityLike) {
		this.activityLike = activityLike;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppIds(List<String> appIds) {
		this.appIds = appIds;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setUrlLike(String urlLike) {
		this.urlLike = urlLike;
	}

	public FormActionQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public FormActionQuery urlLike(String urlLike) {
		if (urlLike == null) {
			throw new RuntimeException("url is null");
		}
		this.urlLike = urlLike;
		return this;
	}

}