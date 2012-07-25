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
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.PageResult;
import com.glaf.base.utils.ParamUtil;

public class SysApplicationAction extends DispatchActionSupport{
	private Log logger = LogFactory.getLog(SysApplicationAction.class);
	private SysApplicationService sysApplicationService;
	private SysTreeService sysTreeService;
	
	public void setSysApplicationService(SysApplicationService sysApplicationService){
		this.sysApplicationService = sysApplicationService;
		logger.info("setSysApplicationService");
	}
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}
	
	/**
	 * 显示所有列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showList(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", Constants.PAGE_SIZE);		
		PageResult pager = sysApplicationService.getApplicationList(parent, pageNo, pageSize);
		request.setAttribute("pager", pager);
		//显示列表页面
		return mapping.findForward("show_list");
	}
	/**
	 * 显示增加页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareAdd(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{		
		return mapping.findForward("show_add");
	}
	/**
	 * 提交增加信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAdd(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		SysApplication bean = new SysApplication();
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setUrl(ParamUtil.getParameter(request, "url"));
		bean.setShowMenu(ParamUtil.getIntParameter(request, "showMenu", 0));
		
		SysTree node = new SysTree();
		node.setName(bean.getName());
		node.setDesc(bean.getName());
		node.setCode("");
		node.setParent((long)ParamUtil.getIntParameter(request, "parent", 0));		
		bean.setNode(node);
		
		boolean ret = sysApplicationService.create(bean);
		ActionMessages messages = new ActionMessages();
		if(ret){//保存成功		
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("application.add_success"));
		}else{//保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("application.add_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}
	/**
	 * 显示修改页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareModify(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysApplication bean = sysApplicationService.findById(id);
		request.setAttribute("bean", bean);
		
		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List list = new ArrayList();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int)parent.getId(), 1);
		request.setAttribute("parent", list);
		
		return mapping.findForward("show_modify");
	}
	/**
	 * 提交修改信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveModify(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{	
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysApplication bean = sysApplicationService.findById(id);
		if(bean!=null){				
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setUrl(ParamUtil.getParameter(request, "url"));
			bean.setShowMenu(ParamUtil.getIntParameter(request, "showMenu", 0));
			
			SysTree node =bean.getNode();
			node.setName(bean.getName());
			node.setDesc(bean.getName());
			node.setParent((long)ParamUtil.getIntParameter(request, "parent", 0));			
			bean.setNode(node);
		} 			
		boolean ret = sysApplicationService.update(bean);		
		ActionMessages messages = new ActionMessages();		
		if(ret){//保存成功		
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("application.modify_success"));
		}else{//保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("application.modify_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}
	/**
	 * 批量删除信息
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchDelete(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{		
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = sysApplicationService.deleteAll(id);
		
		ActionMessages messages = new ActionMessages();
		if(ret){//保存成功
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("application.delete_success"));
		}else{//保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("application.delete_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg2");
	}	
	/**
	 * 显示框架页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPermission(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		return mapping.findForward("show_permission");
	}
	/**
	 * 显示框架页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showFrame(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{		
		SysTree bean = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		request.setAttribute("parent", bean.getId()+"");
		return mapping.findForward("show_frame");
	}
	/**
	 * 显示框架页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showBase(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{		
		return mapping.findForward("show_base");
	}
	/**
	 * 显示二级栏目导航菜单
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showNavMenu(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		int parent = ParamUtil.getIntParameter(request, "parent", 0);		
		List list = new ArrayList();
		sysTreeService.getSysTree(list, parent, 0);
		request.setAttribute("list", list);
		return mapping.findForward("show_nav");
	}	
}
