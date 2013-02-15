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


package org.jpage.core.mail;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jpage.config.Configurator;
import org.jpage.core.mail.model.MailTemplate;
import org.jpage.util.DateTools;
import org.jpage.util.Dom4jToolkit;

public class MailHelper {

	public String getCallbackHref(String messageId) {
		String callbackUrl = Configurator.getProperty("mail.callback.url");
		return this.getCallbackHref(callbackUrl, messageId);
	}

	public String getCallbackHref(MailTemplate template, String messageId) {
		if (template != null
				&& StringUtils.isNotBlank(template.getCallbackUrl())) {
			return this.getCallbackHref(template.getCallbackUrl(), messageId);
		}
		return this.getCallbackHref(messageId);
	}

	public String getCallbackHref(String callbackUrl, String messageId) {
		return callbackUrl + "?messageId=" + messageId;
	}

	public String getCallbackScript(String messageId) {
		String callbackUrl = Configurator.getProperty("mail.callback.url");
		return this.getCallbackScript(callbackUrl, messageId);
	}

	public String getCallbackScript(MailTemplate template, String messageId) {
		if (template != null
				&& StringUtils.isNotBlank(template.getCallbackUrl())) {
			return this.getCallbackScript(template.getCallbackUrl(), messageId);
		}
		return this.getCallbackScript(messageId);
	}

	public String getCallbackScript(String callbackUrl, String messageId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n<script language=\"javascript\">\n");
		buffer.append("    function receiveMailCallback(){\n");
		buffer.append("       var link = \"").append(callbackUrl).append(
				"?messageId=").append(messageId).append("\";\n");
		buffer
				.append("      var iframe = document.getElementById(\"iframe\");\n");
		buffer.append("      if(iframe != null){\n");
		buffer.append("          iframe.src=link;\n");
		buffer.append("      }\n");
		buffer.append("    }\n");
		buffer.append("</script>\n");

		buffer.append("<body onload=\"javascript:receiveMailCallback();\">\n");
		buffer
				.append("<iframe id=\"iframe\" name=\"iframe\" src=\"\" width=\"0\" height=\"0\"/>\n");

		buffer.append("<script>\n");
		buffer.append("    window.open(\"").append(callbackUrl).append(
				"?messageId=").append(messageId).append("\");\n");
		buffer.append("</script>\n");
		return buffer.toString();
	}

	public String embedCallbackScript(String html, String href) {
		StringBuffer buffer = new StringBuffer();
		String temp = html.toLowerCase();
		int fromIndex = temp.indexOf("<body");
		if (fromIndex != -1) {
			int endIndex = temp.indexOf(">", fromIndex);
			buffer.append(html.substring(0, endIndex + 1));
			buffer.append("\n<img src=\"").append(href).append(
					"\" width=\"1\" height=\"1\"/>");
			buffer.append(html.substring(endIndex + 1, html.length()));
		} else {
			buffer.append(html);
			buffer.append("\n<img src=\"").append(href).append(
					"\" width=\"1\" height=\"1\"/>");
		}
		return buffer.toString();
	}

	public String toXml(Map attributes) {
		Document doc = DocumentHelper.createDocument();
		Element elements = doc.addElement("elements");
		Iterator iterator = attributes.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Object value = attributes.get(key);
			if (key == null || value == null) {
				continue;
			}
			String content = null;
			if (value instanceof java.util.Date) {
				java.util.Date date = (java.util.Date) value;
				content = DateTools.getDateTime(date);
			} else {
				content = value.toString();
			}
			if (content != null) {
				Element element = elements.addElement("element");
				element.addAttribute("name", key);
				element.addAttribute("value", content);
			}
		}
		return new String(Dom4jToolkit.getBytesFromPrettyDocument(doc));
	}

	public Map getAttributes(String attribute) {
		Map dataMap = new HashMap();
		StringReader stringReader = new StringReader(attribute);
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(stringReader);
			Element root = doc.getRootElement();
			List elements = root.elements("element");
			if (elements != null && elements.size() > 0) {
				Iterator iterator = elements.iterator();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					String name = element.attributeValue("name");
					String value = element.attributeValue("value");
					if (StringUtils.isBlank(value)) {
						value = element.getStringValue();
					}
					dataMap.put(name, value);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataMap;
	}

}
