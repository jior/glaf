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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.JbpmContext;
import org.jpage.core.cache.CacheFactory;
import org.jpage.core.query.paging.Page;
import org.jpage.jbpm.bytes.DataByteArray;
import org.jpage.jbpm.dao.ProcessDAO;
import org.jpage.jbpm.model.MessageInstance;
import org.jpage.jbpm.model.MessageTemplate;
import org.jpage.jbpm.persistence.PersistenceDAO;
import org.jpage.jbpm.util.MessageType;
import org.jpage.persistence.Executor;
import org.jpage.util.DateTools;
import org.jpage.util.UUID32;

public class MessageManagerImpl implements MessageManager {

 

	private ProcessDAO processDAO;

	private PersistenceDAO persistenceDAO;

	public MessageManagerImpl() {

	}

	public PersistenceDAO getPersistenceDAO() {
		return persistenceDAO;
	}

	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	public ProcessDAO getProcessDAO() {
		return processDAO;
	}

	public void setProcessDAO(ProcessDAO processDAO) {
		this.processDAO = processDAO;
	}

	/**
	 * 保持消息模板
	 * 
	 * @param MessageTemplate
	 */
	public void save(JbpmContext jbpmContext, MessageTemplate template) {
		if (StringUtils.isNotBlank(template.getTemplateId())) {
			template.setModifyDate(new Date());
			persistenceDAO.update(jbpmContext, template);
		} else {
			template.setCreateDate(new Date());
			template.setModifyDate(new Date());
			template.setTemplateId(UUID32.getUUID());
			persistenceDAO.save(jbpmContext, template);
		}

		if (template.getBytes() != null) {
			byte[] bytes = template.getBytes();
			DataByteArray ba = new DataByteArray(bytes);
			ba.setActorId(template.getActorId());
			ba.setCreateDate(new Date());
			ba.setName(template.getTemplateId());
			ba.setObjectId("templateId");
			ba.setObjectValue(template.getTemplateId());
			ba.setVersionNo(System.currentTimeMillis());
			persistenceDAO.save(jbpmContext, ba);
		}
	}

	/**
	 * 获取消息模板
	 * 
	 * @param jbpmContext
	 * @param templateId
	 * @return
	 */
	public MessageTemplate getMessageTemplate(JbpmContext jbpmContext,
			String templateId) {
		MessageTemplate template = (MessageTemplate) persistenceDAO
				.getPersistObject(jbpmContext, MessageTemplate.class,
						templateId);
		if (template != null) {
			Executor executor = new Executor();
			executor
					.setQuery(" from org.jpage.jbpm.bytes.DataByteArray as a where a.objectValue = :objectValue order by a.versionNo desc  ");
			Map params = new HashMap();
			params.put("objectValue", templateId);
			executor.setParams(params);
			List rows = persistenceDAO.query(jbpmContext, executor);
			if (rows != null && rows.size() > 0) {
				for (int i = 0; i < rows.size(); i++) {
					DataByteArray ba = (DataByteArray) rows.get(i);
					if (ba.getByteBlocks() != null) {
						byte[] bytes = ba.getBytes();
						if (bytes != null) {
							template.setBytes(bytes);
							break;
						}
					}
				}
			}
		}
		return template;
	}

	/**
	 * 获取消息模板
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getMessageTemplates(JbpmContext jbpmContext, Map paramMap) {
		Map params = new HashMap();
		StringBuffer buffer = new StringBuffer();
		buffer
				.append(" from org.jpage.jbpm.model.MessageTemplate as a where 1=1 ");

		if (paramMap.get("processName") != null) {
			params.put("processName", paramMap.get("processName"));
			buffer.append(" and  a.processName = :processName ");
		}

		if (paramMap.get("taskName") != null) {
			params.put("taskName", paramMap.get("taskName"));
			buffer.append(" and  a.taskName = :taskName ");
		}

		if (paramMap.get("templateType") != null) {
			params.put("templateType", paramMap.get("templateType"));
			buffer.append(" and  a.templateType = :templateType ");
		}

		if (paramMap.get("objectId") != null) {
			params.put("objectId", paramMap.get("objectId"));
			buffer.append(" and  a.objectId = :objectId ");
		}

		if (paramMap.get("objectValue") != null) {
			params.put("objectValue", paramMap.get("objectValue"));
			buffer.append(" and  a.objectValue = :objectValue ");
		}

		buffer.append(" order by a.versionNo desc ");

		Executor executor = new Executor();
		executor.setParams(params);
		executor.setQuery(buffer.toString());

		return persistenceDAO.query(jbpmContext, executor);
	}

	/**
	 * 发送消息
	 * 
	 * @param processInstanceId
	 *            流程实例编号
	 * @param messageInstances
	 *            消息实例
	 * 
	 * @param messageMap
	 *            消息Map。 key和value都是字符串
	 */
	public void sendMessage(JbpmContext jbpmContext, String processInstanceId,
			Collection messageInstances) {
		if (messageInstances != null && messageInstances.size() > 0) {
			List rows = new ArrayList();
			Iterator iterator = messageInstances.iterator();
			while (iterator.hasNext()) {
				MessageInstance messageInstance = (MessageInstance) iterator
						.next();
				messageInstance.setMessageId(UUID32.getUUID());
				messageInstance.setReceiverType(0);
				messageInstance.setCreateDate(new Date());
				messageInstance.setJobDate(new Date(System.currentTimeMillis()
						+ org.jpage.util.DateTools.DAY));
				messageInstance.setDeleteFlag(0);
				messageInstance.setStatus(MessageType.NEW);
				messageInstance.setVersionNo(System.currentTimeMillis());
				rows.add(messageInstance);
			}
			processDAO.deleteMessageInstances(jbpmContext, processInstanceId);
			persistenceDAO.saveAll(jbpmContext, rows);
			CacheFactory.put(processInstanceId, new Integer(rows.size()));
		}
	}

	/**
	 * 根据条件获取消息实例
	 * 
	 * @param jbpmContext
	 * @param params
	 * @return
	 */
	public List getMessageInstances(JbpmContext jbpmContext, Map paramMap) {
		Map params = new HashMap();
		StringBuffer buffer = new StringBuffer();
		buffer
				.append(" from org.jpage.jbpm.model.MessageInstance as a where 1=1 ");

		if (paramMap.get("messageType") != null) {
			params.put("messageType", paramMap.get("messageType"));
			buffer.append(" and  a.messageType = :messageType ");
		}

		if (paramMap.get("receiverId") != null) {
			params.put("receiverId", paramMap.get("receiverId"));
			buffer.append(" and  a.receiverId = :receiverId ");
		}

		if (paramMap.get("processInstanceId") != null) {
			params.put("processInstanceId", paramMap.get("processInstanceId"));
			buffer.append(" and  a.processInstanceId = :processInstanceId ");
		}

		if (paramMap.get("taskName") != null) {
			params.put("taskName", paramMap.get("taskName"));
			buffer.append(" and  a.taskName = :taskName ");
		}

		if (paramMap.get("objectId") != null) {
			params.put("objectId", paramMap.get("objectId"));
			buffer.append(" and  a.objectId = :objectId ");
		}

		if (paramMap.get("objectValue") != null) {
			params.put("objectValue", paramMap.get("objectValue"));
			buffer.append(" and  a.objectValue = :objectValue ");
		}

		if (paramMap.get("createDate_start") != null) {
			Object obj = paramMap.get("createDate_start");
			if (obj instanceof java.util.Date) {
				params
						.put("createDate_start", paramMap
								.get("createDate_start"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("createDate_start", date);
			}
			buffer.append(" and ( a.createDate >= :createDate_start )");
		}

		if (paramMap.get("createDate_end") != null) {
			Object obj = paramMap.get("createDate_end");
			if (obj instanceof java.util.Date) {
				params.put("createDate_end", paramMap.get("createDate_end"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("createDate_end", date);
			}
			buffer.append(" and ( a.createDate <= :createDate_end )");
		}

		if (paramMap.get("status") != null) {
			Object obj = paramMap.get("status");
			if (obj instanceof java.lang.Integer) {
				params.put("status", paramMap.get("status"));
			} else {
				String status = (String) obj;
				params.put("status", new Integer(status));
			}
			buffer.append(" and ( a.status = :status )");
		}

		if (paramMap.get("deleteFlag") != null) {
			Object obj = paramMap.get("deleteFlag");
			if (obj instanceof java.lang.Integer) {
				params.put("deleteFlag", paramMap.get("deleteFlag"));
			} else {
				String status = (String) obj;
				params.put("deleteFlag", new Integer(status));
			}
			buffer.append(" and ( a.deleteFlag = :deleteFlag )");
		}

		buffer.append(" order by a.versionNo desc ");

		Executor executor = new Executor();
		executor.setParams(params);
		executor.setQuery(buffer.toString());

		return persistenceDAO.query(jbpmContext, executor);
	}

	/**
	 * 获取一页消息实例对象
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Page getPageMessageInstances(JbpmContext jbpmContext,
			int currPageNo, int pageSize, Map paramMap) {
		Map params = new HashMap();
		StringBuffer buffer = new StringBuffer();
		buffer
				.append("  from org.jpage.jbpm.model.MessageInstance as a where 1=1 ");

		if (paramMap.get("messageType") != null) {
			params.put("messageType", paramMap.get("messageType"));
			buffer.append(" and  a.messageType = :messageType ");
		}

		if (paramMap.get("receiverId") != null) {
			params.put("receiverId", paramMap.get("receiverId"));
			buffer.append(" and  a.receiverId = :receiverId ");
		}

		if (paramMap.get("processInstanceId") != null) {
			params.put("processInstanceId", paramMap.get("processInstanceId"));
			buffer.append(" and  a.processInstanceId = :processInstanceId ");
		}

		if (paramMap.get("taskName") != null) {
			params.put("taskName", paramMap.get("taskName"));
			buffer.append(" and  a.taskName = :taskName ");
		}

		if (paramMap.get("objectId") != null) {
			params.put("objectId", paramMap.get("objectId"));
			buffer.append(" and  a.objectId = :objectId ");
		}

		if (paramMap.get("objectValue") != null) {
			params.put("objectValue", paramMap.get("objectValue"));
			buffer.append(" and  a.objectValue = :objectValue ");
		}

		if (paramMap.get("createDate_start") != null) {
			Object obj = paramMap.get("createDate_start");
			if (obj instanceof java.util.Date) {
				params
						.put("createDate_start", paramMap
								.get("createDate_start"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("createDate_start", date);
			}
			buffer.append(" and ( a.createDate >= :createDate_start )");
		}

		if (paramMap.get("createDate_end") != null) {
			Object obj = paramMap.get("createDate_end");
			if (obj instanceof java.util.Date) {
				params.put("createDate_end", paramMap.get("createDate_end"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("createDate_end", date);
			}
			buffer.append(" and ( a.createDate <= :createDate_end )");
		}

		if (paramMap.get("status") != null) {
			Object obj = paramMap.get("status");
			if (obj instanceof java.lang.Integer) {
				params.put("status", paramMap.get("status"));
			} else {
				String status = (String) obj;
				params.put("status", new Integer(status));
			}
			buffer.append(" and ( a.status = :status )");
		}

		if (paramMap.get("deleteFlag") != null) {
			Object obj = paramMap.get("deleteFlag");
			if (obj instanceof java.lang.Integer) {
				params.put("deleteFlag", paramMap.get("deleteFlag"));
			} else {
				String status = (String) obj;
				params.put("deleteFlag", new Integer(status));
			}
			buffer.append(" and ( a.deleteFlag = :deleteFlag )");
		}

		buffer.append(" order by a.versionNo desc ");

		Executor countExecutor = new Executor();
		Executor queryExecutor = new Executor();

		countExecutor.setQuery(" select count(distinct a.messageId) "
				+ buffer.toString());
		queryExecutor.setQuery(" select distinct a " + buffer.toString());
		countExecutor.setParams(params);
		queryExecutor.setParams(params);

		return persistenceDAO.getPage(jbpmContext, currPageNo, pageSize,
				countExecutor, queryExecutor);

	}

}
