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

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.RequestUtils;

@Controller("/rs/sys/deptRole")
@Path("/rs/sys/deptRole")
public class SysDeptRoleResource {
	protected static final Log logger = LogFactory
			.getLog(SysDeptRoleResource.class);

	private SysDepartmentService sysDepartmentService;

	private SysDeptRoleService sysDeptRoleService;

	private SysRoleService sysRoleService;

	/**
	 * 设置权限
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("setPrivilege")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView setPrivilege(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);
		long[] appId = ParamUtil.getLongParameterValues(request, "appId");
		for (int i = 0; i < appId.length; i++) {
			long id = ParamUtil.getLongParameter(request, "access" + appId[i],
					0);
			if (id != 1) {
				appId[i] = 0;
			}
		}
		long[] funcId = ParamUtil.getLongParameterValues(request, "funcId");

		boolean ret = sysDeptRoleService.saveRoleApplication(roleId, appId,
				funcId);
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.app_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.app_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_json_msg");
	}

	/**
	 * 设置部门角色
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("setRole")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView setRole(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		ViewMessages messages = new ViewMessages();
		long deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		SysDepartment dept = sysDepartmentService.findById(deptId);// 查找部门对象
		if (dept != null) {// 部门存在
			long[] id = ParamUtil.getLongParameterValues(request, "id");// 获取roleId
			if (id != null) {
				Iterator<?> iter = dept.getRoles().iterator();
				while (iter.hasNext()) {
					sysDeptRoleService.delete((SysDeptRole) iter.next());
				}
				// 创建新角色
				for (int i = 0; i < id.length; i++) {
					SysRole role = sysRoleService.findById(id[i]);
					if (role == null) {
						continue;
					}
					SysDeptRole deptRole = new SysDeptRole();
					deptRole.setDept(dept);
					deptRole.setRole(role);
					sysDeptRoleService.create(deptRole);
				}
			}
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_json_msg");
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

}