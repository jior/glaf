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

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Query;
import org.jpage.persistence.SqlExecutor;

public class HibernateUtil {
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
					Timestamp datetime = DateTools
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

	public static java.sql.Connection getConnection(
			org.hibernate.Session session) {
		if (session != null) {
			return session.connection();
		}
		return null;
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
