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
 
import com.glaf.core.util.JsonUtils;
import com.glaf.base.config.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.security.*;
import com.glaf.base.utils.*;
 
import com.glaf.apps.trip.model.*;
import com.glaf.apps.trip.query.*;
import com.glaf.apps.trip.service.*;


public class TripBaseController {
	protected static final Log logger = LogFactory
			.getLog(TripBaseController.class);

        @javax.annotation.Resource
	protected TripService tripService;

	public TripBaseController() {

	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap, Trip trip) {
		SysUser user = RequestUtil.getSysUser(request);
		String actorId =  user.getAccount();
		Map<String, Object> params = RequestUtil.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
		
		trip.setCreateBy(actorId);
         
		tripService.save(trip);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveTrip")
	public byte[] saveTrip(HttpServletRequest request ) { 
	        Map<String, Object> params = RequestUtil.getParameterMap(request);
		Trip trip = new Trip();
		try {
		    Tools.populate(trip, params);
		    this.tripService.save(trip);

		    return ResponseUtil.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtil.responseJsonResult(false);
	}

        @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap, Trip trip) {
		SysUser user = RequestUtil.getSysUser(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
		tripService.save(trip);   

		return this.list(request, modelMap);
	}

        @RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		SysUser user = RequestUtil.getSysUser(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		String rowIds = request.getParameter("rowIds");
		if (StringUtils.isNotEmpty(rowIds)) {
			StringTokenizer token = new StringTokenizer(rowIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Trip trip = tripService.getTrip(x);
					if (trip != null && StringUtils.equals(trip.getCreateBy(), user.getAccount())) {
						trip.setDeleteFlag(1);
						tripService.save(trip);
					}
				}
			}
		} else if (StringUtils.isNotEmpty(rowId)) {
			Trip trip = tripService
					.getTrip(rowId);
			if (trip != null && StringUtils.equals(trip.getCreateBy(), user.getAccount())) {
				trip.setDeleteFlag(1);
				tripService.save(trip);
			}
		}

		return this.list(request, modelMap);
	}

    

        @RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		SysUser user = RequestUtil.getSysUser(request);
		String actorId =  user.getAccount();
		RequestUtil.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		Trip trip = null;
		if (StringUtils.isNotEmpty(rowId)) {
			trip = tripService.getTrip(rowId);
			request.setAttribute("trip", trip);
			JSONObject rowJSON =  trip.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
		}

                boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {
			 
		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (trip != null) {
				if (trip.getStatus() == 0
						|| trip.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		 
		request.setAttribute("canUpdate",  canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = SystemConfig.getString("trip.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/trip/edit", modelMap);
	}

        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		Trip trip = null;
		if (StringUtils.isNotEmpty(rowId)) {
			trip = tripService.getTrip(rowId);
			request.setAttribute("trip", trip);
			JSONObject rowJSON =  trip.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = SystemConfig.getString("trip.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/trip/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = SystemConfig.getString("trip.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/apps/trip/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		TripQuery query = new TripQuery();
		Tools.populate(query, params);

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

			Map<String, SysUser> userMap = IdentityFactory.getUserMap();
			List<Trip> list = tripService.getTripsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (Trip trip : list) {
					JSONObject rowJSON = trip.toJsonObject();
					rowJSON.put("id", trip.getId());
					rowJSON.put("tripId", trip.getId());

 					rowsJSON.add(rowJSON);
				}

			}
		}
		return result.toString().getBytes("UTF-8");
	}

        @RequestMapping 
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtil.encodeString(x_complex_query);
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
