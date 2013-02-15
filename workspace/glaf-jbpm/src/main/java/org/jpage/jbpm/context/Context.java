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

package org.jpage.jbpm.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;

public class Context {

	private final static Log logger = LogFactory.getLog(Context.class);

	static ThreadLocal contextsThreadLocal = new ThreadLocal();

	static ThreadLocal attributesThreadLocal = new ThreadLocal();

	static ThreadLocal pagesThreadLocal = new ThreadLocal();

	private Context() {

	}

	public static synchronized JbpmContext getCurrentJbpmContext() {
		JbpmConfiguration cfg = JbpmConfiguration.getInstance();
		JbpmContext jbpmContext = cfg.createJbpmContext();
		if (logger.isDebugEnabled()) {
			logger.debug("已经创建jbpm context");
			logger.debug("---------------------------------------------");
		}
		return jbpmContext;
	}

	public static synchronized void close(JbpmContext jbpmContext) {
		try {
			if (jbpmContext != null) {

				jbpmContext.close();
				if (logger.isDebugEnabled()) {
					logger.debug("已经关闭jbpm context.");
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				throw new JbpmException("Can't close null jbpm context ");
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new JbpmException("Can't close jbpm context ");
		} finally {
			jbpmContext = null;
		}
	}

	public static void create() {
		attributesThreadLocal.set(new HashMap());
		contextsThreadLocal.set(new HashMap());
		pagesThreadLocal.set(new HashMap());
	}

	public static void destroy() {
		attributesThreadLocal.set(null);
		contextsThreadLocal.set(null);
		pagesThreadLocal.set(null);
	}

	public static Object getContext(Class clazz) {
		Map contexts = (Map) contextsThreadLocal.get();
		Object context = contexts.get(clazz);
		if (context == null) {
			try {
				context = clazz.newInstance();
				contexts.put(clazz, context);
			} catch (Exception e) {
				throw new RuntimeException("couldn't instantiate context '"
						+ clazz.getName() + "'");
			}
		}
		return context;
	}

	public static Object getAttribute(String key) {
		Map params = (Map) attributesThreadLocal.get();
		if (key != null && params != null) {
			return params.get(key);
		}
		return null;
	}

	public static void setAttribute(String key, Object value) {
		Map params = (Map) attributesThreadLocal.get();
		if (key != null && value != null) {
			if (params != null) {
				params.put(key, value);
			}
		}
	}

}
