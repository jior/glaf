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


package org.jpage.jbpm.util;

import java.util.HashMap;
import java.util.Map;

public class ProcessActionType {

	public final static int SAVA_DATA_TYPE = 0;

	public final static int START_PROCESS_TYPE = 1;

	public final static int COMPLETE_TASK_TYPE = 3;

	public final static int FINISH_PROCESS_TYPE = 4;

	public final static String START_PROCESS = "START_PROCESS";

	public final static String COMPLETE_TASK = "COMPLETE_TASK";

	public final static String SAVA_DATA = "SAVA_DATA";

	private static Map dataMap = new HashMap();

	static {
		dataMap.put(SAVA_DATA, new Integer(SAVA_DATA_TYPE));
		dataMap.put(START_PROCESS, new Integer(START_PROCESS_TYPE));
		dataMap.put(COMPLETE_TASK, new Integer(COMPLETE_TASK_TYPE));
	}

	public static int getActionType(String actionType) {
		if (dataMap.get(actionType) != null) {
			return ((Integer) dataMap.get(actionType)).intValue();
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println(ProcessActionType
				.getActionType(ProcessActionType.START_PROCESS));
	}
}
