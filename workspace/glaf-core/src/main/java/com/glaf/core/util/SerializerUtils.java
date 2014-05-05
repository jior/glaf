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

package com.glaf.core.util;

import java.io.*;

public class SerializerUtils {

	/**
	 * 将对象序列化为字节流
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(bos);
			oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.flush();
			byte[] result = baos.toByteArray();
			return result;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception ex) {
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception ex) {
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * 将字节流反序列化为对象
	 * 
	 * @param data
	 * @return
	 */
	public static Object unserialize(byte[] data) {
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(data);
			bis = new BufferedInputStream(bais);
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException ex) {
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException ex) {
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException ex) {
				}
			}
		}
	}

}
