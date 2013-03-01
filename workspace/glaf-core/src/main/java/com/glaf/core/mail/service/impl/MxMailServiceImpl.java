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

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.DataFile;
import com.glaf.core.entity.EntityDAO;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mail.Mail;
import com.glaf.core.mail.MailMessage;
import com.glaf.core.mail.MailSender;
import com.glaf.core.mail.service.IMailService;
import com.glaf.core.mail.util.MailStatus;
import com.glaf.core.mail.mapper.MailMapper;
import com.glaf.core.query.MailQuery;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.UUID32;

@Service("mailService")
@Transactional(readOnly = true)
public class MxMailServiceImpl implements IMailService {
	protected static Log logger = LogFactory.getLog(MxMailServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected MailMapper mailMapper;

	protected IBlobService blobService;

	protected MailSender mailSender;

	public MxMailServiceImpl() {

	}

	public int count(MailQuery query) {
		query.ensureInitialized();
		return mailMapper.getMailCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		mailMapper.deleteMailById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		MailQuery query = new MailQuery();
		query.rowIds(rowIds);
		mailMapper.deleteMails(query);
	}

	@Transactional
	public void deleteMail(String mailId) {
		Mail mail = this.getMail(mailId);
		if (mail != null) {
			blobService.deleteBlobByResourceId(mail.getResourceId());
			this.deleteById(mail.getId());
		}
	}

	public Mail getMail(String mailId) {
		Mail mail = mailMapper.getMailByMailId(mailId);
		return mail;
	}

	public List<Mail> getMailList(String resourceId) {
		MailQuery query = new MailQuery();
		query.resourceId(resourceId);
		return this.list(query);
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public Paging getPage(MailQuery query) {
		Paging jpage = new Paging();

		int count = mailMapper.getMailCount(query);
		if (count > 0) {
			SqlExecutor queryExecutor = new SqlExecutor();
			queryExecutor.setParameter(query);
			queryExecutor.setStatementId("getMails");
			List<Object> rows = entityDAO.getList(query.getPageNo(),
					query.getPageSize(), queryExecutor);
			jpage.setTotal(count);
			jpage.setCurrentPage(query.getPageNo());
			jpage.setPageSize(query.getPageSize());
			jpage.setRows(rows);
		} else {
			jpage.setPageSize(0);
			jpage.setCurrentPage(0);
			jpage.setTotal(0);
		}
		return jpage;
	}

	public List<Mail> list(MailQuery query) {
		query.ensureInitialized();
		List<Mail> list = mailMapper.getMails(query);
		return list;
	}

	@Transactional
	public void saveMail(Mail mail) {
		if (StringUtils.isEmpty(mail.getMailId())) {
			mail.setMailId(UUID32.getUUID());
		}
		if (StringUtils.isEmpty(mail.getResourceId())) {
			mail.setResourceId(mail.getMailId());
		}
		mail.setCreateDate(new Date());
		mail.setId(idGenerator.getNextId());
		mailMapper.insertMail(mail);
	}

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 * @return
	 */
	@Transactional
	public boolean send(Mail mail) {
		return this.send(mail, null);
	}

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 *            邮件对象
	 * @param dataMap
	 *            参数集
	 * @return
	 */
	@Transactional
	public boolean send(Mail mail, Map<String, Object> dataMap) {
		boolean sendOK = false;
		if (StringUtils.isEmpty(mail.getMailId())) {
			mail.setMailId(UUID32.getUUID());
		}
		if (StringUtils.isEmpty(mail.getMessageId())) {
			mail.setMessageId(mail.getMailId());
		}
		MailMessage mailMessage = new MailMessage();
		mailMessage.setDataMap(dataMap);
		if (StringUtils.isNotEmpty(mail.getMailBCC())) {
			mailMessage.setBcc(mail.getMailBCC());
		}
		if (StringUtils.isNotEmpty(mail.getMailCC())) {
			mailMessage.setCc(mail.getMailCC());
		}
		mailMessage.setTemplateId(mail.getTemplateId());
		mailMessage.setTo(mail.getMailTo());
		mailMessage.setFrom(mail.getMailFrom());
		mailMessage.setMessageId(mail.getMessageId());
		mailMessage.setSubject(mail.getSubject());
		mailMessage.setContent(mail.getContent());
		if (mail.getDataFiles() != null && mail.getDataFiles().size() > 0) {
			Iterator<DataFile> iterator = mail.getDataFiles().iterator();
			while (iterator.hasNext()) {
				mailMessage.addFile(iterator.next());
			}
		}
		try {
			mailSender.send(mailMessage);
			mail.setStatus(MailStatus.HAS_SENT);
			mail.setSendStatus(1);
			mail.setSendDate(new Date());
			sendOK = true;
		} catch (Throwable ex) {
			sendOK = false;
			mail.setStatus(MailStatus.SEND_FAILED);
			mail.setSendStatus(-1);
			mail.setRetryTimes(mail.getRetryTimes() + 1);
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			if (ex instanceof javax.mail.internet.ParseException) {
				mail.setSendStatus(-10);
			} else if (ex instanceof javax.mail.AuthenticationFailedException) {
				mail.setSendStatus(-20);
			} else if (ex instanceof javax.mail.internet.AddressException) {
				mail.setSendStatus(-30);
			} else if (ex instanceof javax.mail.SendFailedException) {
				mail.setSendStatus(-40);
			} else if (ex instanceof java.net.UnknownHostException) {
				mail.setSendStatus(-50);
			} else if (ex instanceof java.net.SocketException) {
				mail.setSendStatus(-60);
			} else if (ex instanceof java.io.IOException) {
				mail.setSendStatus(-70);
			} else if (ex instanceof java.net.ConnectException) {
				mail.setSendStatus(-80);
			} else if (ex instanceof javax.mail.MessagingException) {
				mail.setSendStatus(-90);
				if (ex.getMessage().indexOf("response: 554") != -1) {
					mail.setSendStatus(-99);
				}
			}
		}

		return sendOK;
	}

	@Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@Resource
	public void setMailMapper(MailMapper mailMapper) {
		this.mailMapper = mailMapper;
	}

	@Resource
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
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

	@Transactional
	public void updateMail(Mail mail) {
		mailMapper.updateMail(mail);
	}

}