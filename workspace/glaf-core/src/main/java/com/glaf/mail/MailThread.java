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

package com.glaf.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;

public class MailThread implements Runnable {
	private static final Log logger = LogFactory.getLog(MailThread.class);

	private MailSender mailSender;

	private MailMessage mailMessage;

	private boolean sendOK = false;

	public MailThread(MailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public boolean isSendOK() {
		return sendOK;
	}

	public void run() {
		try {
			if (mailMessage != null) {
				mailSender = (MailSender) ContextFactory.getBean("mailSender");
				if (mailSender != null) {
					mailSender.send(mailMessage);
					sendOK = true;
				}
			}
		} catch (Exception ex) {
			sendOK = false;
			logger.debug(ex);
		}
	}

}