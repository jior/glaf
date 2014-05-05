package com.glaf.generator.tools;

import java.io.*;
import java.util.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import com.glaf.core.util.*;

public class Pdm2SqlMap {
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
			InputStream inputStream = new FileInputStream(file);
			SAXReader xmlReader = new SAXReader(false);
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List<?> tableList = root.selectNodes("//o:Table");
			Iterator<?> iterator = tableList.iterator();
			while (iterator.hasNext()) {
				Document d = DocumentHelper.createDocument();
				d.addDocType("sqlMap",
						"-//ibatis.apache.org//DTD SQL Map 2.0//EN",
						"http://ibatis.apache.org/dtd/sql-map-2.dtd");

				EntityResolver resolver = new ClassPathEntityResolver();
				xmlReader.setEntityResolver(resolver);
				xmlReader.setValidation(false);
				xmlReader.setMergeAdjacentText(true);
				xmlReader.setIncludeExternalDTDDeclarations(false);
				xmlReader.setIncludeInternalDTDDeclarations(false);
				Element r = d.addElement("sqlMap");

				Element element = (Element) iterator.next();

				String name = element.elementTextTrim("Code");
				String table = element.elementTextTrim("Code");

				if (name == null) {
					continue;
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
				}

				/**
				 * 产生类名及别名
				 */
				Element typeAlias = r.addElement("typeAlias");
				typeAlias.addAttribute("alias", alias);
				typeAlias.addAttribute("type", name);

				String pk_property = null;
				String pk_column = null;
				String pk_type = null;
				Map<String, String> colMap = new TreeMap<String, String>();
				Map<String, String> typeMap = new TreeMap<String, String>();
				Map<String, String> idMap = new HashMap<String, String>();

				List<?> cols = element.element("Columns").elements("Column");

				Iterator<?> iterator001 = cols.iterator();
				while (iterator001.hasNext()) {
					Element ee = (Element) iterator001.next();
					String id = ee.attributeValue("Id");
					String colName = ee.elementTextTrim("Code");
					String dataType = ee.elementTextTrim("DataType");
					if (colName != null && dataType != null) {
						colMap.put(colName, colName);
						typeMap.put(colName, dataType);
						idMap.put(id, colName);
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

										pk_property = idMap.get(id);
										pk_column = pk_property;
										pk_type = typeMap.get(pk_property);

										typeMap.put(pk_column, pk_type);
										typeMap.put(pk_property, pk_type);
										break;
									}
								}
							}
						}
					}
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

				Iterator<String> iter = typeMap.keySet().iterator();
				while (iter.hasNext()) {
					String p = iter.next();
					Element e = resultMap.addElement("result");
					e.addAttribute("property", p);
					e.addAttribute("column", p);
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

				Iterator<String> i = colMap.keySet().iterator();
				while (i.hasNext()) {
					String col = i.next();
					String p = colMap.get(col);
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

				i = colMap.keySet().iterator();
				while (i.hasNext()) {
					String col = i.next();
					String p = colMap.get(col);
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

				i = colMap.keySet().iterator();
				while (i.hasNext()) {
					String col = i.next();
					String p = colMap.get(col);
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

				i = colMap.keySet().iterator();
				while (i.hasNext()) {
					String col = i.next();
					String p = colMap.get(col);
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
				Element count = r.addElement("count");
				count.addAttribute("id", "count" + className);
				count.addAttribute("parameterClass", alias);
				count.addAttribute("resultMap", alias + "Map");
				count.addText(newline + "\t\t SELECT COUNT(*) FROM " + table);

				Element dynamic55 = count.addElement("dynamic");
				dynamic55.addAttribute("prepend", "WHERE");

				/**
				 * 主键也放入查询条件中
				 */
				if (pk_column != null) {
					colMap.put(pk_column, pk_property);
				}

				i = colMap.keySet().iterator();
				while (i.hasNext()) {
					String col = i.next();
					String p = colMap.get(col);
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

						Element isNotNull55 = isPropertyAvailable
								.addElement(op);
						isNotNull55.addAttribute("prepend", "");
						isNotNull55.addAttribute("property", p);
						isNotNull55.addText(expr);
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
				if (pk_column != null) {
					colMap.put(pk_column, pk_property);
				}

				i = colMap.keySet().iterator();
				while (i.hasNext()) {
					String col = i.next();
					String p = colMap.get(col);
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

				String filename = table + "_sqlmap.xml";

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
		Pdm2SqlMap task = new Pdm2SqlMap();
		task.setDir(args[0]);
		task.setTodir(args[1]);
		task.execute();
	}

}
