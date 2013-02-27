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

package com.glaf.base.modules.todo.model;

import java.util.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
 

public class ToDo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private long id;

	/**
	 * 代码
	 */
	private String code;

	/**
	 * 应用编号
	 */
	private int appId = 0;

	/**
	 * 模块编号
	 */
	private int moduleId = 0;

	/**
	 * 模块名称
	 */
	private String moduleName;

	/**
	 * 参与者编号或角色代码
	 */
	private String actorId;

	/**
	 * 角色编号
	 */
	private long roleId = 0;

	/**
	 * 角色代码
	 */
	private String roleCode;

	/**
	 * 部门编号
	 */
	private long deptId = 0;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 报警
	 */
	private String alarm;

	/**
	 * 新闻
	 */
	private String news;

	/**
	 * 开始事件
	 */
	private String eventFrom;

	/**
	 * 结束事件
	 */
	private String eventTo;

	/**
	 * 限制天数
	 */
	private int limitDay = 2;

	/**
	 * a 小时
	 */
	private int xa = 0;

	/**
	 * 小时
	 */
	private int xb = 0;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 链接地址
	 */
	private String link;

	/**
	 * 列表的链接地址
	 */
	private String listLink;

	/**
	 * 链接属性
	 */
	private String linkType;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 流程名称
	 */
	private String processName;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 表名
	 */
	private String tablename;

	/**
	 * 执行的sql语句
	 */
	private String sql;

	/**
	 * 对象编号
	 */
	private String objectId;

	/**
	 * 对象值
	 */
	private String objectValue;

	/**
	 * 是否启用。1－启用、0－禁用
	 */
	private int enableFlag = 1;

	private long versionNo = 0;

	private Collection<Object> ok = new HashSet<Object>();

	private Collection<Object> caution = new HashSet<Object>();

	private Collection<Object> pastDue = new HashSet<Object>();

	public ToDo() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToDo other = (ToDo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getActorId() {
		return actorId;
	}

	public String getAlarm() {
		return alarm;
	}

	public int getAppId() {
		return appId;
	}

	public Collection<Object> getCaution() {
		return caution;
	}

	public String getCode() {
		return code;
	}

	public String getContent() {
		return content;
	}

	public long getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public int getEnableFlag() {
		return enableFlag;
	}

	public String getEventFrom() {
		return eventFrom;
	}

	public String getEventTo() {
		return eventTo;
	}

	public long getId() {
		return id;
	}

	public int getLimitDay() {
		return limitDay;
	}

	public String getLink() {
		return link;
	}

	public String getLinkType() {
		return linkType;
	}

	public String getListLink() {
		return listLink;
	}

	public int getModuleId() {
		return moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getNews() {
		return news;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public Collection<Object> getOk() {
		return ok;
	}

	public Collection<Object> getPastDue() {
		return pastDue;
	}

	public String getProcessName() {
		return processName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public long getRoleId() {
		return roleId;
	}

	public String getSql() {
		return sql;
	}

	public String getTablename() {
		return tablename;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public int getXa() {
		return xa;
	}

	public int getXb() {
		return xb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public ToDo jsonToObject(JSONObject jsonObject) {
		ToDo model = new ToDo();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}
		if (jsonObject.containsKey("alarm")) {
			model.setAlarm(jsonObject.getString("alarm"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getLong("deptId"));
		}
		if (jsonObject.containsKey("deptName")) {
			model.setDeptName(jsonObject.getString("deptName"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getInteger("enableFlag"));
		}
		if (jsonObject.containsKey("eventFrom")) {
			model.setEventFrom(jsonObject.getString("eventFrom"));
		}
		if (jsonObject.containsKey("eventTo")) {
			model.setEventTo(jsonObject.getString("eventTo"));
		}
		if (jsonObject.containsKey("limitDay")) {
			model.setLimitDay(jsonObject.getInteger("limitDay"));
		}
		if (jsonObject.containsKey("xa")) {
			model.setXa(jsonObject.getInteger("xa"));
		}
		if (jsonObject.containsKey("xb")) {
			model.setXb(jsonObject.getInteger("xb"));
		}
		if (jsonObject.containsKey("link")) {
			model.setLink(jsonObject.getString("link"));
		}
		if (jsonObject.containsKey("listLink")) {
			model.setListLink(jsonObject.getString("listLink"));
		}
		if (jsonObject.containsKey("linkType")) {
			model.setLinkType(jsonObject.getString("linkType"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getInteger("appId"));
		}
		if (jsonObject.containsKey("moduleId")) {
			model.setModuleId(jsonObject.getInteger("moduleId"));
		}
		if (jsonObject.containsKey("moduleName")) {
			model.setModuleName(jsonObject.getString("moduleName"));
		}
		if (jsonObject.containsKey("news")) {
			model.setNews(jsonObject.getString("news"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("roleCode")) {
			model.setRoleCode(jsonObject.getString("roleCode"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}
		if (jsonObject.containsKey("tablename")) {
			model.setTablename(jsonObject.getString("tablename"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("sql")) {
			model.setSql(jsonObject.getString("sql"));
		}
		if (jsonObject.containsKey("versionNo")) {
			model.setVersionNo(jsonObject.getLong("versionNo"));
		}
		return model;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public void setCaution(Collection<Object> caution) {
		this.caution = caution;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setEnableFlag(int enableFlag) {
		this.enableFlag = enableFlag;
	}

	public void setEventFrom(String eventFrom) {
		this.eventFrom = eventFrom;
	}

	public void setEventTo(String eventTo) {
		this.eventTo = eventTo;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLimitDay(int limitDay) {
		this.limitDay = limitDay;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public void setListLink(String listLink) {
		this.listLink = listLink;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setNews(String news) {
		this.news = news;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setOk(Collection<Object> ok) {
		this.ok = ok;
	}

	public void setPastDue(Collection<Object> pastDue) {
		this.pastDue = pastDue;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public void setXa(int xa) {
		this.xa = xa;
	}

	public void setXb(int xb) {
		this.xb = xb;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (actorId != null) {
			jsonObject.put("actorId", actorId);
		}
		if (alarm != null) {
			jsonObject.put("alarm", alarm);
		}
		if (code != null) {
			jsonObject.put("code", code);
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		jsonObject.put("deptId", deptId);
		if (deptName != null) {
			jsonObject.put("deptName", deptName);
		}
		jsonObject.put("enableFlag", enableFlag);
		if (eventFrom != null) {
			jsonObject.put("eventFrom", eventFrom);
		}
		if (eventTo != null) {
			jsonObject.put("eventTo", eventTo);
		}
		jsonObject.put("limitDay", limitDay);
		jsonObject.put("xa", xa);
		jsonObject.put("xb", xb);
		if (link != null) {
			jsonObject.put("link", link);
		}
		if (listLink != null) {
			jsonObject.put("listLink", listLink);
		}
		if (linkType != null) {
			jsonObject.put("linkType", linkType);
		}
		jsonObject.put("appId", appId);
		jsonObject.put("moduleId", moduleId);
		if (moduleName != null) {
			jsonObject.put("moduleName", moduleName);
		}
		if (news != null) {
			jsonObject.put("news", news);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		if (roleCode != null) {
			jsonObject.put("roleCode", roleCode);
		}
		jsonObject.put("roleId", roleId);
		if (tablename != null) {
			jsonObject.put("tablename", tablename);
		}
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (type != null) {
			jsonObject.put("type", type);
		}
		if (sql != null) {
			jsonObject.put("sql", sql);
		}
		jsonObject.put("versionNo", versionNo);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (actorId != null) {
			jsonObject.put("actorId", actorId);
		}
		if (alarm != null) {
			jsonObject.put("alarm", alarm);
		}
		if (code != null) {
			jsonObject.put("code", code);
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		jsonObject.put("deptId", deptId);
		if (deptName != null) {
			jsonObject.put("deptName", deptName);
		}
		jsonObject.put("enableFlag", enableFlag);
		if (eventFrom != null) {
			jsonObject.put("eventFrom", eventFrom);
		}
		if (eventTo != null) {
			jsonObject.put("eventTo", eventTo);
		}
		jsonObject.put("limitDay", limitDay);
		jsonObject.put("xa", xa);
		jsonObject.put("xb", xb);
		if (link != null) {
			jsonObject.put("link", link);
		}
		if (listLink != null) {
			jsonObject.put("listLink", listLink);
		}
		if (linkType != null) {
			jsonObject.put("linkType", linkType);
		}
		jsonObject.put("appId", appId);
		jsonObject.put("moduleId", moduleId);
		if (moduleName != null) {
			jsonObject.put("moduleName", moduleName);
		}
		if (news != null) {
			jsonObject.put("news", news);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		if (roleCode != null) {
			jsonObject.put("roleCode", roleCode);
		}
		jsonObject.put("roleId", roleId);
		if (tablename != null) {
			jsonObject.put("tablename", tablename);
		}
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (type != null) {
			jsonObject.put("type", type);
		}
		if (sql != null) {
			jsonObject.put("sql", sql);
		}
		jsonObject.put("versionNo", versionNo);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}