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

package com.glaf.mail.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.mail.domain.MailCount;
import com.glaf.mail.domain.MailItem;
import com.glaf.mail.query.MailItemQuery;

@Transactional(readOnly = true)
public interface IMailDataService {

	/**
	 * 获取邮件帐户接收状况汇总
	 * 
	 * @param query
	 * @return
	 */
	List<MailCount> getMailAccountReceiveStatusCount(MailItemQuery query);

	/**
	 * 获取邮件帐户发送状况汇总
	 * 
	 * @param query
	 * @return
	 */
	List<MailCount> getMailAccountSendStatusCount(MailItemQuery query);

	/**
	 * 获取某个任务的邮件列表
	 * 
	 * @param query
	 * @return
	 */
	List<MailItem> getMailItems(MailItemQuery query);

	/**
	 * 获取邮件
	 * 
	 * @param taskId
	 * @param itemId
	 * @return
	 */
	MailItem getMailItem(String taskId, String itemId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getMailCount(MailItemQuery query);

	/**
	 * 获取邮件接收状况汇总
	 * 
	 * @param query
	 * @return
	 */
	List<MailCount> getMailReceiveStatusCount(MailItemQuery query);

	/**
	 * 获取邮件发送状况汇总
	 * 
	 * @param query
	 * @return
	 */
	List<MailCount> getMailSendStatusCount(MailItemQuery query);

	/**
	 * 保存某个任务的邮件
	 * 
	 * @param taskId
	 * @param mailAddresses
	 */
	@Transactional
	void saveMails(String taskId, List<String> mailAddresses);

	/**
	 * 更新邮件信息
	 * 
	 * @param taskId
	 * @param item
	 */
	@Transactional
	void updateMail(String taskId, MailItem item);

}