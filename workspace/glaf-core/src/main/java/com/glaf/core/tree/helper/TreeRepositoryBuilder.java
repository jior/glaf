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

package com.glaf.core.tree.helper;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.glaf.core.base.TreeModel;
import com.glaf.core.tree.component.TreeComponent;
import com.glaf.core.tree.component.TreeRepository;

public class TreeRepositoryBuilder {

	public TreeRepository build(List<TreeModel> treeModels) {
		// Collections.sort(treeModels);
		List<TreeModel> nodes = new java.util.ArrayList<TreeModel>();
		Map<String, TreeModel> treeMap = new java.util.HashMap<String, TreeModel>();
		Map<Long, TreeModel> treeModelMap = new java.util.HashMap<Long, TreeModel>();

		for (int i = 0, len = treeModels.size(); i < len; i++) {
			TreeModel treeModel = (TreeModel) treeModels.get(i);
			if (treeModel.getId() == treeModel.getParentId()) {
				treeModel.setParentId(-1);
			}
			treeMap.put(String.valueOf(treeModel.getId()), treeModel);
			if (treeModel.getLocked() == 0) {
				nodes.add(treeModel);
			} else {
				treeModelMap.put(treeModel.getId(), treeModel);
			}
		}

		for (int i = 0, len = nodes.size(); i < len; i++) {
			TreeModel treeModel = nodes.get(i);
			if (treeModelMap.get(treeModel.getParentId()) != null) {
				treeModel.setLocked(1);
			}
		}

		TreeRepository repository = new TreeRepository();
		for (int i = 0, len = nodes.size(); i < len; i++) {
			TreeModel treeModel = nodes.get(i);
			if (treeModel.getLocked() != 0) {
				continue;
			}

			TreeComponent component = new TreeComponent();
			component.setId(String.valueOf(treeModel.getId()));
			if(treeModel.getCode() != null){
				component.setCode(treeModel.getCode());
			}else{
			    component.setCode(String.valueOf(treeModel.getId()));
			}
			component.setTitle(treeModel.getName());
			component.setChecked(treeModel.isChecked());
			component.setTreeObject(treeModel);
			component.setImage(treeModel.getIcon());
			component.setTreeModel(treeModel);
			component.setDescription(treeModel.getDescription());
			component.setLocation(treeModel.getUrl());
			component.setUrl(treeModel.getUrl());
			component.setTreeId(treeModel.getTreeId());
			component.setCls(treeModel.getIconCls());
			component.setDataMap(treeModel.getDataMap());

			String parentId = String.valueOf(treeModel.getParentId());
			if (StringUtils.isNotEmpty(parentId)
					&& treeMap.get(parentId) != null) {
				TreeComponent parentTree = repository.getTree(parentId);
				if (parentTree == null) {
					TreeModel parent = treeMap.get(parentId);
					parentTree = new TreeComponent();
					parentTree.setId(String.valueOf(parent.getId()));
					parentTree.setCode(parent.getCode());
					parentTree.setTitle(parent.getName());
					parentTree.setChecked(parent.isChecked());
					parentTree.setTreeModel(parent);
					parentTree.setTreeObject(parent);
					parentTree.setDescription(parent.getDescription());
					parentTree.setLocation(parent.getUrl());
					parentTree.setUrl(parent.getUrl());
					parentTree.setTreeId(parent.getTreeId());
					parentTree.setCls(parent.getIconCls());
					parentTree.setDataMap(parent.getDataMap());
					repository.addTree(parentTree);
				}
				component.setParent(parentTree);
			}
			if (!repository.getTreeIds().contains(component.getId())) {
				repository.addTree(component);
			}
		}
		return repository;
	}

}