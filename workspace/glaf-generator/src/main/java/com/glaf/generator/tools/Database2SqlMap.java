package com.glaf.generator.tools;

import java.sql.*;
import java.util.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

public class Database2SqlMap {
	public final static String newline = System.getProperty("line.separator");

	public String todir;

	public String getTodir() {
		return todir;
	}

	public void setTodir(String todir) {
		this.todir = todir;
	}

	public void execute() {
		Connection con = null;
		try {
			con = DBConnectionFactory.getConnection();
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, null, null);
			while (rs.next()) {
				String tablename = rs.getString(3);
				System.out.println("tablename->" + tablename);
				Table table = new Table();
				table.setTablename(tablename);
				// 获取主键

				ResultSet rs2 = dbmd.getPrimaryKeys(null, null, tablename);
				while (rs2.next()) {
					System.out.println("目录名：" + rs2.getString(1));
					System.out.println("模式名：" + rs2.getString(2));
					System.out.println("表名：" + rs2.getString(3));
					System.out.println("列名：" + rs2.getString(4));
					System.out.println("列名顺序号：" + rs2.getString(5));
					System.out.println("主键名：" + rs2.getString(6));
				}
				rs2.close();

				ResultSet rs3 = dbmd.getColumns(null, null, tablename, "%");
				int index = 0;
				while (rs3.next()) {
					Column col = new Column();
					String name = rs3.getString(4);
					String type = rs3.getString(5);
					col.setName(name);
					col.setTypeName(type);
					if (index == 0) {
						col.setPrimaryKey(true);
						index++;
					}
					System.out.println(name + "\t" + type + "\t"
							+ rs3.getString(6));
					table.getColumns().add(col);
				}
				this.convert(table);
				rs3.close();
			}

			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException ex) {
			}
		}
	}

	public void convert(Table table) throws Exception {

		Document d = DocumentHelper.createDocument();
		d.addDocType("sqlMap", "-//ibatis.apache.org//DTD SQL Map 2.0//EN",
				"http://ibatis.apache.org/dtd/sql-map-2.dtd");

		Element r = d.addElement("sqlMap");

		String tablename = table.getTablename();

		String alias = tablename;
		String className = tablename;
		if (tablename.indexOf('.') != -1) {
			alias = tablename.substring(tablename.lastIndexOf('.') + 1,
					tablename.length());
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
		typeAlias.addAttribute("type", tablename);

		String pk_property = null;
		String pk_column = null;
		String pk_type = null;
		Map<String, String> colMap = new TreeMap<String, String>();
		Map<String, String> typeMap = new TreeMap<String, String>();

		List<Column> cols = table.getColumns();

		Iterator<Column> iterator001 = cols.iterator();
		while (iterator001.hasNext()) {
			Column c = iterator001.next();
			String colName = c.getName();
			String type = c.getTypeName();
			colMap.put(colName, colName);
			typeMap.put(colName, type);
			if (c.isPrimaryKey()) {
				pk_property = colName;
				pk_column = colName;
				pk_type = type;
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
		buffer.append(newline).append("\t\t INSERT INTO ").append(tablename);
		buffer.append(" ( ").append(pk_column).append(", ");

		StringBuffer sb = new StringBuffer();
		sb.append(newline).append("\t\t VALUES ( #").append(pk_property)
				.append("#, ");

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
		buffer.append(newline).append("\t\t INSERT INTO ").append(tablename);
		buffer.append(" ( ").append(pk_column).append(' ');

		sb.delete(0, sb.length());
		sb.append(newline).append("\t\t VALUES ( #").append(pk_property)
				.append("#, ");

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

		dynamicInsert.addText(newline + "\t\t ) VALUES ( #" + pk_property
				+ "# ");

		Element dynamicy = dynamicInsert.addElement("dynamic");
		dynamicy.addAttribute("prepend", "");

		i = colMap.keySet().iterator();
		while (i.hasNext()) {
			String col = i.next();
			String p =  colMap.get(col);
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
		update.addText(newline + "\t\t UPDATE " + tablename);

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
		delete.addText(newline + "\t\t DELETE FROM " + tablename + " WHERE "
				+ pk_column + " = #" + pk_property + "# ");

		/**
		 * 查询统计数据
		 */
		Element count = r.addElement("select");
		count.addAttribute("id", "count" + className);
		count.addAttribute("parameterClass", alias);
		count.addAttribute("resultMap", alias + "Map");
		count.addText(newline + "\t\t SELECT COUNT(*) FROM " + tablename);

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
		iterate55.addText(" ( " + pk_column + " = #" + pk_property + "s[]# ) ");

		/**
		 * 查询数据
		 */
		Element select = r.addElement("select");
		select.addAttribute("id", "select" + className);
		select.addAttribute("parameterClass", alias);
		select.addAttribute("resultMap", alias + "Map");
		select.addText(newline + "\t\t SELECT * FROM " + tablename);

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
		iterate.addText(" ( " + pk_column + " = #" + pk_property + "s[]# ) ");

		String filename = tablename + "_sqlmap.xml";

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

	public static void main(String[] args) {
		Database2SqlMap task = new Database2SqlMap();
		task.setTodir(args[0]);
		task.execute();
	}

}
