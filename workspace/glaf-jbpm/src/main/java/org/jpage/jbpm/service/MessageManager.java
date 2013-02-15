/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
