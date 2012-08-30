package com.glaf.base.callback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glaf.base.modules.sys.model.SysUser;

/**
 * 
 * ��¼�ɹ���Ļص��ӿ�
 * 
 */
public interface LoginCallback {

	/**
	 * ��¼֮��Ҫ����ķ���
	 * 
	 * @param loginUser
	 *            ��¼�û�
	 * @param request
	 * @param response
	 */
	void afterLogin(SysUser loginUser, HttpServletRequest request,
			HttpServletResponse response);

}
