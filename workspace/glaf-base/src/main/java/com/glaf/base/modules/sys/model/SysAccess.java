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
	@Column(name = "APPID", length = 50, nullable = false)
	protected Long appId;

	/**
	 * ½ÇÉ«±àºÅ
	 */
	@Id
	@Column(name = "ROLEID")
	protected Long roleId;

	public SysAccess() {

	}

	public Long getAppId() {
		return this.appId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		return result;
	}

	public SysAccess jsonToObject(JSONObject jsonObject) {
		return SysAccessJsonFactory.jsonToObject(jsonObject);
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
