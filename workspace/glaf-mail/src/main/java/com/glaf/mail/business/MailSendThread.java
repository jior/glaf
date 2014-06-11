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

package com.glaf.mail.business;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailSender;
import com.glaf.mail.MxMailHelper;
import com.glaf.mail.domain.MailAccount;
import com.glaf.mail.domain.MailItem;
import com.glaf.mail.domain.MailTask;
import com.glaf.mail.util.MailTools;

public class MailSendThread implements Runnable {

	protected static final Log logger = LogFactory.getLog(MailSendThread.class);

	protected MailDataFacede mailDataFacede;

	protected MailTask task;

	protected MailItem mailItem;

	public MailSendThread(MailDataFacede mailDataFacede, MailTask task,
			MailItem mailItem) {
		this.mailDataFacede = mailDataFacede;
		this.mailItem = mailItem;
		this.task = task;
	}

	public void run() {
		logger.debug("---------------send mail----------------------------");
		if (mailItem != null && MailTools.isMailAddress(mailItem.getMailTo())) {
			/**
			 * 重试次数超过5次，不再发送
			 */
			if (mailItem.getRetryTimes() > 5) {
				return;
			}
			/**
			 * 已经成功发送的就不再发送
			 */
			if (mailItem.getSendStatus() == 1) {
				return;
			}
			/**
			 * 超过一个月的也不再发送
			 */
			if (mailItem.getLastModified() < (System.currentTimeMillis() - DateUtils.DAY * 30)) {
				return;
			}

			MailAccount mailAccount = null;
			JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
			List<MailAccount> accounts = task.getAccounts();
			if (accounts.size() > 1) {
				java.util.Random r = new java.util.Random();
				mailAccount = accounts
						.get(Math.abs(r.nextInt(accounts.size())));
			} else {
				mailAccount = accounts.get(0);
			}

			logger.debug("send account:" + mailAccount);

			String content = task.getContent();

			if (StringUtils.isEmpty(task.getCallbackUrl())) {
				String serviceUrl = SystemConfig.getServiceUrl();
				String callbackUrl = serviceUrl + "/website/mail/receive/view";
				task.setCallbackUrl(callbackUrl);
			}

			if (StringUtils.isNotEmpty(task.getCallbackUrl())) {
				MxMailHelper mailHelper = new MxMailHelper();
				String messageId = "{taskId:\"" + task.getId() + "\",itemId:\""
						+ mailItem.getId() + "\"}";
				messageId = RequestUtils.encodeString(messageId);
				String url = task.getCallbackUrl() + "?messageId=" + messageId;
				content = mailHelper.embedCallbackScript(content, url);
			}

			MailMessage mailMessage = new MailMessage();
			mailMessage.setFrom(mailAccount.getMailAddress());
			mailMessage.setTo(mailItem.getMailTo());
			mailMessage.setContent(content);
			mailMessage.setSubject(task.getSubject());
			mailMessage.setSaveMessage(false);

			javaMailSender.setHost(mailAccount.getSmtpServer());
			javaMailSender.setPort(mailAccount.getSendPort() > 0 ? mailAccount
					.getSendPort() : 25);
			javaMailSender.setProtocol(JavaMailSenderImpl.DEFAULT_PROTOCOL);
			javaMailSender.setUsername(mailAccount.getUsername());
			if (StringUtils.isNotEmpty(mailAccount.getPassword())) {
				String pwd = RequestUtils.decodeString(mailAccount
						.getPassword());
				javaMailSender.setPassword(pwd);
				// logger.debug("send account pwd:"+pwd);
			}
			javaMailSender.setDefaultEncoding("GBK");

			try {
				mailItem.setSendDate(new Date());
				mailItem.setRetryTimes(mailItem.getRetryTimes() + 1);
				MailSender mailSender = ContextFactory.getBean("mailSender");
				mailSender.send(javaMailSender, mailMessage);
				mailItem.setSendStatus(1);
			} catch (Throwable ex) {
				mailItem.setSendStatus(-1);
				if (LogUtils.isDebug()) {
					logger.debug(ex);
					ex.printStackTrace();
				}
				if (ex instanceof javax.mail.internet.ParseException) {
					mailItem.setSendStatus(-10);
				} else if (ex instanceof javax.mail.AuthenticationFailedException) {
					mailItem.setSendStatus(-20);
				} else if (ex instanceof javax.mail.internet.AddressException) {
					mailItem.setSendStatus(-30);
				} else if (ex instanceof javax.mail.SendFailedException) {
					mailItem.setSendStatus(-40);
				} else if (ex instanceof java.net.UnknownHostException) {
					mailItem.setSendStatus(-50);
				} else if (ex instanceof java.net.SocketException) {
					mailItem.setSendStatus(-60);
				} else if (ex instanceof java.io.IOException) {
					mailItem.setSendStatus(-70);
				} else if (ex instanceof java.net.ConnectException) {
					mailItem.setSendStatus(-80);
				} else if (ex instanceof javax.mail.MessagingException) {
					mailItem.setSendStatus(-90);
					if (ex.getMessage().indexOf("response: 554") != -1) {
						mailItem.setSendStatus(-99);
					}
				}
			} finally {
				mailDataFacede.updateMail(task.getId(), mailItem);
			}
		}
	}
}