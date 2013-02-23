package com.glaf.apps.trip.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TripQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> rowIds;
  	protected String transType;
  	protected String transTypeLike;
  	protected List<String> transTypes;
  	protected Date applyDate;
  	protected Date applyDateGreaterThanOrEqual;
  	protected Date applyDateLessThanOrEqual;
  	protected List<Date> applyDates;
  	protected Date startDate;
  	protected Date startDateGreaterThanOrEqual;
  	protected Date startDateLessThanOrEqual;
  	protected List<Date> startDates;
  	protected Date endDate;
  	protected Date endDateGreaterThanOrEqual;
  	protected Date endDateLessThanOrEqual;
  	protected List<Date> endDates;
  	protected Double days;
  	protected Double daysGreaterThanOrEqual;
  	protected Double daysLessThanOrEqual;
  	protected List<Double> dayss;
  	protected Double money;
  	protected Double moneyGreaterThanOrEqual;
  	protected Double moneyLessThanOrEqual;
  	protected List<Double> moneys;
  	protected String cause;
  	protected String causeLike;
  	protected List<String> causes;
  	protected Date createDate;
  	protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
  	protected List<Date> createDates;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
  	protected String createByName;
  	protected String createByNameLike;
  	protected List<String> createByNames;
  	protected Date updateDate;
  	protected Date updateDateGreaterThanOrEqual;
  	protected Date updateDateLessThanOrEqual;
  	protected List<Date> updateDates;
  	protected Integer locked;
  	protected Integer lockedGreaterThanOrEqual;
  	protected Integer lockedLessThanOrEqual;
  	protected List<Integer> lockeds;
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
  	protected String processName;
  	protected String processNameLike;
  	protected List<String> processNames;

    public TripQuery() {

    }


    public String getTransType(){
        return transType;
    }

    public String getTransTypeLike(){
	    if (transTypeLike != null && transTypeLike.trim().length() > 0) {
		if (!transTypeLike.startsWith("%")) {
		    transTypeLike = "%" + transTypeLike;
		}
		if (!transTypeLike.endsWith("%")) {
		   transTypeLike = transTypeLike + "%";
		}
	    }
	return transTypeLike;
    }

    public List<String> getTransTypes(){
	return transTypes;
    }


    public Date getApplyDate(){
        return applyDate;
    }

    public Date getApplyDateGreaterThanOrEqual(){
        return applyDateGreaterThanOrEqual;
    }

    public Date getApplyDateLessThanOrEqual(){
	return applyDateLessThanOrEqual;
    }

    public List<Date> getApplyDates(){
	return applyDates;
    }


    public Date getStartDate(){
        return startDate;
    }

    public Date getStartDateGreaterThanOrEqual(){
        return startDateGreaterThanOrEqual;
    }

    public Date getStartDateLessThanOrEqual(){
	return startDateLessThanOrEqual;
    }

    public List<Date> getStartDates(){
	return startDates;
    }


    public Date getEndDate(){
        return endDate;
    }

    public Date getEndDateGreaterThanOrEqual(){
        return endDateGreaterThanOrEqual;
    }

    public Date getEndDateLessThanOrEqual(){
	return endDateLessThanOrEqual;
    }

    public List<Date> getEndDates(){
	return endDates;
    }


    public Double getDays(){
        return days;
    }

    public Double getDaysGreaterThanOrEqual(){
        return daysGreaterThanOrEqual;
    }

    public Double getDaysLessThanOrEqual(){
	return daysLessThanOrEqual;
    }

    public List<Double> getDayss(){
	return dayss;
    }


    public Double getMoney(){
        return money;
    }

    public Double getMoneyGreaterThanOrEqual(){
        return moneyGreaterThanOrEqual;
    }

    public Double getMoneyLessThanOrEqual(){
	return moneyLessThanOrEqual;
    }

    public List<Double> getMoneys(){
	return moneys;
    }


    public String getCause(){
        return cause;
    }

    public String getCauseLike(){
	    if (causeLike != null && causeLike.trim().length() > 0) {
		if (!causeLike.startsWith("%")) {
		    causeLike = "%" + causeLike;
		}
		if (!causeLike.endsWith("%")) {
		   causeLike = causeLike + "%";
		}
	    }
	return causeLike;
    }

    public List<String> getCauses(){
	return causes;
    }


    public Date getCreateDate(){
        return createDate;
    }

    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
    }

    public List<Date> getCreateDates(){
	return createDates;
    }


    public String getCreateBy(){
        return createBy;
    }

    public String getCreateByLike(){
	    if (createByLike != null && createByLike.trim().length() > 0) {
		if (!createByLike.startsWith("%")) {
		    createByLike = "%" + createByLike;
		}
		if (!createByLike.endsWith("%")) {
		   createByLike = createByLike + "%";
		}
	    }
	return createByLike;
    }

    public List<String> getCreateBys(){
	return createBys;
    }


    public String getCreateByName(){
        return createByName;
    }

    public String getCreateByNameLike(){
	    if (createByNameLike != null && createByNameLike.trim().length() > 0) {
		if (!createByNameLike.startsWith("%")) {
		    createByNameLike = "%" + createByNameLike;
		}
		if (!createByNameLike.endsWith("%")) {
		   createByNameLike = createByNameLike + "%";
		}
	    }
	return createByNameLike;
    }

    public List<String> getCreateByNames(){
	return createByNames;
    }


    public Date getUpdateDate(){
        return updateDate;
    }

    public Date getUpdateDateGreaterThanOrEqual(){
        return updateDateGreaterThanOrEqual;
    }

    public Date getUpdateDateLessThanOrEqual(){
	return updateDateLessThanOrEqual;
    }

    public List<Date> getUpdateDates(){
	return updateDates;
    }


    public Integer getLocked(){
        return locked;
    }

    public Integer getLockedGreaterThanOrEqual(){
        return lockedGreaterThanOrEqual;
    }

    public Integer getLockedLessThanOrEqual(){
	return lockedLessThanOrEqual;
    }

    public List<Integer> getLockeds(){
	return lockeds;
    }


    public Integer getDeleteFlag(){
        return deleteFlag;
    }

    public Integer getDeleteFlagGreaterThanOrEqual(){
        return deleteFlagGreaterThanOrEqual;
    }

    public Integer getDeleteFlagLessThanOrEqual(){
	return deleteFlagLessThanOrEqual;
    }

    public List<Integer> getDeleteFlags(){
	return deleteFlags;
    }


    public Integer getStatus(){
        return status;
    }

    public Integer getStatusGreaterThanOrEqual(){
        return statusGreaterThanOrEqual;
    }

    public Integer getStatusLessThanOrEqual(){
	return statusLessThanOrEqual;
    }

    public List<Integer> getStatuss(){
	return statuss;
    }


    public String getProcessName(){
        return processName;
    }

    public String getProcessNameLike(){
	    if (processNameLike != null && processNameLike.trim().length() > 0) {
		if (!processNameLike.startsWith("%")) {
		    processNameLike = "%" + processNameLike;
		}
		if (!processNameLike.endsWith("%")) {
		   processNameLike = processNameLike + "%";
		}
	    }
	return processNameLike;
    }

    public List<String> getProcessNames(){
	return processNames;
    }


 

    public void setTransType(String transType){
        this.transType = transType;
    }

    public void setTransTypeLike( String transTypeLike){
	this.transTypeLike = transTypeLike;
    }

    public void setTransTypes(List<String> transTypes){
        this.transTypes = transTypes;
    }


    public void setApplyDate(Date applyDate){
        this.applyDate = applyDate;
    }

    public void setApplyDateGreaterThanOrEqual(Date applyDateGreaterThanOrEqual){
        this.applyDateGreaterThanOrEqual = applyDateGreaterThanOrEqual;
    }

    public void setApplyDateLessThanOrEqual(Date applyDateLessThanOrEqual){
	this.applyDateLessThanOrEqual = applyDateLessThanOrEqual;
    }

    public void setApplyDates(List<Date> applyDates){
        this.applyDates = applyDates;
    }


    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }

    public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual){
        this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
    }

    public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual){
	this.startDateLessThanOrEqual = startDateLessThanOrEqual;
    }

    public void setStartDates(List<Date> startDates){
        this.startDates = startDates;
    }


    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual){
        this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
    }

    public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual){
	this.endDateLessThanOrEqual = endDateLessThanOrEqual;
    }

    public void setEndDates(List<Date> endDates){
        this.endDates = endDates;
    }


    public void setDays(Double days){
        this.days = days;
    }

    public void setDaysGreaterThanOrEqual(Double daysGreaterThanOrEqual){
        this.daysGreaterThanOrEqual = daysGreaterThanOrEqual;
    }

    public void setDaysLessThanOrEqual(Double daysLessThanOrEqual){
	this.daysLessThanOrEqual = daysLessThanOrEqual;
    }

    public void setDayss(List<Double> dayss){
        this.dayss = dayss;
    }


    public void setMoney(Double money){
        this.money = money;
    }

    public void setMoneyGreaterThanOrEqual(Double moneyGreaterThanOrEqual){
        this.moneyGreaterThanOrEqual = moneyGreaterThanOrEqual;
    }

    public void setMoneyLessThanOrEqual(Double moneyLessThanOrEqual){
	this.moneyLessThanOrEqual = moneyLessThanOrEqual;
    }

    public void setMoneys(List<Double> moneys){
        this.moneys = moneys;
    }


    public void setCause(String cause){
        this.cause = cause;
    }

    public void setCauseLike( String causeLike){
	this.causeLike = causeLike;
    }

    public void setCauses(List<String> causes){
        this.causes = causes;
    }


    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }

    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
    }

    public void setCreateDates(List<Date> createDates){
        this.createDates = createDates;
    }


    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }

    public void setCreateByLike( String createByLike){
	this.createByLike = createByLike;
    }

    public void setCreateBys(List<String> createBys){
        this.createBys = createBys;
    }


    public void setCreateByName(String createByName){
        this.createByName = createByName;
    }

    public void setCreateByNameLike( String createByNameLike){
	this.createByNameLike = createByNameLike;
    }

    public void setCreateByNames(List<String> createByNames){
        this.createByNames = createByNames;
    }


    public void setUpdateDate(Date updateDate){
        this.updateDate = updateDate;
    }

    public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
        this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
    }

    public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
	this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
    }

    public void setUpdateDates(List<Date> updateDates){
        this.updateDates = updateDates;
    }


    public void setLocked(Integer locked){
        this.locked = locked;
    }

    public void setLockedGreaterThanOrEqual(Integer lockedGreaterThanOrEqual){
        this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
    }

    public void setLockedLessThanOrEqual(Integer lockedLessThanOrEqual){
	this.lockedLessThanOrEqual = lockedLessThanOrEqual;
    }

    public void setLockeds(List<Integer> lockeds){
        this.lockeds = lockeds;
    }


    public void setDeleteFlag(Integer deleteFlag){
        this.deleteFlag = deleteFlag;
    }

    public void setDeleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
        this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
    }

    public void setDeleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
	this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
    }

    public void setDeleteFlags(List<Integer> deleteFlags){
        this.deleteFlags = deleteFlags;
    }


    public void setStatus(Integer status){
        this.status = status;
    }

    public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
        this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
    }

    public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual){
	this.statusLessThanOrEqual = statusLessThanOrEqual;
    }

    public void setStatuss(List<Integer> statuss){
        this.statuss = statuss;
    }


    public void setProcessName(String processName){
        this.processName = processName;
    }

    public void setProcessNameLike( String processNameLike){
	this.processNameLike = processNameLike;
    }

    public void setProcessNames(List<String> processNames){
        this.processNames = processNames;
    }




    public TripQuery transType(String transType){
	if (transType == null) {
	    throw new RuntimeException("transType is null");
        }         
	this.transType = transType;
	return this;
    }

    public TripQuery transTypeLike( String transTypeLike){
        if (transTypeLike == null) {
            throw new RuntimeException("transType is null");
        }
        this.transTypeLike = transTypeLike;
        return this;
    }

    public TripQuery transTypes(List<String> transTypes){
        if (transTypes == null) {
            throw new RuntimeException("transTypes is empty ");
        }
        this.transTypes = transTypes;
        return this;
    }


    public TripQuery applyDate(Date applyDate){
	if (applyDate == null) {
            throw new RuntimeException("applyDate is null");
        }         
	this.applyDate = applyDate;
	return this;
    }

    public TripQuery applyDateGreaterThanOrEqual(Date applyDateGreaterThanOrEqual){
	if (applyDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("applyDate is null");
        }         
	this.applyDateGreaterThanOrEqual = applyDateGreaterThanOrEqual;
        return this;
    }

    public TripQuery applyDateLessThanOrEqual(Date applyDateLessThanOrEqual){
        if (applyDateLessThanOrEqual == null) {
            throw new RuntimeException("applyDate is null");
        }
        this.applyDateLessThanOrEqual = applyDateLessThanOrEqual;
        return this;
    }

    public TripQuery applyDates(List<Date> applyDates){
        if (applyDates == null) {
            throw new RuntimeException("applyDates is empty ");
        }
        this.applyDates = applyDates;
        return this;
    }


    public TripQuery startDate(Date startDate){
	if (startDate == null) {
            throw new RuntimeException("startDate is null");
        }         
	this.startDate = startDate;
	return this;
    }

    public TripQuery startDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual){
	if (startDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("startDate is null");
        }         
	this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
        return this;
    }

    public TripQuery startDateLessThanOrEqual(Date startDateLessThanOrEqual){
        if (startDateLessThanOrEqual == null) {
            throw new RuntimeException("startDate is null");
        }
        this.startDateLessThanOrEqual = startDateLessThanOrEqual;
        return this;
    }

    public TripQuery startDates(List<Date> startDates){
        if (startDates == null) {
            throw new RuntimeException("startDates is empty ");
        }
        this.startDates = startDates;
        return this;
    }


    public TripQuery endDate(Date endDate){
	if (endDate == null) {
            throw new RuntimeException("endDate is null");
        }         
	this.endDate = endDate;
	return this;
    }

    public TripQuery endDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual){
	if (endDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("endDate is null");
        }         
	this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
        return this;
    }

    public TripQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual){
        if (endDateLessThanOrEqual == null) {
            throw new RuntimeException("endDate is null");
        }
        this.endDateLessThanOrEqual = endDateLessThanOrEqual;
        return this;
    }

    public TripQuery endDates(List<Date> endDates){
        if (endDates == null) {
            throw new RuntimeException("endDates is empty ");
        }
        this.endDates = endDates;
        return this;
    }


    public TripQuery days(Double days){
	if (days == null) {
            throw new RuntimeException("days is null");
        }         
	this.days = days;
	return this;
    }

    public TripQuery daysGreaterThanOrEqual(Double daysGreaterThanOrEqual){
	if (daysGreaterThanOrEqual == null) {
	    throw new RuntimeException("days is null");
        }         
	this.daysGreaterThanOrEqual = daysGreaterThanOrEqual;
        return this;
    }

    public TripQuery daysLessThanOrEqual(Double daysLessThanOrEqual){
        if (daysLessThanOrEqual == null) {
            throw new RuntimeException("days is null");
        }
        this.daysLessThanOrEqual = daysLessThanOrEqual;
        return this;
    }

    public TripQuery dayss(List<Double> dayss){
        if (dayss == null) {
            throw new RuntimeException("dayss is empty ");
        }
        this.dayss = dayss;
        return this;
    }


    public TripQuery money(Double money){
	if (money == null) {
            throw new RuntimeException("money is null");
        }         
	this.money = money;
	return this;
    }

    public TripQuery moneyGreaterThanOrEqual(Double moneyGreaterThanOrEqual){
	if (moneyGreaterThanOrEqual == null) {
	    throw new RuntimeException("money is null");
        }         
	this.moneyGreaterThanOrEqual = moneyGreaterThanOrEqual;
        return this;
    }

    public TripQuery moneyLessThanOrEqual(Double moneyLessThanOrEqual){
        if (moneyLessThanOrEqual == null) {
            throw new RuntimeException("money is null");
        }
        this.moneyLessThanOrEqual = moneyLessThanOrEqual;
        return this;
    }

    public TripQuery moneys(List<Double> moneys){
        if (moneys == null) {
            throw new RuntimeException("moneys is empty ");
        }
        this.moneys = moneys;
        return this;
    }


    public TripQuery cause(String cause){
	if (cause == null) {
	    throw new RuntimeException("cause is null");
        }         
	this.cause = cause;
	return this;
    }

    public TripQuery causeLike( String causeLike){
        if (causeLike == null) {
            throw new RuntimeException("cause is null");
        }
        this.causeLike = causeLike;
        return this;
    }

    public TripQuery causes(List<String> causes){
        if (causes == null) {
            throw new RuntimeException("causes is empty ");
        }
        this.causes = causes;
        return this;
    }


    public TripQuery createDate(Date createDate){
	if (createDate == null) {
            throw new RuntimeException("createDate is null");
        }         
	this.createDate = createDate;
	return this;
    }

    public TripQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public TripQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }

    public TripQuery createDates(List<Date> createDates){
        if (createDates == null) {
            throw new RuntimeException("createDates is empty ");
        }
        this.createDates = createDates;
        return this;
    }


    public TripQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public TripQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public TripQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }


    public TripQuery createByName(String createByName){
	if (createByName == null) {
	    throw new RuntimeException("createByName is null");
        }         
	this.createByName = createByName;
	return this;
    }

    public TripQuery createByNameLike( String createByNameLike){
        if (createByNameLike == null) {
            throw new RuntimeException("createByName is null");
        }
        this.createByNameLike = createByNameLike;
        return this;
    }

    public TripQuery createByNames(List<String> createByNames){
        if (createByNames == null) {
            throw new RuntimeException("createByNames is empty ");
        }
        this.createByNames = createByNames;
        return this;
    }


    public TripQuery updateDate(Date updateDate){
	if (updateDate == null) {
            throw new RuntimeException("updateDate is null");
        }         
	this.updateDate = updateDate;
	return this;
    }

    public TripQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public TripQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }

    public TripQuery updateDates(List<Date> updateDates){
        if (updateDates == null) {
            throw new RuntimeException("updateDates is empty ");
        }
        this.updateDates = updateDates;
        return this;
    }


    public TripQuery locked(Integer locked){
	if (locked == null) {
            throw new RuntimeException("locked is null");
        }         
	this.locked = locked;
	return this;
    }

    public TripQuery lockedGreaterThanOrEqual(Integer lockedGreaterThanOrEqual){
	if (lockedGreaterThanOrEqual == null) {
	    throw new RuntimeException("locked is null");
        }         
	this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
        return this;
    }

    public TripQuery lockedLessThanOrEqual(Integer lockedLessThanOrEqual){
        if (lockedLessThanOrEqual == null) {
            throw new RuntimeException("locked is null");
        }
        this.lockedLessThanOrEqual = lockedLessThanOrEqual;
        return this;
    }

    public TripQuery lockeds(List<Integer> lockeds){
        if (lockeds == null) {
            throw new RuntimeException("lockeds is empty ");
        }
        this.lockeds = lockeds;
        return this;
    }


    public TripQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public TripQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public TripQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public TripQuery deleteFlags(List<Integer> deleteFlags){
        if (deleteFlags == null) {
            throw new RuntimeException("deleteFlags is empty ");
        }
        this.deleteFlags = deleteFlags;
        return this;
    }


    public TripQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public TripQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public TripQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public TripQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public TripQuery processName(String processName){
	if (processName == null) {
	    throw new RuntimeException("processName is null");
        }         
	this.processName = processName;
	return this;
    }

    public TripQuery processNameLike( String processNameLike){
        if (processNameLike == null) {
            throw new RuntimeException("processName is null");
        }
        this.processNameLike = processNameLike;
        return this;
    }

    public TripQuery processNames(List<String> processNames){
        if (processNames == null) {
            throw new RuntimeException("processNames is empty ");
        }
        this.processNames = processNames;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("transType".equals(sortColumn)) {
                orderBy = "E.TRANSTYPE_" + a_x;
            } 

            if ("applyDate".equals(sortColumn)) {
                orderBy = "E.APPLYDATE_" + a_x;
            } 

            if ("startDate".equals(sortColumn)) {
                orderBy = "E.STARTDATE_" + a_x;
            } 

            if ("endDate".equals(sortColumn)) {
                orderBy = "E.ENDDATE_" + a_x;
            } 

            if ("days".equals(sortColumn)) {
                orderBy = "E.DAYS_" + a_x;
            } 

            if ("money".equals(sortColumn)) {
                orderBy = "E.MONEY_" + a_x;
            } 

            if ("cause".equals(sortColumn)) {
                orderBy = "E.CAUSE_" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATEDATE_" + a_x;
            } 

            if ("locked".equals(sortColumn)) {
                orderBy = "E.LOCKED_" + a_x;
            } 

            if ("deleteFlag".equals(sortColumn)) {
                orderBy = "E.DELETEFLAG_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
            } 

            if ("processName".equals(sortColumn)) {
                orderBy = "E.PROCESSNAME_" + a_x;
            } 

            if ("processInstanceId".equals(sortColumn)) {
                orderBy = "E.PROCESSINSTANCEID_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("transType", "TRANSTYPE_");
        addColumn("applyDate", "APPLYDATE_");
        addColumn("startDate", "STARTDATE_");
        addColumn("endDate", "ENDDATE_");
        addColumn("days", "DAYS_");
        addColumn("money", "MONEY_");
        addColumn("cause", "CAUSE_");
        addColumn("createDate", "CREATEDATE_");
        addColumn("createBy", "CREATEBY_");
        addColumn("updateDate", "UPDATEDATE_");
        addColumn("locked", "LOCKED_");
        addColumn("deleteFlag", "DELETEFLAG_");
        addColumn("status", "STATUS_");
        addColumn("processName", "PROCESSNAME_");
        addColumn("processInstanceId", "PROCESSINSTANCEID_");
    }

}