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

import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.domain.SysLog;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.SysLogMapper;
import com.glaf.core.query.SysLogQuery;
import com.glaf.core.service.ISysLogService;

@Service("sysLogService")
@Transactional(readOnly = true)
public class MxSysLogServiceImpl implements ISysLogService {
	protected final static Log logger = LogFactory
			.getLog(MxSysLogServiceImpl.class);
	protected static Configuration conf = BaseConfiguration.create();

	protected static BlockingQueue<SysLog> sysLogs = new ArrayBlockingQueue<SysLog>(
			1000);

	protected static long lastUpdate = System.currentTimeMillis();

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysLogMapper sysLogMapper;

	public MxSysLogServiceImpl() {

	}

	public int count(SysLogQuery query) {
		query.ensureInitialized();
		return sysLogMapper.getSysLogCount(query);
	}

	@Transactional
	public boolean create(SysLog bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(SysLog bean) {
		this.deleteById(bean.getId());
		return true;
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sysLogMapper.deleteSysLogById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SysLogQuery query = new SysLogQuery();
			query.rowIds(rowIds);
			sysLogMapper.deleteSysLogs(query);
		}
	}

	public SysLog findById(long id) {
		return this.getSysLog(id);
	}

	public SysLog getSysLog(Long id) {
		if (id == null) {
			return null;
		}
		SysLog sysLog = sysLogMapper.getSysLogById(id);
		return sysLog;
	}

	public int getSysLogCountByQueryCriteria(SysLogQuery query) {
		return sysLogMapper.getSysLogCount(query);
	}

	public List<SysLog> getSysLogsByQueryCriteria(int start, int pageSize,
			SysLogQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysLog> rows = sqlSessionTemplate.selectList("getSysLogs", query,
				rowBounds);
		return rows;
	}

	public List<SysLog> list(SysLogQuery query) {
		query.ensureInitialized();
		List<SysLog> list = sysLogMapper.getSysLogs(query);
		return list;
	}

	@Transactional
	public void save(SysLog sysLog) {
		sysLog.setId(idGenerator.nextId());
		sysLog.setCreateTime(new Date());
		try {
			sysLogs.put(sysLog);
		} catch (InterruptedException ex) {
		}
		/**
		 * 当记录数达到写数据库的条数或时间超过1分钟，写日志到数据库
		 */
		if (sysLogs.size() >= conf.getInt("sys_log_step", 100)
				|| ((System.currentTimeMillis() - lastUpdate) / 60000 > 0)) {
			while (!sysLogs.isEmpty()) {
				SysLog bean = sysLogs.poll();
				sysLogMapper.insertSysLog(bean);
			}
			lastUpdate = System.currentTimeMillis();
		}
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
	public void setSysLogMapper(SysLogMapper sysLogMapper) {
		this.sysLogMapper = sysLogMapper;
	}

	@Transactional
	public boolean update(SysLog bean) {
		this.save(bean);
		return true;
	}

}
