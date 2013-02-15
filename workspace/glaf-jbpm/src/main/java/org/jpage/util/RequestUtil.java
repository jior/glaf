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


package org.jpage.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.security.CryptorFactory;
import org.jpage.exceptions.InputInvalidException;

public class RequestUtil {
	private static final String STOWED_REQUEST_ATTRIBS = "ssl.redirect.attrib.stowed";

	private final static Log log = LogFactory.getLog(RequestUtil.class);

	private final static Map paramMap = new HashMap();

	static {
		paramMap.put("submit", "submit");
		paramMap.put("reset", "reset");
		paramMap.put("button", "button");
		paramMap.put("cancel", "cancel");
	}

	public static String encodeURL(String str) {
		try {
			return CryptorFactory.getCryptor().encrypt(str);
		} catch (Exception ex) {
			return str;
		}
	}

	public static String decodeURL(String str) {
		try {
			return CryptorFactory.getCryptor().decrypt(str);
		} catch (Exception ex) {
			return str;
		}
	}

	public static String encodeString(String str) {
		try {
			return CryptorFactory.getCryptor().encrypt(str);
		} catch (Exception ex) {
			return str;
		}
	}

	public static String decodeString(String str) {
		try {
			return CryptorFactory.getCryptor().decrypt(str);
		} catch (Exception ex) {
			return str;
		}
	}

	public static Map getGridRows(HttpServletRequest request) {
		Map dataMap = new HashMap();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String value = request.getParameter(name);
			if (name.startsWith("grid@params:")) {
				String row = request.getParameter(name + ":rows");
				if (StringUtils.isNumeric(row)) {
					List sqlParams = new ArrayList();
					int count = Integer.parseInt(row);
					for (int i = 0; i < count; i++) {
						Map rowMap = new HashMap();
						StringTokenizer token = new StringTokenizer(value, ",");
						while (token.hasMoreTokens()) {
							String elem = token.nextToken();
							String gridParamName = "grid_param_" + elem + "_"
									+ i;
							String gridParamValue = request
									.getParameter(gridParamName);
							rowMap.put(elem, gridParamValue);
						}
						sqlParams.add(rowMap);
					}
					name = name.replaceAll("grid@params:", "");
					dataMap.put(name, sqlParams);
				}
			}
		}
		return dataMap;
	}

	public static String getParameter(HttpServletRequest request, String name) {
		if (request.getAttribute(name) != null) {
			return (String) request.getAttribute(name);
		}
		return request.getParameter(name);
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
	public static Object getParameter(HttpServletRequest request, Class pModel)
			throws ServletException {
		Object obj = null;
		try {
			obj = pModel.newInstance();
			return getParameter(request, obj);
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
	}

	public static int getInt(HttpServletRequest request, String paramName) {
		return getInt(request, paramName, 0);
	}

	public static double getDouble(HttpServletRequest request, String paramName) {
		return getDouble(request, paramName, 0.0);
	}

	public static int getInt(HttpServletRequest request, String paramName,
			int defaultValue) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNotBlank(paramValue)) {
			return Integer.parseInt(paramValue);
		}
		return defaultValue;
	}

	public static double getDouble(HttpServletRequest request,
			String paramName, double defaultValue) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNotBlank(paramValue)) {
			return Double.parseDouble(paramValue);
		}
		return defaultValue;
	}

	public static java.util.Date getDate(HttpServletRequest request,
			String paramName) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNotBlank(paramValue)) {
			return DateTools.toDate(paramValue);
		}
		return null;
	}

	/**
	 * 设置参数
	 * 
	 * @param request
	 */
	public static void setRequestParameterToAttribute(HttpServletRequest request) {
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (StringUtils.isNotBlank(paramName) && paramValue != null) {
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
				} else if (StringUtils.equalsIgnoreCase("hasPermission",
						paramName)) {
					continue;
				}
				request.setAttribute(paramName, paramValue);
			}
		}
	}

	/**
	 * 设置参数
	 * 
	 * @param request
	 */
	public static void setRequestParameterToHiddenArea(
			HttpServletRequest request, String hidden) {
		StringBuffer buffer = new StringBuffer();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (StringUtils.isNotBlank(paramName) && paramValue != null) {
				if (StringUtils.equalsIgnoreCase("method", paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("action", paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("__go2pageNo",
						paramName)) {
					continue;
				} else if (StringUtils.equalsIgnoreCase("__mediaType",
						paramName)) {
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
				} else if (StringUtils.equalsIgnoreCase("hasPermission",
						paramName)) {
					continue;
				}
				buffer.append("<input type=\"hidden\" name=\"").append(
						paramName).append("\" value=\"").append(paramValue)
						.append("\" >\n");
			}
		}
		request.setAttribute(hidden, buffer.toString());
	}

	/**
	 * 设置参数
	 * 
	 * @param request
	 */
	public static void setRequestQueryParameterToHiddenArea(
			HttpServletRequest request, String hidden) {
		StringBuffer buffer = new StringBuffer();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (StringUtils.isNotBlank(paramName)
					&& StringUtils.isNotBlank(paramValue)) {
				if (!paramName.startsWith("query_")) {
					continue;
				}
				buffer.append("<input type=\"hidden\" name=\"").append(
						paramName).append("\" value=\"").append(paramValue)
						.append("\" >\n");
			}
		}
		request.setAttribute(hidden, buffer.toString());
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
	public static Object getParameter(HttpServletRequest request, Object obj)
			throws ServletException {
		Map dataMap = new HashMap();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (StringUtils.isNotBlank(paramName) && paramValue != null) {
				if (paramValue.equalsIgnoreCase("null")) {
					continue;
				}
				dataMap.put(paramName, paramValue);
				if (paramName.toLowerCase().trim().indexOf("date") > 0) {
					try {
						Date date = DateTools.toDate(paramValue);
						dataMap.put(paramName, date);
					} catch (Exception ex) {
					}
				}
			}
		}
		try {
			BeanUtils.populate(obj, dataMap);
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return obj;
	}

 

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map getParameterMap(HttpServletRequest request) {
		Map dataMap = new HashMap();
		dataMap.put("contextPath", request.getContextPath());
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (StringUtils.isNotBlank(paramName) && paramValue != null) {
				if (paramValue.equalsIgnoreCase("null")) {
					continue;
				}
				dataMap.put(paramName, paramValue);
				if (paramName.toLowerCase().trim().indexOf("date") > 0) {
					try {
						Date date = DateTools.toDate(paramValue);
						dataMap.put(paramName, date);
					} catch (Exception ex) {
					}
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
	public static Map getQueryParams(HttpServletRequest request) {
		Map params = new HashMap();

		String complex_query = request.getParameter("xyz_complex_query");
		if (StringUtils.isNotEmpty(complex_query)) {
			complex_query = RequestUtil.decodeString(complex_query);
			Map xMap = JSONTools.decode(complex_query);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		String queryString = request.getParameter("x_complex_query");
		if (StringUtils.isNotEmpty(queryString)) {
			queryString = RequestUtil.decodeString(queryString);
			Map xMap = JSONTools.decode(queryString);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		String complexQuery = request.getParameter("complexQuery");
		if (StringUtils.isNotEmpty(complexQuery)) {
			complexQuery = RequestUtil.decodeString(complexQuery);
			Map xMap = JSONTools.decode(complexQuery);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		Enumeration enumeration2 = request.getParameterNames();
		while (enumeration2.hasMoreElements()) {
			String paramName = (String) enumeration2.nextElement();
			String paramValue = request.getParameter(paramName);
			if (paramName != null) {
				if (StringUtils.isNotBlank(paramValue)) {
					if (paramName.startsWith("query_")) {
						paramName = paramName.replaceAll("query_", "");
						if (paramName.toLowerCase().indexOf("date") > 0) {
							try {
								Date date = DateTools.toDate(paramValue);
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
	public static Map getQueryParams(HttpServletRequest request, String prefix) {
		Map params = new HashMap();

		String complex_query = request.getParameter("xyz_complex_query");
		if (StringUtils.isNotEmpty(complex_query)) {
			complex_query = RequestUtil.decodeString(complex_query);
			Map xMap = JSONTools.decode(complex_query);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		String queryString = request.getParameter("x_complex_query");
		if (StringUtils.isNotEmpty(queryString)) {
			queryString = RequestUtil.decodeString(queryString);
			Map xMap = JSONTools.decode(queryString);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		String complexQuery = request.getParameter("complexQuery");
		if (StringUtils.isNotEmpty(complexQuery)) {
			complexQuery = RequestUtil.decodeString(complexQuery);
			Map xMap = JSONTools.decode(complexQuery);
			if (xMap != null) {
				params.putAll(xMap);
			}
		}

		Enumeration enumeration2 = request.getParameterNames();
		while (enumeration2.hasMoreElements()) {
			String paramName = (String) enumeration2.nextElement();
			String paramValue = request.getParameter(paramName);
			if (paramName != null) {
				if (StringUtils.isNotBlank(paramValue)) {
					if (paramName.startsWith(prefix)) {
						paramName = paramName.replaceAll(prefix, "");
						if (paramName.startsWith("query_")) {
							paramName = paramName.replaceAll("query_", "");
							if (paramName.toLowerCase().indexOf("date") > 0) {
								try {
									Date date = DateTools.toDate(paramValue);
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
	 * 从request中获取参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map getParameterMap(HttpServletRequest request, String prefix) {
		Map dataMap = new HashMap();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (paramName != null && paramValue != null) {
				if (!StringUtils.equals(paramValue, "null")) {
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

	 

	public static String getRequestParameters(HttpServletRequest aRequest) {
		Map m = aRequest.getParameterMap();
		return createQueryStringFromMap(m, "&").toString();
	}

	public static StringBuffer createQueryStringFromMap(Map m, String ampersand) {
		StringBuffer aReturn = new StringBuffer("");
		Set aEntryS = m.entrySet();
		Iterator aEntryI = aEntryS.iterator();

		while (aEntryI.hasNext()) {
			Map.Entry aEntry = (Map.Entry) aEntryI.next();
			Object o = aEntry.getValue();

			if (o == null) {
				append(aEntry.getKey(), "", aReturn, ampersand);
			} else if (o instanceof String) {
				append(aEntry.getKey(), o, aReturn, ampersand);
			} else if (o instanceof String[]) {
				String[] aValues = (String[]) o;

				for (int i = 0; i < aValues.length; i++) {
					append(aEntry.getKey(), aValues[i], aReturn, ampersand);
				}
			} else {
				append(aEntry.getKey(), o, aReturn, ampersand);
			}
		}

		return aReturn;
	}

	private static StringBuffer append(Object key, Object value,
			StringBuffer queryString, String ampersand) {
		if (queryString.length() > 0) {
			queryString.append(ampersand);
		}

		try {
			queryString.append(URLEncoder.encode(key.toString(), "UTF-8"));
			queryString.append("=");
			queryString.append(URLEncoder.encode(value.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		return queryString;
	}

	public static void setPageContextAttribute(PageContext pageContext,
			Map dataMap) {
		if (dataMap != null && dataMap.size() > 0) {
			Iterator iter = dataMap.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				if (dataMap.get(key) != null) {
					pageContext.setAttribute(key, dataMap.get(key));
				}
			}
		}
	}

	public static void stowRequestAttributes(HttpServletRequest aRequest) {
		if (aRequest.getSession().getAttribute(STOWED_REQUEST_ATTRIBS) != null) {
			return;
		}

		Enumeration e = aRequest.getAttributeNames();
		Map map = new HashMap();

		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			map.put(name, aRequest.getAttribute(name));
		}

		aRequest.getSession().setAttribute(STOWED_REQUEST_ATTRIBS, map);
	}

	public static void reclaimRequestAttributes(HttpServletRequest aRequest) {
		Map map = (Map) aRequest.getSession().getAttribute(
				STOWED_REQUEST_ATTRIBS);

		if (map == null) {
			return;
		}

		Iterator itr = map.keySet().iterator();

		while (itr.hasNext()) {
			String name = (String) itr.next();
			aRequest.setAttribute(name, map.get(name));
		}

		aRequest.getSession().removeAttribute(STOWED_REQUEST_ATTRIBS);
	}

	public static void setCookie(HttpServletResponse response, String name,
			String value, String path) {
		if (log.isDebugEnabled()) {
			log.debug("Setting cookie '" + name + "' on path '" + path + "'");
		}

		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath(path);
		cookie.setMaxAge(3600 * 24 * 30); // 30 天

		response.addCookie(cookie);
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;

		if (cookies == null) {
			return returnCookie;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];

			if (thisCookie.getName().equals(name)) {
				if (!thisCookie.getValue().equals("")) {
					returnCookie = thisCookie;

					break;
				}
			}
		}

		return returnCookie;
	}

	public static void deleteCookie(HttpServletResponse response,
			Cookie cookie, String path) {
		if (cookie != null) {
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}

	public static String getAppURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80;
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80))
				|| (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}

	public static void removeRequestAttribute(HttpServletRequest request) {
		Set names = new HashSet();
		Enumeration e = request.getAttributeNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			names.add(name);
		}
		Iterator iterator = names.iterator();
		while (iterator.hasNext()) {
			String name = (String) iterator.next();
			request.removeAttribute(name);
		}
	}

	public static void removeSessionAttribute(HttpServletRequest request) {
		Set names = new HashSet();
		Enumeration e = request.getSession().getAttributeNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			names.add(name);
		}
		Iterator iterator = names.iterator();
		while (iterator.hasNext()) {
			String name = (String) iterator.next();
			request.getSession().removeAttribute(name);
		}
	}

	public static void removeAttribute(HttpServletRequest request) {
		removeRequestAttribute(request);
		removeSessionAttribute(request);
	}

}