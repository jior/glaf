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

package com.glaf.core.jdbc.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;

public class SpringDataSourceFactory {
	protected static final Log logger = LogFactory
			.getLog(SpringDataSourceFactory.class);

	private final static String DEFAULT_CONFIG = "conf/datasource/multi-datasource-context.xml";

	private static ClassPathXmlApplicationContext ctx;

	public static ClassPathXmlApplicationContext getApplicationContext() {
		return ctx;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		if (ctx == null) {
			reload();
		}
		return (T) ctx.getBean(name);
	}

	public static java.sql.Connection getConnection(String dataSourceName)
			throws SQLException {
		DataSource dataSource = getBean(dataSourceName);
		if (dataSource != null) {
			return dataSource.getConnection();
		}
		return null;
	}

	public static DataSource getDataSource(String dataSourceName) {
		return getBean(dataSourceName);
	}

	public static ClassPathXmlApplicationContext reload() {
		if (ctx != null) {
			ctx.close();
			ctx = null;
		}
		String configLocation = CustomProperties
				.getString("sys.datasource.context");
		if (StringUtils.isEmpty(configLocation)) {
			configLocation = SystemProperties
					.getString("sys.datasource.context");
		}
		if (StringUtils.isEmpty(configLocation)) {
			configLocation = DEFAULT_CONFIG;
		}
		ctx = new ClassPathXmlApplicationContext(configLocation);
		logger.debug("start spring ioc from: " + configLocation);
		return ctx;
	}

	private SpringDataSourceFactory() {

	}
}