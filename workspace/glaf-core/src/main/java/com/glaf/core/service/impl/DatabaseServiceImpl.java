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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.DatabaseAccess;
import com.glaf.core.mapper.DatabaseAccessMapper;
import com.glaf.core.mapper.DatabaseMapper;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.domain.util.DatabaseAccessJsonFactory;
import com.glaf.core.domain.util.DatabaseJsonFactory;

@Service("databaseService")
@Transactional(readOnly = true)
public class DatabaseServiceImpl implements IDatabaseService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DatabaseMapper databaseMapper;

	protected DatabaseAccessMapper databaseAccessMapper;

	public DatabaseServiceImpl() {

	}

	public int count(DatabaseQuery query) {
		return databaseMapper.getDatabaseCount(query);
	}

	@Transactional
	public void createAccessor(long databaseId, String actorId) {
		String cacheKey = "sys_db_" + databaseId;
		CacheFactory.remove(cacheKey);
		cacheKey = "sys_dbaccess_" + databaseId;
		CacheFactory.remove(cacheKey);
		cacheKey = "sys_db_actor_" + actorId;
		CacheFactory.remove(cacheKey);
		DatabaseAccess model = new DatabaseAccess();
		model.setId(idGenerator.nextId("SYS_DATABASE_ACCESS"));
		model.setActorId(actorId);
		model.setDatabaseId(databaseId);
		databaseAccessMapper.insertDatabaseAccess(model);
	}

	@Transactional
	public void deleteAccessor(long databaseId, String actorId) {
		String cacheKey = "sys_db_" + databaseId;
		CacheFactory.remove(cacheKey);
		cacheKey = "sys_dbaccess_" + databaseId;
		CacheFactory.remove(cacheKey);
		cacheKey = "sys_db_actor_" + actorId;
		CacheFactory.remove(cacheKey);
		DatabaseAccess model = new DatabaseAccess();
		model.setActorId(actorId);
		model.setDatabaseId(databaseId);
		databaseAccessMapper.deleteAccessor(model);
	}

	@Transactional
	public void deleteById(Long databaseId) {
		if (databaseId != null) {
			String cacheKey = "sys_db_" + databaseId;
			CacheFactory.remove(cacheKey);
			cacheKey = "sys_dbaccess_" + databaseId;
			CacheFactory.remove(cacheKey);
			databaseMapper.deleteDatabaseById(databaseId);
			databaseAccessMapper.deleteDatabaseAccessByDatabaseId(databaseId);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long databaseId : ids) {
				String cacheKey = "sys_db_" + databaseId;
				CacheFactory.remove(cacheKey);
				cacheKey = "sys_dbaccess_" + databaseId;
				CacheFactory.remove(cacheKey);
				databaseMapper.deleteDatabaseById(databaseId);
				databaseAccessMapper
						.deleteDatabaseAccessByDatabaseId(databaseId);
			}
		}
	}

	public List<DatabaseAccess> getAllDatabaseAccesses() {
		return databaseAccessMapper.getAllDatabaseAccesses();
	}

	public Database getDatabase(Long databaseId) {
		if (databaseId == null || databaseId == 0) {
			return null;
		}
		Database database = databaseMapper.getDatabaseById(databaseId);
		return database;
	}

	public Database getDatabaseById(Long databaseId) {
		if (databaseId == null || databaseId == 0) {
			return null;
		}
		String cacheKey = "sys_db_" + databaseId;
		String text = CacheFactory.getString(cacheKey);
		if (StringUtils.isNotEmpty(text)) {
			try {
				JSONObject json = JSON.parseObject(text);
				return DatabaseJsonFactory.jsonToObject(json);
			} catch (Exception ex) {
			}
		}
		Database database = databaseMapper.getDatabaseById(databaseId);
		if (database != null) {
			List<DatabaseAccess> accesses = databaseAccessMapper
					.getDatabaseAccessesByDatabaseId(databaseId);
			if (accesses != null && !accesses.isEmpty()) {
				for (DatabaseAccess access : accesses) {
					database.addAccessor(access.getActorId());
				}
			}
			CacheFactory.put(cacheKey, database.toJsonObject().toJSONString());
		}
		return database;
	}

	private List<DatabaseAccess> getDatabaseAccesses(long databaseId) {
		String cacheKey = "sys_dbaccess_" + databaseId;
		String text = CacheFactory.getString(cacheKey);
		if (StringUtils.isNotEmpty(text)) {
			try {
				JSONArray array = JSON.parseArray(text);
				return DatabaseAccessJsonFactory.arrayToList(array);
			} catch (Exception ex) {
			}
		}
		List<DatabaseAccess> accesses = databaseAccessMapper
				.getDatabaseAccessesByDatabaseId(databaseId);
		if (accesses != null && !accesses.isEmpty()) {
			JSONArray array = DatabaseAccessJsonFactory.listToArray(accesses);
			CacheFactory.put(cacheKey, array.toJSONString());
		}
		return accesses;
	}

	/**
	 * 根据编码获取一条记录
	 * 
	 * @return
	 */
	public Database getDatabaseByCode(String code) {
		DatabaseQuery query = new DatabaseQuery();
		List<Database> list = databaseMapper.getDatabases(query);
		if (list != null && !list.isEmpty()) {
			for (Database database : list) {
				if (StringUtils.equals(database.getActive(), "1")) {
					List<DatabaseAccess> accesses = this
							.getDatabaseAccesses(database.getId());
					database.setAccesses(accesses);
					return database;
				}
			}
		}
		return null;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDatabaseCountByQueryCriteria(DatabaseQuery query) {
		return databaseMapper.getDatabaseCount(query);
	}

	public List<Database> getDatabases() {
		List<Database> databases = new ArrayList<Database>();
		DatabaseQuery query = new DatabaseQuery();
		List<Database> list = databaseMapper.getDatabases(query);
		if (list != null && !list.isEmpty()) {
			for (Database database : list) {
				if (StringUtils.equals(database.getActive(), "1")) {
					List<DatabaseAccess> accesses = this
							.getDatabaseAccesses(database.getId());
					database.setAccesses(accesses);
					if (accesses != null && !accesses.isEmpty()) {
						for (DatabaseAccess access : accesses) {
							database.addAccessor(access.getActorId());
						}
					}
					databases.add(database);
				}
			}
		}
		logger.debug("repos size:" + databases.size());
		if (!databases.isEmpty()) {
			Collections.sort(databases);
		}
		return databases;
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<Database> getDatabases(String actorId) {
		List<Database> databases = new ArrayList<Database>();
		DatabaseQuery query = new DatabaseQuery();
		query.setCreateBy(actorId);
		List<Database> list = databaseMapper.getDatabases(query);
		if (list != null && !list.isEmpty()) {
			for (Database database : list) {
				if (StringUtils.equals(database.getActive(), "1")) {
					List<DatabaseAccess> accesses = this
							.getDatabaseAccesses(database.getId());
					database.setAccesses(accesses);
					if (accesses != null && !accesses.isEmpty()) {
						for (DatabaseAccess access : accesses) {
							database.addAccessor(access.getActorId());
						}
					}
					databases.add(database);
				}
			}
		}

		List<DatabaseAccess> accesses = databaseAccessMapper
				.getDatabaseAccessesByActorId(actorId);
		if (accesses != null && !accesses.isEmpty()) {
			for (DatabaseAccess access : accesses) {
				Database database = this
						.getDatabaseById(access.getDatabaseId());
				if (StringUtils.equals(database.getActive(), "1")) {
					databases.add(database);
				}
			}
		}

		return databases;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Database> getDatabasesByQueryCriteria(int start, int pageSize,
			DatabaseQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Database> rows = sqlSessionTemplate.selectList("getDatabases",
				query, rowBounds);
		return rows;
	}

	public List<Database> list(DatabaseQuery query) {
		List<Database> list = databaseMapper.getDatabases(query);
		for (Database database : list) {
			if (StringUtils.equals(database.getActive(), "1")) {
				List<DatabaseAccess> accesses = this
						.getDatabaseAccesses(database.getId());
				database.setAccesses(accesses);
				if (accesses != null && !accesses.isEmpty()) {
					for (DatabaseAccess access : accesses) {
						database.addAccessor(access.getActorId());
					}
				}
			}
		}
		return list;
	}

	@Transactional
	public void save(Database database) {
		String password = database.getPassword();
		if (database.getId() == 0) {
			if (!"88888888".equals(password)) {
				String key = SecurityUtils.genKey();
				String pass = SecurityUtils.encode(key, password);
				database.setKey(key);
				database.setPassword(pass);
			}
			database.setId(idGenerator.nextId("SYS_DATABASE"));
			database.setName("repo_db_" + database.getId());
			database.setCode("repo_db_" + database.getId());
			database.setCreateTime(new Date());
			databaseMapper.insertDatabase(database);
		} else {
			Database model = this.getDatabase(database.getId());
			model.setId(database.getId());
			model.setTitle(database.getTitle());
			model.setProviderClass(database.getProviderClass());
			model.setLevel(database.getLevel());
			model.setPriority(database.getPriority());
			model.setOperation(database.getOperation());
			model.setHost(database.getHost());
			model.setPort(database.getPort());
			model.setActive(database.getActive());
			model.setNodeId(database.getNodeId());
			model.setUpdateTime(new Date());

			if (!"88888888".equals(password)) {
				String key = SecurityUtils.genKey();
				String pass = SecurityUtils.encode(key, password);
				model.setKey(key);
				model.setPassword(pass);
			}
			/**
			 * 只有没有初始化时可以更新库名及表名
			 */
			if (!StringUtils.equals(model.getInitFlag(), "Y")) {
				model.setDbname(database.getDbname());
			}
			databaseMapper.updateDatabase(model);
			String cacheKey = "sys_db_" + model.getId();
			CacheFactory.remove(cacheKey);
			cacheKey = "sys_dbaccess_" + model.getId();
			CacheFactory.remove(cacheKey);
		}

		databaseAccessMapper.deleteDatabaseAccessByDatabaseId(database.getId());
		if (database.getActorIds() != null && database.getActorIds().isEmpty()) {
			for (String actorId : database.getActorIds()) {
				DatabaseAccess access = new DatabaseAccess();
				access.setId(idGenerator.nextId("SYS_DATABASE_ACCESS"));
				access.setActorId(actorId);
				access.setDatabaseId(database.getId());
				databaseAccessMapper.insertDatabaseAccess(access);
			}
		}
	}

	/**
	 * 保存数据库访问者
	 * 
	 * @return
	 */
	@Transactional
	public void saveAccessors(long databaseId, Collection<String> accessors) {
		databaseAccessMapper.deleteDatabaseAccessByDatabaseId(databaseId);
		for (String actorId : accessors) {
			String cacheKey = "sys_db_" + databaseId;
			CacheFactory.remove(cacheKey);
			cacheKey = "sys_dbaccess_" + databaseId;
			CacheFactory.remove(cacheKey);
			cacheKey = "sys_db_actor_" + actorId;
			CacheFactory.remove(cacheKey);
			DatabaseAccess access = new DatabaseAccess();
			access.setId(idGenerator.nextId("SYS_DATABASE_ACCESS"));
			access.setActorId(actorId);
			access.setDatabaseId(databaseId);
			databaseAccessMapper.insertDatabaseAccess(access);
		}
	}

	/**
	 * 保存数据库访问者
	 * 
	 * @return
	 */
	@Transactional
	public void saveAccessors(String accessor, Collection<Long> databaseIds) {
		databaseAccessMapper.deleteDatabaseAccessByActorId(accessor);
		for (Long databaseId : databaseIds) {
			String cacheKey = "sys_db_" + databaseId;
			CacheFactory.remove(cacheKey);
			cacheKey = "sys_dbaccess_" + databaseId;
			CacheFactory.remove(cacheKey);
			cacheKey = "sys_db_actor_" + accessor;
			CacheFactory.remove(cacheKey);
			DatabaseAccess access = new DatabaseAccess();
			access.setId(idGenerator.nextId("SYS_DATABASE_ACCESS"));
			access.setActorId(accessor);
			access.setDatabaseId(databaseId);
			databaseAccessMapper.insertDatabaseAccess(access);
		}
	}

	@javax.annotation.Resource
	public void setDatabaseAccessMapper(
			DatabaseAccessMapper databaseAccessMapper) {
		this.databaseAccessMapper = databaseAccessMapper;
	}

	@javax.annotation.Resource
	public void setDatabaseMapper(DatabaseMapper databaseMapper) {
		this.databaseMapper = databaseMapper;
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
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	public void update(Database database) {
		String cacheKey = "sys_db_" + database.getId();
		CacheFactory.remove(cacheKey);
		cacheKey = "sys_dbaccess_" + database.getId();
		CacheFactory.remove(cacheKey);
		Database model = this.getDatabase(database.getId());
		model.setActive(database.getActive());
		model.setVerify(database.getVerify());
		model.setInitFlag(database.getInitFlag());
		databaseMapper.updateDatabase(model);
	}

}
