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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.xml.MetadataXmlReader;

public class CsvTextParser implements TextParser {

	public static void main(String[] args) throws Exception {
		String mappingFile = "./report/mapping/CarType.mapping.xml";
		String dataFile = "./report/data/SDCMS-PBRT-SPCR-1212280030.csv";
		MetadataXmlReader reader = new MetadataXmlReader();
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
		CsvTextParser textReader = new CsvTextParser();
		InputStreamReader is = new InputStreamReader(
				new java.io.FileInputStream(dataFile));
		List<TableModel> rows = textReader.read(tableModel, is);
		for (TableModel row : rows) {
			System.out.println(row.toString());
		}
		ITableDefinitionService tableDefinitionService = ContextFactory
				.getBean("tableDefinitionService");
		tableDefinitionService.save(tableDefinition);

		ITableDataService tableDataService = ContextFactory
				.getBean("tableDataService");
		tableDataService.saveAll(tableModel.getTableName(), rows);
	}

	public TableModel parseLine(TableModel tableModel, String line) {
		TableModel row = new TableModel();

		row.setIdColumn(tableModel.getIdColumn());
		row.setTableName(tableModel.getTableName());
		row.setAggregationKeys(tableModel.getAggregationKeys());

		SimpleDateFormat formatter = null;
		List<String> pieces = this.split(line, ",");
		for (ColumnModel cell : tableModel.getColumns()) {
			if (cell.getPosition() > 0 && cell.getPosition() <= pieces.size()) {
				ColumnModel col = new ColumnModel();
				col.setColumnName(cell.getColumnName());
				col.setValueExpression(cell.getValueExpression());
				String value = pieces.get(cell.getPosition() - 1);
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

	@SuppressWarnings("unchecked")
	public List<String> split(String text, String delimiter) {
		if (delimiter == null) {
			throw new RuntimeException("delimiter is null");
		}
		if (text == null) {
			return Collections.EMPTY_LIST;
		}
		List<String> pieces = new ArrayList<String>();
		int start = 0;
		int end = text.indexOf(delimiter);
		while (end != -1) {
			pieces.add(text.substring(start, end));
			start = end + delimiter.length();
			end = text.indexOf(delimiter, start);
		}
		if (start < text.length()) {
			String temp = text.substring(start);
			if (temp != null && temp.length() > 0) {
				pieces.add(temp);
			}
		}
		return pieces;
	}

}