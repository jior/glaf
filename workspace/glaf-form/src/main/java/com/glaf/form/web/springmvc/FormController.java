package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataFile;
import com.glaf.core.base.DataModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.identity.User;

import com.glaf.core.query.DataModelQuery;
import com.glaf.core.security.*;
import com.glaf.core.service.EntityDefinitionService;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.*;

import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.service.*;

@Controller("/form")
@RequestMapping("/form")
public class FormController {

	private static final Log logger = LogFactory.getLog(FormController.class);

	protected IBlobService blobService;

	protected EntityDefinitionService entityDefinitionService;

	protected FormDataService formDataService;

	protected ITableDefinitionService tableDefinitionService;

	public FormController() {

	}

	@ResponseBody
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		String appId = request.getParameter("appId");
		String businessKeys = request.getParameter("businessKeys");
		FormApplication formApplication = formDataService
				.getFormApplication(appId);
		if (formApplication != null) {
			if (StringUtils.isNotEmpty(businessKeys)) {
				List<String> list = StringTools.split(businessKeys);
				formDataService.deleteDataModel(appId, list);
			}
		}
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String appId = ParamUtils.getString(params, "appId");
		String businessKey = request.getParameter("businessKey");
		FormApplication formApplication = null;
		if (StringUtils.isNotEmpty(appId)) {
			formApplication = formDataService.getFormApplication(appId);
			request.setAttribute("formApplication", formApplication);
			if (StringUtils.isNotEmpty(businessKey)) {
				DataModel dataModel = formDataService
						.getDataModelByBusinessKey(appId, businessKey);
				request.setAttribute("dataModel", dataModel);

				List<DataFile> dataFiles = blobService.getBlobList(businessKey);
				request.setAttribute("dataFiles", dataFiles);

				if (StringUtils.equals(dataModel.getCreateBy(), actorId)
						&& dataModel.getWfStatus() != 9999) {
					request.setAttribute("editable", true);
				}

			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("form.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/edit", modelMap);
	}

	@RequestMapping("json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelQuery query = new DataModelQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

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

		query.setPageNo(pageNo);
		query.setPageSize(limit);

		String appId = ParamUtils.getString(params, "appId");

		FormApplication formApplication = null;
		List<ColumnDefinition> columns = null;
		if (StringUtils.isNotEmpty(appId)) {
			formApplication = formDataService.getFormApplication(appId);
			request.setAttribute("formApplication", formApplication);
			String targetId = formApplication.getTableName() + "_FormApp";
			columns = tableDefinitionService
					.getColumnDefinitionsByTargetId(targetId);
		}

		JSONObject result = new JSONObject();
		Paging page = formDataService.getPageDataModel(appId, query);
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
					DataModel dataModel = (DataModel) object;
					dataModel.setColumns(columns);
					JSONObject rowJSON = dataModel.toJsonObject();
					rowJSON.put("id", dataModel.getId());
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
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String appId = ParamUtils.getString(params, "appId");
		FormApplication formApplication = null;
		if (StringUtils.isNotEmpty(appId)) {
			formApplication = formDataService.getFormApplication(appId);
			request.setAttribute("formApplication", formApplication);
			String targetId = formApplication.getTableName() + "_FormApp";
			List<ColumnDefinition> columns = tableDefinitionService
					.getColumnDefinitionsByTargetId(targetId);
			request.setAttribute("columns", columns);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("form.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/list", modelMap);
	}

	@RequestMapping("query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String appId = ParamUtils.getString(params, "appId");
		FormApplication formApplication = null;
		if (StringUtils.isNotEmpty(appId)) {
			formApplication = formDataService.getFormApplication(appId);
			request.setAttribute("formApplication", formApplication);
			String targetId = formApplication.getTableName() + "_FormApp";
			List<ColumnDefinition> columns = tableDefinitionService
					.getColumnDefinitionsByTargetId(targetId);
			request.setAttribute("columns", columns);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("form.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/query", modelMap);
	}

	@RequestMapping("save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String actorId = loginContext.getActorId();
		String appId = ParamUtils.getString(params, "appId");
		FormContext formContext = new FormContext();
		formContext.setContextPath(request.getContextPath());
		formContext.setDataMap(params);
		formContext.setLoginContext(loginContext);
		try {
			String businessKey = request.getParameter("businessKey");
			FormApplication formApplication = null;
			if (StringUtils.isNotEmpty(appId)) {
				formApplication = formDataService.getFormApplication(appId);
				request.setAttribute("formApplication", formApplication);
				if (StringUtils.isNotEmpty(businessKey)) {
					DataModel dataModel = formDataService
							.getDataModelByBusinessKey(appId, businessKey);
					if (StringUtils.equals(dataModel.getCreateBy(), actorId)
							&& dataModel.getWfStatus() != 9999) {
						this.formDataService.saveDataModel(appId, formContext);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("saveData")
	public byte[] saveData(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String actorId = loginContext.getActorId();
		String appId = ParamUtils.getString(params, "appId");
		FormContext formContext = new FormContext();
		formContext.setContextPath(request.getContextPath());
		formContext.setDataMap(params);
		formContext.setLoginContext(loginContext);
		try {
			String businessKey = request.getParameter("businessKey");
			FormApplication formApplication = null;
			if (StringUtils.isNotEmpty(appId)) {
				formApplication = formDataService.getFormApplication(appId);
				request.setAttribute("formApplication", formApplication);
				if (StringUtils.isNotEmpty(businessKey)) {
					DataModel dataModel = formDataService
							.getDataModelByBusinessKey(appId, businessKey);
					if (StringUtils.equals(dataModel.getCreateBy(), actorId)
							&& dataModel.getWfStatus() != 9999) {
						this.formDataService.saveDataModel(appId, formContext);
						return ResponseUtils.responseJsonResult(true);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setEntityDefinitionService(
			EntityDefinitionService entityDefinitionService) {
		this.entityDefinitionService = entityDefinitionService;
	}

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@RequestMapping("update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String appId = ParamUtils.getString(params, "appId");
		String actorId = loginContext.getActorId();
		FormContext formContext = new FormContext();
		formContext.setContextPath(request.getContextPath());
		formContext.setDataMap(params);
		formContext.setLoginContext(loginContext);
		try {
			String businessKey = request.getParameter("businessKey");
			FormApplication formApplication = null;
			if (StringUtils.isNotEmpty(appId)) {
				formApplication = formDataService.getFormApplication(appId);
				request.setAttribute("formApplication", formApplication);
				if (StringUtils.isNotEmpty(businessKey)) {
					DataModel dataModel = formDataService
							.getDataModelByBusinessKey(appId, businessKey);
					if (StringUtils.equals(dataModel.getCreateBy(), actorId)
							&& dataModel.getWfStatus() != 9999) {
						this.formDataService
								.updateDataModel(appId, formContext);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("updateData")
	public byte[] updateData(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String actorId = loginContext.getActorId();
		String appId = ParamUtils.getString(params, "appId");
		FormContext formContext = new FormContext();
		formContext.setContextPath(request.getContextPath());
		formContext.setDataMap(params);
		formContext.setLoginContext(loginContext);
		try {
			String businessKey = request.getParameter("businessKey");
			FormApplication formApplication = null;
			if (StringUtils.isNotEmpty(appId)) {
				formApplication = formDataService.getFormApplication(appId);
				request.setAttribute("formApplication", formApplication);
				if (StringUtils.isNotEmpty(businessKey)) {
					DataModel dataModel = formDataService
							.getDataModelByBusinessKey(appId, businessKey);
					if (StringUtils.equals(dataModel.getCreateBy(), actorId)
							&& dataModel.getWfStatus() != 9999) {
						this.formDataService
								.updateDataModel(appId, formContext);
						return ResponseUtils.responseJsonResult(true);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

}
