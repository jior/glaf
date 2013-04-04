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

public class SystemPropertyQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String category;
	protected String descriptionLike;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String titleLike;
	protected String type;
	protected String valueLike;

	public SystemPropertyQuery() {

	}

	public SystemPropertyQuery category(String category) {
		if (category == null) {
			throw new RuntimeException("category is null");
		}
		this.category = category;
		return this;
	}

	public SystemPropertyQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public String getDescriptionLike() {
		return descriptionLike;
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

	public String getTitleLike() {
		return titleLike;
	}

	public String getType() {
		return type;
	}

	public String getValueLike() {
		return valueLike;
	}

	public SystemPropertyQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public SystemPropertyQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public SystemPropertyQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValueLike(String valueLike) {
		this.valueLike = valueLike;
	}

	public SystemPropertyQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public SystemPropertyQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public SystemPropertyQuery valueLike(String valueLike) {
		if (valueLike == null) {
			throw new RuntimeException("value is null");
		}
		this.valueLike = valueLike;
		return this;
	}

}