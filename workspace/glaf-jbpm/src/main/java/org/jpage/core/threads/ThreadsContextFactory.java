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

package org.jpage.core.threads;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ThreadsContextFactory {

	private static Log logger = LogFactory.getLog(ThreadsContextFactory.class);

	private static org.springframework.context.ApplicationContext ctx;

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		if (ctx == null) {
			ThreadsContextFactory.init();
		}
		return (T) ctx.getBean(name);
	}

	public static Map<String, ?> getBeans(Class<?> iType) {
		return ctx.getBeansOfType(iType);
	}

	public static boolean hasBean(String name) {
		return ctx.containsBean(name);
	}

	protected static void init() {
		logger.info("◊∞‘ÿthreads≈‰÷√Œƒº˛......");
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(
					"org/jpage/core/threads/threads-context.xml");
		}
	}

}