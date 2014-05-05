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

import com.glaf.core.config.Configuration;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.DateUtils;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.mapper.SysDataLogMapper;
import com.glaf.core.query.SysDataLogQuery;
import com.glaf.core.service.SysDataLogService;

@Service("sysDataLogService")
@Transactional(readOnly = true)
public class MxSysDataLogServiceImpl implements SysDataLogService {
	protected final static Log logger = LogFactory
			.getLog(MxSysDataLogServiceImpl.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static BlockingQueue<SysDataLog> dataLogs = new ArrayBlockingQueue<SysDataLog>(
			1000);

	protected static long lastUpdate = System.currentTimeMillis();

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysDataLogMapper sysLogMapper;

	public MxSysDataLogServiceImpl() {

	}

	public int count(SysDataLogQuery query) {
		return sysLogMapper.getSysDataLogCount(query);
	}

	public int getSysDataLogCountByQueryCriteria(SysDataLogQuery query) {
		return sysLogMapper.getSysDataLogCount(query);
	}

	public List<SysDataLog> getSysDataLogsByQueryCriteria(int start,
			int pageSize, SysDataLogQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysDataLog> rows = sqlSessionTemplate.selectList("getSysDataLogs",
				query, rowBounds);
		return rows;
	}

	public List<SysDataLog> list(SysDataLogQuery query) {
		List<SysDataLog> list = sysLogMapper.getSysDataLogs(query);
		return list;
	}

	@Transactional
	public void save(SysDataLog sysLog) {
		sysLog.setId(idGenerator.nextId());
		sysLog.setCreateTime(new Date());
		sysLog.setSuffix("_" + DateUtils.getNowYearMonthDay());
		try {
			dataLogs.put(sysLog);
		} catch (InterruptedException ex) {
		}
		logger.debug("->dataLogs.size:" + dataLogs.size());
		/**
		 * 当记录数达到写数据库的条数或时间超过1分钟，写日志到数据库
		 */
		if (dataLogs.size() >= conf.getInt("sys_log_step", 100)
				|| ((System.currentTimeMillis() - lastUpdate) / 60000 > 0)) {
			SysDataLog bean = null;
			while (!dataLogs.isEmpty()) {
				bean = dataLogs.poll();
				sysLogMapper.insertSysDataLog(bean);// 写历史表
				bean.setSuffix("");
				sysLogMapper.insertSysDataLog(bean);// 写当前表
			}
			lastUpdate = System.currentTimeMillis();
			logger.debug("dataLogs.size:" + dataLogs.size());
		}
	}

	@Transactional
	public void saveAll() {
		if (dataLogs.size() >= conf.getInt("sys_log_step", 100)
				|| ((System.currentTimeMillis() - lastUpdate) / 60000 > 0)) {
			SysDataLog bean = null;
			while (!dataLogs.isEmpty()) {
				bean = dataLogs.poll();
				sysLogMapper.insertSysDataLog(bean);// 写历史表

				bean.setSuffix("");
				sysLogMapper.insertSysDataLog(bean);// 写当前表
			}
			lastUpdate = System.currentTimeMillis();
			logger.debug("dataLogs.size:" + dataLogs.size());
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
	public void setSysDataLogMapper(SysDataLogMapper sysLogMapper) {
		this.sysLogMapper = sysLogMapper;
	}

}
