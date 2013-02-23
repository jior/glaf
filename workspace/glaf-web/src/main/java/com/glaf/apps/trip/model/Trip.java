package com.glaf.apps.trip.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
 
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.json.*;

import com.glaf.core.base.*;
import com.glaf.base.utils.DateTools;

@Entity
@Table(name = "X_APP_TRIP")
public class Trip implements Serializable, JsonReadable {
	private static final long serialVersionUID = 1L;

        @Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	

        /**
        *交通工具
        */
        @Column(name = "TRANSTYPE_")
        protected String transType;

        @Transient
        protected String transTypeName;

        /**
        *申请日期
        */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "APPLYDATE_")
        protected Date applyDate;

        /**
        *开始日期
        */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "STARTDATE_")
        protected Date startDate;

        /**
        *结束日期
        */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "ENDDATE_")
        protected Date endDate;

        /**
        *天数
        */
        @Column(name = "DAYS_")
        protected Double days;

        /**
        *费用
        */
        @Column(name = "MONEY_")
        protected Double money;

        /**
        *事由
        */
        @Column(name = "CAUSE_")
        protected String cause;

        /**
        *创建日期
        */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE_")
        protected Date createDate;

        /**
        *创建人
        */
        @Column(name = "CREATEBY_")
        protected String createBy;

        /**
        *创建人
        */
        @Transient
        protected String createByName;

        /**
        *修改日期
        */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE_")
        protected Date updateDate;

        /**
        *是否锁定
        */
        @Column(name = "LOCKED_")
        protected Integer locked;

        /**
        *删除标记
        */
        @Column(name = "DELETEFLAG_")
        protected Integer deleteFlag;

        /**
        *状态
        */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
        *流程名称
        */
        @Column(name = "PROCESSNAME_")
        protected String processName;

        /**
        *流程实例编号
        */
        @Column(name = "PROCESSINSTANCEID_")
        protected String processInstanceId;

	public Trip() {

	}

	public String getId(){
	    return this.id;
	}

	

        public String getTransType(){
            return this.transType;
        }

        public String getTransTypeName(){
            return this.transTypeName;
        }

        public Date getApplyDate(){
            return this.applyDate;
        }

        public Date getStartDate(){
            return this.startDate;
        }

        public Date getEndDate(){
            return this.endDate;
        }

        public Double getDays(){
            return this.days;
        }

        public Double getMoney(){
            return this.money;
        }

        public String getCause(){
            return this.cause;
        }

        public Date getCreateDate(){
            return this.createDate;
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

        public Integer getLocked(){
            return this.locked;
        }

        public Integer getDeleteFlag(){
            return this.deleteFlag;
        }

        public Integer getStatus(){
            return this.status;
        }

        public String getProcessName(){
            return this.processName;
        }

        public String getProcessInstanceId(){
            return this.processInstanceId;
        }


	public void setId(String id) {
	    this.id = id; 
	}

	

        public void  setTransType(String transType){
              this.transType=transType;
        }

        public void  setTransTypeName(String transTypeName){
              this.transTypeName = transTypeName;
        }

        public void  setApplyDate(Date applyDate){
              this.applyDate=applyDate;
        }

        public void  setStartDate(Date startDate){
              this.startDate=startDate;
        }

        public void  setEndDate(Date endDate){
              this.endDate=endDate;
        }

        public void  setDays(Double days){
              this.days=days;
        }

        public void  setMoney(Double money){
              this.money=money;
        }

        public void  setCause(String cause){
              this.cause=cause;
        }

        public void  setCreateDate(Date createDate){
              this.createDate=createDate;
        }

        public void  setCreateBy(String createBy){
              this.createBy=createBy;
        }

        public void  setCreateByName(String createByName){
              this.createByName=createByName;
        }

        public void  setUpdateDate(Date updateDate){
              this.updateDate=updateDate;
        }

        public void  setLocked(Integer locked){
              this.locked=locked;
        }

        public void  setDeleteFlag(Integer deleteFlag){
              this.deleteFlag=deleteFlag;
        }

        public void  setStatus(Integer status){
              this.status=status;
        }

        public void  setProcessName(String processName){
              this.processName=processName;
        }

        public void  setProcessInstanceId(String processInstanceId){
              this.processInstanceId=processInstanceId;
        }

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public JSONObject toJsonObject() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
                if (transType != null) {
		    jsonObject.put("transType", transType);
		} 
	        jsonObject.put("applyDate", DateTools.getDateTime(applyDate));
		jsonObject.put("applyDate_date", DateTools.getDate(applyDate));
		jsonObject.put("applyDate_datetime", DateTools.getDateTime(applyDate));
	        jsonObject.put("startDate", DateTools.getDateTime(startDate));
		jsonObject.put("startDate_date", DateTools.getDate(startDate));
		jsonObject.put("startDate_datetime", DateTools.getDateTime(startDate));
	        jsonObject.put("endDate", DateTools.getDateTime(endDate));
		jsonObject.put("endDate_date", DateTools.getDate(endDate));
		jsonObject.put("endDate_datetime", DateTools.getDateTime(endDate));
                jsonObject.put("days", days);
                jsonObject.put("money", money);
                if (cause != null) {
		    jsonObject.put("cause", cause);
		} 
	        jsonObject.put("createDate", DateTools.getDateTime(createDate));
		jsonObject.put("createDate_date", DateTools.getDate(createDate));
		jsonObject.put("createDate_datetime", DateTools.getDateTime(createDate));
                if (createBy != null) {
		    jsonObject.put("createBy", createBy);
		} 
                if (createByName != null) {
		    jsonObject.put("createByName", createByName);
		} 
	        jsonObject.put("updateDate", DateTools.getDateTime(updateDate));
		jsonObject.put("updateDate_date", DateTools.getDate(updateDate));
		jsonObject.put("updateDate_datetime", DateTools.getDateTime(updateDate));
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

}
