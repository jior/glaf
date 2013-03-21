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

package com.glaf.base.modules.workspace;

import java.util.HashMap;
import java.util.Map;

public class Messages {

	private Map<String, MessageTemplate> messageTemplates = new HashMap<String, MessageTemplate>();

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

	public Map<String, MessageTemplate> getMessageTemplates() {
		return this.messageTemplates;
	}

	public void setMessageTemplates(Map<String, MessageTemplate> messageTemplates) {
		this.messageTemplates = messageTemplates;
	}

}