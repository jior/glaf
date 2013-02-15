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
