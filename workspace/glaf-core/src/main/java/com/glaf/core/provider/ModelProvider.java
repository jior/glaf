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

package com.glaf.core.provider;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ModelProvider implements Singleton {
	protected static final Log log = LogFactory.getLog(ModelProvider.class);

	private static ModelProvider instance;

	/**
	 * Returns the singleton instance providing the ModelProvider functionality.
	 * 
	 * @return the ModelProvider instance
	 */
	public static synchronized ModelProvider getInstance() {
		// set in a localInstance to prevent threading issues when
		// reseting it in setInstance()
		ModelProvider localInstance = instance;
		if (localInstance == null) {
			localInstance = ServiceProvider.getInstance().get(
					ModelProvider.class);
			instance = localInstance;
		}
		return localInstance;
	}

	/**
	 * Creates a new ModelProvider, initializes it and sets it in the instance
	 * here.
	 */
	public static void refresh() {
		try {
			ServiceProvider.getInstance().removeInstance(ModelProvider.class);
			final ModelProvider localProvider = ServiceProvider.getInstance()
					.get(ModelProvider.class);
			setInstance(localProvider);
			// initialize it
			// ......
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Makes it possible to override the default ModelProvider with a custom
	 * implementation.
	 * 
	 * @param instance
	 *            the custom ModelProvider
	 */
	public static synchronized void setInstance(ModelProvider instance) {
		ModelProvider.instance = instance;
	}

	private List<Module> modules;

	public List<Module> getModules() {
		return modules;
	}

}
