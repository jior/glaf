package com.glaf.core.web.springmvc;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;

@Controller("/system/table")
@RequestMapping("/system/table")
public class MxSystemDbTableController {

	@ResponseBody
	@RequestMapping("/genCreateScripts")
	public byte[] genCreateScripts(HttpServletRequest request)
			throws IOException {
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

		return sb.toString().getBytes("UTF-8");
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
				tableName = tableName.toLowerCase();
				if (tableName.startsWith("act_")) {
					continue;
				}
				if (tableName.startsWith("jbpm_")) {
					continue;
				}
				if (tableName.startsWith("tmp_")) {
					continue;
				}
				if (tableName.startsWith("temp_")) {
					continue;
				}
				if (tableName.startsWith("demo_")) {
					continue;
				}
				if (tableName.startsWith("wwv_")) {
					continue;
				}
				if (tableName.startsWith("aq_")) {
					continue;
				}
				if (tableName.startsWith("bsln_")) {
					continue;
				}
				if (tableName.startsWith("mgmt_")) {
					continue;
				}
				if (tableName.startsWith("ogis_")) {
					continue;
				}
				if (tableName.startsWith("ols_")) {
					continue;
				}
				if (tableName.startsWith("em_")) {
					continue;
				}
				if (tableName.startsWith("openls_")) {
					continue;
				}
				if (tableName.startsWith("mrac_")) {
					continue;
				}
				if (tableName.startsWith("orddcm_")) {
					continue;
				}
				if (tableName.startsWith("x_")) {
					continue;
				}
				if (tableName.startsWith("wlm_")) {
					continue;
				}
				if (tableName.startsWith("olap_")) {
					continue;
				}
				if (tableName.startsWith("ggs_")) {
					continue;
				}
				if (tableName.startsWith("jpage_")) {
					continue;
				}
				if (tableName.startsWith("ex_")) {
					continue;
				}
				if (tableName.startsWith("logmnrc_")) {
					continue;
				}
				if (tableName.startsWith("logmnrg_")) {
					continue;
				}
				if (tableName.startsWith("olap_")) {
					continue;
				}
				if (tableName.startsWith("sto_")) {
					continue;
				}
				if (tableName.startsWith("sdo_")) {
					continue;
				}
				if (tableName.startsWith("sys_iot_")) {
					continue;
				}
				if (tableName.indexOf("$") != -1) {
					continue;
				}
				if (tableName.indexOf("+") != -1) {
					continue;
				}
				if (tableName.indexOf("-") != -1) {
					continue;
				}
				if (tableName.indexOf("?") != -1) {
					continue;
				}
				if (tableName.indexOf("=") != -1) {
					continue;
				}
				JSONObject json = new JSONObject();
				json.put("startIndex", startIndex++);
				json.put("cat", rs.getObject("TABLE_CAT"));
				json.put("schem", rs.getObject("TABLE_SCHEM"));
				json.put("tablename", tableName);
				rowsJSON.add(json);
			}
		} catch (Exception ex) {
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

}
