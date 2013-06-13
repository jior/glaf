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
package com.glaf.cms.info.service;

import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.UUID32;
import com.glaf.core.dao.*;
import com.glaf.cms.info.mapper.*;
import com.glaf.cms.info.model.*;
import com.glaf.cms.info.query.*;

@Service("publicInfoService")
@Transactional(readOnly = true)
public class PublicInfoServiceImpl implements PublicInfoService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected IBlobService blobService;

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected PublicInfoMapper publicInfoMapper;

	protected SqlSessionTemplate sqlSessionTemplate;

	public PublicInfoServiceImpl() {

	}

	public int count(PublicInfoQuery query) {
		query.ensureInitialized();
		return publicInfoMapper.getPublicInfoCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			publicInfoMapper.deletePublicInfoById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				publicInfoMapper.deletePublicInfoById(id);
			}
		}
	}

	public PublicInfo getPublicInfo(String id) {
		if (id == null) {
			return null;
		}
		PublicInfo publicInfo = publicInfoMapper.getPublicInfoById(id);
		return publicInfo;
	}

	public int getPublicInfoCountByQueryCriteria(PublicInfoQuery query) {
		return publicInfoMapper.getPublicInfoCount(query);
	}

	public List<PublicInfo> getPublicInfosByQueryCriteria(int start,
			int pageSize, PublicInfoQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<PublicInfo> rows = sqlSessionTemplate.selectList("getPublicInfos",
				query, rowBounds);
		return rows;
	}

	public List<PublicInfo> list(PublicInfoQuery query) {
		query.ensureInitialized();
		List<PublicInfo> list = publicInfoMapper.getPublicInfos(query);
		return list;
	}

	@Transactional
	public void save(PublicInfo publicInfo) {
		if (StringUtils.isEmpty(publicInfo.getId())) {
			publicInfo.setId(UUID32.getUUID());
			publicInfo.setCreateDate(new Date());
			publicInfo.setDeleteFlag(0);
			publicInfo.setPublishFlag(0);
			publicInfo.setViewCount(0);
			publicInfo.setSortNo(0);
			publicInfo.setStatus(0);
			publicInfoMapper.insertPublicInfo(publicInfo);
		} else {
			publicInfoMapper.updatePublicInfo(publicInfo);
		}
		blobService.makeMark(publicInfo.getCreateBy(),
				publicInfo.getServiceKey(), publicInfo.getId());
	}

	@Transactional
	public void updateViewCount(String id) {
		publicInfoMapper.updateViewCount(id);
	}

	@Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
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
	public void setPublicInfoMapper(PublicInfoMapper publicInfoMapper) {
		this.publicInfoMapper = publicInfoMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
