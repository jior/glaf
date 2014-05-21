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

package com.glaf.j2cache;

/**
 * 所获取的缓存对象
 * 
 * @author oschina.net
 */
public class CacheObject {

	private String region;
	private Object key;
	private Object value;
	private byte level;
	private long createTimeMs;

	public long getCreateTimeMs() {
		return createTimeMs;
	}

	public Object getKey() {
		return key;
	}

	public byte getLevel() {
		return level;
	}

	public String getRegion() {
		return region;
	}

	public Object getValue() {
		return value;
	}

	public void setCreateTimeMs(long createTimeMs) {
		this.createTimeMs = createTimeMs;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
