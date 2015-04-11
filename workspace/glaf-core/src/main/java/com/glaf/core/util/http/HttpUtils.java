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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

import com.glaf.core.util.FileUtils;
import com.glaf.core.util.MyX509TrustManager;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

public class HttpUtils {

	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * Regex which matches any of the sequences that we need to fix up after
	 * URLEncoder.encode().
	 */
	private static final Pattern ENCODED_CHARACTERS_PATTERN;
	static {
		StringBuilder pattern = new StringBuilder();

		pattern.append(Pattern.quote("+")).append("|")
				.append(Pattern.quote("*")).append("|")
				.append(Pattern.quote("%7E")).append("|")
				.append(Pattern.quote("%2F"));

		ENCODED_CHARACTERS_PATTERN = Pattern.compile(pattern.toString());
	}

	/**
	 * Append the given path to the given baseUri. By default, all slash
	 * characters in path will not be url-encoded.
	 */
	public static String appendUri(String baseUri, String path) {
		return appendUri(baseUri, path, false);
	}

	/**
	 * Append the given path to the given baseUri.
	 *
	 * <p>
	 * This method will encode the given path but not the given baseUri.
	 * </p>
	 *
	 * @param baseUri
	 *            The URI to append to (required, may be relative)
	 * @param path
	 *            The path to append (may be null or empty)
	 * @param escapeDoubleSlash
	 *            Whether double-slash in the path should be escaped to "/%2F"
	 * @return The baseUri with the (encoded) path appended
	 */
	public static String appendUri(final String baseUri, String path,
			final boolean escapeDoubleSlash) {
		String resultUri = baseUri;
		if (path != null && path.length() > 0) {
			if (path.startsWith("/")) {
				// trim the trailing slash in baseUri, since the path already
				// starts with a slash
				if (resultUri.endsWith("/")) {
					resultUri = resultUri.substring(0, resultUri.length() - 1);
				}
			} else if (!resultUri.endsWith("/")) {
				resultUri += "/";
			}
			String encodedPath = HttpUtils.urlEncode(path, true);
			if (escapeDoubleSlash) {
				encodedPath = encodedPath.replace("//", "/%2F");
			}
			resultUri += encodedPath;
		} else if (!resultUri.endsWith("/")) {
			resultUri += "/";
		}

		return resultUri;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param method
	 *            请求方式（GET、POST）
	 * @param content
	 *            提交的数据
	 * @return
	 */
	public static String doRequest(String requestUrl, String method,
			String content, boolean isSSL) {
		log.debug("requestUrl:" + requestUrl);
		HttpsURLConnection conn = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			conn = (HttpsURLConnection) url.openConnection();
			if (isSSL) {
				// 创建SSLContext对象，并使用我们指定的信任管理器初始化
				TrustManager[] tm = { new MyX509TrustManager() };
				SSLContext sslContext = SSLContext
						.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				conn.setSSLSocketFactory(ssf);
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(method);
			if ("GET".equalsIgnoreCase(method)) {
				conn.connect();
			}

			// 当有数据需要提交时
			if (StringUtils.isNotEmpty(content)) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(content.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			inputStream = conn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			log.debug("response:" + buffer.toString());

		} catch (ConnectException ce) {
			ce.printStackTrace();
			log.error(" http server connection timed out.");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("http request error:{}", ex);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(bufferedReader);
			IOUtils.closeQuietly(inputStreamReader);
			if (conn != null) {
				conn.disconnect();
			}
		}
		return buffer.toString();
	}

	/**
	 * 发起http请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return
	 */
	public static byte[] download(String requestUrl, String requestMethod,
			String outputStr, boolean isSSL) {
		byte[] buff = null;
		AsyncHttpClient client = null;
		InputStream inputStream = null;
		try {
			if (isSSL) {
				client = HttpConnectionFactory.getInstance().getHttpClient();
			} else {
				client = HttpConnectionFactory.getInstance().getHttpsClient();
			}
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
			buff = FileUtils.getBytes(inputStream);
			// 释放资源
			IOUtils.closeQuietly(inputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("http request error:{}", ex);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return buff;
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
	public static JSONObject executeRequest(String requestUrl,
			String requestMethod, String outputStr) {
		String content = process(requestUrl, requestMethod, outputStr, true);
		JSONObject jsonObject = JSON.parseObject(content);
		return jsonObject;
	}

	/**
	 * Returns true if the specified URI is using a non-standard port (i.e. any
	 * port other than 80 for HTTP URIs or any port other than 443 for HTTPS
	 * URIs).
	 *
	 * @param uri
	 *
	 * @return True if the specified URI is using a non-standard port, otherwise
	 *         false.
	 */
	public static boolean isUsingNonDefaultPort(URI uri) {
		String scheme = uri.getScheme().toLowerCase();
		int port = uri.getPort();

		if (port <= 0)
			return false;
		if (scheme.equals("http") && port == 80)
			return false;
		if (scheme.equals("https") && port == 443)
			return false;

		return true;
	}

	/**
	 * 发起http请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return
	 */
	public static String process(String requestUrl, String requestMethod,
			String outputStr, boolean isSSL) {
		StringBuffer buffer = new StringBuffer();
		AsyncHttpClient client = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			if (isSSL) {
				client = HttpConnectionFactory.getInstance().getHttpClient();
			} else {
				client = HttpConnectionFactory.getInstance().getHttpsClient();
			}

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

		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("http request error:{}", ex);
		} finally {
			IOUtils.closeQuietly(bufferedReader);
			IOUtils.closeQuietly(inputStreamReader);
			IOUtils.closeQuietly(inputStream);
		}
		return buffer.toString();
	}

	/**
	 * Encode a string for use in the path of a URL; uses URLEncoder.encode,
	 * (which encodes a string for use in the query portion of a URL), then
	 * applies some postfilters to fix things up per the RFC. Can optionally
	 * handle strings which are meant to encode a path (ie include '/'es which
	 * should NOT be escaped).
	 *
	 * @param value
	 *            the value to encode
	 * @param path
	 *            true if the value is intended to represent a path
	 * @return the encoded value
	 */
	public static String urlEncode(final String value, final boolean path) {
		if (value == null) {
			return "";
		}

		try {
			String encoded = URLEncoder.encode(value, DEFAULT_ENCODING);

			Matcher matcher = ENCODED_CHARACTERS_PATTERN.matcher(encoded);
			StringBuffer buffer = new StringBuffer(encoded.length());

			while (matcher.find()) {
				String replacement = matcher.group(0);

				if ("+".equals(replacement)) {
					replacement = "%20";
				} else if ("*".equals(replacement)) {
					replacement = "%2A";
				} else if ("%7E".equals(replacement)) {
					replacement = "~";
				} else if (path && "%2F".equals(replacement)) {
					replacement = "/";
				}

				matcher.appendReplacement(buffer, replacement);
			}

			matcher.appendTail(buffer);
			return buffer.toString();

		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
	}

	private HttpUtils() {

	}

}
