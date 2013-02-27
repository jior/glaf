package com.glaf.apps.trip.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "X_APP_TRIP")
public class Trip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 交通工具
	 */
	@Column(name = "TRANSTYPE_")
	protected String transType;

	@Transient
	protected String transTypeName;

	/**
	 * 申请日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLYDATE_")
	protected Date applyDate;

	/**
	 * 开始日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE_")
	protected Date startDate;

	/**
	 * 结束日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE_")
	protected Date endDate;

	/**
	 * 天数
	 */
	@Column(name = "DAYS_")
	protected Double days;

	/**
	 * 费用
	 */
	@Column(name = "MONEY_")
	protected Double money;

	/**
	 * 事由
	 */
	@Column(name = "CAUSE_")
	protected String cause;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_")
	protected String createBy;

	/**
	 * 创建人
	 */
	@Transient
	protected String createByName;

	/**
	 * 修改日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected Integer locked;

	/**
	 * 删除标记
	 */
	@Column(name = "DELETEFLAG_")
	protected Integer deleteFlag;

	/**
	 * 状态
	 */
	@Column(name = "STATUS_")
	protected Integer status;

	/**
	 * 流程名称
	 */
	@Column(name = "PROCESSNAME_")
	protected String processName;

	/**
	 * 流程实例编号
	 */
	@Column(name = "PROCESSINSTANCEID_")
	protected String processInstanceId;

	public Trip() {

	}

	public String getId() {
		return this.id;
	}

	public String getTransType() {
		return this.transType;
	}

	public String getTransTypeName() {
		return this.transTypeName;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public Double getDays() {
		return this.days;
	}

	public Double getMoney() {
		return this.money;
	}

	public String getCause() {
		return this.cause;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getCreateByName() {
		return this.createByName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public Integer getLocked() {
		return this.locked;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getProcessName() {
		return this.processName;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setDays(Double days) {
		this.days = days;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trip other = (Trip) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Trip jsonToObject(JSONObject jsonObject) {
		Trip model = new Trip();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("transType")) {
			model.setTransType(jsonObject.getString("transType"));
		}
		if (jsonObject.containsKey("applyDate")) {
			model.setApplyDate(jsonObject.getDate("applyDate"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("days")) {
			model.setDays(jsonObject.getDouble("days"));
		}
		if (jsonObject.containsKey("money")) {
			model.setMoney(jsonObject.getDouble("money"));
		}
		if (jsonObject.containsKey("cause")) {
			model.setCause(jsonObject.getString("cause"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createByName")) {
			model.setCreateByName(jsonObject.getString("createByName"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("processInstanceId")) {
			model.setProcessInstanceId(jsonObject
					.getString("processInstanceId"));
		}
		return model;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (transType != null) {
			jsonObject.put("transType", transType);
		}
		if (applyDate != null) {
			jsonObject.put("applyDate", DateUtils.getDate(applyDate));
			jsonObject.put("applyDate_date", DateUtils.getDate(applyDate));
			jsonObject.put("applyDate_datetime",
					DateUtils.getDateTime(applyDate));
		}
		if (startDate != null) {
			jsonObject.put("startDate", DateUtils.getDate(startDate));
			jsonObject.put("startDate_date", DateUtils.getDate(startDate));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(startDate));
		}
		if (endDate != null) {
			jsonObject.put("endDate", DateUtils.getDate(endDate));
			jsonObject.put("endDate_date", DateUtils.getDate(endDate));
			jsonObject.put("endDate_datetime", DateUtils.getDateTime(endDate));
		}
		jsonObject.put("days", days);
		jsonObject.put("money", money);
		if (cause != null) {
			jsonObject.put("cause", cause);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (createByName != null) {
			jsonObject.put("createByName", createByName);
		}
		if (updateDate != null) {
			jsonObject.put("updateDate", DateUtils.getDate(updateDate));
			jsonObject.put("updateDate_date", DateUtils.getDate(updateDate));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(updateDate));
		}
		jsonObject.put("locked", locked);
		jsonObject.put("deleteFlag", deleteFlag);
		jsonObject.put("status", status);
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (processInstanceId != null) {
			jsonObject.put("processInstanceId", processInstanceId);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (transType != null) {
			jsonObject.put("transType", transType);
		}
		if (applyDate != null) {
			jsonObject.put("applyDate", DateUtils.getDate(applyDate));
			jsonObject.put("applyDate_date", DateUtils.getDate(applyDate));
			jsonObject.put("applyDate_datetime",
					DateUtils.getDateTime(applyDate));
		}
		if (startDate != null) {
			jsonObject.put("startDate", DateUtils.getDate(startDate));
			jsonObject.put("startDate_date", DateUtils.getDate(startDate));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(startDate));
		}
		if (endDate != null) {
			jsonObject.put("endDate", DateUtils.getDate(endDate));
			jsonObject.put("endDate_date", DateUtils.getDate(endDate));
			jsonObject.put("endDate_datetime", DateUtils.getDateTime(endDate));
		}
		jsonObject.put("days", days);
		jsonObject.put("money", money);
		if (cause != null) {
			jsonObject.put("cause", cause);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (createByName != null) {
			jsonObject.put("createByName", createByName);
		}
		if (updateDate != null) {
			jsonObject.put("updateDate", DateUtils.getDate(updateDate));
			jsonObject.put("updateDate_date", DateUtils.getDate(updateDate));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(updateDate));
		}
		jsonObject.put("locked", locked);
		jsonObject.put("deleteFlag", deleteFlag);
		jsonObject.put("status", status);
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (processInstanceId != null) {
			jsonObject.put("processInstanceId", processInstanceId);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
