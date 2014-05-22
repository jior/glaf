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

package com.glaf.j2cache.fourinone;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.fourinone.BeanContext;

public class CacheServer {
	private static String CONFIG_FILE = "../conf/cacheserver.ini";

	public static Properties loadCacheConfig() {
		if (System.getProperty("cs.cfg.path") != null) {
			CONFIG_FILE = System.getProperty("cs.cfg.path");
		}
		System.out.println("config path:" + CONFIG_FILE);
		InputStream configStream = null;
		Properties props = new Properties();
		try {
			File configFile = new File(CONFIG_FILE);
			configStream = new FileInputStream(configFile);
			props.load(configStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (configStream != null) {
				try {
					configStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return props;
	}

	public static void main(String[] args) {
		Properties props = loadCacheConfig();
		String host = "127.0.0.1";
		int port = 15818;
		if (props.getProperty("port") != null) {
			port = Integer.parseInt(props.getProperty("port"));
		}
		String[][] cacheServer = new String[][] {
				{ "127.0.0.1", String.valueOf(port) },
				{ "127.0.0.1", String.valueOf(port + 1) } };
		BeanContext.startCache(host, port, cacheServer);
	}

	public static void close(String[] args) {
		System.out.println("stop");
		System.exit(3);
	}
}