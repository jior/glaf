package com.glaf.base.modules.others.service;

import java.util.Date;
import java.util.List;

import com.glaf.base.modules.others.model.WorkCalendar;

public interface WorkCalendarService {

	/**
	 * ����Ƿ�������,falseΪ��,trueΪ��
	 * 
	 * @param date
	 * @return
	 */
	boolean checkWorkDate(Date date);

	/**
	 * ����
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	boolean create(WorkCalendar bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	boolean delete(WorkCalendar bean);

	/**
	 * ��ȡ����
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @param day
	 *            int
	 * @return WorkCalendar
	 */
	WorkCalendar find(int year, int month, int day);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	WorkCalendar findById(long id);

	/**
	 * ȡ��Ч�ù�������
	 * 
	 * @param startDate
	 *            ��ʼ����
	 * @param interval
	 *            ���
	 * @return
	 */
	Date getWorkDate(Date startDate, int interval);

	/**
	 * 
	 * @param startDate
	 * @param interval
	 * @param noneWorkDays
	 * @return
	 */
	Date getWorkDate(Date startDate, int interval, List<Date> noneWorkDays);

	/**
	 * ȡ��Ч�ù�������
	 * 
	 * @param startDate
	 *            ��ʼ����
	 * @param interval
	 *            ���
	 * @return
	 */
	Date getWorkDate2(Date startDate, int interval);

	/**
	 * �������зǹ������б�
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	List<Integer> getWorkDateList(int year, int month);

	void initWorkDate();

	/**
	 * ����
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	boolean update(WorkCalendar bean);

}
