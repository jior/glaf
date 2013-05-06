package com.glaf.base.modules.sys.model;

import java.io.*;
import java.util.Date;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.base.modules.sys.util.*;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "SYS_DICTORY_DEF")
public class DictoryDefinition implements Serializable, JSONable,
		java.lang.Comparable<DictoryDefinition> {
	private static final long serialVersionUID = 1L;

	/**
	 * 列名
	 */
	@Column(name = "COLUMNNAME", length = 50)
	protected String columnName;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	protected Date createDate;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 数据长度
	 */
	@Column(name = "LENGTH")
	protected int length;

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 50)
	protected String name;

	/**
	 * 节点编号
	 */
	@Column(name = "NODEID")
	protected long nodeId;

	/**
	 * 必填项
	 */
	@Column(name = "REQUIRED")
	protected int required;

	/**
	 * 顺序号
	 */
	@Column(name = "SORT")
	protected int sort;

	/**
	 * 目标
	 */
	@Column(name = "TARGET", length = 100)
	protected String target;

	/**
	 * 标题
	 */
	@Column(name = "TITLE", length = 250)
	protected String title;

	/**
	 * 数据类型
	 */
	@Column(name = "TYPE", length = 50)
	protected String type;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY", length = 50)
	protected String updateBy;

	/**
	 * 修改日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE")
	protected Date updateDate;

	@javax.persistence.Transient
	protected Object value;

	public DictoryDefinition() {

	}

	public int compareTo(DictoryDefinition o) {
		if (o == null) {
			return -1;
		}

		DictoryDefinition obj = o;

		int l = this.sort - obj.getSort();

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DictoryDefinition other = (DictoryDefinition) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getDateType() {
		return this.type.toLowerCase();
	}

	public long getId() {
		return this.id;
	}

	public int getLength() {
		return this.length;
	}

	public String getName() {
		return this.name;
	}

	public long getNodeId() {
		return this.nodeId;
	}

	public String getNullable() {
		if (required == 1) {
			return "no";
		}
		return "yes";
	}

	public int getRequired() {
		return this.required;
	}

	public int getSort() {
		return this.sort;
	}

	public String getTarget() {
		return this.target;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public DictoryDefinition jsonToObject(JSONObject jsonObject) {
		return DictoryDefinitionJsonFactory.jsonToObject(jsonObject);
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public void setRequired(int required) {
		this.required = required;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public JSONObject toJsonObject() {
		return DictoryDefinitionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DictoryDefinitionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
