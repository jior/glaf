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

package com.glaf.activiti.util;

import java.util.List;

import org.activiti.engine.impl.db.DbSqlSession;
import org.apache.ibatis.session.SqlSession;

import com.glaf.activiti.extension.model.ExtensionEntity;

public abstract class ThreadHolder {

	static ThreadLocal<SqlSession> sqlSessionThreadLocal = new ThreadLocal<SqlSession>();

	static ThreadLocal<DbSqlSession> dbSqlSessionThreadLocal = new ThreadLocal<DbSqlSession>();

	static ThreadLocal<ExtensionEntity> extensionThreadLocal = new ThreadLocal<ExtensionEntity>();

	static ThreadLocal<ExtensionEntity> extensionTaskThreadLocal = new ThreadLocal<ExtensionEntity>();

	static ThreadLocal<List<String>> taskIdsThreadLocal = new ThreadLocal<List<String>>();

	public static void clear(){
		sqlSessionThreadLocal.remove();
		dbSqlSessionThreadLocal.remove();
		extensionThreadLocal.remove();
		extensionTaskThreadLocal.remove();
		taskIdsThreadLocal.remove();
	}
	
	public static DbSqlSession getDbSqlSession() {
		return dbSqlSessionThreadLocal.get();
	}

	public static ExtensionEntity getExtension() {
		return extensionThreadLocal.get();
	}

	public static ExtensionEntity getExtensionTask() {
		return extensionTaskThreadLocal.get();
	}

	public static SqlSession getSqlSession() {
		return sqlSessionThreadLocal.get();
	}

	public static List<String> getTaskIds() {
		return taskIdsThreadLocal.get();
	}

	public static void removeExtension() {
		extensionThreadLocal.remove();
	}

	public static void removeExtensionTask() {
		extensionTaskThreadLocal.remove();
	}

	public static void removeTaskIds() {
		taskIdsThreadLocal.remove();
	}

	public static void setDbSqlSession(DbSqlSession dbSqlSession) {
		dbSqlSessionThreadLocal.set(dbSqlSession);
	}

	public static void setExtension(ExtensionEntity extension) {
		extensionThreadLocal.set(extension);
	}

	public static void setExtensionTask(ExtensionEntity extension) {
		extensionTaskThreadLocal.set(extension);
	}

	public static void setSqlSession(SqlSession sqlSession) {
		sqlSessionThreadLocal.set(sqlSession);
	}

	public static void setTaskIds(List<String> taskIds) {
		taskIdsThreadLocal.set(taskIds);
	}

	private ThreadHolder() {

	}

}