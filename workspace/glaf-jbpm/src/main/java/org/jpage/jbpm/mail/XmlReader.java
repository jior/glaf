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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jpage.core.mail.model.MailTemplate;
import org.jpage.util.FileTools;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class XmlReader {

	private final static Log logger = LogFactory.getLog(XmlReader.class);

	public XmlReader() {

	}

	public Map getTemplates(InputStream inputStream) {
		Map dataMap = new HashMap();
		Map variableMap = new HashMap();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List variables = root.elements("variable");
			if (variables != null && variables.size() > 0) {
				Iterator iterator = variables.iterator();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					String name = element.attributeValue("name");
					String value = element.attributeValue("value");
					variableMap.put(name, value);
				}
			}
			List templates = root.elements("mail-template");
			if (templates != null && templates.size() > 0) {
				Iterator iterator = templates.iterator();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					MailTemplate template = new MailTemplate();
					String subject = element.elementText("subject");
					String text = element.elementText("text");
					String templateId = element.attributeValue("name");
					String dataFile = element.elementText("datafile");
					String callbackUrl = element.elementText("callbackUrl");
					if (StringUtils.isBlank(text)) {
						if (StringUtils.isNotBlank(dataFile)) {
							Resource resource = new ClassPathResource(
									"/config/mail/template/" + dataFile);
							String filename = resource.getFile()
									.getAbsolutePath();
							StringBuffer buffer = FileTools
									.fileToStringBuffer(filename);
							text = buffer.toString();
						}
					}
					template.setCallbackUrl(callbackUrl);
					template.setContent(text);
					template.setDataFile(dataFile);
					template.setSubject(subject);
					templateId = templateId.toLowerCase();
					template.setTemplateId(templateId);
					template.getVariables().putAll(variableMap);
					dataMap.put(templateId, template);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return dataMap;
	}
}
