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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysFunction;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysFunctionService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.RequestUtils;

@Controller("/rs/sys/function")
@Path("/rs/sys/function")
public class SysFunctionResource {
	private static final Log logger = LogFactory
			.getLog(SysFunctionResource.class);

	private SysApplicationService sysApplicationService;

	private SysFunctionService sysFunctionService;

	/**
	 * 批量删除信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("batchDelete")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView batchDelete(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
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
		return new ModelAndView("show_json_msg");
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("saveAdd")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView saveAdd(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
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
		return new ModelAndView("show_json_msg");
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("saveModify")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView saveModify(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
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
		return new ModelAndView("show_json_msg");
	}

	@javax.annotation.Resource
	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	@javax.annotation.Resource
	public void setSysFunctionService(SysFunctionService sysFunctionService) {
		this.sysFunctionService = sysFunctionService;
	}

	@POST
	@ResponseBody
	@Path("sort")
	public void sort(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		int id = ParamUtil.getIntParameter(request, "id", 0);
		int operate = ParamUtil.getIntParameter(request, "operate", 0);
		logger.info("id:" + id + ";operate:" + operate);
		sysFunctionService.sort(sysFunctionService.findById(id), operate);
	}
}