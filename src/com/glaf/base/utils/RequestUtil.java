package com.glaf.base.utils;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.util.DateTools;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.action.AuthorizeBean;
import com.glaf.base.modules.sys.model.SysUser;

public class RequestUtil {
	protected final static Log logger = LogFactory.getLog(RequestUtil.class);

	private final static Map<String, Object> paramMap = new HashMap<String, Object>();

	static {
		paramMap.put("submit", "submit");
		paramMap.put("reset", "reset");
		paramMap.put("button", "button");
		paramMap.put("cancel", "cancel");
	}

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
			Base64 base64 = new Base64();
			byte[] bytes = base64.decode(StringTools.decodeHex(str));
			return new String(bytes);
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

	public static String encodeString(String str) {
		try {
			Base64 base64 = new Base64();
			byte[] bytes = base64.encode(str.getBytes());
			return StringTools.encodeHex(bytes);
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

	public static String getActorId(HttpServletRequest request) {
		String actorId = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			actorId = (String) session.getAttribute(SysConstants.LOGIN);
		}
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
				return DateTools.toDate(paramValue);
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
			return Double.valueOf(paramValue);
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
			return Integer.valueOf(paramValue);
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

	public static SysUser getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		SysUser user = null;
		if (session != null) {
			String account = (String) session.getAttribute(SysConstants.LOGIN);
			AuthorizeBean bean = new AuthorizeBean();
			user = bean.getUser(account);
		}
		return user;
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
			return Long.valueOf(paramValue);
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
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
						Date date = DateTools.toDate(paramValue);
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
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
						Date date = DateTools.toDate(paramValue);
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
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
	public static Map<String, Object> getQueryParams(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();

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
								Date date = DateTools.toDate(paramValue);
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
	public static Map<String, Object> getQueryParams(
			HttpServletRequest request, String prefix) {
		Map<String, Object> params = new HashMap<String, Object>();

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

	public static String getRequestParameters(HttpServletRequest request) {
		Map<String, Object> m = getParameterMap(request);
		m.remove("x");
		m.remove("y");
		m.remove("redirectUrl");
		return createQueryStringFromMap(m, "&").toString();
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

	public static Object getValue(Class<?> type, String propertyValue) {
		if (type == null || propertyValue == null) {
			return null;
		}
		Object value = null;
		try {
			if (type == String.class) {
				value = propertyValue;
			} else if ((type == Integer.class) || (type == int.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Integer(propertyValue);
			} else if ((type == Long.class) || (type == long.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Long(propertyValue);
			} else if ((type == Float.class) || (type == float.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Float(propertyValue);
			} else if ((type == Double.class) || (type == double.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Double(propertyValue);
			} else if ((type == Boolean.class) || (type == boolean.class)) {
				value = Boolean.valueOf(propertyValue);
			} else if ((type == Character.class) || (type == char.class)) {
				value = new Character(propertyValue.charAt(0));
			} else if ((type == Short.class) || (type == short.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Short(propertyValue);
			} else if ((type == Byte.class) || (type == byte.class)) {
				value = new Byte(propertyValue);
			} else if (type == java.util.Date.class) {
				value = DateTools.toDate(propertyValue);
			} else if (type == java.sql.Date.class) {
				value = DateTools.toDate(propertyValue);
			} else if (type == java.sql.Timestamp.class) {
				value = DateTools.toDate(propertyValue);
			} else if (type.isAssignableFrom(List.class)) {
			} else if (type.isAssignableFrom(Set.class)) {
			} else if (type.isAssignableFrom(Collection.class)) {
			} else if (type.isAssignableFrom(Map.class)) {
			} else {
				value = propertyValue;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return value;
	}

	public static void populate(Object model, Map dataMap) {
		PropertyDescriptor[] propertyDescriptor = PropertyUtils
				.getPropertyDescriptors(model);
		for (int i = 0; i < propertyDescriptor.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptor[i];
			String propertyName = descriptor.getName();
			if (propertyName.equalsIgnoreCase("class")) {
				continue;
			}
			String value = null;
			Object obj = null;
			Object o = dataMap.get(propertyName);
			if (o != null && o instanceof String) {
				value = (String) o;
			} else {
				obj = o;
			}
			try {

				Class clazz = descriptor.getPropertyType();
				if (obj == null && value != null) {
					obj = getValue(clazz, value);
				}

				if (obj != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("property name:" + propertyName);
						logger.debug("property value:" + obj.toString());
						logger.debug("property class name:"
								+ obj.getClass().getName());
					}
					PropertyUtils.setProperty(model, propertyName, obj);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(dataMap);
				logger.error(ex);
			}
		}
	}

	public static void setLoginUser(HttpServletRequest request, String actorId) {
		AuthorizeBean bean = new AuthorizeBean();
		SysUser user = bean.getUser(actorId);
		setLoginUser(request, user);
	}

	public static void setLoginUser(HttpServletRequest request, SysUser bean) {
		HttpSession session = request.getSession(false);
		session.setAttribute(SysConstants.LOGIN, bean.getAccount());
		AuthorizeBean x = new AuthorizeBean();
		String menus = x.getMenus(bean);

		request.getSession().setAttribute(SysConstants.MENU, menus);

		org.jpage.actor.User user = new org.jpage.actor.User();
		user.setActorId(bean.getAccount().toLowerCase());
		user.setActorType(0);
		user.setMail(bean.getEmail());
		user.setMobile(bean.getMobile());
		if (bean.isSystemAdmin()) {
			user.setAdmin(true);
		}

		session.setAttribute(org.jpage.util.Constant.LOGIN_USER, user);
		session.setAttribute(org.jpage.util.Constant.LOGIN_USER_USERNAME,
				user.getActorId());

	}

	/**
	 * 设置参数
	 * 
	 * @param request
	 */
	public static void setRequestParameterToAttribute(HttpServletRequest request) {
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
				} else if (StringUtils.equalsIgnoreCase("hasPermission",
						paramName)) {
					continue;
				}
				request.setAttribute(paramName, paramValue);
			}
		}
	}

}