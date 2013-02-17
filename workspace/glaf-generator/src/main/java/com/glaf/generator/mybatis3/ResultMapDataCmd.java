package com.glaf.generator.mybatis3;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.glaf.base.api.ClassDefinition;
import com.glaf.base.api.FieldDefinition;
import com.glaf.base.api.Generation;
import com.glaf.base.utils.FieldType;
import com.glaf.base.utils.StringTools;

public class ResultMapDataCmd implements Generation {

	public void addNode(Document doc, ClassDefinition classDefinition) {
		Element root = doc.getRootElement();
		String className = classDefinition.getClassName();
		String simpleName = className;
		if (className.indexOf(".") != -1) {
			simpleName = className.substring(className.lastIndexOf(".") + 1,
					className.length());
		}

		String x_name = StringTools.lower(simpleName);

		Element element = root.addElement("resultMap");
		element.addAttribute("id", x_name + "ResultMap");
		element.addAttribute("type", className);

		FieldDefinition idField = classDefinition.getIdField();
		if (idField != null) {
			Element elem = element.addElement("id");
			elem.addAttribute("property", idField.getName());
			elem.addAttribute("column", idField.getColumnName().toUpperCase());
			int dataType = idField.getDataType();
			switch (dataType) {
			case FieldType.INTEGER_TYPE:
				elem.addAttribute("jdbcType", "INTEGER");
				break;
			case FieldType.LONG_TYPE:
				elem.addAttribute("jdbcType", "LONG");
				break;
			default:
				elem.addAttribute("jdbcType", "VARCHAR");
				break;
			}
		}

		Map<String, FieldDefinition> fields = classDefinition.getFields();

		Set<Entry<String, FieldDefinition>> entrySet = fields.entrySet();
		for (Entry<String, FieldDefinition> entry : entrySet) {
			String name = entry.getKey();
			FieldDefinition field = entry.getValue();
			if (idField != null
					&& StringUtils.equals(field.getName(), idField.getName())) {
				continue;
			}
			String columnName = field.getColumnName();
			if (columnName == null) {
				continue;
			}
			columnName = columnName.toUpperCase();
			Element elem = element.addElement("result");
			elem.addAttribute("property", name);
			elem.addAttribute("column", columnName);
			int dataType = field.getDataType();
			switch (dataType) {
			case FieldType.BOOLEAN_TYPE:
				elem.addAttribute("jdbcType", "BOOLEAN");
				break;
			case FieldType.INTEGER_TYPE:
				elem.addAttribute("jdbcType", "INTEGER");
				break;
			case FieldType.LONG_TYPE:
				elem.addAttribute("jdbcType", "LONG");
				break;
			case FieldType.DOUBLE_TYPE:
				elem.addAttribute("jdbcType", "DOUBLE");
				break;
			case FieldType.DATE_TYPE:
			case FieldType.TIMESTAMP_TYPE:
				elem.addAttribute("jdbcType", "TIMESTAMP");
				break;
			case FieldType.CLOB_TYPE:
				elem.addAttribute("jdbcType", "CLOB");
				break;
			case FieldType.BLOB_TYPE:
				elem.addAttribute("jdbcType", "BLOB");
				break;
			default:
				elem.addAttribute("jdbcType", "VARCHAR");
				break;
			}
		}
	}
}
