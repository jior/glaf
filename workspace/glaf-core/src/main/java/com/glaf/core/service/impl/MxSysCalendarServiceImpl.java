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
import javax.annotation.Resource;

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
import com.glaf.core.service.*;

@Service("sysCalendarService")
@Transactional(readOnly = true)
public class MxSysCalendarServiceImpl implements ISysCalendarService {
	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysCalendarMapper sysCalendarMapper;

	public MxSysCalendarServiceImpl() {

	}

	public int count(SysCalendarQuery query) {
		query.ensureInitialized();
		return sysCalendarMapper.getSysCalendarCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			sysCalendarMapper.deleteSysCalendarById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				sysCalendarMapper.deleteSysCalendarById(id);
			}
		}
	}

	public SysCalendar getSysCalendar(String id) {
		if (id == null) {
			return null;
		}
		SysCalendar sysCalendar = sysCalendarMapper.getSysCalendarById(id);
		return sysCalendar;
	}

	public SysCalendar getSysCalendar(String productionLine, int year,
			int month, int day) {
		SysCalendarQuery query = new SysCalendarQuery();
		query.setYear(year);
		query.setMonth(month);
		query.setDay(day);
		query.setProductionLine(productionLine);
		List<SysCalendar> list = this.list(query);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public int getSysCalendarCountByQueryCriteria(SysCalendarQuery query) {
		return sysCalendarMapper.getSysCalendarCount(query);
	}

	public List<SysCalendar> getSysCalendarsByQueryCriteria(int start,
			int pageSize, SysCalendarQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysCalendar> rows = sqlSessionTemplate.selectList(
				"getSysCalendars", query, rowBounds);
		return rows;
	}

	public List<SysCalendar> list(SysCalendarQuery query) {
		query.ensureInitialized();
		List<SysCalendar> list = sysCalendarMapper.getSysCalendars(query);
		return list;
	}

	@Transactional
	public void save(SysCalendar sysCalendar) {
		if (sysCalendar.getId() == null) {
			sysCalendar.setId(idGenerator.nextId("SYS_CALENDAR"));
			sysCalendarMapper.insertSysCalendar(sysCalendar);
		} else {
			sysCalendarMapper.updateSysCalendar(sysCalendar);
		}
	}

	@Resource(name = "myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Resource(name = "myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setSysCalendarMapper(SysCalendarMapper sysCalendarMapper) {
		this.sysCalendarMapper = sysCalendarMapper;
	}

}
