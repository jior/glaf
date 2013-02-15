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
