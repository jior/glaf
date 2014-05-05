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

import org.springframework.transaction.annotation.Transactional;

import com.glaf.mail.domain.*;
import com.glaf.mail.query.*;

@Transactional(readOnly = true)
public interface IMailTaskService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	MailTask getMailTask(String taskId);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	MailTask getMailTaskWithAccounts(String taskId);

	List<String> getAccountIds(String taskId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getMailTaskCountByQueryCriteria(MailTaskQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<MailTask> getMailTasksByQueryCriteria(int start, int pageSize,
			MailTaskQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<MailTask> list(MailTaskQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(MailTask mailTask);

	/**
	 * 保存某个任务的发送帐户
	 * 
	 * @param taskId
	 * @param accountIds
	 */
	@Transactional
	void saveAccounts(String taskId, List<String> accountIds);

}