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

package com.glaf.core.test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SqlRunner;
import org.junit.Test;

import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.EntityService;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ThreadFactory;
import com.glaf.test.AbstractTest;

public class MyBatisTest extends AbstractTest {

	protected EntityService entityService;

	@Test
	public void testNextDbidBlock() {
		entityService = super.getBean("entityService");
		Thread thread = new EntityTestThread(entityService);
		for (int i = 0; i <= 1; i++) {
			ThreadFactory.run(thread);
		}
	}

	@Test
	public void testSqlRunnerSelectOne() {
		SqlRunner runner = null;
		java.sql.Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			runner = new SqlRunner(conn);
			Map<String, Object> row = runner.selectOne(
					"select * from sys_dbid where NAME_ = ? ", "next.dbid");
			System.out.println("One#######:" + row);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
	}

	@Test
	public void testSqlRunnerSelectAll() {
		SqlRunner runner = null;
		java.sql.Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			runner = new SqlRunner(conn);
			List<Map<String, Object>> rows = runner
					.selectAll("select * from sys_dbid");
			System.out.println("All#######:" + rows);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
	}

}