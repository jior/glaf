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

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.jdbc.datasource.DataSourceFactory;
import com.glaf.core.jdbc.datasource.IDataSourceFactory;
import com.glaf.core.util.Constants;
import com.glaf.core.util.PropertiesUtils;

public class DBConfiguration {
	protected static final Log logger = LogFactory
			.getLog(DBConfiguration.class);

	public javax.sql.DataSource buildDataSource() {
		javax.sql.DataSource dataSource = null;
		String filename = SystemConfig.getConfigRootPath()
				+ Constants.DEFAULT_JDBC_CONFIG;
		logger.info("load jdbc config:" + filename);
		Properties properties = PropertiesUtils.loadFilePathResource(filename);
		IDataSourceFactory dataSourceFactory = DataSourceFactory
				.createDataSource(properties);
		dataSource = dataSourceFactory.makePooledDataSource(properties);
		return dataSource;
	}

	public javax.sql.DataSource buildDataSource(Properties properties) {
		javax.sql.DataSource dataSource = null;
		IDataSourceFactory dataSourceFactory = DataSourceFactory
				.createDataSource(properties);
		dataSource = dataSourceFactory.makePooledDataSource(properties);
		return dataSource;
	}

}