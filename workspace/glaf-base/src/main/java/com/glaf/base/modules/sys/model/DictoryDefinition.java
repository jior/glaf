package com.glaf.base.modules.sys.model;

import java.io.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.base.modules.sys.util.*;

public class DictoryDefinition implements Serializable,
		java.lang.Comparable<DictoryDefinition> {
	private static final long serialVersionUID = 1L;

	/**
	 * 列名
	 */
	protected String columnName;

	protected Long id;

	/**
	 * 数据长度
	 */
	protected Integer length;

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 节点编号
	 */
	protected Long nodeId = 0L;

	/**
	 * 必填项
	 */
	protected Integer required = 0;

	/**
	 * 顺序号
	 */
	protected Integer sort = 0;

	/**
	 * 目标
	 */
	protected String target;

	/**
	 * 标题
	 */
	protected String title;

	/**
	 * 数据类型
	 */
	protected String type;

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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public String getDateType() {
		return this.type.toLowerCase();
	}

	public Long getId() {
		return this.id;
	}

	public Integer getLength() {
		return this.length;
	}

	public String getName() {
		return this.name;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public String getNullable() {
		if (required != null && required == 1) {
			return "no";
		}
		return "yes";
	}

	public Integer getRequired() {
		return this.required;
	}

	public Integer getSort() {
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

	public Object getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public DictoryDefinition jsonToObject(JSONObject jsonObject) {
		return DictoryDefinitionJsonFactory.jsonToObject(jsonObject);
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public void setSort(Integer sort) {
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
