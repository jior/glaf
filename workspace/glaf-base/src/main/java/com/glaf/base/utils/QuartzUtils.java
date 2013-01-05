/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.base.utils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;

import com.glaf.base.context.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.service.*;

public class QuartzUtils {
	protected final static Log logger = LogFactory.getLog(QuartzUtils.class);

	protected static org.quartz.Scheduler scheduler;

	protected static SchedulerService schedulerService;

	public static org.quartz.Scheduler getQuartzScheduler() {
		if (scheduler == null) {
			scheduler = ContextFactory.getBean("scheduler");
			// org.quartz.SchedulerFactory sf = new
			// org.quartz.impl.StdSchedulerFactory();
			// try {
			// scheduler = sf.getScheduler();
			// scheduler.start();
			// logger.info("------- Initialization Complete --------");
			// logger.info("------- Scheduling Jobs ----------------");
			// } catch (org.quartz.SchedulerException ex) {
			// ex.printStackTrace();
			// }
		}
		return scheduler;
	}

	public static SchedulerService getSchedulerService() {
		if (schedulerService == null) {
			schedulerService = ContextFactory.getBean("schedulerService");
		}
		return schedulerService;
	}

	/**
	 * 启动调度任务
	 * 
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public static void restart(String taskId) {
		Scheduler model = getSchedulerService().getSchedulerById(taskId);
		if (model != null && model.isValid()) {
			logger.debug("scheduler:" + model);
			try {
				if (getQuartzScheduler() != null
						&& StringUtils.isNotEmpty(model.getJobClass())) {
					JobDataMap jobDataMap = new JobDataMap();
					jobDataMap.put("taskId", taskId);

					Class<?> clazz = ClassUtil.loadClass(model.getJobClass());

					Class<Job> jobClass = (Class<Job>) clazz;

					String jobName = "JOB_" + model.getId();
					String jobGroup = "JOB_GROUP";
					String triggerName = "TRIGGER_" + model.getId();
					String triggerGroup = "TRIGGER_GROUP";

					if (StringUtils.isNotEmpty(model.getExpression())) {
						jobGroup = "CRON_GROUP_" + model.getId();
						jobName = "CRON_JOB_" + model.getId();
						triggerName = "CRON_TRIGGER_" + model.getId();
						triggerGroup = "CRON_GROUP";
					}

					JobDetail jobDetail = new JobDetail();
					jobDetail.setJobClass(jobClass);
					jobDetail.setJobDataMap(jobDataMap);
					jobDetail.setName(jobName);
					jobDetail.setGroup(jobGroup);

					boolean startup = false;

					if (StringUtils.isNotEmpty(model.getExpression())) {
						CronTrigger trigger = new CronTrigger(triggerName,
								triggerGroup, jobName, jobGroup,
								model.getExpression());
						logger.debug("------------create new CronTrigger----------- ");
						getQuartzScheduler().addJob(jobDetail, true);
						Date ft = getQuartzScheduler().scheduleJob(trigger);
						logger.info(jobDetail.getFullName()
								+ " has been scheduled to run at: "
								+ DateTools.getDateTime(ft)
								+ " and repeat based on expression: "
								+ trigger.getCronExpression());
						startup = true;
						logger.info(model.getTaskName() + " has scheduled.");
						if (startup) {
							model.setStartup(1);
							getSchedulerService().save(model);
							logger.info(model.getTaskName() + " has startup.");
						}
					} else {
						logger.debug("------------create new SimpleTrigger----------- ");
						if (model.getRepeatCount() == -1) {
							model.setRepeatCount(Integer.MAX_VALUE);
						}
						SimpleTrigger trigger = new SimpleTrigger(triggerName,
								triggerGroup, jobName, jobGroup,
								model.getStartDate(), model.getEndDate(),
								model.getRepeatCount(),
								model.getRepeatInterval() * 1000);

						getQuartzScheduler().addJob(jobDetail, true);
						Date ft = getQuartzScheduler().scheduleJob(trigger);
						logger.info(jobDetail.getFullName()
								+ " has been scheduled to run at: "
								+ DateTools.getDateTime(ft)
								+ " and repeat interval: "
								+ trigger.getRepeatInterval());
						startup = true;
						logger.debug("repeatInterval:"
								+ model.getRepeatInterval());
						logger.debug(model.getTaskName() + " has scheduled.");
						if (startup) {
							model.setStartup(1);
							getSchedulerService().save(model);
							logger.info(model.getTaskName() + " has startup.");
						}
					}
				}
			} catch (org.quartz.SchedulerException ex) {
				logger.error(ex);
				throw new RuntimeException(ex);
			} catch (Exception ex) {
				logger.error(ex);
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * 关闭全部调度任务，同时关闭调度引擎Quartz。
	 */
	public static void shutdown() {
		List<Scheduler> list = getSchedulerService().getAllSchedulers();
		Iterator<Scheduler> iterator = list.iterator();
		while (iterator.hasNext()) {
			Scheduler model = iterator.next();
			try {
				stop(model.getId());
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		try {
			if (getQuartzScheduler() != null) {
				logger.warn("------------------------------------------------");
				logger.warn("shutdown scheduler!!!");
				getQuartzScheduler().shutdown(false);
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	/**
	 * 启动全部调度任务
	 */
	public static void startup() {
		if (getQuartzScheduler() != null) {
			List<Scheduler> list = getSchedulerService().getAllSchedulers();
			Iterator<Scheduler> iterator = list.iterator();
			while (iterator.hasNext()) {
				Scheduler model = iterator.next();
				if (model.isValid() && model.getAutoStartup() == 1) {
					try {
						restart(model.getId());
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
			}
		}
	}

	/**
	 * 停止调度任务
	 * 
	 * @param taskId
	 */
	public static void stop(String taskId) {
		Scheduler model = getSchedulerService().getSchedulerById(taskId);
		if (model != null) {
			try {
				if (getQuartzScheduler() != null) {
					String jobName = "JOB_" + model.getId();
					String jobGroup = "JOB_GROUP";
					String triggerName = "TRIGGER_" + model.getId();
					String triggerGroup = "TRIGGER_GROUP";

					if (StringUtils.isNotEmpty(model.getExpression())) {
						jobGroup = "CRON_GROUP_" + model.getId();
						jobName = "CRON_JOB_" + model.getId();
						triggerName = "CRON_TRIGGER_" + model.getId();
						triggerGroup = "CRON_GROUP";
					}

					getQuartzScheduler().unscheduleJob(triggerName,
							triggerGroup);
					getQuartzScheduler().pauseJob(jobName, jobGroup);
					model.setStartup(0);
					getSchedulerService().save(model);
					logger.info(model.getTaskName() + " has stop.");
				}
			} catch (org.quartz.SchedulerException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * 停止全部调度任务，但不关闭调度引擎Quartz。
	 */
	public static void stopAll() {
		List<Scheduler> list = getSchedulerService().getAllSchedulers();
		Iterator<Scheduler> iterator = list.iterator();
		while (iterator.hasNext()) {
			Scheduler model = iterator.next();
			try {
				stop(model.getId());
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
	}

	private QuartzUtils() {

	}

}
