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

package com.glaf.batch.job;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.batch.domain.JobExecution;
import com.glaf.batch.domain.JobInstance;
import com.glaf.batch.domain.StepExecution;
import com.glaf.batch.service.IJobService;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.DateUtils;

public class BatchJob implements IJob, org.quartz.Job {

	protected final static Log logger = LogFactory.getLog(BatchJob.class);

	public void execute(JobExecution execution) {
		IJobService jobService = ContextFactory.getBean("jobService");
		if (execution.getSteps() != null && !execution.getSteps().isEmpty()) {
			List<StepExecution> steps = execution.getSteps();
			Collections.sort(steps);
			/**
			 * 将执行步骤按先后顺序压进栈
			 */
			Stack<StepExecution> stack = new Stack<StepExecution>();
			for (int i = steps.size() - 1; i >= 0; i--) {
				stack.push(steps.get(i));
			}

			while (!stack.empty()) {
				StepExecution step = stack.peek();
				if (!jobService.stepExecutionCompleted(step.getJobStepKey())) {
					boolean success = false;
					int retry = 0;
					while (retry < 3 && !success) {
						retry++;
						try {
							jobService.startStepExecution(step.getJobStepKey());
							if (StringUtils.isNotEmpty(step.getJobClass())) {
								Object object = ClassUtils
										.instantiateObject(step.getJobClass());
								if (object instanceof IStep) {
									IStep ix = (IStep) object;
									ix.execute(step);
									if (jobService.stepExecutionCompleted(step
											.getJobStepKey())) {
										/**
										 * 处理成功，弹出栈，标记成功，结束本次循环
										 */
										stack.pop();
										success = true;
										retry = Integer.MAX_VALUE;
										break;
									}
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					if (!success) {
						throw new RuntimeException(step.getStepKey() + " "
								+ step.getStepName() + " execute failed.");
					}
				}
			}
		}
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String jobName = context.getJobDetail().getKey().getName();
		logger.info("Executing job: " + jobName + " executing at "
				+ DateUtils.getDateTime(new Date()));
		IJobService jobService = ContextFactory.getBean("jobService");
		String jobKey = context.getJobDetail().getJobDataMap()
				.getString("jobKey");
		if (StringUtils.isNotEmpty(jobKey)) {
			JobInstance jobInstance = jobService.getJobInstanceByJobKey(jobKey);
			if (jobInstance != null) {
				List<JobExecution> list = jobService
						.getJobExecutions(jobInstance.getJobInstanceId());
				if (list != null && !list.isEmpty()) {
					for (JobExecution execution : list) {
						this.execute(execution);
					}
				}
			}
		}
	}

}