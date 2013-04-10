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

package com.glaf.base.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.glaf.core.context.ContextFactory;
import com.glaf.base.modules.sys.service.SysApplicationService;

public class ApplicationBean {
	protected static final Log logger = LogFactory.getLog(ApplicationBean.class);
	
	protected SysApplicationService sysApplicationService;

	public ApplicationBean() {

	}

	/**
	 * 获取用户菜单之Javascript对象
	 * 
	 * @param parent
	 * @param userId
	 * @param contextPath
	 * @return
	 */
	public String getMenuScripts(long parent, String userId, String contextPath) {
		JSONArray jsonArray = getSysApplicationService().getUserMenu(parent,
				userId);
		logger.debug(jsonArray.toString('\n'));
		String sMenu = "";
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject rootJson = jsonArray.getJSONObject(i);
			sMenu = sMenu
					+ "<li class='menu'><ul><li class='button'><a href='#' class='red'>"
					+ rootJson.getString("name")
					+ "<span></span></a></li><li class='dropdown'><ul>";
			if (rootJson.has("children")) {
				JSONArray children = rootJson.getJSONArray("children");

				for (int j = 0; children != null && j < children.length(); j++) {
					JSONObject childJson = children.getJSONObject(j);
					JSONArray items2 = null;
					if (childJson.has("children")) {
						items2 = childJson.getJSONArray("children");
					}

					if (items2 != null && items2.length() > 0) {
						sMenu = sMenu
								+ "</ul><ul class='father'><li class='button'><a style='width: 110px' name='lightli' onclick='changeClass(this)' href='#' target='mainFrame'>"
								+ childJson.getString("name")
								+ "</a></li><li class='dropdownFather'><ul>";

						for (int k = 0; k < items2.length(); k++) {
							JSONObject cd = items2.getJSONObject(k);
							sMenu = sMenu
									+ "<li class='highlight'><a name='lightli' onclick='changeClass(this)' href='"
									+ contextPath + "/" + cd.getString("url")
									+ "' target='mainFrame'>"
									+ cd.getString("name") + "</a></li>";
						}

						sMenu = sMenu + "</ul></li></ul><ul>";
					} else {
						sMenu = sMenu
								+ "<li class='highlight'><a name='lightli' onclick='changeClass(this)' href='"
								+ contextPath + "/"
								+ childJson.getString("url")
								+ "' target='mainFrame'>"
								+ childJson.getString("name") + "</a></li>";
					}
				}
				sMenu = sMenu + " </ul></li></ul></li>";
			}
		}
		return sMenu;
	}

	public SysApplicationService getSysApplicationService() {
		if (sysApplicationService == null) {
			sysApplicationService = ContextFactory
					.getBean("sysApplicationService");
		}
		return sysApplicationService;
	}

	/**
	 * 获取用户菜单之Json对象
	 * 
	 * @param parent
	 *            父节点编号
	 * @param userId
	 *            用户登录账号
	 * @return
	 */
	public JSONArray getUserMenu(long parent, String userId) {
		JSONArray array = getSysApplicationService()
				.getUserMenu(parent, userId);
		return array;
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

}