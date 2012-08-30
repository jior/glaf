package com.glaf.base.modules.sys.model;

import java.io.Serializable;

public class SysRole implements Serializable {
	private static final long serialVersionUID = 7738558740111388611L;
	private long id;
	private String name;
	private String desc;
	private String code;
	private int sort;

	public String getCode() {
		return code;
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

	public int getSort() {
		return sort;
	}

	public void setCode(String code) {
		this.code = code;
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

	public void setSort(int sort) {
		this.sort = sort;
	}

}
