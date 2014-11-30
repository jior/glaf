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

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.base.Scheduler;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SchedulerLog;
import com.glaf.core.security.Authentication;
import com.glaf.core.service.ISchedulerLogService;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.UUID32;

/**
 * 调度基础类 <br/>
 * 所有的调度都应当继承本类
 */
public abstract class BaseJob implements Job {

	protected final static Log logger = LogFactory.getLog(BaseJob.class);

	/**
	 * 继承本类应该实现抽象方法
	 * 
	 * @param context
	 */
	public abstract void runJob(JobExecutionContext context)
			throws JobExecutionException;

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String taskId = context.getJobDetail().getJobDataMap()
				.getString("taskId");
		ISysSchedulerService sysSchedulerService = ContextFactory
				.getBean("sysSchedulerService");
		ISchedulerLogService schedulerLogService = ContextFactory
				.getBean("schedulerLogService");
		Scheduler scheduler = sysSchedulerService.getSchedulerByTaskId(taskId);
		if (scheduler != null) {
			if (scheduler.getRunType() == 0) {// 每天只能运行一次
				if (scheduler.getRunStatus() != 0) {// 运行成功
					logger.info(scheduler.getTitle()
							+ " 已经运行了，不再重复运行，如需重新运行，请重置运行状态。");
					return;
				}
			} else if (scheduler.getRunType() == 1) {// 每天可重复运行，但只能有一个实例运行
				if (scheduler.getRunStatus() == 1) {// 运行中
					logger.info(scheduler.getTitle() + " 已经在运行中了，请等待执行完成。");
					return;
				}
			}

			SchedulerLog log = new SchedulerLog();
			log.setId(UUID32.getUUID());
			log.setTaskId(scheduler.getTaskId());
			log.setTaskName(scheduler.getTaskName());
			log.setTitle(scheduler.getTitle());
			log.setContent(scheduler.getContent());
			if (Authentication.getAuthenticatedActorId() != null) {
				log.setCreateBy(Authentication.getAuthenticatedActorId());
			} else {
				log.setCreateBy("system");
			}
			log.setStartDate(new Date());

			long start = System.currentTimeMillis();
			boolean success = false;
			Date now = new Date();
			try {
				scheduler.setPreviousFireTime(context.getPreviousFireTime());
				scheduler.setNextFireTime(context.getNextFireTime());
				scheduler.setRunStatus(1);// 设置状态为正在运行
				sysSchedulerService.update(scheduler);
				log.setStatus(1);
				schedulerLogService.save(log);

				logger.info(scheduler.getTitle() + " 下次运行时间: "
						+ DateUtils.getDateTime(context.getNextFireTime()));

				this.runJob(context);
				success = true;
			} catch (Exception ex) {
				success = false;
				ex.printStackTrace();
				logger.error(ex);
				if (ex.getCause() != null) {
					log.setExitMessage(ex.getCause().getMessage());
				} else {
					log.setExitMessage(ex.getMessage());
				}
				// throw new RuntimeException(ex);
			} finally {
				long jobRunTime = System.currentTimeMillis() - start;
				log.setJobRunTime(jobRunTime);
				log.setEndDate(new Date());
				scheduler.setJobRunTime(jobRunTime);
				scheduler.setPreviousFireTime(now);
				if (success) {
					scheduler.setRunStatus(2);// 设置状态为运行成功完成
					log.setStatus(2);
				} else {
					scheduler.setRunStatus(3);// 设置状态为运行失败
					log.setStatus(3);
				}
				if (scheduler.getRunType() == 0 && success) {
					scheduler.setNextFireTime(null);
				}
				sysSchedulerService.update(scheduler);
				schedulerLogService.save(log);
			}
		}
	}

}
