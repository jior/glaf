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

package com.glaf.base.district.service;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.query.TreeModelQuery;
import com.glaf.core.base.TreeModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.base.district.domain.*;
import com.glaf.base.district.query.*;
import com.glaf.base.district.mapper.*;

@Service("districtService")
@Transactional(readOnly = true)
public class DistrictServiceImpl implements DistrictService {
	protected final static Log logger = LogFactory
			.getLog(DistrictServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected DistrictMapper districtMapper;

	public DistrictServiceImpl() {

	}

	public void deleteById(long id) {
		DistrictQuery query = new DistrictQuery();
		query.parentId(id);
		List<DistrictEntity> children = this.list(query);
		if (children == null || children.isEmpty()) {
			districtMapper.deleteDistrictById(id);
		}
	}

	public int count(DistrictQuery query) {
		return districtMapper.getDistrictCount(query);
	}

	public DistrictEntity getDistrict(Long id) {
		DistrictEntity district = districtMapper.getDistrictById(id);
		return district;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDistrictCountByQueryCriteria(DistrictQuery query) {
		return districtMapper.getDistrictCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DistrictEntity> getDistrictsByQueryCriteria(int start,
			int pageSize, DistrictQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DistrictEntity> rows = sqlSession.selectList("getDistricts",
				query, rowBounds);
		return rows;
	}

	public int getDistrictTreeModelCount(TreeModelQuery query) {
		return districtMapper.getDistrictTreeModelCount(query);
	}

	public List<TreeModel> getDistrictTreeModels(TreeModelQuery query) {
		return districtMapper.getDistrictTreeModels(query);
	}

	public List<DistrictEntity> list(DistrictQuery query) {
		List<DistrictEntity> list = districtMapper.getDistricts(query);
		return list;
	}

	public List<DistrictEntity> getDistrictList(long parentId) {
		DistrictQuery query = new DistrictQuery();
		query.parentId(parentId);
		return list(query);
	}

	@Transactional
	public void save(DistrictEntity district) {
		if (district.getId() == 0) {
			district.setLevel(1);
			district.setId(idGenerator.nextId());
			if (district.getParentId() != 0) {
				DistrictEntity parent = this
						.getDistrict(district.getParentId());
				if (parent != null) {
					district.setLevel(parent.getLevel() + 1);
				}
			}
			districtMapper.insertDistrict(district);
		} else {
			DistrictEntity model = this.getDistrict(district.getId());
			if (model != null) {
				if (district.getParentId() != 0) {
					model.setParentId(district.getParentId());
				}
				if (district.getName() != null) {
					model.setName(district.getName());
				}
				if (district.getUseType() != null) {
					model.setUseType(district.getUseType());
				}
				model.setLevel(district.getLevel());
				model.setSortNo(district.getSortNo());
				districtMapper.updateDistrict(model);
			} else {
				districtMapper.insertDistrict(district);
			}
		}
	}

	@javax.annotation.Resource
	public void setDistrictMapper(DistrictMapper districtMapper) {
		this.districtMapper = districtMapper;
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
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}