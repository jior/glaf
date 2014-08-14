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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.FileUtil;
import org.apache.commons.vfs2.VFS;

import com.glaf.core.util.IOUtils;

import com.glaf.transport.domain.FileTransport;
import com.glaf.transport.util.FileTransportUtils;

public class VFSTransportProvider implements TransportProvider {

	public byte[] getBytes(FileTransport fileTransport, String filename) {
		String remoteUrl = FileTransportUtils
				.getConnectionString(fileTransport);
		StringBuffer buffer = new StringBuffer();
		buffer.append(remoteUrl);
		if (!filename.startsWith("/")) {
			filename = "/" + filename;
		}
		buffer.append(filename);

		InputStream inputStream = null;
		FileObject fo = null;
		try {
			fo = VFS.getManager().resolveFile(buffer.toString());
			if (fo.exists() && fo.getType() == FileType.FILE) {
				byte[] bytes = FileUtil.getContent(fo);
				return bytes;
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(inputStream);
			try {
				if (fo != null) {
					fo.close();
				}
			} catch (FileSystemException e) {
			}
		}
	}

	public void saveFile(FileTransport fileTransport, String filename,
			byte[] bytes) {
		String remoteUrl = FileTransportUtils
				.getConnectionString(fileTransport);
		StringBuffer buffer = new StringBuffer();
		buffer.append(remoteUrl);
		if (!filename.startsWith("/")) {
			filename = "/" + filename;
		}
		buffer.append(filename);

		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		OutputStream out = null;
		BufferedOutputStream bos = null;
		FileObject fo = null;
		try {
			fo = VFS.getManager().resolveFile(remoteUrl);
			if (!fo.exists()) {
				fo.createFolder();
				fo.close();
				fo = null;
			}
			fo = VFS.getManager().resolveFile(buffer.toString());
			if (!fo.exists()) {
				fo.createFile();
			}
			bais = new ByteArrayInputStream(bytes);
			bis = new BufferedInputStream(bais);
			out = fo.getContent().getOutputStream();
			bos = new BufferedOutputStream(out);
			IOUtils.copyBytes(bis, bos, 1024);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(bais);
			IOUtils.closeStream(bis);
			IOUtils.closeStream(out);
			IOUtils.closeStream(bos);
			try {
				if (fo != null) {
					fo.close();
				}
			} catch (FileSystemException e) {
			}
		}
	}
}
