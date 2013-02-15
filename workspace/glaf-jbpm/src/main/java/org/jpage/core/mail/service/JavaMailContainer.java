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

package org.jpage.core.mail.service;

import java.util.Properties;

import javax.mail.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.config.Configurator;
import org.jpage.core.cache.CacheFactory;
import org.jpage.core.mail.MailContext;
import org.jpage.core.mail.model.MailUser;

public class JavaMailContainer {
	private static final Log logger = LogFactory
			.getLog(JavaMailContainer.class);

	public static final String MAIL_SESSION = "MAIL_SESSION";

	private static JavaMailContainer container;

 

	private JavaMailContainer() {
		 
	}

	public static JavaMailContainer getContainer() {
		if (container == null) {
			container = new JavaMailContainer();
		}
		return container;
	}

	public Session getSession() {
		Session session = (Session) CacheFactory.get(MAIL_SESSION);
		if (session == null) {
			MailUser mailUser = MailContext.getSystemMailUser();
			if (mailUser != null) {
				session = this.getSession(mailUser);
				CacheFactory.put(MAIL_SESSION, session);
			}
		}
		return session;
	}

	public Session getSession(MailUser mailUser) {
		String host = mailUser.getSmtpServer();
		int port = mailUser.getSendPort();
		String username = mailUser.getUsername();
		String password = mailUser.getPassword();

		String cacheKey = host + "_" + username;
		if (CacheFactory.get(cacheKey) != null) {
			return (Session) CacheFactory.get(cacheKey);
		}

		Properties props = new Properties();
		props.put("mail.smtp.host", host);

		if (StringUtils.equals(mailUser.getAuthFlag(), "true")) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}

		if (port > 0 && port != 25) {
			props.put("mail.smtp.port", String.valueOf(port));
		}

		// 在jpage.properties文件中使用参数设置这个属性。
		// 例如:proxyServerHost=192.168.0.1
		// proxyServerPort=10080

		if (StringUtils.isNotBlank(Configurator.getProperty("proxyServerHost"))) {
			props.put("socksProxyHost",
					Configurator.getProperty("proxyServerHost"));
			if (logger.isDebugEnabled()) {
				logger.debug("使用代理服务器域名:"
						+ Configurator.getProperty("proxyServerHost"));
			}
		}

		if (StringUtils.isNotBlank(Configurator.getProperty("proxyServerPort"))) {
			props.put("socksProxyPort",
					Configurator.getProperty("proxyServerPort"));
			if (logger.isDebugEnabled()) {
				logger.debug("使用代理服务器端口:"
						+ Configurator.getProperty("proxyServerPort"));
			}
		}

		Session session = null;
		session = Session.getInstance(props, new MailAuthenticator(username,
				password));
		session.setDebug(false);

		CacheFactory.put(cacheKey, session);

		return session;
	}

}
