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
package com.glaf.oa.reimbursement.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.reimbursement.mapper.ReimbursementMapper;
import com.glaf.oa.reimbursement.mapper.RitemMapper;
import com.glaf.oa.reimbursement.model.Reimbursement;
import com.glaf.oa.reimbursement.model.Ritem;
import com.glaf.oa.reimbursement.query.RitemQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("ritemService")
@Transactional(readOnly = true)
public class RitemServiceImpl implements RitemService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected RitemMapper ritemMapper;

	protected ReimbursementMapper reimbursementMapper;

	public RitemServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			ritemMapper.deleteRitemById(id);
		}
	}

	@Transactional
	public void deleteById(Long id, Long reimbursementid) {
		if (id != null) {
			ritemMapper.deleteRitemById(id);
		}
		this.updateSumPrice(reimbursementid);
	}

	@Transactional
	public void deleteByIds(List<Long> ritemids) {
		if (ritemids != null && !ritemids.isEmpty()) {
			for (Long id : ritemids) {
				ritemMapper.deleteRitemById(id);
			}
		}
	}

	public int count(RitemQuery query) {
		query.ensureInitialized();
		return ritemMapper.getRitemCount(query);
	}

	public List<Ritem> list(RitemQuery query) {
		query.ensureInitialized();
		List<Ritem> list = ritemMapper.getRitems(query);
		return list;
	}

	public int getRitemCountByQueryCriteria(RitemQuery query) {
		return ritemMapper.getRitemCount(query);
	}

	public List<Ritem> getRitemsByQueryCriteria(int start, int pageSize,
			RitemQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Ritem> rows = sqlSessionTemplate.selectList("getRitems", query,
				rowBounds);
		return rows;
	}

	public Ritem getRitem(Long id) {
		if (id == null) {
			return null;
		}
		Ritem ritem = ritemMapper.getRitemById(id);
		return ritem;
	}

	@Transactional
	public void save(Ritem ritem) {
		if (ritem.getRitemid() == null) {
			ritem.setRitemid(idGenerator.nextId("oa_ritem"));
			ritemMapper.insertRitem(ritem);
		} else {
			ritemMapper.updateRitem(ritem);
		}
		this.updateSumPrice(ritem.getReimbursementid());
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
	public void setReimbursementMapper(ReimbursementMapper reimbursementMapper) {
		this.reimbursementMapper = reimbursementMapper;
	}

	@Resource
	public void setRitemMapper(RitemMapper ritemMapper) {
		this.ritemMapper = ritemMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	/**
	 * 更新主表总价格
	 * 
	 * @param reimbursementid
	 */
	@Transactional
	public void updateSumPrice(Long reimbursementid) {
		List<Ritem> list = ritemMapper.getRitemByParentId(reimbursementid);
		double sumPrice = 0D;
		for (Ritem ritem : list) {
			sumPrice += ritem.getItemsum() * ritem.getExrate();
		}
		Reimbursement model = new Reimbursement();
		model.setReimbursementid(reimbursementid);
		model.setAppsum(sumPrice);
		reimbursementMapper.updateReimbursementForPrice(model);
	}

	public List<Ritem> getRitemByParentId(Long reimbursementid) {
		return ritemMapper.getRitemByParentId(reimbursementid);
	}
}