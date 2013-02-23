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

package com.glaf.core.mail.service.impl;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.entity.EntityDAO;
import com.glaf.core.id.*;
import com.glaf.core.mail.domain.*;
import com.glaf.core.mail.mapper.*;
import com.glaf.core.mail.query.*;
import com.glaf.core.mail.service.*;

@Service("mailCountService")
@Transactional(readOnly = true)
public class MxMailCountServiceImpl implements IMailCountService {
	protected final static Log logger = LogFactory
			.getLog(MxMailCountServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected MailCountMapper mailCountMapper;

	public MxMailCountServiceImpl() {

	}

	public List<MailCount> getMailCountList(String taskId, String type) {
		MailCountQuery query = new MailCountQuery();
		query.taskId(taskId);
		query.type(type);
		List<MailCount> list = this.list(query);
		return list;
	}

	public List<MailCount> list(MailCountQuery query) {
		List<MailCount> list = mailCountMapper.getMailCounts(query);
		return list;
	}

	@Transactional
	public void save(String taskId, String type, List<MailCount> rows) {
		MailCountQuery query = new MailCountQuery();
		query.taskId(taskId);
		query.type(type);

		mailCountMapper.deleteMailCount(query);

		for (MailCount model : rows) {
			if (model.getId() == null) {
				model.setId(idGenerator.getNextId());
			}
			model.setTaskId(taskId);
			model.setType(type);
			model.setLastModified(System.currentTimeMillis());
			mailCountMapper.insertMailCount(model);
		}
	}

	@javax.annotation.Resource
	@Qualifier("myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setMailCountMapper(
			MailCountMapper mailCountMapper) {
		this.mailCountMapper = mailCountMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}