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
package com.glaf.base.modules.website.springmvc;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.RequestUtils;

@Controller("/public/register")
@RequestMapping("/public/register")
public class UserRegisterController {

	protected static final Log logger = LogFactory
			.getLog(UserRegisterController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected SysUserService sysUserService;

	protected SysDepartmentService sysDepartmentService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	public UserRegisterController() {

	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SysDepartment department = sysDepartmentService.findByCode("website");
		if (department != null) {
			List<SysDepartment> depts = sysDepartmentService
					.getSysDepartmentList(department.getNodeId());
			modelMap.put("depts", depts);
			request.setAttribute("depts", depts);
		} else {
			department = new SysDepartment();
			department.setCode("website");
			department.setCode2("website");
			department.setNo("website");
			department.setDeptLevel(0);
			department.setDesc("网站用户");
			department.setName("网站用户");
			department.setLevel(0);
			department.setCreateBy("website");
			department.setCreateTime(new Date());

			long parentId = ParamUtil.getLongParameter(request, "parent", 0);
			if (parentId == 0) {
				SysTree root = sysTreeService
						.getSysTreeByCode(Constants.TREE_DEPT);
				parentId = root.getId();
			}
			SysTree node = new SysTree();
			node.setCreateBy(RequestUtils.getActorId(request));
			node.setName(department.getName());
			node.setDesc(department.getDesc());
			node.setCode(department.getCode());
			node.setParentId(parentId);
			department.setNode(node);
			department.setCreateBy(RequestUtils.getActorId(request));
			try {
				sysDepartmentService.create(department);
			} catch (Exception ex) {
				logger.error(ex);
			}

			List<SysDepartment> depts = new java.util.concurrent.CopyOnWriteArrayList<SysDepartment>();
			depts.add(department);
			modelMap.put("depts", depts);
			request.setAttribute("depts", depts);
		}

		String x_view = ViewProperties.getString("sys.user.register");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/register", modelMap);
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
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

}
