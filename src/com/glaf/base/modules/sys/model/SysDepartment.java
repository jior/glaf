package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SysDepartment implements Serializable{
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
	
	private Set roles = new HashSet();
	private Integer status;// «∑Ò”––ß
	private Set historyDeparts = new HashSet();
	
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	public Set getRoles() {
		return roles;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}
	public SysTree getNode() {
		return node;
	}
	public void setNode(SysTree node) {
		this.node = node;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Set getHistoryDeparts() {
		return historyDeparts;
	}
	public void setHistoryDeparts(Set historyDeparts) {
		this.historyDeparts = historyDeparts;
	}
	public String getFincode() {
		return fincode;
	}
	public void setFincode(String fincode) {
		this.fincode = fincode;
	}
	
}
