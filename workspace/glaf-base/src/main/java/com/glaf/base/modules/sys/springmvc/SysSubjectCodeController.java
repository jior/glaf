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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.form.SubjectCodeFormBean;
import com.glaf.base.modules.sys.model.SubjectCode;
import com.glaf.base.modules.sys.service.SubjectCodeService;
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.base.utils.WebUtil;

@Controller
@RequestMapping("/sys/subject.do")
public class SysSubjectCodeController {
	private static final Log logger = LogFactory
			.getLog(SysSubjectCodeController.class);

	@javax.annotation.Resource
	private SubjectCodeService subjectCodeService;

	public void setSubjectCodeService(SubjectCodeService subjectCodeService) {
		this.subjectCodeService = subjectCodeService;
	}

	/**
	 * 显示菜单选择页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @
	 */
	public ModelAndView showSubjectTreeList(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("showExesSelect");
		RequestUtil.setRequestParameterToAttribute(request);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		request.setAttribute("list",
				subjectCodeService.getSysSubjectCodeList(parent));
		String url = ParamUtil.getParameter(request, "url");
		request.setAttribute("url", url);

		// 显示列表页面
		return new ModelAndView("/modules/sys/subject/subjecttree_list");
	}

	/**
	 * 显示列表页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @
	 */
	public ModelAndView showList(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		Map filter = WebUtil.getQueryMap(request);
		request.setAttribute("pager", subjectCodeService.getFeePage(filter));
		return new ModelAndView("/modules/sys/subject/show_list1");
	}

	/**
	 * 显示下级节点
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @
	 */
	public ModelAndView getSubFee(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		Map filter = WebUtil.getQueryMap(request);
		filter.put("parent", String.valueOf(parent));
		List list = subjectCodeService.getSubFeeList(filter);
		request.setAttribute("list", list);
		return new ModelAndView("/modules/sys/subject/show_list2");
	}

	/**
	 * 显示增加费用科目
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public ModelAndView prepareAdd(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long parent = ParamUtil.getLongParameter(request, "parent", 0);
		request.setAttribute("parent", subjectCodeService.findById(parent));
		// 显示列表页面
		return new ModelAndView("/modules/sys/subject/show_add");
	}

	/**
	 * 保存费用科目
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public ModelAndView saveAdd(ModelMap modelMap, SubjectCodeFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SubjectCode bean = new SubjectCode();
		try {
			PropertyUtils.copyProperties(bean, form);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		ViewMessages messages = new ViewMessages();
		if (subjectCodeService.create(bean)) {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"fee.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"fee.add_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_msg");
	}

	/**
	 * 显示修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public ModelAndView prepareModify(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SubjectCode bean = subjectCodeService.findById(id);
		request.setAttribute("fee", bean);
		request.setAttribute("parent",
				subjectCodeService.findById(bean.getParent()));
		return new ModelAndView("/modules/sys/subject/show_modify");
	}

	/**
	 * 保存费用科目
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public ModelAndView saveModify(ModelMap modelMap, SubjectCodeFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SubjectCode bean = subjectCodeService.findById(id);
		try {
			PropertyUtils.copyProperties(bean, form);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ViewMessages messages = new ViewMessages();
		if (subjectCodeService.update(bean)) {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"fee.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"fee.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_msg");
	}

	/**
	 * 提交删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public ModelAndView batchDelete(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = subjectCodeService.deleteAll(id);
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"fee.delete_success"));
		} else { // 删除失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"fee.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2");
	}
}