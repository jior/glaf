package com.glaf.base.modules.sys.action;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.ParamUtil;

public class SysDeptRoleAction extends DispatchActionSupport{
	private static final Log logger = LogFactory.getLog(SysDeptRoleAction.class);
	private SysDeptRoleService sysDeptRoleService;
	private SysTreeService sysTreeService;
	private SysDepartmentService sysDepartmentService;	
	private SysRoleService sysRoleService;
	
	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService){
		this.sysDeptRoleService = sysDeptRoleService;
		logger.info("setSysDeptRoleService");
	}	
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}
	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
		logger.info("setSysDepartmentService");
	}		
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
		logger.info("setSysRoleService");
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
		long deptId = (long)ParamUtil.getIntParameter(request, "parent", 0);
		request.setAttribute("department", sysDepartmentService.findById(deptId));
		request.setAttribute("list", sysRoleService.getSysRoleList());
		//��ʾ�б�ҳ��
		return mapping.findForward("show_list");
	}
	/**
	 * ��ʾ��ɫȨ��ӳ��ҳ��
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPrivilege(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		long deptId = ParamUtil.getLongParameter(request, "deptId", 0);
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);
		SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
		if(deptRole==null){//���û���ҵ��򴴽�һ��
			deptRole = new SysDeptRole();
			deptRole.setDept(sysDepartmentService.findById(deptId));
			deptRole.setRole(sysRoleService.findById(roleId));
			sysDeptRoleService.create(deptRole);
		}
		request.setAttribute("role", deptRole);
		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List list = new ArrayList();
		sysTreeService.getSysTree(list, (int)parent.getId(), 0);
		request.setAttribute("list", list);
		return mapping.findForward("show_privilege");
	}
	/**
	 * ����Ȩ��
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setPrivilege(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);		
		long[] appId = ParamUtil.getLongParameterValues(request, "appId");		
		for(int i=0; i<appId.length; i++){
			long id=ParamUtil.getLongParameter(request, "access"+appId[i], 0);
			if(id!=1){
				appId[i]=0;
			}
		}
		long[] funcId = ParamUtil.getLongParameterValues(request, "funcId");	
		
		boolean ret = sysDeptRoleService.saveRoleApplication(roleId, appId, funcId);		
		ActionMessages messages = new ActionMessages();
		if(ret){//����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.app_success"));
		}else{//����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.app_failure"));
		}
		addMessages(request, messages);
		
		return mapping.findForward("show_msg");
	}
	/**
	 * ���ò��Ž�ɫ
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setRole(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		
		ActionMessages messages = new ActionMessages();
		long deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		SysDepartment dept = sysDepartmentService.findById(deptId);//���Ҳ��Ŷ���
		if(dept!=null){//���Ŵ���
			long[] id = ParamUtil.getLongParameterValues(request, "id");//��ȡroleId
			if(id!=null){				
				//��ȷ��Ҫɾ���Ľ�ɫ
				
				//��ȷ��Ҫ���ӵĽ�ɫ
				
				//�ȰѲ�������Ľ�ɫ���
				Iterator iter = dept.getRoles().iterator();
				while(iter.hasNext()){
					sysDeptRoleService.delete((SysDeptRole)iter.next());
				}
				//�����½�ɫ
				for(int i=0;i<id.length;i++){
					SysRole role = sysRoleService.findById(id[i]);
					if(role==null) continue;
					
					SysDeptRole deptRole = new SysDeptRole();
					deptRole.setDept(dept);
					deptRole.setRole(role);
					sysDeptRoleService.create(deptRole);
				}
			}
		}			
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}
}
