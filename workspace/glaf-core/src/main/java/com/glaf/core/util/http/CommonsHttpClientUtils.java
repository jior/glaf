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

package com.glaf.core.util.http;

import java.io.IOException;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class CommonsHttpClientUtils {

	public static String doGet(String url) {
		return doGet(url, "UTF-8", null);
	}

	public static String doGet(String url, Map<String, String> dataMap) {
		return doGet(url, "UTF-8", dataMap);
	}

	/**
	 * 提交GET请求
	 * 
	 * @param url
	 *            服务地址
	 * @param encoding
	 *            字符集
	 * @param dataMap
	 *            请求参数
	 * 
	 * @return
	 */
	public static String doGet(String url, String encoding,
			Map<String, String> dataMap) {
		GetMethod method = null;
		String content = null;
		try {
			method = new GetMethod(url);
			method.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
			method.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=" + encoding);
			if (dataMap != null && !dataMap.isEmpty()) {
				NameValuePair[] nameValues = new NameValuePair[dataMap.size()];
				int i = 0;
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					String name = entry.getKey().toString();
					String value = entry.getValue();
					nameValues[i] = new NameValuePair(name, value);
					i++;
				}
				method.setQueryString(nameValues);
			}
			HttpClient client = new HttpClient();
			int status = client.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				content = method.getResponseBodyAsString();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (method != null) {
				method.releaseConnection();
				method = null;
			}
		}
		return content;
	}

	public static String doPost(String url) {
		return doPost(url, "UTF-8", null);
	}

	public static String doPost(String url, Map<String, String> dataMap) {
		return doPost(url, "UTF-8", dataMap);
	}

	/**
	 * 提交POST请求
	 * 
	 * @param url
	 *            服务地址
	 * @param encoding
	 *            字符集
	 * @param dataMap
	 *            请求参数
	 * 
	 * @return
	 */
	public static String doPost(String url, String encoding,
			Map<String, String> dataMap) {
		PostMethod method = null;
		String content = null;
		try {
			method = new PostMethod(url);
			method.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
			method.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=" + encoding);
			if (dataMap != null && !dataMap.isEmpty()) {
				NameValuePair[] nameValues = new NameValuePair[dataMap.size()];
				int i = 0;
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					String name = entry.getKey().toString();
					String value = entry.getValue();
					nameValues[i] = new NameValuePair(name, value);
					i++;
				}
				method.setRequestBody(nameValues);
			}
			HttpClient client = new HttpClient();
			int status = client.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				content = method.getResponseBodyAsString();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (method != null) {
				method.releaseConnection();
				method = null;
			}
		}
		return content;
	}

	private CommonsHttpClientUtils() {

	}

}
