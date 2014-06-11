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

package com.glaf.mail.business;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.ThreadFactory;
import com.glaf.mail.MailSender;
import com.glaf.mail.domain.MailCount;
import com.glaf.mail.domain.MailItem;
import com.glaf.mail.domain.MailStorage;
import com.glaf.mail.domain.MailTask;
import com.glaf.mail.job.MailSendJob;
import com.glaf.mail.job.MailTaskJob;
import com.glaf.mail.query.MailItemQuery;
import com.glaf.mail.query.MailTaskQuery;
import com.glaf.mail.service.IMailCountService;
import com.glaf.mail.service.IMailDataService;
import com.glaf.mail.service.IMailStorageService;
import com.glaf.mail.service.IMailTaskService;
import com.glaf.mail.util.MailStorageType;

@Component("mailDataFacede")
public class MailDataFacedeImpl implements MailDataFacede {
	protected static Log logger = LogFactory.getLog(MailDataFacedeImpl.class);

	protected static final Configuration conf = BaseConfiguration.create();

	protected IMailStorageService mailStorageService;

	protected IMailTaskService mailTaskService;

	protected IMailCountService mailCountService;

	protected MailSender mailSender;

	protected org.quartz.Scheduler scheduler;

	/**
	 * 添加存储邮件信息的数据表
	 * 
	 * @param dataTable
	 *            表名称
	 * @param storage
	 *            存储类型
	 */
	public void addDataTable(String dataTable, MailStorage storage) {
		String storageType = storage.getStorageType();
		String typeName = MailStorageType.getTypeName(storageType);
		logger.debug("storage:" + storage);

		if (typeName != null) {
			String className = conf.get("mail.storage.creator." + typeName);
			if (StringUtils.isNotEmpty(className)) {
				Object object = ClassUtils.instantiateObject(className);
				if (object instanceof StorageCreator) {
					StorageCreator creator = (StorageCreator) object;
					try {
						creator.createStorage(storage);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			} else {
				StorageCreator creator = new RdbmsCreator();
				try {
					creator.createStorage(storage);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}

	/**
	 * 执行统计
	 */
	public void executeCount() {
		MailTaskQuery q = new MailTaskQuery();
		q.setLocked(0);
		List<MailTask> list = mailTaskService.list(q);
		for (MailTask task : list) {
			/**
			 * 未开始的任务不统计
			 */
			if (task.getStartDate().getTime() > (new java.util.Date().getTime())) {
				continue;
			}

			logger.info("start count " + task.getSubject());

			MailItemQuery query = new MailItemQuery();
			query.taskId(task.getId());
			query.setTableName(task.getStorage().getDataTable());

			/**
			 * 已经完成的任务，不需要统计发送状态
			 */
			if (task.getEndDate().getTime() > (new java.util.Date().getTime())) {
				try {
					List<MailCount> rows = this.getMailSendStatusCount(query);
					mailCountService.save(task.getId(), "MSS", rows);
				} catch (Exception ex) {
					logger.error(ex);
				}
				try {
					List<MailCount> rows2 = this
							.getMailAccountSendStatusCount(query);
					mailCountService.save(task.getId(), "MASS", rows2);
				} catch (Exception ex) {
					logger.error(ex);
				}
			}

			try {
				List<MailCount> rows = this.getMailReceiveStatusCount(query);
				mailCountService.save(task.getId(), "MRS", rows);
			} catch (Exception ex) {
				logger.error(ex);
			}
			try {
				List<MailCount> rows2 = this
						.getMailAccountReceiveStatusCount(query);
				mailCountService.save(task.getId(), "MARS", rows2);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
	}

	/**
	 * 获取邮件帐户接收状况汇总
	 * 
	 * @param query
	 * @return
	 */
	public List<MailCount> getMailAccountReceiveStatusCount(MailItemQuery query) {
		Assert.notNull("taskId is required", query.getTaskId());
		MailTask task = mailTaskService.getMailTask(query.getTaskId());
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			return mailDataService.getMailAccountReceiveStatusCount(query);
		}
		return null;
	}

	/**
	 * 获取邮件帐户发送状况汇总
	 * 
	 * @param query
	 * @return
	 */
	public List<MailCount> getMailAccountSendStatusCount(MailItemQuery query) {
		Assert.notNull("taskId is required", query.getTaskId());
		MailTask task = mailTaskService.getMailTask(query.getTaskId());
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			return mailDataService.getMailAccountSendStatusCount(query);
		}
		return null;
	}

	public int getMailCount(MailItemQuery query) {
		Assert.notNull("taskId is required", query.getTaskId());
		MailTask task = mailTaskService.getMailTask(query.getTaskId());
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			long start = System.currentTimeMillis();
			int count = mailDataService.getMailCount(query);
			long times = System.currentTimeMillis() - start;
			logger.debug("query total times:" + times + " ms.");
			logger.debug("total size:" + count);
			return count;
		}
		return 0;
	}

	public MailItem getMailItem(String taskId, String itemId) {
		Assert.notNull("taskId is required", taskId);
		Assert.notNull("itemId is required", itemId);
		MailTask task = mailTaskService.getMailTask(taskId);
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			return mailDataService.getMailItem(taskId, itemId);
		}
		return null;
	}

	/**
	 * 获取某个任务的邮件列表
	 * 
	 * @param taskId
	 * @param query
	 * @return
	 */
	public List<MailItem> getMailItems(MailItemQuery query) {
		Assert.notNull("taskId is required", query.getTaskId());
		MailTask task = mailTaskService.getMailTask(query.getTaskId());
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			long start = System.currentTimeMillis();
			List<MailItem> list = mailDataService.getMailItems(query);
			long times = System.currentTimeMillis() - start;
			logger.debug("storageType:" + storageType + " \ntaskId:"
					+ query.getTaskId());
			logger.debug("query times:" + times + " ms.");
			logger.debug("items size:" + list.size());
			return list;
		}
		return null;
	}

	/**
	 * 获取邮件接收状况汇总
	 * 
	 * @param query
	 * @return
	 */
	public List<MailCount> getMailReceiveStatusCount(MailItemQuery query) {
		Assert.notNull("taskId is required", query.getTaskId());
		MailTask task = mailTaskService.getMailTask(query.getTaskId());
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			long start = System.currentTimeMillis();
			List<MailCount> list = mailDataService
					.getMailReceiveStatusCount(query);
			long times = System.currentTimeMillis() - start;
			logger.debug("query times:" + times);
			return list;
		}
		return null;
	}

	/**
	 * 获取邮件发送状况汇总
	 * 
	 * @param query
	 * @return
	 */
	public List<MailCount> getMailSendStatusCount(MailItemQuery query) {
		Assert.notNull("taskId is required", query.getTaskId());
		MailTask task = mailTaskService.getMailTask(query.getTaskId());
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			return mailDataService.getMailSendStatusCount(query);
		}
		return null;
	}

	public org.quartz.Scheduler getScheduler() {
		if (scheduler == null) {
			scheduler = ContextFactory.getBean("scheduler");
		}
		return scheduler;
	}

	/**
	 * 保存某个任务的邮件
	 * 
	 * @param taskId
	 * @param mailAddresses
	 */

	public void saveMails(String taskId, List<String> mailAddresses) {
		Assert.notNull("taskId is required", taskId);
		MailTask task = mailTaskService.getMailTask(taskId);
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			mailDataService.saveMails(taskId, mailAddresses);
		}

	}

	public void scheduleAllTasks() {
		MailTaskQuery q = new MailTaskQuery();
		q.setLocked(0);
		List<MailTask> list = mailTaskService.list(q);

		int i = 0;
		Date date = null;

		for (MailTask task : list) {
			/**
			 * 未开始的任务不处理
			 */
			if (task.getStartDate().getTime() > (new java.util.Date().getTime())) {
				continue;
			}
			/**
			 * 已经到期的任务也不处理
			 */
			if (task.getEndDate().getTime() < (new java.util.Date().getTime())) {
				continue;
			}

			i++;
			long time = System.currentTimeMillis()
					+ ((i + task.getDelayTime()) * 1000);
			date = new Date(time);
			String jobName = "JOB_" + task.getId();
			String jobGroup = "GROUP_" + task.getId();
			String triggerName = "TRIGGER_" + task.getId();
			String triggerGroup = "GROUP_" + task.getId();
			JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
					triggerGroup);
			try {
				Trigger trigger = getScheduler().getTrigger(triggerKey);
				if (trigger == null) {
					trigger = TriggerBuilder
							.newTrigger()
							.withIdentity(triggerKey)
							.forJob(jobKey)
							.startAt(date)
							.withSchedule(
									SimpleScheduleBuilder.simpleSchedule()
											.withIntervalInSeconds(2 * 1000)
											.withRepeatCount(3)).build();
					JobDetail jobDetail = JobBuilder.newJob(MailTaskJob.class)
							.withIdentity(jobKey).build();

					jobDetail.getJobDataMap().put("taskId", task.getId());
					getScheduler().scheduleJob(jobDetail, trigger);
					logger.debug("schedule job " + jobName);
				} else {
					trigger = trigger.getTriggerBuilder().startAt(date).build();
					getScheduler().rescheduleJob(triggerKey, trigger);
					logger.debug("reschedule job " + jobName);
				}
			} catch (SchedulerException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void scheduleMailItems() {
		MailTaskQuery q = new MailTaskQuery();
		q.setLocked(0);
		List<MailTask> list = mailTaskService.list(q);

		for (MailTask task : list) {
			/**
			 * 未开始的任务不统计
			 */
			if (task.getStartDate().getTime() > (new java.util.Date().getTime())) {
				continue;
			}

			logger.info("start send " + task.getSubject());

			MailItemQuery query = new MailItemQuery();
			query.taskId(task.getId());
			query.setTableName(task.getStorage().getDataTable());
			query.setPageNo(0);
			query.setPageSize(100);
			query.sendStatusLessThanOrEqual(0);
			query.retryTimesLessThanOrEqual(5);
			List<MailItem> items = this.getMailItems(query);
			if (items != null && !items.isEmpty()) {
				int i = 0;
				Date date = null;
				for (MailItem item : items) {
					i++;
					long time = System.currentTimeMillis()
							+ ((i + task.getDelayTime()) * 1000);
					date = new Date(time);
					String jobName = "JOB_" + item.getId();
					String jobGroup = "GROUP_" + task.getId();
					String triggerName = "TRIGGER_" + item.getId();
					String triggerGroup = "GROUP_" + task.getId();
					JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
					TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
							triggerGroup);
					try {
						Trigger trigger = getScheduler().getTrigger(triggerKey);
						if (trigger == null) {
							trigger = TriggerBuilder
									.newTrigger()
									.withIdentity(triggerKey)
									.forJob(jobKey)
									.startAt(date)
									.withSchedule(
											SimpleScheduleBuilder
													.simpleSchedule()
													.withIntervalInSeconds(
															2 * 1000)
													.withRepeatCount(3))
									.build();
							JobDetail jobDetail = JobBuilder
									.newJob(MailSendJob.class)
									.withIdentity(jobKey).build();

							jobDetail.getJobDataMap().put("taskId",
									task.getId());
							jobDetail.getJobDataMap().put("itemId",
									item.getId());
							getScheduler().scheduleJob(jobDetail, trigger);
							logger.debug("schedule job " + jobName);
						} else {
							trigger = trigger.getTriggerBuilder().startAt(date)
									.build();
							getScheduler().rescheduleJob(triggerKey, trigger);
							logger.debug("reschedule job " + jobName);
						}
					} catch (SchedulerException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param taskId
	 * @param itemId
	 */
	public void sendMail(String taskId, String itemId) {
		Assert.notNull("taskId is required", taskId);
		Assert.notNull("itemId is required", itemId);
		MailTask task = mailTaskService.getMailTaskWithAccounts(taskId);
		if (task == null || task.getAccounts().isEmpty()) {
			return;
		}
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}

		if (mailDataService != null) {
			MailItem mailItem = mailDataService.getMailItem(taskId, itemId);
			MailSendThread thread = new MailSendThread(this, task, mailItem);
			ThreadFactory.run(thread);
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param taskId
	 */
	public void sendTaskMails(String taskId) {
		Assert.notNull("taskId is required", taskId);
		MailTask task = mailTaskService.getMailTaskWithAccounts(taskId);
		if (task == null || task.getAccounts().isEmpty()) {
			return;
		}
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			MailItemQuery query = new MailItemQuery();
			query.taskId(task.getId());
			query.setTableName(stg.getDataTable());
			query.setPageNo(0);
			query.setPageSize(100);
			query.sendStatusLessThanOrEqual(0);
			query.retryTimesLessThanOrEqual(5);
			query.setSortField("retryTimes");
			List<MailItem> items = this.getMailItems(query);
			if (items != null && !items.isEmpty()) {
				for (MailItem item : items) {
					String itemId = item.getId();
					MailItem mailItem = mailDataService.getMailItem(taskId,
							itemId);
					MailSendThread thread = new MailSendThread(this, task,
							mailItem);
					try {
						ThreadFactory.run(thread);
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@javax.annotation.Resource
	public void setMailCountService(IMailCountService mailCountService) {
		this.mailCountService = mailCountService;
	}

	@javax.annotation.Resource
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@javax.annotation.Resource
	public void setMailStorageService(IMailStorageService mailStorageService) {
		this.mailStorageService = mailStorageService;
	}

	@javax.annotation.Resource
	public void setMailTaskService(IMailTaskService mailTaskService) {
		this.mailTaskService = mailTaskService;
	}

	public void setScheduler(org.quartz.Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * 更新邮件信息
	 * 
	 * @param taskId
	 * @param item
	 */
	public void updateMail(String taskId, MailItem item) {
		Assert.notNull("taskId is required", taskId);
		MailTask task = mailTaskService.getMailTask(taskId);
		MailStorage stg = mailStorageService
				.getMailStorage(task.getStorageId());
		String storageType = stg.getStorageType();
		IMailDataService mailDataService = null;
		String typeName = MailStorageType.getTypeName(storageType);
		if (StringUtils.isNotEmpty(typeName)) {
			String beanId = conf.get("mail.storage.service." + typeName
					+ ".beanId");
			if (StringUtils.isNotEmpty(beanId)) {
				mailDataService = ContextFactory.getBean(beanId);
			} else {
				mailDataService = ContextFactory.getBean("mailDataService");
			}
		}
		if (mailDataService != null) {
			mailDataService.updateMail(taskId, item);
		}
	}
}