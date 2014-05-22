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
package com.glaf.test.mail;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailSender;

public class SendMailTest {

	@Test
	public void sendMail() throws Exception {
		System.out.println("ConfigRootPath:"+SystemProperties.getConfigRootPath());
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		dataMap.put("taskDescription", "邮件系统开发");
		dataMap.put("processStarterName", "系统管理员");
		dataMap.put("serviceUrl", "http://127.0.0.1:8080/glaf");
		dataMap.put("callback", "http://127.0.0.1:8080/glaf/task.jsp");

		MailMessage mailMessage = new MailMessage();
		mailMessage.setFrom("joy@127.0.0.1");
		mailMessage.setTo("joy@127.0.0.1");
		mailMessage.setSubject("测试邮件模板");
		mailMessage.setDataMap(dataMap);
		//mailMessage.setContent("测试测试");
		mailMessage.setTemplateId("sendToSupplier");
		mailMessage.setSupportExpression(false);

		Collection<Object> files = new HashSet<Object>();

		mailMessage.setFiles(files);
		mailMessage.setSaveMessage(false);
		MailSender mailSender = ContextFactory.getBean("mailSender");
		mailSender.send(mailMessage);
	}

}
