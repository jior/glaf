package com.glaf.generator.mybatis3;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.glaf.base.api.ClassDefinition;
import com.glaf.base.api.FieldDefinition;
import com.glaf.base.api.Generation;
import com.glaf.base.xml.XmlReader;
import com.glaf.base.config.SystemConfig;
import com.glaf.base.utils.Dom4jToolkit;
import com.glaf.base.utils.FileTools;
import com.glaf.base.utils.StringTools;

public class MyBatis3CodegenFactory {
	protected static final Log logger = LogFactory
			.getLog(MyBatis3CodegenFactory.class);
	private final static String DEFAULT_CONFIG = "com/glaf/generator/mybatis3/mybatis3-codegen-context.xml";

	private static ClassPathXmlApplicationContext ctx;

	public static ClassPathXmlApplicationContext getApplicationContext() {
		return ctx;
	}

	public static Object getBean(Object key) {
		if (ctx == null) {
			ctx = reload();
		}
		return ctx.getBean((String) key);
	}

	public static List<Generation> getGenerations() {
		if (ctx == null) {
			ctx = reload();
		}
		List<Generation> rows = new ArrayList<Generation>();
		Map<String, Generation> map = ctx.getBeansOfType(Generation.class);
		Iterator<Generation> iterator = map.values().iterator();
		while (iterator.hasNext()) {
			Generation gen = iterator.next();
			rows.add(gen);
		}
		return rows;
	}

	public static Document toDocument(ClassDefinition classDefinition) {
		Document doc = DocumentHelper.createDocument();
		doc.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN",
				"http://mybatis.org/dtd/mybatis-3-mapper.dtd");
		Element root = doc.addElement("mapper");
		if (classDefinition != null) {
			String packageName = classDefinition.getPackageName();
			String entityName = classDefinition.getEntityName();
			entityName = StringTools.upper(entityName);
			String namespace = packageName + ".mapper." + entityName + "Mapper";
			root.addAttribute("namespace", namespace);
			List<Generation> rows = getGenerations();
			Iterator<Generation> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Generation gen = iterator.next();
				logger.debug(gen.getClass().getName());
				gen.addNode(doc, classDefinition);
			}
		}
		return doc;
	}

	public static ClassPathXmlApplicationContext reload() {
		if (ctx != null) {
			ctx.close();
			ctx = null;
		}
		
		String	configLocation = SystemConfig.getString("codegen.mybatis3.context");
		
		if (StringUtils.isEmpty(configLocation)) {
			configLocation = DEFAULT_CONFIG;
		}
		ctx = new ClassPathXmlApplicationContext(configLocation);
		logger.debug("start spring ioc from: " + configLocation);
		return ctx;
	}

	public static void main(String[] args) throws Exception {
		FileInputStream fin = new FileInputStream(args[0]);
		ClassDefinition def = new  XmlReader().read(fin);
		if (def.getIdField() == null) {
			FieldDefinition idField = new FieldDefinition();
			idField.setName("id");
			idField.setColumnName("ID_");
			idField.setType("String");
			idField.setClassDefinition(def);
			def.setIdField(idField);
			def.addField(idField);
		}
		Document doc = MyBatis3CodegenFactory.toDocument(def);
		byte[] bytes = Dom4jToolkit.getBytesFromPrettyDocument(doc, "UTF-8");
		System.out.println(new String(bytes));
		FileTools
				.save("./codegen/" + def.getTableName() + ".mapper.xml", bytes);
	}

}
