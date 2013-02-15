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
	 * ��õ�ǰҳ����
	 * 
	 * @return ��ǰҳ��
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * ���ÿҳ����¼����
	 * 
	 * @return ÿҳ����¼��
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
	 * ����ÿҳ����¼����
	 * 
	 * @param pageSize
	 *            ÿҳ����¼��
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * �õ���ҳ�� �㷨���ܼ�¼������ÿҳ�ļ�¼�������Ϊ����������������������Ҫ��һ��
	 * 
	 * @return int
	 */
	public int getTotalPageCount() {
		if (pageSize == 0) {
			return 0;
		}
		// ����������Ľ��
		int pageCount = this.getTotalRecordCount() / pageSize;
		// �����ģ����Ľ��
		int temp = this.getTotalRecordCount() % pageSize;
		// ��ģ����Ľ����Ϊ�㣬����ҳ��Ϊ�����Ľ������ģ����Ľ��
		if (temp > 0) {
			pageCount += 1;
		}
		return pageCount;
	}

	/**
	 * ��ü�¼����
	 * 
	 * @return ��¼����
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 * ���ü�¼����
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
	 * ���õ�ǰҳ����
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
	 * ��request�����õ�ǰ��ҳ��ֵ��ʵ��ʹ�õ���������Ϊ"__go2pageNO"�Ĳ���, ���������û�и�ֵʱ��ȡҳ���Ĭ��ֵ��
	 * 
	 * @param request
	 *            �������õ�ǰҳ���request����
	 * @throws Exception
	 */
	public int getCurrentPage(HttpServletRequest request) throws Exception {
		int num = 1;
		String temp = request.getParameter(PAGENO_PARAMNAME);
		if ((temp != null) && (temp.length() > 0)) {
			try {
				num = Integer.parseInt(temp);
			} catch (NumberFormatException e) {
				throw new Exception("ҳ���������ȷ!");
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
	 * ������ڷ�ҳ��html���롣���а�����תҳ����Ϣ��http����������__go2pageNO��
	 * 
	 * @param form
	 *            HTML�а�����ѯ������form���ơ�û������ҲҪ��һ���յ�form��
	 *            �ڷ�ҳʱ����ͨ��JAVASCRIPT���ô�form��submit()�������Ա��ڷ�ҳʱͬʱ�ύ��ѯ������
	 *            ���ӣ�form1(��ѯ�����ڱ�ҳ��)��parent.conditionFrame.form1(��ѯ��������һ��֡��)
	 *            ��ҳ���ƿ����ʽ������Ϊ��pageFont����HTML�е�class���ԣ���
	 * @return
	 */

	public String getPagingScript(String form, String suffix, boolean showCount) {
		if (prefix == null) {
			prefix = "";
		}
		StringBuffer buffer = new StringBuffer();
		int totalPageCount = getTotalPageCount();
		String pageLabel = prefix + "__go2PageNo" + suffix;
		// ������form��д������javascript
		buffer.append("\n\n<SCRIPT LANGUAGE=javascript>\n\n").append(
				"function jumpUp").append(prefix).append(suffix).append(
				"(maxPage,num){\n").append("     var a = ").append("document.")
				.append(form).append(".").append(pageLabel)
				.append(".value; \n").append(
						"     for(i=0;i<a.length;i++) { \n").append(
						"     var c=a.charAt(i);\n").append(
						"     if(c < \"0\" || c>\"9\"){\n").append(
						"     alert(\"��������ȷ������!!\");\n").append("     ")
				.append("document.").append(form).append(".").append(pageLabel)
				.append(".select();\n").append("      return false;\n").append(
						"         }\n").append("     } \n").append(
						"  if (a<=0 || a>maxPage || maxPage<=0){\n").append(
						"     alert(\"��������ȷҳ��!!\");\n").append("     ").append(
						"document.").append(form).append(".").append(pageLabel)
				.append(".select();\n").append("     return false;\n").append(
						"     }\n\n").append("     __go2page(num);\n").append(
						"}\n\n").append("function __go2page(num){\n").append(
						"     document.").append(form).append(".").append(
						prefix).append(PAGENO_PARAMNAME)
				.append(".value=num;\n").append("     document.").append(form)
				.append(".submit();\n").append("}\n\n").append("\n</SCRIPT>\n");
		// ҳ���html����
		final String blank = "&nbsp;";
		buffer.append("\n<span class=\"pageFont\">\n");

		if (this.getCurrentPage() == 1 || this.getMaxPageRecordCount() == 0) {
			buffer.append("��ҳ").append(blank).append("|").append(blank).append(
					"��ҳ");
		} else {
			buffer.append("<a href=\"javascript:__go2page(1);\">��ҳ</a>")
					.append(blank).append("|").append(blank).append(
							"<a href=\"javascript:__go2page(").append(
							getCurrentPage() - 1).append(");\">��ҳ</a>");
		}
		buffer.append(blank).append("|").append(blank);
		if (this.getCurrentPage() == totalPageCount
				|| this.getMaxPageRecordCount() == 0) {
			buffer.append("��ҳ").append(blank).append("|").append(blank).append(
					"βҳ");
		} else {
			buffer.append("<a href=\"javascript:__go2page(").append(
					getCurrentPage() + 1).append(");\">��ҳ</a>").append(blank)
					.append("|").append(blank).append(
							"<a href=\"javascript:__go2page(").append(
							totalPageCount).append(");\">βҳ</a>");
		}
		buffer
				.append(blank)
				.append("|")
				.append(blank)
				.append("ת���� <input id=\"")
				.append(pageLabel)
				.append("\" name=\"")
				.append(pageLabel)
				.append("\" class=\"pageText\" size=\"3\" value=\"")
				.append(
						this.getCurrentPage() > 0 ? this.getCurrentPage() + ""
								: "")
				.append("\">ҳ ")
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
			buffer.append("��").append(this.getTotalRecordCount()).append("����¼")
					.append(blank);
			buffer.append("ÿҳ").append(this.getPageSize()).append("����¼")
					.append(blank);
			buffer.append("��").append(this.getCurrentPage()).append("/")
					.append(totalPageCount).append("ҳ").append(blank);
		}
		buffer.append("</span>");

		return buffer.toString();
	}

}