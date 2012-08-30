package com.glaf.base.modules.sys.action;

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
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.utils.PageResult;
import com.glaf.base.utils.ParamUtil;

public class SysRoleAction extends DispatchActionSupport{
	private static final Log logger = LogFactory.getLog(SysRoleAction.class);
	private SysRoleService sysRoleService;	
	
	public void setSysRoleService(SysRoleService sysRoleService){
		this.sysRoleService = sysRoleService;
		logger.info("setSysRoleService");
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
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", Constants.PAGE_SIZE);		
		PageResult pager = sysRoleService.getSysRoleList(pageNo, pageSize);			
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
		//显示列表页面
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
		SysRole bean = new SysRole();
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		boolean ret = sysRoleService.create(bean);
		
		ActionMessages messages = new ActionMessages();
		if(ret){//保存成功		
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.add_success"));
		}else{//保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.add_failure"));
		}
		addMessages(request, messages);

		
		//显示列表页面
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
		SysRole bean = sysRoleService.findById(id);
		request.setAttribute("bean", bean);				
		
		//显示列表页面
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
		SysRole bean = sysRoleService.findById(id);
		if(bean!=null){
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));		
			bean.setCode(ParamUtil.getParameter(request, "code"));
		}
		boolean ret = sysRoleService.update(bean);		
		ActionMessages messages = new ActionMessages();		
		if(ret){//保存成功		
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.modify_success"));
		}else{//保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.modify_failure"));
		}
		addMessages(request, messages);
		//显示列表页面
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
		ret = sysRoleService.deleteAll(id);			
		
		ActionMessages messages = new ActionMessages();
		if(ret){//保存成功
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.delete_success"));
		}else{//保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.delete_failure"));
		}
		addMessages(request, messages);

		//显示列表页面
		return mapping.findForward("show_msg2");
	}	
}
