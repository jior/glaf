package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class Dictory implements Serializable {
	private static final long serialVersionUID = 2756737871937885934L;
	private long id;
	private long typeId;
	private String code;
	private String name;
	private int sort;
	private String desc;
	private int blocked;
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private Date ext5;
	private Date ext6;

	public int getBlocked() {
		return blocked;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
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

	public int getSort() {
		return sort;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

}
