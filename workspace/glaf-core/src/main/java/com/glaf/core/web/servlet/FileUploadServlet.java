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
package com.glaf.core.web.servlet;

import java.io.*;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.util.IOUtils;
import com.glaf.core.base.BlobItem;
import com.glaf.core.base.DataFile;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.Constants;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.UUID32;

public class FileUploadServlet extends HttpServlet {

	protected final static Log logger = LogFactory
			.getLog(FileUploadServlet.class);

	private static final long serialVersionUID = 1L;

	private static Configuration conf = BaseConfiguration.create();

	protected IBlobService blobService;

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String filePath = request.getParameter("filePath");
		DataFile dataFile = getBlobService().getBlobByFileId(filePath);
		logger.debug("remove dataFile:" + dataFile);
		if (dataFile != null) {
			if (dataFile.getPath() != null) {
				PrintWriter out = response.getWriter();
				String rootDir = SystemProperties.getConfigRootPath();
				File file = new File(rootDir + dataFile.getPath());
				if (file.exists()) {
					String filename = file.getAbsolutePath();
					file.delete();
					out.print("success");
					logger.debug(filename + " delete ok.");
				}
			}
			getBlobService().deleteBlobByFileId(filePath);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext == null) {
			return;
		}
		response.setContentType("text/html;charset=UTF-8");
		String method = request.getParameter("method");
		if ("delete".equals(method)) {
			this.delete(request, response);
		} else {
			this.upload(request, response);
		}
	}

	public IBlobService getBlobService() {
		if (blobService == null) {
			blobService = ContextFactory.getBean("blobService");
		}
		return blobService;
	}

	public void upload(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext == null) {
			return;
		}
		String serviceKey = request.getParameter("serviceKey");
		String type = request.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			type = "0";
		}
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String rootDir = SystemProperties.getConfigRootPath();

		InputStream inputStream = null;
		try {
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			// threshold 极限、临界值，即硬盘缓存 8M
			diskFactory.setSizeThreshold(8 * FileUtils.MB_SIZE);
			// repository 临时文件目录
			diskFactory.setRepository(new File(rootDir + "/temp"));
			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			int maxUploadSize = conf.getInt(serviceKey + ".maxUploadSize", 0);
			if (maxUploadSize == 0) {
				maxUploadSize = conf.getInt("upload.maxUploadSize", 50);// 50MB
			}
			maxUploadSize = maxUploadSize * FileUtils.MB_SIZE;
			logger.debug("maxUploadSize:" + maxUploadSize);

			upload.setHeaderEncoding("UTF-8");
			upload.setSizeMax(maxUploadSize);
			upload.setFileSizeMax(maxUploadSize);
			String uploadDir = Constants.UPLOAD_PATH;

			if (ServletFileUpload.isMultipartContent(request)) {
				logger.debug("#################start upload process#########################");
				FileItemIterator iter = upload.getItemIterator(request);
				PrintWriter out = response.getWriter();
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					if (!item.isFormField()) {
						// 每天上传的文件根据日期存放在不同的文件夹
						String autoCreatedDateDirByParttern = "yyyy/MM/dd";
						String autoCreatedDateDir = DateFormatUtils.format(
								new java.util.Date(),
								autoCreatedDateDirByParttern);

						File savePath = new File(rootDir + uploadDir
								+ autoCreatedDateDir);
						if (!savePath.exists()) {
							savePath.mkdirs();
						}

						String fileId = UUID32.getUUID();
						String fileName = savePath + "/" + fileId;

						BlobItem dataFile = new BlobItemEntity();
						dataFile.setLastModified(System.currentTimeMillis());
						dataFile.setCreateBy(loginContext.getActorId());
						dataFile.setFileId(fileId);
						dataFile.setPath(uploadDir + autoCreatedDateDir + "/"
								+ fileId);
						dataFile.setFilename(item.getName());
						dataFile.setName(item.getName());
						dataFile.setContentType(item.getContentType());
						dataFile.setType(type);
						dataFile.setStatus(0);
						dataFile.setServiceKey(serviceKey);
						getBlobService().insertBlob(dataFile);

						inputStream = item.openStream();

						FileUtils.save(fileName, inputStream);

						logger.debug(fileName + " save ok.");

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
