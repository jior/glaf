package com.glaf.base.modules.others.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.web.struts.DispatchActionSupport;

import com.glaf.base.modules.others.service.AttachmentService;
import com.glaf.base.utils.ParamUtil;

public class AttachmentAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(AttachmentAction.class);

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
	 * @throws Exception
	 */
	public ActionForward showList(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		int viewType = ParamUtil.getIntParameter(request, "viewType", 0);

		request.setAttribute("list",
				attachmentService.getAttachmentList(referId, referType));

		if (viewType == 1) {
			return mapping.findForward("show_list");
		}
		if (viewType == 2) {
			return mapping.findForward("show_add_list");
		} else {
			return mapping.findForward("show_view_list");
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
	 * @throws Exception
	 */
	public ActionForward showList2(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		int viewType = ParamUtil.getIntParameter(request, "viewType", 0);

		request.setAttribute("list",
				attachmentService.getAttachmentList(referId, referType));

		return mapping.findForward("show_list2");
	}

	/**
	 * 提交删除
	 * 
	 * @param mapping
	 * @param form
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
		ret = attachmentService.deleteAll(id);
		ActionMessages messages = new ActionMessages();

		if (ret) {// 保存成功
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"attach.del_success"));
		} else { // 删除失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"attach.del_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg2");
	}

	/**
	 * 显示附件列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showLists(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String referIds = ParamUtil.getParameter(request, "referId");
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		String[] referIdArray = StringUtils.split(referIds, ",");
		long[] referId = new long[referIdArray.length];
		for (int i = 0; i < referIdArray.length; i++) {
			referId[i] = Long.parseLong(referIdArray[i]);
		}

		request.setAttribute("list",
				attachmentService.getAttachmentList(referId, referType));

		return mapping.findForward("show_view_list");

	}

	/**
	 * 显示附件数量页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showCount(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		return mapping.findForward("showCount");
	}
}
