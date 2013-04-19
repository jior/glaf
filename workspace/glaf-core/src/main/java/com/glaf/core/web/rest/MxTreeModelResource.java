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

package com.glaf.core.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.glaf.core.base.TreeModel;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.tree.helper.JacksonTreeHelper;

@Controller("/rs/tree")
@Path("/rs/tree")
public class MxTreeModelResource {

	@Resource
	protected ITreeModelService treeModelService;

	protected JSONObject fillTreeDataChildren(TreeModel treeNode)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", treeNode.getId());
		if (treeNode.getCode() != null) {
			jsonObject.put("code", treeNode.getCode());
		}
		if (treeNode.getName() != null) {
			jsonObject.put("name", treeNode.getName());
			jsonObject.put("text", treeNode.getName());
		}
		if (treeNode.getDescription() != null) {
			jsonObject.put("description", treeNode.getDescription());
		}
		if (treeNode.getIcon() != null) {
			jsonObject.put("icon", treeNode.getIcon());
		}
		if (treeNode.getUrl() != null) {
			jsonObject.put("url", treeNode.getUrl());
		}
		List<TreeModel> children = treeNode.getChildren();
		if (children != null && children.size() > 0) {
			Collection<JSONObject> rows = new ArrayList<JSONObject>();
			for (TreeModel node : children) {
				JSONObject o = this.fillTreeDataChildren(node);
				rows.add(o);
			}
			jsonObject.put("children", rows);
		}
		return jsonObject;
	}

	protected JSONObject getTreeData(String code) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		TreeModel treeNode = treeModelService.getTreeModelByCode(code);
		if (treeNode != null) {
			treeNode = treeModelService.getTreeModelWithAllChildren(treeNode
					.getId());
			jsonObject = this.fillTreeDataChildren(treeNode);
		}
		return jsonObject;
	}

	@GET
	@Path("json/{code}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] json(@PathParam("code") String code) {
		try {
			JSONObject jsonObject = this.getTreeData(code);
			return jsonObject.toString().getBytes("UTF-8");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

	@GET
	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] treeJson(@Context HttpServletRequest request) {
		String nodeCode = request.getParameter("nodeCode");

		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		if (StringUtils.isNotEmpty(nodeCode)) {
			TreeModel treeNode = treeModelService.getTreeModelByCode(nodeCode);
			if (treeNode != null) {
				treeModels = treeModelService.getChildrenTreeModels(treeNode
						.getId());
			}
		}

		JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		ArrayNode responseJSON = treeHelper.getTreeArrayNode(treeModels);
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

}