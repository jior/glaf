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

package com.glaf.core.jdbc;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract class that represents an external connection pool
 * 
 */
public abstract class ExternalConnectionPool {

	protected final static Log logger = LogFactory
			.getLog(ExternalConnectionPool.class);

	private static ExternalConnectionPool instance;

	/**
	 * 
	 * @param externalConnectionPoolClassName
	 *            The full class name of the external connection pool
	 * @return An instance of the external connection pool
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public final synchronized static ExternalConnectionPool getInstance(
			String externalConnectionPoolClassName)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		if (instance == null) {
			instance = (ExternalConnectionPool) Class.forName(
					externalConnectionPoolClassName).newInstance();
		}
		return instance;
	}

	/**
	 * If the external connection pool supports interceptors this method should
	 * be overwritten
	 * 
	 * @param interceptors
	 *            List of PoolInterceptorProvider comprised of all the
	 *            interceptors injected with Weld
	 */
	public void loadInterceptors(List<PoolInterceptorProvider> interceptors) {
	}

	/**
	 * @return A Connection from the external connection pool
	 */
	public abstract Connection getConnection();

}
