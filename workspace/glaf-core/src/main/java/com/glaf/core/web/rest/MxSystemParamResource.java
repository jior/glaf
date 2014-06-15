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
package com.glaf.core.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.InputDefinition;
import com.glaf.core.domain.SystemParam;
import com.glaf.core.query.SystemParamQuery;
import com.glaf.core.service.ISystemParamService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller("/rs/system/param")
@Path("/rs/system/param")
public class MxSystemParamResource {
	protected static final Log logger = LogFactory
			.getLog(MxSystemParamResource.class);

	protected ISystemParamService systemParamService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String rowIds = request.getParameter("rowIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				// systemParamService.deleteByIds(ids);
				return ResponseUtils.responseJsonResult(true, "删除成功！");
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return ResponseUtils.responseJsonResult(false, "删除失败！");
	}

	@POST
	@Path("/deleteAll/{rowIds}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@PathParam("rowIds") String rowIds,
			@Context UriInfo uriInfo) {
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				// systemParamService.deleteByIds(ids);
				return ResponseUtils.responseJsonResult(true, "删除成功！");
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return ResponseUtils.responseJsonResult(false, "删除失败！");
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String systemParamId = request.getParameter("systemParamId");
		if (StringUtils.isEmpty(systemParamId)) {
			systemParamId = request.getParameter("id");
		}
		if (systemParamId != null) {
			systemParamService.deleteById(systemParamId);
			return ResponseUtils.responseJsonResult(true, "删除成功！");
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/delete/{systemParamId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@PathParam("systemParamId") String systemParamId,
			@Context UriInfo uriInfo) {
		if (systemParamId != null) {
			systemParamService.deleteById(systemParamId);
			return ResponseUtils.responseJsonResult(true, "删除成功！");
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@GET
	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] json(@Context HttpServletRequest request) {
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		String serviceKey = request.getParameter("serviceKey");
		String businessKey = request.getParameter("businessKey");
		try {
			Map<String, InputDefinition> paramMap = new java.util.HashMap<String, InputDefinition>();
			List<InputDefinition> params = systemParamService
					.getInputDefinitions(serviceKey);
			if (params != null && !params.isEmpty()) {
				for (InputDefinition def : params) {
					paramMap.put(def.getKeyName(), def);
				}
			}
			List<SystemParam> rows = systemParamService.getSystemParams(
					serviceKey, businessKey);
			if (rows != null && !rows.isEmpty()) {
				responseJSON.put("total", rows.size());
				ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
				for (SystemParam param : rows) {
					ObjectNode json = new ObjectMapper().createObjectNode();
					InputDefinition def = paramMap.get(param.getKeyName());
					if (param.getTitle() != null) {
						json.put("name",
								param.getTitle() + " (" + param.getKeyName()
										+ ")");
					} else {
						json.put("name", param.getKeyName());
					}
					json.put("title", param.getTitle());
					if (param.getStringVal() != null) {
						json.put("value", param.getStringVal());
					}
					String javaType = param.getJavaType();
					if (def != null) {
						javaType = def.getJavaType();
						json.put("group", def.getTypeTitle());
					} else {
						json.put("group", param.getTypeCd());
					}

					if ("Integer".equals(javaType)) {
						json.put("editor", "numberbox");
					} else if ("Long".equals(javaType)) {
						json.put("editor", "numberbox");
					} else if ("Double".equals(javaType)) {
						json.put("editor", "numberbox");
					} else if ("Date".equals(javaType)) {
						json.put("editor", "datebox");
					} else {
						json.put("editor", "text");
						if (def != null) {
							String inputType = def.getInputType();
							String validType = def.getValidType();

							if (StringUtils.isNotEmpty(def.getRequired())) {
								json.put("required", def.getRequired());
							}
							ObjectNode editor = new ObjectMapper()
									.createObjectNode();
							ObjectNode options = new ObjectMapper()
									.createObjectNode();
							if (validType != null) {
								options.put("validType", validType);
							}
							if (inputType != null) {
								if ("checkbox".equals(inputType)) {
									options.put("on", true);
									options.put("off", false);
								}
								if ("combobox".equals(inputType)) {
									options.put("valueField",
											def.getValueField());
									options.put("textField", def.getTextField());
									if (def.getUrl() != null) {
										options.put(
												"url",
												request.getContextPath()
														+ def.getUrl());
									}
									if (StringUtils.isNotEmpty(def
											.getRequired())) {
										options.put("required",
												def.getRequired());
									}
								}
							}
							editor.put("type", inputType);
							if (options.size() > 0) {
								editor.set("options", options);
							}
							json.set("editor", editor);
						}
					}

					rowsJSON.add(json);
				}
				responseJSON.set("rows", rowsJSON);
			}
			logger.debug(responseJSON.toString());
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemParamQuery query = new SystemParamQuery();
		Tools.populate(query, params);

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

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		int total = systemParamService
				.getSystemParamCountByQueryCriteria(query);
		if (total > 0) {
			responseJSON.put("total", total);
			responseJSON.put("totalCount", total);
			responseJSON.put("totalRecords", total);
			responseJSON.put("start", start);
			responseJSON.put("startIndex", start);
			responseJSON.put("limit", limit);
			responseJSON.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder("desc");
				}
			}

			// Map<String, UserProfile> userMap =
			// MxIdentityFactory.getUserProfileMap();
			List<SystemParam> list = systemParamService
					.getSystemParamsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
				if ("yui".equals(gridType)) {
					responseJSON.set("records", rowsJSON);
				} else {
					responseJSON.set("rows", rowsJSON);
				}

				// int sortNo = 0;
				for (SystemParam systemParam : list) {
					// sortNo++;
					ObjectNode node = systemParam.toObjectNode();
					node.put("sortNo", ++start);
					rowsJSON.add(node);
				}

			}
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/saveAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveAll(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String serviceKey = request.getParameter("serviceKey");
		String businessKey = request.getParameter("businessKey");
		try {
			List<InputDefinition> params = systemParamService
					.getInputDefinitions(serviceKey);
			if (params != null && !params.isEmpty()) {
				List<SystemParam> rows = new java.util.ArrayList<SystemParam>();
				for (InputDefinition def : params) {
					String paramName = def.getKeyName();
					String value = request.getParameter(paramName);
					if (value != null) {
						SystemParam m = new SystemParam();
						m.setServiceKey(serviceKey);
						m.setBusinessKey(businessKey);
						m.setKeyName(paramName);
						m.setTypeCd(def.getTypeCd());
						m.setTitle(def.getTitle());
						m.setJavaType(def.getJavaType());
						String javaType = def.getJavaType();

						if ("Integer".equals(javaType)) {
							m.setStringVal(value);
							m.setIntVal(Integer.parseInt(value));
						} else if ("Long".equals(javaType)) {
							m.setStringVal(value);
							m.setLongVal(Long.parseLong(value));
						} else if ("Double".equals(javaType)) {
							m.setStringVal(value);
							m.setDoubleVal(Double.parseDouble(value));
						} else if ("Date".equals(javaType)) {
							m.setStringVal(value);
							m.setDateVal(DateUtils.toDate(value));
						} else {
							if (value.length() < 2000) {
								m.setStringVal(value);
								m.setTextVal(value);
							} else {
								m.setTextVal(value);
							}
						}
						rows.add(m);
					}
				}
				systemParamService.saveAll(serviceKey, businessKey, rows);
				return ResponseUtils.responseJsonResult(true, "保存成功！");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false, "保存失败！");
	}

	@POST
	@Path("/save")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveSystemParam(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String systemParamId = request.getParameter("systemParamId");
		if (StringUtils.isEmpty(systemParamId)) {
			systemParamId = request.getParameter("id");
		}
		SystemParam systemParam = null;
		if (StringUtils.isNotEmpty(systemParamId)) {
			systemParam = systemParamService.getSystemParam(systemParamId);
		}

		if (systemParam == null) {
			systemParam = new SystemParam();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(systemParam, params);

		try {
			this.systemParamService.save(systemParam);
			return ResponseUtils.responseJsonResult(true, "保存成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false, "保存失败！");
	}

	@javax.annotation.Resource
	public void setSystemParamService(ISystemParamService systemParamService) {
		this.systemParamService = systemParamService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String systemParamId = request.getParameter("systemParamId");
		SystemParam systemParam = null;
		if (StringUtils.isNotEmpty(systemParamId)) {
			systemParam = systemParamService.getSystemParam(systemParamId);
		}
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (systemParam != null) {
			// Map<String, UserProfile> userMap =
			// MxIdentityFactory.getUserProfileMap();
			responseJSON = systemParam.toObjectNode();
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
}
