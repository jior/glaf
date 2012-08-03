package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SysDepartment implements Serializable {
	private static final long serialVersionUID = -1700125499848402378L;
	private long id;
	private String name;
	private String desc;
	private Date createTime;
	private int sort;
	private String no;
	private String code;
	private String code2;
	private SysTree node;
	private String fincode;
	private long nodeId;

	private Set<SysDeptRole> roles = new HashSet<SysDeptRole>();
	private int status = 0;// 是否有效[默认有效]

	public String getCode() {
		return code;
	}

	public String getCode2() {
		return code2;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getDesc() {
		return desc;
	}

	public String getFincode() {
		return fincode;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNo() {
		return no;
	}

	public SysTree getNode() {
		return node;
	}

	public long getNodeId() {
		return nodeId;
	}

	public Set<SysDeptRole> getRoles() {
		return roles;
	}

	public int getSort() {
		return sort;
	}

	public int getStatus() {
		return status;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setFincode(String fincode) {
		this.fincode = fincode;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setNode(SysTree node) {
		this.node = node;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public void setRoles(Set<SysDeptRole> roles) {
		this.roles = roles;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
