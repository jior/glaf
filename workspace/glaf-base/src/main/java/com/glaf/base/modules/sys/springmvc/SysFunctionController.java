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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysFunction;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysFunctionService;
import com.glaf.base.utils.ParamUtil;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.RequestUtils;

@Controller("/sys/function")
@RequestMapping("/sys/function.do")
public class SysFunctionController {
	private static final Log logger = LogFactory
			.getLog(SysFunctionController.class);

	@javax.annotation.Resource
	private SysApplicationService sysApplicationService;

	@javax.annotation.Resource
	private SysFunctionService sysFunctionService;

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
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = sysFunctionService.deleteAll(id);

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"function.delete_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"function.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2", modelMap);
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
		RequestUtils.setRequestParameterToAttribute(request);
		long parent = (long) ParamUtil.getIntParameter(request, "parent", 0);
		SysApplication app = sysApplicationService.findById(parent);
		SysFunction bean = new SysFunction();
		bean.setName(ParamUtil.getParameter(request, "funcName"));
		bean.setFuncMethod(ParamUtil.getParameter(request, "funcMethod"));
		bean.setApp(app);
		boolean ret = sysFunctionService.create(bean);

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"function.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"function.add_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示提交后页面
		return new ModelAndView("show_msg2", modelMap);
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
		RequestUtils.setRequestParameterToAttribute(request);
		long id = (long) ParamUtil.getIntParameter(request, "funcId", 0);
		SysFunction bean = sysFunctionService.findById(id);
		bean.setName(ParamUtil.getParameter(request, "funcName"));
		bean.setFuncMethod(ParamUtil.getParameter(request, "funcMethod"));
		boolean ret = sysFunctionService.update(bean);

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"function.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"function.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示提交后页面
		return new ModelAndView("show_msg2", modelMap);
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	public void setSysFunctionService(SysFunctionService sysFunctionService) {
		this.sysFunctionService = sysFunctionService;
		logger.info("setSysFunctionService");
	}

	/**
	 * 显示对应模块下面的功能列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showFuncList")
	public ModelAndView showFuncList(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtils.setRequestParameterToAttribute(request);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		List<SysFunction> list = sysFunctionService.getSysFunctionList(parent);
		request.setAttribute("list", list);
		
		String x_view = ViewProperties.getString("function.showFuncList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/sys/function/function_list", modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=sort")
	public void sort(@RequestParam(value = "id") int id,
			@RequestParam(value = "operate") int operate) {
		logger.info("id:" + id + ";operate:" + operate);
		sysFunctionService.sort(sysFunctionService.findById(id), operate);
	}
}