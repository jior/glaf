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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.model.MessageInstance;
import org.jpage.jbpm.service.PersistenceContainer;
import org.jpage.util.UUID32;

public class SimpleMailSender implements MailSender {
	private static final Log logger = LogFactory.getLog(SimpleMailSender.class);

	public final static String DEFAULT_PROTOCOL = "smtp";

	protected PersistenceContainer persistenceContainer = PersistenceContainer
			.getContainer();

	protected MailHelper mailHelper;

	public void send(MailMessage mailMessage) {
		Properties props = SystemProperties.getProperties();
		if (logger.isDebugEnabled()) {
			logger.debug(props);
		}

		String host = props.getProperty("jbpm.mail.smtp.host");
		String username = props.getProperty("jbpm.mail.username");
		String password = props.getProperty("jbpm.mail.password");
		String mailForm = props.getProperty("jbpm.mail.mailForm");
		String encoding = props.getProperty("jbpm.mail.encoding");
		String callbackUrl = props.getProperty("jbpm.mail.callbackUrl");

		if (StringUtils.isBlank(host)) {
			host = "127.0.0.1";
		}
		if (StringUtils.isBlank(mailForm)) {
			mailForm = "admin@127.0.0.1";
		}

		if (StringUtils.isNotBlank(mailMessage.getEncoding())) {
			encoding = mailMessage.getEncoding();
		}

		if (StringUtils.isBlank(encoding)) {
			encoding = "GBK";
		}

		StringBuffer buffer = new StringBuffer();
		if (StringUtils.isBlank(mailMessage.getMessageId())) {
			mailMessage.setMessageId(UUID32.getUUID());
		}

		JavaMailContainer container = JavaMailContainer.getContainer();
		Session session = container.getSession();

		MimeMessage mimeMessage = new MimeMessage(session);

		Transport transport = null;

		try {

			if (StringUtils.isNotBlank(mailMessage.getFrom())) {
				mimeMessage.setFrom(new InternetAddress(mailMessage.getFrom()));
			} else {
				mimeMessage.setFrom(new InternetAddress(mailForm));
			}

			if (mailMessage.getTo() != null) {
				InternetAddress[] addresses = new InternetAddress[mailMessage
						.getTo().length];
				for (int i = 0; i < mailMessage.getTo().length; i++) {
					addresses[i] = new InternetAddress(mailMessage.getTo()[i]);
				}
				mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
				String[] array = mailMessage.getTo();
				for (int i = 0; i < array.length; i++) {
					String elem = array[i];
					buffer.append(elem).append(",");
				}
			}

			if (mailMessage.getCc() != null) {
				InternetAddress[] addresses = new InternetAddress[mailMessage
						.getCc().length];
				for (int i = 0; i < mailMessage.getCc().length; i++) {
					addresses[i] = new InternetAddress(mailMessage.getCc()[i]);
				}
				mimeMessage.setRecipients(Message.RecipientType.CC, addresses);
				String[] array = mailMessage.getCc();
				for (int i = 0; i < array.length; i++) {
					String elem = array[i];
					buffer.append(elem).append(",");
				}
			}

			if (mailMessage.getBcc() != null) {
				InternetAddress[] addresses = new InternetAddress[mailMessage
						.getBcc().length];
				for (int i = 0; i < mailMessage.getBcc().length; i++) {
					addresses[i] = new InternetAddress(mailMessage.getCc()[i]);
				}
				mimeMessage.setRecipients(Message.RecipientType.BCC, addresses);
				String[] array = mailMessage.getBcc();
				for (int i = 0; i < array.length; i++) {
					String elem = array[i];
					buffer.append(elem).append(",");
				}
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

			mimeMessage.setHeader("Mime-Version", "1.0");
			mimeMessage.setHeader("Content-Transfer-Encoding",
					"quoted-printable");
			mimeMessage.setHeader("X-Mailer", "Smart Mail System");

			Multipart mp = new MimeMultipart();

			if (mailMessage.getText() != null) {
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setContent(mailMessage.getText(),
						"text/plain;charset=" + encoding);
				textPart.addHeader("Content-Type", "text/plain;charset="
						+ encoding);
				mp.addBodyPart(textPart); // 添加正文

				mailHelper = new MailHelper();
				String text = mailMessage.getText();

				if (StringUtils.isBlank(callbackUrl)) {
					callbackUrl = ObjectFactory.getServiceUrl()
							+ "/workflow/mail/callback";
				}
				String href = callbackUrl + "?messageId="
						+ mailMessage.getMessageId();
				text = mailHelper.embedCallbackScript(text, href);

				MimeBodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(text, "text/html;charset=" + encoding);
				htmlPart.addHeader("Content-Type", "text/html;charset="
						+ encoding);
				mp.addBodyPart(htmlPart); // 添加HTML
			}

			Collection files = mailMessage.getFiles();
			if (files != null && files.size() > 0) {
				Iterator iterator = files.iterator();
				while (iterator.hasNext()) {
					Object obj = iterator.next();
					MimeBodyPart mimeBodyPart = new MimeBodyPart();
					if (obj instanceof java.io.File) {
						java.io.File file = (java.io.File) obj;
						String filename = file.getName();
						FileDataSource dataSource = new FileDataSource(file);
						String name = filename;
						name = MailTools.chineseStringToAscii(name);
						mimeBodyPart.setFileName(name);
						mimeBodyPart
								.setDataHandler(new DataHandler(dataSource));
						mp.addBodyPart(mimeBodyPart);
					} else if (obj instanceof DataSource) {
						DataSource dataSource = (DataSource) obj;
						String name = dataSource.getName();
						name = MailTools.chineseStringToAscii(name);
						mimeBodyPart.setFileName(name);
						mimeBodyPart
								.setDataHandler(new DataHandler(dataSource));
						mp.addBodyPart(mimeBodyPart);
					} else if (obj instanceof DataFile) {
						DataFile dataFile = (DataFile) obj;
						java.io.File file = dataFile.getFile();
						if (file != null) {
							String filename = file.getName();
							FileDataSource dataSource = new FileDataSource(file);
							String name = dataFile.getName();
							if (name == null) {
								name = filename;
							}
							name = MailTools.chineseStringToAscii(name);
							mimeBodyPart.setFileName(name);
							mimeBodyPart.setDataHandler(new DataHandler(
									dataSource));
							mp.addBodyPart(mimeBodyPart);
						} else {
							DataSource dataSource = new JavaMailDataSource(
									dataFile);
							String name = dataFile.getName();
							name = MailTools.chineseStringToAscii(name);
							mimeBodyPart.setFileName(name);
							mimeBodyPart.setDataHandler(new DataHandler(
									dataSource));
							mp.addBodyPart(mimeBodyPart);
						}
					}
				}
			}

			mimeMessage.setContent(mp);

			mimeMessage.setSentDate(new java.util.Date());
			mimeMessage.saveChanges();

			transport = session.getTransport(DEFAULT_PROTOCOL);
			transport.connect(host, username, password);

			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

			logger.debug("邮件已经成功发送。");

			if (mailMessage.isSaveMessage()) {
				if (buffer.toString().endsWith(",")) {
					buffer.delete(buffer.length() - 1, buffer.length());
				}
				MessageInstance messageInstance = new MessageInstance();
				messageInstance.setMessageId(mailMessage.getMessageId());
				messageInstance.setCreateDate(new Date());
				messageInstance.setMessageType("mail");
				messageInstance.setObjectId("messageId");
				messageInstance.setObjectValue(mailMessage.getMessageId());
				messageInstance.setReceiverId(buffer.toString());
				messageInstance.setSenderId(mailMessage.getFrom());
				messageInstance.setTitle(mailMessage.getSubject());
				messageInstance.setVersionNo(System.currentTimeMillis());
				persistenceContainer.save(messageInstance);
			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
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
		org.jpage.context.ApplicationContext
				.setAppPath("C:\\jpage2007\\resin-2.1.16\\webapps\\jbpm");
		MailMessage mailMessage = new MailMessage();
		mailMessage.setFrom("joy.huang@gzgi.com");
		mailMessage.setTo("joy@127.0.0.1");
		mailMessage.setSubject("邮件测试");
		mailMessage.setText("测试");
		Collection files = new HashSet();
		java.io.File file = new java.io.File("C:\\jpage2007\\jbpm\\build.xml");
		files.add(file);
		DataFile dataFile = new DataFile();
		dataFile.setName("jpage-jbpm.jar");
		dataFile.setDeviceId("lib");
		dataFile.setPath("jpage-jbpm.jar");
		files.add(dataFile);
		mailMessage.setFiles(files);
		MailSender mailSender = (MailSender) JbpmContextFactory
				.getBean("mailSenderXY");
		mailSender.send(mailMessage);
	}

}