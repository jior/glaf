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

import java.util.*;

import com.glaf.mail.domain.*;
import com.glaf.mail.query.*;

public interface IMailTaskService   {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */

	void deleteById(String id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */

	void deleteByIds(List<String> rowIds);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	MailTask getMailTask(String taskId);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	MailTask getMailTaskWithAccounts(String taskId);

	List<String> getAccountIds(String taskId);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getMailTaskCountByQueryCriteria(MailTaskQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<MailTask> getMailTasksByQueryCriteria(int start, int pageSize,
			MailTaskQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<MailTask> list(MailTaskQuery query);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */

	void save(MailTask mailTask);

	/**
	 * ����ĳ������ķ����ʻ�
	 * 
	 * @param taskId
	 * @param accountIds
	 */

	void saveAccounts(String taskId, List<String> accountIds);

}