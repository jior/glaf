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

package org.jpage.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 这个类封装了所有的打印调试信息的方法。 如果设置了显示调试信息则能打印出信息，否则不能打印调试信息
 */
public final class Logger {

	private final static Log logger = LogFactory.getLog(Logger.class);

	public static void log(Object obj) {
		if (logger.isDebugEnabled()) {
			logger.debug(obj);
		}
	}

	public static void info(Object obj) {
		if (logger.isInfoEnabled()) {
			logger.info(obj);
		}
	}

	public static void warn(Object obj) {
		if (logger.isWarnEnabled()) {
			logger.warn(obj);
		}
	}

	public static void error(Object obj) {
		if (logger.isErrorEnabled()) {
			logger.error(obj);
		}
	}

	public static void fatal(Object obj) {
		if (logger.isFatalEnabled()) {
			logger.fatal(obj);
		}
	}

	public static boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

}