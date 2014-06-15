package com.glaf.apps.trip.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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

import com.glaf.apps.trip.model.*;
import com.glaf.apps.trip.query.*;
import com.glaf.apps.trip.service.*;

public class TripBaseController {
	protected static final Log logger = LogFactory
			.getLog(TripBaseController.class);

	protected TripService tripService;

	public TripBaseController() {

	}

	@javax.annotation.Resource
	public void setTripService(TripService tripService) {
		this.tripService = tripService;
	}

	@RequestMapping(params = "method=save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap,
			Trip trip) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		// Trip trip = new Trip();
		// Tools.populate(trip, params);

		trip.setWfStatus(RequestUtils.getInt(request, "wfStatus"));
		trip.setStartDate(RequestUtils.getDate(request, "startDate"));
		trip.setCause(request.getParameter("cause"));
		trip.setStatus(RequestUtils.getInt(request, "status"));
		trip.setProcessName(request.getParameter("processName"));
		trip.setEndDate(RequestUtils.getDate(request, "endDate"));
		trip.setCreateBy(request.getParameter("createBy"));
		trip.setCreateByName(request.getParameter("createByName"));
		trip.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		trip.setApplyDate(RequestUtils.getDate(request, "applyDate"));
		trip.setTransType(request.getParameter("transType"));
		trip.setWfStartDate(RequestUtils.getDate(request, "wfStartDate"));
		trip.setProcessInstanceId(RequestUtils.getLong(request,
				"processInstanceId"));
		trip.setDays(RequestUtils.getDouble(request, "days"));
		trip.setMoney(RequestUtils.getDouble(request, "money"));
		trip.setWfEndDate(RequestUtils.getDate(request, "wfEndDate"));
		trip.setLocked(RequestUtils.getInt(request, "locked"));
		trip.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		trip.setCreateDate(RequestUtils.getDate(request, "createDate"));

		trip.setCreateBy(actorId);

		tripService.save(trip);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=saveTrip")
	public byte[] saveTrip(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Trip trip = new Trip();
		try {
			Tools.populate(trip, params);
			trip.setWfStatus(RequestUtils.getInt(request, "wfStatus"));
			trip.setStartDate(RequestUtils.getDate(request, "startDate"));
			trip.setCause(request.getParameter("cause"));
			trip.setStatus(RequestUtils.getInt(request, "status"));
			trip.setProcessName(request.getParameter("processName"));
			trip.setEndDate(RequestUtils.getDate(request, "endDate"));
			trip.setCreateBy(request.getParameter("createBy"));
			trip.setCreateByName(request.getParameter("createByName"));
			trip.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			trip.setApplyDate(RequestUtils.getDate(request, "applyDate"));
			trip.setTransType(request.getParameter("transType"));
			trip.setWfStartDate(RequestUtils.getDate(request, "wfStartDate"));
			trip.setProcessInstanceId(RequestUtils.getLong(request,
					"processInstanceId"));
			trip.setDays(RequestUtils.getDouble(request, "days"));
			trip.setMoney(RequestUtils.getDouble(request, "money"));
			trip.setWfEndDate(RequestUtils.getDate(request, "wfEndDate"));
			trip.setLocked(RequestUtils.getInt(request, "locked"));
			trip.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			trip.setCreateDate(RequestUtils.getDate(request, "createDate"));
			trip.setCreateBy(actorId);
			this.tripService.save(trip);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(params = "method=update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Trip trip = tripService.getTrip(request.getParameter("id"));

		trip.setWfStatus(RequestUtils.getInt(request, "wfStatus"));
		trip.setStartDate(RequestUtils.getDate(request, "startDate"));
		trip.setCause(request.getParameter("cause"));
		trip.setStatus(RequestUtils.getInt(request, "status"));
		trip.setProcessName(request.getParameter("processName"));
		trip.setEndDate(RequestUtils.getDate(request, "endDate"));
		trip.setCreateBy(request.getParameter("createBy"));
		trip.setCreateByName(request.getParameter("createByName"));
		trip.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		trip.setApplyDate(RequestUtils.getDate(request, "applyDate"));
		trip.setTransType(request.getParameter("transType"));
		trip.setWfStartDate(RequestUtils.getDate(request, "wfStartDate"));
		trip.setProcessInstanceId(RequestUtils.getLong(request,
				"processInstanceId"));
		trip.setDays(RequestUtils.getDouble(request, "days"));
		trip.setMoney(RequestUtils.getDouble(request, "money"));
		trip.setWfEndDate(RequestUtils.getDate(request, "wfEndDate"));
		trip.setLocked(RequestUtils.getInt(request, "locked"));
		trip.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		trip.setCreateDate(RequestUtils.getDate(request, "createDate"));

		tripService.save(trip);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = ParamUtils.getString(params, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Trip trip = tripService.getTrip(x);
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (trip != null
							&& (StringUtils.equals(trip.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						trip.setDeleteFlag(1);
						tripService.save(trip);
					}
				}
			}
		} else if (StringUtils.isNotEmpty(id)) {
			Trip trip = tripService.getTrip(id);
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (trip != null
					&& (StringUtils.equals(trip.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				trip.setDeleteFlag(1);
				tripService.save(trip);
			}
		}
	}

	@ResponseBody
	@RequestMapping(params = "method=detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Trip trip = tripService.getTrip(request.getParameter("id"));

		JSONObject rowJSON = trip.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

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
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (trip != null) {
				if (trip.getStatus() == 0 || trip.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

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

	@RequestMapping(params = "method=view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Trip trip = tripService.getTrip(request.getParameter("id"));
		request.setAttribute("trip", trip);
		JSONObject rowJSON = trip.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

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

	@RequestMapping(params = "method=query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("trip.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/apps/trip/query", modelMap);
	}

	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap,
			TripQuery query) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		// TripQuery query = new TripQuery();
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

		return new ModelAndView("/apps/trip/list", modelMap);
	}

}
