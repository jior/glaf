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

package org.jpage.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.util.DateTools;
import org.jpage.util.FieldType;
import org.jpage.util.SQLFormatter;
import org.jpage.util.Tools;

public class ExecutorHelper {

	private final static Log logger = LogFactory.getLog(ExecutorHelper.class);

	public Executor getExecutor(String sql, List params, Map valueMap) {
		Executor executor = new Executor();
		List values = new ArrayList();
		if (params != null && valueMap != null) {
			Iterator iterator = params.iterator();
			while (iterator.hasNext()) {
				SQLParameter param = (SQLParameter) iterator.next();
				String name = param.getName();
				int type = param.getType();
				Object obj = valueMap.get(name);
				if (obj == null) {
					obj = valueMap.get(name.toLowerCase());
				}
				if (obj == null) {
					String defaultValue = param.getDefaultValue();
					if (StringUtils.isNotBlank(defaultValue)) {
						if (defaultValue.startsWith("${")
								&& defaultValue.endsWith("}")) {
							String paramName = Tools.replaceIgnoreCase(defaultValue, "${", "");
							paramName = paramName.replaceAll("}", "");
							if (valueMap.get(paramName) != null) {
								obj = valueMap.get(paramName);
							}
						}
					}
				}

				if (obj == null) {
					/**
					 * 如果该参数是必须的，而参数值为空，则不能继续往下处理，抛出异常。
					 */
					if (param.getRequired() > 0) {
						throw new RuntimeException("parameter \"" + name
								+ "\" is not set");
					}
					values.add(null);
					continue;
				}

				String value = null;
				java.util.Date date = null;

				if (obj instanceof String) {
					value = (String) obj;
				}

				switch (type) {
				case FieldType.DATE_TYPE:
					if (!(obj instanceof java.util.Date)) {
						if (StringUtils.isNotBlank(value)) {
							date = DateTools.toDate(value);
							obj = date;
						}
					}
					break;
				case FieldType.TIMESTAMP_TYPE:
					if (!(obj instanceof java.sql.Timestamp)) {
						if (obj instanceof java.util.Date) {
							date = (java.util.Date) obj;
						} else {
							if (StringUtils.isNotBlank(value)) {
								date = DateTools.toDate(value);
							}
						}
						obj = DateTools.toTimestamp(date);
					}
					break;
				case FieldType.DOUBLE_TYPE:
					if (!(obj instanceof Double)) {
						if (StringUtils.isNotBlank(value)) {
							Double d = new Double(value);
							obj = d;
						}
					}
					break;
				case FieldType.BOOLEAN_TYPE:
					if (!(obj instanceof Boolean)) {
						if (StringUtils.isNotBlank(value)) {
							Boolean b = new Boolean(value);
							obj = b;
						}
					}
					break;
				case FieldType.SHORT_TYPE:
					if (!(obj instanceof Short)) {
						if (StringUtils.isNotBlank(value)) {
							Short s = new Short(value);
							obj = s;
						}
					}
					break;
				case FieldType.INTEGER_TYPE:
					if (!(obj instanceof Integer)) {
						if (StringUtils.isNotBlank(value)) {
							Integer integer = new Integer(value);
							obj = integer;
						}
					}
					break;
				case FieldType.LONG_TYPE:
					if (!(obj instanceof Long)) {
						if (StringUtils.isNotBlank(value)) {
							Long l = new Long(value);
							obj = l;
						}
					}
					break;
				default:
					break;
				}
				values.add(obj);
			}
			executor.setValues(values.toArray());
		}

		executor.setQuery(sql);

		SQLFormatter formatter = new SQLFormatter();
		sql = formatter.format(sql);
		
		logger.info("[resolve value] = " + values);
		logger.info("[resolve query] = " + sql);
		return executor;
	}

}
