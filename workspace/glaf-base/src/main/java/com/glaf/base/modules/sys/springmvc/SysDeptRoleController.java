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

package com.glaf.base.modules.sys.springmvc;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.RequestUtils;

@Controller("/sys/deptRole")
@RequestMapping("/sys/deptRole.do")
public class SysDeptRoleController {
	private static final Log logger = LogFactory
			.getLog(SysDeptRoleController.class);

	private SysDepartmentService sysDepartmentService;

	private SysDeptRoleService sysDeptRoleService;

	private SysRoleService sysRoleService;

	private SysTreeService sysTreeService;

	/**
	 * ����Ȩ��
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=setPrivilege")
	public ModelAndView setPrivilege(HttpServletRequest request,
			ModelMap modelMap) {
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
		if (ret) {// ����ɹ�
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.app_success"));
		} else {// ����ʧ��
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.app_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * ����Ȩ��-ȫ��
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=setPrivilegeWhole")
	public ModelAndView setPrivilegeWhole(HttpServletRequest request,
			ModelMap modelMap) {
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);
		int saveType = ParamUtil.getIntParameter(request, "saveType", 0);// 0��ʾ���棬1��ʾȡ��
		String deptIdsStr = ParamUtil
				.getParameter(request, "departmentIds", "");
		String[] deptIds = deptIdsStr.split(",");
		long[] appId = ParamUtil.getLongParameterValues(request, "appId");
		for (int i = 0; i < appId.length; i++) {
			long id = ParamUtil.getLongParameter(request, "access" + appId[i],
					0);
			if (id != 1) {
				appId[i] = 0;
			}
		}
		// long[] funcId = ParamUtil.getLongParameterValues(request, "funcId");
		boolean ret = false;
		for (int i = 0; i < deptIds.length; i++) {
			long deptId = 0;
			if (null != deptIds[i] && !"".equals(deptIds[i]))
				deptId = Long.parseLong(deptIds[i]);
			if (deptId != 0) {
				SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
				if (saveType == 0) {
					if (deptRole == null) {// ���û���ҵ��򴴽�һ��
						deptRole = new SysDeptRole();
						deptRole.setDept(sysDepartmentService.findById(deptId));
						deptRole.setDeptId(deptId);
						deptRole.setRole(sysRoleService.findById(roleId));
						deptRole.setSysRoleId(roleId);
						sysDeptRoleService.create(deptRole);
					}
					ret = sysDeptRoleService.saveOrCancelRoleApplicationWhole(
							deptRole.getId(), appId, saveType);
				} else {
					if (deptRole != null)
						ret = sysDeptRoleService
								.saveOrCancelRoleApplicationWhole(
										deptRole.getId(), appId, saveType);
				}
			}
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// ����ɹ�
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.app_success"));
		} else {// ����ʧ��
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.app_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * ���ò��Ž�ɫ
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=setRole")
	public ModelAndView setRole(HttpServletRequest request, ModelMap modelMap) {
		ViewMessages messages = new ViewMessages();
		long deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		SysDepartment dept = sysDepartmentService.findById(deptId);// ���Ҳ��Ŷ���
		if (dept != null) {// ���Ŵ���
			long[] id = ParamUtil.getLongParameterValues(request, "id");// ��ȡroleId
			if (id != null) {
				// ��ȷ��Ҫɾ���Ľ�ɫ

				// ��ȷ��Ҫ���ӵĽ�ɫ

				// �ȰѲ�������Ľ�ɫ���
				Iterator<?> iter = dept.getRoles().iterator();
				while (iter.hasNext()) {
					sysDeptRoleService.delete((SysDeptRole) iter.next());
				}
				// �����½�ɫ
				for (int i = 0; i < id.length; i++) {
					SysRole role = sysRoleService.findById(id[i]);
					if (role == null)
						continue;

					SysDeptRole deptRole = new SysDeptRole();
					deptRole.setDept(dept);
					deptRole.setRole(role);
					sysDeptRoleService.create(deptRole);
				}
			}
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg", modelMap);
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
		logger.info("setSysDepartmentService");
	}

	@javax.annotation.Resource
	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;
		logger.info("setSysDeptRoleService");
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
		logger.info("setSysRoleService");
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	/**
	 * ��ʾ�����б�
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long deptId = (long) ParamUtil.getIntParameter(request, "parent", 0);
		request.setAttribute("department",
				sysDepartmentService.findById(deptId));
		request.setAttribute("list", sysRoleService.getSysRoleList());

		String x_view = ViewProperties.getString("deptRole.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// ��ʾ�б�ҳ��
		return new ModelAndView("/modules/sys/deptRole/deptRole_list", modelMap);
	}

	/**
	 * ��ʾ��ɫȨ��ӳ��ҳ��
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showPrivilege")
	public ModelAndView showPrivilege(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long deptId = ParamUtil.getLongParameter(request, "deptId", 0);
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);
		SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
		if (deptRole == null) {// ���û���ҵ��򴴽�һ��
			deptRole = new SysDeptRole();
			deptRole.setDept(sysDepartmentService.findById(deptId));
			deptRole.setDeptId(deptId);
			deptRole.setRole(sysRoleService.findById(roleId));
			deptRole.setSysRoleId(roleId);
			sysDeptRoleService.create(deptRole);
		}
		request.setAttribute("role", deptRole);
		logger.debug("#########################################");
		logger.debug("apps:" + deptRole.getApps());
		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List<SysTree> list = new java.util.concurrent.CopyOnWriteArrayList<SysTree>();
		sysTreeService.getSysTree(list, (int) parent.getId(), 0);
		request.setAttribute("list", list);
		logger.debug("------------list size:" + list.size());

		String x_view = ViewProperties.getString("deptRole.showPrivilege");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/deptRole/deptRole_privilege",
				modelMap);
	}

	@RequestMapping(params = "method=showPrivilegeWhole")
	public ModelAndView showPrivilegeWhole(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);
		SysRole role = sysRoleService.findById(roleId);

		request.setAttribute("role", role);
		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List<SysTree> list = new java.util.concurrent.CopyOnWriteArrayList<SysTree>();
		sysTreeService.getSysTree(list, (int) parent.getId(), 0);
		request.setAttribute("list", list);
		logger.debug("------------list size:" + list.size());

		String x_view = ViewProperties.getString("deptRole.showPrivilege");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView(
				"/modules/sys/deptRole/deptRole_privilege_whole", modelMap);
	}

}