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

package com.glaf.activiti.delegate.vote;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class AddDecisionVote implements JavaDelegate {

	public void execute(DelegateExecution execution) {
		String assignee = (String) execution.getVariableLocal("assignee");
		Boolean voteOutcome = (Boolean) execution.getVariable("vote");
		Vote vote = new Vote();
		vote.setName(assignee);
		vote.setApproved(voteOutcome);
		DecisionVoting voting = (DecisionVoting) execution
				.getVariable("voteOutcome");
		voting.addVote(vote);
		execution.setVariable("voteOutcome", voting);
	}

}