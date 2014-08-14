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

package com.glaf.transport.util;

import org.apache.commons.lang3.StringUtils;

import com.glaf.core.security.SecurityUtils;
import com.glaf.core.util.UUID32;
import com.glaf.transport.domain.FileTransport;

public class FileTransportUtils {

	private FileTransportUtils() {

	}

	public static String genKey() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 31; i++) {
			buffer.append(UUID32.getUUID());
		}
		return buffer.toString();
	}

	public static String getConnectionString(FileTransport fileTransport) {
		if (StringUtils.equalsIgnoreCase(fileTransport.getType(), "http")) {
			// http://admin:admin@127.0.0.1:21/aaa
			StringBuffer buffer = new StringBuffer();
			buffer.append("http://");
			if (StringUtils.isNotEmpty(fileTransport.getUser())
					&& StringUtils.isNotEmpty(fileTransport.getPassword())) {
				String pass = SecurityUtils.decode(fileTransport.getKey(),
						fileTransport.getPassword());
				buffer.append(fileTransport.getUser()).append(":").append(pass);
				buffer.append("@");
			}
			buffer.append(fileTransport.getHost()).append(":")
					.append(fileTransport.getPort()).append("/");
			if (StringUtils.isNotEmpty(fileTransport.getPath())) {
				buffer.append(fileTransport.getPath());
			}
			return buffer.toString();
		} else if (StringUtils.equalsIgnoreCase(fileTransport.getType(),
				"https")) {
			// https://admin:admin@127.0.0.1:21/aaa
			StringBuffer buffer = new StringBuffer();
			buffer.append("https://");
			if (StringUtils.isNotEmpty(fileTransport.getUser())
					&& StringUtils.isNotEmpty(fileTransport.getPassword())) {
				String pass = SecurityUtils.decode(fileTransport.getKey(),
						fileTransport.getPassword());
				buffer.append(fileTransport.getUser()).append(":").append(pass);
				buffer.append("@");
			}
			buffer.append(fileTransport.getHost()).append(":")
					.append(fileTransport.getPort()).append("/");
			if (StringUtils.isNotEmpty(fileTransport.getPath())) {
				buffer.append(fileTransport.getPath());
			}
			return buffer.toString();
		} else if (StringUtils.equalsIgnoreCase(fileTransport.getType(), "ftp")) {
			// ftp://admin:admin@127.0.0.1:21/aaa
			StringBuffer buffer = new StringBuffer();
			buffer.append("ftp://");
			if (StringUtils.isNotEmpty(fileTransport.getUser())
					&& StringUtils.isNotEmpty(fileTransport.getPassword())) {
				String pass = SecurityUtils.decode(fileTransport.getKey(),
						fileTransport.getPassword());
				buffer.append(fileTransport.getUser()).append(":").append(pass);
				buffer.append("@");
			}
			buffer.append(fileTransport.getHost()).append(":")
					.append(fileTransport.getPort()).append("/");
			if (StringUtils.isNotEmpty(fileTransport.getPath())) {
				buffer.append(fileTransport.getPath());
			}
			return buffer.toString();
		} else if (StringUtils
				.equalsIgnoreCase(fileTransport.getType(), "ftps")) {
			// ftps://admin:admin@127.0.0.1:21/aaa
			StringBuffer buffer = new StringBuffer();
			buffer.append("ftps://");
			if (StringUtils.isNotEmpty(fileTransport.getUser())
					&& StringUtils.isNotEmpty(fileTransport.getPassword())) {
				String pass = SecurityUtils.decode(fileTransport.getKey(),
						fileTransport.getPassword());
				buffer.append(fileTransport.getUser()).append(":").append(pass);
				buffer.append("@");
			}
			buffer.append(fileTransport.getHost()).append(":")
					.append(fileTransport.getPort()).append("/");
			if (StringUtils.isNotEmpty(fileTransport.getPath())) {
				buffer.append(fileTransport.getPath());
			}
			return buffer.toString();
		} else if (StringUtils
				.equalsIgnoreCase(fileTransport.getType(), "sftp")) {
			// sftp://admin:admin@127.0.0.1:21/aaa
			StringBuffer buffer = new StringBuffer();
			buffer.append("sftp://");
			if (StringUtils.isNotEmpty(fileTransport.getUser())
					&& StringUtils.isNotEmpty(fileTransport.getPassword())) {
				String pass = SecurityUtils.decode(fileTransport.getKey(),
						fileTransport.getPassword());
				buffer.append(fileTransport.getUser()).append(":").append(pass);
				buffer.append("@");
			}
			buffer.append(fileTransport.getHost()).append(":")
					.append(fileTransport.getPort()).append("/");
			if (StringUtils.isNotEmpty(fileTransport.getPath())) {
				buffer.append(fileTransport.getPath());
			}
			return buffer.toString();
		} else if (StringUtils.equalsIgnoreCase(fileTransport.getType(), "smb")) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("smb://");
			if (StringUtils.isNotEmpty(fileTransport.getUser())
					&& StringUtils.isNotEmpty(fileTransport.getPassword())) {
				String pass = SecurityUtils.decode(fileTransport.getKey(),
						fileTransport.getPassword());
				buffer.append(fileTransport.getUser()).append(":").append(pass);
				buffer.append("@");
			}
			buffer.append(fileTransport.getHost());
			if (StringUtils.isNotEmpty(fileTransport.getPath())) {
				if (!fileTransport.getPath().startsWith("/")) {
					buffer.append("/");
				}
				buffer.append(fileTransport.getPath());
			}
			return buffer.toString();
		}
		return "";
	}

}
