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

import java.util.Properties;

import org.jbpm.JbpmConfiguration;

public class SystemProperties {

	private final static Properties properties = new Properties();

	private static boolean isLoad = false;

	public final static Properties getProperties() {
		if (!isLoad) {

			if (JbpmConfiguration.Configs.hasObject("serviceUrl")) {
				String serviceUrl = JbpmConfiguration.Configs
						.getString("serviceUrl");
				properties.setProperty("serviceUrl", serviceUrl);
			}

			if (JbpmConfiguration.Configs.hasObject("approveUrl")) {
				String approveUrl = JbpmConfiguration.Configs
						.getString("approveUrl");
				properties.setProperty("approveUrl", approveUrl);
			}

			if (JbpmConfiguration.Configs.hasObject("viewUrl")) {
				String viewUrl = JbpmConfiguration.Configs.getString("viewUrl");
				properties.setProperty("viewUrl", viewUrl);
			}

			if (JbpmConfiguration.Configs.hasObject("jbpm.mail.smtp.host")) {
				String host = JbpmConfiguration.Configs
						.getString("jbpm.mail.smtp.host");
				properties.setProperty("jbpm.mail.smtp.host", host);
			}

			if (JbpmConfiguration.Configs.hasObject("jbpm.mail.encoding")) {
				String encoding = JbpmConfiguration.Configs
						.getString("jbpm.mail.encoding");
				properties.setProperty("jbpm.mail.encoding", encoding);
			}

			if (JbpmConfiguration.Configs.hasObject("jbpm.mail.mailForm")) {
				String mailForm = JbpmConfiguration.Configs
						.getString("jbpm.mail.mailForm");
				properties.setProperty("jbpm.mail.mailForm", mailForm);
			}

			if (JbpmConfiguration.Configs.hasObject("jbpm.mail.username")) {
				String username = JbpmConfiguration.Configs
						.getString("jbpm.mail.username");
				properties.setProperty("jbpm.mail.username", username);
			}

			if (JbpmConfiguration.Configs.hasObject("jbpm.mail.password")) {
				String password = JbpmConfiguration.Configs
						.getString("jbpm.mail.password");
				properties.setProperty("jbpm.mail.password", password);
			}

			if (JbpmConfiguration.Configs.hasObject("socksProxyHost")) {
				String socksProxyHost = JbpmConfiguration.Configs
						.getString("socksProxyHost");
				properties.setProperty("socksProxyHost", socksProxyHost);
			}

			if (JbpmConfiguration.Configs.hasObject("socksProxyPort")) {
				int socksProxyPort = JbpmConfiguration.Configs
						.getInt("socksProxyPort");
				properties.setProperty("socksProxyPort", String
						.valueOf(socksProxyPort));
			}

			isLoad = true;

		}

		return properties;
	}

}
