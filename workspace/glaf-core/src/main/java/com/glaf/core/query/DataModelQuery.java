package com.glaf.core.query;

import java.util.List;

public class DataModelQuery extends DataQuery {

	private static final long serialVersionUID = 1L;

	protected List<String> appActorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();

	protected Boolean processInstanceIsNotNull;

	protected Boolean processInstanceIsNull;

	protected String subjectLike;

	protected String tableName;

	public DataModelQuery() {

	}

	public List<String> getAppActorIds() {
		return appActorIds;
	}

	public Boolean getProcessInstanceIsNotNull() {
		return processInstanceIsNotNull;
	}

	public Boolean getProcessInstanceIsNull() {
		return processInstanceIsNull;
	}

	public String getSubjectLike() {
		return subjectLike;
	}

	public String getTableName() {
		return tableName;
	}

	public void setAppActorIds(List<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setProcessInstanceIsNotNull(Boolean processInstanceIsNotNull) {
		this.processInstanceIsNotNull = processInstanceIsNotNull;
	}

	public void setProcessInstanceIsNull(Boolean processInstanceIsNull) {
		this.processInstanceIsNull = processInstanceIsNull;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
