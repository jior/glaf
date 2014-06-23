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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.MessageProperties;

public class ResponseUtils {
	private static Log logger = LogFactory.getLog(ResponseUtils.class);

	public static void download(HttpServletRequest request,
			HttpServletResponse response, byte[] bytes, String filename)
			throws IOException, ServletException {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			download(request, response, inputStream, filename);
		} catch (Exception ex) {
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	public static void download(HttpServletRequest request,
			HttpServletResponse response, InputStream inputStream,
			String filename) {
		BufferedInputStream bis = null;
		OutputStream outputStream = null;
		String contentDisposition = null;
		String fileOrgName = filename;

		filename = filename.trim();
		String userAgent = request.getHeader("User-Agent");
		logger.debug("filename:" + filename);
		logger.debug("User-Agent:" + userAgent);
		try {
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
				} else if (userAgent.indexOf("Gecko") != -1) {
					filename = new String(fileOrgName.getBytes("GBK"),
							"ISO8859_1");
					contentDisposition = "attachment;filename=\"" + filename
							+ "\"";
				}
			}
			if (contentDisposition == null) {
				contentDisposition = "attachment;filename=\"" + filename + "\"";
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		logger.debug("convert filename:" + filename);
		logger.debug("content disposition:" + contentDisposition);

		response.setHeader("Content-Transfer-Encoding", "base64");
		response.setHeader("Content-Disposition", contentDisposition);
		response.setContentType("application/octet-stream");

		String encoding = request.getHeader("Accept-Encoding");

		if (encoding != null) {
			encoding = encoding.toLowerCase();
		}

		try {
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
			request.setCharacterEncoding("UTF-8");
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

	/**
	 * 返回响应的结果，根据系统默认配置返回
	 * 
	 * @param success
	 * @return
	 */
	public static byte[] responseResult(boolean success) {
		String responseDataType = MessageProperties
				.getString("responseDataType");
		if ("xml".equalsIgnoreCase(responseDataType)) {
			responseXmlResult(success);
		}
		return responseJsonResult(success);
	}

	/**
	 * 返回响应的结果
	 * 
	 * @param responseDataType
	 *            json或xml，默认是json格式
	 * @param success
	 * @return
	 */
	public static byte[] responseResult(String responseDataType, boolean success) {
		if ("xml".equalsIgnoreCase(responseDataType)) {
			responseXmlResult(success);
		}
		return responseJsonResult(success);
	}

	public static byte[] responseJsonResult(boolean success) {
		if (success) {
			Map<String, Object> jsonMap = new java.util.HashMap<String, Object>();
			jsonMap.put("statusCode", 200);
			jsonMap.put("msg", MessageProperties.getString("res_op_ok"));
			jsonMap.put("message", MessageProperties.getString("res_op_ok"));
			JSONObject object = new JSONObject(jsonMap);
			try {
				return object.toString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		} else {
			Map<String, Object> jsonMap = new java.util.HashMap<String, Object>();
			jsonMap.put("statusCode", 500);
			jsonMap.put("msg", MessageProperties.getString("res_op_error"));
			jsonMap.put("message", MessageProperties.getString("res_op_error"));
			JSONObject object = new JSONObject(jsonMap);
			try {
				return object.toString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}

	public static byte[] responseJsonResult(boolean success, String message) {
		if (success) {
			Map<String, Object> jsonMap = new java.util.HashMap<String, Object>();
			jsonMap.put("statusCode", 200);
			jsonMap.put("message", message);
			JSONObject object = new JSONObject(jsonMap);
			try {
				return object.toString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		} else {
			Map<String, Object> jsonMap = new java.util.HashMap<String, Object>();
			jsonMap.put("statusCode", 500);
			jsonMap.put("message", message);
			JSONObject object = new JSONObject(jsonMap);
			try {
				return object.toString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}

	public static byte[] responseXmlResult(boolean success) {
		if (success) {
			StringBuffer buffer = new StringBuffer(500);
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buffer.append("<response>");
			buffer.append("\n    <statusCode>200</statusCode>");
			buffer.append("\n    <message>")
					.append(MessageProperties.getString("res_op_ok"))
					.append("</message>");
			buffer.append("\n</response>");
			try {
				return buffer.toString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		} else {
			StringBuffer buffer = new StringBuffer(500);
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buffer.append("<response>");
			buffer.append("\n    <statusCode>500</statusCode>");
			buffer.append("\n    <message>")
					.append(MessageProperties.getString("res_op_error"))
					.append("</message>");
			buffer.append("\n</response>");
			try {
				return buffer.toString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}

}