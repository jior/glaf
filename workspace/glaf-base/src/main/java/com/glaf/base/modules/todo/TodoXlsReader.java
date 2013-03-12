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

package com.glaf.base.modules.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.glaf.core.todo.Todo;
import com.glaf.core.util.Tools;
import com.glaf.base.utils.ParamUtil;

public class TodoXlsReader {

	public List<Todo> readXls(java.io.InputStream inputStream) {
		List<Todo> todos = new ArrayList<Todo>();
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(1);
		Map<Integer, String> keyMap = new HashMap<Integer, String>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int cells = row.getPhysicalNumberOfCells();
		for (int colIndex = 0; colIndex < cells; colIndex++) {
			HSSFCell cell = row.getCell(colIndex);
			keyMap.put(colIndex, cell.getStringCellValue());
		}
		Set<String> keys = new HashSet<String>();
		for (int rowIndex = 2; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
			HSSFRow rowx = sheet.getRow(rowIndex);
			if (rowx == null) {
				continue;
			}
			// System.out.println();
			dataMap.clear();
			for (int colIndex = 0; colIndex < cells; colIndex++) {
				String fieldName = keyMap.get(colIndex);
				HSSFCell cell = rowx.getCell(colIndex);
				if (cell == null) {
					continue;
				}
				Object cellValue = null;
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_FORMULA:
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					cellValue = cell.getBooleanCellValue();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					cellValue = cell.getNumericCellValue();
					break;
				case HSSFCell.CELL_TYPE_STRING:
					if (StringUtils.isNotEmpty(cell.getRichStringCellValue()
							.getString())) {
						cellValue = cell.getRichStringCellValue().getString();
					}
					break;
				default:
					if (StringUtils.isNotEmpty(cell.getStringCellValue())) {
						cellValue = cell.getStringCellValue();
					}
					break;
				}
				if (cellValue != null) {
					dataMap.put(fieldName, cellValue);
					if ("id".equals(fieldName)) {
						cellValue = cellValue.toString().substring(0,
								cellValue.toString().indexOf("."));
						dataMap.put(fieldName, cellValue);
					}
				}
			}
			 
			if (dataMap.get("code") != null) {
	 
				String id = ParamUtil.getString(dataMap, "id");
				if (!keys.contains(ParamUtil.getString(dataMap, "code"))) {
					if (id != null && StringUtils.isNotEmpty(id)) {
						Todo model = new Todo();
						Tools.populate(model, dataMap);
						if (ParamUtil.getInt(dataMap, "limitDay") > 0) {
							model.setLimitDay(ParamUtil.getInt(dataMap,
									"limitDay"));
						}
						todos.add(model);
						keys.add(model.getCode());
					}
				}
			}
		}

		return todos;
	}
}