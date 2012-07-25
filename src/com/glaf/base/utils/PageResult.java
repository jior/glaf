package com.glaf.base.utils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: PageResult.java</p>
 *
 * <p>Description: 分页对象</p>
 *
 */
public class PageResult implements Serializable{
	private static final long serialVersionUID = 4829284558188407984L;
	private int currentPageNo;//当前页
	private int totalPageCount;//总页数
	private int totalRecordCount;//总记录数
	private List results;//结果集
	private int pageSize;//页面大小
	
	
	/**
	 * 取当前页号
	 * @return 当前页号
	 */
	public int getCurrentPageNo() {
		if (currentPageNo == 0 || getTotalPageCount()==0)
			currentPageNo = 1;
		else if (currentPageNo > getTotalPageCount())
			currentPageNo = getTotalPageCount();
		
		return currentPageNo;
	}
	
	/**
	 * 设置当前页号
	 * @param currentPageNo 当前页号
	 */
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	
	/**
	 * 取总的页数
	 * @return 总的页数
	 */
	public int getTotalPageCount() {
		this.totalPageCount = (this.totalRecordCount + pageSize - 1) / pageSize;
		return totalPageCount;
	}
	
	/**
	 * 设置总的页数
	 * @param totalPageCount 总的页数
	 */
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
	/**
	 * 取总的记录数
	 * @return 总的记录数
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	
	/**
	 * 设置总的记录数
	 * @param totalRecordCount 总的记录数
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	
	/**
	 * 取结果集
	 * @return 结果集
	 */
	public List getResults() {
		return results;
	}
	
	/**
	 * 设置结果集
	 * @param results 结果集
	 */
	public void setResults(List results) {
		this.results = results;
	}
	
	/**
	 * 取页面大小
	 * @return 页面大小
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 设置页面大小
	 * @param pageSize 页面大小
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}