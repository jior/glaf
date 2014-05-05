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

package com.glaf.core.web.springmvc;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.BlobItem;
import com.glaf.core.base.DataFile;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.query.BlobItemQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.util.UUID32;

@Controller("/upload")
@RequestMapping("/upload")
public class MxUploadController {
	protected final static Log logger = LogFactory
			.getLog(MxUploadController.class);
	private static final int MAX_AVAILABLE = 10;
	protected IBlobService blobService;
	 
	private final Semaphore semaphore = new Semaphore(MAX_AVAILABLE, true);
	
	
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request, ModelMap modelMap) {
		String serviceKey = request.getParameter("serviceKey");
		if (StringUtils.isEmpty(serviceKey)) {
			modelMap.put("error_message", "您没有提供必要的信息，serviceKey是必须的！");
			return new ModelAndView("/error");
		}

		RequestUtils.setRequestParameterToAttribute(request);
		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isEmpty(view)) {
			view = "/upload/main";
		}

		String x_view = ViewProperties.getString("upload.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView(view, modelMap);
	}

	@RequestMapping("/remark")
	public ModelAndView remark(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String message = "";
		String fileId = request.getParameter("fileId");
		try {
			DataFile dataFile = blobService.getBlobByFileId(fileId);
			if (dataFile != null) {
				if (StringUtils.equals(loginContext.getActorId(),
						dataFile.getCreateBy())
						|| loginContext.isSystemAdministrator()) {
					String subject = request.getParameter("subject");
					dataFile.setName(subject);

					message = "操作成功！";
				} else {
					message = "无修改权限！";
				}
			} else {
				message = "文件不存在！";
			}
		} catch (Exception ex) {
			message = "操作失败！";
		}

		String responseDataType = request.getParameter("responseDataType");
		if (StringUtils.equals(responseDataType, "json")) {
			Map<String, Object> jsonMap = new java.util.HashMap<String, Object>();
			jsonMap.put("message", message);
			JSONObject object = new JSONObject(jsonMap);
			response.getWriter().write(object.toString());
			return null;
		} else if (StringUtils.equals(responseDataType, "xml")) {
			StringBuffer buffer = new StringBuffer(500);
			buffer.append("<response>");
			buffer.append("\n    <message>").append(message)
					.append("</message>");
			buffer.append("\n</response>");
			response.getWriter().write(buffer.toString());
			return null;
		}

		return this.showUpload(request, modelMap);
	}

	@RequestMapping("/remove")
	public ModelAndView remove(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String message = "";
		String fileId = request.getParameter("fileId");
		try {
			DataFile dataFile = blobService.getBlobByFileId(fileId);
			if (dataFile != null) {
				if (StringUtils.equals(loginContext.getActorId(),
						dataFile.getCreateBy())
						|| loginContext.isSystemAdministrator()) {
					blobService.deleteBlobByFileId(fileId);
					message = "删除成功！";
				} else {
					message = "无删除权限！";
				}
			} else {
				message = "文件不存在！";
			}
		} catch (Exception ex) {
			message = "操作失败！";
		}

		String responseDataType = request.getParameter("responseDataType");
		if (StringUtils.equals(responseDataType, "json")) {
			Map<String, Object> jsonMap = new java.util.HashMap<String, Object>();
			jsonMap.put("message", message);
			JSONObject object = new JSONObject(jsonMap);
			response.getWriter().write(object.toString());
			return null;
		} else if (StringUtils.equals(responseDataType, "xml")) {
			StringBuffer buffer = new StringBuffer(500);
			buffer.append("<response>");
			buffer.append("\n    <message>").append(message)
					.append("</message>");
			buffer.append("\n</response>");
			response.getWriter().write(buffer.toString());
			return null;
		}

		return this.showUpload(request, modelMap);
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@RequestMapping("/showUpload")
	public ModelAndView showUpload(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		String serviceKey = request.getParameter("serviceKey");
		if (StringUtils.isEmpty(serviceKey)) {
			modelMap.put("error_message", "您没有提供必要的信息，serviceKey是必须的！");
			return new ModelAndView("/error");
		}
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String businessKey = request.getParameter("businessKey");
		int status = ParamUtils.getInt(paramMap, "status");
		try {
			if (request.getAttribute("dataFiles") == null) {
				List<DataFile> dataFiles = new java.util.ArrayList<DataFile>();
				if (StringUtils.isNotEmpty(businessKey)) {
					List<DataFile> rows = blobService.getBlobList(businessKey);
					if (rows != null && rows.size() > 0) {
						dataFiles.addAll(rows);
					}
				}

				paramMap.remove("businessKey");
				paramMap.put("createBy", loginContext.getActorId());
				paramMap.put("serviceKey", serviceKey);
				paramMap.put("status", status);
				BlobItemQuery query = new BlobItemQuery();
				Tools.populate(query, paramMap);
	 
				query.createBy(loginContext.getActorId());
				query.serviceKey(serviceKey);
				query.status(status);

				List<DataFile> rows = blobService.getBlobList(query);
				if (rows != null && rows.size() > 0) {
					Iterator<DataFile> iterator = rows.iterator();
					while (iterator.hasNext()) {
						DataFile dataFile = iterator.next();
						if (!dataFiles.contains(dataFile)) {
							dataFiles.add(dataFile);
						}
					}
				}

				if (dataFiles.size() > 0) {
					modelMap.put("dataFiles", dataFiles);
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			modelMap.put("error_message", "不能获取文件信息。");
			return new ModelAndView("/error");
		}

		String view = request.getParameter("view");
		if (StringUtils.isEmpty(view)) {
			view = "/upload/showUpload";
		}

		return new ModelAndView(view, modelMap);
	}

	@RequestMapping("/uploadNow")
	public ModelAndView upload(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		String type = req.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			type = "0";
		}

		String serviceKey = req.getParameter("serviceKey");
		if (StringUtils.isEmpty(serviceKey)) {
			modelMap.put("error_message", "您没有提供必要的信息，serviceKey是必须的！");
			return new ModelAndView("/error");
		}

		Map<String, Object> paramMap = RequestUtils.getParameterMap(req);
		logger.debug("paramMap:" + paramMap);
		String businessKey = req.getParameter("businessKey");
		String objectId = req.getParameter("objectId");
		String objectValue = req.getParameter("objectValue");
		int status = ParamUtils.getInt(paramMap, "status");
		List<DataFile> dataFiles = new java.util.ArrayList<DataFile>();
		try {
			semaphore.acquire();

			if (StringUtils.isNotEmpty(businessKey)) {
				List<DataFile> rows = blobService.getBlobList(businessKey);
				if (rows != null && rows.size() > 0) {
					dataFiles.addAll(rows);
				}
			}

			paramMap.remove("businessKey");
			paramMap.put("createBy", loginContext.getActorId());
			paramMap.put("serviceKey", serviceKey);
			paramMap.put("status", status);
			BlobItemQuery query = new BlobItemQuery();
			Tools.populate(query, paramMap);
 
			query.createBy(loginContext.getActorId());
			query.serviceKey(serviceKey);
			query.status(status);
			List<DataFile> rows = blobService.getBlobList(query);
			if (rows != null && rows.size() > 0) {
				Iterator<DataFile> iterator = rows.iterator();
				while (iterator.hasNext()) {
					DataFile dataFile = iterator.next();
					if (!dataFiles.contains(dataFile)) {
						dataFiles.add(dataFile);
					}
				}
			}

			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
					String filename = mFile.getOriginalFilename();
					logger.debug("upload file:" + filename);
					String fileId = UUID32.getUUID();
					if (filename.indexOf("/") != -1) {
						filename = filename.substring(
								filename.lastIndexOf("/") + 1,
								filename.length());
					} else if (filename.indexOf("\\") != -1) {
						filename = filename.substring(
								filename.lastIndexOf("\\") + 1,
								filename.length());
					}
					BlobItem dataFile = new BlobItemEntity();
					dataFile.setLastModified(System.currentTimeMillis());
					dataFile.setCreateBy(loginContext.getActorId());
					dataFile.setFileId(fileId);
					dataFile.setData(mFile.getBytes());
					dataFile.setFilename(filename);
					dataFile.setName(mFile.getName());
					dataFile.setContentType(mFile.getContentType());
					dataFile.setSize((int) mFile.getSize());
					dataFile.setType(type);
					dataFile.setStatus(status);
					dataFile.setObjectId(objectId);
					dataFile.setObjectValue(objectValue);
					dataFile.setServiceKey(serviceKey);
					blobService.insertBlob(dataFile);
					dataFile.setData(null);
					dataFiles.add(dataFile);
				}
			}

			if (dataFiles.size() > 0) {
				modelMap.put("dataFiles", dataFiles);
			}

		} catch (Exception ex) {
			logger.debug(ex);
			return new ModelAndView("/error");
		} finally {
			semaphore.release();
		}

		return this.showUpload(request, modelMap);
	}

}