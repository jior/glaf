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

@Service("dictoryService")
@Transactional(readOnly = true)
public class DictoryServiceImpl implements DictoryService {
	protected final static Log logger = LogFactory
			.getLog(DictoryServiceImpl.class);

	protected LongIdGenerator idGenerator;

	protected PersistenceDAO persistenceDAO;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DictoryMapper dictoryMapper;

	public DictoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			dictoryMapper.deleteDictoryById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			DictoryQuery query = new DictoryQuery();
			query.rowIds(rowIds);
			dictoryMapper.deleteDictorys(query);
		}
	}

	public int count(DictoryQuery query) {
		query.ensureInitialized();
		return dictoryMapper.getDictoryCount(query);
	}

	public List<Dictory> list(DictoryQuery query) {
		query.ensureInitialized();
		List<Dictory> list = dictoryMapper.getDictorys(query);
		return list;
	}

	public int getDictoryCountByQueryCriteria(DictoryQuery query) {
		return dictoryMapper.getDictoryCount(query);
	}

	public List<Dictory> getDictorysByQueryCriteria(int start, int pageSize,
			DictoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Dictory> rows = sqlSessionTemplate.selectList("getDictorys",
				query, rowBounds);
		return rows;
	}

	public Dictory getDictory(Long id) {
		if (id == null) {
			return null;
		}
		Dictory dictory = dictoryMapper.getDictoryById(id);
		return dictory;
	}

	@Transactional
	public void save(Dictory dictory) {
		if (dictory.getId() == 0L) {
			dictory.setId(idGenerator.getNextId());
			// dictory.setCreateDate(new Date());
			dictoryMapper.insertDictory(dictory);
		} else {
			dictoryMapper.updateDictory(dictory);
		}
	}

	@Resource
	@Qualifier("myBatisDbLongIdGenerator")
	public void setLongIdGenerator(LongIdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setDictoryMapper(DictoryMapper dictoryMapper) {
		this.dictoryMapper = dictoryMapper;
	}

	@Resource
	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	public boolean create(Dictory bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(Dictory bean) {
		this.deleteById(bean.getId());
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

	public Dictory find(long id) {
		return this.getDictory(id);
	}

	public List<Dictory> getAvailableDictoryList(long typeId) {
		DictoryQuery query = new DictoryQuery();
		query.typeId(typeId);
		query.blocked(0);
		query.setOrderBy(" E.SORT desc");
		return this.list(query);
	}

	public String getCodeById(long id) {
		Dictory dic = find(id);
		return dic.getCode();
	}

	public PageResult getDictoryList(int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		DictoryQuery query = new DictoryQuery();
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);
		List<Dictory> list = this.getDictorysByQueryCriteria(start, pageSize,
				query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<Dictory> getDictoryList(long typeId) {
		DictoryQuery query = new DictoryQuery();
		query.typeId(typeId);
		query.setOrderBy(" E.SORT desc");
		return this.list(query);
	}

	public PageResult getDictoryList(long typeId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		DictoryQuery query = new DictoryQuery();
		query.typeId(typeId);
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);
		List<Dictory> list = this.getDictorysByQueryCriteria(start, pageSize,
				query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public Map<String, String> getDictoryMap(List<Dictory> list, long purchaseId) {
		Map<String, String> dictoryMap = new HashMap<String, String>();
		return dictoryMap;
	}

	@Transactional
	public void sort(long parent, Dictory bean, int operate) {
		if (bean == null)
			return;
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
	private void sortByPrevious(long typeId, Dictory bean) {
		DictoryQuery query = new DictoryQuery();
		query.typeId(typeId);
		query.setSortGreaterThan(bean.getSort());
		query.setOrderBy(" E.SORT asc");

		List<?> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			Dictory temp = (Dictory) list.get(0);
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
	private void sortByForward(long typeId, Dictory bean) {
		DictoryQuery query = new DictoryQuery();
		query.typeId(typeId);
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc");

		List<?> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			Dictory temp = (Dictory) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	@Transactional
	public boolean update(Dictory bean) {
		this.save(bean);
		return true;
	}

}
