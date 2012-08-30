package com.glaf.base.modules.sys.action;

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

import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysFunction;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysFunctionService;
import com.glaf.base.utils.ParamUtil;

public class SysFunctionAction extends DispatchActionSupport{
	private static final Log logger = LogFactory.getLog(SysFunctionAction.class);
	private SysFunctionService sysFunctionService;
	private SysApplicationService sysApplicationService;
	
	public void setSysFunctionService(SysFunctionService sysFunctionService) {
		this.sysFunctionService = sysFunctionService;
		logger.info("setSysFunctionService");
	}	
	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	/**
	 * ��ʾ��Ӧģ������Ĺ����б�
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showFuncList(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		int parent = ParamUtil.getIntParameter(request, "parent", 0);		
		List list = sysFunctionService.getSysFunctionList(parent);
		request.setAttribute("list", list);
					
		//��ʾ�б�ҳ��
		return mapping.findForward("show_list");
	}
	/**
	 * ����ɾ����Ϣ
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
		ret = sysFunctionService.deleteAll(id);

		ActionMessages messages = new ActionMessages();
		if(ret){//����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("function.delete_success"));
		}else{//����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("function.delete_failure"));
		}
		addMessages(request, messages);		
		return mapping.findForward("show_msg2");
	}
	
	/**
	 * �ύ������Ϣ
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
		long parent = (long)ParamUtil.getIntParameter(request, "parent", 0);
		SysApplication app = sysApplicationService.findById(parent);
		SysFunction bean = new SysFunction();
		bean.setName(ParamUtil.getParameter(request, "funcName"));
		bean.setFuncMethod(ParamUtil.getParameter(request, "funcMethod"));
		bean.setApp(app);
		boolean ret = sysFunctionService.create(bean);
		
		ActionMessages messages = new ActionMessages();
		if(ret){//����ɹ�		
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("function.add_success"));
		}else{//����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("function.add_failure"));
		}
		addMessages(request, messages);
		//��ʾ�ύ��ҳ��		
		return mapping.findForward("show_msg2");
	}
	/**
	 * �ύ�޸���Ϣ
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
		long id = (long)ParamUtil.getIntParameter(request, "funcId", 0);
		SysFunction bean = sysFunctionService.findById(id);		
		bean.setName(ParamUtil.getParameter(request, "funcName"));
		bean.setFuncMethod(ParamUtil.getParameter(request, "funcMethod"));
		boolean ret = sysFunctionService.update(bean);
		
		ActionMessages messages = new ActionMessages();
		if(ret){//����ɹ�		
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("function.modify_success"));
		}else{//����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("function.modify_failure"));
		}
		addMessages(request, messages);
		//��ʾ�ύ��ҳ��		
		return mapping.findForward("show_msg2");
	}	
}
