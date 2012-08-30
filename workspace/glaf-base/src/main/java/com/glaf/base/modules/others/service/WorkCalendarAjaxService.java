package com.glaf.base.modules.others.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.others.model.WorkCalendar;

public class WorkCalendarAjaxService {
	private static final Log logger = LogFactory.getLog(WorkCalendarAjaxService.class);
	private WorkCalendarService workCalendarService;
	
	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
		logger.info("setWorkCalendarService");
	}
	/**
	 * ´´½¨¼ÇÂ¼
	 * @param year
	 * @param month
	 * @param day
	 */
	public void createData(int year, int month, int day){
		WorkCalendar bean = workCalendarService.find(year, month, day);
		if(bean==null){
			bean = new WorkCalendar();
			bean.setFreeYear(year);
			bean.setFreeMonth(month);
			bean.setFreeDay(day);
			workCalendarService.create(bean);
		}
	}
	/**
	 * É¾³ý
	 * @param id
	 */
	public void deleteData(int year, int month, int day){
		WorkCalendar bean = workCalendarService.find(year, month, day);
		if(bean!=null) workCalendarService.delete(bean);
	}
}
