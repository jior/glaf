/*
 * $Id: Globals.java 471754 2006-11-06 14:55:09Z husted $
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

import java.io.Serializable;

/**
 * Global manifest constants for the entire Struts Framework.
 * 
 * @version $Rev: 471754 $ $Date: 2005-06-18 20:27:26 -0400 (Sat, 18 Jun 2005) $
 */
public class Globals implements Serializable {
	// ----------------------------------------------------- Manifest Constants

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_RESOURCE_NAME = "ApplicationStrings";

	/**
	 * The name of this package.
	 */
	public static final String Package = "com.glaf.core.tag";

	/**
	 * The attribute key for the bean our form is related to.
	 */
	public static final String BEAN_KEY = Package + ".BEAN";

	/**
	 * The session attributes key under which the user's selected
	 * <code>java.util.Locale</code> is stored, if any. If no such attribute is
	 * found, the system default locale will be used when retrieving
	 * internationalized messages. If used, this attribute is typically set
	 * during user login processing.
	 */
	public static final String LOCALE_KEY = "com.glaf.core.tag.LOCALE";

	/**
	 * The request attributes key under which your action should store an
	 * <code>ViewMessages</code> object, if you are using the corresponding
	 * custom tag library elements.
	 * 
	 * @since Struts 1.1
	 */
	public static final String MESSAGE_KEY = "com.glaf.core.tag.VIEW_MESSAGE";

	/**
	 * <p>
	 * The base of the context attributes key under which our module
	 * <code>MessageResources</code> will be stored. This will be suffixed with
	 * the actual module prefix (including the leading "/" character) to form
	 * the actual resources key.
	 * </p>
	 * 
	 * <p>
	 * For each request processed by the controller servlet, the
	 * <code>MessageResources</code> object for the module selected by the
	 * request URI currently being processed will also be exposed under this key
	 * as a request attribute.
	 * </p>
	 */
	public static final String MESSAGES_KEY = "com.glaf.core.tag.MESSAGE";

	public static final String EXCEPTION_KEY = "com.glaf.core.tag.EXCEPTION";

	public static final String ERROR_KEY = "com.glaf.core.tag.ERROR";

	/**
	 * The name of the taglib package.
	 */
	public static final String TAGLIB_PACKAGE = "com.glaf.core.tag";

	/**
	 * The property under which a Cancel button press is reported.
	 */
	public static final String CANCEL_PROPERTY = TAGLIB_PACKAGE + ".CANCEL";

	/**
	 * The property under which a Cancel button press is reported, if the Cancel
	 * button is rendered as an image.
	 */
	public static final String CANCEL_PROPERTY_X = TAGLIB_PACKAGE + ".CANCEL.x";

	/**
	 * The property under which a transaction token is reported.
	 */
	public static final String TOKEN_KEY = TAGLIB_PACKAGE + ".TOKEN";
}
