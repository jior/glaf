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
package com.glaf.form.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.DataModel;
import com.glaf.core.identity.User;

import com.glaf.core.query.DataModelQuery;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.Tools;

import com.glaf.form.core.container.MxFormContainer;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.graph.def.FormNode;
import com.glaf.form.core.service.*;

@Service("formJsonService")
@Transactional(readOnly = true)
public class MxFormJsonServiceImpl implements FormJsonService {

	protected FormDataService formDataService;

	public MxFormJsonServiceImpl() {

	}

	@Transactional(readOnly = true)
	public JSONObject getPage(int pageNo, int pageSize,
			LoginContext loginContext, String app_name,
			Map<String, Object> paramMap) {
		FormContext formContext = new FormContext();
		FormApplication formApplication = formDataService
				.getFormApplicationByName(app_name);
		FormDefinition formDefinition = formDataService
				.getLatestFormDefinition(formApplication.getFormName());
		formContext.setLoginContext(loginContext);
		formContext.setFormApplication(formApplication);
		formContext.setFormDefinition(formDefinition);
		paramMap.put("actorId", loginContext.getActorId());
		formContext.getDataMap().putAll(paramMap);

		DataModelQuery query = new DataModelQuery();
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		query.setParameter(paramMap);
		query.setLoginContext(loginContext);
		Map<String, User> userMap = IdentityFactory.getUserMap();
		Paging page = MxFormContainer.getContainer().getPageDataModel(
				formApplication.getId(), query);

		List<Object> list = new ArrayList<Object>();
		List<Object> rows = page.getRows();
		for (Object object : rows) {
			if (object instanceof DataModel) {
				DataModel dataModel = (DataModel) object;
				Map<String, Object> rowMap = Tools.getDataMap(dataModel);
				if (dataModel.getDataMap() != null) {
					rowMap.putAll(dataModel.getDataMap());
				}

				String applyActorId = ParamUtils.getString(rowMap, "actorId");

				User userProfile = userMap.get(applyActorId);
				if (userProfile != null) {
					rowMap.put("applyUser", userProfile.getName());
					// rowMap.put("applyDept", userProfile.getDeptName());
				} else {
					rowMap.put("applyUser", applyActorId);
					rowMap.put("applyDept", "");
				}
				list.add(rowMap);
			}
		}

		Map<String, Object> pageInfo = new HashMap<String, Object>();
		// 当前页数设置
		pageInfo.put(
				"start",
				Integer.valueOf((page.getCurrentPage() - 1)
						* page.getPageSize()));
		pageInfo.put("pageNo", Integer.valueOf(page.getCurrentPage()));

		// 每页记录数
		pageInfo.put("limit", Integer.valueOf(page.getPageSize()));
		pageInfo.put("pageSize", Integer.valueOf(page.getPageSize()));

		// 总数据量设置
		pageInfo.put("totalCount", Integer.valueOf(page.getTotal()));
		pageInfo.put("rows", list);

		List<FormNode> nodes = formDefinition.getNodes();
		if (nodes != null && nodes.size() > 0) {
			List<Object> list2 = new ArrayList<Object>();
			for (FormNode node : nodes) {
				Map<String, Object> rowMap = new HashMap<String, Object>();
				rowMap.put("name", node.getName());
				rowMap.put("title", node.getTitle());
				rowMap.put("nodeType", node.getNodeType());
				rowMap.put("size", String.valueOf(node.getSize()));
				rowMap.put("maxLength", String.valueOf(node.getMaxLength()));
				list2.add(rowMap);
			}
			pageInfo.put("header", list2);
		}

		JSONObject object = new JSONObject(pageInfo);

		return object;
	}

	@Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

}