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

package com.glaf.core.tag;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.RequestUtils;

public class InfoPageTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	protected final transient Log logger = LogFactory.getLog(InfoPageTag.class);

	private final static String PAGE_PART_COUNTER = "x_paging_counter";

	public final static String PAGENO_PARAMNAME = "__go2pageNo";

	private String name;

	private String form;

	private String prefix;

	private String display;

	private String imagePath;

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getName() {
		if (name == null) {
			name = "xxxx";
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

	public String getDefaultHiddenField(int pageNo) {
		if (prefix == null) {
			prefix = "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\n<input type=\"hidden\" id=\"").append(prefix)
				.append(PAGENO_PARAMNAME).append("\" name=\"").append(prefix)
				.append(PAGENO_PARAMNAME).append('"');

		if (pageNo > 0) {
			buffer.append(" value=\"").append(String.valueOf(pageNo))
					.append('"');
		}
		buffer.append('>');

		return buffer.toString();
	}

	public String getPagingScript(Paging page, String form, String suffix,
			boolean showCount) {
		if (prefix == null) {
			prefix = "";
		}
		StringBuffer buffer = new StringBuffer();
		int currPage = page.getCurrentPage();
		int totalPage = page.getTotalPage();
		if (currPage <= 0) {
			currPage = 1;
		}
		if (totalPage < 0) {
			totalPage = 0;
		}
		if (currPage > totalPage) {
			currPage = totalPage;
		}
		String pageLabel = prefix + "__go2PageNo" + suffix;
		// 用于向form里写参数的javascript
		buffer.append("\n\n<SCRIPT LANGUAGE=javascript>\n\n")
				.append("function jumpUp").append(prefix).append(suffix)
				.append("(maxPage,num){\n").append("     var a = ")
				.append("document.").append(form).append('.').append(pageLabel)
				.append(".value; \n")
				.append("     for(i=0;i<a.length;i++) { \n")
				.append("     var c=a.charAt(i);\n")
				.append("     if(c < \"0\" || c>\"9\"){\n")
				.append("     alert(\"请输入正确的整数!\");\n").append("     ")
				.append("document.").append(form).append('.').append(pageLabel)
				.append(".select();\n").append("      return false;\n")
				.append("         }\n").append("     } \n")
				.append("  if (a<=0 || a>maxPage || maxPage<=0){\n")
				.append("     alert(\"请输入正确页码!!\");\n").append("     ")
				.append("document.").append(form).append('.').append(pageLabel)
				.append(".select();\n").append("     return false;\n")
				.append("     }\n\n").append("     __go2page(num);\n")
				.append("}\n\n").append("function __go2page(num){\n")
				.append("     document.").append(form).append('.')
				.append(prefix).append(PAGENO_PARAMNAME)
				.append(".value=num;\n").append("     document.").append(form)
				.append(".submit();\n").append("}\n\n").append("\n</SCRIPT>\n");

		if (totalPage > 0) {
			// buffer.append("\n<div class=\"page-nav fix\">");
			buffer.append("\n    <div class=\"suf-page-nav fix\">");
			buffer.append(
					"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(1);\"><img src=\"")
					.append(imagePath)
					.append("/page_first.gif\" title=\"第一页\"></a>");

			if (currPage - 1 > 0) {
				buffer.append(
						"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(")
						.append(currPage - 1).append(");\"><img src=\"")
						.append(imagePath)
						.append("/page_previous.gif\" title=\"前一页\"></a>");
			} else {
				buffer.append("\n       <img src=\"").append(imagePath)
						.append("/page_previous.gif\" title=\"前一页\">");
			}

			if (totalPage > 10) {
				if (currPage < 5) {
					for (int i = 1; i <= 5; i++) {
						if (currPage != i) {
							buffer.append(
									"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(")
									.append(i).append(");\">").append(i)
									.append("</a>");
						} else {
							buffer.append(
									"\n        <span class=\"page-numbers page-current\">")
									.append(currPage).append("</span>");
						}
					}

					buffer.append("\n        <span class=\"page-numbers dots\">...</span> ");

					for (int i = totalPage - 5; i <= totalPage; i++) {
						buffer.append(
								"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(")
								.append(i).append(");\">").append(i)
								.append("</a>");
					}

				} else {
					if (currPage > (totalPage - 10)) {
						for (int i = 1; i <= 5; i++) {
							buffer.append(
									"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(")
									.append(i).append(");\">").append(i)
									.append("</a>");
						}
					}

					for (int i = currPage - 3; ((i <= currPage + 3) && (i < totalPage - 4)); i++) {
						if (currPage != i) {
							buffer.append(
									"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(")
									.append(i).append(");\">").append(i)
									.append("</a>");
						} else {
							buffer.append(
									"\n        <span class=\"page-numbers page-current\">")
									.append(currPage).append("</span>");
						}
					}

					buffer.append("\n        <span class=\"page-numbers dots\">...</span> ");

					for (int i = totalPage - 4; i <= totalPage; i++) {
						if (currPage != i) {
							buffer.append(
									"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(")
									.append(i).append(");\">").append(i)
									.append("</a>");
						} else {
							buffer.append(
									"\n        <span class=\"page-numbers page-current\">")
									.append(currPage).append("</span>");
						}
					}

				}
			} else {
				for (int i = 1; i <= 10 && i <= totalPage; i++) {
					if (currPage != i) {
						buffer.append(
								"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(")
								.append(i).append(");\">").append(i)
								.append("</a>");
					} else {
						buffer.append(
								"\n        <span class=\"page-numbers page-current\">")
								.append(currPage).append("</span>");
					}
				}

			}

			if (currPage + 1 <= totalPage) {
				buffer.append(
						"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(")
						.append(currPage + 1).append(");\"><img src=\"")
						.append(imagePath)
						.append("/page_next.gif\" title=\"下一页\"></a>");
			} else {
				buffer.append("\n       <img src=\"").append(imagePath)
						.append("/page_next.gif\" title=\"下一页\">");
			}

			buffer.append(
					"\n        <a class=\"page-numbers\" href=\"javascript:__go2page(")
					.append(totalPage).append(");\"><img src=\"")
					.append(imagePath)
					.append("/page_last.gif\" title=\"最后一页\"></a>");

			buffer.append("\n    </div>");
			// buffer.append("\n</div>");

		}

		return buffer.toString();
	}

	public int doEndTag() throws JspException {
		Paging page = null;
		if (name == null) {
			name = "glaf";
		}
		page = (Paging) pageContext.getAttribute(name);
		if (page == null) {
			page = (Paging) pageContext.getRequest().getAttribute(name);
		}
		if (page == null) {
			page = Paging.EMPTY_PAGE;
		}

		if (prefix == null) {
			prefix = "";
		}

		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		String complexQuery = request.getParameter("complexQuery");
		String x_complex_query = request.getParameter("x_complex_query");
		String xyz_complex_query = request.getParameter("xyz_complex_query");

		imagePath = request.getContextPath() + "/images";

		Map<String, Object> paramMap = new java.util.HashMap<String, Object>();
		StringBuffer buffer = new StringBuffer();

		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (paramName.startsWith(prefix)) {
				paramName = paramName.replaceAll(prefix, "");
			}
			if (paramName.startsWith("query_")) {
				if (StringUtils.isNotEmpty(paramValue)) {
					paramName = paramName.replaceAll("query_", "");
					paramMap.put(paramName, paramValue);
				}
			}
		}

		Integer count = (Integer) pageContext.getRequest().getAttribute(
				prefix + PAGE_PART_COUNTER);
		if (count == null) {
			count = 0;
		}
		try {
			JspWriter out = pageContext.getOut();
			page.setPrefix(prefix);
			if (StringUtils.isNotEmpty(form)) {
				String suffix = "_part_" + count.intValue();
				count = count.intValue() + 1;
				if (count.intValue() == 1) {
					if (paramMap.size() > 0) {
						String queryString = JsonUtils.encode(paramMap);
						if (LogUtils.isDebug()) {
							logger.debug("query string:" + queryString);
						}
						queryString = RequestUtils.encodeString(queryString);
						buffer.append(
								"\n<input type=\"hidden\" id=\"x_complex_query\" name=\"x_complex_query\" value=\"")
								.append(queryString).append("\">");
					}
					if (StringUtils.isNotEmpty(xyz_complex_query)) {
						buffer.append(
								"\n<input type=\"hidden\" id=\"xyz_complex_query\" name=\"xyz_complex_query\" value=\"")
								.append(xyz_complex_query).append("\">");
					} else {
						if (StringUtils.isNotEmpty(x_complex_query)) {
							buffer.append(
									"\n<input type=\"hidden\" id=\"xyz_complex_query\" name=\"xyz_complex_query\" value=\"")
									.append(x_complex_query).append("\">");
						}
					}
					if (StringUtils.isNotEmpty(complexQuery)) {
						buffer.append(
								"\n<input type=\"hidden\" id=\"complexQuery\" name=\"complexQuery\" value=\"")
								.append(complexQuery).append("\">");
					}
					out.println(this.getDefaultHiddenField(page
							.getCurrentPage()));
					out.println(buffer.toString());
				}
				pageContext.getRequest().setAttribute(
						prefix + PAGE_PART_COUNTER, count);
				boolean showCount = true;
				if (StringUtils.equals(display, "0")) {
					showCount = false;
				}
				String content = this.getPagingScript(page, form, suffix,
						showCount);
				out.println(content);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return EVAL_PAGE;
	}
}