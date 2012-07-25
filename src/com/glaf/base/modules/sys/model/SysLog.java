package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable{	
	private static final long serialVersionUID = 3489584842305336744L;
	private long id;
	private String account;
	private String ip;
	private Date createTime;
	private String operate;
	private int flag;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	
}
