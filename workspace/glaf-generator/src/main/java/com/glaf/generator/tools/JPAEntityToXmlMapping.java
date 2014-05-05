/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.generator.tools;

import java.util.*;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import javassist.*;

import com.glaf.core.base.ClassDefinition;
import com.glaf.core.base.FieldDefinition;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.AnnotationUtils;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.xml.*;

public class JPAEntityToXmlMapping {

	public final static String newline = System.getProperty("line.separator");

	protected ClassPool pool = null;

	protected CtClass ctClass;

	public JPAEntityToXmlMapping() {
		pool = new ClassPool(null);
		pool.appendSystemPath();
	}

	public ClassDefinition convert(String className) throws Exception {
		ClassDefinition classDefinition = new TableDefinition();

		String simpleName = className;
		String packageName = "";

		if (className.indexOf(".") != -1) {
			simpleName = className.substring(className.lastIndexOf(".") + 1,
					className.length());
			packageName = className.substring(0, className.lastIndexOf("."));
			packageName = packageName
					.substring(0, packageName.lastIndexOf("."));
		}

		classDefinition.setClassName(simpleName);
		classDefinition.setTitle(simpleName);
		classDefinition.setEnglishTitle(simpleName);
		classDefinition.setEntityName(simpleName);
		classDefinition.setPackageName(packageName);

		CtClass ctClass = pool.getCtClass(className);
		Object[] anns = ctClass.getAnnotations();
		if (anns != null && anns.length > 0) {
			for (Object object : anns) {
				if (object instanceof javax.persistence.Table) {
					javax.persistence.Table table = (javax.persistence.Table) object;
					System.out.println(table.name());
					classDefinition.setTableName(table.name());
				}
			}
		}

		CtField[] fields = ctClass.getFields();
		if (fields != null && fields.length > 0) {
			for (CtField field : fields) {
				boolean isPK = false;
				System.out.println(field.getName() + " "
						+ field.getType().getName());
				FieldDefinition fieldDefinition = new ColumnDefinition();
				fieldDefinition.setTitle(StringTools.upper(field.getName()));
				fieldDefinition.setEditable(true);
				fieldDefinition.setEnglishTitle(StringTools.upper(field
						.getName()));
				fieldDefinition.setName(field.getName());
				if (StringUtils.equals(field.getType().getName(),
						"java.lang.String")) {
					fieldDefinition.setType("String");
				} else if (StringUtils.equals(field.getType().getName(),
						"java.util.Date")) {
					fieldDefinition.setType("Date");
				} else if (StringUtils.equals(field.getType().getName(),
						"boolean")
						|| StringUtils.equals(field.getType().getName(),
								"java.lang.Boolean")) {
					fieldDefinition.setType("Boolean");
				} else if (StringUtils.equals(field.getType().getName(), "int")
						|| StringUtils.equals(field.getType().getName(),
								"java.lang.Integer")) {
					fieldDefinition.setType("Integer");
				} else if (StringUtils
						.equals(field.getType().getName(), "long")
						|| StringUtils.equals(field.getType().getName(),
								"java.lang.Long")) {
					fieldDefinition.setType("Long");
				} else if (StringUtils.equals(field.getType().getName(),
						"double")
						|| StringUtils.equals(field.getType().getName(),
								"java.lang.Double")) {
					fieldDefinition.setType("Double");
				}

				anns = field.getAnnotations();
				if (anns != null && anns.length > 0) {
					for (Object object : anns) {
						if (object instanceof javax.persistence.Column) {
							javax.persistence.Column col = (javax.persistence.Column) object;
							fieldDefinition.setColumnName(col.name());
							if (col.length() > 0) {
								fieldDefinition.setLength(col.length());
							}

							fieldDefinition.setNullable(col.nullable());
							fieldDefinition.setUpdatable(col.updatable());
							fieldDefinition.setUnique(col.unique());
						}
						if (object instanceof javax.persistence.Id) {
							isPK = true;
							fieldDefinition.setEditable(false);
						}
					}
				}

				if (fieldDefinition.getType() != null
						&& fieldDefinition.getColumnName() != null) {
					if (isPK) {
						classDefinition.setIdField(fieldDefinition);
					} else {
						classDefinition.addField(fieldDefinition);
					}
				}
			}
		}

		CtMethod[] methods = ctClass.getMethods();
		if (methods != null && methods.length > 0) {
			for (CtMethod method : methods) {
				if (method.getName().startsWith("get")) {
					boolean isPK = false;
					System.out.println("#######################");
					String mm = method.getName().substring(3,
							method.getName().length());
					String x_mm = StringTools.lower(mm);
					System.out.println(method.getName() + " "
							+ method.getReturnType().getName());
					FieldDefinition fieldDefinition = new ColumnDefinition();
					fieldDefinition.setTitle(StringTools.upper(mm));
					fieldDefinition.setEnglishTitle(StringTools.upper(mm));
					fieldDefinition.setName(x_mm);
					fieldDefinition.setEditable(true);
					if (StringUtils.equals(method.getReturnType().getName(),
							"java.lang.String")) {
						fieldDefinition.setType("String");
					} else if (StringUtils.equals(method.getReturnType()
							.getName(), "java.util.Date")) {
						fieldDefinition.setType("Date");
					} else if (StringUtils.equals(method.getReturnType()
							.getName(), "boolean")
							|| StringUtils.equals(method.getReturnType()
									.getName(), "java.lang.Boolean")) {
						fieldDefinition.setType("Boolean");
					} else if (StringUtils.equals(method.getReturnType()
							.getName(), "int")
							|| StringUtils.equals(method.getReturnType()
									.getName(), "java.lang.Integer")) {
						fieldDefinition.setType("Integer");
					} else if (StringUtils.equals(method.getReturnType()
							.getName(), "long")
							|| StringUtils.equals(method.getReturnType()
									.getName(), "java.lang.Long")) {
						fieldDefinition.setType("Long");
					} else if (StringUtils.equals(method.getReturnType()
							.getName(), "double")
							|| StringUtils.equals(method.getReturnType()
									.getName(), "java.lang.Double")) {
						fieldDefinition.setType("Double");
					}

					anns = method.getAnnotations();
					if (anns != null && anns.length > 0) {
						System.out.println("---------------------");
						for (Object object : anns) {
							if (object instanceof javax.persistence.Column) {
								javax.persistence.Column col = (javax.persistence.Column) object;
								System.out.println("col:" + col.name());
								fieldDefinition.setColumnName(col.name());
								System.out.println("col->:"
										+ fieldDefinition.getColumnName());
								if (col.length() > 0) {
									fieldDefinition.setLength(col.length());
								}

								fieldDefinition.setNullable(col.nullable());
								fieldDefinition.setUpdatable(col.updatable());
								fieldDefinition.setUnique(col.unique());
							}

							if (object instanceof javax.persistence.Id) {
								isPK = true;
							}
						}
					}

					if (fieldDefinition.getType() != null
							&& fieldDefinition.getColumnName() != null) {
						if (isPK) {
							classDefinition.setIdField(fieldDefinition);
						} else {
							classDefinition.addField(fieldDefinition);
						}
					}
				}
			}
		}

		// System.out.println(classDefinition);
		return classDefinition;
	}

	@Test
	public void genAll() {
		Collection<String> classes = AnnotationUtils.findJPAEntity("com.glaf");
		for (String cls : classes) {
			this.gen(cls);
		}
	}

	public void genAll(String packageName) {
		Collection<String> classes = AnnotationUtils.findJPAEntity(packageName);
		System.out.println(classes);
		for (String cls : classes) {
			this.gen(cls);
		}
	}

	public void gen(String className) {
		try {
			ClassDefinition classDefinition = this.convert(className);
			OutputFormat format = OutputFormat.createPrettyPrint();

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
			System.out.println(new String(bytes));
			String toFile = "./codegen/jpa/mapping/"
					+ classDefinition.getClassName() + ".mapping.xml";
			FileUtils.save(toFile, bytes);
			System.out.println("文件保存到：" + toFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JPAEntityToXmlMapping gen = new JPAEntityToXmlMapping();
		long start = System.currentTimeMillis();
		Collection<String> entities = AnnotationUtils.findJPAEntity("com.glaf");
		long time = System.currentTimeMillis() - start;
		for (String str : entities) {
			System.out.println(str);
			gen.gen(str);
		}
		System.out.println("time:" + time);

		if (args != null && args.length > 0) {

			gen.genAll(args[0]);
		} else {
			gen.genAll();
		}
	}

}