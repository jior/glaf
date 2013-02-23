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

	String getAttribute();

	/**
	 * ��ȡ��ͼ��
	 * 
	 * @return
	 */
	String getBigIcon();

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

	/**
	 * ����
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * Сͼ��
	 * 
	 * @return
	 */
	String getIcon();

	/**
	 * ͼ����ʽ
	 * 
	 * @return
	 */
	String getIconCls();

	/**
	 * ����
	 * 
	 * @return
	 */
	int getId();

	int getLevel();

	/**
	 * ����
	 * 
	 * @return
	 */
	int getLocked();

	/**
	 * ����
	 * 
	 * @return
	 */
	String getName();

	/**
	 * �ڵ�����
	 * 
	 * @return
	 */
	String getNodeType();

	String getObjectId();

	String getObjectValue();

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
	int getParentId();

	/**
	 * ��Ŀ���
	 * 
	 * @return
	 */
	String getProjectId();

	/**
	 * �������ƻ�ȡ����ֵ
	 * 
	 * @return
	 */
	String getProperty(String name);

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	int getReadAccessLevel();

	/**
	 * ��ȡ˳���
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
	 * д����
	 * 
	 * @return
	 */
	int getWriteAccessLevel();

	boolean isLeaf();

	boolean isWriteProtected();

	/**
	 * ɾ���ӽڵ�
	 * 
	 */
	void removeChild(TreeModel treeModel);

	void setAttribute(String attribute);

	/**
	 * �����ӽڵ�
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