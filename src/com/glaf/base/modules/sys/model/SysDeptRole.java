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

	private Set users = new HashSet();
	private Set functions = new HashSet();
	private Set apps = new HashSet();

	public SysDeptRole() {

	}

	public Set getApps() {
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

	public Set getFunctions() {
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

	public Set getUsers() {
		return users;
	}

	public void setApps(Set apps) {
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

	public void setFunctions(Set functions) {
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

	public void setUsers(Set users) {
		this.users = users;
	}

}
