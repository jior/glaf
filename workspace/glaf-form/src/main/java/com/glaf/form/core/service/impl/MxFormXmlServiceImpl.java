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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.DataModel;
import com.glaf.core.identity.User;
import com.glaf.core.query.DataModelQuery;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.container.MxFormContainer;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.graph.def.FormNode;
import com.glaf.form.core.service.FormDataService;
import com.glaf.form.core.service.FormXmlService;

@Service("formXmlService")
@Transactional(readOnly = true)
public class MxFormXmlServiceImpl implements FormXmlService {

	protected FormDataService formDataService;

	public Document getPage(int pageNo, int pageSize,
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
		Map<String, Object> rowMap = new java.util.HashMap<String, Object>();
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("List");
		root.addAttribute("app_name", formApplication.getName());
		root.addAttribute("title", formApplication.getTitle());
		root.addAttribute("pageNo", String.valueOf(page.getCurrentPage()));
		root.addAttribute("pageSize", String.valueOf(page.getPageSize()));
		root.addAttribute("totalCount", String.valueOf(page.getTotal()));
		root.addAttribute("start", String.valueOf((page.getCurrentPage() - 1)
				* page.getPageSize()));
		root.addAttribute("limit", String.valueOf(page.getPageSize()));

		List<FormNode> nodes = formDefinition.getNodes();
		if (nodes != null && nodes.size() > 0) {
			Element element = root.addElement("Header");
			for (FormNode node : nodes) {
				Element elem = element.addElement("Column");
				elem.addAttribute("name", node.getName());
				elem.addAttribute("title", node.getTitle());
				elem.addAttribute("nodeType", node.getNodeType());
				elem.addAttribute("size", String.valueOf(node.getSize()));
				elem.addAttribute("maxLength",
						String.valueOf(node.getMaxLength()));
			}
		}

		List<Object> rows = page.getRows();
		for (Object object : rows) {
			if (object instanceof DataModel) {
				DataModel dataModel = (DataModel) object;
				Element element = root.addElement("Row");
				element.addAttribute("id", String.valueOf(dataModel.getId()));
				element.addAttribute("businessKey", dataModel.getBusinessKey());
				rowMap.clear();
				rowMap = Tools.getDataMap(dataModel);
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
				Set<Entry<String, Object>> entrySet = rowMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String name = entry.getKey();
					Object value = entry.getValue();
					if (value != null) {
						Element elem = element.addElement(name);
						if (value instanceof Date) {
							String date = DateUtils.getDate((Date) value);
							elem.setText(date);
						} else {
							elem.setText(value.toString());
						}
					}
				}
			}
		}

		return doc;
	}

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}
}