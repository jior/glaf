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

package com.glaf.core.mail.service;

import java.util.List;
import java.util.Map;

import com.glaf.core.query.MailQuery;
import com.glaf.core.mail.Mail;
import com.glaf.core.util.Paging;

public interface IMailService {

	void deleteMail(String mailId);

	Mail getMail(String mailId);

	List<Mail> getMailList(String resourceId);

	Paging getPage(MailQuery query);

	void saveMail(Mail mail);

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 * @return
	 */

	boolean send(Mail mail);

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 *            邮件对象
	 * @param dataMap
	 *            参数集
	 * @return
	 */

	boolean send(Mail mail, Map<String, Object> dataMap);

	void updateMail(Mail mail);

}