package com.glaf.base.modules.sys.action;

import java.util.ArrayList;
import java.util.Date;
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
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.PageResult;
import com.glaf.base.utils.ParamUtil;

public class SysDepartmentAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(SysDepartmentAction.class);
	private SysDepartmentService sysDepartmentService;
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
		ret = sysDepartmentService.deleteAll(id);

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"department.delete_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"department.delete_failure"));
		}
		addMessages(request, messages);

		// ��ʾ�б�ҳ��
		return mapping.findForward("show_msg2");
	}

	/**
	 * ��ʾ�¼����Žڵ�
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = ParamUtil.getIntParameter(request, "id", 0);
		int status = ParamUtil.getIntParameter(request, "status", -1);
		List list = sysTreeService.getSysTreeListForDept(id, status);
		request.setAttribute("list", list);
		return mapping.findForward("show_sub_list");
	}

	/**
	 * ��ʾ�¼����в��Žڵ�,������Ч�Ĳ���
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubDeptAll(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = ParamUtil.getIntParameter(request, "id", 0);
		List list = sysTreeService.getSysTreeList(id);
		request.setAttribute("list", list);
		return mapping.findForward("show_suball_list");
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
		SysDepartment bean = sysDepartmentService.findById(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		List list = new ArrayList();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		// ��ʾ�б�ҳ��
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

		// ���Ӳ���ʱ��ͬʱҪ���Ӷ�Ӧ�ڵ�
		SysDepartment bean = new SysDepartment();
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setCode2(ParamUtil.getParameter(request, "code2"));
		bean.setNo(ParamUtil.getParameter(request, "no"));
		bean.setCreateTime(new Date());

		SysTree node = new SysTree();
		node.setName(bean.getName());
		node.setDesc(bean.getName());
		node.setCode(bean.getCode());
		node.setParent((long) ParamUtil.getIntParameter(request, "parent", 0));
		bean.setNode(node);
		boolean ret = sysDepartmentService.create(bean);

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"department.add_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"department.add_failure"));
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
		SysUser user = (SysUser) request.getSession().getAttribute(
				SysConstants.LOGIN);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysDepartment bean = sysDepartmentService.findById(id);
		boolean ret = false;
		if (bean != null) {
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setCode2(ParamUtil.getParameter(request, "code2"));
			bean.setNo(ParamUtil.getParameter(request, "no"));
			bean.setStatus(ParamUtil.getIntParameter(request, "status", 0));
			SysTree node = bean.getNode();
			node.setName(bean.getName());
			node.setParent((long) ParamUtil.getIntParameter(request, "parent",
					0));
			bean.setNode(node);

			ret = sysDepartmentService.update(bean);
			long historyIds[] = ParamUtil.getLongParameterValues(request,
					"historyId");
			if (ret) {
				// this.sysDepartmentService.updateHistoryDepart(bean,
				// historyIds, user);
			}
		}
		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"department.modify_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"department.modify_failure"));
		}
		addMessages(request, messages);

		// ��ʾ�б�ҳ��
		return mapping.findForward("show_msg");
	}

	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
		logger.info("setSysDepartmentService");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	/**
	 * ��ʾ���в��ŵĲ˵�ѡ��ҳ��
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showDeptAllSelect(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("show_select_all");
		int parent = ParamUtil.getIntParameter(request, "parent",
				SysConstants.TREE_ROOT);
		request.setAttribute("parent", sysTreeService.findById(parent));
		String url = ParamUtil.getParameter(request, "url");
		request.setAttribute("url", url);

		// ��ʾ�б�ҳ��
		return forward;
	}

	/**
	 * ��ʾ�˵�ѡ��ҳ��
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showDeptSelect(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("show_select");
		int type = ParamUtil.getIntParameter(request, "type", 1);
		if (type == 2) {
			forward = mapping.findForward("show_select2");
		} else if (type == 3) {
			forward = mapping.findForward("show_select3");
		}

		int parent = ParamUtil.getIntParameter(request, "parent",
				SysConstants.TREE_ROOT);
		request.setAttribute("parent", sysTreeService.findById(parent));
		String url = ParamUtil.getParameter(request, "url");
		request.setAttribute("url", url);

		// ��ʾ�б�ҳ��
		return forward;
	}

	/**
	 * ��ʾ���ҳ��
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showFrame(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysTree bean = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		request.setAttribute("parent", bean.getId() + "");
		// ��ʾ�б�ҳ��
		return mapping.findForward("show_frame");
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
		PageResult pager = sysDepartmentService.getSysDepartmentList(parent,
				pageNo, pageSize);
		request.setAttribute("pager", pager);

		SysTree treeNode = sysTreeService.findById(parent);
		SysDepartment dept = treeNode.getDepartment();
		List list = new ArrayList();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		return mapping.findForward("show_list");
	}
}
