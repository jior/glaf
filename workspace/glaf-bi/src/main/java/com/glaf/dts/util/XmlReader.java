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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.domain.*;
import com.glaf.core.util.Tools;
import com.glaf.dts.domain.*;

public class XmlReader {

	public DataTransfer read(java.io.InputStream inputStream) {
		DataTransfer tableModel = new DataTransfer();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			Element element = root.element("entity");
			if (element != null) {
				List<?> attrs = element.attributes();
				if (attrs != null && !attrs.isEmpty()) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					Iterator<?> iter = attrs.iterator();
					while (iter.hasNext()) {
						Attribute attr = (Attribute) iter.next();
						dataMap.put(attr.getName(), attr.getStringValue());
					}
					Tools.populate(tableModel, dataMap);
				}

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
				tableModel.setAggregationKeys(element
						.attributeValue("aggregationKeys"));
				tableModel.setSplit(element.attributeValue("split"));
				if (StringUtils.equals(element.attributeValue("insertOnly"),
						"true")) {
					tableModel.setInsertOnly("true");
				}

				String startRow = element.attributeValue("startRow");
				if (StringUtils.isNotEmpty(startRow)
						&& StringUtils.isNumeric(startRow)) {
					tableModel.setStartRow(Integer.parseInt(startRow));
				}

				String stopSkipRow = element.attributeValue("stopSkipRow");
				if (StringUtils.isNotEmpty(stopSkipRow)
						&& StringUtils.isNumeric(stopSkipRow)) {
					tableModel.setStopSkipRow(Integer.parseInt(stopSkipRow));
				}

				String batchSize = element.attributeValue("batchSize");
				if (StringUtils.isNotEmpty(batchSize)
						&& StringUtils.isNumeric(batchSize)) {
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
						ColumnDefinition field = new ColumnDefinition();
						this.readField(elem, field);
						tableModel.addColumn(field);
						if (StringUtils.equalsIgnoreCase(tableModel.getPrimaryKey(),
								field.getColumnName())) {
							tableModel.setIdColumn(field);
						}
					}
				}
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return tableModel;
	}

	protected void readField(Element elem, ColumnDefinition field) {
		List<?> attrs = elem.attributes();
		if (attrs != null && !attrs.isEmpty()) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Iterator<?> iter = attrs.iterator();
			while (iter.hasNext()) {
				Attribute attr = (Attribute) iter.next();
				dataMap.put(attr.getName(), attr.getStringValue());
			}
			Tools.populate(field, dataMap);
		}

		field.setName(elem.attributeValue("name"));
		field.setType(elem.attributeValue("type"));
		field.setColumnName(elem.attributeValue("column"));
		field.setTitle(elem.attributeValue("title"));
		field.setValueExpression(elem.attributeValue("valueExpression"));

		/**
		 * 如果是必须字段
		 */
		if ("true".equals(elem.attributeValue("required"))) {
			field.setRequired(true);
		}

		String length = elem.attributeValue("length");
		if (StringUtils.isNotEmpty(length) && StringUtils.isNumeric(length)) {
			field.setLength(Integer.parseInt(length));
		}

		String nullable = elem.attributeValue("nullable");
		if (StringUtils.isNotEmpty(nullable)) {
			if (StringUtils.equalsIgnoreCase(nullable, "true")) {
				field.setNullable(true);
				field.setNullableField("true");
			} else {
				field.setNullable(false);
				field.setNullableField("false");
			}
		}

		String position = elem.attributeValue("position");
		if (StringUtils.isNotEmpty(position) && StringUtils.isNumeric(position)) {
			field.setPosition(Integer.parseInt(position));
		}

		String precision = elem.attributeValue("precision");
		if (StringUtils.isNotEmpty(precision)
				&& StringUtils.isNumeric(precision)) {
			field.setPrecision(Integer.parseInt(precision));
		}

	}
}