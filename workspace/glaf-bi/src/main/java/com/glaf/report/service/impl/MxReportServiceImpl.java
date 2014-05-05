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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.Scheduler;
import com.glaf.core.domain.SchedulerEntity;
import com.glaf.core.id.*;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.DateUtils;

import com.glaf.report.mapper.*;
import com.glaf.report.domain.*;
import com.glaf.report.query.*;
import com.glaf.report.service.IReportService;

@Service("reportService")
@Transactional(readOnly = true)
public class MxReportServiceImpl implements IReportService {
	protected final static Log logger = LogFactory
			.getLog(MxReportServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReportMapper reportMapper;

	protected ISysSchedulerService sysSchedulerService;

	public MxReportServiceImpl() {

	}

	public int count(ReportQuery query) {
		query.ensureInitialized();
		return reportMapper.getReportCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			reportMapper.deleteReportById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			ReportQuery query = new ReportQuery();
			query.rowIds(rowIds);
			reportMapper.deleteReports(query);
		}
	}

	public Report getReport(String id) {
		if (id == null) {
			return null;
		}
		Report report = reportMapper.getReportById(id);
		return report;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getReportCountByQueryCriteria(ReportQuery query) {
		return reportMapper.getReportCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Report> getReportsByQueryCriteria(int start, int pageSize,
			ReportQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Report> rows = sqlSessionTemplate.selectList("getReports", query,
				rowBounds);
		return rows;
	}

	public List<Report> list(ReportQuery query) {
		query.ensureInitialized();
		List<Report> list = reportMapper.getReports(query);
		return list;
	}

	@Transactional
	public void save(Report report) {
		if (StringUtils.isEmpty(report.getId())) {
			report.setId(idGenerator.getNextId());
			// report.setCreateDate(new Date());
			reportMapper.insertReport(report);
		} else {
			reportMapper.updateReport(report);
		}
		if (StringUtils.isNotEmpty(report.getCronExpression())) {
			String taskId = report.getId();
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
			scheduler.setTaskType("REPORT_MAIL");
			scheduler.setRepeatCount(-1);
			scheduler.setJobClass("com.glaf.report.job.ReportMailJob");
			scheduler.setExpression(report.getCronExpression());
			scheduler.setTaskName(report.getName());
			scheduler.setTitle(report.getSubject());
			scheduler.setStartDate(new Date());
			scheduler.setEndDate(new Date(System.currentTimeMillis()
					+ DateUtils.DAY * 3600));
			if (StringUtils.equals(report.getEnableFlag(), "1")) {
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
	public void setReportMapper(ReportMapper reportMapper) {
		this.reportMapper = reportMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysSchedulerService(ISysSchedulerService sysSchedulerService) {
		this.sysSchedulerService = sysSchedulerService;
	}

}