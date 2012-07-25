package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SerialNumber implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7285967860734876783L;
	private long id;
	private String moduleNo;
	private Date lastDate;
	private int intervelNo;
	private int currentSerail;
	
	
	public int getCurrentSerail() {
		return currentSerail;
	}
	public void setCurrentSerail(int currentSerail) {
		this.currentSerail = currentSerail;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getIntervelNo() {
		return intervelNo;
	}
	public void setIntervelNo(int intervelNo) {
		this.intervelNo = intervelNo;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public String getModuleNo() {
		return moduleNo;
	}
	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

}
