package com.glaf.base.modules.workspace.model;

import java.io.Serializable;
import java.util.Date;

import com.glaf.base.modules.sys.model.SysUser;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3111749338365950889L;

	private long id;

	private int type;
	
	//sysType  0:系统警告  1:系统消息
	private int sysType;

	private SysUser sender;

	private SysUser recver;

	private String recverList;

	private String title;

	private String content;

	private Date createDate;

	private int readed;

	private int category;

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getReaded() {
		return readed;
	}

	public void setReaded(int readed) {
		this.readed = readed;
	}

	public SysUser getRecver() {
		return recver;
	}

	public void setRecver(SysUser recver) {
		this.recver = recver;
	}

	public SysUser getSender() {
		return sender;
	}

	public void setSender(SysUser sender) {
		this.sender = sender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRecverList() {
		return recverList;
	}

	public void setRecverList(String recverList) {
		this.recverList = recverList;
	}

	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}

}
