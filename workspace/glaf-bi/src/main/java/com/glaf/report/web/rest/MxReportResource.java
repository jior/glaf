/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.report.web.rest;

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
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QuartzUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.dts.input.TextFileImporter;
import com.glaf.dts.transform.MxTransformManager;
import com.glaf.report.domain.Report;
import com.glaf.report.gen.ReportFactory;
import com.glaf.report.mail.ReportMailSender;
import com.glaf.report.query.ReportQuery;
import com.glaf.report.service.IReportService;

@Controller("/rs/bi/report")
@Path("/rs/bi/report")
public class MxReportResource {
	protected static final Log logger = LogFactory
			.getLog(MxReportResource.class);

	protected IReportService reportService;

	protected ITableDefinitionService tableDefinitionService;

	protected ITreeModelService treeModelService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("reportIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				reportService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete/{reportIds}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@PathParam("reportIds") String rowIds,
			@Context HttpServletRequest request) throws IOException {
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				reportService.deleteByIds(ids);
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
		String reportId = request.getParameter("reportId");
		if (StringUtils.isEmpty(reportId)) {
			reportId = request.getParameter("id");
		}
		reportService.deleteById(reportId);
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete/{reportId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@PathParam("reportId") String reportId,
			@Context HttpServletRequest request) throws IOException {
		reportService.deleteById(reportId);
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/importData")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] importData(@Context HttpServletRequest request)
			throws IOException {
		try {
			TextFileImporter imp = new TextFileImporter();
			imp.importData();
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@POST
	@Path("/importDataAndFetch")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] importDataAndFetch(@Context HttpServletRequest request)
			throws IOException {
		try {
			TextFileImporter imp = new TextFileImporter();
			imp.importData();
			TableDefinitionQuery query = new TableDefinitionQuery();
			List<TableDefinition> tables = tableDefinitionService.list(query);
			if (tables != null && !tables.isEmpty()) {
				Collections.sort(tables);
				MxTransformManager manager = new MxTransformManager();
				for (TableDefinition tableDefinition : tables) {
					try {
						manager.transformTable(tableDefinition.getTableName());
						Thread.sleep(200);
					} catch (Exception ex) {
						ex.printStackTrace();
						return ResponseUtils.responseJsonResult(false);
					}
				}
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportQuery query = new ReportQuery();
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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = reportService.getReportCountByQueryCriteria(query);
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

			List<Report> list = reportService.getReportsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Report report : list) {
					JSONObject rowJSON = report.toJsonObject();
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		}
		return result.toString().getBytes("UTF-8");
	}

	@POST
	@Path("/saveReport")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveReport(@Context HttpServletRequest request) {
		String reportId = request.getParameter("reportId");
		if (StringUtils.isEmpty(reportId)) {
			reportId = request.getParameter("id");
		}
		Report report = null;
		if (StringUtils.isNotEmpty(reportId)) {
			report = reportService.getReport(reportId);
		}

		if (report == null) {
			report = new Report();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		Tools.populate(report, params);

		report.setChartIds(request.getParameter("chartIds"));
		report.setQueryIds(request.getParameter("queryIds"));

		try {
			this.reportService.save(report);

			String taskId = report.getId();

			if (StringUtils.equals(report.getEnableFlag(), "1")) {
				QuartzUtils.stop(taskId);
				QuartzUtils.restart(taskId);
			} else {
				QuartzUtils.stop(taskId);
			}
			return ResponseUtils.responseJsonResult(true, "保存成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false, "保存失败！");
	}

	@GET
	@POST
	@Path("/sendMail")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] sendMail(@Context HttpServletRequest request)
			throws IOException {
		JSONObject result = new JSONObject();
		Report report = null;
		String reportId = request.getParameter("reportId");
		if (StringUtils.isNotEmpty(reportId)) {
			report = reportService.getReport(reportId);
			if (report != null
					&& StringUtils.isNotEmpty(report.getMailRecipient())) {
				ReportMailSender mailSender = new ReportMailSender();
				try {
					mailSender.sendMail(report.getId());
					result.put("message", "邮件发送成功！");
				} catch (Exception ex) {
					ex.printStackTrace();
					result.put("message", "邮件发送失败：" + ex.getMessage());
				}
			}
		}

		return result.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/sendMailAllInOne")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] sendMailAllInOne(@Context HttpServletRequest request)
			throws IOException {
		JSONObject result = new JSONObject();
		ReportMailSender mailSender = new ReportMailSender();
		try {
			/**
			 * 生成报表并发送
			 */
			ReportFactory.genAllReportFile();
			mailSender.sendAllReportsInOneMail();
			result.put("message", "邮件发送成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("message", "邮件发送失败：" + ex.getMessage());
		}
		return result.toString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

	@GET
	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] treeJson(@Context HttpServletRequest request)
			throws IOException {
		JSONArray array = new JSONArray();
		Long nodeId = RequestUtils.getLong(request, "nodeId");
		String nodeCode = request.getParameter("nodeCode");
		String selected = request.getParameter("selected");

		logger.debug(RequestUtils.getParameterMap(request));
		List<TreeModel> treeModels = new java.util.ArrayList<TreeModel>();
		List<String> chooseList = new java.util.ArrayList<String>();
		if (StringUtils.isNotEmpty(selected)) {
			chooseList = StringTools.split(selected);
		}

		TreeModel treeNode = null;

		if (nodeId != null && nodeId > 0) {
			treeNode = treeModelService.getTreeModel(nodeId);
		} else if (StringUtils.isNotEmpty(nodeCode)) {
			treeNode = treeModelService.getTreeModelByCode(nodeCode);
		}

		if (treeNode != null) {
			ReportQuery query = new ReportQuery();
			List<TreeModel> subTrees = treeModelService
					.getSubTreeModels(treeNode.getId());
			if (subTrees != null && !subTrees.isEmpty()) {
				for (TreeModel tree : subTrees) {
					tree.getDataMap().put("nocheck", "true");
					tree.getDataMap().put("iconSkin", "tree_folder");
					tree.getDataMap().put("isParent", "true");
					tree.setIconCls("folder");
					tree.setLevel(0);
					treeModels.add(tree);
					query.nodeId(tree.getId());
					List<Report> reports = reportService.list(query);
					for (Report report : reports) {
						if (StringUtils.isNumeric(report.getId())) {
							TreeModel t = new BaseTree();
							t.setId(Long.parseLong(report.getId()));
							t.setParentId(tree.getId());
							t.setName(report.getSubject());
							t.setCode(report.getId());
							t.setTreeId(report.getId());
							t.setIconCls("leaf");
							t.getDataMap().put("iconSkin", "tree_leaf");
							if (chooseList.contains(report.getId())) {
								t.setChecked(true);
							}
							treeModels.add(t);
						}
					}
				}
			}
			TreeHelper treeHelper = new TreeHelper();
			array = treeHelper.getTreeJSONArray(treeModels);
		}

		return array.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/view/{reportId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("reportId") String reportId,
			@Context HttpServletRequest request) throws IOException {
		Report report = null;
		if (StringUtils.isNotEmpty(reportId)) {
			report = reportService.getReport(reportId);
		}
		JSONObject result = new JSONObject();
		if (report != null) {
			result = report.toJsonObject();
		}
		return result.toString().getBytes("UTF-8");
	}
}