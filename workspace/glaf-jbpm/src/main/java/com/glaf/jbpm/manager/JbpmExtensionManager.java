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

package com.glaf.jbpm.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.def.Task;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.jbpm.dao.JbpmEntityDAO;
import com.glaf.jbpm.model.Extension;

public class JbpmExtensionManager {
	protected final static Log logger = LogFactory
			.getLog(JbpmExtensionManager.class);

	private JbpmEntityDAO jbpmEntityDAO;

	public JbpmExtensionManager() {
		jbpmEntityDAO = new JbpmEntityDAO();
	}

	public Extension getExtensionAction(JbpmContext jbpmContext, String name) {
		logger.debug("name:" + name);
		Extension extension = null;
		final SqlExecutor queryExecutor = new SqlExecutor();
		final String hql = " select a from "
				+ Extension.class.getSimpleName()
				+ " as a where a.locked = 0 and a.name = :name and a.processName is null order by a.id desc ";
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		queryExecutor.setSql(hql);
		queryExecutor.setParameter(paramMap);

		final List<Object> rows = jbpmEntityDAO.getList(jbpmContext,
				queryExecutor);
		if (rows != null && rows.size() > 0) {
			extension = (Extension) rows.get(0);
		}
		return extension;
	}

	public Extension getExtensionAction(JbpmContext jbpmContext,
			String processName, String name) {
		logger.debug("processName:" + processName);
		logger.debug("name:" + name);
		Extension extension = null;
		final SqlExecutor queryExecutor = new SqlExecutor();
		final String hql = " select a from "
				+ Extension.class.getSimpleName()
				+ " as a where a.locked = 0 and a.processName = :processName and a.name = :name order by a.id desc ";
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("processName", processName);
		paramMap.put("name", name);
		queryExecutor.setSql(hql);
		queryExecutor.setParameter(paramMap);

		final List<Object> rows = jbpmEntityDAO.getList(jbpmContext,
				queryExecutor);
		if (rows != null && rows.size() > 0) {
			extension = (Extension) rows.get(0);
		}
		return extension;
	}

	public List<Extension> getExtensions(JbpmContext jbpmContext,
			String processName) {
		final List<Extension> extensions = new ArrayList<Extension>();
		final SqlExecutor queryExecutor = new SqlExecutor();
		final String hql = " select a from " + Extension.class.getSimpleName()
				+ " as a where a.processName = :processName and a.locked = 0 ";
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("processName", processName);
		queryExecutor.setSql(hql);
		queryExecutor.setParameter(paramMap);

		final List<Object> rows = jbpmEntityDAO.getList(jbpmContext,
				queryExecutor);
		if (rows != null && rows.size() > 0) {
			final Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Extension model = (Extension) iterator.next();
				extensions.add(model);
			}
		}
		return extensions;
	}

	public Extension getExtension(JbpmContext jbpmContext, String extendId) {
		logger.debug("extendId:" + extendId);
		Extension extension = null;
		final SqlExecutor queryExecutor = new SqlExecutor();
		final String hql = " select a from " + Extension.class.getSimpleName()
				+ " as a where a.locked = 0 and a.extendId = :extendId  ";
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("extendId", extendId);

		queryExecutor.setSql(hql);
		queryExecutor.setParameter(paramMap);

		final List<Object> rows = jbpmEntityDAO.getList(jbpmContext,
				queryExecutor);
		if (rows != null && rows.size() > 0) {
			extension = (Extension) rows.get(0);
		}
		return extension;
	}

	public Extension getExtensionTask(JbpmContext jbpmContext,
			String processName, String taskName) {
		logger.debug("processName:" + processName);
		logger.debug("taskName:" + taskName);
		Extension extension = null;
		SqlExecutor queryExecutor = new SqlExecutor();
		final String hql = " select a from "
				+ Extension.class.getSimpleName()
				+ " as a where a.locked = 0 and a.processName = :processName and a.taskName = :taskName order by a.id desc ";
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("processName", processName);
		paramMap.put("taskName", taskName);
		queryExecutor.setSql(hql);
		queryExecutor.setParameter(paramMap);

		List<Object> rows = jbpmEntityDAO.getList(jbpmContext, queryExecutor);
		if (rows != null && rows.size() > 0) {
			extension = (Extension) rows.get(0);
		}
		return extension;
	}

	public List<Extension> getExtensionTasks(JbpmContext jbpmContext,
			String processName) {
		List<Extension> extensions = new ArrayList<Extension>();
		SqlExecutor queryExecutor = new SqlExecutor();
		String hql = " select a from "
				+ Extension.class.getSimpleName()
				+ " as a where a.processName = :processName and a.taskName is not null and a.locked = 0 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("processName", processName);
		queryExecutor.setSql(hql);
		queryExecutor.setParameter(paramMap);

		List<Object> rows = jbpmEntityDAO.getList(jbpmContext, queryExecutor);
		if (rows != null && rows.size() > 0) {
			ProcessDefinition processDefinition = jbpmContext.getGraphSession()
					.findLatestProcessDefinition(processName);
			Map<String, Task> taskMap = processDefinition
					.getTaskMgmtDefinition().getTasks();
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Extension model = (Extension) iterator.next();
				model.setProcessDescription(processDefinition.getDescription());
				Task task = taskMap.get(model.getTaskName());
				model.setTaskDescription(task.getDescription());
				extensions.add(model);
			}
		}
		return extensions;
	}

	public synchronized void reconfig(JbpmContext jbpmContext,
			List<Extension> extensions) {
		if (extensions != null && extensions.size() > 0) {
			Iterator<Extension> iter = extensions.iterator();
			while (iter.hasNext()) {
				Extension extension = iter.next();
				String extendId = null;
				String processName = extension.getProcessName();
				if (StringUtils.isNotEmpty(processName)) {
					if (StringUtils.isNotEmpty(extension.getTaskName())) {
						extendId = processName + "_" + extension.getTaskName();
					} else if (StringUtils.isNotEmpty(extension.getName())) {
						extendId = processName + "_" + extension.getName();
					}
				}

				if (StringUtils.isEmpty(extendId)) {
					if (StringUtils.isNotEmpty(extension.getName())) {
						extendId = extension.getName();
					}
				}

				String sql = " delete from EX_JBPM_EXFIELD where EXTENDID_ = '"
						+ extendId + "'";
				jbpmEntityDAO.executeSqlUpdate(jbpmContext, sql);
				sql = " delete from EX_JBPM_EXPARAM where EXTENDID_ = '"
						+ extendId + "'";
				jbpmEntityDAO.executeSqlUpdate(jbpmContext, sql);
				sql = " delete from EX_JBPM_EXTENSION where EXTENDID_ = '"
						+ extendId + "'";
				jbpmEntityDAO.executeSqlUpdate(jbpmContext, sql);

				extension.setExtendId(extendId);
				extension.setCreateDate(new Date());
			}

			jbpmEntityDAO.saveAll(jbpmContext, extensions);
		}
	}

	public void setJbpmEntityDAO(JbpmEntityDAO jbpmEntityDAO) {
		this.jbpmEntityDAO = jbpmEntityDAO;
	}

}