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

package com.glaf.base.modules.sys.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.web.struts.DispatchActionSupport;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.core.util.PageResult;
import com.glaf.base.utils.ParamUtil;

public class SysTreeAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(SysTreeAction.class);
	private SysTreeService sysTreeService;

	/**
	 * ����ɾ����Ϣ
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchDelete(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = sysTreeService.deleteAll(id);
		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tree.delete_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tree.delete_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg2");
	}

	/**
	 * ��ʾ�¼��ڵ�
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubTree(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = ParamUtil.getIntParameter(request, "id", 0);
		List list = sysTreeService.getSysTreeList(id);
		request.setAttribute("list", list);
		return mapping.findForward("show_sub_list");
	}

	/**
	 * ��ʾ����ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareAdd(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("show_add");
	}

	/**
	 * ��ʾ�޸�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareModify(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysTree bean = sysTreeService.findById(id);
		request.setAttribute("bean", bean);
		List list = new ArrayList();
		sysTreeService.getSysTree(list, 0, 0);
		request.setAttribute("parent", list);
		return mapping.findForward("show_modify");
	}

	/**
	 * �ύ������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAdd(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysTree bean = new SysTree();
		bean.setParent(ParamUtil.getIntParameter(request, "parent", 0));
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		boolean ret = sysTreeService.create(bean);
		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tree.add_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tree.add_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}

	/**
	 * �ύ�޸���Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveModify(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysTree bean = sysTreeService.findById(id);
		if (bean != null) {
			bean.setParent(ParamUtil.getIntParameter(request, "parent", 0));
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
		}
		boolean ret = sysTreeService.update(bean);
		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tree.modify_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tree.modify_failure"));
		}
		addMessages(request, messages);
		// ��ʾ�б�ҳ��
		return mapping.findForward("show_msg");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	/**
	 * ��ʾ��߲˵�
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showLeft(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = mapping.findForward("show_left");
		int parent = ParamUtil.getIntParameter(request, "parent",
				SysConstants.TREE_ROOT);
		request.setAttribute("parent", sysTreeService.findById(parent));
		String url = ParamUtil.getParameter(request, "url");
		request.setAttribute("url", url);

		// ��ʾ�б�ҳ��
		return forward;
	}

	/**
	 * ��ʾ�����б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showList(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);
		PageResult pager = sysTreeService.getSysTreeList(parent, pageNo,
				pageSize);
		request.setAttribute("pager", pager);
		return mapping.findForward("show_list");
	}

	/**
	 * ��ʾ��ҳ��
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showMain(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("show_frame");
	}

	public ActionForward showTop(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("show_top");
	}
}