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

public class UpdateDataCmd implements Generation {

	public void addNode(Document doc, ClassDefinition classDefinition) {
		FieldDefinition idField = classDefinition.getIdField();
		if (idField == null) {
			return;
		}
		Element root = doc.getRootElement();
		String className = classDefinition.getClassName();
		String tableName = classDefinition.getTableName();
		String simpleName = className;

		if (className.indexOf(".") != -1) {
			simpleName = className.substring(className.lastIndexOf(".") + 1,
					className.length());
		}

		Element element = root.addElement("update");
		element.addAttribute("id", "update" + simpleName);
		element.addAttribute("parameterType", className);
		element.addText("\n\r    update " + tableName.toUpperCase());

		Element setElement = element.addElement("set");
		Map<String, FieldDefinition> fields = classDefinition.getFields();
		StringBuffer vBuffer = new StringBuffer();

		Set<Entry<String, FieldDefinition>> entrySet = fields.entrySet();
		for (Entry<String, FieldDefinition> entry : entrySet) {
			String name = entry.getKey();
			if(StringUtils.equals(idField.getName(), name)){
				continue;
			}
			FieldDefinition field = entry.getValue();
			String columnName = field.getColumnName();
			if (columnName == null) {
				continue;
			}
			columnName = columnName.toUpperCase();
			vBuffer.append("\n\r    ");
			vBuffer.append(columnName).append(" = ");
			vBuffer.append("#{").append(name).append(", jdbcType=");
			if (StringUtils.isNotEmpty(field.getType())) {
				if ("Integer".equals(field.getType())) {
					vBuffer.append("INTEGER");
				} else if ("Short".equals(field.getType())) {
					vBuffer.append("INTEGER");
				} else if ("Long".equals(field.getType())) {
					vBuffer.append("LONG");
				} else if ("Double".equals(field.getType())) {
					vBuffer.append("DOUBLE");
				} else if ("Date".equals(field.getType())) {
					vBuffer.append("TIMESTAMP");
				} else if ("String".equals(field.getType())) {
					vBuffer.append("VARCHAR");
				} else if ("Boolean".equals(field.getType())) {
					vBuffer.append("BOOLEAN");
				}
			} else {
				int dataType = field.getDataType();
				switch (dataType) {
				case FieldType.BOOLEAN_TYPE:
					vBuffer.append("BOOLEAN");
					break;
				case FieldType.INTEGER_TYPE:
					vBuffer.append("INTEGER");
					break;
				case FieldType.LONG_TYPE:
					vBuffer.append("LONG");
					break;
				case FieldType.DOUBLE_TYPE:
					vBuffer.append("DOUBLE");
					break;
				case FieldType.DATE_TYPE:
				case FieldType.TIMESTAMP_TYPE:
					vBuffer.append("TIMESTAMP");
					break;
				case FieldType.CLOB_TYPE:
					vBuffer.append("CLOB");
					break;
				case FieldType.BLOB_TYPE:
					vBuffer.append("BLOB");
					break;
				default:
					vBuffer.append("VARCHAR");
					break;
				}
			}
			vBuffer.append("},");
		}

		vBuffer.deleteCharAt(vBuffer.length() - 1);
		setElement.setText(vBuffer.toString());

		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\r    where ");
		buffer.append(idField.getColumnName().toUpperCase()).append(" = ");
		buffer.append("#{value}");

		element.addText(buffer.toString());
	}
}
