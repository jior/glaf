package com.glaf.base.modules.others.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.others.model.WorkCalendar;

public interface WorkCalendarService {
	 
	
	/**
	 * 保存
	 * @param bean WorkCalendar
	 * @return boolean
	 */
	  boolean create(WorkCalendar bean);

	/**
	 * 更新
	 * @param bean WorkCalendar
	 * @return boolean
	 */
	  boolean update(WorkCalendar bean);

	/**
	 * 删除
	 * @param bean WorkCalendar
	 * @return boolean
	 */
	  boolean delete(WorkCalendar bean);

	/**
	 * 删除
	 * @param id int
	 * @return boolean
	 */
	  boolean delete(long id);
	
	/**
	 * 获取对象
	 * @param id
	 * @return
	 */
	  WorkCalendar findById(long id);

	/**
	 * 获取对象
	 * 
	 * @param year int
	 * @param month int
	 * @param day int
	 * @return WorkCalendar
	 */
	  WorkCalendar find(int year, int month, int day);

	/**
	 * 返回所有非工作日列表
	 * @param year
	 * @param month
	 * @return
	 */
	  List getWorkDateList(int year, int month);
	/**
	 * 取有效得工作日期
	 * @param startDate 开始日期
	 * @param interval 间隔
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
	  Date getWorkDate(Date startDate, int interval, List noneWorkDays);
	/**
	 * 检测是否工作日期,false为否,true为是
	 * @param date
	 * @return
	 */
	  boolean checkWorkDate(Date date);
	
 	  void initWorkDate();
	
	/**
	 * 取有效得工作日期
	 * @param startDate 开始日期
	 * @param interval 间隔
	 * @return
	 */
	  Date getWorkDate2(Date startDate, int interval) ;
	
}
