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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * A collection of utility methods for working on GZIPed data.
 */
public class GZIPUtils {

	private static final int EXPECTED_COMPRESSION_RATIO = 5;
	private static final int BUF_SIZE = 65536;

	/**
	 * Returns an gunzipped copy of the input array.
	 * 
	 * @throws IOException
	 *             if the input cannot be properly decompressed
	 */
	public static final byte[] unzip(byte[] bytes) {
		// decompress using GZIPInputStream
		ByteArrayOutputStream outStream = null;
		GZIPInputStream zis = null;
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		try {
			outStream = new ByteArrayOutputStream(EXPECTED_COMPRESSION_RATIO
					* bytes.length);
			bais = new ByteArrayInputStream(bytes);
			bis = new BufferedInputStream(bais);
			zis = new GZIPInputStream(bis);

			byte[] buff = new byte[BUF_SIZE];
			while (true) {
				int size = zis.read(buff);
				if (size <= 0) {
					break;
				}
				outStream.write(buff, 0, size);
			}
			outStream.close();
			return outStream.toByteArray();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(bais);
			IOUtils.closeStream(bis);
			IOUtils.closeStream(zis);
			IOUtils.closeStream(outStream);
		}
	}

	/**
	 * Returns an gunzipped copy of the input array. If the gzipped input has
	 * been truncated or corrupted, a best-effort attempt is made to unzip as
	 * much as possible. If no data can be extracted <code>null</code> is
	 * returned.
	 */
	public static final byte[] unzipBestEffort(byte[] in) {
		return unzipBestEffort(in, Integer.MAX_VALUE);
	}

	/**
	 * Returns an gunzipped copy of the input array, truncated to
	 * <code>sizeLimit</code> bytes, if necessary. If the gzipped input has been
	 * truncated or corrupted, a best-effort attempt is made to unzip as much as
	 * possible. If no data can be extracted <code>null</code> is returned.
	 */
	public static final byte[] unzipBestEffort(byte[] in, int sizeLimit) {
		try {
			// decompress using GZIPInputStream
			ByteArrayOutputStream outStream = new ByteArrayOutputStream(
					EXPECTED_COMPRESSION_RATIO * in.length);

			GZIPInputStream inStream = new GZIPInputStream(
					new ByteArrayInputStream(in));

			byte[] buf = new byte[BUF_SIZE];
			int written = 0;
			while (true) {
				try {
					int size = inStream.read(buf);
					if (size <= 0)
						break;
					if ((written + size) > sizeLimit) {
						outStream.write(buf, 0, sizeLimit - written);
						break;
					}
					outStream.write(buf, 0, size);
					written += size;
				} catch (Exception e) {
					break;
				}
			}
			try {
				outStream.close();
			} catch (IOException e) {
			}

			return outStream.toByteArray();

		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Returns an gzipped copy of the input array.
	 */
	public static final byte[] zip(byte[] bytes) {
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		GZIPOutputStream outStream = null;
		try {
			baos = new ByteArrayOutputStream(bytes.length
					/ EXPECTED_COMPRESSION_RATIO);
			bos = new BufferedOutputStream(baos);
			outStream = new GZIPOutputStream(bos);
			outStream.write(bytes);
			outStream.close();
			return baos.toByteArray();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(bos);
			IOUtils.closeStream(baos);
			IOUtils.closeStream(outStream);
		}
	}

}