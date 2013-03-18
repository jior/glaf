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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.form.DictoryFormBean;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.PageResult;

@Controller("/sys/dictory")
@RequestMapping("/sys/dictory.do")
public class DictoryController {
	private static final Log logger = LogFactory
			.getLog(DictoryController.class);

	@javax.annotation.Resource
	private DictoryService dictoryService;

	@javax.annotation.Resource
	private SysTreeService sysTreeService;

	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
		logger.info("setDictoryService");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
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
		int pageSize = ParamUtil.getIntParameter(request, "page_size", 10);
		PageResult pager = dictoryService.getDictoryList(parent, pageNo,
				pageSize);
		request.setAttribute("pager", pager);
		// 显示列表页面
		return new ModelAndView("/modules/sys/dictory/dictory_list", modelMap);
	}

	/**
	 * 显示增加字典页面
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
		// 显示列表页面
		RequestUtil.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/sys/dictory/dictory_add", modelMap);
	}

	/**
	 * 提交增加字典信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=saveAdd")
	public ModelAndView saveAdd(ModelMap modelMap, DictoryFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysUser user = RequestUtil.getLoginUser(request);
		Dictory bean = new Dictory();
		try {
			PropertyUtils.copyProperties(bean, form);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (bean.getTypeId() == 17) {
			// 添加当前操作汇率的担当和最后修改时间
			StringBuffer sb = new StringBuffer();
			List<SysDepartment> list = user.getNestingDepartment();

			if (list != null && list.size() > 0) {
				SysDepartment depart = (SysDepartment) list.get(0);
				sb.append(depart.getName());
			}
			if (!sb.toString().equals("")) {
				sb.append("\\" + user.getName());
			} else {
				sb.append(user.getName());
			}

			bean.setExt3(sb.toString());
			bean.setExt4(sb.toString());
			bean.setExt5(new Date());
			bean.setExt6(new Date());
		}
		ViewMessages messages = new ViewMessages();
		if (dictoryService.create(bean)) {// 保存成功
			if (bean.getTypeId() == 17) {
				BaseDataManager.getInstance().loadDictInfo();
			}
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"dictory.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"dictory.add_failure"));
		}
		MessageUtils.addMessages(request, messages);
		request.setAttribute("url", "dictory.do?method=showList");

		return new ModelAndView("show_msg", modelMap);
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
		RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		Dictory bean = dictoryService.find(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService
				.getSysTreeByCode(Constants.TREE_DICTORY);
		List list = new ArrayList();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		return new ModelAndView("/modules/sys/dictory/dictory_modify", modelMap);
	}

	/**
	 * 提交修改字典信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=saveModify")
	public ModelAndView saveModify(ModelMap modelMap, DictoryFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysUser user = RequestUtil.getLoginUser(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		Dictory bean = dictoryService.find(id);
		bean.setName(form.getName());
		bean.setCode(form.getCode());
		bean.setValue(form.getValue());
		bean.setBlocked(form.getBlocked());
		bean.setExt1(form.getExt1());
		bean.setExt2(form.getExt2());
		if (bean.getTypeId() == 17) {
			// 添加当前操作汇率的担当和最后修改时间
			StringBuffer sb = new StringBuffer();
			List<SysDepartment> list = user.getNestingDepartment();

			if (list != null && list.size() > 0) {
				SysDepartment depart = (SysDepartment) list.get(0);
				sb.append(depart.getName());
			}
			if (!sb.toString().equals("")) {
				sb.append("\\" + user.getName());
			} else {
				sb.append(user.getName());
			}

			bean.setExt3(sb.toString());
			bean.setExt4(sb.toString());
			bean.setExt5(new Date());
			bean.setExt6(new Date());
		}
		ViewMessages messages = new ViewMessages();
		if (dictoryService.update(bean)) {// 保存成功
			if (bean.getTypeId() == 17) {
				BaseDataManager.getInstance().loadDictInfo();
			}
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"dictory.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"dictory.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_msg", modelMap);
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
		RequestUtil.setRequestParameterToAttribute(request);
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = dictoryService.deleteAll(id);
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"dictory.delete_success"));
		} else { // 删除失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"dictory.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2", modelMap);
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
		SysTree bean = sysTreeService.getSysTreeByCode(Constants.TREE_DICTORY);
		request.setAttribute("parent", bean.getId() + "");
		return new ModelAndView("/modules/sys/dictory/dictory_frame", modelMap);
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
	@RequestMapping(params = "method=loadDictory")
	public ModelAndView loadDictory(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/sys/dictory/dictory_load", modelMap);
	}

	/**
	 * 显示重载数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=saveLoadDictory")
	public ModelAndView saveLoadDictory(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		BaseDataManager.getInstance().refreshBaseData();
		ViewMessages messages = new ViewMessages();
		messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
				"dictory.reload_success"));
		MessageUtils.addMessages(request, messages);
		// 显示列表页面
		return new ModelAndView("/modules/sys/dictory/dictory_load", modelMap);
	}

	/**
	 * 显示字典数据
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showDictData")
	public ModelAndView showDictData(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		ModelAndView view = null;
		String code = ParamUtil.getParameter(request, "code");
		Iterator iter = null;
		long parent = ParamUtil.getLongParameter(request, "parent", -1);
		if (!(parent == -1)) {
			// List list =
			// this.goodsCategoryService.getGoodsCategoryList(parent);
			// iter = list.iterator();
			// view = new ModelAndView("show_contract_dictory_select");
		} else {
			iter = BaseDataManager.getInstance().getList(code);
			view = new ModelAndView("/modules/sys/dictory/dictory_select",
					modelMap);
		}
		request.setAttribute("list", iter);

		// 显示列表页面
		return view;
	}
}