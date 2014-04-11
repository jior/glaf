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

package com.glaf.core.startup;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.QueryUtils;

public class DBNativeCmdThread extends Thread {

	protected final static Log logger = LogFactory
			.getLog(DBNativeCmdThread.class);

	protected String cmd;

	protected java.util.Properties props;

	public DBNativeCmdThread(String cmd, java.util.Properties props) {
		this.cmd = cmd;
		this.props = props;
	}

	public void run() {
		Map<String, Object> params = new HashMap<String, Object>();
		java.util.Enumeration<?> e = props.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = props.getProperty(key);
			params.put(key, value);
		}
		try {
			String path = SystemProperties.getConfigRootPath()
					+ "/conf/bootstrap/scripts";
			File dir = new File(path);
			File contents[] = dir.listFiles();
			if (contents != null) {
				for (int i = 0; i < contents.length; i++) {
					if (contents[i].isFile()
							&& StringUtils.endsWith(contents[i].getName(),
									".sql")) {
						params.put("file", contents[i].getAbsolutePath());
						String command = QueryUtils.replaceBlankParas(cmd,
								params);
						logger.debug("exec cmd:" + command);
						Runtime.getRuntime().exec(command);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

}
