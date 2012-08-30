package com.glaf.base.modules.sys.actionform;

import org.apache.struts.action.ActionForm;

public class SubjectCodeForm extends ActionForm{
	private static final long serialVersionUID = -7440549958960441410L;
	private long id;
	private long parent;
	private String subjectCode;
	private String subjectName;
	private int sort;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getParent() {
		return parent;
	}
	public void setParent(long parent) {
		this.parent = parent;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	
}
