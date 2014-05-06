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

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.DictoryMapper;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.DictoryQuery;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.util.DictoryJsonFactory;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.PageResult;

@Service("dictoryService")
@Transactional(readOnly = true)
public class DictoryServiceImpl implements DictoryService {
	protected final static Log logger = LogFactory
			.getLog(DictoryServiceImpl.class);

	protected DictoryMapper dictoryMapper;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysTreeService sysTreeService;

	public DictoryServiceImpl() {

	}

	public int count(DictoryQuery query) {
		return dictoryMapper.getDictoryCount(query);
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
			dictoryMapper.deleteDictories(query);
		}
	}

	public Dictory find(long id) {
		return this.getDictory(id);
	}

	/**
	 * 获取全部基础数据的分类树
	 * 
	 * @return
	 */
	public List<SysTree> getAllCategories() {
		SysTreeQuery query = new SysTreeQuery();
		query.locked(0);
		List<SysTree> trees = sysTreeService.getDictorySysTrees(query);
		return trees;
	}

	public List<Dictory> getAvailableDictoryList(long nodeId) {
		String cacheKey = "sys_dicts_" + nodeId;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONArray array = JSON.parseArray(text);
			return DictoryJsonFactory.arrayToList(array);
		}
		DictoryQuery query = new DictoryQuery();
		query.nodeId(nodeId);
		query.blocked(0);
		query.setOrderBy(" E.SORT desc");
		List<Dictory> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				JSONArray array = DictoryJsonFactory.listToArray(list);
				CacheFactory.put(cacheKey, array.toJSONString());
			}
		}
		return list;
	}

	public String getCodeById(long id) {
		Dictory dic = find(id);
		return dic.getCode();
	}

	public Dictory getDictory(Long id) {
		if (id == null) {
			return null;
		}
		String cacheKey = "sys_dict_" + id;

		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONObject json = JSON.parseObject(text);
			return DictoryJsonFactory.jsonToObject(json);
		}

		Dictory dictory = dictoryMapper.getDictoryById(id);

		if (dictory != null && SystemConfig.getBoolean("use_query_cache")) {
			JSONObject json = dictory.toJsonObject();
			CacheFactory.put(cacheKey, json.toJSONString());
		}
		return dictory;
	}

	public int getDictoryCountByQueryCriteria(DictoryQuery query) {
		return dictoryMapper.getDictoryCount(query);
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

	public List<Dictory> getDictoryList(long nodeId) {
		DictoryQuery query = new DictoryQuery();
		query.nodeId(nodeId);
		query.setOrderBy(" E.SORT desc");
		return this.list(query);
	}

	public PageResult getDictoryList(long nodeId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		DictoryQuery query = new DictoryQuery();
		query.nodeId(nodeId);
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

	/**
	 * 返回某分类下的所有字典列表
	 * 
	 * @param nodeCode
	 * @return
	 */
	public List<Dictory> getDictoryList(String nodeCode) {
		SysTree tree = sysTreeService.getSysTreeByCode(nodeCode);
		if (tree == null) {
			return null;
		}
		return this.getAvailableDictoryList(tree.getId());
	}

	public List<Dictory> getDictorysByQueryCriteria(int start, int pageSize,
			DictoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Dictory> rows = sqlSessionTemplate.selectList("getDictories",
				query, rowBounds);
		return rows;
	}

	public List<Dictory> list(DictoryQuery query) {
		List<Dictory> list = dictoryMapper.getDictories(query);
		return list;
	}

	@Transactional
	public void save(Dictory dictory) {
		if (dictory.getId() == 0) {
			dictory.setId(idGenerator.nextId());
			dictory.setCreateDate(new Date());
			dictory.setSort(1);
			dictoryMapper.insertDictory(dictory);
		} else {
			dictory.setUpdateDate(new Date());
			dictoryMapper.updateDictory(dictory);
			if (SystemConfig.getBoolean("use_query_cache")) {
				String cacheKey = "sys_dict_" + dictory.getId();
				CacheFactory.remove(cacheKey);
			}
		}
		if (SystemConfig.getBoolean("use_query_cache")) {
			String cacheKey = "sys_dicts_" + dictory.getNodeId();
			CacheFactory.remove(cacheKey);
		}
	}

	@javax.annotation.Resource
	public void setDictoryMapper(DictoryMapper dictoryMapper) {
		this.dictoryMapper = dictoryMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@Transactional
	public void sort(long parent, Dictory bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			logger.debug("前移:" + bean.getName());
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(parent, bean);
			logger.debug("后移:" + bean.getName());
		}
	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(long nodeId, Dictory bean) {
		DictoryQuery query = new DictoryQuery();
		query.nodeId(nodeId);
		query.setSortLessThanOrEqual(bean.getSort());
		query.setIdNotEqual(bean.getId());
		query.setOrderBy(" E.SORT desc ");

		List<?> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			Dictory temp = (Dictory) list.get(0);
			int sort = bean.getSort();
			bean.setSort(temp.getSort() - 1);
			if (sort != temp.getSort()) {
				bean.setSort(temp.getSort());
			}
			this.update(bean);// 更新bean

			temp.setSort(sort + 1);
			this.update(temp);// 更新temp
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long nodeId, Dictory bean) {
		DictoryQuery query = new DictoryQuery();
		query.nodeId(nodeId);
		query.setSortGreaterThanOrEqual(bean.getSort());
		query.setIdNotEqual(bean.getId());
		query.setOrderBy(" E.SORT asc ");

		List<?> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			Dictory temp = (Dictory) list.get(0);
			int sort = bean.getSort();
			bean.setSort(temp.getSort() + 1);
			if (sort != temp.getSort()) {
				bean.setSort(temp.getSort());
			}
			this.update(bean);// 更新bean

			temp.setSort(sort - 1);
			this.update(temp);// 更新temp
		}
	}

	@Transactional
	public boolean update(Dictory bean) {
		this.save(bean);
		return true;
	}

}
