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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TreeModel;

import com.glaf.core.util.RequestUtils;

public class JsonTreeHelper {

	public JsonTreeHelper() {

	}

	public JSONObject fillTreeDataChildren(TreeModel treeModel) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", treeModel.getId());
		jsonObject.put("menuId",
				RequestUtils.encodeString(String.valueOf(treeModel.getId())));
		if (treeModel.getCode() != null) {
			jsonObject.put("code", treeModel.getCode());
		}
		if (treeModel.getName() != null) {
			jsonObject.put("name", treeModel.getName());
			jsonObject.put("text", treeModel.getName());
		}
		if (treeModel.getDescription() != null) {
			jsonObject.put("description", treeModel.getDescription());
		}
		if (treeModel.getIcon() != null) {
			jsonObject.put("icon", treeModel.getIcon());
		}

		if (treeModel.getUrl() != null) {
			jsonObject.put("url", treeModel.getUrl());
		}

		List<TreeModel> children = treeModel.getChildren();

		if (children != null && children.size() > 0) {
			Collections.sort(children);
			Collection<JSONObject> rows = new ArrayList<JSONObject>();
			for (TreeModel node : children) {
				JSONObject o = this.fillTreeDataChildren(node);
				rows.add(o);
			}
			jsonObject.put("children", rows);
		} else {
			jsonObject.put("iconCls", "icon-menu");
		}
		return jsonObject;
	}

	public JSONObject fillTreeDataChildren(TreeModel treeModel,
			List<Integer> includes) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", treeModel.getId());
		jsonObject.put("menuId",
				RequestUtils.encodeString(String.valueOf(treeModel.getId())));
		if (treeModel.getCode() != null) {
			jsonObject.put("code", treeModel.getCode());
		}
		if (treeModel.getName() != null) {
			jsonObject.put("name", treeModel.getName());
			jsonObject.put("text", treeModel.getName());
		}
		if (treeModel.getDescription() != null) {
			jsonObject.put("description", treeModel.getDescription());
		}
		if (treeModel.getIcon() != null) {
			jsonObject.put("icon", treeModel.getIcon());
		}

		if (treeModel.getUrl() != null) {
			jsonObject.put("url", treeModel.getUrl());
		}
		List<TreeModel> children = treeModel.getChildren();
		if (children != null && children.size() > 0) {
			Collections.sort(children);
			Collection<JSONObject> rows = new ArrayList<JSONObject>();
			for (TreeModel node : children) {
				if (includes.contains(node.getId())) {
					JSONObject o = this.fillTreeDataChildren(node, includes);
					rows.add(o);
				}
			}
			jsonObject.put("children", rows);
		} else {
			jsonObject.put("iconCls", "icon-menu");
		}
		return jsonObject;
	}

}