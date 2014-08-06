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

package com.glaf.core.execution;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.domain.TableDefinition;
import com.glaf.core.domain.util.BlobItemDomainFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.UUID32;

public class FileExecutionHelper {
	protected static final Log logger = LogFactory
			.getLog(FileExecutionHelper.class);

	public void createTable(Connection connection) {
		boolean autoCommit = false;
		try {
			autoCommit = connection.getAutoCommit();
			connection.setAutoCommit(false);
			TableDefinition tableDefinition = BlobItemDomainFactory
					.getTableDefinition();
			if (!DBUtils.tableExists(connection,
					BlobItemDomainFactory.TABLENAME)) {
				DBUtils.createTable(connection, tableDefinition);
			} else {
				DBUtils.alterTable(connection, tableDefinition);
			}
			connection.commit();
			connection.setAutoCommit(autoCommit);
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public boolean exists(Connection connection, String serviceKey, File file) {
		String sql = " select ID_ from " + BlobItemDomainFactory.TABLENAME
				+ " where SERVICEKEY_ = ? and FILENAME_ = ?  ";
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = connection.prepareStatement(sql);
			psmt.setString(1, serviceKey);
			psmt.setString(2, file.getAbsolutePath());
			rs = psmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
		return false;
	}

	public long lastModified(Connection connection, String serviceKey, File file) {
		String sql = " select LASTMODIFIED_ from "
				+ BlobItemDomainFactory.TABLENAME
				+ " where SERVICEKEY_ = ? and FILENAME_ = ? order by LASTMODIFIED_ desc ";
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = connection.prepareStatement(sql);
			psmt.setString(1, serviceKey);
			psmt.setString(2, file.getAbsolutePath());
			rs = psmt.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
		return -1L;
	}

	public void save(Connection connection, String serviceKey, File file) {
		String sql = " insert into "
				+ BlobItemDomainFactory.TABLENAME
				+ " (ID_, BUSINESSKEY_, FILEID_, SERVICEKEY_, NAME_, TYPE_, FILENAME_, PATH_, LASTMODIFIED_, LOCKED_, STATUS_, DATA_, CREATEBY_, CREATEDATE_)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement psmt = null;
		try {
			psmt = connection.prepareStatement(sql);
			psmt.setString(1, UUID32.getUUID());
			psmt.setString(2, serviceKey);
			psmt.setString(3, DigestUtils.md5Hex(file.getAbsolutePath()));
			psmt.setString(4, serviceKey);
			psmt.setString(5, file.getName());
			psmt.setString(6, "Execution");
			psmt.setString(7, file.getAbsolutePath());
			psmt.setString(8, file.getAbsolutePath());
			psmt.setLong(9, file.lastModified());
			psmt.setInt(10, 0);
			psmt.setInt(11, 1);
			psmt.setBytes(12, FileUtils.getBytes(file));
			psmt.setString(13, "system");
			psmt.setTimestamp(14, DateUtils.toTimestamp(new java.util.Date()));
			psmt.executeUpdate();
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
	}

}
