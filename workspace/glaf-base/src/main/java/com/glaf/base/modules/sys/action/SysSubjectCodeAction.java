package com.glaf.base.modules.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.web.struts.DispatchActionSupport;

import com.glaf.base.modules.sys.actionform.SubjectCodeForm;
import com.glaf.base.modules.sys.model.SubjectCode;
import com.glaf.base.modules.sys.service.SubjectCodeService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.WebUtil;

public class SysSubjectCodeAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(SysSubjectCodeAction.class);
	private SubjectCodeService subjectCodeService;
	
	public void setSubjectCodeService(SubjectCodeService subjectCodeService){
		this.subjectCodeService=subjectCodeService;
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
	public ActionForward showSubjectTreeList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("showExesSelect");
		
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		request.setAttribute("list", subjectCodeService.getSysSubjectCodeList(parent));
		String url = ParamUtil.getParameter(request, "url");
		request.setAttribute("url", url);

		// ��ʾ�б�ҳ��
		return mapping.findForward("show_subjecttree");
	}
	/**
	 * ��ʾ�б�ҳ��
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
		Map filter = WebUtil.getQueryMap(request);
		request.setAttribute("pager", subjectCodeService.getFeePage(filter));
		return mapping.findForward("show_list1");
	}
	/**
	 * ��ʾ�¼��ڵ�
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubFee(
			ActionMapping mapping,
			ActionForm actionForm,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		Map filter = WebUtil.getQueryMap(request);
		filter.put("parent", String.valueOf(parent));
		List list = subjectCodeService.getSubFeeList(filter);
		request.setAttribute("list", list);
		return mapping.findForward("show_list2");
	}
	/**
	 * ��ʾ���ӷ��ÿ�Ŀ
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
		long parent = ParamUtil.getLongParameter(request, "parent", 0);
		request.setAttribute("parent", subjectCodeService.findById(parent));
		//��ʾ�б�ҳ��
		return mapping.findForward("show_add");
	}
	/**
	 * ������ÿ�Ŀ
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
		SubjectCode bean = new SubjectCode();
		PropertyUtils.copyProperties(bean, (SubjectCodeForm)actionForm);
		
		ActionMessages messages = new ActionMessages();
		if(subjectCodeService.create(bean)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("fee.add_success"));
		}else{//����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("fee.add_failure"));
		}
		addMessages(request, messages);
		
		return mapping.findForward("show_msg");
	}
	/**
	 * ��ʾ�޸�ҳ��
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
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SubjectCode bean = subjectCodeService.findById(id);
		request.setAttribute("fee", bean);
		request.setAttribute("parent", subjectCodeService.findById(bean.getParent()));
		return mapping.findForward("show_modify");
	}
	/**
	 * ������ÿ�Ŀ
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
		long id = ParamUtil.getLongParameter(request, "id", 0);		
		SubjectCode bean = subjectCodeService.findById(id);
		PropertyUtils.copyProperties(bean, (SubjectCodeForm)actionForm);	
		
		ActionMessages messages = new ActionMessages();
		if(subjectCodeService.update(bean)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("fee.modify_success"));
		}else{//����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("fee.modify_failure"));
		}
		addMessages(request, messages);
		
		return mapping.findForward("show_msg");
	}
	/**
	 * �ύɾ��
	 * @param mapping
	 * @param form
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
		ret = subjectCodeService.deleteAll(id);
		ActionMessages messages = new ActionMessages();
		if(ret){//����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("fee.delete_success"));
		}else{ //ɾ��ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("fee.delete_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg2");
	}
}
