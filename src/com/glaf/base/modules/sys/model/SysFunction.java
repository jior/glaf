package com.glaf.base.modules.sys.model;

import java.io.Serializable;

public class SysFunction implements Serializable{	
	private static final long serialVersionUID = -4669036487930746301L;
	private long id;
	private SysApplication app;
	private String name;
	private String funcDesc;
	private String funcMethod;
	private int sort;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SysApplication getApp() {
		return app;
	}

	public void setApp(SysApplication app) {
		this.app = app;
	}

	public String getFuncDesc() {
		return funcDesc;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}

	public String getFuncMethod() {
		return funcMethod;
	}

	public void setFuncMethod(String funcMethod) {
		this.funcMethod = funcMethod;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
}
