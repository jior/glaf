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

package com.glaf.jbpm.util;

import java.io.File;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.cfg.Configuration;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.DateUtils;
import com.glaf.jbpm.db.JbpmConfiguration;

public class HibernateUtils {

	public static Configuration createHibernateConfiguration() {
		try {
			JbpmConfiguration cfg = new JbpmConfiguration();
			cfg.config();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Configuration configuration = new Configuration();
		String filename = SystemConfig.getConfigRootPath()
				+ "/conf/jbpm/hibernate.cfg.xml";
		File cfgUrl = new File(filename);
		configuration.configure(cfgUrl);
		return configuration;
	}

	public static void fillParameters(Query query, List<Object> values) {
		if (values == null || values.size() == 0) {
			return;
		}
		for (int i = 0; i < values.size(); i++) {
			Object object = values.get(i);
			int index = i;
			if (object != null) {
				if (object instanceof java.sql.Date) {
					java.sql.Date sqlDate = (java.sql.Date) object;
					query.setDate(index, sqlDate);
				} else if (object instanceof java.sql.Time) {
					java.sql.Time sqlTime = (java.sql.Time) object;
					query.setTime(index, sqlTime);
				} else if (object instanceof java.sql.Timestamp) {
					Timestamp datetime = (Timestamp) object;
					query.setTimestamp(index, datetime);
				} else if (object instanceof java.util.Date) {
					Timestamp datetime = DateUtils
							.toTimestamp((java.util.Date) object);
					query.setTimestamp(index, datetime);
				} else {
					query.setParameter(index, object);
				}
			} else {
				query.setParameter(index, null);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void fillParameters(Query query, Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			return;
		}
		Set<Entry<String, Object>> entrySet = params.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			query.setParameter(name, value);
			if (value instanceof Collection) {
				Collection values = (Collection) value;
				query.setParameterList(name, values);
			}
		}
	}

	public static SqlExecutor replaceSQL(String sql, Map<String, Object> params) {
		if (sql == null || params == null) {
			return null;
		}
		SqlExecutor sqlExecutor = new SqlExecutor();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < sql.length(); i++) {
			if (sql.charAt(i) == '$' && sql.charAt(i + 1) == '{') {
				sb.append(sql.substring(end, i));
				begin = i + 2;
				flag = true;
			}
			if (flag && sql.charAt(i) == '}') {
				String temp = sql.substring(begin, i);
				sb.append("  :").append(temp).append(" ");
				end = i + 1;
				flag = false;
				paramMap.put(temp, params.get(temp));
			}
			if (i == sql.length() - 1) {
				sb.append(sql.substring(end, i + 1));
			}
		}
		sqlExecutor.setParameter(paramMap);
		sqlExecutor.setSql(sb.toString());
		return sqlExecutor;
	}
}