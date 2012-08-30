package com.glaf.base.modules.sys.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.actionform.DictoryForm;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.PageResult;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

public class DictoryAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(DictoryAction.class);
	private DictoryService dictoryService;
	private SysTreeService sysTreeService;
 
	
	public void setDictoryService(DictoryService dictoryService){
		this.dictoryService = dictoryService;
		logger.info("setDictoryService");
	}
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}
	 
	/**
	 * ��ʾ�����б�
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
		int pageSize = ParamUtil.getIntParameter(request, "page_size", 10);
		PageResult pager = dictoryService.getDictoryList(parent, pageNo, pageSize);
		request.setAttribute("pager", pager);
		//��ʾ�б�ҳ��
		return mapping.findForward("show_list");
	}
	/**
	 * ��ʾ�����ֵ�ҳ��
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
		//��ʾ�б�ҳ��
		return mapping.findForward("show_add");
	}
	
	/**
	 * �ύ�����ֵ���Ϣ
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
		SysUser user = RequestUtil.getLoginUser(request);
		Dictory bean = new Dictory();
		PropertyUtils.copyProperties(bean, (DictoryForm)actionForm);
		
		if(bean.getTypeId() == 17){
			//��ӵ�ǰ�������ʵĵ���������޸�ʱ��
			StringBuffer sb = new StringBuffer();
			List list = user.getNestingDepartment();
			
			if(list != null && list.size()>0){
				SysDepartment depart = (SysDepartment)list.get(0);
				sb.append(depart.getName());
			}
			if(!sb.toString().equals("")){
				sb.append("\\"+user.getName());
			}else{
				sb.append(user.getName());
			}
			
			bean.setExt3(sb.toString());
			bean.setExt4(sb.toString());
			bean.setExt5(new Date());
			bean.setExt6(new Date());
		}
		ActionMessages messages = new ActionMessages();		
		if(dictoryService.create(bean)){//����ɹ�
			if(bean.getTypeId() == 17){
				BaseDataManager.getInstance().loadDictInfo();
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("dictory.add_success"));
		}else{//����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("dictory.add_failure"));
		}
		addMessages(request, messages);
		request.setAttribute("url", "dictory.do?method=showList");
		
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
		long id=ParamUtil.getIntParameter(request, "id", 0);
		Dictory bean = dictoryService.find(id);
		request.setAttribute("bean", bean);
		
		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DICTORY);
		List list = new ArrayList();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int)parent.getId(), 1);
		request.setAttribute("parent", list);
		
		return mapping.findForward("show_modify");
	}
	/**
	 * �ύ�޸��ֵ���Ϣ
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
		SysUser user = RequestUtil.getLoginUser(request);
		long id=ParamUtil.getIntParameter(request, "id", 0);
		Dictory bean = dictoryService.find(id);
		DictoryForm form = (DictoryForm)actionForm;
		bean.setName(form.getName());
		bean.setCode(form.getCode());
		bean.setBlocked(form.getBlocked());
		bean.setExt1(form.getExt1());
		bean.setExt2(form.getExt2());
		if(bean.getTypeId() == 17){
			//��ӵ�ǰ�������ʵĵ���������޸�ʱ��
			StringBuffer sb = new StringBuffer();
			List list = user.getNestingDepartment();
			
			if(list != null && list.size()>0){
				SysDepartment depart = (SysDepartment)list.get(0);
				sb.append(depart.getName());
			}
			if(!sb.toString().equals("")){
				sb.append("\\"+user.getName());
			}else{
				sb.append(user.getName());
			}
			
			bean.setExt3(sb.toString());
			bean.setExt4(sb.toString());
			bean.setExt5(new Date());
			bean.setExt6(new Date());
		}
		ActionMessages messages = new ActionMessages();
		if(dictoryService.update(bean)){//����ɹ�
			if(bean.getTypeId() == 17){
				BaseDataManager.getInstance().loadDictInfo();
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("dictory.modify_success"));
		}else{//����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("dictory.modify_failure"));
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
		ret = dictoryService.deleteAll(id);		
		ActionMessages messages = new ActionMessages();
		if(ret){//����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("dictory.delete_success"));
		}else{ //ɾ��ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("dictory.delete_failure"));
		}
		addMessages(request, messages);		
		return mapping.findForward("show_msg2");
	}
	/**
	 * ��ʾ���ҳ��
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
		SysTree bean = sysTreeService.getSysTreeByCode(Constants.TREE_DICTORY);
		request.setAttribute("parent", bean.getId()+"");
		return mapping.findForward("show_frame");
	}
	/**
	 * ��ʾ���ҳ��
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadDictory(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		return mapping.findForward("show_load");
	}
	/**
	 * ��ʾ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveLoadDictory(
			ActionMapping mapping,
			ActionForm actionForm,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		BaseDataManager.getInstance().refreshBaseData();
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("dictory.reload_success"));
		addMessages(request, messages);
		//��ʾ�б�ҳ��
		return mapping.findForward("show_load");		
	}
	/**
	 * ��ʾ�ֵ�����
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showDictData(
			ActionMapping mapping,
			ActionForm actionForm,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		ActionForward forward = null;
		String code = ParamUtil.getParameter(request, "code");
		Iterator iter=null;
		long parent = ParamUtil.getLongParameter(request, "parent", -1);
		if(!(parent == -1)){
			//List list = this.goodsCategoryService.getGoodsCategoryList(parent);
			//iter = list.iterator();
			forward=mapping.findForward("show_contract_dictory_select");
		}else{
			iter=BaseDataManager.getInstance().getList(code);
			forward = mapping.findForward("show_select");
		}
		request.setAttribute("list", iter);
		
		//��ʾ�б�ҳ��
		return forward;
	}
}
