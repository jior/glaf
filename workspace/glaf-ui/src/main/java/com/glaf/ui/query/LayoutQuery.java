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

package com.glaf.ui.query;

import java.util.*;
import com.glaf.core.query.*;

public class LayoutQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String titleLike;
	protected String templateId;
	protected List<String> templateIds;
	protected String spaceStyleLike;
	protected String columnStyleLike;

	public LayoutQuery() {

	}

	public LayoutQuery columnStyleLike(String columnStyleLike) {
		if (columnStyleLike == null) {
			throw new RuntimeException("columnStyle is null");
		}
		this.columnStyleLike = columnStyleLike;
		return this;
	}

	public String getColumnStyleLike() {
		return columnStyleLike;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public String getSpaceStyleLike() {
		return spaceStyleLike;
	}

	public String getTemplateId() {
		return templateId;
	}

	public List<String> getTemplateIds() {
		return templateIds;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public LayoutQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public LayoutQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public LayoutQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setColumnStyleLike(String columnStyleLike) {
		this.columnStyleLike = columnStyleLike;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setSpaceStyleLike(String spaceStyleLike) {
		this.spaceStyleLike = spaceStyleLike;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTemplateIds(List<String> templateIds) {
		this.templateIds = templateIds;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public LayoutQuery spaceStyleLike(String spaceStyleLike) {
		if (spaceStyleLike == null) {
			throw new RuntimeException("spaceStyle is null");
		}
		this.spaceStyleLike = spaceStyleLike;
		return this;
	}

	public LayoutQuery templateId(String templateId) {
		if (templateId == null) {
			throw new RuntimeException("templateId is null");
		}
		this.templateId = templateId;
		return this;
	}

	public LayoutQuery templateIds(List<String> templateIds) {
		if (templateIds == null) {
			throw new RuntimeException("templateIds is empty ");
		}
		this.templateIds = templateIds;
		return this;
	}

	public LayoutQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}