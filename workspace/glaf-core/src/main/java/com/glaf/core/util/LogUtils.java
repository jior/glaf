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

package com.glaf.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

public class LogUtils {
	@SuppressWarnings("serial")
	public static class LogInitializationException extends Exception {
		public LogInitializationException(String msg) {
			super(msg);
		}
	}

	public static final String PLATFORM_L4J = "glaf-log4j.properties";

	private final static Log LOG = LogFactory.getLog(LogUtils.class);

	private static Method DEBUG = null;
	private static Method INFO = null;
	private static Method WARN = null;
	private static Method ERROR = null;
	private static Method FATAL = null;

	static {
		try {
			DEBUG = Log.class.getMethod("debug", new Class[] { Object.class });
			INFO = Log.class.getMethod("info", new Class[] { Object.class });
			WARN = Log.class.getMethod("warn", new Class[] { Object.class });
			ERROR = Log.class.getMethod("error", new Class[] { Object.class });
			FATAL = Log.class.getMethod("fatal", new Class[] { Object.class });
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Cannot init log methods", e);
			}
		}
	}

	public static void debug(Object o) {
		LOG.debug(o);
	}

	public static void error(Object o) {
		LOG.error(o);
	}

	public static void fatal(Object o) {
		LOG.fatal(o);
	}

	public static PrintStream getDebugStream(final Log logger) {
		return getLogStream(logger, DEBUG);
	}

	public static PrintStream getErrorStream(final Log logger) {
		return getLogStream(logger, ERROR);
	}

	public static PrintStream getFatalStream(final Log logger) {
		return getLogStream(logger, FATAL);
	}

	public static PrintStream getInfoStream(final Log logger) {
		return getLogStream(logger, INFO);
	}

	/** Returns a stream that, when written to, adds log lines. */
	private static PrintStream getLogStream(final Log logger,
			final Method method) {
		return new PrintStream(new ByteArrayOutputStream() {
			private int scan = 0;

			public void flush() throws IOException {
				if (!hasNewline())
					return;
				try {
					method.invoke(logger, new Object[] { toString().trim() });
				} catch (Exception e) {
					if (LOG.isErrorEnabled()) {
						LOG.error("Cannot log with method [" + method + "]", e);
					}
				}
				reset();
				scan = 0;
			}

			private boolean hasNewline() {
				for (; scan < count; scan++) {
					if (buf[scan] == '\n')
						return true;
				}
				return false;
			}
		}, true);
	}

	public static PrintStream getWarnStream(final Log logger) {
		return getLogStream(logger, WARN);
	}

	public static void info(Object o) {
		LOG.info(o);
	}

	/**
	 * Initialize log4j based on platform-log4j.properties.
	 * 
	 * @return an message suitable for display to the user
	 * @throws LogInitializationException
	 *             if log4j fails to initialize correctly
	 */
	public static String initPlatformLog4j() throws LogInitializationException {
		// allow platform log4j to override any normal initialized one
		URL platform_l4j = LogUtils.class.getClassLoader().getResource(
				PLATFORM_L4J);
		if (platform_l4j != null) {
			LogManager.resetConfiguration();
			PropertyConfigurator.configure(platform_l4j);
			return "Logging initialized using configuration in " + platform_l4j;
		} else {
			throw new LogInitializationException(
					"Unable to initialize logging using "
							+ LogUtils.PLATFORM_L4J
							+ ", not found on CLASSPATH!");
		}
	}

	public static boolean isDebug() {
		// if (SystemConfig.hasObject("logger.debug")) {
		// return SystemConfig.getBoolean("logger.debug");
		// }
		return true;
	}

	public static void warn(Object o) {
		LOG.warn(o);
	}

	private LogUtils() {

	}

}