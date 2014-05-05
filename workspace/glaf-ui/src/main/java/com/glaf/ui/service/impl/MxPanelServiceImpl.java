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

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.ui.mapper.PanelMapper;
import com.glaf.ui.mapper.UserPortalMapper;
import com.glaf.ui.model.Layout;
import com.glaf.ui.model.Panel;
import com.glaf.ui.model.PanelInstance;
import com.glaf.ui.model.UserPanel;
import com.glaf.ui.model.UserPortal;
import com.glaf.ui.query.PanelQuery;
import com.glaf.ui.service.LayoutService;
import com.glaf.ui.service.PanelService;

@Service("panelService")
@Transactional(readOnly = true)
public class MxPanelServiceImpl implements PanelService {

	protected final static Log logger = LogFactory
			.getLog(MxPanelServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected LayoutService layoutService;

	protected PanelMapper panelMapper;

	protected UserPortalMapper userPortalMapper;

	public MxPanelServiceImpl() {

	}

	public Panel getPanel(String panelId) {
		return panelMapper.getPanelById(panelId);
	}

	public Panel getPanelByName(String name) {
		Panel panel = null;
		PanelQuery query = new PanelQuery();
		query.name(name);
		List<Panel> rows = this.list(query);
		if (rows != null && rows.size() > 0) {
			panel = (Panel) rows.get(0);
		}
		return panel;
	}

	public Panel getPanelByTitle(String title) {
		Panel panel = null;
		PanelQuery query = new PanelQuery();
		query.titleLike(title + "%");
		List<Panel> rows = this.list(query);
		if (rows != null && rows.size() > 0) {
			panel = (Panel) rows.get(0);
		}
		return panel;
	}

	/**
	 * 获取用户自定义面板
	 * 
	 * @return
	 */
	public List<Panel> getPanels(String actorId) {
		PanelQuery query = new PanelQuery();
		query.actorId(actorId);
		List<Panel> rows = this.list(query);
		List<Panel> panels = new java.util.ArrayList<Panel>();
		if (rows != null && rows.size() > 0) {
			Iterator<Panel> iterator = rows.iterator();
			while (iterator.hasNext()) {
				panels.add(iterator.next());
			}
		}
		return panels;
	}

	/**
	 * 获取系统面板
	 * 
	 * @return
	 */
	public List<Panel> getSystemPanels() {
		PanelQuery query = new PanelQuery();
		query.actorId("system");
		List<Panel> rows = this.list(query);
		List<Panel> panels = new java.util.ArrayList<Panel>();
		if (rows != null && rows.size() > 0) {
			Iterator<Panel> iterator = rows.iterator();
			while (iterator.hasNext()) {
				panels.add(iterator.next());
			}
		}
		return panels;
	}

	public UserPanel getUserPanel(String actorId) {
		UserPanel userPanel = panelMapper.getUserPanel(actorId);
		if (userPanel != null) {
			if (userPanel.getLayoutId() != null) {
				Layout layout = layoutService
						.getLayout(userPanel.getLayoutId());
				if (layout != null) {
					userPanel.setLayout(layout);
				}
			}
			List<PanelInstance> panelInstances = panelMapper
					.getPanelInstances(userPanel.getId());
			if (panelInstances != null && panelInstances.size() > 0) {
				Iterator<PanelInstance> iter = panelInstances.iterator();
				while (iter.hasNext()) {
					PanelInstance panelInstance = iter.next();
					Panel panel = panelMapper.getPanelById(panelInstance
							.getPanelId());
					if (panel != null) {
						panelInstance.setPanel(panel);
						userPanel.addPanel(panel);
						userPanel.addPanelInstance(panelInstance);
					}
				}
			}
		}

		return userPanel;
	}

	@Transactional
	public void installPanels(List<Panel> panels) {
		Map<String, Panel> panelMap = new java.util.HashMap<String, Panel>();
		if (panels != null && panels.size() > 0) {
			Iterator<Panel> iter = panels.iterator();
			while (iter.hasNext()) {
				Panel panel = iter.next();
				panelMap.put(panel.getName(), panel);
			}
		}

		if (panels != null && panels.size() > 0) {
			Iterator<Panel> iter = panels.iterator();
			while (iter.hasNext()) {
				Panel model = (Panel) iter.next();
				if (panelMap.containsKey(model.getName())) {
					Panel panel = panelMap.get(model.getName());
					panel.setLink(model.getLink());
					panel.setMoreLink(model.getMoreLink());
					panel.setTitle(model.getTitle());
					panel.setModuleId(model.getModuleId());
					panel.setModuleName(model.getModuleName());
					panelMapper.updatePanel(model);
				} else {
					model.setActorId("system");
					model.setCreateDate(new Date());
					model.setId(idGenerator.getNextId());
					UserPortal p = new UserPortal();
					p.setActorId(model.getActorId());
					p.setCreateDate(new Date());
					p.setId(idGenerator.getNextId());
					p.setPanelId(model.getId());
					p.setColumnIndex(model.getColumnIndex());
					p.setPosition(99);
					panelMapper.insertPanel(model);
					userPortalMapper.insertUserPortal(p);
				}
			}
		}
	}

	public List<Panel> list(PanelQuery query) {
		query.ensureInitialized();
		List<Panel> list = panelMapper.getPanels(query);
		return list;
	}

	@Transactional
	public void savePanel(Panel panel) {
		panel.setCreateDate(new Date());
		panel.setId(idGenerator.getNextId());
		panelMapper.insertPanel(panel);

		UserPortal p = new UserPortal();
		p.setActorId(panel.getActorId());
		p.setCreateDate(new Date());
		p.setId(idGenerator.getNextId());
		p.setPanelId(panel.getId());
		p.setColumnIndex(panel.getColumnIndex());
		p.setPosition(99);
		userPortalMapper.insertUserPortal(p);
	}

	@Transactional
	public void saveUserPanel(UserPanel userPanel) {
		UserPanel model = this.getUserPanel(userPanel.getActorId());
		if (model != null) {
			panelMapper.deletePanelInstancesById(model.getId());
			panelMapper.deleteUserPanelById(model.getId());
		}
		userPanel.setCreateDate(new Date());
		userPanel.setId(idGenerator.getNextId());
		if (userPanel.getLayout() != null) {
			userPanel.setLayoutId(userPanel.getLayout().getId());
		}

		panelMapper.insertUserPanel(userPanel);

		Set<PanelInstance> panelInstances = userPanel.getPanelInstances();
		for (PanelInstance panelInstance : panelInstances) {
			panelInstance.setId(idGenerator.getNextId());
			panelInstance.setUserPanelId(userPanel.getId());
			if (panelInstance.getPanel() != null) {
				panelInstance.setPanelId(panelInstance.getPanel().getId());
			}
			panelMapper.insertPanelInstance(panelInstance);
		}

		userPortalMapper.deleteUserPortalByActorId(userPanel.getActorId());
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
	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	@javax.annotation.Resource
	public void setPanelMapper(PanelMapper panelMapper) {
		this.panelMapper = panelMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setUserPortalMapper(UserPortalMapper userPortalMapper) {
		this.userPortalMapper = userPortalMapper;
	}

	@Transactional
	public void updatePanel(Panel panel) {
		panelMapper.updatePanel(panel);
	}

}