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


package org.jpage.core.mail;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jpage.config.Configurator;
import org.jpage.core.mail.model.MailUser;

public class MailContext {
	private final static Map cache = Collections.synchronizedMap(new HashMap());

	public final static String SYSTEM_MAIL_USER = "system_mail_user";

	public final static String SYSTEM_MAIL_USERID = "999999999";

	private MailContext() {
	}

	public static MailUser getSystemMailUser() {
		if (cache.get(SYSTEM_MAIL_USER) != null) {
			return (MailUser) cache.get(SYSTEM_MAIL_USER);
		}
		Properties props = Configurator.getProperties();
		if (props != null) {
			MailUser mailUser = new MailUser();
			mailUser.setUserId(SYSTEM_MAIL_USERID);
			mailUser.setSmtpServer(props.getProperty("mail.host", "127.0.0.1"));
			mailUser.setUsername(props.getProperty("mail.username", "admin"));
			mailUser.setPassword(props.getProperty("mail.password", "admin"));
			mailUser.setMailAddress(props.getProperty("mail.from",
					"admin@127.0.0.1"));
			mailUser.setShowName(props.getProperty("mail.from.name", "Administrator"));
			String sendPort = props.getProperty("port", "25");
			int port = 25;
			try {
				port = Integer.parseInt(sendPort);
			} catch (NumberFormatException ex) {
			}
			mailUser.setSendPort(port);
			cache.put(SYSTEM_MAIL_USER, mailUser);
			return mailUser;
		}
		return null;
	}

}