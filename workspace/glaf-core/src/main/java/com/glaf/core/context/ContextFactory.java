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

package com.glaf.core.context;
 
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.util.Constants;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public final class ContextFactory {

	protected static final Log logger = LogFactory.getLog(ContextFactory.class);

	private static org.springframework.context.ApplicationContext ctx;

	private static DataSource dataSource;

	public static org.springframework.context.ApplicationContext getApplicationContext() {
		return ctx;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> clazz) {
		if (ctx == null) {

		}
		String name = clazz.getSimpleName();
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return (T) ctx.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		if (ctx == null) {
			init();
		}
		return (T) ctx.getBean(name);
	}

	public static java.sql.Connection getConnection()   {
		if (dataSource == null) {
			dataSource = ContextFactory.getBean("dataSource");
		}
		if (dataSource != null) {
			try{
			   return dataSource.getConnection();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public static DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = ContextFactory.getBean("dataSource");
		}
		return dataSource;
	}

	protected static void init() {
		logger.info("◊∞‘ÿ≈‰÷√Œƒº˛......");
		if (ctx == null) {
			try {
				String filename = SystemConfig.getConfigRootPath()
						+ Constants.SPRING_APPLICATION_CONTEXT;
				logger.info("load spring config:" + filename);
				ctx = new FileSystemXmlApplicationContext(filename);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static DataSource getDataSource(String dataSourceName) {
		return ContextFactory.getBean(dataSourceName);
	}

	public static boolean hasBean(String name) {
		return ctx.containsBean(name);
	}

	public static void setContext(
			org.springframework.context.ApplicationContext context) {
		if (context != null) {
			ctx = context;
		}
	}

}