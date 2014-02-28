/*
 * $Id: TagUtils.java 471754 2006-11-06 14:55:09Z husted $
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.res.MessageResources;
import com.glaf.core.res.PropertyMessageResourcesFactory;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;

/**
 * Provides helper methods for JSP tags.
 * 
 * @version $Rev: 471754 $
 * @since Struts 1.2
 */
public class TagUtils {

	private static Configuration conf = BaseConfiguration.create();
	/**
	 * The Singleton instance.
	 * 
	 * @since 1.3.5 Changed to non-final so it may be overridden, use at your
	 *        own risk (you've been warned!!)
	 */
	private static TagUtils instance = new TagUtils();

	/**
	 * Commons logging instance.
	 */
	private static final Log log = LogFactory.getLog(TagUtils.class);

	/**
	 * The message resources for this package. relevant messages out of this
	 * properties file.
	 */
	private static final MessageResources messages = MessageResources
			.getMessageResources(Globals.DEFAULT_RESOURCE_NAME);

	/**
	 * Maps lowercase JSP scope names to their PageContext integer constant
	 * values.
	 */
	private static final Map<String, Integer> scopes = new java.util.concurrent.ConcurrentHashMap<String, Integer>();

	/**
	 * Initialize the scope names map and the encode variable with the Java 1.4
	 * method if available.
	 */
	static {
		scopes.put("page", new Integer(PageContext.PAGE_SCOPE));
		scopes.put("request", new Integer(PageContext.REQUEST_SCOPE));
		scopes.put("session", new Integer(PageContext.SESSION_SCOPE));
		scopes.put("application", new Integer(PageContext.APPLICATION_SCOPE));
	}

	/**
	 * Constructor for TagUtils.
	 */
	protected TagUtils() {
		super();
	}

	/**
	 * Returns the Singleton instance of TagUtils.
	 */
	public static TagUtils getInstance() {
		return instance;
	}

	/**
	 * Set the instance. This blatently violates the Singleton pattern, but then
	 * some say Singletons are an anti-pattern.
	 * 
	 * @since 1.3.5 Changed to non-final and added setInstance() so TagUtils may
	 *        be overridden, use at your own risk (you've been warned!!)
	 * @param instance
	 *            The instance to set.
	 */
	public static void setInstance(TagUtils instance) {
		TagUtils.instance = instance;
	}

	/**
	 * URLencodes a string assuming the character encoding is UTF-8.
	 * 
	 * @param url
	 * @return String The encoded url in UTF-8
	 */
	public String encodeURL(String url) {
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
	public String encodeURL(String url, String enc) {
		return ResponseUtils.encodeURL(url, enc);
	}

	/**
	 * Filter the specified string for characters that are senstive to HTML
	 * interpreters, returning the string with these characters replaced by the
	 * corresponding character entities.
	 * 
	 * @param value
	 *            The string to be filtered and returned
	 */
	public String filter(String value) {
		return ResponseUtils.filter(value);
	}

	/**
	 * Retrieves the value from request scope and if it isn't already an
	 * <code>ViewMessages</code>, some classes are converted to one.
	 * 
	 * @param pageContext
	 *            The PageContext for the current page
	 * @param paramName
	 *            Key for parameter value
	 * @return ActionErrors in page context.
	 * @throws JspException
	 */
	public ViewMessages getViewMessages(PageContext pageContext,
			String paramName) throws JspException {
		ViewMessages am = new ViewMessages();

		Object value = pageContext.findAttribute(paramName);

		if (value != null) {
			try {
				if (value instanceof String) {
					am.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
							(String) value));
				} else if (value instanceof String[]) {
					String[] keys = (String[]) value;

					for (int i = 0; i < keys.length; i++) {
						am.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
								keys[i]));
					}
				} else if (value instanceof ViewMessages) {
					am = (ViewMessages) value;
				} else {
					throw new JspException(messages.getMessage(
							"viewMessages.errors", value.getClass().getName()));
				}
			} catch (JspException e) {
				throw e;
			} catch (Exception e) {
				log.warn("Unable to retieve ViewMessage for paramName : "
						+ paramName, e);
			}
		}

		return am;
	}

	/**
	 * Converts the scope name into its corresponding PageContext constant
	 * value.
	 * 
	 * @param scopeName
	 *            Can be "page", "request", "session", or "application" in any
	 *            case.
	 * @return The constant representing the scope (ie.
	 *         PageContext.REQUEST_SCOPE).
	 * @throws JspException
	 *             if the scopeName is not a valid name.
	 */
	public int getScope(String scopeName) throws JspException {
		Integer scope = (Integer) scopes.get(scopeName.toLowerCase());

		if (scope == null) {
			throw new JspException(messages.getMessage("lookup.scope", scope));
		}

		return scope.intValue();
	}

	/**
	 * Look up and return current user locale, based on the specified
	 * parameters.
	 * 
	 * @param pageContext
	 *            The PageContext associated with this request
	 * @param locale
	 *            Name of the session attribute for our user's Locale. If this
	 *            is <code>null</code>, the default locale key is used for the
	 *            lookup.
	 * @return current user locale
	 */
	public Locale getUserLocale(PageContext pageContext, String locale) {
		return ResponseUtils.getUserLocale(
				(HttpServletRequest) pageContext.getRequest(), locale);
	}

	/**
	 * Locate and return the specified bean, from an optionally specified scope,
	 * in the specified page context. If no such bean is found, return
	 * <code>null</code> instead. If an exception is thrown, it will have
	 * already been saved via a call to <code>saveException()</code>.
	 * 
	 * @param pageContext
	 *            Page context to be searched
	 * @param name
	 *            Name of the bean to be retrieved
	 * @param scopeName
	 *            Scope to be searched (page, request, session, application) or
	 *            <code>null</code> to use <code>findAttribute()</code> instead
	 * @return JavaBean in the specified page context
	 * @throws JspException
	 *             if an invalid scope name is requested
	 */
	public Object lookup(PageContext pageContext, String name, String scopeName)
			throws JspException {
		if (scopeName == null) {
			return pageContext.findAttribute(name);
		}

		try {
			return pageContext.getAttribute(name, instance.getScope(scopeName));
		} catch (JspException e) {
			saveException(pageContext, e);
			throw e;
		}
	}

	/**
	 * Locate and return the specified property of the specified bean, from an
	 * optionally specified scope, in the specified page context. If an
	 * exception is thrown, it will have already been saved via a call to
	 * <code>saveException()</code>.
	 * 
	 * @param pageContext
	 *            Page context to be searched
	 * @param name
	 *            Name of the bean to be retrieved
	 * @param property
	 *            Name of the property to be retrieved, or <code>null</code> to
	 *            retrieve the bean itself
	 * @param scope
	 *            Scope to be searched (page, request, session, application) or
	 *            <code>null</code> to use <code>findAttribute()</code> instead
	 * @return property of specified JavaBean
	 * @throws JspException
	 *             if an invalid scope name is requested
	 * @throws JspException
	 *             if the specified bean is not found
	 * @throws JspException
	 *             if accessing this property causes an IllegalAccessException,
	 *             IllegalArgumentException, InvocationTargetException, or
	 *             NoSuchMethodException
	 */
	public Object lookup(PageContext pageContext, String name, String property,
			String scope) throws JspException {
		// Look up the requested bean, and return if requested
		Object bean = lookup(pageContext, name, scope);

		if (bean == null) {
			JspException e = null;

			if (scope == null) {
				e = new JspException(messages.getMessage("lookup.bean.any",
						name));
			} else {
				e = new JspException(messages.getMessage("lookup.bean", name,
						scope));
			}

			saveException(pageContext, e);
			throw e;
		}

		if (property == null) {
			return bean;
		}

		// Locate and return the specified property
		try {
			return PropertyUtils.getProperty(bean, property);
		} catch (IllegalAccessException e) {
			saveException(pageContext, e);
			throw new JspException(messages.getMessage("lookup.access",
					property, name));
		} catch (IllegalArgumentException e) {
			saveException(pageContext, e);
			throw new JspException(messages.getMessage("lookup.argument",
					property, name));
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();

			if (t == null) {
				t = e;
			}

			saveException(pageContext, t);
			throw new JspException(messages.getMessage("lookup.target",
					property, name));
		} catch (NoSuchMethodException e) {
			saveException(pageContext, e);

			String beanName = name;

			// Name defaults to Contants.BEAN_KEY if no name is specified by
			// an input tag. Thus lookup the bean under the key and use
			// its class name for the exception message.
			if (Globals.BEAN_KEY.equals(name)) {
				Object obj = pageContext.findAttribute(Globals.BEAN_KEY);

				if (obj != null) {
					beanName = obj.getClass().getName();
				}
			}

			throw new JspException(messages.getMessage("lookup.method",
					property, beanName));
		}
	}

	/**
	 * Look up and return a message string, based on the specified parameters.
	 * 
	 * @param pageContext
	 *            The PageContext associated with this request
	 * @param bundle
	 *            Name of the servlet context attribute for our message
	 *            resources bundle
	 * @param locale
	 *            Name of the session attribute for our user's Locale
	 * @param key
	 *            Message key to be looked up and returned
	 * @return message string
	 * @throws JspException
	 *             if a lookup error occurs (will have been saved in the request
	 *             already)
	 */
	public String message(PageContext pageContext, String bundle,
			String locale, String key) throws JspException {
		return message(pageContext, bundle, locale, key, null);
	}

	/**
	 * Look up and return a message string, based on the specified parameters.
	 * 
	 * @param pageContext
	 *            The PageContext associated with this request
	 * @param bundle
	 *            Name of the servlet context attribute for our message
	 *            resources bundle
	 * @param locale
	 *            Name of the session attribute for our user's Locale
	 * @param key
	 *            Message key to be looked up and returned
	 * @param args
	 *            Replacement parameters for this message
	 * @return message string
	 * @throws JspException
	 *             if a lookup error occurs (will have been saved in the request
	 *             already)
	 */
	public String message(PageContext pageContext, String bundle,
			String locale, String key, Object[] args) throws JspException {
		MessageResources resources = retrieveMessageResources(pageContext,
				bundle, false);

		Locale userLocale = getUserLocale(pageContext, locale);
		String message = null;

		if (args == null) {
			message = resources.getMessage(userLocale, key);
		} else {
			message = resources.getMessage(userLocale, key, args);
		}

		if ((message == null) && log.isDebugEnabled()) {
			// log missing key to ease debugging
			log.debug(resources.getMessage("message.resources", key, bundle,
					locale));
		}

		return message;
	}

	/**
	 * Return true if a message string for the specified message key is present
	 * for the specified <code>Locale</code> and bundle.
	 * 
	 * @param pageContext
	 *            The PageContext associated with this request
	 * @param bundle
	 *            Name of the servlet context attribute for our message
	 *            resources bundle
	 * @param locale
	 *            Name of the session attribute for our user's Locale
	 * @param key
	 *            Message key to be looked up and returned
	 * @return true if a message string for message key exists
	 * @throws JspException
	 *             if a lookup error occurs (will have been saved in the request
	 *             already)
	 */
	public boolean present(PageContext pageContext, String bundle,
			String locale, String key) throws JspException {
		MessageResources resources = retrieveMessageResources(pageContext,
				bundle, true);

		Locale userLocale = getUserLocale(pageContext, locale);

		return resources.isPresent(userLocale, key);
	}

	/**
	 * Returns the appropriate MessageResources object for the current module
	 * and the given bundle.
	 * 
	 * @param pageContext
	 *            Search the context's scopes for the resources.
	 * @param bundle
	 *            The bundle name to look for. If this is <code>null</code>, the
	 *            default bundle name is used.
	 * @param checkPageScope
	 *            Whether to check page scope
	 * @return MessageResources The bundle's resources stored in some scope.
	 * @throws JspException
	 *             if the MessageResources object could not be found.
	 */
	public MessageResources retrieveMessageResources(PageContext pageContext,
			String bundle, boolean checkPageScope) throws JspException {
		MessageResources resources = null;

		if (bundle == null) {
			bundle = conf.get("i18n.messages_key");
		}

		if (bundle == null) {
			bundle = Globals.MESSAGES_KEY;
		}

		if (checkPageScope) {
			resources = (MessageResources) pageContext.getAttribute(bundle,
					PageContext.PAGE_SCOPE);
		}

		if (resources == null) {
			resources = (MessageResources) pageContext.getAttribute(bundle,
					PageContext.REQUEST_SCOPE);
		}

		if (resources == null) {
			resources = (MessageResources) pageContext.getAttribute(bundle,
					PageContext.APPLICATION_SCOPE);
		}

		if (resources == null) {
			resources = (MessageResources) pageContext.getAttribute(
					Globals.DEFAULT_RESOURCE_NAME,
					PageContext.APPLICATION_SCOPE);
			if (resources == null) {
				resources = PropertyMessageResourcesFactory.createFactory()
						.createResources(Globals.DEFAULT_RESOURCE_NAME);
				pageContext.setAttribute(Globals.DEFAULT_RESOURCE_NAME,
						resources, PageContext.APPLICATION_SCOPE);
			}
		}

		if (resources == null) {
			JspException e = new JspException(messages.getMessage(
					"message.bundle", bundle));

			saveException(pageContext, e);
			throw e;
		}

		return resources;
	}

	/**
	 * Save the specified exception as a request attribute for later use.
	 * 
	 * @param pageContext
	 *            The PageContext for the current page
	 * @param exception
	 *            The exception to be saved
	 */
	public void saveException(PageContext pageContext, Throwable exception) {
		pageContext.setAttribute(Globals.EXCEPTION_KEY, exception,
				PageContext.REQUEST_SCOPE);
	}

	/**
	 * Write the specified text as the response to the writer associated with
	 * this page. <strong>WARNING</strong> - If you are writing body content
	 * from the <code>doAfterBody()</code> method of a custom tag class that
	 * implements <code>BodyTag</code>, you should be calling
	 * <code>writePrevious()</code> instead.
	 * 
	 * @param pageContext
	 *            The PageContext object for this page
	 * @param text
	 *            The text to be written
	 * @throws JspException
	 *             if an input/output error occurs (already saved)
	 */
	public void write(PageContext pageContext, String text) throws JspException {
		JspWriter writer = pageContext.getOut();

		try {
			writer.print(text);
		} catch (IOException e) {
			saveException(pageContext, e);
			throw new JspException(
					messages.getMessage("write.io", e.toString()));
		}
	}

	/**
	 * Write the specified text as the response to the writer associated with
	 * the body content for the tag within which we are currently nested.
	 * 
	 * @param pageContext
	 *            The PageContext object for this page
	 * @param text
	 *            The text to be written
	 * @throws JspException
	 *             if an input/output error occurs (already saved)
	 */
	public void writePrevious(PageContext pageContext, String text)
			throws JspException {
		JspWriter writer = pageContext.getOut();

		if (writer instanceof BodyContent) {
			writer = ((BodyContent) writer).getEnclosingWriter();
		}

		try {
			writer.print(text);
		} catch (IOException e) {
			saveException(pageContext, e);
			throw new JspException(
					messages.getMessage("write.io", e.toString()));
		}
	}
}
