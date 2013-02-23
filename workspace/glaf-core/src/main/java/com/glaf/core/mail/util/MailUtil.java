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

package com.glaf.core.mail.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.mail.MailMessage;
import com.glaf.core.mail.MailThread;
import com.glaf.core.util.LogUtils;

public class MailUtil {
	protected final static Log logger = LogFactory.getLog(MailUtil.class);

	public static void send(String to, String subject, String content) {
		MailMessage mailMessage = new MailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setContent(content);

		logger.debug("subject:" + subject);
		logger.debug(content);

		if (LogUtils.isDebug()) {
			logger.debug(content);
		}
		try {
			MailThread thread = new MailThread(mailMessage);
			ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) ContextFactory
					.getBean("threadPoolTaskExecutor");
			executor.execute(thread);
		} catch (Exception ex) {
			logger.error("Send mail error! \n" + ex.getMessage());
		}
	}

}