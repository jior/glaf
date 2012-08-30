package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class BaseDataInfo implements Serializable {
	private static final long serialVersionUID = 4103533989257821676L;

	private long id; // 基础信息内部标识号
	private String name; // 基础信息名称
	private String code; // 代码
	private String no; // 基础信息内容名称
	private int parentId; // 基础信息父类标识号
	private int deep; // 树状结构基础信息深度
	private double value1; // 相关数值，主要有汇率，单位换算等
	private double value2;
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private Date ext5;
	private Date ext6;

	public String getCode() {
		return code;
	}

	public int getDeep() {
		return deep;
	}

	public String getExt1() {
		return ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public Date getExt5() {
		return ext5;
	}

	public Date getExt6() {
		return ext6;
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

	public int getParentId() {
		return parentId;
	}

	public double getValue1() {
		return value1;
	}

	public double getValue2() {
		return value2;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public void setExt5(Date ext5) {
		this.ext5 = ext5;
	}

	public void setExt6(Date ext6) {
		this.ext6 = ext6;
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

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setValue1(double value1) {
		this.value1 = value1;
	}

	public void setValue2(double value2) {
		this.value2 = value2;
	}
}
