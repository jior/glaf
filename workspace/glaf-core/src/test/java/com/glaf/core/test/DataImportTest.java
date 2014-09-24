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

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.parse.CsvTextParser;
import com.glaf.core.parse.ParserFacede;
import com.glaf.core.parse.PlainTextParser;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.xml.XmlMappingReader;
import com.glaf.core.xml.XmlReader;
import com.glaf.test.AbstractTest;

public class DataImportTest extends AbstractTest {

	@Test
	public void testImportCsv() throws IOException {
		String mappingFile = "./report/mapping/CarType.mapping.xml";
		String dataFile = "./report/data/SDCMS-PBRT-SPCR.csv";
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
		CsvTextParser textReader = new CsvTextParser();
		List<TableModel> rows = textReader.parse(tableModel,
				new java.io.FileInputStream(dataFile));
		for (TableModel row : rows) {
			System.out.println(row.toString());
		}
		ITableDefinitionService tableDefinitionService = ContextFactory
				.getBean("tableDefinitionService");
		tableDefinitionService.save(tableDefinition);

		ITableDataService tableDataService = ContextFactory
				.getBean("tableDataService");
		tableDataService.saveAll(tableModel.getTableName(), null, rows);
	}

	@Test
	public void testImportText() throws IOException {
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

	@Test
	public void testImportXls() {
		String mappingFile = "./report/mapping/BaseData.mapping.xml";
		String dataFile = "./report/data/BaseData.xls";
		ContextFactory.hasBean("dataSource");
		ParserFacede parser = new ParserFacede();
		List<TableModel> rows = parser.parse(mappingFile, dataFile, null, true);
		if (rows != null && !rows.isEmpty()) {
			JSONArray array = new JSONArray();
			for (TableModel model : rows) {
				JSONObject jsonObject = new JSONObject();
				List<ColumnModel> columns = model.getColumns();
				if (columns != null && !columns.isEmpty()) {
					for (ColumnModel col : columns) {
						if (col.getName() != null) {
							jsonObject.put(col.getName(), col.getValue());
						}
						if (col.getColumnName() != null) {
							jsonObject.put(col.getColumnName().toLowerCase(),
									col.getValue());
						}
					}
				}
				if (model.getIdColumn() != null) {
					ColumnModel col = model.getIdColumn();
					if (col.getName() != null) {
						jsonObject.put(col.getName(), col.getValue());
					}
					if (col.getColumnName() != null) {
						jsonObject.put(col.getColumnName().toLowerCase(),
								col.getValue());
					}
				}
				array.put(jsonObject);
			}
			System.out.println(array.toString('\n'));
		}
	}

}
