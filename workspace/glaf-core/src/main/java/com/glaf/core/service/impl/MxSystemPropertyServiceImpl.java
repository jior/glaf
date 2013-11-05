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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.config.ViewProperties;
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
		query.ensureInitialized();
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
		return this.list(query);
	}

	public Map<String, SystemProperty> getProperyMap() {
		List<SystemProperty> list = this.getAllSystemProperties();
		Map<String, SystemProperty> dataMap = new HashMap<String, SystemProperty>();
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
		return this.list(query);
	}

	public SystemProperty getSystemProperty(String id) {
		SystemProperty systemProperty = systemPropertyMapper
				.getSystemPropertyById(id);
		return systemProperty;
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

	public List<SystemProperty> list(SystemPropertyQuery query) {
		List<SystemProperty> list = systemPropertyMapper
				.getSystemProperties(query);
		return list;
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
				if (StringUtils.isNotEmpty(p.getValue())) {
					ViewProperties.getProperties().setProperty(p.getName(),
							p.getValue());
				} else {
					if (StringUtils.isNotEmpty(p.getInitValue())) {
						ViewProperties.getProperties().setProperty(p.getName(),
								p.getInitValue());
					}
				}
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
		}
	}

	@Resource
	@Qualifier("myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Resource
	public void setSystemPropertyMapper(
			SystemPropertyMapper systemPropertyMapper) {
		this.systemPropertyMapper = systemPropertyMapper;
	}

}