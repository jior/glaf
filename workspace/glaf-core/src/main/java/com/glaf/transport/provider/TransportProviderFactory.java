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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.transport.domain.FileTransport;
import com.glaf.transport.service.FileTransportService;

public class TransportProviderFactory {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static class TransportProviderFactoryHolder {
		public static TransportProviderFactory instance = new TransportProviderFactory();
	}

	public static TransportProviderFactory getInstance() {
		return TransportProviderFactoryHolder.instance;
	}

	private TransportProviderFactory() {

	}

	/**
	 * 获取字节流
	 * 
	 * @param fileTransport
	 * @param filename
	 * @return
	 */
	public byte[] getBytes(FileTransport fileTransport, String filename) {
		TransportProvider provider = null;
		String type = fileTransport.getType();
		if (StringUtils.equals(type, "ftp")) {
			provider = new FtpTransportProvider();
		} else if (StringUtils.equals(type, "smb")) {
			provider = new SmbTransportProvider();
		} else {
			provider = new VFSTransportProvider();
		}
		return provider.getBytes(fileTransport, filename);
	}

	/**
	 * 保存文件流
	 * 
	 * @param fileTransport
	 * @param filename
	 * @param bytes
	 */
	public void saveFile(FileTransport fileTransport, String filename,
			byte[] bytes) {
		TransportProvider provider = null;
		String type = fileTransport.getType();
		if (StringUtils.equals(type, "ftp")) {
			provider = new FtpTransportProvider();
		} else if (StringUtils.equals(type, "smb")) {
			provider = new SmbTransportProvider();
		} else {
			provider = new VFSTransportProvider();
		}
		provider.saveFile(fileTransport, filename, bytes);
		logger.debug(provider.getClass().getName()+" save ok");
	}

	public static void main(String[] args) {
		FileTransportService fileTransportService = ContextFactory
				.getBean("fileTransportService");
		FileTransport t = fileTransportService.getFileTransport(1L);
		System.out.println("key=" + t.getKey());
		TransportProviderFactory p = TransportProviderFactory.getInstance();
		p.saveFile(t, "test2.txt", "24680xyzABC9898zasdfs".getBytes());
		System.out.println(new String(p.getBytes(t, "test2.txt")));
	}

}
