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

package com.glaf.base.business;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.*;

import com.glaf.core.base.TreeModel;
import com.glaf.core.tree.component.TreeComponent;
import com.glaf.core.tree.component.TreeRepository;
import com.glaf.core.tree.helper.TreeRepositoryBuilder;
import com.glaf.core.util.DateUtils;

public class TreeHelper {
	protected final static Log logger = LogFactory.getLog(TreeHelper.class);

	public TreeHelper() {

	}

	public void addDataMap(TreeComponent component, JSONObject row) {
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
					} else {
						row.put(name, value);
					}
				}
			}
		}
	}

	public void buildTree(JSONObject row, TreeComponent menu,
			Collection<String> checkedNodes, Map<String, TreeModel> nodeMap) {
		if (menu.getComponents() != null && menu.getComponents().size() > 0) {
			JSONArray array = new JSONArray();
			Iterator<?> iterator = menu.getComponents().iterator();
			while (iterator.hasNext()) {
				TreeComponent component = (TreeComponent) iterator.next();
				TreeModel node = nodeMap.get(menu.getId());
				JSONObject child = new JSONObject();
				this.addDataMap(component, child);
				child.put("id", component.getId());
				child.put("code", component.getCode());
				child.put("text", component.getTitle());
				child.put("icon", component.getImage());
				child.put("img", component.getImage());
				child.put("image", component.getImage());
				if (component.isChecked()) {
					child.put("checked", true);
				}
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
				} else {
					child.put("leaf", Boolean.valueOf(true));
				}
				if (component.getCls() != null) {
					child.put("cls", component.getCls());
					child.put("iconCls", component.getCls());
				}
				array.put(child);
				this.buildTree(child, component, checkedNodes, nodeMap);
			}
			row.put("children", array);
		}

	}

	public void buildTreeModel(JSONObject row, TreeComponent treeComponent) {
		buildTreeModel(row, treeComponent, 0);
	}

	public void buildTreeModel(JSONObject row, TreeComponent treeComponent,
			int viewType) {
		if (treeComponent.getComponents() != null
				&& treeComponent.getComponents().size() > 0) {
			JSONArray array = new JSONArray();
			Iterator<?> iterator = treeComponent.getComponents().iterator();
			while (iterator.hasNext()) {
				TreeComponent component = (TreeComponent) iterator.next();
				JSONObject child = new JSONObject();
				this.addDataMap(component, child);
				child.put("id", component.getId());
				child.put("code", component.getCode());
				child.put("text", component.getTitle());
				child.put("url", component.getUrl());
				if (viewType == 1) {
					child.put("name",
							component.getTitle() + "(" + component.getCode()
									+ ")");
				} else {
					child.put("name", component.getTitle());
				}
				child.put("icon", component.getImage());
				child.put("img", component.getImage());
				child.put("image", component.getImage());
				if (component.isChecked()) {
					child.put("checked", true);
				}
				// child.put("uiProvider", "col");
				if (component.getComponents() != null
						&& component.getComponents().size() > 0) {
					child.put("leaf", Boolean.valueOf(false));
					this.buildTreeModel(child, component, viewType);
				} else {
					child.put("leaf", Boolean.valueOf(true));
				}
				if (component.getCls() != null) {
					child.put("cls", component.getCls());
					child.put("iconCls", component.getCls());
				}
				array.put(child);
			}
			row.put("children", array);
		}
	}

	public JSONObject getJsonCheckboxNode(TreeModel root,
			List<TreeModel> treeNodes, List<TreeModel> selectedNodes) {
		Collection<String> checkedNodes = new HashSet<String>();
		if (selectedNodes != null && selectedNodes.size() > 0) {
			for (int i = 0; i < selectedNodes.size(); i++) {
				TreeModel treeNode = (TreeModel) selectedNodes.get(i);
				checkedNodes.add(String.valueOf(treeNode.getId()));
			}
		}

		Map<String, TreeModel> nodeMap = new HashMap<String, TreeModel>();
		if (treeNodes != null && treeNodes.size() > 0) {
			for (int i = 0; i < treeNodes.size(); i++) {
				TreeModel treeNode = (TreeModel) treeNodes.get(i);
				nodeMap.put(String.valueOf(treeNode.getId()), treeNode);
			}
		}

		JSONObject object = new JSONObject();

		object.put("id", String.valueOf(root.getId()));
		object.put("code", root.getCode());
		object.put("text", root.getName());
		object.put("leaf", Boolean.valueOf(false));
		object.put("icon", root.getIcon());
		object.put("img", root.getIcon());
		object.put("image", root.getIcon());

		if (checkedNodes.contains(String.valueOf(root.getId()))) {
			object.put("checked", Boolean.valueOf(true));
		} else {
			object.put("checked", Boolean.valueOf(false));
		}

		JSONArray array = new JSONArray();
		if (treeNodes != null && treeNodes.size() > 0) {
			TreeRepositoryBuilder builder = new TreeRepositoryBuilder();
			TreeRepository menuRepository = builder.build(treeNodes);
			if (menuRepository != null) {
				List<?> topTrees = menuRepository.getTopTrees();
				if (topTrees != null && topTrees.size() > 0) {
					if (topTrees.size() == 1) {
						TreeComponent component = (TreeComponent) topTrees
								.get(0);
						if (StringUtils.equals(component.getId(),
								String.valueOf(root.getId()))) {
							this.buildTree(object, component, checkedNodes,
									nodeMap);
						} else {
							JSONObject child = new JSONObject();
							this.addDataMap(component, child);
							child.put("id", component.getId());
							child.put("code", component.getCode());
							child.put("text", component.getTitle());
							child.put("leaf", Boolean.valueOf(false));
							child.put("icon", component.getImage());
							child.put("img", component.getImage());
							child.put("image", component.getImage());

							if (checkedNodes.contains(component.getId())) {
								child.put("checked", Boolean.valueOf(true));
							} else {
								child.put("checked", Boolean.valueOf(false));
							}
							if (component.getCls() != null) {
								child.put("cls", component.getCls());
								child.put("iconCls", component.getCls());
							}

							array.put(child);
							object.put("children", array);
							this.buildTree(child, component, checkedNodes,
									nodeMap);
						}
					} else {
						for (int i = 0; i < topTrees.size(); i++) {
							TreeComponent component = (TreeComponent) topTrees
									.get(i);
							TreeModel node = (TreeModel) nodeMap.get(component
									.getId());
							JSONObject child = new JSONObject();
							this.addDataMap(component, child);
							child.put("id", component.getId());
							child.put("code", component.getCode());
							child.put("text", component.getTitle());
							child.put("icon", component.getImage());
							child.put("img", component.getImage());
							child.put("image", component.getImage());

							if (component.getCls() != null) {
								child.put("cls", component.getCls());
								child.put("iconCls", component.getCls());
							}
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
							} else {
								child.put("leaf", Boolean.valueOf(true));
							}
							array.put(child);
							this.buildTree(child, component, checkedNodes,
									nodeMap);
						}
						object.put("children", array);
					}
				}
			}
		}

		return object;
	}

	public JSONObject getTreeJson(List<TreeModel> treeModels) {
		return this.getTreeJson(null, treeModels);
	}

	public JSONObject getTreeJson(TreeModel root, List<TreeModel> treeModels) {
		JSONObject object = new JSONObject();
		if (root != null) {
			object.put("id", root.getId());
			object.put("code", root.getCode());
			object.put("text", root.getName());
			object.put("leaf", Boolean.valueOf(false));
			object.put("cls", "folder");
		}

		JSONArray array = new JSONArray();
		if (treeModels != null && treeModels.size() > 0) {
			java.util.Collections.sort(treeModels);
			TreeRepositoryBuilder builder = new TreeRepositoryBuilder();
			TreeRepository treeRepository = builder.build(treeModels);
			if (treeRepository != null) {
				List<?> topTrees = treeRepository.getTopTrees();
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
							object.put("text", component.getTitle());
							object.put("leaf", Boolean.valueOf(false));
							object.put("cls", "folder");
							object.put("icon", component.getImage());
							object.put("img", component.getImage());
							object.put("image", component.getImage());

							if (component.isChecked()) {
								object.put("checked", true);
							}
							if (component.getCls() != null) {
								object.put("cls", component.getCls());
								object.put("iconCls", component.getCls());
							}
							this.buildTreeModel(object, component);
						}
					} else {
						for (int i = 0; i < topTrees.size(); i++) {
							TreeComponent component = (TreeComponent) topTrees
									.get(i);
							JSONObject child = new JSONObject();
							this.addDataMap(component, child);
							child.put("id", component.getId());
							child.put("code", component.getCode());
							child.put("text", component.getTitle());
							child.put("icon", component.getImage());
							child.put("img", component.getImage());
							child.put("image", component.getImage());

							if (component.isChecked()) {
								child.put("checked", true);
							}
							if (component.getComponents() != null
									&& component.getComponents().size() > 0) {
								child.put("leaf", Boolean.valueOf(false));
								object.put("cls", "folder");
								this.buildTreeModel(child, component);
							} else {
								child.put("leaf", Boolean.valueOf(true));
							}
							if (component.getCls() != null) {
								child.put("cls", component.getCls());
								child.put("iconCls", component.getCls());
							}
							array.put(child);
						}
						object.put("children", array);
					}
				}
			}
		}

		return object;
	}

	public JSONArray getTreeJSONArray(List<TreeModel> treeModels) {
		return getTreeJSONArray(treeModels, 0);
	}

	public JSONArray getTreeJSONArray(List<TreeModel> treeModels, int viewType) {
		JSONArray result = new JSONArray();
		if (treeModels != null && treeModels.size() > 0) {
			TreeRepositoryBuilder builder = new TreeRepositoryBuilder();
			TreeRepository treeRepository = builder.build(treeModels);
			List<TreeComponent> topTrees = treeRepository.getTopTrees();
			if (topTrees != null && topTrees.size() > 0) {
				for (int i = 0; i < topTrees.size(); i++) {
					TreeComponent component = topTrees.get(i);
					logger.debug(component.getId() + "\t"
							+ component.getTitle());
					if (component.getComponents() == null
							|| component.getComponents().isEmpty()) {
						continue;
					}
					JSONObject child = new JSONObject();
					this.addDataMap(component, child);

					child.put("id", component.getId());
					child.put("code", component.getCode());
					child.put("text", component.getTitle());
					child.put("url", component.getUrl());
					if (viewType == 1) {
						child.put("name", component.getTitle() + "("
								+ component.getCode() + ")");
					} else {
						child.put("name", component.getTitle());
					}
					child.put("icon", component.getImage());
					child.put("img", component.getImage());
					child.put("image", component.getImage());

					if (component.isChecked()) {
						child.put("checked", true);
					}
					// row.put("uiProvider", "col");

					if (component.getComponents() != null
							&& component.getComponents().size() > 0) {
						child.put("leaf", Boolean.valueOf(false));
						child.put("cls", "folder");
						child.put("classes", "folder");
						this.buildTreeModel(child, component, viewType);
					} else {
						child.put("leaf", Boolean.valueOf(true));
					}
					if (component.getCls() != null) {
						child.put("cls", component.getCls());
						child.put("iconCls", component.getCls());
					}
					result.put(child);
				}
			}
		}

		return result;
	}

	public JSONObject toJSONObject(TreeModel tree) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", tree.getId());
		jsonObject.put("parentId", tree.getParentId());

		if (tree.getCode() != null) {
			jsonObject.put("code", tree.getCode());
		}
		if (tree.getName() != null) {
			jsonObject.put("name", tree.getName());
			jsonObject.put("text", tree.getName());
		}

		if (tree.isChecked()) {
			jsonObject.put("checked", true);
		}

		if (tree.getDescription() != null) {
			jsonObject.put("description", tree.getDescription());
		}
		if (tree.getIcon() != null) {
			jsonObject.put("icon", tree.getIcon());
		}

		if (tree.getIconCls() != null) {
			jsonObject.put("iconCls", tree.getIconCls());
		}
		if (tree.getUrl() != null) {
			jsonObject.put("url", tree.getUrl());
		}
		if (tree.getChildren() != null && !tree.getChildren().isEmpty()) {
			JSONArray arr = new JSONArray();
			for (TreeModel t : tree.getChildren()) {
				JSONObject j = this.toJSONObject(t);
				arr.put(j);
			}
			jsonObject.put("children", arr);
		}
		return jsonObject;
	}

}