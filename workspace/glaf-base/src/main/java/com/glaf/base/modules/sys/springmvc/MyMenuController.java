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

package com.glaf.base.modules.sys.springmvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.core.base.TreeModel;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.util.RequestUtils;

@Controller("/my/menu")
@RequestMapping("/my/menu.do")
public class MyMenuController {

	protected final static Log logger = LogFactory
			.getLog(MyMenuController.class);

	protected SysApplicationService sysApplicationService;

	protected ITreeModelService treeModelService;

	@RequestMapping(params = "method=jump")
	public void jump(HttpServletRequest request, HttpServletResponse response) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext == null) {
			try {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			} catch (IOException ex) {
			}
		}
		String menuId = request.getParameter("menuId");
		if (menuId != null) {
			menuId = RequestUtils.decodeString(menuId);
			logger.debug("->menuId:" + menuId);
		} else {
			menuId = request.getParameter("id");
		}
		if (menuId != null && StringUtils.isNumber(menuId)) {
			TreeModel treeModel = sysApplicationService
					.getTreeModelByAppId(Long.parseLong(menuId));
			if (treeModel != null) {
				try {
					String url = treeModel.getUrl();
					if (url != null) {
						if (!(url.toLowerCase().startsWith("http://") || url
								.toLowerCase().startsWith("https://"))) {
							if (url.startsWith("/")) {
								url = request.getContextPath() + url;
							} else {
								url = request.getContextPath() + "/" + url;
							}
						}
						if (url.indexOf("?") != -1) {
							url = url + "&time=" + System.currentTimeMillis();
						} else {
							url = url + "?time=" + System.currentTimeMillis();
						}
						response.sendRedirect(url);
					} else {
						return;
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		try {
			request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(
					request, response);
		} catch (Exception e) {
		}
	}

	@javax.annotation.Resource
	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

}