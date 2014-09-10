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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.web.resource.WebResource;
import com.glaf.core.xml.MimeMappingReader;

public class WebResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected static final Log logger = LogFactory
			.getLog(WebResourceServlet.class);

	protected static ConcurrentMap<String, String> mimeMapping = new ConcurrentHashMap<String, String>();

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
		if (StringUtils.equalsIgnoreCase(ext, "jpg")
				|| StringUtils.equalsIgnoreCase(ext, "jpeg")
				|| StringUtils.equalsIgnoreCase(ext, "gif")
				|| StringUtils.equalsIgnoreCase(ext, "png")
				|| StringUtils.equalsIgnoreCase(ext, "bmp")) {
			contentType = "image/" + ext;
		} else if (StringUtils.equalsIgnoreCase(ext, "svg")) {
			contentType = "image/svg+xml";
		} else if (StringUtils.equalsIgnoreCase(ext, "css")) {
			contentType = "text/css";
		} else if (StringUtils.equalsIgnoreCase(ext, "txt")) {
			contentType = "text/plain";
		} else if (StringUtils.equalsIgnoreCase(ext, "htm")
				|| StringUtils.equalsIgnoreCase(ext, "html")) {
			contentType = "text/html";
		} else if (StringUtils.equalsIgnoreCase(ext, "js")) {
			contentType = "application/javascript";
		} else if (StringUtils.equalsIgnoreCase(ext, "ttf")) {
			contentType = "application/x-font-ttf";
		} else if (StringUtils.equalsIgnoreCase(ext, "eot")) {
			contentType = "application/vnd.ms-fontobject";
		} else if (StringUtils.equalsIgnoreCase(ext, "woff")) {
			contentType = "application/x-font-woff";
		} else if (StringUtils.equalsIgnoreCase(ext, "swf")) {
			contentType = "application/x-shockwave-flash";
		} else {
			contentType = mimeMapping.get(ext.trim().toLowerCase());
		}

		if (StringUtils.startsWith(resPath, "/static")) {
			resPath = resPath.substring(7, resPath.length());
		}

		InputStream inputStream = null;
		ServletOutputStream outraw = null;
		try {
			outraw = response.getOutputStream();
			byte[] raw = WebResource.getData(resPath);
			if (raw == null) {
				String filename = SystemProperties.getAppPath() + resPath;
				File filex = new File(filename);
				if (filex.exists() && filex.isFile()) {
					inputStream = FileUtils.getInputStream(filename);
				}
				if (inputStream == null) {
					inputStream = WebResourceServlet.class
							.getResourceAsStream(resPath);
				}
				raw = FileUtils.getBytes(inputStream);
				WebResource.setBytes(resPath, raw);
				logger.debug("load resource:" + resPath);
			}
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(contentType);
			response.setContentLength(raw.length);
			outraw.write(raw);
		} catch (IOException ex) {
			// ex.printStackTrace();
		} finally {
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(outraw);
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
