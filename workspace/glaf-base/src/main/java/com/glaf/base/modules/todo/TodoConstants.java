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

package com.glaf.base.modules.todo;

import java.util.Date;

import com.glaf.core.todo.TodoInstance;

public class TodoConstants {

	public final static int OK_STATUS = 1;

	public final static int CAUTION_STATUS = 2;

	public final static int PAST_DUE_STATUS = 3;

	private TodoConstants() {

	}

	public static int getTodoStatus(TodoInstance model) {
		int status = 0;
		if (model != null) {
			long now = System.currentTimeMillis();
			Date startDate = model.getStartDate();
			Date alarmDate = model.getAlarmDate();
			Date pastDueDate = model.getPastDueDate();
			if (pastDueDate != null && now > pastDueDate.getTime()) {
				status = PAST_DUE_STATUS;
			} else if (alarmDate != null && now < alarmDate.getTime()) {
				status = OK_STATUS;
			} else {
				status = CAUTION_STATUS;
			}

			if (startDate != null) {
				if (pastDueDate.getTime() < startDate.getTime()
						|| alarmDate.getTime() < startDate.getTime()) {
					status = PAST_DUE_STATUS;
				}
			}
		}
		return status;
	}
}