package com.glaf.base.modules.others.model;

import java.io.Serializable;
import java.util.Date;

public class Attachment implements Serializable{
	private static final long serialVersionUID = 3825200508464771531L;
	private long id;
	private long referId;
	private int referType;
	private String name;
	private String url;
	private Date createDate;
	private long createId;
	
	public long getCreateId() {
		return createId;
	}
	public void setCreateId(long createId) {
		this.createId = createId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public long getReferId() {
		return referId;
	}
	public void setReferId(long referId) {
		this.referId = referId;
	}
	public int getReferType() {
		return referType;
	}
	public void setReferType(int referType) {
		this.referType = referType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
}
