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

package com.glaf.core.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SysCalendar;
import com.glaf.core.query.SysCalendarQuery;
import com.glaf.core.service.ISysCalendarService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.PropertiesUtils;

public class SysCalendarUtils {

	public static final String DEFAULT_PRODUCTION_LINE = "LINE_1";// ������Ĭ��ֵ

	static int endTime = 0;

	protected static final Logger logger = LoggerFactory
			.getLogger(SysCalendarUtils.class);

	protected static int startTime = 0;

	protected static ISysCalendarService sysCalendarService;

	protected static int WorkMode = 0; // 0��ʾĬ�����ʱ��Ϊ���� 1��ʾĬ�����ʱ��Ϊǰ��

	/**
	 * �õ�������ʱ��
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	private static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * �õ�����ǰ��ʱ��
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	private static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * ���ݵ�ǰʱ����ڴ�����ʱ���ȡ�磬��� 1��ʾ��࣬2��ʾ��࣬-1��ʾȡ������
	 * 
	 * @return �ַ���
	 */
	public static String getInputDutyCD() {
		// �ڴ�ȡֵ
		Date nowdate = new Date();
		return getInputDutyCD(nowdate);
	}

	/**
	 * ���ݵ�ǰʱ����ڴ�����ʱ���ȡ�磬��� 1��ʾ��࣬2��ʾ��࣬-1��ʾȡ������
	 * 
	 * @return �ַ���
	 */
	public static String getInputDutyCD(Date nowdate) {
		// �ڴ�ȡֵ
		// ��ǰʱ������
		String sInputDutyCD = "-1";
		String morning = "", evening = "";
		Properties p = loadCalendarProperties();
		if (null != p) {
			morning = (String) p.get("morning");
			evening = (String) p.get("evening");
		}
		if (morning == null || "".equals(morning))
			morning = "00:00~00:00";
		if (evening == null || "".equals(evening))
			evening = "00:00~00:00";

		boolean rst = getInputDutyCD(nowdate, morning);
		if (rst) {
			sInputDutyCD = "1";
		} else {
			rst = getInputDutyCD(nowdate, evening);
			if (rst) {
				sInputDutyCD = "2";
			}
		}
		return sInputDutyCD;
	}

	public static boolean getInputDutyCD(Date nowDate, String morning) {
		int nowtime = Integer.parseInt(new java.text.SimpleDateFormat("HHmm")
				.format(nowDate));
		boolean rst = false;
		int mintime = 0;
		int maxtime = 0;
		String marr[] = morning.split("~");
		String submarr1[] = marr[0].split(":");
		mintime = Integer.parseInt((new StringBuilder(String
				.valueOf(submarr1[0]))).append(submarr1[1]).toString());
		String submarr2[] = marr[1].split(":");
		maxtime = Integer.parseInt((new StringBuilder(String
				.valueOf(submarr2[0]))).append(submarr2[1]).toString());
		if (maxtime >= mintime) { // ������ʱ����ڿ�ʼʱ��
			if (nowtime >= mintime && nowtime < maxtime) {
				rst = true;
			}
		} else { // ����ʱ��С�ڿ�ʼʱ��
			if (nowtime >= mintime && nowtime <= 2359)
				rst = true;
			if (nowtime >= 0 && nowtime <= maxtime)
				rst = true;
		}
		return rst;
	}

	/**
	 * ��������ȡÿ�µ�����
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getYearMonthDays(int year, int month) {
		int days = 31;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		case 2:
			if (year % 4 == 0 || year % 100 == 0 || year % 400 == 0) {
				days = 29;
				break;
			}
			days = 28;
			break;
		}
		return days;
	}

	public static List<SysCalendar> getNextMonthDays() {
		return getNextMonthDays(new Date(), DEFAULT_PRODUCTION_LINE);
	}

	public static List<SysCalendar> getNextMonthDays(Date date) {
		return getNextMonthDays(date, DEFAULT_PRODUCTION_LINE);
	}

	/**
	 * ȡ�¸��¹����ռ���
	 * 
	 * @param date
	 * @param proline
	 * @return
	 */
	public static List<SysCalendar> getNextMonthDays(Date date,
			String productionLine) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		SysCalendarQuery query = new SysCalendarQuery();
		query.setYear(cal.get(Calendar.YEAR));
		query.setMonth(cal.get(Calendar.MONTH) + 1);
		query.setProductionLine(productionLine);
		query.setIsFreeDay(0); 
		query.setOrderBy(" E.WORKDATE_ ");
		return getSysCalendarService().list(query);
	}

	public static List<SysCalendar> getNextWeekDays() {
		return getNextWeekDays(new Date(), DEFAULT_PRODUCTION_LINE);
	}

	public static List<SysCalendar> getNextWeekDays(Date date) {
		return getNextWeekDays(date, DEFAULT_PRODUCTION_LINE);
	}

	/**
	 * ȡ���ܹ����ռ���
	 * 
	 * @param date
	 * @param proline
	 * @return
	 */
	public static List<SysCalendar> getNextWeekDays(Date date,
			String productionLine) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR) + 1);
		SysCalendarQuery query = new SysCalendarQuery();
		query.setWeek(cal.get(Calendar.WEEK_OF_YEAR));
		query.setProductionLine(productionLine);
		query.setIsFreeDay(0);// 0��ʾ�����գ�1��ʾ�ǹ�����
		query.setWorkDateGreaterThanOrEqual(date);
		query.setOrderBy(" E.WORKDATE_ ");
		return getSysCalendarService().getSysCalendarsByQueryCriteria(0, 10,
				query);
	}

	public static ISysCalendarService getSysCalendarService() {
		if (sysCalendarService == null) {
			sysCalendarService = ContextFactory.getBean("sysCalendarService");
		}
		return sysCalendarService;
	}

	public static Date getWorkDate() {
		return getWorkDate(new Date());
	}

	/**
	 * ��ȡ��Ӧ���ڵĵ�ǰ������
	 * 
	 * @return
	 */
	public static Date getWorkDate(Date nowdate) {
		initWorkDayEnv();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowdate);

		int nowtime = cal.get(Calendar.HOUR) * 100 + cal.get(Calendar.MINUTE);
		if (startTime > endTime) { // ��ʼʱ����ڽ���ʱ���� 23:30 ~ 13:30
			switch (WorkMode) {
			case 0: // 0��ʾĬ�����ʱ��Ϊ����
				break;
			case 1: // 1��ʾĬ�����ʱ��Ϊǰ��
				if (nowtime > startTime && nowtime <= 2359)
					nowdate = getDateAfter(nowdate, 1);
				break;
			}
		} else { // ��ʼʱ����ڽ���ʱ���� 7:30 ~ 16:30
			switch (WorkMode) {
			case 0: // 0��ʾĬ�����ʱ��Ϊ����
				if (nowtime >= 0 && nowtime < startTime)
					nowdate = getDateBefore(nowdate, 1);
				break;
			case 1: // 1��ʾĬ�����ʱ��Ϊǰ��
				break;
			}
		}
		return nowdate;
	}

	/**
	 * ��ȡ��ǰʱ��Ĺ�����
	 * 
	 * @return
	 */
	public static String getWortDateString(Date nowdate) {
		Date workDate = getWorkDate(nowdate);
		return DateUtils.getDate(workDate);
	}

	public static synchronized void initWorkDayEnv() {
		java.util.Properties p = loadCalendarProperties();
		String morning = "00:00~00:00";

		if (p.get("morning") != null) {
			morning = (String) p.get("morning");
		}

		if (p.get("WorkMode") != null) {
			WorkMode = Integer.parseInt((String) p.get("WorkMode"));
		}
		String marr[] = morning.split("~");
		String submarr1[] = marr[0].split(":");
		startTime = Integer.parseInt(submarr1[0] + submarr1[1]);
		String submarr2[] = marr[1].split(":");
		endTime = Integer.parseInt(submarr2[0] + submarr2[1]);
	}

	/**
	 * �ж������Ƿ�Ϊ��������
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWorkDate() {
		return isWorkDate(new Date(), DEFAULT_PRODUCTION_LINE);
	}

	/**
	 * �ж������Ƿ�Ϊ��������
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWorkDate(Date date) {
		return isWorkDate(date, DEFAULT_PRODUCTION_LINE);
	}

	/**
	 * �ж������Ƿ�Ϊ��������
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWorkDate(Date date, String productionLine) {
		boolean flag = false;
		SysCalendarQuery query = new SysCalendarQuery();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		query.setYear(cal.get(Calendar.YEAR));
		query.setMonth(cal.get(Calendar.MONTH) + 1);
		query.setDay(cal.get(Calendar.DATE));
		query.setProductionLine(productionLine);
		query.setIsFreeDay(0);
		List<SysCalendar> list = getSysCalendarService().list(query);
		if (null != list && list.size() > 0) {
			flag = true;
		}
		return flag;
	}

	public static Properties loadCalendarProperties() {
		String filename = SystemProperties.getAppPath()
				+ "/WEB-INF/conf/work_calendar.properties";
		return PropertiesUtils.loadFilePathResource(filename);
	}

	public static boolean saveCalendarProperties(Map<String, String> map) {
		Properties p = loadCalendarProperties();
		p.setProperty("morning", map.get("morning").toString());
		p.setProperty("evening", map.get("evening").toString());
		p.setProperty("WorkMode", map.get("mode").toString());
		return saveCalendarProperties(p);
	}

	public static boolean saveCalendarProperties(Properties p) {
		String filename = SystemProperties.getAppPath()
				+ "/WEB-INF/conf/work_calendar.properties";
		try {
			PropertiesUtils.save(filename, p);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void setSysCalendarService(
			ISysCalendarService sysCalendarService) {
		SysCalendarUtils.sysCalendarService = sysCalendarService;
	}

}
