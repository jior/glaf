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

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.workspace.mapper.MessageMapper;
import com.glaf.base.modules.workspace.model.Message;
import com.glaf.base.modules.workspace.query.MessageQuery;
import com.glaf.base.modules.workspace.service.MessageService;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.PageResult;

@Service("messageService")
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {
	protected IdGenerator idGenerator;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected MessageMapper messageMapper;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysUserService sysUserService;

	public MessageServiceImpl() {

	}

	public int count(MessageQuery query) {
		query.ensureInitialized();
		return messageMapper.getMessageCount(query);
	}

	@Transactional
	public boolean create(Message bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(Message bean) {
		this.deleteById(bean.getId());
		return true;
	}

	@Transactional
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

	public Message find(long id) {
		return this.getMessage(id);
	}

	public Message getMessage(Long id) {
		if (id == null) {
			return null;
		}
		Message message = messageMapper.getMessageById(id);
		if (message != null) {
			message.setRecver(sysUserService.findById(message.getRecverId()));
			message.setSender(sysUserService.findById(message.getSenderId()));
		}
		return message;
	}

	public int getMessageCountByQueryCriteria(MessageQuery query) {
		return messageMapper.getMessageCount(query);
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
		if (list != null && !list.isEmpty()) {
			List<SysUser> users = sysUserService.getSysUserList();
			Map<Long, SysUser> userMap = new java.util.HashMap<Long, SysUser>();
			for (SysUser user : users) {
				userMap.put(user.getId(), user);
			}
			for (Message message : list) {
				message.setRecver(userMap.get(message.getRecverId()));
				message.setSender(userMap.get(message.getSenderId()));
			}
		}
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<Message> getMessagesByQueryCriteria(int start, int pageSize,
			MessageQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Message> rows = sqlSessionTemplate.selectList("getMessages",
				query, rowBounds);
		if (rows != null && !rows.isEmpty()) {
			List<SysUser> users = sysUserService.getSysUserList();
			Map<Long, SysUser> userMap = new java.util.HashMap<Long, SysUser>();
			for (SysUser user : users) {
				userMap.put(user.getId(), user);
			}
			for (Message message : rows) {
				message.setRecver(userMap.get(message.getRecverId()));
				message.setSender(userMap.get(message.getSenderId()));
			}
		}
		return rows;
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
		if (list != null && !list.isEmpty()) {
			List<SysUser> users = sysUserService.getSysUserList();
			Map<Long, SysUser> userMap = new java.util.HashMap<Long, SysUser>();
			for (SysUser user : users) {
				userMap.put(user.getId(), user);
			}
			for (Message message : list) {
				message.setRecver(userMap.get(message.getRecverId()));
				message.setSender(userMap.get(message.getSenderId()));
			}
		}
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
		if (list != null && !list.isEmpty()) {
			List<SysUser> users = sysUserService.getSysUserList();
			Map<Long, SysUser> userMap = new java.util.HashMap<Long, SysUser>();
			for (SysUser user : users) {
				userMap.put(user.getId(), user);
			}
			for (Message message : list) {
				message.setRecver(userMap.get(message.getRecverId()));
				message.setSender(userMap.get(message.getSenderId()));
			}
		}
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
		if (list != null && !list.isEmpty()) {
			List<SysUser> users = sysUserService.getSysUserList();
			Map<Long, SysUser> userMap = new java.util.HashMap<Long, SysUser>();
			for (SysUser user : users) {
				userMap.put(user.getId(), user);
			}
			for (Message message : list) {
				message.setRecver(userMap.get(message.getRecverId()));
				message.setSender(userMap.get(message.getSenderId()));
			}
		}
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<Message> list(MessageQuery query) {
		query.ensureInitialized();
		List<Message> list = messageMapper.getMessages(query);
		return list;
	}

	@Transactional
	public void save(Message message) {
		if (message.getId() == 0L) {
			message.setId(idGenerator.nextId());
			message.setCreateDate(new Date());
			messageMapper.insertMessage(message);
		} else {
			messageMapper.updateMessage(message);
		}
	}

	@Transactional
	public boolean saveOrUpdate(Message bean) {
		this.save(bean);
		return true;
	}

	@Transactional
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

			SysUser recver = sysUserService.findByAccount(recverIds[i]);
			if (recver != null) {
				newMessage.setRecver(recver);
				newMessage.setRecverId(recver.getId());
				recverList.append(recver.getName()).append(",");
			}

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

	@Transactional
	public boolean saveSendMessageToDept(Message message, String[] recverIds) {
		boolean rst = true;

		// 收件部门列表
		StringBuffer recverList = new StringBuffer("");

		// 发送消息给收件人
		for (int i = 0; i < recverIds.length; i++) {
			if (recverIds[i] == null || recverIds[i].trim().length() == 0) {
				continue;
			}

			List<SysUser> list = sysUserService.getSysUserList(Long
					.parseLong(recverIds[i]));

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
				newMessage.setRecverId(recver.getId());

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
		newMessage.setRecverId(message.getSender().getId());

		if (recverLists.endsWith(",")) {
			recverLists = recverLists.substring(0, recverLists.length() - 1);
		}
		if (recverLists.length() > 2000) {
			recverLists = recverLists.substring(0, 2000);
		}
		newMessage.setRecverList(recverLists);
		return saveOrUpdate(newMessage);
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setMessageMapper(MessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Transactional
	public boolean update(Message bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public Message updateReadMessage(long id) {
		Message message = find(id);
		message.setReaded(1);// 设置已读
		update(message);
		return message;
	}

}
