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

package org.jpage.core.query.paging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.CachedRowSet;

import org.jpage.context.ApplicationContext;

 
public class Page implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	 
	public final static String PAGENO_PARAMNAME = "__go2pageNo";

 
	public final static Page EMPTY_PAGE = new Page(Collections.EMPTY_LIST, 0,
			0, 0);

	 
	public final static int DEFAULT_PAGE_SIZE = 25;

	 
	public final static int MAX_RECORD_COUNT = 5000;

 
	private int currentPage = 1;

	 
	private int pageSize = 10;

	 
	private int totalRecordCount = 0;

 
	private int currRowNo;

 
	private List rows = new ArrayList();

	 
	private boolean cacheable;

 
	private CachedRowSet cachedRowSet;

 
	private String title;

 
	private String imagePath;

	private String prefix;

 
	private Map contextMap = new HashMap();
 
	private Map paramMap = new HashMap();

	public Page() {
		rows = new ArrayList();
		this.currentPage = 0;
		this.pageSize = 0;
		this.totalRecordCount = 0;
	}

	public Page(List rows, int currPageNo, int pageSize, int totalRecordCount) {
		this.rows = rows;
		this.currentPage = currPageNo;
		this.pageSize = pageSize;
		this.totalRecordCount = totalRecordCount;
	}

	public String getImagePath() {
		if (imagePath == null) {
			imagePath = "..";
		}
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * 获得当前页数。
	 * 
	 * @return 当前页数
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 获得每页最大记录数。
	 * 
	 * @return 每页最大记录数
	 */
	public int getMaxPageRecordCount() {
		return pageSize;
	}

	public int getCurrRowNo() {
		if (currRowNo == 0) {
			currRowNo = (currentPage - 1) * pageSize + 1;
		}
		return currRowNo;
	}

	public void setCurrRowNo(int currRowNo) {
		this.currRowNo = currRowNo;
	}

	/**
	 * 设置每页最大记录数。
	 * 
	 * @param pageSize
	 *            每页最大记录数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 得到总页数 算法：总记录数除以每页的纪录数，结果为整数，若还有余数，则需要加一。
	 * 
	 * @return int
	 */
	public int getTotalPageCount() {
		if (pageSize == 0) {
			return 0;
		}
		// 先求得整除的结果
		int pageCount = this.getTotalRecordCount() / pageSize;
		// 再求得模运算的结果
		int temp = this.getTotalRecordCount() % pageSize;
		// 若模运算的结果不为零，则总页数为整除的结果加上模运算的结果
		if (temp > 0) {
			pageCount += 1;
		}
		return pageCount;
	}

	/**
	 * 获得记录总数
	 * 
	 * @return 记录总数
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 * 设置记录总数
	 * 
	 * @param i
	 */
	public void setTotalRecordCount(int i) {
		this.totalRecordCount = i;
	}

	public List getRows() {
		if (rows == null) {
			rows = new ArrayList();
		}
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public CachedRowSet getCachedRowSet() {
		return cachedRowSet;
	}

	public void setCachedRowSet(CachedRowSet cachedRowSet) {
		this.cachedRowSet = cachedRowSet;
	}

	/**
	 * 设置当前页数。
	 * 
	 * @param currPageNo
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Map getContextMap() {
		return contextMap;
	}

	public void setContextMap(Map contextMap) {
		this.contextMap = contextMap;
	}

	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * 从request对象获得当前的页码值。实际使用的是其中名为"__go2pageNO"的参数, 当这个参数没有赋值时，取页码的默认值。
	 * 
	 * @param request
	 *            用来设置当前页码的request对象
	 * @throws Exception
	 */
	public int getCurrentPage(HttpServletRequest request) throws Exception {
		int num = 1;
		String temp = request.getParameter(PAGENO_PARAMNAME);
		if ((temp != null) && (temp.length() > 0)) {
			try {
				num = Integer.parseInt(temp);
			} catch (NumberFormatException e) {
				throw new Exception("页码参数不正确!");
			}
		}
		return num;
	}

	public String getDefaultHiddenField() {
		if (prefix == null) {
			prefix = "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\n<input type=\"hidden\" id=\"").append(prefix)
				.append(PAGENO_PARAMNAME).append("\" name=\"").append(prefix)
				.append(PAGENO_PARAMNAME).append("\"");

		if (this.getCurrentPage() > 0) {
			buffer.append(" value=\"").append(
					String.valueOf(this.getCurrentPage())).append("\"");
		}
		buffer.append(">");

		return buffer.toString();
	}

	public String getPagingHead(String form) {
		return getPagingScript(form, "_head", false);
	}

	public String getPagingTrail(String form) {
		return getPagingScript(form, "_trail", false);
	}

	public String getPagingHead(String form, boolean showCount) {
		return getPagingScript(form, "_head", showCount);
	}

	public String getPagingTrail(String form, boolean showCount) {
		return getPagingScript(form, "_trail", showCount);
	}

	/**
	 * 获得用于翻页的html代码。其中包含跳转页码信息的http参数名称是__go2pageNO。
	 * 
	 * @param form
	 *            HTML中包含查询条件的form名称。没有条件也要有一个空的form。
	 *            在翻页时，会通过JAVASCRIPT调用此form的submit()方法，以便在翻页时同时提交查询条件。
	 *            例子：form1(查询条件在本页面)，parent.conditionFrame.form1(查询条件在另一个帧内)
	 *            翻页控制块的样式类属性为“pageFont”（HTML中的class属性）。
	 * @return
	 */

	public String getPagingScript(String form, String suffix, boolean showCount) {
		if (prefix == null) {
			prefix = "";
		}
		StringBuffer buffer = new StringBuffer();
		int totalPageCount = getTotalPageCount();
		String pageLabel = prefix + "__go2PageNo" + suffix;
		// 用于向form里写参数的javascript
		buffer.append("\n\n<SCRIPT LANGUAGE=javascript>\n\n").append(
				"function jumpUp").append(prefix).append(suffix).append(
				"(maxPage,num){\n").append("     var a = ").append("document.")
				.append(form).append(".").append(pageLabel)
				.append(".value; \n").append(
						"     for(i=0;i<a.length;i++) { \n").append(
						"     var c=a.charAt(i);\n").append(
						"     if(c < \"0\" || c>\"9\"){\n").append(
						"     alert(\"请输入正确的整数!!\");\n").append("     ")
				.append("document.").append(form).append(".").append(pageLabel)
				.append(".select();\n").append("      return false;\n").append(
						"         }\n").append("     } \n").append(
						"  if (a<=0 || a>maxPage || maxPage<=0){\n").append(
						"     alert(\"请输入正确页码!!\");\n").append("     ").append(
						"document.").append(form).append(".").append(pageLabel)
				.append(".select();\n").append("     return false;\n").append(
						"     }\n\n").append("     __go2page(num);\n").append(
						"}\n\n").append("function __go2page(num){\n").append(
						"     document.").append(form).append(".").append(
						prefix).append(PAGENO_PARAMNAME)
				.append(".value=num;\n").append("     document.").append(form)
				.append(".submit();\n").append("}\n\n").append("\n</SCRIPT>\n");
		// 页码的html代码
		final String blank = "&nbsp;";
		buffer.append("\n<span class=\"pageFont\">\n");

		if (this.getCurrentPage() == 1 || this.getMaxPageRecordCount() == 0) {
			buffer.append("首页").append(blank).append("|").append(blank).append(
					"上页");
		} else {
			buffer.append("<a href=\"javascript:__go2page(1);\">首页</a>")
					.append(blank).append("|").append(blank).append(
							"<a href=\"javascript:__go2page(").append(
							getCurrentPage() - 1).append(");\">上页</a>");
		}
		buffer.append(blank).append("|").append(blank);
		if (this.getCurrentPage() == totalPageCount
				|| this.getMaxPageRecordCount() == 0) {
			buffer.append("下页").append(blank).append("|").append(blank).append(
					"尾页");
		} else {
			buffer.append("<a href=\"javascript:__go2page(").append(
					getCurrentPage() + 1).append(");\">下页</a>").append(blank)
					.append("|").append(blank).append(
							"<a href=\"javascript:__go2page(").append(
							totalPageCount).append(");\">尾页</a>");
		}
		buffer
				.append(blank)
				.append("|")
				.append(blank)
				.append("转到第 <input id=\"")
				.append(pageLabel)
				.append("\" name=\"")
				.append(pageLabel)
				.append("\" class=\"pageText\" size=\"3\" value=\"")
				.append(
						this.getCurrentPage() > 0 ? this.getCurrentPage() + ""
								: "")
				.append("\">页 ")
				.append(
						"\n<img src=\""
								+ getImagePath()
								+ "/images/go.gif\" border=\"0\" style=\"cursor:hand\" onclick=\"javascript:return jumpUp")
				.append(prefix).append(suffix).append("(").append(
						totalPageCount).append(",document.").append(form)
				.append(".").append(pageLabel).append(".value);\" >").append(
						blank);
		buffer.append("</span>");
		buffer.append("\n<span align=\"right\">&nbsp;&nbsp;&nbsp;&nbsp;");
		if (showCount && this.getTotalRecordCount() > 0) {
			buffer.append("共").append(this.getTotalRecordCount()).append("条记录")
					.append(blank);
			buffer.append("每页").append(this.getPageSize()).append("条记录")
					.append(blank);
			buffer.append("第").append(this.getCurrentPage()).append("/")
					.append(totalPageCount).append("页").append(blank);
		}
		buffer.append("</span>");

		return buffer.toString();
	}

}