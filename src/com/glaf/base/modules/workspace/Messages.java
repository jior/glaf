package com.glaf.base.modules.workspace;

import java.util.HashMap;
import java.util.Map;

public class Messages {

	private Map messageTemplates = new HashMap();

	public MessageTemplate getMessageTemplate(String name) {
		if (messageTemplates.get(name) == null) {
			return null;
		}
		MessageTemplate msgt = (MessageTemplate) messageTemplates.get(name);
		return msgt;
	}

	public void addMessageTemplate(MessageTemplate msgt) {
		messageTemplates.put(msgt.getName(), msgt);
	}

	public Map getMessageTemplates() {
		return this.messageTemplates;
	}

	public void setMessageTemplates(Map messageTemplates) {
		this.messageTemplates = messageTemplates;
	}

}
