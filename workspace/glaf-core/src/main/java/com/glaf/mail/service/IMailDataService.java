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

import com.glaf.mail.domain.MailCount;
import com.glaf.mail.domain.MailItem;
import com.glaf.mail.query.MailItemQuery;

public interface IMailDataService   {

	/**
	 * ��ȡ�ʼ��ʻ�����״������
	 * 
	 * @param query
	 * @return
	 */
	List<MailCount> getMailAccountReceiveStatusCount(MailItemQuery query);

	/**
	 * ��ȡ�ʼ��ʻ�����״������
	 * 
	 * @param query
	 * @return
	 */
	List<MailCount> getMailAccountSendStatusCount(MailItemQuery query);

	/**
	 * ��ȡĳ��������ʼ��б�
	 * 
	 * @param query
	 * @return
	 */
	List<MailItem> getMailItems(MailItemQuery query);

	/**
	 * ��ȡ�ʼ�
	 * 
	 * @param taskId
	 * @param itemId
	 * @return
	 */
	MailItem getMailItem(String taskId, String itemId);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getMailCount(MailItemQuery query);

	/**
	 * ��ȡ�ʼ�����״������
	 * 
	 * @param query
	 * @return
	 */
	List<MailCount> getMailReceiveStatusCount(MailItemQuery query);

	/**
	 * ��ȡ�ʼ�����״������
	 * 
	 * @param query
	 * @return
	 */
	List<MailCount> getMailSendStatusCount(MailItemQuery query);

	/**
	 * ����ĳ��������ʼ�
	 * 
	 * @param taskId
	 * @param mailAddresses
	 */

	void saveMails(String taskId, List<String> mailAddresses);

	/**
	 * �����ʼ���Ϣ
	 * 
	 * @param taskId
	 * @param item
	 */

	void updateMail(String taskId, MailItem item);

}