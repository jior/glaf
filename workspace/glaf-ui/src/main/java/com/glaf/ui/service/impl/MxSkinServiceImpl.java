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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.ui.mapper.SkinMapper;
import com.glaf.ui.model.Skin;
import com.glaf.ui.model.SkinInstance;
import com.glaf.ui.query.SkinQuery;
import com.glaf.ui.service.SkinService;

@Service("skinService")
@Transactional(readOnly = true)
public class MxSkinServiceImpl implements SkinService {
	protected final static Log logger = LogFactory.getLog(SkinService.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected SkinMapper skinMapper;

	public MxSkinServiceImpl() {

	}

	public List<Skin> getAllSkins() {
		SkinQuery query = new SkinQuery();
		return this.list(query);
	}

	public Skin getSkin(String name) {
		Skin skin = null;
		SkinQuery query = new SkinQuery();
		query.name(name);
		List<Skin> rows = this.list(query);
		if (rows != null && rows.size() > 0) {
			skin = (Skin) rows.get(0);
		}
		return skin;
	}

	public Skin getUserSkin(String actorId) {
		Skin skin = skinMapper.getUserSkin(actorId);
		return skin;
	}

	@Transactional
	public void installSkins(List<Skin> rows) {
		if (rows != null && rows.size() > 0) {
			Iterator<Skin> iter = rows.iterator();
			while (iter.hasNext()) {
				Skin skin = (Skin) iter.next();
				this.saveSkin(skin);
			}
		}
	}

	public List<Skin> list(SkinQuery query) {
		query.ensureInitialized();
		List<Skin> list = skinMapper.getSkins(query);
		return list;
	}

	@Transactional
	public void saveSkin(Skin skin) {
		Skin model = this.getSkin(skin.getName());
		if (model != null) {
			model.setDescription(skin.getDescription());
			model.setImage(skin.getImage());
			model.setTitle(skin.getTitle());
			model.setStyleClass(skin.getStyleClass());
			skinMapper.updateSkin(model);
		} else {
			skin.setId(idGenerator.getNextId());
			skinMapper.insertSkin(skin);
		}
	}

	@Transactional
	public void saveUserSkin(String actorId, String name) {
		skinMapper.deleteSkinInstanceByActorId(actorId);
		Skin skin = this.getSkin(name);
		if (skin != null) {
			SkinInstance model = new SkinInstance();
			model.setActorId(actorId);
			model.setSkin(skin);
			model.setId(idGenerator.getNextId());
			skinMapper.insertSkinInstance(model);
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
	public void setSkinMapper(SkinMapper skinMapper) {
		this.skinMapper = skinMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}