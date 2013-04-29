package com.glaf.base.modules.sys.model;

import java.io.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.*;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "sys_access")
public class SysAccess implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * Ó¦ÓÃ±àºÅ
	 */
	@Id
	@Column(name = "APPID", nullable = false)
	protected long appId;

	/**
	 * ½ÇÉ«±àºÅ
	 */
	@Id
	@Column(name = "ROLEID", nullable = false)
	protected long roleId;

	public SysAccess() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysAccess other = (SysAccess) obj;
		if (appId != other.appId)
			return false;
		if (roleId != other.roleId)
			return false;
		return true;
	}

	public long getAppId() {
		return this.appId;
	}

	public long getRoleId() {
		return this.roleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (appId ^ (appId >>> 32));
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
		return result;
	}

	public SysAccess jsonToObject(JSONObject jsonObject) {
		return SysAccessJsonFactory.jsonToObject(jsonObject);
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public JSONObject toJsonObject() {
		return SysAccessJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysAccessJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}
