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

package com.glaf.base.modules.sys.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysTreeService;

/**
 * 
 * /rs/dictory/jsonArray/chart_image <br/>
 * 
 * /rs/dictory/json/chart_image <br/>
 * 
 */
@Controller("/rs/dictory")
@Path("/rs/dictory")
public class MxDictoryResource {

	private DictoryService dictoryService;

	private SysTreeService sysTreeService;

	@GET
	@POST
	@Path("json/{category}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] json(@PathParam("category") String category) {
		try {
			List<JSONObject> rows = new java.util.ArrayList<JSONObject>();
			SysTree tree = sysTreeService.getSysTreeByCode(category);
			if (tree != null) {
				List<Dictory> list = dictoryService
						.getAvailableDictoryList(tree.getId());
				for (Dictory item : list) {
					rows.add(item.toJsonObject());
				}
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("category", category);
			jsonObject.put("total", rows.size());
			jsonObject.put("rows", rows);
			return jsonObject.toString().getBytes("UTF-8");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@GET
	@POST
	@Path("jsonArray/{category}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] jsonArray(@PathParam("category") String category) {
		try {
			JSONArray array = new JSONArray();
			SysTree tree = sysTreeService.getSysTreeByCode(category);
			if (tree != null) {
				List<Dictory> list = dictoryService
						.getAvailableDictoryList(tree.getId());
				int index = 0;
				for (Dictory item : list) {
					index++;
					JSONObject json = item.toJsonObject();
					json.put("index", index);
					json.put("listno", index);
					array.add(json);
				}
			}

			return array.toString().getBytes("UTF-8");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@GET
	@POST
	@Path("jsonArrayIncludeEmpty/{category}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] jsonArrayIncludeEmpty(@PathParam("category") String category) {
		try {
			JSONArray array = new JSONArray();
			SysTree tree = sysTreeService.getSysTreeByCode(category);
			if (tree != null) {
				List<Dictory> list = dictoryService
						.getAvailableDictoryList(tree.getId());
				Dictory dicEmpty = new Dictory();
				dicEmpty.setId(0);
				dicEmpty.setNodeId(0L);
				dicEmpty.setName("-请选择-");
				dicEmpty.setCode("");
				dicEmpty.setValue("");
				dicEmpty.setDesc("");
				JSONObject jsonEmpty = dicEmpty.toJsonObject();
				jsonEmpty.put("index", 1);
				jsonEmpty.put("listno", 1);
				array.add(jsonEmpty);
				int index = 1;
				for (Dictory item : list) {
					index++;
					JSONObject json = item.toJsonObject();
					json.put("index", index);
					json.put("listno", index);
					array.add(json);
				}
			}

			return array.toString().getBytes("UTF-8");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@GET
	@POST
	@Path("deptJsonArray/{category}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] deptJsonArray(@PathParam("category") String category) {
		try {
			if(null==category || "".equals(category))
				category = Constants.TREE_DEPT;
			SysTree parent = sysTreeService.getSysTreeByCode(category);
			List<SysTree> list = sysTreeService.getAllSysTreeListForDeptNoSort(parent.getId(), -1);
			
			JSONArray array = new JSONArray();
			if (list != null && list.size()>0) {
				int index = 0;
				for (SysTree item : list) {
					index++;
					String deptStr = "";
					if(null!=item.getCode() && item.getCode().length()>1)
						deptStr = item.getCode().substring(0, 2);
					deptStr = BaseDataManager.getInstance().getValue(deptStr, "eara")==null?"":"["+BaseDataManager.getInstance().getValue(deptStr, "eara").getName()+"]";
					item.setName(deptStr+item.getName());
					item.setCode(item.getCode().replaceAll("000", ""));
					JSONObject json = item.toJsonObject();
					json.put("index", index);
					json.put("listno", index);
					array.add(json);
				}
			}
			return array.toString().getBytes("UTF-8");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

}