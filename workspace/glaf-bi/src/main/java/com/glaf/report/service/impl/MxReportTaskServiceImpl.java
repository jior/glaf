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
package com.glaf.report.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.Scheduler;
import com.glaf.core.domain.SchedulerEntity;
import com.glaf.core.entity.EntityDAO;
import com.glaf.core.id.*;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.DateUtils;
import com.glaf.report.domain.*;
import com.glaf.report.query.*;
import com.glaf.report.mapper.*;
import com.glaf.report.service.*;

@Service("reportTaskService")
@Transactional(readOnly = true)
public class MxReportTaskServiceImpl implements IReportTaskService {
	protected static final Log logger = LogFactory
			.getLog(MxReportTaskServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected ReportTaskMapper reportTaskMapper;

	protected ISysSchedulerService sysSchedulerService;

	public MxReportTaskServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		reportTaskMapper.deleteReportTaskById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			for (String rowId : rowIds) {
				reportTaskMapper.deleteReportTaskById(rowId);
			}
		}
	}

	public int count(ReportTaskQuery query) {
		query.ensureInitialized();
		return reportTaskMapper.getReportTaskCountByQueryCriteria(query);
	}

	public List<ReportTask> list(ReportTaskQuery query) {
		query.ensureInitialized();
		List<ReportTask> list = reportTaskMapper
				.getReportTasksByQueryCriteria(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getReportTaskCount(Map<String, Object> parameter) {
		return reportTaskMapper.getReportTaskCount(parameter);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getReportTaskCountByQueryCriteria(ReportTaskQuery query) {
		return reportTaskMapper.getReportTaskCountByQueryCriteria(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ReportTask> getReportTasksByQueryCriteria(int start,
			int pageSize, ReportTaskQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportTask> rows = sqlSession.selectList(
				"getReportTasksByQueryCriteria", query, rowBounds);
		return rows;
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<ReportTask> getReportTasks(Map<String, Object> parameter) {
		return reportTaskMapper.getReportTasks(parameter);
	}

	public ReportTask getReportTask(String id) {
		ReportTask reportTask = reportTaskMapper.getReportTaskById(id);
		return reportTask;
	}

	public ReportTask getReportTaskByName(String name) {
		ReportTask reportTask = reportTaskMapper.getReportTaskByName(name);
		return reportTask;
	}

	@Transactional
	public void save(ReportTask reportTask) {
		if (StringUtils.isEmpty(reportTask.getId())) {
			reportTask.setId(idGenerator.getNextId());
			// reportTask.setId(idGenerator.getNextId());
			// reportTask.setCreateDate(new Date());
			reportTaskMapper.insertReportTask(reportTask);
		} else {
			ReportTask model = this.getReportTask(reportTask.getId());
			if (model != null) {
				if (reportTask.getReportIds() != null) {
					model.setReportIds(reportTask.getReportIds());
				}
				if (reportTask.getEnableFlag() != null) {
					model.setEnableFlag(reportTask.getEnableFlag());
				}
				if (reportTask.getCronExpression() != null) {
					model.setCronExpression(reportTask.getCronExpression());
				}
				if (reportTask.getName() != null) {
					model.setName(reportTask.getName());
				}
				if (reportTask.getSubject() != null) {
					model.setSubject(reportTask.getSubject());
				}
				if (reportTask.getMailRecipient() != null) {
					model.setMailRecipient(reportTask.getMailRecipient());
				}
				if (reportTask.getMobileRecipient() != null) {
					model.setMobileRecipient(reportTask.getMobileRecipient());
				}
				if (reportTask.getSendTitle() != null) {
					model.setSendTitle(reportTask.getSendTitle());
				}
				if (reportTask.getSendContent() != null) {
					model.setSendContent(reportTask.getSendContent());
				}
				if (reportTask.getCreateDate() != null) {
					model.setCreateDate(reportTask.getCreateDate());
				}
				if (reportTask.getCreateBy() != null) {
					model.setCreateBy(reportTask.getCreateBy());
				}
				reportTaskMapper.updateReportTask(model);
			}
		}

		if (StringUtils.isNotEmpty(reportTask.getCronExpression())) {
			String taskId = reportTask.getId();
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
			scheduler.setTaskType("REPORT_TASK_MAIL");
			scheduler.setRepeatCount(-1);
			scheduler.setJobClass("com.glaf.report.job.ReportTaskMailJob");
			scheduler.setExpression(reportTask.getCronExpression());
			scheduler.setTaskName(reportTask.getName());
			scheduler.setTitle(reportTask.getSubject());
			scheduler.setStartDate(new Date());
			scheduler.setEndDate(new Date(System.currentTimeMillis()
					+ DateUtils.DAY * 3600));
			if (StringUtils.equals(reportTask.getEnableFlag(), "1")) {
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

	@javax.annotation.Resource(name = "myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource(name = "myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setReportTaskMapper(ReportTaskMapper reportTaskMapper) {
		this.reportTaskMapper = reportTaskMapper;
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
