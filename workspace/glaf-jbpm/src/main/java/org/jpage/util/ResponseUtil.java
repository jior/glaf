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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResponseUtil {
	private static Log logger = LogFactory.getLog(ResponseUtil.class);

	public static void responseDownload(HttpServletRequest request,
			HttpServletResponse response, InputStream inputStream,
			String filename) {
		BufferedInputStream bis = null;
		OutputStream outputStream = null;
		String contentDisposition = null;
		String fileOrgName = filename;
		try {
			filename = filename.trim();
			String userAgent = request.getHeader("User-Agent");
			logger.info("filename:" + filename);
			logger.info("User-Agent:" + userAgent);

			if (userAgent != null) {
				if (userAgent.indexOf("MSIE") != -1) {
					filename = new String(filename.getBytes("GBK"), "ISO8859_1");
				} else {
					filename = new String(filename.getBytes("UTF-8"),
							"ISO8859_1");
				}
			} else {
				filename = new String(filename.getBytes("GBK"), "ISO8859_1");
			}

			if (userAgent != null) {
				if (userAgent.indexOf("MSIE 5.5") != -1) {
					contentDisposition = "attachment;filename=\"" + filename
							+ "\"";
				} else if (userAgent.indexOf("MSIE 6.0b") != -1) {
					filename = new String(fileOrgName.getBytes("GBK"),
							"ISO8859_1");
					contentDisposition = "attachment;filename=\"" + filename
							+ "\"";
				}
			}
			if (contentDisposition == null) {
				contentDisposition = "attachment;filename=\"" + filename + "\"";
			}

			logger.info("convert filename:" + filename);
			logger.info("content disposition:" + contentDisposition);
			request.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Transfer-Encoding", "base64");
			response.setHeader("Content-Disposition", contentDisposition);
			response.setContentType("application/octet-stream");

			String encoding = request.getHeader("Accept-Encoding");

			if (encoding != null) {
				encoding = encoding.toLowerCase();
			}
			if (encoding != null && encoding.indexOf("gzip") != -1) {
				response.setHeader("Content-Encoding", "gzip");
				outputStream = new java.util.zip.GZIPOutputStream(
						response.getOutputStream());
			} else if (encoding != null && encoding.indexOf("compress") != -1) {
				response.setHeader("Content-Encoding", "compress");
				outputStream = new java.util.zip.ZipOutputStream(
						response.getOutputStream());
			} else {
				outputStream = response.getOutputStream();
			}
			bis = new BufferedInputStream(inputStream);
			int bytesRead = 0;
			byte[] buffer = new byte[256];
			while ((bytesRead = bis.read(buffer, 0, 256)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			bis.close();
			bis = null;
			inputStream = null;
			outputStream = null;
			response.flushBuffer();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException ex) {
			}
		}
	}

}