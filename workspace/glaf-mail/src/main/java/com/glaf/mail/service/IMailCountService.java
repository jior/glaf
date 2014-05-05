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

import com.glaf.mail.domain.*;

@Transactional(readOnly = true)
public interface IMailCountService {

	/**
	 * 根据任务编号及类型获取汇总信息
	 * 
	 * @param taskId
	 * @param type
	 * @return
	 */
	List<MailCount> getMailCountList(String taskId, String type);

	/**
	 * 
	 * @param taskId
	 * @param type
	 * @param rows
	 */
	@Transactional
	void save(String taskId, String type, List<MailCount> rows);

}