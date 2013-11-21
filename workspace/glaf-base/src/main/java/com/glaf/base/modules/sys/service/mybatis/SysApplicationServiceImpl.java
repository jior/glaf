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
package com.glaf.base.modules.sys.service.mybatis;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.identity.Agent;
import com.glaf.core.service.EntityService;
import com.glaf.core.util.PageResult;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.context.ApplicationContext;
import com.glaf.base.business.TreeHelper;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;

@Service("sysApplicationService")
@Transactional(readOnly = true)
public class SysApplicationServiceImpl implements SysApplicationService {
	protected final static Log logger = LogFactory
			.getLog(SysApplicationServiceImpl.class);

	protected AuthorizeService authorizeService;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysApplicationMapper sysApplicationMapper;

	protected SysAccessMapper sysAccessMapper;

	protected SysTreeMapper sysTreeMapper;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected EntityService entityService;

	public SysApplicationServiceImpl() {

	}

	public int count(SysApplicationQuery query) {
		query.ensureInitialized();
		return sysApplicationMapper.getSysApplicationCount(query);
	}

	@Transactional
	public boolean create(SysApplication bean) {
		boolean ret = false;
		if (bean.getId() == 0) {
			bean.setId(idGenerator.nextId());
		}
		if (bean.getNode() != null) {
			bean.getNode().setDiscriminator("A");
			bean.getNode().setCreateBy(bean.getCreateBy());
			sysTreeService.create(bean.getNode());
			bean.setNodeId(bean.getNode().getId());
		}
		bean.setSort((int) bean.getId());// 设置排序号为刚插入的id值
		bean.setCreateDate(new Date());
		sysApplicationMapper.insertSysApplication(bean);
		ret = true;
		return ret;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(SysApplication bean) {
		this.deleteById(bean.getId());
		return true;
	}

	@Transactional
	public boolean deleteAll(long[] ids) {
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				this.deleteById(id);
			}
		}
		return true;
	}

	@Transactional
	public void deleteById(Long appId) {
		if (appId != null && appId > 0) {
			sysAccessMapper.deleteSysAccessByAppId(appId);
			sysApplicationMapper.deleteSysApplicationById(appId);
			SysApplication app = this.getSysApplication(appId);
			if (app != null) {
				sysTreeMapper.deleteSysTreeById(app.getNodeId());
			}
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			for (Long appId : rowIds) {
				this.deleteById(appId);
			}
		}
	}

	/**
	 * 按编码查找对象
	 * 
	 * @param code
	 *            String
	 * @return SysApplication
	 */
	public SysApplication findByCode(String code) {
		SysApplicationQuery query = new SysApplicationQuery();
		query.code(code);

		List<SysApplication> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			SysApplication sysApplication = list.get(0);
			SysTree node = sysTreeService.findById(sysApplication.getNodeId());
			sysApplication.setNode(node);
			return sysApplication;
		}

		return null;
	}

	public SysApplication findById(long id) {
		SysApplication app = this.getSysApplication(id);
		if (app != null && app.getNodeId() > 0) {
			SysTree node = sysTreeMapper.getSysTreeById(app.getNodeId());
			app.setNode(node);
		}
		return app;
	}

	public SysApplication findByName(String name) {
		SysApplicationQuery query = new SysApplicationQuery();
		query.name(name);

		List<SysApplication> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			SysApplication sysApplication = list.get(0);
			SysTree node = sysTreeService.findById(sysApplication.getNodeId());
			sysApplication.setNode(node);
			return sysApplication;
		}

		return null;
	}

	public List<SysApplication> getAccessAppList(long parentId, SysUser user) {
		long parentAppId = parentId;
		SysApplication parentApp = findById(parentId);
		if (parentApp != null) {
			parentAppId = parentApp.getNode().getId();
		}

		logger.debug("parent node:" + parentAppId);

		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(parentAppId);
		query.setOrderBy(" E.SORT asc ");
		query.setLocked(0);
		List<Long> nodeIds = new ArrayList<Long>();
		nodeIds.add(-1L);

		List<SysApplication> apps = sysApplicationMapper
				.getSysApplicationByUserId(user.getId());
		if (apps != null && !apps.isEmpty()) {
			for (SysApplication app : apps) {
				nodeIds.add(app.getNodeId());
			}
		}
		query.nodeIds(nodeIds);

		return this.list(query);
	}

	public List<SysApplication> getApplicationList() {
		SysApplicationQuery query = new SysApplicationQuery();
		query.setOrderBy(" E.SORT asc ");
		return this.list(query);
	}

	public List<SysApplication> getApplicationList(long parentId) {
		long parentAppId = parentId;
		SysApplication parentApp = findById(parentId);
		if (parentApp != null) {
			parentAppId = parentApp.getNode().getId();
		}

		logger.info("parent node:" + parentAppId);

		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(parentAppId);
		query.setLocked(0);
		query.setOrderBy(" E.SORT asc ");
		List<SysApplication> apps = this.list(query);
		logger.debug("----------------apps size:" + apps.size());
		return apps;
	}

	public PageResult getApplicationList(long parentId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(Long.valueOf(parentId));
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT asc");

		int start = pageSize * (pageNo - 1);
		List<SysApplication> list = this.getSysApplicationsByQueryCriteria(
				start, pageSize, query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public String getMenu(long parent, SysUser user) {
		StringBuffer menu = new StringBuffer("");
		List<SysApplication> list = getAccessAppList(parent, user);
		if (list == null || list.isEmpty()) {
			if (user.isSystemAdmin()) {
				list = getApplicationList((int) parent);
			}
		}
		if (list != null && list.size() > 0) {
			Iterator<SysApplication> iter = list.iterator();
			while (iter.hasNext()) {
				SysApplication bean = (SysApplication) iter.next();
				menu.append("<li>");
				menu.append("<a href=\"javascript:jump('");
				// System.out.println("ContextUtil.getContextPath():"+ContextUtil.getContextPath());
				if (bean.getUrl() != null && bean.getUrl().startsWith("/")) {
					if (ApplicationContext.getContextPath() != null) {
						menu.append(ApplicationContext.getContextPath());
					}
				}
				menu.append(bean.getUrl());
				menu.append("',");
				menu.append(bean.getShowMenu());
				menu.append(");\">");
				menu.append(bean.getName()).append("</a>\n");

				List<SysApplication> sonNode = getAccessAppList(bean.getId(),
						user);
				if (sonNode == null || sonNode.isEmpty()) {
					if (user.isSystemAdmin()) {
						sonNode = getApplicationList((int) bean.getId());
					}
				}
				if (sonNode != null && sonNode.size() > 0) {// 有子菜单
					menu.append("<ul>");
					menu.append(getMenu(bean.getId(), user));
					menu.append("</ul>");
				}
				menu.append("</li>").append("<li></li>\n");
			}
		}
		return menu.toString();
	}

	public List<RealmInfo> getRealmInfos() {
		Map<String, Object> params = new HashMap<String, Object>();
		return sysApplicationMapper.getRealmInfos(params);
	}

	public SysApplication getSysApplication(Long id) {
		if (id == null) {
			return null;
		}
		SysApplication sysApplication = sysApplicationMapper
				.getSysApplicationById(id);
		if (sysApplication != null) {
			SysTree node = sysTreeService.findById(sysApplication.getNodeId());
			sysApplication.setNode(node);
		}
		return sysApplication;
	}

	public int getSysApplicationCountByQueryCriteria(SysApplicationQuery query) {
		return sysApplicationMapper.getSysApplicationCount(query);
	}

	public List<SysApplication> getSysApplicationsByQueryCriteria(int start,
			int pageSize, SysApplicationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysApplication> rows = sqlSessionTemplate.selectList(
				"getSysApplications", query, rowBounds);
		return rows;
	}

	public TreeModel getTreeModelByAppId(long appId) {
		SysApplication bean = this.findById(appId);
		if (bean != null) {
			TreeModel treeModel = new BaseTree();
			treeModel.setCode(bean.getCode());
			treeModel.setId(bean.getId());
			treeModel.setName(bean.getName());
			treeModel.setLocked(bean.getLocked());
			treeModel.setDescription(bean.getDesc());
			treeModel.setUrl(bean.getUrl());
			treeModel.setSortNo(bean.getSort());
			return treeModel;
		}
		return null;
	}

	/**
	 * 获取用户某个分类下的全部分类节点
	 * 
	 * @param parent
	 *            父节点编号
	 * @param userId
	 *            用户登录账号
	 * @return
	 */
	public List<TreeModel> getTreeModels(long parentId, String userId) {
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		SysUser user = authorizeService.login(userId);
		if (user != null) {
			this.loadChildrenTreeModels(treeModels, parentId, user);
		}
		return treeModels;
	}

	public JSONArray getUserMenu(long parent, String actorId) {
		JSONArray array = new JSONArray();
		SysUser user = authorizeService.login(actorId);
		if (user != null) {
			List<SysTree> treeList = null;
			SysApplication app = this.findById(parent);
			SysTreeQuery query = new SysTreeQuery();
			query.treeId(app.getNode().getTreeId());
			query.treeIdLike(app.getNode().getTreeId() + "%");
			if (!user.isSystemAdmin()) {
				List<String> actorIds = new ArrayList<String>();
				List<Object> rows = entityService.getList("getAgents", actorId);
				if (rows != null && !rows.isEmpty()) {
					for (Object object : rows) {
						if (object instanceof Agent) {
							Agent agent = (Agent) object;
							if (!agent.isValid()) {
								continue;
							}
							switch (agent.getAgentType()) {
							case 0:// 全局代理
								actorIds.add(agent.getAssignFrom());
								break;
							default:
								break;
							}
						}
					}
				}
				if (!actorIds.isEmpty()) {
					actorIds.add(actorId);
					query.setActorIds(actorIds);
				} else {
					query.setActorId(actorId);
				}
				treeList = sysTreeMapper.getTreeListByUsers(query);
			} else {
				treeList = sysTreeMapper.getTreeList(query);
			}

			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			for (SysTree tree : treeList) {
				treeModels.add(tree);
			}
			TreeHelper treeHelper = new TreeHelper();
			array = treeHelper.getTreeJSONArray(treeModels);
			// logger.debug(array.toString('\n'));
		}
		return array;
	}

	protected JSONArray getUserMenu(long parent, SysUser user) {
		JSONArray array = new JSONArray();
		if (user != null) {
			List<SysApplication> list = null;
			if (user.isSystemAdmin()) {
				logger.debug("#admin user=" + user.getName());
				list = getApplicationList((int) parent);
			} else {
				logger.debug("#user=" + user.getName());
				list = getAccessAppList(parent, user);
				logger.debug("#app list=" + list);
			}
			if (list != null && list.size() > 0) {
				Iterator<SysApplication> iter = list.iterator();
				while (iter.hasNext()) {
					SysApplication bean = (SysApplication) iter.next();
					if (bean.getLocked() == 1) {
						continue;
					}
					JSONObject item = new JSONObject();
					item.put("id", String.valueOf(bean.getId()));
					item.put("nodeId", bean.getNodeId());
					item.put("showMenu", bean.getShowMenu());
					item.put("sort", bean.getSort());
					item.put("description", bean.getDesc());
					item.put("name", bean.getName());
					item.put("icon", "icon-sys");
					item.put("url", bean.getUrl());

					List<SysApplication> childrenNodes = null;
					if (user.isSystemAdmin()) {
						childrenNodes = getApplicationList((int) bean.getId());
					} else {
						childrenNodes = getAccessAppList(bean.getId(), user);
					}
					if (childrenNodes != null && childrenNodes.size() > 0) {// 有子菜单
						JSONArray children = this.getUserMenu(bean.getId(),
								user);
						item.put("children", children);
					}

					array.put(item);

				}
			}
		}
		return array;
	}

	public JSONArray getUserMenu2(long parent, String userId) {
		JSONArray array = new JSONArray();
		SysUser user = authorizeService.login(userId);
		if (user != null) {
			List<SysApplication> list = null;
			if (user.isSystemAdmin()) {
				logger.debug("#admin user=" + user.getName());
				list = getApplicationList((int) parent);
			} else {
				logger.debug("#user=" + user.getName());
				list = getAccessAppList(parent, user);
				logger.debug("#app list=" + list);
			}
			if (list != null && list.size() > 0) {
				Iterator<SysApplication> iter = list.iterator();
				while (iter.hasNext()) {
					SysApplication bean = (SysApplication) iter.next();
					if (bean.getLocked() == 1) {
						continue;
					}
					JSONObject item = new JSONObject();
					item.put("id", String.valueOf(bean.getId()));
					item.put("nodeId", bean.getNodeId());
					item.put("showMenu", bean.getShowMenu());
					item.put("sort", bean.getSort());
					item.put("description", bean.getDesc());
					item.put("name", bean.getName());
					item.put("icon", "icon-sys");
					item.put("url", bean.getUrl());

					List<SysApplication> childrenNodes = null;
					if (user.isSystemAdmin()) {
						childrenNodes = getApplicationList((int) bean.getId());
					} else {
						childrenNodes = getAccessAppList(bean.getId(), user);
					}
					if (childrenNodes != null && childrenNodes.size() > 0) {// 有子菜单
						JSONArray children = this.getUserMenu(bean.getId(),
								user);
						item.put("children", children);
					}

					array.put(item);

				}
			}
		}
		return array;
	}

	public List<SysApplication> list(SysApplicationQuery query) {
		query.ensureInitialized();
		List<SysApplication> list = sysApplicationMapper
				.getSysApplications(query);
		return list;
	}

	protected void loadChildrenTreeModels(List<TreeModel> treeModels,
			long parentId, SysUser user) {
		List<SysApplication> list = null;
		if (user.isSystemAdmin()) {
			logger.debug("#admin user=" + user.getName());
			list = getApplicationList((int) parentId);
		} else {
			logger.debug("#user=" + user.getName());
			list = getAccessAppList(parentId, user);
			logger.debug("#app list=" + list);
		}
		if (list != null && list.size() > 0) {
			Iterator<SysApplication> iter = list.iterator();
			while (iter.hasNext()) {
				SysApplication bean = (SysApplication) iter.next();
				if (bean.getLocked() == 1) {
					continue;
				}
				TreeModel treeModel = new BaseTree();
				treeModel.setCode(bean.getCode());
				treeModel.setId(bean.getId());
				treeModel.setParentId(parentId);
				treeModel.setName(bean.getName());
				treeModel.setLocked(bean.getLocked());
				treeModel.setDescription(bean.getDesc());
				treeModel.setUrl(bean.getUrl());
				treeModel.setSortNo(bean.getSort());

				List<SysApplication> childrenNodes = null;
				if (user.isSystemAdmin()) {
					childrenNodes = getApplicationList((int) bean.getId());
				} else {
					childrenNodes = getAccessAppList(bean.getId(), user);
				}
				if (childrenNodes != null && childrenNodes.size() > 0) {// 有子菜单
					this.loadChildrenTreeModels(treeModels, bean.getId(), user);
				}
				treeModels.add(treeModel);
			}
		}
	}

	@Resource
	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	@Resource
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setSysAccessMapper(SysAccessMapper sysAccessMapper) {
		this.sysAccessMapper = sysAccessMapper;
	}

	@Resource
	public void setSysApplicationMapper(
			SysApplicationMapper sysApplicationMapper) {
		this.sysApplicationMapper = sysApplicationMapper;
	}

	@Resource
	public void setSysTreeMapper(SysTreeMapper sysTreeMapper) {
		this.sysTreeMapper = sysTreeMapper;
	}

	@Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Transactional
	public void sort(long parent, SysApplication bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(parent, bean);
		}
	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(long parentId, SysApplication bean) {
		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(Long.valueOf(parentId));
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		List<SysApplication> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysApplication temp = (SysApplication) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean
			SysTree node = sysTreeService.findById(bean.getNodeId());
			node.setSort(bean.getSort());
			sysTreeService.update(node);

			temp.setSort(i);
			this.update(temp);// 更新temp
			node = sysTreeService.findById(temp.getNodeId());
			node.setSort(temp.getSort());
			sysTreeService.update(node);
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parentId, SysApplication bean) {
		// 查找前一个对象

		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(Long.valueOf(parentId));
		query.setSortGreaterThan(bean.getSort());
		query.setOrderBy(" E.SORT asc ");

		List<SysApplication> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysApplication temp = (SysApplication) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean
			SysTree node = sysTreeService.findById(bean.getNodeId());
			node.setSort(bean.getSort());
			sysTreeService.update(node);

			temp.setSort(i);
			this.update(temp);// 更新temp
			node = sysTreeService.findById(temp.getNodeId());
			node.setSort(temp.getSort());
			sysTreeService.update(node);
		}
	}

	@Transactional
	public boolean update(SysApplication bean) {
		bean.setUpdateDate(new Date());
		this.sysApplicationMapper.updateSysApplication(bean);
		if (bean.getNode() != null) {
			bean.getNode().setLocked(bean.getLocked());
			bean.getNode().setUpdateBy(bean.getUpdateBy());
			sysTreeService.update(bean.getNode());
		}
		return true;
	}

}
