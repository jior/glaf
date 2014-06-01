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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.glaf.core.base.ClassDefinition;
import com.glaf.core.base.FieldDefinition;

public class XmlWriter {

	public Document write(ClassDefinition classDefinition) {
		List<ClassDefinition> rows = new java.util.ArrayList<ClassDefinition>();
		rows.add(classDefinition);
		return this.write(rows);
	}

	public Document write(List<ClassDefinition> rows) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("mapping");
		for (ClassDefinition classDefinition : rows) {
			Element element = root.addElement("entity");
			element.addAttribute("name", classDefinition.getEntityName());
			element.addAttribute("package", classDefinition.getPackageName());
			element.addAttribute("moduleName", classDefinition.getModuleName());
			element.addAttribute("table", classDefinition.getTableName());
			element.addAttribute("title", classDefinition.getTitle());
			element.addAttribute("englishTitle",
					classDefinition.getEnglishTitle());
			FieldDefinition idField = classDefinition.getIdField();
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
			}

			Map<String, FieldDefinition> fields = classDefinition.getFields();
			Set<Entry<String, FieldDefinition>> entrySet = fields.entrySet();
			for (Entry<String, FieldDefinition> entry : entrySet) {
				String name = entry.getKey();
				FieldDefinition field = entry.getValue();
				if (idField != null
						&& StringUtils.equalsIgnoreCase(
								idField.getColumnName(), field.getColumnName())) {
					continue;
				}
				Element elem = element.addElement("property");
				elem.addAttribute("name", name);
				elem.addAttribute("column", field.getColumnName());
				elem.addAttribute("type", field.getType());
				elem.addAttribute("title", field.getTitle());
				elem.addAttribute("englishTitle", field.getEnglishTitle());

				if (StringUtils.equals(field.getType(), "String")
						&& field.getLength() > 0) {
					elem.addAttribute("length",
							String.valueOf(field.getLength()));
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
				if (field.getDisplayType() > 0) {
					elem.addAttribute("displayType",
							String.valueOf(field.getDisplayType()));
				}
			}
		}
		return doc;
	}
}
