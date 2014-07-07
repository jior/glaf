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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.identity.User;
import com.glaf.core.security.Encryptor;
import com.glaf.core.security.EncryptorFactory;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;

public class RequestUtils {
	private static final int COOKIE_LIVING_SECONDS = 3600 * 8;

	protected final static Log logger = LogFactory.getLog(RequestUtils.class);

	private static StringBuffer append(Object key, Object value,
			StringBuffer queryString, String ampersand) {
		if (queryString.length() > 0) {
			queryString.append(ampersand);
		}
		try {
			queryString.append(URLEncoder.encode(key.toString(), "UTF-8"));
			queryString.append('=');
			queryString.append(URLEncoder.encode(value.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		return queryString;
	}

	public static StringBuffer createQueryStringFromMap(Map<String, Object> m,
			String ampersand) {
		StringBuffer aReturn = new StringBuffer("");
		Set<Entry<String, Object>> entrySet = m.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				append(key, "", aReturn, ampersand);
			} else if (value instanceof String) {
				append(key, value, aReturn, ampersand);
			} else if (value instanceof String[]) {
				String[] aValues = (String[]) value;
				for (int i = 0; i < aValues.length; i++) {
					append(key, aValues[i], aReturn, ampersand);
				}
			} else {
				append(key, value, aReturn, ampersand);
			}
		}
		return aReturn;
	}

	public static String decodeString(String str) {
		try {
			// Base64 base64 = new Base64();
			// byte[] bytes = base64.decode(StringTools.decodeHex(str));
			// String tmp = new String(bytes);
			String tmp = str;
			Encryptor cryptor = EncryptorFactory.getEncryptor();
			tmp = cryptor.decrypt(tmp);
			String salt = DigestUtils.md5Hex(SystemConfig.getToken());
			tmp = StringUtils.replace(tmp, salt, "");
			return tmp;
		} catch (Exception ex) {
			return str;
		}
	}

	public static String decodeURL(String str) {
		try {
			return decodeString(str);
		} catch (Exception ex) {
			return str;
		}
	}

	private static Map<String, String> decodeValues(String ip, String value) {
		Map<String, String> cookieMap = new java.util.HashMap<String, String>();
		if (StringUtils.isNotEmpty(value)) {
			String c_x = decodeString(value);
			c_x = StringUtils.replace(c_x, DigestUtils.md5Hex(ip), "");
			JSONObject jsonObject = null;
			try {
				jsonObject = JSON.parseObject(c_x);
			} catch (Exception ex) {
				// Ignore Exception
			}
			if (jsonObject != null) {
				Iterator<Entry<String, Object>> iterator = jsonObject
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = iterator.next();
					String key = (String) entry.getKey();
					Object val = entry.getValue();
					if (val != null) {
						cookieMap.put(key, val.toString());
					}
				}
			}
		}
		return cookieMap;
	}

	public static String encodeString(String str) {
		try {
			String salt = DigestUtils.md5Hex(SystemConfig.getToken());
			str = str + salt;
			Encryptor cryptor = EncryptorFactory.getEncryptor();
			str = cryptor.encrypt(str);
			// Base64 base64 = new Base64();
			// byte[] bytes = base64.encode(str.getBytes());
			// return StringTools.encodeHex(bytes);
			return str;
		} catch (Exception ex) {
			return str;
		}
	}

	public static String encodeURL(String str) {
		try {
			return encodeString(str);
		} catch (Exception ex) {
			return str;
		}
	}

	private static String encodeValues(String ip, String systemName,
			String actorId) {
		JSONObject rootJson = new JSONObject();
		rootJson.put(Constants.LOGIN_IP, ip);
		rootJson.put(Constants.LOGIN_ACTORID, actorId);
		if (systemName != null) {
			rootJson.put(Constants.SYSTEM_NAME, systemName);
		}
		rootJson.put(Constants.TS,
				String.valueOf(Long.MAX_VALUE - System.currentTimeMillis()));
		String c_x = rootJson.toJSONString();
		c_x = DigestUtils.md5Hex(ip) + c_x;
		c_x = encodeString(c_x);
		return c_x;
	}

	public static String getActorId(HttpServletRequest request) {
		String actorId = null;
		String ip = getIPAddress(request);
		ip = DigestUtils.md5Hex(ip);
		HttpSession session = request.getSession(false);
		if (session != null) {
			String value = (String) session.getAttribute(Constants.LOGIN_INFO);
			Map<String, String> cookieMap = decodeValues(ip, value);
			if (StringUtils.equals(cookieMap.get(Constants.LOGIN_IP), ip)) {
				actorId = cookieMap.get(Constants.LOGIN_ACTORID);
				logger.debug("#actorId=" + actorId);
			}
		}

		if (actorId == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equals(cookie.getName(),
							Constants.COOKIE_NAME)) {
						String value = cookie.getValue();
						Map<String, String> cookieMap = decodeValues(ip, value);
						// logger.debug("#cookieMap=" + cookieMap);
						if (StringUtils.equals(
								cookieMap.get(Constants.LOGIN_IP), ip)) {
							String time = cookieMap.get(Constants.TS);
							long now = Long.MAX_VALUE
									- System.currentTimeMillis();
							if (StringUtils.isNumeric(time)
									&& (Long.parseLong(time) - now) < COOKIE_LIVING_SECONDS * 1000) {
								actorId = cookieMap
										.get(Constants.LOGIN_ACTORID);
								break;
							}
						}
					}
				}
			}
		}

		logger.debug("@actorId=" + actorId);

		return actorId;
	}

	public static String getAttribute(HttpServletRequest request, String name) {
		Object value = request.getAttribute(name);
		if (value != null) {
			return value.toString();
		}
		return "";
	}

	/**
	 * 从request中获取boolean参数
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request,
			String paramName) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNotEmpty(paramValue)) {
			if ("on".equalsIgnoreCase(paramValue)) {
				return true;
			}
			if ("ok".equalsIgnoreCase(paramValue)) {
				return true;
			}
			if ("true".equalsIgnoreCase(paramValue)) {
				return true;
			}
			if ("1".equalsIgnoreCase(paramValue)) {
				return true;
			}
		}
		return false;
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equals(cookie.getName(), name)) {
					value = cookie.getValue();
				}
			}
		}
		return value;
	}

	public static String getCurrentSystem(HttpServletRequest request) {
		String currentSystem = null;
		String paramValue = request.getParameter(Constants.SYSTEM_NAME);
		if (StringUtils.isNotEmpty(paramValue)) {
			return paramValue;
		}
		String ip = getIPAddress(request);
		ip = DigestUtils.md5Hex(ip);
		HttpSession session = request.getSession(false);
		if (session != null) {
			String value = (String) session.getAttribute(Constants.LOGIN_INFO);
			Map<String, String> cookieMap = decodeValues(ip, value);
			if (StringUtils.equals(cookieMap.get(Constants.LOGIN_IP), ip)) {
				currentSystem = cookieMap.get(Constants.SYSTEM_NAME);
			}
		}

		if (currentSystem == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equals(cookie.getName(),
							Constants.COOKIE_NAME)) {
						String value = cookie.getValue();
						Map<String, String> cookieMap = decodeValues(ip, value);
						if (StringUtils.equals(
								cookieMap.get(Constants.LOGIN_IP), ip)) {
							String time = cookieMap.get(Constants.TS);
							long now = Long.MAX_VALUE
									- System.currentTimeMillis();
							if (StringUtils.isNumeric(time)
									&& (Long.parseLong(time) - now) < COOKIE_LIVING_SECONDS * 1000) {
								currentSystem = cookieMap
										.get(Constants.SYSTEM_NAME);
								break;
							}
						}
					}
				}
			}
		}

		return currentSystem;
	}

	/**
	 * 从request中获取date参数
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static java.util.Date getDate(HttpServletRequest request,
			String paramName) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNotEmpty(paramValue)) {
			try {
				return DateUtils.toDate(paramValue);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 从request中获取double参数
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static double getDouble(HttpServletRequest request, String paramName) {
		return getDouble(request, paramName, 0.0);
	}

	/**
	 * 从request中获取double参数
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultValue
	 * @return
	 */
	public static double getDouble(HttpServletRequest request,
			String paramName, double defaultValue) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNotEmpty(paramValue)) {
			return Double.parseDouble(paramValue);
		}
		return defaultValue;
	}

	/**
	 * 从request中获取int参数
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static int getInt(HttpServletRequest request, String paramName) {
		return getInt(request, paramName, 0);
	}

	/**
	 * 从request中获取int参数
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(HttpServletRequest request, String paramName,
			int defaultValue) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNotEmpty(paramValue)) {
			return Integer.parseInt(paramValue);
		}
		return defaultValue;
	}

	/**
	 * 从request中获取int参数
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static Integer getInteger(HttpServletRequest request,
			String paramName) {
		return getInteger(request, paramName, null);
	}

	/**
	 * 从request中获取int参数
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultValue
	 * @return
	 */
	public static Integer getInteger(HttpServletRequest request,
			String paramName, Integer defaultValue) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNotEmpty(paramValue)) {
			return Integer.parseInt(paramValue);
		}
		return defaultValue;
	}

	/**
	 * 获取Web客户端的真实IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIPAddress(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ipAddress)
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ipAddress)
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ipAddress)
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}

		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	public static String getLocalAddress(HttpServletRequest request) {
		// reads local address
		String host = getLocalHostAddress(request, true);
		return host + request.getContextPath();
	}

	public static String getLocalHostAddress(HttpServletRequest request) {
		return getLocalHostAddress(request, false);
	}

	public static String getLocalHostAddress(HttpServletRequest request,
			boolean includePort) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		String port = "";
		if (includePort) {
			int p = request.getServerPort();
			port = (p == 80) ? "" : ":" + p;
		}
		return scheme + "://" + serverName + port;
	}

	public static LoginContext getLoginContext(HttpServletRequest request) {
		String actorId = getActorId(request);
		if (StringUtils.isNotEmpty(actorId)) {
			return IdentityFactory.getLoginContext(actorId);
		}
		return null;
	}

	/**
	 * 从request中获取long参数
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static long getLong(HttpServletRequest request, String paramName) {
		return getLong(request, paramName, 0L);
	}

	/**
	 * 从request中获取long参数
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(HttpServletRequest request, String paramName,
			long defaultValue) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNotEmpty(paramValue)) {
			return Long.parseLong(paramValue);
		}
		return defaultValue;
	}

	/**
	 * 从request中封装一个对象
	 * 
	 * @param pRequest
	 *            request对象
	 * @param pModel
	 *            封装对象的类
	 * @return 超类
	 * @throws InputInvalidException
	 */
	public static Object getParameter(HttpServletRequest request, Object object)
			throws ServletException {
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = getParameter(request, paramName);
			if (StringUtils.isNotEmpty(paramName)
					&& StringUtils.isNotEmpty(paramValue)) {
				if (paramValue.equalsIgnoreCase("null")) {
					continue;
				}
				dataMap.put(paramName, paramValue);
				if (paramName.toLowerCase().trim().indexOf("date") > 0) {
					try {
						Date date = DateUtils.toDate(paramValue);
						dataMap.put(paramName, date);
					} catch (Exception ex) {
					}
				}
			}
		}
		try {
			BeanUtils.populate(object, dataMap);
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return object;
	}

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (StringUtils.isEmpty(value)) {
			String[] values = request.getParameterValues(name);
			if (values != null && values.length > 0) {
				StringBuffer buff = new StringBuffer();
				for (int i = 0; i < values.length; i++) {
					if (i < values.length - 1) {
						if (StringUtils.isNotEmpty(values[i])) {
							buff.append(values[i]).append(',');
						}
					} else {
						if (StringUtils.isNotEmpty(values[i])) {
							buff.append(values[i]);
						}
					}
				}
				if (StringUtils.isNotEmpty(buff.toString())) {
					value = buff.toString();
				}
			}
		}
		return value;
	}

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = getParameter(request, paramName);
			if (StringUtils.isNotEmpty(paramName)
					&& StringUtils.isNotEmpty(paramValue)) {
				if (paramValue.equalsIgnoreCase("null")) {
					continue;
				}
				dataMap.put(paramName, paramValue);
				String tmp = paramName.trim().toLowerCase();
				if (tmp.indexOf("date") > 0) {
					try {
						Date date = DateUtils.toDate(paramValue);
						dataMap.put(tmp, date);
					} catch (Exception ex) {
					}
				} else if (tmp.startsWith("x_encode_")) {
					String name = StringTools.replace(paramName, "x_encode_",
							"");
					dataMap.put(name, decodeString(paramValue));
				} else if (tmp.startsWith("x_collection_")) {
					String name = StringTools.replace(paramName,
							"x_collection_", "");
					dataMap.put(name, StringTools.split(paramValue));
				}
			}
		}
		return dataMap;
	}

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParameterMap(
			HttpServletRequest request, String prefix) {
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = getParameter(request, paramName);
			if (paramName != null && paramValue != null) {
				if (paramValue.trim().length() > 0
						&& !"null".equalsIgnoreCase(paramValue)) {
					if (!paramName.startsWith(prefix)) {
						continue;
					}
					paramName = paramName.substring(prefix.length());
					dataMap.put(paramName, paramValue);
				}
			}
		}
		return dataMap;
	}

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParameterMap(
			MultipartHttpServletRequest request) {
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		dataMap.put("contextPath", request.getContextPath());
		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = getParameter(request, paramName);
			if (StringUtils.isNotEmpty(paramName)
					&& StringUtils.isNotEmpty(paramValue)) {
				if (paramValue.equalsIgnoreCase("null")) {
					continue;
				}
				dataMap.put(paramName, paramValue);
				String tmp = paramName.trim().toLowerCase();
				if (tmp.indexOf("date") > 0) {
					try {
						Date date = DateUtils.toDate(paramValue);
						dataMap.put(paramName, date);
					} catch (Exception ex) {
					}
				} else if (tmp.startsWith("x_encode_")) {
					String name = StringTools.replace(paramName, "x_encode_",
							"");
					dataMap.put(name, decodeString(paramValue));
				}
			}
		}
		return dataMap;
	}

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getQueryParams(HttpServletRequest request) {
		Map<String, Object> params = new java.util.HashMap<String, Object>();

		String complex_query = request.getParameter("xyz_complex_query");
		if (StringUtils.isNotEmpty(complex_query)) {
			complex_query = RequestUtils.decodeString(complex_query);
			Map<String, Object> xMap = JsonUtils.decode(complex_query);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		String queryString = request.getParameter("x_complex_query");
		if (StringUtils.isNotEmpty(queryString)) {
			queryString = RequestUtils.decodeString(queryString);
			Map<String, Object> xMap = JsonUtils.decode(queryString);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		String complexQuery = request.getParameter("complexQuery");
		if (StringUtils.isNotEmpty(complexQuery)) {
			complexQuery = RequestUtils.decodeString(complexQuery);
			Map<String, Object> xMap = JsonUtils.decode(complexQuery);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		Enumeration<?> enumeration2 = request.getParameterNames();
		while (enumeration2.hasMoreElements()) {
			String paramName = (String) enumeration2.nextElement();
			String paramValue = getParameter(request, paramName);
			if (paramName != null) {
				if (StringUtils.isNotEmpty(paramValue)) {
					if (paramName.startsWith("query_")) {
						paramName = paramName.replaceAll("query_", "");
						if (paramName.toLowerCase().indexOf("date") > 0) {
							try {
								Date date = DateUtils.toDate(paramValue);
								params.put(paramName, date);
							} catch (Exception ex) {
								params.put(paramName, paramValue);
							}
						} else {
							params.put(paramName, paramValue);
						}
					} else if (paramName.startsWith("x_query_")) {
						paramName = paramName.replaceAll("x_query_", "");
						if (paramName.toLowerCase().indexOf("date") > 0) {
							try {
								Date date = DateUtils.toDate(paramValue);
								params.put(paramName, date);
							} catch (Exception ex) {
								params.put(paramName, paramValue);
							}
						} else {
							params.put(paramName, paramValue);
						}
					}
				}
			}
		}

		return params;
	}

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getQueryParams(
			HttpServletRequest request, String prefix) {
		Map<String, Object> params = new java.util.HashMap<String, Object>();

		String complex_query = request.getParameter("xyz_complex_query");
		if (StringUtils.isNotEmpty(complex_query)) {
			complex_query = RequestUtils.decodeString(complex_query);
			Map<String, Object> xMap = JsonUtils.decode(complex_query);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		String queryString = request.getParameter("x_complex_query");
		if (StringUtils.isNotEmpty(queryString)) {
			queryString = RequestUtils.decodeString(queryString);
			Map<String, Object> xMap = JsonUtils.decode(queryString);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		String complexQuery = request.getParameter("complexQuery");
		if (StringUtils.isNotEmpty(complexQuery)) {
			complexQuery = RequestUtils.decodeString(complexQuery);
			Map<String, Object> xMap = JsonUtils.decode(complexQuery);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		Enumeration<?> enumeration2 = request.getParameterNames();
		while (enumeration2.hasMoreElements()) {
			String paramName = (String) enumeration2.nextElement();
			String paramValue = getParameter(request, paramName);
			if (paramName != null) {
				if (StringUtils.isNotEmpty(paramValue)) {
					if (paramName.startsWith(prefix)) {
						paramName = paramName.replaceAll(prefix, "");
						if (paramName.startsWith("query_")) {
							paramName = paramName.replaceAll("query_", "");
							if (paramName.toLowerCase().indexOf("date") > 0) {
								try {
									Date date = DateUtils.toDate(paramValue);
									params.put(paramName, date);
								} catch (Exception ex) {
									params.put(paramName, paramValue);
								}
							} else {
								params.put(paramName, paramValue);
							}
						}
					}
				}
			}
		}

		return params;
	}

	/**
	 * 获取相对路径
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	public static String getRelativeUrl(HttpServletRequest request, String url) {
		if (!url.startsWith(getLocalHostAddress(request, true) + "/")) {
			return url;
		}
		url = url.replace((getLocalHostAddress(request, true) + "/"), "");
		String actualServlet = request.getContextPath()
				+ request.getServletPath();
		if (actualServlet.startsWith("/")) {
			actualServlet = actualServlet.substring(1);
		}
		int i = actualServlet.indexOf("/");
		while (i != -1) {
			if (url.startsWith(actualServlet.substring(0, i))) {
				url = url.replace(actualServlet.substring(0, i + 1), "");
			} else {
				url = "../" + url;
			}
			actualServlet = actualServlet.substring(i + 1);
			i = actualServlet.indexOf("/");
		}
		return url;
	}

	public static String getRequestParameters(HttpServletRequest request) {
		Map<String, Object> m = getParameterMap(request);
		m.remove("x");
		m.remove("y");
		m.remove("redirectUrl");
		return createQueryStringFromMap(m, "&").toString();
	}

	public static String getServiceUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serviceUrl = scheme + "://" + request.getServerName();
		if (request.getServerPort() != 80) {
			serviceUrl += ":" + request.getServerPort();
		}
		if (!"/".equals(request.getContextPath())) {
			serviceUrl += request.getContextPath();
		}
		return serviceUrl;
	}

	public static LoginContext getSessionLoginContext(HttpServletRequest request) {
		String actorId = null;
		String ip = getIPAddress(request);
		ip = DigestUtils.md5Hex(ip);
		HttpSession session = request.getSession(false);
		if (session != null) {
			String value = (String) session.getAttribute(Constants.LOGIN_INFO);
			Map<String, String> cookieMap = decodeValues(ip, value);
			if (StringUtils.equals(cookieMap.get(Constants.LOGIN_IP), ip)) {
				actorId = cookieMap.get(Constants.LOGIN_ACTORID);
			}
		}

		if (StringUtils.isNotEmpty(actorId)) {
			return IdentityFactory.getLoginContext(actorId);
		}

		return null;
	}

	/**
	 * 从request中获取字符串参数
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultValue
	 * @return
	 */
	public static String getString(HttpServletRequest request, String paramName) {
		String paramValue = getParameter(request, paramName);
		if (StringUtils.isNotEmpty(paramValue)) {
			return paramValue;
		}
		return null;
	}

	/**
	 * 从request中获取字符串参数
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultValue
	 * @return
	 */
	public static String getString(HttpServletRequest request,
			String paramName, String defaultValue) {
		String paramValue = getParameter(request, paramName);
		if (StringUtils.isNotEmpty(paramValue)) {
			return paramValue;
		}
		return defaultValue;
	}

	/**
	 * 获取参数值
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getStringValue(HttpServletRequest request, String name) {
		if (request.getAttribute(name) != null) {
			return (String) request.getAttribute(name);
		}
		return request.getParameter(name);
	}

	/**
	 * 获取系统名称 （即要访问的数据源定义名称）
	 * 
	 * @param request
	 * @return
	 */
	public static String getSystemName(HttpServletRequest request) {
		String systemName = request.getParameter("systemName");
		if (StringUtils.isEmpty(systemName)) {
			systemName = getCurrentSystem(request);
		}
		if (StringUtils.isEmpty(systemName)) {
			systemName = com.glaf.core.config.Environment.DEFAULT_SYSTEM_NAME;
		}
		return systemName;
	}

	public static String getTheme(HttpServletRequest request) {
		String theme = "default";
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils
						.equals(cookie.getName(), Constants.THEME_COOKIE)) {
					if (cookie.getValue() != null) {
						theme = cookie.getValue();
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(request.getParameter("theme"))) {
			theme = request.getParameter("theme");
		}
		return theme;
	}

	public static User getUser(HttpServletRequest request) {
		String actorId = getActorId(request);
		if (StringUtils.isNotEmpty(actorId)) {
			return IdentityFactory.getUser(actorId);
		}
		return null;
	}

	public static void removeLoginUser(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equals(cookie.getName(), Constants.COOKIE_NAME)) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					cookie.setValue(UUID32.getUUID());
					response.addCookie(cookie);
					logger.debug("remove user from cookie");
				}
			}
		}

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(Constants.LOGIN_INFO);
			session.invalidate();
		}
	}

	public static void setLoginUser(HttpServletRequest request,
			HttpServletResponse response, String systemName, String actorId) {
		String ip = getIPAddress(request);
		ip = DigestUtils.md5Hex(ip);
		String value = encodeValues(ip, systemName, actorId);
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute(Constants.LOGIN_INFO, value);
		}
		Cookie cookie = new Cookie(Constants.COOKIE_NAME, value);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}

	/**
	 * 设置参数
	 * 
	 * @param request
	 */
	public static void setRequestParameterToAttribute(HttpServletRequest request) {
		request.setAttribute("contextPath", request.getContextPath());
		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = getParameter(request, paramName);
			if (StringUtils.isNotEmpty(paramName)
					&& StringUtils.isNotEmpty(paramValue)) {
				if (request.getAttribute(paramName) != null) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("method", paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("action", paramName)) {
					continue;
				} else if (StringUtils
						.equalsIgnoreCase("actionType", paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("reset", paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("submit", paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("button", paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("cancel", paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("parent", paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("hasPermission",
						paramName)) {
					continue;
				}
				request.setAttribute(paramName, paramValue);
			}
		}
	}

	public static void setTheme(HttpServletRequest request,
			HttpServletResponse response) {
		String theme = request.getParameter("theme");
		if (StringUtils.isNotEmpty(theme)) {
			Cookie cookie = new Cookie(Constants.THEME_COOKIE, theme);
			cookie.setPath("/");
			cookie.setMaxAge(-1);
			response.addCookie(cookie);

			Cookie cookie2 = new Cookie("data-theme", "theme-" + theme);
			cookie2.setPath("/");
			cookie2.setMaxAge(-1);
			response.addCookie(cookie2);
		}
	}

	public static void setTheme(HttpServletRequest request,
			HttpServletResponse response, String theme) {
		if (StringUtils.isNotEmpty(theme)) {
			Cookie cookie = new Cookie(Constants.THEME_COOKIE, theme);
			cookie.setPath("/");
			cookie.setMaxAge(-1);
			response.addCookie(cookie);

			Cookie cookie2 = new Cookie("data-theme", "theme-" + theme);
			cookie2.setPath("/");
			cookie2.setMaxAge(-1);
			response.addCookie(cookie2);
		}
	}

}