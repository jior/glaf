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

package com.glaf.core.startup;

import java.io.File;
import java.sql.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.db.dataimport.XmlToDbImporter;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;

public class DBUpdateThread extends Thread {

	protected final static Log logger = LogFactory.getLog(DBUpdateThread.class);

	protected java.util.Properties props;

	public DBUpdateThread(java.util.Properties props) {
		this.props = props;
	}

	public void run() {
		logger.debug("->jdbc url:"
				+ props.getProperty(DBConfiguration.JDBC_URL));
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBConnectionFactory.getConnection(props);
			if (conn != null) {
				String path = SystemProperties.getConfigRootPath()
						+ "/conf/bootstrap/update";
				File dir = new File(path);
				File contents[] = dir.listFiles();
				if (contents != null) {
					for (int i = 0; i < contents.length; i++) {
						if (contents[i].isFile()
								&& StringUtils.endsWith(contents[i].getName(),
										".sql")) {
							try {
								conn.setAutoCommit(false);
								String ddlStatements = FileUtils
										.readFile(contents[i].getAbsolutePath());
								DBUtils.executeSchemaResourceIgnoreException(
										conn, ddlStatements);
								conn.commit();
							} catch (Exception ex) {
								ex.printStackTrace();
								logger.error(ex);
							}
						} else if (contents[i].isFile()
								&& StringUtils.endsWith(contents[i].getName(),
										".xml")) {
							XmlToDbImporter imp = new XmlToDbImporter();
							try {
								conn.setAutoCommit(false);
								imp.doImport(contents[i], conn);
								conn.commit();
							} catch (Exception ex) {
								ex.printStackTrace();
								logger.error(ex);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			JdbcUtils.close(stmt);
			JdbcUtils.close(conn);
		}
	}

}
