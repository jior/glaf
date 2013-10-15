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

package com.glaf.batch.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.batch.domain.*;
import com.glaf.batch.query.*;

@Transactional(readOnly = true)
public interface IJobService {

	/**
	 * 完成某个步骤
	 * 
	 * @param jobStepKey
	 */
	@Transactional
	void completeStepExecution(String jobStepKey);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteJobInstanceById(int jobInstanceId);

	/**
	 * 根据Key删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteJobInstanceByJobKey(String jobKey);

	/**
	 * 根据执行Id获取执行信息
	 * 
	 * @param jobExecutionId
	 * @return
	 */
	JobExecution getJobExecutionById(int jobExecutionId);

	/**
	 * 根据Job实例编号获取执行信息
	 * 
	 * @param jobInstanceId
	 * @return
	 */
	List<JobExecution> getJobExecutions(int jobInstanceId);

	/**
	 * 根据主键获取Job实例
	 * 
	 * @return
	 */
	JobInstance getJobInstanceById(int jobInstanceId);

	/**
	 * 根据主键获取Job实例
	 * 
	 * @return
	 */
	JobInstance getJobInstanceByIdWithAll(int jobInstanceId);

	/**
	 * 根据Job Key获取Job实例
	 * 
	 * @param jobKey
	 * @return
	 */
	JobInstance getJobInstanceByJobKey(String jobKey);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getJobInstanceCount(Map<String, Object> parameter);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getJobInstanceCountByQueryCriteria(JobInstanceQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<JobInstance> getJobInstances(Map<String, Object> parameter);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<JobInstance> getJobInstancesByQueryCriteria(int start, int pageSize,
			JobInstanceQuery query);

	/**
	 * 根据ID获取执行步骤
	 * 
	 * @param stepExecutionId
	 * @return
	 */
	StepExecution getStepExecutionById(int stepExecutionId);

	/**
	 * 
	 * @param jobStepKey
	 * @return
	 */
	StepExecution getStepExecutionByKey(String jobStepKey);

	/**
	 * 
	 * @param jobInstanceId
	 * @return
	 */
	List<StepExecution> getStepExecutions(int jobInstanceId);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<JobInstance> list(JobInstanceQuery query);

	/**
	 * 保存Job执行信息
	 * 
	 * @return
	 */
	@Transactional
	void saveJobExecution(JobExecution jobExecution);

	/**
	 * 保存Job实例信息
	 * 
	 * @return
	 */
	@Transactional
	void saveJobInstance(JobInstance jobInstance);

	/**
	 * 保存执行步骤信息
	 * 
	 * @return
	 */
	@Transactional
	void saveStepExecution(StepExecution stepExecution);

	/**
	 * 开始某个步骤
	 * 
	 * @param jobStepKey
	 */
	@Transactional
	void startStepExecution(String jobStepKey);

	/**
	 * 判断某个步骤是否成功完成
	 * 
	 * @param jobStepKey
	 * @return
	 */
	boolean stepExecutionCompleted(String jobStepKey);
	
	/**
	 * 判断某个Job是否完成
	 * @param jobKey
	 * @return
	 */
	boolean jobCompleted(String jobKey);

}