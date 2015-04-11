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

package com.glaf.core.test;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.TreeModel;
import com.glaf.core.domain.SysDataTable;
import com.glaf.core.query.SysDataTableQuery;
import com.glaf.core.service.ISysDataTableService;
import com.glaf.core.xml.XmlReader;
import com.glaf.test.AbstractTest;

public class SysDataTableTest extends AbstractTest {

	protected ISysDataTableService sysDataTableService;

	@Test
	public void testGetPageData() {
		sysDataTableService = super.getBean("sysDataTableService");
		SysDataTableQuery query = new SysDataTableQuery();
		query.setTablename("sys_scheduler");
		query.setServiceKey("sys_scheduler");
		DataRequest dataRequest = new DataRequest();
		query.setDataRequest(dataRequest);
		JSONObject json = sysDataTableService.getPageTableData(0, 100, query);
		System.out.println(json.toJSONString());
	}

	@Test
	public void testGetTreeModels() {
		ISysDataTableService svc = super.getBean("sysDataTableService");
		List<TreeModel> list = svc.getTreeModels("tree_test", 0L);
		logger.debug(list);
	}

	@Test
	public void testImportMappingData() throws IOException {
		sysDataTableService = super.getBean("sysDataTableService");
		File path = FileUtils.getFile("mapping");
		if (path.exists() && path.isDirectory()) {
			XmlReader reader = new XmlReader();
			File[] filelist = path.listFiles();
			if (filelist != null) {
				for (int i = 0, len = filelist.length; i < len; i++) {
					File file = filelist[i];
					if (file.getAbsolutePath().endsWith(".mapping.xml")) {
						SysDataTable dataTable = reader
								.getSysDataTable(new FileInputStream(file));
						sysDataTableService.saveDataTable(dataTable);
						Collections.sort(dataTable.getFields());
						System.out.println(dataTable.getFields());
					}
				}
			}
		}
	}

	public void testSaveData() {
		ISysDataTableService svc = super.getBean("sysDataTableService");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// dataMap.put("id", 1L);
		dataMap.put("parentId", 0L);
		dataMap.put("name", "分类树");
		dataMap.put("code", "/");
		dataMap.put("ordinal", 0);
		dataMap.put("description", "分类树");
		svc.saveData("tree_test", dataMap);
	}

	public void testSaveJsonArrayData() {
		ISysDataTableService svc = super.getBean("sysDataTableService");
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < 10; i++) {
			JSONObject jsonObject = new JSONObject();
			// jsonObject.put("id", i);
			jsonObject.put("parentId", 0L);
			jsonObject.put("name", "分类树");
			jsonObject.put("code", "/");
			jsonObject.put("ordinal", 0);
			jsonObject.put("description", "分类树");
			array.add(jsonObject);
		}
		result.put("tree_test", array);
		logger.debug(result.toJSONString());
		svc.saveJsonData("tree_test", result);
	}

	@Test
	public void testSaveJsonData() {
		ISysDataTableService svc = super.getBean("sysDataTableService");
		JSONObject result = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		// jsonObject.put("id", 1L);
		jsonObject.put("parentId", 0L);
		jsonObject.put("name", "分类树");
		jsonObject.put("code", "/");
		jsonObject.put("ordinal", 0);
		jsonObject.put("description", "分类树");
		result.put("tree_test", jsonObject);
		logger.debug(result.toJSONString());
		svc.saveJsonData("tree_test", result);
	}

}
