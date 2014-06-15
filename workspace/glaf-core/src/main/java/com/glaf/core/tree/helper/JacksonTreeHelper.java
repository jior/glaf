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

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.TreeModel;
import com.glaf.core.tree.component.TreeComponent;
import com.glaf.core.tree.component.TreeRepository;
import com.glaf.core.util.DateUtils;

public class JacksonTreeHelper {
	protected static final Log logger = LogFactory
			.getLog(JacksonTreeHelper.class);

	protected void addDataMap(TreeComponent component, ObjectNode row) {
		if (component.getTreeModel() != null
				&& component.getTreeModel().getDataMap() != null) {
			Map<String, Object> dataMap = component.getTreeModel().getDataMap();
			Set<Entry<String, Object>> entrySet = dataMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					if (value instanceof Date) {
						Date d = (Date) value;
						row.put(name, DateUtils.getDate(d));
					} else if (value instanceof Boolean) {
						row.put(name, (Boolean) value);
					} else if (value instanceof Integer) {
						row.put(name, (Integer) value);
					} else if (value instanceof Long) {
						row.put(name, (Long) value);
					} else if (value instanceof Double) {
						row.put(name, (Double) value);
					} else if (value instanceof Float) {
						row.put(name, (Float) value);
					} else {
						row.put(name, value.toString());
					}
				}
			}
		}

		if (component.getTreeObject() != null) {
			if (component.getTreeObject() instanceof TreeModel) {
				TreeModel tree = (TreeModel) component.getTreeObject();
				ObjectNode objectNode = tree.toObjectNode();
				if (objectNode != null && !objectNode.isNull()) {
					Iterator<String> iterator = objectNode.fieldNames();
					while (iterator.hasNext()) {
						String nodeName = iterator.next();
						row.set(nodeName, objectNode.get(nodeName));
					}
				}

				row.put("id", tree.getId());
				row.put("_id_", tree.getId());
				row.put("_oid_", tree.getId());
				row.put("parentId", tree.getParentId());
				row.put("_parentId", tree.getParentId());
				row.put("name", tree.getName());
				row.put("text", tree.getName());
				row.put("checked", tree.isChecked());
				row.put("level", tree.getLevel());
				row.put("icon", tree.getIcon());
				row.put("img", tree.getIcon());
				row.put("image", tree.getIcon());
			}
		}
	}

	public TreeRepository build(List<TreeModel> treeModels) {
		Map<Long, TreeModel> treeModelMap = new java.util.HashMap<Long, TreeModel>();

		for (int i = 0, len = treeModels.size(); i < len; i++) {
			TreeModel treeModel = (TreeModel) treeModels.get(i);
			if (treeModel != null
					&& treeModel.getId() == treeModel.getParentId()) {
				treeModel.setParentId(-1);
			}
			if (treeModel != null && treeModel.getId() > 0) {
				treeModelMap.put(Long.valueOf(treeModel.getId()), treeModel);
			}
		}

		TreeRepository repository = new TreeRepository();
		for (int i = 0, len = treeModels.size(); i < len; i++) {
			TreeModel treeModel = treeModels.get(i);
			if (treeModel == null) {
				continue;
			}
			TreeComponent component = new TreeComponent();
			component.setId(String.valueOf(treeModel.getId()));
			component.setCode(String.valueOf(treeModel.getId()));
			component.setTitle(treeModel.getName());
			component.setChecked(treeModel.isChecked());
			component.setTreeObject(treeModel);
			component.setImage(treeModel.getIcon());
			component.setId(String.valueOf(treeModel.getId()));
			component.setCode(treeModel.getCode());
			component.setTreeModel(treeModel);
			component.setDescription(treeModel.getDescription());
			component.setLocation(treeModel.getUrl());
			component.setUrl(treeModel.getUrl());
			component.setTreeId(treeModel.getTreeId());
			component.setCls(treeModel.getIconCls());
			component.setDataMap(treeModel.getDataMap());
			repository.addTree(component);

			long parentId = treeModel.getParentId();
			if (treeModelMap.get(parentId) != null) {
				TreeComponent parentTree = repository.getTree(String
						.valueOf(parentId));
				if (parentTree == null) {
					TreeModel parent = treeModelMap.get(parentId);
					parentTree = new TreeComponent();
					parentTree.setId(String.valueOf(parent.getId()));
					parentTree.setCode(String.valueOf(parent.getId()));
					parentTree.setTitle(parent.getName());
					parentTree.setChecked(parent.isChecked());
					parentTree.setTreeObject(parent);
					repository.addTree(parentTree);
				}
				component.setParent(parentTree);
			}

		}
		return repository;
	}

	public void buildTree(ObjectNode row, TreeComponent treeComponent,
			Collection<String> checkedNodes, Map<String, TreeModel> nodeMap) {
		if (treeComponent.getComponents() != null
				&& treeComponent.getComponents().size() > 0) {
			ArrayNode array = new ObjectMapper().createArrayNode();
			Iterator<?> iterator = treeComponent.getComponents().iterator();
			while (iterator.hasNext()) {
				TreeComponent component = (TreeComponent) iterator.next();
				TreeModel node = nodeMap.get(treeComponent.getId());
				ObjectNode child = new ObjectMapper().createObjectNode();
				this.addDataMap(component, child);
				child.put("id", component.getId());
				child.put("code", component.getCode());
				child.put("name", component.getTitle());
				child.put("text", component.getTitle());
				child.put("checked", component.isChecked());
				child.put("icon", component.getImage());
				child.put("img", component.getImage());
				child.put("image", component.getImage());

				if (node != null) {

				}
				if (checkedNodes.contains(component.getId())) {
					child.put("checked", Boolean.valueOf(true));
				} else {
					child.put("checked", Boolean.valueOf(false));
				}
				if (component.getComponents() != null
						&& component.getComponents().size() > 0) {
					child.put("leaf", Boolean.valueOf(false));
					child.put("isParent", true);
				} else {
					child.put("leaf", Boolean.valueOf(true));
					child.put("isParent", false);
				}
				array.add(child);
				this.buildTree(child, component, checkedNodes, nodeMap);
			}
			row.set("children", array);
		}

	}

	public void buildTreeModel(ObjectNode row, TreeComponent treeComponent) {
		if (treeComponent.getComponents() != null
				&& treeComponent.getComponents().size() > 0) {
			ArrayNode array = new ObjectMapper().createArrayNode();
			Iterator<?> iterator = treeComponent.getComponents().iterator();
			while (iterator.hasNext()) {
				TreeComponent component = (TreeComponent) iterator.next();
				ObjectNode child = new ObjectMapper().createObjectNode();
				this.addDataMap(component, child);
				child.put("id", component.getId());
				child.put("code", component.getCode());
				child.put("text", component.getTitle());
				child.put("name", component.getTitle());
				child.put("checked", component.isChecked());
				child.put("icon", component.getImage());
				child.put("img", component.getImage());
				child.put("image", component.getImage());

				if (component.getComponents() != null
						&& component.getComponents().size() > 0) {
					child.put("leaf", Boolean.valueOf(false));
					child.put("isParent", true);
					array.add(child);
					this.buildTreeModel(child, component);
				} else {
					child.put("leaf", Boolean.valueOf(true));
					child.put("isParent", false);
					array.add(child);
				}
			}
			row.set("children", array);
		}
	}

	public ObjectNode getJsonCheckboxNode(TreeModel root,
			List<TreeModel> trees, List<TreeModel> selectedNodes) {
		Collection<String> checkedNodes = new HashSet<String>();
		if (selectedNodes != null && selectedNodes.size() > 0) {
			for (int i = 0, len = selectedNodes.size(); i < len; i++) {
				TreeModel treeNode = (TreeModel) selectedNodes.get(i);
				checkedNodes.add(String.valueOf(treeNode.getId()));
			}
		}

		Map<String, TreeModel> nodeMap = new java.util.HashMap<String, TreeModel>();
		if (trees != null && trees.size() > 0) {
			for (int i = 0, len = trees.size(); i < len; i++) {
				TreeModel treeNode = (TreeModel) trees.get(i);
				nodeMap.put(String.valueOf(treeNode.getId()), treeNode);
			}
		}

		ObjectNode object = new ObjectMapper().createObjectNode();

		if (root != null) {
			object.put("id", String.valueOf(root.getId()));
			object.put("code", String.valueOf(root.getId()));
			object.put("name", root.getName());
			object.put("text", root.getName());
			object.put("leaf", Boolean.valueOf(false));
			object.put("isParent", true);
			object.put("checked", root.isChecked());
			object.put("icon", root.getIcon());
			object.put("img", root.getIcon());
			object.put("image", root.getIcon());

			if (checkedNodes.contains(String.valueOf(root.getId()))) {
				object.put("checked", Boolean.valueOf(true));
			} else {
				object.put("checked", Boolean.valueOf(false));
			}
		}

		ArrayNode array = new ObjectMapper().createArrayNode();
		if (trees != null && trees.size() > 0) {
			TreeRepository repository = this.build(trees);
			if (repository != null) {
				List<?> topTrees = repository.getTopTrees();
				if (topTrees != null && topTrees.size() > 0) {
					if (topTrees.size() == 1) {
						TreeComponent component = (TreeComponent) topTrees
								.get(0);
						if (StringUtils.equals(component.getId(),
								String.valueOf(root.getId()))) {
							this.buildTree(object, component, checkedNodes,
									nodeMap);
						} else {
							ObjectNode child = new ObjectMapper()
									.createObjectNode();
							this.addDataMap(component, child);
							child.put("id", component.getId());
							child.put("code", component.getCode());
							child.put("name", component.getTitle());
							child.put("text", component.getTitle());
							child.put("leaf", Boolean.valueOf(false));
							child.put("isParent", true);
							child.put("checked", component.isChecked());
							child.put("icon", component.getImage());
							child.put("img", component.getImage());
							child.put("image", component.getImage());

							if (checkedNodes.contains(component.getId())) {
								child.put("checked", Boolean.valueOf(true));
							} else {
								child.put("checked", Boolean.valueOf(false));
							}
							array.add(child);
							object.set("children", array);
							this.buildTree(child, component, checkedNodes,
									nodeMap);
						}
					} else {
						for (int i = 0, len = topTrees.size(); i < len; i++) {
							TreeComponent component = (TreeComponent) topTrees
									.get(i);
							TreeModel node = (TreeModel) nodeMap.get(component
									.getId());
							ObjectNode child = new ObjectMapper()
									.createObjectNode();
							this.addDataMap(component, child);
							child.put("id", component.getId());
							child.put("code", component.getCode());
							child.put("name", component.getTitle());
							child.put("text", component.getTitle());
							child.put("checked", component.isChecked());
							child.put("icon", component.getImage());
							child.put("img", component.getImage());
							child.put("image", component.getImage());

							if (node != null) {

							}
							if (checkedNodes.contains(component.getId())) {
								child.put("checked", Boolean.valueOf(true));
							} else {
								child.put("checked", Boolean.valueOf(false));
							}
							if (component.getComponents() != null
									&& component.getComponents().size() > 0) {
								child.put("leaf", Boolean.valueOf(false));
								child.put("isParent", true);
							} else {
								child.put("leaf", Boolean.valueOf(true));
								child.put("isParent", false);
							}
							array.add(child);
							this.buildTree(child, component, checkedNodes,
									nodeMap);
						}
						object.set("children", array);
					}
				}
			}
		}

		return object;
	}

	public ArrayNode getTreeArrayNode(List<TreeModel> treeModels) {
		return this.getTreeArrayNode(treeModels, true);
	}

	public ArrayNode getTreeArrayNode(List<TreeModel> treeModels,
			boolean showParentIfNotChildren) {
		ArrayNode result = new ObjectMapper().createArrayNode();
		if (treeModels != null && treeModels.size() > 0) {
			TreeRepository repository = this.build(treeModels);
			List<?> topTrees = repository.getTopTrees();
			logger.debug("topTrees:" + (topTrees != null ? topTrees.size() : 0));
			if (topTrees != null && topTrees.size() > 0) {
				for (int i = 0, len = topTrees.size(); i < len; i++) {
					TreeComponent component = (TreeComponent) topTrees.get(i);
					ObjectNode child = new ObjectMapper().createObjectNode();
					this.addDataMap(component, child);

					child.put("id", component.getId());
					child.put("code", component.getCode());
					child.put("text", component.getTitle());
					child.put("name", component.getTitle());
					child.put("checked", component.isChecked());
					child.put("icon", component.getImage());
					child.put("img", component.getImage());
					child.put("image", component.getImage());

					if (component.getComponents() != null
							&& component.getComponents().size() > 0) {
						child.put("leaf", Boolean.valueOf(false));
						child.put("cls", "folder");
						child.put("isParent", true);
						child.put("classes", "folder");
						result.add(child);
						this.buildTreeModel(child, component);
					} else {
						child.put("leaf", Boolean.valueOf(true));
						child.put("isParent", false);
						if (showParentIfNotChildren) {
							result.add(child);
						}
					}
				}
			}
		}

		return result;
	}

	public ObjectNode getTreeJson(List<TreeModel> treeModels) {
		return this.getTreeJson(null, treeModels);
	}

	public ObjectNode getTreeJson(List<TreeModel> treeModels,
			boolean showParentIfNotChildren) {
		return this.getTreeJson(null, treeModels, showParentIfNotChildren);
	}

	public ObjectNode getTreeJson(TreeModel root, List<TreeModel> treeModels) {
		return this.getTreeJson(root, treeModels, true);
	}

	public ObjectNode getTreeJson(TreeModel root, List<TreeModel> treeModels,
			boolean showParentIfNotChildren) {
		ObjectNode object = new ObjectMapper().createObjectNode();
		if (root != null) {
			object = root.toObjectNode();
			object.put("id", String.valueOf(root.getId()));
			object.put("code", String.valueOf(root.getId()));
			object.put("name", root.getName());
			object.put("text", root.getName());
			object.put("leaf", Boolean.valueOf(false));
			object.put("cls", "folder");
			object.put("isParent", true);
			object.put("checked", root.isChecked());
			object.put("icon", root.getIcon());
			object.put("img", root.getIcon());
			object.put("image", root.getIcon());
		}

		if (treeModels != null && treeModels.size() > 0) {
			ArrayNode array = new ObjectMapper().createArrayNode();
			TreeRepository repository = this.build(treeModels);
			if (repository != null) {
				List<?> topTrees = repository.getTopTrees();
				if (topTrees != null && topTrees.size() > 0) {
					if (topTrees.size() == 1) {
						TreeComponent component = (TreeComponent) topTrees
								.get(0);
						if (root != null) {
							if (StringUtils.equals(component.getId(),
									String.valueOf(root.getId()))) {
								this.buildTreeModel(object, component);
							}
						} else {
							this.addDataMap(component, object);
							object.put("id", component.getId());
							object.put("code", component.getCode());
							object.put("name", component.getTitle());
							object.put("text", component.getTitle());
							object.put("leaf", Boolean.valueOf(false));
							object.put("cls", "folder");
							object.put("checked", component.isChecked());
							object.put("isParent", true);
							this.buildTreeModel(object, component);
						}
					} else {
						for (int i = 0, len = topTrees.size(); i < len; i++) {
							TreeComponent component = (TreeComponent) topTrees
									.get(i);
							ObjectNode child = new ObjectMapper()
									.createObjectNode();
							this.addDataMap(component, child);
							child.put("id", component.getId());
							child.put("code", component.getCode());
							child.put("name", component.getTitle());
							child.put("text", component.getTitle());
							child.put("checked", component.isChecked());
							child.put("icon", component.getImage());
							child.put("img", component.getImage());
							child.put("image", component.getImage());

							if (component.getComponents() != null
									&& component.getComponents().size() > 0) {
								child.put("leaf", Boolean.valueOf(false));
								child.put("cls", "folder");
								child.put("isParent", true);
								array.add(child);
								this.buildTreeModel(child, component);
							} else {
								if (showParentIfNotChildren) {
									child.put("leaf", Boolean.valueOf(true));
									child.put("isParent", false);
									array.add(child);
								}
							}
						}
						object.set("children", array);
					}
				}
			}
		}

		return object;
	}

}
