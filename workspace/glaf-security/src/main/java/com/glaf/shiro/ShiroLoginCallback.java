package com.glaf.shiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.web.callback.LoginCallback;

public class ShiroLoginCallback implements LoginCallback {

	protected final static Log logger = LogFactory
			.getLog(ShiroLoginCallback.class);

	public void afterLogin(String actorId, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("---------------ShiroLoginCallback---------------");
		try {
			ShiroSecurity.login(actorId, "pwd");
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

}
