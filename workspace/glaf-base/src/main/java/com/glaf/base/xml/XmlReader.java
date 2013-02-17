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

package com.glaf.base.xml;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.base.api.ClassDefinition;
import com.glaf.base.api.FieldDefinition;
import com.glaf.base.utils.FieldType;

 

public class XmlReader {

	public ClassDefinition read(java.io.InputStream inputStream) {
		ClassDefinition classDefinition = new ClassDefinition();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			Element element = root.element("entity");
			if (element != null) {
				classDefinition.setEntityName(element.attributeValue("name"));
				classDefinition.setPackageName(element
						.attributeValue("package"));
				classDefinition.setTableName(element.attributeValue("table"));
				classDefinition.setTitle(element.attributeValue("title"));
				classDefinition.setEnglishTitle(element
						.attributeValue("englishTitle"));
				classDefinition.setFormResourceName(element
						.attributeValue("formResourceName"));
				Element idElem = element.element("id");
				if (idElem != null) {
					FieldDefinition idField = new FieldDefinition();
					this.read(idElem, idField);
					classDefinition.setIdField(idField);
				}
				List<?> rows = element.elements("property");
				if (rows != null && rows.size() > 0) {
					Iterator<?> iterator = rows.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						FieldDefinition field = new FieldDefinition();
						this.read(elem, field);
						classDefinition.addField(field);
					}
				}
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return classDefinition;
	}

	protected void read(Element elem, FieldDefinition field) {
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
