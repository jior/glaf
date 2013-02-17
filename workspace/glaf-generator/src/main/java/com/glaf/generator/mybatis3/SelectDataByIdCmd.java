package com.glaf.generator.mybatis3;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.glaf.base.api.ClassDefinition;
import com.glaf.base.api.FieldDefinition;
import com.glaf.base.api.Generation;
import com.glaf.base.utils.FieldType;
import com.glaf.base.utils.StringTools;

public class SelectDataByIdCmd implements Generation {

	public void addNode(Document doc, ClassDefinition classDefinition) {
		FieldDefinition idField = classDefinition.getIdField();
		if (idField == null) {
			return;
		}
		Element root = doc.getRootElement();
		String className = classDefinition.getClassName();
		String tableName = classDefinition.getTableName();
		String simpleName = className;
		String parameterType = null;

		if (className.indexOf(".") != -1) {
			simpleName = className.substring(className.lastIndexOf(".") + 1,
					className.length());
		}

		if (StringUtils.isNotEmpty(idField.getType())) {
			if ("Integer".equals(idField.getType())) {
				parameterType = "int";
			} else if ("Short".equals(idField.getType())) {
				parameterType = "short";
			} else if ("Long".equals(idField.getType())) {
				parameterType = "long";
			} else if ("Double".equals(idField.getType())) {
				parameterType = "double";
			} else if ("String".equals(idField.getType())) {
				parameterType = "string";
			}
		} else {
			int dataType = idField.getDataType();
			switch (dataType) {
			case FieldType.INTEGER_TYPE:
				parameterType = "int";
				break;
			case FieldType.LONG_TYPE:
				parameterType = "long";
				break;
			case FieldType.DOUBLE_TYPE:
				parameterType = "double";
				break;
			default:
				parameterType = "string";
				break;
			}
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\r    select * from ").append(tableName.toUpperCase());

		buffer.append("\n\r    ");
		buffer.append(" where ");
		buffer.append(idField.getColumnName().toUpperCase()).append(" = ");
		buffer.append("#{value}");

		String x_name = StringTools.lower(simpleName);
		Element element = root.addElement("select");
		element.addAttribute("id", "get" + simpleName + "ById");
		element.addAttribute("parameterType", parameterType);
		element.addAttribute("resultMap", x_name + "ResultMap");
		element.setText(buffer.toString());
	}
}
