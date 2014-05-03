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

package com.glaf.jbpm.action;

import java.util.Collection;
import java.util.Map;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

public class JoinEndForkedTokens implements ActionHandler {
	private static final long serialVersionUID = 8679390550752208189L;

	public JoinEndForkedTokens() {
	}

	public void execute(ExecutionContext executionContext) {
		Token token = executionContext.getToken();
		Map<String, Token> childTokens = token.getActiveChildren();
		for (Object childToken : childTokens.values()) {
			cancelToken(executionContext, (Token) childToken);
		}
	}

	/**
	 * Cancel token
	 * 
	 * @param executionContext
	 * @param token
	 */
	protected void cancelToken(ExecutionContext executionContext, Token token) {
		// visit child tokens
		Map<String, Token> childTokens = token.getActiveChildren();
		for (Object childToken : childTokens.values()) {
			cancelToken(executionContext, (Token) childToken);
		}

		// end token
		if (!token.hasEnded()) {
			token.end(false);
		}

		// end any associated tasks
		cancelTokenTasks(executionContext, token);
	}

	/**
	 * Cancel tasks associated with a token
	 * 
	 * @param executionContext
	 * @param token
	 */
	protected void cancelTokenTasks(ExecutionContext executionContext,
			Token token) {
		TaskMgmtInstance tms = executionContext.getTaskMgmtInstance();
		Collection<TaskInstance> tasks = tms.getUnfinishedTasks(token);
		for (Object task : tasks) {
			TaskInstance taskInstance = (TaskInstance) task;
			if (taskInstance.isBlocking()) {
				taskInstance.setBlocking(false);
			}
			if (taskInstance.isSignalling()) {
				taskInstance.setSignalling(false);
			}
			if (!taskInstance.hasEnded()) {
				taskInstance.cancel();
			}
		}
	}
}