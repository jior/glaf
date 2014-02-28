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

import java.util.List;

public class SystemParamQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String keyName;
	protected List<String> keyNames;
	protected String stringValLike;
	protected String textValLike;
	protected String titleLike;
	protected String typeCd;
	protected List<String> typeCds;

	public SystemParamQuery() {

	}

	public SystemParamQuery businessKey(String businessKey) {
		if (businessKey == null) {
			throw new RuntimeException("businessKey is null");
		}
		this.businessKey = businessKey;
		return this;
	}

	public String getKeyName() {
		return keyName;
	}

	public List<String> getKeyNames() {
		return keyNames;
	}

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if (columns.get(sortField) != null) {
				orderBy = " E." + columns.get(sortField) + a_x;
			}
		}
		return orderBy;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getStringValLike() {
		if (stringValLike != null && stringValLike.trim().length() > 0) {
			if (!stringValLike.startsWith("%")) {
				stringValLike = "%" + stringValLike;
			}
			if (!stringValLike.endsWith("%")) {
				stringValLike = stringValLike + "%";
			}
		}
		return stringValLike;
	}

	public String getTextValLike() {
		if (textValLike != null && textValLike.trim().length() > 0) {
			if (!textValLike.startsWith("%")) {
				textValLike = "%" + textValLike;
			}
			if (!textValLike.endsWith("%")) {
				textValLike = textValLike + "%";
			}
		}
		return textValLike;
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

	public String getTypeCd() {
		return typeCd;
	}

	public List<String> getTypeCds() {
		return typeCds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "id");
		addColumn("serviceKey", "service_key");
		addColumn("typeCd", "type_cd");
		addColumn("keyName", "key_name");
		addColumn("title", "title");
		addColumn("stringVal", "string_val");
		addColumn("textVal", "text_val");
		addColumn("dateVal", "date_val");
	}

	public SystemParamQuery keyName(String keyName) {
		if (keyName == null) {
			throw new RuntimeException("keyName is null");
		}
		this.keyName = keyName;
		return this;
	}

	public SystemParamQuery keyNames(List<String> keyNames) {
		if (keyNames == null) {
			throw new RuntimeException("keyNames is empty ");
		}
		this.keyNames = keyNames;
		return this;
	}

	public SystemParamQuery serviceKey(String serviceKey) {
		if (serviceKey == null) {
			throw new RuntimeException("serviceKey is null");
		}
		this.serviceKey = serviceKey;
		return this;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setKeyNames(List<String> keyNames) {
		this.keyNames = keyNames;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setStringValLike(String stringValLike) {
		this.stringValLike = stringValLike;
	}

	public void setTextValLike(String textValLike) {
		this.textValLike = textValLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public void setTypeCds(List<String> typeCds) {
		this.typeCds = typeCds;
	}

	public SystemParamQuery stringValLike(String stringValLike) {
		if (stringValLike == null) {
			throw new RuntimeException("stringVal is null");
		}
		this.stringValLike = stringValLike;
		return this;
	}

	public SystemParamQuery textValLike(String textValLike) {
		if (textValLike == null) {
			throw new RuntimeException("textVal is null");
		}
		this.textValLike = textValLike;
		return this;
	}

	public SystemParamQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public SystemParamQuery typeCd(String typeCd) {
		if (typeCd == null) {
			throw new RuntimeException("typeCd is null");
		}
		this.typeCd = typeCd;
		return this;
	}

	public SystemParamQuery typeCds(List<String> typeCds) {
		if (typeCds == null) {
			throw new RuntimeException("typeCds is empty ");
		}
		this.typeCds = typeCds;
		return this;
	}

}