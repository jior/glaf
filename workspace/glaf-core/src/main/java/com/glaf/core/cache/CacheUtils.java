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

package com.glaf.core.cache;

public class CacheUtils {

	/**
	 * 清除目录树缓存
	 * 
	 * @param treeId
	 */
	public static void clearTreeCache(String treeId) {
		String cacheKey = CacheConstants.TREE_PREFIX + treeId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.TREE_SUB_PREFIX + treeId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.TREE_ANCESTOR_PREFIX + treeId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.TREE_CHILDREN_PREFIX + treeId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.TREE_ALL_CHILDREN_PREFIX + treeId;
		CacheFactory.remove(cacheKey);
	}

	/**
	 * 清除用户缓存
	 * 
	 * @param actorId
	 */
	public static void clearUserCache(String actorId) {
		String cacheKey = CacheConstants.SECURITYCONTEXT_PREFIX + actorId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.USER_PREFIX + actorId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.USER_PROFILE_PREFIX + actorId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.USER_UNDERLING_PREFIX + actorId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.USER_MENU_PREFIX + actorId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.USER_MENUID_PREFIX + actorId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.USER_LEADER_PREFIX + actorId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.USER_SUPERIOR_PREFIX + actorId;
		CacheFactory.remove(cacheKey);

		cacheKey = CacheConstants.USER_AGENT_PREFIX + actorId;
		CacheFactory.remove(cacheKey);
	}
}
