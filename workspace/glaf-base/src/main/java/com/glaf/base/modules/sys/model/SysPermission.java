package com.glaf.base.modules.sys.model;

import java.io.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.*;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "sys_permission")
public class SysPermission implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * ¹¦ÄÜ±àºÅ
	 */
	@Id
	@Column(name = "FUNCID", nullable = false)
	protected long funcId;

	/**
	 * ½ÇÉ«±àºÅ
	 */
	@Id
	@Column(name = "ROLEID", nullable = false)
	protected long roleId;

	public SysPermission() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysPermission other = (SysPermission) obj;
		if (funcId != other.funcId)
			return false;
		if (roleId != other.roleId)
			return false;
		return true;
	}

	public long getFuncId() {
		return this.funcId;
	}

	public long getRoleId() {
		return this.roleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (funcId ^ (funcId >>> 32));
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
		return result;
	}

	public SysPermission jsonToObject(JSONObject jsonObject) {
		return SysPermissionJsonFactory.jsonToObject(jsonObject);
	}

	public void setFuncId(long funcId) {
		this.funcId = funcId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public JSONObject toJsonObject() {
		return SysPermissionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysPermissionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}
