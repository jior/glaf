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

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesHelper;

public class DataSourceFactory {

	private static final ConcurrentMap<String, IDataSourceFactory> classCache = new ConcurrentHashMap<String, IDataSourceFactory>();

	private static final ConcurrentMap<String, DataSource> dsCache = new ConcurrentHashMap<String, DataSource>();

	public static IDataSourceFactory createDataSource(Properties properties) {
		IDataSourceFactory dataSourceFactory = null;
		String connectionPoolType = PropertiesHelper.getString(
				DBConfiguration.JDBC_POOL_TYPE, properties, "druid");
		String className = null;
		if (StringUtils.equalsIgnoreCase(connectionPoolType, "C3P0")) {
			className = "com.glaf.core.jdbc.datasource.C3P0DataSourceFactory";
		} else if (StringUtils.equalsIgnoreCase(connectionPoolType, "DBCP")) {
			className = "com.glaf.core.jdbc.datasource.DBCPDataSourceFactory";
		} else {
			className = "com.glaf.core.jdbc.datasource.DruidDataSourceFactory";
		}

		if (classCache.get(connectionPoolType) != null) {
			return classCache.get(connectionPoolType);
		}

		dataSourceFactory = (IDataSourceFactory) ClassUtils
				.instantiateObject(className);
		classCache.put(connectionPoolType, dataSourceFactory);

		return dataSourceFactory;
	}

	public static DataSource createDataSource(String systemName) {
		if (dsCache.get(systemName) != null) {
			return dsCache.get(systemName);
		}
		Properties props = DBConfiguration
				.getDataSourcePropertiesByName(systemName);
		IDataSourceFactory model = createDataSource(props);
		DataSource ds = model.makePooledDataSource(props);
		dsCache.put(systemName, ds);
		return ds;
	}

	public static Connection getConnection(String systemName)
			throws SQLException {
		DataSource ds = null;
		if (dsCache.get(systemName) != null) {
			ds = dsCache.get(systemName);
		}

		if (ds == null) {
			Properties props = DBConfiguration
					.getDataSourcePropertiesByName(systemName);
			IDataSourceFactory model = createDataSource(props);
			ds = model.makePooledDataSource(props);
			dsCache.put(systemName, ds);
		}

		return ds.getConnection();
	}

	public static void main(String[] args) {
		Connection conn = null;
		DatabaseMetaData dbmd = null;
		try {
			conn = DataSourceFactory.getConnection("pdemo");
			dbmd = conn.getMetaData();
			System.out.println("DatabaseProductName:"
					+ dbmd.getDatabaseProductName());
			System.out.println("DatabaseProductVersion:"
					+ dbmd.getDatabaseProductVersion());
			System.out.println("DriverName:" + dbmd.getDriverName());
			System.out.println("DriverVersion:" + dbmd.getDriverVersion());
			System.out.println("SQLKeywords:" + dbmd.getSQLKeywords());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
	}

	private DataSourceFactory() {

	}

}