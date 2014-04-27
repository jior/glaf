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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.mapper.*;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.service.SysDataService;

@Service("sysDataService")
@Transactional(readOnly = true)
public class MxSysDataServiceImpl implements SysDataService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysDataMapper sysDataMapper;

	public MxSysDataServiceImpl() {

	}

	public int count(SysDataQuery query) {
		query.ensureInitialized();
		return sysDataMapper.getSysDataCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			sysDataMapper.deleteSysDataById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				sysDataMapper.deleteSysDataById(id);
			}
		}
	}

	public SysData getSysData(String id) {
		if (id == null) {
			return null;
		}
		SysData sysData = sysDataMapper.getSysDataById(id);
		return sysData;
	}

	public int getSysDataCountByQueryCriteria(SysDataQuery query) {
		return sysDataMapper.getSysDataCount(query);
	}

	public List<SysData> getSysDatasByQueryCriteria(int start, int pageSize,
			SysDataQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysData> rows = sqlSessionTemplate.selectList("getSysDatas",
				query, rowBounds);
		return rows;
	}

	public List<SysData> list(SysDataQuery query) {
		query.ensureInitialized();
		List<SysData> list = sysDataMapper.getSysDatas(query);
		return list;
	}

	@Transactional
	public void save(SysData sysData) {
		SysData model = this.getSysData(sysData.getId());

		if (model == null) {
			sysDataMapper.insertSysData(sysData);
		} else {
			sysDataMapper.updateSysData(sysData);
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
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysDataMapper(SysDataMapper sysDataMapper) {
		this.sysDataMapper = sysDataMapper;
	}

}
