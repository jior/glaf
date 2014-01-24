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

import javax.servlet.http.HttpSession;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.*;

public class HessianSerializer implements Serializer {
	private ClassLoader loader;

	public HttpSession deserializeInto(byte[] data, HttpSession session)
			throws IOException {
		ZooKeeperSession zooKeeperSession = (ZooKeeperSession) session;
		BufferedInputStream bis = null;
		Hessian2Input ois = null;
		try {
			bis = new BufferedInputStream(new ByteArrayInputStream(data));
			ois = new Hessian2Input(bis);
			ois.startMessage();
			zooKeeperSession = (ZooKeeperSession) ois.readObject();
			if (zooKeeperSession != null) {
				zooKeeperSession.setCreationTime(ois.readLong());
			}
			ois.completeMessage();
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
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
		return session;
	}

	public byte[] serializeFrom(HttpSession session) throws IOException {
		ZooKeeperSession zooKeeperSession = (ZooKeeperSession) session;
		ByteArrayOutputStream bos = null;
		Hessian2Output oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new Hessian2Output(new BufferedOutputStream(bos));
			oos.startMessage();
			oos.writeObject(zooKeeperSession);
			oos.writeLong(zooKeeperSession.getCreationTime());
			oos.completeMessage();
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException ex) {
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException ex) {
				}
			}
		}
		return bos.toByteArray();
	}

	public ClassLoader getLoader() {
		return loader;
	}

	public void setLoader(ClassLoader loader) {
		this.loader = loader;
	}

	public void setClassLoader(ClassLoader loader) {
		this.loader = loader;
	}
}
