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

package com.glaf.base.modules.others.service.mybatis;

import java.util.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;

import com.glaf.base.modules.others.mapper.*;
import com.glaf.base.modules.others.model.*;
import com.glaf.base.modules.others.query.*;
import com.glaf.base.modules.others.service.*;

@Service("attachmentService")
@Transactional(readOnly = true)
public class AttachmentServiceImpl implements AttachmentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected LongIdGenerator idGenerator;

	protected PersistenceDAO persistenceDAO;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected AttachmentMapper attachmentMapper;

	public AttachmentServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			attachmentMapper.deleteAttachmentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			AttachmentQuery query = new AttachmentQuery();
			query.rowIds(rowIds);
			attachmentMapper.deleteAttachments(query);
		}
	}

	public int count(AttachmentQuery query) {
		query.ensureInitialized();
		return attachmentMapper.getAttachmentCount(query);
	}

	public List<Attachment> list(AttachmentQuery query) {
		query.ensureInitialized();
		List<Attachment> list = attachmentMapper.getAttachments(query);
		return list;
	}

	public int getAttachmentCountByQueryCriteria(AttachmentQuery query) {
		return attachmentMapper.getAttachmentCount(query);
	}

	public List<Attachment> getAttachmentsByQueryCriteria(int start,
			int pageSize, AttachmentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Attachment> rows = sqlSessionTemplate.selectList("getAttachments",
				query, rowBounds);
		return rows;
	}

	public Attachment getAttachment(Long id) {
		if (id == null) {
			return null;
		}
		Attachment attachment = attachmentMapper.getAttachmentById(id);
		return attachment;
	}

	@Transactional
	public void save(Attachment attachment) {
		if (attachment.getId() == 0L) {
			attachment.setId(idGenerator.getNextId());
			// attachment.setCreateDate(new Date());
			attachmentMapper.insertAttachment(attachment);
		} else {
			attachmentMapper.updateAttachment(attachment);
		}
	}

	@Resource
	@Qualifier("myBatisDbLongIdGenerator")
	public void setLongIdGenerator(LongIdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setAttachmentMapper(AttachmentMapper attachmentMapper) {
		this.attachmentMapper = attachmentMapper;
	}

	@Resource
	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	public boolean create(Attachment bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean update(Attachment bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(Attachment bean) {
		this.deleteById(bean.getId());
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean deleteAll(long[] ids) {
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				this.deleteById(id);
			}
		}
		return true;
	}

	public Attachment find(long id) {
		return this.getAttachment(id);
	}

	public List<Attachment> getAttachmentList(long referId, int referType) {
		AttachmentQuery query = new AttachmentQuery();
		query.referId(referId);
		query.referType(referType);
		query.setOrderBy(" E.ID desc ");
		return this.list(query);
	}

	public Attachment find(long referId, int referType) {
		AttachmentQuery query = new AttachmentQuery();
		query.referId(referId);
		query.referType(referType);
		query.setOrderBy(" E.ID desc ");
		List<Attachment> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public Attachment find(long id, long referId, int referType) {
		AttachmentQuery query = new AttachmentQuery();
		query.referId(referId);
		query.referType(referType);
		query.setOrderBy(" E.ID desc ");
		List<Attachment> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public Attachment find(long referId, int referType, String name) {
		AttachmentQuery query = new AttachmentQuery();
		query.referId(referId);
		query.referType(referType);
		query.name(name);
		query.setOrderBy(" E.ID desc ");
		List<Attachment> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public Map<String, String> getNameMap(long referId, int referType) {
		AttachmentQuery query = new AttachmentQuery();
		query.referId(referId);
		query.referType(referType);
		query.setOrderBy(" E.ID desc ");
		Map<String, String> map = new HashMap<String, String>();
		List<Attachment> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			for (int i = 0; list != null && i < list.size(); i++) {
				Attachment atta = (Attachment) list.get(i);
				map.put(atta.getName(), atta.getName());
			}
		}
		return map;
	}

	public List<Attachment> getAttachmentList(long[] referIds, int referType) {
		List<Long> rows = new ArrayList<Long>();
		for (int i = 0; i < referIds.length; i++) {
			rows.add(referIds[i]);
		}
		AttachmentQuery query = new AttachmentQuery();
		query.referIds(rows);
		query.referType(referType);
		query.setOrderBy(" E.ID desc ");
		return this.list(query);
	}

	public int getAttachmentCount(long[] referIds, int referType) {
		List<Long> rows = new ArrayList<Long>();
		for (int i = 0; i < referIds.length; i++) {
			rows.add(referIds[i]);
		}
		AttachmentQuery query = new AttachmentQuery();
		query.referIds(rows);
		query.referType(referType);
		query.setOrderBy(" E.ID desc ");
		return this.count(query);
	}

	public List<Attachment> getPurchaseAttachmentList(long[] ids, int referType) {
		List<Long> rows = new ArrayList<Long>();
		for (int i = 0; i < ids.length; i++) {
			rows.add(ids[i]);
		}
		AttachmentQuery query = new AttachmentQuery();
		query.rowIds(rows);
		query.referType(referType);
		query.setOrderBy(" E.ID desc ");
		return this.list(query);
	}

}
