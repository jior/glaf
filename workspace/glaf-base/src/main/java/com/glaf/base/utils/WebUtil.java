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

package com.glaf.base.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebUtil {
	protected static final Log logger = LogFactory.getLog(WebUtil.class);

	/**
	 * 根据queryString构造url地址
	 * 
	 * @param request
	 */
	public static String getQueryString(HttpServletRequest request) {
		return getQueryString(request, "UTF-8");
	}

	/**
	 * 根据queryString构造url地址
	 * 
	 * @param request
	 */
	public static String getQueryString(HttpServletRequest request,
			String encoding) {
		Enumeration<String> names = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			sb.append(name).append("=").append(value).append("&");
		}
		return sb.toString();
	}

	/**
	 * 根据queryString构造url地址
	 * 
	 * @param url
	 * @return
	 */
	public static String getQueryString(String url) {
		Map<String, String> map = new HashMap<String, String>();
		String[] param = url.split("&");
		for (int i = 0; i < param.length; i++) {
			String[] entry = param[i].split("=");
			if (entry.length == 2)
				map.put(entry[0], entry[1]);
		}
		StringBuffer sb = new StringBuffer();
		Iterator<String> keys = map.keySet().iterator();
		while (keys != null && keys.hasNext()) {
			String name = (String) keys.next();
			String value = (String) map.get(name);
			sb.append(name).append("=").append(value).append("&");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getQueryMap(HttpServletRequest request) {
		Map<String, String> map = new LinkedHashMap<String, String>();// 排序Map

		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			if (request.getParameter(name) != null) {
				String value = request.getParameter(name).trim();
				map.put(name, value);
			}
		}

		return map;
	}

	/**
	 * String->String
	 */
	public static String stringToString(String str) {
		return str != null ? str : "";
	}

	/**
	 * Date -> String
	 */
	public static String dateToString(Date date) {
		return dateToString(date, null);
	}

	/**
	 * Date -> String
	 */
	public static String dateToString(Date date, String format) {
		String retStr = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					(format == null ? "yyyy-MM-dd" : format));
			retStr = sdf.format(date);
		}
		return retStr;
	}

	/**
	 * String -> Date
	 */
	public static Date stringToDate(String date) {
		return stringToDate(date, null);
	}

	/**
	 * String -> Date
	 */
	public static Date stringToDate(String date, String format) {
		Date retDate = null;
		if (date != null && !"".equals(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					(format == null ? "yyyy-MM-dd" : format));
			try {
				retDate = sdf.parse(date);
			} catch (ParseException e) {
			}
		}
		return retDate;
	}

	/**
	 * 格式化数据并四舍五入
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormatCurrency(double number) {
		return getFormatCurrency(number, 2);
	}

	/**
	 * 格式化数据并四舍五入
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormatCurrency(Double number) {
		return getFormatCurrency(number.doubleValue(), 2);
	}

	public static String getFormatCurrency(double number, int digits) {
		BigDecimal bd = new BigDecimal(number);
		double n = bd.setScale(BigDecimal.ROUND_HALF_UP, digits).doubleValue();
		String num = "";
		for (int i = 0; i < digits; i++) {
			num += "0";
		}
		if (num.length() > 0) {
			num = "." + num;
		}
		DecimalFormat df = new DecimalFormat("##0" + num);

		return df.format(n);
	}

	/**
	 * 格式化数据并四舍五入,并返回以逗号分隔的字符串
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormatNumberToString(double number) {
		return getFormatNumberToString(number, 2);
	}

	/**
	 * 格式化数据并四舍五入,并返回以逗号分隔的字符串
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormatNumberToString(Double number) {
		return getFormatNumberToString(number.doubleValue(), 2);
	}

	public static String getFormatNumberToString(double number, int digits) {
		BigDecimal bd = new BigDecimal(number);
		double n = bd.setScale(BigDecimal.ROUND_HALF_UP, digits).doubleValue();
		String num = "";
		for (int i = 0; i < digits; i++) {
			num += "0";
		}
		if (num.length() > 0) {
			num = "." + num;
		}
		DecimalFormat df = new DecimalFormat("#,##0" + num);

		return df.format(n);
	}

	/**
	 * 格式化数据并四舍五入
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormarPercentage(double number) {
		return getFormarPercentage(number, 0);
	}

	public static String getFormarPercentage(double number, int digits) {
		return String.valueOf(number) + "%";
	}

	/**
	 * 拷贝BEAN
	 * 
	 * @param dest
	 * @param orig
	 */
	public static void copyProperties(Object dest, Object orig) {
		try {
			PropertyUtilExtends pue = new PropertyUtilExtends();
			pue.copyCustomerProperties(dest, orig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择排序
	 * 
	 * @param array
	 * @return
	 */
	public static long[] selectionSort(long[] array) {
		int out, in, min, nElems = array.length;

		for (out = 0; out < nElems - 1; out++) // 外层循环
		{
			min = out; // 最小值
			for (in = out + 1; in < nElems; in++)
				// 内层循环
				if (array[in] < array[min]) // 如果有比最小值还小的
					min = in; // 得到新最小值
			// 交换
			long temp = array[out];
			array[out] = array[min];
			array[min] = temp;
		}
		return array;
	}

	public static void main(String[] args) {
		System.out.println(getFormatCurrency(1111.235234324, 2));
		System.out.println(getFormatCurrency(1111, 2));
		System.out.println(getFormatCurrency(0));

		System.out.println(getFormatNumberToString(1132411.235, 6));
		System.out.println(getFormatNumberToString(1123411.235, 2));
		System.out.println(getFormatNumberToString(1112341.34, 2));
		System.out.println("DATE -----------------"
				+ stringToDate("20091126", "yyyyMMdd"));
		long[] t = new long[] { 1, 3, 2, 43, 23, 3, 4, 5, 3, 2 };
		t = selectionSort(t);
		for (int i = 0; i < t.length; i++) {
			System.out.print(t[i] + ", ");
		}
	}

}