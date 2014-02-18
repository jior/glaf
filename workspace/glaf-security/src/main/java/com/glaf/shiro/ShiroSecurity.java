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
package com.glaf.shiro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import com.glaf.core.util.Constants;

public class ShiroSecurity {
	protected final static Log logger = LogFactory.getLog(ShiroSecurity.class);

	public static void login(String actorId, String password) {
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername(actorId);
		token.setPassword(actorId.toCharArray());
		token.setRememberMe(true);
		try {
			Session session = currentUser.getSession();
			session.setAttribute(Constants.LOGIN_ACTORID, actorId);
			currentUser.login(token);
			logger.info("User [" + currentUser.getPrincipal()
					+ "] logged in successfully.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void logout() {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
			ThreadContext.unbindSubject();
			logger.debug(" shior logout.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
