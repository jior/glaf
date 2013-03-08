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
	@Column(name = "FUNCID", length = 50, nullable = false)
	protected Long funcId;

	/**
	 * ½ÇÉ«±àºÅ
	 */
	@Id
	@Column(name = "ROLEID")
	protected Long roleId;

	public SysPermission() {

	}

	public Long getFuncId() {
		return this.funcId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
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
		SysPermission other = (SysPermission) obj;
		if (funcId == null) {
			if (other.funcId != null)
				return false;
		} else if (!funcId.equals(other.funcId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcId == null) ? 0 : funcId.hashCode());
		return result;
	}

	public SysPermission jsonToObject(JSONObject jsonObject) {
		return SysPermissionJsonFactory.jsonToObject(jsonObject);
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
