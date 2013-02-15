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


package org.jpage.jbpm.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jbpm.JbpmContext;
import org.jpage.core.query.paging.Page;
import org.jpage.jbpm.model.MessageTemplate;

public interface MessageManager {

	/**
	 * ������Ϣģ��
	 * 
	 * @param messageTemplate
	 */
	public void save(JbpmContext jbpmContext, MessageTemplate messageTemplate);

	/**
	 * ��ȡ��Ϣģ��
	 * 
	 * @param jbpmContext
	 * @param templateId
	 * @return
	 */
	public MessageTemplate getMessageTemplate(JbpmContext jbpmContext,
			String templateId);

	/**
	 * ��ȡ��Ϣģ��
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getMessageTemplates(JbpmContext jbpmContext, Map paramMap);

	/**
	 * ������Ϣ
	 * 
	 * @param processInstanceId
	 *            ����ʵ�����
	 * @param messageInstances
	 *            ��Ϣʵ��
	 * 
	 * @param messageMap
	 *            ��ϢMap�� key��value�����ַ���
	 */
	public void sendMessage(JbpmContext jbpmContext, String processInstanceId,
			Collection messageInstances);

	/**
	 * ����������ȡ��Ϣʵ��
	 * 
	 * @param jbpmContext
	 * @param params
	 * @return
	 */
	public List getMessageInstances(JbpmContext jbpmContext, Map params);
	
	
	/**
	 * ��ȡһҳ��Ϣʵ������
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Page getPageMessageInstances(JbpmContext jbpmContext, int currPageNo,
			int pageSize, Map paramMap);
	
}
