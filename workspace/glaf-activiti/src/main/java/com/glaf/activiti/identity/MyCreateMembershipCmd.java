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

 
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import com.glaf.core.util.UUID32;

public class MyCreateMembershipCmd implements Command<Object> {

	String userId;
	String groupId;

	public MyCreateMembershipCmd(String userId, String groupId) {
		this.userId = userId;
		this.groupId = groupId;
	}

	public Object execute(CommandContext commandContext) {
		if (userId == null) {
			throw new ActivitiException("userId is null");
		}
		if (groupId == null) {
			throw new ActivitiException("groupId is null");
		}
		Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("id", UUID32.getUUID());
		parameters.put("userId", userId);
		parameters.put("groupId", groupId);
		commandContext.getDbSqlSession().getSqlSession()
				.insert("insertMembership", parameters);
		return null;
	}
}