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

package com.glaf.base.modules.todo.service;

import java.io.*;
import java.net.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import com.glaf.core.context.ContextFactory;

public class TodoQuartzJob {

	private final static Log logger = LogFactory.getLog(TodoQuartzJob.class);

	private String sendMessageServiceUrl;

	public String getSendMessageServiceUrl() {
		return sendMessageServiceUrl;
	}

	public void setSendMessageServiceUrl(String sendMessageServiceUrl) {
		this.sendMessageServiceUrl = sendMessageServiceUrl;
	}

	public void createTasksFromSQL() {
		try {
			TodoJobBean bean = ContextFactory.getBean("todoJobBean");
			if (bean != null) {
				bean.createTasksFromSQL();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	public void sendMessageToAllUsers() {
		try {
			TodoJobBean bean = ContextFactory.getBean("todoJobBean");
			if (bean != null) {
				bean.sendMessageToAllUsers();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	public void sendMessageToAllUsersViaJSP() {
		try {
			if (sendMessageServiceUrl != null) {
				URL url = new URL(sendMessageServiceUrl);
				URLConnection con = url.openConnection();
				con.setDoOutput(true);
				InputStream in = con.getInputStream();
				in = new BufferedInputStream(in);
				Reader r = new InputStreamReader(in);
				int c;
				logger.debug("==============Beging====================");

				while ((c = r.read()) != -1) {
					logger.debug((char) c);
				}
				in.close();

				logger.debug("===============End======================");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

}