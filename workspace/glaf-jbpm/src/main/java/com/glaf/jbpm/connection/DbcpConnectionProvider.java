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

package com.glaf.jbpm.connection;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.exceptions.PoolNotFoundException;
import com.glaf.core.jdbc.ConnectionProviderImpl;

public class DbcpConnectionProvider extends ConnectionProviderImpl implements
		ConnectionProvider {
	protected final static Log logger = LogFactory
			.getLog(DbcpConnectionProvider.class);

	public DbcpConnectionProvider() {

	}

	public DbcpConnectionProvider(Properties properties)
			throws PoolNotFoundException {
		super(properties);
		this.configure(properties);
	}

	@Override
	public void close() throws HibernateException {
		try {
			super.destroy();
		} catch (Exception ex) {
			throw new HibernateException(ex);
		}
	}

	@Override
	public void configure(Properties properties) throws HibernateException {
		logger.info("jbpm properties:" + properties);
		try {
			if (properties == null) {
				properties = DBConfiguration.getDefaultDataSourceProperties();
			}
			String jdbcDriverClass = properties.getProperty(Environment.DRIVER);
			String jdbcUrl = properties.getProperty(Environment.URL);
			String user = properties.getProperty(Environment.USER);
			String password = properties.getProperty(Environment.PASS);
			String type = DBConfiguration.getDatabaseType(jdbcUrl);
			properties.setProperty("jdbc.name", "jbpm");
			properties.setProperty("jdbc.type", type);
			properties.setProperty("jdbc.driver", jdbcDriverClass);
			properties.setProperty("jdbc.url", jdbcUrl);
			properties.setProperty("jdbc.user", user);
			properties.setProperty("jdbc.password", password);

			super.create(properties, false, "jbpm");
		} catch (PoolNotFoundException ex) {
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

}
