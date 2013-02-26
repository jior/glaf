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

package com.glaf.dts.business;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.dts.domain.TransformTask;
import com.glaf.dts.service.ITransformTaskService;

public class MxTransformScheduler {
	public final static Logger log = LoggerFactory
			.getLogger(MxTransformScheduler.class);

	protected ITransformTaskService transformTaskService;

	protected List<String> taskIds;

	public MxTransformScheduler(List<String> taskIds) {
		this.taskIds = taskIds;
		transformTaskService = ContextFactory.getBean("transformTaskService");
	}

	public void run() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		if (taskIds != null && !taskIds.isEmpty()) {
			for (int i = 0; i < taskIds.size(); i++) {
				String taskId = taskIds.get(i);
				TransformTask task = transformTaskService
						.getTransformTask(taskId);
				if (task == null || task.getStatus() == 9) {
					continue;
				}
				long time = System.currentTimeMillis() + 5000L + (i * 1000);
				Date date = new Date(time);
				log.info(taskId + " will start time "
						+ DateUtils.getDateTime(date));
				String jobName = "JOB_" + taskId;
				String jobGroup = "GROUP_" + taskId;
				String triggerName = "TRIGGER_" + taskId;
				String triggerGroup = "GROUP_" + task.getQueryId();
				JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
				TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
						triggerGroup);
				try {
					Trigger trigger = null;

					trigger = sched.getTrigger(triggerKey);
					if (trigger == null) {
						trigger = TriggerBuilder
								.newTrigger()
								.withIdentity(triggerKey)
								.forJob(jobKey)
								.startAt(date)
								.withSchedule(
										SimpleScheduleBuilder
												.simpleSchedule()
												.withIntervalInSeconds(2 * 1000)
												.withRepeatCount(3)).build();
						JobDetail jobDetail = JobBuilder
								.newJob(MxTransformJob.class)
								.withIdentity(jobKey).requestRecovery(true)
								.build();

						jobDetail.getJobDataMap().put("TransformJob_taskId",
								taskId);

						sched.scheduleJob(jobDetail, trigger);
					} else {
						trigger = trigger.getTriggerBuilder().startAt(date)
								.build();
						sched.rescheduleJob(triggerKey, trigger);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		try {
			sched.start();
		} catch (SchedulerException ex) {
			ex.printStackTrace();
		}
	}
}