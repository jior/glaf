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

package com.glaf.core.execution;

import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.PropertiesUtils;

/**
 * 
 * 可以指定一个配置文件，执行配置文件中指定的内容，路径是相对于应用配置SystemProperties.getConfigRootPath()的路径<br/>
 * 如放到conf的test.properties，那么取值为/conf/test.properties
 * 
 */
public class SolitaryExecutionManager {
	private static class SolitaryExecutionManagerHolder {
		public static SolitaryExecutionManager instance = new SolitaryExecutionManager();
	}

	private static final Log logger = LogFactory
			.getLog(SolitaryExecutionManager.class);

	protected static AtomicBoolean running = new AtomicBoolean(false);

	public static SolitaryExecutionManager getInstance() {
		return SolitaryExecutionManagerHolder.instance;
	}

	private SolitaryExecutionManager() {

	}

	/**
	 * 指定配置文件
	 * 
	 * @param configFile
	 *            相对于应用配置路径目录的文件
	 *            如放到conf的exec.properties，那么取值为/conf/exec.properties
	 */
	public void execute(String configFile) {
		if (!running.get()) {
			try {
				running.set(true);
				if (!configFile.startsWith("/")) {
					configFile = "/" + configFile;
				}
				String filename = SystemProperties.getConfigRootPath()
						+ configFile;
				Properties props = PropertiesUtils
						.loadFilePathResource(filename);
				if (props != null && props.keys().hasMoreElements()) {
					Enumeration<?> e = props.keys();
					while (e.hasMoreElements()) {
						String className = (String) e.nextElement();
						String content = props.getProperty(className);
						Object object = ClassUtils.instantiateObject(className);
						if (object instanceof ExecutionHandler) {
							ExecutionHandler handler = (ExecutionHandler) object;
							try {
								handler.execute(content);
							} catch (Exception ex) {
								ex.printStackTrace();
								logger.error(ex);
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				running.set(false);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("应用配置路径：" + SystemProperties.getConfigRootPath());
		if (args != null && args.length == 1) {
			SolitaryExecutionManager.getInstance().execute(args[0]);
		} else {
			System.out.println("请指定配置文件！");
			throw new java.lang.IllegalArgumentException(
					"config file is not specified");
		}
	}

}
