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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.batch.domain.JobExecution;
import com.glaf.batch.domain.JobExecutionContext;
import com.glaf.batch.domain.JobInstance;
import com.glaf.batch.domain.JobParam;
import com.glaf.batch.domain.StepExecution;
import com.glaf.batch.mapper.JobExecutionContextMapper;
import com.glaf.batch.mapper.JobExecutionMapper;
import com.glaf.batch.mapper.JobInstanceMapper;
import com.glaf.batch.mapper.JobParamMapper;
import com.glaf.batch.mapper.StepExecutionContextMapper;
import com.glaf.batch.mapper.StepExecutionMapper;
import com.glaf.batch.query.JobInstanceQuery;
import com.glaf.batch.service.IJobService;
import com.glaf.batch.util.BatchStatus;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("jobService")
@Transactional(readOnly = true)
public class MxJobServiceImpl implements IJobService {
	protected static final Log logger = LogFactory
			.getLog(MxJobServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected JobInstanceMapper jobInstanceMapper;

	protected JobParamMapper jobParamMapper;

	protected JobExecutionMapper jobExecutionMapper;

	protected JobExecutionContextMapper jobExecutionContextMapper;

	protected StepExecutionMapper stepExecutionMapper;

	protected StepExecutionContextMapper stepExecutionContextMapper;

	public MxJobServiceImpl() {

	}

	@Transactional
	public void completeStepExecution(String jobStepKey) {
		StepExecution step = this.getStepExecutionByKey(jobStepKey);
		if (step != null && step.getStatus() != null) {
			step.setExitCode(BatchStatus.COMPLETED.toString());
			step.setStatus(BatchStatus.COMPLETED.toString());
			step.setEndTime(new Date());
			stepExecutionMapper.updateStepExecution(step);
		}
	}

	public int count(JobInstanceQuery query) {
		query.ensureInitialized();
		return jobInstanceMapper.getJobInstanceCountByQueryCriteria(query);
	}

	@Transactional
	public void deleteJobInstanceById(int jobInstanceId) {
		stepExecutionMapper.deleteStepExecutionByJobInstanceId(jobInstanceId);
		jobExecutionMapper.deleteJobExecutionByJobInstanceId(jobInstanceId);
		jobParamMapper.deleteJobParamsByJobInstanceId(jobInstanceId);
		jobInstanceMapper.deleteJobInstanceById(jobInstanceId);
	}

	@Transactional
	public void deleteJobInstanceByJobKey(String jobKey) {
		JobInstance jobInstance = jobInstanceMapper
				.getJobInstanceByJobKey(jobKey);
		if (jobInstance != null) {
			this.deleteJobInstanceById(jobInstance.getJobInstanceId());
		}
	}

	public JobExecution getJobExecutionById(int jobExecutionId) {
		JobExecution jobExecution = jobExecutionMapper
				.getJobExecutionById(jobExecutionId);
		return jobExecution;
	}

	public JobExecutionContext getJobExecutionContextById(int id) {
		JobExecutionContext jobExecutionContext = jobExecutionContextMapper
				.getJobExecutionContextById(id);
		return jobExecutionContext;
	}

	public List<JobExecution> getJobExecutions(int jobInstanceId) {
		return jobExecutionMapper.getJobExecutionByJobInstanceId(jobInstanceId);
	}

	public JobInstance getJobInstanceById(int jobInstanceId) {
		JobInstance jobInstance = jobInstanceMapper
				.getJobInstanceById(jobInstanceId);
		if (jobInstance != null) {
			List<JobParam> params = jobParamMapper
					.getJobParamsByJobInstanceId(jobInstance.getJobInstanceId());
			jobInstance.setParams(params);
		}
		return jobInstance;
	}

	public JobInstance getJobInstanceByIdWithAll(int jobInstanceId) {
		JobInstance jobInstance = jobInstanceMapper
				.getJobInstanceById(jobInstanceId);
		if (jobInstance != null) {
			List<JobParam> params = jobParamMapper
					.getJobParamsByJobInstanceId(jobInstanceId);
			jobInstance.setParams(params);

			List<JobExecution> executions = jobExecutionMapper
					.getJobExecutionByJobInstanceId(jobInstanceId);
			jobInstance.setExecutions(executions);

			List<StepExecution> steps = stepExecutionMapper
					.getStepExecutionsByJobInstanceId(jobInstanceId);
			jobInstance.setSteps(steps);
		}
		return jobInstance;
	}

	public JobInstance getJobInstanceByJobKey(String jobKey) {
		JobInstance jobInstance = jobInstanceMapper
				.getJobInstanceByJobKey(jobKey);
		if (jobInstance != null) {
			List<JobParam> params = jobParamMapper
					.getJobParamsByJobInstanceId(jobInstance.getJobInstanceId());
			jobInstance.setParams(params);
		}
		return jobInstance;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getJobInstanceCount(Map<String, Object> parameter) {
		return jobInstanceMapper.getJobInstanceCount(parameter);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getJobInstanceCountByQueryCriteria(JobInstanceQuery query) {
		return jobInstanceMapper.getJobInstanceCountByQueryCriteria(query);
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<JobInstance> getJobInstances(Map<String, Object> parameter) {
		return jobInstanceMapper.getJobInstances(parameter);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<JobInstance> getJobInstancesByQueryCriteria(int start,
			int pageSize, JobInstanceQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<JobInstance> rows = sqlSession.selectList("getJobInstances",
				query, rowBounds);
		return rows;
	}

	public StepExecution getStepExecutionById(int stepExecutionId) {
		StepExecution stepExecution = stepExecutionMapper
				.getStepExecutionById(stepExecutionId);
		return stepExecution;
	}

	public StepExecution getStepExecutionByKey(String jobStepKey) {
		StepExecution stepExecution = stepExecutionMapper
				.getStepExecutionByKey(jobStepKey);
		return stepExecution;
	}

	public List<StepExecution> getStepExecutions(int jobInstanceId) {
		return stepExecutionMapper
				.getStepExecutionsByJobInstanceId(jobInstanceId);
	}

	/**
	 * 判断某个Job是否完成
	 * 
	 * @param jobKey
	 * @return
	 */
	public boolean jobCompleted(String jobKey) {
		JobInstance jobInstance = this.getJobInstanceByJobKey(jobKey);
		if (jobInstance == null) {
			return false;
		}
		List<StepExecution> steps = this.getStepExecutions(jobInstance
				.getJobInstanceId());
		if (steps != null && !steps.isEmpty()) {
			for (StepExecution step : steps) {
				if (step.getStatus() != null
						&& BatchStatus.match(step.getStatus()) != BatchStatus.COMPLETED) {
					return false;
				}
			}
		}
		return true;
	}

	public List<JobInstance> list(JobInstanceQuery query) {
		query.ensureInitialized();
		List<JobInstance> list = jobInstanceMapper
				.getJobInstancesByQueryCriteria(query);
		return list;
	}

	@Transactional
	public void saveJobContext(JobExecutionContext jobExecutionContext) {
		if (jobExecutionContext.getJobExecutionId() == 0) {
			jobExecutionContext.setJobExecutionId(idGenerator.nextId()
					.intValue());
			jobExecutionContextMapper
					.insertJobExecutionContext(jobExecutionContext);
		} else {
			JobExecutionContext model = this
					.getJobExecutionContextById(jobExecutionContext
							.getJobExecutionId());
			if (model != null) {
				if (jobExecutionContext.getShortContext() != null) {
					model.setShortContext(jobExecutionContext.getShortContext());
				}
				model.setSerializedContext(jobExecutionContext
						.getSerializedContext());
				jobExecutionContextMapper.updateJobExecutionContext(model);
			}
		}
	}

	@Transactional
	public void saveJobExecution(JobExecution jobExecution) {
		if (jobExecution.getJobExecutionId() == 0) {
			jobExecution.setJobExecutionId(idGenerator.nextId().intValue());
			jobExecutionMapper.insertJobExecution(jobExecution);
			if (jobExecution.getSteps() != null
					&& !jobExecution.getSteps().isEmpty()) {
				for (StepExecution stepExecution : jobExecution.getSteps()) {
					stepExecution.setJobExecutionId(jobExecution
							.getJobExecutionId());
					stepExecution.setJobInstanceId(jobExecution
							.getJobInstanceId());
					this.saveStepExecution(stepExecution);
				}
			}
		} else {
			JobExecution model = this.getJobExecutionById(jobExecution
					.getJobExecutionId());
			if (model != null) {
				model.setVersion(jobExecution.getVersion());
				if (jobExecution.getStartTime() != null) {
					model.setStartTime(jobExecution.getStartTime());
				}
				if (jobExecution.getEndTime() != null) {
					model.setEndTime(jobExecution.getEndTime());
				}
				if (jobExecution.getStatus() != null) {
					model.setStatus(jobExecution.getStatus());
				}
				if (jobExecution.getExitCode() != null) {
					model.setExitCode(jobExecution.getExitCode());
				}
				if (jobExecution.getExitMessage() != null) {
					model.setExitMessage(jobExecution.getExitMessage());
				}
				if (jobExecution.getLastUpdated() != null) {
					model.setLastUpdated(jobExecution.getLastUpdated());
				}
				jobExecutionMapper.updateJobExecution(model);
			}
		}
	}

	@Transactional
	public void saveJobInstance(JobInstance jobInstance) {
		if (jobInstance.getJobInstanceId() == 0) {
			jobInstance.setJobInstanceId(idGenerator.nextId().intValue());
			jobInstanceMapper.insertJobInstance(jobInstance);
			if (jobInstance.getParams() != null
					&& !jobInstance.getParams().isEmpty()) {
				for (JobParam param : jobInstance.getParams()) {
					param.setId(idGenerator.nextId().intValue());
					param.setJobInstanceId(jobInstance.getJobInstanceId());
					jobParamMapper.insertJobParam(param);
				}
			}
			if (jobInstance.getExecutions() != null
					&& !jobInstance.getExecutions().isEmpty()) {
				for (JobExecution jobExecution : jobInstance.getExecutions()) {
					jobExecution.setJobInstanceId(jobInstance
							.getJobInstanceId());
					this.saveJobExecution(jobExecution);
				}
			}
		} else {
			JobInstance model = this.getJobInstanceById(jobInstance
					.getJobInstanceId());
			if (model != null) {
				model.setVersion(jobInstance.getVersion());
				if (jobInstance.getJobName() != null) {
					model.setJobName(jobInstance.getJobName());
				}
				jobInstanceMapper.updateJobInstance(model);
				jobParamMapper.deleteJobParamsByJobInstanceId(model
						.getJobInstanceId());
				if (jobInstance.getParams() != null
						&& !jobInstance.getParams().isEmpty()) {
					for (JobParam param : jobInstance.getParams()) {
						param.setId(idGenerator.nextId().intValue());
						param.setJobInstanceId(model.getJobInstanceId());
						jobParamMapper.insertJobParam(param);
					}
				}
			}
		}
	}

	@Transactional
	public void saveStepExecution(StepExecution stepExecution) {
		if (stepExecution.getStepExecutionId() == 0) {
			stepExecution.setStepExecutionId(idGenerator.nextId().intValue());
			stepExecutionMapper.insertStepExecution(stepExecution);
		} else {
			StepExecution model = this.getStepExecutionById(stepExecution
					.getStepExecutionId());
			if (model != null) {
				model.setVersion(stepExecution.getVersion());
				if (stepExecution.getStepKey() != null) {
					model.setStepKey(stepExecution.getStepKey());
				}
				if (stepExecution.getStepName() != null) {
					model.setStepName(stepExecution.getStepName());
				}
				if (stepExecution.getStartTime() != null) {
					model.setStartTime(stepExecution.getStartTime());
				}
				if (stepExecution.getEndTime() != null) {
					model.setEndTime(stepExecution.getEndTime());
				}
				if (stepExecution.getStatus() != null) {
					model.setStatus(stepExecution.getStatus());
				}
				model.setCommitCount(stepExecution.getCommitCount());
				model.setReadCount(stepExecution.getReadCount());
				model.setFilterCount(stepExecution.getFilterCount());
				model.setWriteCount(stepExecution.getWriteCount());
				model.setReadSkipCount(stepExecution.getReadSkipCount());
				model.setWriteSkipCount(stepExecution.getWriteSkipCount());
				model.setProcessSkipCount(stepExecution.getProcessSkipCount());
				model.setRollbackCount(stepExecution.getRollbackCount());
				if (stepExecution.getExitCode() != null) {
					model.setExitCode(stepExecution.getExitCode());
				}
				if (stepExecution.getExitMessage() != null) {
					model.setExitMessage(stepExecution.getExitMessage());
				}
				if (stepExecution.getLastUpdated() != null) {
					model.setLastUpdated(stepExecution.getLastUpdated());
				}
				stepExecutionMapper.updateStepExecution(model);
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
	public void setJobExecutionContextMapper(
			JobExecutionContextMapper jobExecutionContextMapper) {
		this.jobExecutionContextMapper = jobExecutionContextMapper;
	}

	@javax.annotation.Resource
	public void setJobExecutionMapper(JobExecutionMapper jobExecutionMapper) {
		this.jobExecutionMapper = jobExecutionMapper;
	}

	@javax.annotation.Resource
	public void setJobInstanceMapper(JobInstanceMapper jobInstanceMapper) {
		this.jobInstanceMapper = jobInstanceMapper;
	}

	@javax.annotation.Resource
	public void setJobParamMapper(JobParamMapper jobParamMapper) {
		this.jobParamMapper = jobParamMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setStepExecutionContextMapper(
			StepExecutionContextMapper stepExecutionContextMapper) {
		this.stepExecutionContextMapper = stepExecutionContextMapper;
	}

	@javax.annotation.Resource
	public void setStepExecutionMapper(StepExecutionMapper stepExecutionMapper) {
		this.stepExecutionMapper = stepExecutionMapper;
	}

	@Transactional
	public void startStepExecution(String jobStepKey) {
		StepExecution step = this.getStepExecutionByKey(jobStepKey);
		if (step != null) {
			step.setStartTime(new Date());
			step.setStatus(BatchStatus.STARTED.toString());
			step.setEndTime(null);
			stepExecutionMapper.updateStepExecution(step);
		}
	}

	@Override
	public boolean stepExecutionCompleted(String jobStepKey) {
		StepExecution step = this.getStepExecutionByKey(jobStepKey);
		if (step != null && step.getStatus() != null) {
			if (BatchStatus.match(step.getStatus()) == BatchStatus.COMPLETED) {
				return true;
			}
		}
		return false;
	}

}