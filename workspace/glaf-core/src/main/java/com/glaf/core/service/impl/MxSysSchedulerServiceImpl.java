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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.Parameter;
import com.glaf.core.base.Scheduler;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.SchedulerParam;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.SchedulerMapper;
import com.glaf.core.mapper.SchedulerParamMapper;
import com.glaf.core.query.SchedulerQuery;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.UUID32;

@Service("sysSchedulerService")
@Transactional(readOnly = true)
public class MxSysSchedulerServiceImpl implements ISysSchedulerService {
	protected final static Log logger = LogFactory
			.getLog(MxSysSchedulerServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected SchedulerMapper schedulerMapper;

	protected SchedulerParamMapper schedulerParamMapper;

	public MxSysSchedulerServiceImpl() {

	}

	public int count(SchedulerQuery query) {
		query.ensureInitialized();
		return schedulerMapper.getSchedulerCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		schedulerMapper.deleteSchedulerById(id);
		schedulerParamMapper.deleteSchedulerParamsByTaskId(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		SchedulerQuery query = new SchedulerQuery();
		query.rowIds(rowIds);
		schedulerMapper.deleteSchedulers(query);
	}

	@Transactional
	public void deleteScheduler(String taskId) {
		schedulerMapper.deleteSchedulerByTaskId(taskId);
		schedulerParamMapper.deleteSchedulerParamsByTaskId(taskId);
	}

	public List<Scheduler> getAllSchedulers() {
		SchedulerQuery query = new SchedulerQuery();
		return this.list(query);
	}

	public Scheduler getScheduler(String id) {
		Scheduler scheduler = schedulerMapper.getSchedulerById(id);
		if (scheduler == null) {
			scheduler = this.getSchedulerByTaskId(id);
		}
		if (scheduler != null) {
			List<SchedulerParam> params = schedulerParamMapper
					.getSchedulerParamsByTaskId(scheduler.getTaskId());
			if (params != null && !params.isEmpty()) {
				for (SchedulerParam param : params) {
					scheduler.getJobDataMap().put(param.getKeyName(), param);
				}
			}
		}
		return scheduler;
	}

	public Scheduler getSchedulerByTaskId(String taskId) {
		Scheduler scheduler = schedulerMapper.getSchedulerByTaskId(taskId);
		if (scheduler != null) {
			List<SchedulerParam> params = schedulerParamMapper
					.getSchedulerParamsByTaskId(scheduler.getTaskId());
			if (params != null && !params.isEmpty()) {
				for (SchedulerParam param : params) {
					scheduler.getJobDataMap().put(param.getKeyName(), param);
				}
			}
		}
		return scheduler;
	}

	public List<Scheduler> getSchedulers(String taskType) {
		SchedulerQuery query = new SchedulerQuery();
		query.taskType(taskType);
		return this.list(query);
	}

	public List<Scheduler> getUserSchedulers(String createBy) {
		SchedulerQuery query = new SchedulerQuery();
		query.createBy(createBy);
		return this.list(query);
	}

	public List<Scheduler> list(SchedulerQuery query) {
		query.ensureInitialized();
		List<Scheduler> list = schedulerMapper.getSchedulers(query);
		return list;
	}

	@Transactional
	public void locked(String taskId, int locked) {
		Scheduler model = this.getSchedulerByTaskId(taskId);
		if (model != null) {
			model.setLocked(locked);
			schedulerMapper.updateScheduler(model);
		}
	}

	@Transactional
	public void save(Scheduler model) {
		if (StringUtils.isEmpty(model.getId())) {
			if (StringUtils.isEmpty(model.getTaskId())) {
				model.setTaskId(UUID32.getUUID());
			}
			if (model.getStartDate() == null) {
				model.setStartDate(new Date());
			}
			if (model.getRepeatInterval() <= 0) {
				model.setRepeatInterval(3600);
			}
			model.setCreateDate(new Date());
			model.setId(idGenerator.getNextId());
			schedulerMapper.insertScheduler(model);
		} else {
			schedulerMapper.updateScheduler(model);
		}
		schedulerParamMapper.deleteSchedulerParamsByTaskId(model.getTaskId());
		Collection<Parameter> params = model.getJobDataMap().values();
		if (params != null && !params.isEmpty()) {
			for (Parameter param : params) {
				if (param instanceof SchedulerParam) {
					SchedulerParam p = (SchedulerParam) param;
					if (StringUtils.isEmpty(p.getId())) {
						p.setId(idGenerator.getNextId());
						p.setTaskId(model.getTaskId());
						schedulerParamMapper.insertSchedulerParam(p);
					}
				}
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
	public void setSchedulerMapper(SchedulerMapper schedulerMapper) {
		this.schedulerMapper = schedulerMapper;
	}

	@javax.annotation.Resource
	public void setSchedulerParamMapper(
			SchedulerParamMapper schedulerParamMapper) {
		this.schedulerParamMapper = schedulerParamMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Transactional
	public void update(Scheduler model) {
		schedulerMapper.updateScheduler(model);
	}

}