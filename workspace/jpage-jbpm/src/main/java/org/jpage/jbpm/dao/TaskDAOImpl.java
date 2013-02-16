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


package org.jpage.jbpm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.model.TaskItem;
import org.jpage.jbpm.util.ParamUtil;
import org.jpage.util.JdbcUtil;

public class TaskDAOImpl implements TaskDAO {
	private static final Log logger = LogFactory.getLog(TaskDAOImpl.class);

	public TaskDAOImpl() {

	}

	/**
	 * 根据流程实例编号获取用户的任务实例
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getProcessTaskItems(JbpmContext jbpmContext,
			String processInstanceId) {
		return this.getTaskItemsByProcessInstanceId(jbpmContext,
				processInstanceId);
	}

	protected String getINSQL(java.util.Collection rowIds) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" ( ");
		Iterator item = rowIds.iterator();
		while (item.hasNext()) {
			String rowId = (String) item.next();
			rowId = rowId.replaceAll("'", "");
			rowId = rowId.replaceAll("%", "");
			buffer.append("'").append(rowId).append("'  ");
			if (item.hasNext()) {
				buffer.append(" , ");
			}
		}
		buffer.append(" ) ");
		return buffer.toString();
	}

	protected String getNumericINSQL(java.util.Collection rowIds) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" ( ");
		Iterator item = rowIds.iterator();
		while (item.hasNext()) {
			Object rowId = (Object) item.next();
			buffer.append(rowId);
			if (item.hasNext()) {
				buffer.append(" , ");
			}
		}
		buffer.append(" ) ");
		return buffer.toString();
	}

	public List queryTasks(JbpmContext jbpmContext, Map params) {
		List values = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT a.ID_  taskInstId, ");
		sqlBuffer.append("  x.PROCESSINSTANCE_  processInstId, ");
		sqlBuffer.append("  a.NAME_  taskName, ");
		sqlBuffer.append("  a.DESCRIPTION_  taskDescription, ");
		sqlBuffer.append("  a.CREATE_  createDate, ");
		sqlBuffer.append("  a.ACTORID_  actorId ");
		sqlBuffer.append(" FROM JBPM_TASKINSTANCE a ");
		sqlBuffer.append(" INNER JOIN JBPM_TOKEN x ");
		sqlBuffer.append(" ON a.TOKEN_ = x.ID_ ");
		sqlBuffer.append(" WHERE (1 = 1) ");
		sqlBuffer.append(" AND (a.ACTORID_ IS NOT NULL) ");
		//sqlBuffer.append(" AND (a.ISOPEN_ = 1) ");
		//sqlBuffer.append(" AND (a.ISSUSPENDED_ != 1)");

		if (ObjectFactory.isBooleanDatabase()) {
			sqlBuffer.append(" AND (a.ISOPEN_ = true) ");
			sqlBuffer.append(" AND (a.ISSUSPENDED_ != true)");
		} else {
			sqlBuffer.append(" AND (a.ISOPEN_ = 1) ");
			sqlBuffer.append(" AND (a.ISSUSPENDED_ != 1)");
		}

		if (params.get("actorId") != null) {
			sqlBuffer.append(" AND a.ACTORID_ = ? ");
			values.add(params.get("actorId"));
		}

		if (params.get("processInstanceId") != null) {
			sqlBuffer.append(" AND ( x.PROCESSINSTANCE_ = ? ) ");
			values.add(Long.valueOf(params.get("processInstanceId").toString()));
		}

		if (ParamUtil.isNotEmpty(params, "actorIds")) {
			Collection rowIds = (Collection) params.get("actorIds");
			if (rowIds != null && rowIds.size() > 0) {
				sqlBuffer.append(" AND a.ACTORID_ IN ")
						.append(getINSQL(rowIds));
			}
		}

		if (ParamUtil.isNotEmpty(params, "processInstanceIds")) {
			Collection rowIds = (Collection) params.get("processInstanceIds");
			if (rowIds != null && rowIds.size() > 0) {
				sqlBuffer.append("  AND x.PROCESSINSTANCE_ IN ").append(
						getNumericINSQL(rowIds));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sql:" + sqlBuffer.toString());
			logger.debug("values:" + values);
		}

		List rows = new ArrayList();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = jbpmContext.getConnection();
			psmt = con.prepareStatement(sqlBuffer.toString());
			JdbcUtil.fillStatement(psmt, values);
			rs = psmt.executeQuery();
			while (rs.next()) {
				TaskItem item = new TaskItem();
				item.setTaskInstanceId(String.valueOf(rs.getInt(1)));
				item.setProcessInstanceId(String.valueOf(rs.getInt(2)));
				item.setTaskName(rs.getString(3));
				item.setTaskDescription(rs.getString(4));
				item.setTaskCreateDate(rs.getTimestamp(5));
				item.setActorId(rs.getString(6));
				rows.add(item);
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rows size:" + rows.size());
		}

		return rows;
	}

	public List queryPooledTasks(JbpmContext jbpmContext, Map params) {
		List values = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append(" SELECT a.ID_ taskInstId,  ");
		sqlBuffer.append(" x.PROCESSINSTANCE_ processInstId,  ");
		sqlBuffer.append(" a.NAME_ taskName,  ");
		sqlBuffer.append(" a.DESCRIPTION_ taskDescription,  ");
		sqlBuffer.append(" a.CREATE_ createDate,  ");
		sqlBuffer.append(" p.ACTORID_ actorId  ");
		sqlBuffer.append(" FROM JBPM_POOLEDACTOR p  ");
		sqlBuffer.append(" INNER JOIN JBPM_TASKACTORPOOL t  ");
		sqlBuffer.append(" ON p.ID_ = t.POOLEDACTOR_  ");
		sqlBuffer.append(" INNER JOIN JBPM_TASKINSTANCE a  ");
		sqlBuffer.append(" ON t.TASKINSTANCE_ = a.ID_  ");
		sqlBuffer.append(" INNER JOIN JBPM_TOKEN x  ");
		sqlBuffer.append(" ON a.TOKEN_ = x.ID_  ");
		sqlBuffer.append(" WHERE (1 = 1)  ");
		sqlBuffer.append(" AND (a.ACTORID_ IS NULL)  ");
		sqlBuffer.append(" AND (x.END_ IS NULL) ");
		// sqlBuffer.append(" AND (a.ISOPEN_ = 1)  ");
		// sqlBuffer.append(" AND (a.ISSUSPENDED_ != 1)  ");
		

		if (ObjectFactory.isBooleanDatabase()) {
			sqlBuffer.append(" AND (a.ISOPEN_ = true) ");
			sqlBuffer.append(" AND (a.ISSUSPENDED_ != true)");
		} else {
			sqlBuffer.append(" AND (a.ISOPEN_ = 1) ");
			sqlBuffer.append(" AND (a.ISSUSPENDED_ != 1)");
		}

		if (params.get("actorId") != null) {
			sqlBuffer.append(" AND ( p.ACTORID_ = ? ) ");
			values.add(params.get("actorId"));
		}

		if (params.get("processInstanceId") != null) {
			sqlBuffer.append(" AND ( x.PROCESSINSTANCE_ = ? ) ");
			values.add(Long.valueOf(params.get("processInstanceId").toString()));
		}

		if (ParamUtil.isNotEmpty(params, "actorIds")) {
			Collection rowIds = (Collection) params.get("actorIds");
			if (rowIds != null && rowIds.size() > 0) {
				sqlBuffer.append(" AND p.ACTORID_ IN ")
						.append(getINSQL(rowIds));
			}
		}

		if (ParamUtil.isNotEmpty(params, "processInstanceIds")) {
			Collection rowIds = (Collection) params.get("processInstanceIds");
			if (rowIds != null && rowIds.size() > 0) {
				logger.info("#processInstanceIds:" + rowIds);
				sqlBuffer.append("  AND x.PROCESSINSTANCE_  IN ").append(
						getNumericINSQL(rowIds));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sql:" + sqlBuffer.toString());
			logger.debug("values:" + values);
		}
		
		logger.info("sql:" + sqlBuffer.toString());
		logger.info("values:" + values);

		List rows = new ArrayList();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = jbpmContext.getConnection();
			psmt = con.prepareStatement(sqlBuffer.toString());
			JdbcUtil.fillStatement(psmt, values);
			rs = psmt.executeQuery();
			while (rs.next()) {
				TaskItem item = new TaskItem();
				item.setTaskInstanceId(String.valueOf(rs.getInt(1)));
				item.setProcessInstanceId(String.valueOf(rs.getInt(2)));
				item.setTaskName(rs.getString(3));
				item.setTaskDescription(rs.getString(4));
				item.setTaskCreateDate(rs.getTimestamp(5));
				item.setActorId(rs.getString(6));
				rows.add(item);
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rows size:" + rows.size());
		}

		return rows;
	}

	public List queryFinishedTasks(JbpmContext jbpmContext, Map params) {
		List values = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append(" SELECT a.TASKINSTANCEID, a.PROCESSINSTANCEID, a.TASKNAME, a.TASKDESCRIPTION, a.STARTDATE, a.PROCESSNAME, a.ACTORID FROM JPAGE_JBPM_STATE_INSTANCE a WHERE 1=1 ");

		// joy 2011-07-27 增加按日期过滤功能
		if (params.get("afterCreateDate") != null) {
			sqlBuffer.append(" AND a.STARTDATE >= ? ");
			values.add(params.get("afterCreateDate"));
		} else {
			sqlBuffer.append(" AND a.STARTDATE >= ? ");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new java.util.Date());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int num = 365;// 取一年前到今天的数据
			calendar.set(year, month, day - num);
			java.util.Date startDate = calendar.getTime();
			values.add(startDate);
		}

		if (params.get("actorId") != null) {
			sqlBuffer.append(" AND a.ACTORID = ? ");
			values.add(params.get("actorId"));
		}

		if (ParamUtil.isNotEmpty(params, "actorIds")) {
			Collection rowIds = (Collection) params.get("actorIds");
			if (rowIds != null && rowIds.size() > 0) {
				sqlBuffer.append("  AND a.ACTORID IN ")
						.append(getINSQL(rowIds));
			}
		}

		if (params.get("processName") != null) {
			sqlBuffer.append(" AND a.PROCESSNAME = ? ");
			values.add(params.get("processName"));
		}

		if (ParamUtil.isNotEmpty(params, "processNames")) {
			Collection rowIds = (Collection) params.get("processNames");
			if (rowIds != null && rowIds.size() > 0) {
				sqlBuffer.append("  AND a.PROCESSNAME IN ").append(
						getINSQL(rowIds));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sql:" + sqlBuffer.toString());
			logger.debug("values:" + values);
		}

		List rows = new ArrayList();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = jbpmContext.getConnection();
			psmt = con.prepareStatement(sqlBuffer.toString());
			JdbcUtil.fillStatement(psmt, values);
			rs = psmt.executeQuery();
			while (rs.next()) {
				TaskItem taskItem = new TaskItem();
				taskItem.setTaskInstanceId(rs.getString(1));
				taskItem.setProcessInstanceId(rs.getString(2));
				taskItem.setTaskName(rs.getString(3));
				taskItem.setTaskDescription(rs.getString(4));
				taskItem.setTaskCreateDate(rs.getTimestamp(5));
				taskItem.setProcessName(rs.getString(6));
				taskItem.setActorId(rs.getString(7));
				rows.add(taskItem);
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rows size:" + rows.size());
		}

		return rows;
	}

	public List queryRunningProcessInstanceIds(JbpmContext jbpmContext,
			Map params) {
		List values = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT DISTINCT a.PROCESSINSTANCE_ ");
		sqlBuffer.append(" FROM JBPM_TOKEN a ");
		sqlBuffer.append(" INNER JOIN JBPM_PROCESSINSTANCE b ");
		sqlBuffer.append(" ON a.PROCESSINSTANCE_ = b.ID_ ");
		sqlBuffer.append(" INNER JOIN JBPM_PROCESSDEFINITION c ");
		sqlBuffer.append(" ON b.PROCESSDEFINITION_ = c.ID_ ");
		sqlBuffer.append(" WHERE (1 = 1) ");
		sqlBuffer.append(" AND (a.END_ IS NULL) ");

		// joy 2011-07-26 增加按日期过滤功能
		if (params.get("afterCreateDate") != null) {
			sqlBuffer.append(" AND b.START_ >= ? ");
			values.add(params.get("afterCreateDate"));
		} else {
			sqlBuffer.append(" AND b.START_ >= ? ");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new java.util.Date());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int num = 365;// 取一年前到今天的数据
			calendar.set(year, month, day - num);
			java.util.Date startDate = calendar.getTime();
			values.add(startDate);
		}

		if (params.get("processName") != null) {
			sqlBuffer.append(" AND c.NAME_ = ? ");
			values.add(params.get("processName"));
		}

		if (ParamUtil.isNotEmpty(params, "processNames")) {
			Collection rowIds = (Collection) params.get("processNames");
			if (rowIds != null && rowIds.size() > 0) {
				sqlBuffer.append("  AND c.NAME_ IN ").append(getINSQL(rowIds));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sql:" + sqlBuffer.toString());
			logger.debug("values:" + values);
		}

		List rows = new ArrayList();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = jbpmContext.getConnection();
			psmt = con.prepareStatement(sqlBuffer.toString());
			JdbcUtil.fillStatement(psmt, values);
			rs = psmt.executeQuery();
			while (rs.next()) {
				rows.add(rs.getString(1));
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rows size:" + rows.size());
		}

		return rows;
	}

	public List queryProcessInstanceIds(JbpmContext jbpmContext, Map params) {
		List values = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append(" SELECT DISTINCT a.ID_ processInstanceId FROM JBPM_PROCESSINSTANCE a INNER JOIN JBPM_PROCESSDEFINITION b ON a.PROCESSDEFINITION_ = b.ID_  WHERE 1=1 ");

		if (params.get("processName") != null) {
			sqlBuffer.append(" AND b.NAME_ = ? ");
			values.add(params.get("processName"));
		}

		if (ParamUtil.isNotEmpty(params, "processNames")) {
			Collection rowIds = (Collection) params.get("processNames");
			if (rowIds != null && rowIds.size() > 0) {
				sqlBuffer.append("  AND b.NAME_ IN ").append(getINSQL(rowIds));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sql:" + sqlBuffer.toString());
			logger.debug("values:" + values);
		}

		List rows = new ArrayList();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = jbpmContext.getConnection();
			psmt = con.prepareStatement(sqlBuffer.toString());
			JdbcUtil.fillStatement(psmt, values);
			rs = psmt.executeQuery();
			while (rs.next()) {
				rows.add(rs.getString(1));
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rows size:" + rows.size());
		}

		return rows;
	}

	public List queryFinishedProcessInstanceIds(JbpmContext jbpmContext,
			Map params) {
		List values = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("  SELECT DISTINCT token.PROCESSINSTANCE_ processInstanceId ");
		sqlBuffer.append(" FROM JBPM_TASKINSTANCE ti ");
		sqlBuffer.append(" INNER JOIN JBPM_TOKEN token ");
		sqlBuffer.append(" ON token.ID_ = ti.TOKEN_ ");
		sqlBuffer.append(" WHERE 1=1 ");
		sqlBuffer.append(" AND ( ti.END_ IS NOT NULL ) ");
		//sqlBuffer.append(" AND ( ti.ISOPEN_ = 0 ) ");
		//sqlBuffer.append(" AND ( ti.ISSUSPENDED_ != 1 ) ");

		if (ObjectFactory.isBooleanDatabase()) {
			sqlBuffer.append(" AND ( ti.ISOPEN_ = false ) ");
			sqlBuffer.append(" AND ( ti.ISSUSPENDED_ != true ) ");
		} else {
			sqlBuffer.append(" AND ( ti.ISOPEN_ = 0 ) ");
			sqlBuffer.append(" AND ( ti.ISSUSPENDED_ != 1 ) ");
		}

		// joy 2011-07-26 增加按日期过滤功能
		if (params.get("afterCreateDate") != null) {
			sqlBuffer.append(" AND ti.CREATE_ >= ? ");
			values.add(params.get("afterCreateDate"));
		} else {
			sqlBuffer.append(" AND ti.CREATE_ >= ? ");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new java.util.Date());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int num = 365;// 取一年前到今天的数据
			calendar.set(year, month, day - num);
			java.util.Date startDate = calendar.getTime();
			values.add(startDate);
		}

		if (params.get("actorId") != null) {
			sqlBuffer.append(" AND ti.ACTORID_ = ? ");
			values.add(params.get("actorId"));
		}

		if (ParamUtil.isNotEmpty(params, "actorIds")) {
			Collection rowIds = (Collection) params.get("actorIds");
			if (rowIds != null && rowIds.size() > 0) {
				sqlBuffer.append("  AND  ti.ACTORID_ IN ").append(
						getINSQL(rowIds));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sql:" + sqlBuffer.toString());
			logger.debug("values:" + values);
		}

		List rows = new ArrayList();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = jbpmContext.getConnection();
			psmt = con.prepareStatement(sqlBuffer.toString());
			JdbcUtil.fillStatement(psmt, values);
			rs = psmt.executeQuery();
			while (rs.next()) {
				rows.add(rs.getString(1));
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rows size:" + rows.size());
		}

		return rows;
	}

	public List queryFinishedPooledProcessInstanceIds(JbpmContext jbpmContext,
			Map params) {
		List values = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append(" SELECT DISTINCT token.PROCESSINSTANCE_ processInstanceId ");
		sqlBuffer.append(" FROM JBPM_POOLEDACTOR pool ");
		sqlBuffer.append(" INNER JOIN  JBPM_TASKACTORPOOL tap ");
		sqlBuffer.append(" ON pool.ID_ = tap.POOLEDACTOR_ ");
		sqlBuffer.append(" INNER JOIN JBPM_TASKINSTANCE ti ");
		sqlBuffer.append(" ON tap.TASKINSTANCE_ = ti.ID_ ");
		sqlBuffer.append(" INNER JOIN JBPM_TOKEN token ");
		sqlBuffer.append(" ON token.ID_ = ti.TOKEN_ ");
		sqlBuffer.append(" WHERE (ti.END_ IS NOT NULL) ");
		// sqlBuffer.append(" AND ( ti.ISOPEN_ = 0 ) ");
		// sqlBuffer.append(" AND ( ti.ISSUSPENDED_ != 1 ) ");

		if (ObjectFactory.isBooleanDatabase()) {
			sqlBuffer.append(" AND ( ti.ISOPEN_ = false ) ");
			sqlBuffer.append(" AND ( ti.ISSUSPENDED_ != true ) ");
		} else {
			sqlBuffer.append(" AND ( ti.ISOPEN_ = 0 ) ");
			sqlBuffer.append(" AND ( ti.ISSUSPENDED_ != 1 ) ");
		}

		// joy 2011-07-26 增加按日期过滤功能
		if (params.get("afterCreateDate") != null) {
			sqlBuffer.append(" AND ti.CREATE_ >= ? ");
			values.add(params.get("afterCreateDate"));
		} else {
			sqlBuffer.append(" AND ti.CREATE_ >= ? ");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new java.util.Date());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int num = 365;// 取一年前到今天的数据
			calendar.set(year, month, day - num);
			java.util.Date startDate = calendar.getTime();
			values.add(startDate);
		}

		if (params.get("actorId") != null) {
			sqlBuffer.append(" AND pool.ACTORID_ = ? ");
			values.add(params.get("actorId"));
		}

		if (ParamUtil.isNotEmpty(params, "actorIds")) {
			Collection rowIds = (Collection) params.get("actorIds");
			if (rowIds != null && rowIds.size() > 0) {
				sqlBuffer.append("  AND  pool.ACTORID_ IN ").append(
						getINSQL(rowIds));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sql:" + sqlBuffer.toString());
			logger.debug("values:" + values);
		}

		List rows = new ArrayList();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = jbpmContext.getConnection();
			psmt = con.prepareStatement(sqlBuffer.toString());
			JdbcUtil.fillStatement(psmt, values);
			rs = psmt.executeQuery();
			while (rs.next()) {
				rows.add(rs.getString(1));
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rows size:" + rows.size());
		}

		return rows;
	}

	public List getTaskItems(JbpmContext jbpmContext, Map params) {
		List taskItems = new ArrayList();
		List processInstanceIds = new ArrayList();

		List rows01 = this.queryTasks(jbpmContext, params);
		List rows02 = this.queryPooledTasks(jbpmContext, params);

		if (rows01 != null && rows01.size() > 0) {
			logger.debug("task size:" + rows01.size());
			Iterator iter01 = rows01.iterator();
			while (iter01.hasNext()) {
				TaskItem taskItem = (TaskItem) iter01.next();
				taskItems.add(taskItem);
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}

		if (rows02 != null && rows02.size() > 0) {
			logger.debug("pooled task size:" + rows02.size());
			Iterator iter02 = rows02.iterator();
			while (iter02.hasNext()) {
				TaskItem taskItem = (TaskItem) iter02.next();
				taskItems.add(taskItem);
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}

		logger.debug("processInstanceIds size:" + processInstanceIds.size());

		if (processInstanceIds.size() > 0) {
			Map paramMap = new HashMap();
			paramMap.put("processInstanceIds", processInstanceIds);
		}

		if (taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem item = (TaskItem) iterator.next();

			}
		}

		return taskItems;
	}

	/**
	 * 获取全部用户的待办任务，用于消息系统的催办。
	 * 
	 * @param jbpmContext
	 * @return
	 */
	public List getAllTaskItems(JbpmContext jbpmContext) {
		Map params = new HashMap();
		return this.getTaskItems(jbpmContext, params);
	}

	public List getTaskItems(JbpmContext jbpmContext, String actorId) {
		Map params = new HashMap();
		params.put("actorId", actorId);
		return this.getTaskItems(jbpmContext, params);
	}

	public List getTaskItems(JbpmContext jbpmContext, Collection actorIds) {
		if (actorIds == null || actorIds.size() == 0) {
			return null;
		}
		Map params = new HashMap();
		params.put("actorIds", actorIds);
		return this.getTaskItems(jbpmContext, params);
	}

	/**
	 * 根据流程实例编号和用户编号获取用户的任务实例编号
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, String actorId,
			String processInstanceId) {
		Map params = new HashMap();
		params.put("actorId", actorId);
		params.put("processInstanceId", processInstanceId);
		return this.getTaskItems(jbpmContext, params);
	}

	/**
	 * 根据流程实例编号和用户编号获取用户的任务实例
	 * 
	 * @param actorIds
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, Collection actorIds,
			String processInstanceId) {
		Map params = new HashMap();
		params.put("actorIds", actorIds);
		params.put("processInstanceId", processInstanceId);
		return this.getTaskItems(jbpmContext, params);
	}

	/**
	 * 根据流程实例编号获取用户的任务实例
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItemsByProcessInstanceId(JbpmContext jbpmContext,
			String processInstanceId) {
		Map paramMap = new HashMap();
		paramMap.put("processInstanceId", processInstanceId);
		List rows = new ArrayList();
		List taskItems = this.getTaskItems(jbpmContext, paramMap);
		if (taskItems != null && taskItems.size() > 0) {
			rows.addAll(taskItems);
		}
		return rows;
	}

	/**
	 * 根据流程实例编号获取用户的任务实例
	 * 
	 * @param processInstanceIds
	 * @return
	 */
	public List getTaskItemsByProcessInstanceIds(JbpmContext jbpmContext,
			Collection processInstanceIds) {
		Map paramMap = new HashMap();
		paramMap.put("processInstanceIds", processInstanceIds);
		List rows = new ArrayList();
		List taskItems = this.getTaskItems(jbpmContext, paramMap);
		if (taskItems != null && taskItems.size() > 0) {
			rows.addAll(taskItems);
		}
		return rows;
	}

	/**
	 * 获取某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName) {
		Map params = new HashMap();
		params.put("processName", processName);
		List processInstanceIds = this.queryRunningProcessInstanceIds(
				jbpmContext, params);
		logger.info("@processName="+processName+" processInstanceIds="+processInstanceIds);
		if (StringUtils.isNotEmpty(processName)) {
			if(processInstanceIds.isEmpty()){
				processInstanceIds.add("0");
			}
		}
		Map paramMap = new HashMap();
		paramMap.put("processInstanceIds", processInstanceIds);
		return this.getTaskItems(jbpmContext, paramMap);
	}

	/**
	 * 获取某个用户某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, String actorId) {
		Map params = new HashMap();
		params.put("processName", processName);
		List processInstanceIds = this.queryRunningProcessInstanceIds(
				jbpmContext, params);
		logger.info("processName="+processName+" processInstanceIds="+processInstanceIds);
		if (StringUtils.isNotEmpty(processName)) {
			if(processInstanceIds.isEmpty()){
				processInstanceIds.add("0");
			}
		}
		Map paramMap = new HashMap();
		paramMap.put("actorId", actorId);
		paramMap.put("processInstanceIds", processInstanceIds);
		return this.getTaskItems(jbpmContext, paramMap);
	}

	/**
	 * 获取某个用户某些流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processNames
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection processNames, String actorId) {
		Map params = new HashMap();
		params.put("processNames", processNames);
		List processInstanceIds = this.queryRunningProcessInstanceIds(
				jbpmContext, params);
		if (ParamUtil.isNotEmpty(params, "processNames")) {
			if(processInstanceIds.isEmpty()){
				processInstanceIds.add("0");
			}
		}
		Map paramMap = new HashMap();
		paramMap.put("actorId", actorId);
		paramMap.put("processInstanceIds", processInstanceIds);
		return this.getTaskItems(jbpmContext, paramMap);
	}

	/**
	 * 获取某些用户某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, Collection actorIds) {
		Map params = new HashMap();
		params.put("processName", processName);
		List processInstanceIds = this.queryRunningProcessInstanceIds(
				jbpmContext, params);
		logger.info("@@processName="+processName+" processInstanceIds="+processInstanceIds);
		if (StringUtils.isNotEmpty(processName)) {
			if(processInstanceIds.isEmpty()){
				processInstanceIds.add("0");
			}
		}
		Map paramMap = new HashMap();
		paramMap.put("actorIds", actorIds);
		paramMap.put("processInstanceIds", processInstanceIds);
		return this.getTaskItems(jbpmContext, paramMap);
	}

	/**
	 * 获取某些用户某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection processNames, Collection actorIds) {
		Map params = new HashMap();
		params.put("processNames", processNames);
		List processInstanceIds = this.queryRunningProcessInstanceIds(
				jbpmContext, params);
		if (ParamUtil.isNotEmpty(params, "processNames")) {
			if(processInstanceIds.isEmpty()){
				processInstanceIds.add("0");
			}
		}
		Map paramMap = new HashMap();
		paramMap.put("actorIds", actorIds);
		paramMap.put("processInstanceIds", processInstanceIds);
		return this.getTaskItems(jbpmContext, paramMap);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext, String actorId) {
		Map params = new HashMap();
		params.put("actorId", actorId);
		return this.getFinishedTaskItems(jbpmContext, params);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorIds
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			Collection actorIds) {
		Map params = new HashMap();
		params.put("actorIds", actorIds);
		return this.getFinishedTaskItems(jbpmContext, params);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			String processName, String actorId) {
		Map params = new HashMap();
		params.put("processName", processName);
		params.put("actorId", actorId);
		return this.getFinishedTaskItems(jbpmContext, params);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorIds
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			String processName, Collection actorIds) {
		Map params = new HashMap();
		params.put("processName", processName);
		params.put("actorIds", actorIds);
		return this.getFinishedTaskItems(jbpmContext, params);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param params
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext, Map params) {

		List processInstanceIds = new ArrayList();

		List rows = this.queryFinishedTasks(jbpmContext, params);

		if (rows != null && rows.size() > 0) {
			logger.debug("finished task size:" + rows.size());
			Iterator iter01 = rows.iterator();
			while (iter01.hasNext()) {
				TaskItem taskItem = (TaskItem) iter01.next();
				if (StringUtils.isNotEmpty(taskItem.getRowId())) {
					processInstanceIds.add(taskItem.getProcessInstanceId());
				}
			}
		}

		if (rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				TaskItem item = (TaskItem) iterator.next();
			}
		}

		return rows;
	}

	/**
	 * 获取用户已经处理过的流程实例编号
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public Collection getFinishedProcessInstanceIds(JbpmContext jbpmContext,
			Collection actorIds) {
		Collection processInstanceIds = new HashSet();
		if (actorIds == null || actorIds.size() == 0) {
			return processInstanceIds;
		}
		Map params = new HashMap();
		params.put("actorIds", actorIds);

		List rows01 = this.queryFinishedProcessInstanceIds(jbpmContext, params);
		if (rows01 != null && rows01.size() > 0) {
			processInstanceIds.addAll(rows01);
		}

		List rows02 = this.queryFinishedPooledProcessInstanceIds(jbpmContext,
				params);
		if (rows02 != null && rows02.size() > 0) {
			processInstanceIds.addAll(rows02);
		}

		return processInstanceIds;
	}

	/**
	 * 获取某个流程名称的流程实例
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public Collection getProcessInstanceIds(JbpmContext jbpmContext,
			String processName) {
		Collection processInstanceIds = new HashSet();
		if (StringUtils.isEmpty(processName)) {
			return processInstanceIds;
		}
		Map params = new HashMap();
		params.put("processName", processName);
		processInstanceIds = this.queryProcessInstanceIds(jbpmContext, params);
		return processInstanceIds;
	}

	/**
	 * 根据流程实例编号和任务编号获取该任务的处理者
	 * 
	 * @param processInstanceId
	 * @param taskId
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, String processInstanceId,
			String taskId) {
		Set actorIds = new HashSet();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT  a.actorId actorId FROM JPAGE_JBPM_STATE_INSTANCE a ");
		buffer.append(" WHERE 1=1 ");
		buffer.append(" AND a.processInstanceId = ? ");
		buffer.append(" AND a.taskId = ? ");
		buffer.append(" AND a.versionNo ");

		if (logger.isDebugEnabled()) {
			logger.debug("sql:" + buffer.toString());
		}

		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = jbpmContext.getConnection().prepareStatement(
					buffer.toString());
			psmt.setString(1, processInstanceId);
			psmt.setString(2, taskId);
			rs = psmt.executeQuery();
			while (rs.next()) {
				String actorId = rs.getString(1);
				actorIds.add(actorId);
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (SQLException ex) {
			logger.error(ex);
			throw new RuntimeException(ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actorIds size:" + actorIds.size());
		}

		return actorIds;
	}
}
