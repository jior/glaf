package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SysDepartHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5222802125836801964L;
	private long id;
	private Date createDate;
	private Date updateDate;
	private long actorId;
	private String remark;
	
	private SysDepartment newDepart;
	private SysDepartment oldDepart;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public long getActorId() {
		return actorId;
	}
	public void setActorId(long actorId) {
		this.actorId = actorId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public SysDepartment getNewDepart() {
		return newDepart;
	}
	public void setNewDepart(SysDepartment newDepart) {
		this.newDepart = newDepart;
	}
	public SysDepartment getOldDepart() {
		return oldDepart;
	}
	public void setOldDepart(SysDepartment oldDepart) {
		this.oldDepart = oldDepart;
	}
	
}
