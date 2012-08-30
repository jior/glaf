package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SysDeptRole implements Serializable {
	private static final long serialVersionUID = 273479478656626289L;
	private long id;
	private SysRole role;
	private SysDepartment dept;
	private long deptId;
	private int grade;
	private String code;
	private int sort;

	private Set<SysUser> users = new HashSet<SysUser>();
	private Set<SysFunction> functions = new HashSet<SysFunction>();
	private Set<SysApplication> apps = new HashSet<SysApplication>();

	public SysDeptRole() {

	}

	public Set<SysApplication> getApps() {
		return apps;
	}

	public String getCode() {
		return code;
	}

	public SysDepartment getDept() {
		return dept;
	}

	public long getDeptId() {
		return deptId;
	}

	public Set<SysFunction> getFunctions() {
		return functions;
	}

	public int getGrade() {
		return grade;
	}

	public long getId() {
		return id;
	}

	public SysRole getRole() {
		return role;
	}

	public int getSort() {
		return sort;
	}

	public Set<SysUser> getUsers() {
		return users;
	}

	public void setApps(Set<SysApplication> apps) {
		this.apps = apps;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDept(SysDepartment dept) {
		this.dept = dept;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public void setFunctions(Set<SysFunction> functions) {
		this.functions = functions;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRole(SysRole role) {
		this.role = role;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

}
