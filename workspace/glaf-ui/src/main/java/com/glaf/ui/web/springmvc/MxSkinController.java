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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;
import com.glaf.ui.model.Skin;
import com.glaf.ui.service.LayoutService;
import com.glaf.ui.service.PanelService;
import com.glaf.ui.service.SkinService;

@Controller("/skin")
@RequestMapping("/skin")
public class MxSkinController {
	protected final static Log logger = LogFactory
			.getLog(MxSkinController.class);

	protected final static String LIST_ACTION = "redirect:/mx/skin";

	protected SkinService skinService;

	protected PanelService panelService;

	protected LayoutService layoutService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam("skinId") String skinId,
			HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		if (StringUtils.isNotEmpty(skinId)) {
			skinService.saveUserSkin(actorId, skinId);
		}
		return LIST_ACTION;
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

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showSkins(ModelMap modelMap) {
		List<Skin> skins = skinService.getAllSkins();
		modelMap.put("skins", skins);

		String x_view = ViewProperties.getString("sys_skin.showSkins");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/ui/skin/showSkins", modelMap);
	}

}