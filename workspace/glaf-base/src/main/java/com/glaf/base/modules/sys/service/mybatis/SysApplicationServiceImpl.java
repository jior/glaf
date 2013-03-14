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
import com.glaf.core.util.PageResult;
import com.glaf.core.context.ApplicationContext;
import com.glaf.core.dao.*;

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

	protected PersistenceDAO persistenceDAO;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysApplicationMapper sysApplicationMapper;

	protected SysTreeService sysTreeService;

	public SysApplicationServiceImpl() {

	}

	public int count(SysApplicationQuery query) {
		query.ensureInitialized();
		return sysApplicationMapper.getSysApplicationCount(query);
	}

	@Transactional
	public boolean create(SysApplication bean) {
		boolean ret = false;
		if (bean.getId() == 0L) {
			bean.setId(idGenerator.nextId());
		}
		bean.setSort((int) bean.getId());// 设置排序号为刚插入的id值
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
	public void deleteById(Long id) {
		if (id != null) {
			sysApplicationMapper.deleteSysApplicationById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SysApplicationQuery query = new SysApplicationQuery();
			query.rowIds(rowIds);
			sysApplicationMapper.deleteSysApplications(query);
		}
	}

	public SysApplication findById(long id) {
		return this.getSysApplication(id);
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

		logger.info("parent node:" + parentAppId);

		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(parentAppId);
		query.setOrderBy(" E.SORT desc ");
		List<SysApplication> apps = sysApplicationMapper
				.getSysApplicationByUserId(user.getId());
		if (apps != null && !apps.isEmpty()) {
			List<Long> nodeIds = new ArrayList<Long>();
			for (SysApplication app : apps) {
				nodeIds.add(app.getNodeId());
			}
			query.nodeIds(nodeIds);
		}
		return this.list(query);
	}

	public List<SysApplication> getApplicationList() {
		SysApplicationQuery query = new SysApplicationQuery();
		query.setOrderBy(" E.SORT desc ");
		return this.list(query);
	}

	public List<SysApplication> getApplicationList(int parentId) {
		long parentAppId = parentId;
		SysApplication parentApp = findById(parentId);
		if (parentApp != null) {
			parentAppId = parentApp.getNode().getId();
		}

		logger.info("parent node:" + parentAppId);

		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(Long.valueOf(parentAppId));
		query.setOrderBy(" E.SORT desc ");
		List<SysApplication> apps = this.list(query);
		logger.debug("----------------apps size:" + apps.size());
		return apps;
	}

	public PageResult getApplicationList(int parentId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(Long.valueOf(parentId));
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

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
				if (bean.getUrl().startsWith("/")) {
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

	public JSONArray getUserMenu(long parent, String userId) {
		JSONArray array = new JSONArray();
		SysUser user = authorizeService.login(userId);
		if (user != null) {
			logger.debug("#user=" + user.getName());
			List<SysApplication> list = null;
			if (user.isSystemAdmin()) {
				list = getApplicationList((int) parent);
			} else {
				list = getAccessAppList(parent, user);
			}
			if (list != null && list.size() > 0) {
				Iterator<SysApplication> iter = list.iterator();
				while (iter.hasNext()) {
					SysApplication bean = (SysApplication) iter.next();
					JSONObject item = new JSONObject();
					item.put("nodeId", bean.getNodeId());
					item.put("showMenu", bean.getShowMenu());
					item.put("sort", bean.getSort());
					item.put("description", bean.getDesc());
					item.put("name", bean.getName());
					item.put("url", bean.getUrl());

					List<SysApplication> childrenNodes = null;
					if (user.isSystemAdmin()) {
						childrenNodes = getApplicationList((int) bean.getId());
					} else {
						childrenNodes = getAccessAppList(bean.getId(), user);
					}
					if (childrenNodes != null && childrenNodes.size() > 0) {// 有子菜单
						JSONArray children = this.getUserMenu(bean.getId(),
								userId);
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

	@Transactional
	public void save(SysApplication sysApplication) {
		if (sysApplication.getId() == 0L) {
			sysApplication.setId(idGenerator.nextId());
			// sysApplication.setCreateDate(new Date());
			sysApplicationMapper.insertSysApplication(sysApplication);
		} else {
			sysApplicationMapper.updateSysApplication(sysApplication);
		}
	}

	@Resource
	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setSysApplicationMapper(
			SysApplicationMapper sysApplicationMapper) {
		this.sysApplicationMapper = sysApplicationMapper;
	}

	@Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
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
			sysTreeService.update(node);// TODOX

			temp.setSort(i);
			this.update(temp);// 更新temp
			node = sysTreeService.findById(temp.getNodeId());
			node.setSort(temp.getSort());
			sysTreeService.update(node);// TODOX
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
			sysTreeService.update(node);// TODOX

			temp.setSort(i);
			this.update(temp);// 更新temp
			node = sysTreeService.findById(temp.getNodeId());
			node.setSort(temp.getSort());
			sysTreeService.update(node);// TODOX
		}
	}

	@Transactional
	public boolean update(SysApplication bean) {
		this.save(bean);
		if (bean.getNode() != null) {
			sysTreeService.update(bean.getNode());
		}
		return true;
	}

}
