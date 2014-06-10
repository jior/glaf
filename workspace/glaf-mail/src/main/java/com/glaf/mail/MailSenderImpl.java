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

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.glaf.core.base.DataFile;
import com.glaf.core.config.MailProperties;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.UUID32;
import com.glaf.template.Template;
import com.glaf.template.TemplateContainer;
import com.glaf.template.util.TemplateUtils;
import com.glaf.mail.config.JavaMailSenderConfiguration;
import com.glaf.mail.util.MailTools;

@Component("mailSender")
public class MailSenderImpl implements MailSender {
	private final static Log logger = LogFactory.getLog(MailSenderImpl.class);

	protected JavaMailSender javaMailSender;
	protected MxMailHelper mailHelper;
	protected String callbackUrl;
	protected String mailFrom;

	public static void main(String[] args) throws Exception {
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		dataMap.put("taskDescription", "邮件系统开发");
		dataMap.put("processStarterName", "系统管理员");
		dataMap.put("serviceUrl", "http://127.0.0.1:8080/glaf");
		dataMap.put("callback", "http://127.0.0.1:8080/glaf/task.jsp");

		MailMessage mailMessage = new MailMessage();
		mailMessage.setFrom("joy@127.0.0.1");
		mailMessage.setTo("joy@127.0.0.1");
		mailMessage.setSubject("邮件测试");
		mailMessage.setDataMap(dataMap);
		mailMessage.setContent("测试测试");
		// mailMessage.setTemplateId(args[0]);
		mailMessage.setSupportExpression(false);

		Collection<Object> files = new HashSet<Object>();

		mailMessage.setFiles(files);
		mailMessage.setSaveMessage(false);
		MailSender mailSender = ContextFactory.getBean("mailSender");
		mailSender.send(mailMessage);
	}

	public MailSenderImpl() {

	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void send(JavaMailSender javaMailSender, MailMessage mailMessage)
			throws Exception {
		if (StringUtils.isEmpty(mailMessage.getMessageId())) {
			mailMessage.setMessageId(UUID32.getUUID());
		}

		mailHelper = new MxMailHelper();
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
				true);

		if (StringUtils.isNotEmpty(mailMessage.getFrom())) {
			messageHelper.setFrom(mailMessage.getFrom());
			mailFrom = mailMessage.getFrom();
		} else {
			if (StringUtils.isEmpty(mailFrom)) {
				mailFrom = MailProperties.getString("mail.mailFrom");
			}
			messageHelper.setFrom(mailFrom);
		}

		logger.debug("mailFrom:" + mailFrom);

		if (mailMessage.getTo() != null) {
			messageHelper.setTo(mailMessage.getTo());
		}

		if (mailMessage.getCc() != null) {
			messageHelper.setCc(mailMessage.getCc());
		}

		if (mailMessage.getBcc() != null) {
			messageHelper.setBcc(mailMessage.getBcc());
		}

		if (mailMessage.getReplyTo() != null) {
			messageHelper.setReplyTo(mailMessage.getReplyTo());
		}

		String mailSubject = mailMessage.getSubject();
		if (mailSubject == null) {
			mailSubject = "无主题";
		}

		if (mailSubject != null) {
			// mailSubject = MimeUtility.encodeText(new
			// String(mailSubject.getBytes(), encoding), encoding, "B");
			mailSubject = MimeUtility.encodeWord(mailSubject);
		}

		mimeMessage.setSubject(mailSubject);

		Map<String, Object> dataMap = mailMessage.getDataMap();
		if (dataMap == null) {
			dataMap = new java.util.HashMap<String, Object>();
		}

		String serviceUrl = SystemConfig.getServiceUrl();

		logger.debug("mailSubject:" + mailSubject);
		logger.debug("serviceUrl:" + serviceUrl);

		if (serviceUrl != null) {
			String loginUrl = serviceUrl + "/mx/login";
			String mainUrl = serviceUrl + "/mx/main";
			logger.debug("loginUrl:" + loginUrl);
			dataMap.put("loginUrl", loginUrl);
			dataMap.put("mainUrl", mainUrl);
		}

		mailMessage.setDataMap(dataMap);

		if (StringUtils.isEmpty(mailMessage.getContent())) {
			Template template = TemplateContainer.getContainer().getTemplate(
					mailMessage.getTemplateId());
			if (template != null) {
				String templateType = template.getTemplateType();
				logger.debug("templateType:" + templateType);
				// logger.debug("content:" + template.getContent());
				if (StringUtils.equals(templateType, "eml")) {
					if (template.getContent() != null) {
						Mail m = mailHelper.getMail(template.getContent()
								.getBytes());
						String content = m.getContent();
						if (StringUtils.isNotEmpty(content)) {
							template.setContent(content);
							try {
								Writer writer = new StringWriter();
								TemplateUtils.evaluate(
										mailMessage.getTemplateId(), dataMap,
										writer);
								String text = writer.toString();
								writer.close();
								writer = null;
								mailMessage.setContent(text);
							} catch (Exception ex) {
								ex.printStackTrace();
								throw new RuntimeException(ex);
							}
						}
					}
				} else {
					try {
						String text = TemplateUtils.process(dataMap,
								template.getContent());
						mailMessage.setContent(text);
					} catch (Exception ex) {
						ex.printStackTrace();
						throw new RuntimeException(ex);
					}
				}
			}
		}

		if (StringUtils.isNotEmpty(mailMessage.getContent())) {
			String text = mailMessage.getContent();
			if (StringUtils.isNotEmpty(callbackUrl)) {
				String href = callbackUrl + "?messageId="
						+ mailMessage.getMessageId();
				text = mailHelper.embedCallbackScript(text, href);
				mailMessage.setContent(text);
				logger.debug(text);
				messageHelper.setText(text, true);
			}
			messageHelper.setText(text, true);
		}

		logger.debug("mail body:" + mailMessage.getContent());

		Collection<Object> files = mailMessage.getFiles();

		if (files != null && !files.isEmpty()) {
			Iterator<Object> iterator = files.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof java.io.File) {
					java.io.File file = (java.io.File) object;
					FileSystemResource resource = new FileSystemResource(file);
					String name = file.getName();
					name = MailTools.chineseStringToAscii(name);
					messageHelper.addAttachment(name, resource);
					logger.debug("add attachment:" + name);
				} else if (object instanceof DataSource) {
					DataSource dataSource = (DataSource) object;
					String name = dataSource.getName();
					name = MailTools.chineseStringToAscii(name);
					messageHelper.addAttachment(name, dataSource);
					logger.debug("add attachment:" + name);
				} else if (object instanceof DataFile) {
					DataFile dataFile = (DataFile) object;
					if (StringUtils.isNotEmpty(dataFile.getFilename())) {
						String name = dataFile.getFilename();
						name = MailTools.chineseStringToAscii(name);
						InputStreamSource inputStreamSource = new MxMailInputSource(
								dataFile);
						messageHelper.addAttachment(name, inputStreamSource);
						logger.debug("add attachment:" + name);
					}
				}
			}
		}

		mimeMessage.setSentDate(new java.util.Date());

		javaMailSender.send(mimeMessage);

		logger.info("-----------------------------------------");
		logger.info("邮件已经成功发送。");
		logger.info("-----------------------------------------");
	}

	public void send(MailMessage mailMessage) throws Exception {
		if (javaMailSender == null) {
			JavaMailSenderConfiguration cfg = new JavaMailSenderConfiguration();
			javaMailSender = cfg.buildJavaMailSender();
		}
		this.send(javaMailSender, mailMessage);
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

}