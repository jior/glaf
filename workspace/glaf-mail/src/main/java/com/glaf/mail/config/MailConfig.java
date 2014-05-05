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

package com.glaf.mail.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.glaf.core.config.MailProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.Constants;
import com.glaf.core.util.PropertiesUtils;

import com.glaf.mail.MxMimeMessage;
import com.glaf.mail.MxMailAuthenticator;

public class MailConfig implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String host;

	private int port;

	private String username;

	private String password;

	private String mailFrom;

	private String encoding;

	private String text;

	private boolean auth;

	public MailConfig() {

	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getActorId() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public void check() throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);

		if (auth) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}

		if (port > 0 && port < 65536) {
			props.put("mail.smtp.port", String.valueOf(port));
		}
		Session session = null;
		session = Session.getInstance(props, new MxMailAuthenticator(username,
				password));
		session.setDebug(true);

		MimeMessage newMessage = new MxMimeMessage(session);

		newMessage.setFrom(new InternetAddress(mailFrom, mailFrom));

		newMessage.setRecipients(RecipientType.TO, mailFrom);
		String subject = MimeUtility.encodeText(new String(text.getBytes(),
				encoding), encoding, "B");
		newMessage.setSubject(subject);
		newMessage.setSentDate(new java.util.Date());
		newMessage.setHeader("Mime-Version", "1.0");
		newMessage.setHeader("Content-Transfer-Encoding", "quoted-printable");
		newMessage.setHeader("X-Mailer", "JavaMailSystem");

		Multipart mp = new MimeMultipart();

		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(text, "text/plain;charset=" + encoding);
		textPart.addHeader("Content-Type", "text/plain;charset=" + encoding);
		mp.addBodyPart(textPart);

		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(text, "text/html;charset=" + encoding);
		htmlPart.addHeader("Content-Type", "text/html;charset=" + encoding);
		mp.addBodyPart(htmlPart);

		newMessage.setContent(mp);
		newMessage.saveChanges();

		try {
			Transport transport = session.getTransport("smtp");
			if (username != null && password != null) {
				transport.connect(host, username, password);
			} else {
				transport.connect();
			}
			Transport.send(newMessage);
			transport.close();
		} catch (Exception ex) {
			if (ex instanceof javax.mail.internet.ParseException) {
				throw new RuntimeException("邮件地址不正确，发送邮件失败。");
			} else if (ex instanceof javax.mail.AuthenticationFailedException) {
				throw new RuntimeException("用户没有通过认证，发送邮件失败。");
			} else if (ex instanceof javax.mail.internet.AddressException) {
				throw new RuntimeException("邮件地址有误，邮件无法成功发送。");
			} else if (ex instanceof javax.mail.SendFailedException) {
				throw new RuntimeException("邮件发送失败。");
			} else if (ex instanceof java.net.UnknownHostException) {
				throw new RuntimeException("无法解析SMTP服务器地址。");
			} else if (ex instanceof java.net.SocketException) {
				throw new RuntimeException("网络中断错误。");
			} else if (ex instanceof java.io.IOException) {
				throw new RuntimeException("IO中断错误。");
			} else if (ex instanceof java.net.ConnectException) {
				throw new RuntimeException("发送端口错误。");
			}
			if (ex instanceof javax.mail.MessagingException) {
				throw new RuntimeException(
						"连接邮件服务器失败，可能是网络已经断开或邮件服务器已经关闭，请稍后再试。");
			}
			String errormsg = ex.getMessage();
			if (errormsg == null) {
				errormsg = "未知错误原因。";
			}
			throw new RuntimeException(ex);
		}
	}

	public void writeMailProperties() {
		String cfgFile = SystemProperties.getConfigRootPath()
				+ Constants.MAIL_CONFIG;
		Resource resource = new FileSystemResource(cfgFile);
		Properties properties = new Properties();
		try {
			InputStream inputStream = resource.getInputStream();
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException("不能读取配置文件", ex);
		}

		properties.put("mail.host", host);

		if (port > 0 && port < 65536) {
			properties.put("mail.port", String.valueOf(port));
		}

		properties.put("mail.defaultEncoding", encoding);
		properties.put("mail.username", username);
		properties.put("mail.password", password);
		properties.put("mail.auth", String.valueOf(auth));
		properties.put("mail.mailFrom", mailFrom);

		Map<String, String> dataMap = new java.util.HashMap<String, String>();

		Enumeration<Object> e = properties.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			dataMap.put(key, value);
		}

		try {
			PropertiesUtils.save(resource, dataMap);
			MailProperties.reload();
		} catch (IOException ex) {
			throw new RuntimeException("不能保存配置文件", ex);
		}

	}

	public void config() {
		try {
			this.check();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		this.writeMailProperties();
	}

}