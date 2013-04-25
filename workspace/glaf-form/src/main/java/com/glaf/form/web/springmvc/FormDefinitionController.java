package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.form.core.domain.FormDefinition;

import com.glaf.form.core.query.*;
import com.glaf.form.core.service.*;

@Controller("/system/form/definition")
@RequestMapping("/system/form/definition.do")
public class FormDefinitionController {
	private static final Log logger = LogFactory
			.getLog(FormDefinitionController.class);

	protected FormDataService formDataService;

	public FormDefinitionController() {

	}

	@ResponseBody
	@RequestMapping(params = "method=delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "rowId");
		String rowIds = request.getParameter("rowIds");
		if (StringUtils.isNotEmpty(rowIds)) {
			StringTokenizer token = new StringTokenizer(rowIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					FormDefinition formDefinition = formDataService
							.getFormDefinition(x);
					if (formDefinition != null
							&& (StringUtils.equals(
									formDefinition.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
					}
				}
			}
		} else if (StringUtils.isNotEmpty(rowId)) {
			FormDefinition formDefinition = formDataService
					.getFormDefinition(rowId);
			if (formDefinition != null
					&& (StringUtils.equals(formDefinition.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
			}
		}
	}

	@RequestMapping(params = "method=edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String formDefinitionId = ParamUtils.getString(params,
				"formDefinitionId");
		FormDefinition formDefinition = null;
		if (StringUtils.isNotEmpty(formDefinitionId)) {
			formDefinition = formDataService
					.getFormDefinition(formDefinitionId);
			if (formDefinition != null) {
				request.setAttribute("formDefinition", formDefinition);
				JSONObject rowJSON = formDefinition.toJsonObject();
				request.setAttribute("x_json", rowJSON.toJSONString());
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formDefinition.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/definition/edit", modelMap);
	}

	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormDefinitionQuery query = new FormDefinitionQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
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
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		Paging page = formDataService.getPageFormDefinition(query);
		int total = page.getTotal();
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

			List<Object> list = page.getRows();

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (Object object : list) {
					FormDefinition formDefinition = (FormDefinition) object;
					JSONObject rowJSON = formDefinition.toJsonObject();
					rowJSON.put("id", formDefinition.getId());
					rowJSON.put("formDefinitionId", formDefinition.getFormDefinitionId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}
			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("total", 0);
			result.put("totalCount", 0);
			result.put("totalRecords", 0);
			result.put("rows", rowsJSON);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/form/definition/list", modelMap);
	}

	@RequestMapping(params = "method=query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formDefinition.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/definition/query", modelMap);
	}

	@RequestMapping(params = "method=save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap,
			FormDefinition formDefinition) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		formDefinition.setCreateBy(actorId);

		formDataService.saveFormDefinition(formDefinition);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=saveFormDefinition")
	public byte[] saveFormDefinition(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormDefinition formDefinition = new FormDefinition();
		try {
			Tools.populate(formDefinition, params);
			formDefinition.setCreateBy(actorId);
			this.formDataService.saveFormDefinition(formDefinition);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

	@RequestMapping(params = "method=update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap,
			FormDefinition formDefinition) {
		formDataService.saveFormDefinition(formDefinition);
		return this.list(request, modelMap);
	}

	@RequestMapping(params = "method=view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "rowId");
		FormDefinition formDefinition = null;
		if (StringUtils.isNotEmpty(rowId)) {
			formDefinition = formDataService.getFormDefinition(rowId);
			request.setAttribute("formDefinition", formDefinition);
			JSONObject rowJSON = formDefinition.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formDefinition.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/definition/view");
	}

}
