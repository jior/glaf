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

package com.glaf.cluster.catalina.session;

import org.apache.catalina.util.CustomObjectInputStream;

import javax.servlet.http.HttpSession;

import java.io.*;

public class JavaSerializer implements Serializer {
	private ClassLoader loader;

	public HttpSession deserializeInto(byte[] data, HttpSession session)
			throws IOException {
		ZooKeeperSession zooKeeperSession = (ZooKeeperSession) session;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new BufferedInputStream(new ByteArrayInputStream(data));
			ois = new CustomObjectInputStream(bis, loader);
			zooKeeperSession.setCreationTime(ois.readLong());
			zooKeeperSession.readObjectData(ois);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (ois != null) {
				ois.close();
			}
		}
		return session;
	}

	public byte[] serializeFrom(HttpSession session) throws IOException {
		ZooKeeperSession zooKeeperSession = (ZooKeeperSession) session;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(new BufferedOutputStream(bos));
			oos.writeLong(zooKeeperSession.getCreationTime());
			zooKeeperSession.writeObjectData(oos);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (oos != null) {
				oos.close();
			}
		}
		return bos.toByteArray();
	}

	public void setClassLoader(ClassLoader loader) {
		this.loader = loader;
	}
}
