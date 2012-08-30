package com.glaf.base.modules.others.model;

import java.io.Serializable;
import java.util.Date;

public class Audit implements Serializable{
	private static final long serialVersionUID = 4192168036356165765L;
	private long id;
	private long referId;
	private int referType;
	private long deptId;
	private String deptName;
	private String headship;
	private String leaderName;
	private long leaderId;
	private Date createDate;
	private String memo;
	private int flag;
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public long getDeptId() {
		return deptId;
	}
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	public String getHeadship() {
		return headship;
	}
	public void setHeadship(String headship) {
		this.headship = headship;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(long leaderId) {
		this.leaderId = leaderId;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
