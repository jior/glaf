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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.mail.model.MailTemplate;
import org.jpage.core.mail.service.MailMessage;
import org.jpage.datacenter.file.DataFile;
import org.jpage.jbpm.config.SystemProperties;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MruCacheStorage;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerJavaMailSender implements MailSender {
	private static final Log logger = LogFactory
			.getLog(FreeMarkerJavaMailSender.class);

	protected static final Configuration cfg = new Configuration();

	protected static boolean isStartup = false;

	private MessageSender messageSender;

	public FreeMarkerJavaMailSender() {

	}

	public MessageSender getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void startup() {
		if (isStartup) {
			return;
		}
		try {
			Resource resource = new ClassPathResource("/jbpm.cfg.xml");
			String path = resource.getFile().getParentFile().getAbsolutePath()
					+ "/config/mail/template";
			path = org.jpage.util.FileTools.getJavaFileSystemPath(path);
			System.out.println("FreeMarker load path:" + path);
			FileTemplateLoader ftl1 = new FileTemplateLoader(resource.getFile()
					.getParentFile());
			FileTemplateLoader ftl2 = new FileTemplateLoader(new File(path));
			ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "");
			TemplateLoader[] loaders = new TemplateLoader[] { ftl1, ftl2, ctl };
			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
			cfg.setCacheStorage(new MruCacheStorage(20, 250));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setTemplateLoader(mtl);
			isStartup = true;
		} catch (IOException ex) {
			logger.error(ex);
		}
	}

	public void send(MailMessage mailMessage) {
		if (!isStartup) {
			startup();
		}

		boolean supportExpression = mailMessage.isSupportExpression();

		String encoding = mailMessage.getEncoding();

		if (StringUtils.isBlank(encoding)) {
			encoding = "GBK";
		}

		String text = null;

		try {

			Map dataMap = mailMessage.getDataMap();
			if (mailMessage.getTemplateId() != null && dataMap != null
					&& dataMap.size() > 0) {
				if (dataMap.get("serviceUrl") == null) {
					String serviceUrl = SystemProperties.getProperties()
							.getProperty("serviceUrl");
					dataMap.put("serviceUrl", serviceUrl);
				}
				MailTemplate template = JavaMailContainer.getContainer()
						.getMailTemplate(mailMessage.getTemplateId());
				
				if (template != null && template.getDataFile() != null) {
					text = template.getContent();
					String dataFile = template.getDataFile();
					StringWriter writer = new StringWriter();
					if (supportExpression) {
						text = (String) DefaultExpressionEvaluator.evaluate(
								text, dataMap);
						try {
							Reader reader = new BufferedReader(
									new StringReader(text));
							Template t = new Template(mailMessage
									.getTemplateId(), reader, cfg, encoding);
							t.process(dataMap, writer);
							writer.flush();
							text = writer.toString();
						} catch (TemplateException ex) {
							logger.error(ex);
							throw ex;
						}
					} else {
						try {
							Template t = cfg.getTemplate(dataFile, encoding);
							t.process(dataMap, writer);
							writer.flush();
							text = writer.toString();
						} catch (TemplateException ex) {
							logger.error(ex);
							throw ex;
						}
					}
				}
			}

			if (text == null) {
				text = "";
			}

			if (logger.isDebugEnabled()) {
				logger.debug(text);
			}

			mailMessage.setText(text);

			messageSender.send(mailMessage);

		} catch (Exception ex) {
			logger.error(ex);
			throw new RuntimeException(ex);
		}
	}

	public static void main(String[] args) throws Exception {
		org.jpage.context.ApplicationContext
				.setAppPath("C:\\jpage2007\\product\\jbpm3\\webapps\\jbpm");

		Map dataMap = new HashMap();
		dataMap.put("taskDescription", "邮件系统开发");
		dataMap.put("processStarterName", "系统管理员");
		dataMap.put("serviceUrl", "http://127.0.0.1:8080/jbpm");
		dataMap.put("callback", "http://127.0.0.1:8080/jbpm/task.jsp");

		MailMessage mailMessage = new MailMessage();
		mailMessage.setFrom("joy.huang@gzgi.com");
		mailMessage.setTo("joy@127.0.0.1");
		mailMessage.setSubject("邮件测试");
		mailMessage.setDataMap(dataMap);
		mailMessage.setTemplateId("freemarker-task");
		mailMessage.setSupportExpression(false);

		Collection files = new HashSet();
		java.io.File file = new java.io.File("C:\\jpage2007\\jbpm\\build.xml");
		files.add(file);

		DataFile dataFile = new DataFile();
		dataFile.setName("jpage-jbpm.jar");
		dataFile.setDeviceId("lib");
		dataFile.setPath("jpage-jbpm.jar");
		// files.add(dataFile);

		DataFile dataFile2 = new DataFile();
		dataFile2.setName("ant编译的配置文件.xml");
		dataFile2.setFile(new java.io.File("C:\\jpage2007\\jbpm\\build.xml"));
		files.add(dataFile2);

		mailMessage.setFiles(files);
		mailMessage.setSaveMessage(false);
		MailSender mailSender = (MailSender) JbpmContextFactory
				.getBean("freeMarkerMailSender");
		mailSender.send(mailMessage);
	}

}