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

package com.glaf.core.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.xml.XmlMappingReader;
import com.glaf.core.xml.XmlReader;

public class PlainTextParser implements Parser {

	public static void main(String[] args) throws Exception {
		String mappingFile = "./report/mapping/ProductFact.mapping.xml";
		String dataFile = "./report/data/PALC-PBRT-RSLT.txt";
		XmlReader reader = new XmlReader();
		TableDefinition tableDefinition = reader
				.read(new java.io.FileInputStream(mappingFile));
		if (tableDefinition != null) {
			if (DBUtils.tableExists(tableDefinition.getTableName())) {
				com.glaf.core.util.DBUtils.alterTable(tableDefinition);
			} else {
				com.glaf.core.util.DBUtils.createTable(tableDefinition);
			}
		}
		XmlMappingReader xmlReader = new XmlMappingReader();
		TableModel tableModel = xmlReader.read(new java.io.FileInputStream(
				mappingFile));
		System.out.println("start row no:" + tableModel.getStartRow());
		PlainTextParser textReader = new PlainTextParser();

		List<TableModel> rows = textReader.parse(tableModel,
				new java.io.FileInputStream(dataFile));

		ITableDefinitionService tableDefinitionService = ContextFactory
				.getBean("tableDefinitionService");
		tableDefinitionService.save(tableDefinition);

		ITableDataService tableDataService = ContextFactory
				.getBean("tableDataService");
		tableDataService.saveAll(tableModel.getTableName(), null, rows);
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

		List<ColumnModel> columns = new java.util.ArrayList<ColumnModel>();
		for (ColumnModel cell : tableModel.getColumns()) {
			if (cell.getPosition() > 0) {
				columns.add(cell);
			}
		}

		ColumnModel[] array = new ColumnModel[columns.size()];
		for (int i = 0, len = columns.size(); i < len; i++) {
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

	public List<TableModel> parse(TableModel tableModel,
			java.io.InputStream data) {
		List<TableModel> rows = new java.util.ArrayList<TableModel>();
		InputStreamReader isr = null;
		BufferedReader reader = null;
		int startRow = tableModel.getStartRow();
		String line = null;
		int currentRowNo = 0;
		try {
			isr = new InputStreamReader(data);
			reader = new BufferedReader(isr);
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

				/**
				 * 排除不需要处理的行
				 */
				if (tableModel.getExcludes() != null
						&& !tableModel.getExcludes().isEmpty()) {
					for (String exclude : tableModel.getExcludes()) {
						if (StringUtils.contains(line, exclude)) {
							continue;
						}
					}
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
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(isr);
			IOUtils.closeStream(reader);
		}
		return rows;
	}

}