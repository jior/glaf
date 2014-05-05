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
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * 节点树模型
 * 
 */

public interface TreeModel extends Serializable,
		java.lang.Comparable<TreeModel> {

	/**
	 * 添加子节点
	 * 
	 * @param treeModel
	 */
	void addChild(TreeModel treeModel);

	/**
	 * 返回子节点
	 * 
	 * @return
	 */
	List<TreeModel> getChildren();

	/**
	 * 代码
	 * 
	 * @return
	 */
	String getCode();

	String getCreateBy();

	Date getCreateDate();

	Map<String, Object> getDataMap();

	String getDescription();

	String getDiscriminator();

	String getIcon();

	String getIconCls();

	/**
	 * 主键
	 * 
	 * @return
	 */
	long getId();

	int getLevel();

	int getLocked();

	/**
	 * 名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 父节点
	 * 
	 * @return
	 */
	TreeModel getParent();

	/**
	 * 父节点编号
	 * 
	 * @return
	 */
	long getParentId();

	/**
	 * 获取顺序号
	 * 
	 * @return
	 */
	int getSortNo();

	String getTreeId();

	String getUpdateBy();

	Date getUpdateDate();

	String getUrl();

	boolean isChecked();

	/**
	 * 删除子节点
	 * 
	 */
	void removeChild(TreeModel treeModel);

	void setChecked(boolean checked);

	/**
	 * 设置子节点
	 * 
	 */
	void setChildren(List<TreeModel> children);

	void setCode(String code);

	void setCreateBy(String createBy);

	void setCreateDate(Date createDate);

	void setDataMap(Map<String, Object> dataMap);

	void setDescription(String description);

	void setDiscriminator(String discriminator);

	void setIcon(String icon);

	void setIconCls(String iconCls);

	void setId(long id);

	void setLevel(int level);

	void setLocked(int locked);

	void setName(String name);

	void setParent(TreeModel parent);

	void setParentId(long parentId);

	void setSortNo(int sortNo);

	void setTreeId(String treeId);

	void setUpdateBy(String updateBy);

	void setUpdateDate(Date updateDate);

	void setUrl(String url);

	JSONObject toJsonObject();

	ObjectNode toObjectNode();

}