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

package org.jpage.util;

public interface Constant {

	public final static int SORT_PREVIOUS = 0;

	public final static int SORT_FORWARD = 1;

	public static final String LOGIN_USER = "login_user";

	public static final String LOGIN_USER_ID = "login_user_id";

	public static final String LOGIN_USER_NAME = "login_user_name";

	public static final String LOGIN_USER_USERNAME = "login_user_username";

	public static final String LOGIN_USER_ROLES = "login_user_roles";

	public static final String SESSION_DISPLAYABLE_PARAM_KEY = "session_displayable_param_key";

	public static final String jpage_CONFIG_CACHE = "jpage_config_cache";

	public static final String SYSTEM_INIT_PASSWORD = "111111";

	public static final String APPLICATION_EXCEPTION_KEY = "application_exception_key";

	public static final String APPLICATION_EXCEPTION_CODE = "application_exception_code";

	public static final String APPLICATION_EXCEPTION_MESSAGE = "application_exception_message";

	public static final String DATASOURCE_JNDINAME = "java:comp/env/jdbc/datastore";

	public static final String USERTRANSACTION_JNDINAME = "java:comp/UserTransaction";

	public static final String MAILSESSION_JNDINAME = "java:comp/env/mail/Session";

	public static final String TEMP_FILE_RESOURCEID = "temp";

	public static final String SESSION_ATTACHMENT_KEY = "session_attachment_key";

	public static final String SESSION_ATTACHMENT_SIZE = "session_attachment_size";

	public static final String PRIMARY_KEY_NAME = "primary_key_name";

	public static final String PRIMARY_KEY_VALUE = "primary_key_value";

	public static final long ALLOWED_MAX_FILE_SIZE = 20480000;// 20M

	public static final String CURRENT_USER_USERID = "CURRENT_USER_USERID";

	public static final String CURRENT_USER_NAME = "CURRENT_USER_NAME";

	public static final String CURRENT_USER_USERNAME = "CURRENT_USER_USERNAME";

	public static final String CURRENT_USER_ORGANIZATION_ID = "CURRENT_USER_ORGANIZATION_ID";

	public static final String CURRENT_USER_ORGANIZATION_NAME = "CURRENT_USER_ORGANIZATION_NAME";

	public static final String CURRENT_DATE = "CURRENT_DATE";

	public static final String CURRENT_DATE_TIME = "CURRENT_DATE_TIME";

	public static final String SYSTEM_CODE_LONG_ID = "SYSTEM_CODE_LONG_ID";

	public static final String SYSTEM_CODE_DIGIT8ID = "SYSTEM_CODE_DIGIT8ID";

	public static final String SYSTEM_CODE_UUID32 = "SYSTEM_CODE_UUID32";

	public static final long DAY_TIME_MILLIS = 86400000L;

	public static final String BUNDLE_KEY = "ApplicationResources";

	/** The encryption algorithm key to be used for passwords */
	public static final String ENC_ALGORITHM = "algorithm";

	/** A flag to indicate if passwords should be encrypted */
	public static final String ENCRYPT_PASSWORD = "encryptPassword";

	/** File separator from System properties */
	public static final String FILE_SEP = System.getProperty("file.separator");

	/** User home from System properties */
	public static final String USER_HOME = System.getProperty("user.home")
			+ FILE_SEP;

	/** The name of the configuration hashmap stored in application scope. */
	public static final String CONFIG = "application_config";

	/**
	 * Session scope attribute that holds the locale set by the user. By setting
	 * this key to the same one that Struts uses, we get synchronization in
	 * Struts w/o having to do extra work or have two session-level variables.
	 */
	public static final String PREFERRED_LOCALE_KEY = "org.apache.struts.action.LOCALE";

	/**
	 * The request scope attribute under which an editable user form is stored
	 */
	public static final String USER_KEY = "userForm";

	/**
	 * The request scope attribute that holds the user list
	 */
	public static final String USER_LIST = "userList";

	/**
	 * The request scope attribute for indicating a newly-registered user
	 */
	public static final String REGISTERED = "registered";

	/**
	 * The name of the Administrator role, as specified in web.xml
	 */
	public static final String ADMIN_ROLE = "admin";

	/**
	 * The name of the User role, as specified in web.xml
	 */
	public static final String USER_ROLE = "user";

	/**
	 * The name of the user's role list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String USER_ROLES = "userRoles";

	/**
	 * The name of the available roles list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String AVAILABLE_ROLES = "availableRoles";

	/**
	 * The name of the CSS Theme setting.
	 */
	public static final String CSS_THEME = "csstheme";

}