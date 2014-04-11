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

package com.glaf.core.context;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ApplicationContext {

	private static final Log logger = LogFactory
			.getLog(ApplicationContext.class);

	public final static String sp = System.getProperty("file.separator");

	private static volatile String appPath;

	private static volatile String classPath;

	private static volatile String contextPath;

	public static String getAppClasspath() {
		if (classPath == null) {
			StringBuffer buffer = new StringBuffer(5000);
			String root = getAppPath();
			String path = root + sp + "WEB-INF" + sp + "lib";
			java.io.File file = new java.io.File(path);
			if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					if (filelist[i].endsWith(".jar")) {
						String filename = path + sp + filelist[i];
						buffer.append(filename).append(';');
					}
				}
			}
			classPath = buffer.toString();
		}
		return classPath;
	}

	public static String getAppPath() {
		if (appPath == null) {
			try {
				Resource resource = new ClassPathResource("/glaf.properties");
				appPath = resource.getFile().getParentFile().getParentFile()
						.getParentFile().getAbsolutePath();
				logger.info("app path:" + appPath);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return appPath;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void setAppPath(String appPath) {
		ApplicationContext.appPath = appPath;
	}

	public static void setContextPath(String pContextPath) {
		if (StringUtils.isNotEmpty(pContextPath)) {
			if (StringUtils.isEmpty(contextPath)) {
				contextPath = pContextPath;
			}
		}
	}

	private ApplicationContext() {
	}

}