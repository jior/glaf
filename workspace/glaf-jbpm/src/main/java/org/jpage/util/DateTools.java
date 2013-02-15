/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class DateTools {
    
    public static final long SECOND = 1000;
    
    public static final long MINUTE = 60 * SECOND;
    
    public static final long HOUR = 60 * MINUTE;
    
    public static final long DAY = 24 * HOUR;
    
    public static final long WEEK = 7 * DAY;
    
    public static final long MONTH = 30 * DAY;
    
    public static final long YEAR = 365 * DAY;
    
    private DateTools() {
    }
    
    public static Date getLastDateOfMonth(Date date) {
        Date d = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = 30;
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = 28;
                break;
            default:
                day = 30;
                break;
        }
        
        Calendar calendarx = Calendar.getInstance();
        calendarx.set(Calendar.YEAR, year);
        calendarx.set(Calendar.MONTH, month - 1);
        calendarx.set(Calendar.DAY_OF_MONTH, day);
        calendarx.set(Calendar.HOUR_OF_DAY, 23);
        calendarx.set(Calendar.MINUTE, 59);
        calendarx.set(Calendar.SECOND, 59);
        
        d = calendarx.getTime();
        return d;
    }
    
    /**
     * 获取年月日,格式20060520
     *
     * @return
     */
    public static int getNowYearMonthDay() {
        Date date = new Date(System.currentTimeMillis());
        return getYearMonthDay(date);
    }
    
    public static int getYearMonthDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        String monthStr = null;
        String dayStr = null;
        String yearStr = null;
        
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }
        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        yearStr = "" + year;
        String totay = yearStr + monthStr + dayStr;
        return Integer.parseInt(totay);
    }
    
    public static boolean beforeNowTime(Date time) {
        Date now = new Date(System.currentTimeMillis());
        return beforeTime(now, time);
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
        
        int hour1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
        
        if (hour1 > hour2) {
            return true;
        } else {
            if (hour1 == hour2) {
                int minute1 = calendar1.get(Calendar.MINUTE);
                int minute2 = calendar2.get(Calendar.MINUTE);
                if (minute1 > minute2) {
                    return true;
                } else {
                    if (minute1 == minute2) {
                        int second1 = calendar1.get(Calendar.SECOND);
                        int second2 = calendar2.get(Calendar.SECOND);
                        if (second1 > second2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * 按指定格式将java.util.Date日期转换为字符串 例如:2004-04-02
     *
     * @param date
     * @return
     */
    public static String getDate(java.util.Date date) {
        return toDateString(date);
    }
    
    public static String toDateString(java.util.Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        String monthStr = null;
        String dayStr = null;
        String yearStr = null;
        
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }
        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        yearStr = "" + year;
        return yearStr + "-" + monthStr + "-" + dayStr;
    }
    
    /**
     * 按指定格式将java.util.Date日期转换为字符串 例如:2003年04月22日
     *
     * @param date
     * @return
     */
    public static String getChinaDate(java.util.Date date) {
        return toChinaDateString(date);
    }
    
    public static String toChinaDateString(java.util.Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        String monthStr = null;
        String dayStr = null;
        String yearStr = null;
        
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }
        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        yearStr = "" + year;
        return yearStr + "年" + monthStr + "月" + dayStr + "日";
    }
    
    public static String toTimeString(java.util.Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (toTimeString(calendar.get(Calendar.HOUR_OF_DAY), calendar
                .get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
    }
    
    public static String toChinaTimeString(java.util.Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (toChinaTimeString(calendar.get(Calendar.HOUR_OF_DAY), calendar
                .get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
    }
    
    public static String toTimeString(int hour, int minute, int second) {
        String hourStr = null;
        String minuteStr = null;
        String secondStr = null;
        
        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = "" + hour;
        }
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = "" + second;
        }
        if (second == 0) {
            return hourStr + ":" + minuteStr + ":00";
        } else {
            return hourStr + ":" + minuteStr + ":" + secondStr;
        }
    }
    
    public static String toChinaTimeString(int hour, int minute, int second) {
        String hourStr = null;
        String minuteStr = null;
        String secondStr = null;
        
        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = "" + hour;
        }
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = "" + second;
        }
        if (second == 0) {
            return hourStr + "时" + minuteStr + "分";
        } else {
            return hourStr + "时" + minuteStr + "分" + secondStr + "秒";
        }
    }
    
    /**
     * 按指定格式将java.util.Date日期转换为字符串 例如:2003-04-22 15:02:01
     *
     * @param date
     * @return
     */
    
    public static String getDateTime(java.util.Date date) {
        if (date == null) {
            return "";
        }
        String dateString = toDateString(date);
        String timeString = toTimeString(date);
        
        if (dateString != null && timeString != null) {
            return dateString + " " + timeString;
        } else {
            return "";
        }
    }
    
    /**
     * 按指定格式将java.util.Date日期转换为字符串 例如:2003年04月22日 15时02分01秒
     *
     * @param date
     * @return
     */
    
    public static String getChinaDateTime(java.util.Date date) {
        if (date == null) {
            return "";
        }
        String dateString = toChinaDateString(date);
        String timeString = toChinaTimeString(date);
        
        if (dateString != null && timeString != null) {
            return dateString + " " + timeString;
        } else {
            return "";
        }
    }
    
    /**
     * 按给定格式转换java.util.Date日期为字符串
     *
     * @param pattern
     * @param date
     * @return
     * @throws Exception
     */
    public static String getDate(String pattern, java.util.Date date)
    throws Exception {
        if (date == null || pattern == null) {
            return getDate(date);
        }
        String lastdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale
                .getDefault());
        lastdate = formatter.format(date);
        return lastdate;
    }
    
    /**
     * 按给定格式转换java.util.Date日期为字符串
     *
     * @param pattern
     * @param date
     * @return
     * @throws Exception
     */
    
    public static String getDateTime(String pattern, java.util.Date date)
    throws Exception {
        if (date == null || pattern == null) {
            return getDateTime(date);
        }
        String lastdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale
                .getDefault());
        lastdate = formatter.format(date);
        return lastdate;
    }
    
    /**
     * 将字符串的日期转换成java.util.Date
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static java.util.Date parseDate(String date) throws Exception {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale
                .getDefault());
        Date loginTime = (java.util.Date) sdf.parse(date);
        return loginTime;
    }
    
    /**
     * 将字符串的日期时间转换成java.util.Date
     *
     * @param datetime
     * @return
     * @throws Exception
     */
    
    public static java.util.Date parseDateTime(String datetime)
    throws Exception {
        if (StringUtils.isBlank(datetime)) {
            return null;
        }
        if (datetime.length() <= 10 && datetime.indexOf(":") == -1) {
            return parseDate(datetime);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        Date loginTime = (java.util.Date) sdf.parse(datetime);
        return loginTime;
    }
    
    /**
     * 获取当前时间之后的day天是某一天(例如：2003年05月22日之后的23天是2003-06-14)
     *
     * @param day
     * @return
     * @throws Exception
     */
    
    public static String getAfterDateString(int day) throws Exception {
        long onetimes = (long) 1 * 24 * 60 * 60 * 1000; // 一天的毫秒数
        long now = System.currentTimeMillis();
        long after = now + onetimes * day;
        java.util.Date date = new java.util.Date(after);
        return getDate(date);
    }
    
    /**
     * 获取当前时间之后的day天是某一天(例如：2003年05月22日之后的23天是2003-06-14)
     *
     * @param day
     * @return
     * @throws Exception
     */
    
    public static java.util.Date getAfterDate(int day) {
        long onetimes = (long) 1 * 24 * 60 * 60 * 1000; // 一天的毫秒数
        long now = System.currentTimeMillis();
        long after = now + onetimes * day;
        java.util.Date date = new java.util.Date(after);
        return date;
    }
    
    /**
     * 获取当前时间之后的day天是某一天(例如：2003年05月22日之后的23天是2003-06-14)
     *
     * @param day
     * @return
     * @throws Exception
     */
    
    public static java.util.Date getAfterDate(Date startDate, int day) {
        long onetimes = (long) 1 * 24 * 60 * 60 * 1000; // 一天的毫秒数
        long now = startDate.getTime();
        long after = now + onetimes * day;
        java.util.Date date = new java.util.Date(after);
        return date;
    }
    
    /**
     * 获取当前时间之前的day天是某一天(例如：2003年05月22日之前的20天是2003-05-02)
     *
     * @param day
     * @return
     * @throws Exception
     */
    
    public static String getBeforeDateString(int day) throws Exception {
        long onetimes = (long) 1 * 24 * 60 * 60 * 1000; // 一天的毫秒数
        long now = System.currentTimeMillis();
        long after = now - onetimes * day;
        java.util.Date date = new java.util.Date(after);
        return getDate(date);
    }
    
    /**
     * 获取当前时间之前的day天是某一天(例如：2003年05月22日之前的20天是2003-05-02)
     *
     * @param day
     * @return
     * @throws Exception
     */
    
    public static java.util.Date getBeforeDate(int day) {
        long onetimes = (long) 1 * 24 * 60 * 60 * 1000; // 一天的毫秒数
        long now = System.currentTimeMillis();
        long after = now - onetimes * day;
        java.util.Date date = new java.util.Date(after);
        return date;
    }
    
    public static java.util.Date toDate(String dateTime) {
        if (StringUtils.isBlank(dateTime)) {
            return null;
        }
        String date = null;
        String time = null;
        dateTime = dateTime.trim();
        
        if (dateTime.indexOf(" ") != -1 && dateTime.length() > 8) {
            date = dateTime.substring(0, dateTime.indexOf(" "));
            if (dateTime.length() > 20 && dateTime.indexOf(".") != -1) {
                time = dateTime.substring(dateTime.indexOf(" ") + 1, dateTime
                        .indexOf("."));
            } else {
                time = dateTime.substring(dateTime.indexOf(" ") + 1);
            }
            return toDate(date, time);
        } else if (dateTime.trim().length() == 8
                || dateTime.trim().length() == 10) {
            date = dateTime;
            time = "00:00:00";
            return toDate(date, time);
        } else {
            throw new RuntimeException("日期时间格式不正确。");
        }
    }
    
    /**
     * Converts a date String and a time String into a Date
     *
     * @param date
     *            The date String: YYYY-MM-DD
     * @param time
     *            The time String: either HH:MM or HH:MM:SS
     * @return A Date made from the date and time Strings
     */
    public static java.util.Date toDate(String date, String time) {
        if (StringUtils.isBlank(date)) {
            if (StringUtils.isNotBlank(time)) {
                int timeColon1 = time.indexOf(":");
                int timeColon2 = time.lastIndexOf(":");
                String hour = null;
                String minute = null;
                String second = null;
                hour = time.substring(0, timeColon1);
                
                if (timeColon1 == timeColon2) {
                    minute = time.substring(timeColon1 + 1);
                    second = "0";
                } else {
                    minute = time.substring(timeColon1 + 1, timeColon2);
                    second = time.substring(timeColon2 + 1);
                }
                
                int h = Integer.parseInt(hour != null ? hour.trim() : "0");
                int m = Integer.parseInt(minute != null ? minute.trim() : "0");
                int s = Integer.parseInt(second != null ? second.trim() : "0");
                
                Calendar calendar = Calendar.getInstance();
                try {
                    calendar.set(0, 0, 0, h, m, s);
                    return calendar.getTime();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
            }
            return null;
        }
        String month = null;
        String day = null;
        String year = null;
        String hour = null;
        String minute = null;
        String second = null;
        
        int dateSlash1 = date.indexOf("-");
        int dateSlash2 = date.lastIndexOf("-");
        if (dateSlash1 <= 0 || dateSlash1 == dateSlash2) {
            throw new RuntimeException("日期时间格式不正确。");
        }
        int timeColon1 = time.indexOf(":");
        int timeColon2 = time.lastIndexOf(":");
        
        if (timeColon1 <= 0) {
            throw new RuntimeException("日期时间格式不正确。");
        }
        year = date.substring(0, dateSlash1);
        month = date.substring(dateSlash1 + 1, dateSlash2);
        day = date.substring(dateSlash2 + 1);
        hour = time.substring(0, timeColon1);
        
        if (timeColon1 == timeColon2) {
            minute = time.substring(timeColon1 + 1);
            second = "0";
        } else {
            minute = time.substring(timeColon1 + 1, timeColon2);
            second = time.substring(timeColon2 + 1);
        }
        return toDate(month, day, year, hour, minute, second);
    }
    
    /**
     * Makes a Date from separate Strings for month, day, year, hour, minute,
     * and second.
     *
     * @param monthStr
     *            The month String
     * @param dayStr
     *            The day String
     * @param yearStr
     *            The year String
     * @param hourStr
     *            The hour String
     * @param minuteStr
     *            The minute String
     * @param secondStr
     *            The second String
     * @return A Date made from separate Strings for month, day, year, hour,
     *         minute, and second.
     */
    public static java.util.Date toDate(String monthStr, String dayStr,
            String yearStr, String hourStr, String minuteStr, String secondStr) {
        int month, day, year, hour, minute, second;
        try {
            month = Integer.parseInt(monthStr != null ? monthStr.trim() : "0");
            day = Integer.parseInt(dayStr != null ? dayStr.trim() : "0");
            year = Integer.parseInt(yearStr != null ? yearStr.trim() : "0");
            hour = Integer.parseInt(hourStr != null ? hourStr.trim() : "0");
            minute = Integer.parseInt(minuteStr != null ? minuteStr.trim()
            : "0");
            second = Integer.parseInt(secondStr != null ? secondStr.trim()
            : "0");
        } catch (Exception ex) {
            throw new RuntimeException("日期时间格式不正确。");
        }
        return toDate(month, day, year, hour, minute, second);
    }
    
    /**
     * Makes a Date from separate ints for month, day, year, hour, minute, and
     * second.
     *
     * @param month
     *            The month int
     * @param day
     *            The day int
     * @param year
     *            The year int
     * @param hour
     *            The hour int
     * @param minute
     *            The minute int
     * @param second
     *            The second int
     * @return A Date made from separate ints for month, day, year, hour,
     *         minute, and second.
     */
    public static java.util.Date toDate(int month, int day, int year, int hour,
            int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.set(year, month - 1, day, hour, minute, second);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return new java.util.Date(calendar.getTime().getTime());
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
    
    public static java.sql.Timestamp toTimestamp(String date, String time) {
        java.util.Date newDate = toDate(date, time);
        if (newDate != null) {
            return new java.sql.Timestamp(newDate.getTime());
        } else {
            return null;
        }
    }
    
    public static void main(String[] args) throws Exception {
        java.util.Date date = new java.util.Date();
        System.out.println(DateTools.getChinaDateTime(date));
        System.out.println(DateTools.getAfterDateString(23));
        System.out.println(DateTools.getBeforeDateString(20));
        System.out.println(DateTools.toTimestamp(" 2004-04-02 ", "20:15:24  "));
        System.out.println(DateTools.parseDateTime("2004-02-02 10:00:00"));
        
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(System.currentTimeMillis() - DateTools.MONTH
                * 3));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        
        System.out.println("----------------------------------");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale
                .getDefault());
        
        Date startDate = (java.util.Date) sdf.parse("07:35:00");
        
        Date endDate = (java.util.Date) sdf.parse("08:00:00");
        
        System.out.println(DateTools.beforeTime(startDate, endDate));
        
        endDate = (java.util.Date) sdf.parse("06:00:00");
        
        System.out.println(DateTools.beforeTime(startDate, endDate));
        
        System.out.println(DateTools.beforeNowTime(endDate));
        
        endDate = (java.util.Date) sdf.parse("17:30:00");
        
        System.out.println(DateTools.beforeNowTime(endDate));
        
        System.out.println(DateTools.getAfterDate(new Date(), 30));
        
    }
}