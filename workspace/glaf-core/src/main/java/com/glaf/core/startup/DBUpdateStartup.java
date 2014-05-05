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

package com.glaf.core.startup;

import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.threads.ThreadFactory;

public class DBUpdateStartup implements Bootstrap {

	protected final static Log logger = LogFactory
			.getLog(DBUpdateStartup.class);

	public void startup(ServletContext context, String text) {
		logger.debug("-----------------DBUpdateStartup.startup----------------");
		Properties props = DBConfiguration.getDefaultDataSourceProperties();
		if (props != null) {
			logger.debug("->jdbc driver:"
					+ props.getProperty(DBConfiguration.JDBC_DRIVER));
			logger.debug("->jdbc url:"
					+ props.getProperty(DBConfiguration.JDBC_URL));
			try {
				/**
				 * 检查连接信息，如果正确，执行更新
				 */
				if (DBConnectionFactory.checkConnection(props)) {
					logger.debug("准备执行更新SQL......");
					DBUpdateThread thread = new DBUpdateThread(props);
					ThreadFactory.run(thread);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

}
