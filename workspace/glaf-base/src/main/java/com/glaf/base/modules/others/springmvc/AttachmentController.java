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

package com.glaf.base.modules.others.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.others.service.AttachmentService;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.base.utils.ParamUtil;

@Controller
@RequestMapping("/others/attachment.do")
public class AttachmentController {
	private static final Log logger = LogFactory
			.getLog(AttachmentController.class);

	@javax.annotation.Resource
	private AttachmentService attachmentService;

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
		logger.info("setAttachmentService");
	}

	/**
	 * 显示附件列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		int viewType = ParamUtil.getIntParameter(request, "viewType", 0);

		request.setAttribute("list",
				attachmentService.getAttachmentList(referId, referType));

		if (viewType == 1) {
			return new ModelAndView(
					"/modules/others/attachment/attachment_list", modelMap);
		}
		if (viewType == 2) {
			return new ModelAndView(
					"/modules/others/attachment/attachment_list3", modelMap);
		} else {
			return new ModelAndView(
					"/modules/others/attachment/attachment_list2", modelMap);
		}
	}

	/**
	 * 显示附件列表 合同意见交流添加附件,只有上传者才能删除附件
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showList2")
	public ModelAndView showList2(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		int viewType = ParamUtil.getIntParameter(request, "viewType", 0);

		request.setAttribute("list",
				attachmentService.getAttachmentList(referId, referType));

		return new ModelAndView("/modules/others/attachment/attachment_list2",
				modelMap);
	}

	/**
	 * 提交删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=batchDelete")
	public ModelAndView batchDelete(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = attachmentService.deleteAll(id);
		ViewMessages messages = new ViewMessages();

		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"attach.del_success"));
		} else { // 删除失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"attach.del_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2", modelMap);
	}

	/**
	 * 显示附件列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showLists")
	public ModelAndView showLists(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		String referIds = ParamUtil.getParameter(request, "referId");
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		String[] referIdArray = StringUtils.split(referIds, ",");
		long[] referId = new long[referIdArray.length];
		for (int i = 0; i < referIdArray.length; i++) {
			referId[i] = Long.parseLong(referIdArray[i]);
		}

		request.setAttribute("list",
				attachmentService.getAttachmentList(referId, referType));

		return new ModelAndView("/modules/others/attachment/attachment_list2",
				modelMap);

	}

	/**
	 * 显示附件数量页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showCount")
	public ModelAndView showCount(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		String referId = ParamUtil.getParameter(request, "referId");
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		String[] referIdArray = StringUtils.split(referId, ",");
		long[] longReferId = new long[referIdArray.length];
		for (int i = 0; i < referIdArray.length; i++) {
			longReferId[i] = Long.parseLong(referIdArray[i]);
		}
		int count = attachmentService
				.getAttachmentCount(longReferId, referType);
		String Strcount = count + "";
		request.setAttribute("count", Strcount);
		return new ModelAndView("/modules/others/attachment/showCount",
				modelMap);
	}
}