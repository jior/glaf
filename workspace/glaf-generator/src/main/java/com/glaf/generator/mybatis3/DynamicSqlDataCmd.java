package com.glaf.generator.mybatis3;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;

import com.glaf.base.api.ClassDefinition;
import com.glaf.base.api.FieldDefinition;
import com.glaf.base.api.Generation;
import com.glaf.base.utils.FieldType;

public class DynamicSqlDataCmd implements Generation {

	public void addNode(Document doc, ClassDefinition classDefinition) {
		Element root = doc.getRootElement();
		String className = classDefinition.getClassName();
		String tableName = classDefinition.getTableName();
		String simpleName = className;

		if (className.indexOf(".") != -1) {
			simpleName = className.substring(className.lastIndexOf(".") + 1,
					className.length());
		}

		Element element = root.addElement("sql");
		element.addAttribute("id", "select" + simpleName
				+ "sByQueryCriteriaSql");

		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\r    from ").append(tableName.toUpperCase());
		element.addText(buffer.toString());

		Map<String, FieldDefinition> fields = classDefinition.getFields();
		Element elem = element.addElement("where");
		Set<Entry<String, FieldDefinition>> entrySet = fields.entrySet();
		for (Entry<String, FieldDefinition> entry : entrySet) {
			String name = entry.getKey();
			FieldDefinition field = entry.getValue();
			String columnName = field.getColumnName();
			if (columnName == null) {
				continue;
			}
			columnName = columnName.toUpperCase();
			int dataType = field.getDataType();
			switch (dataType) {
			case FieldType.INTEGER_TYPE:
			case FieldType.LONG_TYPE:
			case FieldType.DOUBLE_TYPE:
				Element em21 = elem.addElement("if");
				em21.addAttribute("test", name + "GreaterThanOrEqual != null ");
				em21.addCDATA(" and " + columnName + " >= #{" + name
						+ "GreaterThanOrEqual } ");

				Element em22 = elem.addElement("if");
				em22.addAttribute("test", name + "LessThanOrEqual != null ");
				em22.addCDATA(" and " + columnName + " <= #{" + name
						+ "LessThanOrEqual } ");

				Element em23 = elem.addElement("if");
				em23.addAttribute("test", name + " != null ");
				em23.addCDATA(" and " + columnName + " = #{" + name
						+ " } ");

				break;
			case FieldType.DATE_TYPE:
			case FieldType.TIMESTAMP_TYPE:
				Element em81 = elem.addElement("if");
				em81.addAttribute("test", name + "GreaterThanOrEqual != null ");
				em81.addCDATA(" and " + columnName + " >= #{" + name
						+ "GreaterThanOrEqual } ");

				Element em82 = elem.addElement("if");
				em82.addAttribute("test", name + "LessThanOrEqual != null ");
				em82.addCDATA(" and " + columnName + " <= #{" + name
						+ "LessThanOrEqual } ");
				break;
			case FieldType.CHAR_TYPE:
			case FieldType.STRING_TYPE:
				Element em = elem.addElement("if");
				em.addAttribute("test", name + " != null ");
				em.addCDATA(" and " + columnName + " = #{" + name + "} ");

				Element em2 = elem.addElement("if");
				em2.addAttribute("test", name + "Like != null ");
				em2.addCDATA(" and " + columnName + " like #{" + name
						+ "Like} ");

				Element emxs = elem.addElement("if");
				emxs.addAttribute("test", name + "s != null ");
				Element iterate = emxs.addElement("foreach");
				iterate.addAttribute("collection", name + "s");
				iterate.addAttribute("index", "index");
				iterate.addAttribute("item", name);
				iterate.addCDATA("\n\r    or " + columnName + " = #{" + name
						+ "}");
				break;
			default:
				break;
			}
		}
	}
}
