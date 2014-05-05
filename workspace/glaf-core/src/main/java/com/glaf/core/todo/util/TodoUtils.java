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

package com.glaf.core.todo.util;

import java.util.Date;

import com.glaf.core.todo.Todo;
import com.glaf.core.util.DateUtils;

public class TodoUtils {

	public final static String TODO_CONFIG = "/conf/system/todo/";

	public final static int OK_STATUS = 1;

	public final static int CAUTION_STATUS = 3;

	public final static int PAST_DUE_STATUS = 4;

	public static int getTodoStatus(Todo todo, Date limitWorkDate) {
		return getTodoStatus(todo, limitWorkDate, new Date());
	}

	public static int getTodoStatus(Todo todo, Date limitWorkDate, Date date) {
		int status = 0;
		if (limitWorkDate != null && todo != null) {
			long limit = limitWorkDate.getTime();
			long now = System.currentTimeMillis();
			if (date != null) {
				now = date.getTime();
			}
			if (now > limit) {// 超过期限
				if ((now - limit) > todo.getXb() * DateUtils.HOUR) {
					status = PAST_DUE_STATUS;
				} else {
					status = CAUTION_STATUS;
				}
			} else {
				// 未到期限
				if ((limit - DateUtils.HOUR
						* (todo.getLimitDay() * 24 - todo.getXa())) > now) {
					status = CAUTION_STATUS;
				} else {
					status = OK_STATUS;
				}
			}
		}
		return status;
	}

	private TodoUtils() {

	}
}