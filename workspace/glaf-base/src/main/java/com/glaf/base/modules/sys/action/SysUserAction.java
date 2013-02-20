/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.glaf.base.modules.sys.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.glaf.base.utils.RequestUtil;

public class SysUserAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(SysUserAction.class);
	private SysUserService sysUserService;
	private SysDeptRoleService sysDeptRoleService;
	private SysTreeService sysTreeService;
	private SysDepartmentService sysDepartmentService;
	private SysRoleService sysRoleService;

	/**
	 * ���ӽ�ɫ�û�
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
		if(deptRole == null){
			deptRole = new SysDeptRole();
			deptRole.setDeptId(deptId);
			deptRole.setDept(sysDepartmentService.findById(deptId));
			deptRole.setRole(sysRoleService.findById(roleId));
			sysDeptRoleService.create(deptRole);
		}
		if (deptRole != null) {
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
		}

		ActionMessages messages = new ActionMessages();
		if (sysDeptRoleService.update(deptRole)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.add_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.add_failure"));
		}
		addMessages(request, messages);

		return mapping.findForward("show_msg");
	}

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
		ret = sysUserService.deleteAll(id);
		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.delete_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.delete_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg2");
	}

	/**
	 * ɾ����ɫ�û�
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
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.delete_failure"));
		}
		addMessages(request, messages);

		return mapping.findForward("show_msg2");
	}

	/**
	 * �õ����������в����б�
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
	 * �õ���������������ɫ���� ���û�н�ɫ����õ���������������
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
	 * ��ʾ�޸�ҳ��
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
		SysUser user = RequestUtil.getLoginUser(request);
		request.setAttribute("bean", user);

		return mapping.findForward("show_modify_info");
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
	public ActionForward prepareModifyPwd(ActionMapping mapping,
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

		return mapping.findForward("show_modify_pwd");
	}

	/**
	 * ��ʾ��������ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareResetPwd(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
		request.setAttribute("bean", bean);

		return mapping.findForward("show_reset_pwd");
	}

	/**
	 * �����û�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetPwd(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser login = RequestUtil.getLoginUser(request);
		boolean ret = false;

		if (login.isSystemAdmin()) {
			logger.debug(login.getAccount() + " is system admin");
		}

		if (login.isDepartmentAdmin()) {
			logger.debug(login.getAccount() + " is dept admin");
		}

		if (login.isDepartmentAdmin() || login.isSystemAdmin()) {

			long id = ParamUtil.getIntParameter(request, "id", 0);
			SysUser bean = sysUserService.findById(id);

			String newPwd = ParamUtil.getParameter(request, "newPwd");
			if (bean != null && StringUtils.isNotEmpty(newPwd)) {
				bean.setPassword(DigestUtil.digestString(newPwd, "MD5"));
				ret = sysUserService.update(bean);
			}
		}

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
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

		SysUser bean = new SysUser();
		SysDepartment department = sysDepartmentService.findById(ParamUtil
				.getIntParameter(request, "parent", 0));
		bean.setDepartment(department);
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setAccount(bean.getCode());
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setSuperiorIds(ParamUtil.getParameter(request, "superiorIds"));
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
		} else {// �ʺŴ���
			ret = 1;
		}

		ActionMessages messages = new ActionMessages();
		if (ret == 2) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.add_success"));
		} else if (ret == 1) {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.existed"));
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.add_failure"));
		}
		addMessages(request, messages);

		// ��ʾ�б�ҳ��
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
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
		boolean ret = false;
		if (bean != null) {
			SysDepartment department = sysDepartmentService.findById(ParamUtil
					.getIntParameter(request, "parent", 0));
			bean.setDepartment(department);
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setSuperiorIds(ParamUtil.getParameter(request, "superiorIds"));
			bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
			bean.setMobile(ParamUtil.getParameter(request, "mobile"));
			bean.setEmail(ParamUtil.getParameter(request, "email"));
			bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
			bean.setEvection(ParamUtil.getIntParameter(request, "evection", 0));
			bean.setBlocked(ParamUtil.getIntParameter(request, "blocked", 0));
			bean.setHeadship(ParamUtil.getParameter(request, "headship"));
			bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
			ret = sysUserService.update(bean);
		}

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_failure"));
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
	public ActionForward saveModifyInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		if (bean != null) {
			// bean.setPassword(ParamUtil.getParameter(request, "password"));
			// bean.setPassword(CryptUtil.EnCryptPassword(ParamUtil.getParameter(request,
			// "password")));
			SysUser user = sysUserService.findById(bean.getId());
			user.setMobile(ParamUtil.getParameter(request, "mobile"));
			user.setEmail(ParamUtil.getParameter(request, "email"));
			user.setTelephone(ParamUtil.getParameter(request, "telephone"));
			ret = sysUserService.update(user);
		}

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			RequestUtil.setLoginUser(request, bean);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}

	/**
	 * �޸��û�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePwd(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser bean= RequestUtil.getLoginUser(request);
		boolean ret = false;
		String oldPwd = ParamUtil.getParameter(request, "oldPwd");
		String newPwd = ParamUtil.getParameter(request, "newPwd");
		if (bean != null && StringUtils.isNotEmpty(oldPwd)
				&& StringUtils.isNotEmpty(newPwd)) {
			SysUser user = sysUserService.findById(bean.getId());
			// bean.setPassword(ParamUtil.getParameter(request, "password"));
			// bean.setPassword(CryptUtil.EnCryptPassword(ParamUtil.getParameter(request,
			// "password")));

			String encPwd = DigestUtil.digestString(oldPwd, "MD5");
			if (StringUtils.equals(encPwd, user.getPassword())) {
				user.setPassword(DigestUtil.digestString(newPwd, "MD5"));
				ret = sysUserService.update(user);
			}
		}

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.modify_failure"));
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}

	/**
	 * ��ѯ��ȡ�û��б�
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
	 * ��ѯ��ȡ�ض������û��б�
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
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
	 * �����û���ɫ
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
		SysUser user = sysUserService.findById(userId);// �����û�����
		if (user != null) {// �û�����
			long[] id = ParamUtil.getLongParameterValues(request, "id");// ��ȡҳ�����
			if (id != null) {
				Set delRoles = new HashSet();
				Set oldRoles = user.getRoles();
				Set newRoles = new HashSet();
				for (int i = 0; i < id.length; i++) {
					SysDeptRole role = sysDeptRoleService.findById(id[i]);// ���ҽ�ɫ����
					if (role != null) {
						newRoles.add(role);// ���뵽��ɫ�б�
					}
				}
				delRoles.addAll(oldRoles);
				oldRoles.retainAll(newRoles);// ����Ȩ��
				delRoles.removeAll(newRoles);// ��ɾ����Ȩ��
				newRoles.removeAll(oldRoles);// �����ӵ�Ȩ��

				if (sysUserService.updateRole(user, delRoles, newRoles)) {// ��Ȩ�ɹ�
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("user.role_success"));
				} else {// ����ʧ��
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("user.role_failure"));
				}
			}
		}
		addMessages(request, messages);
		return mapping.findForward("show_msg");
	}

	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
		logger.info("setSysDepartmentService");
	}

	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;
		logger.info("setSysDeptRoleService");
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
		logger.info("setSysRoleService");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

	/**
	 * ��ʾ������������
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
	 * ��ʾ�����б�
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
		// sysUserService.updateSysUserPassword();//��ʼ�������û�������м���
		PageResult pager = sysUserService.getSysUserList(deptId, userName,
				account, pageNo, pageSize);
		request.setAttribute("pager", pager);
		return mapping.findForward("showPasswordList");
	}

	/**
	 * ��ʾ��ɫ
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
		// ��ʾ�б�ҳ��
		return mapping.findForward("show_role");
	}

	/**
	 * ��ʾ��ɫ�û��б�
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

		// ������Ϣ
		SysDepartment dept = sysDepartmentService.findById(deptId);
		List list = new ArrayList();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		// ��ɫ
		SysRole role = sysRoleService.findById(roleId);
		request.setAttribute("role", role.getName());

		Set users = sysDeptRoleService.findRoleUser(deptId, role.getCode());
		request.setAttribute("list", users);

		return mapping.findForward("show_role_user");
	}

	/**
	 * ��ʾ��ɫ�û��б�
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
	 * ���ӽ�ɫ�û�
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
}