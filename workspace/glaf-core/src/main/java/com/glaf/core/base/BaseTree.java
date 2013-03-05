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

package com.glaf.core.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.base.JSONable;
import com.glaf.core.base.TreeModel;
import com.glaf.core.tree.util.TreeJsonFactory;

public class BaseTree implements Serializable, TreeModel, JSONable {
	private static final long serialVersionUID = 2666681837822864771L;
	protected long id;
	protected long parentId;
	protected String name;
	protected String desc;
	protected int sort;
	protected String code;
	protected int deep;
	protected String icon;
	protected String url;
	protected String iconCls;
	protected int locked;
	protected TreeModel parent;
	protected List<TreeModel> children = new ArrayList<TreeModel>();

	public void addChild(TreeModel treeModel) {
		if (children == null) {
			children = new ArrayList<TreeModel>();
		}
		children.add(treeModel);
	}

	public int compareTo(TreeModel o) {
		if (o == null) {
			return -1;
		}

		TreeModel obj = o;

		int l = this.sort - obj.getSortNo();

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseTree other = (BaseTree) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public List<TreeModel> getChildren() {
		return children;
	}

	public String getCode() {
		return code;
	}

	public Map<String, Object> getDataMap() {
		return null;
	}

	public int getDeep() {
		return deep;
	}

	public String getDesc() {
		return desc;
	}

	public String getDescription() {
		return desc;
	}

	public String getIcon() {
		return icon;
	}

	public String getIconCls() {
		return iconCls;
	}

	public long getId() {
		return id;
	}

	public int getLevel() {
		return deep;
	}

	public int getLocked() {
		return locked;
	}

	public String getName() {
		return name;
	}

	public TreeModel getParent() {
		return parent;
	}

	public long getParentId() {
		return parentId;
	}

	public int getSort() {
		return sort;
	}

	public int getSortNo() {
		return sort;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public BaseTree jsonToObject(JSONObject jsonObject) {
		return TreeJsonFactory.jsonToObject(jsonObject);
	}

	@Override
	public void removeChild(TreeModel treeModel) {
		if (children != null) {
			children.remove(treeModel);
		}
	}

	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setDescription(String description) {
		this.desc = description;

	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel(int level) {
		deep = level;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(TreeModel parent) {
		this.parent = parent;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setSortNo(int sortNo) {
		this.sort = sortNo;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject toJsonObject() {
		return TreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TreeJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}