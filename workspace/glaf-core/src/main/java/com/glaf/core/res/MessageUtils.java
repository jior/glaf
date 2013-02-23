/*
 *  
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
package com.glaf.core.res;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.tag.Globals;

public class MessageUtils {
	private static final Log log = LogFactory.getLog(MessageUtils.class);

	/**
	 * Adds the specified messages keys into the appropriate request attribute
	 * for use by the &lt;html:messages&gt; tag (if messages="true" is set), if
	 * any messages are required. Initialize the attribute if it has not already
	 * been. Otherwise, ensure that the request attribute is not set.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param messages
	 *            Messages object
	 * @since Struts 1.2.1
	 */
	public static void addMessages(HttpServletRequest request,
			ViewMessages messages) {
		if (messages == null) {
			// bad programmer! *slap*
			return;
		}

		// get any existing messages from the request, or make a new one
		ViewMessages viewMessages = (ViewMessages) request
				.getAttribute(Globals.MESSAGE_KEY);

		if (viewMessages == null) {
			viewMessages = new ViewMessages();
		}

		// add incoming messages
		viewMessages.add(messages);

		// if still empty, just wipe it out from the request
		if (viewMessages.isEmpty()) {
			request.removeAttribute(Globals.MESSAGE_KEY);
			return;
		}
		
		log.debug("save viewMessages...");

		// Save the messages
		request.setAttribute(Globals.MESSAGE_KEY, viewMessages);
	}
}
