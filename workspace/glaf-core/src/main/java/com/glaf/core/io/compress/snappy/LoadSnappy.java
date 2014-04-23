/**
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
package com.glaf.core.io.compress.snappy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.NativeCodeLoader;

/**
 * Determines if Snappy native library is available and loads it if available.
 */
public class LoadSnappy {
	private static final Log LOG = LogFactory
			.getLog(LoadSnappy.class.getName());

	private static boolean AVAILABLE = false;
	private static boolean LOADED = false;

	static {

		try {
			System.loadLibrary("snappy");
			LOG.warn("Snappy native library is available");
			AVAILABLE = true;
		} catch (UnsatisfiedLinkError ex) {

		}
		boolean nativeAvailable = NativeCodeLoader.isNativeCodeLoaded();
		LOADED = AVAILABLE && nativeAvailable;
		if (LOADED) {
			LOG.info("Snappy native library loaded");
		} else {
			LOG.warn("Snappy native library not loaded");
		}
	}

	/**
	 * Returns if Snappy native library is loaded.
	 * 
	 * @return <code>true</code> if Snappy native library is loaded,
	 *         <code>false</code> if not.
	 */
	public static boolean isAvailable() {
		return AVAILABLE;
	}

	/**
	 * Returns if Snappy native library is loaded.
	 * 
	 * @return <code>true</code> if Snappy native library is loaded,
	 *         <code>false</code> if not.
	 */
	public static boolean isLoaded() {
		return LOADED;
	}

}
