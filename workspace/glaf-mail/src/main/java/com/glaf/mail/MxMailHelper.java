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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.config.MailProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.template.Template;

public class MxMailHelper {

	public final static String TEXT_PLAIN = "text/plain";

	public final static String TEXT_HTML = "text/html";

	public final static String MULTIPART = "multipart/*";

	public String getCallbackHref(String messageId) {
		String callbackUrl = MailProperties.getString("mail.callback.url");
		return this.getCallbackHref(callbackUrl, messageId);
	}

	public String getCallbackHref(Template template, String messageId) {
		if (template != null
				&& StringUtils.isNotEmpty(template.getCallbackUrl())) {
			return this.getCallbackHref(template.getCallbackUrl(), messageId);
		}
		return this.getCallbackHref(messageId);
	}

	public String getCallbackHref(String callbackUrl, String messageId) {
		return callbackUrl + "?messageId=" + messageId;
	}

	public String embedCallbackScript(String html, String href) {
		StringBuffer buffer = new StringBuffer();
		String temp = html.toLowerCase();
		int fromIndex = temp.indexOf("<body");
		if (fromIndex != -1) {
			int endIndex = temp.indexOf(">", fromIndex);
			buffer.append(html.substring(0, endIndex + 1));
			buffer.append("\n<img src=\"").append(href)
					.append("\" width=\"1\" height=\"1\"/>");
			buffer.append(html.substring(endIndex + 1, html.length()));
		} else {
			buffer.append(html);
			buffer.append("\n<img src=\"").append(href)
					.append("\" width=\"1\" height=\"1\"/>");
		}
		return buffer.toString();
	}

	public Mail getMail(byte[] bytes) {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			return this.getMail(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	public Mail getMail(InputStream inputStream) {
		try {
			Properties props = new Properties();
			props.put("mail.pop3.host", "abcd.com");
			Session session = Session.getInstance(props);
			MimeMessage mimeMessage = new MimeMessage(session, inputStream);
			Object body = mimeMessage.getContent();
			Mail mail = new Mail();
			if (body instanceof Multipart) {
				processMultipart((Multipart) body, mail);
			} else {
				processPart(mimeMessage, mail);
			}
			return mail;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void processPart(Part p, Mail mail) throws Exception {
		String contentType = p.getContentType();
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(p.getInputStream());
			if (contentType.startsWith("text/html")) {
				byte[] content = FileUtils.getBytes(inputStream);
				if (content != null && content.length > 0) {
					String html = new String(content);
					mail.setContent(html);
					mail.setMailType(TEXT_HTML);
				}
			} else if (contentType.startsWith("text/plain")) {
				byte[] content = FileUtils.getBytes(inputStream);
				if (content != null && content.length > 0) {
					mail.setContent(new String(content));
					mail.setMailType(TEXT_PLAIN);
				}
			} else if (contentType.startsWith("multipart")
					|| contentType.startsWith("application/octet-stream")) {
				Multipart mp = (Multipart) p.getContent();
				processMultipart(mp, mail);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
			}
		}

	}

	public void processMultipart(Multipart mp, Mail mail) throws Exception {
		for (int i = 0; i < mp.getCount(); i++) {
			String filename = (mp.getBodyPart(i)).getFileName();
			if (filename == null) {
				this.processPart(mp.getBodyPart(i), mail);
			}
		}
	}

	public Map<String, String> getAttributes(String attribute) {
		Map<String, String> dataMap = new java.util.HashMap<String, String>();
		StringReader stringReader = new StringReader(attribute);
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(stringReader);
			Element root = doc.getRootElement();
			List<?> elements = root.elements("element");
			if (elements != null && elements.size() > 0) {
				Iterator<?> iterator = elements.iterator();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					String name = element.attributeValue("name");
					String value = element.attributeValue("value");
					if (StringUtils.isEmpty(value)) {
						value = element.getStringValue();
					}
					dataMap.put(name, value);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataMap;
	}

}