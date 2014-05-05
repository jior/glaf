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

package com.glaf.ui.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.ui.mapper.LayoutMapper;
import com.glaf.ui.model.Layout;
import com.glaf.ui.query.LayoutQuery;
import com.glaf.ui.service.LayoutService;

@Service("layoutService")
@Transactional(readOnly = true)
public class MxLayoutServiceImpl implements LayoutService {
	protected final static Log logger = LogFactory
			.getLog(MxLayoutServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected LayoutMapper layoutMapper;

	public MxLayoutServiceImpl() {

	}

	/**
	 * 获取全部布局
	 * 
	 * @return
	 */
	public List<Layout> getAllLayouts() {
		LayoutQuery query = new LayoutQuery();
		return this.list(query);
	}

	public Layout getLayout(String layoutId) {
		Layout layout = layoutMapper.getLayoutById(layoutId);
		return layout;
	}

	public Layout getLayoutByName(String name) {
		Layout layout = null;
		LayoutQuery query = new LayoutQuery();
		query.name(name);
		List<Layout> rows = this.list(query);
		if (rows != null && rows.size() > 0) {
			layout = (Layout) rows.get(0);
		}
		return layout;
	}

	@Transactional
	public void installLayouts(List<Layout> layouts) {
		Collection<String> names = new HashSet<String>();
		if (layouts != null && layouts.size() > 0) {
			Iterator<Layout> iter = layouts.iterator();
			while (iter.hasNext()) {
				Layout layout = iter.next();
				names.add(layout.getName());
			}
		}

		if (layouts != null && layouts.size() > 0) {
			Iterator<Layout> iter = layouts.iterator();
			while (iter.hasNext()) {
				Layout model = (Layout) iter.next();
				if (!names.contains(model.getName())) {
					model.setActorId("system");
					model.setCreateDate(new Date());
					model.setId(idGenerator.getNextId());
					layoutMapper.insertLayout(model);
				}
			}
		}
	}

	public List<Layout> list(LayoutQuery query) {
		query.ensureInitialized();
		List<Layout> list = layoutMapper.getLayouts(query);
		return list;
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
	public void setLayoutMapper(LayoutMapper layoutMapper) {
		this.layoutMapper = layoutMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}