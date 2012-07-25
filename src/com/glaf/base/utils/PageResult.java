package com.glaf.base.utils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: PageResult.java</p>
 *
 * <p>Description: ��ҳ����</p>
 *
 */
public class PageResult implements Serializable{
	private static final long serialVersionUID = 4829284558188407984L;
	private int currentPageNo;//��ǰҳ
	private int totalPageCount;//��ҳ��
	private int totalRecordCount;//�ܼ�¼��
	private List results;//�����
	private int pageSize;//ҳ���С
	
	
	/**
	 * ȡ��ǰҳ��
	 * @return ��ǰҳ��
	 */
	public int getCurrentPageNo() {
		if (currentPageNo == 0 || getTotalPageCount()==0)
			currentPageNo = 1;
		else if (currentPageNo > getTotalPageCount())
			currentPageNo = getTotalPageCount();
		
		return currentPageNo;
	}
	
	/**
	 * ���õ�ǰҳ��
	 * @param currentPageNo ��ǰҳ��
	 */
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	
	/**
	 * ȡ�ܵ�ҳ��
	 * @return �ܵ�ҳ��
	 */
	public int getTotalPageCount() {
		this.totalPageCount = (this.totalRecordCount + pageSize - 1) / pageSize;
		return totalPageCount;
	}
	
	/**
	 * �����ܵ�ҳ��
	 * @param totalPageCount �ܵ�ҳ��
	 */
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
	/**
	 * ȡ�ܵļ�¼��
	 * @return �ܵļ�¼��
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	
	/**
	 * �����ܵļ�¼��
	 * @param totalRecordCount �ܵļ�¼��
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	
	/**
	 * ȡ�����
	 * @return �����
	 */
	public List getResults() {
		return results;
	}
	
	/**
	 * ���ý����
	 * @param results �����
	 */
	public void setResults(List results) {
		this.results = results;
	}
	
	/**
	 * ȡҳ���С
	 * @return ҳ���С
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * ����ҳ���С
	 * @param pageSize ҳ���С
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}