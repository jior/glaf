package com.glaf.base.modules.todo.model;

import java.util.*;

public class TMembership implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private long id;

	/**
	 * 参与者
	 */
	private String actorId;

	/**
	 * 参与者上级
	 */
	private String superiorId;

	/**
	 * 修改时间
	 */
	private Date modifyDate;

	/**
	 * 扩展属性
	 */
	private String attribute;

	public TMembership() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

}
