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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SysCalendar;
import com.glaf.core.domain.SystemParam;
import com.glaf.core.query.SysCalendarQuery;
import com.glaf.core.service.ISysCalendarService;
import com.glaf.core.service.ISystemParamService;

public class CalendarUtils {

	public static final String DEFAULT_PRODUCTION_LINE = "LINE_1";// 生产线默认值

	static int endTime = 0;

	protected static final Logger logger = LoggerFactory
			.getLogger(CalendarUtils.class);

	protected static int mode = 0; // 0表示默认早班时间为当日 1表示默认早班时间为前日

	protected static int startTime = 0;

	protected static volatile ISysCalendarService sysCalendarService;

	protected static volatile ISystemParamService systemParamService;

	/**
	 * 得到几天后的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	private static Date getDateAfter(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + day);
		return cal.getTime();
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	private static Date getDateBefore(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - day);
		return cal.getTime();
	}

	/**
	 * 根据当前时间和勤次设置时间获取早，晚班 1表示早班，2表示晚班，-1表示取不到次
	 * 
	 * @return 字符串
	 */
	public static String getDutyCalendar() {
		Date date = new Date();
		return getDutyCalendar(date);
	}

	/**
	 * 根据当前时间和勤次设置时间获取早，晚班 1表示早班，2表示晚班，-1表示取不到次
	 * 
	 * @return 字符串
	 */
	public static String getDutyCalendar(Date date) {
		String dutyCalendar = "-1";
		String morning = "";
		String evening = "";
		Properties props = loadCalendarProperties();
		if (props != null) {
			morning = (String) props.get("morning");
			evening = (String) props.get("evening");
		}
		if (StringUtils.isEmpty(morning)) {
			morning = "00:00~00:00";
		}
		if (StringUtils.isEmpty(evening)) {
			evening = "00:00~00:00";
		}

		boolean rst = isDutyCalendar(date, morning);
		if (rst) {
			dutyCalendar = "1";
		} else {
			rst = isDutyCalendar(date, evening);
			if (rst) {
				dutyCalendar = "2";
			}
		}
		return dutyCalendar;
	}

	public static List<SysCalendar> getNextMonthCalendars() {
		return getNextMonthCalendars(new Date(), DEFAULT_PRODUCTION_LINE);
	}

	public static List<SysCalendar> getNextMonthCalendars(Date date) {
		return getNextMonthCalendars(date, DEFAULT_PRODUCTION_LINE);
	}

	/**
	 * 取下个月工作日集合
	 * 
	 * @param date
	 * @param productionLine
	 * @return
	 */
	public static List<SysCalendar> getNextMonthCalendars(Date date,
			String productionLine) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		SysCalendarQuery query = new SysCalendarQuery();
		query.setYear(cal.get(Calendar.YEAR));
		query.setMonth(cal.get(Calendar.MONTH) + 1);
		query.setProductionLine(productionLine);
		query.setIsFreeDay(0);// 0表示工作日，1表示休息日
		query.setOrderBy(" E.WORKDATE_ ");
		return getSysCalendarService().list(query);
	}

	public static List<SysCalendar> getNextWeekCalendars() {
		return getNextWeekCalendars(new Date(), DEFAULT_PRODUCTION_LINE);
	}

	public static List<SysCalendar> getNextWeekCalendars(Date date) {
		return getNextWeekCalendars(date, DEFAULT_PRODUCTION_LINE);
	}

	/**
	 * 取下周工作日集合
	 * 
	 * @param date
	 * @param productionLine
	 * @return
	 */
	public static List<SysCalendar> getNextWeekCalendars(Date date,
			String productionLine) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR) + 1);
		SysCalendarQuery query = new SysCalendarQuery();
		query.setWeek(cal.get(Calendar.WEEK_OF_YEAR));
		query.setProductionLine(productionLine);
		query.setIsFreeDay(0);// 0表示工作日，1表示休息日
		query.setWorkDateGreaterThanOrEqual(date);
		query.setOrderBy(" E.WORKDATE_ ");
		return getSysCalendarService().list(query);
	}

	public static ISysCalendarService getSysCalendarService() {
		if (sysCalendarService == null) {
			sysCalendarService = ContextFactory.getBean("sysCalendarService");
		}
		return sysCalendarService;
	}

	public static ISystemParamService getSystemParamService() {
		if (systemParamService == null) {
			systemParamService = ContextFactory.getBean("systemParamService");
		}
		return systemParamService;
	}

	public static Date getWorkDate() {
		return getWorkDate(new Date());
	}

	/**
	 * 获取对应日期的当前工作日
	 * 
	 * @return
	 */
	public static Date getWorkDate(Date date) {
		initWorkDayEnv();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int nowtime = cal.get(Calendar.HOUR) * 100 + cal.get(Calendar.MINUTE);
		if (startTime > endTime) { // 开始时间大于结束时间如 23:30 ~ 13:30
			switch (mode) {
			case 0: // 0表示默认早班时间为当日
				break;
			case 1: // 1表示默认早班时间为前日
				if (nowtime > startTime && nowtime <= 2359)
					date = getDateAfter(date, 1);
				break;
			}
		} else { // 开始时间大于结束时间如 7:30 ~ 16:30
			switch (mode) {
			case 0: // 0表示默认早班时间为当日
				if (nowtime >= 0 && nowtime < startTime)
					date = getDateBefore(date, 1);
				break;
			case 1: // 1表示默认早班时间为前日
				break;
			}
		}
		return date;
	}

	/**
	 * 获取当前时间的工作日
	 * 
	 * @return
	 */
	public static String getWortDateString(Date date) {
		Date workDate = getWorkDate(date);
		return DateUtils.getDate(workDate);
	}

	

	public static void initWorkDayEnv() {
		java.util.Properties p = loadCalendarProperties();
		String morning = "00:00~00:00";

		if (p.get("morning") != null) {
			morning = (String) p.get("morning");
		}

		if (p.get("mode") != null) {
			mode = Integer.parseInt((String) p.get("mode"));
		}
		String marr[] = morning.split("~");
		String submarr1[] = marr[0].split(":");
		startTime = Integer.parseInt(submarr1[0] + submarr1[1]);
		String submarr2[] = marr[1].split(":");
		endTime = Integer.parseInt(submarr2[0] + submarr2[1]);
	}

	public static boolean isDutyCalendar(Date date, String morning) {
		int nowtime = Integer.parseInt(new java.text.SimpleDateFormat("HHmm")
				.format(date));
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
		if (maxtime >= mintime) { // 按结束时间大于开始时间
			if (nowtime >= mintime && nowtime < maxtime) {
				rst = true;
			}
		} else { // 结束时间小于开始时间
			if (nowtime >= mintime && nowtime <= 2359)
				rst = true;
			if (nowtime >= 0 && nowtime <= maxtime)
				rst = true;
		}
		return rst;
	}

	/**
	 * 判断日期是否为工作日历
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWorkDate() {
		return isWorkDate(new Date(), DEFAULT_PRODUCTION_LINE);
	}

	/**
	 * 判断日期是否为工作日历
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWorkDate(Date date) {
		return isWorkDate(date, DEFAULT_PRODUCTION_LINE);
	}

	/**
	 * 判断日期是否为工作日历
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
		List<SystemParam> params = getSystemParamService().getSystemParams(
				"calendar", "calendar");
		if (params != null && !params.isEmpty()) {
			Properties props = new Properties();
			for (SystemParam p : params) {
				props.put(p.getKeyName(), p.getStringVal());
			}
			return props;
		}
		String filename = SystemProperties.getAppPath()
				+ "/WEB-INF/conf/work_calendar.properties";
		return PropertiesUtils.loadFilePathResource(filename);
	}

	public static boolean saveCalendarProperties(Map<String, String> dataMap) {
		Properties props = loadCalendarProperties();
		props.putAll(dataMap);
		return saveCalendarProperties(props);
	}

	public static boolean saveCalendarProperties(Properties props) {
		try {
			List<SystemParam> rows = new java.util.concurrent.CopyOnWriteArrayList<SystemParam>();
			java.util.Set<Object> set = props.keySet();
			java.util.Iterator<Object> iter = set.iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String value = props.getProperty(key);
				SystemParam p = new SystemParam();
				p.setBusinessKey("calendar");
				p.setServiceKey("calendar");
				p.setTypeCd("calendar");
				p.setJavaType("String");
				p.setKeyName(key);
				p.setStringVal(value);
				rows.add(p);
			}
			getSystemParamService().saveAll("calendar", "calendar", rows);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void setSysCalendarService(
			ISysCalendarService sysCalendarService) {
		CalendarUtils.sysCalendarService = sysCalendarService;
	}

	public static void setSystemParamService(
			ISystemParamService systemParamService) {
		CalendarUtils.systemParamService = systemParamService;
	}

}
