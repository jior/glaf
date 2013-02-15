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

package org.jpage.jbpm.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ParamUtil {
	

	public static String getString(Map dataMap, String key) {
		String result = null;
		if (dataMap == null || key == null) {
			return null;
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

	public static int getInt(Map dataMap, String key) {
		int result = 0;
		Object value = dataMap.get(key);
		if (value != null) {
			if (value instanceof String) {
				String tmp = (String) value;
				if (StringUtils.isNumeric(tmp)) {
					result = new Integer(tmp).intValue();
				}
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = x.intValue();
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.intValue();
			}
		}
		return result;
	}

	public static long getLong(Map dataMap, String key) {
		long result = 0;
		Object value = dataMap.get(key);
		if (value != null) {
			if (value instanceof String) {
				String tmp = (String) value;
				if (StringUtils.isNumeric(tmp)) {
					result = new Long(tmp).longValue();
				}
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = x.intValue();
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.longValue();
			}
		}
		return result;
	}

	public static boolean isNotEmpty(Map paramMap, String name) {
		if (paramMap != null && paramMap.get(name) != null) {
			Object obj = paramMap.get(name);
			if (obj instanceof Collection) {
				Collection rows = (Collection) obj;
				if (rows != null && rows.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}
}
