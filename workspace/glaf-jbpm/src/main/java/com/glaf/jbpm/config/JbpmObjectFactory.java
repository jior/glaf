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

package com.glaf.jbpm.config;

import com.glaf.core.config.Configuration;

public class JbpmObjectFactory {

	private static Configuration conf = JbpmBaseConfiguration.create();

	public static boolean canAbortProcess() {
		boolean canAbortProcess = conf
				.getBoolean("jbpm.canAbortProcess", false);
		return canAbortProcess;
	}

	public static boolean canDeleteProcessInstance() {
		boolean canDeleteProcessInstance = conf.getBoolean(
				"jbpm.canDeleteProcessInstance", false);
		return canDeleteProcessInstance;
	}

	public static String getDefaultActors() {
		if (conf.get("jbpm.defaultActors") != null) {
			return conf.get("jbpm.defaultActors");
		}
		return null;
	}

	public static int getMailRepeatCount() {
		int repeatCount = conf.getInt("mail.repeatCount", 1);
		if (repeatCount > 3) {
			repeatCount = 3;
		}
		if (repeatCount < 0) {
			repeatCount = 0;
		}
		return repeatCount;
	}

	public static boolean isDefaultActorEnable() {
		boolean isDefaultActorEnable = conf.getBoolean(
				"jbpm.isDefaultActorEnable", false);
		return isDefaultActorEnable;
	}

	private JbpmObjectFactory() {

	}

}