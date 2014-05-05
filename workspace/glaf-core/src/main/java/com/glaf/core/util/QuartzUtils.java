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

package com.glaf.core.util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.glaf.core.base.Scheduler;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.service.ISysSchedulerService;
 

public class QuartzUtils {
	protected final static Log logger = LogFactory.getLog(QuartzUtils.class);

	protected static volatile org.quartz.Scheduler scheduler;

	protected static volatile ISysSchedulerService sysSchedulerService;

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

	public static ISysSchedulerService getSysSchedulerService() {
		if (sysSchedulerService == null) {
			sysSchedulerService = ContextFactory.getBean("sysSchedulerService");
		}
		return sysSchedulerService;
	}

	/**
	 * 启动调度任务
	 * 
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public static void restart(String taskId) {
		Scheduler model = getSysSchedulerService().getSchedulerByTaskId(taskId);
		if (model != null && model.isValid()) {
			logger.debug("scheduler:" + model);
			try {
				if (getQuartzScheduler() != null
						&& StringUtils.isNotEmpty(model.getJobClass())) {
					JobDataMap jobDataMap = new JobDataMap();
					jobDataMap.put("taskId", taskId);

					Class<?> clazz = ClassUtils.loadClass(model.getJobClass());

					Class<Job> jobClass = (Class<Job>) clazz;

					String jobName = "JOB_" + model.getId();
					String jobGroup = "MX_JOB_GROUP";
					String triggerName = "TRIGGER_" + model.getId();
					String triggerGroup = "MX_TRIGGER_GROUP";

					JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
					TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
							triggerGroup);

					JobDetail jobDetail = JobBuilder.newJob(jobClass)
							.withIdentity(jobKey).usingJobData(jobDataMap)
							.build();

					Trigger trigger = null;
					boolean startup = false;

					if (StringUtils.isNotEmpty(model.getExpression())) {
						trigger = getQuartzScheduler().getTrigger(triggerKey);
						if (trigger == null) {
							logger.debug("------------create new CronTrigger----------- ");
							trigger = TriggerBuilder
									.newTrigger()
									.withIdentity(triggerKey)
									.forJob(jobKey)
									.withSchedule(
											CronScheduleBuilder
													.cronSchedule(model
															.getExpression()))
									.build();

							getQuartzScheduler()
									.scheduleJob(jobDetail, trigger);
							startup = true;
							logger.info(model.getTaskName() + " has scheduled.");
							if (startup) {
								model.setStartup(1);
								sysSchedulerService.save(model);
								logger.info(model.getTaskName()
										+ " has startup.");
							}
						} else {
							if (trigger instanceof CronTrigger) {
								logger.debug("------------update CronTrigger------------- ");
								CronTrigger cronTrigger = (CronTrigger) trigger;
								cronTrigger = cronTrigger
										.getTriggerBuilder()
										.withSchedule(
												CronScheduleBuilder.cronSchedule(model
														.getExpression()))
										.build();

								getQuartzScheduler().rescheduleJob(triggerKey,
										cronTrigger);
								startup = true;
								logger.info(model.getTaskName()
										+ " has rescheduled.");
								if (startup) {
									model.setStartup(1);
									sysSchedulerService.save(model);
									logger.info(model.getTaskName()
											+ " has startup.");
								}
							}
						}
					} else {
						trigger = getQuartzScheduler().getTrigger(triggerKey);
						if (trigger == null) {
							logger.debug("------------create new SimpleTrigger----------- ");
							if (model.getRepeatCount() == -1) {
								model.setRepeatCount(Integer.MAX_VALUE);
							}
							trigger = TriggerBuilder
									.newTrigger()
									.startAt(model.getStartDate())
									.endAt(model.getEndDate())
									.forJob(jobKey)
									.withIdentity(triggerKey)
									.withSchedule(
											SimpleScheduleBuilder
													.simpleSchedule()
													.withIntervalInSeconds(
															model.getRepeatInterval())
													.withRepeatCount(
															model.getRepeatCount()))
									.build();

							getQuartzScheduler()
									.scheduleJob(jobDetail, trigger);
							startup = true;
							logger.debug("repeatInterval:"
									+ model.getRepeatInterval());
							logger.debug(model.getTaskName()
									+ " has scheduled.");
							if (startup) {
								model.setStartup(1);
								getSysSchedulerService().save(model);
								logger.info(model.getTaskName()
										+ " has startup.");
							}
						} else {
							if (trigger instanceof SimpleTrigger) {
								logger.debug("------------update SimpleTrigger----------- ");
								SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
								if (model.getRepeatCount() == -1) {
									model.setRepeatCount(Integer.MAX_VALUE);
								}
								simpleTrigger = simpleTrigger
										.getTriggerBuilder()
										.startAt(model.getStartDate())
										.endAt(model.getEndDate())
										.forJob(jobKey)
										.withIdentity(triggerKey)
										.withSchedule(
												SimpleScheduleBuilder
														.simpleSchedule()
														.withIntervalInSeconds(
																model.getRepeatInterval())
														.withRepeatCount(
																model.getRepeatCount()))
										.build();
								getQuartzScheduler().rescheduleJob(triggerKey,
										simpleTrigger);
								startup = true;
								logger.info(model.getTaskName()
										+ " has rescheduled.");
								if (startup) {
									model.setStartup(1);
									getSysSchedulerService().save(model);
									logger.info(model.getTaskName()
											+ " has startup.");
								}
							}
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

	public static void setScheduler(org.quartz.Scheduler scheduler) {
		QuartzUtils.scheduler = scheduler;
	}

	public static void setSysSchedulerService(ISysSchedulerService sysSchedulerService) {
		QuartzUtils.sysSchedulerService = sysSchedulerService;
	}

	/**
	 * 关闭全部调度任务，同时关闭调度引擎Quartz。
	 */
	public static void shutdown() {
		List<Scheduler> list = getSysSchedulerService().getAllSchedulers();
		Iterator<Scheduler> iterator = list.iterator();
		while (iterator.hasNext()) {
			Scheduler model = iterator.next();
			try {
				stop(model.getId());
			} catch (Exception ex) {
				ex.printStackTrace();
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
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	/**
	 * 启动全部调度任务
	 */
	public static void startup() {
		if (getQuartzScheduler() != null) {
			List<Scheduler> list = getSysSchedulerService().getAllSchedulers();
			Iterator<Scheduler> iterator = list.iterator();
			while (iterator.hasNext()) {
				Scheduler model = iterator.next();
				if (model.isValid() && model.isSchedulerAutoStartup() ) {
					try {
						restart(model.getId());
					} catch (Exception ex) {
						ex.printStackTrace();
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
		Scheduler model = getSysSchedulerService().getSchedulerByTaskId(taskId);
		if (model != null) {
			try {
				if (getQuartzScheduler() != null) {
					String jobName = "JOB_" + model.getId();
					String jobGroup = "MX_JOB_GROUP";
					String triggerName = "TRIGGER_" + model.getId();
					String triggerGroup = "MX_TRIGGER_GROUP";

					JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
					TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
							triggerGroup);
					getQuartzScheduler().unscheduleJob(triggerKey);
					getQuartzScheduler().pauseJob(jobKey);
					model.setStartup(0);
					sysSchedulerService.save(model);
					logger.info(model.getTaskName() + " has stop.");
				}
			} catch (org.quartz.SchedulerException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * 停止全部调度任务，但不关闭调度引擎Quartz。
	 */
	public static void stopAll() {
		List<Scheduler> list = getSysSchedulerService().getAllSchedulers();
		Iterator<Scheduler> iterator = list.iterator();
		while (iterator.hasNext()) {
			Scheduler model = iterator.next();
			try {
				stop(model.getId());
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

	private QuartzUtils() {

	}

}
