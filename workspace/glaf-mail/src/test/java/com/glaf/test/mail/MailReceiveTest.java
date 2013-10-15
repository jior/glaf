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

package com.glaf.test.mail;

import java.util.List;

import org.junit.Test;
import com.glaf.test.AbstractTest;
import com.glaf.core.util.Paging;
import com.glaf.mail.Mail;
import com.glaf.mail.business.ReceiveMailBean;
import com.glaf.mail.query.MailQuery;
import com.glaf.mail.service.IMailService;

public class MailReceiveTest extends AbstractTest {

	protected IMailService mailService;

	@Test
	public void testList() {
		mailService = super.getBean("mailService");
		ReceiveMailBean rm = new ReceiveMailBean();
		rm.receiveMail("pop3.163.com", 110, "cinsoft2013@163.com",
				"cinsoft@2013", "/temp");
		MailQuery query = new MailQuery();
		Paging page = mailService.getPage(query);
		List<Object> rows = page.getRows();
		for (Object row : rows) {
			Mail mail = (Mail) row;
			logger.debug(mail.toJsonObject().toJSONString());
		}
	}

}
