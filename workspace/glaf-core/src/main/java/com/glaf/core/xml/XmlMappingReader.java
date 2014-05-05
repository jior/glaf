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

package com.glaf.core.xml;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;

public class XmlMappingReader {

	public TableModel read(java.io.InputStream inputStream) {
		TableModel tableModel = new TableModel();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			Element element = root.element("entity");
			if (element != null) {
				tableModel.setEntityName(element.attributeValue("name"));
				tableModel.setPrimaryKey(element.attributeValue("primaryKey"));
				tableModel.setTableName(element.attributeValue("table"));
				tableModel.setTitle(element.attributeValue("title"));
				tableModel.setStopWord(element.attributeValue("stopWord"));
				tableModel.setPackageName(element.attributeValue("package"));
				tableModel.setEnglishTitle(element
						.attributeValue("englishTitle"));
				tableModel.setFilePrefix(element.attributeValue("filePrefix"));
				tableModel.setParseType(element.attributeValue("parseType"));
				tableModel.setParseClass(element.attributeValue("parseClass"));
				tableModel.setAggregationKey(element
						.attributeValue("aggregationKeys"));
				tableModel.setSplit(element.attributeValue("split"));
				if (StringUtils.equals(element.attributeValue("insertOnly"),
						"true")) {
					tableModel.setInsertOnly(true);
				}

				String startRow = element.attributeValue("startRow");
				if (StringUtils.isNumeric(startRow)) {
					tableModel.setStartRow(Integer.parseInt(startRow));
				}
				String stopSkipRow = element.attributeValue("stopSkipRow");
				if (StringUtils.isNumeric(stopSkipRow)) {
					tableModel.setStopSkipRow(Integer.parseInt(stopSkipRow));
				}

				String minLength = element.attributeValue("minLength");
				if (StringUtils.isNumeric(minLength)) {
					tableModel.setMinLength(Integer.parseInt(minLength));
				}

				String batchSize = element.attributeValue("batchSize");
				if (StringUtils.isNumeric(batchSize)) {
					tableModel.setBatchSize(Integer.parseInt(batchSize));
				}

				List<?> excludes = element.elements("excludes");
				if (excludes != null && excludes.size() > 0) {
					Iterator<?> iterator = excludes.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						tableModel.addExclude(elem.getStringValue());
					}
				}

				List<?> rows = element.elements("property");
				if (rows != null && rows.size() > 0) {
					Iterator<?> iterator = rows.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						ColumnModel field = new ColumnModel();
						this.readField(elem, field);
						tableModel.addColumn(field);
						if (StringUtils.equals(tableModel.getPrimaryKey(),
								field.getColumnName())) {
							tableModel.setIdColumn(field);
						}
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return tableModel;
	}

	protected void readField(Element elem, ColumnModel field) {
		field.setName(elem.attributeValue("name"));
		field.setType(elem.attributeValue("type"));
		field.setColumnName(elem.attributeValue("column"));
		field.setTitle(elem.attributeValue("title"));
		field.setSecondTitle(elem.attributeValue("secondTitle"));
		field.setTrimType(elem.attributeValue("trimType"));
		field.setValueExpression(elem.attributeValue("valueExpression"));
		field.setFormat(elem.attributeValue("format"));
		field.setCurrency(elem.attributeValue("currency"));

		/**
		 * 如果是占位符，则不存储该字段
		 */
		if ("true".equals(elem.attributeValue("temporary"))) {
			field.setTemporary(true);
		}

		/**
		 * 如果是必须字段
		 */
		if ("true".equals(elem.attributeValue("required"))) {
			field.setRequired(true);
		}

		String length = elem.attributeValue("length");
		if (StringUtils.isNumeric(length)) {
			field.setLength(Integer.parseInt(length));
		}

		String precision = elem.attributeValue("precision");
		if (StringUtils.isNumeric(precision)) {
			field.setPrecision(Integer.parseInt(precision));
		}

		String decimal = elem.attributeValue("decimal");
		if (StringUtils.isNumeric(decimal)) {
			field.setDecimal(Integer.parseInt(decimal));
		}

		String position = elem.attributeValue("position");
		if (StringUtils.isNumeric(position)) {
			field.setPosition(Integer.parseInt(position));
		}
	}
}