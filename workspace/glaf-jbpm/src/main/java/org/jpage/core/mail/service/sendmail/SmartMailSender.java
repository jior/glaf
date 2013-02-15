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

package org.jpage.core.mail.service.sendmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.activation.DataHandler;
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
import javax.mail.internet.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.attachment.model.Attachment;
import org.jpage.core.mail.model.Mail;
import org.jpage.core.mail.model.MailUser;
import org.jpage.core.mail.service.JavaMailContainer;
import org.jpage.core.mail.util.MailTools;

public class SmartMailSender {
	private static Log logger = LogFactory.getLog(SmartMailSender.class);

	private static SmartMailSender sender;

	private Map mailTo;

	private Map mailCC;

	private int totalAttempts = 0;

	private String sp = System.getProperty("file.separator");

	private SmartMailSender() {
	}

	public static SmartMailSender getSender() {
		if (sender == null) {
			sender = new SmartMailSender();
		}
		return sender;
	}

	public void sendMail(Mail mail, MailUser user) throws Exception {
		String username = user.getUsername();
		String password = user.getPassword();
		String fromAddress = user.getMailAddress();
		String fromName = user.getShowName();
		String subject = mail.getMailSubject();
		mailTo = getMailAddress(mail.getMailTo());
		mailCC = getMailAddress(mail.getMailCC());

		if (username == null || password == null) {
			username = "";
			password = "";
		}
		if (fromAddress == null) {
			fromAddress = "";
		}
		if (subject == null) {
			subject = "�������⣩";
		}

		if (fromName == null) {
			fromName = "";
		}

		JavaMailContainer container = JavaMailContainer.getContainer();
		Session session = container.getSession(user);

		MimeMessage newMessage = new MimeMessage(session);
		newMessage.setFrom(new InternetAddress(fromAddress, fromName));

		if (mailTo != null && mailTo.size() > 0) {
			setRecipients(newMessage, Message.RecipientType.TO, mailTo);
			totalAttempts = totalAttempts + mailTo.size();
		} else {
			throw new Exception("�ʼ�Ŀ���ַ����ȷ��");
		}

		if (mailCC != null && mailCC.size() > 0) {
			setRecipients(newMessage, Message.RecipientType.CC, mailCC);
			totalAttempts = totalAttempts + mailCC.size();
		}

		String mailSubject = subject;
		mailSubject = MimeUtility.encodeText(new String(mailSubject.getBytes(),
				"GBK"), "GBK", "B");
		newMessage.setSubject(mailSubject);
		newMessage.setSentDate(new java.util.Date());

		newMessage.setHeader("Mime-Version", "1.0");
		newMessage.setHeader("Content-Transfer-Encoding", "quoted-printable");
		newMessage.setHeader("X-Mailer", "Smart Mail System");

		Multipart mp = new MimeMultipart();
		if (mail.getMailText() != null
				&& mail.getMailText().trim().length() > 0) {
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(MailTools.getText(mail.getMailText(), fromName,
					fromAddress), "text/plain;charset=GBK");
			textPart.addHeader("Content-Type", "text/plain;charset=GBK");
			mp.addBodyPart(textPart); // �������
		}

		if (mail.getMailHtml() != null) {
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(mail.getMailHtml(), "text/html;charset=GBK");
			htmlPart.addHeader("Content-Type", "text/html;charset=GBK");
			mp.addBodyPart(htmlPart); // ���HTML
		}
		Collection attachment = mail.getAttachments();
		if (attachment != null && attachment.size() > 0) {
			String absPath = org.jpage.context.ApplicationContext.getAppPath();
			MimeBodyPart[] attachPart = new MimeBodyPart[attachment.size()]; // ��ʼ���ʼ��ĸ����ĸ�����
			FileDataSource[] fds = new FileDataSource[attachment.size()];
			Iterator item = attachment.iterator();
			int i = 0;
			while (item.hasNext()) {
				Attachment attach = (Attachment) item.next();
				String myFile = absPath + sp + attach.getFolder(); // ���Ǹ����ľ���·��
				fds[i] = new FileDataSource(myFile);
				attachPart[i] = new MimeBodyPart();
				attachPart[i].setDataHandler(new DataHandler(fds[i])); // �Ѹ����ӵ�������
				String fn = attach.getFilename();
				fn = MailTools.chineseStringToAscii(fn);
				attachPart[i].setFileName(fn);
				i++;
			}
			for (int k = 0; k < attachment.size(); k++) {
				mp.addBodyPart(attachPart[k]); // ����ʼ��ĸ�������
			}
		}
		newMessage.setContent(mp);
		newMessage.saveChanges();
		try {
			send(session, user, newMessage);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	private void send(Session session, MailUser user, Message newMessage)
			throws Exception {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("-----------------�����ʼ�������---------------");
			}
			Transport transport = session.getTransport("smtp");
			if (user.getUsername() != null && user.getPassword() != null) {
				transport.connect(user.getSmtpServer(), user.getUsername(),
						user.getPassword());
			} else {
				transport.connect();
			}

			Transport.send(newMessage);

			if (logger.isDebugEnabled()) {
				logger.debug("-------------------�ɹ����---------------");
			}
		} catch (Exception ex) {
			logger.error(">>>>>>>>>>>>>>>>>>>>>�����ʼ�����>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.error(ex);
			if (ex instanceof javax.mail.internet.ParseException) {
				throw new Exception("�ʼ���ַ����ȷ�������ʼ�ʧ�ܡ�");
			} else if (ex instanceof javax.mail.AuthenticationFailedException) {
				throw new Exception("�û�û��ͨ����֤�������ʼ�ʧ�ܡ�");
			} else if (ex instanceof javax.mail.internet.AddressException) {
				throw new Exception("�ʼ���ַ�����ʼ��޷��ɹ����͡�");
			} else if (ex instanceof javax.mail.SendFailedException) {
				throw new Exception("�ʼ�����ʧ�ܡ�");
			} else if (ex instanceof java.net.UnknownHostException) {
				throw new Exception("�޷�����SMTP��������ַ��");
			} else if (ex instanceof java.net.SocketException) {
				throw new Exception("�����жϴ���");
			} else if (ex instanceof java.io.IOException) {
				throw new Exception("IO�жϴ���");
			} else if (ex instanceof java.net.ConnectException) {
				throw new Exception("���Ͷ˿ڴ���");
			}
			if (ex instanceof javax.mail.MessagingException) {
				throw new Exception("�����ʼ�������ʧ�ܣ������������Ѿ��Ͽ����ʼ��������Ѿ��رգ����Ժ����ԡ�");
			}
			String errormsg = ex.getMessage();
			logger.error("����ԭ��:" + errormsg);
			if (errormsg == null) {
				errormsg = "δ֪����ԭ��";
			}
			throw new Exception(errormsg);
		}
	}

	private void setRecipients(javax.mail.Message message,
			Message.RecipientType type, Map addresses)
			throws MessagingException {
		InternetAddress iaa[] = getAddresses(addresses);
		if (iaa != null) {
			message.setRecipients(type, iaa);
		}
	}

	private InternetAddress[] getAddresses(Map addresses) throws ParseException {
		InternetAddress iaa[] = null;
		Vector via = new Vector(10);
		String name = null, address = null;

		Set entries = addresses.entrySet();
		Iterator iter = entries.iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			address = (String) entry.getKey();
			name = (String) entry.getValue();
			try {
				via.addElement(new InternetAddress(address, name));
			} catch (Exception ex) {
				logger
						.error("Invalid Mail Address : " + name + " - "
								+ address);
				throw new ParseException("������ʼ���ַ : " + name + " - " + address);
			}
		}

		if (via.size() < 1) {
			return null;
		}

		iaa = new InternetAddress[via.size()];
		via.copyInto(iaa);
		return iaa;
	}

	private HashMap getMailAddress(String address) throws ParseException {
		if (address == null || address.trim().equals("null")
				|| address.trim().equals("")) {
			return null;
		}
		HashMap map = new HashMap();
		if (address.indexOf(";") == -1) {
			if (address.indexOf(".") >= 4 && address.indexOf("@") >= 2
					&& address.indexOf("@") < address.indexOf(".")) {
				map.put(address, address);
			}
		}
		StringTokenizer token = new StringTokenizer(address, ";");
		while (token.hasMoreTokens()) {
			String addr = token.nextToken();
			if (addr != null && addr.trim().length() > 0) {
				if (address.indexOf(".") >= 4 && address.indexOf("@") >= 2
						&& address.indexOf("@") < address.indexOf(".")) {
					map.put(addr, addr);
				}
			}
		}

		return map;
	}

	public static void main(String[] args) throws Exception {
		org.jpage.context.ApplicationContext.setAppPath("C:\\tmp");
		SmartMailSender sender = SmartMailSender.getSender();
		for (int i = 0; i < 1; i++) {
			Mail mail = new Mail();
			MailUser user = new MailUser();
			mail.setMailTo("admin@127.0.0.1");
			mail.setMailSubject("����");
			mail.setMailText("�����ʼ�����");
			mail
					.setMailHtml("<A href='http://www.google.com'><font color=red>�����ʼ�����</font></a>");
			user.setShowName("�Ƴ���");
			user.setMailAddress("joy@127.0.0.1");
			user.setUsername("joy");
			user.setPassword("111111");
			user.setSmtpServer("127.0.0.1");
			user.setSendPort(25);
			List attachments = new ArrayList();
			Attachment att = new Attachment();
			att.setFilename("JavaMail����ָ��.pdf");
			att.setFolder("JavaMail.pdf");
			attachments.add(att);
			mail.setAttachments(attachments);
			sender.sendMail(mail, user);
		}
	}
}