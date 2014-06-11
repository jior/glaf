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

package com.glaf.mail.web.springmvc;

import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.glaf.core.config.*;
import com.glaf.core.util.*;
import com.glaf.mail.business.MailDataFacede;
import com.glaf.mail.domain.*;
import com.glaf.mail.query.MailAccountQuery;
import com.glaf.mail.query.MailStorageQuery;
import com.glaf.mail.service.*;


@Controller("/mail/mailTask")
@RequestMapping("/mail/mailTask")
public class MailTaskController {
	protected final transient Log logger = LogFactory
			.getLog(MailTaskController.class);

	protected IMailDataService mailDataService;

	protected IMailTaskService mailTaskService;

	protected IMailAccountService mailAccountService;

	protected IMailStorageService mailStorageService;

	protected MailDataFacede mailDataFacede;

	public MailTaskController() {

	}

	@RequestMapping("/account")
	public ModelAndView account(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String taskId = ParamUtils.getString(params, "taskId");
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
			request.setAttribute("mailTask", mailTask);

			MailAccountQuery query = new MailAccountQuery();
			query.createBy(RequestUtils.getActorId(request));
			List<MailAccount> accounts = mailAccountService.list(query);
			request.setAttribute("accounts", accounts);
			List<String> selecteds = mailTaskService.getAccountIds(taskId);
			request.setAttribute("selecteds", selecteds);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = CustomProperties.getString("mailTask.account");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/mail/mailTask/account");
	}

	@RequestMapping("/addMail")
	public ModelAndView addMail(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String taskId = ParamUtils.getString(params, "taskId");
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
			request.setAttribute("mailTask", mailTask);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = CustomProperties.getString("mailTask.addMail");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/mail/mailTask/addMail");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String taskId = ParamUtils.getString(params, "taskId");
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
			request.setAttribute("mailTask", mailTask);
		}

		MailStorageQuery query = new MailStorageQuery();

		List<MailStorage> list = mailStorageService.list(query);
		request.setAttribute("rows", list);

		String view = request.getParameter("edit");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = CustomProperties.getString("mailTask.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/mail/mailTask/edit");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = CustomProperties.getString("mailTask.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/mail/mailTask/list", modelMap);
	}

	@RequestMapping("/mailList")
	public ModelAndView mailList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = CustomProperties.getString("mailTask.mailList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/mail/mailTask/mailList", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = CustomProperties.getString("mailTask.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/mail/mailTask/query", modelMap);
	}

	@javax.annotation.Resource
	public void setMailAccountService(IMailAccountService mailAccountService) {
		this.mailAccountService = mailAccountService;
	}

	@javax.annotation.Resource
	public void setMailDataFacede(MailDataFacede mailDataFacede) {
		this.mailDataFacede = mailDataFacede;
	}

	@javax.annotation.Resource
	public void setMailDataService(IMailDataService mailDataService) {
		this.mailDataService = mailDataService;
	}

	@javax.annotation.Resource
	public void setMailStorageService(IMailStorageService mailStorageService) {
		this.mailStorageService = mailStorageService;
	}

	@javax.annotation.Resource
	public void setMailTaskService(IMailTaskService mailTaskService) {
		this.mailTaskService = mailTaskService;
	}

	@RequestMapping("/showUpload")
	public ModelAndView showUpload(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String taskId = ParamUtils.getString(params, "taskId");
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
			request.setAttribute("mailTask", mailTask);
			Map<String, Object> dataMap = Tools.getDataMap(mailTask);
			String x_json = JsonUtils.encode(dataMap);
			x_json = RequestUtils.encodeString(x_json);
			request.setAttribute("x_json", x_json);

		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = CustomProperties.getString("mailTask.showUpload");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/mail/mailTask/showUpload");
	}

	@RequestMapping("/uploadMails")
	public ModelAndView uploadMails(HttpServletRequest request,
			ModelMap modelMap) {
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		Map<String, Object> params = RequestUtils.getParameterMap(req);
		logger.debug(params);
		// System.out.println(params);
		String taskId = req.getParameter("taskId");
		if (StringUtils.isEmpty(taskId)) {
			taskId = req.getParameter("id");
		}
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
		}

		if (mailTask != null
				&& StringUtils.equals(RequestUtils.getActorId(request),
						mailTask.getCreateBy())) {

			try {
				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					if (mFile.getOriginalFilename() != null
							&& mFile.getSize() > 0) {
						logger.debug(mFile.getName());
						if (mFile.getOriginalFilename().endsWith(".txt")) {
							byte[] bytes = mFile.getBytes();
							String rowIds = new String(bytes);
							List<String> addresses = StringTools.split(rowIds);
							if (addresses.size() <= 100000) {
								mailDataFacede.saveMails(taskId, addresses);
							} else {
								throw new RuntimeException(
										"mail addresses too many");
							}
							break;
						}
					}
				}
			} catch (Exception ex) {// 处理文件尺寸过大异常
				ex.printStackTrace();
				throw new RuntimeException(ex.getMessage());
			}
		}
		return this.mailList(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "taskId");
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(rowId)) {
			mailTask = mailTaskService.getMailTask(rowId);
			request.setAttribute("mailTask", mailTask);
			Map<String, Object> dataMap = Tools.getDataMap(mailTask);
			String x_json = JsonUtils.encode(dataMap);
			x_json = RequestUtils.encodeString(x_json);
			request.setAttribute("x_json", x_json);

		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = CustomProperties.getString("mailTask.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/mail/mailTask/view");
	}

}