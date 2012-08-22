package com.glaf.base.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTools {

	public static final long SECOND = 1000;

	public static final long MINUTE = 60 * SECOND;

	public static final long HOUR = 60 * MINUTE;

	public static final long DAY = 24 * HOUR;

	public static final long WEEK = 7 * DAY;

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private final static TimeZone timeZone = TimeZone.getDefault();

	private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat(
			"yyyy", Locale.getDefault());
	private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat(
			"yyyy-MM", Locale.getDefault());
	private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.getDefault());
	private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH", Locale.getDefault());
	private static final SimpleDateFormat MINUTE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm", Locale.getDefault());
	private static final SimpleDateFormat SECOND_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.getDefault());

	static {
		YEAR_FORMAT.setTimeZone(timeZone);
		MONTH_FORMAT.setTimeZone(timeZone);
		DAY_FORMAT.setTimeZone(timeZone);
		HOUR_FORMAT.setTimeZone(timeZone);
		MINUTE_FORMAT.setTimeZone(timeZone);
		SECOND_FORMAT.setTimeZone(timeZone);
	}

	/**
	 * 判断某个时间time2是否在另一个时间time1之前
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

	/**
	 * 
	 * Given the time in long milliseconds, returns a String in the format Xhrs,
	 * Ymins, Z sec.
	 * 
	 * @param timeDiff
	 *            The time difference to format
	 */
	public static String formatTime(long timeDiff) {
		StringBuffer buf = new StringBuffer();
		long hours = timeDiff / (60 * 60 * 1000);
		long rem = (timeDiff % (60 * 60 * 1000));
		long minutes = rem / (60 * 1000);
		rem = rem % (60 * 1000);
		long seconds = rem / 1000;

		if (hours != 0) {
			buf.append(hours);
			buf.append("小时, ");
		}
		if (minutes != 0) {
			buf.append(minutes);
			buf.append("分, ");
		}
		// return "0sec if no difference
		buf.append(seconds);
		buf.append("秒");
		return buf.toString();
	}

	/**
	 * 按指定格式将java.util.Date日期转换为字符串 例如:2009-10-01
	 * 
	 * @param date
	 * @return
	 */
	public static String getDate(java.util.Date date) {
		return getDateTime(DATE_PATTERN, date);
	}

	/**
	 * 按指定格式将java.util.Date日期转换为字符串 例如:2009-01-01 15:02:01
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTime(java.util.Date date) {
		return getDateTime(DATE_TIME_PATTERN, date);
	}

	/**
	 * 按给定格式转换java.util.Date日期为字符串
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
	 * 获取两个日期之间的天数
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
	 * 获取两个日期之间的天数
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
	 * 计算两个日期之间的假期天数（仅仅是正常休息日即周六周日）
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
	 * 获得指定日期的下一个星期一的日期
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

	/**
	 * 计算两个日期之间的工作天数
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

		int charge_start_date = 0;// 开始日期的日期偏移量
		int charge_end_date = 0;// 结束日期的日期偏移量
		// 日期不在同一个日期内
		int stmp;
		int etmp;
		stmp = 7 - startDate.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - endDate.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {// 开始日期为星期六和星期日时偏移量为0
			charge_start_date = stmp - 1;
		}
		if (etmp != 0 && etmp != 6) {// 结束日期为星期六和星期日时偏移量为0
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
	 * 计算两个日期之间的工作天数
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

	public static int getYearMonthDay(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
				Locale.getDefault());
		String ret = formatter.format(date);
		return Integer.parseInt(ret);
	}

	public static void main(String[] args) {
		System.out.println(DateTools.getDate(new Date()));
		System.out.println(DateTools.getDateTime(new Date()));
		System.out.println(DateTools.getYearMonthDay(new Date()));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(year, month, day - 7);
		Date dateBefore = calendar.getTime();
		System.out.println(DateTools.getDate(dateBefore));

		String[] parsePatterns = { DateTools.DATE_TIME_PATTERN,
				DateTools.DATE_PATTERN };
		System.out.println(DateTools.parseDate("2009-12-25", parsePatterns));
		System.out.println(DateTools.parseDate("2009-12-25 12:00:05",
				parsePatterns));
		System.out.println(DateTools.parseDate("2009-12-25 23:59:59",
				parsePatterns));
		System.out.println(DateTools.parseDate("2009-12-25 00:00:00",
				parsePatterns));
		System.out.println(Locale.getDefault());
		System.out.println(DateTools.toDate("2009"));
		System.out.println(DateTools.toDate("2009-12"));
		System.out.println(DateTools.toDate("2009-12-25"));
		System.out.println(DateTools.toDate("2009-12-25 13"));
		System.out.println(DateTools.toDate("2009-12-25 10:45"));
		System.out.println(DateTools.toDate("2009-12-25 22:45:50"));

	}

	public static synchronized Date parseDate(String str, String[] parsePatterns) {
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

	public static synchronized Date toDate(String dateString) {
		try {
			if (dateString.length() == 4) {
				return YEAR_FORMAT.parse(dateString);
			} else if (dateString.length() == 7) {
				return MONTH_FORMAT.parse(dateString);
			} else if (dateString.length() == 10) {
				return DAY_FORMAT.parse(dateString);
			} else if (dateString.length() == 13) {
				return HOUR_FORMAT.parse(dateString);
			} else if (dateString.length() == 16) {
				return MINUTE_FORMAT.parse(dateString);
			} else if (dateString.length() == 19) {
				return SECOND_FORMAT.parse(dateString);
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

	private DateTools() {
	}

}