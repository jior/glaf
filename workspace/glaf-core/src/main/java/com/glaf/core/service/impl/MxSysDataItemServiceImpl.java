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

import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.id.*;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.*;
import com.glaf.core.service.ISysDataItemService;
import com.glaf.core.util.CaseInsensitiveHashMap;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.mapper.*;
import com.glaf.core.domain.*;
import com.glaf.core.domain.util.SysDataItemJsonFactory;
import com.glaf.core.query.*;

@Service("sysDataItemService")
@Transactional(readOnly = true)
public class MxSysDataItemServiceImpl implements ISysDataItemService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysDataItemMapper sysDataItemMapper;

	protected TableDataMapper tableDataMapper;

	protected TablePageMapper tablePageMapper;

	public MxSysDataItemServiceImpl() {

	}

	public int count(SysDataItemQuery query) {
		return sysDataItemMapper.getSysDataItemCount(query);
	}

	@Transactional
	public void deleteById(long id) {
		if (id != 0) {
			String cacheKey = "sys_dataitem_" + id;
			CacheFactory.remove(cacheKey);
			cacheKey = "sys_dataitem_data_" + id;
			CacheFactory.remove(cacheKey);
			sysDataItemMapper.deleteSysDataItemById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				sysDataItemMapper.deleteSysDataItemById(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public JSONArray getJsonData(long id, Map<String, Object> parameter) {
		JSONArray result = new JSONArray();
		SysDataItem sysDataItem = this.getSysDataItemById(id);
		if (sysDataItem != null) {
			String cacheKey = "sys_dataitem_data_" + id;
			if (StringUtils.equals("Y", sysDataItem.getCacheFlag())) {
				String text = CacheFactory.getString(cacheKey);
				if (StringUtils.isNotEmpty(text)) {
					try {
						result = JSON.parseArray(text);
						return result;
					} catch (Exception ex) {
						// Ignore error
					}
				}
			}
			if (StringUtils.isNotEmpty(sysDataItem.getQueryId())) {
				List<Object> list = sqlSessionTemplate.selectList(
						sysDataItem.getQueryId(), parameter);
				if (list != null && !list.isEmpty()) {
					Map<String, Object> newDataMap = new CaseInsensitiveHashMap();
					for (Object object : list) {
						newDataMap.clear();
						JSONObject json = new JSONObject();
						if (object instanceof Map) {
							Map<String, Object> dataMap = (Map<String, Object>) object;
							Iterator<Entry<String, Object>> iterator = dataMap
									.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<String, Object> entry = iterator.next();
								String name = entry.getKey();
								Object value = entry.getValue();
								newDataMap.put(name, value);
								newDataMap.put(name.toLowerCase(), value);
								json.put(name, value);
							}
						} else {
							Map<String, Object> dataMap = Tools
									.getDataMap(object);
							Iterator<Entry<String, Object>> iterator = dataMap
									.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<String, Object> entry = iterator.next();
								String name = entry.getKey();
								Object value = entry.getValue();
								newDataMap.put(name, value);
								newDataMap.put(name.toLowerCase(), value);
								json.put(name, value);
							}
						}
						if (StringUtils.isNotEmpty(sysDataItem.getTextField())) {
							json.put("text", newDataMap.get(sysDataItem
									.getTextField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem.getValueField())) {
							json.put("value", newDataMap.get(sysDataItem
									.getValueField().toLowerCase()));
						}
						if (StringUtils
								.isNotEmpty(sysDataItem.getTreeIdField())) {
							json.put("id", newDataMap.get(sysDataItem
									.getTreeIdField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem
								.getTreeParentIdField())) {
							json.put("parentId", newDataMap.get(sysDataItem
									.getTreeParentIdField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem
								.getTreeNameField())) {
							json.put("name", newDataMap.get(sysDataItem
									.getTreeNameField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem
								.getTreeTreeIdField())) {
							json.put("treeId", newDataMap.get(sysDataItem
									.getTreeTreeIdField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem
								.getTreeListNoField())) {
							json.put("listno", newDataMap.get(sysDataItem
									.getTreeListNoField().toLowerCase()));
						}
						result.add(json);
					}
				}
			} else if (StringUtils.isNotEmpty(sysDataItem.getQuerySQL())) {
				if (DBUtils.isLegalQuerySql(sysDataItem.getQuerySQL())) {
					throw new RuntimeException(" SQL statement illegal ");
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("queryString", sysDataItem.getQuerySQL());
				params.put("parameter", parameter);
				List<Map<String, Object>> list = tablePageMapper
						.getSqlQueryList(params);
				if (list != null && !list.isEmpty()) {
					Map<String, Object> newDataMap = new CaseInsensitiveHashMap();
					for (Map<String, Object> dataMap : list) {
						newDataMap.clear();
						JSONObject json = new JSONObject();
						Iterator<Entry<String, Object>> iterator = dataMap
								.entrySet().iterator();
						while (iterator.hasNext()) {
							Entry<String, Object> entry = iterator.next();
							String name = entry.getKey();
							Object value = entry.getValue();
							newDataMap.put(name, value);
							newDataMap.put(name.toLowerCase(), value);
							json.put(name, value);
						}
						if (StringUtils.isNotEmpty(sysDataItem.getTextField())) {
							json.put("text", newDataMap.get(sysDataItem
									.getTextField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem.getValueField())) {
							json.put("value", newDataMap.get(sysDataItem
									.getValueField().toLowerCase()));
						}
						if (StringUtils
								.isNotEmpty(sysDataItem.getTreeIdField())) {
							json.put("id", newDataMap.get(sysDataItem
									.getTreeIdField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem
								.getTreeParentIdField())) {
							json.put("parentId", newDataMap.get(sysDataItem
									.getTreeParentIdField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem
								.getTreeNameField())) {
							json.put("name", newDataMap.get(sysDataItem
									.getTreeNameField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem
								.getTreeTreeIdField())) {
							json.put("treeId", newDataMap.get(sysDataItem
									.getTreeTreeIdField().toLowerCase()));
						}
						if (StringUtils.isNotEmpty(sysDataItem
								.getTreeListNoField())) {
							json.put("listno", newDataMap.get(sysDataItem
									.getTreeListNoField().toLowerCase()));
						}
						result.add(json);
					}
				}
			}

			if (StringUtils.equals("Y", sysDataItem.getCacheFlag())) {
				if (!result.isEmpty()) {
					CacheFactory.put(cacheKey, result.toJSONString());
				}
			}
		}

		return result;
	}

	public SysDataItem getSysDataItemById(long id) {
		if (id == 0) {
			return null;
		}
		String cacheKey = "sys_dataitem_" + id;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONObject json = JSON.parseObject(text);
			return SysDataItemJsonFactory.jsonToObject(json);
		}
		SysDataItem sysDataItem = sysDataItemMapper.getSysDataItemById(id);
		JSONObject json = SysDataItemJsonFactory.toJsonObject(sysDataItem);
		CacheFactory.put(cacheKey, json.toJSONString());
		return sysDataItem;
	}

	/**
	 * 根据名称获取一条记录
	 * 
	 * @return
	 */
	public SysDataItem getSysDataItemByName(String name) {
		SysDataItemQuery query = new SysDataItemQuery();
		query.name(name);
		List<SysDataItem> list = sysDataItemMapper.getSysDataItems(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSysDataItemCountByQueryCriteria(SysDataItemQuery query) {
		return sysDataItemMapper.getSysDataItemCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysDataItem> getSysDataItemsByQueryCriteria(int start,
			int pageSize, SysDataItemQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysDataItem> rows = sqlSessionTemplate.selectList(
				"getSysDataItems", query, rowBounds);
		return rows;
	}

	public List<SysDataItem> list(SysDataItemQuery query) {
		List<SysDataItem> list = sysDataItemMapper.getSysDataItems(query);
		return list;
	}

	@Transactional
	public void save(SysDataItem sysDataItem) {
		if (sysDataItem.getId() == 0) {
			sysDataItem.setId(idGenerator.nextId());
			sysDataItem.setCreateTime(new Date());
			sysDataItemMapper.insertSysDataItem(sysDataItem);
		} else {
			String cacheKey = "sys_dataitem_" + sysDataItem.getId();
			CacheFactory.remove(cacheKey);
			cacheKey = "sys_dataitem_data_" + sysDataItem.getId();
			CacheFactory.remove(cacheKey);
			sysDataItem.setUpdateTime(new Date());
			sysDataItemMapper.updateSysDataItem(sysDataItem);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysDataItemMapper(SysDataItemMapper sysDataItemMapper) {
		this.sysDataItemMapper = sysDataItemMapper;
	}

	@javax.annotation.Resource
	public void setTableDataMapper(TableDataMapper tableDataMapper) {
		this.tableDataMapper = tableDataMapper;
	}

	@javax.annotation.Resource
	public void setTablePageMapper(TablePageMapper tablePageMapper) {
		this.tablePageMapper = tablePageMapper;
	}

}
