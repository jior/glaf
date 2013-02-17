package com.glaf.generator.mybatis3;

import org.dom4j.Document;
import org.dom4j.Element;

import com.glaf.base.api.ClassDefinition;
import com.glaf.base.api.Generation;

public class CountDataCmd implements Generation {

	public void addNode(Document doc, ClassDefinition classDefinition) {
		Element root = doc.getRootElement();
		String className = classDefinition.getClassName();
		String simpleName = className;

		if (className.indexOf(".") != -1) {
			simpleName = className.substring(className.lastIndexOf(".") + 1,
					className.length());
		}
		String pkg = classDefinition.getPackageName();
		String p_x_name = pkg + ".query." + simpleName + "Query";

		Element element = root.addElement("select");
		element.addAttribute("id", "get" + simpleName + "Count");
		element.addAttribute("parameterType", p_x_name);
		element.addAttribute("resultType", "int");

		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\r    select count(*) ");
		element.setText(buffer.toString());
		Element elem = element.addElement("include");
		elem.addAttribute("refid", "select" + simpleName
				+ "sByQueryCriteriaSql");
	}
}
