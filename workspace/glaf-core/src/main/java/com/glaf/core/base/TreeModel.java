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
 * �ڵ���ģ��
 * 
 */

public interface TreeModel extends Serializable,
		java.lang.Comparable<TreeModel> {

	/**
	 * ����ӽڵ�
	 * 
	 * @param treeModel
	 */
	void addChild(TreeModel treeModel);

	/**
	 * �����ӽڵ�
	 * 
	 * @return
	 */
	List<TreeModel> getChildren();

	/**
	 * ����
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
	 * ����
	 * 
	 * @return
	 */
	long getId();

	int getLevel();

	int getLocked();

	/**
	 * ����
	 * 
	 * @return
	 */
	String getName();

	/**
	 * ���ڵ�
	 * 
	 * @return
	 */
	TreeModel getParent();

	/**
	 * ���ڵ���
	 * 
	 * @return
	 */
	long getParentId();

	/**
	 * ��ȡ˳���
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
	 * ɾ���ӽڵ�
	 * 
	 */
	void removeChild(TreeModel treeModel);

	void setChecked(boolean checked);

	/**
	 * �����ӽڵ�
	 * 
	 */
	void setChildren(List<TreeModel> children);

	void setCode(String code);

	void setCreateBy(String createBy);

	void setCreateDate(Date createDate);

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

}