package com.glaf.base.modules.sys.model;

import java.io.Serializable;

public class SysTree implements Serializable{
	private static final long serialVersionUID = 2666681837822864771L;
	private long id;
	private long parent;
	private String name;
	private String desc;
	private int sort;
	private String code;
	private SysApplication app;
	private SysDepartment department;
	private int deep;
	
	public SysApplication getApp() {
		return app;
	}
	public void setApp(SysApplication app) {
		this.app = app;
	}
	public SysDepartment getDepartment() {
		return department;
	}
	public void setDepartment(SysDepartment department) {
		this.department = department;
	}
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
