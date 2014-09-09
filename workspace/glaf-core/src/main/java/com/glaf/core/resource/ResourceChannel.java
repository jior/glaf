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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式资源配置通道
 * 
 */
public class ResourceChannel {

	protected final static Logger log = LoggerFactory
			.getLogger(ResourceChannel.class);

	private static class ResourceChannelHolder {
		public static ResourceChannel instance = new ResourceChannel();
	}

	public static ResourceChannel getInstance() {
		return ResourceChannelHolder.instance;
	}

	/**
	 * 初始化分布式资源配置通道
	 * 
	 */
	private ResourceChannel() {
		try {
			ResourceManager.initResourceProvider();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Clear the resource
	 */
	public void clear(String region) {
		ResourceManager.clear(region);
	}

	/**
	 * 关闭到通道的连接
	 */
	public void close() {
		ResourceManager.shutdown();
	}

	/**
	 * 获取分布式配置中的数据
	 * 
	 * @param region
	 * @param key
	 * @return
	 */
	public byte[] getData(String region, String key) {
		if (region != null && key != null) {
			return ResourceManager.getData(region, key);
		}
		return null;
	}

	/**
	 * 写入分布式配置
	 * 
	 * @param region
	 * @param key
	 * @param value
	 */
	public void put(String region, String key, byte[] value) {
		if (region != null && key != null) {
			if (value == null)
				remove(region, key);
			else {
				ResourceManager.put(region, key, value);
			}
		}
	}

	/**
	 * 删除分布式配置
	 * 
	 * @param region
	 * @param key
	 */
	public void remove(String region, String key) {
		ResourceManager.remove(region, key);
	}

}
