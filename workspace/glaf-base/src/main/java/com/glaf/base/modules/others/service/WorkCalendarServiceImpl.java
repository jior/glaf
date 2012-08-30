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

public class WorkCalendarServiceImpl implements WorkCalendarService {
	private static final Log logger = LogFactory
			.getLog(WorkCalendarServiceImpl.class);

	private AbstractSpringDao abstractDao;
	private static ArrayList workDateList = new ArrayList();

	/**
	 * 检测是否工作日期,false为否,true为是
	 * 
	 * @param date
	 * @return
	 */
	public boolean checkWorkDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		logger.info("year:" + year + ",month:" + month + ",day:" + day);
		WorkCalendar bean = find(year, month + 1, day);
		if (bean != null)
			return false;
		return true;
	}

	/**
	 * 保存
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	public boolean create(WorkCalendar bean) {
		return abstractDao.create(bean);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		WorkCalendar bean = findById(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	public boolean delete(WorkCalendar bean) {
		return abstractDao.delete(bean);
	}

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
	public WorkCalendar find(int year, int month, int day) {
		WorkCalendar bean = null;
		Object[] values = new Object[] { new Integer(year), new Integer(month),
				new Integer(day) };
		String query = "from WorkCalendar a where a.freeYear=? and a.freeMonth=? and a.freeDay=?";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (WorkCalendar) list.get(0);
		}
		return bean;
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public WorkCalendar findById(long id) {
		return (WorkCalendar) abstractDao
				.find(WorkCalendar.class, new Long(id));
	}

	/**
	 * 取有效得工作日期
	 * 
	 * @param startDate
	 *            开始日期
	 * @param interval
	 *            间隔
	 * @return
	 */
	public Date getWorkDate(Date startDate, int interval) {
		if (startDate == null) {
			return null;
		}
		Date endDate = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		// int day = cal.get(Calendar.DAY_OF_MONTH);

		ArrayList noneWorkDays = new ArrayList();
		if (workDateList.size() == 0)
			initWorkDate();
		if (!workDateList.isEmpty()) {
			Iterator iter = workDateList.iterator();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, ((Integer) iter.next()).intValue());
			noneWorkDays.add(cal.getTime());
		}
		// //取连续2个月的非工作日
		// List list = getWorkDateList(year, month+1);
		// if(list!=null){
		// Iterator iter = list.iterator();
		// while(iter.hasNext()){
		// cal.set(Calendar.YEAR, year);
		// cal.set(Calendar.MONTH, month);
		// cal.set(Calendar.DAY_OF_MONTH, ((Integer)iter.next()).intValue());
		//
		// noneWorkDays.add(cal.getTime());
		// }
		// }
		// list = getWorkDateList(year, month+2);
		// if(list!=null){
		// Iterator iter = list.iterator();
		// while(iter.hasNext()){
		// cal.set(Calendar.YEAR, year);
		// cal.set(Calendar.MONTH, month+1);
		// cal.set(Calendar.DAY_OF_MONTH, ((Integer)iter.next()).intValue());
		//
		// noneWorkDays.add(cal.getTime());
		// }
		// }

		// iter = workDays.iterator();
		// while(iter.hasNext()){
		// Date date = (Date)iter.next();
		// logger.info(glafUtil.dateToString(date));
		// }

		endDate = getWorkDate(startDate, interval, noneWorkDays);
		return endDate;
	}

	/**
	 * 
	 * @param startDate
	 * @param interval
	 * @param noneWorkDays
	 * @return
	 */
	public Date getWorkDate(Date startDate, int interval, List noneWorkDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);

		// 按照间隔天数,循环
		for (int i = 1; i <= interval; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			Date nextDate = cal.getTime();
			if (noneWorkDays.contains(nextDate)) {// 下一天是非工作日,则往后移
				return getWorkDate(nextDate, interval + 1 - i, noneWorkDays);
			}
		}
		return cal.getTime();
	}

	/**
	 * 取有效得工作日期
	 * 
	 * @param startDate
	 *            开始日期
	 * @param interval
	 *            间隔
	 * @return
	 */
	public Date getWorkDate2(Date startDate, int interval) {
		if (startDate == null) {
			return null;
		}
		Date endDate = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		// int day = cal.get(Calendar.DAY_OF_MONTH);

		ArrayList noneWorkDays = new ArrayList();
		// if(workDateList.size()==0) initWorkDate();
		// if(workDateList!=null){
		// Iterator iter = workDateList.iterator();
		// cal.set(Calendar.YEAR, year);
		// cal.set(Calendar.MONTH, month);
		// cal.set(Calendar.DAY_OF_MONTH, ((Integer)iter.next()).intValue());
		// noneWorkDays.add(cal.getTime());
		//
		// }
		// 取连续2个月的非工作日
		List list = getWorkDateList(year, month + 1);
		if (list != null) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.DAY_OF_MONTH,
						((Integer) iter.next()).intValue());
				noneWorkDays.add(cal.getTime());
			}
		}
		list = getWorkDateList(year, month + 2);
		if (list != null) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month + 1);
				cal.set(Calendar.DAY_OF_MONTH,
						((Integer) iter.next()).intValue());
				noneWorkDays.add(cal.getTime());
			}
		}

		endDate = getWorkDate(startDate, interval, noneWorkDays);
		return endDate;
	}

	/**
	 * 返回所有非工作日列表
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List getWorkDateList(int year, int month) {
		Object[] values = new Object[] { new Integer(year), new Integer(month) };
		String query = "select a.freeDay from WorkCalendar a "
				+ "where a.freeYear=? and a.freeMonth=? "
				+ "order by a.freeDay";
		return abstractDao.getList(query, values, null);
	}

	public void initWorkDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);

		workDateList.addAll(getWorkDateList(year - 1, 12));
		for (int i = 1; i <= 12; i++) {
			workDateList.addAll(getWorkDateList(year, i));
		}
		workDateList.addAll(getWorkDateList(year + 1, 1));
	}

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	public boolean update(WorkCalendar bean) {
		return abstractDao.update(bean);
	}

}
