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

import java.sql.Connection;
 
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SqlRunner;
import org.junit.Test;

import com.alibaba.druid.util.JdbcUtils;
import com.glaf.core.jdbc.DBConnectionFactory;

public class MyBatisJdbcRunnerTest {

	@Test
	public void testSelectMany() {
		Connection connection = null;
		SqlRunner runner = null;
		try {
			connection = DBConnectionFactory.getConnection();
			runner = new SqlRunner(connection);
			Object[] args = { "SYS", "fs_%" };
			List<Map<String, Object>> dataList = runner
					.selectAll(
							" select * from SYS_PROPERTY where CATEGORY_ = ? and NAME_ like ? ",
							args);
			System.out.println(dataList);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(connection);
		}
	}

	@Test
	public void testSelectOne() {
		Connection connection = null;
		SqlRunner runner = null;
		try {
			connection = DBConnectionFactory.getConnection();
			runner = new SqlRunner(connection);
			Object args = "res_system_name";
			Map<String, Object> dataMap = runner.selectOne(
					"select * from SYS_PROPERTY where NAME_ = ? ", args);
			System.out.println(dataMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(connection);
		}
	}

	@Test
	public void testSelectOne2() {
		Connection connection = null;
		SqlRunner runner = null;
		try {
			connection = DBConnectionFactory.getConnection("wechat");
			runner = new SqlRunner(connection);
			Object args = "res_system_name";
			Map<String, Object> dataMap = runner.selectOne(
					"select * from SYS_PROPERTY where NAME_ = ? ", args);
			System.out.println(dataMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(connection);
		}
	}

	@Test
	public void testSelectOne3() {
		Connection connection = null;
		SqlRunner runner = null;
		try {
			connection = DBConnectionFactory.getConnection("yz");
			runner = new SqlRunner(connection);
			Object args = "res_system_name";
			Map<String, Object> dataMap = runner.selectOne(
					"select * from SYS_PROPERTY where NAME_ = ? ", args);
			System.out.println(dataMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(connection);
		}
	}

}
