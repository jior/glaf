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

import java.io.Reader;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.alibaba.druid.util.JdbcUtils;
import com.glaf.core.base.TreeModel;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.DBConnectionFactory;

public class MyBatisMultiConnectionTest {

	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	static {
		try {
			reader = Resources.getResourceAsReader("configuration.xml");
			// java.util.Properties properties =
			// com.glaf.core.util.PropertiesUtils.loadClassPathResource("/jdbc.properties");
			// sqlSessionFactory = new
			// SqlSessionFactoryBuilder().build(reader,properties);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		SqlSession session = null;
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			session = sqlSessionFactory.openSession(conn);
			User user = session.selectOne("getUserById", "root");
			System.out.println(user.toJsonObject().toJSONString());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
			JdbcUtils.close(conn);
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("用时（耗秒）：" + (time));

		for (int i = 0; i < 100; i++) {
			start = System.currentTimeMillis();
			session = null;
			conn = null;
			try {
				conn = DBConnectionFactory.getConnection("yz");
				session = sqlSessionFactory.openSession(conn);
				TreeModel tree = session.selectOne("getTreeModelByCode",
						"SYS000");
				System.out.println(tree.toJsonObject().toJSONString());
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (session != null) {
					session.close();
				}
				JdbcUtils.close(conn);
			}
			time = System.currentTimeMillis() - start;
			System.out.println("用时（耗秒）：" + (time));
		}
	}
}
