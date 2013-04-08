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

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.Tools;

public class MetadataXmlReader {

	public TableDefinition read(java.io.InputStream inputStream) {
		TableDefinition tableDefinition = new TableDefinition();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			Element element = root.element("entity");
			if (element != null) {

				List<?> attrs = element.attributes();
				if (attrs != null && !attrs.isEmpty()) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					Iterator<?> it = attrs.iterator();
					while (it.hasNext()) {
						Attribute attr = (Attribute) it.next();
						dataMap.put(attr.getName(), attr.getStringValue());
					}
					Tools.populate(tableDefinition, dataMap);
				}

				tableDefinition.setTableName(element.attributeValue("table"));
				tableDefinition.setTitle(element.attributeValue("title"));
				tableDefinition.setEnglishTitle(element
						.attributeValue("englishTitle"));
				String primaryKey = element.attributeValue("primaryKey");
				
				if (StringUtils.equals(element.attributeValue("insertOnly"),
						"true")) {
					tableDefinition.setInsertOnly(true);
				}

				Element idElem = element.element("id");
				if (idElem != null) {
					ColumnDefinition idColumn = new ColumnDefinition();
					this.readColumn(idElem, idColumn);
					idColumn.setPrimaryKey(true);
					tableDefinition.setIdColumn(idColumn);
				}

				List<?> rows = element.elements("property");
				if (rows != null && rows.size() > 0) {
					Iterator<?> iterator = rows.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						ColumnDefinition field = new ColumnDefinition();
						this.readColumn(elem, field);
						if (StringUtils.equals(primaryKey,
								field.getColumnName())) {
							field.setPrimaryKey(true);
							tableDefinition.setIdColumn(field);
							tableDefinition.addColumn(field);
						} else {
							tableDefinition.addColumn(field);
						}
					}
				}
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return tableDefinition;
	}

	protected void readColumn(Element elem, ColumnDefinition field) {
		List<?> attrs = elem.attributes();
		if (attrs != null && !attrs.isEmpty()) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Iterator<?> it = attrs.iterator();
			while (it.hasNext()) {
				Attribute attr = (Attribute) it.next();
				dataMap.put(attr.getName(), attr.getStringValue());
			}
			Tools.populate(field, dataMap);
		}
		field.setColumnName(elem.attributeValue("column"));
		field.setJavaType(elem.attributeValue("type"));
		field.setDataType(FieldType.getFieldType(field.getJavaType()));
	}
}