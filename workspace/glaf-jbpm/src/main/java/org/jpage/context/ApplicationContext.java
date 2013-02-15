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


package org.jpage.context;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ApplicationContext {

	private static String appPath;

	private static String contextPath;

	private ApplicationContext() {
	}

	public static String getAppPath() {
		if (appPath == null) {
			try {
				Resource res = new ClassPathResource("jbpm.cfg.xml");
				appPath = res.getFile().getParentFile().getParentFile()
						.getParentFile().getAbsolutePath();
				System.out.println("app path:" + appPath);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return appPath;
	}

	public static void setAppPath(String pAppPath) {
		appPath = pAppPath;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void setContextPath(String pContextPath) {
		if (StringUtils.isNotBlank(pContextPath)) {
			if (StringUtils.isBlank(contextPath)) {
				contextPath = pContextPath;
			}
		}
	}

}