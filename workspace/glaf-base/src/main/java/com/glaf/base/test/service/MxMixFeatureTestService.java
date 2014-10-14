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

package com.glaf.base.test.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.SysLog;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.service.ISysLogService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;

@Service("mixFeatureTestService")
@Transactional
public class MxMixFeatureTestService implements IMixFeatureTestService {

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ISysLogService sysLogService;

	protected JdbcTemplate jdbcTemplate;

	@Transactional
	public void run() {
		for (int i = 0; i < 100; i++) {
			SysLog bean = new SysLog();
			bean.setAccount("test");
			bean.setIp("127.0.0.1");
			bean.setOperate("add");
			sysLogService.create(bean);
		}

		String sql = "insert into SYS_LOG(ID, ACCOUNT, IP, CREATETIME, MODULEID, OPERATE, FLAG, TIMEMS) values (?, ?, ?, ?, ?, ?, ?, ?) ";
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate
					.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 100; i++) {
				psmt.setLong(1, idGenerator.nextId());
				psmt.setString(2, "test2");
				psmt.setString(3, "192.168.0.100");
				psmt.setTimestamp(4, DateUtils.toTimestamp(new Date()));
				psmt.setString(5, "xx");
				psmt.setString(6, "Y");
				psmt.setInt(7, 1);
				psmt.setLong(8, i * i);
				psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
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
	public void setSysLogService(ISysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

}
