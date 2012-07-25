package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SysDeptRole implements Serializable{	
	private static final long serialVersionUID = 273479478656626289L;
	private long id;
	private SysRole role;
	private SysDepartment dept;
	private int grade;
	private String code;
	private int sort;
	private Set users = new HashSet();
	private Set functions = new HashSet();
	private Set apps = new HashSet();
	
	public Set getApps() {
		return apps;
	}
	public void setApps(Set apps) {
		this.apps = apps;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public SysDepartment getDept() {
		return dept;
	}
	public void setDept(SysDepartment dept) {
		this.dept = dept;
	}
	public Set getFunctions() {
		return functions;
	}
	public void setFunctions(Set functions) {
		this.functions = functions;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SysRole getRole() {
		return role;
	}
	public void setRole(SysRole role) {
		this.role = role;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Set getUsers() {
		return users;
	}
	public void setUsers(Set users) {
		this.users = users;
	}
	
}
