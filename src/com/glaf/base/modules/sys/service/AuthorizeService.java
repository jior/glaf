package com.glaf.base.modules.sys.service;

import com.glaf.base.modules.sys.model.SysUser;

public interface AuthorizeService {

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @return
	 */
	SysUser login(String account);

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	SysUser login(String account, String pwd);

}
