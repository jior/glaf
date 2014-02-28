package com.glaf.core.startup;

import java.sql.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.DataSourceConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.el.ExpressionTools;
import com.glaf.core.util.PropertiesUtils;
import com.glaf.core.util.StringTools;

public class MultiDBStartup implements Bootstrap {

	protected final static Log logger = LogFactory.getLog(MultiDBStartup.class);

	public void startup(ServletContext context, String text) {
		logger.debug("-----------------MultiDBStartup.startup----------------");
		if (StringUtils.isNotEmpty(text)) {
			JSONObject json = JSON.parseObject(text);
			logger.debug(json.toJSONString());
			String sql = json.getString("sql");
			String dbName = json.getString("dbName");
			if (StringUtils.isNotEmpty(sql) && StringUtils.isNotEmpty(dbName)) {
				Properties defaultProps = DBConfiguration
						.getTemplateProperties(Environment.DEFAULT_SYSTEM_NAME);
				Properties props = DBConfiguration.getTemplateProperties(dbName);
				logger.debug(dbName+" props:"+props);
				if (props != null
						&& StringUtils
								.isNotEmpty(props.getProperty("jdbc.url"))) {
					String old_url = props.getProperty("jdbc.url");
					Connection conn = null;
					PreparedStatement stmt = null;
					ResultSet rs = null;
					try {
						conn = DataSourceConfig.getDefaultConnection();
						if (conn != null) {
							stmt = conn.prepareStatement(sql);
							rs = stmt.executeQuery();
							while (rs.next()) {
								Map<String, Object> dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
								Enumeration<?> e = props.keys();
								while (e.hasMoreElements()) {
									String key = (String) e.nextElement();
									String value = props.getProperty(key);
									dataMap.put(key, value);
								}
								String host = rs.getString(1);
								String databaseName = rs.getString(2);
								String name = rs.getString(5);
								if (databaseName.indexOf("$") != -1) {
									String url_xy = defaultProps
											.getProperty("jdbc.url");
									String driver = defaultProps
											.getProperty("jdbc.driver");
									String db = null;
									if (StringUtils
											.equals(driver,
													"com.microsoft.sqlserver.jdbc.SQLServerDriver")) {
										db = url_xy.substring(
												url_xy.lastIndexOf("=") + 1,
												url_xy.length());
									} else if (StringUtils.equals(driver,
											"net.sourceforge.jtds.jdbc.Driver")) {
										db = url_xy.substring(
												url_xy.lastIndexOf("/") + 1,
												url_xy.length());
									}
									if (db != null) {
										databaseName = StringTools
												.replaceIgnoreCase(
														databaseName, "self",
														db);
									}
								}
								logger.debug("databaseName:" + databaseName);
								dataMap.put("host", host);
								dataMap.put("databaseName", databaseName);
								props.put("jdbc.user", rs.getString(3));
								props.put("jdbc.password", rs.getString(4));

								logger.debug(dataMap);
								String url = ExpressionTools.evaluate(old_url,
										dataMap);
								props.put("jdbc.url", url);
								host = StringTools.replace(host, ".", "_");
								props.put("jdbc.name", name);

								/**
								 * 检查连接信息，如果正确，保存配置
								 */
								if (DataSourceConfig.getConnection(props) != null) {
									String filename = SystemConfig
											.getConfigRootPath()
											+ "/conf/jdbc/"
											+ host
											+ "_"
											+ name
											+ ".properties";
									PropertiesUtils.save(filename, props);
									logger.info("成功保存数据库配置文件:" + filename);
								}
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex);
					} finally {
						JdbcUtils.close(rs);
						JdbcUtils.close(stmt);
						JdbcUtils.close(conn);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=pMagic2013";
		System.out
				.println(url.substring(url.lastIndexOf("=") + 1, url.length()));
		url = "jdbc:jtds:sqlserver://127.0.0.1:1433/pMagic2013";
		System.out
				.println(url.substring(url.lastIndexOf("/") + 1, url.length()));
	}

}
