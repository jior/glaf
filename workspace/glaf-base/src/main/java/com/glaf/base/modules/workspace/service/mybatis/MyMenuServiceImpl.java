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
package com.glaf.base.modules.workspace.service.mybatis;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.workspace.mapper.MyMenuMapper;
import com.glaf.base.modules.workspace.model.MyMenu;
import com.glaf.base.modules.workspace.query.MyMenuQuery;
import com.glaf.base.modules.workspace.service.MyMenuService;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.PageResult;

@Service("myMenuService")
@Transactional(readOnly = true)
public class MyMenuServiceImpl implements MyMenuService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MyMenuMapper myMenuMapper;

	public MyMenuServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			myMenuMapper.deleteMyMenuById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			MyMenuQuery query = new MyMenuQuery();
			query.rowIds(rowIds);
			myMenuMapper.deleteMyMenus(query);
		}
	}

	public int count(MyMenuQuery query) {
		query.ensureInitialized();
		return myMenuMapper.getMyMenuCount(query);
	}

	public List<MyMenu> list(MyMenuQuery query) {
		query.ensureInitialized();
		List<MyMenu> list = myMenuMapper.getMyMenus(query);
		return list;
	}

	public int getMyMenuCountByQueryCriteria(MyMenuQuery query) {
		return myMenuMapper.getMyMenuCount(query);
	}

	public List<MyMenu> getMyMenusByQueryCriteria(int start, int pageSize,
			MyMenuQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MyMenu> rows = sqlSessionTemplate.selectList("getMyMenus", query,
				rowBounds);
		return rows;
	}

	public MyMenu getMyMenu(Long id) {
		if (id == null) {
			return null;
		}
		MyMenu myMenu = myMenuMapper.getMyMenuById(id);
		return myMenu;
	}

	@Transactional
	public void save(MyMenu myMenu) {
		if (myMenu.getId() == 0L) {
			myMenu.setId(idGenerator.nextId());
			// myMenu.setCreateDate(new Date());
			myMenuMapper.insertMyMenu(myMenu);
		} else {
			myMenuMapper.updateMyMenu(myMenu);
		}
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setMyMenuMapper(MyMenuMapper myMenuMapper) {
		this.myMenuMapper = myMenuMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	public boolean create(MyMenu bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(MyMenu myMenu) {
		this.deleteById(myMenu.getId());
		return true;
	}

	@Transactional
	@SuppressWarnings("rawtypes")
	public boolean deleteAll(Collection c) {
		for (Object object : c) {
			if (object instanceof Long) {
				this.deleteById((Long) object);
			}
			if (object instanceof MyMenu) {
				MyMenu bean = (MyMenu) object;
				this.deleteById(bean.getId());
			}
		}
		return true;
	}

	public MyMenu find(long id) {
		return this.getMyMenu(id);
	}

	public List<MyMenu> getMyMenuList(long userId) {
		MyMenuQuery query = new MyMenuQuery();
		query.setUserId(userId);
		return this.list(query);
	}

	public PageResult getMyMenuList(long userId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		MyMenuQuery query = new MyMenuQuery();
		query.userId(userId);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.ID desc");

		int start = pageSize * (pageNo - 1);
		List<MyMenu> list = this.getMyMenusByQueryCriteria(start, pageSize,
				query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	/**
	 * 排序
	 * 
	 * @param bean
	 *            MyMenu
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	public void sort(MyMenu bean, int operate) {
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
	private void sortByPrevious(MyMenu bean) {
		MyMenuQuery query = new MyMenuQuery();
		query.setUserId(bean.getUserId());

		// 查找前一个对象
		// String query =
		// "from MyMenu a where a.userId=? and a.sort>? order by a.sort asc";
		List<MyMenu> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			MyMenu temp = (MyMenu) list.get(0);
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
	private void sortByForward(MyMenu bean) {
		MyMenuQuery query = new MyMenuQuery();
		query.setUserId(bean.getUserId());
		// 查找后一个对象
		// String query =
		// "from MyMenu a where a.userId=? and a.sort<? order by a.sort desc";
		List<MyMenu> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			MyMenu temp = (MyMenu) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp

		}
	}

	@Transactional
	public boolean update(MyMenu myMenu) {
		this.save(myMenu);
		return true;
	}

}
