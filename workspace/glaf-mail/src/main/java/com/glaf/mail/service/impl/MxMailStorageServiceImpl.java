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

package com.glaf.mail.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.cache.CacheFactory;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.mail.domain.MailStorage;
import com.glaf.mail.mapper.MailStorageMapper;
import com.glaf.mail.query.MailStorageQuery;
import com.glaf.mail.service.IMailStorageService;

@Service("mailStorageService")
@Transactional(readOnly = true)
public class MxMailStorageServiceImpl implements IMailStorageService {
	protected final static Log logger = LogFactory
			.getLog(MxMailStorageServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected MailStorageMapper mailStorageMapper;

	public MxMailStorageServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		mailStorageMapper.deleteMailStorageById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		MailStorageQuery query = new MailStorageQuery();
		query.rowIds(rowIds);
		mailStorageMapper.deleteMailStorages(query);
	}

	public int count() {
		MailStorageQuery query = new MailStorageQuery();
		return mailStorageMapper.getMailStorageCount(query);
	}

	public int count(MailStorageQuery query) {
		return mailStorageMapper.getMailStorageCount(query);
	}

	public List<MailStorage> list(MailStorageQuery query) {
		List<MailStorage> list = mailStorageMapper.getMailStorages(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getMailStorageCountByQueryCriteria(MailStorageQuery query) {
		return mailStorageMapper.getMailStorageCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MailStorage> getMailStoragesByQueryCriteria(int start,
			int pageSize, MailStorageQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MailStorage> rows = sqlSession.selectList("getMailStorages",
				query, rowBounds);
		return rows;
	}

	public MailStorage getMailStorage(String id) {
		MailStorage mailStorage = mailStorageMapper.getMailStorageById(id);
		return mailStorage;
	}

	@Transactional
	public void save(MailStorage mailStorage) {
		if (StringUtils.isEmpty(mailStorage.getId())) {
			mailStorage.setId(idGenerator.getNextId());
			mailStorage.setCreateDate(new Date());
			mailStorageMapper.insertMailStorage(mailStorage);
		} else {
			mailStorageMapper.updateMailStorage(mailStorage);
			String cacheKey = "mx_mail_stg_" + mailStorage.getId();
			CacheFactory.remove(cacheKey);
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
	public void setMailStorageMapper(MailStorageMapper mailStorageMapper) {
		this.mailStorageMapper = mailStorageMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}