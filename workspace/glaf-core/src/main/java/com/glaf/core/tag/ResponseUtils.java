/*
 * $Id: ResponseUtils.java 471754 2006-11-06 14:55:09Z husted $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.glaf.core.tag;

import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.glaf.core.res.MessageResources;

/**
 * General purpose utility methods related to generating a servlet response in
 * the Struts controller framework.
 * 
 * @version $Rev: 471754 $ $Date: 2005-08-21 14:46:28 -0400 (Sun, 21 Aug 2005) $
 */
public class ResponseUtils {
	// ------------------------------------------------------- Static Variables

	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages = MessageResources
			.getMessageResources(Globals.DEFAULT_RESOURCE_NAME);

	// --------------------------------------------------------- Public Methods

	/**
	 * <p>
	 * Look up and return current user locale, based on the specified
	 * parameters.
	 * </p>
	 * 
	 * @param request
	 *            The request used to lookup the Locale
	 * @param locale
	 *            Name of the session attribute for our user's Locale. If this
	 *            is <code>null</code>, the default locale key is used for the
	 *            lookup.
	 * @return current user locale
	 * @since Struts 1.2
	 */
	public static Locale getUserLocale(HttpServletRequest request, String locale) {
		Locale userLocale = null;
		HttpSession session = request.getSession(false);

		if (locale == null) {
			locale = Globals.LOCALE_KEY;
		}

		// Only check session if sessions are enabled
		if (session != null) {
			userLocale = (Locale) session.getAttribute(locale);
		}

		if (userLocale == null) {
			// Returns Locale based on Accept-Language header or the server
			// default
			userLocale = request.getLocale();
		}

		return userLocale;
	}

	/**
	 * <p>
	 * Return the <code>Class</code> object for the specified fully qualified
	 * class name, from this web application's class loader.
	 * </p>
	 * 
	 * @param className
	 *            Fully qualified class name to be loaded
	 * @return Class object
	 * @throws ClassNotFoundException
	 *             if the class cannot be found
	 */
	public static Class<?> applicationClass(String className)
			throws ClassNotFoundException {
		return applicationClass(className, null);
	}

	/**
	 * <p>
	 * Return the <code>Class</code> object for the specified fully qualified
	 * class name, from this web application's class loader.
	 * </p>
	 * 
	 * @param className
	 *            Fully qualified class name to be loaded
	 * @param classLoader
	 *            The desired classloader to use
	 * @return Class object
	 * @throws ClassNotFoundException
	 *             if the class cannot be found
	 */
	public static Class<?> applicationClass(String className,
			ClassLoader classLoader) throws ClassNotFoundException {
		if (classLoader == null) {
			// Look up the class loader to be used
			classLoader = Thread.currentThread().getContextClassLoader();

			if (classLoader == null) {
				classLoader = ResponseUtils.class.getClassLoader();
			}
		}

		// Attempt to load the specified class
		return (classLoader.loadClass(className));
	}

	/**
	 * Filter the specified string for characters that are senstive to HTML
	 * interpreters, returning the string with these characters replaced by the
	 * corresponding character entities.
	 * 
	 * @param value
	 *            The string to be filtered and returned
	 */
	public static String filter(String value) {
		if ((value == null) || (value.length() == 0)) {
			return value;
		}

		StringBuffer result = null;
		String filtered = null;

		for (int i = 0; i < value.length(); i++) {
			filtered = null;

			switch (value.charAt(i)) {
			case '<':
				filtered = "&lt;";

				break;

			case '>':
				filtered = "&gt;";

				break;

			case '&':
				filtered = "&amp;";

				break;

			case '"':
				filtered = "&quot;";

				break;

			case '\'':
				filtered = "&#39;";

				break;
			}

			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);

					if (i > 0) {
						result.append(value.substring(0, i));
					}

					result.append(filtered);
				}
			} else {
				if (filtered == null) {
					result.append(value.charAt(i));
				} else {
					result.append(filtered);
				}
			}
		}

		return (result == null) ? value : result.toString();
	}

	/**
	 * URLencodes a string assuming the character encoding is UTF-8.
	 * 
	 * @param url
	 * @return String The encoded url in UTF-8
	 */
	public static String encodeURL(String url) {
		return encodeURL(url, "UTF-8");
	}

	/**
	 * Use the new URLEncoder.encode() method from Java 1.4 if available, else
	 * use the old deprecated version. This method uses reflection to find the
	 * appropriate method; if the reflection operations throw exceptions, this
	 * will return the url encoded with the old URLEncoder.encode() method.
	 * 
	 * @param enc
	 *            The character encoding the urlencode is performed on.
	 * @return String The encoded url.
	 */
	public static String encodeURL(String url, String enc) {
		if ((enc == null) || (enc.length() == 0)) {
			enc = "UTF-8";
		}
		try {
			return URLEncoder.encode(url, enc);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
