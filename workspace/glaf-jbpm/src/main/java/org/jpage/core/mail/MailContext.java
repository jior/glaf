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

package org.jpage.core.mail;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jpage.config.Configurator;
import org.jpage.core.mail.model.MailUser;

public class MailContext {
	private final static Map cache = Collections.synchronizedMap(new HashMap());

	public final static String SYSTEM_MAIL_USER = "system_mail_user";

	public final static String SYSTEM_MAIL_USERID = "999999999";

	private MailContext() {
	}

	public static MailUser getSystemMailUser() {
		if (cache.get(SYSTEM_MAIL_USER) != null) {
			return (MailUser) cache.get(SYSTEM_MAIL_USER);
		}
		Properties props = Configurator.getProperties();
		if (props != null) {
			MailUser mailUser = new MailUser();
			mailUser.setUserId(SYSTEM_MAIL_USERID);
			mailUser.setSmtpServer(props.getProperty("mail.host", "127.0.0.1"));
			mailUser.setUsername(props.getProperty("mail.username", "admin"));
			mailUser.setPassword(props.getProperty("mail.password", "admin"));
			mailUser.setMailAddress(props.getProperty("mail.from",
					"admin@127.0.0.1"));
			mailUser.setShowName(props.getProperty("mail.from.name", "Administrator"));
			String sendPort = props.getProperty("port", "25");
			int port = 25;
			try {
				port = Integer.parseInt(sendPort);
			} catch (NumberFormatException ex) {
			}
			mailUser.setSendPort(port);
			cache.put(SYSTEM_MAIL_USER, mailUser);
			return mailUser;
		}
		return null;
	}

}