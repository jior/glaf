/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
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
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.Tools;

public class XmlReader {

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
					Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
					Iterator<?> iter = attrs.iterator();
					while (iter.hasNext()) {
						Attribute attr = (Attribute) iter.next();
						dataMap.put(attr.getName(), attr.getStringValue());
					}
					Tools.populate(tableDefinition, dataMap);
				}

				tableDefinition.setEntityName(element.attributeValue("name"));
				tableDefinition.setPackageName(element
						.attributeValue("package"));
				tableDefinition.setTableName(element.attributeValue("table"));
				tableDefinition.setTitle(element.attributeValue("title"));
				tableDefinition.setEnglishTitle(element
						.attributeValue("englishTitle"));

				if (StringUtils.equals("true",
						element.attributeValue("jbpmSupport"))) {
					tableDefinition.setJbpmSupport(true);
				}
				if (StringUtils.equals("true",
						element.attributeValue("treeSupport"))) {
					tableDefinition.setTreeSupport(true);
				}

				tableDefinition.setAggregationKeys(element
						.attributeValue("aggregationKeys"));

				tableDefinition.setModuleName(element
						.attributeValue("moduleName"));

				String primaryKey = element.attributeValue("primaryKey");

				List<?> rows = element.elements("property");
				if (rows != null && rows.size() > 0) {
					Iterator<?> iterator = rows.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						ColumnDefinition field = new ColumnDefinition();
						this.read(elem, field);
						if (primaryKey != null
								&& StringUtils.equals(primaryKey,
										field.getColumnName())) {
							tableDefinition.setIdColumn(field);
						} else {
							tableDefinition.addColumn(field);
						}
					}
				}

				Element idElem = element.element("id");
				if (idElem != null) {
					ColumnDefinition idField = new ColumnDefinition();
					this.read(idElem, idField);
					tableDefinition.setIdColumn(idField);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return tableDefinition;
	}

	protected void read(Element elem, ColumnDefinition field) {
		List<?> attrs = elem.attributes();
		if (attrs != null && !attrs.isEmpty()) {
			Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
			Iterator<?> iter = attrs.iterator();
			while (iter.hasNext()) {
				Attribute attr = (Attribute) iter.next();
				dataMap.put(attr.getName(), attr.getStringValue());
			}
			Tools.populate(field, dataMap);
		}

		field.setName(elem.attributeValue("name"));
		field.setMask(elem.attributeValue("mask"));
		field.setTitle(elem.attributeValue("title"));
		field.setEnglishTitle(elem.attributeValue("englishTitle"));
		field.setType(elem.attributeValue("type"));
		field.setDataCode(elem.attributeValue("dataCode"));
		field.setRenderType(elem.attributeValue("renderType"));
		field.setColumnName(elem.attributeValue("column"));
		field.setDataType(FieldType.getFieldType(field.getType()));
		String length = elem.attributeValue("length");
		if (StringUtils.isNumeric(length)) {
			field.setLength(Integer.valueOf(length));
		}
		String maxLength = elem.attributeValue("maxLength");
		if (StringUtils.isNumeric(maxLength)) {
			field.setMaxLength(Integer.valueOf(maxLength));
		}
		String minLength = elem.attributeValue("minLength");
		if (StringUtils.isNumeric(minLength)) {
			field.setMinLength(Integer.valueOf(minLength));
		}
		String displayType = elem.attributeValue("displayType");
		if (StringUtils.isNumeric(displayType)) {
			field.setDisplayType(Integer.valueOf(displayType));
		}
		String sortNo = elem.attributeValue("sortNo");
		if (StringUtils.isNumeric(sortNo)) {
			field.setSortNo(Integer.valueOf(sortNo));
		}
		if (StringUtils.equals(elem.attributeValue("updatable"), "false")) {
			field.setUpdatable(false);
		}
		if (StringUtils.equals(elem.attributeValue("nullable"), "false")) {
			field.setNullable(false);
		}
		if (StringUtils.equals(elem.attributeValue("editable"), "true")) {
			field.setEditable(true);
		}
		if (StringUtils.equals(elem.attributeValue("searchable"), "true")) {
			field.setSearchable(true);
		}
	}
}
