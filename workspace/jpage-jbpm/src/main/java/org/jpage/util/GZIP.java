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


package org.jpage.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class GZIP {

	public static byte[] getUnGZIPBytes(InputStream inputStream)
			throws IOException {
		GZIPInputStream gzip = null;
		OutputStream bos = null;
		ByteArrayOutputStream baos = null;
		try {
			gzip = new GZIPInputStream(inputStream);
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = gzip.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			gzip.close();
			bos.close();
			baos.close();
			gzip = null;
			bos = null;
			byte[] bytes = baos.toByteArray();
			return bytes;
		} finally {
			try {
				if (gzip != null) {
					gzip.close();
					gzip = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
				if (baos != null) {
					baos.close();
					baos = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static InputStream getUnGZIPStream(InputStream inputStream)
			throws IOException {
		GZIPInputStream gzip = null;
		InputStream bais = null;
		InputStream bis = null;
		OutputStream bos = null;
		ByteArrayOutputStream baos = null;
		try {
			gzip = new GZIPInputStream(inputStream);
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = gzip.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			gzip.close();
			bos.close();
			baos.close();
			gzip = null;
			bos = null;
			bais = new ByteArrayInputStream(baos.toByteArray());
			bis = new BufferedInputStream(bais);
			return bis;
		} finally {
			try {
				if (gzip != null) {
					gzip.close();
					gzip = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
				if (baos != null) {
					baos.close();
					baos = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void writeGZIPStream(InputStream inputStream,
			OutputStream outputStream) throws IOException {
		InputStream bis = null;
		OutputStream gzip = null;
		OutputStream bos = null;
		try {
			bis = new BufferedInputStream(inputStream);
			gzip = new GZIPOutputStream(outputStream);
			bos = new BufferedOutputStream(gzip);
			byte[] buffer = new byte[8 * 1024];
			int readCount;
			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			gzip.close();
			bos.close();
			bis = null;
			gzip = null;
			bos = null;
		} finally {
			try {
				if (bis != null) {
					bis.close();
					bis = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
				if (gzip != null) {
					gzip.close();
					gzip = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static byte[] getGZIPBytes(InputStream inputStream)
			throws IOException {
		InputStream bis = null;
		OutputStream gzip = null;
		OutputStream bos = null;
		ByteArrayOutputStream baos = null;
		byte[] bytes = null;
		try {
			bis = new BufferedInputStream(inputStream);
			baos = new ByteArrayOutputStream();
			gzip = new GZIPOutputStream(baos);
			bos = new BufferedOutputStream(gzip);
			byte[] buffer = new byte[8 * 1024];
			int readCount;
			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			gzip.close();
			bos.close();
			baos.close();
			bytes = baos.toByteArray();
			bis = null;
			gzip = null;
			baos = null;
			bos = null;
			return bytes;
		} finally {
			try {
				if (bis != null) {
					bis.close();
					bis = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
				if (gzip != null) {
					gzip.close();
					gzip = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

}