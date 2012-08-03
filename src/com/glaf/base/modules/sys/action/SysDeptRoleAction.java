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
		long deptId = (long)ParamUtil.getIntParameter(request, "parent", 0);
		request.setAttribute("department", sysDepartmentService.findById(deptId));
		request.setAttribute("list", sysRoleService.getSysRoleList());
		//显示列表页面
		return mapping.findForward("show_list");
	}
	/**
	 * 显示角色权限映射页面
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
		if(deptRole==null){//如果没有找到则创建一个
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
	 * 设置权限
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
		if(ret){//保存成功
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.app_success"));
		}else{//保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.app_failure"));
		}
		addMessages(request, messages);
		
		return mapping.findForward("show_msg");
	}
	/**
	 * 设置部门角色
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
		SysDepartment dept = sysDepartmentService.findById(deptId);//查找部门对象
		if(dept!=null){//部门存在
			long[] id = ParamUtil.getLongParameterValues(request, "id");//获取roleId
			if(id!=null){				
				//先确定要删除的角色
				
				//再确定要增加的角色
				
				//先把部门下面的角色清空
				Iterator iter = dept.getRoles().iterator();
				while(iter.hasNext()){
					sysDeptRoleService.delete((SysDeptRole)iter.next());
				}
				//创建新角色
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
