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

package com.glaf.core.db.dataimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;

public class SqlToDbImporter {

	protected static final Log logger = LogFactory
			.getLog(SqlToDbImporter.class);

	public static void main(String[] args) {
		SqlToDbImporter exp = new SqlToDbImporter();
		// exp.exportTables("glaf", "data/exportDb");
		// exp.exportTables("default", "data/exportDb");
		exp.importTables("dest", "sql");
		// exp.exportTable("src", "dest", "error", "sys_tree");
	}

	public void importTables(String destSystemName, java.io.InputStream data) {
		InputStreamReader isr = null;
		BufferedReader reader = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		String line = null;
		try {
			conn = DBConnectionFactory.getConnection(destSystemName);
			conn.setAutoCommit(false);
			isr = new InputStreamReader(data);
			reader = new BufferedReader(isr);
			while ((line = reader.readLine()) != null) {
				if (StringUtils.startsWith(line, "#")) {
					continue;
				}
				if (StringUtils.startsWith(line, "-")) {
					continue;
				}
				if (StringUtils.startsWith(line, "/*")) {
					continue;
				}
				// logger.debug(line);
				stmt = conn.prepareStatement(line);
				stmt.executeUpdate();
			}
			conn.commit();
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(stmt);
			JdbcUtils.close(conn);
		}
	}

	public void importTables(String destSystemName, String sqlDir) {
		int retry = 0;
		boolean success = false;
		File dir = new File(sqlDir);
		File contents[] = dir.listFiles();
		if (contents != null) {
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isFile()
						&& contents[i].getName().endsWith(".sql")) {
					retry = 0;
					success = false;
					while (retry < 3 && !success) {
						try {
							retry++;
							java.io.InputStream data = FileUtils
									.getInputStream(contents[i]
											.getAbsolutePath());
							this.importTables(destSystemName, data);
							success = true;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

}