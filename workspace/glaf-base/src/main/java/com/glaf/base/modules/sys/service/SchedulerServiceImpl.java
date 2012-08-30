package com.glaf.base.modules.sys.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jpage.util.UUID32;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.util.ClassUtils;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.model.*;

public class SchedulerServiceImpl implements SchedulerService {
	protected static final Log logger = LogFactory
			.getLog(SchedulerServiceImpl.class);

	private AbstractSpringDao abstractDao;

	private org.quartz.Scheduler scheduler;

	public SchedulerServiceImpl() {

	}

	public List<Scheduler> getAllSchedulers() {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = " select a from " + Scheduler.class.getSimpleName()
				+ "  as a where a.locked = 0 ";
		List<Object> schedulers = abstractDao.getList(sql, params);
		List<Scheduler> list = new ArrayList<Scheduler>();
		Iterator<Object> iterator = schedulers.iterator();
		while (iterator.hasNext()) {
			Scheduler scheduler = (Scheduler) iterator.next();
			list.add(scheduler);
		}
		return list;
	}

	public Scheduler getScheduler(String taskId) {
		String sql = " select a from " + Scheduler.class.getSimpleName()
				+ "  as a where a.id = :taskId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", taskId);
		List<Object> rows = abstractDao.getList(sql, params);
		if (rows != null && rows.size() > 0) {
			Scheduler scheduler = (Scheduler) rows.get(0);
			return scheduler;
		}
		return null;
	}

	public Scheduler getSchedulerById(String taskId) {
		String sql = " select a from " + Scheduler.class.getSimpleName()
				+ "  as a where a.id = :taskId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", taskId);

		List<Object> rows = abstractDao.getList(sql, params);
		if (rows != null && rows.size() > 0) {
			Scheduler scheduler = (Scheduler) rows.get(0);
			return scheduler;
		}
		return null;
	}

	public List<Scheduler> getSchedulers(String taskType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskType", taskType);
		String sql = " select a from " + Scheduler.class.getSimpleName()
				+ "  as a where a.taskType = :taskType and a.locked = 0 ";
		List<Object> schedulers = abstractDao.getList(sql, params);
		List<Scheduler> list = new ArrayList<Scheduler>();
		Iterator<Object> iterator = schedulers.iterator();
		while (iterator.hasNext()) {
			Scheduler scheduler = (Scheduler) iterator.next();
			list.add(scheduler);
		}
		return list;
	}

	public List<Scheduler> getUserSchedulers(String createBy) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("createBy", createBy);
		String sql = " select a from " + Scheduler.class.getSimpleName()
				+ "  as a where a.createBy = :createBy ";
		List<Object> schedulers = abstractDao.getList(sql, params);
		List<Scheduler> list = new ArrayList<Scheduler>();
		Iterator<Object> iterator = schedulers.iterator();
		while (iterator.hasNext()) {
			Scheduler scheduler = (Scheduler) iterator.next();
			list.add(scheduler);
		}
		return list;
	}

	public void locked(String taskId, int locked) {
		String sql = " select a from " + Scheduler.class.getSimpleName()
				+ "  as a where a.id = :taskId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", taskId);

		List<Object> rows = abstractDao.getList(sql, params);
		if (rows != null && rows.size() > 0) {
			Scheduler scheduler = (Scheduler) rows.get(0);
			scheduler.setLocked(locked);
			abstractDao.update(scheduler);
		}
	}

	/**
	 * 启动调度任务
	 * 
	 * @param taskId
	 */
	public void restart(String taskId) {
		Scheduler model = this.getScheduler(taskId);
		if (model != null && model.isValid()) {
			logger.debug("model:" + model);
			try {

				if (scheduler != null
						&& StringUtils.isNotEmpty(model.getJobClass())) {
					JobDataMap jobDataMap = new JobDataMap();
					jobDataMap.put("taskId", taskId);

					Class<?> jobClass = ClassUtils.forName(model.getJobClass(),
							Thread.currentThread().getContextClassLoader());

					String jobName = "JOB_" + model.getId();
					String jobGroup = "GROUP_" + model.getId();
					String triggerName = "TRIGGER_" + model.getId();
					String triggerGroup = org.quartz.Scheduler.DEFAULT_GROUP;

					JobDetail jobDetail = new JobDetail();
					jobDetail.setJobClass(jobClass);
					jobDetail.setJobDataMap(jobDataMap);
					jobDetail.setName(jobName);
					jobDetail.setGroup(jobGroup);

					Trigger trigger = null;
					int startup = 0;

					if (StringUtils.isNotEmpty(model.getExpression())) {
						trigger = scheduler.getTrigger(triggerName,
								triggerGroup);
						if (trigger == null) {
							trigger = new CronTrigger(triggerName,
									triggerGroup, jobName, jobGroup,
									model.getStartDate(), model.getEndDate(),
									model.getExpression());
							scheduler.scheduleJob(jobDetail, trigger);
							startup = 1;
							logger.info(model.getTaskName() + " has scheduled.");
							if (startup == 1) {
								model.setStartup(startup);
								abstractDao.update(model);
								logger.info(model.getTaskName()
										+ " has startup.");
							}
						} else {
							if (trigger instanceof CronTrigger) {
								CronTrigger cronTrigger = (CronTrigger) trigger;
								cronTrigger.setCronExpression(model
										.getExpression());
								cronTrigger.setStartTime(model.getStartDate());
								cronTrigger.setEndTime(model.getEndDate());
								scheduler.rescheduleJob(triggerName,
										triggerGroup, cronTrigger);
								startup = 1;
								logger.info(model.getTaskName()
										+ " has rescheduled.");
								if (startup == 1) {
									model.setStartup(startup);
									abstractDao.update(model);
									logger.info(model.getTaskName()
											+ " has startup.");
								}
							}
						}
					} else {
						trigger = scheduler.getTrigger(triggerName,
								triggerGroup);
						if (trigger == null) {
							trigger = new SimpleTrigger(triggerName,
									triggerGroup, jobName, jobGroup,
									model.getStartDate(), model.getEndDate(),
									model.getRepeatCount(),
									model.getRepeatInterval() * 1000);
							scheduler.scheduleJob(jobDetail, trigger);
							startup = 1;
							logger.info(model.getTaskName() + " has scheduled.");
							if (startup == 1) {
								model.setStartup(startup);
								abstractDao.update(model);
								logger.info(model.getTaskName()
										+ " has startup.");
							}
						} else {
							if (trigger instanceof SimpleTrigger) {
								SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
								simpleTrigger.setRepeatCount(model
										.getRepeatCount());
								simpleTrigger.setRepeatInterval(model
										.getRepeatInterval() * 1000);
								simpleTrigger
										.setStartTime(model.getStartDate());
								simpleTrigger.setEndTime(model.getEndDate());
								scheduler.rescheduleJob(triggerName,
										triggerGroup, simpleTrigger);
								startup = 1;
								logger.info(model.getTaskName()
										+ " has rescheduled.");
								if (startup == 1) {
									model.setStartup(startup);
									abstractDao.update(model);
									logger.info(model.getTaskName()
											+ " has startup.");
								}
							}
						}

					}
				}
			} catch (org.quartz.SchedulerException ex) {
				throw new RuntimeException(ex);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * 创建调度任务
	 * 
	 * @param scheduler
	 */
	public void save(Scheduler scheduler) {
		if (scheduler.getStartDate() == null) {
			scheduler.setStartDate(new Date());
		}
		if (scheduler.getRepeatInterval() <= 0) {
			scheduler.setRepeatInterval(3600);
		}

		if (StringUtils.isEmpty(scheduler.getId())) {
			scheduler.setId(UUID32.getUUID());
			scheduler.setCreateDate(new Date());
			abstractDao.create(scheduler);
		} else {
			if (this.getScheduler(scheduler.getId()) == null) {
				scheduler.setCreateDate(new Date());
				abstractDao.create(scheduler);
			} else {
				abstractDao.update(scheduler);
			}
		}
	}

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	public void setScheduler(org.quartz.Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * 关闭全部调度任务，同时关闭调度引擎Quartz。
	 */
	public void shutdown() {
		List<Scheduler> schedulers = this.getAllSchedulers();
		Iterator<Scheduler> iterator = schedulers.iterator();
		while (iterator.hasNext()) {
			Scheduler scheduler = iterator.next();
			try {
				this.stop(scheduler.getId());
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		try {
			if (scheduler != null) {
				scheduler.shutdown(true);
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	/**
	 * 启动全部调度任务
	 */
	public void startup() {
		List<Scheduler> schedulers = this.getAllSchedulers();
		Iterator<Scheduler> iterator = schedulers.iterator();
		while (iterator.hasNext()) {
			Scheduler scheduler = iterator.next();
			if (scheduler.isValid() && scheduler.getAutoStartup() == 1) {
				try {
					this.restart(scheduler.getId());
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}
	}

	/**
	 * 停止调度任务
	 * 
	 * @param taskId
	 */
	public void stop(String taskId) {
		Scheduler model = this.getScheduler(taskId);
		if (model != null && scheduler != null) {
			try {
				String triggerName = "TRIGGER_" + model.getId();
				String triggerGroup = org.quartz.Scheduler.DEFAULT_GROUP;
				scheduler.unscheduleJob(triggerName, triggerGroup);
				model.setStartup(0);
				abstractDao.update(model);
				logger.info(model.getTaskName() + " has unscheduled.");
			} catch (org.quartz.SchedulerException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * 停止全部调度任务，但不关闭调度引擎Quartz。
	 */
	public void stopAll() {
		List<Scheduler> schedulers = this.getAllSchedulers();
		Iterator<Scheduler> iterator = schedulers.iterator();
		while (iterator.hasNext()) {
			Scheduler model = iterator.next();
			try {
				this.stop(model.getId());
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
	}
}