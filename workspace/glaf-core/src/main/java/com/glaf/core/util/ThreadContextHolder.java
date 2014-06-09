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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThreadContextHolder {

	private static ThreadLocal<ServletContext> servletContextThreadLocalHolder = new ThreadLocal<ServletContext>();
	private static ThreadLocal<HttpServletRequest> HttpRequestThreadLocalHolder = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> HttpResponseThreadLocalHolder = new ThreadLocal<HttpServletResponse>();
	private static ThreadLocal<Map<String, Object>> dataMapHolder = new ThreadLocal<Map<String, Object>>();

	public static void addData(String key, Object value) {
		Map<String, Object> dataMap = dataMapHolder.get();
		if (dataMap == null) {
			dataMap = new HashMap<String, Object>();
			dataMapHolder.set(dataMap);
		}
		dataMap.put(key, value);
	}

	public static void clear() {
		dataMapHolder.remove();
		HttpRequestThreadLocalHolder.remove();
		HttpResponseThreadLocalHolder.remove();
		servletContextThreadLocalHolder.remove();
	}

	public static Map<String, Object> getDataMap() {
		return dataMapHolder.get();
	}

	public static HttpServletRequest getHttpRequest() {
		return HttpRequestThreadLocalHolder.get();
	}

	public static HttpServletResponse getHttpResponse() {
		return HttpResponseThreadLocalHolder.get();
	}

	public static HttpSession getHttpSession() {
		return getHttpRequest().getSession();
	}

	public static ServletContext getServletContext() {
		return servletContextThreadLocalHolder.get();
	}

	public static void setHttpRequest(HttpServletRequest request) {
		HttpRequestThreadLocalHolder.set(request);
	}

	public static void setHttpResponse(HttpServletResponse response) {
		HttpResponseThreadLocalHolder.set(response);
	}

	public static void setServletContext(ServletContext servletContext) {
		servletContextThreadLocalHolder.set(servletContext);
	}

	private ThreadContextHolder() {

	}
}
