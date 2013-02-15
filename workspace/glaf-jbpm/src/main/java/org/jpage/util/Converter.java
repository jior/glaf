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

import java.io.InputStream;
import java.sql.Clob;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.ObjectUtils;

public final class Converter {

	private Converter() {
	}

	public static Map convertToMapString(Object obj) {
		Map properties = convertToMap(obj);
		if (properties != null) {
			Map dataMap = convertToString(properties);
			return dataMap;
		}
		return null;
	}

	public static Map convertToMap(Object obj) {
		return convertToMap(obj, false);
	}

	public static Map convertToMap(Object obj, boolean toLowerCase) {
		if (obj == null) {
			return null;
		}
		Map dataMap = new HashMap();
		if (obj instanceof Map) {
			Map map = (Map) obj;
			Iterator iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String name = (String) iterator.next();
				if (map.get(name) != null) {
					Object value = map.get(name);
					if (value instanceof Clob) {
						value = clobToString(value);
					}
					dataMap.put(name, value);
					if (toLowerCase) {
						dataMap.put(name.toLowerCase(), value);
					}
				}
			}
		} else if (obj instanceof DynaBean) {
			DynaBean dynaBean = (DynaBean) obj;
			DynaProperty[] dp = dynaBean.getDynaClass().getDynaProperties();
			for (int j = 0; j < dp.length; j++) {
				String name = dp[j].getName();
				if (dynaBean.get(name) != null) {
					Object value = dynaBean.get(name);
					if (value instanceof Clob) {
						value = clobToString(value);
					}
					dataMap.put(name, value);
					if (toLowerCase) {
						dataMap.put(name.toLowerCase(), value);
					}
				}
			}
		} else {
			Map properties = null;
			try {
				properties = BeanUtils.describe(obj);
			} catch (Exception ex) {
			}
			if (properties != null) {
				Iterator iterator = properties.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					if (properties.get(name) != null) {
						Object value = properties.get(name);
						if (value instanceof Clob) {
							value = clobToString(value);
						}
						dataMap.put(name, value);
						if (toLowerCase) {
							dataMap.put(name.toLowerCase(), value);
						}
					}
				}
			}
		}
		return dataMap;
	}

	public static Map convertToString(Map map) {
		Map dataMap = new HashMap();
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String name = (String) iterator.next();
			Object obj = map.get(name);
			if (obj != null) {
				String value = null;
				if (obj instanceof Clob) {
					value = clobToString(obj);
				} else if (obj instanceof java.util.Date) {
					java.util.Date date = (java.util.Date) obj;
					value = DateTools.getDate(date);
					dataMap.put(name + "_date", DateTools.getDate(date));
					dataMap
							.put(name + "_datetime", DateTools
									.getDateTime(date));
					dataMap.put(name + "_date_zh_cn", DateTools
							.getChinaDate(date));
					dataMap.put(name + "_datetime_zh_cn", DateTools
							.getChinaDateTime(date));
				} else if (obj instanceof java.sql.Date) {
					java.sql.Date date = (java.sql.Date) obj;
					value = DateTools.getDate(date);
					dataMap.put(name + "_date", DateTools.getDate(date));
					dataMap
							.put(name + "_datetime", DateTools
									.getDateTime(date));
					dataMap.put(name + "_date_zh_cn", DateTools
							.getChinaDate(date));
					dataMap.put(name + "_datetime_zh_cn", DateTools
							.getChinaDateTime(date));
				} else if (obj instanceof java.sql.Timestamp) {
					java.sql.Timestamp date = (java.sql.Timestamp) obj;
					value = DateTools.getDate(date);
					dataMap.put(name + "_date", DateTools.getDate(date));
					dataMap
							.put(name + "_datetime", DateTools
									.getDateTime(date));
					dataMap.put(name + "_date_zh_cn", DateTools
							.getChinaDate(date));
					dataMap.put(name + "_datetime_zh_cn", DateTools
							.getChinaDateTime(date));
				} else if (obj instanceof byte[]) {
					value = "【二进制数据】";
				} else if (obj instanceof InputStream) {
					value = "【二进制数据】";
				} else {
					value = ObjectUtils.toString(obj);
				}
				if (value == null) {
					value = "";
				}
				dataMap.put(name, value);
			}
		}
		return dataMap;
	}

	private static String clobToString(Object obj) {
		String value = "";
		if (obj instanceof Clob) {
			try {
				Clob clob = (Clob) obj;
				java.io.Reader reader = clob.getCharacterStream();
				char[] ch = new char[(int) clob.length()];
				reader.read(ch);
				reader.close();
				value = new String(ch);
			} catch (Exception ex) {
			}
		}
		return value;
	}

}