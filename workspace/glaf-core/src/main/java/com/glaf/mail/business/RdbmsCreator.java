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

package com.glaf.mail.business;

import java.sql.Connection;
import java.sql.Statement;

import org.springframework.core.io.*;
 
import com.glaf.core.context.ContextFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.mail.domain.MailStorage;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.StringTools;

public class RdbmsCreator implements StorageCreator {

	public void createStorage(MailStorage storage) throws Exception {
		String dataTable = storage.getDataTable();
		if (DBUtils.tableExists(dataTable)) {
			return;
		}
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ContextFactory.getConnection();

			String dbType = DBConnectionFactory.getDatabaseType(conn);
			String filename = "com/glaf/core/mail/business/sql/create/mail."
					+ dbType + ".create.sql";
			Resource resource = new ClassPathResource(filename);

			if (!resource.exists()) {
				throw new RuntimeException(filename + " is not exists");
			}
			byte[] bytes = FileUtils.getBytes(resource.getInputStream());
			String content = new String(bytes);
			content = StringTools.replaceIgnoreCase(content, "${tableName}",
					dataTable);
			System.out.println(content);
			stmt = conn.createStatement();
			stmt.executeUpdate(content);
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

}