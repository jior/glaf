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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.SystemPropertyMapper;
import com.glaf.core.query.SystemPropertyQuery;
import com.glaf.core.service.ISystemPropertyService;

@Service("systemPropertyService")
@Transactional(readOnly = true)
public class MxSystemPropertyServiceImpl implements ISystemPropertyService {
	protected final static Log logger = LogFactory
			.getLog(MxSystemPropertyServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected SystemPropertyMapper systemPropertyMapper;

	public MxSystemPropertyServiceImpl() {

	}

	public int count(SystemPropertyQuery query) {
		return systemPropertyMapper.getSystemPropertyCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		systemPropertyMapper.deleteSystemPropertyById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		SystemPropertyQuery query = new SystemPropertyQuery();
		query.rowIds(rowIds);
		systemPropertyMapper.deleteSystemProperties(query);
	}

	public List<SystemProperty> getAllSystemProperties() {
		SystemPropertyQuery query = new SystemPropertyQuery();
		List<SystemProperty> list = this.list(query);
		List<SystemProperty> rows = new ArrayList<SystemProperty>();
		if (list != null && !list.isEmpty()) {
			for (SystemProperty p : list) {
				if (!StringUtils.equals("TOKEN", p.getId())) {
					rows.add(p);
				}
			}
		}
		return rows;
	}

	public Map<String, SystemProperty> getProperyMap() {
		List<SystemProperty> list = this.getAllSystemProperties();
		Map<String, SystemProperty> dataMap = new java.util.HashMap<String, SystemProperty>();
		Iterator<SystemProperty> iterator = list.iterator();
		while (iterator.hasNext()) {
			SystemProperty p = iterator.next();
			dataMap.put(p.getName(), p);
		}
		return dataMap;
	}

	public List<SystemProperty> getSystemProperties(String category) {
		SystemPropertyQuery query = new SystemPropertyQuery();
		query.category(category);
		List<SystemProperty> list = this.list(query);
		List<SystemProperty> rows = new ArrayList<SystemProperty>();
		if (list != null && !list.isEmpty()) {
			for (SystemProperty p : list) {
				if (!StringUtils.equals("TOKEN", p.getId())) {
					rows.add(p);
				}
			}
		}
		return rows;
	}

	public SystemProperty getSystemProperty(String category, String name) {
		SystemPropertyQuery query = new SystemPropertyQuery();
		query.category(category);
		query.name(name);
		List<SystemProperty> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public SystemProperty getSystemPropertyById(String id) {
		SystemProperty systemProperty = systemPropertyMapper
				.getSystemPropertyById(id);
		return systemProperty;
	}

	public List<SystemProperty> list(SystemPropertyQuery query) {
		List<SystemProperty> list = systemPropertyMapper
				.getSystemProperties(query);
		List<SystemProperty> rows = new ArrayList<SystemProperty>();
		if (list != null && !list.isEmpty()) {
			for (SystemProperty p : list) {
				if (!StringUtils.equals("TOKEN", p.getId())) {
					rows.add(p);
				}
			}
		}
		return rows;
	}

	@Transactional
	public void save(SystemProperty property) {
		if (StringUtils.isNotEmpty(property.getId())) {
			SystemProperty bean = this.getSystemPropertyById(property.getId());
			if (bean != null) {
				bean.setDescription(property.getDescription());
				bean.setValue(property.getValue());
				bean.setInitValue(property.getInitValue());
				bean.setInputType(property.getInputType());
				bean.setLocked(property.getLocked());
				bean.setTitle(property.getTitle());
				bean.setType(property.getType());
				systemPropertyMapper.updateSystemProperty(bean);
				SystemConfig.setProperty(bean);
			} else {
				systemPropertyMapper.insertSystemProperty(property);
				SystemConfig.setProperty(property);
			}
		} else {
			SystemPropertyQuery query = new SystemPropertyQuery();
			query.category(property.getCategory());
			query.name(property.getName());
			List<SystemProperty> list = this.list(query);
			if (list != null && !list.isEmpty()) {
				SystemProperty bean = list.get(0);
				bean.setDescription(property.getDescription());
				bean.setValue(property.getValue());
				bean.setInitValue(property.getInitValue());
				bean.setInputType(property.getInputType());
				bean.setLocked(property.getLocked());
				bean.setTitle(property.getTitle());
				bean.setType(property.getType());
				systemPropertyMapper.updateSystemProperty(bean);
				SystemConfig.setProperty(bean);
			} else {
				if (property.getId() == null) {
					property.setId(idGenerator.getNextId());
				}
				systemPropertyMapper.insertSystemProperty(property);
				SystemConfig.setProperty(property);
			}
		}
	}

	@Transactional
	public void saveAll(List<SystemProperty> props) {
		Map<String, SystemProperty> propertyMap = this.getProperyMap();
		if (props != null && props.size() > 0) {
			Map<String, String> dataMap = new TreeMap<String, String>();
			Iterator<SystemProperty> iterator = props.iterator();
			while (iterator.hasNext()) {
				SystemProperty p = iterator.next();
				dataMap.put(p.getName(), p.getValue());

				if (propertyMap.get(p.getName()) != null) {
					SystemProperty model = propertyMap.get(p.getName());
					model.setDescription(p.getDescription());
					model.setTitle(p.getTitle());
					model.setValue(p.getValue());
					systemPropertyMapper.updateSystemProperty(model);
				} else {
					p.setId(idGenerator.getNextId());
					systemPropertyMapper.insertSystemProperty(p);
				}
			}
			SystemConfig.reload();
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
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setSystemPropertyMapper(
			SystemPropertyMapper systemPropertyMapper) {
		this.systemPropertyMapper = systemPropertyMapper;
	}

}