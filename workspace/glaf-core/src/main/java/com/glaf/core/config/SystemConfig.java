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

package com.glaf.core.config;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.service.ISystemPropertyService;
import com.glaf.core.util.DateUtils;

public class SystemConfig {
	protected static final Log logger = LogFactory.getLog(SystemConfig.class);

	protected final static Map<String, SystemProperty> properties = new ConcurrentHashMap<String, SystemProperty>();

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	public final static String CURR_YYYYMMDD = "${curr_yyyymmdd}";

	public final static String CURR_YYYYMM = "${curr_yyyymm}";

	public final static String INPUT_YYYYMMDD = "${input_yyyymmdd}";

	public final static String INPUT_YYYYMM = "${input_yyyymm}";

	public final static String LONG_ID = "${longId}";

	public final static String NOW = "${now}";

	private static volatile String TOKEN = null;

	public static Map<String, Object> getContextMap() {
		Map<String, Object> dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		dataMap.put(CURR_YYYYMMDD, getCurrentYYYYMMDD());
		dataMap.put(CURR_YYYYMM, getCurrentYYYYMM());
		dataMap.put(INPUT_YYYYMMDD, getInputYYYYMMDD());
		dataMap.put(INPUT_YYYYMM, getInputYYYYMM());
		dataMap.put("curr_yyyymmdd", getCurrentYYYYMMDD());
		dataMap.put("curr_yyyymm", getCurrentYYYYMM());
		dataMap.put("input_yyyymmdd", getInputYYYYMMDD());
		dataMap.put("input_yyyymm", getInputYYYYMM());
		dataMap.put("now", getCurrentYYYYMMDD());
		dataMap.put("${now}", getCurrentYYYYMMDD());
		dataMap.put("#{now}", getCurrentYYYYMMDD());
		return dataMap;
	}

	public static String getCurrentYYYYMM() {
		String value = getString("curr_yyyymm");
		if (StringUtils.isEmpty(value)
				|| StringUtils.equals("curr_yyyymm", value)
				|| StringUtils.equals(CURR_YYYYMM, value)) {
			Date now = new Date();
			value = String.valueOf(DateUtils.getYearMonth(now));
		}
		return value;
	}

	public static String getCurrentYYYYMMDD() {
		String value = getString("curr_yyyymmdd");
		if (StringUtils.isEmpty(value)
				|| StringUtils.equals("curr_yyyymmdd", value)
				|| StringUtils.equals(CURR_YYYYMMDD, value)) {
			Date now = new Date();
			value = String.valueOf(DateUtils.getYearMonthDay(now));
		}
		return value;
	}

	public static String getDataPath() {
		String dataDir = getString("dataDir");
		if (StringUtils.isEmpty(dataDir)) {
			dataDir = SystemProperties.getConfigRootPath() + "/report/data";
		}
		return dataDir;
	}

	public static String getInputYYYYMM() {
		String value = getString("input_yyyymm");
		if (StringUtils.isEmpty(value)
				|| StringUtils.equals("input_yyyymm", value)
				|| StringUtils.equals(INPUT_YYYYMM, value)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			calendar.set(year, month, day - 1);

			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			month = month + 1;
			logger.debug(year + ":" + month);

			StringBuffer sb = new StringBuffer(50);
			sb.append(year);

			if (month <= 9) {
				sb.append("0").append(month);
			} else {
				sb.append(month);
			}
			value = sb.toString();
		}

		logger.debug("input_yyyymm:" + value);
		return value;
	}

	public static String getInputYYYYMMDD() {
		String value = getString("input_yyyymmdd");
		if (StringUtils.isEmpty(value)
				|| StringUtils.equals("input_yyyymmdd", value)
				|| StringUtils.equals(INPUT_YYYYMMDD, value)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			calendar.set(year, month, day - 1);

			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			month = month + 1;
			day = calendar.get(Calendar.DAY_OF_MONTH);

			logger.debug(year + ":" + month + ":" + day);

			StringBuffer sb = new StringBuffer(50);
			sb.append(year);

			if (month <= 9) {
				sb.append("0").append(month);
			} else {
				sb.append(month);
			}

			if (day <= 9) {
				sb.append("0").append(day);
			} else {
				sb.append(day);
			}

			value = sb.toString();
		}
		logger.debug("input_yyyymmdd:" + value);
		return value;
	}

	public static String getMappingPath() {
		String mappingDir = SystemProperties.getConfigRootPath()
				+ "/report/mapping";
		return mappingDir;
	}

	public static Map<String, SystemProperty> getProperties() {
		return properties;
	}

	public static SystemProperty getProperty(String key) {
		if (properties.isEmpty()) {
			reload();
		}
		SystemProperty prop = properties.get(key);
		if (prop != null) {

		}
		return prop;
	}

	public static String getReportSavePath() {
		String value = getString("report_save_path");
		if (StringUtils.isEmpty(value)) {
			value = SystemProperties.getConfigRootPath() + "/report";
		}
		return value;
	}

	/**
	 * 获取服务地址
	 * 
	 * @return
	 */
	public static String getServiceUrl() {
		ISystemPropertyService systemPropertyService = ContextFactory
				.getBean("systemPropertyService");
		SystemProperty property = systemPropertyService.getSystemProperty(
				"SYS", "serviceUrl");
		String serviceUrl = null;
		if (property != null && property.getValue() != null) {
			serviceUrl = property.getValue();
		}
		return serviceUrl;
	}

	public static String getString(String key) {
		String ret = null;
		if (properties.isEmpty()) {
			reload();
		}
		SystemProperty prop = properties.get(key);
		if (prop != null) {
			String value = prop.getValue();
			if (StringUtils.isEmpty(value)) {
				value = prop.getInitValue();
			}
			ret = value;
		}
		return ret;
	}

	public static String getString(String key, String defaultValue) {
		String ret = defaultValue;
		if (properties.isEmpty()) {
			reload();
		}
		SystemProperty prop = properties.get(key);
		if (prop != null) {
			String value = prop.getValue();
			if (StringUtils.isEmpty(value)) {
				value = prop.getInitValue();
			}
			ret = value;
		}
		return ret;
	}

	public static String getToken() {
		if (TOKEN != null) {
			return TOKEN;
		}
		ISystemPropertyService systemPropertyService = ContextFactory
				.getBean("systemPropertyService");
		SystemProperty property = systemPropertyService
				.getSystemPropertyById("TOKEN");
		if (property != null && property.getValue() != null) {
			TOKEN = property.getValue();
		} else {
			java.util.Random random = new java.util.Random();
			TOKEN = Math.abs(random.nextInt(9999))
					+ com.glaf.core.util.UUID32.getUUID()
					+ Math.abs(random.nextInt(9999));
			property = new SystemProperty();
			property.setId("TOKEN");
			property.setCategory("SYS");
			property.setName("TOKEN");
			property.setLocked(0);
			property.setValue(TOKEN);
			property.setTitle("TOKEN");
			property.setType("String");
			systemPropertyService.save(property);
		}
		return TOKEN;
	}

	public static void main(String[] args) {
		Date now = new Date();
		Map<String, Object> sysMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		sysMap.put("curr_yyyymmdd", DateUtils.getYearMonthDay(now));
		sysMap.put("curr_yyyymm", DateUtils.getYearMonth(now));
		System.out.println(Mvel2ExpressionEvaluator.evaluate(
				"${curr_yyyymmdd}-1", sysMap));
	}

	public static void reload() {
		if (!loading.get()) {
			try {
				loading.set(true);
				ISystemPropertyService systemPropertyService = ContextFactory
						.getBean("systemPropertyService");
				List<SystemProperty> list = systemPropertyService
						.getAllSystemProperties();
				if (list != null && !list.isEmpty()) {
					for (SystemProperty p : list) {
						properties.put(p.getName(), p);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				loading.set(false);
			}
		}
	}

}