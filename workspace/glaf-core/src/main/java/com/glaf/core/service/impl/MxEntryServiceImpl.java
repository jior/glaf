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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.TreeModel;
import com.glaf.core.cache.CacheFactory;

import com.glaf.core.domain.EntityEntry;
import com.glaf.core.domain.EntryPoint;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.EntityEntryMapper;
import com.glaf.core.query.EntityEntryQuery;
import com.glaf.core.query.EntryPointQuery;
import com.glaf.core.query.TreeModelQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IEntryService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.util.Tools;

@Service("entryService")
@Transactional(readOnly = true)
public class MxEntryServiceImpl implements IEntryService {
	protected final static Log logger = LogFactory
			.getLog(MxEntryServiceImpl.class);

	protected EntityDAO entityDAO;

	protected EntityEntryMapper entityEntryMapper;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected ITreeModelService treeModelService;

	public MxEntryServiceImpl() {

	}

	public int count(EntityEntryQuery query) {
		query.ensureInitialized();
		return entityEntryMapper.getEntityEntryCount(query);
	}

	/**
	 * 删除记录
	 * 
	 * @param rowId
	 */
	@Transactional
	public void deleteEntityEntry(String id) {
		entityEntryMapper.deleteEntityEntryById(id);
		entityEntryMapper.deleteEntryPoint(id);
	}

	public List<EntityEntry> getEntityEntries(Map<String, Object> paramMap) {
		List<EntityEntry> list = new ArrayList<EntityEntry>();
		EntityEntryQuery query = new EntityEntryQuery();
		Tools.populate(query, paramMap);
		List<EntityEntry> rows = this.list(query);
		if (rows != null && rows.size() > 0) {
			Iterator<EntityEntry> iterator = rows.iterator();
			while (iterator.hasNext()) {
				EntityEntry entityEntry = iterator.next();
				this.initializeEntryPoints(entityEntry);
				list.add(entityEntry);
			}
		}
		return list;
	}

	public EntityEntry getEntityEntry(int nodeId, String entryKey) {
		EntityEntry entityEntry = null;
		if (StringUtils.isNotEmpty(entryKey)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nodeId", nodeId);
			params.put("entryKey", entryKey);
			List<EntityEntry> rows = this.getEntityEntries(params);
			if (rows != null && rows.size() > 0) {
				entityEntry = rows.get(0);
				this.initializeEntryPoints(entityEntry);
			}
		}
		return entityEntry;
	}

	/**
	 * 删除记录
	 * 
	 * @param id
	 */

	public EntityEntry getEntityEntry(String id) {
		EntityEntry entityEntry = entityEntryMapper.getEntityEntryById(id);
		this.initializeEntryPoints(entityEntry);
		return entityEntry;
	}

	public EntityEntry getEntityEntry(String moduleId, String entityId,
			String entryKey) {
		EntityEntry entityEntry = null;
		if (StringUtils.isNotEmpty(moduleId)
				&& StringUtils.isNotEmpty(entityId)
				&& StringUtils.isNotEmpty(entryKey)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("entityId", entityId);
			params.put("entryKey", entryKey);
			params.put("moduleId", moduleId);
			List<EntityEntry> rows = this.getEntityEntries(params);
			if (rows != null && rows.size() > 0) {
				entityEntry = rows.get(0);
				this.initializeEntryPoints(entityEntry);
			}
		}
		return entityEntry;
	}

	/**
	 * 获取某个用户能访问的记录集合
	 * 
	 * 
	 * @param loginContext
	 *            用户上下文
	 * @param moduleId
	 *            模块标识
	 * @param entryKey
	 *            权限
	 * @return
	 */
	public List<String> getEntityIds(LoginContext loginContext,
			String moduleId, String entryKey) {
		String cacheKey = "eix_" + loginContext.getActorId() + "_" + moduleId
				+ "_" + entryKey;
		if (CacheFactory.get(cacheKey) != null) {

		}
		List<String> entityIds = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("moduleId", moduleId);
		params.put("entryKey", entryKey);

		/**
		 * 角色拥有的记录编号
		 */
		params.put("name", "ROLE");
		params.put("values", loginContext.getRoles());

		List<EntryPoint> entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				entityIds.add(entryPoint.getEntityId());
			}
		}

		/**
		 * 部门拥有的记录编号
		 */
		params.remove("values");
		params.put("name", "DEPT");
		params.put("value", loginContext.getDeptId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				entityIds.add(entryPoint.getEntityId());
			}
		}

		/**
		 * 用户拥有的记录编号
		 */
		params.put("name", "USER");
		params.put("value", loginContext.getActorId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				entityIds.add(entryPoint.getEntityId());
			}
		}

		return entityIds;
	}

	public List<EntryPoint> getEntityPoints(Map<String, Object> paramMap) {
		List<EntryPoint> list = new ArrayList<EntryPoint>();
		EntryPointQuery query = new EntryPointQuery();
		Tools.populate(query, paramMap);

		List<EntryPoint> rows = this.getEntryPoints(query);

		if (rows != null && rows.size() > 0) {
			Iterator<EntryPoint> iterator = rows.iterator();
			while (iterator.hasNext()) {
				EntryPoint entryPoint = iterator.next();
				list.add(entryPoint);
			}
		}

		return list;
	}

	/**
	 * 获取某个用户某个模块的访问权限
	 * 
	 * @param loginContext
	 * @param moduleId
	 * @return
	 */
	public List<String> getEntryKeys(LoginContext loginContext, String moduleId) {
		String cacheKey = "ecx_" + loginContext.getActorId() + "_" + moduleId;
		if (CacheFactory.get(cacheKey) != null) {

		}
		List<String> entryKeys = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("moduleId", moduleId);

		/**
		 * 角色拥有的记录编号
		 */
		params.put("name", "ROLE");
		params.put("values", loginContext.getRoles());

		List<EntryPoint> entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				entryKeys.add(entryPoint.getEntryKey());
			}
		}

		/**
		 * 部门拥有的记录编号
		 */
		params.remove("values");
		params.put("name", "DEPT");
		params.put("value", loginContext.getDeptId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				entryKeys.add(entryPoint.getEntryKey());
			}
		}

		/**
		 * 用户拥有的记录编号
		 */
		params.put("name", "USER");
		params.put("value", loginContext.getActorId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				entryKeys.add(entryPoint.getEntryKey());
			}
		}

		// CacheFactory.put(cacheKey, entryKeys);

		return entryKeys;
	}

	public List<EntryPoint> getEntryPoints(EntryPointQuery query) {
		List<EntryPoint> list = entityEntryMapper.getEntryPoints(query);
		return list;
	}

	/**
	 * 获取某个用户能访问的节点集合
	 * 
	 * @param loginContext
	 *            用户上下文
	 * @return
	 */
	public List<TreeModel> getTreeModels(LoginContext loginContext) {
		String cacheKey = "cx_" + loginContext.getActorId();
		if (CacheFactory.get(cacheKey) != null) {

		}
		List<TreeModel> treeNodes = new ArrayList<TreeModel>();
		List<Long> nodeIds = new ArrayList<Long>();
		Map<String, Object> params = new HashMap<String, Object>();

		/**
		 * 角色拥有的节点
		 */
		params.put("name", "ROLE");
		params.put("values", loginContext.getRoles());

		List<EntryPoint> entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				nodeIds.add(entryPoint.getNodeId());
			}
		}

		/**
		 * 部门拥有的节点
		 */
		params.remove("values");
		params.put("name", "DEPT");
		params.put("value", loginContext.getDeptId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				nodeIds.add(entryPoint.getNodeId());
			}
		}

		/**
		 * 用户拥有的节点
		 */
		params.put("name", "USER");
		params.put("value", loginContext.getActorId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				nodeIds.add(entryPoint.getNodeId());
			}
		}

		if (nodeIds.size() > 0) {
			params.clear();
			params.put("nodeIds", nodeIds);
			List<EntityEntry> list = this.getEntityEntries(params);
			Iterator<EntityEntry> iter = list.iterator();
			while (iter.hasNext()) {
				EntityEntry entityEntry = iter.next();
				if (entityEntry.isPropagationAllowed()) {
					List<TreeModel> children = treeModelService
							.getChildrenTreeModels(entityEntry.getNodeId());
					if (children != null && children.size() > 0) {
						Iterator<TreeModel> it = children.iterator();
						while (it.hasNext()) {
							TreeModel nd = it.next();
							nodeIds.add(nd.getId());
						}
					}
				}
			}
			TreeModelQuery query = new TreeModelQuery();
			query.nodeIds(nodeIds);
			treeNodes = treeModelService.getTreeModels(query);
		}

		// CacheFactory.put(cacheKey, treeNodes);
		return treeNodes;
	}

	/**
	 * 检查某个用户是否有某个分类的某个权限
	 * 
	 * @param loginContext
	 *            用户上下文
	 * @param nodeId
	 *            分类编号
	 * @param permKey
	 *            权限点
	 * @return
	 */
	public boolean hasPermission(LoginContext loginContext, int nodeId,
			String permKey) {
		boolean hasPermission = false;
		if (StringUtils.equals(loginContext.getActorId(), "admin")) {
			return true;
		}
		if (loginContext.isSystemAdministrator()) {
			return true;
		}
		if (loginContext.hasAdvancedPermission()) {
			return true;
		}
		TreeModel treeNode = treeModelService.getTreeModel(nodeId);
		if (treeNode != null) {
			if (StringUtils.equals(treeNode.getCreateBy(),
					loginContext.getActorId())) {
				return true;
			}
		}

		String cacheKey = "CX_" + permKey + "_" + nodeId + "_"
				+ loginContext.getActorId();
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug(cacheKey + " has caching.");

		}

		/**
		 * 角色是否具有该节点的权限
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nodeId", nodeId);
		params.put("entryKey", permKey);
		params.put("name", "ROLE");
		params.put("values", loginContext.getRoles());

		List<EntryPoint> entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {

			return true;
		}

		/**
		 * 部门是否具有该节点的权限
		 */
		params.remove("values");
		params.put("name", "DEPT");
		params.put("value", loginContext.getDeptId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			// CacheFactory.put(cacheKey, true);
			return true;
		}

		/**
		 * 用户是否具有该节点的权限
		 */
		params.put("name", "USER");
		params.put("value", loginContext.getActorId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			// CacheFactory.put(cacheKey, true);
			return true;
		}

		/**
		 * 查找该节点的所有上级节点，判断是否有权限
		 */
		List<TreeModel> treeNodes = treeModelService
				.getAncestorTreeModels(nodeId);
		if (treeNodes != null && treeNodes.size() > 0) {
			List<Long> nodeIds = new ArrayList<Long>();
			Iterator<TreeModel> it = treeNodes.iterator();
			while (it.hasNext()) {
				TreeModel nd = it.next();
				nodeIds.add(nd.getId());
			}
			params.clear();
			params.put("entryKey", permKey);
			params.put("nodeIds", nodeIds);
			List<EntityEntry> list = this.getEntityEntries(params);
			Iterator<EntityEntry> iter = list.iterator();
			while (iter.hasNext()) {
				EntityEntry entityEntry = iter.next();
				if (entityEntry.isPropagationAllowed()) {
					hasPermission = true;
					// CacheFactory.put(cacheKey, true);
					break;
				}
			}
		}

		// CacheFactory.put(cacheKey, hasPermission);

		return hasPermission;
	}

	/**
	 * 检查某个用户是否有某个记录的某个权限
	 * 
	 * @param loginContext
	 *            用户上下文
	 * @param moduleId
	 *            模块标识
	 * @param entityId
	 *            记录编号
	 * 
	 * @param permKey
	 *            权限点
	 * @return
	 */
	public boolean hasPermission(LoginContext loginContext, String moduleId,
			String entityId, String permKey) {
		boolean hasPermission = false;
		if (StringUtils.equals(loginContext.getActorId(), "admin")) {
			return true;
		}
		if (loginContext.isSystemAdministrator()) {
			return true;
		}
		if (loginContext.hasAdvancedPermission()) {
			return true;
		}

		// String cacheKey = "PX_" + moduleId + "_" + permKey + "_" + entityId
		// + "_" + loginContext.getActorId();

		/**
		 * 角色是否具有该记录的权限
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entityId", entityId);
		params.put("moduleId", moduleId);
		params.put("entryKey", permKey);
		params.put("name", "ROLE");
		params.put("values", loginContext.getRoles());

		List<EntryPoint> entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			// CacheFactory.put(cacheKey, true);
			return true;
		}

		/**
		 * 部门是否具有该记录的权限
		 */
		params.remove("values");
		params.put("name", "DEPT");
		params.put("value", loginContext.getDeptId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			// CacheFactory.put(cacheKey, true);
			return true;
		}

		/**
		 * 用户是否具有该记录的权限
		 */
		params.put("name", "USER");
		params.put("value", loginContext.getActorId());

		entryPoints = this.getEntityPoints(params);
		if (entryPoints != null && entryPoints.size() > 0) {
			// CacheFactory.put(cacheKey, true);
			return true;
		}

		// CacheFactory.put(cacheKey, hasPermission);

		return hasPermission;
	}

	protected void initializeEntryPoints(EntityEntry entityEntry) {
		if (entityEntry != null) {
			EntryPointQuery query = new EntryPointQuery();
			query.entityEntryId(entityEntry.getId());
			List<EntryPoint> entryPoints = this.getEntryPoints(query);
			entityEntry.setEntryPoints(entryPoints);
		}
	}

	public List<EntityEntry> list(EntityEntryQuery query) {
		List<EntityEntry> list = entityEntryMapper.getEntityEntries(query);
		return list;
	}

	/**
	 * 保存记录
	 * 
	 * @param entityEntry
	 */
	@Transactional
	public void saveEntityEntry(EntityEntry entityEntry) {
		entityEntry.setId(idGenerator.getNextId());
		entityEntry.setCreateDate(new Date());
		entityEntryMapper.insertEntityEntry(entityEntry);
		if (entityEntry.getEntryPoints() != null) {
			Iterator<EntryPoint> iter = entityEntry.getEntryPoints().iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				entryPoint.setId(idGenerator.getNextId());
				entryPoint.setEntityEntry(entityEntry);
				entryPoint.setEntryKey(entityEntry.getEntryKey());
				entryPoint.setEntityId(entityEntry.getEntityId());
				entryPoint.setNodeId(entityEntry.getNodeId());
				entryPoint.setEntityEntryId(entityEntry.getId());
				entityEntryMapper.insertEntryPoint(entryPoint);
			}
		}
	}

	@Resource
	@Qualifier("myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Resource
	public void setEntityEntryMapper(EntityEntryMapper entityEntryMapper) {
		this.entityEntryMapper = entityEntryMapper;
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

}