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

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.jpage.core.mail.MailContext;
import org.jpage.core.mail.model.MailUser;
import org.jpage.core.mail.service.JavaMailContainer;

public class SendMail {

	public static final String sp = System.getProperty("file.separator");

	private static SendMail sender = null;

	private SendMail() {
	}

	public static SendMail getSender() {
		if (sender == null) {
			sender = new SendMail();
		}
		return sender;
	}

	public void sendMail(Message message) {
		JavaMailContainer container = JavaMailContainer.getContainer();
		Session session = container.getSession();
		if (session == null) {
			throw new RuntimeException("没有设置系统邮件帐号，不能发送邮件！");
		}
		MailUser mailUser = MailContext.getSystemMailUser();
		if (mailUser != null) {
			send(session, mailUser, message);
		}
	}

	/**
	 * 发邮件
	 * 
	 * @param transport
	 * @param message
	 * @throws Exception
	 */
	public void send(Session session, MailUser mailUser, Message message) {
		Transport transport = null;
		try {
			transport = session.getTransport("smtp");
			transport.connect(mailUser.getSmtpServer(), mailUser.getSendPort(),
					mailUser.getUsername(), mailUser.getPassword());
			Transport.send(message);
		} catch (AuthenticationFailedException ex) {
			throw new RuntimeException(ex);
		} catch (MessagingException ex) {
			throw new RuntimeException("Mail server connection failed", ex);
		} finally {
			try {
				if (transport != null) {
					transport.close();
				}
			} catch (MessagingException ex) {
			}
		}
	}

}