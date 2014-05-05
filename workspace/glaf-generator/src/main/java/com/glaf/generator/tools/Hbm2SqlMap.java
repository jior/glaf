package com.glaf.generator.tools;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import com.glaf.core.util.*;

public class Hbm2SqlMap {
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
		if (file.isFile() && file.getName().toLowerCase().endsWith(".hbm.xml")) {
			System.out.println("convert file:" + file.getAbsolutePath());
			InputStream inputStream = new FileInputStream(file);
			SAXReader xmlReader = new SAXReader(false);
			Document d = DocumentHelper.createDocument();
			d.addDocType("sqlMap", "-//ibatis.apache.org//DTD SQL Map 2.0//EN",
					"http://ibatis.apache.org/dtd/sql-map-2.dtd");
			EntityResolver resolver = new ClassPathEntityResolver();
			xmlReader.setEntityResolver(resolver);
			xmlReader.setValidation(false);
			xmlReader.setIncludeExternalDTDDeclarations(false);
			xmlReader.setIncludeInternalDTDDeclarations(false);
			Element r = d.addElement("sqlMap");

			Document doc = xmlReader.read(inputStream);

			Element root = doc.getRootElement();
			String pkg = root.attributeValue("package");
			List<?> classList = root.elements("class");
			Iterator<?> iterator = classList.iterator();
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				String name = element.attributeValue("name");
				String table = element.attributeValue("table");
				if (name == null) {
					name = element.attributeValue("entity-name");
				}
				String alias = name;
				String className = name;
				if (name.indexOf('.') != -1) {
					alias = name.substring(name.lastIndexOf('.') + 1,
							name.length());
					className = alias;
					char ch = alias.charAt(0);
					alias = String.valueOf(ch).toLowerCase()
							+ alias.substring(1, alias.length());
				} else {
					if (pkg != null) {
						name = pkg + "." + name;
						char ch = alias.charAt(0);
						alias = String.valueOf(ch).toLowerCase()
								+ alias.substring(1, alias.length());
					}
				}

				/**
				 * 产生类名及别名
				 */
				Element typeAlias = r.addElement("typeAlias");
				typeAlias.addAttribute("alias", alias);
				typeAlias.addAttribute("type", name);

				Element idElement = element.element("id");
				String pk_property = null;
				String pk_column = null;
				String pk_type = null;
				Map<String, String> colMap = new TreeMap<String, String>();
				Map<String, String> typeMap = new TreeMap<String, String>();

				if (idElement != null) {
					pk_property = idElement.attributeValue("name");
					pk_column = idElement.attributeValue("column");
					pk_type = idElement.attributeValue("type");
					if (pk_column == null) {
						if (idElement.element("column") != null) {
							pk_column = idElement.element("column")
									.attributeValue("name");
						}
					}
					typeMap.put(pk_column, pk_type);
					typeMap.put(pk_property, pk_type);
				}

				/**
				 * 产生结果映射集
				 */
				Element resultMap = r.addElement("resultMap");
				resultMap.addAttribute("id", alias + "Map");
				resultMap.addAttribute("class", alias);
				Element idResult = resultMap.addElement("result");
				idResult.addAttribute("property", pk_property);
				idResult.addAttribute("column", pk_column);

				List<?> properties = element.elements("property");

				Iterator<?> iter = properties.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					String type = elem.attributeValue("type");
					String column = elem.attributeValue("column");
					if (column == null) {
						if (elem.element("column") != null) {
							column = elem.element("column").attributeValue(
									"name");
						}
					}
					if (column == null) {
						continue;
					}
					Element e = resultMap.addElement("result");
					e.addAttribute("property", elem.attributeValue("name"));
					e.addAttribute("column", column);
					colMap.put(column, elem.attributeValue("name"));
					typeMap.put(column, type);
					typeMap.put(elem.attributeValue("name"), type);
				}

				/**
				 * 插入数据
				 */
				Element insert = r.addElement("insert");
				insert.addAttribute("id", "insert" + className);
				insert.addAttribute("parameterClass", alias);

				StringBuffer buffer = new StringBuffer();
				buffer.append(newline).append("\t\t INSERT INTO ")
						.append(table);
				buffer.append(" ( ").append(pk_column).append(", ");

				StringBuffer sb = new StringBuffer();
				sb.append(newline).append("\t\t VALUES ( #")
						.append(pk_property).append("#, ");

				Set<Entry<String, String>> entrySet = colMap.entrySet();
				for (Entry<String, String> entry : entrySet) {
					String p = entry.getValue();
					String col = entry.getKey();

					buffer.append(col).append(", ");
					sb.append('#').append(p).append("#, ");
				}

				buffer.delete(buffer.length() - 2, buffer.length());
				sb.delete(sb.length() - 2, sb.length());
				sb.append(" ) ");
				buffer.append(" ) ");
				buffer.append(sb.toString());

				insert.addText(buffer.toString());

				/**
				 * 动态插入数据
				 */
				Element dynamicInsert = r.addElement("insert");
				dynamicInsert.addAttribute("id", "dynamicInsert" + className);
				dynamicInsert.addAttribute("parameterClass", alias);

				buffer.delete(0, buffer.length());
				buffer.append(newline).append("\t\t INSERT INTO ")
						.append(table);
				buffer.append(" ( ").append(pk_column).append(' ');

				sb.delete(0, sb.length());
				sb.append(newline).append("\t\t VALUES ( #")
						.append(pk_property).append("#, ");

				dynamicInsert.addText(buffer.toString());

				Element dynamicx = dynamicInsert.addElement("dynamic");
				dynamicx.addAttribute("prepend", "");

				entrySet = colMap.entrySet();
				for (Entry<String, String> entry : entrySet) {
					String p = entry.getValue();
					String col = entry.getKey();
					Element isPropertyAvailable = dynamicx
							.addElement("isPropertyAvailable");
					isPropertyAvailable.addAttribute("prepend", ",");
					isPropertyAvailable.addAttribute("property", p);
					isPropertyAvailable.setText(col);
				}

				dynamicInsert.addText(newline + "\t\t ) VALUES ( #"
						+ pk_property + "# ");

				Element dynamicy = dynamicInsert.addElement("dynamic");
				dynamicy.addAttribute("prepend", "");

				entrySet = colMap.entrySet();
				for (Entry<String, String> entry : entrySet) {
					String p = entry.getValue();
					Element isPropertyAvailable = dynamicy
							.addElement("isPropertyAvailable");
					isPropertyAvailable.addAttribute("prepend", ",");
					isPropertyAvailable.addAttribute("property", p);
					isPropertyAvailable.setText("#" + p + "#");
				}

				dynamicInsert.addText(newline + "\t\t ) ");

				/**
				 * 修改数据
				 */
				Element update = r.addElement("update");
				update.addAttribute("id", "update" + className);
				update.addAttribute("parameterClass", alias);
				update.addText(newline + "\t\t UPDATE " + table);

				Element dynamic = update.addElement("dynamic");
				dynamic.addAttribute("prepend", "SET");

				entrySet = colMap.entrySet();
				for (Entry<String, String> entry : entrySet) {
					String p = entry.getValue();
					String col = entry.getKey();
					Element isPropertyAvailable = dynamic
							.addElement("isPropertyAvailable");
					isPropertyAvailable.addAttribute("prepend", ",");
					isPropertyAvailable.addAttribute("property", p);
					isPropertyAvailable.addText(col + " = #" + p + "#");
				}

				update.addText(newline + "\t\t WHERE " + pk_column + " = #"
						+ pk_property + "# ");

				/**
				 * 根据主键删除数据
				 */
				Element delete = r.addElement("delete");
				delete.addAttribute("id", "delete" + className);
				if ("int".equalsIgnoreCase(pk_type)) {
					delete.addAttribute("parameterClass", "java.lang.Integer");
				} else if ("integer".equalsIgnoreCase(pk_type)) {
					delete.addAttribute("parameterClass", "java.lang.Integer");
				} else if ("long".equalsIgnoreCase(pk_type)) {
					delete.addAttribute("parameterClass", "java.lang.Long");
				} else {
					delete.addAttribute("parameterClass", "java.lang.String");
				}
				delete.addText(newline + "\t\t DELETE FROM " + table
						+ " WHERE " + pk_column + " = #" + pk_property + "# ");

				/**
				 * 查询统计数据
				 */
				Element count = r.addElement("select");
				count.addAttribute("id", "count" + className);
				count.addAttribute("parameterClass", alias);
				count.addAttribute("resultMap", alias + "Map");
				count.addText(newline + "\t\t SELECT COUNT(*) FROM " + table);

				Element dynamic55 = count.addElement("dynamic");
				dynamic55.addAttribute("prepend", "WHERE");

				/**
				 * 主键也放入查询条件中
				 */
				colMap.put(pk_column, pk_property);

				entrySet = colMap.entrySet();
				for (Entry<String, String> entry : entrySet) {
					String p = entry.getValue();
					String col = entry.getKey();
					String type = typeMap.get(p);

					String op = "isNotEmpty";
					String expr = " " + col + " = #" + p + "# ";

					if ("boolean".equalsIgnoreCase(type)) {
						op = "";
					} else if ("short".equalsIgnoreCase(type)) {
						op = "";
					} else if ("int".equalsIgnoreCase(type)) {
						op = "";
					} else if ("integer".equalsIgnoreCase(type)) {
						op = "";
					} else if ("long".equalsIgnoreCase(type)) {
						op = "";
					} else if ("float".equalsIgnoreCase(type)) {
						op = "";
					} else if ("double".equalsIgnoreCase(type)) {
						op = "";
					} else if ("currency".equalsIgnoreCase(type)) {
						op = "";
					} else if ("date".equalsIgnoreCase(type)) {
						op = "isNotNull";
					} else if ("time".equalsIgnoreCase(type)) {
						op = "isNotNull";
					} else if ("timestamp".equalsIgnoreCase(type)) {
						op = "isNotNull";
					} else if ("string".equalsIgnoreCase(type)) {
						op = "isNotEmpty";
					} else if ("text".equalsIgnoreCase(type)) {
						expr = " " + col + " like #" + p + "# ";
					} else if ("clob".equalsIgnoreCase(type)) {
						expr = " " + col + " like #" + p + "# ";
					} else if ("binary".equalsIgnoreCase(type)) {
						continue;
					} else if ("blob".equalsIgnoreCase(type)) {
						continue;
					} else {
						op = "isNotNull";
					}

					if (op.trim().length() > 0) {
						Element isPropertyAvailable = dynamic55
								.addElement("isPropertyAvailable");
						isPropertyAvailable.addAttribute("prepend", "AND");
						isPropertyAvailable.addAttribute("property", p);

						Element isNotNull = isPropertyAvailable.addElement(op);
						isNotNull.addAttribute("prepend", "");
						isNotNull.addAttribute("property", p);
						isNotNull.addText(expr);
					} else {
						Element isPropertyAvailable = dynamic55
								.addElement("isPropertyAvailable");
						isPropertyAvailable.addAttribute("prepend", "AND");
						isPropertyAvailable.addAttribute("property", p);
						isPropertyAvailable.addText(expr);
					}
				}

				Element ids55 = dynamic55.addElement("isPropertyAvailable");
				ids55.addAttribute("prepend", "AND");
				ids55.addAttribute("property", pk_property + "s");

				Element isNotNull55 = ids55.addElement("isNotNull");
				isNotNull55.addAttribute("prepend", "");
				isNotNull55.addAttribute("property", pk_property + "s");

				Element iterate55 = isNotNull55.addElement("iterate");
				iterate55.addAttribute("prepend", "");
				iterate55.addAttribute("property", pk_property + "s");
				iterate55.addAttribute("open", "(");
				iterate55.addAttribute("close", ")");
				iterate55.addAttribute("conjunction", "OR");
				iterate55.addText(" ( " + pk_column + " = #" + pk_property
						+ "s[]# ) ");

				/**
				 * 查询数据
				 */
				Element select = r.addElement("select");
				select.addAttribute("id", "select" + className);
				select.addAttribute("parameterClass", alias);
				select.addAttribute("resultMap", alias + "Map");
				select.addText(newline + "\t\t SELECT * FROM " + table);

				Element dynamic5 = select.addElement("dynamic");
				dynamic5.addAttribute("prepend", "WHERE");

				/**
				 * 主键也放入查询条件中
				 */
				colMap.put(pk_column, pk_property);

				entrySet = colMap.entrySet();
				for (Entry<String, String> entry : entrySet) {
					String p = entry.getValue();
					String col = entry.getKey();
					String type = typeMap.get(p);

					String op = "isNotEmpty";
					String expr = " " + col + " = #" + p + "# ";

					if ("boolean".equalsIgnoreCase(type)) {
						op = "";
					} else if ("short".equalsIgnoreCase(type)) {
						op = "";
					} else if ("int".equalsIgnoreCase(type)) {
						op = "";
					} else if ("integer".equalsIgnoreCase(type)) {
						op = "";
					} else if ("long".equalsIgnoreCase(type)) {
						op = "";
					} else if ("float".equalsIgnoreCase(type)) {
						op = "";
					} else if ("double".equalsIgnoreCase(type)) {
						op = "";
					} else if ("currency".equalsIgnoreCase(type)) {
						op = "";
					} else if ("date".equalsIgnoreCase(type)) {
						op = "isNotNull";
					} else if ("time".equalsIgnoreCase(type)) {
						op = "isNotNull";
					} else if ("timestamp".equalsIgnoreCase(type)) {
						op = "isNotNull";
					} else if ("string".equalsIgnoreCase(type)) {
						op = "isNotEmpty";
					} else if ("text".equalsIgnoreCase(type)) {
						expr = " " + col + " like #" + p + "# ";
					} else if ("clob".equalsIgnoreCase(type)) {
						expr = " " + col + " like #" + p + "# ";
					} else if ("binary".equalsIgnoreCase(type)) {
						continue;
					} else if ("blob".equalsIgnoreCase(type)) {
						continue;
					} else {
						op = "isNotNull";
					}

					if (op.trim().length() > 0) {
						Element isPropertyAvailable = dynamic5
								.addElement("isPropertyAvailable");
						isPropertyAvailable.addAttribute("prepend", "AND");
						isPropertyAvailable.addAttribute("property", p);

						Element isNotNull = isPropertyAvailable.addElement(op);
						isNotNull.addAttribute("prepend", "");
						isNotNull.addAttribute("property", p);
						isNotNull.addText(expr);
					} else {
						Element isPropertyAvailable = dynamic5
								.addElement("isPropertyAvailable");
						isPropertyAvailable.addAttribute("prepend", "AND");
						isPropertyAvailable.addAttribute("property", p);
						isPropertyAvailable.addText(expr);
					}
				}

				Element ids = dynamic5.addElement("isPropertyAvailable");
				ids.addAttribute("prepend", "AND");
				ids.addAttribute("property", pk_property + "s");

				Element isNotNull = ids.addElement("isNotNull");
				isNotNull.addAttribute("prepend", "");
				isNotNull.addAttribute("property", pk_property + "s");

				Element iterate = isNotNull.addElement("iterate");
				iterate.addAttribute("prepend", "");
				iterate.addAttribute("property", pk_property + "s");
				iterate.addAttribute("open", "(");
				iterate.addAttribute("close", ")");
				iterate.addAttribute("conjunction", "OR");
				iterate.addText(" ( " + pk_column + " = #" + pk_property
						+ "s[]# ) ");

				/**
				 * 产生Hibernate查询
				 */
				Element hbmQuery = r.addElement("select");
				hbmQuery.addAttribute("id", "query" + className);
				hbmQuery.addAttribute("parameterClass", alias);
				hbmQuery.addAttribute("resultClass", alias);
				hbmQuery.addText(newline + "\t\t from " + className + " as a ");

				Element dynamic555 = hbmQuery.addElement("dynamic");
				dynamic555.addAttribute("prepend", "where");

				/**
				 * 主键也放入查询条件中
				 */
				colMap.put(pk_property, pk_property);

				entrySet = colMap.entrySet();
				for (Entry<String, String> entry : entrySet) {
					String p = entry.getValue();
					String type = typeMap.get(p);

					String op = "isNotEmpty";
					String expr = " a." + p + " = #" + p + "# ";

					if ("boolean".equalsIgnoreCase(type)) {
						op = "";
					} else if ("short".equalsIgnoreCase(type)) {
						op = "";
					} else if ("int".equalsIgnoreCase(type)) {
						op = "";
					} else if ("integer".equalsIgnoreCase(type)) {
						op = "";
					} else if ("long".equalsIgnoreCase(type)) {
						op = "";
					} else if ("float".equalsIgnoreCase(type)) {
						op = "";
					} else if ("double".equalsIgnoreCase(type)) {
						op = "";
					} else if ("currency".equalsIgnoreCase(type)) {
						op = "";
					} else if ("date".equalsIgnoreCase(type)) {
						op = "isNotNull";
					} else if ("time".equalsIgnoreCase(type)) {
						op = "isNotNull";
					} else if ("timestamp".equalsIgnoreCase(type)) {
						op = "isNotNull";
					} else if ("string".equalsIgnoreCase(type)) {
						op = "isNotEmpty";
					} else if ("text".equalsIgnoreCase(type)) {
						expr = " a." + p + " like #" + p + "# ";
					} else if ("clob".equalsIgnoreCase(type)) {
						expr = " a." + p + " like #" + p + "# ";
					} else if ("binary".equalsIgnoreCase(type)) {
						continue;
					} else if ("blob".equalsIgnoreCase(type)) {
						continue;
					} else {
						op = "isNotNull";
					}

					if (op.trim().length() > 0) {
						Element isPropertyAvailable = dynamic555
								.addElement("isPropertyAvailable");
						isPropertyAvailable.addAttribute("prepend", "and");
						isPropertyAvailable.addAttribute("property", p);

						Element isNotNull555 = isPropertyAvailable
								.addElement(op);
						isNotNull555.addAttribute("prepend", "");
						isNotNull555.addAttribute("property", p);
						isNotNull555.addText(expr);
					} else {
						Element isPropertyAvailable = dynamic555
								.addElement("isPropertyAvailable");
						isPropertyAvailable.addAttribute("prepend", "and");
						isPropertyAvailable.addAttribute("property", p);
						isPropertyAvailable.addText(expr);
					}
				}

				Element ids555 = dynamic555.addElement("isPropertyAvailable");
				ids555.addAttribute("prepend", "and");
				ids555.addAttribute("property", pk_property + "s");

				Element isNotNull555 = ids555.addElement("isNotNull");
				isNotNull555.addAttribute("prepend", "");
				isNotNull555.addAttribute("property", pk_property + "s");

				Element iterate555 = isNotNull555.addElement("iterate");
				iterate555.addAttribute("prepend", "");
				iterate555.addAttribute("property", pk_property + "s");
				iterate555.addAttribute("open", "(");
				iterate555.addAttribute("close", ")");
				iterate555.addAttribute("conjunction", "or");
				iterate555.addText(" ( a." + pk_property + " = #" + pk_property
						+ "s[]# ) ");
			}

			String filename = file.getName();
			filename = filename.replaceAll(".hbm.xml", "_sqlmap.xml");

			String toFile = todir + "/" + filename;

			OutputFormat format = new OutputFormat();

			format.setPadText(true);
			format.setNewlines(true);
			format.setIndentSize(4);
			format.setEncoding("UTF-8");
			format.setLineSeparator(newline);
			format.setNewLineAfterDeclaration(true);
			format.setSuppressDeclaration(true);

			byte[] bytes = Dom4jUtils.getBytesFromDocument(d, format);
			FileUtils.save(toFile, bytes);

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
		Hbm2SqlMap task = new Hbm2SqlMap();
		task.setDir(args[0]);
		task.setTodir(args[1]);
		task.execute();
	}

}
