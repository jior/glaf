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

package com.glaf.mail.config;

import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.Configuration;

/**
 * Adds Mail configuration files to a Configuration
 */
public class MailConfiguration extends Configuration {

	protected static final Log LOG = LogFactory.getLog(MailConfiguration.class);

	public static Configuration addCustomResources(Configuration conf) {
		conf.addResource("glaf-mail-default.xml");
		conf.addResource("glaf-mail-site.xml");
		return conf;
	}

	/**
	 * Creates a Configuration with Mail resources
	 * 
	 * @return a Configuration with Mail resources
	 */
	public static Configuration create() {
		Configuration conf = new Configuration();
		return addCustomResources(conf);
	}

	/**
	 * Creates a clone of passed configuration.
	 * 
	 * @param that
	 *            Configuration to clone.
	 * @return a Configuration created with the glaf-mail-*.xml files plus the
	 *         given configuration.
	 */
	public static Configuration create(final Configuration that) {
		Configuration conf = create();
		merge(conf, that);
		return conf;
	}

	/**
	 * Merge two configurations.
	 * 
	 * @param destConf
	 *            the configuration that will be overwritten with items from the
	 *            srcConf
	 * @param srcConf
	 *            the source configuration
	 **/
	public static void merge(Configuration destConf, Configuration srcConf) {
		for (Entry<String, String> e : srcConf) {
			destConf.set(e.getKey(), e.getValue());
		}
	}

	private MailConfiguration() {
		super();
		addCustomResources(this);
	}
}