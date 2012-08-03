package com.glaf.base.modules.workspace;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

public class MessageBuilder {
	private static final Log logger = LogFactory.getLog(MessageBuilder.class);
	
	public static Messages buildFromXML(InputStream xmlStream)
			throws IOException, SAXException {
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("template",
				"com.glaf.base.modules.workspace.Messages");
		digester.addObjectCreate("template/message",
				"com.glaf.base.modules.workspace.MessageTemplate");
		digester.addSetProperties("template/message");
		
		digester.addBeanPropertySetter("template/message/sysType");

		digester.addCallMethod("template/message/title", "setTitle", 1);
		digester.addCallParam("template/message/title", 0);
		
		digester.addCallMethod("template/message/content", "setContent", 1);
		digester.addCallParam("template/message/content", 0);
		digester.addSetNext("template/message", "addMessageTemplate");
		return (Messages) digester.parse(xmlStream);

	}
}
