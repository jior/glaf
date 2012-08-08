package com.glaf.base.modules.others.service;

import java.util.Date;
import java.util.List;

import com.glaf.base.modules.others.model.WorkCalendar;

public interface WorkCalendarService {

	/**
	 * 检测是否工作日期,false为否,true为是
	 * 
	 * @param date
	 * @return
	 */
	boolean checkWorkDate(Date date);

	/**
	 * 保存
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	boolean create(WorkCalendar bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	boolean delete(WorkCalendar bean);

	/**
	 * 获取对象
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
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	WorkCalendar findById(long id);

	/**
	 * 取有效得工作日期
	 * 
	 * @param startDate
	 *            开始日期
	 * @param interval
	 *            间隔
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
	 * 取有效得工作日期
	 * 
	 * @param startDate
	 *            开始日期
	 * @param interval
	 *            间隔
	 * @return
	 */
	Date getWorkDate2(Date startDate, int interval);

	/**
	 * 返回所有非工作日列表
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	List<Integer> getWorkDateList(int year, int month);

	void initWorkDate();

	/**
	 * 更新
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	boolean update(WorkCalendar bean);

}
