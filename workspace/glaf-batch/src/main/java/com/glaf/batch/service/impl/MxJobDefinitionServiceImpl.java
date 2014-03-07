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

	protected volatile StepDefinitionMapper stepDefinitionMapper;

	protected volatile StepDefinitionParamMapper stepDefinitionParamMapper;

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
			List<StepDefinition> steps = stepDefinitionMapper
					.getStepDefinitionsByJobDefinitionId(jobDefinitionId);
			List<StepDefinitionParam> params = stepDefinitionParamMapper
					.getParamsByJobDefinitionId(jobDefinitionId);
			if (steps != null && !steps.isEmpty()) {
				if (params != null && !params.isEmpty()) {
					for (StepDefinition step : steps) {
						for (StepDefinitionParam param : params) {
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
			List<StepDefinition> steps = stepDefinitionMapper
					.getStepDefinitionsByJobDefinitionId(jobDefinition
							.getJobDefinitionId());
			List<StepDefinitionParam> params = stepDefinitionParamMapper
					.getParamsByJobDefinitionId(jobDefinition
							.getJobDefinitionId());
			if (steps != null && !steps.isEmpty()) {
				if (params != null && !params.isEmpty()) {
					for (StepDefinition step : steps) {
						for (StepDefinitionParam param : params) {
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
	public void saveStepDefinition(StepDefinition stepDefinition) {
		if (stepDefinition.getStepDefinitionId() == null) {
			stepDefinition.setStepDefinitionId(idGenerator.nextId());
			stepDefinitionMapper.insertStepDefinition(stepDefinition);
		} else {
			StepDefinition model = stepDefinitionMapper
					.getStepDefinitionById(stepDefinition
							.getStepDefinitionId());
			if (model != null) {
				if (stepDefinition.getStepName() != null) {
					model.setStepName(stepDefinition.getStepName());
				}
				if (stepDefinition.getStepKey() != null) {
					model.setStepKey(stepDefinition.getStepKey());
				}
				stepDefinitionMapper.updateStepDefinition(model);
			}
		}
		if (stepDefinition.getStepDefinitionId() != null) {
			stepDefinitionParamMapper
					.deleteParamsByStepDefinitionId(stepDefinition
							.getStepDefinitionId());
			if (stepDefinition.getParams() != null
					&& !stepDefinition.getParams().isEmpty()) {
				for (StepDefinitionParam param : stepDefinition.getParams()) {
					param.setId(idGenerator.nextId());
					param.setStepDefinitionId(stepDefinition
							.getStepDefinitionId());
					param.setJobDefinitionId(stepDefinition
							.getJobDefinitionId());
					stepDefinitionParamMapper
							.insertStepDefinitionParam(param);
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
	public void setStepDefinitionMapper(
			StepDefinitionMapper stepDefinitionMapper) {
		this.stepDefinitionMapper = stepDefinitionMapper;
	}

	public void setStepDefinitionParamMapper(
			StepDefinitionParamMapper stepDefinitionParamMapper) {
		this.stepDefinitionParamMapper = stepDefinitionParamMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
