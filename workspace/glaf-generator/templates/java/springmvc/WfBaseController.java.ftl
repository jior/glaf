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
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;
import com.glaf.jbpm.model.*;
import com.glaf.jbpm.container.*;


import ${packageName}.domain.*;
import ${packageName}.query.*;


public class ${entityName}WfController extends ${entityName}Controller {
	private static final Log logger = LogFactory.getLog(${entityName}WfController.class);

	public ${entityName}WfController() {

	}

	@RequestMapping("/startProcess")
	@ResponseBody
	public byte[] startProcess(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		<#if idField.type=='Integer' >
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(RequestUtils.getInt(request, "${idField.name}"));
		<#elseif idField.type== 'Long' >
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(RequestUtils.getLong(request, "${idField.name}"));
		<#else>
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(request.getParameter("${idField.name}"));
		</#if>
		if (${modelName} != null) {
			String processName = ViewProperties.getString("${entityName}.processName");
			if (StringUtils.isEmpty(processName)) {
				processName = "${entityName}Process";
				${modelName}.setProcessName(processName);
			}
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(${modelName}.getId());
			ctx.setActorId(actorId);
			ctx.setTitle(ViewProperties.getString("res_${idField.name}") + ${modelName}.getId());
			ctx.setProcessName(${modelName}.getProcessName());
			try{
			    Long processInstanceId = ProcessContainer.getContainer()
					.startProcess(ctx);
			    if (processInstanceId != null && processInstanceId > 0) {
			        return ResponseUtils.responseJsonResult(true);	 
			   }
			} catch(Exception ex){
			   ex.printStackTrace();
			   logger.error(ex);
			}
		}

	    return ResponseUtils.responseJsonResult(false);	 
	}

	@RequestMapping("/completeTask")
	@ResponseBody
	public byte[] completeTask(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		
		<#if idField.type=='Integer' >
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(RequestUtils.getInt(request, "${idField.name}"));
		<#elseif idField.type== 'Long' >
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(RequestUtils.getLong(request, "${idField.name}"));
		<#else>
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(request.getParameter("${idField.name}"));
		</#if>
		if (${modelName} != null && ${modelName}.getProcessInstanceId() != null && ${modelName}.getWfStatus() != 9999) {
			TaskItem taskItem = ProcessContainer.getContainer().getMinTaskItem(actorId, ${modelName}.getProcessInstanceId());
			if (taskItem != null) {				
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
				ctx.setTaskInstanceId(taskItem.getTaskInstanceId());
				ctx.setProcessInstanceId(${modelName}.getProcessInstanceId());
				try{
					boolean isOK = ProcessContainer.getContainer().completeTask(ctx);
					if (isOK) {
					    return ResponseUtils.responseJsonResult(true);	  
					}
				} catch(Exception ex){
				    ex.printStackTrace();
				    logger.error(ex);
				}
			}
	     }
		
            return ResponseUtils.responseJsonResult(false);	 
	}

        @Override
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		<#if idField.type=='Integer' >
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(RequestUtils.getInt(request, "${idField.name}"));
		<#elseif idField.type== 'Long' >
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(RequestUtils.getLong(request, "${idField.name}"));
		<#else>
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(request.getParameter("${idField.name}"));
		</#if>

                if (${modelName} != null) {
		    request.setAttribute("${modelName}", ${modelName});
		    JSONObject rowJSON = ${modelName}.toJsonObject();
		    request.setAttribute("x_json", rowJSON.toJSONString());
		}
	

		boolean canUpdate = false;
		boolean canSubmit = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {
			if (${modelName} != null && ${modelName}.getProcessInstanceId() != null) {
				ProcessContainer container = ProcessContainer.getContainer();
				Collection<Long> processInstanceIds = container
						.getRunningProcessInstanceIds(actorId);
				if (processInstanceIds.contains(${modelName}.getProcessInstanceId())) {
					canSubmit = true;
				}
				if (${modelName}.getStatus() == 0 || ${modelName}.getStatus() == -1) {
					canUpdate = true;
				}
				TaskItem taskItem = container.getMinTaskItem(actorId, ${modelName}.getProcessInstanceId());
				if (taskItem != null) {
					request.setAttribute("taskItem", taskItem);
				}
				List<ActivityInstance> stepInstances = container
						.getActivityInstances(${modelName}.getProcessInstanceId());
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

		return new ModelAndView("/${classDefinition.moduleName}/${modelName}/edit", modelMap);
	}

        @Override
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		<#if idField.type=='Integer' >
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(RequestUtils.getInt(request, "${idField.name}"));
		<#elseif idField.type== 'Long' >
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(RequestUtils.getLong(request, "${idField.name}"));
		<#else>
                ${entityName} ${modelName} = ${modelName}Service.get${entityName}(request.getParameter("${idField.name}"));
		</#if>
		request.setAttribute("${modelName}", ${modelName});
 
		JSONObject rowJSON = ${modelName}.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		if ( ${modelName}.getProcessInstanceId() != null) {
			ProcessContainer container = ProcessContainer.getContainer();
			List<ActivityInstance> stepInstances = container
						.getActivityInstances(${modelName}.getProcessInstanceId());
			request.setAttribute("stepInstances", stepInstances);
			request.setAttribute("stateInstances", stepInstances);
		}
		

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("${modelName}.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/${classDefinition.moduleName}/${modelName}/view");
	}

        @Override
	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
	 
		RequestUtils.setRequestParameterToAttribute(request);

		String processName = ViewProperties.getString("${entityName}.processName");
		if (StringUtils.isEmpty(processName)) {
			processName = "${entityName}Process";
		}

		String workedProcessFlag = request.getParameter("workedProcessFlag");
		if (StringUtils.isEmpty(workedProcessFlag)) {
			workedProcessFlag = "ALL";
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		${entityName}Query query = new ${entityName}Query();
		Tools.populate(query, params);
		query.setWorkedProcessFlag(workedProcessFlag);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		ProcessContainer container = ProcessContainer.getContainer();
		if (StringUtils.equals(workedProcessFlag, "PD")) {
			List<Long> processInstanceIds = container
					.getRunningProcessInstanceIdsByName(processName,
							loginContext.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new ArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(workedProcessFlag, "END")) {
			List<Long> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName,
							loginContext.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new ArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(workedProcessFlag, "FB")) {
			List<Long> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName,
							loginContext.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new ArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(workedProcessFlag, "WD")) {
			List<Long> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName,
							loginContext.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new ArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<${entityName}> list = ${modelName}Service.get${entityName}sByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (${entityName} ${modelName} : list) {
					JSONObject rowJSON = ${modelName}.toJsonObject();
					rowJSON.put("id", ${modelName}.getId());
					rowJSON.put("${modelName}Id", ${modelName}.getId());
                                        rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

}
