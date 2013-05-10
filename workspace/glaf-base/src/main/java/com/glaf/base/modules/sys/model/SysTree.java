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

package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.base.modules.sys.util.SysTreeJsonFactory;

import com.glaf.core.base.JSONable;
import com.glaf.core.base.TreeModel;

@Entity
@Table(name = "sys_tree")
public class SysTree implements Serializable, TreeModel, JSONable {
	private static final long serialVersionUID = 2666681837822864771L;

	@javax.persistence.Transient
	protected SysApplication app;

	@javax.persistence.Transient
	protected String cacheFlag;

	@javax.persistence.Transient
	protected boolean checked;

	@javax.persistence.Transient
	protected List<TreeModel> children = new ArrayList<TreeModel>();

	/**
	 * 编码
	 */
	@Column(name = "CODE", length = 50)
	protected String code;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	protected Date createDate;

	@javax.persistence.Transient
	protected Map<String, Object> dataMap;

	@javax.persistence.Transient
	protected int deep;

	@javax.persistence.Transient
	protected SysDepartment department;

	/**
	 * 节点描述
	 */
	@Column(name = "NODEDESC", length = 500)
	protected String desc;

	@Column(name = "DISCRIMINATOR", length = 10)
	protected String discriminator;

	/**
	 * 图标
	 */
	@Column(name = "icon", length = 50)
	protected String icon;

	/**
	 * 图标样式
	 */
	@Column(name = "iconCls", length = 50)
	protected String iconCls;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 是否启用
	 */
	@Column(name = "locked")
	protected int locked;

	@Column(name = "MOVEABLE", length = 10)
	protected String moveable;

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 100)
	protected String name;

	@javax.persistence.Transient
	protected TreeModel parent;

	/**
	 * 父节点编号
	 */
	@Column(name = "PARENT")
	protected long parentId;

	/**
	 * 序号
	 */
	@Column(name = "SORT")
	protected int sort;

	@Column(name = "TREEID", length = 500)
	protected String treeId;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY", length = 50)
	protected String updateBy;

	/**
	 * 修改日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE")
	protected Date updateDate;

	/**
	 * url
	 */
	@Column(name = "url", length = 500)
	protected String url;

	public SysTree() {

	}

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
		SysTree other = (SysTree) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public SysApplication getApp() {
		return app;
	}

	public String getCacheFlag() {
		return cacheFlag;
	}

	public List<TreeModel> getChildren() {
		return children;
	}

	public String getCode() {
		return code;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public int getDeep() {
		return deep;
	}

	public SysDepartment getDepartment() {
		return department;
	}

	public String getDesc() {
		return desc;
	}

	public String getDescription() {
		return desc;
	}

	public String getDiscriminator() {
		return discriminator;
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

	public String getMoveable() {
		return moveable;
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

	public String getTreeId() {
		return treeId;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
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

	public boolean isChecked() {
		return checked;
	}

	public SysTree jsonToObject(JSONObject jsonObject) {
		return SysTreeJsonFactory.jsonToObject(jsonObject);
	}

	@Override
	public void removeChild(TreeModel treeModel) {
		if (children != null) {
			children.remove(treeModel);
		}
	}

	public void setApp(SysApplication app) {
		this.app = app;
	}

	public void setCacheFlag(String cacheFlag) {
		this.cacheFlag = cacheFlag;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setDescription(String description) {
		this.desc = description;

	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
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

	public void setMoveable(String moveable) {
		this.moveable = moveable;
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

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject toJsonObject() {
		return SysTreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysTreeJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}