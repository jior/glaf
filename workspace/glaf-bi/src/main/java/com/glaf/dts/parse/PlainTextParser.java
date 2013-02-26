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

package com.glaf.dts.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.xml.MetadataXmlReader;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;

public class PlainTextParser implements TextParser {

	public static void main(String[] args) throws Exception {
		String mappingFile = "./report/mapping/ProductFact.mapping.xml";
		String dataFile = "./report/data/PALC-PBRT-RSLT-1301040950.txt";
		MetadataXmlReader reader = new MetadataXmlReader();
		TableDefinition tableDefinition = reader
				.read(new java.io.FileInputStream(mappingFile));
		if (tableDefinition != null) {
			com.glaf.core.util.DBUtils.createTable(tableDefinition);
		}
		XmlMappingReader xmlReader = new XmlMappingReader();
		TableModel tableModel = xmlReader.read(new java.io.FileInputStream(
				mappingFile));
		System.out.println("start row no:" + tableModel.getStartRow());
		PlainTextParser textReader = new PlainTextParser();
		InputStreamReader is = new InputStreamReader(
				new java.io.FileInputStream(dataFile));
		List<TableModel> rows = textReader.read(tableModel, is);

		ITableDefinitionService tableDefinitionService = ContextFactory
				.getBean("tableDefinitionService");
		tableDefinitionService.save(tableDefinition);

		ITableDataService tableDataService = ContextFactory
				.getBean("tableDataService");
		tableDataService.saveAll(tableModel.getTableName(), rows);
	}

	public void bubbleSort(ColumnModel[] array) {
		ColumnModel temp = null;
		for (int i = 0; i < array.length; ++i) {
			for (int j = array.length - 1; j > i; --j) {
				if (array[j].getPosition() < array[j - 1].getPosition()) {
					temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				}
			}
		}
	}

	public TableModel parseLine(TableModel tableModel, String line) {
		TableModel row = new TableModel();

		row.setIdColumn(tableModel.getIdColumn());
		row.setTableName(tableModel.getTableName());
		row.setAggregationKeys(tableModel.getAggregationKeys());

		List<ColumnModel> columns = new ArrayList<ColumnModel>();
		for (ColumnModel cell : tableModel.getColumns()) {
			if (cell.getPosition() > 0) {
				columns.add(cell);
			}
		}

		ColumnModel[] array = new ColumnModel[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			array[i] = columns.get(i);
		}

		this.bubbleSort(array);

		int startIndex = 0;
		SimpleDateFormat formatter = null;
		for (ColumnModel cell : array) {
			if (cell.getPosition() > 0 && startIndex < line.length()) {
				String value = line.substring(startIndex,
						startIndex + cell.getLength());
				startIndex = startIndex + cell.getLength();
				ColumnModel col = new ColumnModel();
				col.setColumnName(cell.getColumnName());
				col.setValueExpression(cell.getValueExpression());
				col.setStringValue(value);
				col.setValue(value);
				if (StringUtils.isNotEmpty(value)) {
					value = value.trim();
					col.setStringValue(value);
					col.setValue(value);
					String type = cell.getType();
					if ("Boolean".equals(type)) {
						col.setBooleanValue(Boolean.valueOf(value));
						col.setValue(Boolean.valueOf(value));
					} else if ("Integer".equals(type)) {
						if (StringUtils.isNotEmpty(value)) {
							col.setIntValue(Integer.parseInt(value));
							col.setValue(Integer.parseInt(value));
						} else {
							col.setValue(null);
						}
					} else if ("Long".equals(type)) {
						if (StringUtils.isNotEmpty(value)) {
							col.setLongValue(Long.parseLong(value));
							col.setValue(Long.parseLong(value));
						} else {
							col.setValue(null);
						}
					} else if ("Double".equals(type)) {
						if (StringUtils.isNotEmpty(value)) {
							col.setDoubleValue(Double.parseDouble(value));
							col.setValue(Double.parseDouble(value));
						} else {
							col.setValue(null);
						}
					} else if ("Date".equals(type)) {
						formatter = new SimpleDateFormat(cell.getFormat());
						try {
							Date date = formatter.parse(value);
							col.setDateValue(date);
							col.setValue(date);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				row.addColumn(col);
			}
		}
		return row;
	}

	public List<TableModel> read(TableModel tableModel, Reader data)
			throws IOException {
		List<TableModel> rows = new ArrayList<TableModel>();
		BufferedReader reader = new BufferedReader(data);
		int startRow = tableModel.getStartRow();
		String line = null;
		int currentRowNo = 0;
		while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			/**
			 * 如果读取到指定的结束指令，结束循环
			 */
			if (StringUtils.contains(line, tableModel.getStopWord())) {
				break;
			}

			if (StringUtils.isEmpty(line)) {
				continue;
			}

			if (currentRowNo >= startRow) {
				/**
				 * 如果当前数据的长度大于规定的最小长度
				 */
				if (line.length() >= tableModel.getMinLength()) {
					try {
						TableModel model = this.parseLine(tableModel, line);
						rows.add(model);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

			currentRowNo++;
		}
		return rows;
	}

}