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
