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
package com.glaf.shiro;

import java.util.List;
import java.util.Collection;
import java.util.Set;

@SuppressWarnings("rawtypes")
public interface Cacheable {
	/**
	 * 清空缓存
	 * 
	 */
	void clear();

	/**
	 * 删除 缓存
	 * 
	 * @param key
	 * @param mapkey
	 * @return
	 */
	Long deleteHashCache(byte[] key, byte[] mapkey);

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	Object getCache(byte[] key);

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @param mapkey
	 * @return
	 */
	Object getHashCache(byte[] key, byte[] mapkey);

	/**
	 * 根据 正则表达式key 获取 列表
	 * 
	 * @param keys
	 * @return
	 */
	Set getHashKeys(byte[] key);

	/**
	 * 获取 map的长度
	 * 
	 * @param key
	 * @return
	 */
	Long getHashSize(byte[] key);

	/**
	 * 获取 map中的所有值
	 * 
	 * @param key
	 * @return
	 */
	List getHashValues(byte[] key);

	/**
	 * 根据 正则表达式key 获取 列表
	 * 
	 * @param keys
	 * @return
	 */
	Collection getKeys(byte[] keys);

	/**
	 * 获取 map的长度
	 * 
	 * @return
	 */
	Long getSize();

	/**
	 * 删除 缓存
	 * 
	 * @param key
	 * @return
	 */
	String remove(byte[] key);

	/**
	 * 更新 缓存
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	Object update(byte[] key, byte[] value, Long expire);

	/**
	 * 更新 缓存
	 * 
	 * @param key
	 * @param mapkey
	 * @param value
	 * @param expire
	 * @return
	 */
	Boolean updateHashCache(byte[] key, byte[] mapkey, byte[] value, Long expire);
}
