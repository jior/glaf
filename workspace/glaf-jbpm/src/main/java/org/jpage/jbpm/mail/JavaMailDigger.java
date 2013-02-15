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


package org.jpage.jbpm.mail;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.jpage.util.FileTools;

public class JavaMailDigger {

	public String getContent(byte[] bytes) {
		String content = null;
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			bis = new BufferedInputStream(bais);
			Properties props = new Properties();
			props.put("mail.pop3.host", "abcd.com");
			Session session = Session.getInstance(props);
			MimeMessage mimeMessage = new MimeMessage(session, bis);
			Object obj = mimeMessage.getContent();
			if (obj instanceof Multipart) {
				Multipart mp = (Multipart) obj;
				content = this.processMultipart(mp);
			} else if (obj instanceof Part) {
				content = this.processPart((Part) obj);
			} else {
				content = this.processPart(mimeMessage);
			}
		} catch (MessagingException me) {
			throw new RuntimeException(me);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (bais != null) {
					bais.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException ex) {
			}
		}
		return content;
	}

	protected String processMultipart(Multipart mp) {
		String content = null;
		try {
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				BodyPart bp = (BodyPart) mp.getBodyPart(i);
				String filename = bp.getFileName();
				if (filename == null) {
					content = this.processPart(bp);
				} else {
					String contentType = bp.getContentType();
					if (contentType == null) {
						contentType = "";
					}
					contentType = contentType.toLowerCase();
					if (StringUtils.contains(contentType, "text/html")
							|| StringUtils.contains(contentType, "text/plain")) {
						byte[] bytes = FileTools.getBytes(bp.getInputStream());
						if (bytes != null && bytes.length > 0) {
							content = new String(bytes);
						}
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return content;
	}

	protected String processPart(Part p) {
		String content = null;
		try {
			String contentType = p.getContentType();
			if (contentType == null) {
				contentType = "";
			}
			contentType = contentType.toLowerCase();
			if (StringUtils.contains(contentType, "text/html")
					|| StringUtils.contains(contentType, "text/plain")) {
				byte[] bytes = FileTools.getBytes(p.getInputStream());
				if (bytes != null && bytes.length > 0) {
					content = new String(bytes);
				}
			} else if (contentType.startsWith("multipart")) {
				Multipart mp = (Multipart) p.getContent();
				content = this.processMultipart(mp);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return content;
	}

	public static void main(String[] args) throws Exception {
		JavaMailDigger digger = new JavaMailDigger();
		byte[] bytes = FileTools.getBytes(new File(args[0]));
		System.out.println(digger.getContent(bytes));
	}

}
