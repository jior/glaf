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


package org.jpage.core.mail.service.sendmail;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.jpage.core.mail.MailContext;
import org.jpage.core.mail.model.MailUser;
import org.jpage.core.mail.service.JavaMailContainer;

public class SendMail {

	public static final String sp = System.getProperty("file.separator");

	private static SendMail sender = null;

	private SendMail() {
	}

	public static SendMail getSender() {
		if (sender == null) {
			sender = new SendMail();
		}
		return sender;
	}

	public void sendMail(Message message) {
		JavaMailContainer container = JavaMailContainer.getContainer();
		Session session = container.getSession();
		if (session == null) {
			throw new RuntimeException("没有设置系统邮件帐号，不能发送邮件！");
		}
		MailUser mailUser = MailContext.getSystemMailUser();
		if (mailUser != null) {
			send(session, mailUser, message);
		}
	}

	/**
	 * 发邮件
	 * 
	 * @param transport
	 * @param message
	 * @throws Exception
	 */
	public void send(Session session, MailUser mailUser, Message message) {
		Transport transport = null;
		try {
			transport = session.getTransport("smtp");
			transport.connect(mailUser.getSmtpServer(), mailUser.getSendPort(),
					mailUser.getUsername(), mailUser.getPassword());
			Transport.send(message);
		} catch (AuthenticationFailedException ex) {
			throw new RuntimeException(ex);
		} catch (MessagingException ex) {
			throw new RuntimeException("Mail server connection failed", ex);
		} finally {
			try {
				if (transport != null) {
					transport.close();
				}
			} catch (MessagingException ex) {
			}
		}
	}

}