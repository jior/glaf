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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 分布式配置工厂类 如果需要进行分布式部署，需要将配置项写入分布式存储中
 */
public class ConfigFactory {
	protected static final Log logger = LogFactory.getLog(ConfigFactory.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static final ConfigChannel channel = ConfigChannel.getInstance();

	protected static final String DISTRIBUTED_ENABLED = "distributed.config.enabled";

	protected static ExecutorService pool = Executors.newCachedThreadPool();

	public static void clear(String region) {
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			channel.clear(region);
		}
	}

	public static JSONObject getJSONObject(String region, String key) {
		String text = getString(region, key);
		if (StringUtils.isNotEmpty(text)) {
			JSONObject jsonObject = JSON.parseObject(text);
			return jsonObject;
		}
		return null;
	}

	public static String getString(final String region, final String key) {
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			boolean waitFor = true;
			Callable<String> task = new Callable<String>() {
				@Override
				public String call() throws Exception {
					return channel.getString(region, key);
				}
			};
			try {
				Future<String> result = pool.submit(task);
				long start = System.currentTimeMillis();
				// 如果需要等待执行结果
				if (waitFor) {
					while (true) {
						if (System.currentTimeMillis() - start > 2000) {
							break;
						}
						if (result.isDone()) {
							return result.get();
						}
					}
				}
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		return null;
	}

	public static void put(final String region, final String key,
			final String value) {
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			channel.put(region, key, value);
		}
	}

	public static void remove(String region, String key) {
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			channel.remove(region, key);
		}
	}

	private ConfigFactory() {

	}

}
