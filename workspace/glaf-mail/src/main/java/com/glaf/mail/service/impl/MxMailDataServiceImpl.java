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

package com.glaf.mail.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.mail.domain.MailCount;
import com.glaf.mail.domain.MailItem;
import com.glaf.mail.domain.MailStorage;
import com.glaf.mail.domain.MailTask;
import com.glaf.mail.mapper.MailItemMapper;
import com.glaf.mail.mapper.MailStorageMapper;
import com.glaf.mail.mapper.MailTaskAccountMapper;
import com.glaf.mail.mapper.MailTaskMapper;
import com.glaf.mail.query.MailItemQuery;
import com.glaf.mail.service.IMailDataService;

@Service("mailDataService")
@Transactional(readOnly = true)
public class MxMailDataServiceImpl implements IMailDataService {
	protected final static Log logger = LogFactory
			.getLog(MxMailDataServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected MailItemMapper mailItemMapper;

	protected MailTaskMapper mailTaskMapper;

	protected MailStorageMapper mailStorageMapper;

	protected MailTaskAccountMapper mailTaskAccountMapper;

	public MxMailDataServiceImpl() {

	}

	public List<MailCount> getMailAccountReceiveStatusCount(MailItemQuery query) {
		MailTask task = this.getMailTask(query.getTaskId());
		query.setTableName(task.getStorage().getDataTable());
		return mailItemMapper.getMailAccountReceiveStatusCount(query);
	}

	public List<MailCount> getMailAccountSendStatusCount(MailItemQuery query) {
		MailTask task = this.getMailTask(query.getTaskId());
		query.setTableName(task.getStorage().getDataTable());
		return mailItemMapper.getMailAccountSendStatusCount(query);
	}

	public int getMailCount(MailItemQuery query) {
		MailTask task = this.getMailTask(query.getTaskId());
		query.setTableName(task.getStorage().getDataTable());
		return mailItemMapper.getMailItemCount(query);
	}

	public MailItem getMailItem(String taskId, String itemId) {
		MailItemQuery query = new MailItemQuery();
		query.taskId(taskId);
		query.itemId(itemId);
		MailTask task = this.getMailTask(query.getTaskId());
		query.setTableName(task.getStorage().getDataTable());

		return mailItemMapper.getMailItemById(query);
	}

	public List<MailItem> getMailItems(MailItemQuery query) {
		RowBounds rowBounds = new RowBounds(query.getBegin(),
				query.getPageSize());
		MailTask task = this.getMailTask(query.getTaskId());
		query.setTableName(task.getStorage().getDataTable());
		return sqlSession.selectList("getMailItems", query, rowBounds);
	}

	public List<MailItem> getMailListByQueryCriteria(int start, int pageSize,
			MailItemQuery query) {
		MailTask task = this.getMailTask(query.getTaskId());
		query.setTableName(task.getStorage().getDataTable());
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MailItem> rows = sqlSession.selectList("getMailItems", query,
				rowBounds);
		return rows;
	}

	public List<MailCount> getMailReceiveStatusCount(MailItemQuery query) {
		MailTask task = this.getMailTask(query.getTaskId());
		query.setTableName(task.getStorage().getDataTable());
		return mailItemMapper.getMailReceiveStatusCount(query);
	}

	public List<MailCount> getMailSendStatusCount(MailItemQuery query) {
		MailTask task = this.getMailTask(query.getTaskId());
		query.setTableName(task.getStorage().getDataTable());
		return mailItemMapper.getMailSendStatusCount(query);
	}

	public MailTask getMailTask(String id) {
		MailTask mailTask = mailTaskMapper.getMailTaskById(id);
		if (mailTask != null) {
			MailStorage stg = mailStorageMapper.getMailStorageById(mailTask
					.getStorageId());
			mailTask.setStorage(stg);
		}
		return mailTask;
	}

	@Transactional
	public void saveMails(String taskId, List<String> mailAddresses) {
		if (mailAddresses != null && !mailAddresses.isEmpty()) {
			MailTask task = this.getMailTask(taskId);
			for (String mail : mailAddresses) {
				MailItem item = new MailItem();
				item.setId(idGenerator.getNextId());
				item.setMailTo(mail);
				item.setRetryTimes(0);
				item.setTaskId(taskId);
				item.setSendStatus(0);
				item.setReceiveStatus(0);
				item.setTableName(task.getStorage().getDataTable());
				item.setLastModified(System.currentTimeMillis());
				mailItemMapper.insertMailItem(item);
			}
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setMailItemMapper(MailItemMapper mailItemMapper) {
		this.mailItemMapper = mailItemMapper;
	}

	@javax.annotation.Resource
	public void setMailStorageMapper(MailStorageMapper mailStorageMapper) {
		this.mailStorageMapper = mailStorageMapper;
	}

	@javax.annotation.Resource
	public void setMailTaskAccountMapper(
			MailTaskAccountMapper mailTaskAccountMapper) {
		this.mailTaskAccountMapper = mailTaskAccountMapper;
	}

	@javax.annotation.Resource
	public void setMailTaskMapper(MailTaskMapper mailTaskMapper) {
		this.mailTaskMapper = mailTaskMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 更新邮件信息
	 * 
	 * @param taskId
	 * @param item
	 */
	@Transactional
	public void updateMail(String taskId, MailItem item) {
		MailTask task = this.getMailTask(taskId);
		item.setTableName(task.getStorage().getDataTable());
		mailItemMapper.updateMailItem(item);
	}

}