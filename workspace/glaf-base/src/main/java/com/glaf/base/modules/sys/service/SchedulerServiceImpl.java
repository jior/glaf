package com.glaf.base.modules.sys.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jpage.util.UUID32;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.model.*;

public class SchedulerServiceImpl implements SchedulerService {
	protected static final Log logger = LogFactory
			.getLog(SchedulerServiceImpl.class);

	private AbstractSpringDao abstractDao;

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

}