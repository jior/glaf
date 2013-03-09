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
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.util.PageResult;
import com.glaf.core.dao.*;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;

@Service("sysRoleService")
@Transactional(readOnly = true)
public class SysRoleServiceImpl implements SysRoleService {
	protected final static Log logger = LogFactory
			.getLog(SysRoleServiceImpl.class);

	protected IdGenerator idGenerator;

	protected PersistenceDAO persistenceDAO;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysRoleMapper sysRoleMapper;

	protected SysDeptRoleMapper sysDeptRoleMapper;

	public SysRoleServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sysRoleMapper.deleteSysRoleById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SysRoleQuery query = new SysRoleQuery();
			query.rowIds(rowIds);
			sysRoleMapper.deleteSysRoles(query);
		}
	}

	public int count(SysRoleQuery query) {
		query.ensureInitialized();
		return sysRoleMapper.getSysRoleCount(query);
	}

	public List<SysRole> list(SysRoleQuery query) {
		query.ensureInitialized();
		List<SysRole> list = sysRoleMapper.getSysRoles(query);
		return list;
	}

	public int getSysRoleCountByQueryCriteria(SysRoleQuery query) {
		return sysRoleMapper.getSysRoleCount(query);
	}

	public List<SysRole> getSysRolesByQueryCriteria(int start, int pageSize,
			SysRoleQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysRole> rows = sqlSessionTemplate.selectList("getSysRoles",
				query, rowBounds);
		return rows;
	}

	public SysRole getSysRole(Long id) {
		if (id == null) {
			return null;
		}
		SysRole sysRole = sysRoleMapper.getSysRoleById(id);
		return sysRole;
	}

	@Transactional
	public void save(SysRole sysRole) {
		if (sysRole.getId() == 0L) {
			sysRole.setId(idGenerator.nextId());
			// sysRole.setCreateDate(new Date());
			sysRoleMapper.insertSysRole(sysRole);
		} else {
			sysRoleMapper.updateSysRole(sysRole);
		}
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
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
	public void setSysDeptRoleMapper(SysDeptRoleMapper sysDeptRoleMapper) {
		this.sysDeptRoleMapper = sysDeptRoleMapper;
	}

	@Transactional
	public boolean create(SysRole bean) {
		if (bean.getId() == 0L) {
			bean.setId(idGenerator.nextId());
		}
		bean.setSort((int) bean.getId());
		sysRoleMapper.insertSysRole(bean);
		return true;
	}

	@Transactional
	public boolean update(SysRole bean) {
		sysRoleMapper.updateSysRole(bean);
		return true;
	}

	@Transactional
	public boolean delete(SysRole bean) {
		sysRoleMapper.deleteSysRoleById(bean.getId());
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
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

	public SysRole findById(long id) {
		return this.getSysRole(id);
	}

	public SysRole findByName(String name) {
		SysRoleQuery query = new SysRoleQuery();
		query.name(name);
		query.setOrderBy(" E.ID desc ");

		List<SysRole> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public SysRole findByCode(String code) {
		SysRoleQuery query = new SysRoleQuery();
		query.code(code);
		query.setOrderBy(" E.ID desc ");

		List<SysRole> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public PageResult getSysRoleList(int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysRoleQuery query = new SysRoleQuery();

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);
		List<SysRole> list = this.getSysRolesByQueryCriteria(start, pageSize,
				query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<SysRole> getSysRoleList() {
		SysRoleQuery query = new SysRoleQuery();
		query.setOrderBy(" E.SORT desc ");

		List<SysRole> list = this.list(query);
		return list;
	}

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysRole
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	public void sort(SysRole bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(bean);
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(SysRole bean) {
		SysRoleQuery query = new SysRoleQuery();
		query.setSortGreaterThan(bean.getSort());
		// 查找前一个对象

		List<SysRole> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysRole temp = (SysRole) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(SysRole bean) {
		SysRoleQuery query = new SysRoleQuery();
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		List<SysRole> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysRole temp = (SysRole) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}
}
