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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.util.UUID32;
import com.glaf.ui.model.Panel;
import com.glaf.ui.service.LayoutService;
import com.glaf.ui.service.PanelService;
import com.glaf.ui.service.SkinService;

@Controller("/panel")
@RequestMapping("/panel")
public class MxPanelController {
	protected final static Log logger = LogFactory
			.getLog(MxPanelController.class);

	protected final static String LIST_ACTION = "redirect:/mx/panel";

	protected SkinService skinService;

	protected PanelService panelService;

	protected LayoutService layoutService;

	@RequestMapping("/content")
	public ModelAndView content(
			@RequestParam(value = "pid", required = true) String panelId,
			ModelMap modelMap) {

		Panel panel = null;

		if (StringUtils.isNotEmpty(panelId)) {
			panel = panelService.getPanel(panelId);
		}

		if (panel != null) {
			modelMap.addAttribute("panel", panel);
		}

		String x_view = ViewProperties.getString("sys_panel.content");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/ui/panel/content", modelMap);

	}

	@RequestMapping("/edit")
	public ModelAndView edit(
			@RequestParam(value = "panelId", required = false) String panelId,
			ModelMap modelMap) {

		Panel panel = null;

		if (StringUtils.isNotEmpty(panelId)) {
			panel = panelService.getPanel(panelId);
		}
		modelMap.put("panel", panel);

		String x_view = ViewProperties.getString("sys_panel.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/ui/panel/edit", modelMap);
	}

	@RequestMapping
	public ModelAndView list(ModelMap modelMap, HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		List<Panel> panels = null;
		String actorId = loginContext.getActorId();
		String isSystem = request.getParameter("isSystem");

		if (loginContext.isSystemAdministrator()
				&& StringUtils.equals(isSystem, "true")) {
			panels = panelService.getSystemPanels();
		} else {
			panels = panelService.getPanels(actorId);
		}
		modelMap.put("panels", panels);

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_panel.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/ui/panel/list", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(  ModelMap modelMap,
			HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();

		String isSystem = request.getParameter("isSystem");
		if (loginContext.isSystemAdministrator()
				&& StringUtils.equals(isSystem, "true")) {
			actorId = "system";
		}

		Panel panel = new Panel();
		Tools.populate(panel, params);
		Panel model = null;
		if (panel.getId() != null) {
			model = panelService.getPanel(panel.getId());
		}
		if (model == null) {
			if ((loginContext.isSystemAdministrator() && StringUtils.equals(
					isSystem, "true"))) {
				if (StringUtils.isEmpty(panel.getName())) {
					panel.setName("sys_panel_" + UUID32.getUUID());
				}
			} else {
				panel.setName("user_panel_" + UUID32.getUUID());
			}
			panel.setActorId(actorId);
			panelService.savePanel(panel);
		} else {
			if (StringUtils.equals(panel.getActorId(), actorId)) {
				logger.debug(panel);
				panelService.updatePanel(panel);
			} else if ((loginContext.isSystemAdministrator() && StringUtils
					.equals(isSystem, "true"))) {
				logger.debug(panel);
				panelService.updatePanel(panel);
			}
		}
		return this.list(modelMap, request);
	}

	@javax.annotation.Resource
	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	@javax.annotation.Resource
	public void setPanelService(PanelService panelService) {
		this.panelService = panelService;
	}

	@javax.annotation.Resource
	public void setSkinService(SkinService skinService) {
		this.skinService = skinService;
	}

}