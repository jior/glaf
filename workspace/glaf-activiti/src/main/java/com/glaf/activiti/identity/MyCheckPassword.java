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

package com.glaf.activiti.identity;

import org.activiti.engine.identity.User;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import com.glaf.core.security.DigestUtil;

public class MyCheckPassword implements Command<Boolean> {

	String userId;
	String password;

	public MyCheckPassword(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public Boolean execute(CommandContext commandContext) {
		User user = commandContext.getUserIdentityManager()
				.findUserById(userId);
		if ((user != null) && (password != null)
				&& (user.getPassword() != null)) {
			if (password.equals(user.getPassword())) {
				return true;
			}
			String pwd = DigestUtil.digestString(password, "MD5");
			if (user.getPassword().equals(pwd)) {
				return true;
			}
			pwd = DigestUtil.digestString(password, "SHA");
			if (user.getPassword().equals(pwd)) {
				return true;
			}
		}
		return false;
	}

}