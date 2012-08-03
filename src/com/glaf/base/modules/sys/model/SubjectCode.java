package com.glaf.base.modules.sys.model;

import java.io.Serializable;

public class SubjectCode implements Serializable {
	private static final long serialVersionUID = -1L;
	private long id;
	private long parent;
	private String subjectCode;
	private String subjectName;
	private int sort;

	public long getId() {
		return id;
	}

	public long getParent() {
		return parent;
	}

	public int getSort() {
		return sort;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}
