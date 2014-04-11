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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.Environment;
import com.glaf.core.util.RequestUtils;

@Controller("/main")
@RequestMapping("/main.do")
public class MainController {

	protected static final Log logger = LogFactory.getLog(MainController.class);

	@RequestMapping(params = "method=footer")
	public ModelAndView footer(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/footer", modelMap);
	}

	@RequestMapping(params = "method=frame")
	public ModelAndView frame(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/frame", modelMap);
	}

	@RequestMapping(params = "method=header")
	public ModelAndView header(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/header", modelMap);
	}

	@RequestMapping(params = "method=home")
	public ModelAndView home(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/home", modelMap);
	}

	@RequestMapping(params = "method=index")
	public ModelAndView index(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/index", modelMap);
	}

	@RequestMapping(params = "method=left")
	public ModelAndView left(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/left", modelMap);
	}

	@RequestMapping(params = "method=leftbar")
	public ModelAndView leftbar(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/leftbar", modelMap);
	}

	@RequestMapping
	public ModelAndView main(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/main", modelMap);
	}

	@RequestMapping(params = "method=spframe")
	public ModelAndView spframe(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/spframe", modelMap);
	}

	@RequestMapping(params = "method=top")
	public ModelAndView top(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		return new ModelAndView("/main/top", modelMap);
	}

}
