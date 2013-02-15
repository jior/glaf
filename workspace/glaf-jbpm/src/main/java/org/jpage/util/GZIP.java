/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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