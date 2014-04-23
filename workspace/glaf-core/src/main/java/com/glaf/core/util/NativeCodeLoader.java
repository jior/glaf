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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.Configuration;

public class NativeCodeLoader {

	private static final Log LOG = LogFactory.getLog(NativeCodeLoader.class);

	private static volatile boolean nativeCodeLoaded = false;

	static {
		// Try to load native glaf library and set fallback flag appropriately
		LOG.debug("Trying to load the custom-built native-glaf library...");
		try {
			System.loadLibrary("glaf");
			LOG.info("Loaded the native-glaf library");
			nativeCodeLoaded = true;
		} catch (Throwable t) {
			// Ignore failure to load
			LOG.debug("Failed to load native-glaf with error: " + t);
			LOG.debug("java.library.path="
					+ System.getProperty("java.library.path"));
		}

		if (!nativeCodeLoaded) {
			LOG.warn("Unable to load native-glaf library for your platform... "
					+ "using builtin-java classes where applicable");
		}
	}

	/**
	 * Check if native-glaf code is loaded for this platform.
	 * 
	 * @return <code>true</code> if native-glaf is loaded, else
	 *         <code>false</code>
	 */
	public static boolean isNativeCodeLoaded() {
		return nativeCodeLoaded;
	}

	/**
	 * Return if native glaf libraries, if present, can be used for this job.
	 * 
	 * @param conf
	 *            configuration
	 * 
	 * @return <code>true</code> if native glaf libraries, if present, can be
	 *         used for this job; <code>false</code> otherwise.
	 */
	public boolean getLoadNativeLibraries(Configuration conf) {
		return conf.getBoolean("glaf.native.lib", true);
	}

	/**
	 * Set if native glaf libraries, if present, can be used for this job.
	 * 
	 * @param conf
	 *            configuration
	 * @param loadNativeLibraries
	 *            can native glaf libraries be loaded
	 */
	public void setLoadNativeLibraries(Configuration conf,
			boolean loadNativeLibraries) {
		conf.setBoolean("glaf.native.lib", loadNativeLibraries);
	}

}