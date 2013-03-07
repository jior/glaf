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

	protected LongIdGenerator idGenerator;

	protected PersistenceDAO persistenceDAO;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysTreeMapper sysTreeMapper;

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
		query.ensureInitialized();
		return sysTreeMapper.getSysTreeCount(query);
	}

	public List<SysTree> list(SysTreeQuery query) {
		query.ensureInitialized();
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
			sysTree.setId(idGenerator.getNextId());
			// sysTree.setCreateDate(new Date());
			sysTreeMapper.insertSysTree(sysTree);
		} else {
			sysTreeMapper.updateSysTree(sysTree);
		}
	}

	@Resource
	@Qualifier("myBatisDbLongIdGenerator")
	public void setLongIdGenerator(LongIdGenerator idGenerator) {
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

	public boolean create(SysTree bean) {
		this.save(bean);
		return true;
	}

	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	public boolean delete(SysTree bean) {
		this.deleteById(bean.getId());
		return true;
	}

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

	public void getSysTree(List<SysTree> tree, int parent, int deep) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParent(Long.valueOf(parent));
		query.setOrderBy("  E.SORT desc ");
		List<SysTree> nodes = this.list(query);
		if (nodes != null) {
			Iterator<SysTree> iter = nodes.iterator();
			while (iter.hasNext()) {// 递归遍历
				SysTree bean = (SysTree) iter.next();
				bean.setDeep(deep + 1);
				tree.add(bean);// 加入到数组
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

	public List<SysTree> getSysTreeList(int parent) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParent(Long.valueOf(parent));
		query.setOrderBy("  E.SORT desc ");
		List<SysTree> trees = this.list(query);
		return trees;
	}

	public PageResult getSysTreeList(int parent, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysTreeQuery query = new SysTreeQuery();
		query.parentId(Long.valueOf(parent));
		int count = this.count(query);
		if (count == 0) {// 结果集为空
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

	@Override
	public List<SysTree> getSysTreeListForDept(int parent, int status) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParent(Long.valueOf(parent));
		if (status != -1) {
			query.setDepartmentStatus(status);
		}
		query.setOrderBy(" E.SORT desc");
		return this.list(query);
	}

	/**
	 * 获取父节点列表，如:根目录>A>A1>A11
	 * 
	 * @param tree
	 * @param int id
	 */
	public void getSysTreeParent(List<SysTree> tree, long id) {
		// 查找是否有父节点
		SysTree bean = findById(id);
		if (bean != null) {
			if (bean.getParentId() != 0) {
				getSysTreeParent(tree, bean.getParentId());
			}
			tree.add(bean);
		}
	}

	public void sort(long parent, SysTree bean, int operate) {
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(parent, bean);
		}

	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parent, SysTree bean) {
		SysTreeQuery query = new SysTreeQuery();
		query.parentId(Long.valueOf(parent));
		query.setSortGreaterThan(bean.getSort());
		query.setOrderBy(" E.SORT asc ");

		// 查找前一个对象
		List<SysTree> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysTree temp = (SysTree) list.get(0);
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
	private void sortByForward(long parent, SysTree bean) {
		SysTreeQuery query = new SysTreeQuery();
		query.parentId(Long.valueOf(parent));
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		// 查找后一个对象
		List<SysTree> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysTree temp = (SysTree) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	public boolean update(SysTree bean) {
		this.save(bean);
		return true;
	}

}
