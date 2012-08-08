package com.glaf.base.modules.sys.service;

import com.glaf.base.modules.sys.model.SysUser;

public interface AuthorizeService {

	/**
	 * ÓÃ»§µÇÂ½
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	SysUser login(String account, String pwd);

}
