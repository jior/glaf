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

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.others.mapper.AuditMapper;
import com.glaf.base.modules.others.model.Audit;
import com.glaf.base.modules.others.query.AuditQuery;
import com.glaf.base.modules.others.service.AuditService;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.core.id.IdGenerator;

@Service("auditService")
@Transactional(readOnly = true)
public class AuditServiceImpl implements AuditService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected AuditMapper auditMapper;

	public AuditServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			auditMapper.deleteAuditById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			AuditQuery query = new AuditQuery();
			query.rowIds(rowIds);
			auditMapper.deleteAudits(query);
		}
	}

	public int count(AuditQuery query) {
		query.ensureInitialized();
		return auditMapper.getAuditCount(query);
	}

	public List<Audit> list(AuditQuery query) {
		query.ensureInitialized();
		List<Audit> list = auditMapper.getAudits(query);
		return list;
	}

	public int getAuditCountByQueryCriteria(AuditQuery query) {
		return auditMapper.getAuditCount(query);
	}

	public List<Audit> getAuditsByQueryCriteria(int start, int pageSize,
			AuditQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Audit> rows = sqlSessionTemplate.selectList("getAudits", query,
				rowBounds);
		return rows;
	}

	public Audit getAudit(Long id) {
		if (id == null) {
			return null;
		}
		Audit audit = auditMapper.getAuditById(id);
		return audit;
	}

	@Transactional
	public void save(Audit audit) {
		if (audit.getId() == 0L) {
			audit.setId(idGenerator.nextId());
			// audit.setCreateDate(new Date());
			auditMapper.insertAudit(audit);
		} else {
			auditMapper.updateAudit(audit);
		}
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setAuditMapper(AuditMapper auditMapper) {
		this.auditMapper = auditMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	public boolean create(Audit bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean update(Audit bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(Audit bean) {
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

	public Audit findById(long id) {
		return this.getAudit(id);
	}

	public List<Audit> getAuditList(long referId, int referType) {
		AuditQuery query = new AuditQuery();
		query.referId(referId);
		query.referType(referType);
		query.setOrderBy(" E.ID asc ");
		return this.list(query);
	}

	public List<Audit> getAuditList(long referId, String referTypes) {
		List<Integer> rows = new java.util.concurrent.CopyOnWriteArrayList<Integer>();
		StringTokenizer token = new StringTokenizer(referTypes, ",");
		while (token.hasMoreTokens()) {
			String str = token.nextToken();
			rows.add(Integer.parseInt(str));
		}
		AuditQuery query = new AuditQuery();
		query.referId(referId);
		query.referTypes(rows);
		query.setOrderBy(" E.ID asc ");
		return this.list(query);
	}

	public List<Audit> getAuditUserList(long referId, int referType) {
		AuditQuery query = new AuditQuery();
		query.referId(referId);
		query.referType(referType);
		query.flag(0);
		query.setOrderBy(" E.createDate desc ");
		Date rejectDate = null;
		List<Audit> list = this.list(query);
		if (list != null && list.size() > 0) {
			Audit audit = (Audit) list.get(0);
			if (audit != null) {
				rejectDate = audit.getCreateDate();
			}
		}

		if (rejectDate != null) {
			query.createDateGreaterThanOrEqual(rejectDate);
			query.flag(1);
		}

		return this.list(query);
	}

	public List<Audit> getAuditDeptList(long referId, int referType, long deptId) {
		AuditQuery query = new AuditQuery();
		query.referId(referId);
		query.referType(referType);
		query.deptId(deptId);
		query.setOrderBy(" E.createDate desc ");
		return this.list(query);
	}

	@Transactional
	public boolean saveAudit(SysUser user, long referId, int referType,
			boolean confirm) {
		Audit bean = new Audit();
		bean.setDeptId(user.getDepartment().getId());
		bean.setDeptName(user.getDepartment().getName());
		bean.setHeadship(user.getHeadship());
		bean.setLeaderId(user.getId());
		bean.setLeaderName(user.getName());
		bean.setMemo("");
		bean.setReferId(referId);
		bean.setReferType(referType);
		bean.setFlag(confirm ? 1 : 0);
		bean.setCreateDate(new Date());
		return create(bean);
	}

	public List<Audit> getAuditNotList(long referId) {
		AuditQuery query = new AuditQuery();
		query.referId(referId);
		query.flag(0);
		query.setOrderBy(" E.ID desc ");
		return this.list(query);
	}

}
