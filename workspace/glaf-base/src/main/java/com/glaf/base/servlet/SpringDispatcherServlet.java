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

package com.glaf.base.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.glaf.core.config.Environment;
import com.glaf.core.jdbc.ConnectionThreadHolder;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ThreadContextHolder;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.Authentication;
import com.glaf.base.utils.RequestUtil;

public class SpringDispatcherServlet extends DispatcherServlet {

	protected static final Log logger = LogFactory
			.getLog(SpringDispatcherServlet.class);

	private static final long serialVersionUID = 1L;

	public SpringDispatcherServlet() {
		super();
	}

	@Override
	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("RequestURI:" + request.getRequestURI());
		try {
			String systemName = RequestUtils.getCurrentSystem(request);
			if (systemName != null && !StringUtils.equals("GLAF", systemName)) {
				Environment.setCurrentSystemName(systemName);
			}

			String actorId = RequestUtils.getActorId(request);
			if (actorId != null) {
				// logger.debug("actorId:" + actorId);
				Authentication.setAuthenticatedAccount(actorId);
			}

			SysUser user = RequestUtil.getLoginUser(request);
			if (user != null) {
				Authentication.setAuthenticatedUser(user);
				com.glaf.core.security.Authentication
						.setAuthenticatedActorId(user.getAccount());
			}

			/**
			 * 未登录或不是系统管理员，不允许访问系统管理地址
			 */
			if ((user == null) || (!user.isSystemAdmin())) {
				String uri = request.getRequestURI();
				logger.debug("request uri:" + uri);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		ThreadContextHolder.setHttpRequest(request);
		ThreadContextHolder.setHttpResponse(response);
		try {
			super.doService(request, response);
		} finally {
			Environment.removeCurrentSystemName();
			Environment.clear();
			Authentication.clear();
			ThreadContextHolder.clear();
			ConnectionThreadHolder.closeAndClear();
			com.glaf.core.security.Authentication.clear();
		}

	}

	@Override
	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext wac = super.initWebApplicationContext();
		return wac;
	}

}