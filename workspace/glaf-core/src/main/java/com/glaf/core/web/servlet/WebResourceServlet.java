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
package com.glaf.core.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.web.resource.WebResource;
import com.glaf.core.xml.MimeMappingReader;

public class WebResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected static final Log logger = LogFactory
			.getLog(WebResourceServlet.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static ConcurrentMap<String, String> mimeMapping = new ConcurrentHashMap<String, String>();

	protected static boolean debugMode = false;

	protected boolean isGZIPSupported(HttpServletRequest req) {
		String browserEncodings = req.getHeader("accept-encoding");
		boolean supported = ((browserEncodings != null) && (browserEncodings
				.indexOf("gzip") != -1));
		String userAgent = req.getHeader("user-agent");
		if ((userAgent != null) && userAgent.startsWith("httpunit")) {
			logger.debug("httpunit detected, disabling filter...");
			return false;
		} else {
			return supported;
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		String resPath = requestURI.substring(contextPath.length(),
				requestURI.length());

		// logger.debug("contextPath:" + contextPath);
		// logger.debug("requestURI:" + requestURI);
		// logger.debug("resPath:" + resPath);

		int slash = request.getRequestURI().lastIndexOf("/");
		String file = request.getRequestURI().substring(slash + 1);
		if (StringUtils.endsWithIgnoreCase(file, ".class")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".jsp")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".conf")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, "-config.properties")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, "-spring-context.xml")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, "Mapper.xml")) {
			return;
		}

		int dot = file.lastIndexOf(".");
		String ext = file.substring(dot + 1);
		String contentType = "";
		boolean requiredZip = false;
		if (StringUtils.equalsIgnoreCase(ext, "jpg")
				|| StringUtils.equalsIgnoreCase(ext, "jpeg")
				|| StringUtils.equalsIgnoreCase(ext, "gif")
				|| StringUtils.equalsIgnoreCase(ext, "png")) {
			contentType = "image/" + ext;
			requiredZip = false;
		} else if (StringUtils.equalsIgnoreCase(ext, "bmp")) {
			contentType = "image/bmp";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "svg")) {
			contentType = "image/svg+xml";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "css")) {
			contentType = "text/css";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "txt")) {
			contentType = "text/plain";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "htm")
				|| StringUtils.equalsIgnoreCase(ext, "html")) {
			contentType = "text/html";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "js")) {
			contentType = "application/javascript";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "ttf")) {
			contentType = "application/x-font-ttf";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "eot")) {
			contentType = "application/vnd.ms-fontobject";
		} else if (StringUtils.equalsIgnoreCase(ext, "woff")) {
			contentType = "application/x-font-woff";
		} else if (StringUtils.equalsIgnoreCase(ext, "swf")) {
			contentType = "application/x-shockwave-flash";
		} else {
			contentType = mimeMapping.get(ext.trim().toLowerCase());
		}

		if (requiredZip && isGZIPSupported(request)
				&& conf.getBoolean("gzipEnabled", true)) {
			requiredZip = true;
		} else {
			requiredZip = false;
		}

		InputStream inputStream = null;
		ServletOutputStream output = null;
		boolean zipFlag = false;
		byte[] raw = null;
		try {
			output = response.getOutputStream();
			if (!debugMode) {
				raw = WebResource.getData(resPath);
				if (requiredZip) {
					raw = WebResource.getData(resPath + ".gz");
					if (raw != null) {
						zipFlag = true;
					}
				}
			}
			if (raw == null) {
				zipFlag = false;
				String filename = SystemProperties.getAppPath() + resPath;
				File filex = new File(filename);
				if (filex.exists() && filex.isFile()) {
					inputStream = FileUtils.getInputStream(filename);
				}
				if (inputStream == null) {
					inputStream = WebResourceServlet.class
							.getResourceAsStream(resPath);
				}

				logger.debug("load resource:" + resPath);
				raw = FileUtils.getBytes(inputStream);
				if (!debugMode) {
					WebResource.setBytes(resPath, raw);
				}

				GZIPOutputStream gzipStream = null;
				ByteArrayOutputStream compressedContent = null;
				try {
					// prepare a gzip stream
					compressedContent = new ByteArrayOutputStream();
					gzipStream = new GZIPOutputStream(compressedContent);
					gzipStream.write(raw);
					gzipStream.finish();
					// get the compressed content
					byte[] compressedBytes = compressedContent.toByteArray();
					WebResource.setBytes(resPath + ".gz", compressedBytes);
					logger.debug(resPath + " raw size:[" + raw.length
							+ "] gzip compressed size:["
							+ compressedBytes.length + "]");
					if (requiredZip) {
						raw = compressedBytes;
						zipFlag = true;
					}
				} catch (Exception ex) {
					zipFlag = false;
				} finally {
					IOUtils.closeStream(compressedContent);
					IOUtils.closeStream(gzipStream);
				}
			}

			if (zipFlag) {
				response.addHeader("Content-Encoding", "gzip");
			}

			if (raw != null) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType(contentType);
				response.setContentLength(raw.length);
				output.write(raw);
				output.flush();
				IOUtils.closeStream(output);
			}
		} catch (IOException ex) {
			// ex.printStackTrace();
		} finally {
			raw = null;
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(output);
		}

		response.flushBuffer();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void init(ServletConfig config) {
		logger.info("--------------WebResourceServlet init----------------");
		try {
			if (System.getProperty("debugMode") != null) {
				debugMode = true;
				logger.info("---------------WebResource开启调试模式---------------");
			}
			MimeMappingReader reader = new MimeMappingReader();
			Map<String, String> mapping = reader.read();
			Set<Entry<String, String>> entrySet = mapping.entrySet();
			for (Entry<String, String> entry : entrySet) {
				String key = entry.getKey();
				String value = entry.getValue();
				mimeMapping.put(key, value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
