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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.util.IOUtils;
import com.glaf.core.base.BlobItem;
import com.glaf.core.base.DataFile;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.query.BlobItemQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.Constants;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.util.UUID32;

@Controller("/myupload")
@RequestMapping("/myupload")
public class FileUploadController {

	protected final static Log logger = LogFactory
			.getLog(FileUploadController.class);

	private static Configuration conf = BaseConfiguration.create();

	protected IBlobService blobService;

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String filePath = request.getParameter("filePath");
		DataFile dataFile = blobService.getBlobByFileId(filePath);
		if (dataFile != null) {
			if (dataFile.getPath() != null) {
				PrintWriter out = response.getWriter();
				String rootDir = SystemProperties.getConfigRootPath();
				File file = new File(rootDir + dataFile.getPath());
				if (file.exists()) {
					file.delete();
					out.print("success");
					logger.debug(file.getAbsolutePath() + " delete ok.");
				}
			}
			blobService.deleteBlobByFileId(filePath);
		}
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
			view = "showUpload";
		}

		view = "/modules/myupload/" + view;

		return new ModelAndView(view, modelMap);
	}

	@ResponseBody
	@RequestMapping("/upload")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain;charset=UTF-8");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String serviceKey = request.getParameter("serviceKey");
		String responseType = request.getParameter("responseType");
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		String type = req.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			type = "0";
		}
		int maxUploadSize = conf.getInt(serviceKey + ".maxUploadSize", 0);
		if (maxUploadSize == 0) {
			maxUploadSize = conf.getInt("upload.maxUploadSize", 50);// 50MB
		}
		maxUploadSize = maxUploadSize * FileUtils.MB_SIZE;

		/**
		 * 文件大小超过maxDiskSize时将文件写到本地硬盘,默认超过5MB的将写到本地硬盘
		 */
		int maxDiskSize = conf.getInt(serviceKey + ".maxDiskSize", 0);
		if (maxDiskSize == 0) {
			maxDiskSize = conf.getInt("upload.maxDiskSize", 1024 * 1024 * 2);// 2MB
		}

		logger.debug("maxUploadSize:" + maxUploadSize);
		String uploadDir = Constants.UPLOAD_PATH;
		InputStream inputStream = null;
		try {
			PrintWriter out = response.getWriter();
			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				logger.debug("fize size:" + mFile.getSize());
				if (mFile.getOriginalFilename() != null && mFile.getSize() > 0
						&& mFile.getSize() < maxUploadSize) {
					String filename = mFile.getOriginalFilename();
					logger.debug("upload file:" + filename);
					logger.debug("fize size:" + mFile.getSize());
					String fileId = UUID32.getUUID();

					// 每天上传的文件根据日期存放在不同的文件夹
					String autoCreatedDateDirByParttern = "yyyy/MM/dd";
					String autoCreatedDateDir = DateFormatUtils.format(
							new java.util.Date(), autoCreatedDateDirByParttern);
					String rootDir = SystemProperties.getConfigRootPath();
					if (!rootDir.endsWith(String.valueOf(File.separatorChar))) {
						rootDir = rootDir + File.separatorChar;
					}
					File savePath = new File(rootDir + uploadDir
							+ autoCreatedDateDir);
					if (!savePath.exists()) {
						savePath.mkdirs();
					}

					String fileName = savePath + "/" + fileId;

					BlobItem dataFile = new BlobItemEntity();
					dataFile.setLastModified(System.currentTimeMillis());
					dataFile.setCreateBy(loginContext.getActorId());
					dataFile.setFileId(fileId);
					dataFile.setPath(uploadDir + autoCreatedDateDir + "/"
							+ fileId);
					dataFile.setFilename(mFile.getOriginalFilename());
					dataFile.setName(mFile.getOriginalFilename());
					dataFile.setContentType(mFile.getContentType());
					dataFile.setType(type);
					dataFile.setStatus(0);
					dataFile.setServiceKey(serviceKey);
					if (mFile.getSize() <= maxDiskSize) {
						dataFile.setData(mFile.getBytes());
					}
					blobService.insertBlob(dataFile);
					dataFile.setData(null);

					if (mFile.getSize() > maxDiskSize) {
						FileUtils.save(fileName, inputStream);
						logger.debug(fileName + " save ok.");
					}

					if (StringUtils.equalsIgnoreCase(responseType, "json")) {
						StringBuilder json = new StringBuilder();
						json.append("{");
						json.append("'");
						json.append("fileId");
						json.append("':'");
						json.append(fileId);
						json.append("'");
						Enumeration<String> pNames = request
								.getParameterNames();
						String pName;
						while (pNames.hasMoreElements()) {
							json.append(",");
							pName = (String) pNames.nextElement();
							json.append("'");
							json.append(pName);
							json.append("':'");
							json.append(request.getParameter(pName));
							json.append("'");
						}
						json.append("}");
						logger.debug(json.toString());
						response.getWriter().write(json.toString());
					} else {
						out.print(fileId);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.close(inputStream);
		}
	}

}
