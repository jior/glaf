package com.glaf.generator.tools;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

import com.glaf.core.base.ClassDefinition;
import com.glaf.core.base.FieldDefinition;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.xml.*;
import com.glaf.core.util.*;

public class Pdm2XmlMapping {
	public final static String newline = System.getProperty("line.separator");

	public String dir;

	public String todir;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getTodir() {
		return todir;
	}

	public void setTodir(String todir) {
		this.todir = todir;
	}

	public void execute() {
		try {
			File file = new File(dir);
			this.convert(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void convert(File file) throws Exception {
		if (file.isFile() && file.getName().toLowerCase().endsWith(".pdm")) {
			String dt = DateUtils.getDateTime("yyyyMMddHHmm", new Date());
			InputStream inputStream = new FileInputStream(file);
			SAXReader xmlReader = new SAXReader(false);
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List<?> tableList = root.selectNodes("//o:Table");
			Iterator<?> iterator = tableList.iterator();
			while (iterator.hasNext()) {

				Element element = (Element) iterator.next();

				String title = element.elementTextTrim("Name");
				String table = element.elementTextTrim("Code");
				String entityName = table;

				if (title == null || table == null) {
					continue;
				}

				entityName = StringTools.replace(entityName, "_", "");
				entityName = StringTools.upper(entityName);

				System.out.println("------------------------------");
				System.out.println(title + " [" + entityName + "]");

				ClassDefinition classDefinition = new TableDefinition();
				classDefinition.setTableName(table.toUpperCase());
				classDefinition.setTitle(title);
				classDefinition.setEntityName(entityName);
				classDefinition.setEnglishTitle(entityName);
				classDefinition.setPackageName("com.glaf.apps."
						+ entityName.toLowerCase());

				Map<String, String> idMap = new HashMap<String, String>();

				List<?> cols = element.element("Columns").elements("Column");

				Iterator<?> iterator001 = cols.iterator();
				while (iterator001.hasNext()) {
					Element ee = (Element) iterator001.next();
					String id = ee.attributeValue("Id");
					String colLabel = ee.elementTextTrim("Name");
					String colName = ee.elementTextTrim("Code");
					String dataType = ee.elementTextTrim("DataType");
					System.out.println(colLabel + " " + colName);
					if (colName != null && dataType != null) {
						idMap.put(id, colName);

						String name = StringTools.lower(colName);
						name = StringTools.replace(name, "_", "");
						FieldDefinition field = new ColumnDefinition();
						field.setColumnName(colName.toUpperCase());
						field.setName(name);
						field.setTitle(colLabel);
						field.setEnglishTitle(name);
						dataType = dataType.toUpperCase();
						if (StringUtils.startsWith(dataType, "BIT")) {
							field.setDataType(FieldType.BOOLEAN_TYPE);
							field.setType("Boolean");
						} else if (StringUtils.startsWith(dataType, "NUMBER")
								|| StringUtils.startsWith(dataType, "DOUBLE")
								|| StringUtils.startsWith(dataType, "FLOAT")) {
							field.setDataType(FieldType.DOUBLE_TYPE);
							field.setType("Double");
						} else if (StringUtils.startsWith(dataType, "INT")
								|| StringUtils.startsWith(dataType, "INT2")
								|| StringUtils.startsWith(dataType, "INT4")
								|| StringUtils.startsWith(dataType, "INTEGER")
								|| StringUtils.startsWith(dataType, "SMALLINT")) {
							field.setDataType(FieldType.INTEGER_TYPE);
							field.setType("Integer");
						} else if (StringUtils.startsWith(dataType, "DATE")
								|| StringUtils.startsWith(dataType, "DATETIME")
								|| StringUtils
										.startsWith(dataType, "TIMESTAMP")) {
							field.setDataType(FieldType.TIMESTAMP_TYPE);
							field.setType("Date");
						} else if (StringUtils.startsWith(dataType, "BIGINT")
								|| StringUtils.startsWith(dataType, "INT8")) {
							field.setDataType(FieldType.LONG_TYPE);
							field.setType("Long");
						} else {
							field.setDataType(FieldType.STRING_TYPE);
							field.setType("String");
							String length = ee.elementTextTrim("Length");
							if (length != null && StringUtils.isNumeric(length)) {
								field.setLength(Integer.valueOf(length));
								field.setMaxLength(Integer.valueOf(length));
							}
						}
						field.setDisplayType(4);
						field.setEditable(true);
						classDefinition.addField(field);
					}
				}

				Element primaryKey = element.element("PrimaryKey");
				if (primaryKey != null) {
					if (primaryKey.element("Key") != null) {
						String ref = primaryKey.element("Key").attributeValue(
								"Ref");
						if (element.element("Keys") != null) {
							List<?> keys = element.element("Keys").elements(
									"Key");
							Iterator<?> iiii = keys.iterator();
							while (iiii.hasNext()) {
								Element e = (Element) iiii.next();
								if (ref.equals(e.attributeValue("Id"))) {
									if (e.element("Key.Columns") != null
											&& e.element("Key.Columns")
													.element("Column") != null) {
										String id = e.element("Key.Columns")
												.element("Column")
												.attributeValue("Ref");
										String colName = idMap.get(id);
										String name = StringTools
												.lower(colName);
										name = StringTools.replace(name, "_",
												"");
										FieldDefinition idField = classDefinition
												.getFields().get(name);
										classDefinition.setIdField(idField);
										classDefinition.getFields()
												.remove(name);
										break;
									}
								}
							}
						}
					}
				}

				String filename = table + ".mapping.xml";

				String path = todir + "/" + dt;
				FileUtils.mkdirs(path);

				String toFile = path + "/" + filename;

				OutputFormat format = new OutputFormat();

				format.setPadText(true);
				format.setNewlines(true);
				format.setIndentSize(4);
				format.setEncoding("UTF-8");
				format.setLineSeparator(newline);
				format.setNewLineAfterDeclaration(true);
				format.setSuppressDeclaration(true);

				XmlWriter xmlWriter = new XmlWriter();
				Document d = xmlWriter.write(classDefinition);
				byte[] bytes = Dom4jUtils.getBytesFromDocument(d, format);
				FileUtils.save(toFile, bytes);
				
				System.out.println("gen mapping file:"+toFile);

			}

		} else {
			if (file.isDirectory()) {
				String[] filelist = file.list(); // 列出所有的子文件（夹）名字
				for (int i = 0; i < filelist.length; i++) {
					File f = new File(file.getPath() + "/" + filelist[i]);
					this.convert(f);
				}
			}
		}
	}

	public static void main(String[] args) {
		Pdm2XmlMapping task = new Pdm2XmlMapping();
		task.setDir(args[0]);
		task.setTodir(args[1]);
		task.execute();
	}

}
