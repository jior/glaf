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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.xml.sax.SAXException;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.freemarker.TemplateUtils;

public class Messager {

	private static final String CFG = "/conf/templates/message.xml";

	private static Messages messages = null;

	public static MessageTemplate convertMessage(String msgName, Map root) {
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

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return msgt;
	}

	public static void main(String[] args) {
		Map root = new HashMap();

		Map purchase = new HashMap();
		purchase.put("name", "消息");
		purchase.put("purchaseNo", "WQ12556");

		Map sender = new HashMap();
		sender.put("name", "张三");
		sender.put("account", "012457");

		root.put("sender", sender);
		root.put("purchase", purchase);
		root.put("date", new Date());
		root.put("reason", "reason");

		MessageTemplate msgt = convertMessage("disable-purchase", root);
		System.out.println("=====" + msgt.getTitle());
		System.out.println("=====" + msgt.getContent());

		// MessageTemplate msgt2 = convertMessage("purchase-check", root);
		// System.out.println("=====" + msgt2.getTitle());
		// System.out.println("=====" + msgt2.getContent());

		Map root2 = new HashMap();

		Map purchase2 = new HashMap();
		purchase2.put("name", "消息!!!!");
		purchase2.put("purchaseNo", "WQ12556!!!!");

		Map sender2 = new HashMap();
		sender2.put("name", "张三!!!!");
		sender2.put("account", "012457!!!");

		root2.put("sender", sender2);
		root2.put("purchase", purchase2);
		root2.put("date", new Date());
		root2.put("reason", "reason!!!!!");
		MessageTemplate msgt3 = convertMessage("disable-purchase", root2);
		System.out.println("=====" + msgt3.getTitle());
		System.out.println("=====" + msgt3.getContent());
	}
}