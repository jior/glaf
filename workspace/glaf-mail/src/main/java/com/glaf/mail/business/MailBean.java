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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;
import com.glaf.mail.Mail;
import com.glaf.mail.domain.MailDataFile;
import com.glaf.mail.util.MailUtils;

public class MailBean {

	protected static final Log logger = LogFactory.getLog(MailBean.class);

	private String dateFormat = "yyyy-MM-dd HH:mm";

	private Mail mail = null;

	private MimeMessage mimeMessage = null;

	public MailBean(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
		this.mail = new Mail();
	}

	/**
	 * ��ȡ�����ʼ�����Ϣ
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getFrom() throws MessagingException {
		InternetAddress[] address = (InternetAddress[]) mimeMessage.getFrom();
		String from = address[0].getAddress();
		if (from == null) {
			from = "";
		}
		String personal = address[0].getPersonal();
		if (personal == null) {
			personal = "";
		}
		String fromaddr = personal + "<" + from + ">";
		return fromaddr;
	}

	public Mail getMail() {
		return mail;
	}

	/**
	 * ��ȡ�ʼ��ռ��ˣ����ͣ����͵ĵ�ַ����Ϣ��<br/>
	 * ���������ݵĲ�����ͬ "to"-->�ռ���,"cc"-->�����˵�ַ,"bcc"-->���͵�ַ
	 * 
	 * @param type
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public String getMailAddress(String type) throws MessagingException,
			UnsupportedEncodingException {
		String mailAddr = "";
		String addrType = type.toUpperCase();
		InternetAddress[] address = null;

		if (addrType.equals("TO") || addrType.equals("CC")
				|| addrType.equals("BCC")) {
			if (addrType.equals("TO")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.TO);
			}
			if (addrType.equals("CC")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.CC);
			}
			if (addrType.equals("BCC")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.BCC);
			}
			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					String mail = address[i].getAddress();
					if (mail == null) {
						mail = "";
					} else {
						mail = MimeUtility.decodeText(mail);
					}
					String personal = address[i].getPersonal();
					if (personal == null) {
						personal = "";
					} else {
						personal = MimeUtility.decodeText(personal);
					}
					String compositeto = personal + "<" + mail + ">";
					mailAddr += "," + compositeto;
				}
				mailAddr = mailAddr.substring(1);
			}
		} else {
			throw new RuntimeException("Error email Type!");
		}
		mailAddr = MailUtils.convertString(mailAddr);
		return mailAddr;
	}

	/**
	 * ��ȡ���ʼ���message-id
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getMessageId() throws MessagingException {
		return mimeMessage.getMessageID();
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	/**
	 * �ж��ʼ��Ƿ���Ҫ��ִ�������ִ����true�����򷵻�false
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public boolean getReplySign() throws MessagingException {
		boolean replySign = false;
		String needreply[] = mimeMessage
				.getHeader("Disposition-Notification-TO");
		if (needreply != null) {
			replySign = true;
		}
		return replySign;
	}

	/**
	 * ��ȡ�ʼ���������
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getSendDate() throws MessagingException {
		Date sendDate = mimeMessage.getSentDate();
		SimpleDateFormat smd = new SimpleDateFormat(dateFormat);
		return smd.format(sendDate);
	}

	/**
	 * ��ȡ�ʼ�����
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public String getSubject() throws UnsupportedEncodingException,
			MessagingException {
		String subject = "";
		if (mimeMessage.getSubject() != null) {
			String text = mimeMessage.getSubject();
			logger.debug("orgi:" + text);
			text = MimeUtility.decodeText(mimeMessage.getSubject());
			logger.debug("MimeUtility.decodeText:" + text);
			text = MailUtils.convertString(mimeMessage.getSubject());
			logger.debug("MailUtils.convertString:" + text);
			subject = text;
		}
		if (subject == null) {
			subject = "";
		}
		return subject;
	}

	/**
	 * �ж����Ƿ��������
	 * 
	 * @param part
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public boolean isContainAttch(Part part) throws MessagingException,
			IOException {
		boolean flag = false;
		if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int count = multipart.getCount();
			for (int i = 0; i < count; i++) {
				BodyPart bodypart = multipart.getBodyPart(i);
				String dispostion = bodypart.getDisposition();
				if ((dispostion != null)
						&& (dispostion.equals(Part.ATTACHMENT) || dispostion
								.equals(Part.INLINE))) {
					flag = true;
				} else if (bodypart.isMimeType("multipart/*")) {
					flag = isContainAttch(bodypart);
				} else {
					String contentType = bodypart.getContentType();
					if (contentType.toLowerCase().indexOf("appliaction") != -1) {
						flag = true;
					}
					if (contentType.toLowerCase().indexOf("name") != -1) {
						flag = true;
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			flag = isContainAttch((Part) part.getContent());
		}

		return flag;
	}

	/**
	 * �жϴ��ʼ��Ƿ��Ѷ������δ���򷵻�false���Ѷ�����true
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public boolean isNew() throws MessagingException {
		boolean isnew = false;
		Flags flags = ((Message) mimeMessage).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		logger.debug("flags's length:" + flag.length);
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				logger.debug("seen message .......");
				break;
			}
		}

		return isnew;
	}

	public void parse() throws MessagingException, IOException {
		this.parse(getMimeMessage());
	}

	public void parse(Part part) throws MessagingException, IOException {
		logger.debug("------------------START-----------------------");
		mail.setMessageId(this.getMessageId());
		mail.setSendDate(mimeMessage.getSentDate());
		mail.setSubject(this.getSubject());
		mail.setMailFrom(this.getFrom());
		mail.setMailTo(this.getMailAddress("TO"));
		mail.setMailCC(this.getMailAddress("CC"));

		logger.debug("Message subject:" + getSubject());
		logger.debug("Message from:" + getFrom());
		logger.debug("Message isNew:" + isNew());
		boolean flag = isContainAttch(part);
		logger.debug("Message isContainAttch:" + flag);
		logger.debug("Message replySign:" + getReplySign());
		parseMailContent(part);
		// logger.debug("Message" + i + " content:" + getBodyText());

		if (flag) {
			parseAttachment(part);
		}
		logger.debug("------------------END-----------------------");
	}

	/**
	 * ���渽��
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void parseAttachment(Part part) throws MessagingException,
			IOException {
		String filename = "";
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String dispostion = mpart.getDisposition();
				if ((dispostion != null)
						&& (dispostion.equals(Part.ATTACHMENT) || dispostion
								.equals(Part.INLINE))) {
					filename = mpart.getFileName();
					if (filename != null) {
						logger.debug("orig filename=" + filename);
						if (filename.indexOf("?") != -1) {
							filename = new String(filename.getBytes("GBK"),
									"UTF-8");
						}
						if (filename.toLowerCase().indexOf("gb2312") != -1) {
							filename = MimeUtility.decodeText(filename);
						}
						filename = MailUtils.convertString(filename);
						logger.debug("filename=" + filename);
						parseFileContent(filename, mpart.getInputStream());
					}
				} else if (mpart.isMimeType("multipart/*")) {
					parseAttachment(mpart);
				} else {
					filename = mpart.getFileName();
					if (filename != null) {
						logger.debug("orig filename=" + filename);
						if (filename.indexOf("?") != -1) {
							filename = new String(filename.getBytes("GBK"),
									"UTF-8");
						}
						if (filename.toLowerCase().indexOf("gb2312") != -1) {
							filename = MimeUtility.decodeText(filename);
						}
						filename = MailUtils.convertString(filename);
						parseFileContent(filename, mpart.getInputStream());
						logger.debug("filename=" + filename);
					}
				}
			}

		} else if (part.isMimeType("message/rfc822")) {
			parseAttachment((Part) part.getContent());
		}
	}

	/**
	 * �����ļ�����
	 * 
	 * @param filename
	 * @param inputStream
	 * @throws IOException
	 */
	public void parseFileContent(String filename, InputStream inputStream) {
		if (filename == null) {
			return;
		}
		try {
			filename = MailUtils.convertString(filename);
			filename = FileUtils.replaceWin32FileName(filename);
			logger.debug("filename:" + filename);
			byte[] bytes = FileUtils.getBytes(inputStream);
			MailDataFile att = new MailDataFile();
			att.setCreateDate(new Date());
			att.setFileContent(bytes);
			att.setFilename(filename);
			att.setSize(bytes.length);
			att.setFileId(UUID32.getUUID());
			mail.addFile(att);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �����ʼ������õ����ʼ����ݱ��浽һ��stringBuffer�����У������ʼ� ��Ҫ����MimeType�Ĳ�ִͬ�в�ͬ�Ĳ�����һ��һ���Ľ���
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void parseMailContent(Part part) throws MessagingException,
			IOException {
		String contentType = part.getContentType();
		int nameindex = contentType.indexOf("name");
		boolean conname = false;
		if (nameindex != -1) {
			conname = true;
		}
		logger.debug("contentType:" + contentType);
		if (part.isMimeType("text/plain") && !conname) {
			mail.setContent((String) part.getContent());
		} else if (part.isMimeType("text/html") && !conname) {
			mail.setHtml((String) part.getContent());
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int count = multipart.getCount();
			for (int i = 0; i < count; i++) {
				parseMailContent(multipart.getBodyPart(i));
			}
		} else if (part.isMimeType("message/rfc822")) {
			parseMailContent((Part) part.getContent());
		}
	}

}
