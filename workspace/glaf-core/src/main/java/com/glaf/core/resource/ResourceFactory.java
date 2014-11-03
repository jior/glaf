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

package com.glaf.core.resource;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.*;

/**
 * 分布式资源工厂类 如果需要进行分布式部署，需要将资源项写入分布式存储中
 */
public class ResourceFactory {
	protected static final Log logger = LogFactory
			.getLog(ResourceFactory.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static final ResourceChannel channel = ResourceChannel
			.getInstance();

	protected static final String DISTRIBUTED_ENABLED = "distributed.resource.enabled";

	protected static List<String> regions = new java.util.concurrent.CopyOnWriteArrayList<String>();

	protected static ExecutorService pool = Executors.newCachedThreadPool();

	public static void clear(String region) {
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			String regionName = Environment.getCurrentSystemName() + "_res_"
					+ region;
			if (SystemProperties.getDeploymentSystemName() != null) {
				regionName = SystemProperties.getDeploymentSystemName() + "_"
						+ Environment.getCurrentSystemName() + "_res_" + region;
			}
			try {
				channel.clear(regionName);
				logger.debug("###################################");
				logger.debug(regionName + " clear.");
				logger.debug("###################################");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

	public static void clearAll() {
		if (regions != null && !regions.isEmpty()) {
			for (String region : regions) {
				try {
					String regionName = Environment.getCurrentSystemName()
							+ "_res_" + region;
					if (SystemProperties.getDeploymentSystemName() != null) {
						regionName = SystemProperties.getDeploymentSystemName()
								+ "_" + Environment.getCurrentSystemName()
								+ "_res_" + region;
					}
					channel.clear(regionName);
					logger.debug("###################################");
					logger.debug(regionName + " clear.");
					logger.debug("###################################");
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			}
		}
	}

	public static byte[] getData(final String region, final String key) {
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			String regionName = Environment.getCurrentSystemName() + "_res_"
					+ region;
			String complexKey = Environment.getCurrentSystemName() + "_res_"
					+ key;
			if (SystemProperties.getDeploymentSystemName() != null) {
				regionName = SystemProperties.getDeploymentSystemName() + "_"
						+ Environment.getCurrentSystemName() + "_res_" + region;
			}
			if (SystemProperties.getDeploymentSystemName() != null) {
				complexKey = SystemProperties.getDeploymentSystemName() + "_"
						+ Environment.getCurrentSystemName() + "_res_" + key;
			}
			final String regionName2 = regionName;
			final String complexKey2 = complexKey;

			boolean waitFor = true;
			Callable<byte[]> task = new Callable<byte[]>() {
				@Override
				public byte[] call() throws Exception {
					return channel.getData(regionName2, complexKey2);
				}
			};
			try {
				Future<byte[]> result = pool.submit(task);
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
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		return null;
	}

	public static void put(final String region, final String key,
			final byte[] value) {
		if (!regions.contains(region)) {
			regions.add(region);
		}
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			String regionName = Environment.getCurrentSystemName() + "_res_"
					+ region;
			String complexKey = Environment.getCurrentSystemName() + "_res_"
					+ key;
			if (SystemProperties.getDeploymentSystemName() != null) {
				regionName = SystemProperties.getDeploymentSystemName() + "_"
						+ Environment.getCurrentSystemName() + "_res_" + region;
			}
			if (SystemProperties.getDeploymentSystemName() != null) {
				complexKey = SystemProperties.getDeploymentSystemName() + "_"
						+ Environment.getCurrentSystemName() + "_res_" + key;
			}
			try {
				channel.put(regionName, complexKey, value);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

	public static void remove(String region, String key) {
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			String regionName = Environment.getCurrentSystemName() + "_res_"
					+ region;
			String complexKey = Environment.getCurrentSystemName() + "_res_"
					+ key;
			if (SystemProperties.getDeploymentSystemName() != null) {
				regionName = SystemProperties.getDeploymentSystemName() + "_"
						+ Environment.getCurrentSystemName() + "_res_" + region;
			}
			if (SystemProperties.getDeploymentSystemName() != null) {
				complexKey = SystemProperties.getDeploymentSystemName() + "_"
						+ Environment.getCurrentSystemName() + "_res_" + key;
			}
			try {
				channel.remove(regionName, complexKey);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

	private ResourceFactory() {

	}

}
