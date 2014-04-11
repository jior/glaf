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

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.TreeModel;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.EntityEntry;
import com.glaf.core.domain.EntryPoint;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.EntityEntryMapper;
import com.glaf.core.query.EntityEntryQuery;
import com.glaf.core.query.EntryPointQuery;
import com.glaf.core.query.TreeModelQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IEntryService;
import com.glaf.core.service.ITreeModelService;

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
	 * ɾ����¼
	 * 
	 * @param rowId
	 */
	@Transactional
	public void deleteEntityEntry(String id) {
		entityEntryMapper.deleteEntryPoint(id);
		entityEntryMapper.deleteEntityEntryById(id);
	}

	public EntityEntry getEntityEntry(long nodeId, String entryKey) {
		EntityEntry entityEntry = null;
		if (StringUtils.isNotEmpty(entryKey)) {
			EntityEntryQuery query = new EntityEntryQuery();
			query.nodeId(nodeId);
			query.entryKey(entryKey);
			List<EntityEntry> rows = this.list(query);
			if (rows != null && rows.size() > 0) {
				entityEntry = rows.get(0);
				this.initializeEntryPoints(entityEntry);
			}
		}
		return entityEntry;
	}

	/**
	 * ɾ����¼
	 * 
	 * @param id
	 */

	public EntityEntry getEntityEntry(String id) {
		EntityEntry entityEntry = entityEntryMapper.getEntityEntryById(id);
		if (entityEntry != null) {
			this.initializeEntryPoints(entityEntry);
		}
		return entityEntry;
	}

	public EntityEntry getEntityEntry(String moduleId, String entityId,
			String entryKey) {
		EntityEntry entityEntry = null;
		if (StringUtils.isNotEmpty(moduleId)
				&& StringUtils.isNotEmpty(entityId)
				&& StringUtils.isNotEmpty(entryKey)) {
			EntityEntryQuery query = new EntityEntryQuery();
			query.entityId(entityId);
			query.moduleId(moduleId);
			query.entryKey(entryKey);
			List<EntityEntry> rows = entityEntryMapper.getEntityEntries(query);
			if (rows != null && rows.size() > 0) {
				entityEntry = rows.get(0);
				this.initializeEntryPoints(entityEntry);
			}
		}
		return entityEntry;
	}

	/**
	 * ��ȡĳ���û��ܷ��ʵļ�¼����
	 * 
	 * @param loginContext
	 *            �û�������
	 * @param moduleId
	 *            ģ���ʶ
	 * @param entryKey
	 *            Ȩ��
	 * @return
	 */
	public List<String> getEntityIds(LoginContext loginContext,
			String moduleId, String entryKey) {
		String cacheKey = "mx_" + loginContext.getActorId() + "_" + moduleId
				+ "_" + entryKey;
		if (CacheFactory.get(cacheKey) != null) {

		}
		List<String> entityIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		Map<String, Object> params = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		List<EntryPoint> entryPoints = null;

		params.put("moduleId", moduleId);
		params.put("entryKey", entryKey);

		EntryPointQuery query = new EntryPointQuery();

		query.moduleId(moduleId);
		query.entryKey(entryKey);

		/**
		 * ��ɫӵ�еļ�¼���
		 */
		params.put("name", "ROLE");
		params.put("value", loginContext.getRoles());

		Collection<Long> roleIds = loginContext.getRoleIds();
		if (roleIds != null && !roleIds.isEmpty()) {
			query.name("ROLE");
			for (Long roleId : roleIds) {
				query.value(String.valueOf(roleId));
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					Iterator<EntryPoint> iter = entryPoints.iterator();
					while (iter.hasNext()) {
						EntryPoint entryPoint = iter.next();
						entityIds.add(entryPoint.getEntityId());
					}
				}
			}
		}

		Collection<String> roles = loginContext.getRoles();
		if (roles != null && !roles.isEmpty()) {
			query.name("ROLE");
			for (String role : roles) {
				query.value(role);
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					Iterator<EntryPoint> iter = entryPoints.iterator();
					while (iter.hasNext()) {
						EntryPoint entryPoint = iter.next();
						entityIds.add(entryPoint.getEntityId());
					}
				}
			}
		}

		if (loginContext.getDeptId() != null) {
			/**
			 * ����ӵ�еļ�¼���
			 */
			params.remove("values");
			params.put("name", "DEPT");
			params.put("value", loginContext.getDeptId());
			query.name("DEPT");
			query.value(String.valueOf(loginContext.getDeptId()));

			entryPoints = entityEntryMapper.getEntryPoints(query);
			if (entryPoints != null && entryPoints.size() > 0) {
				Iterator<EntryPoint> iter = entryPoints.iterator();
				while (iter.hasNext()) {
					EntryPoint entryPoint = iter.next();
					entityIds.add(entryPoint.getEntityId());
				}
			}
		}

		/**
		 * �û�ӵ�еļ�¼���
		 */
		params.put("name", "USER");
		params.put("value", loginContext.getActorId());
		query.name("USER");
		query.value(loginContext.getActorId());

		entryPoints = entityEntryMapper.getEntryPoints(query);
		if (entryPoints != null && entryPoints.size() > 0) {
			Iterator<EntryPoint> iter = entryPoints.iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				entityIds.add(entryPoint.getEntityId());
			}
		}

		return entityIds;
	}

	/**
	 * ��ȡĳ���û�ĳ��ģ��ķ���Ȩ��
	 * 
	 * @param loginContext
	 * @param moduleId
	 * @return
	 */
	public List<String> getEntryKeys(LoginContext loginContext, String moduleId) {
		String cacheKey = "mx_ek_" + loginContext.getActorId() + "_" + moduleId;
		if (CacheFactory.get(cacheKey) != null) {

		}
		List<String> entryKeys = new java.util.concurrent.CopyOnWriteArrayList<String>();
		Map<String, Object> params = new java.util.concurrent.ConcurrentHashMap<String, Object>();

		params.put("moduleId", moduleId);

		List<EntryPoint> entryPoints = null;
		EntryPointQuery query = new EntryPointQuery();

		query.moduleId(moduleId);

		/**
		 * ��ɫӵ�еļ�¼���
		 */
		params.put("name", "ROLE");
		params.put("values", loginContext.getRoles());

		Collection<Long> roleIds = loginContext.getRoleIds();
		if (roleIds != null && !roleIds.isEmpty()) {
			query.name("ROLE");
			for (Long roleId : roleIds) {
				query.value(String.valueOf(roleId));
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					Iterator<EntryPoint> iter = entryPoints.iterator();
					while (iter.hasNext()) {
						EntryPoint entryPoint = iter.next();
						entryKeys.add(entryPoint.getEntryKey());
					}
				}
			}
		}

		Collection<String> roles = loginContext.getRoles();
		if (roles != null && !roles.isEmpty()) {
			query.name("ROLE");
			for (String role : roles) {
				query.value(role);
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					Iterator<EntryPoint> iter = entryPoints.iterator();
					while (iter.hasNext()) {
						EntryPoint entryPoint = iter.next();
						entryKeys.add(entryPoint.getEntryKey());
					}
				}
			}
		}

		/**
		 * ����ӵ�еļ�¼���
		 */
		if (loginContext.getDeptId() != null) {
			params.remove("values");
			params.put("name", "DEPT");
			params.put("value", loginContext.getDeptId());

			query.name("DEPT");
			query.value(String.valueOf(loginContext.getDeptId()));

			entryPoints = entityEntryMapper.getEntryPoints(query);
			if (entryPoints != null && entryPoints.size() > 0) {
				Iterator<EntryPoint> iter = entryPoints.iterator();
				while (iter.hasNext()) {
					EntryPoint entryPoint = iter.next();
					entryKeys.add(entryPoint.getEntryKey());
				}
			}
		}

		/**
		 * �û�ӵ�еļ�¼���
		 */
		params.put("name", "USER");
		params.put("value", loginContext.getActorId());

		query.name("USER");
		query.value(loginContext.getActorId());

		entryPoints = entityEntryMapper.getEntryPoints(query);

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
	 * ��ȡĳ���û��ܷ��ʵĽڵ㼯��
	 * 
	 * @param loginContext
	 *            �û�������
	 * @return
	 */
	public List<TreeModel> getTreeModels(LoginContext loginContext) {
		String cacheKey = "mx_" + loginContext.getActorId();
		if (CacheFactory.get(cacheKey) != null) {

		}
		List<TreeModel> treeModels = new java.util.concurrent.CopyOnWriteArrayList<TreeModel>();
		List<Long> nodeIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		Map<String, Object> params = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		EntryPointQuery query = new EntryPointQuery();
		List<EntryPoint> entryPoints = null;

		/**
		 * ��ɫӵ�еĽڵ�
		 */
		params.put("name", "ROLE");
		params.put("values", loginContext.getRoles());

		Collection<Long> roleIds = loginContext.getRoleIds();
		if (roleIds != null && !roleIds.isEmpty()) {
			query.name("ROLE");
			for (Long roleId : roleIds) {
				query.value(String.valueOf(roleId));
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					Iterator<EntryPoint> iter = entryPoints.iterator();
					while (iter.hasNext()) {
						EntryPoint entryPoint = iter.next();
						nodeIds.add(entryPoint.getNodeId());
					}
				}
			}
		}

		Collection<String> roles = loginContext.getRoles();
		if (roles != null && !roles.isEmpty()) {
			query.name("ROLE");
			for (String role : roles) {
				query.value(role);
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					Iterator<EntryPoint> iter = entryPoints.iterator();
					while (iter.hasNext()) {
						EntryPoint entryPoint = iter.next();
						nodeIds.add(entryPoint.getNodeId());
					}
				}
			}
		}

		/**
		 * ����ӵ�еĽڵ�
		 */
		if (loginContext.getDeptId() != null) {
			params.remove("values");
			params.put("name", "DEPT");
			params.put("value", loginContext.getDeptId());

			query.name("DEPT");
			query.value(String.valueOf(loginContext.getDeptId()));

			entryPoints = entityEntryMapper.getEntryPoints(query);

			if (entryPoints != null && entryPoints.size() > 0) {
				Iterator<EntryPoint> iter = entryPoints.iterator();
				while (iter.hasNext()) {
					EntryPoint entryPoint = iter.next();
					nodeIds.add(entryPoint.getNodeId());
				}
			}
		}

		/**
		 * �û�ӵ�еĽڵ�
		 */

		params.put("name", "USER");
		params.put("value", loginContext.getActorId());

		query.name("USER");
		query.value(loginContext.getActorId());

		entryPoints = entityEntryMapper.getEntryPoints(query);
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
			EntityEntryQuery qx = new EntityEntryQuery();
			List<EntityEntry> list = this.list(qx);
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
			TreeModelQuery q = new TreeModelQuery();
			q.nodeIds(nodeIds);
			treeModels = treeModelService.getTreeModels(q);
		}

		return treeModels;
	}

	/**
	 * ���ĳ���û��Ƿ���ĳ�������ĳ��Ȩ��
	 * 
	 * @param loginContext
	 *            �û�������
	 * @param nodeId
	 *            ������
	 * @param permKey
	 *            Ȩ�޵�
	 * @return
	 */
	public boolean hasPermission(LoginContext loginContext, long nodeId,
			String permKey) {
		boolean hasPermission = false;

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
		 * ��ɫ�Ƿ���иýڵ��Ȩ��
		 */
		Map<String, Object> params = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		params.put("nodeId", nodeId);
		params.put("entryKey", permKey);

		List<EntryPoint> entryPoints = null;
		EntryPointQuery query = new EntryPointQuery();

		query.nodeId(nodeId);
		query.entryKey(permKey);

		/**
		 * ��ɫӵ�еļ�¼���
		 */
		params.put("name", "ROLE");
		params.put("values", loginContext.getRoles());

		Collection<Long> roleIds = loginContext.getRoleIds();
		if (roleIds != null && !roleIds.isEmpty()) {
			query.name("ROLE");
			for (Long roleId : roleIds) {
				query.value(String.valueOf(roleId));
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					hasPermission = true;
					break;
				}
			}
		}

		Collection<String> roles = loginContext.getRoles();
		if (roles != null && !roles.isEmpty()) {
			query.name("ROLE");
			for (String role : roles) {
				query.value(role);
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					hasPermission = true;
					break;
				}
			}
		}

		/**
		 * �����Ƿ���иýڵ��Ȩ��
		 */
		if (!hasPermission && loginContext.getDeptId() != null) {
			params.remove("values");
			params.put("name", "DEPT");
			params.put("value", loginContext.getDeptId());

			query.name("DEPT");
			query.value(String.valueOf(loginContext.getDeptId()));
			entryPoints = entityEntryMapper.getEntryPoints(query);
			if (entryPoints != null && entryPoints.size() > 0) {
				hasPermission = true;
			}
		}

		/**
		 * �û��Ƿ���иýڵ��Ȩ��
		 */
		if (!hasPermission) {
			params.put("name", "USER");
			params.put("value", loginContext.getActorId());

			query.name("USER");
			query.value(loginContext.getActorId());
			entryPoints = entityEntryMapper.getEntryPoints(query);
			if (entryPoints != null && entryPoints.size() > 0) {
				hasPermission = true;
			}
		}

		if (!hasPermission) {
			/**
			 * ���Ҹýڵ�������ϼ��ڵ㣬�ж��Ƿ���Ȩ��
			 */
			List<TreeModel> treeModels = treeModelService
					.getAncestorTreeModels(nodeId);
			if (treeModels != null && treeModels.size() > 0) {
				List<Long> nodeIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
				Iterator<TreeModel> it = treeModels.iterator();
				while (it.hasNext()) {
					TreeModel nd = it.next();
					nodeIds.add(nd.getId());
				}
				params.clear();
				params.put("entryKey", permKey);
				params.put("nodeIds", nodeIds);
				EntityEntryQuery qx = new EntityEntryQuery();
				qx.entryKey(permKey);
				qx.nodeIds(nodeIds);
				List<EntityEntry> list = this.list(qx);
				Iterator<EntityEntry> iter = list.iterator();
				while (iter.hasNext()) {
					EntityEntry entityEntry = iter.next();
					if (entityEntry.isPropagationAllowed()) {
						hasPermission = true;
						break;
					}
				}
			}
		}

		return hasPermission;
	}

	/**
	 * ���ĳ���û��Ƿ���ĳ����¼��ĳ��Ȩ��
	 * 
	 * @param loginContext
	 *            �û�������
	 * @param moduleId
	 *            ģ���ʶ
	 * @param entityId
	 *            ��¼���
	 * 
	 * @param permKey
	 *            Ȩ�޵�
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
		 * ��ɫ�Ƿ���иü�¼��Ȩ��
		 */
		Map<String, Object> params = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		params.put("entityId", entityId);
		params.put("moduleId", moduleId);
		params.put("entryKey", permKey);
		params.put("name", "ROLE");
		params.put("values", loginContext.getRoles());

		List<EntryPoint> entryPoints = null;
		EntryPointQuery query = new EntryPointQuery();

		query.moduleId(moduleId);
		query.entryKey(permKey);
		query.entityId(entityId);

		Collection<Long> roleIds = loginContext.getRoleIds();
		if (roleIds != null && !roleIds.isEmpty()) {
			query.name("ROLE");
			for (Long roleId : roleIds) {
				query.value(String.valueOf(roleId));
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					hasPermission = true;
					break;
				}
			}
		}

		Collection<String> roles = loginContext.getRoles();
		if (roles != null && !roles.isEmpty()) {
			query.name("ROLE");
			for (String role : roles) {
				query.value(role);
				entryPoints = entityEntryMapper.getEntryPoints(query);
				if (entryPoints != null && entryPoints.size() > 0) {
					hasPermission = true;
					break;
				}
			}
		}

		/**
		 * �����Ƿ���иýڵ��Ȩ��
		 */
		if (!hasPermission && loginContext.getDeptId() != null) {
			params.remove("values");
			params.put("name", "DEPT");
			params.put("value", loginContext.getDeptId());

			query.name("DEPT");
			query.value(String.valueOf(loginContext.getDeptId()));
			entryPoints = entityEntryMapper.getEntryPoints(query);
			if (entryPoints != null && entryPoints.size() > 0) {
				hasPermission = true;
			}
		}

		/**
		 * �û��Ƿ���иýڵ��Ȩ��
		 */
		if (!hasPermission) {
			params.put("name", "USER");
			params.put("value", loginContext.getActorId());

			query.name("USER");
			query.value(loginContext.getActorId());
			entryPoints = entityEntryMapper.getEntryPoints(query);
			if (entryPoints != null && entryPoints.size() > 0) {
				hasPermission = true;
			}
		}

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
	 * �����¼
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

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setEntityEntryMapper(EntityEntryMapper entityEntryMapper) {
		this.entityEntryMapper = entityEntryMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

}