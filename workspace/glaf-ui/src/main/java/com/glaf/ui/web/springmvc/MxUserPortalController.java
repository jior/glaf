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

package com.glaf.ui.web.springmvc;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.ui.model.Panel;
import com.glaf.ui.model.PanelInstance;
import com.glaf.ui.model.UserPanel;
import com.glaf.ui.model.UserPortal;
import com.glaf.ui.service.PanelService;
import com.glaf.ui.service.UserPortalService;

@Controller("/user/portal")
@RequestMapping("/user/portal")
public class MxUserPortalController {
	protected static final Log logger = LogFactory
			.getLog(MxUserPortalController.class);

	protected UserPortalService userPortalService;

	protected PanelService panelService;

	@RequestMapping
	public ModelAndView myPortal(ModelMap modelMap, HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext == null) {
			return new ModelAndView("/modules/login");
		}
		RequestUtils.setRequestParameterToAttribute(request);

		UserPanel userPanel = panelService.getUserPanel(loginContext
				.getActorId());
		if (userPanel == null) {
			userPanel = panelService.getUserPanel("system");
		}

		Map<String, Integer> panelPxMap = new java.util.HashMap<String, Integer>();
		Map<String, Integer> panelMap = new java.util.HashMap<String, Integer>();
		if (userPanel != null && userPanel.getPanelInstances() != null) {
			String layoutName = userPanel.getLayoutName();
			Set<PanelInstance> set = userPanel.getPanelInstances();
			Iterator<PanelInstance> iter = set.iterator();
			while (iter.hasNext()) {
				PanelInstance p = iter.next();
				if (StringUtils.isNumeric(p.getName())) {
					int pos = Math.abs(Integer.parseInt(p.getName()));
					if (pos > 0) {
						panelPxMap.put(p.getPanelId(), pos);

						if ("P2".equals(layoutName)) {
							if (pos % 2 == 1) {
								panelMap.put(p.getPanelId(), 0);
							} else {
								panelMap.put(p.getPanelId(), 1);
							}
						} else if ("P3".equals(layoutName)) {
							if (pos % 3 == 1) {
								panelMap.put(p.getPanelId(), 0);
							} else if (pos % 3 == 2) {
								panelMap.put(p.getPanelId(), 1);
							} else if (pos % 3 == 0) {
								panelMap.put(p.getPanelId(), 2);
							}
						} else {
							panelMap.put(p.getPanelId(), 0);
						}
					}
				}
			}
		}

		List<UserPortal> userPortals = userPortalService
				.getUserPortals(loginContext.getActorId());
		if (userPortals == null || userPortals.isEmpty()) {
			userPortals = userPortalService.getUserPortals("system");
			if (userPortals == null || userPortals.isEmpty()) {
				List<Panel> panels = panelService.getPanels("system");
				if (panels != null && !panels.isEmpty()) {
					int i = 100;
					for (Panel panel : panels) {
						UserPortal p = new UserPortal();
						p.setActorId(loginContext.getActorId());
						p.setPanelId(panel.getId());
						if (panelMap.get(panel.getId()) != null) {
							p.setColumnIndex(panelMap.get(panel.getId()));
						} else {
							p.setColumnIndex(0);
						}
						if (panelPxMap.get(panel.getId()) != null) {
							p.setPosition(panelPxMap.get(panel.getId()));
						} else {
							p.setPosition(i++);
						}
						userPortals.add(p);
					}
					userPortalService.save(loginContext.getActorId(),
							userPortals);
					userPortals = userPortalService.getUserPortals(loginContext
							.getActorId());
				}
			}
		}

		List<UserPortal> sysPortals = userPortalService
				.getUserPortals("system");

		if (userPortals == null) {
			userPortals = new java.util.ArrayList<UserPortal>();
		}

		if (sysPortals != null && !sysPortals.isEmpty()) {
			userPortals.addAll(sysPortals);
		}

		for (UserPortal p : userPortals) {
			String panelId = p.getPanelId();
			p.setPanel(panelService.getPanel(panelId));
		}

		modelMap.put("userPanel", userPanel);
		modelMap.put("userPortals", userPortals);

		return new ModelAndView("/modules/ui/portal/myPortal", modelMap);
	}

	@RequestMapping("/save")
	public void save(HttpServletRequest request) {
		String actorId = RequestUtils.getActorId(request);
		String pids = request.getParameter("pids");
		String titles = request.getParameter("titles");
		String poss = request.getParameter("poss");

		if (titles.length() > 0) {
			List<UserPortal> rows = new java.util.ArrayList<UserPortal>();
			String title[] = titles.split("[,]");
			String pos[] = poss.split("[,]");
			for (int i = 0; i < title.length; i++) {
				String panelId = null;
				Panel panel = panelService.getPanelByTitle(title[i]);
				if (panel != null) {
					panelId = panel.getId();
				}
				if (panelId != null) {
					UserPortal p = new UserPortal();
					p.setPanelId(panelId);
					p.setColumnIndex(Integer.parseInt(pos[i]));
					p.setActorId(actorId);
					p.setPosition(i);
					rows.add(p);
				}
			}

			if (pids.length() > 0) {
				String pp[] = pids.split("[,]");
				for (int i = 0; i < pp.length; i++) {
					UserPortal p = new UserPortal();
					p.setPanelId(pp[i]);
					p.setColumnIndex(0);
					p.setActorId(actorId);
					p.setPosition(i);
					rows.add(p);
				}
			}
			if (!rows.isEmpty()) {
				userPortalService.save(actorId, rows);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/savePortal")
	public void savePortal(HttpServletRequest request) {
		String actorId = RequestUtils.getActorId(request);
		String portals = request.getParameter("portalState");
		logger.debug(portals);
		List<UserPortal> rows = new java.util.ArrayList<UserPortal>();
		if (portals != null && portals.length() > 0) {
			int position = 0;
			StringTokenizer st01 = new StringTokenizer(portals, ":");
			while (st01.hasMoreTokens()) {
				String str = st01.nextToken();
				List<String> pp = StringTools.split(str, ",");
				for (int i = 0; i < pp.size(); i++) {
					UserPortal p = new UserPortal();
					p.setPanelId(pp.get(i));
					p.setColumnIndex(position);
					p.setActorId(actorId);
					p.setPosition(i);
					rows.add(p);
				}
				position++;
			}
		}
		if (!rows.isEmpty()) {
			userPortalService.save(actorId, rows);
		}
	}

	@javax.annotation.Resource
	public void setPanelService(PanelService panelService) {
		this.panelService = panelService;
	}

	@javax.annotation.Resource
	public void setUserPortalService(UserPortalService userPortalService) {
		this.userPortalService = userPortalService;
	}

}