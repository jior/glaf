package com.glaf.core.web.rest;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TablePage;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;

@Controller("/rs/system/table")
@Path("/rs/system/table")
public class MxSystemTableResource {

	protected static Configuration conf = BaseConfiguration.create();

	protected static final Log logger = LogFactory
			.getLog(MxSystemTableResource.class);

	protected ITablePageService tablePageService;

	protected ITableDefinitionService tableDefinitionService;

	@POST
	@Path("/updateMetaInfo")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] updateMetaInfo(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) throws IOException {
		String tables = request.getParameter("tables");

		if (StringUtils.isNotEmpty(tables)) {
			TableDefinitionQuery query = new TableDefinitionQuery();

			List<TableDefinition> list = tableDefinitionService
					.getTableColumnsCount(query);
			Map<String, TableDefinition> tableMap = new java.util.HashMap<String, TableDefinition>();
			if (list != null && !list.isEmpty()) {
				for (TableDefinition t : list) {
					tableMap.put(t.getTableName().toLowerCase(), t);
				}
				list.clear();
			}

			List<String> list2 = StringTools.split(tables);
			for (String tableName : list2) {
				String tbl = tableName;
				if (DBUtils.isTemoraryTable(tableName)) {
					continue;
				}
				if (tableMap.get(tableName) == null) {
					try {
						List<ColumnDefinition> columns = DBUtils
								.getColumnDefinitions(tbl);
						tableDefinitionService.saveSystemTable(tableName,
								columns);
						logger.debug(tableName + " save ok");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					TableDefinition table = tableMap.get(tableName);
					boolean success = false;
					int retry = 0;
					while (retry < 2 && !success) {
						try {
							retry++;
							List<ColumnDefinition> columns = DBUtils
									.getColumnDefinitions(tbl);
							if (table.getColumnQty() != columns.size()) {
								tableDefinitionService.saveSystemTable(
										tableName, columns);
								logger.debug(tableName + " save ok");
							} else {
								logger.debug(tableName + " check ok");
							}
							success = true;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}

			return ResponseUtils.responseJsonResult(true);
		}

		return ResponseUtils.responseJsonResult(false);
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

		String tableName = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = RequestUtils.decodeString(tableName);
		} else {
			tableName = request.getParameter("tableName");
		}

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
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

		tableName = tableName.toUpperCase();

		Collection<String> rejects = new java.util.ArrayList<String>();
		rejects.add("FILEATT");
		rejects.add("SYS_LOB");
		rejects.add("SYS_MAIL_FILE");
		rejects.add("Attachment");

		if (conf.get("table.rejects") != null) {
			String str = conf.get("table.rejects");
			List<String> list = StringTools.split(str);
			for (String t : list) {
				rejects.add(t.toUpperCase());
			}
		}

		TablePage tablePage = null;
		TablePageQuery tablePageQuery = new TablePageQuery();
		tablePageQuery.tableName(tableName);
		tablePageQuery.setFirstResult(start);
		tablePageQuery.setMaxResults(limit);

		if (orderName != null && orderName.trim().length() > 0) {
			if (StringUtils.equals(order, "asc")) {
				tablePageQuery.orderAsc(orderName);
			} else {
				tablePageQuery.orderDesc(orderName);
			}
		}

		if (!rejects.contains(tableName)) {
			try {
				long startTs = System.currentTimeMillis();
				tablePage = tablePageService.getTablePage(tablePageQuery,
						start, limit);
				long time = System.currentTimeMillis() - startTs;
				logger.debug("查询完成,记录总数:" + tablePage.getTotal() + " 用时(毫秒):"
						+ time);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}

		JSONObject responseJSON = new JSONObject();
		responseJSON.put("total", 0);
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
							rowJSON.put(name.toLowerCase(),
									DateUtils.getDate(date));
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
							rowJSON.put(name.toLowerCase(), value);
						}
					}
				}
				rowJSON.put("startIndex", ++start);
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

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

}
