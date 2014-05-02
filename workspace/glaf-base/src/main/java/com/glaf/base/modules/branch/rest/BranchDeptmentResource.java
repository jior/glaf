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

package com.glaf.base.modules.branch.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.domain.Membership;
import com.glaf.core.query.MembershipQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.MembershipService;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;

@Controller("/rs/branch/dept")
@Path("/rs/branch/dept")
public class BranchDeptmentResource {
	protected static final Log logger = LogFactory
			.getLog(BranchDeptmentResource.class);

	protected MembershipService membershipService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	@POST
	@Path("save")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] save(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String type = request.getParameter("type");
		Long deptId = RequestUtils.getLong(request, "deptId");
		if (deptId != null && deptId > 0) {
			String objectIds = request.getParameter("userIds");
			List<String> actorIds = StringTools.split(objectIds);
			boolean hasPermission = false;
			if (loginContext.isSystemAdministrator()) {
				hasPermission = true;
			}
			if (loginContext.getSubDeptIds().contains(deptId)) {
				hasPermission = true;
			}
			if (hasPermission) {
				List<Membership> memberships = new ArrayList<Membership>();
				for (String actorId : actorIds) {
					Membership m = new Membership();
					m.setActorId(actorId);
					m.setModifyBy(loginContext.getActorId());
					m.setNodeId(deptId);
					m.setType(type);
					memberships.add(m);
				}
				membershipService.saveMemberships(deptId, type, memberships);
				return ResponseUtils.responseJsonResult(true);
			}
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setMembershipService(MembershipService membershipService) {
		this.membershipService = membershipService;
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@GET
	@POST
	@Path("userJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] userJson(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) throws IOException {
		JSONObject result = new JSONObject();
		String type = request.getParameter("type");
		Long deptId = RequestUtils.getLong(request, "deptId");
		MembershipQuery query = new MembershipQuery();
		query.type(type);
		query.nodeId(deptId);

		Collection<String> userIds = new HashSet<String>();

		List<Membership> list = membershipService.list(query);
		if (list != null && !list.isEmpty()) {
			for (Membership m : list) {
				userIds.add(m.getActorId());
			}
		}

		List<SysUser> users = sysUserService.getSysUserWithDeptList();
		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		if (root != null && users != null) {
			logger.debug(root.toJsonObject().toJSONString());
			logger.debug("users size:" + users.size());
			List<TreeModel> treeModels = new java.util.ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(
					(int) root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysTree> treeMap = new java.util.HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					treeMap.put(dept.getId(), tree);
				}
				long ts = System.currentTimeMillis();
				for (SysTree tree : trees) {
					treeModels.add(tree);
					SysDepartment dept = tree.getDepartment();
					if (dept != null && dept.getId() > 0) {
						for (SysUser user : users) {
							SysTree t = treeMap.get(Long.valueOf(user
									.getDeptId()));
							if (dept.getId() == user.getDeptId() && t != null) {
								TreeModel treeModel = new BaseTree();
								treeModel.setParentId(t.getId());
								treeModel.setId(ts++);
								treeModel.setCode(user.getAccount());
								treeModel.setName(user.getAccount() + " "
										+ user.getName());
								treeModel.setIconCls("icon-user");
								treeModel.setIcon(request.getContextPath()
										+ "/icons/icons/user.gif");
								if (userIds != null
										&& userIds.contains(user.getAccount())) {
									treeModel.setChecked(true);
								}
								treeModels.add(treeModel);
							}
						}
					}
				}
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			// logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

}
