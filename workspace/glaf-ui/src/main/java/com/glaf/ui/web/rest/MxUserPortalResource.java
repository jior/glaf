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

package com.glaf.ui.web.rest;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.ui.model.Panel;
import com.glaf.ui.model.UserPortal;
import com.glaf.ui.service.PanelService;
import com.glaf.ui.service.UserPortalService;

@Controller("/rs/user/portal")
@Path("/rs/user/portal")
public class MxUserPortalResource {
	protected static Log logger = LogFactory.getLog(MxUserPortalResource.class);

	 
	protected UserPortalService userPortalService;

 
	protected PanelService panelService;

	@POST
	@Path("/save")
	public void save(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String actorId = RequestUtils.getActorId(request);
		String pids = request.getParameter("pids");
		String titles = request.getParameter("titles");
		String poss = request.getParameter("poss");
		logger.debug(titles);
		logger.debug(poss);
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
					p.setColumnIndex(Integer.valueOf(pos[i]));
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

	@POST
	@Path("/savePortal")
	public void savePortal(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
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
					p.setColumnIndex(Integer.valueOf(position));
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

	@POST
	@Path("/update")
	public void update(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String actorId = RequestUtils.getActorId(request);
		String panelId = request.getParameter("panelId");
		int columnIndex = RequestUtils.getInt(request, "columnIndex");
		int position = RequestUtils.getInt(request, "position");
		if (actorId != null && panelId != null) {
			userPortalService.save(actorId, panelId, columnIndex, position);
		}
	}

}