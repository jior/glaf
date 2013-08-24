package com.glaf.cms.fullcalendar.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.cms.fullcalendar.model.*;
import com.glaf.cms.fullcalendar.query.*;
import com.glaf.cms.fullcalendar.service.*;

@Controller("/cms/fullCalendar/")
@RequestMapping("/cms/fullCalendar")
public class FullCalendarController {
	protected static final Log logger = LogFactory
			.getLog(FullCalendarController.class);

	protected FullCalendarService fullCalendarService;

	public FullCalendarController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					fullCalendarService.deleteById(Long.valueOf(x));
				}
			}
		} else if (id != null) {
			fullCalendarService.deleteById(Long.valueOf(id));
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		FullCalendar fullCalendar = fullCalendarService
				.getFullCalendar(RequestUtils.getLong(request, "id"));

		JSONObject rowJSON = fullCalendar.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		FullCalendar fullCalendar = fullCalendarService
				.getFullCalendar(RequestUtils.getLong(request, "id"));

		request.setAttribute("start", RequestUtils.getDate(request, "start"));
		request.setAttribute("end", RequestUtils.getDate(request, "end"));
		Integer status = 1;
		if (!RequestUtils.getBoolean(request, "allDay")) {
			status = 0;
		}
		request.setAttribute("status", status);
		
		if (fullCalendar != null) {
			request.setAttribute("fullCalendar", fullCalendar);
			request.setAttribute("status", fullCalendar.getStatus());
			JSONObject rowJSON = fullCalendar.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (fullCalendar != null) {
				if (fullCalendar.getStatus() == 0
						|| fullCalendar.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("fullCalendar.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/cms/fullCalendar/edit", modelMap);
	}

	@RequestMapping("/getJson")
	@ResponseBody
	public byte[] getJson(HttpServletRequest request, ModelMap modelMap) {
		try {
			LoginContext loginContext = RequestUtils.getLoginContext(request);
			Map<String, Object> params = RequestUtils.getParameterMap(request);
			FullCalendarQuery query = new FullCalendarQuery();
			Tools.populate(query, params);
			query.deleteFlag(0);
			query.setActorId(loginContext.getActorId());
			loginContext.setDeptId(0L);
			query.setLoginContext(loginContext);
			query.createBy(loginContext.getActorId());

			List<FullCalendar> list = fullCalendarService.list(query);
			Map<String, User> userMap = IdentityFactory.getUserMap();

			JSONArray rowsJSON = new JSONArray();
			if (list != null && !list.isEmpty()) {
				for (FullCalendar fullCalendar : list) {
					JSONObject rowJSON = fullCalendar.toJsonObject();
					User user = userMap.get(fullCalendar.getCreateBy());
					if (user != null) {
						rowJSON.put("createByName", user.getName());
					}
					rowJSON.put("id", fullCalendar.getId());
					rowJSON.put("allDay", fullCalendar.getStatus() == 0 ? false
							: true);
					rowsJSON.add(rowJSON);
				}
			} else {

			}
			return rowsJSON.toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FullCalendarQuery query = new FullCalendarQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		/**
		 * 此处业务逻辑需自行调整
		 */
		int isOther = ParamUtils.getInt(params, "isOther");
		if (isOther == 0) {
			query.createBy(loginContext.getActorId());
		} else {
			query.shareFlag(1);
			query.setCreateByNot(loginContext.getActorId());
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
		int total = fullCalendarService
				.getFullCalendarCountByQueryCriteria(query);
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

			List<FullCalendar> list = fullCalendarService
					.getFullCalendarsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				Map<String, User> userMap = IdentityFactory.getUserMap();
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (FullCalendar fullCalendar : list) {
					JSONObject rowJSON = fullCalendar.toJsonObject();
					User user = userMap.get(fullCalendar.getCreateBy());
					if (user != null) {
						rowJSON.put("createByName", user.getName());
					}
					rowJSON.put("id", fullCalendar.getId());
					rowJSON.put("fullCalendarId", fullCalendar.getId());
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

		return new ModelAndView("/cms/fullCalendar/list", modelMap);
	}

	@RequestMapping("/list2")
	public ModelAndView list2(HttpServletRequest request, ModelMap modelMap) {
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

		return new ModelAndView("/cms/fullCalendar/list2", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("fullCalendar.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/cms/fullCalendar/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FullCalendar fullCalendar = new FullCalendar();
		Tools.populate(fullCalendar, params);

		fullCalendar.setTitle(request.getParameter("title"));
		fullCalendar.setContent(request.getParameter("content"));
		fullCalendar.setAddress(request.getParameter("address"));
		fullCalendar.setRemark(request.getParameter("remark"));
		fullCalendar.setShareFlag(RequestUtils.getInt(request, "shareFlag"));
		fullCalendar.setStatus(RequestUtils.getInt(request, "status"));
		fullCalendar.setDateStart(RequestUtils.getDate(request, "dateStart"));
		fullCalendar.setDateEnd(RequestUtils.getDate(request, "dateEnd"));
		fullCalendar.setExt1(request.getParameter("ext1"));
		fullCalendar.setExt2(request.getParameter("ext2"));
		fullCalendar.setCreateBy(request.getParameter("createBy"));
		fullCalendar.setCreateDate(RequestUtils.getDate(request, "createDate"));
		fullCalendar.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		fullCalendar.setUpdateBy(request.getParameter("updateBy"));

		fullCalendar.setCreateBy(actorId);

		fullCalendarService.save(fullCalendar);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFullCalendar")
	public byte[] saveFullCalendar(HttpServletRequest request)
			throws IOException {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FullCalendar fullCalendar = new FullCalendar();
		JSONObject jsonObject = new JSONObject();
		try {
			Tools.populate(fullCalendar, params);
			fullCalendar.setTitle(request.getParameter("title"));
			fullCalendar.setContent(request.getParameter("content"));
			fullCalendar.setAddress(request.getParameter("address"));
			fullCalendar.setRemark(request.getParameter("remark"));
			fullCalendar
					.setShareFlag(RequestUtils.getInt(request, "shareFlag"));
			fullCalendar.setStatus(RequestUtils.getInt(request, "status"));
			fullCalendar.setDateStart(RequestUtils
					.getDate(request, "dateStart"));
			fullCalendar.setDateEnd(RequestUtils.getDate(request, "dateEnd"));
			fullCalendar.setExt1(request.getParameter("ext1"));
			fullCalendar.setExt2(request.getParameter("ext2"));
			fullCalendar.setCreateBy(actorId);
			fullCalendar.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			this.fullCalendarService.save(fullCalendar);

			jsonObject.put("id", fullCalendar.getId());
			jsonObject.put("title", request.getParameter("title"));
			jsonObject.put(
					"start",
					DateUtils.getDateTime("yyyy-MM-dd HH:mm",
							RequestUtils.getDate(request, "dateStart")));
			jsonObject.put(
					"end",
					DateUtils.getDateTime("yyyy-MM-dd HH:mm",
							RequestUtils.getDate(request, "dateEnd")));

			return jsonObject.toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return null;
	}

	@javax.annotation.Resource
	public void setFullCalendarService(FullCalendarService fullCalendarService) {
		this.fullCalendarService = fullCalendarService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FullCalendar fullCalendar = fullCalendarService
				.getFullCalendar(RequestUtils.getLong(request, "id"));

		fullCalendar.setTitle(request.getParameter("title"));
		fullCalendar.setContent(request.getParameter("content"));
		fullCalendar.setAddress(request.getParameter("address"));
		fullCalendar.setRemark(request.getParameter("remark"));
		fullCalendar.setShareFlag(RequestUtils.getInt(request, "shareFlag"));
		fullCalendar.setStatus(RequestUtils.getInt(request, "status"));
		fullCalendar.setDateStart(RequestUtils.getDate(request, "dateStart"));
		fullCalendar.setDateEnd(RequestUtils.getDate(request, "dateEnd"));
		fullCalendar.setExt1(request.getParameter("ext1"));
		fullCalendar.setExt2(request.getParameter("ext2"));
		fullCalendar.setCreateBy(request.getParameter("createBy"));
		fullCalendar.setCreateDate(RequestUtils.getDate(request, "createDate"));
		fullCalendar.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		fullCalendar.setUpdateBy(request.getParameter("updateBy"));

		fullCalendarService.save(fullCalendar);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		FullCalendar fullCalendar = fullCalendarService
				.getFullCalendar(RequestUtils.getLong(request, "id"));
		request.setAttribute("fullCalendar", fullCalendar);
		JSONObject rowJSON = fullCalendar.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("fullCalendar.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/cms/fullCalendar/view");
	}

}
