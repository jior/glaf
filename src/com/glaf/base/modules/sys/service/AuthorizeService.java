package com.glaf.base.modules.sys.service;

import com.glaf.base.modules.sys.model.SysUser;

public interface AuthorizeService {

	/**
	 * �û���½
	 * 
	 * @param account
	 * @return
	 */
	SysUser login(String account);

	/**
	 * �û���½
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	SysUser login(String account, String pwd);

}
