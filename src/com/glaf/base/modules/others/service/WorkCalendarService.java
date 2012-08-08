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
	 * ����
	 * @param bean WorkCalendar
	 * @return boolean
	 */
	  boolean create(WorkCalendar bean);

	/**
	 * ����
	 * @param bean WorkCalendar
	 * @return boolean
	 */
	  boolean update(WorkCalendar bean);

	/**
	 * ɾ��
	 * @param bean WorkCalendar
	 * @return boolean
	 */
	  boolean delete(WorkCalendar bean);

	/**
	 * ɾ��
	 * @param id int
	 * @return boolean
	 */
	  boolean delete(long id);
	
	/**
	 * ��ȡ����
	 * @param id
	 * @return
	 */
	  WorkCalendar findById(long id);

	/**
	 * ��ȡ����
	 * 
	 * @param year int
	 * @param month int
	 * @param day int
	 * @return WorkCalendar
	 */
	  WorkCalendar find(int year, int month, int day);

	/**
	 * �������зǹ������б�
	 * @param year
	 * @param month
	 * @return
	 */
	  List getWorkDateList(int year, int month);
	/**
	 * ȡ��Ч�ù�������
	 * @param startDate ��ʼ����
	 * @param interval ���
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
	 * ����Ƿ�������,falseΪ��,trueΪ��
	 * @param date
	 * @return
	 */
	  boolean checkWorkDate(Date date);
	
 	  void initWorkDate();
	
	/**
	 * ȡ��Ч�ù�������
	 * @param startDate ��ʼ����
	 * @param interval ���
	 * @return
	 */
	  Date getWorkDate2(Date startDate, int interval) ;
	
}
