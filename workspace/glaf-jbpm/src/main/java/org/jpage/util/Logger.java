/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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