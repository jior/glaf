package com.glaf.apps.trip.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.security.*;
import com.glaf.base.utils.*;
import com.glaf.core.util.PageResult;

import com.glaf.apps.trip.model.Trip;
import com.glaf.apps.trip.query.TripQuery;
import com.glaf.apps.trip.service.TripService;

@Controller
@Path("/rs/apps/trip")
public class TripResourceRest {
	protected  static final Log logger = LogFactory.getLog(TripResourceRest.class);

	@javax.annotation.Resource
	protected TripService tripService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("rowIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				tripService.deleteByIds(ids);
			}
		}
		return ResponseUtil.responseJsonResult(true);
	}

	@POST
	@Path("/delete/{rowIds}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@PathParam("rowIds") String rowIds,
			@Context HttpServletRequest request) throws IOException {
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				tripService.deleteByIds(ids);
			}
		}
		return ResponseUtil.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		String tripId = request.getParameter("tripId");
		if (StringUtils.isEmpty(tripId)) {
                      tripId = request.getParameter("id");
		}
		tripService.deleteById(tripId);
		return ResponseUtil.responseJsonResult(true);
	}

	@POST
	@Path("/delete/{tripId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@PathParam("tripId") String tripId,
			@Context HttpServletRequest request) throws IOException {
		tripService.deleteById(tripId);
		return ResponseUtil.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
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

	@POST
	@Path("/saveTrip")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveTrip(@Context HttpServletRequest request) {
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

	public void setTripService(TripService tripService) {
		this.tripService = tripService;
	}

	@GET
	@POST
	@Path("/view/{tripId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("tripId") String tripId,
			@Context HttpServletRequest request) throws IOException {
		Trip trip = null;
		if (StringUtils.isNotEmpty(tripId)) {
			trip = tripService.getTrip(tripId);
		}
		JSONObject result = new JSONObject();
		if (trip != null) {
		    result =  trip.toJsonObject();
		    Map<String, SysUser> userMap = IdentityFactory.getUserMap();
		    result.put("id", trip.getId());
		    result.put("tripId", trip.getId());
		}
		return result.toString().getBytes("UTF-8");
	}
}
