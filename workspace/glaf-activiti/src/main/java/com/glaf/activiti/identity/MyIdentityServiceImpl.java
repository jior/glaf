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

import org.activiti.engine.impl.IdentityServiceImpl;

public class MyIdentityServiceImpl extends IdentityServiceImpl {

	@Override
	public boolean checkPassword(String userId, String password) {
		return commandExecutor.execute(new MyCheckPassword(userId, password));
	}

	@Override
	public void createMembership(String userId, String groupId) {
		commandExecutor.execute(new MyCreateMembershipCmd(userId, groupId));
	}

}