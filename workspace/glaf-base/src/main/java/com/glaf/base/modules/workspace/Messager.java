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

import java.io.FileInputStream;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.freemarker.TemplateUtils;

public class Messager {

	private static final String CFG = "/conf/templates/message.xml";

	private static Messages messages = null;

	public static MessageTemplate convertMessage(String msgName,
			Map<String, Object> root) {
		MessageTemplate msgt = new MessageTemplate();
		try {

			String filename = SystemConfig.getConfigRootPath() + CFG;

			if (messages == null) {
				messages = MessageBuilder.buildFromXML(new FileInputStream(
						filename));
			}
			MessageTemplate msgtemp = messages.getMessageTemplate(msgName);
			PropertyUtils.copyProperties(msgt, msgtemp);

			msgt.setTitle(TemplateUtils.process(root, msgt.getTitle()));
			msgt.setContent(TemplateUtils.process(root, msgt.getContent()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return msgt;
	}
}