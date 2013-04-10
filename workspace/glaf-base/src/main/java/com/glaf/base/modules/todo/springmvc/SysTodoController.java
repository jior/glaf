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

package com.glaf.base.modules.todo.springmvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.todo.TodoXlsReader;

import com.glaf.core.todo.Todo;
import com.glaf.core.util.RequestUtils;

import com.glaf.base.modules.todo.service.TodoService;
import com.glaf.base.utils.RequestUtil;

@Controller("/sys/todo")
@RequestMapping("/sys/todo.do")
public class SysTodoController {
	private static final Log logger = LogFactory.getLog(SysTodoController.class);

	@javax.annotation.Resource
	private TodoService todoService;

	 

	/**
	 * 显示TODO列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @
	 */
	@RequestMapping(params = "method=save")
	public ModelAndView save(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysUser user = RequestUtil.getLoginUser(request);
		if (user.isSystemAdmin()) {
			String id = request.getParameter("id");
			String enableFlag = request.getParameter("enableFlag");
			String limitDay = request.getParameter("limitDay");
			String xa = request.getParameter("xa");
			String xb = request.getParameter("xb");
			Todo todo = todoService.getTodo(Long.valueOf(id));
			todo.setTitle(request.getParameter("title"));
			todo.setContent(request.getParameter("content"));
			try {
				todo.setEnableFlag(new Integer(enableFlag).intValue());
				todo.setLimitDay(new Integer(limitDay).intValue());
				todo.setXa(new Integer(xa).intValue());
				todo.setXb(new Integer(xb).intValue());
				todoService.update(todo);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}

		return this.showList(modelMap, request, response);
	}

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
		logger.info("setTodoService");
	}

	/**
	 * 显示TODO列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtils.setRequestParameterToAttribute(request);
		List<Todo> rows = todoService.getAllTodoList();
		request.setAttribute("rows", rows);
		return new ModelAndView("/modules/sys/todo/show_list", modelMap);
	}

	/**
	 * 显示TODO列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showModify")
	public ModelAndView showModify(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/sys/todo/show_modify", modelMap);
	}

	/**
	 * 上传文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	@RequestMapping(params = "method=showUpload")
	public ModelAndView showUpload(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/sys/todo/show_upload", modelMap);
	}

	/**
	 * 上传文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	@RequestMapping(params = "method=uploadFile")
	public ModelAndView uploadFile(ModelMap modelMap,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TodoXlsReader reader = new TodoXlsReader();
		List<Todo> todos = reader.readXls(file.getInputStream());
		if (todos != null && !todos.isEmpty()) {
			logger.debug("import size:" + todos.size());
			for (Todo todo : todos) {
				todo.setEnableFlag(1);
			}
			todoService.saveAll(todos);
		}
		return this.showList(modelMap, request, response);
	}
}