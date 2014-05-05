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

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.Scheduler;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.SchedulerEntity;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.DateUtils;
import com.glaf.mail.domain.MailAccount;
import com.glaf.mail.domain.MailItem;
import com.glaf.mail.domain.MailStorage;
import com.glaf.mail.domain.MailTask;
import com.glaf.mail.domain.MailTaskAccount;
import com.glaf.mail.mapper.MailAccountMapper;
import com.glaf.mail.mapper.MailItemMapper;
import com.glaf.mail.mapper.MailStorageMapper;
import com.glaf.mail.mapper.MailTaskAccountMapper;
import com.glaf.mail.mapper.MailTaskMapper;
import com.glaf.mail.query.MailTaskQuery;
import com.glaf.mail.service.IMailTaskService;

@Service("mailTaskService")
@Transactional(readOnly = true)
public class MxMailTaskServiceImpl implements IMailTaskService {
	protected final static Log logger = LogFactory
			.getLog(MxMailTaskServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected MailItemMapper mailItemMapper;

	protected MailTaskMapper mailTaskMapper;

	protected MailAccountMapper mailAccountMapper;

	protected MailStorageMapper mailStorageMapper;

	protected MailTaskAccountMapper mailTaskAccountMapper;

	protected ISysSchedulerService sysSchedulerService;

	public MxMailTaskServiceImpl() {

	}

	public int count(MailTaskQuery query) {
		return mailTaskMapper.getMailTaskCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		mailTaskMapper.deleteMailTaskById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		MailTaskQuery query = new MailTaskQuery();
		query.rowIds(rowIds);
		mailTaskMapper.deleteMailTasks(query);
	}

	public List<String> getAccountIds(String taskId) {
		List<String> list = new java.util.ArrayList<String>();
		List<MailTaskAccount> accounts = mailTaskAccountMapper
				.getMailTaskAccountsByTaskId(taskId);
		for (MailTaskAccount a : accounts) {
			list.add(a.getAccountId());
		}
		return list;
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

	public MailTask getMailTaskWithAccounts(String taskId) {
		MailTask mailTask = mailTaskMapper.getMailTaskById(taskId);
		if (mailTask != null) {
			MailStorage stg = mailStorageMapper.getMailStorageById(mailTask
					.getStorageId());
			mailTask.setStorage(stg);
			List<MailAccount> accounts = mailAccountMapper
					.getMailAccountsByTaskId(taskId);
			mailTask.setAccounts(accounts);
		}

		return mailTask;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getMailTaskCountByQueryCriteria(MailTaskQuery query) {
		return mailTaskMapper.getMailTaskCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MailTask> getMailTasksByQueryCriteria(int start, int pageSize,
			MailTaskQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MailTask> rows = sqlSession.selectList("getMailTasks", query,
				rowBounds);
		return rows;
	}

	public List<MailTask> list(MailTaskQuery query) {
		List<MailTask> list = mailTaskMapper.getMailTasks(query);
		return list;
	}

	@Transactional
	public void save(MailTask mailTask) {
		if (StringUtils.isEmpty(mailTask.getId())) {
			mailTask.setId(idGenerator.getNextId());
			mailTask.setCreateDate(new Date());
			mailTaskMapper.insertMailTask(mailTask);
		} else {
			mailTaskMapper.updateMailTask(mailTask);
			String cacheKey = "mx_mail_act_" + mailTask.getId();
			CacheFactory.remove(cacheKey);
			cacheKey = "mx_mail_" + mailTask.getId();
			CacheFactory.remove(cacheKey);
		}

		String taskId = mailTask.getId();
		Scheduler scheduler = sysSchedulerService.getSchedulerByTaskId(taskId);
		boolean insert = false;
		if (scheduler == null) {
			scheduler = new SchedulerEntity();
			scheduler.setTaskId(taskId);
			scheduler.setCreateBy("system");
			scheduler.setCreateDate(new Date());
			insert = true;
		}
		scheduler.setTaskType("SYS_MAIL_TASK");
		scheduler.setRepeatCount(-1);
		scheduler.setJobClass("com.glaf.mail.job.MailTaskJob");
		scheduler.setTaskName(taskId);
		scheduler.setTitle(mailTask.getSubject());
		scheduler.setStartDate(new Date());
		scheduler.setEndDate(new Date(System.currentTimeMillis()
				+ DateUtils.DAY * 3600));
		if (mailTask.getLocked() == 0) {
			scheduler.setLocked(0);
			scheduler.setAutoStartup(1);
			scheduler.setStartup(1);
		} else {
			scheduler.setLocked(1);
			scheduler.setAutoStartup(0);
			scheduler.setStartup(0);
		}
		if (insert) {
			sysSchedulerService.save(scheduler);
		} else {
			sysSchedulerService.update(scheduler);
		}
	}

	@Transactional
	public void saveAccounts(String taskId, List<String> accountIds) {
		mailTaskAccountMapper.deleteMailTaskAccountByTaskId(taskId);
		if (accountIds != null && !accountIds.isEmpty()) {
			for (String accountId : accountIds) {
				MailTaskAccount a = new MailTaskAccount();
				a.setAccountId(accountId);
				a.setTaskId(taskId);
				a.setId(idGenerator.getNextId());
				mailTaskAccountMapper.insertMailTaskAccount(a);
			}
		}
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
	public void setMailAccountMapper(MailAccountMapper mailAccountMapper) {
		this.mailAccountMapper = mailAccountMapper;
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
	public void setMailStorageMapper(MailStorageMapper mailStorageMapper) {
		this.mailStorageMapper = mailStorageMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setSysSchedulerService(ISysSchedulerService sysSchedulerService) {
		this.sysSchedulerService = sysSchedulerService;
	}

}