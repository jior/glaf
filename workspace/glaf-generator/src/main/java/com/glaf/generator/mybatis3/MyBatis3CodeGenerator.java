package com.glaf.generator.mybatis3;

import java.util.Map;

import org.dom4j.Document;

import com.glaf.base.api.ClassDefinition;
import com.glaf.base.utils.Dom4jToolkit;
import com.glaf.generator.CodeGenerator;

public class MyBatis3CodeGenerator implements CodeGenerator {

	public String process(ClassDefinition def, Map<String, Object> context) {
		Document doc = MyBatis3CodegenFactory.toDocument(def);
		byte[] bytes = Dom4jToolkit.getBytesFromPrettyDocument(doc, "UTF-8");
		return new String(bytes);
	}

}
