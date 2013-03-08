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
package com.glaf.base.modules.workspace.service.mybatis;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.util.PageResult;
import com.glaf.core.dao.*;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.workspace.mapper.*;
import com.glaf.base.modules.workspace.model.*;
import com.glaf.base.modules.workspace.query.*;
import com.glaf.base.modules.workspace.service.*;

@Service("messageService")
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected LongIdGenerator idGenerator;

	protected PersistenceDAO persistenceDAO;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MessageMapper messageMapper;

	protected SysUserService sysUserService;

	public MessageServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			messageMapper.deleteMessageById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			MessageQuery query = new MessageQuery();
			query.rowIds(rowIds);
			messageMapper.deleteMessages(query);
		}
	}

	public int count(MessageQuery query) {
		query.ensureInitialized();
		return messageMapper.getMessageCount(query);
	}

	public List<Message> list(MessageQuery query) {
		query.ensureInitialized();
		List<Message> list = messageMapper.getMessages(query);
		return list;
	}

	public int getMessageCountByQueryCriteria(MessageQuery query) {
		return messageMapper.getMessageCount(query);
	}

	public List<Message> getMessagesByQueryCriteria(int start, int pageSize,
			MessageQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Message> rows = sqlSessionTemplate.selectList("getMessages",
				query, rowBounds);
		return rows;
	}

	public Message getMessage(Long id) {
		if (id == null) {
			return null;
		}
		Message message = messageMapper.getMessageById(id);
		return message;
	}

	@Transactional
	public void save(Message message) {
		if (message.getId() == 0L) {
			message.setId(idGenerator.getNextId());
			// message.setCreateDate(new Date());
			messageMapper.insertMessage(message);
		} else {
			messageMapper.updateMessage(message);
		}
	}

	@Resource
	@Qualifier("myBatisDbLongIdGenerator")
	public void setLongIdGenerator(LongIdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setMessageMapper(MessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}

	@Resource
	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public boolean create(Message bean) {
		this.save(bean);
		return true;
	}

	public boolean saveOrUpdate(Message bean) {
		this.save(bean);
		return true;
	}

	public boolean update(Message bean) {
		this.save(bean);
		return true;
	}

	public boolean delete(Message bean) {
		this.deleteById(bean.getId());
		return true;
	}

	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@SuppressWarnings("rawtypes")
	public boolean deleteAll(Collection c) {
		for (Object object : c) {
			if (object instanceof Long) {
				this.deleteById((Long) object);
			}
			if (object instanceof Message) {
				Message bean = (Message) object;
				this.deleteById(bean.getId());
			}
		}
		return true;
	}

	public Message find(long id) {
		return this.getMessage(id);
	}

	public boolean saveSendMessage(Message message, String[] recverIds) {
		boolean rst = true;

		// 收件人列表
		StringBuffer recverList = new StringBuffer("");

		// 发送消息给收件人
		for (int i = 0; i < recverIds.length; i++) {
			if (recverIds[i] == null || recverIds[i].trim().length() == 0) {
				continue;
			}

			Message newMessage = new Message();
			try {
				PropertyUtils.copyProperties(newMessage, message);
			} catch (Exception ex) {
				org.springframework.beans.BeanUtils.copyProperties(newMessage,
						message);
			}

			SysUser recver = sysUserService.findById(Long
					.parseLong(recverIds[i]));
			newMessage.setRecver(recver);

			recverList.append(recver.getName()).append(",");

			if (!saveOrUpdate(newMessage)) {
				rst = false;
			}
		}

		// 发送消息给自己,保存在发件箱
		if (rst) {
			sendToSelf(message, recverList.toString());
		}

		return rst;
	}

	/**
	 * 发送消息给自己,保存在发件箱
	 * 
	 * @param message
	 * @param recverLists
	 * @return
	 */
	private boolean sendToSelf(Message message, String recverLists) {
		Message newMessage = new Message();
		try {
			PropertyUtils.copyProperties(newMessage, message);
		} catch (Exception ex) {
			org.springframework.beans.BeanUtils.copyProperties(newMessage,
					message);
		}

		newMessage.setCategory(1);// 发件箱
		newMessage.setRecver(message.getSender());

		if (recverLists.endsWith(",")) {
			recverLists = recverLists.substring(0, recverLists.length() - 1);
		}
		if (recverLists.length() > 2000) {
			recverLists = recverLists.substring(0, 2000);
		}
		newMessage.setRecverList(recverLists);
		return saveOrUpdate(newMessage);
	}

	public boolean saveSendMessageToDept(Message message, String[] recverIds) {
		boolean rst = true;

		// 收件部门列表
		StringBuffer recverList = new StringBuffer("");

		// 发送消息给收件人
		for (int i = 0; i < recverIds.length; i++) {
			if (recverIds[i] == null || recverIds[i].trim().length() == 0) {
				continue;
			}

			List<SysUser> list = sysUserService.getSysUserList(Integer
					.parseInt(recverIds[i]));

			if (list == null) {
				continue;
			}

			Iterator<SysUser> iter = list.iterator();
			int index = 0;
			while (iter.hasNext()) {
				SysUser recver = (SysUser) iter.next();
				if (index == 0) {// 取部门名称
					recverList.append(recver.getDepartment().getName()).append(
							",");
				}
				index++;

				Message newMessage = new Message();
				try {
					PropertyUtils.copyProperties(newMessage, message);
				} catch (Exception ex) {
					org.springframework.beans.BeanUtils.copyProperties(
							newMessage, message);
				}
				newMessage.setRecver(recver);

				if (!saveOrUpdate(newMessage)) {
					rst = false;
				}
			}

		}

		// 发送消息给自己,保存在发件箱
		if (rst) {
			sendToSelf(message, recverList.toString());
		}

		return rst;
	}

	public Message updateReadMessage(long id) {
		Message message = find(id);
		message.setReaded(1);// 设置已读
		update(message);
		return message;
	}

	@SuppressWarnings("rawtypes")
	public PageResult getMessageList(Map params, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		MessageQuery query = new MessageQuery();

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.ID desc");

		int start = pageSize * (pageNo - 1);
		List<Message> list = this.getMessagesByQueryCriteria(start, pageSize,
				query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	@SuppressWarnings("rawtypes")
	public PageResult getReceiveList(long userId, Map params, int pageNo,
			int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		MessageQuery query = new MessageQuery();
		query.setRecverId(userId);
		query.category(0);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.ID desc");

		int start = pageSize * (pageNo - 1);
		List<Message> list = this.getMessagesByQueryCriteria(start, pageSize,
				query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	@SuppressWarnings("rawtypes")
	public PageResult getNoReadList(long userId, Map params, int pageNo,
			int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		MessageQuery query = new MessageQuery();
		query.setRecverId(userId);
		query.category(0);
		query.setReaded(0);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.ID desc");

		int start = pageSize * (pageNo - 1);
		List<Message> list = this.getMessagesByQueryCriteria(start, pageSize,
				query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	@SuppressWarnings("rawtypes")
	public PageResult getSendedList(long userId, Map params, int pageNo,
			int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		MessageQuery query = new MessageQuery();
		query.senderId(userId);
		query.category(1);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.ID desc");

		int start = pageSize * (pageNo - 1);
		List<Message> list = this.getMessagesByQueryCriteria(start, pageSize,
				query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

}
