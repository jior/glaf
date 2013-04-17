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

package com.glaf.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.TreeModel;
import com.glaf.core.query.TreeModelQuery;

@Transactional(readOnly = true)
public interface ITreeModelService {

	/**
	 * ��ȡĳ���ڵ���������Ƚڵ�
	 * 
	 * @param treeId
	 * @return
	 */
	List<TreeModel> getAncestorTreeModels(long id);

	/**
	 * ��ȡĳ���ڵ����������ڵ�
	 * 
	 * @param treeId
	 * @return
	 */
	List<TreeModel> getChildrenTreeModels(long id);

	/**
	 * ��ȡĳ���ڵ��µ��ӽڵ�
	 * 
	 * @param treeId
	 * @return
	 */
	List<TreeModel> getSubTreeModels(long id);

	/**
	 * ����������ȡ���ڵ�
	 * 
	 * @param treeId
	 * @return
	 */
	TreeModel getTreeModel(long id);

	/**
	 * ���ݱ����ȡ���ڵ�
	 * 
	 * @param code
	 * @return
	 */
	TreeModel getTreeModelByCode(String code);

	/**
	 * ��ȡ���ͽṹ
	 * 
	 * @return
	 */
	List<TreeModel> getTreeModels();

	/**
	 * ��ȡ���ͽṹ
	 * 
	 * @param parameter
	 * @return
	 */
	List<TreeModel> getTreeModels(TreeModelQuery query);

	/**
	 * ��ȡĳ���ڵ����������ڵ�
	 * 
	 * @param treeId
	 * @return
	 */
	TreeModel getTreeModelWithAllChildren(long id);

	/**
	 * ����ڵ�
	 * 
	 * @param treeModel
	 */
	@Transactional
	void save(TreeModel treeModel);

	/**
	 * ����ڵ�
	 * 
	 * @param treeModels
	 */
	@Transactional
	void saveAll(List<TreeModel> treeModels);
 

}