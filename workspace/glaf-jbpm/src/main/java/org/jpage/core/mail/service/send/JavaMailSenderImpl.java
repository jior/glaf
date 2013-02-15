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


package org.jpage.core.mail.service.send;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.jpage.core.mail.model.MailUser;
import org.jpage.core.mail.service.CustomMimeMessage;
import org.jpage.core.mail.service.JavaMailContainer;
import org.jpage.core.mail.service.JavaMailSender;
import org.jpage.core.mail.service.MailMessage;
import org.jpage.core.mail.service.MimeMessageHelper;

public class JavaMailSenderImpl implements JavaMailSender {

	public final static String DEFAULT_PROTOCOL = "smtp";

	public void send(MailUser mailUser, MailMessage mailMessage)
			throws Exception {
		send(mailUser, new MailMessage[] { mailMessage });
	}

	public void send(MailUser mailUser, MailMessage[] mailMessages)
			throws Exception {
		try {
			JavaMailContainer container = JavaMailContainer.getContainer();
			Session session = container.getSession(mailUser);
			List mimeMessages = new ArrayList();
			for (int i = 0; i < mailMessages.length; i++) {
				MailMessage mailMessage = mailMessages[i];
				MimeMessageHelper message = new MimeMessageHelper();

				CustomMimeMessage mimeMessage = new CustomMimeMessage(session);
				mimeMessage.updateMessageID();
				message.setMimeMessage(mimeMessage);

				if (mailMessage.getFrom() != null) {
					message.setFrom(mailMessage.getFrom());
				}
				if (mailMessage.getTo() != null) {
					message.setTo(mailMessage.getTo());
				}
				if (mailMessage.getCc() != null) {
					message.setCc(mailMessage.getCc());
				}
				if (mailMessage.getBcc() != null) {
					message.setBcc(mailMessage.getBcc());
				}
				if (mailMessage.getSubject() != null) {
					message.setSubject(mailMessage.getSubject());
				}
				if (mailMessage.getText() != null) {
					message.setText(mailMessage.getText(), true);
				}
				if (mailMessage.getReplyTo() != null) {
					message.setReplyTo(mailMessage.getReplyTo());
				}

				mimeMessages.add(message.getMimeMessage());

			}
			send(mailUser, (MimeMessage[]) mimeMessages
					.toArray(new MimeMessage[mimeMessages.size()]));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void send(MailUser mailUser, MimeMessage mimeMessage)
			throws Exception {
		send(mailUser, new MimeMessage[] { mimeMessage });
	}

	public void send(MailUser mailUser, MimeMessage[] mimeMessages)
			throws Exception {
		Transport transport = null;
		try {
			JavaMailContainer container = JavaMailContainer.getContainer();
			Session session = container.getSession(mailUser);
			/*
			 * Properties props = new Properties(); props.put("mail.smtp.host",
			 * mailUser.getSmtpServer()); props.put("mail.smtp.port",
			 * String.valueOf(mailUser.getSendPort()));
			 * props.put("mail.smtp.auth", "true");
			 * 
			 * MailAuthenticator authenticator = new MailAuthenticator(mailUser
			 * .getUsername(), mailUser.getPassword());
			 * 
			 * Session session = Session.getInstance(props, null);
			 * session.setDebug(true);
			 * 
			 */

			transport = session.getTransport(DEFAULT_PROTOCOL);

			transport.connect(mailUser.getSmtpServer(), mailUser.getSendPort(),
					mailUser.getUsername(), mailUser.getPassword());

			for (int i = 0; i < mimeMessages.length; i++) {
				MimeMessage mimeMessage = mimeMessages[i];
				if (mimeMessage.getSentDate() == null) {
					mimeMessage.setSentDate(new java.util.Date());
				}
				mimeMessage.saveChanges();
				transport.sendMessage(mimeMessage, mimeMessage
						.getAllRecipients());
			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (transport != null) {
					transport.close();
				}
			} catch (MessagingException ex) {
			}
		}
	}

	public static void main(String[] args) throws Exception {
		JavaMailSender sender = new JavaMailSenderImpl();
		MailUser mailUser = new MailUser();
		mailUser.setSmtpServer("172.16.1.143");
		mailUser.setSendPort(25);
		mailUser.setUsername("pps");
		mailUser.setPassword("GTMCmail9453");
		MailMessage mailMessage = new MailMessage();
		mailMessage.setFrom("pps@gtmc.com.cn");
		mailMessage.setTo("agoo.chen@gzgi.com");
		mailMessage.setReplyTo("agoo20@163.com");
		mailMessage.setSubject("中文测试");
		mailMessage.setText("这是测试内容!");
		sender.send(mailUser, mailMessage);
	}

}