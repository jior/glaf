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


package org.jpage.jbpm.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.mail.service.MailMessage;
import org.jpage.jbpm.context.JbpmContextFactory;

public class MailThread extends Thread {
	private static final Log logger = LogFactory.getLog(MailThread.class);

	private MailSender mailSender;

	private MailMessage mailMessage = null;

	private boolean sendOK = false;

	public MailThread(MailMessage mailMessage) {
		this.mailMessage = mailMessage;
		this.setDaemon(true);
	}

	public boolean isSendOK() {
		return sendOK;
	}

	public void run() {
		try {
			if (mailMessage != null) {
				mailSender = (MailSender) JbpmContextFactory
						.getBean("mailSender");
				mailSender.send(mailMessage);
				sendOK = true;
			}
		} catch (Exception ex) {
			sendOK = false;
			logger.error(ex);
		}
	}

}
