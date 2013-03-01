package com.glaf.apps.trip.action;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.web.struts.DispatchActionSupport;
import com.alibaba.fastjson.*;
 

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.security.*;
import com.glaf.base.utils.*;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;

import com.glaf.apps.trip.model.*;
import com.glaf.apps.trip.query.*;
import com.glaf.apps.trip.service.*;

public class TripBaseAction extends DispatchActionSupport {
	protected static final Log logger = LogFactory.getLog(TripBaseAction.class);

	protected TripService tripService;

	public TripBaseAction() {

	}

	public void setTripService(TripService tripService) {
		this.tripService = tripService;
	}

	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = RequestUtil.getSysUser(request);
		String actorId = user.getAccount();
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		Trip trip = new Trip();
		Tools.populate(trip, params);
		trip.setCreateBy(actorId);

		tripService.save(trip);

		return this.list(mapping, actionForm, request, response);
	}

	public ActionForward saveTrip(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SysUser user = RequestUtil.getSysUser(request);
			String actorId = user.getAccount();
			Map<String, Object> params = RequestUtil.getParameterMap(request);
			params.remove("status");
			params.remove("wfStatus");
			Trip trip = new Trip();
			Tools.populate(trip, params);
			trip.setCreateBy(actorId);

			tripService.save(trip);
			byte[] bytes = ResponseUtil.responseJsonResult(true);
			response.getOutputStream().write(bytes);
			response.flushBuffer();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		byte[] bytes = ResponseUtil.responseJsonResult(false);
		response.getOutputStream().write(bytes);
		response.flushBuffer();
		return null;
	}

	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = RequestUtil.getSysUser(request);
		String actorId = user.getAccount();
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		Trip trip = new Trip();
		Tools.populate(trip, params);
		trip.setCreateBy(actorId);

		tripService.save(trip);

		return this.list(mapping, actionForm, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
					if (trip != null
							&& StringUtils.equals(trip.getCreateBy(),
									user.getAccount())) {
						trip.setDeleteFlag(1);
						tripService.save(trip);
					}
				}
			}
		} else if (StringUtils.isNotEmpty(rowId)) {
			Trip trip = tripService.getTrip(rowId);
			if (trip != null
					&& StringUtils
							.equals(trip.getCreateBy(), user.getAccount())) {
				trip.setDeleteFlag(1);
				tripService.save(trip);
			}
		}

		return this.list(mapping, actionForm, request, response);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = RequestUtil.getSysUser(request);
		String actorId = user.getAccount();
		RequestUtil.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		Trip trip = null;
		if (StringUtils.isNotEmpty(rowId)) {
			trip = tripService.getTrip(rowId);
			request.setAttribute("trip", trip);
			JSONObject rowJSON = trip.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
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

		return mapping.findForward("show_modify");
	}

	public ActionForward view(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RequestUtil.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		Trip trip = null;
		if (StringUtils.isNotEmpty(rowId)) {
			trip = tripService.getTrip(rowId);
			request.setAttribute("trip", trip);
			JSONObject rowJSON = trip.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
		}

		return mapping.findForward("show_view");
	}

	public ActionForward query(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RequestUtil.setRequestParameterToAttribute(request);

		return mapping.findForward("show_query");
	}

	public ActionForward json(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		response.getOutputStream().write(result.toString().getBytes("UTF-8"));
		return null;
	}

	public ActionForward list(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		return mapping.findForward("show_list");
	}

}
