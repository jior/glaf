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

package com.glaf.base.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.business.AuthorizeBean;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.RequestUtil;

public class PermissionTag extends BodyTagSupport {
	protected final transient Log logger = LogFactory
			.getLog(PermissionTag.class);

	private static final long serialVersionUID = 1L;

	private String key;

	private String operator;

	public PermissionTag() {

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int doStartTag() {
		String userId = RequestUtil
				.getActorId((HttpServletRequest) super.pageContext.getRequest());
		AuthorizeBean bean = new AuthorizeBean();
		SysUser user = bean.getUser(userId);
		boolean hasPermission = false;
		StringTokenizer token = new StringTokenizer(key, ",");
		Collection<String> roleIds = new ArrayList<String>();
		Collection<String> permissions = new ArrayList<String>();
		if (user != null) {
			if (user.isSystemAdmin()) {
				return 1;
			}
			Set<SysDeptRole> roles = user.getRoles();
			if (roles != null && !roles.isEmpty()) {
				for (SysDeptRole dr : roles) {
					if (dr.getRole() != null) {
						roleIds.add(dr.getRole().getCode());
					}
				}
			}
		}

		logger.debug("roleIds:" + roleIds);
		logger.debug("permissions:" + permissions);
		logger.debug("key:" + key);

		if (operator != null) {
			if ((operator.equals("||") || operator.equals("or"))) {
				while (token.hasMoreTokens()) {
					String permKey = token.nextToken();
					if (roleIds != null && permissions != null) {
						if (permissions.contains(permKey)
								|| roleIds.contains(permKey)) {
							hasPermission = true;
						}
					}
				}
			}
		} else {
			if (roleIds != null && permissions != null) {
				while (token.hasMoreTokens()) {
					String permKey = token.nextToken();
					if (permissions.contains(permKey)
							|| roleIds.contains(permKey)) {
						hasPermission = true;
					}
				}
			}
		}
		return hasPermission ? 1 : 0;
	}
}