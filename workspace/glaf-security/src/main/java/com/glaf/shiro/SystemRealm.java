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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;

public class SystemRealm extends AuthorizingRealm {
	protected final static Log logger = LogFactory.getLog(SystemRealm.class);

	public SystemRealm() {
		super();
		setName("SystemRealm");
		setCredentialsMatcher(new AllowAllCredentialsMatcher());
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String actorId = upToken.getUsername();
		User user = IdentityFactory.getUser(actorId);
		String password = user.getPassword();
		if (password == null) {
			password = user.getActorId();
		}
		AuthenticationInfo info = new SimpleAuthenticationInfo(actorId,
				password.toCharArray(), getName());
		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException(
					"PrincipalCollection method argument cannot be null.");
		}
		String actorId = (String) getAvailablePrincipal(principals);
		Set<String> roles = new HashSet<String>();
		Set<String> perms = new HashSet<String>();
		if (actorId != null) {

			LoginContext loginContext = IdentityFactory
					.getLoginContext(actorId);

			if (loginContext.isSystemAdministrator()) {
				perms.add("SystemAdministrator");
				roles.add("SystemAdministrator");
			}
			Collection<String> roleIds = loginContext.getRoles();
			if (roleIds != null && !roleIds.isEmpty()) {
				for (String roleId : roleIds) {
					if (StringUtils.isNotEmpty(roleId)) {
						if (!StringUtils.contains(roleId, ":")) {
							roles.add(roleId);
						}
						perms.add(roleId);
					}
				}
			}
			Collection<String> permissions = loginContext.getPermissions();
			if (permissions != null && !permissions.isEmpty()) {
				for (String p : permissions) {
					if (StringUtils.isNotEmpty(p)) {
						if (!StringUtils.contains(p, ":")) {
							roles.add(p);
						}
						perms.add(p);
					}
				}
			}
			permissions = loginContext.getFunctions();
			if (permissions != null && !permissions.isEmpty()) {
				for (String p : permissions) {
					if (StringUtils.isNotEmpty(p)) {
						perms.add(p);
					}
				}
			}
		}
		logger.info("-----------------------@shiro@--------------------");
		logger.info("shiro roles:{" + roles + "}");
		logger.info("shiro perms:{" + perms + "}");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		info.setStringPermissions(perms);
		return info;
	}

}
