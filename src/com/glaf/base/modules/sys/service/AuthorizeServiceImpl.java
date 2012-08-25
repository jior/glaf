package com.glaf.base.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.SysUser;

public class AuthorizeServiceImpl implements AuthorizeService {
	private static final Log logger = LogFactory
			.getLog(AuthorizeServiceImpl.class);
	private SysUserService sysUserService;
	private SysDepartmentService sysDepartmentService;

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	/**
	 * �û���½
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	public SysUser login(String account) {
		SysUser bean = sysUserService.findByAccount(account);
		if (bean != null) {
			org.hibernate.Hibernate.initialize(bean.getRoles());
			org.hibernate.Hibernate.initialize(bean.getUserRoles());
			org.hibernate.Hibernate.initialize(bean.getDepartment());
			org.hibernate.Hibernate.initialize(bean.getFunctions());
			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if (bean.getAccountType() != 1) {
				// ȡ���û���Ӧ��ģ��Ȩ��
				bean = sysUserService.getUserPrivileges(bean);
				// bean=sysUserService.getUserAndPrivileges(bean);
				// ȡ���û��Ĳ����б�
				List list = new ArrayList();
				sysDepartmentService.findNestingDepartment(list,
						bean.getDepartment());
				bean.setNestingDepartment(list);
			}
		}
		return bean;
	}

	/**
	 * �û���½
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	public SysUser login(String account, String pwd) {
		SysUser bean = sysUserService.findByAccount(account);
		if (bean != null) {
			org.hibernate.Hibernate.initialize(bean.getRoles());
			org.hibernate.Hibernate.initialize(bean.getUserRoles());
			org.hibernate.Hibernate.initialize(bean.getDepartment());
			org.hibernate.Hibernate.initialize(bean.getFunctions());
			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if (!bean.getPassword().equals(pwd) || // ���벻ƥ��
					bean.getBlocked() == 1) {// �ʺŽ�ֹ
				bean = null;
			} else if (bean.getAccountType() != 1) {
				// ȡ���û���Ӧ��ģ��Ȩ��
				bean = sysUserService.getUserPrivileges(bean);
				// bean=sysUserService.getUserAndPrivileges(bean);
				// ȡ���û��Ĳ����б�
				List list = new ArrayList();
				sysDepartmentService.findNestingDepartment(list,
						bean.getDepartment());
				bean.setNestingDepartment(list);
			}
		}
		return bean;
	}

}
