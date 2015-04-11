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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.glaf.core.util.IOUtils;

public class HttpClientUtils {

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
		StringBuffer buffer = new StringBuffer();
		HttpGet httpGet = null;
		InputStreamReader is = null;
		BufferedReader reader = null;
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient client = builder.setDefaultCookieStore(cookieStore)
				.build();
		try {
			if (dataMap != null && !dataMap.isEmpty()) {
				StringBuffer sb = new StringBuffer();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					String name = entry.getKey().toString();
					String value = entry.getValue();
					sb.append("&").append(name).append("=").append(value);
				}
				if (StringUtils.contains(url, "?")) {
					url = url + sb.toString();
				} else {
					url = url + "?xxxx=1" + sb.toString();
				}
			}
			httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			is = new InputStreamReader(entity.getContent(), encoding);
			reader = new BufferedReader(is);
			String tmp = reader.readLine();
			while (tmp != null) {
				buffer.append(tmp);
				tmp = reader.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(reader);
			IOUtils.closeStream(is);
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
			try {
				client.close();
			} catch (IOException ex) {
			}
		}
		return buffer.toString();
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
		StringBuffer buffer = new StringBuffer();
		HttpPost post = null;
		InputStreamReader is = null;
		BufferedReader reader = null;
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient client = builder.setDefaultCookieStore(cookieStore)
				.build();
		try {
			post = new HttpPost(url);
			if (dataMap != null && !dataMap.isEmpty()) {
				List<org.apache.http.NameValuePair> nameValues = new ArrayList<org.apache.http.NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					String name = entry.getKey().toString();
					String value = entry.getValue();
					nameValues.add(new BasicNameValuePair(name, value));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						nameValues, encoding);
				post.setEntity(entity);
			}
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			is = new InputStreamReader(entity.getContent(), encoding);
			reader = new BufferedReader(is);
			String tmp = reader.readLine();
			while (tmp != null) {
				buffer.append(tmp);
				tmp = reader.readLine();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(reader);
			IOUtils.closeStream(is);
			if (post != null) {
				post.releaseConnection();
			}
			try {
				client.close();
			} catch (IOException ex) {
			}
		}
		return buffer.toString();
	}

	public static String doPost(String url, String data, String contentType,
			String encoding) {
		StringBuffer buffer = new StringBuffer();
		InputStreamReader is = null;
		BufferedReader reader = null;
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient client = builder.setDefaultCookieStore(cookieStore)
				.build();
		try {
			HttpPost post = new HttpPost(url);
			if (data != null) {
				StringEntity entity = new StringEntity(data, encoding);
				post.setHeader("Content-Type", contentType);
				post.setEntity(entity);
			}
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			is = new InputStreamReader(entity.getContent(), encoding);
			reader = new BufferedReader(is);
			String tmp = reader.readLine();
			while (tmp != null) {
				buffer.append(tmp);
				tmp = reader.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(reader);
			IOUtils.closeStream(is);
			try {
				client.close();
			} catch (IOException ex) {
			}
		}
		return buffer.toString();
	}

	public static String postJson(String url, String json) {
		return doPost(url, json, "text/json", "UTF-8");
	}

	public static String postXml(String url, String xml) {
		return doPost(url, xml, "text/xml", "UTF-8");
	}

	private HttpClientUtils() {

	}

}
