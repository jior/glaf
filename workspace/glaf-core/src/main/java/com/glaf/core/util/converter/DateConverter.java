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

package com.glaf.core.util.converter;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.time.DateUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DateConverter implements Converter {
	String[] parsePatterns = new String[] { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S" };

	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			String tmp = (String) value;
			if (tmp.trim().length() == 0) {
				return null;
			} else {
				try {
					return DateUtils.parseDate(tmp, parsePatterns);
				} catch (ParseException ex) {
					ex.printStackTrace();
					return com.glaf.core.util.DateUtils.toDate(tmp);
				}
			}
		} else if (value instanceof Long) {
			Long ts = (Long) value;
			Date date = new Date(ts);
			return date;
		}
		return value;
	}
}