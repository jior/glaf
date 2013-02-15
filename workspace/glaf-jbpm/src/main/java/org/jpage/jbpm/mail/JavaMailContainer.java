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

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.cache.CacheFactory;
import org.jpage.core.mail.model.MailTemplate;
import org.jpage.core.mail.service.MailAuthenticator;
import org.jpage.jbpm.config.SystemProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class JavaMailContainer {
	private static final Log logger = LogFactory
			.getLog(JavaMailContainer.class);

	public static final String MAIL_SESSION = "MAIL_SESSION";

	public static final String DEFAULT_MAIL_TEMPLATE = "mail_template_default";

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
			Properties props = SystemProperties.getProperties();
			if (props != null) {
				session = this.getSession(props);
				CacheFactory.put(MAIL_SESSION, session);
			}
		}
		return session;
	}

	public Session getSession(Properties props) {
		String host = props.getProperty("jbpm.mail.smtp.host");
		String username = props.getProperty("jbpm.mail.username");
		String password = props.getProperty("jbpm.mail.password");

		String cacheKey = host + "_" + username;
		if (CacheFactory.get(cacheKey) != null) {
			return (Session) CacheFactory.get(cacheKey);
		}

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");

		// 在jbpm.cfg.xml文件中使用参数设置这个属性。
		// 例如:socksProxyHost=192.168.0.1
		// socksProxyPort=10080
		// <string name="socksProxyHost" value="192.168.0.1" singleton="true"/>
		// <int name="socksProxyPort" value="10080" singleton="true"/>

		if (StringUtils.isNotBlank(props.getProperty("socksProxyHost"))) {
			props.put("socksProxyHost", props.getProperty("socksProxyHost"));
			if (logger.isDebugEnabled()) {
				logger
						.debug("使用代理服务器域名:"
								+ props.getProperty("socksProxyHost"));
			}
		}

		if (StringUtils.isNotBlank(props.getProperty("socksProxyPort"))) {
			props.put("socksProxyPort", props.getProperty("socksProxyPort"));
			if (logger.isDebugEnabled()) {
				logger
						.debug("使用代理服务器端口:"
								+ props.getProperty("socksProxyPort"));
			}
		}

		Session session = null;
		session = Session.getInstance(props, new MailAuthenticator(username,
				password));
		session.setDebug(false);

		CacheFactory.put(cacheKey, session);

		return session;
	}

	public MailTemplate getDefaultMailTemplate() {
		String templateId = "default";
		return this.getMailTemplate(templateId);
	}

	public MailTemplate getMailTemplate(String templateId) {
		if (templateId == null) {
			templateId = "default";
		}
		templateId = templateId.toLowerCase();
		String cacheKey = "mail_template_" + templateId;
		MailTemplate template = (MailTemplate) CacheFactory.get(cacheKey);
		if (template == null) {
			Map templates = this.getTemplates();
			if (templates != null && templates.size() > 0) {
				Iterator iterator = templates.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					name = name.toLowerCase();
					MailTemplate templatexy = (MailTemplate) templates
							.get(name);
					String cKey = "mail_template_" + name;
					CacheFactory.put(cKey, templatexy);
					if (name.equalsIgnoreCase(templateId)) {
						template = templatexy;
					}
				}
			}
		}
		return template;
	}

	private Map getTemplates() {
		String cacheKey = "mail_templates";
		if (CacheFactory.get(cacheKey) != null) {
			return (Map) CacheFactory.get(cacheKey);
		}
		Resource resource = new ClassPathResource("/jbpm.mail.templates.xml");
		InputStream inputStream = null;
		try {
			inputStream = resource.getInputStream();
			XmlReader reader = new XmlReader();
			logger.debug("准备装载邮件模板,请等待...");
			Map templates = reader.getTemplates(inputStream);
			CacheFactory.put(cacheKey, templates);
			if (templates != null && templates.size() > 0) {
				Iterator iterator = templates.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					name = name.toLowerCase();
					MailTemplate template = (MailTemplate) templates.get(name);
					String cKey = "mail_template_" + name;
					CacheFactory.put(cKey, template);
					logger.debug("加载邮件模板到缓存：" + name);
				}
			}
			return templates;
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
			}
		}
	}

}
