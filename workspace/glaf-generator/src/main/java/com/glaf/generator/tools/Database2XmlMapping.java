package com.glaf.generator.tools;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;

import com.glaf.core.base.ClassDefinition;
import com.glaf.core.base.FieldDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.xml.XmlWriter;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.StringTools;

public class Database2XmlMapping {

	public final static String newline = System.getProperty("line.separator");

	public static void main(String[] args) {
		Database2XmlMapping gen = new Database2XmlMapping();
		if (args != null && args.length > 0) {
			gen.setTodir(args[0]);
		} else {
			gen.setTodir("codegen/mapping");
		}
		gen.execute();
		System.out.println("生成结束，请到" + gen.getTodir() + "查看输出。");
		System.exit(0);
	}

	public String todir;

	public String systemName;

	public void execute() {
		try {
			List<String> tables = DBUtils.getTables();
			for (String tableName : tables) {
				System.out.println("process " + tableName);
				List<FieldDefinition> fields = DBUtils
						.getFieldDefinitions(tableName);

				ClassDefinition classDefinition = new TableDefinition();
				classDefinition.setTableName(tableName.toLowerCase());
				classDefinition.setTitle(StringTools.upper(StringTools
						.camelStyle(tableName)));
				classDefinition.setEntityName(StringTools.upper(StringTools
						.camelStyle(tableName)));
				classDefinition
						.setEnglishTitle(classDefinition.getEntityName());
				// classDefinition.setPackageName("com.glaf.apps."
				// + StringTools.camelStyle(tableName));

				classDefinition.setPackageName("com.glaf.apps");

				List<String> primaryKeys = DBUtils.getPrimaryKeys(tableName);

				for (FieldDefinition f : fields) {
					f.setName(StringTools.lower(StringTools.camelStyle(f
							.getName().toLowerCase())));
					f.setTitle(f.getName());
					if (!primaryKeys.isEmpty()) {
						if (primaryKeys.contains(f.getColumnName()) || primaryKeys.contains(f.getColumnName().toLowerCase()) || primaryKeys.contains(f.getColumnName().toUpperCase())) {
							classDefinition.setIdField(f);
						} else {
							f.setEditable(true);
							classDefinition.addField(f);
						}
					} else {
						if (StringUtils.equalsIgnoreCase(f.getColumnName(),
								"id")) {
							classDefinition.setIdField(f);
						} else {
							f.setEditable(true);
							classDefinition.addField(f);
						}
					}
				}

				OutputFormat format = OutputFormat.createPrettyPrint();

				format.setPadText(true);
				format.setNewlines(true);
				format.setIndentSize(4);
				format.setEncoding("UTF-8");
				format.setLineSeparator(newline);
				format.setNewLineAfterDeclaration(true);
				format.setSuppressDeclaration(true);

				String filename = classDefinition.getEntityName().toLowerCase()
						+ ".mapping.xml";

				String toFile = todir + "/" + filename;

				XmlWriter xmlWriter = new XmlWriter();
				Document d = xmlWriter.write(classDefinition);
				byte[] bytes = Dom4jUtils.getBytesFromDocument(d, format);
				FileUtils.save(toFile, bytes);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getSystemName() {
		return systemName;
	}

	public String getTodir() {
		return todir;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public void setTodir(String todir) {
		this.todir = todir;
	}

}
