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

import java.io.File;
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import com.glaf.core.util.QuartzUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.mail.business.MailDataFacede;
import com.glaf.mail.domain.MailItem;
import com.glaf.mail.domain.MailTask;
import com.glaf.mail.query.MailItemQuery;
import com.glaf.mail.query.MailTaskQuery;
import com.glaf.mail.service.IMailDataService;
import com.glaf.mail.service.IMailTaskService;

@Controller("/rs/mail/mailTask")
@Path("/rs/mail/mailTask")
public class MailTaskResource {
	protected static Log logger = LogFactory.getLog(MailTaskResource.class);

	protected IMailDataService mailDataService;

	protected IMailTaskService mailTaskService;

	protected MailDataFacede mailDataFacede;

	@POST
	@Path("/delete")
	public void deleteById(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String mailTaskId = request.getParameter("taskId");
		if (StringUtils.isEmpty(mailTaskId)) {
			mailTaskId = request.getParameter("id");
		}
		if (mailTaskId != null) {
			MailTask mailTask = mailTaskService.getMailTask(mailTaskId);
			if (mailTask != null
					&& StringUtils.equals(mailTask.getCreateBy(),
							RequestUtils.getActorId(request))) {
				mailTaskService.deleteById(mailTaskId);
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/delete/{taskId}")
	public void deleteById(@PathParam("taskId") String mailTaskId,
			@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		if (mailTaskId != null) {
			MailTask mailTask = mailTaskService.getMailTask(mailTaskId);
			if (mailTask != null
					&& StringUtils.equals(mailTask.getCreateBy(),
							RequestUtils.getActorId(request))) {
				mailTaskService.deleteById(mailTaskId);
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
		MailTaskQuery query = new MailTaskQuery();
		Tools.populate(query, params);

		logger.debug("params:" + params);

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

		query.createBy(RequestUtils.getActorId(request));

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		int total = mailTaskService.getMailTaskCountByQueryCriteria(query);
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

			List<MailTask> list = mailTaskService.getMailTasksByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
				if ("yui".equals(gridType)) {
					responseJSON.set("records", rowsJSON);
				} else {
					responseJSON.set("rows", rowsJSON);
				}

				for (MailTask mailTask : list) {
					ObjectNode rowJSON = new ObjectMapper().createObjectNode();
					rowJSON.put("id", mailTask.getId());
					rowJSON.put("taskId", mailTask.getId());

					if (mailTask.getSubject() != null) {
						rowJSON.put("subject", mailTask.getSubject());
					}

					if (mailTask.getCallbackUrl() != null) {
						rowJSON.put("callbackUrl", mailTask.getCallbackUrl());
					}

					rowJSON.put("threadSize", mailTask.getThreadSize());

					rowJSON.put("locked", mailTask.getLocked());

					rowJSON.put("delayTime", mailTask.getDelayTime());

					if (mailTask.getStartDate() != null) {
						rowJSON.put("startDate",
								DateUtils.getDateTime(mailTask.getStartDate()));
					}

					if (mailTask.getEndDate() != null) {
						rowJSON.put("endDate",
								DateUtils.getDateTime(mailTask.getEndDate()));
					}

					rowJSON.put("isHtml", mailTask.isHtml());

					rowJSON.put("isBack", mailTask.isBack());

					rowJSON.put("isUnSubscribe", mailTask.isUnSubscribe());

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

	@GET
	@POST
	@Path("/mailList")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] mailList(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MailItemQuery query = new MailItemQuery();
		Tools.populate(query, params);

		logger.debug("params:" + params);

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

		int pageNo = start / limit + 1;

		query.setPageSize(limit);
		query.setPageNo(pageNo);
		query.setTaskId(request.getParameter("taskId"));
		logger.debug("taskId:" + query.getTaskId());
		logger.debug("pageNo:" + query.getPageNo());

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (query.getTaskId() != null) {

			int total = mailDataFacede.getMailCount(query);
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

				List<MailItem> list = mailDataFacede.getMailItems(query);

				if (list != null && !list.isEmpty()) {
					ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
					if ("yui".equals(gridType)) {
						responseJSON.set("records", rowsJSON);
					} else {
						responseJSON.set("rows", rowsJSON);
					}

					responseJSON.put("lastRow", list.get(list.size() - 1)
							.getId());

					for (MailItem mailItem : list) {
						ObjectNode rowJSON = new ObjectMapper()
								.createObjectNode();
						rowJSON.put("id", mailItem.getId());
						rowJSON.put("itemId", mailItem.getId());

						if (mailItem.getTaskId() != null) {
							rowJSON.put("taskId", mailItem.getTaskId());
						}
						if (mailItem.getMailTo() != null) {
							rowJSON.put("mailTo", mailItem.getMailTo());
						}
						if (mailItem.getSendDate() != null) {
							rowJSON.put("sendDate", DateUtils
									.getDateTime(mailItem.getSendDate()));
						}
						rowJSON.put("sendStatus", mailItem.getSendStatus());
						rowJSON.put("retryTimes", mailItem.getRetryTimes());
						if (mailItem.getReceiveIP() != null) {
							rowJSON.put("receiveIP", mailItem.getReceiveIP());
						}
						if (mailItem.getReceiveDate() != null) {
							rowJSON.put("receiveDate", DateUtils
									.getDateTime(mailItem.getReceiveDate()));
						}
						rowJSON.put("receiveStatus",
								mailItem.getReceiveStatus());
						rowJSON.put("lastModified", mailItem.getLastModified());
						rowsJSON.add(rowJSON);
					}

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
		String taskId = request.getParameter("taskId");
		if (StringUtils.isEmpty(taskId)) {
			taskId = request.getParameter("id");
		}
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
		}

		if (mailTask == null) {
			mailTask = new MailTask();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(mailTask, params);

		mailTask.setBack(RequestUtils.getBoolean(request, "isBack"));
		mailTask.setHtml(RequestUtils.getBoolean(request, "isHtml"));
		mailTask.setUnSubscribe(RequestUtils.getBoolean(request,
				"isUnSubscribe"));

		mailTask.setCreateBy(RequestUtils.getActorId(request));

		this.mailTaskService.save(mailTask);

		taskId = mailTask.getId();

		if (mailTask.getLocked() == 0) {
			QuartzUtils.stop(taskId);
			QuartzUtils.restart(taskId);
		} else {
			QuartzUtils.stop(taskId);
		}

		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/saveAccounts")
	public void saveAccounts(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String taskId = request.getParameter("taskId");
		if (StringUtils.isEmpty(taskId)) {
			taskId = request.getParameter("id");
		}
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
		}

		if (mailTask != null
				&& StringUtils.equals(RequestUtils.getActorId(request),
						mailTask.getCreateBy())) {
			String rowIds = request.getParameter("accountIds");
			List<String> accountIds = StringTools.split(rowIds);
			mailTaskService.saveAccounts(taskId, accountIds);
		}

	}

	@POST
	@Path("/saveMails")
	public void saveMails(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String taskId = request.getParameter("taskId");
		if (StringUtils.isEmpty(taskId)) {
			taskId = request.getParameter("id");
		}
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
		}
		logger.debug(mailTask);

		if (mailTask != null
				&& StringUtils.equals(RequestUtils.getActorId(request),
						mailTask.getCreateBy())) {
			String rowIds = request.getParameter("addresses");
			logger.debug("addresses:" + rowIds);
			List<String> addresses = StringTools.split(rowIds);
			if (addresses.size() <= 100000) {
				mailDataFacede.saveMails(taskId, addresses);
				taskId = mailTask.getId();

				if (mailTask.getLocked() == 0) {
					QuartzUtils.stop(taskId);
					QuartzUtils.restart(taskId);
				} else {
					QuartzUtils.stop(taskId);
				}

			} else {
				throw new RuntimeException("mail addresses too many");
			}
		}
	}

	@POST
	@Path("/sendMails")
	public void sendMails(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String taskId = request.getParameter("taskId");
		if (StringUtils.isEmpty(taskId)) {
			taskId = request.getParameter("id");
		}
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
		}
		logger.debug(mailTask);

		if (mailTask != null
				&& StringUtils.equals(RequestUtils.getActorId(request),
						mailTask.getCreateBy())) {
			MailDataFacede mailDataFacede = ContextFactory
					.getBean("mailDataFacede");
			if (mailDataFacede != null && taskId != null) {
				logger.debug("execute task " + taskId);
				mailDataFacede.sendTaskMails(taskId);
			}
		}
	}

	@javax.annotation.Resource
	public void setMailDataFacede(MailDataFacede mailDataFacede) {
		this.mailDataFacede = mailDataFacede;
	}

	@javax.annotation.Resource
	public void setMailDataService(IMailDataService mailDataService) {
		this.mailDataService = mailDataService;
	}

	@javax.annotation.Resource
	public void setMailTaskService(IMailTaskService mailTaskService) {
		this.mailTaskService = mailTaskService;
	}

	@POST
	@Path("/uploadMails")
	@ResponseBody
	public void uploadMails(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		String taskId = request.getParameter("taskId");
		if (StringUtils.isEmpty(taskId)) {
			taskId = request.getParameter("id");
		}
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(taskId)) {
			mailTask = mailTaskService.getMailTask(taskId);
		}

		if (mailTask != null
				&& StringUtils.equals(RequestUtils.getActorId(request),
						mailTask.getCreateBy())) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096);
			// 设置临时存放目录
			factory.setRepository(new File(SystemProperties.getConfigRootPath()
					+ "/temp/"));
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 获取文件尺寸
			// 设置最大文件尺寸
			// upload.setSizeMax(4194304);
			upload.setHeaderEncoding("UTF-8");
			List<?> fileItems = null;
			try {
				fileItems = upload.parseRequest(request);
				Iterator<?> i = fileItems.iterator();
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					logger.debug(fi.getName());
					if (fi.getName().endsWith(".txt")) {
						byte[] bytes = fi.get();
						String rowIds = new String(bytes);
						List<String> addresses = StringTools.split(rowIds);
						if (addresses.size() <= 100000) {
							mailDataFacede.saveMails(taskId, addresses);
							taskId = mailTask.getId();

							if (mailTask.getLocked() == 0) {
								QuartzUtils.stop(taskId);
								QuartzUtils.restart(taskId);
							} else {
								QuartzUtils.stop(taskId);
							}

						} else {
							throw new RuntimeException(
									"mail addresses too many");
						}
						break;
					}
				}
			} catch (FileUploadException ex) {// 处理文件尺寸过大异常
				ex.printStackTrace();
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	@GET
	@POST
	@Path("/view/{taskId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("taskId") String mailTaskId,
			@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(mailTaskId)) {
			mailTask = mailTaskService.getMailTask(mailTaskId);
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (mailTask != null
				&& StringUtils.equals(mailTask.getCreateBy(),
						RequestUtils.getActorId(request))) {

			responseJSON.put("id", mailTask.getId());
			responseJSON.put("mailTaskId", mailTask.getId());
			if (mailTask.getSubject() != null) {
				responseJSON.put("subject", mailTask.getSubject());
			}
			if (mailTask.getCallbackUrl() != null) {
				responseJSON.put("callbackUrl", mailTask.getCallbackUrl());
			}

			if (mailTask.getStorageId() != null) {
				responseJSON.put("storageId", mailTask.getStorageId());
			}

			responseJSON.put("threadSize", mailTask.getThreadSize());

			responseJSON.put("delayTime", mailTask.getDelayTime());

			if (mailTask.getStartDate() != null) {
				responseJSON.put("startDate",
						DateUtils.getDateTime(mailTask.getStartDate()));
			}
			if (mailTask.getEndDate() != null) {
				responseJSON.put("endDate",
						DateUtils.getDateTime(mailTask.getEndDate()));
			}

			responseJSON.put("locked", String.valueOf(mailTask.getLocked()));

			responseJSON.put("isHtml", String.valueOf(mailTask.isHtml()));

			responseJSON.put("isBack", String.valueOf(mailTask.isBack()));

			responseJSON.put("isUnSubscribe",
					String.valueOf(mailTask.isUnSubscribe()));

		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
	
	@GET
	@POST
	@Path("/view2/{taskId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view2(@PathParam("taskId") String mailTaskId,
			@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		MailTask mailTask = null;
		if (StringUtils.isNotEmpty(mailTaskId)) {
			mailTask = mailTaskService.getMailTask(mailTaskId);
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (mailTask != null
				&& StringUtils.equals(mailTask.getCreateBy(),
						RequestUtils.getActorId(request))) {

			responseJSON.put("id", mailTask.getId());
			responseJSON.put("mailTaskId", mailTask.getId());
			if (mailTask.getSubject() != null) {
				responseJSON.put("subject", mailTask.getSubject());
			}
			if (mailTask.getCallbackUrl() != null) {
				responseJSON.put("callbackUrl", mailTask.getCallbackUrl());
			}

			responseJSON.put("threadSize", mailTask.getThreadSize());

			responseJSON.put("delayTime", mailTask.getDelayTime());

			if (mailTask.getStartDate() != null) {
				responseJSON.put("startDate",
						DateUtils.getDateTime(mailTask.getStartDate()));
			}
			if (mailTask.getEndDate() != null) {
				responseJSON.put("endDate",
						DateUtils.getDateTime(mailTask.getEndDate()));
			}

			responseJSON.put("locked", String.valueOf(mailTask.getLocked()));

			responseJSON.put("isHtml", String.valueOf(mailTask.isHtml()));

			responseJSON.put("isBack", String.valueOf(mailTask.isBack()));

			responseJSON.put("isUnSubscribe",
					String.valueOf(mailTask.isUnSubscribe()));

		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
}