package com.glaf.base.modules.sys.model;

import java.io.Serializable;

public class SysTree implements Serializable {
	private static final long serialVersionUID = 2666681837822864771L;
	private long id;
	private long parent;
	private String name;
	private String desc;
	private int sort;
	private String code;
	private int deep;
	private SysApplication app;
	private SysDepartment department;

	public SysApplication getApp() {
		return app;
	}

	public String getCode() {
		return code;
	}

	public int getDeep() {
		return deep;
	}

	public SysDepartment getDepartment() {
		return department;
	}

	public String getDesc() {
		return desc;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getParent() {
		return parent;
	}

	public int getSort() {
		return sort;
	}

	public void setApp(SysApplication app) {
		this.app = app;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
