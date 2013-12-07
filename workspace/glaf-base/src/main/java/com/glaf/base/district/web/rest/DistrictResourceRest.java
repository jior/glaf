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

package com.glaf.base.district.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.security.LoginContext;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.base.district.domain.DistrictEntity;
import com.glaf.base.district.query.DistrictQuery;
import com.glaf.base.district.service.DistrictService;

@Controller("/rs/sys/district")
@Path("/rs/sys/district")
public class DistrictResourceRest {
	protected static final Log logger = LogFactory
			.getLog(DistrictResourceRest.class);

	protected DistrictService districtService;

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DistrictQuery query = new DistrictQuery();
		Tools.populate(query, params);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (!loginContext.isSystemAdministrator()) {
			query.createBy(loginContext.getActorId());
		}

		Long parentId = RequestUtils.getLong(request, "parentId", 0);
		query.parentId(parentId);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = districtService.getDistrictCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<DistrictEntity> list = districtService
					.getDistrictsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DistrictEntity district : list) {
					JSONObject rowJSON = district.toJsonObject();
					rowJSON.put("id", district.getId());
					rowJSON.put("districtId", district.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setDistrictService(DistrictService districtService) {
		this.districtService = districtService;
	}

	@GET
	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] treeJson(@Context HttpServletRequest request)
			throws IOException {
		JSONArray array = new JSONArray();

		Long parentId = RequestUtils.getLong(request, "parentId", 0);
		List<DistrictEntity> districts = null;
		if (parentId != null) {
			districts = districtService.getDistrictList(parentId);
		}

		if (districts != null && !districts.isEmpty()) {
			Map<Long, TreeModel> treeMap = new HashMap<Long, TreeModel>();
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			List<Long> districtIds = new ArrayList<Long>();
			for (DistrictEntity district : districts) {
				if (district.getLocked() != 0) {
					continue;
				}
				TreeModel tree = new BaseTree();
				tree.setId(district.getId());
				tree.setParentId(district.getParentId());
				tree.setCode(district.getCode());
				tree.setName(district.getName());
				tree.setSortNo(district.getSortNo());
				tree.setCreateBy(district.getCreateBy());
				tree.setIconCls("tree_folder");
				tree.setTreeId(district.getTreeId());
				treeModels.add(tree);
				districtIds.add(district.getId());
				treeMap.put(district.getId(), tree);
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			for (int i = 0, len = jsonArray.size(); i < len; i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				json.put("isParent", true);
			}
			logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return array.toJSONString().getBytes("UTF-8");
	}

}
