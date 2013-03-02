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

public class PanelQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String titleLike;
	protected String iconLike;
	protected String moduleId;
	protected List<String> moduleIds;
	protected String moduleNameLike;
	protected String linkLike;
	protected String moreLinkLike;

	public PanelQuery() {

	}

	public String getIconLike() {
		return iconLike;
	}

	public String getLinkLike() {
		return linkLike;
	}

	public String getModuleId() {
		return moduleId;
	}

	public List<String> getModuleIds() {
		return moduleIds;
	}

	public String getModuleNameLike() {
		return moduleNameLike;
	}

	public String getMoreLinkLike() {
		return moreLinkLike;
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

	public PanelQuery iconLike(String iconLike) {
		if (iconLike == null) {
			throw new RuntimeException("icon is null");
		}
		this.iconLike = iconLike;
		return this;
	}

	public PanelQuery linkLike(String linkLike) {
		if (linkLike == null) {
			throw new RuntimeException("link is null");
		}
		this.linkLike = linkLike;
		return this;
	}

	public PanelQuery moduleId(String moduleId) {
		if (moduleId == null) {
			throw new RuntimeException("moduleId is null");
		}
		this.moduleId = moduleId;
		return this;
	}

	public PanelQuery moduleIds(List<String> moduleIds) {
		if (moduleIds == null) {
			throw new RuntimeException("moduleIds is empty ");
		}
		this.moduleIds = moduleIds;
		return this;
	}

	public PanelQuery moduleNameLike(String moduleNameLike) {
		if (moduleNameLike == null) {
			throw new RuntimeException("moduleName is null");
		}
		this.moduleNameLike = moduleNameLike;
		return this;
	}

	public PanelQuery moreLinkLike(String moreLinkLike) {
		if (moreLinkLike == null) {
			throw new RuntimeException("moreLink is null");
		}
		this.moreLinkLike = moreLinkLike;
		return this;
	}

	public PanelQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public PanelQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public PanelQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setIconLike(String iconLike) {
		this.iconLike = iconLike;
	}

	public void setLinkLike(String linkLike) {
		this.linkLike = linkLike;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setModuleIds(List<String> moduleIds) {
		this.moduleIds = moduleIds;
	}

	public void setModuleNameLike(String moduleNameLike) {
		this.moduleNameLike = moduleNameLike;
	}

	public void setMoreLinkLike(String moreLinkLike) {
		this.moreLinkLike = moreLinkLike;
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

	public PanelQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}