package com.glaf.generator;

import java.io.*;

import com.glaf.core.base.ClassDefinition;
import com.glaf.core.xml.XmlReader;

public class GenAll {

	private final static String DEFAULT_CONFIG = "templates/codegen/codegen_all.xml";

	public void genAll(File templateDir, File outputDir) throws Exception {
		File[] list = templateDir.listFiles();
		if (list != null && list.length > 0) {
			for (File file : list) {
				if (file.isDirectory()) {
					this.genAll(file, outputDir);
				} else {
					if (file.getName().endsWith("mapping.xml")) {
						FileInputStream fin = new FileInputStream(file);
						ClassDefinition def = new XmlReader().read(fin);
						JavaCodeGen gen = new JavaCodeGen();
						String config = System.getProperty("codegen.cfg");
						if (config == null) {
							config = DEFAULT_CONFIG;
						}
						String entityName = def.getEntityName();
						gen.codeGen(def, new File(outputDir, entityName),
								config);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		GenAll gen = new GenAll();
		gen.genAll(new File(args[0]), new File(args[1]));
	}

}
