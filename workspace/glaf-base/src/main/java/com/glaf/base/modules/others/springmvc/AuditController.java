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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.others.model.Audit;
import com.glaf.base.modules.others.service.AuditService;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

@Controller
@RequestMapping("/others/audit.do")
public class AuditController {
	private static final Log logger = LogFactory.getLog(AuditController.class);

	@javax.annotation.Resource
	private AuditService auditService;

	/**
	 * 显示审批意见页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @
	 */
	@RequestMapping(params = "method=prepareComment")
	public ModelAndView prepareComment(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		String confirm = ParamUtil.getParameter(request, "confirm", "true");
		SysUser user = RequestUtil.getLoginUser(request);

		Audit bean = new Audit();
		bean.setDeptId(user.getDepartment().getId());
		bean.setDeptName(user.getDepartment().getName());
		bean.setHeadship(user.getHeadship());
		bean.setLeaderId(user.getId());
		bean.setLeaderName(user.getName());
		bean.setMemo("");
		bean.setReferId(referId);
		bean.setReferType(referType);
		bean.setFlag(confirm.equals("false") ? 0 : 1);
		bean.setCreateDate(new Date());
		auditService.create(bean);
		request.setAttribute("id", String.valueOf(bean.getId()));

		// 显示修改页面
		return new ModelAndView("/modules/others/audit/audit_comment", modelMap);
	}

	@RequestMapping(params = "method=saveComment")
	public ModelAndView saveComment(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		String memo = ParamUtil.getParameter(request, "memo", "");
		Audit bean = auditService.findById(id);
		bean.setMemo(memo);

		ViewMessages messages = new ViewMessages();
		if (auditService.update(bean)) {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"audit.success"));
		} else {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"audit.failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示修改页面
		return new ModelAndView("show_msg", modelMap);
	}

	public void setAuditService(AuditService auditService) {
		logger.info("setAuditService");
		this.auditService = auditService;
	}

	/**
	 * 显示审批意见列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		String type = ParamUtil.getParameter(request, "type", "");

		// referType=>0－新增,1－变更,2－废止,34－退单重提
		if (type.equals("purchase")) {
			request.setAttribute("list",
					auditService.getAuditList(referId, "0,1,2,34"));
		} else {
			request.setAttribute("list",
					auditService.getAuditList(referId, referType));
		}

		// 显示修改页面
		return new ModelAndView("/modules/others/audit/audit_list", modelMap);
	}

	/**
	 * 显示审批意见列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @
	 */
	@RequestMapping(params = "method=showList2")
	public ModelAndView showList2(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		// 显示页面
		return new ModelAndView("/modules/others/audit/showList2", modelMap);
	}
}