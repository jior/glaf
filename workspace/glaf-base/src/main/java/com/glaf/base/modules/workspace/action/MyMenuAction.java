package com.glaf.base.modules.workspace.action;

import java.net.URLDecoder;

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
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.workspace.actionform.MyMenuForm;
import com.glaf.base.modules.workspace.model.MyMenu;
import com.glaf.base.modules.workspace.service.MyMenuService;
import com.glaf.base.utils.PageResult;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

public class MyMenuAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(MyMenuAction.class);

	private MyMenuService myMenuService;

	public void setMyMenuService(MyMenuService myMenuService) {
		this.myMenuService = myMenuService;
		logger.info("setMyMenuService");
	}

	/**
	 * ��ʾ�б�
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showList(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = RequestUtil.getLoginUser(request);
		long userId = user == null ? 0L : user.getId();

		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);
		PageResult pager = myMenuService
				.getMyMenuList(userId, pageNo, pageSize);
		request.setAttribute("pager", pager);

		// ��ʾ�б�ҳ��
		return mapping.findForward("show_list");
	}

	/**
	 * ��ʾ����ҳ��
	 * 
	 * @param mapping
	 * @param actionForm
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
	 * ��ʾ�����ҵĲ˵�ҳ��
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareAddMyMenu(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("show_addmymenu");
	}

	/**
	 * �ύ������Ϣ
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAdd(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MyMenuForm formBean = (MyMenuForm) actionForm;

		MyMenu bean = new MyMenu();

		SysUser user = RequestUtil.getLoginUser(request);
		long userId = user == null ? 0L : user.getId();

		String url = formBean.getUrl();
		url = URLDecoder.decode(url, "UTF-8");
		// System.out.println(url);
		String contextPath = request.getContextPath();
		if (url != null && url.startsWith(contextPath)) {
			url = org.jpage.util.Tools.replaceIgnoreCase(url, contextPath, "");
		}

		bean.setTitle(formBean.getTitle());
		bean.setUrl(url);
		bean.setUserId(userId);

		boolean ret = false;
		if (isTokenValid(request)) {// ��ֹ���ظ��ύ
			ret = myMenuService.create(bean);
		}
		saveToken(request);

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"myMenu.add_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"myMenu.add_failure"));
		}
		addMessages(request, messages);

		// ��ʾ�ύ��ҳ��
		int showList = ParamUtil.getIntParameter(request, "showList", 0);
		if (showList == 1) {// ��ӵ��ҵĲ˵�

			int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
			int pageSize = ParamUtil.getIntParameter(request, "page_size",
					Constants.PAGE_SIZE);
			PageResult pager = myMenuService.getMyMenuList(userId, pageNo,
					pageSize);
			request.setAttribute("pager", pager);

			return mapping.findForward("show_list");
		}
		return mapping.findForward("show_msg");
	}

	/**
	 * ��ʾ�޸�ҳ��
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareModify(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long id = ParamUtil.getLongParameter(request, "id", 0);
		MyMenu bean = myMenuService.find(id);
		request.setAttribute("bean", bean);

		// ��ʾ�޸�ҳ��
		return mapping.findForward("show_modify");
	}

	/**
	 * �ύ�޸���Ϣ
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveModify(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MyMenuForm formBean = (MyMenuForm) actionForm;

		MyMenu bean = myMenuService.find(formBean.getId());

		if (bean != null) {
			bean.setTitle(formBean.getTitle());
			bean.setUrl(formBean.getUrl());
		}

		boolean ret = false;
		if (isTokenValid(request)) {// ��ֹ���ظ��ύ
			ret = myMenuService.update(bean);
		}
		saveToken(request);

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"myMenu.modify_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"myMenu.modify_failure"));
		}
		addMessages(request, messages);
		// ��ʾ�ύ��ҳ��
		return mapping.findForward("show_msg");
	}

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
		if (isTokenValid(request)) {// ��ֹ���ظ��ύ
			int[] id = ParamUtil.getIntParameterValues(request, "id");
			if (id != null) {
				for (int i = 0; i < id.length; i++) {
					if (!myMenuService.delete((long) id[i])) {
						ret = false;
					}
				}
			}
		}
		saveToken(request);

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"myMenu.delete_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"myMenu.delete_failure"));
		}
		addMessages(request, messages);
		// ��ʾ�ύ��ҳ��
		return mapping.findForward("show_msg");
	}

}
