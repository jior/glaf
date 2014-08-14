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
package com.glaf.transport.provider;

import org.apache.commons.lang.StringUtils;

import com.glaf.core.security.SecurityUtils;
import com.glaf.core.util.FtpUtils;
import com.glaf.transport.domain.FileTransport;

public class FtpTransportProvider implements TransportProvider {

	public byte[] getBytes(FileTransport fileTransport, String filename) {
		String pass = SecurityUtils.decode(fileTransport.getKey(),
				fileTransport.getPassword());
		try {
			FtpUtils.connectServer(fileTransport.getHost(),
					fileTransport.getPort(), fileTransport.getUser(), pass);
			String remoteFile = "";
			if (StringUtils.isNotEmpty(fileTransport.getPath())) {
				remoteFile = fileTransport.getPath();
			}
			if (!remoteFile.startsWith("/")) {
				remoteFile = "/" + remoteFile;
			}
			if (!remoteFile.endsWith("/")) {
				remoteFile = remoteFile + "/";
			}
			if (!filename.startsWith("/")) {
				filename = "/" + filename;
			}
			remoteFile = remoteFile + filename;
			byte[] bytes = FtpUtils.getBytes(remoteFile);
			return bytes;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			FtpUtils.closeConnect();
		}
	}

	public void saveFile(FileTransport fileTransport, String filename,
			byte[] bytes) {
		String pass = SecurityUtils.decode(fileTransport.getKey(),
				fileTransport.getPassword());
		try {
			FtpUtils.connectServer(fileTransport.getHost(),
					fileTransport.getPort(), fileTransport.getUser(), pass);
			String remoteFile = "";
			if (StringUtils.isNotEmpty(fileTransport.getPath())) {
				remoteFile = fileTransport.getPath();
			}
			if (!remoteFile.startsWith("/")) {
				remoteFile = "/" + remoteFile;
			}
			if (!remoteFile.endsWith("/")) {
				remoteFile = remoteFile + "/";
			}
			if (!filename.startsWith("/")) {
				filename = "/" + filename;
			}
			remoteFile = remoteFile + filename;
			FtpUtils.upload(remoteFile, bytes);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			FtpUtils.closeConnect();
		}
	}

}
