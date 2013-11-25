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

package com.glaf.base.modules.others.springmvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;

import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.others.model.Attachment;
import com.glaf.base.modules.others.service.AttachmentService;

import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

@Controller
@RequestMapping("/others/attachment.do")
public class AttachmentController {
	private static final Log logger = LogFactory
			.getLog(AttachmentController.class);

	private AttachmentService attachmentService;

	/**
	 * 提交删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=batchDelete")
	public ModelAndView batchDelete(ModelMap modelMap,
			HttpServletRequest request) {
		boolean ret = true;
		long[] ids = ParamUtil.getLongParameterValues(request, "id");
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				Attachment attachment = attachmentService.find(id);
				if (attachment != null) {
					try {
						String filePath = SystemProperties.getAppPath()
								+ Constants.UPLOAD_DIR + attachment.getUrl();
						logger.debug("prepare delete file:" + filePath);
						File file = new File(filePath);
						if (file.exists() && file.isFile()) {
							file.delete();
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			ret = attachmentService.deleteAll(ids);
		}

		ViewMessages messages = new ViewMessages();

		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"attach.del_success"));
		} else { // 删除失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"attach.del_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2", modelMap);
	}

	/**
	 * 下载附件
	 * 
	 */
	@RequestMapping(params = "method=download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long id = ParamUtil.getLongParameter(request, "id", 0);

		Attachment attachment = attachmentService.find(id);
		if (attachment == null) {
			throw new IllegalArgumentException("Attachment " + id
					+ " File not found.");
		}

		String filePath = SystemProperties.getAppPath() + Constants.UPLOAD_DIR
				+ attachment.getUrl();
		String filename = attachment.getName();
		File file = new File(filePath);
		ResponseUtils.download(request, response, new FileInputStream(file),
				filename);
	}

	/**
	 * 保存附件
	 * 
	 */
	@RequestMapping(params = "method=save")
	public void save(HttpServletRequest request,
			@RequestParam(value = "referId") long referId,
			@RequestParam(value = "referType") int referType,
			@RequestParam(value = "json") String json) {
		if (StringUtils.isNotEmpty(json)) {
			json = RequestUtils.decodeString(json);
			JSONObject jsonObject = (JSONObject) JSON.parse(json);
			Attachment bean = new Attachment();
			String filename = jsonObject.getString("filename");
			bean.setName(filename);
			bean.setUrl(jsonObject.getString("path"));
			bean.setCreateDate(jsonObject.getDate("createDate"));
			bean.setCreateId(RequestUtil.getLoginUser(request).getId());
			bean.setReferId(referId);
			bean.setReferType(referType);

			attachmentService.create(bean);
		}
	}

	/**
	 * 保存附件
	 * 
	 */
	@RequestMapping(params = "method=saveAdd")
	public void saveAdd(HttpServletRequest request,
			@RequestParam(value = "referId") long referId,
			@RequestParam(value = "referType") int referType,
			@RequestParam(value = "url") String url) {
		Attachment bean = new Attachment();
		int i = url.lastIndexOf("\\");
		if (i > 0) {
			bean.setName(url.substring(i + 1));
		}
		bean.setCreateId(RequestUtil.getLoginUser(request).getId());
		bean.setCreateDate(new Date());
		bean.setReferId(referId);
		bean.setReferType(referType);
		bean.setUrl(url);
		attachmentService.create(bean);
	}

	@javax.annotation.Resource
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
		logger.info("setAttachmentService");
	}

	/**
	 * 显示附件数量页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=showCount")
	public ModelAndView showCount(ModelMap modelMap, HttpServletRequest request) {
		String referId = ParamUtil.getParameter(request, "referId");
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		String[] referIdArray = StringUtils.split(referId, ",");
		long[] longReferId = new long[referIdArray.length];
		long createId = RequestUtil.getLoginUser(request).getId();
		for (int i = 0; i < referIdArray.length; i++) {
			longReferId[i] = Long.parseLong(referIdArray[i]);
		}
		int count = attachmentService.getAttachmentCount(longReferId,
				referType, createId);
		String Strcount = count + "";
		request.setAttribute("count", Strcount);

		String x_view = ViewProperties.getString("attachment.showCount");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/others/attachment/showCount",
				modelMap);
	}

	/**
	 * 显示附件列表
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=getCount")
	@ResponseBody
	public byte[] getCount(ModelMap modelMap, HttpServletRequest request) {
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		long createId = RequestUtil.getLoginUser(request).getId();
		long[] longReferId = new long[1];
		longReferId[0] = referId;
		int count = attachmentService.getAttachmentCount(longReferId,
				referType, createId);
		String countStr = count + "";
		return ResponseUtils.responseJsonResult(true, countStr);
	}

	/**
	 * 显示附件列表
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(ModelMap modelMap, HttpServletRequest request) {
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		int viewType = ParamUtil.getIntParameter(request, "viewType", 0);
		int modifyType = ParamUtil.getIntParameter(request, "modifyType", 0);
		long createId = RequestUtil.getLoginUser(request).getId();
		if (viewType == 0) {
			createId = 0;
		}
		if (viewType == 1 && modifyType == 1) {
			createId = 0;
		}

		// request.setAttribute("list",attachmentService.getAttachmentList(referId,
		// referType));
		request.setAttribute("list", attachmentService.getAttachmentList(
				referId, referType, createId));

		String x_view = ViewProperties
				.getString("attachment.showList.referType");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		if (viewType == 1) {
			return new ModelAndView(
					"/modules/others/attachment/attachment_list", modelMap);
		} else {
			return new ModelAndView(
					"/modules/others/attachment/attachment_list2", modelMap);
		}
	}

	/**
	 * 显示附件列表 合同意见交流添加附件,只有上传者才能删除附件
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=showList2")
	public ModelAndView showList2(ModelMap modelMap, HttpServletRequest request) {
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);

		request.setAttribute("list",
				attachmentService.getAttachmentList(referId, referType));

		String x_view = ViewProperties.getString("attachment.showList2");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/others/attachment/attachment_list2",
				modelMap);
	}

	/**
	 * 显示附件列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=showLists")
	public ModelAndView showLists(ModelMap modelMap, HttpServletRequest request) {
		String referIds = ParamUtil.getParameter(request, "referId");
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		String[] referIdArray = StringUtils.split(referIds, ",");
		long[] referId = new long[referIdArray.length];
		for (int i = 0; i < referIdArray.length; i++) {
			referId[i] = Long.parseLong(referIdArray[i]);
		}

		request.setAttribute("list",
				attachmentService.getAttachmentList(referId, referType));

		String x_view = ViewProperties.getString("attachment.showLists");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/others/attachment/attachment_list2",
				modelMap);
	}

	/**
	 * 显示附件上传页面
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=showResult")
	public ModelAndView showResult(ModelMap modelMap, HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/others/attachment/result", modelMap);
	}

	/**
	 * 显示附件上传页面
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=showUpload")
	public ModelAndView showUpload(ModelMap modelMap, HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/others/attachment/showUpload",
				modelMap);
	}

}