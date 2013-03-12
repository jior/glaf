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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.PageResult;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

@Controller("/sys/application")
@RequestMapping("/sys/application.do")
public class SysApplicationController {
	private static final Log logger = LogFactory
			.getLog(SysApplicationController.class);

	@javax.annotation.Resource
	private SysApplicationService sysApplicationService;

	@javax.annotation.Resource
	private SysTreeService sysTreeService;

	/**
	 * 批量删除信息
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=batchDelete")
	public ModelAndView batchDelete(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = sysApplicationService.deleteAll(id);

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.delete_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2", modelMap);
	}

	/**
	 * 显示增加页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareAdd")
	public ModelAndView prepareAdd(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		// RequestUtil.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/sys/app/app_add", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareModify")
	public ModelAndView prepareModify(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		// RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysApplication bean = sysApplicationService.findById(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List list = new ArrayList();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		return new ModelAndView("/modules/sys/app/app_modify", modelMap);
	}

	/**
	 * 提交增加信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=saveAdd")
	public ModelAndView saveAdd(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysApplication bean = new SysApplication();
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setUrl(ParamUtil.getParameter(request, "url"));
		bean.setShowMenu(ParamUtil.getIntParameter(request, "showMenu", 0));

		SysTree node = new SysTree();
		node.setName(bean.getName());
		node.setDesc(bean.getName());
		node.setCode("");
		node.setParentId((long) ParamUtil.getIntParameter(request, "parent", 0));
		bean.setNode(node);

		boolean ret = sysApplicationService.create(bean);
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.add_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * 提交修改信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=saveModify")
	public ModelAndView saveModify(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysApplication bean = sysApplicationService.findById(id);
		if (bean != null) {
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setUrl(ParamUtil.getParameter(request, "url"));
			bean.setShowMenu(ParamUtil.getIntParameter(request, "showMenu", 0));

			SysTree node = bean.getNode();
			node.setName(bean.getName());
			node.setDesc(bean.getName());
			node.setParentId((long) ParamUtil.getIntParameter(request,
					"parent", 0));
			bean.setNode(node);
		}
		boolean ret = sysApplicationService.update(bean);
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg", modelMap);
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
		logger.info("setSysApplicationService");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	/**
	 * 显示框架页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showBase")
	public ModelAndView showBase(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/sys/app/basedata_frame", modelMap);
	}

	/**
	 * 显示框架页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showFrame")
	public ModelAndView showFrame(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysTree bean = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		request.setAttribute("parent", bean.getId() + "");
		return new ModelAndView("/modules/sys/app/app_frame", modelMap);
	}

	/**
	 * 显示所有列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);
		PageResult pager = sysApplicationService.getApplicationList(parent,
				pageNo, pageSize);
		request.setAttribute("pager", pager);
		// 显示列表页面
		return new ModelAndView("/modules/sys/app/app_list", modelMap);
	}

	/**
	 * 显示二级栏目导航菜单
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showNavMenu")
	public ModelAndView showNavMenu(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		List list = new ArrayList();
		sysTreeService.getSysTree(list, parent, 0);
		request.setAttribute("list", list);
		return new ModelAndView("/modules/sys/app/navmenu", modelMap);
	}

	/**
	 * 显示框架页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showPermission")
	public ModelAndView showPermission(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/sys/app/permission_frame", modelMap);
	}
}