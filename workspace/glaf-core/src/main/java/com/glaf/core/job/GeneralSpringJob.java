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

package com.glaf.core.job;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.base.Scheduler;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.ReflectUtils;

public class GeneralSpringJob extends BaseJob {

	@Override
	public void runJob(JobExecutionContext context)
			throws JobExecutionException {
		logger.debug("------------GeneralSpringJob runJob-----------------");
		String taskId = context.getJobDetail().getJobDataMap()
				.getString("taskId");
		ISysSchedulerService sysSchedulerService = ContextFactory
				.getBean("sysSchedulerService");

		Scheduler scheduler = sysSchedulerService.getSchedulerByTaskId(taskId);
		if (scheduler != null) {
			logger.debug(scheduler.toJsonObject().toJSONString());
			if (StringUtils.isNotEmpty(scheduler.getSpringClass())
					&& StringUtils.isNotEmpty(scheduler.getSpringBeanId())
					&& StringUtils.isNotEmpty(scheduler.getMethodName())) {
				Object object = ContextFactory.getBean(scheduler
						.getSpringBeanId());
				logger.info(object + " call method:"
						+ scheduler.getMethodName());
				ReflectUtils.invoke(object, scheduler.getMethodName());
			}
		}
	}

}
