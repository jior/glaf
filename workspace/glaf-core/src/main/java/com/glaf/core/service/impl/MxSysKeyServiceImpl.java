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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.dao.*;
import com.glaf.core.mapper.*;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.service.SysKeyService;
import com.glaf.core.util.DBUtils;

@Service("sysKeyService")
@Transactional(readOnly = true)
public class MxSysKeyServiceImpl implements SysKeyService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysKeyMapper sysKeyMapper;

	public MxSysKeyServiceImpl() {

	}

	public int count(SysKeyQuery query) {
		return sysKeyMapper.getSysKeyCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			sysKeyMapper.deleteSysKeyById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				sysKeyMapper.deleteSysKeyById(id);
			}
		}
	}

	public SysKey getSysKey(String id) {
		if (id == null) {
			return null;
		}
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			SysKey sysKey = sysKeyMapper.getSysKeyById_postgres(id);
			return sysKey;
		}
		SysKey sysKey = sysKeyMapper.getSysKeyById(id);
		return sysKey;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSysKeyCountByQueryCriteria(SysKeyQuery query) {
		return sysKeyMapper.getSysKeyCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysKey> getSysKeysByQueryCriteria(int start, int pageSize,
			SysKeyQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysKey> rows = sqlSessionTemplate.selectList("getSysKeys", query,
				rowBounds);
		return rows;
	}

	public List<SysKey> list(SysKeyQuery query) {
		List<SysKey> list = sysKeyMapper.getSysKeys(query);
		return list;
	}

	@Transactional
	public void save(SysKey sysKey) {
		if (StringUtils.isEmpty(sysKey.getId())) {
			sysKey.setId(idGenerator.getNextId("SYS_KEY"));
		}
		sysKey.setCreateDate(new Date());
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			sysKeyMapper.insertSysKey(sysKey);
		} else {
			sysKeyMapper.insertSysKey_postgres(sysKey);
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
	public void setSysKeyMapper(SysKeyMapper sysKeyMapper) {
		this.sysKeyMapper = sysKeyMapper;
	}

}
