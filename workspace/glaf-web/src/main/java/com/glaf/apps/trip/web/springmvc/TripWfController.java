package com.glaf.apps.trip.web.springmvc;

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


import com.glaf.apps.trip.model.*;
import com.glaf.apps.trip.query.*;

public class TripWfController extends TripBaseController {
	private static final Log logger = LogFactory.getLog(TripWfController.class);

	public TripWfController() {

	}

	@RequestMapping(params = "method=startProcess")
	@ResponseBody
	public byte[] startProcess(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                Trip trip = tripService.getTrip(request.getParameter("id"));
		if (trip != null) {
			String processName = ViewProperties.getString("Trip.processName");
			if (StringUtils.isEmpty(processName)) {
				processName = "TripProcess";
				trip.setProcessName(processName);
			}
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(trip.getId());
			ctx.setActorId(actorId);
			ctx.setTitle(ViewProperties.getString("res_id") + trip.getId());
			ctx.setProcessName(trip.getProcessName());
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

	@RequestMapping(params = "method=completeTask")
	@ResponseBody
	public byte[] completeTask(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		
                Trip trip = tripService.getTrip(request.getParameter("id"));
		if (trip != null && trip.getProcessInstanceId() != null && trip.getWfStatus() != 9999) {
			TaskItem taskItem = ProcessContainer.getContainer().getMinTaskItem(actorId, trip.getProcessInstanceId());
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
				ctx.setProcessInstanceId(trip.getProcessInstanceId());
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
	@RequestMapping(params = "method=edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);

                Trip trip = tripService.getTrip(request.getParameter("id"));

                if (trip != null) {
		    request.setAttribute("trip", trip);
		    JSONObject rowJSON = trip.toJsonObject();
		    request.setAttribute("x_json", rowJSON.toJSONString());
		}
	

		boolean canUpdate = false;
		boolean canSubmit = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {
			if (trip != null && trip.getProcessInstanceId() != null) {
				ProcessContainer container = ProcessContainer.getContainer();
				Collection<Long> processInstanceIds = container
						.getRunningProcessInstanceIds(actorId);
				if (processInstanceIds.contains(trip.getProcessInstanceId())) {
					canSubmit = true;
				}
				if (trip.getStatus() == 0 || trip.getStatus() == -1) {
					canUpdate = true;
				}
				TaskItem taskItem = container.getMinTaskItem(actorId, trip.getProcessInstanceId());
				if (taskItem != null) {
					request.setAttribute("taskItem", taskItem);
				}
				List<ActivityInstance> stepInstances = container
						.getActivityInstances(trip.getProcessInstanceId());
				request.setAttribute("stepInstances", stepInstances);
				request.setAttribute("stateInstances", stepInstances);
			} else {
				canSubmit = true;
				canUpdate = true;
			}
		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (trip != null) {
				if (trip.getStatus() == 0 || trip.getStatus() == -1) {
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

		String x_view = ViewProperties.getString("trip.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/trip/edit", modelMap);
	}

        @Override
	@RequestMapping(params = "method=view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                Trip trip = tripService.getTrip(request.getParameter("id"));
		request.setAttribute("trip", trip);
 
		JSONObject rowJSON = trip.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		if ( trip.getProcessInstanceId() != null) {
			ProcessContainer container = ProcessContainer.getContainer();
			List<ActivityInstance> stepInstances = container
						.getActivityInstances(trip.getProcessInstanceId());
			request.setAttribute("stepInstances", stepInstances);
			request.setAttribute("stateInstances", stepInstances);
		}
		

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("trip.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/trip/view");
	}

        @Override
	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap, TripQuery query)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
	 
		RequestUtils.setRequestParameterToAttribute(request);

		String processName = ViewProperties.getString("Trip.processName");
		if (StringUtils.isEmpty(processName)) {
			processName = "TripProcess";
		}

		String workedProcessFlag = request.getParameter("workedProcessFlag");
		if (StringUtils.isEmpty(workedProcessFlag)) {
			workedProcessFlag = "ALL";
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		//TripQuery query = new TripQuery();
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
		int total = tripService.getTripCountByQueryCriteria(query);
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
			List<Trip> list = tripService.getTripsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Trip trip : list) {
					JSONObject rowJSON = trip.toJsonObject();
					rowJSON.put("id", trip.getId());
					rowJSON.put("tripId", trip.getId());
                                        rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

}
