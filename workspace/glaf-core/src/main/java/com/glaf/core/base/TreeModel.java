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

	String getAttribute();

	/**
	 * 获取大图标
	 * 
	 * @return
	 */
	String getBigIcon();

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

	/**
	 * 描述
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * 小图标
	 * 
	 * @return
	 */
	String getIcon();

	/**
	 * 图标样式
	 * 
	 * @return
	 */
	String getIconCls();

	/**
	 * 主键
	 * 
	 * @return
	 */
	int getId();

	int getLevel();

	/**
	 * 锁定
	 * 
	 * @return
	 */
	int getLocked();

	/**
	 * 名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 节点类型
	 * 
	 * @return
	 */
	String getNodeType();

	String getObjectId();

	String getObjectValue();

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
	int getParentId();

	/**
	 * 项目编号
	 * 
	 * @return
	 */
	String getProjectId();

	/**
	 * 根据名称获取属性值
	 * 
	 * @return
	 */
	String getProperty(String name);

	/**
	 * 读取级别
	 * 
	 * @return
	 */
	int getReadAccessLevel();

	/**
	 * 获取顺序号
	 * 
	 * @return
	 */
	int getSortNo();

	String getTreeId();

	String getTreeType();

	/**
	 * URL
	 * 
	 * @return
	 */
	String getUrl();

	/**
	 * 写级别
	 * 
	 * @return
	 */
	int getWriteAccessLevel();

	boolean isLeaf();

	boolean isWriteProtected();

	/**
	 * 删除子节点
	 * 
	 */
	void removeChild(TreeModel treeModel);

	void setAttribute(String attribute);

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

	void setIcon(String icon);

	void setIconCls(String iconCls);

	void setId(int id);

	void setLeaf(boolean leaf);

	void setLevel(int level);

	void setLocked(int locked);

	void setName(String name);

	void setNodeType(String nodeType);

	void setObjectId(String objectId);

	void setObjectValue(String objectValue);

	void setParent(TreeModel parent);

	void setParentId(int parentId);

	void setProjectId(String projectId);

	void setProperty(String name, String value);

	void setSortNo(int sortNo);

	void setTreeId(String treeId);

	void setTreeType(String treeType);

	void setUrl(String url);

	void setWriteProtected(boolean isWriteProtected);

	JSONObject toJSONObject();

}