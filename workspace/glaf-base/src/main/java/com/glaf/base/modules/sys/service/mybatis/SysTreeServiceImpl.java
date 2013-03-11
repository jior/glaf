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

@Service("sysTreeService")
@Transactional(readOnly = true)
public class SysTreeServiceImpl implements SysTreeService {
	protected final static Log logger = LogFactory
			.getLog(SysTreeServiceImpl.class);

	protected IdGenerator idGenerator;

	protected PersistenceDAO persistenceDAO;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysTreeMapper sysTreeMapper;

	protected SysApplicationMapper sysApplicationMapper;

	protected SysDepartmentMapper sysDepartmentMapper;

	public SysTreeServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sysTreeMapper.deleteSysTreeById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SysTreeQuery query = new SysTreeQuery();
			query.rowIds(rowIds);
			sysTreeMapper.deleteSysTrees(query);
		}
	}

	public int count(SysTreeQuery query) {
		return sysTreeMapper.getSysTreeCount(query);
	}

	public List<SysTree> list(SysTreeQuery query) {
		List<SysTree> list = sysTreeMapper.getSysTrees(query);
		return list;
	}

	public int getSysTreeCountByQueryCriteria(SysTreeQuery query) {
		return sysTreeMapper.getSysTreeCount(query);
	}

	public List<SysTree> getSysTreesByQueryCriteria(int start, int pageSize,
			SysTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysTree> rows = sqlSessionTemplate.selectList("getSysTrees",
				query, rowBounds);
		return rows;
	}

	public SysTree getSysTree(Long id) {
		if (id == null) {
			return null;
		}
		SysTree sysTree = sysTreeMapper.getSysTreeById(id);
		return sysTree;
	}

	@Transactional
	public void save(SysTree sysTree) {
		if (sysTree.getId() == 0L) {
			sysTree.setId(idGenerator.nextId());
			// sysTree.setCreateDate(new Date());
			sysTreeMapper.insertSysTree(sysTree);
		} else {
			sysTreeMapper.updateSysTree(sysTree);
		}
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSysTreeMapper(SysTreeMapper sysTreeMapper) {
		this.sysTreeMapper = sysTreeMapper;
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
	public void setSysDepartmentMapper(SysDepartmentMapper sysDepartmentMapper) {
		this.sysDepartmentMapper = sysDepartmentMapper;
	}

	@Transactional
	public boolean create(SysTree bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(SysTree bean) {
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

	public SysTree findById(long id) {
		return this.getSysTree(id);
	}

	public SysTree findByName(String name) {
		SysTreeQuery query = new SysTreeQuery();
		query.name(name);

		List<SysTree> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public void getSysTree(List<SysTree> tree, int parentId, int deep) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		query.setOrderBy("  E.SORT desc ");
		List<SysTree> nodes = this.list(query);
		if (nodes != null && !nodes.isEmpty()) {
			this.initDepartments(nodes);
			this.initApplications(nodes);
			Iterator<SysTree> iter = nodes.iterator();
			while (iter.hasNext()) {// �ݹ����
				SysTree bean = iter.next();
				bean.setDeep(deep + 1);
				tree.add(bean);// ���뵽����
				getSysTree(tree, (int) bean.getId(), bean.getDeep());
			}
		}
	}

	public SysTree getSysTreeByCode(String code) {
		SysTreeQuery query = new SysTreeQuery();
		query.code(code);

		List<SysTree> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public List<SysTree> getSysTreeList(int parentId) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		query.setOrderBy("  E.SORT desc ");
		List<SysTree> trees = this.list(query);
		return trees;
	}

	protected void initDepartments(List<SysTree> list) {
		if (list != null && !list.isEmpty()) {
			SysDepartmentQuery query = new SysDepartmentQuery();
			query.status(0);
			List<SysDepartment> depts = sysDepartmentMapper
					.getSysDepartments(query);
			Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
			if (depts != null && !depts.isEmpty()) {
				for (SysDepartment dept : depts) {
					deptMap.put(dept.getNodeId(), dept);
				}
			}
			for (SysTree bean : list) {
				bean.setDepartment(deptMap.get(Long.valueOf(bean.getId())));
			}
		}
	}

	protected void initApplications(List<SysTree> list) {
		if (list != null && !list.isEmpty()) {
			SysApplicationQuery query = new SysApplicationQuery();
			List<SysApplication> apps = sysApplicationMapper
					.getSysApplications(query);
			Map<Long, SysApplication> appMap = new HashMap<Long, SysApplication>();
			if (apps != null && !apps.isEmpty()) {
				for (SysApplication m : apps) {
					appMap.put(m.getNodeId(), m);
				}
			}
			for (SysTree bean : list) {
				bean.setApp(appMap.get(Long.valueOf(bean.getId())));
			}
		}
	}

	public PageResult getSysTreeList(int parent, int pageNo, int pageSize) {
		// ��������
		PageResult pager = new PageResult();
		SysTreeQuery query = new SysTreeQuery();
		query.parentId(Long.valueOf(parent));
		int count = this.count(query);
		if (count == 0) {// �����Ϊ��
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);
		List<SysTree> list = this.getSysTreesByQueryCriteria(start, pageSize,
				query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<SysTree> getSysTreeListForDept(int parentId, int status) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		if (status != -1) {
			query.setDepartmentStatus(status);
		}
		query.setOrderBy(" E.SORT desc");
		List<SysTree> list = this.list(query);
		this.initDepartments(list);
		return list;
	}

	/**
	 * ��ȡ���ڵ��б�����:��Ŀ¼>A>A1>A11
	 * 
	 * @param tree
	 * @param int id
	 */
	public void getSysTreeParent(List<SysTree> tree, long id) {
		// �����Ƿ��и��ڵ�
		SysTree bean = findById(id);
		if (bean != null) {
			if (bean.getParentId() != 0) {
				getSysTreeParent(tree, bean.getParentId());
			}
			tree.add(bean);
		}
	}

	@Transactional
	public void sort(long parent, SysTree bean, int operate) {
		if (operate == SysConstants.SORT_PREVIOUS) {// ǰ��
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// ����
			sortByForward(parent, bean);
		}

	}

	/**
	 * ��ǰ�ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parent, SysTree bean) {
		SysTreeQuery query = new SysTreeQuery();
		query.parentId(Long.valueOf(parent));
		query.setSortGreaterThan(bean.getSort());
		query.setOrderBy(" E.SORT asc ");

		// ����ǰһ������
		List<SysTree> list = this.list(query);
		if (list != null && list.size() > 0) {// �м�¼
			SysTree temp = (SysTree) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// ����bean

			temp.setSort(i);
			this.update(temp);// ����temp
		}
	}

	/**
	 * ����ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByForward(long parent, SysTree bean) {
		SysTreeQuery query = new SysTreeQuery();
		query.parentId(Long.valueOf(parent));
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		// ���Һ�һ������
		List<SysTree> list = this.list(query);
		if (list != null && list.size() > 0) {// �м�¼
			SysTree temp = (SysTree) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// ����bean

			temp.setSort(i);
			this.update(temp);// ����temp
		}
	}

	@Transactional
	public boolean update(SysTree bean) {
		this.save(bean);
		return true;
	}

}