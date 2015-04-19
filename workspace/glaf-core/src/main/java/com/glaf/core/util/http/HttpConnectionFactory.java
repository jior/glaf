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

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.glaf.core.util.MyX509TrustManager;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.providers.netty.NettyAsyncHttpProvider;
import com.ning.http.client.providers.netty.NettyAsyncHttpProviderConfig;

public class HttpConnectionFactory {

	private static volatile AsyncHttpClient httpClient;

	private static volatile AsyncHttpClient httpsClient;

	public static void close() {
		if (httpClient != null && !httpClient.isClosed()) {
			httpClient.close();
		}
		if (httpsClient != null && !httpsClient.isClosed()) {
			httpsClient.close();
		}
	}

	public static AsyncHttpClient getHttpClient() {
		if (httpClient == null || httpClient.isClosed()) {
			NettyAsyncHttpProviderConfig providerConfig = new NettyAsyncHttpProviderConfig();
			AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
			builder.setMaxRequestRetry(3);
			builder.setConnectTimeout(1000 * 10);
			builder.setCompressionEnforced(false);
			builder.setAllowPoolingSslConnections(false);
			builder.setAllowPoolingConnections(true);
			builder.setFollowRedirect(true);
			builder.setMaxRedirects(3);
			builder.setMaxConnectionsPerHost(500);
			builder.setMaxConnections(5000);
			builder.setAsyncHttpClientProviderConfig(providerConfig);
			AsyncHttpClientConfig config = builder.build();
			httpClient = new AsyncHttpClient(
					new NettyAsyncHttpProvider(config), config);
		}
		return httpClient;
	}

	public static AsyncHttpClient getHttpsClient() {
		if (httpsClient == null || httpsClient.isClosed()) {
			NettyAsyncHttpProviderConfig providerConfig = new NettyAsyncHttpProviderConfig();
			AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
			builder.setMaxRequestRetry(3);
			builder.setConnectTimeout(1000 * 10);
			builder.setCompressionEnforced(false);
			builder.setAllowPoolingSslConnections(false);
			builder.setAllowPoolingConnections(true);
			builder.setFollowRedirect(true);
			builder.setMaxRedirects(3);
			builder.setMaxConnectionsPerHost(500);
			builder.setMaxConnections(5000);
			builder.setAsyncHttpClientProviderConfig(providerConfig);
			AsyncHttpClientConfig config = builder.build();
			try {
				// 创建SSLContext对象，并使用我们指定的信任管理器初始化
				TrustManager[] tm = { new MyX509TrustManager() };
				SSLContext sslContext = SSLContext
						.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				builder.setSSLContext(sslContext);
				builder.setAcceptAnyCertificate(true);
				builder.setAllowPoolingSslConnections(true);
			} catch (Exception ex) {
			}
			httpsClient = new AsyncHttpClient(
					new NettyAsyncHttpProvider(config), config);
		}
		return httpsClient;
	}

	private HttpConnectionFactory() {

	}

}
