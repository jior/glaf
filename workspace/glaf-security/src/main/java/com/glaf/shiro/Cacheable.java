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
	 * ��ջ���
	 * 
	 */
	void clear();

	/**
	 * ɾ�� ����
	 * 
	 * @param key
	 * @param mapkey
	 * @return
	 */
	Long deleteHashCache(byte[] key, byte[] mapkey);

	/**
	 * ��ȡ����
	 * 
	 * @param key
	 * @return
	 */
	Object getCache(byte[] key);

	/**
	 * ��ȡ����
	 * 
	 * @param key
	 * @param mapkey
	 * @return
	 */
	Object getHashCache(byte[] key, byte[] mapkey);

	/**
	 * ���� ������ʽkey ��ȡ �б�
	 * 
	 * @param keys
	 * @return
	 */
	Set getHashKeys(byte[] key);

	/**
	 * ��ȡ map�ĳ���
	 * 
	 * @param key
	 * @return
	 */
	Long getHashSize(byte[] key);

	/**
	 * ��ȡ map�е�����ֵ
	 * 
	 * @param key
	 * @return
	 */
	List getHashValues(byte[] key);

	/**
	 * ���� ������ʽkey ��ȡ �б�
	 * 
	 * @param keys
	 * @return
	 */
	Collection getKeys(byte[] keys);

	/**
	 * ��ȡ map�ĳ���
	 * 
	 * @return
	 */
	Long getSize();

	/**
	 * ɾ�� ����
	 * 
	 * @param key
	 * @return
	 */
	String remove(byte[] key);

	/**
	 * ���� ����
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	Object update(byte[] key, byte[] value, Long expire);

	/**
	 * ���� ����
	 * 
	 * @param key
	 * @param mapkey
	 * @param value
	 * @param expire
	 * @return
	 */
	Boolean updateHashCache(byte[] key, byte[] mapkey, byte[] value, Long expire);
}
