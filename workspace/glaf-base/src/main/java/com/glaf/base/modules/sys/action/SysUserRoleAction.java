package com.glaf.base.modules.sys.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.utils.BaseUtil;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.base.utils.WebUtil;

public class SysUserRoleAction extends DispatchActionSupport{
	private static final Log logger = LogFactory.getLog(SysUserRoleAction.class);
	
	private SysUserRoleService sysUserRoleService;
	private SysUserService sysUserService;
	
	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
		logger.info("setSysUserRoleService");
	}
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}
	


	/**
	 * 显示授权页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showMain(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		SysUser user = RequestUtil.getLoginUser(request);
		request.setAttribute("available", sysUserRoleService.getUnAuthorizedUser(user));
		request.setAttribute("unavailable", sysUserRoleService.getAuthorizedUser(user));
		
		//显示列表页面
		return mapping.findForward("show_list");
	}
	/**
	 * 显示授权页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showUsers(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		Map filter = WebUtil.getQueryMap(request);
		request.setAttribute("pager", sysUserRoleService.getAllAuthorizedUser(filter));
		//显示列表页面
		return mapping.findForward("show_users");
	}
	/**
	 * 显示授权页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showSysAuth(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		long userId = ParamUtil.getLongParameter(request, "id", 0);
		SysUser user = (SysUser)sysUserService.findById(userId);
		if(user==null){
			  user = RequestUtil.getLoginUser(request);
		}
		
		request.setAttribute("user", user);
		request.setAttribute("authorizedUser", sysUserRoleService.getAuthorizedUser(user));
		request.setAttribute("processList", sysUserRoleService.getProcessByUser(user));
		
		//显示列表页面
		return mapping.findForward("show_auth_panel");
	}
	
	/**
	 * 显示授权页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showUserSysAuth(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		long userId = ParamUtil.getLongParameter(request, "id", 0);
		SysUser user = (SysUser)sysUserService.findById(userId);
		if(user==null){
			user = RequestUtil.getLoginUser(request);
		}
		
		request.setAttribute("user", user);
		request.setAttribute("authorizedUser", sysUserRoleService.getAuthorizedUser(user));
		
		//显示列表页面
		return mapping.findForward("show_userAuth_panel");
	}
	
	/**
	 * 保存用户授权
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveUserSysAuth(
		      ActionMapping mapping,
		      ActionForm actionForm,
		      HttpServletRequest request,
		      HttpServletResponse response) throws Exception{
		long fromUserId = ParamUtil.getLongParameter(request, "uid", 0);
		long[] userIds = ParamUtil.getLongParameterValues(request, "userIds");
		
		SysUser user = sysUserService.findById(fromUserId);
		SysUser rootUser = sysUserService.findByAccount("root");//管理员
		
		String msgStr = user.getName()+"["+user.getAccount()+"]的受权列表如下:<br><br>";
		ActionMessages messages = new ActionMessages();
		if(fromUserId!=0 && userIds.length>0){
			//取得授权列表
			List userList = sysUserRoleService.getAuthorizedUser(user);
			logger.info("userList.size()=>"+userList.size());
			
			authorStart:
			for(int i=0;i<userIds.length;i++){
				SysUser sysUser = sysUserService.findById(userIds[i]);
				
				String startDate = ParamUtil.getParameter(request, "startDate_"+userIds[i], "");
				String endDate = ParamUtil.getParameter(request, "endDate_"+userIds[i], "");
				
				if(!startDate.equals("") && !endDate.equals("")){
					for(int j=0;j<userList.size();j++){
						  Object[] bean = (Object[])userList.get(j);
						  SysUser authorUser = (SysUser)bean[0];
						if(authorUser.getId()==sysUser.getId()){//已授权
							msgStr=msgStr+"&nbsp;&nbsp;&nbsp;&nbsp;修改授权=>"+sysUser.getName()+"["+sysUser.getAccount()+"]&nbsp;&nbsp;"+startDate+"至"+endDate+"<br>";
							logger.info(msgStr);
							userList.remove(j);
							
							continue authorStart;
						}
					}
					
					msgStr=msgStr+"&nbsp;&nbsp;&nbsp;&nbsp;添加授权=>"+sysUser.getName()+"["+sysUser.getAccount()+"]&nbsp;&nbsp;"+startDate+"至"+endDate+"<br>";
					logger.info(msgStr);
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							sysUser.getName()+"["+sysUser.getAccount()+"]"+"sys.author_dateErr"));
					
					return mapping.findForward("show_msg");
				}
			}
			
			for(int i=0;i<userList.size();i++){
				Object[] bean = (Object[])userList.get(i);
				SysUser authorUser = (SysUser)bean[0];
				Date aStartDate=(Date)bean[1];
				Date aEndDate=(Date)bean[2];
				msgStr=msgStr+"&nbsp;&nbsp;&nbsp;&nbsp;取消授权=>"+authorUser.getName()+"["+authorUser.getAccount()+"]&nbsp;&nbsp;"+BaseUtil.dateToString(aStartDate)+"至"+BaseUtil.dateToString(aEndDate)+"<br>";
				logger.info(msgStr);
			}
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"sys.author_msgErr"));
			
			return mapping.findForward("show_msg");
		}
		
		if(sendMail(user,rootUser,"特采授权书",msgStr)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"sys.author_success"));
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"sys.author_failure"));
		}
		
		addMessages(request, messages);
		request.setAttribute("refresh", "false");
		//显示列表页面
		return mapping.findForward("show_msg");
	}
	
	/**
	 * 向管理员发送授权电邮
	 * @param fromUser
	 * @param toUser
	 * @param msgStr
	 * @return
	 * @throws Exception
	 */
	private boolean sendMail(SysUser fromUser, SysUser toUser, String title, String msgStr)
			throws Exception {
		boolean rst = true;

		String subject = title;
		String context = msgStr;
		logger.info(fromUser.getEmail()+"--"+toUser.getEmail()+"--"+
				subject+"--"+ context);
		// EMail.send(user.getEmail(), order.getSupplier().getBusinessEmail(),
		// "business_callpricefile", context, attach);
//		EMail.send(fromUser.getEmail(), toUser.getEmail(),
//				subject, context, null);

		return rst;
	}
	
}
