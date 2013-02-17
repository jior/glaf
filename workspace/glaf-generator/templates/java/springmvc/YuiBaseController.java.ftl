package ${packageName}.web.springmvc;

import java.io.IOException;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import org.json.*;

import com.glaf.base.config.*;
import com.glaf.base.entity.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.security.*;
import com.glaf.base.utils.*;
import org.jpage.jbpm.container.ProcessContainer;
import org.jpage.jbpm.context.ProcessContext;
import org.jpage.jbpm.datafield.DataField;
import org.jpage.jbpm.model.*; 

import ${packageName}.model.*;
import ${packageName}.service.*;


public class ${entityName}BaseController {
	protected final transient Log logger = LogFactory
			.getLog(${entityName}BaseController.class);

        @Autowired
	protected ${entityName}Service ${modelName}Service;

	public ${entityName}BaseController() {

	}


	@RequestMapping(params = "method=save")
	public ModelAndView save(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil.getSysUser(request);
		String actorId =  user.getAccount();
		Map<String, Object> params = RequestUtil.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
		${entityName} ${modelName} = new ${entityName}();
		Tools.populate(${modelName}, params);
		${modelName}.setCreateBy(actorId);
         
		${modelName}Service.save(${modelName});   

		return this.list(request, modelMap);
	}

    @RequestMapping(params = "method=update")
	public ModelAndView update(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil.getSysUser(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
		}
 
		if (${modelName} != null && StringUtils.equals(${modelName}.getCreateBy(), user.getAccount())) {
			if (${modelName}.getStatus() == 0
					|| ${modelName}.getStatus() == -1) {
				Tools.populate(${modelName}, params);
				${modelName}Service.save(${modelName});
			}
		}

		return this.list(request, modelMap);
	}

    @RequestMapping(params = "method=delete")
	public ModelAndView delete(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil
				.getSysUser(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		String rowIds = request.getParameter("rowIds");
		if (StringUtils.isNotEmpty(rowIds)) {
			StringTokenizer token = new StringTokenizer(rowIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					${entityName} ${modelName} = ${modelName}Service.get${entityName}(x);
					if (${modelName} != null && StringUtils.equals(${modelName}.getCreateBy(), user.getAccount())) {
						${modelName}.setDeleteFlag(1);
						${modelName}Service.save(${modelName});
					}
				}
			}
		} else if (StringUtils.isNotEmpty(rowId)) {
			${entityName} ${modelName} = ${modelName}Service
					.get${entityName}(rowId);
			if (${modelName} != null && StringUtils.equals(${modelName}.getCreateBy(), user.getAccount())) {
				${modelName}.setDeleteFlag(1);
				${modelName}Service.save(${modelName});
			}
		}

		return this.list(request, modelMap);
	}

    @RequestMapping(params = "method=startProcess")
	public ModelAndView startProcess(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil
				.getSysUser(request);
		String actorId =  user.getAccount();
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = ${modelName}Service
				.get${entityName}(rowId);
		if (${modelName} != null) {
			String processName = SystemConfig.getString("${entityName}.processName");
			if (StringUtils.isEmpty(processName)) {
				processName = "${entityName}Process";
				${modelName}.setProcessName(processName);
			}
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(String.valueOf(${modelName}.getId()));
			ctx.setActorId(actorId);
			ctx.setTitle(SystemConfig.getString("res_rowId") + ${modelName}.getId());
			ctx.setProcessName(${modelName}.getProcessName());
			String processInstanceId = ProcessContainer.getContainer()
					.startProcess(ctx);
			if (processInstanceId == null) {
				request.setAttribute("error_message", SystemConfig.getString("res_process_start_error"));
				return new ModelAndView("/error", modelMap);
			}
		}

		return this.list(request, modelMap);
	}

    @RequestMapping(params = "method=completeTask")
	public ModelAndView completeTask(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil
				.getSysUser(request);
		String actorId =  user.getAccount();
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String taskInstanceId = ParamUtil.getString(params, "taskInstanceId");
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
			if(${modelName} !=null && ${modelName}.getStatus() != 50){ 
			TaskItem taskItem = ProcessContainer.getContainer().getMinTaskItem(actorId, ${modelName}.getProcessInstanceId());
			if(taskItem != null && StringUtils.equals(taskItem.getTaskInstanceId(), taskInstanceId)){
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
					ctx.setTaskInstanceId(taskInstanceId);
					ctx.setProcessInstanceId(${modelName}.getProcessInstanceId());
					boolean isOK = ProcessContainer.getContainer().completeTask(ctx);
					if (!isOK) {
						request.setAttribute("error_message", SystemConfig.getString("res_complete_task_error"));
						return new ModelAndView("/error", modelMap);
					}
				 }
			}
		  }
		}

		return this.list(request, modelMap);
	}

    @RequestMapping(params = "method=edit")
	public ModelAndView edit(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil
				.getSysUser(request);
		String actorId =  user.getAccount();
		RequestUtil.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
			request.setAttribute("${modelName}", ${modelName});
			Map<String, Object> dataMap = Tools.getDataMap(${modelName});
			String x_json = JSONTools.encode(dataMap);
			x_json = RequestUtil.encodeString(x_json);
			request.setAttribute("x_json", x_json);
		}

        boolean canUpdate = false;
		boolean canSubmit = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {
			if (${modelName} != null
					&& StringUtils.isNotEmpty(${modelName}
							.getProcessInstanceId())) {
				ProcessContainer container = ProcessContainer.getContainer();
				Collection<String> processInstanceIds = container
						.getRunningProcessInstanceIds(actorId);
				if (processInstanceIds.contains(${modelName}
						.getProcessInstanceId())) {
					canSubmit = true;
				}
				if (${modelName}.getStatus() == 0
						|| ${modelName}.getStatus() == -1) {
					canUpdate = true;
				}
				TaskItem taskItem = container.getMinTaskItem(actorId, ${modelName}
							.getProcessInstanceId());
				if(taskItem != null){
					  request.setAttribute("taskItem", taskItem);
				}
                List<StepInstance> stepInstances = container
						.getStepInstances(${modelName}.getProcessInstanceId());
				request.setAttribute("stepInstances", stepInstances);
			} else {
				canSubmit = true;
				canUpdate = true;
			}
		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (${modelName} != null) {
				if (${modelName}.getStatus() == 0
						|| ${modelName}.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canSubmit",  canSubmit);
		request.setAttribute("canUpdate",  canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = SystemConfig.getString("${modelName}.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/${modelName}/edit", modelMap);
	}

    @RequestMapping(params = "method=view")
	public ModelAndView view(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
			request.setAttribute("${modelName}", ${modelName});
			Map<String, Object> dataMap = Tools.getDataMap(${modelName});
			String x_json = JSONTools.encode(dataMap);
			x_json = RequestUtil.encodeString(x_json);
			request.setAttribute("x_json", x_json);

			if (StringUtils.isNotEmpty(${modelName}.getProcessInstanceId())) {
				ProcessContainer container = ProcessContainer.getContainer();
				List<StepInstance> stepInstances = container
						.getStepInstances(${modelName}.getProcessInstanceId());
				request.setAttribute("stepInstances", stepInstances);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = SystemConfig.getString("${modelName}.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/${modelName}/view");
	}

     @RequestMapping(params = "method=query")
	public ModelAndView query(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = SystemConfig.getString("${modelName}.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/apps/${modelName}/query", modelMap);
	}

	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		SysUser user = RequestUtil
				.getSysUser(request);
		String actorId =  user.getAccount();
		RequestUtil.setRequestParameterToAttribute(request);

		Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
		String queryString = request.getParameter("x_complex_query");
		if (StringUtils.isNotEmpty(queryString)) {
			queryString = RequestUtil.decodeString(queryString);
			if (LogUtil.isDebug()) {
				logger.debug(queryString);
			}
			Map<String, Object> xMap = JSONTools.decode(queryString);
			if (xMap != null) {
				paramMap.putAll(xMap);
			}
		}

		String x_names = ParamUtil.getString(paramMap, "x_display_names");

		logger.info("show names:" + x_names);

		int start = ParamUtil.getInt(paramMap, "startIndex");
		int limit = ParamUtil.getInt(paramMap, "results");

		int pageNo = 1;
		if (start > 0 && limit > 0) {
			pageNo = start / limit + 1;
		}

		Collection<QueryParameter> searchFields = new ArrayList<QueryParameter>();
		Map<String, Class<?>> propertyMap = new HashMap<String, Class<?>>();
		try {
			Object object = ${entityName}.class.newInstance();
			Map<String, Class<?>> xMap = Tools.getPropertyMap(object);
			if (xMap != null) {
				propertyMap.putAll(xMap);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		Iterator<String> iterator = propertyMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Object value = paramMap.get("x_query_" + key);
			if (value != null) {
				String filter = (String) paramMap.get("x_filter_" + key);
				QueryParameter param = new QueryParameter();
				param.setName(key);
				param.setValue(value);
				param.setFilter(filter);
				searchFields.add(param);
			}
		}


        String processName = SystemConfig.getString("${entityName}.processName");
		if (StringUtils.isEmpty(processName)) {
			processName = "${entityName}";
		}

		String taskType = request.getParameter("taskType");
		if (StringUtils.isEmpty(taskType)) {
			taskType = "all";
		}

		ProcessContainer container = ProcessContainer.getContainer();
		if (StringUtils.equals(taskType, "running")) {
			Collection<String> processInstanceIds = container
					.getRunningProcessInstanceIds(processName, securityContext
							.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				paramMap.put("processInstanceIds", processInstanceIds);
				paramMap.put("x_processName", processName);
			} else {
				processInstanceIds = new ArrayList<String>();
				processInstanceIds.add("0");
				paramMap.put("processInstanceIds", processInstanceIds);
			}
		} else if (StringUtils.equals(taskType, "finished")) {
			Collection<String> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName, securityContext
							.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				paramMap.put("processInstanceIds", processInstanceIds);
				paramMap.put("x_processName", processName);
			} else {
				processInstanceIds = new ArrayList<String>();
				processInstanceIds.add("0");
				paramMap.put("processInstanceIds", processInstanceIds);
			}
		} else if (StringUtils.equals(taskType, "fallback")) {
			Collection<String> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName, securityContext
							.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				paramMap.put("processInstanceIds", processInstanceIds);
				paramMap.put("x_processName", processName);
			} else {
				processInstanceIds = new ArrayList<String>();
				processInstanceIds.add("0");
				paramMap.put("processInstanceIds", processInstanceIds);
			}
		} else if (StringUtils.equals(taskType, "worked")) {
			Collection<String> processInstanceIds = container
					.getWorkedProcessInstanceIds(processName, securityContext
							.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				paramMap.put("processInstanceIds", processInstanceIds);
				paramMap.put("x_processName", processName);
			} else {
				processInstanceIds = new ArrayList<String>();
				processInstanceIds.add("0");
				paramMap.put("processInstanceIds", processInstanceIds);
			}
		} else {
			paramMap.put("taskType", taskType);
		}

		 
		if (LogUtil.isDebug()) {
			logger.debug("paramMap:" + paramMap);
			logger.debug("searchFields:" + searchFields);
		}

 
		QueryContext queryContext = new QueryContext();
		queryContext.setPageResultNo(pageNo);
		queryContext.setPageResultSize(limit);
		queryContext.setQueryParams(paramMap);
		queryContext.setSearchFields(searchFields);
		queryContext.setSecurityContext(securityContext);

        String sortname = ParamUtil.getString(paramMap, "sort");
	    String sortorder  = ParamUtil.getString(paramMap, "dir");
		 if(StringUtils.isNotEmpty(sortname)){
		    OrderByField orderByField = new OrderByField();
            orderByField.setName(sortname);
            if(sortorder != null && "DESC".equalsIgnoreCase(sortorder)){
				orderByField.setSort(1);
			}
           queryContext.getOrderByFields().add(orderByField);
		}

        ${entityName}Service ${modelName}Service = (${entityName}Service)modelMap.get("${modelName}Service");
		PageResult jpage = ${modelName}Service.getPageResult${entityName}s(queryContext);
		RequestUtil.setRequestParameterToAttribute(request);
		request.setAttribute("jpage", jpage);

		Map<String, SysUser> userMap = IdentityFactory
				.getUserMap();

		int index = 0;
		List<Object> rows = jpage.getRows();
		List<Object> list = new ArrayList<Object>();
		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			Object object = iter.next();
			Map<String, Object> rowMap = Tools.getDataMap(object);

			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			dataMap.put("x_row_no", Integer.valueOf(jpage.getCurrRowNo()
					+ index++));

			String x_actorId = ParamUtil.getString(rowMap, "actorId");

			SysUser userProfile = (SysUser) userMap.get(x_actorId);
			if (userProfile != null) {
				dataMap.put("applyUser", userProfile.getName());
				dataMap.put("applyDept", userProfile.getDeptName());
			} else {
				dataMap.put("applyUser", actorId);
				dataMap.put("applyDept", "");
			}

			Date createDate = (Date) rowMap.get("createDate");
			if (createDate != null) {
				dataMap.put("createDate", DateTools.getDate(createDate));
			} else {
				dataMap.put("createDate", "");
			}

			if (StringUtils.isNotEmpty(x_names)) {
				StringTokenizer token = new StringTokenizer(x_names, ",");
				while (token.hasMoreTokens()) {
					String name = token.nextToken();
					Object value = rowMap.get(name);
					if (value != null) {
						if (value instanceof Date) {
							String date = DateTools.getDate((Date) value);
							value = date;
							dataMap.put(name, value);
						} else {
							dataMap.put(name, value);
						}
					} else {
						dataMap.put(name, "");
					}
				}
			}

			int status = ParamUtil.getInt(rowMap, "status");

			switch (status) {
			case 40:
				dataMap.put("x_status", SystemConfig
						.getString("res_status_40"));
				break;
			case 50:
				dataMap.put("x_status", SystemConfig
						.getString("res_status_50"));
				break;
			case -1:
				dataMap.put("x_status", SystemConfig
						.getString("res_status_5555"));
				break;
			default:
				dataMap.put("x_status", SystemConfig
						.getString("res_status_0"));
				break;
			}

			if (rowMap.get("id") != null) {
				dataMap.put("id", rowMap.get("id").toString());
				dataMap.put("rowId", rowMap.get("id").toString());
			} else if (rowMap.get("rowId") != null) {
				dataMap.put("id", rowMap.get("rowId").toString());
				dataMap.put("rowId", rowMap.get("rowId").toString());
			}

			if (rowMap.get("processInstanceId") != null) {
				dataMap.put("processInstanceId", rowMap
						.get("processInstanceId"));
			}

			if (rowMap.get("wfStatus") != null) {
				dataMap.put("wfStatus", rowMap.get("wfStatus"));
			}

			if (rowMap.get("processName") != null) {
				dataMap.put("processName", rowMap.get("processName"));
			}

			if (rowMap.get("resourceId") != null) {
				dataMap.put("resourceId", rowMap.get("resourceId"));
			}

			if (rowMap.get("key") != null) {
				dataMap.put("key", rowMap.get("key"));
			}

			if (rowMap.get("title") != null) {
				dataMap.put("title", rowMap.get("title"));
			}

			if (rowMap.get("subject") != null) {
				dataMap.put("subject", rowMap.get("subject"));
			}

			dataMap.put("actorId", actorId);
			dataMap.put("status", status);

			list.add(dataMap);
		}

		Map<String, Object> pageInfo = new HashMap<String, Object>();
		// 当前页数设置
		pageInfo.put("startIndex", Integer.valueOf(start));

		// 每页记录数
		pageInfo.put("pageSize", Integer.valueOf(limit));

		// 总数据量设置
		pageInfo.put("totalRecords", Integer.valueOf(jpage
				.getTotalRecordCount()));
		pageInfo.put("records", list);
		JSONObject jsonObject = new JSONObject(pageInfo);

		return jsonObject.toString().getBytes("UTF-8");
	}

        @RequestMapping 
	public ModelAndView list(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
			String x_complex_query = JSONTools.encode(paramMap);
			x_complex_query = RequestUtil.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = SystemConfig.getString("${modelName}.yuigrid");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/${modelName}/yuigrid", modelMap);
	}

	public void set${entityName}Service(
			${entityName}Service ${modelName}Service) {
		this.${modelName}Service = ${modelName}Service;
	}

}
