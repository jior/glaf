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

package com.glaf.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.SchedulerLog;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.SchedulerLogMapper;
import com.glaf.core.query.SchedulerLogQuery;
import com.glaf.core.service.ISchedulerLogService;

@Service("schedulerLogService")
@Transactional(readOnly = true)
public class MxSchedulerLogServiceImpl implements ISchedulerLogService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SchedulerLogMapper schedulerLogMapper;

	public MxSchedulerLogServiceImpl() {

	}

	public int count(SchedulerLogQuery query) {
		query.ensureInitialized();
		return schedulerLogMapper.getSchedulerLogCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			schedulerLogMapper.deleteSchedulerLogById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				schedulerLogMapper.deleteSchedulerLogById(id);
			}
		}
	}
	
	@Transactional
	public void deleteSchedulerLogByTaskId(String taskId){
		schedulerLogMapper.deleteSchedulerLogByTaskId(taskId);
	}

	public SchedulerLog getSchedulerLog(String id) {
		if (id == null) {
			return null;
		}
		SchedulerLog schedulerLog = schedulerLogMapper.getSchedulerLogById(id);
		return schedulerLog;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSchedulerLogCountByQueryCriteria(SchedulerLogQuery query) {
		return schedulerLogMapper.getSchedulerLogCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SchedulerLog> getSchedulerLogsByQueryCriteria(int start,
			int pageSize, SchedulerLogQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SchedulerLog> rows = sqlSessionTemplate.selectList(
				"getSchedulerLogs", query, rowBounds);
		return rows;
	}

	public List<SchedulerLog> list(SchedulerLogQuery query) {
		query.ensureInitialized();
		List<SchedulerLog> list = schedulerLogMapper.getSchedulerLogs(query);
		return list;
	}

	@Transactional
	public void save(SchedulerLog schedulerLog) {
		if (StringUtils.isEmpty(schedulerLog.getId())) {
			schedulerLog.setId(idGenerator.getNextId("SYS_SCHEDULER_LOG"));
			schedulerLog.setCreateDate(new Date());
			schedulerLogMapper.insertSchedulerLog(schedulerLog);
		} else {
			if (this.getSchedulerLog(schedulerLog.getId()) == null) {
				schedulerLog.setCreateDate(new Date());
				schedulerLogMapper.insertSchedulerLog(schedulerLog);
			} else {
				schedulerLogMapper.updateSchedulerLog(schedulerLog);
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
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSchedulerLogMapper(SchedulerLogMapper schedulerLogMapper) {
		this.schedulerLogMapper = schedulerLogMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
