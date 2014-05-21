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

package com.glaf.core.web.springmvc;

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

import com.glaf.core.cache.CacheFactory;
import com.glaf.core.cache.ClearCacheJob;
import com.glaf.core.config.ConfigFactory;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.ResponseUtils;

@Controller("/sys/cacheMgr")
@RequestMapping("/sys/cacheMgr")
public class MxSystemCacheMgrController {
	protected static final Log logger = LogFactory
			.getLog(MxSystemCacheMgrController.class);

	@RequestMapping("/clearAll")
	public ModelAndView clearAll(HttpServletRequest request, ModelMap modelMap) {
		ClearCacheJob job = new ClearCacheJob();
		try {
			logger.debug("#################CacheFactory.clearAll##################");
			job.clearAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			logger.debug("#################ConfigFactory.clearAll##################");
			ConfigFactory.clearAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		modelMap.put("reloadOK", true);

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_cache.clearAll");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		String rowIds = request.getParameter("keys");
		if (StringUtils.isNotEmpty(rowIds)) {
			logger.debug("remove keys:" + rowIds);
			StringTokenizer token = new StringTokenizer(rowIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					CacheFactory.remove(x);
				}
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_cache.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/cache/list", modelMap);
	}
}