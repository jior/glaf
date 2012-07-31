package com.glaf.base.utils;

import java.text.DateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title: ParamUtil.java
 * </p>
 * <p>
 * Description: ҳ������ֶδ�����
 * </p>
 */
public class ParamUtil {

	/**
	 * ��ȡ�ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @param defaultValue
	 *            String ȱʡֵ
	 * @return String ����ֵ��ȱʡΪ��
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
	 * ��ȡ�ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @return String ����ֵ��ȱʡΪ��
	 */
	public static String getParameter(HttpServletRequest request, String param) {
		return getParameter(request, param, "");
	}

	/**
	 * ��ȡ�ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @param value
	 *            String �Ƚ�ֵ
	 * @return boolean �ͱȽ�ֵ��ͬ�򷵻�true�����򷵻�false
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
	 * ��ȡint���ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @param defaultNum
	 *            int ȱʡֵ
	 * @return int ����ֵ
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
	 * ��ȡlong���ֶ�ֵ
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
	 * ��ȡattribute�ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @param defaultValue
	 *            String ȱʡֵ
	 * @return String ����ֵ
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
	 * ��ȡattribute�ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @return String ����ֵ
	 */
	public static String getAttribute(HttpServletRequest request, String param) {
		return getAttribute(request, param, "");
	}

	/**
	 * ��ȡattribute�ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @param value
	 *            String �Ƚ�ֵ
	 * @return boolean �ͱȽ�ֵ��ͬ�򷵻�true�����򷵻�false
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
	 * ��ȡint��attribute�ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @param defaultNum
	 *            int ȱʡֵ
	 * @return int ����ֵ
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
	 * ��ȡ����
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
	 * ��ȡ����
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
	 * ��ȡ����
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
	 * ��ȡdouble���ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @param defaultNum
	 *            double ȱʡֵ
	 * @return int ����ֵ
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
	 * ��ȡ���ڲ���
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
	 * ��ȡfloat���ֶ�ֵ
	 * 
	 * @param request
	 *            HttpServletRequest request����
	 * @param param
	 *            String ����
	 * @param defaultNum
	 *            float ȱʡֵ
	 * @return int ����ֵ
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
