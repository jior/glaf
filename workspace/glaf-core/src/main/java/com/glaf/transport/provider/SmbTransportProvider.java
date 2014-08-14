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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;

import com.glaf.transport.domain.FileTransport;
import com.glaf.transport.util.FileTransportUtils;

public class SmbTransportProvider implements TransportProvider {

	public byte[] getBytes(FileTransport fileTransport, String filename) {
		String remoteUrl = FileTransportUtils
				.getConnectionString(fileTransport);
		if (!filename.startsWith("/")) {
			filename = "/" + filename;
		}
		InputStream inputStream = null;
		try {
			SmbFile smbFile = new SmbFile(remoteUrl + "/" + filename);
			if (!smbFile.exists()) {
				return null;
			}
			inputStream = new BufferedInputStream(new SmbFileInputStream(
					smbFile));
			byte[] bytes = FileUtils.getBytes(inputStream);
			return bytes;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(inputStream);
		}
	}

	public void saveFile(FileTransport fileTransport, String filename,
			byte[] bytes) {
		String remoteUrl = FileTransportUtils
				.getConnectionString(fileTransport);
		if (!filename.startsWith("/")) {
			filename = "/" + filename;
		}
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		OutputStream out = null;
		try {
			SmbFile smbFile = new SmbFile(remoteUrl);
			if (!smbFile.exists()) {
				smbFile.mkdirs();
			}
			smbFile = new SmbFile(remoteUrl + "/" + filename);

			bais = new ByteArrayInputStream(bytes);
			bis = new BufferedInputStream(bais);
			out = new SmbFileOutputStream(smbFile);

			int length = bytes.length;
			if (length > 1024) {
				length = 1024;
			}

			IOUtils.copyBytes(bis, out, length);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(bais);
			IOUtils.closeStream(bis);
			IOUtils.closeStream(out);
		}
	}

}
