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

/**
 * 对象序列化接口
 */
public interface Serializer {

	/**
	 * 序列化接口名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 将对象序列化为字节流
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	byte[] serialize(Object object) throws IOException;

	/**
	 * 将字节流反序列化为对象
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	Object deserialize(byte[] bytes) throws IOException;

}
