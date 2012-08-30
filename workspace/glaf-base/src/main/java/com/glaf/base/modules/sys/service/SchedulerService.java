package com.glaf.base.modules.sys.service;

import java.util.List;

import com.glaf.base.modules.sys.model.Scheduler;

public interface SchedulerService {

	/**
	 * ������������
	 * 
	 * @param scheduler
	 */
	void save(Scheduler scheduler);

	/**
	 * ��ȡȫ����������
	 * 
	 * @return
	 */
	List<Scheduler> getAllSchedulers();

	/**
	 * ���������Ż�ȡ��������
	 * 
	 * @param taskId
	 * @return
	 */
	Scheduler getSchedulerById(String taskId);

	/**
	 * �����������ͻ�ȡ��������
	 * 
	 * @param taskType
	 * @return
	 */
	List<Scheduler> getSchedulers(String taskType);

	/**
	 * ��ȡ�û����ж���ĵ�������
	 * 
	 * @param createBy
	 * @return
	 */
	List<Scheduler> getUserSchedulers(String createBy);

	/**
	 * ������������
	 * 
	 * @param taskId
	 * @param locked
	 */
	void locked(String taskId, int locked);

	/**
	 * ������������
	 * 
	 * @param taskId
	 */
	void restart(String taskId);

	/**
	 * �ر�ȫ����������ͬʱ�رյ�������Quartz��
	 */
	void shutdown();

	/**
	 * ����ȫ����������
	 */
	void startup();

	/**
	 * ֹͣ��������
	 * 
	 * @param taskId
	 */
	void stop(String taskId);

	/**
	 * ֹͣȫ���������񣬵����رյ�������Quartz��
	 */
	void stopAll();
}