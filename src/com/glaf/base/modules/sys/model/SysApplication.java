package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SysApplication implements Serializable{	
	private static final long serialVersionUID = 5148300850285163044L;
	private long id;
	private String name;
	private String desc;
	private String url;
	private int sort;
	private int showMenu;
	private SysTree node;
	private Set functions = new HashSet();
	
	public SysTree getNode() {
		return node;
	}
	public void setNode(SysTree node) {
		this.node = node;
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getShowMenu() {
		return showMenu;
	}
	public void setShowMenu(int showMenu) {
		this.showMenu = showMenu;
	}
	public Set getFunctions() {
		return functions;
	}
	public void setFunctions(Set functions) {
		this.functions = functions;
	}	
}
