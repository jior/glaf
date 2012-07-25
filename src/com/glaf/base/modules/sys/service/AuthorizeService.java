package com.glaf.base.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.SysUser;
 

public class AuthorizeService {
	private Log logger = LogFactory.getLog(AuthorizeService.class);
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
	 * 用户登陆
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
			if (!bean.getPassword().equals(pwd) || // 密码不匹配
					bean.getBlocked() == 1) {// 帐号禁止
				bean = null;
			} else if (bean.getAccountType() != 1) {
				// 取出用户对应的模块权限
				bean = sysUserService.getUserPrivileges(bean);
				// bean=sysUserService.getUserAndPrivileges(bean);
				// 取出用户的部门列表
				List list = new ArrayList();
				sysDepartmentService.findNestingDepartment(list,
						bean.getDepartment());
				bean.setNestingDepartment(list);
			}
		}
		return bean;
	}

}
