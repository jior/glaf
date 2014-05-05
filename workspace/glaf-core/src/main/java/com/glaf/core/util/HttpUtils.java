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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;

public class HttpUtils {

	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	@SuppressWarnings("resource")
	public static JSONObject executeRequest(String requestUrl,
			String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		JSONObject jsonObject = null;
		AsyncHttpClient client = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
			builder.setSSLContext(sslContext);
			builder.setCompressionEnabled(true);
			builder.setFollowRedirects(true);
			builder.setAllowPoolingConnection(true);
			builder.setAllowSslConnectionPool(true);
			builder.setMaximumConnectionsPerHost(20);
			builder.setMaximumConnectionsTotal(10000);
			builder.setMaxRequestRetry(3);
			builder.setRequestTimeoutInMs(30000);// 30seconds

			client = new AsyncHttpClient(builder.build());
			Future<Response> future = null;
			if ("GET".equalsIgnoreCase(requestMethod)) {
				future = client.prepareGet(requestUrl).execute();
			} else {
				if (StringUtils.isNotEmpty(outputStr)) {
					future = client.preparePost(requestUrl)
							.setBody(outputStr.getBytes("UTF-8")).execute();
				} else {
					future = client.preparePost(requestUrl).execute();
				}
			}

			Response response = (Response) future.get();
			inputStream = response.getResponseBodyAsStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			IOUtils.closeQuietly(bufferedReader);
			IOUtils.closeQuietly(inputStreamReader);
			IOUtils.closeQuietly(inputStream);

			jsonObject = JSON.parseObject(buffer.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("https request error:{}", ex);
		} finally {
			IOUtils.closeQuietly(bufferedReader);
			IOUtils.closeQuietly(inputStreamReader);
			IOUtils.closeQuietly(inputStream);
		}
		return jsonObject;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		HttpsURLConnection httpUrlConn = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			log.debug("requestUrl:" + requestUrl);
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;

			log.debug("response:" + buffer.toString());

			jsonObject = JSON.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
			log.error("Weixin server connection timed out.");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("https request error:{}", ex);
		} finally {
			if (httpUrlConn != null) {
				httpUrlConn.disconnect();
			}
		}
		return jsonObject;
	}

}
