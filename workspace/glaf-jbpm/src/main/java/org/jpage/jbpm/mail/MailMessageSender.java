/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.mail;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.mail.MailHelper;
import org.jpage.core.mail.service.JavaMailDataSource;
import org.jpage.core.mail.service.MailMessage;
import org.jpage.core.mail.util.MailTools;
import org.jpage.datacenter.file.DataFile;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.config.SystemProperties;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.model.MessageInstance;
import org.jpage.jbpm.service.PersistenceContainer;
import org.jpage.util.UUID32;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailMessageSender implements MessageSender {
	private static final Log logger = LogFactory
			.getLog(MailMessageSender.class);

	protected PersistenceContainer persistenceContainer = PersistenceContainer
			.getContainer();
	
	protected JavaMailSenderImpl javaMailSender;

	protected MailHelper mailHelper;

	protected String callbackUrl;

	protected String mailFrom;

	public MailMessageSender() {
		mailHelper = new MailHelper();
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public JavaMailSenderImpl getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void send(MailMessage mailMessage) {

		StringBuffer buffer = new StringBuffer();

		if (StringUtils.isBlank(mailMessage.getMessageId())) {
			mailMessage.setMessageId(UUID32.getUUID());
		}
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mimeMessage, true);

			if (StringUtils.isNotBlank(mailMessage.getFrom())) {
				messageHelper.setFrom(mailMessage.getFrom());
			} else {
				messageHelper.setFrom(mailFrom);
			}

			if (mailMessage.getTo() != null) {
				messageHelper.setTo(mailMessage.getTo());
				String[] array = mailMessage.getTo();
				for (int i = 0; i < array.length; i++) {
					String elem = array[i];
					buffer.append(elem).append(",");
				}
			}

			if (mailMessage.getCc() != null) {
				messageHelper.setCc(mailMessage.getCc());
				String[] array = mailMessage.getCc();
				for (int i = 0; i < array.length; i++) {
					String elem = array[i];
					buffer.append(elem).append(",");
				}
			}

			if (mailMessage.getBcc() != null) {
				messageHelper.setBcc(mailMessage.getBcc());
				String[] array = mailMessage.getBcc();
				for (int i = 0; i < array.length; i++) {
					String elem = array[i];
					buffer.append(elem).append(",");
				}
			}
			
			if (mailMessage.getReplyTo() != null) {
				messageHelper.setReplyTo(mailMessage.getReplyTo()[0]);
			}

			String encoding = mailMessage.getEncoding();

			if (StringUtils.isBlank(encoding)) {
				encoding = "GBK";
			}

			String mailSubject = mailMessage.getSubject();
			try {
				if (mailSubject != null) {
					mailSubject = MimeUtility.encodeText(new String(mailSubject
							.getBytes(), encoding), encoding, "B");
				}
			} catch (UnsupportedEncodingException ex) {
			}

			if (mailSubject == null) {
				mailSubject = "";
			}

			mimeMessage.setSubject(mailSubject);

			if (StringUtils.isNotBlank(mailMessage.getText())) {
				String text = mailMessage.getText();
				if (StringUtils.isBlank(callbackUrl)) {
					callbackUrl = ObjectFactory.getServiceUrl()
							+ "/workflow/mail/callback";
				}
				String href = callbackUrl + "?messageId="
						+ mailMessage.getMessageId();
				text = mailHelper.embedCallbackScript(text, href);

				Map dataMap = mailMessage.getDataMap();
				if (dataMap == null) {
					dataMap = new HashMap();
					String serviceUrl = SystemProperties.getProperties()
							.getProperty("serviceUrl");
					dataMap.put("serviceUrl", serviceUrl);
				}

				text = (String) DefaultExpressionEvaluator.evaluate(text,
						dataMap);

				if (logger.isDebugEnabled()) {
					logger.debug(text);
				}

				messageHelper.setText(text, true);

			}

			Collection files = mailMessage.getFiles();

			if (files != null && files.size() > 0) {
				Iterator iterator = files.iterator();
				while (iterator.hasNext()) {
					Object obj = iterator.next();
					if (obj instanceof java.io.File) {
						java.io.File file = (java.io.File) obj;
						FileSystemResource resource = new FileSystemResource(
								file);
						String name = file.getName();
						name = MailTools.chineseStringToAscii(name);
						messageHelper.addAttachment(name, resource);
					} else if (obj instanceof DataSource) {
						DataSource dataSource = (DataSource) obj;
						String name = dataSource.getName();
						name = MailTools.chineseStringToAscii(name);
						messageHelper.addAttachment(name, dataSource);
					} else if (obj instanceof DataFile) {
						DataFile dataFile = (DataFile) obj;
						if (dataFile.getFile() != null) {
							java.io.File file = dataFile.getFile();
							FileSystemResource resource = new FileSystemResource(
									file);
							if (StringUtils.isNotBlank(dataFile.getName())) {
								String name = dataFile.getName();
								name = MailTools.chineseStringToAscii(name);
								messageHelper.addAttachment(name, resource);
							} else {
								messageHelper.addAttachment(file.getName(),
										resource);
							}
						} else {
							JavaMailDataSource dataSource = new JavaMailDataSource(
									dataFile);
							String name = dataSource.getName();
							name = MailTools.chineseStringToAscii(name);
							messageHelper.addAttachment(name, dataSource);
						}
					}
				}
			}
			

			Properties javaMailProperties =  System.getProperties();
			javaMailProperties.setProperty("mail.smtp.from",mailMessage.getReplyTo()[0]);
			javaMailProperties.setProperty("mail.smtp.auth","true");
			javaMailSender.setJavaMailProperties(javaMailProperties);
			mimeMessage.setSentDate(new java.util.Date());
			javaMailSender.send(mimeMessage);
			

			System.out.println("-----------------------------------------");
			System.out.println("邮件已经成功发送。");
			System.out.println("-----------------------------------------");

			String messageId = mailMessage.getMessageId();
			if (mailMessage.isSaveMessage()) {
				if (StringUtils.isBlank(messageId)) {
					messageId = UUID32.getUUID();
				}
				if (persistenceContainer.getPersistObject(
						MessageInstance.class, messageId) == null) {
					if (buffer.toString().endsWith(",")) {
						buffer.delete(buffer.length() - 1, buffer.length());
					}
					MessageInstance messageInstance = new MessageInstance();
					messageInstance.setMessageId(mailMessage.getMessageId());
					messageInstance.setCreateDate(new Date());
					messageInstance.setMessageType("mail");
					messageInstance.setMessageTo(buffer.toString());
					messageInstance.setObjectId("messageId");
					messageInstance.setObjectValue(mailMessage.getMessageId());
					messageInstance.setReceiverId(buffer.toString());
					messageInstance.setSenderId(mailMessage.getFrom());
					messageInstance.setTitle(mailMessage.getSubject());
					messageInstance.setVersionNo(System.currentTimeMillis());
					persistenceContainer.save(messageInstance);
					System.out.println("已经保存邮件的消息实例。");
				}
			}

		} catch (Exception ex) {
			logger.error(ex);
			throw new RuntimeException(ex);
		}
	}

}
