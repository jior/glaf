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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.Constants;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public final class ContextFactory {
	private static Configuration conf = BaseConfiguration.create();

	private static volatile org.springframework.context.ApplicationContext ctx;

	private static Log logger = LogFactory.getLog(ContextFactory.class);

	public static org.springframework.context.ApplicationContext getApplicationContext() {
		return ctx;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> clazz) {
		if (ctx == null) {
			throw new RuntimeException(
					" Spring context is null, please check your spring config.");
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
		if (ctx == null) {
			throw new RuntimeException(
					" Spring context is null, please check your spring config.");
		}
		return (T) ctx.getBean(name);
	}

	public static boolean hasBean(String name) {
		if (ctx == null) {
			init();
		}
		if (ctx == null) {
			throw new RuntimeException(
					" Spring context is null, please check your spring config.");
		}
		return ctx.containsBean(name);
	}

	protected static void init() {
		reload();
	}

	public static void reload() {
		logger.info("装载配置文件......");
		if (ctx == null) {
			try {
				if (StringUtils.isNotEmpty(conf.get("spring-config"))) {
					String filename = SystemProperties.getConfigRootPath()
							+ conf.get("spring-config");
					logger.info("load custom spring config:" + filename);
					if (filename.startsWith("/")) {
						filename = "/" + filename;// For linux
					}
					ctx = new FileSystemXmlApplicationContext(filename);
				} else if (StringUtils.isNotEmpty(conf
						.get("spring-config-file"))) {
					String filename = conf.get("spring-config-file");
					logger.info("load custom spring config:" + filename);
					if (filename.startsWith("/")) {
						filename = "/" + filename;// For linux
					}
					ctx = new FileSystemXmlApplicationContext(filename);
				} else if (StringUtils.isNotEmpty(System
						.getProperty("spring-config-file"))) {
					String filename = System.getProperty("spring-config-file");
					logger.info("load custom spring config:" + filename);
					if (filename.startsWith("/")) {
						filename = "/" + filename;// For linux
					}
					ctx = new FileSystemXmlApplicationContext(filename);
				} else {
					String filename = SystemProperties.getConfigRootPath()
							+ Constants.SPRING_APPLICATION_CONTEXT;
					logger.info("load default spring config:" + filename);
					ctx = new FileSystemXmlApplicationContext(filename);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

	public static void setContext(
			org.springframework.context.ApplicationContext context) {
		if (context != null) {
			ctx = context;
		}
	}

	public static void main(String[] args) {
		System.out.println(ContextFactory.hasBean("dataSource"));
	}

}