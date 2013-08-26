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
package com.glaf.oa.base.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;

import com.glaf.core.util.*;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.model.ActivityInstance;

@Controller("/oa/baseData")
@RequestMapping("/oa/baseData")
public class BaseDataController {
	protected static final Log logger = LogFactory
			.getLog(BaseDataController.class);

	@RequestMapping("/getAllCompanyJson")
	@ResponseBody
	public byte[] getAllCompanyJson(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		JSONArray rowsJSON = new JSONArray();
		JSONObject result = null;

		JSONObject result0 = new JSONObject();
		result0.put("code", "");
		result0.put("name", "");
		rowsJSON.add(result0);
		List<BaseDataInfo> BaseDataInfoList = BaseDataManager.getInstance()
				.getBaseData("area");
		for (BaseDataInfo baseDataInfo : BaseDataInfoList) {

			List<BaseDataInfo> BaseDataInfoList2 = BaseDataManager
					.getInstance().getBaseData(baseDataInfo.getCode());
			for (BaseDataInfo baseDataInfo2 : BaseDataInfoList2) {
				result = new JSONObject();
				result.put("code", baseDataInfo2.getCode());
				result.put("name", baseDataInfo2.getName());

				rowsJSON.add(result);
			}

		}
		return rowsJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/getJBPMActivityJson")
	@ResponseBody
	public byte[] getJBPMActivityJson(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		String str_processInstanceId = request
				.getParameter("processInstanceId");
		if (str_processInstanceId.contains(".")) {
			str_processInstanceId = str_processInstanceId.substring(0,
					str_processInstanceId.lastIndexOf("."));
		}
		Long processInstanceId = Long.valueOf(str_processInstanceId);

		JSONObject result = null;
		List<ActivityInstance> list = ProcessContainer.getContainer()
				.getActivityInstances(processInstanceId);
		String starDateStr = "";
		result = new JSONObject();
		JSONArray rowsJSON = new JSONArray();
		for (ActivityInstance activityInstance : list) {

			JSONObject jsonObject = new JSONObject();
			// 任务名称
			jsonObject.put("taskName", activityInstance.getTaskDescription());
			// 执行者Id
			jsonObject.put("actorId", activityInstance.getActorId());
			// 执行者name
			jsonObject
					.put("actorName",
							BaseDataManager.getInstance().getStringValue(
									activityInstance.getActorId(), "SYS_USERS"));
			// 开始时间
			jsonObject.put("starDate", starDateStr.length() > 0 ? starDateStr
					: (DateUtils.getDate(activityInstance.getDate())));
			// 结束时间
			jsonObject.put("endDate",
					DateUtils.getDate(activityInstance.getDate()));
			// 审批结果
			jsonObject.put(
					"isAgree",
					activityInstance.getIsAgree() != null ? activityInstance
							.getIsAgree() : "");
			// 审批意见
			jsonObject.put(
					"content",
					activityInstance.getContent() != null ? activityInstance
							.getContent() : "");

			String subcontent = activityInstance.getContent() != null ? activityInstance
					.getContent() : "";
			if (subcontent.length() > 20) {
				subcontent = subcontent.substring(0, 20);
				subcontent = subcontent + "...";
			}
			jsonObject.put("subcontent", subcontent);

			starDateStr = DateUtils.getDate(activityInstance.getDate());

			rowsJSON.add(jsonObject);
		}
		result.put("rows", rowsJSON);
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/getSingleUserJson")
	@ResponseBody
	public byte[] getSingleUserJson(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		JSONObject result = new JSONObject();

		String userCode = "";
		if (request.getParameter("userCode") != null
				&& !request.getParameter("userCode").equals("")) {
			userCode = request.getParameter("userCode");
			SysUser syUser = BaseDataManager.getInstance().getSysUserService()
					.findByAccount(userCode);
			result = syUser.toJsonObject();

			SysDepartment sysDepartment = BaseDataManager.getInstance()
					.getSysDepartmentService().findById(syUser.getDeptId());
			result.put("deptCode", sysDepartment.getCode());
			result.put("area", sysDepartment.getCode().substring(0, 2));
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/getUserJson")
	@ResponseBody
	public byte[] getUserJson(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		JSONArray rowsJSON = new JSONArray();
		JSONObject result = null;

		JSONObject result0 = new JSONObject();
		result0.put("code", "");
		result0.put("name", "");
		rowsJSON.add(result0);
		List<BaseDataInfo> BaseDataInfoList = BaseDataManager.getInstance()
				.getBaseData("SYS_USERS");
		for (BaseDataInfo baseDataInfo : BaseDataInfoList) {
			if ("root".equals(baseDataInfo.getCode())) {
				continue;
			}

			result = new JSONObject();
			result.put("code", baseDataInfo.getCode());
			result.put("name", baseDataInfo.getName());

			rowsJSON.add(result);
		}
		return rowsJSON.toJSONString().getBytes("UTF-8");
	}

}