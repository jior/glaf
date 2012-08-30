package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SysApplication implements Serializable {
	private static final long serialVersionUID = 5148300850285163044L;
	private long id;
	private String name;
	private String desc;
	private String url;
	private int sort;
	private int showMenu;
	private SysTree node;
	private long nodeId;
	private Set<SysFunction> functions = new HashSet<SysFunction>();

	public SysApplication() {

	}

	public String getDesc() {
		return desc;
	}

	public Set<SysFunction> getFunctions() {
		return functions;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public SysTree getNode() {
		return node;
	}

	public long getNodeId() {
		return nodeId;
	}

	public int getShowMenu() {
		return showMenu;
	}

	public int getSort() {
		return sort;
	}

	public String getUrl() {
		return url;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setFunctions(Set<SysFunction> functions) {
		this.functions = functions;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNode(SysTree node) {
		this.node = node;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public void setShowMenu(int showMenu) {
		this.showMenu = showMenu;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
