package com.glaf.core.web.springmvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.db.dataexport.DbToDBMyBatisExporter;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.SystemParam;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.entity.hibernate.HibernateBeanFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ISystemParamService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.ZipUtils;
import com.glaf.core.xml.XmlWriter;

@Controller("/sys/table")
@RequestMapping("/sys/table")
public class MxSystemDbTableController {
	protected static final Log logger = LogFactory
			.getLog(MxSystemDbTableController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected ITableDataService tableDataService;

	protected ITableDefinitionService tableDefinitionService;

	protected ITablePageService tablePageService;

	protected ISystemParamService systemParamService;
	
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String tableName = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = RequestUtils.decodeString(tableName);
		} else {
			tableName = request.getParameter("tableName");
		}
		String businessKey = request.getParameter("businessKey");
		String primaryKey = null;
		ColumnDefinition idColumn = null;
		List<ColumnDefinition> columns = null;
		try {
			if (StringUtils.isNotEmpty(tableName)) {
				columns = DBUtils.getColumnDefinitions(tableName);
				modelMap.put("tableName", tableName);
				modelMap.put("tableName_enc",
						RequestUtils.encodeString(tableName));
				List<String> pks = DBUtils.getPrimaryKeys(tableName);
				if (pks != null && !pks.isEmpty()) {
					if (pks.size() == 1) {
						primaryKey = pks.get(0);
					}
				}
				if (primaryKey != null) {
					for (ColumnDefinition column : columns) {
						if (StringUtils.equalsIgnoreCase(primaryKey,
								column.getColumnName())) {
							idColumn = column;
							break;
						}
					}
				}
				if (idColumn != null) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName(tableName);
					ColumnModel idCol = new ColumnModel();
					idCol.setColumnName(idColumn.getColumnName());
					idCol.setJavaType(idColumn.getJavaType());
					if ("Integer".equals(idColumn.getJavaType())) {
						idCol.setValue(Integer.parseInt(businessKey));
					} else if ("Long".equals(idColumn.getJavaType())) {
						idCol.setValue(Long.parseLong(businessKey));
					} else {
						idCol.setValue(businessKey);
					}
					tableModel.setIdColumn(idCol);
					Map<String, Object> dataMap = tableDataService
							.getTableDataByPrimaryKey(tableModel);
					Map<String, Object> rowMap = QueryUtils
							.lowerKeyMap(dataMap);
					for (ColumnDefinition column : columns) {
						Object value = rowMap.get(column.getColumnName()
								.toLowerCase());
						column.setValue(value);
					}
					modelMap.put("idColumn", idColumn);
					modelMap.put("columns", columns);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		String x_view = ViewProperties.getString("sys_table.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/table/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/exportData")
	public void exportData(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		StringBuffer sb = new StringBuffer();
		String tables = request.getParameter("exportTables");
		String dbType = request.getParameter("dbType");
		if (StringUtils.isNotEmpty(dbType) && StringUtils.isNotEmpty(tables)) {
			List<String> list = StringTools.split(tables);
			for (String tablename : list) {
				if (StringUtils.isNotEmpty(tablename)) {
					logger.debug("process table:" + tablename);
					List<ColumnDefinition> columns = DBUtils
							.getColumnDefinitions(tablename);
					TablePageQuery query = new TablePageQuery();
					query.tableName(tablename);
					query.firstResult(0);
					query.maxResults(5000);
					int count = tablePageService.getTableCount(query);
					if (count <= 5000) {
						List<Map<String, Object>> rows = tablePageService
								.getTableData(query);
						if (rows != null && !rows.isEmpty()) {
							for (Map<String, Object> dataMap : rows) {
								Map<String, Object> lowerMap = QueryUtils
										.lowerKeyMap(dataMap);
								sb.append(" insert into ").append(tablename)
										.append(" (");
								for (int i = 0, len = columns.size(); i < len; i++) {
									ColumnDefinition column = columns.get(i);
									sb.append(column.getColumnName()
											.toLowerCase());
									if (i < columns.size() - 1) {
										sb.append(", ");
									}
								}
								sb.append(" ) values (");
								for (int i = 0, len = columns.size(); i < len; i++) {
									ColumnDefinition column = columns.get(i);
									Object value = lowerMap.get(column
											.getColumnName().toLowerCase());
									if (value != null) {
										if (value instanceof Short) {
											sb.append(value);
										} else if (value instanceof Integer) {
											sb.append(value);
										} else if (value instanceof Long) {
											sb.append(value);
										} else if (value instanceof Double) {
											sb.append(value);
										} else if (value instanceof String) {
											String str = (String) value;
											str = StringTools.replace(str, "'",
													"''");
											sb.append("'").append(str)
													.append("'");
										} else if (value instanceof Date) {
											Date date = (Date) value;
											if (StringUtils.equalsIgnoreCase(
													dbType, "oracle")) {
												sb.append(" to_date('")
														.append(DateUtils
																.getDateTime(date))
														.append("', 'yyyy-mm-dd hh24:mi:ss')");
											} else if (StringUtils
													.equalsIgnoreCase(dbType,
															"db2")) {
												sb.append(" TO_DATE('")
														.append(DateUtils
																.getDateTime(date))
														.append("', ''YYY-MM-DD HH24:MI:SS')");
											} else {
												sb.append("'")
														.append(DateUtils
																.getDateTime(date))
														.append("'");
											}
										} else {
											String str = value.toString();
											str = StringTools.replace(str, "'",
													"''");
											sb.append("'").append(str)
													.append("'");
										}
									} else {
										sb.append("null");
									}
									if (i < columns.size() - 1) {
										sb.append(", ");
									}
								}
								sb.append(");");
								sb.append(FileUtils.newline);
							}
						}
					}
					sb.append(FileUtils.newline);
					sb.append(FileUtils.newline);
				}
			}
		}

		try {
			ResponseUtils.download(request, response, sb.toString().getBytes(),
					"insert_" + DateUtils.getDate(new Date()) + "." + dbType
							+ ".sql");
		} catch (ServletException ex) {
			ex.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping("/exportDB")
	public void exportDB(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String systemName = request.getParameter("systemName");
		if (systemName == null) {
			systemName = "default";
		}
		String dataPath = null;
		String filename = null;
		String dbType = request.getParameter("dbType");
		logger.debug("dbType:" + dbType);
		if (StringUtils.isNotEmpty(dbType)) {
			if (!running.get()) {
				InputStream inputStream = null;
				try {
					running.set(true);
					if (StringUtils.equals(dbType, "h2")) {
						dataPath = "/data/" + DateUtils.getNowYearMonthDay()
								+ "/glafdb";
						filename = "/data/" + DateUtils.getNowYearMonthDay()
								+ "/glafdb.h2.db";
						FileUtils.mkdirs("/data/"
								+ DateUtils.getNowYearMonthDay());
						DbToDBMyBatisExporter exp = new DbToDBMyBatisExporter();
						exp.exportTables(systemName, "h2", dataPath);
					} else if (StringUtils.equals(dbType, "sqlite")) {
						dataPath = "/data/" + DateUtils.getNowYearMonthDay()
								+ "/glafdb.db";
						filename = dataPath;
						FileUtils.mkdirs("/data/"
								+ DateUtils.getNowYearMonthDay());
						DbToDBMyBatisExporter exp = new DbToDBMyBatisExporter();
						exp.exportTables(systemName, "sqlite", dataPath);
					}
					if (dataPath != null) {
						File file = new File(filename);
						File[] files = { file };
						ZipUtils.compressFile(files, filename + ".zip");
						inputStream = FileUtils.getInputStream(filename
								+ ".zip");
						ResponseUtils.download(request, response, inputStream,
								"glafdb_" + dbType + ".zip");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					running.set(false);
					IOUtils.closeStream(inputStream);
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping("/exportSysTables")
	public void exportSysTables(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		StringBuffer sb = new StringBuffer();
		JSONArray result = null;
		SystemParam param = systemParamService.getSystemParam("sys_table");
		if (param != null && StringUtils.isNotEmpty(param.getTextVal())) {
			result = JSON.parseArray(param.getTextVal());
		}
		String dbType = request.getParameter("dbType");
		if (StringUtils.isNotEmpty(dbType) && result != null) {
			for (int index = 0, len = result.size(); index < len; index++) {
				JSONObject json = result.getJSONObject(index);
				String tablename = json.getString("tablename");
				if (StringUtils.isNotEmpty(tablename)) {
					logger.debug("process table:" + tablename);
					List<ColumnDefinition> columns = DBUtils
							.getColumnDefinitions(tablename);
					TablePageQuery query = new TablePageQuery();
					query.tableName(tablename);
					query.firstResult(0);
					query.maxResults(5000);
					int count = tablePageService.getTableCount(query);
					if (count <= 5000) {
						List<Map<String, Object>> rows = tablePageService
								.getTableData(query);
						if (rows != null && !rows.isEmpty()) {
							for (Map<String, Object> dataMap : rows) {
								Map<String, Object> lowerMap = QueryUtils
										.lowerKeyMap(dataMap);
								sb.append(" insert into ").append(tablename)
										.append(" (");
								for (int i = 0, len2 = columns.size(); i < len2; i++) {
									ColumnDefinition column = columns.get(i);
									sb.append(column.getColumnName()
											.toLowerCase());
									if (i < columns.size() - 1) {
										sb.append(", ");
									}
								}
								sb.append(" ) values (");
								for (int i = 0, len2 = columns.size(); i < len2; i++) {
									ColumnDefinition column = columns.get(i);
									Object value = lowerMap.get(column
											.getColumnName().toLowerCase());
									if (value != null) {
										if (value instanceof Short) {
											sb.append(value);
										} else if (value instanceof Integer) {
											sb.append(value);
										} else if (value instanceof Long) {
											sb.append(value);
										} else if (value instanceof Double) {
											sb.append(value);
										} else if (value instanceof String) {
											String str = (String) value;
											str = StringTools.replace(str, "'",
													"''");
											sb.append("'").append(str)
													.append("'");
										} else if (value instanceof Date) {
											Date date = (Date) value;
											if (StringUtils.equalsIgnoreCase(
													dbType, "oracle")) {
												sb.append(" to_date('")
														.append(DateUtils
																.getDateTime(date))
														.append("', 'yyyy-mm-dd hh24:mi:ss')");
											} else if (StringUtils
													.equalsIgnoreCase(dbType,
															"db2")) {
												sb.append(" TO_DATE('")
														.append(DateUtils
																.getDateTime(date))
														.append("', ''YYY-MM-DD HH24:MI:SS')");
											} else {
												sb.append("'")
														.append(DateUtils
																.getDateTime(date))
														.append("'");
											}
										} else {
											String str = value.toString();
											str = StringTools.replace(str, "'",
													"''");
											sb.append("'").append(str)
													.append("'");
										}
									} else {
										sb.append("null");
									}
									if (i < columns.size() - 1) {
										sb.append(", ");
									}
								}
								sb.append(");");
								sb.append(FileUtils.newline);
							}
						}
					}
					sb.append(FileUtils.newline);
					sb.append(FileUtils.newline);
				}
			}
		}

		try {
			ResponseUtils.download(request, response, sb.toString().getBytes(),
					"insert_sys_" + DateUtils.getDate(new Date()) + "."
							+ dbType + ".sql");
		} catch (ServletException ex) {
			ex.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping("/genCreateScripts")
	public void genCreateScripts(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		StringBuffer sb = new StringBuffer();
		String tables = request.getParameter("tables");
		String dbType = request.getParameter("dbType");
		if (StringUtils.isNotEmpty(dbType) && StringUtils.isNotEmpty(tables)) {
			List<String> list = StringTools.split(tables);
			for (String table : list) {
				List<ColumnDefinition> columns = DBUtils
						.getColumnDefinitions(table);
				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(table);
				tableDefinition.setColumns(columns);
				for (ColumnDefinition column : columns) {
					if (column.isPrimaryKey()) {
						tableDefinition.setIdColumn(column);
						String sql = DBUtils.getCreateTableScript(dbType,
								tableDefinition);
						sb.append(FileUtils.newline).append(sql)
								.append(FileUtils.newline)
								.append(FileUtils.newline);
						break;
					}
				}
			}
		}

		try {
			ResponseUtils.download(request, response, sb.toString().getBytes(),
					"createTable_" + DateUtils.getDate(new Date()) + ".sql");
		} catch (ServletException ex) {
			ex.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping("/genMappings")
	public void genMappings(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String tables = request.getParameter("tables");
		String dbType = request.getParameter("dbType");
		if (StringUtils.isNotEmpty(dbType) && StringUtils.isNotEmpty(tables)) {
			XmlWriter xmlWriter = new XmlWriter();
			Map<String, byte[]> bytesMap = new java.util.HashMap<String, byte[]>();
			List<String> list = StringTools.split(tables);
			for (String table : list) {
				List<ColumnDefinition> columns = DBUtils
						.getColumnDefinitions(table);
				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setPackageName("com.glaf.apps");
				tableDefinition.setModuleName("apps");
				tableDefinition.setTableName(table);
				tableDefinition.setClassName(StringTools.upper(StringTools
						.camelStyle(table)));
				tableDefinition.setTitle(tableDefinition.getClassName());
				tableDefinition.setEnglishTitle(tableDefinition.getClassName());
				tableDefinition.setEntityName(StringTools.upper(StringTools
						.camelStyle(table)));
				tableDefinition.setColumns(columns);
				for (ColumnDefinition column : columns) {
					column.setEditable(true);
					column.setUpdatable(true);
					column.setDisplayType(4);
					column.setName(StringTools.lower(StringTools
							.camelStyle(column.getColumnName())));
					column.setTitle(column.getName());
					column.setEnglishTitle(column.getName());
					if (column.isPrimaryKey()) {
						column.setUpdatable(false);
						tableDefinition.setIdColumn(column);
					}
				}
				Document doc = xmlWriter.write(tableDefinition);
				byte[] bytes = Dom4jUtils.getBytesFromPrettyDocument(doc);
				bytesMap.put(table + ".mapping.xml", bytes);
			}
			if (!bytesMap.isEmpty()) {
				byte[] bytes = ZipUtils.toZipBytes(bytesMap);
				try {
					ResponseUtils.download(request, response, bytes,
							"mappings.zip");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@RequestMapping("/initHB")
	public ModelAndView initHB(HttpServletRequest request, ModelMap modelMap) {
		return new ModelAndView("/modules/sys/table/initHB", modelMap);
	}

	@ResponseBody
	@RequestMapping("/json")
	public byte[] json(HttpServletRequest request) throws IOException {
		JSONObject result = new JSONObject();
		JSONArray rowsJSON = new JSONArray();
		String[] types = { "TABLE" };
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getTables(null, null, null, types);
			int startIndex = 1;
			while (rs.next()) {
				String tableName = rs.getObject("TABLE_NAME").toString();
				if (tableName.toLowerCase().startsWith("cell_useradd")) {
					continue;
				}
				if (DBUtils.isTemoraryTable(tableName)) {
					continue;
				}
				JSONObject json = new JSONObject();
				json.put("startIndex", startIndex++);
				json.put("cat", rs.getObject("TABLE_CAT"));
				json.put("schem", rs.getObject("TABLE_SCHEM"));
				json.put("tablename", tableName);
				json.put("tableName_enc", RequestUtils.encodeString(tableName));
				rowsJSON.add(json);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}

		result.put("rows", rowsJSON);
		result.put("total", rowsJSON.size());
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("system_table.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/table/list", modelMap);
	}

	@RequestMapping("/resultList")
	public ModelAndView resultList(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");
		RequestUtils.setRequestParameterToAttribute(request);
		String tableName = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = RequestUtils.decodeString(tableName);
		} else {
			tableName = request.getParameter("tableName");
		}
		List<ColumnDefinition> columns = null;
		try {
			if (StringUtils.isNotEmpty(tableName)) {
				columns = DBUtils.getColumnDefinitions(tableName);
				modelMap.put("columns", columns);
				modelMap.put("tableName_enc",
						RequestUtils.encodeString(tableName));
				List<String> pks = DBUtils.getPrimaryKeys(tableName);
				if (pks != null && !pks.isEmpty()) {
					if (pks.size() == 1) {
						modelMap.put("primaryKey", pks.get(0));
						for (ColumnDefinition column : columns) {
							if (StringUtils.equalsIgnoreCase(pks.get(0),
									column.getColumnName())) {
								modelMap.put("idColumn", column);
								break;
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_table.resultList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/table/resultList", modelMap);
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String tableName = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = RequestUtils.decodeString(tableName);
		} else {
			tableName = request.getParameter("tableName");
		}

		Collection<String> rejects = new java.util.ArrayList<String>();
		rejects.add("FILEATT");
		rejects.add("ATTACHMENT");
		rejects.add("CMS_PUBLICINFO");
		rejects.add("SYS_LOB");
		rejects.add("SYS_MAIL_FILE");
		rejects.add("SYS_DBID");
		rejects.add("SYS_PROPERTY");

		if (conf.get("table.rejects") != null) {
			String str = conf.get("table.rejects");
			List<String> list = StringTools.split(str);
			for (String t : list) {
				rejects.add(t.toUpperCase());
			}
		}

		String businessKey = request.getParameter("businessKey");
		String primaryKey = null;
		ColumnDefinition idColumn = null;
		List<ColumnDefinition> columns = null;
		try {
			/**
			 * 保证系统安全性不得修改系统表及工作流的数据
			 */
			if (StringUtils.isNotEmpty(tableName)
					&& !rejects.contains(tableName.toUpperCase())
					&& !StringUtils.startsWithIgnoreCase(tableName, "user")
					&& !StringUtils.startsWithIgnoreCase(tableName, "net")
					&& !StringUtils.startsWithIgnoreCase(tableName, "sys")
					&& !StringUtils.startsWithIgnoreCase(tableName, "jbpm")
					&& !StringUtils.startsWithIgnoreCase(tableName, "act")) {
				columns = DBUtils.getColumnDefinitions(tableName);
				modelMap.put("tableName", tableName);
				modelMap.put("tableName_enc",
						RequestUtils.encodeString(tableName));
				List<String> pks = DBUtils.getPrimaryKeys(tableName);
				if (pks != null && !pks.isEmpty()) {
					if (pks.size() == 1) {
						primaryKey = pks.get(0);
					}
				}
				if (primaryKey != null) {
					for (ColumnDefinition column : columns) {
						if (StringUtils.equalsIgnoreCase(primaryKey,
								column.getColumnName())) {
							idColumn = column;
							break;
						}
					}
				}
				if (idColumn != null) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName(tableName);
					ColumnModel idCol = new ColumnModel();
					idCol.setColumnName(idColumn.getColumnName());
					idCol.setJavaType(idColumn.getJavaType());
					if ("Integer".equals(idColumn.getJavaType())) {
						idCol.setValue(Integer.parseInt(businessKey));
					} else if ("Long".equals(idColumn.getJavaType())) {
						idCol.setValue(Long.parseLong(businessKey));
					} else {
						idCol.setValue(businessKey);
					}
					tableModel.setIdColumn(idCol);

					for (ColumnDefinition column : columns) {
						String value = request.getParameter(column
								.getColumnName());
						ColumnModel col = new ColumnModel();
						col.setColumnName(column.getColumnName());
						col.setJavaType(column.getJavaType());
						if (value != null && value.trim().length() > 0
								&& !value.equals("null")) {
							if ("Integer".equals(column.getJavaType())) {
								col.setValue(Integer.parseInt(value));
								tableModel.addColumn(col);
							} else if ("Long".equals(column.getJavaType())) {
								col.setValue(Long.parseLong(value));
								tableModel.addColumn(col);
							} else if ("Double".equals(column.getJavaType())) {
								col.setValue(Double.parseDouble(value));
								tableModel.addColumn(col);
							} else if ("Date".equals(column.getJavaType())) {
								col.setValue(DateUtils.toDate(value));
								tableModel.addColumn(col);
							} else if ("String".equals(column.getJavaType())) {
								col.setValue(value);
								tableModel.addColumn(col);
							}
						}
					}

					tableDataService.updateTableData(tableModel);

					return ResponseUtils.responseJsonResult(true);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSystemParamService(ISystemParamService systemParamService) {
		this.systemParamService = systemParamService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@ResponseBody
	@RequestMapping("/sysTables")
	public byte[] sysTables(HttpServletRequest request) throws IOException {
		JSONArray result = new JSONArray();
		SystemParam param = systemParamService.getSystemParam("sys_table");
		if (param != null && StringUtils.isNotEmpty(param.getTextVal())) {
			result = JSON.parseArray(param.getTextVal());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/updateHibernateDDL")
	public byte[] updateHibernateDDL(HttpServletRequest request)
			throws IOException {
		try {
			HibernateBeanFactory.reload();
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

}
