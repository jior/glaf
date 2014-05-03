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

package com.glaf.dts.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.dts.domain.DataTransfer;

public class XmlWriter {

	public byte[] toBytes(DataTransfer dataTransfer) {
		Document doc = this.write(dataTransfer);
		byte[] bytes = Dom4jUtils.getBytesFromPrettyDocument(doc, "UTF-8");
		return bytes;
	}

	public InputStream toInputStream(DataTransfer dataTransfer) {
		Document doc = this.write(dataTransfer);
		byte[] bytes = Dom4jUtils.getBytesFromPrettyDocument(doc, "UTF-8");
		return new BufferedInputStream(new ByteArrayInputStream(bytes));
	}

	public Document write(DataTransfer dataTransfer) {
		List<DataTransfer> rows = new java.util.ArrayList<DataTransfer>();
		rows.add(dataTransfer);
		return this.write(rows);
	}

	public Document write(List<DataTransfer> rows) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("mapping");
		for (DataTransfer dataTransfer : rows) {
			Element element = root.addElement("entity");
			element.addAttribute("name", dataTransfer.getEntityName());
			element.addAttribute("package", dataTransfer.getPackageName());
			element.addAttribute("entityName", dataTransfer.getEntityName());
			element.addAttribute("className", dataTransfer.getClassName());
			element.addAttribute("table", dataTransfer.getTableName());
			element.addAttribute("title", dataTransfer.getTitle());
			element.addAttribute("englishTitle", dataTransfer.getEnglishTitle());
			element.addAttribute("parseType", dataTransfer.getParseType());
			element.addAttribute("parseClass", dataTransfer.getParseClass());
			element.addAttribute("filePrefix", dataTransfer.getFilePrefix());
			element.addAttribute("aggregationKeys",
					dataTransfer.getAggregationKeys());
			element.addAttribute("deleteFetch", dataTransfer.getDeleteFetch());
			element.addAttribute("insertOnly", dataTransfer.getInsertOnly());
			element.addAttribute("primaryKey", dataTransfer.getPrimaryKey());
			element.addAttribute("queryIds", dataTransfer.getQueryIds());
			element.addAttribute("split", dataTransfer.getSplit());
			element.addAttribute("stopWord", dataTransfer.getStopWord());
			element.addAttribute("temporaryFlag",
					dataTransfer.getTemporaryFlag());
			element.addAttribute("batchSize",
					String.valueOf(dataTransfer.getBatchSize()));
			element.addAttribute("startRow",
					String.valueOf(dataTransfer.getStartRow()));
			element.addAttribute("stopSkipRow",
					String.valueOf(dataTransfer.getStopSkipRow()));
			element.addAttribute("systemFlag", dataTransfer.getSystemFlag());

			ColumnDefinition idField = dataTransfer.getIdColumn();
			if (idField != null) {
				Element idElement = element.addElement("id");
				idElement.addAttribute("name", idField.getName());
				idElement.addAttribute("column", idField.getColumnName());
				idElement.addAttribute("type", idField.getType());
				idElement.addAttribute("title", idField.getTitle());
				idElement.addAttribute("englishTitle",
						idField.getEnglishTitle());
				if (idField.getLength() > 0) {
					idElement.addAttribute("length",
							String.valueOf(idField.getLength()));
				}
				if (idField.getValueExpression() != null) {
					idElement.addAttribute("valueExpression",
							idField.getValueExpression());
				}
			}

			List<ColumnDefinition> columns = dataTransfer.getColumns();
			for (ColumnDefinition field : columns) {
				if (idField != null
						&& StringUtils.equalsIgnoreCase(
								idField.getColumnName(), field.getColumnName())) {
					continue;
				}
				Element elem = element.addElement("property");
				elem.addAttribute("name", field.getName());
				elem.addAttribute("column", field.getColumnName());
				elem.addAttribute("type", field.getType());
				if (field.getTitle() != null) {
					elem.addAttribute("title", field.getTitle());
				}
				if (field.getEnglishTitle() != null) {
					elem.addAttribute("englishTitle", field.getEnglishTitle());
				}
				if (field.getValueExpression() != null) {
					elem.addAttribute("valueExpression",
							field.getValueExpression());
				}

				if (field.getLength() > 0) {
					elem.addAttribute("length",
							String.valueOf(field.getLength()));
				}
				if (field.getPosition() != null && field.getPosition() > 0) {
					elem.addAttribute("position",
							String.valueOf(field.getPosition()));
				}
				if (field.getPrecision() > 0) {
					elem.addAttribute("precision",
							String.valueOf(field.getPrecision()));
				}
				if (field.isUnique()) {
					elem.addAttribute("unique",
							String.valueOf(field.isUnique()));
				}
				if (field.isSearchable()) {
					elem.addAttribute("searchable",
							String.valueOf(field.isSearchable()));
				}
				if (!field.isNullable()) {
					elem.addAttribute("nullable",
							String.valueOf(field.isNullable()));
				}
				if (field.isEditable()) {
					elem.addAttribute("editable",
							String.valueOf(field.isEditable()));
				}
				elem.addAttribute("displayType",
						String.valueOf(field.getDisplayType()));
			}
		}
		return doc;
	}
}
