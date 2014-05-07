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

package com.glaf.core.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.AccessEntry;
import com.glaf.core.base.AccessPoint;
import com.glaf.core.base.Accessable;
import com.glaf.core.base.DataAccess;
import com.glaf.core.base.ModuleAccess;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.AccessEntryMapper;
import com.glaf.core.mapper.AccessPointMapper;
import com.glaf.core.mapper.DataAccessMapper;
import com.glaf.core.query.AccessEntryQuery;
import com.glaf.core.query.AccessPointQuery;
import com.glaf.core.service.IAccessService;

@Service("accessService")
@Transactional(readOnly = true)
public class MxAccessServiceImpl implements IAccessService {
	protected final static Log logger = LogFactory
			.getLog(MxAccessServiceImpl.class);

	protected AccessEntryMapper accessEntryMapper;

	protected AccessPointMapper accessPointMapper;

	protected DataAccessMapper dataAccessMapper;

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	public MxAccessServiceImpl() {

	}

	public int count(AccessEntryQuery query) {
		query.ensureInitialized();
		return accessEntryMapper.getAccessEntryCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		accessEntryMapper.deleteAccessEntryById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		AccessEntryQuery query = new AccessEntryQuery();
		query.rowIds(rowIds);
		accessEntryMapper.deleteAccessEntries(query);
	}

	public List<Accessable> getAccessableList(String serviceKey,
			String dataInstanceId) {
		if (StringUtils.isEmpty(serviceKey)) {
			throw new RuntimeException("serviceKey is null");
		}

		List<Accessable> rows = new java.util.ArrayList<Accessable>();
		List<DataAccess> list01 = this.getDataAccesses(serviceKey,
				dataInstanceId);
		for (DataAccess da : list01) {
			rows.add(da);
		}

		List<ModuleAccess> list02 = this.getModuleAccesses(serviceKey);
		for (ModuleAccess ma : list02) {
			rows.add(ma);
		}
		return rows;
	}

	public List<AccessEntry> getAccessEntries(AccessEntryQuery query) {
		List<AccessEntry> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			for (AccessEntry accessEntry : list) {
				this.initAccessEntry(accessEntry);
			}
		}
		return list;
	}

	public List<AccessEntry> getAccessEntries(String processName) {
		AccessEntryQuery query = new AccessEntryQuery();
		query.processName(processName);
		List<AccessEntry> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			for (AccessEntry accessEntry : list) {
				this.initAccessEntry(accessEntry);
			}
		}
		return list;
	}

	public AccessEntry getAccessEntry(String id) {
		AccessEntry accessEntry = accessEntryMapper.getAccessEntryById(id);
		return accessEntry;
	}

	public List<AccessPoint> getAccessPoints(AccessPointQuery query) {
		List<AccessPoint> list = accessPointMapper.getAccessPoints(query);
		return list;
	}

	public List<DataAccess> getDataAccesses(String serviceKey,
			String businessKey) {
		if (StringUtils.isEmpty(serviceKey)) {
			throw new RuntimeException("serviceKey is null");
		}
		Map<String, Object> parameter = new java.util.HashMap<String, Object>();
		parameter.put("serviceKey", serviceKey);
		parameter.put("businessKey", businessKey);
		List<Object> rows = this.entityDAO.getList(
				"getDataAccessByBusinessKey", parameter);
		List<DataAccess> list = new java.util.ArrayList<DataAccess>();
		for (Object object : rows) {
			list.add((DataAccess) object);
		}
		return list;
	}

	public AccessEntry getLatestAccessEntry(String applicationName) {
		AccessEntryQuery query = new AccessEntryQuery();
		query.applicationName(applicationName);
		query.entryType(0);
		List<AccessEntry> list = this.list(query);
		AccessEntry accessEntry = null;
		if (list != null && !list.isEmpty()) {
			accessEntry = list.get(0);
			this.initAccessEntry(accessEntry);
		}

		return accessEntry;
	}

	public AccessEntry getLatestAccessEntry(String processName, String taskName) {
		AccessEntryQuery query = new AccessEntryQuery();
		query.processName(processName);
		query.taskName(taskName);
		query.setEntryType(99);
		List<AccessEntry> list = this.list(query);
		AccessEntry accessEntry = null;
		if (list != null && !list.isEmpty()) {
			accessEntry = list.get(0);
			this.initAccessEntry(accessEntry);
		}
		return accessEntry;
	}

	public List<ModuleAccess> getModuleAccesses(String serviceKey) {
		if (StringUtils.isEmpty(serviceKey)) {
			throw new RuntimeException("serviceKey is null");
		}
		Map<String, Object> parameter = new java.util.HashMap<String, Object>();
		parameter.put("serviceKey", serviceKey);
		List<Object> rows = this.entityDAO.getList(
				"getModuleAccessByServiceKey", parameter);
		List<ModuleAccess> list = new java.util.ArrayList<ModuleAccess>();
		for (Object object : rows) {
			list.add((ModuleAccess) object);
		}
		return list;
	}

	protected void initAccessEntry(AccessEntry accessEntry) {
		AccessPointQuery query = new AccessPointQuery();
		query.accessEntryId(accessEntry.getId());
		List<AccessPoint> list = this.getAccessPoints(query);
		if (list != null && !list.isEmpty()) {
			for (AccessPoint accessPoint : list) {
				accessEntry.addAccessPoint(accessPoint);
			}
		}
	}

	public List<AccessEntry> list(AccessEntryQuery query) {
		query.ensureInitialized();
		List<AccessEntry> list = accessEntryMapper.getAccessEntries(query);
		return list;
	}

	@Transactional
	public void saveAccessEntry(AccessEntry accessEntry) {
		if (StringUtils.isNotEmpty(accessEntry.getId())) {
			accessPointMapper.deleteAccessPoints(accessEntry.getId());
			accessEntryMapper.deleteAccessEntryById(accessEntry.getId());
		}

		accessEntry.setId(idGenerator.getNextId());
		accessEntry.setCreateDate(new Date());
		accessEntryMapper.insertAccessEntry(accessEntry);

		if (SystemConfig.getBoolean("use_query_cache")) {
			String cacheKey = "x_acs_proc_" + accessEntry.getProcessName()
					+ "_" + accessEntry.getTaskName();
			CacheFactory.remove(cacheKey);

			cacheKey = "x_acs_app_" + accessEntry.getApplicationName();
			CacheFactory.remove(cacheKey);

			cacheKey = "x_acs_proc_" + accessEntry.getProcessName();
			CacheFactory.remove(cacheKey);
		}

		Map<String, AccessPoint> accessPoints = accessEntry.getAccessPoints();
		if (accessPoints != null) {
			Set<Entry<String, AccessPoint>> entrySet = accessPoints.entrySet();
			for (Entry<String, AccessPoint> entry : entrySet) {
				AccessPoint accessPoint = entry.getValue();
				if (accessPoint != null) {
					if (accessPoint.getId() == null) {
						accessPoint.setId(idGenerator.getNextId());
					}
					accessPoint.setAccessEntry(accessEntry);
					accessPoint.setAccessEntryId(accessEntry.getId());
					accessPointMapper.insertAccessPoint(accessPoint);
				}
			}
		}
	}

	@Transactional
	public void saveAll(List<AccessEntry> rows) {
		Iterator<AccessEntry> iterator88 = rows.iterator();
		while (iterator88.hasNext()) {
			AccessEntry accessEntry = iterator88.next();
			if (StringUtils.isNotEmpty(accessEntry.getProcessName())
					&& StringUtils.isNotEmpty(accessEntry.getTaskName())) {
				AccessEntryQuery query = new AccessEntryQuery();
				query.processName(accessEntry.getProcessName());
				query.taskName(accessEntry.getTaskName());
				List<AccessEntry> x_rows = this.list(query);
				if (x_rows != null && x_rows.size() > 0) {
					for (AccessEntry a : rows) {
						accessPointMapper.deleteAccessPoints(a.getId());
						accessEntryMapper.deleteAccessEntryById(a.getId());
					}
				}
			}

			accessEntry.setCreateDate(new Date());
			accessEntry.setId(idGenerator.getNextId());

			if (SystemConfig.getBoolean("use_query_cache")) {
				String cacheKey = "x_acs_proc_" + accessEntry.getProcessName()
						+ "_" + accessEntry.getTaskName();
				CacheFactory.remove(cacheKey);

				cacheKey = "x_acs_app_" + accessEntry.getApplicationName();
				CacheFactory.remove(cacheKey);

				cacheKey = "x_acs_proc_" + accessEntry.getProcessName();
				CacheFactory.remove(cacheKey);
			}

			accessEntryMapper.insertAccessEntry(accessEntry);

			Map<String, AccessPoint> accessPoints = accessEntry
					.getAccessPoints();
			if (accessPoints != null) {
				Set<Entry<String, AccessPoint>> entrySet = accessPoints
						.entrySet();
				for (Entry<String, AccessPoint> entry : entrySet) {
					AccessPoint accessPoint = entry.getValue();
					if (accessPoint != null) {
						if (accessPoint.getId() == null) {
							accessPoint.setId(idGenerator.getNextId());
						}
						accessPoint.setAccessEntry(accessEntry);
						accessPoint.setAccessEntryId(accessEntry.getId());
						accessPointMapper.insertAccessPoint(accessPoint);
					}
				}
			}
		}
	}

	@javax.annotation.Resource
	public void setAccessEntryMapper(AccessEntryMapper accessEntryMapper) {
		this.accessEntryMapper = accessEntryMapper;
	}

	@javax.annotation.Resource
	public void setAccessPointMapper(AccessPointMapper accessPointMapper) {
		this.accessPointMapper = accessPointMapper;
	}

	@javax.annotation.Resource
	public void setDataAccessMapper(DataAccessMapper dataAccessMapper) {
		this.dataAccessMapper = dataAccessMapper;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}