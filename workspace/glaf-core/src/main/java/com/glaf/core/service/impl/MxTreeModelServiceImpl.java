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

package com.glaf.core.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.query.TreeModelQuery;
import com.glaf.core.service.ITreeModelService;

@Service("treeModelService")
@Transactional(readOnly = true)
public class MxTreeModelServiceImpl implements ITreeModelService {

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	public MxTreeModelServiceImpl() {

	}

	protected List<TreeModel> cloneProperties(List<TreeModel> rows) {
		List<TreeModel> list = new java.util.ArrayList<TreeModel>();
		if (rows != null && !rows.isEmpty()) {
			for (TreeModel model : rows) {
				TreeModel m = new BaseTree();
				this.cloneTreeModel(model, m);
				list.add(m);
			}
		}
		return list;
	}

	/**
	 * 复制树对象
	 * 
	 * @param 源对象
	 * @param 目标对象
	 */
	protected void cloneTreeModel(TreeModel model, TreeModel m) {
		m.setId(model.getId());
		m.setParentId(model.getParentId());
		if (model.getParent() != null) {
			m.setParent(model.getParent());
		}

		m.setChildren(model.getChildren());
		m.setCode(model.getCode());

		m.setDescription(model.getDescription());

		m.setIcon(model.getIcon());
		m.setIconCls(model.getIcon());
		m.setLevel(model.getLevel());
		m.setLocked(model.getLocked());
		m.setName(model.getName());

		m.setSortNo(model.getSortNo());
		m.setTreeId(model.getTreeId());

		m.setUrl(model.getUrl());

	}

	/**
	 * 获取某个节点的所有祖先节点
	 * 
	 * @param treeId
	 * @return
	 */
	public List<TreeModel> getAncestorTreeModels(long treeId) {
		List<TreeModel> treeModels = new java.util.ArrayList<TreeModel>();
		TreeModel treeModel = this.getTreeModel(treeId);

		if (treeModel != null && treeModel.getParentId() > 0) {
			TreeModel parent = this.getTreeModel(treeModel.getParentId());
			if (parent != null) {
				this.loadAncestorTreeNodes(parent, treeModels);
			}
		}

		return treeModels;
	}

	/**
	 * 获取某个节点的所有子孙节点
	 * 
	 * @param treeId
	 * @return
	 */
	public List<TreeModel> getChildrenTreeModels(long treeId) {
		List<TreeModel> treeModels = new java.util.ArrayList<TreeModel>();
		TreeModel treeModel = this.getTreeModel(treeId);
		if (treeModel != null) {
			this.loadChildrenTreeModels(treeModel, treeModels);
		}
		return treeModels;
	}

	public List<TreeModel> getSubTreeModels(long treeId) {
		List<TreeModel> treeModels = new java.util.ArrayList<TreeModel>();
		TreeModel treeModel = this.getTreeModel(treeId);
		if (treeModel != null) {

			TreeModelQuery query = new TreeModelQuery();
			query.parentId(treeModel.getId());

			String statementId = CustomProperties
					.getString("sys.getTreeModels");
			if (StringUtils.isEmpty(statementId)) {
				statementId = SystemProperties.getString("sys.getTreeModels");
			}

			if (StringUtils.isEmpty(statementId)) {
				statementId = "getTreeModels";
			}

			List<?> list = entityDAO.getList(statementId, query);
			for (Object object : list) {
				treeModels.add((TreeModel) object);
			}

		}
		return treeModels;
	}

	public Map<String, TreeModel> getTreeMap() {
		Map<String, TreeModel> codeMap = new java.util.HashMap<String, TreeModel>();
		TreeModelQuery query = new TreeModelQuery();
		List<TreeModel> treeModels = this.getTreeModels(query);
		if (treeModels != null && treeModels.size() > 0) {
			Iterator<TreeModel> iterator = treeModels.iterator();
			while (iterator.hasNext()) {
				TreeModel treeModel = iterator.next();
				codeMap.put(treeModel.getCode(), treeModel);
			}
		}
		return codeMap;
	}

	public Map<String, TreeModel> getTreeMap(List<String> codes) {
		Map<String, TreeModel> codeMap = new java.util.HashMap<String, TreeModel>();
		TreeModelQuery query = new TreeModelQuery();
		query.setCodes(codes);
		List<TreeModel> treeModels = this.getTreeModels(query);
		if (treeModels != null && treeModels.size() > 0) {
			Iterator<TreeModel> iterator = treeModels.iterator();
			while (iterator.hasNext()) {
				TreeModel treeModel = iterator.next();
				codeMap.put(treeModel.getCode(), treeModel);
				codeMap.put(treeModel.getCode().toLowerCase(), treeModel);
				codeMap.put(treeModel.getCode().toUpperCase(), treeModel);
			}
		}
		return codeMap;
	}

	/**
	 * 根据编码获取树节点
	 * 
	 * @param treeId
	 * @return
	 */
	public TreeModel getTreeModel(long treeId) {
		TreeModel treeModel = null;
		String statementId = CustomProperties.getString("sys.getTreeModelById");
		if (StringUtils.isEmpty(statementId)) {
			statementId = SystemProperties.getString("sys.getTreeModelById");
		}
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTreeModelById";
		}
		List<Object> rows = entityDAO.getList(statementId, treeId);
		if (rows != null && rows.size() > 0) {
			treeModel = (TreeModel) rows.get(0);
		}
		return treeModel;
	}

	/**
	 * 根据编码获取树节点
	 * 
	 * @param treeId
	 * @return
	 */
	public TreeModel getTreeModelByCode(String code) {
		TreeModel treeModel = null;
		String statementId = CustomProperties
				.getString("sys.getTreeModelByCode");
		if (StringUtils.isEmpty(statementId)) {
			statementId = SystemProperties.getString("sys.getTreeModelByCode");
		}
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTreeModelByCode";
		}
		List<Object> rows = entityDAO.getList(statementId, code);
		if (rows != null && rows.size() > 0) {
			treeModel = (TreeModel) rows.get(0);
		}

		return treeModel;
	}

	public Map<String, TreeModel> getTreeModelMap(List<String> codes) {
		Map<String, TreeModel> codeMap = new java.util.HashMap<String, TreeModel>();
		TreeModelQuery query = new TreeModelQuery();
		query.codes(codes);
		List<TreeModel> treeModels = this.getTreeModels(query);
		if (treeModels != null && treeModels.size() > 0) {
			Iterator<TreeModel> iterator = treeModels.iterator();
			while (iterator.hasNext()) {
				TreeModel treeModel = iterator.next();
				codeMap.put(treeModel.getCode(), treeModel);
				codeMap.put(treeModel.getCode().toLowerCase(), treeModel);
				codeMap.put(treeModel.getCode().toUpperCase(), treeModel);
			}
		}
		return codeMap;
	}

	public List<TreeModel> getTreeModels() {
		TreeModelQuery query = new TreeModelQuery();
		List<TreeModel> departments = getTreeModels(query);
		return departments;
	}

	public List<TreeModel> getTreeModels(TreeModelQuery query) {
		List<TreeModel> treeModels = new java.util.ArrayList<TreeModel>();

		String statementId = CustomProperties.getString("sys.getTreeModels");
		if (StringUtils.isEmpty(statementId)) {
			statementId = SystemProperties.getString("sys.getTreeModels");
		}

		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTreeModels";
		}

		List<Object> rows = entityDAO.getList(statementId, query);
		if (rows != null && rows.size() > 0) {
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				TreeModel treeModel = (TreeModel) iterator.next();
				treeModels.add(treeModel);
			}
		}
		return treeModels;
	}

	public TreeModel getTreeModelWithAllChildren(long treeId) {
		TreeModel model = this.getTreeModel(treeId);
		if (model != null) {
			this.loadTreeModelWithAllChildren(model);
			return model;
		}
		return null;
	}

	public TreeModel getTreeModelWithAncestor(long nodeId) {
		TreeModel treeModel = this.getTreeModel(nodeId);
		if (treeModel != null && treeModel.getParentId() != 0) {
			TreeModel parent = this.getTreeModelWithAncestor(treeModel
					.getParentId());
			if (parent != null) {
				treeModel.setParent(parent);
			}
		}
		return treeModel;
	}

	private void loadAncestorTreeNodes(TreeModel treeModel,
			List<TreeModel> treeModels) {
		if (treeModel != null && treeModels != null) {
			treeModels.add(treeModel);
			if (treeModel.getParentId() != 0) {
				TreeModel parent = this.getTreeModel(treeModel.getParentId());
				if (parent != null) {
					treeModel.setParent(parent);
					this.loadAncestorTreeNodes(parent, treeModels);
				}
			}
		}
	}

	private void loadChildrenTreeModels(TreeModel treeModel,
			List<TreeModel> treeModels) {
		if (treeModel != null && treeModels != null) {
			List<TreeModel> rows = this.getSubTreeModels(treeModel.getId());
			if (rows != null && rows.size() > 0) {
				for (int i = 0, len = rows.size(); i < len; i++) {
					TreeModel model = rows.get(i);
					model.setParent(treeModel);
					treeModels.add(model);
					this.loadChildrenTreeModels(model, treeModels);
				}
			}
		}
	}

	private void loadTreeModelWithAllChildren(TreeModel root) {
		List<TreeModel> children = this.getSubTreeModels(root.getId());
		if (children != null && children.size() > 0) {
			for (TreeModel model : children) {
				root.addChild(model);
				this.loadTreeModelWithAllChildren(model);
			}
		}
	}

	@Transactional
	public void save(TreeModel mxTreeModel) {
		long parentId = mxTreeModel.getParentId();
		String parentTreeId = null;
		TreeModel parent = mxTreeModel.getParent();
		if (parent != null && parent.getTreeId() != null) {
			parentTreeId = parent.getTreeId();
		}
		if (parentId != 0) {
			parent = this.getTreeModel(parentId);
			if (parent != null) {
			}
		} else {
			if (mxTreeModel.getParent() != null) {
				parent = this.getTreeModelByCode(mxTreeModel.getParent()
						.getCode());
				if (parent != null) {
					mxTreeModel.setParentId(parent.getId());
				}
			}
		}

		if (mxTreeModel.getId() == 0) {
			mxTreeModel.setId(idGenerator.nextId().intValue());
		}

		if (StringUtils.isEmpty(mxTreeModel.getCode())) {
			mxTreeModel.setCode("code_" + mxTreeModel.getId());
		}

		if (parentTreeId != null) {
			mxTreeModel.setTreeId(parentTreeId + mxTreeModel.getId() + "|");
		} else {
			if (parent == null) {
				mxTreeModel
						.setTreeId(String.valueOf(mxTreeModel.getId()) + "|");
			} else {
				if (parent != null && parent.getTreeId() != null) {
					mxTreeModel.setTreeId(parent.getTreeId()
							+ mxTreeModel.getId() + "|");
				}
			}
		}

		if (this.getTreeModel(mxTreeModel.getId()) == null) {
			String statementId = CustomProperties
					.getString("sys.insertTreeModel");
			if (StringUtils.isEmpty(statementId)) {
				statementId = SystemProperties.getString("sys.insertTreeModel");
			}

			if (StringUtils.isEmpty(statementId)) {
				statementId = "insertTreeModel";
			}
			entityDAO.insert(statementId, mxTreeModel);
		} else {
			String statementId = CustomProperties
					.getString("sys.updateTreeModel");
			if (StringUtils.isEmpty(statementId)) {
				statementId = SystemProperties.getString("sys.updateTreeModel");
			}

			if (StringUtils.isEmpty(statementId)) {
				statementId = "updateTreeModel";
			}
			entityDAO.update(statementId, mxTreeModel);
		}

	}

	@Transactional
	public void saveAll(List<TreeModel> treeModels) {
		if (treeModels != null && treeModels.size() > 0) {
			for (int i = 0, len = treeModels.size(); i < len; i++) {
				TreeModel treeNode = treeModels.get(i);
				this.save(treeNode);
			}
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	protected void updateTreeId(TreeModel parent) {
		if (parent != null && parent.getChildren() != null
				&& !parent.getChildren().isEmpty()) {
			for (TreeModel t : parent.getChildren()) {
				t.setTreeId(parent.getTreeId() + t.getId() + "|");
				this.save(t);
				this.updateTreeId(t);
			}
		}
	}

}