/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jpage.actor.User;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.model.MessageTemplate;
import org.jpage.jbpm.service.MessageManager;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.RequestUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
 

public class TemplateController implements org.springframework.web.servlet.mvc.Controller {
	private final static Log logger = LogFactory.getLog(ActorController.class);

	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();

	private MessageManager messageManager;

	public TemplateController() {
		messageManager = (MessageManager) org.jpage.jbpm.context.JbpmContextFactory
				.getBean("messageManager");
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(
				org.jpage.util.Constant.LOGIN_USER);
		if (user == null) {
			request.setAttribute(Constant.APPLICATION_EXCEPTION_MESSAGE,
					"您没有登录或会话已经超时，请重新登录！");
			return new ModelAndView("error");
		}

		String method = request.getParameter("method");
		if (method == null) {
			method = "templateList";
		}
		try {

			if ("edit".equalsIgnoreCase(method)) {
				return this.edit(request, response);
			} else if ("view".equalsIgnoreCase(method)) {
				return this.view(request, response);
			} else if ("download".equalsIgnoreCase(method)) {
				return this.download(request, response);
			} else if ("save".equalsIgnoreCase(method)) {
				return this.save(request, response);
			}

			return templateList(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			return new ModelAndView("error");
		}
	}

	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateId = request.getParameter("templateId");
		if (StringUtils.isNotBlank(templateId)) {
			JbpmContext jbpmContext = null;
			try {
				jbpmContext = jbpmConfiguration.createJbpmContext();
				MessageTemplate template = messageManager.getMessageTemplate(
						jbpmContext, templateId);
				request.setAttribute("template", template);
			} catch (Exception ex) {
				logger.error(ex);
				return new ModelAndView("error");
			} finally {
				Context.close(jbpmContext);
			}
		}
		return new ModelAndView("/template/edit");
	}

	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateId = request.getParameter("templateId");
		if (StringUtils.isNotBlank(templateId)) {
			JbpmContext jbpmContext = null;
			try {
				jbpmContext = jbpmConfiguration.createJbpmContext();

				MessageTemplate template = messageManager.getMessageTemplate(
						jbpmContext, templateId);
				request.setAttribute("template", template);
			} catch (Exception ex) {
				logger.error(ex);
				return new ModelAndView("error");
			} finally {
				Context.close(jbpmContext);
			}
		}
		return new ModelAndView("/template/view");
	}

	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateId = request.getParameter("templateId");
		if (StringUtils.isNotBlank(templateId)) {
			JbpmContext jbpmContext = null;
			try {
				jbpmContext = jbpmConfiguration.createJbpmContext();

				MessageTemplate template = messageManager.getMessageTemplate(
						jbpmContext, templateId);
				request.setAttribute("template", template);
			} catch (Exception ex) {
				logger.error(ex);
				return new ModelAndView("error");
			} finally {
				Context.close(jbpmContext);
			}
		}
		return new ModelAndView("/template/download");
	}

	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(
				org.jpage.util.Constant.LOGIN_USER);
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

		String templateId = req.getParameter("templateId");
		String templateName = req.getParameter("templateName");
		String messageType = req.getParameter("messageType");
		String rendererType = req.getParameter("rendererType");
		String description = req.getParameter("description");
		String callbackUrl = req.getParameter("callbackUrl");
		String processName = req.getParameter("processName");
		String taskName = req.getParameter("taskName");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");

		MessageTemplate template = null;

		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();

			if (StringUtils.isNotBlank(templateId)) {
				template = messageManager.getMessageTemplate(jbpmContext,
						templateId);
			}

			if (template == null) {
				template = new MessageTemplate();
			}

			template.setActorId(user.getActorId());
			template.setCallbackUrl(callbackUrl);
			template.setProcessName(processName);
			template.setTaskName(taskName);
			template.setTemplateId(templateId);
			template.setTemplateName(templateName);
			template.setSubject(subject);
			template.setContent(content);
			template.setMessageType(messageType);
			template.setRendererType(rendererType);
			template.setDescription(description);
			template.setVariables(req.getParameterMap());

			Map fileMap = req.getFileMap();
			Iterator iterator = fileMap.keySet().iterator();
			while (iterator.hasNext()) {
				String name = (String) iterator.next();
				MultipartFile tFile = (MultipartFile) fileMap.get(name);
				if (tFile.getOriginalFilename() != null && tFile.getSize() > 0) {
					template.setBytes(tFile.getBytes());
					template.setFilename(tFile.getOriginalFilename());

					if (tFile.getOriginalFilename().toLowerCase().endsWith(
							".eml")) {
						template.setTemplateType("eml");
					} else if (tFile.getOriginalFilename().toLowerCase()
							.endsWith(".htm")) {
						template.setTemplateType("html");
					} else if (tFile.getOriginalFilename().toLowerCase()
							.endsWith(".html")) {
						template.setTemplateType("html");
					}
				}
			}

			messageManager.save(jbpmContext, template);

		} catch (Exception ex) {
			logger.error(ex);
			return new ModelAndView("error");
		} finally {
			Context.close(jbpmContext);
		}
		return this.templateList(request, response);
	}

	public ModelAndView templateList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map params = RequestUtil.getQueryParams(request);

		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			List rows = messageManager.getMessageTemplates(jbpmContext, params);
			request.setAttribute("rows", rows);
		} catch (Exception ex) {
			logger.error(ex);
			return new ModelAndView("error");
		} finally {
			Context.close(jbpmContext);
		}

		RequestUtil.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotBlank(view)) {
			return new ModelAndView(view);
		}

		return new ModelAndView("/template/list");
	}

}