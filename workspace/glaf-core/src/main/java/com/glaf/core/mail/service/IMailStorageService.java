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

import java.util.*;

import com.glaf.core.mail.domain.*;
import com.glaf.core.mail.query.*;

public interface IMailStorageService   {

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

	int count();

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<MailStorage> list(MailStorageQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getMailStorageCountByQueryCriteria(MailStorageQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<MailStorage> getMailStoragesByQueryCriteria(int start, int pageSize,
			MailStorageQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	MailStorage getMailStorage(String id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */

	void save(MailStorage mailStorage);

}