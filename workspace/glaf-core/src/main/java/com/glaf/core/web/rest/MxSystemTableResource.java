package com.glaf.core.web.rest;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TablePage;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;

@Controller("/rs/system/table")
@Path("/rs/system/table")
public class MxSystemTableResource {

	protected static final Log logger = LogFactory
			.getLog(MxSystemTableResource.class);

	protected ITablePageService tablePageService;

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@GET
	@POST
	@Path("/resultList")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] resultList(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);

		String tableName = ParamUtils.getString(params, "tableName_enc");
		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = RequestUtils.decodeString(tableName);
		}

		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;
		if ("easyui".equals(gridType)) {
			int pageNo = ParamUtils.getInt(params, "page");
			limit = ParamUtils.getInt(params, "rows");
			start = (pageNo - 1) * limit;
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "order");
		} else if ("extjs".equals(gridType)) {
			start = ParamUtils.getInt(params, "start");
			limit = ParamUtils.getInt(params, "limit");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		} else if ("yui".equals(gridType)) {
			start = ParamUtils.getInt(params, "startIndex");
			limit = ParamUtils.getInt(params, "results");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		}

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0 || limit > Paging.MAX_RECORD_COUNT) {
			limit = Paging.MAX_RECORD_COUNT;
		}

		TablePage tablePage = null;
		TablePageQuery tablePageQuery = new TablePageQuery();
		tablePageQuery.tableName(tableName);
		tablePageQuery.setFirstResult(start);
		tablePageQuery.setMaxResults(limit);

		if (StringUtils.equals(order, "asc")) {
			tablePageQuery.orderAsc(orderName);
		} else {
			tablePageQuery.orderDesc(orderName);
		}

		try {
			long startTs = System.currentTimeMillis();
			tablePage = tablePageService.getTablePage(tablePageQuery, start,
					limit);
			long time = System.currentTimeMillis() - startTs;
			logger.debug("查询完成,记录总数:" + tablePage.getTotal() + " 用时(毫秒):"
					+ time);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		JSONObject responseJSON = new JSONObject();

		JSONArray rowsJSON = new JSONArray();
		if (tablePage != null && tablePage.getRows() != null) {
			responseJSON.put("total", tablePage.getTotal());
			for (Map<String, Object> dataMap : tablePage.getRows()) {
				JSONObject rowJSON = new JSONObject();
				Iterator<String> iterator = dataMap.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					if (StringUtils.equalsIgnoreCase("password", name)
							|| StringUtils.equalsIgnoreCase("pwd", name)) {
						continue;
					}
					Object value = dataMap.get(name);
					if (value != null) {
						if (value instanceof Date) {
							Date date = (Date) value;
							rowJSON.put(name, DateUtils.getDate(date));
						} else if (value instanceof byte[]) {
							rowJSON.put(name, "二进制流");
						} else if (value instanceof java.io.InputStream) {
							rowJSON.put(name, "二进制流");
						} else if (value instanceof java.sql.Blob) {
							rowJSON.put(name, "二进制流");
						} else if (value instanceof java.sql.Clob) {
							rowJSON.put(name, "长文本");
						} else {
							rowJSON.put(name, value);
						}
					}
				}
				rowsJSON.add(rowJSON);
				dataMap.clear();
			}
		}

		if ("yui".equals(gridType)) {
			responseJSON.put("records", rowsJSON);
		} else {
			responseJSON.put("rows", rowsJSON);
		}

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

}
