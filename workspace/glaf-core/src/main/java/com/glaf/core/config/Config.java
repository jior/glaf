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

package com.glaf.core.config;

/**
 * Implementors define a config algorithm. All implementors <b>must</b> be
 * threadsafe.
 * 
 * 
 */
public interface Config {

	/**
	 * Get an item from the config, nontransactionally
	 * 
	 * @param key
	 * @return the configd object or <tt>null</tt>
	 */
	String getString(String key);

	/**
	 * Add an item to the config, nontransactionally, with failfast semantics
	 * 
	 * @param key
	 * @param value
	 * 
	 */
	void put(String key, String value);

	/**
	 * Remove an item from the config
	 */
	void remove(String key);

	/**
	 * Clear the config
	 */
	void clear();

}
