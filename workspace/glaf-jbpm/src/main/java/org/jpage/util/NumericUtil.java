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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class NumericUtil {

	// 默认除法运算精度
	private static final int DEFAULT_DIV_SCALE = 10;

	private NumericUtil() {

	}

	public static int getInt(Map dataMap, String paramName) {
		int value = 0;
		Object obj = dataMap.get(paramName);
		if (obj == null) {
			obj = dataMap.get(paramName.toLowerCase());
		}
		if (obj == null) {
			obj = dataMap.get(paramName.toUpperCase());
		}
		if (obj != null) {
			if (obj instanceof String) {
				String d = (String) obj;
				value = new Integer(d).intValue();
			} else if (obj instanceof Integer) {
				Integer d = (Integer) obj;
				value = d.intValue();
			} else if (obj instanceof Long) {
				Long d = (Long) obj;
				value = d.intValue();
			} else if (obj instanceof BigDecimal) {
				BigDecimal d = (BigDecimal) obj;
				value = d.intValue();
			} else if (obj instanceof BigInteger) {
				BigInteger d = (BigInteger) obj;
				value = d.intValue();
			}
		}
		return value;
	}

	public static long getLong(Map dataMap, String paramName) {
		long value = 0;
		Object obj = dataMap.get(paramName);
		if (obj == null) {
			obj = dataMap.get(paramName.toLowerCase());
		}
		if (obj == null) {
			obj = dataMap.get(paramName.toUpperCase());
		}
		if (obj != null) {
			if (obj instanceof String) {
				String d = (String) obj;
				value = new Integer(d).longValue();
			} else if (obj instanceof Integer) {
				Integer d = (Integer) obj;
				value = d.longValue();
			} else if (obj instanceof Long) {
				Long d = (Long) obj;
				value = d.longValue();
			} else if (obj instanceof BigDecimal) {
				BigDecimal d = (BigDecimal) obj;
				value = d.longValue();
			} else if (obj instanceof BigInteger) {
				BigInteger d = (BigInteger) obj;
				value = d.longValue();
			}
		}
		return value;
	}

	public static double getDouble(Map dataMap, String paramName) {
		double value = 0.0;
		Object obj = dataMap.get(paramName);
		if (obj == null) {
			obj = dataMap.get(paramName.toLowerCase());
		}
		if (obj == null) {
			obj = dataMap.get(paramName.toUpperCase());
		}
		if (obj != null) {
			if (obj instanceof Double) {
				Double d = (Double) obj;
				value = d.doubleValue();
			} else if (obj instanceof String) {
				String d = (String) obj;
				value = new Double(d).doubleValue();
			} else if (obj instanceof Integer) {
				Integer d = (Integer) obj;
				value = d.doubleValue();
			} else if (obj instanceof Long) {
				Long d = (Long) obj;
				value = d.doubleValue();
			} else if (obj instanceof BigDecimal) {
				BigDecimal d = (BigDecimal) obj;
				value = d.doubleValue();
			}
		}
		return value;
	}

	public static String getString(Map dataMap, String paramName) {
		String value = null;
		Object obj = dataMap.get(paramName);
		if (obj == null) {
			obj = dataMap.get(paramName.toLowerCase());
		}
		if (obj == null) {
			obj = dataMap.get(paramName.toUpperCase());
		}
		if (obj != null) {
			if (obj instanceof String) {
				value = (String) obj;
			} else {
				value = obj.toString();
			}
		}
		return value;
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
