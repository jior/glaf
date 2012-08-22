package com.glaf.base.utils;

import java.text.DateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Title: ParamUtil.java
 * </p>
 * <p>
 * Description: 页面参数字段处理工具
 * </p>
 */
public class ParamUtil {
	
	public static Object get(Map<String, Object> dataMap, String key) {
		if (dataMap == null || key == null) {
			return null;
		}
		Object value = dataMap.get(key);
		return value;
	}

	public static Date getDate(Map<String, Object> dataMap, String key) {
		Date result = null;
		if (dataMap == null || key == null) {
			return null;
		}
		Object value = dataMap.get(key);
		if (value != null) {
			if (value instanceof String) {
				result = DateTools.toTimestamp(value.toString());
			} else if (value instanceof Date) {
				result = (Date) value;
			}
		}
		return result;
	}

	public static double getDouble(Map<String, Object> dataMap, String key) {
		double result = 0.0;
		Object value = dataMap.get(key);
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				String tmp = (String) value;
				result = Double.valueOf(tmp);
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = x.doubleValue();
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.doubleValue();
			} else if (value instanceof Double) {
				Double x = (Double) value;
				result = x.doubleValue();
			}
		}
		return result;
	}

	public static int getInt(Map<String, Object> dataMap, String key) {
		int result = 0;
		Object value = dataMap.get(key);
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				String tmp = (String) value;
				result = Integer.valueOf(tmp);
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = x.intValue();
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.intValue();
			} else if (value instanceof Double) {
				Double x = (Double) value;
				result = x.intValue();
			}
		}
		return result;
	}

	public static long getLong(Map<String, Object> dataMap, String key) {
		long result = 0;
		Object value = dataMap.get(key);
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				String tmp = (String) value;
				result = Long.valueOf(tmp);
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = x.intValue();
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.longValue();
			} else if (value instanceof Double) {
				Double x = (Double) value;
				result = x.longValue();
			}
		}
		return result;
	}

	public static String getString(Map<String, Object> dataMap, String key) {
		String result = null;
		if (dataMap == null || key == null) {
			return null;
		}
		Object value = dataMap.get(key);
		if (value != null) {
			if (value instanceof String) {
				result = (String) value;
			} else if (value instanceof Date) {
				result = DateTools.getDate((Date) value);
			} else {
				result = value.toString();
			}
		}
		return result;
	}

	public static String getString(Map<String, Object> dataMap, String key,
			String defaultValue) {
		String result = defaultValue;
		if (dataMap == null || key == null) {
			return result;
		}
		Object value = dataMap.get(key);
		if (value != null) {
			if (value instanceof String) {
				result = (String) value;
			} else {
				result = value.toString();
			}
		}
		return result;
	}

	public static boolean isNotEmpty(Map<String, Object> paramMap, String name) {
		if (paramMap != null && paramMap.get(name) != null) {
			Object obj = paramMap.get(name);
			if (obj instanceof Collection<?>) {
				Collection<?> rows = (Collection<?>) obj;
				if (rows != null && rows.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @param defaultValue
	 *            String 缺省值
	 * @return String 返回值，缺省为空
	 */
	public static String getParameter(HttpServletRequest request, String param,
			String defaultValue) {
		String value = request.getParameter(param);
		if (value == null || value.length() == 0) {
			if (defaultValue != null)
				value = defaultValue;
			else
				value = "";
		} else {
			value = value.trim();
		}

		return value;
	}

	/**
	 * 获取字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @return String 返回值，缺省为空
	 */
	public static String getParameter(HttpServletRequest request, String param) {
		return getParameter(request, param, "");
	}

	/**
	 * 获取字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @param value
	 *            String 比较值
	 * @return boolean 和比较值相同则返回true，否则返回false
	 */
	public static boolean getBooleanParameter(HttpServletRequest request,
			String param, String value) {
		String temp = getParameter(request, param);
		if (temp != null && temp.equals(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取int型字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @param defaultNum
	 *            int 缺省值
	 * @return int 返回值
	 */
	public static int getIntParameter(HttpServletRequest request, String param,
			int defaultNum) {
		String temp = getParameter(request, param);
		if (!"".equals(temp)) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * 获取long型字段值
	 * 
	 * @param request
	 * @param param
	 * @param defaultNum
	 * @return
	 */
	public static long getLongParameter(HttpServletRequest request,
			String param, long defaultNum) {
		String temp = getParameter(request, param);
		if (!"".equals(temp)) {
			long num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * 获取attribute字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @param defaultValue
	 *            String 缺省值
	 * @return String 返回值
	 */
	public static String getAttribute(HttpServletRequest request, String param,
			String defaultValue) {
		String value = (String) request.getAttribute(param);
		if (value == null) {
			if (defaultValue != null)
				value = defaultValue;
			else
				value = "";
		}
		return value;
	}

	/**
	 * 获取attribute字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @return String 返回值
	 */
	public static String getAttribute(HttpServletRequest request, String param) {
		return getAttribute(request, param, "");
	}

	/**
	 * 获取attribute字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @param value
	 *            String 比较值
	 * @return boolean 和比较值相同则返回true，否则返回false
	 */
	public static boolean getBooleanAttribute(HttpServletRequest request,
			String param, String value) {
		String temp = (String) request.getAttribute(param);
		if (temp != null && temp.equals(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取int型attribute字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @param defaultNum
	 *            int 缺省值
	 * @return int 返回值
	 */
	public static int getIntAttribute(HttpServletRequest request, String param,
			int defaultNum) {
		String temp = getAttribute(request, param);
		if (!temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * 获取数组
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public static String[] getParameterValues(HttpServletRequest request,
			String param) {
		return request.getParameterValues(param);
	}

	/**
	 * 获取数组
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public static int[] getIntParameterValues(HttpServletRequest request,
			String param) {
		int[] ret = null;
		String[] s = request.getParameterValues(param);
		if (s != null) {
			ret = new int[s.length];
			for (int i = 0; i < s.length; i++) {
				ret[i] = Integer.parseInt(s[i]);
			}
		}
		return ret;
	}

	/**
	 * 获取数组
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public static long[] getLongParameterValues(HttpServletRequest request,
			String param) {
		long[] ret = null;
		String[] s = request.getParameterValues(param);
		if (s != null) {
			ret = new long[s.length];
			for (int i = 0; i < s.length; i++) {
				ret[i] = Integer.parseInt(s[i]);
			}
		}
		return ret;
	}

	/**
	 * 获取double型字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @param defaultNum
	 *            double 缺省值
	 * @return int 返回值
	 */
	public static double getDoubleParameter(HttpServletRequest request,
			String param, double defaultNum) {
		String temp = getParameter(request, param);
		if (!"".equals(temp)) {
			double num = defaultNum;
			try {
				num = Double.parseDouble(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * 获取日期参数
	 * 
	 * @param request
	 * @param param
	 * @param defaultNum
	 * @return
	 */
	public static Date getDateParameter(HttpServletRequest request,
			String param, Date defaultNum) {
		String temp = getParameter(request, param);
		if (!"".equals(temp)) {
			Date date = defaultNum;
			try {
				date = DateFormat.getDateInstance().parse(temp);
			} catch (Exception ignored) {
			}
			return date;
		} else {
			return defaultNum;
		}
	}

	/**
	 * 获取float型字段值
	 * 
	 * @param request
	 *            HttpServletRequest request对象
	 * @param param
	 *            String 参数
	 * @param defaultNum
	 *            float 缺省值
	 * @return int 返回值
	 */
	public static float getFloatParameter(HttpServletRequest request,
			String param, float defaultNum) {
		String temp = getParameter(request, param);
		if (!"".equals(temp)) {
			float num = defaultNum;
			try {
				num = Float.parseFloat(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

}
