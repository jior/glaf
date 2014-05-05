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

package com.glaf.core.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.JoinPoint;
import org.springframework.aop.MethodBeforeAdvice;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SysLog;
import com.glaf.core.exceptions.AthenticationException;
import com.glaf.core.identity.User;
import com.glaf.core.security.Authentication;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.service.ISysLogService;
import com.glaf.core.util.Constants;
import com.glaf.core.util.ContextUtils;
import com.glaf.core.util.RequestUtils;

public class MethodInterceptor implements MethodBeforeAdvice {
	protected static final Log logger = LogFactory
			.getLog(MethodInterceptor.class);

	public MethodInterceptor() {
		logger.info("-----------------------MethodInterceptor------------");
	}

	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		logger.debug("-------------MethodInterceptor.before------------");
		String targetName = target.getClass().getName();
		String methodName = method.getName();
		logger.debug("target:" + targetName);
		logger.debug("method:" + methodName);
		if (StringUtils.startsWith(targetName,
				"org.springframework.web.servlet.view")) {
			return;
		}
		String operation = targetName + "." + methodName;
		String actorId = Authentication.getAuthenticatedActorId();
		boolean authorized = false;

		String ip = null;

		for (int i = 0; i < args.length; i++) {
			logger.debug("args:" + args[i]);
			if (args[i] instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) args[i];
				if (request != null) {
					ip = RequestUtils.getIPAddress(request);
					actorId = RequestUtils.getActorId(request);
					logger.debug("IP:" + ip + ", actorId:" + actorId);
				}
			}
		}

		// 拦截的功能在系统功能列表中
		if (checkSystemFunction(operation)) {
			// 在用户功能列表中，通过
			if (checkUserFunction(actorId, methodName)) {
				logger.debug("method is in user functions");
				authorized = true;
			}
		} else {// 拦截的功能不在系统功能列表中，通过
			logger.debug("method isn't in system functions");
			authorized = true;
		}

		try {
			LoginContext loginContext = IdentityFactory
					.getLoginContext(actorId);
			if (loginContext.isSystemAdministrator()) {
				/**
				 * 系统管理员拥有全部权限
				 */
				authorized = true;
			}
			User user = loginContext.getUser();
			SysLog sysLog = new SysLog();
			sysLog.setAccount(user.getActorId());
			sysLog.setOperate(operation);
			sysLog.setIp(ip);
			sysLog.setCreateTime(new Date());
			sysLog.setFlag(authorized ? 1 : 0);

			ISysLogService sysLogService = ContextFactory
					.getBean("sysLogService");
			sysLogService.create(sysLog);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (!authorized) {
			throw new AthenticationException("No Privileges.");
		}
	}

	public void beforeInvoke(JoinPoint invocation) throws Throwable {
		logger.debug("-------------MethodInterceptor.beforeInvoke------------");
		String targetName = invocation.getSignature().getDeclaringType()
				.getName();
		String methodName = invocation.getSignature().getName();
		Object[] args = invocation.getArgs();
		logger.debug("target:" + targetName);
		logger.debug("method:" + methodName);
		if (StringUtils.startsWith(targetName,
				"org.springframework.web.servlet.view")) {
			return;
		}
		String ip = null;
		String actorId = Authentication.getAuthenticatedActorId();

		for (int i = 0; i < args.length; i++) {
			logger.debug("args:" + args[i]);
			if (args[i] instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) args[i];
				if (request != null) {
					ip = RequestUtils.getIPAddress(request);
					actorId = RequestUtils.getActorId(request);
					logger.debug("IP:" + ip + ", actorId:" + actorId);
				}
			}
		}

		String operation = targetName + "." + methodName;

		boolean authorized = false;

		// 拦截的功能在系统功能列表中
		if (checkSystemFunction(operation)) {
			// 在用户功能列表中，通过
			if (checkUserFunction(actorId, methodName)) {
				logger.debug("method is in user functions");
				authorized = true;
			}
		} else {// 拦截的功能不在系统功能列表中，通过
			logger.debug("method isn't in system functions");
			authorized = true;
		}

		try {
			LoginContext loginContext = IdentityFactory
					.getLoginContext(actorId);
			if (loginContext.isSystemAdministrator()) {
				/**
				 * 系统管理员拥有全部权限
				 */
				authorized = true;
			}
			User user = loginContext.getUser();
			SysLog sysLog = new SysLog();
			sysLog.setAccount(user.getActorId());
			sysLog.setOperate(operation);
			sysLog.setIp(ip);
			sysLog.setCreateTime(new Date());
			sysLog.setFlag(authorized ? 1 : 0);
			ISysLogService sysLogService = ContextFactory
					.getBean("sysLogService");
			sysLogService.create(sysLog);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (!authorized) {
			throw new AthenticationException("No Privileges.");
		}
	}

	/**
	 * 检查功能是否存在系统功能列表中
	 * 
	 * @param methodName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean checkSystemFunction(String function) {
		boolean ret = false;
		if (ContextUtils.get(Constants.SYSTEM_PERMISSION_IDS) != null) {
			// 系统功能列表，在初始化servlet中加载
			Iterator<String> iter = ((List<String>) ContextUtils
					.get(Constants.SYSTEM_PERMISSION_IDS)).iterator();
			while (iter.hasNext()) {
				String item = (String) iter.next();
				if (StringUtils.equals(function, item)) {// 找到
					ret = true;
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * 检查功能是否存在用户功能列表中
	 * 
	 * @param methodName
	 * @return
	 */
	protected boolean checkUserFunction(String actorId, String methodName) {
		boolean ret = false;
		// 用户对象，在登陆后加载
		LoginContext loginContext = IdentityFactory.getLoginContext(actorId);
		Iterator<String> iter = loginContext.getFunctions().iterator();// 用户功能列表
		while (iter.hasNext()) {
			String function = iter.next();
			if (StringUtils.equals(methodName, function)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

}