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
	 * ���ĳ������
	 * 
	 * @param jobStepKey
	 */
	@Transactional
	void completeStepExecution(String jobStepKey);

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteJobInstanceById(long jobInstanceId);

	/**
	 * ����Keyɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteJobInstanceByJobKey(String jobKey);

	/**
	 * ����ִ��Id��ȡִ����Ϣ
	 * 
	 * @param jobExecutionId
	 * @return
	 */
	JobExecution getJobExecutionById(long jobExecutionId);

	/**
	 * ����Jobʵ����Ż�ȡִ����Ϣ
	 * 
	 * @param jobInstanceId
	 * @return
	 */
	List<JobExecution> getJobExecutions(long jobInstanceId);

	/**
	 * ����������ȡJobʵ��
	 * 
	 * @return
	 */
	JobInstance getJobInstanceById(long jobInstanceId);

	/**
	 * ����������ȡJobʵ��
	 * 
	 * @return
	 */
	JobInstance getJobInstanceByIdWithAll(long jobInstanceId);

	/**
	 * ����Job Key��ȡJobʵ��
	 * 
	 * @param jobKey
	 * @return
	 */
	JobInstance getJobInstanceByJobKey(String jobKey);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getJobInstanceCount(Map<String, Object> parameter);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getJobInstanceCountByQueryCriteria(JobInstanceQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<JobInstance> getJobInstances(Map<String, Object> parameter);

	/**
	 * ��ȡһҳJobʵ��
	 * 
	 * @param jobName
	 * @param start
	 * @param limit
	 * @return
	 */
	List<JobInstance> getJobInstances(String jobName, int start, int limit);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<JobInstance> getJobInstancesByQueryCriteria(int start, int pageSize,
			JobInstanceQuery query);

	/**
	 * ����ID��ȡִ�в���
	 * 
	 * @param stepExecutionId
	 * @return
	 */
	StepExecution getStepExecutionById(long stepExecutionId);

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
	List<StepExecution> getStepExecutions(long jobInstanceId);

	/**
	 * �ж�ĳ��Job�Ƿ����
	 * 
	 * @param jobKey
	 * @return
	 */
	boolean jobCompleted(String jobKey);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<JobInstance> list(JobInstanceQuery query);

	/**
	 * ����Jobִ����Ϣ
	 * 
	 * @return
	 */
	@Transactional
	void saveJobExecution(JobExecution jobExecution);

	/**
	 * ����Jobʵ����Ϣ
	 * 
	 * @return
	 */
	@Transactional
	void saveJobInstance(JobInstance jobInstance);

	/**
	 * ����ִ�в�����Ϣ
	 * 
	 * @return
	 */
	@Transactional
	void saveStepExecution(StepExecution stepExecution);

	/**
	 * ��ʼĳ������
	 * 
	 * @param jobStepKey
	 */
	@Transactional
	void startStepExecution(String jobStepKey);

	/**
	 * �ж�ĳ�������Ƿ�ɹ����
	 * 
	 * @param jobStepKey
	 * @return
	 */
	boolean stepExecutionCompleted(String jobStepKey);

}