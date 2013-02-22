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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
	 * ������������
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
								schedulerService.save(model);
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
									schedulerService.save(model);
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
								getSchedulerService().save(model);
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
									getSchedulerService().save(model);
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

	public static void setSchedulerService(SchedulerService schedulerService) {
		QuartzUtils.schedulerService = schedulerService;
	}

	/**
	 * �ر�ȫ����������ͬʱ�رյ�������Quartz��
	 */
	public static void shutdown() {
		List<Scheduler> list = getSchedulerService().getAllSchedulers();
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
	 * ����ȫ����������
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
						ex.printStackTrace();
						logger.error(ex);
					}
				}
			}
		}
	}

	/**
	 * ֹͣ��������
	 * 
	 * @param taskId
	 */
	public static void stop(String taskId) {
		Scheduler model = getSchedulerService().getSchedulerById(taskId);
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
					schedulerService.save(model);
					logger.info(model.getTaskName() + " has stop.");
				}
			} catch (org.quartz.SchedulerException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * ֹͣȫ���������񣬵����رյ�������Quartz��
	 */
	public static void stopAll() {
		List<Scheduler> list = getSchedulerService().getAllSchedulers();
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
