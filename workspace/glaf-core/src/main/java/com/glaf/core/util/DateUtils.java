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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	public static final long SECOND = 1000L;

	public static final long MINUTE = 60L * SECOND;

	public static final long HOUR = 60L * MINUTE;

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final long DAY = 24L * HOUR;

	public static final String DAY_FORMAT = "yyyy-MM-dd";

	public static final String HOUR_FORMAT = "yyyy-MM-dd HH";

	public static final String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

	public static final String MONTH_FORMAT = "yyyy-MM";

	public static final String SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final long WEEK = 7L * DAY;

	public static final String YEAR_FORMAT = "yyyy";

	public static final String YEAR_MONTH_DAY_FORMAT = "yyyyMMdd";

	/**
	 * �ж�ĳ��ʱ��time2�Ƿ�����һ��ʱ��time1֮ǰ
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean beforeTime(Date time1, Date time2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(time1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(time2);

		return calendar1.before(calendar2);
	}

	public static BigDecimal getCurrentTimeAsNumber() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		returnStr = f.format(date);
		return new BigDecimal(returnStr);
	}

	/**
	 * ��ָ����ʽ��java.util.Date����ת��Ϊ�ַ��� ����:2009-10-01
	 * 
	 * @param date
	 * @return
	 */
	public static String getDate(java.util.Date date) {
		return getDateTime(DATE_PATTERN, date);
	}

	/**
	 * ��ָ����ʽ��java.util.Date����ת��Ϊ�ַ��� ����:2009-01-01 15:02:01
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTime(java.util.Date date) {
		return getDateTime(DATE_TIME_PATTERN, date);
	}

	/**
	 * ��������ʽת��java.util.Date����Ϊ�ַ���
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String getDateTime(String pattern, java.util.Date date) {
		if (date == null) {
			return "";
		}
		if (pattern == null) {
			pattern = DATE_TIME_PATTERN;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				Locale.getDefault());
		String ret = formatter.format(date);
		return ret;
	}

	/**
	 * ��ȡ��������֮�������
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDaysBetween(Calendar startDate, Calendar endDate) {
		if (startDate.after(endDate)) {
			java.util.Calendar swap = startDate;
			startDate = endDate;
			endDate = swap;
		}
		int days = endDate.get(java.util.Calendar.DAY_OF_YEAR)
				- startDate.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = endDate.get(java.util.Calendar.YEAR);
		if (startDate.get(java.util.Calendar.YEAR) != y2) {
			startDate = (java.util.Calendar) startDate.clone();
			do {
				days += startDate
						.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				startDate.add(java.util.Calendar.YEAR, 1);
			} while (startDate.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * ��ȡ��������֮�������
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDaysBetween(Date startDate, Date endDate) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(startDate);
		cal2.setTime(endDate);
		return getDaysBetween(cal1, cal2);
	}

	/**
	 * ������������֮��ļ���������������������Ϣ�ռ��������գ�
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getHolidays(Calendar startDate, Calendar endDate) {
		return getDaysBetween(startDate, endDate)
				- getWorkingDay(startDate, endDate);
	}

	/**
	 * ���ָ�����ڵ���һ������һ������
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getNextMonday(Calendar date) {
		Calendar result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	public static int getNowYearMonth() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		returnStr = f.format(date);
		return Integer.parseInt(returnStr);
	}

	public static int getNowYearMonthDay() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		returnStr = f.format(date);
		return Integer.parseInt(returnStr);
	}

	/**
	 * ������������֮��Ĺ�������
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getWorkingDay(Calendar startDate, Calendar endDate) {
		int result = -1;
		if (startDate.after(endDate)) {
			java.util.Calendar swap = startDate;
			startDate = endDate;
			endDate = swap;
		}

		int charge_start_date = 0;// ��ʼ���ڵ�����ƫ����
		int charge_end_date = 0;// �������ڵ�����ƫ����
		// ���ڲ���ͬһ��������
		int stmp;
		int etmp;
		stmp = 7 - startDate.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - endDate.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {// ��ʼ����Ϊ��������������ʱƫ����Ϊ0
			charge_start_date = stmp - 1;
		}
		if (etmp != 0 && etmp != 6) {// ��������Ϊ��������������ʱƫ����Ϊ0
			charge_end_date = etmp - 1;
		}

		result = (getDaysBetween(getNextMonday(startDate),
				getNextMonday(endDate)) / 7)
				* 5
				+ charge_start_date
				- charge_end_date;
		return result;
	}

	/**
	 * ������������֮��Ĺ�������
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getWorkingDay(Date startDate, Date endDate) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(startDate);
		cal2.setTime(endDate);
		return getWorkingDay(cal1, cal2);
	}

	public static int getYearMonth(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM",
				Locale.getDefault());
		String ret = formatter.format(date);
		return Integer.parseInt(ret);
	}

	public static int getYearMonthDay(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
				Locale.getDefault());
		String ret = formatter.format(date);
		return Integer.parseInt(ret);
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.getDate(new Date()));
		System.out.println(DateUtils.getDateTime(new Date()));
		System.out.println(DateUtils.getYearMonthDay(new Date()));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(year, month, day - 7);
		Date dateBefore = calendar.getTime();
		System.out.println("day:" + year + "-" + month + "-" + day);
		System.out.println(DateUtils.getDate(dateBefore));

		calendar.set(year - 1, month, day);
		dateBefore = calendar.getTime();
		System.out.println(DateUtils.getDate(dateBefore));

		calendar.set(year, month - 15, day);
		dateBefore = calendar.getTime();
		System.out.println(">>" + DateUtils.getDate(dateBefore));

		calendar.set(year, month, day - 365);
		dateBefore = calendar.getTime();
		System.out.println(">>>>" + DateUtils.getDate(dateBefore));

		String[] parsePatterns = { DateUtils.DATE_TIME_PATTERN,
				DateUtils.DATE_PATTERN };
		System.out.println(DateUtils.parseDate("2009-12-25", parsePatterns));
		System.out.println(DateUtils.parseDate("2009-12-25 12:00:05",
				parsePatterns));
		System.out.println(DateUtils.parseDate("2009-12-25 23:59:59",
				parsePatterns));
		System.out.println(DateUtils.parseDate("2009-12-25 00:00:00",
				parsePatterns));
		System.out.println(Locale.getDefault());
		System.out.println(DateUtils.toDate("2009"));
		System.out.println(DateUtils.toDate("2009-12"));
		System.out.println(DateUtils.toDate("2009-12-25"));
		System.out.println(DateUtils.toDate("2009-12-25 13"));
		System.out.println(DateUtils.toDate("2009-12-25 10:45"));
		System.out.println(DateUtils.toDate("2009-12-25 22:45:50"));

	}

	public static Date parseDate(String str, String[] parsePatterns) {
		if (str == null || parsePatterns == null) {
			throw new IllegalArgumentException(
					"Date and Patterns must not be null");
		}
		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; i++) {
			if (i == 0) {
				parser = new SimpleDateFormat(parsePatterns[0]);
			} else {
				parser.applyPattern(parsePatterns[i]);
			}
			pos.setIndex(0);
			Date date = parser.parse(str, pos);
			if (date != null && pos.getIndex() == str.length()) {
				return date;
			}
		}
		throw new RuntimeException("Unable to parse the date: " + str);
	}

	public static java.util.Date toDate(long ts) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(ts);
		return calendar.getTime();
	}

	public static Date toDate(String dateString) {
		String[] parsePatterns = new String[1];
		try {
			if (dateString.length() == 4) {
				parsePatterns[0] = YEAR_FORMAT;
				return org.apache.commons.lang.time.DateUtils.parseDate(
						dateString, parsePatterns);
			} else if (dateString.length() == 7) {
				parsePatterns[0] = MONTH_FORMAT;
				return org.apache.commons.lang.time.DateUtils.parseDate(
						dateString, parsePatterns);
			} else if (dateString.length() == 10) {
				parsePatterns[0] = DAY_FORMAT;
				return org.apache.commons.lang.time.DateUtils.parseDate(
						dateString, parsePatterns);
			} else if (dateString.length() == 13) {
				parsePatterns[0] = HOUR_FORMAT;
				return org.apache.commons.lang.time.DateUtils.parseDate(
						dateString, parsePatterns);
			} else if (dateString.length() == 16) {
				parsePatterns[0] = MINUTE_FORMAT;
				return org.apache.commons.lang.time.DateUtils.parseDate(
						dateString, parsePatterns);
			} else if (dateString.length() == 19) {
				parsePatterns[0] = SECOND_FORMAT;
				return org.apache.commons.lang.time.DateUtils.parseDate(
						dateString, parsePatterns);
			}
		} catch (ParseException ex) {
			throw new RuntimeException(" parse date string error: " + ex);
		}
		throw new RuntimeException("Input is not valid date string: "
				+ dateString);
	}

	public static java.sql.Timestamp toTimestamp(java.util.Date date) {
		if (date != null) {
			return new java.sql.Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	public static java.sql.Timestamp toTimestamp(String dateTime) {
		java.util.Date newDate = toDate(dateTime);
		if (newDate != null) {
			return new java.sql.Timestamp(newDate.getTime());
		} else {
			return null;
		}
	}

	private DateUtils() {
	}

}