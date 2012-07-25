package com.glaf.base.modules.sys.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.jpage.util.DigestUtil;
import org.springframework.web.struts.DispatchActionSupport;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.PageResult;
import com.glaf.base.utils.ParamUtil;

public class SysUserAction extends DispatchActionSupport {
	private Log logger = LogFactory.getLog(SysUserAction.class);
	private SysUserService sysUserService;
	private SysDeptRoleService sysDeptRoleService;
	private SysTreeService sysTreeService;
	private SysDepartmentService sysDepartmentService;
	private SysRoleService sysRoleService;

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;
		logger.info("setSysDeptRoleService");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
		logger.info("setSysDepartmentService");
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
		logger.info("setSysRoleService");
	}

	/**
	 * 显示所有列表
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
		int deptId = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);
		PageResult pager = sysUserService.getSysUserList(deptId, pageNo,
				pageSize);
		request.setAttribute("department",
				sysDepartmentService.findById(deptId));
		request.setAttribute("pager", pager);

		SysDepartment dept = sysDepartmentService.findById(deptId);
		List list = new ArrayList();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		return mapping.findForward("show_list");
	}

	/**
	 * 显示增加页面
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
	 * 提交增加信息
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

		SysUser bean = new SysUser();
		SysDepartment department = sysDepartmentService.findById(ParamUtil
				.getIntParameter(request, "parent", 0));
		bean.setDepartment(department);
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setAccount(bean.getCode());
		bean.setName(ParamUtil.getParameter(request, "name"));
		// bean.setPassword(ParamUtil.getParameter(request, "password"));
		String password = ParamUtil.getParameter(request, "password");
		String pwd = DigestUtil.digestString(password, "MD5");
		bean.setPassword(pwd);
		bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
		bean.setMobile(ParamUtil.getParameter(request, "mobile"));
		bean.setEmail(ParamUtil.getParameter(request, "email"));
		bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
		bean.setBlocked(ParamUtil.getIntParameter(request, "blocked", 0));
		bean.setHeadship(ParamUtil.getParameter(request, "headship"));
		bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
		bean.setEvection(0);
		bean.setCreateTime(new Date());
		bean.setLastLoginTime(new Date());

		int ret = 0;
		if (sysUserService.findByAccount(bean.getAccount()) == null) {
			if (sysUserService.create(bean))
				ret = 2;
		} else {// 帐号存在
			ret = 1;
		}

		ActionMessages messages = new ActionMessages();
		if (ret == 2) {// 保存成功
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.add_success"));
		} else if (ret == 1) {// 保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.existed"));
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.add_failure"));
		}
		addMessages(request, messages);

		// 显示列表页面
		return mapping.findForward("show_msg");
	}

	/**
	 * 显示修改页面
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
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		List list = new ArrayList();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		return mapping.findForward("show_modify");
	}

	/**
	 * 提交修改信息
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
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
		if (bean != null) {
			SysDepartment department = sysDepartmentService.findById(ParamUtil
					.getIntParameter(request, "parent", 0));
			bean.setDepartment(department);
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setPassword(ParamUtil.getParameter(request, "password"));
			// bean.setPassword(CryptUtil.EnCryptPassword(ParamUtil.getParameter(request,
			// "password")));
			bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
			bean.setMobile(ParamUtil.getParameter(request, "mobile"));
			bean.setEmail(ParamUtil.getParameter(request, "email"));
			bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
			bean.setEvection(ParamUtil.getIntParameter(request, "evection", 0));
			bean.setBlocked(ParamUtil.getIntParameter(request, "blocked", 0));
			bean.setHeadship(ParamUtil.getParameter(request, "headship"));
			bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
		}

		boolean ret = sysUserService.update(bean);
		ActionMessages messages = new ActionMessages();
		if (ret) {// 保存成功
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_success"));
		} else {// 保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}

	/**
	 * 批量删除信息
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
		ret = sysUserService.deleteAll(id);
		ActionMessages messages = new ActionMessages();
		if (ret) {// 保存成功
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.delete_success"));
		} else {// 保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.delete_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg2");
	}

	/**
	 * 显示角色
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showRole(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long id = ParamUtil.getIntParameter(request, "user_id", 0);
		SysUser user = sysUserService.findById(id);
		request.setAttribute("user", user);
		request.setAttribute("list",
				sysDeptRoleService.getRoleList(user.getDepartment().getId()));
		// 显示列表页面
		return mapping.findForward("show_role");
	}

	/**
	 * 设置用户角色
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setRole(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		long userId = ParamUtil.getIntParameter(request, "user_id", 0);
		SysUser user = sysUserService.findById(userId);// 查找用户对象
		if (user != null) {// 用户存在
			long[] id = ParamUtil.getLongParameterValues(request, "id");// 获取页面参数
			if (id != null) {
				Set delRoles = new HashSet();
				Set oldRoles = user.getRoles();
				Set newRoles = new HashSet();
				for (int i = 0; i < id.length; i++) {
					SysDeptRole role = sysDeptRoleService.findById(id[i]);// 查找角色对象
					if (role != null) {
						newRoles.add(role);// 加入到角色列表
					}
				}
				delRoles.addAll(oldRoles);
				oldRoles.retainAll(newRoles);// 公共权限
				delRoles.removeAll(newRoles);// 待删除的权限
				newRoles.removeAll(oldRoles);// 待增加的权限

				if (sysUserService.updateRole(user, delRoles, newRoles)) {// 授权成功
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("user.role_success"));
				} else {// 保存失败
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("user.role_failure"));
				}
			}
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}

	/**
	 * 显示框架页面
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
		return mapping.findForward("show_frame");
	}

	/**
	 * 显示采购担当
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPurchaseDuty(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long deptId = ParamUtil.getLongParameter(request, "dept", 310);
		String roleCode = ParamUtil.getParameter(request, "code", "R010");
		logger.info("dept = " + deptId + "     , code = " + roleCode);
		request.setAttribute("user",
				sysDeptRoleService.findRoleUser(deptId, roleCode));
		return mapping.findForward("show_duty");
	}

	/**
	 * 显示部门下所有人
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showDeptUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = new ArrayList();
		Set set = new HashSet();
		// 6:
		long deptId = ParamUtil.getLongParameter(request, "dept", 5);
		String roleCode = ParamUtil.getParameter(request, "code", "");
		SysDepartment node = this.sysDepartmentService.findById(deptId);
		if (node != null) {
			list.add(node);
			this.getAllSysDepartmentList(list, (int) node.getId());
		} else {
			this.getAllSysDepartmentList(list, (int) deptId);
		}
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			SysDepartment element = (SysDepartment) iter.next();
			this.getRoleUser(set, element.getId(), roleCode);
		}
		request.setAttribute("user", set);
		return mapping.findForward("show_duty");
	}

	/**
	 * 得到部门下所有部门列表
	 * 
	 * @param list
	 * @param parentId
	 */
	public void getAllSysDepartmentList(List list, int parentId) {
		List temp = new ArrayList();
		temp = this.sysDepartmentService.getSysDepartmentList(parentId);
		if (temp != null && temp.size() != 0) {
			for (int i = 0; i < temp.size(); i++) {
				SysDepartment element = (SysDepartment) temp.get(i);
				getAllSysDepartmentList(list, (int) element.getId());
			}
			list.addAll(temp);
		}
	}

	/**
	 * 得到本部门下所属角色的人 如果没有角色，则得到本部门里所有人
	 * 
	 * @param set
	 * @param deptId
	 * @param code
	 */
	public void getRoleUser(Set set, long deptId, String code) {
		if (!"".equals(code)) {
			Set temp = sysDeptRoleService.findRoleUser(deptId, "R011");
			set.addAll(temp);
		} else {
			List list = sysUserService.getSysUserList((int) deptId);
			set.addAll(list);
		}
	}

	/**
	 * 显示修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareModifyInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute(
				SysConstants.LOGIN);
		request.setAttribute("bean", user);

		return mapping.findForward("show_modify_info");
	}

	/**
	 * 提交修改信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveModifyInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUser bean = (SysUser) request.getSession().getAttribute(
				SysConstants.LOGIN);
		if (bean != null) {
			bean.setPassword(ParamUtil.getParameter(request, "password"));
			// bean.setPassword(CryptUtil.EnCryptPassword(ParamUtil.getParameter(request,
			// "password")));
			bean.setMobile(ParamUtil.getParameter(request, "mobile"));
			bean.setEmail(ParamUtil.getParameter(request, "email"));
			bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
		}

		boolean ret = sysUserService.update(bean);
		ActionMessages messages = new ActionMessages();
		if (ret) {// 保存成功
			request.getSession().setAttribute(SysConstants.LOGIN, bean);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_success"));
		} else {// 保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}

	/**
	 * 显示角色用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showRoleUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);

		// 部门信息
		SysDepartment dept = sysDepartmentService.findById(deptId);
		List list = new ArrayList();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		// 角色
		SysRole role = sysRoleService.findById(roleId);
		request.setAttribute("role", role.getName());

		Set users = sysDeptRoleService.findRoleUser(deptId, role.getCode());
		request.setAttribute("list", users);

		return mapping.findForward("show_role_user");
	}

	/**
	 * 显示角色用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showSelUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int deptId = ParamUtil.getIntParameter(request, "deptId2", 0);
		if (deptId != 0) {
			request.setAttribute("list", sysUserService.getSysUserList(deptId));
		}

		return mapping.findForward("show_sel_user");
	}

	/**
	 * 增加角色用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addRoleUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
		Set users = deptRole.getUsers();

		long[] userIds = ParamUtil.getLongParameterValues(request, "id");
		for (int i = 0; i < userIds.length; i++) {
			SysUser user = sysUserService.findById(userIds[i]);
			if (user != null) {
				logger.info(user.getName());
				users.add(user);
			}
		}
		deptRole.setUsers(users);

		ActionMessages messages = new ActionMessages();
		if (sysDeptRoleService.update(deptRole)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.add_success"));
		} else {// 保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.add_failure"));
		}
		addMessages(request, messages);

		return mapping.findForward("show_msg");
	}

	/**
	 * 删除角色用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delRoleUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
		Set users = deptRole.getUsers();

		long[] userIds = ParamUtil.getLongParameterValues(request, "id");
		for (int i = 0; i < userIds.length; i++) {
			SysUser user = sysUserService.findById(userIds[i]);
			if (user != null) {
				logger.info(user.getName());
				users.remove(user);
			}
		}
		deptRole.setUsers(users);

		ActionMessages messages = new ActionMessages();
		if (sysDeptRoleService.update(deptRole)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.delete_success"));
		} else {// 保存失败
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.delete_failure"));
		}
		addMessages(request, messages);

		return mapping.findForward("show_msg2");
	}

	/**
	 * 查询获取用户列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectSysUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = Constants.PAGE_SIZE;
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int multDate = ParamUtil.getIntParameter(request, "multDate", 0);
		String name = ParamUtil.getParameter(request, "fullName", null);
		PageResult pager = sysUserService.getSysUserList(deptId, name, pageNo,
				pageSize);
		request.setAttribute("pager", pager);
		request.setAttribute("multDate", new Integer(multDate));

		return mapping.findForward("sysUserList");
	}

	/**
	 * 查询获取特定部门用户列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @author zoumin
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectSysUserByDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = Constants.PAGE_SIZE;
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		String name = ParamUtil.getParameter(request, "fullName", null);

		SysDepartment sysDepartment = sysDepartmentService.findById(deptId);
		request.setAttribute("sysDepartment", sysDepartment);
		PageResult pager = null;
		if (name != null && !"".equals(name)) {
			pager = sysUserService.getSysUserList(deptId, name, pageNo,
					pageSize);
		} else {
			pager = sysUserService.getSysUserList(deptId, pageNo, pageSize);
		}
		request.setAttribute("pager", pager);

		return mapping.findForward("userByDept_list");
	}

	/**
	 * 增加角色用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showUser(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long userId = ParamUtil.getLongParameter(request, "userId", 0);
		SysUser user = sysUserService.findById(userId);
		request.setAttribute("user", user);

		return mapping.findForward("show_user");
	}

	/**
	 * 显示所有列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPasswordList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String userName = ParamUtil.getParameter(request, "userName");
		String account = ParamUtil.getParameter(request, "account");
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", 20);
		// sysUserService.updateSysUserPassword();//初始化所有用户密码进行加密
		PageResult pager = sysUserService.getSysUserList(deptId, userName,
				account, pageNo, pageSize);
		request.setAttribute("pager", pager);
		return mapping.findForward("showPasswordList");
	}
}
