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
 

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;

@Service("sysFunctionService")
@Transactional(readOnly = true)
public class SysFunctionServiceImpl implements SysFunctionService {
	protected final static Log logger = LogFactory
			.getLog(SysFunctionServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysFunctionMapper sysFunctionMapper;

	public SysFunctionServiceImpl() {

	}

	public int count(SysFunctionQuery query) {
		query.ensureInitialized();
		return sysFunctionMapper.getSysFunctionCount(query);
	}

	@Transactional
	public boolean create(SysFunction bean) {
		if (bean.getId() == 0L) {
			bean.setId(idGenerator.nextId());
		}
		bean.setSort((int) bean.getId());
		sysFunctionMapper.insertSysFunction(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(SysFunction bean) {
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
			sysFunctionMapper.deleteSysFunctionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SysFunctionQuery query = new SysFunctionQuery();
			query.rowIds(rowIds);
			sysFunctionMapper.deleteSysFunctions(query);
		}
	}

	public SysFunction findById(long id) {
		return this.getSysFunction(id);
	}

	public SysFunction getSysFunction(Long id) {
		if (id == null) {
			return null;
		}
		SysFunction sysFunction = sysFunctionMapper.getSysFunctionById(id);
		return sysFunction;
	}

	public int getSysFunctionCountByQueryCriteria(SysFunctionQuery query) {
		return sysFunctionMapper.getSysFunctionCount(query);
	}

	public List<SysFunction> getSysFunctionList() {
		SysFunctionQuery query = new SysFunctionQuery();
		query.setOrderBy(" E.SORT desc ");
		return this.list(query);
	}

	public List<SysFunction> getSysFunctionList(long appId) {
		SysFunctionQuery query = new SysFunctionQuery();
		query.appId(Long.valueOf(appId));
		query.setOrderBy(" E.SORT desc ");
		return this.list(query);
	}

	public List<SysFunction> getSysFunctionsByQueryCriteria(int start,
			int pageSize, SysFunctionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysFunction> rows = sqlSessionTemplate.selectList(
				"getSysFunctions", query, rowBounds);
		return rows;
	}

	public List<SysFunction> list(SysFunctionQuery query) {
		query.ensureInitialized();
		List<SysFunction> list = sysFunctionMapper.getSysFunctions(query);
		return list;
	}

	@Transactional
	public void save(SysFunction sysFunction) {
		if (sysFunction.getId() == 0) {
			sysFunction.setId(idGenerator.nextId());
			// sysFunction.setCreateDate(new Date());
			sysFunctionMapper.insertSysFunction(sysFunction);
		} else {
			sysFunctionMapper.updateSysFunction(sysFunction);
		}
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
	public void setSysFunctionMapper(SysFunctionMapper sysFunctionMapper) {
		this.sysFunctionMapper = sysFunctionMapper;
	}

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysFunction
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	public void sort(SysFunction bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(bean);
		}
	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(SysFunction bean) {
		SysFunctionQuery query = new SysFunctionQuery();
		query.appId(Long.valueOf(bean.getAppId()));
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		// 查找前一个对象
		List<SysFunction> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysFunction temp = (SysFunction) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(SysFunction bean) {
		SysFunctionQuery query = new SysFunctionQuery();
		query.appId(Long.valueOf(bean.getAppId()));
		query.setSortGreaterThan(bean.getSort());
		query.setOrderBy(" E.SORT asc ");
		// 查找前一个对象
		List<SysFunction> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysFunction temp = (SysFunction) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	@Transactional
	public boolean update(SysFunction bean) {
		sysFunctionMapper.updateSysFunction(bean);
		return true;
	}
}
