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
import java.util.Set;

import org.jbpm.JbpmContext;
import org.jpage.actor.User;
import org.jpage.persistence.Executor;

public interface ActorManager {

	/**
	 * ��ȡ�û�
	 * 
	 * @param actorId
	 * @return
	 */
	public User getUser(JbpmContext jbpmContext, String actorId);

	/**
	 * ����������ȡ������
	 * 
	 * @param queryExecutor
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * ��ȡ�û�Map������key���û��ʺţ�value��org.jpage.actor.User����
	 * 
	 * @return
	 */
	public Map getUserMap(JbpmContext jbpmContext);

	/**
	 * ����ĳ����������Ĳ�����
	 * 
	 * @param roleId
	 * @param roleUsers
	 */
	public void saveActors(JbpmContext jbpmContext, String roleId,
			List roleUsers);

	/**
	 * ���������
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param agents
	 */
	public void saveAgents(JbpmContext jbpmContext, String actorId,
			List agents, String objectValue);

	/**
	 * ��ȡĳ������ĳ������Ĳ�����
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, String roleId);

	/**
	 * ��ȡĳ������ĳЩ��ɫ���û�ID
	 * 
	 * @param roleIds
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Collection roleIds);

	/**
	 * ��ȡĳ������ĳ������Ĳ�����
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActorIds(JbpmContext jbpmContext, String roleId);

	/**
	 * ��ȡĳ���û��Ĵ������б�
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, String actorId);

	/**
	 * ��ȡĳ���û���ĳ�����̵Ĵ������б�
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param processName
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, String actorId,
			String processName);

	/**
	 * ���ݲ�ѯ������ȡ������
	 * 
	 * @param jbpmContext
	 * @param params
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Map params);

	/**
	 * ���ݲ�ѯ������ȡ������
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getAgents(JbpmContext jbpmContext, Map paramMap);

	/**
	 * ���ݲ�ѯ������ȡ������
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, Map paramMap);

 

}
