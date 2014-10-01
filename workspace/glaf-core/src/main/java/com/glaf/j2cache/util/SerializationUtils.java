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

package com.glaf.j2cache.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.j2cache.CacheException;
import com.glaf.j2cache.CacheManager;

public class SerializationUtils {

	private final static Logger log = LoggerFactory
			.getLogger(SerializationUtils.class);

	private static Serializer serializer;

	static {
		String ser = CacheManager.getSerializer();
		if (ser == null || "".equals(ser.trim())) {
			serializer = new JavaSerializer();
		} else {
			if (ser.equals("java")) {
				serializer = new JavaSerializer();
			} else if (ser.equals("fst")) {
				serializer = new FSTSerializer();
			} else if (ser.equals("kryo")) {
				serializer = new KryoSerializer();
			} else if (ser.equals("kryo_pool")) {
				serializer = new KryoPoolSerializer();
			} else {
				try {
					serializer = (Serializer) Class.forName(ser).newInstance();
				} catch (Exception e) {
					throw new CacheException(
							"Cannot initialize Serializer named [" + ser + ']',
							e);
				}
			}
		}
		log.info("Using Serializer -> [" + serializer.name() + ":"
				+ serializer.getClass().getName() + ']');
	}

	public static byte[] serialize(Object obj) throws IOException {
		return serializer.serialize(obj);
	}

	public static Object deserialize(byte[] bytes) throws IOException {
		return serializer.deserialize(bytes);
	}

}
