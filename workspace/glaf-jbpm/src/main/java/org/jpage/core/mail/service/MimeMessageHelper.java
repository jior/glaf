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


package org.jpage.core.mail.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.FileTypeMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.mail.model.Mail;
import org.jpage.core.mail.model.MailUser;
import org.jpage.core.mail.util.MailTools;
import org.jpage.datacenter.file.DataFile;

public class MimeMessageHelper {
	private static Log logger = LogFactory.getLog(MimeMessageHelper.class);
	private String encoding;

	private MimeMessage mimeMessage;

	private MimeMultipart mimeMultipart;

	public MimeMessageHelper() {

	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setFrom(InternetAddress from) throws MessagingException {
		this.mimeMessage.setFrom(from);
	}

	public void setFrom(String from) throws MessagingException {
		this.mimeMessage.setFrom(new InternetAddress(from));
	}

	public void setFrom(String from, String personal)
			throws MessagingException, UnsupportedEncodingException {
		this.mimeMessage.setFrom(this.encoding != null ? new InternetAddress(
				from, personal, this.encoding) : new InternetAddress(from,
				personal));
	}

	public void setReplyTo(String[] replyTo) throws MessagingException {
		InternetAddress[] addresses = new InternetAddress[replyTo.length];
		for (int i = 0; i < replyTo.length; i++) {
			addresses[i] = new InternetAddress(replyTo[i]);
		}
		this.mimeMessage.setReplyTo(addresses);
		logger.error("-------------mimeMessage----------"
				+ this.mimeMessage.getReplyTo()[0]);
	}

	public void setTo(InternetAddress to) throws MessagingException {
		this.mimeMessage.setRecipient(Message.RecipientType.TO, to);
	}

	public void setTo(InternetAddress[] to) throws MessagingException {
		this.mimeMessage.setRecipients(Message.RecipientType.TO, to);
	}

	public void setTo(String to) throws MessagingException {
		this.mimeMessage.setRecipient(Message.RecipientType.TO,
				new InternetAddress(to));
	}

	public void setTo(String[] to) throws MessagingException {
		InternetAddress[] addresses = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			addresses[i] = new InternetAddress(to[i]);
		}
		this.mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
	}

	public void addTo(InternetAddress to) throws MessagingException {
		this.mimeMessage.addRecipient(Message.RecipientType.TO, to);
	}

	public void addTo(String to) throws MessagingException {
		this.mimeMessage.addRecipient(Message.RecipientType.TO,
				new InternetAddress(to));
	}

	public void addTo(String to, String personal) throws MessagingException,
			UnsupportedEncodingException {
		this.mimeMessage.addRecipient(Message.RecipientType.TO,
				this.encoding != null ? new InternetAddress(to, personal,
						this.encoding) : new InternetAddress(to, personal));
	}

	public void setCc(InternetAddress cc) throws MessagingException {
		this.mimeMessage.setRecipient(Message.RecipientType.CC, cc);
	}

	public void setCc(InternetAddress[] cc) throws MessagingException {
		this.mimeMessage.setRecipients(Message.RecipientType.CC, cc);
	}

	public void setCc(String cc) throws MessagingException {
		this.mimeMessage.setRecipient(Message.RecipientType.CC,
				new InternetAddress(cc));
	}

	public void setCc(String[] cc) throws MessagingException {
		InternetAddress[] addresses = new InternetAddress[cc.length];
		for (int i = 0; i < cc.length; i++) {
			addresses[i] = new InternetAddress(cc[i]);
		}
		this.mimeMessage.setRecipients(Message.RecipientType.CC, addresses);
	}

	public void addCc(InternetAddress cc) throws MessagingException {
		this.mimeMessage.addRecipient(Message.RecipientType.CC, cc);
	}

	public void addCc(String cc) throws MessagingException {
		this.mimeMessage.addRecipient(Message.RecipientType.CC,
				new InternetAddress(cc));
	}

	public void addCc(String cc, String personal) throws MessagingException,
			UnsupportedEncodingException {
		this.mimeMessage.addRecipient(Message.RecipientType.CC,
				this.encoding != null ? new InternetAddress(cc, personal,
						this.encoding) : new InternetAddress(cc, personal));
	}

	public void setBcc(InternetAddress bcc) throws MessagingException {
		this.mimeMessage.setRecipient(Message.RecipientType.BCC, bcc);
	}

	public void setBcc(InternetAddress[] bcc) throws MessagingException {
		this.mimeMessage.setRecipients(Message.RecipientType.BCC, bcc);
	}

	public void setBcc(String bcc) throws MessagingException {
		this.mimeMessage.setRecipient(Message.RecipientType.BCC,
				new InternetAddress(bcc));
	}

	public void setBcc(String[] bcc) throws MessagingException {
		InternetAddress[] addresses = new InternetAddress[bcc.length];
		for (int i = 0; i < bcc.length; i++) {
			addresses[i] = new InternetAddress(bcc[i]);
		}
		this.mimeMessage.setRecipients(Message.RecipientType.BCC, addresses);
	}

	public void addBcc(InternetAddress bcc) throws MessagingException {
		this.mimeMessage.addRecipient(Message.RecipientType.BCC, bcc);
	}

	public void addBcc(String bcc) throws MessagingException {
		this.mimeMessage.addRecipient(Message.RecipientType.BCC,
				new InternetAddress(bcc));
	}

	public void addBcc(String bcc, String personal) throws MessagingException,
			UnsupportedEncodingException {
		this.mimeMessage.addRecipient(Message.RecipientType.BCC,
				this.encoding != null ? new InternetAddress(bcc, personal,
						this.encoding) : new InternetAddress(bcc, personal));
	}

	public void setSubject(String subject) throws MessagingException {
		if (this.encoding != null) {
			this.mimeMessage.setSubject(subject, this.encoding);
		} else {
			this.mimeMessage.setSubject(subject);
		}
	}

	public void setText(String text) throws MessagingException {
		setText(text, false);
	}

	public void setText(final String text, boolean html)
			throws MessagingException {
		MimePart partToUse = null;
		if (this.mimeMultipart != null) {
			MimeBodyPart bodyPart = null;
			for (int i = 0; i < this.mimeMultipart.getCount(); i++) {
				BodyPart bp = this.mimeMultipart.getBodyPart(i);
				if (bp.getFileName() == null) {
					bodyPart = (MimeBodyPart) bp;
				}
			}
			if (bodyPart == null) {
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				this.mimeMultipart.addBodyPart(mimeBodyPart);
				bodyPart = mimeBodyPart;
			}
			partToUse = bodyPart;
		} else {
			partToUse = this.mimeMessage;
		}

		if (html) {
			partToUse.setDataHandler(new DataHandler(new DataSource() {
				public InputStream getInputStream() throws IOException {
					return new ByteArrayInputStream(encoding != null ? text
							.getBytes(encoding) : text.getBytes());
				}

				public OutputStream getOutputStream() throws IOException {
					throw new UnsupportedOperationException(
							"Read-only javax.activation.DataSource");
				}

				public String getContentType() {
					return "text/html";
				}

				public String getName() {
					return "text";
				}
			}));
		} else {
			if (this.encoding != null) {
				partToUse.setText(text, this.encoding);
			} else {
				partToUse.setText(text);
			}
		}
	}

	public void addAttachment(String attachmentFilename, File file)
			throws MessagingException {
		addAttachment(attachmentFilename, new FileDataSource(file));
	}

	public void addAttachment(final String attachmentFilename,
			final InputStreamSource inputStreamSource)
			throws MessagingException {
		addAttachment(attachmentFilename, new DataSource() {
			public InputStream getInputStream() throws IOException {
				return inputStreamSource.getInputStream();
			}

			public OutputStream getOutputStream() {
				throw new UnsupportedOperationException(
						"Read-only javax.activation.DataSource");
			}

			public String getContentType() {
				return FileTypeMap.getDefaultFileTypeMap().getContentType(
						attachmentFilename);
			}

			public String getName() {
				return attachmentFilename;
			}
		});
	}

	public void addAttachment(String attachmentFilename, DataSource dataSource)
			throws MessagingException {
		if (this.mimeMultipart == null) {
			throw new IllegalStateException(
					"Cannot add attachment - not in multipart mode");
		}
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setFileName(attachmentFilename);
		bodyPart.setDataHandler(new DataHandler(dataSource));
		this.mimeMultipart.addBodyPart(bodyPart);
	}

	public MimeMessage createMimeMessage(Mail mail) throws MessagingException,
			UnsupportedEncodingException {
		Session session = Session.getInstance(new Properties());
		session.setDebug(false);
		return this.createMimeMessage(session, mail);
	}

	public MimeMessage createMimeMessage(Mail mail, MailUser mailUser)
			throws MessagingException, UnsupportedEncodingException {
		Session session = null;
		return this.createMimeMessage(session, mail);
	}

	public MimeMessage createMimeMessage(Session session, Mail mail)
			throws MessagingException, UnsupportedEncodingException {
		if (session == null) {
			throw new IllegalArgumentException(
					"Cannot work with a null Session");
		}

		mimeMessage = new CustomMimeMessage(session);

		if (StringUtils.isNotBlank(mail.getMailFrom())) {
			this.setFrom(mail.getMailFrom());
		}
		if (StringUtils.isNotBlank(mail.getMailTo())) {
			this.setTo(this.getMailAddress(mail.getMailTo()));
		}
		if (StringUtils.isNotBlank(mail.getMailCC())) {
			this.setCc(this.getMailAddress(mail.getMailCC()));
		}
		if (StringUtils.isNotBlank(mail.getMailBCC())) {
			this.setBcc(this.getMailAddress(mail.getMailBCC()));
		}

		String mailSubject = mail.getMailSubject();
		try {
			if (mailSubject != null) {
				mailSubject = MimeUtility.encodeText(
						new String(mailSubject.getBytes(), "GBK"), "GBK", "B");
			}
		} catch (UnsupportedEncodingException ex) {
		}

		if (mailSubject == null) {
			mailSubject = "";
		}

		mimeMessage.setSubject(mailSubject);
		mimeMessage.setSentDate(new java.util.Date());

		Multipart mp = new MimeMultipart();
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(mail.getMailText(), "text/plain;charset=GBK");
		textPart.addHeader("Content-Type", "text/plain;charset=GBK");
		mp.addBodyPart(textPart); // 添加正文

		if (mail.getMailHtml() != null) {
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(mail.getMailHtml(), "text/html;charset=GBK");
			htmlPart.addHeader("Content-Type", "text/html;charset=GBK");
			mp.addBodyPart(htmlPart); // 添加HTML
		}
		Collection files = mail.getAttachments();
		if (files != null && files.size() > 0) {
			MimeBodyPart[] attachPart = new MimeBodyPart[files.size()]; // 初始化邮件的附件的各部分
			DataSource[] ds = new JavaMailDataSource[files.size()];
			Iterator item = files.iterator();
			int i = 0;
			while (item.hasNext()) {
				DataFile attach = (DataFile) item.next();
				ds[i] = new JavaMailDataSource(attach);
				attachPart[i] = new MimeBodyPart();
				attachPart[i].setDataHandler(new DataHandler(ds[i])); // 把附件加到各部分
				String fn = attach.getName();
				fn = MailTools.chineseStringToAscii(fn);
				attachPart[i].setFileName(fn);
				i++;
			}
			for (int k = 0; k < files.size(); k++) {
				mp.addBodyPart(attachPart[k]); // 添加邮件的附件部分
			}
		}
		mimeMessage.setContent(mp);
		mimeMessage.saveChanges();
		return mimeMessage;
	}

	private String[] getMailAddress(String mailAddress) {
		if (mailAddress == null) {
			return null;
		}
		Vector vector = new Vector();
		StringTokenizer token = new StringTokenizer(mailAddress, ",");
		while (token.hasMoreTokens()) {
			String addr = token.nextToken();
			if (addr.length() > 0) {
				addr = addr.trim();
				if (addr.indexOf("@") > 1 && addr.indexOf(".") > 3
						&& addr.indexOf(".") > addr.indexOf("@") + 2) {
					vector.add(addr);
				}
			}
		}
		int index = 0;
		String[] address = new String[vector.size()];
		java.util.Enumeration enumeration = vector.elements();
		while (enumeration.hasMoreElements()) {
			address[index] = (String) enumeration.nextElement();
			index = index + 1;
		}
		return address;
	}
}