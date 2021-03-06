package com.glaf.apps.trip.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.util.*;

import com.glaf.apps.trip.model.Trip;
import com.glaf.apps.trip.query.TripQuery;
import com.glaf.apps.trip.service.TripService;

@Controller
@Path("/rs/apps/trip")
public class TripResourceRest {
	protected static final Log logger = LogFactory
			.getLog(TripResourceRest.class);

	protected TripService tripService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				tripService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		tripService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TripQuery query = new TripQuery();
		Tools.populate(query, params);

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

	@POST
	@Path("/saveTrip")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveTrip(@Context HttpServletRequest request) {
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

			this.tripService.save(trip);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setTripService(TripService tripService) {
		this.tripService = tripService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		Trip trip = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			trip = tripService.getTrip(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (trip != null) {
			result = trip.toJsonObject();

			result.put("id", trip.getId());
			result.put("tripId", trip.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
