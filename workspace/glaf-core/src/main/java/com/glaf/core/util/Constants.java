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

package com.glaf.core.util;

public final class Constants {

	public static final String SYSTEM_NAME = "GLAF";

	public static final String SYSTEM_CONFIG = "/glaf.properties";

	public static final String LOGIN_IP = "LOGIN_IP";

	public static final String LOGIN_ACTORID = "LOGIN_ACTORID";

	public static final String SYSTEM_PERMISSION_IDS = "SYSTEM_PERMISSION_IDS";

	public static final String TS = "TS";

	public final static String NUMBER_OF_INSTANCES = "nrOfInstances";

	public final static String NUMBER_OF_ACTIVE_INSTANCES = "nrOfActiveInstances";

	public final static String NUMBER_OF_COMPLETED_INSTANCES = "nrOfCompletedInstances";

	public final static String LOOP_COUNTER = "loopCounter";

	public final static String BUSINESS_KEY = "businessKey";

	public final static String PROCESS_DEFINITION_XML = ".bpmn20.xml";

	public final static String PROCESS_STARTER = "process_starter";

	public final static String PROCESS_START_DATE = "start_date";

	public final static String PROCESS_NAME = "process_name";

	public final static String OUTCOME = "outcome";

	public final static String TM_RUNTIME_ACTORIDS = "dynamicActors";

	public final static int TM_DEPT_ROLE_USER_TYPE = 0;

	public final static int TM_USER_TYPE = 5;

	public final static int TM_RUNTIME_TYPE = 10;

	public final static int TM_PROCESS_STARTER_TYPE = 20;

	public final static int TM_PROCESS_STARTER_LEADER_TYPE = 22;

	public final static int TM_PROCESS_STARTER_LEADERS_TYPE = 25;

	public static final String MAIL_CONFIG = "/conf/mail.properties";

	public static final String JDBC_CONFIG = "/conf/jdbc";

	public static final String DEFAULT_JDBC_CONFIG = "/conf/jdbc.properties";

	public static final String SPRING_APPLICATION_CONTEXT = "/conf/spring/spring-config.xml";

	public static final String ID_EXPRESSION = "#{id}";

	public static final String NOW_EXPRESSION = "#{now}";

	public static final String CURRENT_YYYYMMDD_EXPRESSION = "#{curr_yyyymmdd}";

	public static final String CURRENT_YYYYMM_EXPRESSION = "#{curr_yyyymm}";

	public static final String INPUT_YYYYMMDD_EXPRESSION = "#{input_yyyymmdd}";

	public static final String INPUT_YYYYMM_EXPRESSION = "#{input_yyyymm}";
}