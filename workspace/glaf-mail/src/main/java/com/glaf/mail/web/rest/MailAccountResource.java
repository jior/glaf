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
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.WebApplicationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.mail.domain.MailAccount;
import com.glaf.mail.query.MailAccountQuery;
import com.glaf.mail.service.IMailAccountService;

import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/rs/mail/mailAccount")
@Path("/rs/mail/mailAccount")
public class MailAccountResource {
	protected static Log logger = LogFactory.getLog(MailAccountResource.class);

	protected IMailAccountService mailAccountService;

	@POST
	@Path("/delete")
	public void deleteById(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String accountId = request.getParameter("accountId");
		if (StringUtils.isEmpty(accountId)) {
			accountId = request.getParameter("id");
		}
		if (accountId != null) {
			String actorId = RequestUtils.getActorId(request);
			MailAccount mailAccount = mailAccountService
					.getMailAccount(accountId);
			if (mailAccount != null
					&& StringUtils.equals(mailAccount.getCreateBy(), actorId)) {
				mailAccountService.deleteById(accountId);
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/delete/{accountId}")
	public void deleteById(@PathParam("accountId") String accountId,
			@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		if (accountId != null) {
			String actorId = RequestUtils.getActorId(request);
			MailAccount mailAccount = mailAccountService
					.getMailAccount(accountId);
			if (mailAccount != null
					&& StringUtils.equals(mailAccount.getCreateBy(), actorId)) {
				mailAccountService.deleteById(accountId);
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MailAccountQuery query = new MailAccountQuery();
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

		String actorId = RequestUtils.getActorId(request);
		query.createBy(actorId);

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		int total = mailAccountService
				.getMailAccountCountByQueryCriteria(query);
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

			List<MailAccount> list = mailAccountService
					.getMailAccountsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
				if ("yui".equals(gridType)) {
					responseJSON.set("records", rowsJSON);
				} else {
					responseJSON.set("rows", rowsJSON);
				}

				for (MailAccount mailAccount : list) {
					ObjectNode rowJSON = new ObjectMapper().createObjectNode();
					rowJSON.put("id", mailAccount.getId());
					rowJSON.put("mailAccountId", mailAccount.getId());

					if (mailAccount.getAccountType() != null) {
						rowJSON.put("accountType", mailAccount.getAccountType());
					}
					if (mailAccount.getCreateBy() != null) {
						rowJSON.put("createBy", mailAccount.getCreateBy());
					}
					if (mailAccount.getMailAddress() != null) {
						rowJSON.put("mailAddress", mailAccount.getMailAddress());
					}
					if (mailAccount.getShowName() != null) {
						rowJSON.put("showName", mailAccount.getShowName());
					}
					if (mailAccount.getUsername() != null) {
						rowJSON.put("username", mailAccount.getUsername());
					}
					if (mailAccount.getPassword() != null) {
						rowJSON.put("password", mailAccount.getPassword());
					}
					if (mailAccount.getPop3Server() != null) {
						rowJSON.put("pop3Server", mailAccount.getPop3Server());
					}

					rowJSON.put("receivePort", mailAccount.getReceivePort());

					if (mailAccount.getSmtpServer() != null) {
						rowJSON.put("smtpServer", mailAccount.getSmtpServer());
					}

					rowJSON.put("sendPort", mailAccount.getSendPort());

					rowJSON.put("autoReceive", mailAccount.autoReceive());

					rowJSON.put("rememberPassword",
							mailAccount.rememberPassword());

					rowJSON.put("locked", mailAccount.getLocked());

					if (mailAccount.getCreateDate() != null) {
						rowJSON.put("createDate", DateUtils
								.getDateTime(mailAccount.getCreateDate()));
					}

					rowJSON.put("authFlag", mailAccount.authFlag());

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
	public void save(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String accountId = request.getParameter("accountId");
		if (StringUtils.isEmpty(accountId)) {
			accountId = request.getParameter("id");
		}
		MailAccount mailAccount = null;
		if (StringUtils.isNotEmpty(accountId)) {
			mailAccount = mailAccountService.getMailAccount(accountId);
		}

		if (mailAccount == null) {
			mailAccount = new MailAccount();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(mailAccount, params);

		mailAccount.setCreateBy(RequestUtils.getActorId(request));
		mailAccount.setAuthFlag(RequestUtils.getBoolean(request, "authFlag"));
		mailAccount.setAutoReceive(RequestUtils.getBoolean(request,
				"autoReceive"));
		mailAccount.setRememberPassword(RequestUtils.getBoolean(request,
				"rememberPassword"));
		String password = request.getParameter("password");
		if (StringUtils.isNotEmpty(password)) {
			password = RequestUtils.encodeString(password);
			mailAccount.setPassword(password);
		}

		logger.debug(mailAccount);
		this.mailAccountService.save(mailAccount);
	}

	@javax.annotation.Resource
	public void setMailAccountService(IMailAccountService mailAccountService) {
		this.mailAccountService = mailAccountService;
	}

	@GET
	@POST
	@Path("/view/{accountId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("accountId") String accountId,
			@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		MailAccount mailAccount = null;
		if (StringUtils.isNotEmpty(accountId)) {
			mailAccount = mailAccountService.getMailAccount(accountId);
		}
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		if (mailAccount != null
				&& StringUtils.equals(RequestUtils.getActorId(request),
						mailAccount.getCreateBy())) {

			responseJSON.put("id", mailAccount.getId());
			responseJSON.put("accountId", mailAccount.getId());

			if (mailAccount.getAccountType() != null) {
				responseJSON.put("accountType", mailAccount.getAccountType());
			}
			if (mailAccount.getCreateBy() != null) {
				responseJSON.put("createBy", mailAccount.getCreateBy());
			}
			if (mailAccount.getMailAddress() != null) {
				responseJSON.put("mailAddress", mailAccount.getMailAddress());
			}
			if (mailAccount.getShowName() != null) {
				responseJSON.put("showName", mailAccount.getShowName());
			}
			if (mailAccount.getUsername() != null) {
				responseJSON.put("username", mailAccount.getUsername());
			}

			if (mailAccount.getPop3Server() != null) {
				responseJSON.put("pop3Server", mailAccount.getPop3Server());
			}

			responseJSON.put("receivePort", mailAccount.getReceivePort());

			if (mailAccount.getSmtpServer() != null) {
				responseJSON.put("smtpServer", mailAccount.getSmtpServer());
			}

			responseJSON.put("sendPort", mailAccount.getSendPort());

			responseJSON.put("autoReceive",
					String.valueOf(mailAccount.autoReceive()));

			responseJSON.put("rememberPassword",
					String.valueOf(mailAccount.rememberPassword()));

			responseJSON.put("locked", String.valueOf(mailAccount.getLocked()));

			if (mailAccount.getCreateDate() != null) {
				responseJSON.put("createDate",
						DateUtils.getDateTime(mailAccount.getCreateDate()));
			}

			responseJSON
					.put("authFlag", String.valueOf(mailAccount.authFlag()));

		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
}