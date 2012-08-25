package com.glaf.base.modules.others.action;

import java.util.Date;

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

import com.glaf.base.modules.others.model.Audit;
import com.glaf.base.modules.others.service.AuditService;
 
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

public class AuditAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(AuditAction.class);

	private AuditService auditService;

	/**
	 * 显示审批意见页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareComment(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		return mapping.findForward("show_comment");
	}

	public ActionForward saveComment(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = ParamUtil.getLongParameter(request, "id", 0);
		String memo = ParamUtil.getParameter(request, "memo", "");
		Audit bean = auditService.findById(id);
		bean.setMemo(memo);

		ActionMessages messages = new ActionMessages();
		if (auditService.update(bean)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"audit.success"));
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"audit.failure"));
		}
		addMessages(request, messages);
		// 显示修改页面
		return mapping.findForward("show_msg");
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
	 * @return
	 * @throws Exception
	 */
	public ActionForward showList(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
		return mapping.findForward("show_list");
	}

	/**
	 * 显示审批意见列表
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

		// 显示页面
		return mapping.findForward("showList2");
	}
}
