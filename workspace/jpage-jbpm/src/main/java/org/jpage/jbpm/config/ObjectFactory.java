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


package org.jpage.jbpm.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jbpm.JbpmConfiguration;

public class ObjectFactory {
	private final static Map cache = Collections.synchronizedMap(new HashMap());
	private static Boolean databaseFlag;

	private ObjectFactory() {

	}

	public static boolean isBooleanDatabase() {
		if (databaseFlag == null) {
			databaseFlag = JbpmDataSourceConfig.isBooleanDatabase();
		}
		return databaseFlag;
	}

	public static String getDataFieldKeys() {
		String jbpm_datafield_keys = (String) cache.get("jbpm_datafield_keys");
		if (jbpm_datafield_keys == null) {
			if (JbpmConfiguration.Configs.hasObject("jbpm_datafield_keys")) {
				jbpm_datafield_keys = JbpmConfiguration.Configs
						.getString("jbpm_datafield_keys");
			}
			if (jbpm_datafield_keys == null) {
				jbpm_datafield_keys = "isAgree,isPass,money,day";
			}
			cache.put("jbpm_datafield_keys", jbpm_datafield_keys);
		}
		return jbpm_datafield_keys;
	}

	public static int getMailRepeatCount() {
		int repeatCount = 3;
		if (JbpmConfiguration.Configs.hasObject("jbpm.mail.repeatCount")) {
			repeatCount = JbpmConfiguration.Configs
					.getInt("jbpm.mail.repeatCount");
		}
		if (repeatCount > 10) {
			repeatCount = 10;
		}
		if (repeatCount < 1) {
			repeatCount = 1;
		}
		return repeatCount;
	}

	public static boolean isDefaultActorEnable() {
		if (JbpmConfiguration.Configs.hasObject("isDefaultActorEnable")) {
			return JbpmConfiguration.Configs.getBoolean("isDefaultActorEnable");
		}
		return false;
	}

	public static String getDefaultActors() {
		if (JbpmConfiguration.Configs.hasObject("defaultActors")) {
			return JbpmConfiguration.Configs.getString("defaultActors");
		}
		return "root";
	}

	public static boolean isAgentEnable() {
		if (JbpmConfiguration.Configs.hasObject("isAgentEnable")) {
			return JbpmConfiguration.Configs.getBoolean("isAgentEnable");
		}
		return false;
	}

	public static boolean isAutoCompleteTask() {
		if (JbpmConfiguration.Configs.hasObject("isAutoCompleteTask")) {
			return JbpmConfiguration.Configs.getBoolean("isAutoCompleteTask");
		}
		return false;
	}

	public static boolean canAbortProcess() {
		if (JbpmConfiguration.Configs.hasObject("canAbortProcess")) {
			return JbpmConfiguration.Configs.getBoolean("canAbortProcess");
		}
		return false;
	}

	public static boolean canDeleteProcessInstance() {
		if (JbpmConfiguration.Configs.hasObject("canDeleteProcessInstance")) {
			return JbpmConfiguration.Configs
					.getBoolean("canDeleteProcessInstance");
		}
		return false;
	}

	public static boolean canSuspendProcess() {
		if (JbpmConfiguration.Configs.hasObject("canSuspendProcess")) {
			return JbpmConfiguration.Configs.getBoolean("canSuspendProcess");
		}
		return false;
	}

	public static String getWarnMail() {
		if (JbpmConfiguration.Configs.hasObject("warnMail")) {
			return JbpmConfiguration.Configs.getString("warnMail");
		}
		return "huangjue_2001@163.com";
	}

	public static String getLoginUrl() {
		if (JbpmConfiguration.Configs.hasObject("loginUrl")) {
			return JbpmConfiguration.Configs.getString("loginUrl");
		}
		return "/";
	}

	public static String getServiceUrl() {
		if (JbpmConfiguration.Configs.hasObject("serviceUrl")) {
			return JbpmConfiguration.Configs.getString("serviceUrl");
		}
		return "http://localhost:8080/jbpm";
	}

	public static String getProcessStatusUrl() {
		if (JbpmConfiguration.Configs.hasObject("processStatusUrl")) {
			return JbpmConfiguration.Configs.getString("processStatusUrl");
		}
		return "/workflow/processMonitorController.jspa?method=stateInstances";
	}

	public static int getInt(String key, int defaultValue) {
		if (JbpmConfiguration.Configs.hasObject(key)) {
			return JbpmConfiguration.Configs.getInt(key);
		}
		return defaultValue;
	}

	public static long getLong(String key, long defaultValue) {
		if (JbpmConfiguration.Configs.hasObject(key)) {
			return JbpmConfiguration.Configs.getLong(key);
		}
		return defaultValue;
	}

	public static String getString(String key, String defaultValue) {
		if (JbpmConfiguration.Configs.hasObject(key)) {
			return JbpmConfiguration.Configs.getString(key);
		}
		return defaultValue;
	}

}
