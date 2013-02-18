package com.glaf.generator.tools;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;

import com.glaf.base.api.ClassDefinition;
import com.glaf.base.api.FieldDefinition;
import com.glaf.base.xml.XmlWriter;
 
import com.glaf.base.utils.DBToolkit;
import com.glaf.base.utils.Dom4jToolkit;
import com.glaf.base.utils.FileTools;
import com.glaf.base.utils.StringTools;

public class Database2XmlMapping {

	public final static String newline = System.getProperty("line.separator");

	public static void main(String[] args) {
		Database2XmlMapping gen = new Database2XmlMapping();
		gen.setTodir("codegen/gmmc");
		gen.execute();
	}

	public String todir;

	public String systemName;

	public void execute() {
		try {
			List<String> tables = DBToolkit.getTables();
			for (String tableName : tables) {
                System.out.println("process "+tableName);
				List<FieldDefinition> fields = DBToolkit
						.getFieldDefinitions(tableName);

				ClassDefinition classDefinition = new ClassDefinition();
				classDefinition.setTableName(tableName.toLowerCase());
				classDefinition.setTitle(StringTools.upper(StringTools
						.camelStyle(tableName)));
				classDefinition.setEntityName(StringTools.upper(StringTools
						.camelStyle(tableName)));
				classDefinition
						.setEnglishTitle(classDefinition.getEntityName());
				// classDefinition.setPackageName("com.mxalloy.apps."
				// + StringTools.camelStyle(tableName));

				classDefinition.setPackageName("com.glaf.apps");

				List<String> primaryKeys = DBToolkit.getPrimaryKeys(tableName);

				for (FieldDefinition f : fields) {
					f.setName(StringTools.lower(StringTools.camelStyle(f
							.getName())));
					if (!primaryKeys.isEmpty()) {
						if (primaryKeys.contains(f.getColumnName())) {
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

				OutputFormat format = new OutputFormat();

				format.setPadText(true);
				format.setNewlines(true);
				format.setIndentSize(4);
				format.setEncoding("UTF-8");
				format.setLineSeparator(newline);
				format.setNewLineAfterDeclaration(true);
				format.setSuppressDeclaration(true);

				String filename = classDefinition.getEntityName()
						+ ".mapping.xml";

				String toFile = todir + "/" + filename;

				XmlWriter xmlWriter = new XmlWriter();
				Document d = xmlWriter.write(classDefinition);
				byte[] bytes = Dom4jToolkit.getBytesFromDocument(d, format);
				FileTools.save(toFile, bytes);

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