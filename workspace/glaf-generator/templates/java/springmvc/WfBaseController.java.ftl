package ${packageName}.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.config.ViewProperties;

import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;
import com.glaf.jbpm.model.*;
import com.glaf.jbpm.container.*;


import com.glaf.base.modules.sys.model.*;
import com.glaf.base.security.*;
import com.glaf.base.utils.*;

import ${packageName}.model.*;
import ${packageName}.query.*;

public class ${entityName}WfController extends ${entityName}BaseController {
	protected static final Log logger = LogFactory
			.getLog(${entityName}WfController.class);

	public ${entityName}WfController() {

	}

	@RequestMapping("/startProcess")
	public ModelAndView startProcess(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil.getSysUser(request);
		String actorId = user.getAccount();
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = ${modelName}Service.get${entityName}(rowId);
		if (${modelName} != null) {
			String processName = ViewProperties.getString("${entityName}.processName");
			if (StringUtils.isEmpty(processName)) {
				processName = "${entityName}Process";
				${modelName}.setProcessName(processName);
			}
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(String.valueOf(${modelName}.getId()));
			ctx.setActorId(actorId);
			ctx.setTitle(ViewProperties.getString("res_rowId") + ${modelName}.getId());
			ctx.setProcessName(${modelName}.getProcessName());
			Object processInstanceId = ProcessContainer.getContainer()
					.startProcess(ctx);
			if (processInstanceId == null) {
				request.setAttribute("error_message",
						ViewProperties.getString("res_process_start_error"));
				return new ModelAndView("/error", modelMap);
			}
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/completeTask")
	public ModelAndView completeTask(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil.getSysUser(request);
		String actorId = user.getAccount();
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String taskInstanceId = ParamUtil.getString(params, "taskInstanceId");
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
			if (${modelName} != null && ${modelName}.getStatus() != 50) {
				TaskItem taskItem = ProcessContainer.getContainer()
						.getMinTaskItem(actorId,
								Long.parseLong(${modelName}.getProcessInstanceId()));
				if (taskItem != null
						&& StringUtils.equals(
								String.valueOf(taskItem.getTaskInstanceId()),
								taskInstanceId)) {
					if (StringUtils.isNotEmpty(${modelName}.getProcessInstanceId())) {
						String route = request.getParameter("route");
						String isAgree = request.getParameter("isAgree");
						String opinion = request.getParameter("opinion");
						ProcessContext ctx = new ProcessContext();
						Collection<DataField> datafields = new ArrayList<DataField>();
						if (StringUtils.isNotEmpty(isAgree)) {
							DataField datafield = new DataField();
							datafield.setName("isAgree");
							datafield.setValue(isAgree);
							datafields.add(datafield);
						}
						if (StringUtils.isNotEmpty(route)) {
							DataField datafield = new DataField();
							datafield.setName("route");
							datafield.setValue(route);
							datafields.add(datafield);
						}
						ctx.setActorId(actorId);
						ctx.setOpinion(opinion);
						ctx.setContextMap(params);
						ctx.setDataFields(datafields);
						ctx.setTaskInstanceId(Long.parseLong(taskInstanceId));
						ctx.setProcessInstanceId(Long.parseLong(${modelName}
								.getProcessInstanceId()));
						boolean isOK = ProcessContainer.getContainer()
								.completeTask(ctx);
						if (!isOK) {
							request.setAttribute("error_message", ViewProperties
									.getString("res_complete_task_error"));
							return new ModelAndView("/error", modelMap);
						}
					}
				}
			}
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		SysUser user = RequestUtil.getSysUser(request);
		String actorId = user.getAccount();
		RequestUtil.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
			request.setAttribute("${modelName}", ${modelName});
			JSONObject rowJSON = ${modelName}.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
		}

		boolean canUpdate = false;
		boolean canSubmit = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {
			if (${modelName} != null
					&& StringUtils.isNotEmpty(${modelName}.getProcessInstanceId())) {
				ProcessContainer container = ProcessContainer.getContainer();
				Collection<Long> processInstanceIds = container
						.getRunningProcessInstanceIds(actorId);
				if (processInstanceIds.contains(${modelName}.getProcessInstanceId())) {
					canSubmit = true;
				}
				if (${modelName}.getStatus() == 0 || ${modelName}.getStatus() == -1) {
					canUpdate = true;
				}
				TaskItem taskItem = container.getMinTaskItem(actorId,
						Long.parseLong(${modelName}.getProcessInstanceId()));
				if (taskItem != null) {
					request.setAttribute("taskItem", taskItem);
				}
				List<ActivityInstance> stepInstances = container
						.getActivityInstances(Long.parseLong(${modelName}
								.getProcessInstanceId()));
				request.setAttribute("stepInstances", stepInstances);
				request.setAttribute("stateInstances", stepInstances);
			} else {
				canSubmit = true;
				canUpdate = true;
			}
		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (${modelName} != null) {
				if (${modelName}.getStatus() == 0 || ${modelName}.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canSubmit", canSubmit);
		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("${modelName}.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/${modelName}/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
			request.setAttribute("${modelName}", ${modelName});
 
			JSONObject rowJSON = ${modelName}.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());

			if (StringUtils.isNotEmpty(${modelName}.getProcessInstanceId())) {
				ProcessContainer container = ProcessContainer.getContainer();
				List<ActivityInstance> stepInstances = container
						.getActivityInstances(Long.parseLong(${modelName}
								.getProcessInstanceId()));
				request.setAttribute("stepInstances", stepInstances);
				request.setAttribute("stateInstances", stepInstances);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("${modelName}.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/${modelName}/view");
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		SysUser user = RequestUtil.getSysUser(request);
	 
		RequestUtil.setRequestParameterToAttribute(request);

		String processName = ViewProperties.getString("${entityName}.processName");
		if (StringUtils.isEmpty(processName)) {
			processName = "${entityName}Process";
		}

		String taskType = request.getParameter("taskType");
		if (StringUtils.isEmpty(taskType)) {
			taskType = "all";
		}

		Map<String, Object> params = RequestUtil.getParameterMap(request);
		${entityName}Query query = new ${entityName}Query();
		Tools.populate(query, params);

		ProcessContainer container = ProcessContainer.getContainer();
		if (StringUtils.equals(taskType, "running")) {
			List<Long> processInstanceIds = container
					.getRunningProcessInstanceIdsByName(processName,
							user.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new ArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(taskType, "finished")) {
			List<Long> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName,
							user.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new ArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(taskType, "fallback")) {
			List<Long> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName,
							user.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new ArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(taskType, "worked")) {
			List<Long> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName,
							user.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new ArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		}

		String gridType = ParamUtil.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtil.getInt(params, "page");
		limit = ParamUtil.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtil.getString(params, "sortName");
		order = ParamUtil.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = ${modelName}Service.get${entityName}CountByQueryCriteria(query);
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

			Map<String, SysUser> userMap = IdentityFactory.getUserMap();
			List<${entityName}> list = ${modelName}Service.get${entityName}sByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (${entityName} ${modelName} : list) {
					JSONObject rowJSON = ${modelName}.toJsonObject();
					rowJSON.put("id", ${modelName}.getId());
					rowJSON.put("${modelName}Id", ${modelName}.getId());

					rowsJSON.add(rowJSON);
				}

			}
		}
		return result.toString().getBytes("UTF-8");
	}

}
