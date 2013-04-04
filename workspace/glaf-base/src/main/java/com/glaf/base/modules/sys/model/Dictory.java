package com.glaf.base.modules.sys.model;

import java.io.*;
import java.util.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.base.modules.sys.util.*;

public class Dictory implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否启用
	 */
	protected Integer blocked;

	/**
	 * 编码
	 */
	protected String code;

	/**
	 * 描述
	 */
	protected String desc;

	/**
	 * 字符串值1
	 */
	protected String ext1;

	/**
	 * 日期值10
	 */
	protected Date ext10;

	protected Long ext11;

	protected Long ext12;

	protected Long ext13;

	protected Long ext14;

	protected Long ext15;

	protected Double ext16;

	protected Double ext17;

	protected Double ext18;

	protected Double ext19;

	/**
	 * 字符串值2
	 */
	protected String ext2;

	protected Double ext20;

	/**
	 * 字符串值3
	 */
	protected String ext3;

	/**
	 * 字符串值4
	 */
	protected String ext4;

	/**
	 * 日期值5
	 */
	protected Date ext5;

	/**
	 * 日期值6
	 */
	protected Date ext6;

	/**
	 * 日期值7
	 */
	protected Date ext7;

	/**
	 * 日期值8
	 */
	protected Date ext8;

	/**
	 * 日期值9
	 */
	protected Date ext9;

	protected Long id;

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 类型编号
	 */
	protected Long nodeId;

	/**
	 * 序号
	 */
	protected Integer sort;

	/**
	 * 值
	 */
	protected String value;

	public Dictory() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dictory other = (Dictory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getBlocked() {
		return this.blocked;
	}

	public String getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getExt1() {
		return this.ext1;
	}

	public Date getExt10() {
		return this.ext10;
	}

	public Long getExt11() {
		return this.ext11;
	}

	public Long getExt12() {
		return this.ext12;
	}

	public Long getExt13() {
		return this.ext13;
	}

	public Long getExt14() {
		return this.ext14;
	}

	public Long getExt15() {
		return this.ext15;
	}

	public Double getExt16() {
		return this.ext16;
	}

	public Double getExt17() {
		return this.ext17;
	}

	public Double getExt18() {
		return this.ext18;
	}

	public Double getExt19() {
		return this.ext19;
	}

	public String getExt2() {
		return this.ext2;
	}

	public Double getExt20() {
		return this.ext20;
	}

	public String getExt3() {
		return this.ext3;
	}

	public String getExt4() {
		return this.ext4;
	}

	public Date getExt5() {
		return this.ext5;
	}

	public Date getExt6() {
		return this.ext6;
	}

	public Date getExt7() {
		return this.ext7;
	}

	public Date getExt8() {
		return this.ext8;
	}

	public Date getExt9() {
		return this.ext9;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public Integer getSort() {
		return this.sort;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Dictory jsonToObject(JSONObject jsonObject) {
		return DictoryJsonFactory.jsonToObject(jsonObject);
	}

	public void setBlocked(Integer blocked) {
		this.blocked = blocked;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public void setExt10(Date ext10) {
		this.ext10 = ext10;
	}

	public void setExt11(Long ext11) {
		this.ext11 = ext11;
	}

	public void setExt12(Long ext12) {
		this.ext12 = ext12;
	}

	public void setExt13(Long ext13) {
		this.ext13 = ext13;
	}

	public void setExt14(Long ext14) {
		this.ext14 = ext14;
	}

	public void setExt15(Long ext15) {
		this.ext15 = ext15;
	}

	public void setExt16(Double ext16) {
		this.ext16 = ext16;
	}

	public void setExt17(Double ext17) {
		this.ext17 = ext17;
	}

	public void setExt18(Double ext18) {
		this.ext18 = ext18;
	}

	public void setExt19(Double ext19) {
		this.ext19 = ext19;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public void setExt20(Double ext20) {
		this.ext20 = ext20;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public void setExt5(Date ext5) {
		this.ext5 = ext5;
	}

	public void setExt6(Date ext6) {
		this.ext6 = ext6;
	}

	public void setExt7(Date ext7) {
		this.ext7 = ext7;
	}

	public void setExt8(Date ext8) {
		this.ext8 = ext8;
	}

	public void setExt9(Date ext9) {
		this.ext9 = ext9;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public JSONObject toJsonObject() {
		return DictoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DictoryJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
