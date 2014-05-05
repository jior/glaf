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

package com.glaf.template.web.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.template.Template;
import com.glaf.template.query.TemplateQuery;
import com.glaf.template.service.ITemplateService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/sys/template")
@RequestMapping("/sys/template")
public class MxSystemTemplateController {
	protected final static Log logger = LogFactory
			.getLog(MxSystemTemplateController.class);

	protected ITemplateService templateService;

	@RequestMapping("/delete")
	public ModelAndView deleteTemplate(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String templateId = request.getParameter("templateId");
		if (StringUtils.isNotEmpty(templateId)) {
			templateService.deleteTemplate(templateId);
		}
		return this.list(request, modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String templateId = request.getParameter("templateId");
		if (StringUtils.isNotEmpty(templateId)) {
			Template template = templateService.getTemplate(templateId);
			modelMap.put("template", template);
		}
		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_template.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/template/edit");
	}

	public ITemplateService getTemplateService() {
		return templateService;
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TemplateQuery query = new TemplateQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = templateService.getTemplateCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<Template> list = templateService.getTemplatesByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Template template : list) {
					JSONObject rowJSON = template.toJsonObject();
					rowJSON.put("id", template.getTemplateId());
					rowJSON.put("templateId", template.getTemplateId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		String queryString = request.getParameter("x_complex_query");
		if (StringUtils.isNotEmpty(queryString)) {
			queryString = RequestUtils.decodeString(queryString);
			if (LogUtils.isDebug()) {
				logger.debug(queryString);
			}
			Map<String, Object> xMap = JsonUtils.decode(queryString);
			if (xMap != null) {
				paramMap.putAll(xMap);
			}
		}

		String nodeCode = request.getParameter("nodeCode");
		if (StringUtils.isEmpty(nodeCode)) {
			nodeCode = "template_category";
		}
		modelMap.put("nodeCode", nodeCode);

		TemplateQuery query = new TemplateQuery();
		Tools.populate(query, paramMap);

		query.setPageSize(-1);
		query.setParameter(paramMap);
		List<Template> templates = templateService.getTemplates(query);
		modelMap.put("templates", templates);

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_template.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/template/list", modelMap);
	}

	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request, ModelMap modelMap) {
		// RequestUtils.setRequestParameterToAttribute(request);
		String jx_view = request.getParameter("jx_view");

		RequestUtils.setRequestParameterToAttribute(request);
		TreeModel treeModel = IdentityFactory.getTreeModelByCode("template");
		if (treeModel != null) {
			modelMap.put("treeModel", treeModel);
			request.setAttribute("parent", treeModel.getId() + "");
		}

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_template.main");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/template/main");
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		String templateId = request.getParameter("templateId");
		Template template = null;
		if (StringUtils.isNotEmpty(templateId)) {
			template = templateService.getTemplate(templateId);
		}

		if (template == null) {
			template = new Template();
			template.setCreateBy(loginContext.getActorId());
		}

		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		Map<String, Object> paramMap = RequestUtils.getParameterMap(req);
		Tools.populate(template, paramMap);

		String nodeId = ParamUtils.getString(paramMap, "nodeId");
		if (nodeId != null) {

		}

		Map<String, MultipartFile> fileMap = req.getFileMap();
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile mFile = entry.getValue();
			String filename = mFile.getOriginalFilename();
			if (mFile.getSize() > 0) {
				template.setFileSize(mFile.getSize());
				int fileType = 0;

				if (filename.endsWith(".java")) {
					fileType = 50;
				} else if (filename.endsWith(".jsp")) {
					fileType = 51;
				} else if (filename.endsWith(".ftl")) {
					fileType = 52;
					template.setLanguage("freemarker");
				} else if (filename.endsWith(".vm")) {
					fileType = 54;
					template.setLanguage("velocity");
				} else if (filename.endsWith(".xml")) {
					fileType = 60;
				} else if (filename.endsWith(".htm")
						|| filename.endsWith(".html")) {
					fileType = 80;
				}

				template.setDataFile(filename);
				template.setFileType(fileType);
				template.setCreateDate(new Date());
				template.setData(mFile.getBytes());
				template.setLastModified(System.currentTimeMillis());
				template.setTemplateType(FileUtils.getFileExt(filename));
				break;
			}
		}

		templateService.saveTemplate(template);

		return this.list(request, modelMap);
	}

	@javax.annotation.Resource
	public void setTemplateService(ITemplateService templateService) {
		this.templateService = templateService;
	}

	/**
	 * 显示框架页面
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 */
	@RequestMapping("/showFrame")
	public ModelAndView showFrame(HttpServletRequest request, ModelMap modelMap) {
		// RequestUtils.setRequestParameterToAttribute(request);
		TreeModel treeModel = IdentityFactory.getTreeModelByCode("template");
		if (treeModel != null) {
			modelMap.put("treeModel", treeModel);
			request.setAttribute("parent", treeModel.getId() + "");
		}
		return new ModelAndView("/modules/sys/template/template_frame",
				modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String templateId = request.getParameter("templateId");
		if (StringUtils.isNotEmpty(templateId)) {
			Template template = templateService.getTemplate(templateId);
			modelMap.put("template", template);
		}
		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_template.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/template/view");
	}

}