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

package org.jpage.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class NumericUtil {

	// Ĭ�ϳ������㾫��
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
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������ĺ�
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������Ĳ�
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������Ļ�
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ�� С�����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ������������
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ �����ȣ��Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @param scale
	 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * @return ������������
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
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
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
