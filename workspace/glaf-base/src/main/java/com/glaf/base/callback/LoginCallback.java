package com.glaf.base.callback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glaf.base.modules.sys.model.SysUser;

/**
 * 
 * 登录成功后的回调接口
 * 
 */
public interface LoginCallback {

	/**
	 * 登录之后要处理的方法
	 * 
	 * @param loginUser
	 *            登录用户
	 * @param request
	 * @param response
	 */
	void afterLogin(SysUser loginUser, HttpServletRequest request,
			HttpServletResponse response);

}
