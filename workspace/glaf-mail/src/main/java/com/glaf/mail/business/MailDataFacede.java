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

package com.glaf.mail.business;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.mail.domain.MailCount;
import com.glaf.mail.domain.MailItem;
import com.glaf.mail.domain.MailStorage;
import com.glaf.mail.query.MailItemQuery;

public interface MailDataFacede {

	/**
	 * 添加存储邮件信息的数据表
	 * 
	 * @param dataTable
	 *            表名称
	 * @param storage
	 *            存储类型
	 */
	void addDataTable(String dataTable, MailStorage strorage);

	/**
	 * 执行统计
	 */
	void executeCount();

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
	 * 根据条件获取邮件总数
	 * 
	 * @param query
	 * @return
	 */
	int getMailCount(MailItemQuery query);

	/**
	 * 获取邮件信息
	 * 
	 * @param taskId
	 * @param itemId
	 * @return
	 */
	MailItem getMailItem(String taskId, String itemId);

	/**
	 * 获取某个任务的邮件列表
	 * 
	 * @param query
	 * @return
	 */
	List<MailItem> getMailItems(MailItemQuery query);

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
	 * 将所有任务加到调度
	 */
	void scheduleAllTasks();

	/**
	 * 将所有任务的指定待发送邮件记录加到调度
	 */
	void scheduleMailItems();

	/**
	 * 发送邮件
	 * 
	 * @param taskId
	 */
	void sendTaskMails(String taskId);

	/**
	 * 发送邮件
	 * 
	 * @param taskId
	 * @param itemId
	 */
	void sendMail(String taskId, String itemId);

	/**
	 * 更新邮件信息
	 * 
	 * @param taskId
	 * @param item
	 */
	@Transactional
	void updateMail(String taskId, MailItem item);

}