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

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

public class MessageBuilder {
	protected static final Log logger = LogFactory.getLog(MessageBuilder.class);

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