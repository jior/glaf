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

package com.glaf.base.modules.workspace.springmvc;

import java.util.Date;
import java.util.HashMap;
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
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.workspace.form.MessageFormBean;
import com.glaf.base.modules.workspace.model.Message;
import com.glaf.base.modules.workspace.service.MessageService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailSender;

import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.base.utils.WebUtil;

@Controller
@RequestMapping("/workspace/message.do")
public class MessageController {
	private static final Log logger = LogFactory
			.getLog(MessageController.class);

	private MessageService messageService;

	private SysUserService sysUserService;

	/**
	 * 批量删除消息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=batchDelete")
	public ModelAndView batchDelete(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil.getLoginUser(request);

		boolean ret = true;

		int[] id = ParamUtil.getIntParameterValues(request, "id");
		if (id != null) {
			for (int i = 0; i < id.length; i++) {
				Message bean = messageService.find((long) id[i]);
				// 判断是否是自己的消息
				if (bean == null || bean.getRecver().getId() != user.getId()) {
					ret = false;
				} else {
					if (!messageService.delete(bean)) {
						ret = false;
					}
				}
			}
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 删除成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"message.delete_success"));
		} else {// 删除失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"message.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示删除后页面
		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * 显示收件箱列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=indexList")
	public ModelAndView indexList(ModelMap modelMap, HttpServletRequest request) {
		SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);

		int msgPageSize = 5;
		com.glaf.core.util.PageResult messagePager = messageService
				.getNoReadList(user.getId(), new HashMap<String, Object>(), 1,
						msgPageSize);
		List<?> messageList = messagePager.getResults();
		request.setAttribute("messageList", messageList);

		String x_view = ViewProperties.getString("message.indexList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/workspace/message/indexList",
				modelMap);
	}

	/**
	 * 显示发送消息页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareSend")
	public ModelAndView prepareSend(ModelMap modelMap,
			HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		Message bean = messageService.find(id);
		request.setAttribute("bean", bean);

		String x_view = ViewProperties.getString("message.prepareSend");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/workspace/message/message_send",
				modelMap);
	}

	/**
	 * 发送系统信息和Email
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=saveAndSend")
	public ModelAndView saveAndSend(HttpServletRequest request,
			MessageFormBean form, ModelMap modelMap) {
		logger.debug(RequestUtils.getParameterMap(request));
		SysUser user = RequestUtil.getLoginUser(request);

		String messageType = request.getParameter("messageType");

		String recverIds = ParamUtil.getParameter(request, "recverIds");
		String recverName = ParamUtil.getParameter(request, "recverName");
		// 用户或部门
		int recverType = ParamUtil.getIntParameter(request, "recverType", 0);

		int sysType = ParamUtil.getIntParameter(request, "sysType", 1);// 0：为系统警告
																		// 1：为系统消息
		MailMessage mailMessage = new MailMessage();
		if (user.getEmail() != null) {
			mailMessage.setFrom(user.getEmail());
		}
		mailMessage.setSubject(request.getParameter("title"));
		mailMessage.setContent(request.getParameter("content"));
		mailMessage.setSupportExpression(false);
		mailMessage.setSaveMessage(false);

		MailSender mailSender = ContextFactory.getBean("mailSender");

		MessageFormBean formBean = new MessageFormBean();
		WebUtil.copyProperties(formBean, form);

		if (StringUtils.equals(messageType, "email")
				|| StringUtils.equals(messageType, "both")) {
			if (recverType == 0 || recverType == 2) {
				List<String> actorIds = StringTools.split(recverIds);
				for (int i = 0; i < actorIds.size(); i++) {
					String actorId = actorIds.get(i);
					try {
						logger.debug(" find user:" + actorId);
						SysUser sysUser = sysUserService.findByAccount(actorId);
						if (sysUser != null
								&& StringUtils.isNotEmpty(sysUser.getEmail())) {
							logger.debug(" send mail to user:" + sysUser.getName());
							mailMessage.setTo(sysUser.getEmail());
							mailSender.send(mailMessage);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		if (recverType == 1) {
			if (StringUtils.equals(messageType, "email")
					|| StringUtils.equals(messageType, "both")) {
				List<SysUser> list = sysUserService.getSysUserList(Long
						.parseLong(recverIds));
				if (list != null) {
					Iterator<SysUser> iter = list.iterator();
					while (iter.hasNext()) {
						SysUser sysUser = (SysUser) iter.next();
						String email = sysUser.getEmail();
						if (email != null) {
							try {
								logger.debug(" send mail to user:" + sysUser.getName());
								mailMessage.setTo(email);
								mailSender.send(mailMessage);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		}

		Message bean = new Message();
		bean.setSysType(sysType);
		bean.setTitle(formBean.getTitle());
		bean.setContent(formBean.getContent());
		bean.setSender(user);
		bean.setSenderId(user.getId());
		bean.setCategory(0);// 收件箱
		bean.setReaded(0);
		bean.setCreateDate(new Date());

		int type = 1;// 用户消息
		if (bean.getSender().getId() == 0
				|| StringUtils.equals(user.getAccount(), "10000")) {
			type = 0;// 系统消息
		}
		bean.setType(type);

		boolean ret = false;

		if (StringUtils.equals(messageType, "msg")
				|| StringUtils.equals(messageType, "both")) {
			if (recverType == 0) {
				ret = messageService
						.saveSendMessage(bean, recverIds.split(","));
			}
			if (recverType == 1) {
				ret = messageService.saveSendMessageToDept(bean,
						recverIds.split(","));
			}
			if (recverType == 2) {
				List<SysUser> userList = sysUserService
						.getSupplierUser(recverIds);
				if (userList != null) {
					Iterator<SysUser> iter = userList.iterator();
					StringBuffer sb = new StringBuffer();
					while (iter.hasNext()) {
						SysUser user_sp = (SysUser) iter.next();
						if (user_sp.getAccountType() == 1) {

							sb.append(user_sp.getId() + ",");
						}
						String userIds = sb.toString();
						ret = messageService.saveSendMessage(bean,
								userIds.split(","));
					}
				}
			}
		} else {
			// 如果发送邮件，保存该信息即可
			bean.setRecver(user);
			bean.setRecverId(user.getId());
			bean.setRecverList(recverName);
			bean.setCategory(1);// 发件箱
			bean.setReaded(0);
			ret = messageService.saveOrUpdate(bean);
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"message.send_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"message.send_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示提交后页面
		return new ModelAndView("show_msg", modelMap);
	}

	@javax.annotation.Resource
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
		logger.info("setMessageService");
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setsysUserService");
	}

	/**
	 * 显示消息信息
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showMessage")
	public ModelAndView showMessage(ModelMap modelMap,
			HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);

		Message bean = messageService.updateReadMessage(id);
		request.setAttribute("bean", bean);

		String x_view = ViewProperties.getString("message.showMessage");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示消息页面
		return new ModelAndView("/modules/workspace/message/message_detail",
				modelMap);
	}

	/**
	 * 显示收件箱列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showReceiveList")
	public ModelAndView showReceiveList(ModelMap modelMap,
			HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String flag = ParamUtil.getParameter(request, "flag", null);

		SysUser user = RequestUtil.getLoginUser(request);
		long userId = user == null ? 0L : user.getId();

		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);

		PageResult pager = messageService.getReceiveList(userId,
				WebUtil.getQueryMap(request), pageNo, pageSize);
		request.setAttribute("pager", pager);
		request.setAttribute("flag", flag);

		String x_view = ViewProperties.getString("message.showReceiveList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/workspace/message/message_list",
				modelMap);
	}

	/**
	 * 显示发件箱列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showSendedList")
	public ModelAndView showSendedList(ModelMap modelMap,
			HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String flag = ParamUtil.getParameter(request, "flag", null);

		SysUser user = RequestUtil.getLoginUser(request);
		long userId = user == null ? 0L : user.getId();

		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);

		PageResult pager = messageService.getSendedList(userId,
				WebUtil.getQueryMap(request), pageNo, pageSize);
		request.setAttribute("pager", pager);
		request.setAttribute("flag", flag);

		String x_view = ViewProperties.getString("message.showSendedList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView(
				"/modules/workspace/message/message_sended_list", modelMap);
	}

}