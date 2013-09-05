package com.glaf.dts.web.springmvc;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.*;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.dts.domain.*;
import com.glaf.dts.query.*;
import com.glaf.dts.service.*;
import com.glaf.dts.util.XmlReader;

@Controller("/dts/dataTransfer")
@RequestMapping("/dts/dataTransfer")
public class MxDataTransferController {
	protected static final Log logger = LogFactory
			.getLog(MxDataTransferController.class);

	protected IDataTransferService dataTransferService;

	public MxDataTransferController() {

	}

	@RequestMapping("/columns")
	@ResponseBody
	public byte[] columns(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		String tableName = request.getParameter("tableName");
		JSONObject result = new JSONObject();
		List<ColumnDefinition> list = dataTransferService.getColumns(tableName);

		if (list != null && !list.isEmpty()) {
			int start = 0;
			JSONArray rowsJSON = new JSONArray();

			for (ColumnDefinition c : list) {
				JSONObject rowJSON = c.toJsonObject();
				rowJSON.put("id", c.getId());
				rowJSON.put("columnId", c.getId());
				rowJSON.put("columnDefinitionId", c.getId());
				rowJSON.put("startIndex", ++start);
				rowsJSON.add(rowJSON);
			}

			result.put("rows", rowsJSON);
			result.put("total", list.size());

		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", 0);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DataTransfer dataTransfer = dataTransferService
							.getDataTransfer(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */

					if (dataTransfer != null
							&& (StringUtils.equals(dataTransfer.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						dataTransfer.setDeleteFlag(1);
						dataTransferService.save(dataTransfer);
					}
				}
			}
		} else if (id != null) {
			DataTransfer dataTransfer = dataTransferService
					.getDataTransfer(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */

			if (dataTransfer != null
					&& (StringUtils.equals(dataTransfer.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				dataTransfer.setDeleteFlag(1);
				dataTransferService.save(dataTransfer);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/deleteColumns")
	public byte[] deleteColumns(HttpServletRequest request, ModelMap modelMap) {
		String id = RequestUtils.getString(request, "columnId");
		String ids = request.getParameter("columnIds");
		try {
			if (StringUtils.isNotEmpty(ids)) {
				List<String> rowIds = StringTools.split(ids);
				dataTransferService.deleteByColumnIds(rowIds);
			} else if (id != null) {
				dataTransferService.deleteByColumnId(id);
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/deploy", method = RequestMethod.POST)
	public ModelAndView deploy(HttpServletRequest request, ModelMap modelMap) {
		try {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				if (mFile.getOriginalFilename() != null
						&& mFile.getSize() > 0
						&& mFile.getOriginalFilename().toLowerCase()
								.endsWith(".xml")) {
					XmlReader reader = new XmlReader();
					DataTransfer dataTransfer = reader.read(mFile
							.getInputStream());
					Long nodeId = RequestUtils.getLong(request, "nodeId");
					dataTransfer.setNodeId(nodeId);
					dataTransferService.save(dataTransfer);
					modelMap.put("dataTransfer", dataTransfer);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
		return new ModelAndView("/bi/dts/transfer/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		DataTransfer dataTransfer = dataTransferService.getDataTransfer(request
				.getParameter("id"));
		JSONObject rowJSON = dataTransfer.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		DataTransfer dataTransfer = dataTransferService.getDataTransfer(request
				.getParameter("id"));
		if (dataTransfer != null) {
			request.setAttribute("dataTransfer", dataTransfer);
			JSONObject rowJSON = dataTransfer.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (dataTransfer != null) {
				canUpdate = true;
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataTransfer.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/dts/transfer/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataTransferQuery query = new DataTransferQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		
		Long nodeId = RequestUtils.getLong(request, "nodeId");
		if (nodeId != null && nodeId > 0) {
			query.nodeId(nodeId);
		}
		
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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = dataTransferService
				.getDataTransferCountByQueryCriteria(query);
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

			List<DataTransfer> list = dataTransferService
					.getDataTransfersByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataTransfer dataTransfer : list) {
					JSONObject rowJSON = dataTransfer.toJsonObject();
					rowJSON.put("id", dataTransfer.getId());
					rowJSON.put("dataTransferId", dataTransfer.getId());
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

		return new ModelAndView("/bi/dts/transfer/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataTransfer.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/dts/transfer/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataTransfer dataTransfer = new DataTransfer();
		Tools.populate(dataTransfer, params);

		dataTransfer.setTableName(request.getParameter("tableName"));
		dataTransfer
				.setParentTableName(request.getParameter("parentTableName"));
		dataTransfer.setPackageName(request.getParameter("packageName"));
		dataTransfer.setEntityName(request.getParameter("entityName"));
		dataTransfer.setClassName(request.getParameter("className"));
		dataTransfer.setTitle(request.getParameter("title"));
		dataTransfer.setEnglishTitle(request.getParameter("englishTitle"));
		dataTransfer.setPrimaryKey(request.getParameter("primaryKey"));
		dataTransfer.setFilePrefix(request.getParameter("filePrefix"));
		dataTransfer.setParseType(request.getParameter("parseType"));
		dataTransfer.setParseClass(request.getParameter("parseClass"));
		dataTransfer.setSplit(request.getParameter("split"));
		dataTransfer.setBatchSize(RequestUtils.getInt(request, "batchSize"));
		dataTransfer.setInsertOnly(request.getParameter("insertOnly"));
		dataTransfer.setStartRow(RequestUtils.getInt(request, "startRow"));
		dataTransfer.setStopWord(request.getParameter("stopWord"));
		dataTransfer
				.setStopSkipRow(RequestUtils.getInt(request, "stopSkipRow"));
		dataTransfer
				.setAggregationKeys(request.getParameter("aggregationKeys"));
		dataTransfer.setQueryIds(request.getParameter("queryIds"));
		dataTransfer.setTemporaryFlag(request.getParameter("temporaryFlag"));
		dataTransfer.setDeleteFetch(request.getParameter("deleteFetch"));
		dataTransfer.setDescription(request.getParameter("description"));
		dataTransfer.setType(request.getParameter("type"));
		dataTransfer.setNodeId(RequestUtils.getLong(request, "nodeId"));
		dataTransfer.setLocked(RequestUtils.getInt(request, "locked"));
		dataTransfer.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		dataTransfer.setSystemFlag(request.getParameter("systemFlag"));
		dataTransfer.setCreateTime(RequestUtils.getDate(request, "createTime"));
		dataTransfer.setCreateBy(request.getParameter("createBy"));

		dataTransfer.setCreateBy(actorId);

		dataTransferService.save(dataTransfer);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveColumn")
	public byte[] saveColumn(HttpServletRequest request) {
		String tableName = request.getParameter("tableName");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ColumnDefinition columnDefinition = new ColumnDefinition();
		try {
			Tools.populate(columnDefinition, params);
			this.dataTransferService.saveColumn(tableName, columnDefinition);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveDataTransfer")
	public byte[] saveDataTransfer(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataTransfer dataTransfer = new DataTransfer();
		try {
			Tools.populate(dataTransfer, params);
			dataTransfer.setTableName(request.getParameter("tableName"));
			dataTransfer.setParentTableName(request
					.getParameter("parentTableName"));
			dataTransfer.setPackageName(request.getParameter("packageName"));
			dataTransfer.setEntityName(request.getParameter("entityName"));
			dataTransfer.setClassName(request.getParameter("className"));
			dataTransfer.setTitle(request.getParameter("title"));
			dataTransfer.setEnglishTitle(request.getParameter("englishTitle"));
			dataTransfer.setPrimaryKey(request.getParameter("primaryKey"));
			dataTransfer.setFilePrefix(request.getParameter("filePrefix"));
			dataTransfer.setParseType(request.getParameter("parseType"));
			dataTransfer.setParseClass(request.getParameter("parseClass"));
			dataTransfer.setSplit(request.getParameter("split"));
			dataTransfer
					.setBatchSize(RequestUtils.getInt(request, "batchSize"));
			dataTransfer.setInsertOnly(request.getParameter("insertOnly"));
			dataTransfer.setStartRow(RequestUtils.getInt(request, "startRow"));
			dataTransfer.setStopWord(request.getParameter("stopWord"));
			dataTransfer.setStopSkipRow(RequestUtils.getInt(request,
					"stopSkipRow"));
			dataTransfer.setAggregationKeys(request
					.getParameter("aggregationKeys"));
			dataTransfer.setQueryIds(request.getParameter("queryIds"));
			dataTransfer
					.setTemporaryFlag(request.getParameter("temporaryFlag"));
			dataTransfer.setDeleteFetch(request.getParameter("deleteFetch"));
			dataTransfer.setDescription(request.getParameter("description"));
			dataTransfer.setType(request.getParameter("type"));
			dataTransfer.setNodeId(RequestUtils.getLong(request, "nodeId"));
			dataTransfer.setLocked(RequestUtils.getInt(request, "locked"));
			dataTransfer.setDeleteFlag(RequestUtils.getInt(request,
					"deleteFlag"));
			dataTransfer.setSystemFlag(request.getParameter("systemFlag"));
			dataTransfer.setCreateTime(RequestUtils.getDate(request,
					"createTime"));
			dataTransfer.setCreateBy(request.getParameter("createBy"));
			dataTransfer.setCreateBy(actorId);
			this.dataTransferService.save(dataTransfer);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDataTransferService(IDataTransferService dataTransferService) {
		this.dataTransferService = dataTransferService;
	}

	@RequestMapping("/showDeploy")
	public ModelAndView showDeploy(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties
				.getString("dts_transfer_deploy.showDeploy");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/bi/dts/transfer/showDeploy");
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataTransfer dataTransfer = dataTransferService.getDataTransfer(request
				.getParameter("id"));

		dataTransfer.setTableName(request.getParameter("tableName"));
		dataTransfer
				.setParentTableName(request.getParameter("parentTableName"));
		dataTransfer.setPackageName(request.getParameter("packageName"));
		dataTransfer.setEntityName(request.getParameter("entityName"));
		dataTransfer.setClassName(request.getParameter("className"));
		dataTransfer.setTitle(request.getParameter("title"));
		dataTransfer.setEnglishTitle(request.getParameter("englishTitle"));
		dataTransfer.setPrimaryKey(request.getParameter("primaryKey"));
		dataTransfer.setFilePrefix(request.getParameter("filePrefix"));
		dataTransfer.setParseType(request.getParameter("parseType"));
		dataTransfer.setParseClass(request.getParameter("parseClass"));
		dataTransfer.setSplit(request.getParameter("split"));
		dataTransfer.setBatchSize(RequestUtils.getInt(request, "batchSize"));
		dataTransfer.setInsertOnly(request.getParameter("insertOnly"));
		dataTransfer.setStartRow(RequestUtils.getInt(request, "startRow"));
		dataTransfer.setStopWord(request.getParameter("stopWord"));
		dataTransfer
				.setStopSkipRow(RequestUtils.getInt(request, "stopSkipRow"));
		dataTransfer
				.setAggregationKeys(request.getParameter("aggregationKeys"));
		dataTransfer.setQueryIds(request.getParameter("queryIds"));
		dataTransfer.setTemporaryFlag(request.getParameter("temporaryFlag"));
		dataTransfer.setDeleteFetch(request.getParameter("deleteFetch"));
		dataTransfer.setDescription(request.getParameter("description"));
		dataTransfer.setType(request.getParameter("type"));
		dataTransfer.setNodeId(RequestUtils.getLong(request, "nodeId"));
		dataTransfer.setLocked(RequestUtils.getInt(request, "locked"));
		dataTransfer.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		dataTransfer.setSystemFlag(request.getParameter("systemFlag"));
		dataTransfer.setCreateTime(RequestUtils.getDate(request, "createTime"));
		dataTransfer.setCreateBy(request.getParameter("createBy"));

		dataTransferService.save(dataTransfer);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		DataTransfer dataTransfer = dataTransferService.getDataTransfer(request
				.getParameter("id"));
		request.setAttribute("dataTransfer", dataTransfer);
		JSONObject rowJSON = dataTransfer.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataTransfer.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/bi/dts/transfer/view");
	}

}
