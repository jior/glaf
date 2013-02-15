/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
	 * 人员编号
	 */
	protected String actorId;

	/**
	 * 人员类型 <br>
	 */
	protected int actorType;

	/**
	 * 团队类型
	 */
	protected String teamType;

	/**
	 * 被观察者的用户编号
	 */
	protected String observerId;

	/**
	 * 查询语句
	 */
	protected StringBuffer queryBuffer = new StringBuffer();

	/**
	 * 角色编号集合
	 */
	protected Collection roleIds = new HashSet();

	/**
	 * 查询参数
	 */
	protected Map queryParams = new HashMap();

	/**
	 * 排序字段
	 */
	protected List orderByFields = new ArrayList();

	/**
	 * 分组字段
	 */
	protected List groupByFields = new ArrayList();

	/**
	 * 比较条件
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
