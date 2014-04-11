package com.glaf.apps.trip.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.apps.trip.util.*;

@Entity
@Table(name = "X_APP_TRIP")
public class Trip implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        @Column(name = "wfStatus")
        protected Integer wfStatus;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "STARTDATE_")	
        protected Date startDate;

        @Column(name = "CAUSE_", length=500) 
        protected String cause;

        @Column(name = "status")
        protected Integer status;

        @Column(name = "processName", length=100) 
        protected String processName;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "ENDDATE_")	
        protected Date endDate;

        @Column(name = "CREATEBY_", length=50) 
        protected String createBy;

        @Transient
        protected String createByName;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE_")	
        protected Date updateDate;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "APPLYDATE_")	
        protected Date applyDate;

        @Column(name = "TRANSTYPE_", length=50) 
        protected String transType;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "wfStartDate")	
        protected Date wfStartDate;

        @Column(name = "processInstanceId")	 
        protected Long processInstanceId;

        @Column(name = "DAYS_")	 
        protected Double days;

        @Column(name = "MONEY_")	 
        protected Double money;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "wfEndDate")	
        protected Date wfEndDate;

        @Column(name = "LOCKED_")
        protected Integer locked;

        @Column(name = "DELETEFLAG_")
        protected Integer deleteFlag;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE_")	
        protected Date createDate;


         
	public Trip() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


	public Integer getWfStatus(){
	    return this.wfStatus;
	}

	public Date getStartDate(){
	    return this.startDate;
	}

	public String getCause(){
	    return this.cause;
	}

	public Integer getStatus(){
	    return this.status;
	}

	public String getProcessName(){
	    return this.processName;
	}

	public Date getEndDate(){
	    return this.endDate;
	}

	public String getCreateBy(){
	    return this.createBy;
	}

	public String getCreateByName(){
	    return this.createByName;
	}

	public Date getUpdateDate(){
	    return this.updateDate;
	}

	public Date getApplyDate(){
	    return this.applyDate;
	}

	public String getTransType(){
	    return this.transType;
	}

	public Date getWfStartDate(){
	    return this.wfStartDate;
	}

	public Long getProcessInstanceId(){
	    return this.processInstanceId;
	}

	public Double getDays(){
	    return this.days;
	}

	public Double getMoney(){
	    return this.money;
	}

	public Date getWfEndDate(){
	    return this.wfEndDate;
	}

	public Integer getLocked(){
	    return this.locked;
	}

	public Integer getDeleteFlag(){
	    return this.deleteFlag;
	}

	public Date getCreateDate(){
	    return this.createDate;
	}



	public void setWfStatus(Integer wfStatus) {
	    this.wfStatus = wfStatus; 
	}	 

	public void setStartDate(Date startDate) {
	    this.startDate = startDate; 
	}	 

	public void setCause(String cause) {
	    this.cause = cause; 
	}	 

	public void setStatus(Integer status) {
	    this.status = status; 
	}	 

	public void setProcessName(String processName) {
	    this.processName = processName; 
	}	 

	public void setEndDate(Date endDate) {
	    this.endDate = endDate; 
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

	public void setApplyDate(Date applyDate) {
	    this.applyDate = applyDate; 
	}	 

	public void setTransType(String transType) {
	    this.transType = transType; 
	}	 

	public void setWfStartDate(Date wfStartDate) {
	    this.wfStartDate = wfStartDate; 
	}	 

	public void setProcessInstanceId(Long processInstanceId) {
	    this.processInstanceId = processInstanceId; 
	}	 

	public void setDays(Double days) {
	    this.days = days; 
	}	 

	public void setMoney(Double money) {
	    this.money = money; 
	}	 

	public void setWfEndDate(Date wfEndDate) {
	    this.wfEndDate = wfEndDate; 
	}	 

	public void setLocked(Integer locked) {
	    this.locked = locked; 
	}	 

	public void setDeleteFlag(Integer deleteFlag) {
	    this.deleteFlag = deleteFlag; 
	}	 

	public void setCreateDate(Date createDate) {
	    this.createDate = createDate; 
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
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}


	public Trip jsonToObject(JSONObject jsonObject) {
            return TripJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return TripJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return TripJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
