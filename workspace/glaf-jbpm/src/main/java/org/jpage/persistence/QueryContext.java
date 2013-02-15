/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package org.jpage.persistence;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class QueryContext {

	/**
	 * ��Ա���
	 */
	protected String actorId;

	/**
	 * ��Ա���� <br>
	 */
	protected int actorType;

	/**
	 * �Ŷ�����
	 */
	protected String teamType;

	/**
	 * ���۲��ߵ��û����
	 */
	protected String observerId;

	/**
	 * ��ѯ���
	 */
	protected StringBuffer queryBuffer = new StringBuffer();

	/**
	 * ��ɫ��ż���
	 */
	protected Collection roleIds = new HashSet();

	/**
	 * ��ѯ����
	 */
	protected Map queryParams = new HashMap();

	/**
	 * �����ֶ�
	 */
	protected List orderByFields = new ArrayList();

	/**
	 * �����ֶ�
	 */
	protected List groupByFields = new ArrayList();

	/**
	 * �Ƚ�����
	 */
	protected List havingFields = new ArrayList();

	public QueryContext() {

	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public int getActorType() {
		return actorType;
	}

	public void setActorType(int actorType) {
		this.actorType = actorType;
	}

	public String getTeamType() {
		return teamType;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

	public String getObserverId() {
		return observerId;
	}

	public void setObserverId(String observerId) {
		this.observerId = observerId;
	}

	public Collection getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Collection roleIds) {
		this.roleIds = roleIds;
	}

	public StringBuffer getQueryBuffer() {
		return queryBuffer;
	}

	public void setQueryBuffer(StringBuffer queryBuffer) {
		this.queryBuffer = queryBuffer;
	}

	public Map getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map queryParams) {
		this.queryParams = queryParams;
	}

	public List getOrderByFields() {
		return orderByFields;
	}

	public void setOrderByFields(List orderByFields) {
		this.orderByFields = orderByFields;
	}

	public List getGroupByFields() {
		return groupByFields;
	}

	public void setGroupByFields(List groupByFields) {
		this.groupByFields = groupByFields;
	}

	public List getHavingFields() {
		return havingFields;
	}

	public void setHavingFields(List havingFields) {
		this.havingFields = havingFields;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
