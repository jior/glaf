package com.glaf.base.modules.sys.model;

import java.io.Serializable;

public class SysFunction implements Serializable {
	private static final long serialVersionUID = -4669036487930746301L;
	private long id;
	private SysApplication app;
	private long appId;
	private String name;
	private String funcDesc;
	private String funcMethod;
	private int sort;

	public SysFunction() {

	}

	public SysApplication getApp() {
		return app;
	}

	public long getAppId() {
		return appId;
	}

	public String getFuncDesc() {
		return funcDesc;
	}

	public String getFuncMethod() {
		return funcMethod;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getSort() {
		return sort;
	}

	public void setApp(SysApplication app) {
		this.app = app;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}

	public void setFuncMethod(String funcMethod) {
		this.funcMethod = funcMethod;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
