package com.glaf.cms.fullcalendar.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.cms.fullcalendar.util.*;

@Entity
@Table(name = "CMS_FULLCALENDAR")
public class FullCalendar implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected Long id;

	@Column(name = "title", length = 500)
	protected String title;

	@Column(name = "content", length = 1000)
	protected String content;

	@Column(name = "address", length = 500)
	protected String address;

	@Column(name = "remark", length = 1000)
	protected String remark;

	@Column(name = "shareFlag")
	protected Integer shareFlag;

	@Column(name = "status")
	protected Integer status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateStart")
	protected Date dateStart;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateEnd")
	protected Date dateEnd;

	@Column(name = "ext1", length = 500)
	protected String ext1;

	@Column(name = "ext2", length = 500)
	protected String ext2;

	@Column(name = "createBy", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	protected Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate")
	protected Date updateDate;

	@Column(name = "updateBy", length = 50)
	protected String updateBy;

	@Transient
	protected String createByName;

	public FullCalendar() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FullCalendar other = (FullCalendar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAddress() {
		return this.address;
	}

	public String getContent() {
		return this.content;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getCreateByName() {
		return createByName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Date getDateEnd() {
		return this.dateEnd;
	}

	public Date getDateStart() {
		return this.dateStart;
	}

	public String getExt1() {
		return this.ext1;
	}

	public String getExt2() {
		return this.ext2;
	}

	public Long getId() {
		return this.id;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getShareFlag() {
		return shareFlag;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FullCalendar jsonToObject(JSONObject jsonObject) {
		return FullCalendarJsonFactory.jsonToObject(jsonObject);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setShareFlag(Integer shareFlag) {
		this.shareFlag = shareFlag;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public JSONObject toJsonObject() {
		return FullCalendarJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FullCalendarJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
