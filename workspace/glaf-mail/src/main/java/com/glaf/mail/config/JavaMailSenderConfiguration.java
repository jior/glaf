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

package com.glaf.mail.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.Constants;
import com.glaf.core.util.PropertiesUtils;

@Configuration
public class JavaMailSenderConfiguration {

	@Bean(name = "javaMailSender")
	public JavaMailSenderImpl buildJavaMailSender() {
		MailConfig cfg = new MailConfig();
		String filename = SystemProperties.getConfigRootPath()
				+ Constants.MAIL_CONFIG;
		Properties properties = PropertiesUtils.loadFilePathResource(filename);
		cfg.setEncoding(properties.getProperty("mail.defaultEncoding", "GBK"));
		cfg.setHost(properties.getProperty("mail.host", "127.0.0.1"));
		cfg.setUsername(properties.getProperty("mail.username"));
		cfg.setPassword(properties.getProperty("mail.password"));
		if (StringUtils.equals(properties.getProperty("mail.auth"), "true")) {
			cfg.setAuth(true);
		}

		int port = JavaMailSenderImpl.DEFAULT_PORT;
		if (properties.getProperty("mail.port") != null) {
			port = Integer.parseInt(properties.getProperty("mail.port"));
		}

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setJavaMailProperties(properties);
		sender.setHost(properties.getProperty("mail.host"));
		sender.setPort(port);
		sender.setProtocol(JavaMailSenderImpl.DEFAULT_PROTOCOL);
		sender.setUsername(properties.getProperty("mail.username"));
		sender.setPassword(properties.getProperty("mail.password"));
		sender.setDefaultEncoding(properties.getProperty(
				"mail.defaultEncoding", "GBK"));

		return sender;
	}

}