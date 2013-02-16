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

package org.jpage.tag;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.context.ApplicationContext;
import org.jpage.core.query.paging.Page;

public class PageTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private final static Log logger = LogFactory.getLog(PageTag.class);

	private final static String PAGE_PART_COUNTER = "jpage_paging_counter";

	private String name;

	private String form;

	private String prefix;

	private String display;

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getName() {
		if (name == null) {
			name = "jpage";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		if (prefix == null) {
			prefix = "";
		}
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int doEndTag() throws JspException {
		Page page = null;
		if (name == null) {
			name = "jpage";
		}
		page = (Page) pageContext.getAttribute(name);
		if (page == null) {
			page = (Page) pageContext.getRequest().getAttribute(name);
		}
		if (page == null) {
			page = Page.EMPTY_PAGE;
		}

		if (prefix == null) {
			prefix = "";
		}

		if (StringUtils.isNotBlank(prefix)) {
			boolean ok = org.jpage.util.Tools.isExpectableString(prefix);
			if (!ok) {
				throw new JspException("paging tag prefix is illegal:" + prefix);
			}
		}

		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String contextPath = ApplicationContext.getContextPath();
		String complexQuery = request.getParameter("complexQuery");
		String x_complex_query = request.getParameter("x_complex_query");
		String xyz_complex_query = request.getParameter("xyz_complex_query");
		page.setImagePath(contextPath);

		Map paramMap = new HashMap();
		StringBuffer buffer = new StringBuffer();

		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (paramName.startsWith(prefix)) {
				paramName = paramName.replaceAll(prefix, "");
			}
			if (paramName.startsWith("query_")) {
				if (StringUtils.isNotBlank(paramValue)) {
					paramName = paramName.replaceAll("query_", "");
					paramMap.put(paramName, paramValue);
				}
			}
		}

		Integer count = (Integer) pageContext.getRequest().getAttribute(
				prefix + PAGE_PART_COUNTER);
		if (count == null) {
			count = new Integer(0);
		}
		try {
			JspWriter out = pageContext.getOut();
			page.setPrefix(prefix);
			page.setImagePath(request.getContextPath());
			if (StringUtils.isNotBlank(form)) {
				String suffix = "_part_" + count.intValue();
				count = new Integer(count.intValue() + 1);
				if (count.intValue() == 1) {
					if (paramMap.size() > 0) {
						String queryString = org.jpage.util.JSONTools
								.encode(paramMap);
						if (logger.isDebugEnabled()) {
							logger.debug("query string:" + queryString);
						}
						queryString = org.jpage.util.RequestUtil
								.encodeString(queryString);
						buffer
								.append(
										"\n<input type=\"hidden\" id=\"x_complex_query\" name=\"x_complex_query\" value=\"")
								.append(queryString).append("\">");
					}
					if (StringUtils.isNotEmpty(xyz_complex_query)) {
						buffer
								.append(
										"\n<input type=\"hidden\" id=\"xyz_complex_query\" name=\"xyz_complex_query\" value=\"")
								.append(xyz_complex_query).append("\">");
					} else {
						if (StringUtils.isNotEmpty(x_complex_query)) {
							buffer
									.append(
											"\n<input type=\"hidden\" id=\"xyz_complex_query\" name=\"xyz_complex_query\" value=\"")
									.append(x_complex_query).append("\">");
						}
					}
					if (StringUtils.isNotEmpty(complexQuery)) {
						buffer
								.append(
										"\n<input type=\"hidden\" id=\"complexQuery\" name=\"complexQuery\" value=\"")
								.append(complexQuery).append("\">");
					}
					out.println(page.getDefaultHiddenField());
					out.println(buffer.toString());
				}
				pageContext.getRequest().setAttribute(
						prefix + PAGE_PART_COUNTER, count);
				boolean showCount = true;
				if (StringUtils.equals(display, "0")) {
					showCount = false;
				}
				String content = page.getPagingScript(form, suffix, showCount);
				out.println(content);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return EVAL_PAGE;
	}
}
