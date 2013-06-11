package com.glaf.form.web.springmvc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.EntityDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.identity.*;
import com.glaf.core.query.EntityDefinitionQuery;
import com.glaf.core.security.*;
import com.glaf.core.service.EntityDefinitionService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.*;
import com.glaf.core.xml.XmlReader;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.*;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;

@Controller("/system/form/application")
@RequestMapping("/system/form/application")
public class FormApplicationController {

	private static final Log logger = LogFactory
			.getLog(FormApplicationController.class);

	protected EntityDefinitionService entityDefinitionService;

	protected FormDataService formDataService;

	protected ITableDefinitionService tableDefinitionService;

	public FormApplicationController() {

	}

	@ResponseBody
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String appId = ParamUtils.getString(params, "appId");
		String appIds = request.getParameter("appIds");
		if (StringUtils.isNotEmpty(appIds)) {
			StringTokenizer token = new StringTokenizer(appIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					FormApplication formApplication = formDataService
							.getFormApplication(x);
					if (formApplication != null
							&& (StringUtils.equals(
									formApplication.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
					}
				}
			}
		} else if (StringUtils.isNotEmpty(appId)) {
			FormApplication formApplication = formDataService
					.getFormApplication(appId);
			if (formApplication != null
					&& (StringUtils.equals(formApplication.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
			}
		}
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String appId = ParamUtils.getString(params, "appId");
		FormApplication formApplication = null;
		if (StringUtils.isNotEmpty(appId)) {
			formApplication = formDataService.getFormApplication(appId);
			request.setAttribute("formApplication", formApplication);
			JSONObject rowJSON = formApplication.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
			logger.debug(formApplication.toJsonObject().toJSONString());
		}

		FormDefinitionQuery query = new FormDefinitionQuery();
		query.locked(0);
		query.setNodeId(RequestUtils.getLong(request, "nodeId"));
		if (formApplication != null && formApplication.getNodeId() != null) {
			query.setNodeId(formApplication.getNodeId());
		}

		List<FormDefinition> formDefinitions = formDataService
				.getLatestFormDefinitions(query);
		request.setAttribute("formDefinitions", formDefinitions);

		EntityDefinitionQuery q = new EntityDefinitionQuery();
		q.setNodeId(RequestUtils.getLong(request, "nodeId"));
		q.setType("FormApp");
		if (formApplication != null && formApplication.getNodeId() != null) {
			q.setNodeId(formApplication.getNodeId());
		}

		List<EntityDefinition> entityDefinitions = entityDefinitionService
				.list(q);
		request.setAttribute("entityDefinitions", entityDefinitions);

		JbpmContext jbpmContext = null;
		GraphSession graphSession = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext != null) {
				graphSession = jbpmContext.getGraphSession();
				List<ProcessDefinition> processDefinitions = graphSession
						.findLatestProcessDefinitions();
				modelMap.put("processDefinitions", processDefinitions);
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formApplication.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/application/edit", modelMap);
	}

	@RequestMapping("editListColumns")
	public ModelAndView editListColumns(HttpServletRequest request,
			ModelMap modelMap) {
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
			EntityDefinition entityDefinition = entityDefinitionService
					.getEntityDefinition(targetId);
			if (entityDefinition != null) {
				// logger.debug(entityDefinition.toJsonObject().toJSONString());
				XmlReader reader = new XmlReader();
				TableDefinition tableDefinition = reader
						.read(new ByteArrayInputStream(entityDefinition
								.getData()));

				List<ColumnDefinition> unselectColumns = new ArrayList<ColumnDefinition>();
				if (tableDefinition.getColumns() != null) {
					for (ColumnDefinition c : tableDefinition.getColumns()) {
						if (columns != null && columns.contains(c)) {
							continue;
						}
						unselectColumns.add(c);
					}
				}

				logger.debug("column size:"
						+ tableDefinition.getColumns().size());

				request.setAttribute("tableDefinition", tableDefinition);
				request.setAttribute("unselectColumns", unselectColumns);
				request.setAttribute("selectedColumns", columns);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties
				.getString("formApplication.editListColumns");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/application/editListColumns", modelMap);
	}

	@RequestMapping("json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormApplicationQuery query = new FormApplicationQuery();
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
		Paging page = formDataService.getPageApplication(query);
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
					FormApplication formApplication = (FormApplication) object;
					JSONObject rowJSON = formApplication.toJsonObject();
					rowJSON.put("id", formApplication.getId());
					rowJSON.put("formApplicationId", formApplication.getId());
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

		return new ModelAndView("/form/application/list", modelMap);
	}

	@RequestMapping("query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formApplication.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/application/query", modelMap);
	}

	@RequestMapping("save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap,
			FormApplication formApplication) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();

		formApplication.setCreateBy(actorId);

		formDataService.saveFormApplication(formApplication);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("saveFormApplication")
	public byte[] saveFormApplication(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormApplication formApplication = new FormApplication();
		try {
			Tools.populate(formApplication, params);
			formApplication.setCreateBy(actorId);
			this.formDataService.saveFormApplication(formApplication);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("saveListColumns")
	public byte[] saveListColumns(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String appId = ParamUtils.getString(params, "appId");
		String objectIds = request.getParameter("objectIds");
		FormApplication formApplication = null;
		if (StringUtils.isNotEmpty(appId)) {
			try {
				formApplication = formDataService.getFormApplication(appId);
				request.setAttribute("formApplication", formApplication);
				String targetId = formApplication.getTableName() + "_FormApp";
				EntityDefinition entityDefinition = entityDefinitionService
						.getEntityDefinition(targetId);
				if (entityDefinition != null) {
					List<ColumnDefinition> selectColumns = new ArrayList<ColumnDefinition>();
					XmlReader reader = new XmlReader();
					TableDefinition tableDefinition = reader
							.read(new ByteArrayInputStream(entityDefinition
									.getData()));
					if (tableDefinition.getColumns() != null) {
						for (ColumnDefinition c : tableDefinition.getColumns()) {
							if (StringUtils.contains(objectIds,
									c.getColumnName())) {
								selectColumns.add(c);
							}
						}
					}
					tableDefinitionService.saveColumns(targetId, selectColumns);
					return ResponseUtils.responseJsonResult(true);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		return ResponseUtils.responseJsonResult(false);
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
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap,
			FormApplication formApplication) {
		formDataService.saveFormApplication(formApplication);
		return this.list(request, modelMap);
	}

	@RequestMapping("view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String appId = ParamUtils.getString(params, "appId");
		FormApplication formApplication = null;
		if (StringUtils.isNotEmpty(appId)) {
			formApplication = formDataService.getFormApplication(appId);
			request.setAttribute("formApplication", formApplication);
			JSONObject rowJSON = formApplication.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formApplication.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/application/view");
	}

}
