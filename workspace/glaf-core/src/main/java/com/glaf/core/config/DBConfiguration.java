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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.Constants;
import com.glaf.core.util.PropertiesUtils;

public class DBConfiguration {
	protected static final Log logger = LogFactory
			.getLog(DBConfiguration.class);

	protected static Map<String, Properties> dataMap = new HashMap<String, Properties>();

	public static Properties getProperties(String name) {
		if (dataMap.isEmpty()) {
			reload();
		}
		return dataMap.get(name);
	}

	public static void reload() {
		try {
			String config = SystemConfig.getConfigRootPath()
					+ "/conf/templates/jdbc";
			File directory = new File(config);
			if (directory.exists() && directory.isDirectory()) {
				String[] filelist = directory.list();
				for (int i = 0; i < filelist.length; i++) {
					String filename = config + "/" + filelist[i];
					File file = new File(filename);
					if (file.isFile() && file.getName().endsWith(".properties")) {
						InputStream inputStream = new FileInputStream(file);
						Properties props = PropertiesUtils
								.loadProperties(inputStream);
						if (props != null) {
							dataMap.put(props.getProperty("jdbc.name"), props);
						}
					}
				}
			}

			String glabal_config = SystemConfig.getConfigRootPath()
					+ Constants.DEFAULT_JDBC_CONFIG;
			File file = new File(glabal_config);
			if (file.isFile() && file.getName().endsWith(".properties")) {
				InputStream inputStream = new FileInputStream(file);
				Properties props = PropertiesUtils.loadProperties(inputStream);
				if (props != null) {
					dataMap.put("default", props);
					dataMap.put(props.getProperty("jdbc.name"), props);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	private DBConfiguration() {
		reload();
	}

}