package com.glaf.core.domain;

import java.io.*;

import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.domain.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_DATABASE_ACCESS")
public class DatabaseAccess implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "DATABASEID_")
	protected Long databaseId;

	@Column(name = "ACTORID_", length = 50)
	protected String actorId;

	public DatabaseAccess() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseAccess other = (DatabaseAccess) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getActorId() {
		return this.actorId;
	}

	public Long getDatabaseId() {
		return this.databaseId;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public DatabaseAccess jsonToObject(JSONObject jsonObject) {
		return DatabaseAccessJsonFactory.jsonToObject(jsonObject);
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JSONObject toJsonObject() {
		return DatabaseAccessJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DatabaseAccessJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
