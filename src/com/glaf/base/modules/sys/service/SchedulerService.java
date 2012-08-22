package com.glaf.base.modules.sys.service;

import java.util.List;

import com.glaf.base.modules.sys.model.Scheduler;

public interface SchedulerService {

	/**
	 * 创建调度任务
	 * 
	 * @param scheduler
	 */
	void save(Scheduler scheduler);

	/**
	 * 获取全部调度任务
	 * 
	 * @return
	 */
	List<Scheduler> getAllSchedulers();

	/**
	 * 根据任务编号获取调度任务
	 * 
	 * @param taskId
	 * @return
	 */
	Scheduler getSchedulerById(String taskId);

	/**
	 * 根据任务类型获取调度任务
	 * 
	 * @param taskType
	 * @return
	 */
	List<Scheduler> getSchedulers(String taskType);

	/**
	 * 获取用户自行定义的调度任务
	 * 
	 * @param createBy
	 * @return
	 */
	List<Scheduler> getUserSchedulers(String createBy);

	/**
	 * 锁定调度任务
	 * 
	 * @param taskId
	 * @param locked
	 */
	void locked(String taskId, int locked);

	/**
	 * 启动调度任务
	 * 
	 * @param taskId
	 */
	void restart(String taskId);

	/**
	 * 关闭全部调度任务，同时关闭调度引擎Quartz。
	 */
	void shutdown();

	/**
	 * 启动全部调度任务
	 */
	void startup();

	/**
	 * 停止调度任务
	 * 
	 * @param taskId
	 */
	void stop(String taskId);

	/**
	 * 停止全部调度任务，但不关闭调度引擎Quartz。
	 */
	void stopAll();
}