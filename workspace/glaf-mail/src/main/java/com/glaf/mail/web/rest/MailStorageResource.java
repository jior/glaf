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

package com.glaf.mail.web.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;

import com.glaf.core.util.Tools;

import com.glaf.mail.business.MailDataFacede;
import com.glaf.mail.domain.MailStorage;
import com.glaf.mail.query.MailStorageQuery;
import com.glaf.mail.service.IMailStorageService;

@Controller("/rs/mail/mailStorage")
@Path("/rs/mail/mailStorage")
public class MailStorageResource {
	protected static Log logger = LogFactory.getLog(MailStorageResource.class);

	protected MailDataFacede mailDataFacede;

	protected IMailStorageService mailStorageService;

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MailStorageQuery query = new MailStorageQuery();
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
		int total = mailStorageService
				.getMailStorageCountByQueryCriteria(query);
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

			List<MailStorage> list = mailStorageService
					.getMailStoragesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
				if ("yui".equals(gridType)) {
					responseJSON.set("records", rowsJSON);
				} else {
					responseJSON.set("rows", rowsJSON);
				}

				for (MailStorage mailStorage : list) {
					ObjectNode rowJSON = new ObjectMapper().createObjectNode();
					rowJSON.put("id", mailStorage.getId());
					rowJSON.put("mailStorageId", mailStorage.getId());

					if (mailStorage.getSubject() != null) {
						rowJSON.put("subject", mailStorage.getSubject());
					}
					if (mailStorage.getDataSpace() != null) {
						rowJSON.put("dataSpace", mailStorage.getDataSpace());
					}
					if (mailStorage.getDataTable() != null) {
						rowJSON.put("dataTable", mailStorage.getDataTable());
					}
					if (mailStorage.getStorageType() != null) {
						rowJSON.put("storageType", mailStorage.getStorageType());
					}
					if (mailStorage.getHost() != null) {
						rowJSON.put("host", mailStorage.getHost());
					}
					rowJSON.put("port", mailStorage.getPort());
					if (mailStorage.getUsername() != null) {
						rowJSON.put("username", mailStorage.getUsername());
					}
					if (mailStorage.getPassword() != null) {
						rowJSON.put("password", mailStorage.getPassword());
					}
					if (mailStorage.getStatus() != null) {
						rowJSON.put("status ", mailStorage.getStatus());
					}
					if (mailStorage.getCreateBy() != null) {
						rowJSON.put("createBy", mailStorage.getCreateBy());
					}
					if (mailStorage.getCreateDate() != null) {
						rowJSON.put("createDate", DateUtils
								.getDateTime(mailStorage.getCreateDate()));
					}
					rowsJSON.add(rowJSON);
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
	@Path("/save")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] save(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {

		String mailStorageId = request.getParameter("storageId");
		if (StringUtils.isEmpty(mailStorageId)) {
			mailStorageId = request.getParameter("id");
		}
		MailStorage mailStorage = null;
		if (StringUtils.isNotEmpty(mailStorageId)) {
			mailStorage = mailStorageService.getMailStorage(mailStorageId);
		}

		if (mailStorage == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss",
					Locale.getDefault());
			String ret = formatter.format(new Date());
			mailStorage = new MailStorage();
			mailStorage.setDataSpace("datastg_" + ret);
			mailStorage.setDataTable("mail_" + ret);
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(mailStorage, params);

		mailStorage.setCreateBy(RequestUtils.getActorId(request));
		mailStorage.setCreateDate(new Date());
		mailDataFacede.addDataTable(mailStorage.getDataTable(), mailStorage);
		mailStorageService.save(mailStorage);
		return ResponseUtils.responseJsonResult(true);

	}

	@javax.annotation.Resource
	public void setMailDataFacede(MailDataFacede mailDataFacede) {
		this.mailDataFacede = mailDataFacede;
	}

	@javax.annotation.Resource
	public void setMailStorageService(IMailStorageService mailStorageService) {
		this.mailStorageService = mailStorageService;
	}

	@GET
	@POST
	@Path("/view/{storageId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("storageId") String storageId,
			@Context UriInfo uriInfo) {
		MailStorage mailStorage = null;
		if (StringUtils.isNotEmpty(storageId)) {
			mailStorage = mailStorageService.getMailStorage(storageId);
		}
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (mailStorage != null) {

			responseJSON.put("id", mailStorage.getId());
			responseJSON.put("storageId", mailStorage.getId());
			if (mailStorage.getSubject() != null) {
				responseJSON.put("subject", mailStorage.getSubject());
			}
			if (mailStorage.getDataSpace() != null) {
				responseJSON.put("dataSpace", mailStorage.getDataSpace());
			}
			if (mailStorage.getDataTable() != null) {
				responseJSON.put("dataTable", mailStorage.getDataTable());
			}
			if (mailStorage.getStorageType() != null) {
				responseJSON.put("storageType", mailStorage.getStorageType());
			}
			if (mailStorage.getHost() != null) {
				responseJSON.put("host", mailStorage.getHost());
			}
			responseJSON.put("port", mailStorage.getPort());
			if (mailStorage.getUsername() != null) {
				responseJSON.put("username", mailStorage.getUsername());
			}
			if (mailStorage.getPassword() != null) {
				responseJSON.put("password", mailStorage.getPassword());
			}
			if (mailStorage.getStatus() != null) {
				responseJSON.put("status ", mailStorage.getStatus());
			}
			if (mailStorage.getCreateBy() != null) {
				responseJSON.put("createBy", mailStorage.getCreateBy());
			}
			if (mailStorage.getCreateDate() != null) {
				responseJSON.put("createDate",
						DateUtils.getDateTime(mailStorage.getCreateDate()));
			}
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
}