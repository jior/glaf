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

package com.glaf.batch.service.impl;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.batch.mapper.*;
import com.glaf.batch.domain.*;
import com.glaf.batch.query.*;
import com.glaf.batch.service.IJobDefinitionService;

@Service("jobDefinitionService")
@Transactional(readOnly = true)
public class MxJobDefinitionServiceImpl implements IJobDefinitionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected volatile EntityDAO entityDAO;

	protected volatile IdGenerator idGenerator;

	protected volatile SqlSessionTemplate sqlSessionTemplate;

	protected volatile JobDefinitionMapper jobDefinitionMapper;

	protected volatile JobStepDefinitionMapper jobStepDefinitionMapper;

	protected volatile JobStepDefinitionParamMapper jobStepDefinitionParamMapper;

	public MxJobDefinitionServiceImpl() {

	}

	public int count(JobDefinitionQuery query) {
		return jobDefinitionMapper.getJobDefinitionCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			jobDefinitionMapper.deleteJobDefinitionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> jobDefinitionIds) {
		if (jobDefinitionIds != null && !jobDefinitionIds.isEmpty()) {
			for (Long id : jobDefinitionIds) {
				jobDefinitionMapper.deleteJobDefinitionById(id);
			}
		}
	}

	public JobDefinition getJobDefinition(Long jobDefinitionId) {
		if (jobDefinitionId == null) {
			return null;
		}
		JobDefinition jobDefinition = jobDefinitionMapper
				.getJobDefinitionById(jobDefinitionId);
		if (jobDefinition != null) {
			List<JobStepDefinition> steps = jobStepDefinitionMapper
					.getJobStepDefinitionsByJobDefinitionId(jobDefinitionId);
			List<JobStepDefinitionParam> params = jobStepDefinitionParamMapper
					.getParamsByJobDefinitionId(jobDefinitionId);
			if (steps != null && !steps.isEmpty()) {
				if (params != null && !params.isEmpty()) {
					for (JobStepDefinition step : steps) {
						for (JobStepDefinitionParam param : params) {
							if (step.getStepDefinitionId().longValue() == param
									.getStepDefinitionId().longValue()) {
								step.addParam(param);
							}
						}
						jobDefinition.addStep(step);
					}
				}
			}
		}
		return jobDefinition;
	}

	public JobDefinition getJobDefinitionByKey(String jobKey) {
		JobDefinitionQuery query = new JobDefinitionQuery();
		query.jobKey(jobKey);
		List<JobDefinition> list = jobDefinitionMapper.getJobDefinitions(query);
		if (list != null && !list.isEmpty()) {
			JobDefinition jobDefinition = list.get(0);
			List<JobStepDefinition> steps = jobStepDefinitionMapper
					.getJobStepDefinitionsByJobDefinitionId(jobDefinition
							.getJobDefinitionId());
			List<JobStepDefinitionParam> params = jobStepDefinitionParamMapper
					.getParamsByJobDefinitionId(jobDefinition
							.getJobDefinitionId());
			if (steps != null && !steps.isEmpty()) {
				if (params != null && !params.isEmpty()) {
					for (JobStepDefinition step : steps) {
						for (JobStepDefinitionParam param : params) {
							if (step.getStepDefinitionId().longValue() == param
									.getStepDefinitionId().longValue()) {
								step.addParam(param);
							}
						}
						jobDefinition.addStep(step);
					}
				}
			}
		}
		return null;
	}

	public int getJobDefinitionCountByQueryCriteria(JobDefinitionQuery query) {
		return jobDefinitionMapper.getJobDefinitionCount(query);
	}

	public List<JobDefinition> getJobDefinitionsByQueryCriteria(int start,
			int pageSize, JobDefinitionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<JobDefinition> rows = sqlSessionTemplate.selectList(
				"getJobDefinitions", query, rowBounds);
		return rows;
	}

	public List<JobDefinition> list(JobDefinitionQuery query) {
		List<JobDefinition> list = jobDefinitionMapper.getJobDefinitions(query);
		return list;
	}

	@Transactional
	public void saveJobDefinition(JobDefinition jobDefinition) {
		if (jobDefinition.getJobDefinitionId() == null) {
			jobDefinition.setJobDefinitionId(idGenerator.nextId());
			jobDefinitionMapper.insertJobDefinition(jobDefinition);
		} else {
			jobDefinitionMapper.updateJobDefinition(jobDefinition);
		}
	}

	@Transactional
	public void saveStepDefinition(JobStepDefinition stepDefinition) {
		if (stepDefinition.getStepDefinitionId() == null) {
			stepDefinition.setStepDefinitionId(idGenerator.nextId());
			jobStepDefinitionMapper.insertJobStepDefinition(stepDefinition);
		} else {
			JobStepDefinition model = jobStepDefinitionMapper
					.getJobStepDefinitionById(stepDefinition
							.getStepDefinitionId());
			if (model != null) {
				if (stepDefinition.getStepName() != null) {
					model.setStepName(stepDefinition.getStepName());
				}
				if (stepDefinition.getStepKey() != null) {
					model.setStepKey(stepDefinition.getStepKey());
				}
				jobStepDefinitionMapper.updateJobStepDefinition(model);
			}
		}
		if (stepDefinition.getStepDefinitionId() != null) {
			jobStepDefinitionParamMapper
					.deleteParamsByStepDefinitionId(stepDefinition
							.getStepDefinitionId());
			if (stepDefinition.getParams() != null
					&& !stepDefinition.getParams().isEmpty()) {
				for (JobStepDefinitionParam param : stepDefinition.getParams()) {
					param.setId(idGenerator.nextId());
					param.setStepDefinitionId(stepDefinition
							.getStepDefinitionId());
					param.setJobDefinitionId(stepDefinition
							.getJobDefinitionId());
					jobStepDefinitionParamMapper
							.insertJobStepDefinitionParam(param);
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
	public void setJobDefinitionMapper(JobDefinitionMapper jobDefinitionMapper) {
		this.jobDefinitionMapper = jobDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setJobStepDefinitionMapper(
			JobStepDefinitionMapper jobStepDefinitionMapper) {
		this.jobStepDefinitionMapper = jobStepDefinitionMapper;
	}

	public void setJobStepDefinitionParamMapper(
			JobStepDefinitionParamMapper jobStepDefinitionParamMapper) {
		this.jobStepDefinitionParamMapper = jobStepDefinitionParamMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
