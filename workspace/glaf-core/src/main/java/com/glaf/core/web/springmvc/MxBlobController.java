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

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.base.DataFile;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ISysLogService;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;

@Controller("/lob/lob")
@RequestMapping("/lob/lob")
public class MxBlobController {
	protected final static Log logger = LogFactory
			.getLog(MxBlobController.class);

	protected IBlobService blobService;

	protected ISysLogService sysLogService;

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String fileId = request.getParameter("fileId");
		if (StringUtils.isNotEmpty(fileId)) {
			blobService.deleteBlobByFileId(fileId);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/download")
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) {
		String fileId = request.getParameter("fileId");
		if (StringUtils.isNotEmpty(fileId)) {
			logger.debug("fileId:" + fileId);
			DataFile blob = blobService.getBlobByFileId(fileId);
			if (blob != null) {
				logger.debug("id:" + blob.getId());
				InputStream inputStream = null;
				try {
					inputStream = blobService.getInputStreamById(blob.getId());
					ResponseUtils.download(request, response, inputStream,
							blob.getFilename());
				} finally {
					blob.setData(null);
					blob = null;
					try {
						if (inputStream != null) {
							inputStream.close();
							inputStream = null;
						}
					} catch (Exception ex) {

					}
				}
			}
		}
		return null;
	}

	@RequestMapping("/files")
	public ModelAndView files(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String businessKey = request.getParameter("businessKey");
		if (StringUtils.isNotEmpty(businessKey)) {
			List<DataFile> dataList = blobService.getBlobList(businessKey);
			modelMap.put("dataList", dataList);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		return new ModelAndView("/modules/main/lob/files", modelMap);
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setSysLogService(ISysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	@RequestMapping("/showUpload")
	public ModelAndView showUpload(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String businessKey = request.getParameter("businessKey");
		if (StringUtils.isNotEmpty(businessKey)) {
			List<DataFile> dataList = blobService.getBlobList(businessKey);
			modelMap.put("dataList", dataList);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		return new ModelAndView("/modules/main/lob/showUpload", modelMap);
	}

}