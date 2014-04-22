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
package com.glaf.shiro.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.glaf.core.util.SerializerUtils;
import com.glaf.shiro.Cacheable;

public class RedisCacheableImpl implements Cacheable {

	// -1 - never expire
	private int expire = -1;

	private RedisTemplate<String, Object> redisTemplate;

	public RedisCacheableImpl() {

	}

	@Override
	public void clear() {
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				connection.flushDb();
				return null;
			}
		});
	}

	@Override
	public Long deleteHashCache(final byte[] key, final byte[] mapkey) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				Long hDel = connection.hDel(key, mapkey);
				return hDel;
			}
		});
	}

	@Override
	public Object getCache(final byte[] sessionId) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) {
				byte[] bytes = connection.get(sessionId);
				return SerializerUtils.unserialize(bytes);
			}
		});
	}

	public int getExpire() {
		return expire;
	}

	@Override
	public Object getHashCache(final byte[] key, final byte[] mapkey) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) {
				byte[] hGet = connection.hGet(key, mapkey);
				return SerializerUtils.unserialize(hGet);
			}
		});
	}

	@Override
	public Set<Object> getHashKeys(final byte[] key) {
		return (Set<Object>) redisTemplate
				.execute(new RedisCallback<Set<Object>>() {
					public Set<Object> doInRedis(RedisConnection connection) {
						Set<byte[]> hKeys = connection.hKeys(key);
						if (hKeys == null || hKeys.size() > 1) {
							return null;
						}
						Set<Object> result = new HashSet<Object>();
						for (byte[] bytes : hKeys) {
							result.add(SerializerUtils.unserialize(bytes));
						}
						return result;
					}
				});

	}

	@Override
	public Long getHashSize(final byte[] key) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				Long len = connection.hLen(key);
				return len;
			}
		});
	}

	@Override
	public List<Object> getHashValues(final byte[] key) {
		return redisTemplate.execute(new RedisCallback<List<Object>>() {
			public List<Object> doInRedis(RedisConnection connection) {
				List<byte[]> hVals = connection.hVals(key);

				if (hVals == null || hVals.size() < 1) {
					return null;
				}
				List<Object> result = new ArrayList<Object>();

				for (byte[] bs : hVals) {
					result.add(SerializerUtils.unserialize(bs));
				}
				return result;
			}
		});
	}

	@Override
	public Set<Object> getKeys(final byte[] keys) {
		return redisTemplate.execute(new RedisCallback<Set<Object>>() {
			public Set<Object> doInRedis(RedisConnection connection) {
				Set<byte[]> setByte = connection.keys(keys);
				if (setByte == null || setByte.size() < 1) {
					return null;
				}
				Set<Object> result = new HashSet<Object>();
				for (byte[] key : setByte) {
					byte[] bs = connection.get(key);
					result.add(SerializerUtils.unserialize(bs));
				}
				return result;
			}
		});
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	@Override
	public Long getSize() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				Long len = connection.dbSize();
				return len;
			}
		});
	}

	@Override
	public String remove(final byte[] sessionId) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection) {
				connection.del(sessionId);
				return null;
			}
		});
		return null;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public String update(final byte[] key, final byte[] session,
			final Long expireSec) {
		return (String) redisTemplate.execute(new RedisCallback<Object>() {
			public String doInRedis(final RedisConnection connection) {
				connection.set(key, session);
				if (expireSec != null) {
					connection.expire(key, expireSec);
				} else {
					connection.expire(key, expire);
				}
				return new String(key);
			}
		});

	}

	@Override
	public Boolean updateHashCache(final byte[] key, final byte[] mapkey,
			final byte[] value, Long expire) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				Boolean hSet = connection.hSet(key, mapkey, value);
				return hSet;
			}
		});
	}

}
