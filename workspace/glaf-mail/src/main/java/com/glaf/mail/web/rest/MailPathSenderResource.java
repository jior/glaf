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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.mail.business.MailPathTaskSender;
import com.glaf.mail.domain.MailPathSender;
import com.glaf.mail.query.MailPathSenderQuery;
import com.glaf.mail.service.IMailPathSenderService;

import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QuartzUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller("/rs/mail/sender")
@Path("/rs/mail/sender")
public class MailPathSenderResource {
	protected static final Log logger = LogFactory
			.getLog(MailPathSenderResource.class);

	protected IMailPathSenderService mailPathSenderService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("rowIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				mailPathSenderService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete/{rowIds}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@PathParam("rowIds") String rowIds,
			@Context HttpServletRequest request) throws IOException {
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				mailPathSenderService.deleteByIds(ids);
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
		String mailId = request.getParameter("mailId");
		if (StringUtils.isEmpty(mailId)) {
			mailId = request.getParameter("id");
		}
		mailPathSenderService.deleteById(mailId);
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete/{mailPathSenderId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(
			@PathParam("mailPathSenderId") String mailPathSenderId,
			@Context HttpServletRequest request) throws IOException {
		mailPathSenderService.deleteById(mailPathSenderId);
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MailPathSenderQuery query = new MailPathSenderQuery();
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
		int total = mailPathSenderService
				.getMailPathSenderCountByQueryCriteria(query);
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

			List<MailPathSender> list = mailPathSenderService
					.getMailPathSendersByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MailPathSender mailPathSender : list) {
					JSONObject rowJSON = mailPathSender.toJsonObject();
					rowsJSON.add(rowJSON);
				}

			}
		}
		return result.toString().getBytes("UTF-8");
	}

	@POST
	@Path("/saveMail")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveMail(@Context HttpServletRequest request) {
		String mailId = request.getParameter("mailId");
		if (StringUtils.isEmpty(mailId)) {
			mailId = request.getParameter("id");
		}
		MailPathSender mailPathSender = null;
		if (StringUtils.isNotEmpty(mailId)) {
			mailPathSender = mailPathSenderService.getMailPathSender(mailId);
		}

		if (mailPathSender == null) {
			mailPathSender = new MailPathSender();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		Tools.populate(mailPathSender, params);

		this.mailPathSenderService.save(mailPathSender);

		String taskId = "mail_task_" + mailPathSender.getId();

		if (StringUtils.equals(mailPathSender.getEnableFlag(), "1")) {
			QuartzUtils.stop(taskId);
			QuartzUtils.restart(taskId);
		} else {
			QuartzUtils.stop(taskId);
		}

		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/sendMail")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] sendMail(@Context HttpServletRequest request)
			throws IOException {
		JSONObject result = new JSONObject();
		MailPathSender mailPathSender = null;
		String mailId = request.getParameter("mailId");
		if (StringUtils.isNotEmpty(mailId)) {
			mailPathSender = mailPathSenderService.getMailPathSender(mailId);
			if (mailPathSender != null
					&& StringUtils
							.isNotEmpty(mailPathSender.getMailRecipient())) {
				MailPathTaskSender sender = new MailPathTaskSender();
				try {
					sender.sendMail(mailId);
					result.put("message", "邮件发送成功！");
				} catch (Exception ex) {
					ex.printStackTrace();
					result.put("message", "邮件发送失败：" + ex.getMessage());
				}
			}
		}

		return result.toString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setMailPathSenderService(
			IMailPathSenderService mailPathSenderService) {
		this.mailPathSenderService = mailPathSenderService;
	}

	@GET
	@POST
	@Path("/view/{mailId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("mailId") String mailId,
			@Context HttpServletRequest request) throws IOException {
		MailPathSender mailPathSender = null;
		if (StringUtils.isNotEmpty(mailId)) {
			mailPathSender = mailPathSenderService.getMailPathSender(mailId);
		}
		JSONObject result = new JSONObject();
		if (mailPathSender != null) {
			result = mailPathSender.toJsonObject();
		}
		return result.toString().getBytes("UTF-8");
	}
}