package com.glaf.base.callback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.SysUser;

public class SimpleLoginCallback implements LoginCallback {
	private static final Log logger = LogFactory
			.getLog(SimpleLoginCallback.class);

	public void afterLogin(SysUser loginUser, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug(loginUser.getName() + " is Login. ");
	}

}
