package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SysDepartHistory implements Serializable {

	private static final long serialVersionUID = 5222802125836801964L;
	private long id;
	private Date createDate;
	private Date updateDate;
	private long actorId;
	private String remark;

	private SysDepartment newDepart;
	private SysDepartment oldDepart;

	public long getActorId() {
		return actorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public long getId() {
		return id;
	}

	public SysDepartment getNewDepart() {
		return newDepart;
	}

	public SysDepartment getOldDepart() {
		return oldDepart;
	}

	public String getRemark() {
		return remark;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNewDepart(SysDepartment newDepart) {
		this.newDepart = newDepart;
	}

	public void setOldDepart(SysDepartment oldDepart) {
		this.oldDepart = oldDepart;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
