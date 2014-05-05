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
import com.glaf.mail.domain.MailAccount;
import com.glaf.mail.mapper.MailAccountMapper;
import com.glaf.mail.query.MailAccountQuery;
import com.glaf.mail.service.IMailAccountService;

@Service("mailAccountService")
@Transactional(readOnly = true)
public class MxMailAccountServiceImpl implements IMailAccountService {
	protected final static Log logger = LogFactory
			.getLog(MxMailAccountServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected MailAccountMapper mailAccountMapper;

	public MxMailAccountServiceImpl() {

	}

	public int count(MailAccountQuery query) {
		return mailAccountMapper.getMailAccountCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		mailAccountMapper.deleteMailAccountById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		MailAccountQuery query = new MailAccountQuery();
		query.rowIds(rowIds);
		mailAccountMapper.deleteMailAccounts(query);
	}

	public MailAccount getMailAccount(String id) {

		MailAccount mailAccount = mailAccountMapper.getMailAccountById(id);

		return mailAccount;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getMailAccountCountByQueryCriteria(MailAccountQuery query) {
		return mailAccountMapper.getMailAccountCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MailAccount> getMailAccountsByQueryCriteria(int start,
			int pageSize, MailAccountQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MailAccount> rows = sqlSession.selectList("getMailAccounts",
				query, rowBounds);
		return rows;
	}

	public List<MailAccount> list(MailAccountQuery query) {
		List<MailAccount> list = mailAccountMapper.getMailAccounts(query);
		return list;
	}

	@Transactional
	public void save(MailAccount mailAccount) {
		if (StringUtils.isEmpty(mailAccount.getId())) {
			mailAccount.setId(idGenerator.getNextId());
			mailAccount.setCreateDate(new Date());
			mailAccountMapper.insertMailAccount(mailAccount);
		} else {
			mailAccountMapper.updateMailAccount(mailAccount);
			String cacheKey = "mx_mail_account_" + mailAccount.getId();
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
	public void setMailAccountMapper(MailAccountMapper mailAccountMapper) {
		this.mailAccountMapper = mailAccountMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}