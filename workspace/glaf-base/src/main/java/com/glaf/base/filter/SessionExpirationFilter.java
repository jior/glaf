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

package com.glaf.base.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionExpirationFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		HttpSession session = request.getSession(false);
		if (null != session) {
			Date expirationDate = (Date) session.getAttribute("expirationDate");

			if (expirationDate == null) {
				expirationDate = new Date(System.currentTimeMillis()
						+ session.getMaxInactiveInterval() * 1000 + 1000000);
			}

			if (expirationDate.before(new Date())) {
				session.invalidate();
				session = null;
			} else {
				// ignore requests marked as both ajaxCall and
				// ignoreForSessionTimeout
				String isAjaxCall = request.getParameter("IsAjaxCall");
				String ignoreForSessionTimeout = request
						.getParameter("ignoreForSessionTimeout");
				boolean ignoreForTimeout = "1".equals(isAjaxCall)
						&& ("1".equals(ignoreForSessionTimeout));
				if (ignoreForTimeout) {
					// Do nothing; don't update the session timestamp
				} else {
					session.setAttribute(
							"expirationDate",
							new Date(System.currentTimeMillis()
									+ session.getMaxInactiveInterval() * 1000));
				}
			}
		}
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) {

	}

}