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
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.Scheduler;
import com.glaf.core.domain.SchedulerEntity;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.DateUtils;
import com.glaf.mail.domain.MailPathSender;
import com.glaf.mail.mapper.MailPathSenderMapper;
import com.glaf.mail.query.MailPathSenderQuery;
import com.glaf.mail.service.IMailPathSenderService;

@Service("mailPathSenderService")
@Transactional(readOnly = true)
public class MxMailPathSenderServiceImpl implements IMailPathSenderService {
	protected final static Log logger = LogFactory
			.getLog(MxMailPathSenderServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MailPathSenderMapper mailPathSenderMapper;

	protected ISysSchedulerService sysSchedulerService;

	public MxMailPathSenderServiceImpl() {

	}

	public int count(MailPathSenderQuery query) {
		query.ensureInitialized();
		return mailPathSenderMapper.getMailPathSenderCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			mailPathSenderMapper.deleteMailPathSenderById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			MailPathSenderQuery query = new MailPathSenderQuery();
			query.rowIds(rowIds);
			mailPathSenderMapper.deleteMailPathSenders(query);
		}
	}

	public MailPathSender getMailPathSender(String id) {
		if (id == null) {
			return null;
		}
		MailPathSender mailTask = mailPathSenderMapper
				.getMailPathSenderById(id);
		return mailTask;
	}

	public MailPathSender getMailPathSenderByTaskId(String taskId) {
		return mailPathSenderMapper.getMailPathSenderByTaskId(taskId);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getMailPathSenderCountByQueryCriteria(MailPathSenderQuery query) {
		return mailPathSenderMapper.getMailPathSenderCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MailPathSender> getMailPathSendersByQueryCriteria(int start,
			int pageSize, MailPathSenderQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MailPathSender> rows = sqlSessionTemplate.selectList(
				"getMailPathSenders", query, rowBounds);
		return rows;
	}

	public List<MailPathSender> list(MailPathSenderQuery query) {
		query.ensureInitialized();
		List<MailPathSender> list = mailPathSenderMapper
				.getMailPathSenders(query);
		return list;
	}

	@Transactional
	public void save(MailPathSender mailTask) {
		if (StringUtils.isEmpty(mailTask.getId())) {
			mailTask.setId(idGenerator.getNextId());
			// mailTask.setCreateDate(new Date());
			mailTask.setTaskId("mail_task_" + mailTask.getId());
			mailPathSenderMapper.insertMailPathSender(mailTask);
		} else {
			mailPathSenderMapper.updateMailPathSender(mailTask);
		}
		if (StringUtils.isNotEmpty(mailTask.getCronExpression())) {
			String taskId = "mail_task_" + mailTask.getId();
			Scheduler scheduler = sysSchedulerService
					.getSchedulerByTaskId(taskId);
			boolean insert = false;
			if (scheduler == null) {
				scheduler = new SchedulerEntity();
				scheduler.setTaskId(taskId);
				scheduler.setCreateBy("system");
				scheduler.setCreateDate(new Date());
				insert = true;
			}
			scheduler.setTaskType("MAIL_TASK");
			scheduler.setRepeatCount(-1);
			scheduler.setJobClass("com.glaf.mail.job.MailPathSenderJob");
			scheduler.setExpression(mailTask.getCronExpression());
			scheduler.setTaskName(mailTask.getName());
			scheduler.setTitle(mailTask.getSubject());
			scheduler.setStartDate(new Date());
			scheduler.setEndDate(new Date(System.currentTimeMillis()
					+ DateUtils.DAY * 3600));
			if (StringUtils.equals(mailTask.getEnableFlag(), "1")) {
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
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setMailPathSenderMapper(
			MailPathSenderMapper mailPathSenderMapper) {
		this.mailPathSenderMapper = mailPathSenderMapper;
	}

	@javax.annotation.Resource
	public void setSysSchedulerService(ISysSchedulerService sysSchedulerService) {
		this.sysSchedulerService = sysSchedulerService;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}